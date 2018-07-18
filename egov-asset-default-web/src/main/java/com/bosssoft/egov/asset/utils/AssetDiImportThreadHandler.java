package com.bosssoft.egov.asset.utils;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.di.core.database.DatabaseMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.asset.etl.core.util.EtlHelper;
import com.bosssoft.asset.etl.core.util.KettleHelper;
import com.bosssoft.asset.etl.entity.TransResult;
import com.bosssoft.egov.asset.attach.service.AssetAttachFileService;
import com.bosssoft.egov.asset.attach.service.AssetAttachFileServiceImpl;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.common.util.XmlUtilsExt;
import com.bosssoft.egov.asset.common.util.file.ZipFileUtilExt;
import com.bosssoft.egov.asset.di.DiConsts;
import com.bosssoft.egov.asset.di.TaskExecLog;
import com.bosssoft.egov.asset.di.controller.WebDiController;
import com.bosssoft.egov.asset.di.entity.AssetDiTaskDetails;
import com.bosssoft.egov.asset.di.service.AssetDiTaskDetailsService;
import com.bosssoft.egov.asset.di.service.AssetDiTaskDetailsServiceImpl;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.egov.asset.service.AssetGeneralServiceImpl;
import com.bosssoft.platform.persistence.entity.Condition;
import com.bosssoft.platform.persistence.entity.Example.Criteria;

public class AssetDiImportThreadHandler implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(AssetDiImportThreadHandler.class);
	
	private AssetAttachFileService attachFileService;
	private AssetDiTaskDetailsService taskDetailsService;
	private AssetGeneralService generalService;
	
	private String importTaskId;
	private String attachId;
	private String[] taskTypes;
	private Map<String, Object> params;
	
	public AssetDiImportThreadHandler(String importTaskId, String attachId, String[] taskTypes, Map<String, Object> params){
		this.importTaskId = importTaskId;
		this.attachId = attachId;
		this.taskTypes = taskTypes;
		this.params = params;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		initService();
		logger.debug("启用线程开始导入数据......");
		
		String importPath = StringUtilsExt.environmentSubstituteByMapObj("${__IN_FILE_DIR__}${__ETL_OUT_UUID_FILE__}${__FILE_SEPARATOR__}", params);
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"解压文件(附件id:{}).......", attachId);			
		try {
			// 导入状态-1：正在解压文件。。。
			WebDiController.progress.put(params.get("__userCode__").toString(), -1);
			
			ZipFileUtilExt.decompressFile(attachFileService.downloadFile(attachId), importPath, "^.+((\\.data)|(\\.xml))$");
			TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"解压文件完成!");
			List<File> fileList = FileUtilsExt.listFiles(new File(importPath), new FileFilter(){
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().toLowerCase().endsWith(".xml");
				}			
			});
			
			if(fileList == null || fileList.isEmpty()){
				TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"未找到xml配置文件或者配置文件名含中文，请先调整后再导入！");
				WebDiController.message.put(params.get("__userCode__").toString(), "未找到xml配置文件或者配置文件名含中文，请先调整后再导入！");
				return;
			}
			
			Document doc = XmlUtilsExt.createDocument(fileList.get(0));
			
			Condition qryCondtion = new Condition(AssetDiTaskDetails.class);
			Criteria criteria = qryCondtion.createCriteria().andEqualTo("state", 1)
			.andEqualTo("taskId", importTaskId);
			if(taskTypes != null && taskTypes.length > 0){
				criteria.andIn("taskType", Arrays.asList(taskTypes));
			}
			qryCondtion.orderBy("taskNo");
			
			// 获取导入任务明细
			List<AssetDiTaskDetails> taskDetails = taskDetailsService.getTaskDetailsListByCondition(qryCondtion);
			TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"开始循环导入数据.......");
			List<DatabaseMeta> databaseMetas = new ArrayList<DatabaseMeta>();		   
			databaseMetas.add(EtlHelper.getDefaultDatabaseMeta("Source_Data"));
		    databaseMetas.add(EtlHelper.getDefaultDatabaseMeta("System_data_Native"));		    
		  	params.put("F_SQL", "SELECT 1 FROM DUAL WHERE 1=2");		  	

			WebDiController.progress.put(params.get("__userCode__").toString(), 0);
		  	BigDecimal curProgress = new BigDecimal(0);
		  	BigDecimal prog = new BigDecimal(100).divide(new BigDecimal(taskDetails.size()), 2, RoundingMode.FLOOR);
		    for(AssetDiTaskDetails taskDetail : taskDetails) {
				String tableName = taskDetail.getSrcData();
	            String srcFiltType = taskDetail.getSrcFilter(); 
	            String taskMxId = taskDetail.getTaskMxId();
	            String auxiliary = taskDetail.getSrcAuxiliary();//源目标 辅助
	            String destTableName = taskDetail.getDestData(); // 目标表
	            //查找出存放单位信息的文件名
	            Element node = WebDiController.getElement(doc,tableName,auxiliary);
	            if (node == null){
	            	curProgress = curProgress.add(prog);
	            	WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());
	            	continue;
	            }
	            
	            if (srcFiltType != null){
	            	params.put("F_SRC_FILTER", srcFiltType);//源数据过滤条件
	            } else{
	            	params.remove("F_SRC_FILTER");
	            }
	            params.put("F_DEST_DATA", destTableName);//目标表名 即导入的表mi
	            //这里 可以扩张 用查找方式  
	            //如  xpath  ./*[contains(name(),'dataFilePath')] 
	           //表示查找当前节点下  节点名称包含 dataFilePath 的节点  
	            Element childNode = (Element) node.selectSingleNode("./dataFilePath");
	            if (childNode == null){
	            	childNode = (Element) node.selectSingleNode("./dataFilePath1");
	            	if (childNode == null){
	            		curProgress = curProgress.add(prog);
	    	        	WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());	            		
	            		continue;//未找到此配置文件  
	            	}
	            }
	            //数据文件
	            String dataFileName = childNode.getStringValue();
	            if (dataFileName == null || dataFileName.equals("")){
	            	curProgress = curProgress.add(prog);
    	        	WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());	            	
	            	continue;//继续
	            }
	            //2014-09-03 xds 文件地址有变如： tmp\23232323\xxxx.data
	            //故直接截取 最后一个\到结束的文件名
	            dataFileName = dataFileName.substring(dataFileName.lastIndexOf("\\") + 1);
	            String dealType = taskDetail.getDealType();
	            String keyField = taskDetail.getKeyField();
	            if ( dealType == null) {
	            	WebDiController.message.put(params.get("__userCode__").toString(), "处理类型[F_DEAL_TYPE] 值为空，请填写正确数值(关联表ASSET_DI_TASK_DETAILS)");
	            	break;
	            }
	            if ("1".equals(dealType) && keyField == null) {
	            	WebDiController.message.put(params.get("__userCode__").toString(), "明细ID[" + taskMxId + "]处理类型[F_DEAL_TYPE] 值为 完整导入，请填写正确的更新字段[F_KEY_FIELD]数值(关联表ASSET_DI_TASK_DETAILS)");
	            	break;
	            }
	            
	            params.put("F_DEAL_TYPE", dealType );//获取入库类型 1 更新插入 0 新增
				//避免变量被使用  用guid 作为变量名
				//这里特定  E24061C210A97B6DE043B509A8C02304
				//特殊情况 关键字 是根据配置，不知道有几个关键字  
	            //关键字  用 "," 隔开
	            params.put(DiConsts.MultiKey, StringUtilsExt.toString(keyField));
	      
	            //文件文件从上一步骤获取时 kettle 没有进行文件存在保护 故在不存在时 会保存
	            //未从上一步骤获取时 keettle 会进行过滤，不存在的不会进行添加
	            //这里统一进行判断是否存在
	            if (new File(importPath + File.separator + dataFileName).exists()){
	            	params.put("ORGFILENAME", importPath + File.separator + dataFileName);
	            } else{
	            	curProgress = curProgress.add(prog);
    	        	WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());
	            	continue;//不存在继续下一任务
	            }
	        	params.put("F_TASK_MX_ID", taskMxId);//明细ID 即 对应配置ID
	        	
	    		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"开始导入{}任务数据.......",taskMxId);
	    		//task 参数
	            params.put("__CONFIG_CODE__", "DI_IMP");
	            params.put("__CONFIG_NAME__", "数据接口导入");
	        	TransResult transResult = KettleHelper.runTrans(DiConsts.DI_ETL_RESOURCE_PATH + "trans_OutTransAction.ktr", params, databaseMetas);
	        	
	        	// 修复数据
        		String repairSql = taskDetail.getRepairSql();
        		if(StringUtilsExt.isNotBlank(repairSql)){
        			curProgress = curProgress.add(prog.divide(new BigDecimal(2), 2, RoundingMode.FLOOR));
    	        	WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());
        			logger.debug("修复表[{}]数据...", taskDetail.getDestData());
        			List<String> orgCompareIds = (List<String>) params.get("orgCompareIds");
        			repairSql = repairSql.replaceAll("\\__ORG_ID__", StringUtilsExt.join(orgCompareIds, ","));
        			repairSql = repairSql.replaceAll("\\__ID__", ComponetIdGen.newWKID() + "");
        			repairSql = repairSql.replaceAll("\\__USER_CODE__", params.get("__userCode__").toString());
        			String[] sqls = repairSql.split("__SEPARATOR__");
        			
        			BigDecimal sqlProg = prog.divide(new BigDecimal(2), 2, RoundingMode.FLOOR).divide(new BigDecimal(sqls.length), 2, RoundingMode.FLOOR);
        			for(String sql : sqls){
        				logger.debug("修复数据=> {}", StringUtilsExt.trim(sql));
        				generalService.commonUpdate(StringUtilsExt.trim(sql));
        				curProgress = curProgress.add(sqlProg);
        				WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());
        			}
        		} else{
        			curProgress = curProgress.add(prog);
    	        	WebDiController.progress.put(params.get("__userCode__").toString(), curProgress.intValue());	
        		}
        		
	        	if(transResult.isException()){
	        		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"导入{}任务数据异常：{}",taskMxId, transResult.getMessage());
	        		WebDiController.message.put(params.get("__userCode__").toString(), StringUtilsExt.formatString("导入任务[{0}]数据异常：{1}", taskMxId, transResult.getMessage()));
	        		break;
	        	}
			}
	    } catch(Exception e){
	    	WebDiController.message.put(params.get("__userCode__").toString(), e.getMessage());
	    } finally {
	    	FileUtilsExt.delete(new File(importPath));
	    }
	}

	private void initService(){
		if(attachFileService == null){
			attachFileService = AppContext.getAppContext().getBeanContext().getBean(AssetAttachFileServiceImpl.class);
		}
		if(taskDetailsService == null){
			taskDetailsService = AppContext.getAppContext().getBeanContext().getBean(AssetDiTaskDetailsServiceImpl.class);
		}
		if(generalService == null){
			generalService = AppContext.getAppContext().getBeanContext().getBean(AssetGeneralServiceImpl.class);
		}
	}
}
