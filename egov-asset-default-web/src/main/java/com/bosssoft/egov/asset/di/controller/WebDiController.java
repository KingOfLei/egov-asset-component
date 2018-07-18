package com.bosssoft.egov.asset.di.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.pentaho.di.core.database.DatabaseMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.bosssoft.asset.etl.core.util.EtlHelper;
import com.bosssoft.asset.etl.core.util.KettleHelper;
import com.bosssoft.asset.etl.entity.TransResult;
import com.bosssoft.asset.etl.web.ResultUtils;
import com.bosssoft.egov.asset.attach.service.AssetAttachFileService;
import com.bosssoft.egov.asset.basic.BaseController;
import com.bosssoft.egov.asset.bizlog.api.BizLogHelper;
import com.bosssoft.egov.asset.bizlog.entity.ApiBizLog;
import com.bosssoft.egov.asset.bizlog.entity.BizOperConst;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.idgenerator.UUIDUtils;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.IOUtilsExt;
import com.bosssoft.egov.asset.common.util.MIMETypeUtils;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.PathUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.common.util.XmlUtilsExt;
import com.bosssoft.egov.asset.common.util.file.ZipFileUtilExt;
import com.bosssoft.egov.asset.di.DiConsts;
import com.bosssoft.egov.asset.di.TaskExecLog;
import com.bosssoft.egov.asset.di.entity.AssetDiTask;
import com.bosssoft.egov.asset.di.entity.AssetDiTaskDataSource;
import com.bosssoft.egov.asset.di.entity.AssetDiTaskDetails;
import com.bosssoft.egov.asset.di.entity.AssetDiTaskOrgCompare;
import com.bosssoft.egov.asset.di.service.AssetDiService;
import com.bosssoft.egov.asset.di.service.AssetDiTaskDataSourceService;
import com.bosssoft.egov.asset.di.service.AssetDiTaskDetailsService;
import com.bosssoft.egov.asset.di.service.AssetDiTaskOrgCompareService;
import com.bosssoft.egov.asset.di.service.AssetDiTaskService;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.platform.page.utils.StringUtil;
import com.bosssoft.platform.persistence.entity.Condition;
import com.bosssoft.platform.persistence.entity.Example.Criteria;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.web.binding.annotation.AjaxResponseBody;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;
import com.bosssoft.platform.runtime.web.response.AjaxResult;

/** 
*
* @ClassName   类名：WebDiController 
* @Description 功能说明：数据接口控制层
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2018年1月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2018年1月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Controller
@RequestMapping(value="egov/asset/di",name="数据接口")
public class WebDiController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(WebDiController.class);

	/**
	 * 导入导出进度
	 */
	public static Map<String, Integer> progress = new HashMap<String, Integer>();
	
	public static Map<String, String> message = new HashMap<String, String>();
	
	@Autowired
	private AssetDiTaskService taskService;
	
	@Autowired
	private AssetDiTaskDetailsService taskDetailsService;
	
	@Autowired
	private AssetGeneralService generalService;
	
	@Autowired
	private AssetDiService diService;
	
	@Autowired
	private AssetDiTaskDataSourceService dataSourceService;
	
	@Autowired
	private AssetAttachFileService attachFileService;
	 
	@Autowired
	private AssetDiTaskOrgCompareService orgCompareService;
	/**
	 * 
	 * <p>函数名称： dataExport       </p>
	 * <p>功能说明： 导出功能
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param exportTaskId 导出任务id
	 * @param exportTaskTypes 导出任务对应的类型列表
	 * @return 
	 *
	 * @date   创建时间：2018年1月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 * @throws BusinessException 
	 */
	@RequestMapping(value="export.do",name="数据导出")
	@AjaxResponseBody
	public AjaxResult dataExport(String exportTaskId,String billDate,String[] orgCodes,String[] exportTaskTypes, HttpServletResponse response){
		if(progress == null){
			progress = new HashMap<String, Integer>();
		}
		if(message == null){
			message = new HashMap<String, String>();
		}
        progress.put(getUserParams().get("__userCode__").toString(), -10);
        message.put(getUserParams().get("__userCode__").toString(),"正在导出数据...");
		
		//获取导出类型列表后 ，进行遍历操作。
		//导出步骤：循环步骤都在job中直接处理了
		//1、根据导出任务的类型，获取其导出任务信息，如源类型、目标类型、对应的任务xml配置信息等等
		//2、根据导出的类别及对应任务xml配置信息，进行逐个循环遍历任务，同时将目标文件写入xml配置中。
		//3、后期应该考虑附件的导出功能。
		//4、导出完成后，将目标文件进行压缩，最后打包发送前端。
		//5、记录日志信息：任务信息、导出文件名、导入任务类别、操作人，操作ip等 此操作可结合etl基础服务进行
		AssetDiTask task = taskService.getAssetDiTask(exportTaskId);
		
		if(task == null){
			//找不到此任务类型
			return ResultUtils.ERROR(StringUtilsExt.formatString("任务[{0}]配置不存在!", exportTaskId),exportTaskId);
		}
		Map<String,Object> params = getParams();
		String xmlFilePath = DiConsts.DI_ETL_RESOURCE_PATH + task.getTaskExpConfigXml();
		if(!FileUtilsExt.exists(xmlFilePath)){
			return ResultUtils.ERROR(StringUtilsExt.formatString("任务[{0}]对应xml配置文件{1}不存在!", exportTaskId, task.getTaskExpConfigXml()),exportTaskId);
		}
		//读取配置文件
		Document configXmlDoc = XmlUtilsExt.createDocument(xmlFilePath);		
		//预先处理 xml 还原原始状态
		Element docRoot = configXmlDoc.getRootElement();
	    @SuppressWarnings("unchecked")
		List<Element> nodes = configXmlDoc.selectNodes("//table");
	    for(Element node: nodes){
	    	node.clearContent();//清除节点数据
	    }
	    //处理特殊节点
	    Element specialOrgUnits  = (Element) docRoot.selectSingleNode("//specialOrgUnits");
	    if (specialOrgUnits != null)
           specialOrgUnits.clearContent();
        Element sourceOrgs = (Element) docRoot.selectSingleNode("//sourceOrgs");
        if (sourceOrgs != null)
           sourceOrgs.clearContent();
        
        //需要将配置xml文件放入导出目录中
      	String exportPath = StringUtilsExt.environmentSubstituteByMapObj("${__OUT_FILE_DIR__}${__ETL_OUT_UUID_FILE__}${__FILE_SEPARATOR__}", params);
      		
        //将新xml文件放入导出临时目录
      	String xmlName = UUIDUtils.getRandomUUIDToUpperCase() + ".xml";
		XmlUtilsExt.createPrettyPrint(configXmlDoc, exportPath + xmlName, "UTF-8");
		//需要生成临时文件夹目录
		params.put("GUID", UUIDUtils.getRandomUUIDToUpperCase());
		params.put("DIRPATH", exportPath);
		params.put("PREFIX", task.getTaskExpDataPrefix());//数据文件后缀
		params.put("XML_NAME", xmlName);//xml文件名称
		
		params.put("TASK_ID", exportTaskId);
		params.put("SRCCONTYPE",task.getTaskSrcConn());//源数据来源类型
		params.put("INTERFACEMODE",task.getTaskDestConn());//目标数据 转换类型 dzp dzf 等
		params.put("TASK_TYPE", StringUtilsExt.join(exportTaskTypes,","));//导出的类型 对应库的值为 taskType字段值　因为此字段为分组字段　同一类型的是一样的
		params.put("ORG_CODE_LIKES", getOrgCodeLikes(orgCodes, task.getTaskSrcAuxiliary()));        
		
		params.put("CONDITION", " OR 1=2");
		//此出为何这么写。。已经忘记了 直接照搬旧资产数据导入的。。。囧
        if (StringUtilsExt.isBlank(getCurrUser().getOrgCode())){
		   params.put("CONDITION", " OR 1=1");  
	    }
        //导出的截止日期 对卡片而已 为 getDate取得日期 单据是billDate日期
        params.put("JZRQ", billDate);
        
        //task 参数
        params.put("__CONFIG_CODE__", "DI_EXP");
        params.put("__CONFIG_NAME__", "数据接口导出");        
        //附件导出 
        
        //执行job
        List<DatabaseMeta> databaseMetas = new ArrayList<DatabaseMeta>();
        databaseMetas.add(EtlHelper.getDefaultDatabaseMeta("Source_Data"));
        databaseMetas.add(EtlHelper.getDefaultDatabaseMeta("System_data_Native"));
        TransResult transResult = KettleHelper.runJob(DiConsts.DI_ETL_JOB_PATH, databaseMetas, params);
        if(transResult.isException()){
        	//删除临时目录
        	FileUtilsExt.delete(new File(exportPath));
        	return ResultUtils.ERROR("导出异常：" + transResult.getMessage());
        }
        // 插入操作日志
        insertBizLog(task, "export", StringUtilsExt.join(exportTaskTypes,","));
        
        //获取文件 导出
        //导出文件名称
        String outFileName = StringUtilsExt.environmentSubstituteByMapObj(task.getTaskExpFileName(), params);
        
		// 导出成功，开始打包...
        progress.put(getUserParams().get("__userCode__").toString(), -10);
        message.put(getUserParams().get("__userCode__").toString(), "正在打包数据...");
        
        String ext = StringUtilsExt.toString(task.getTaskDestConn());  
		String contentType = "";
		if (StringUtilsExt.isNotBlank(ext)) {
			contentType = MIMETypeUtils.getContentType(contentType);
	    }
	    if (StringUtilsExt.isBlank(contentType)) {
			contentType = "application/octet-stream";
	    }
	    response.setContentType(contentType);
		response.setCharacterEncoding("GBK");
		OutputStream os = null;
		InputStream is = null;	
		AjaxResult result = ResultUtils.SUCCESS("导出成功");
		// 导出成功
		progress.put(getUserParams().get("__userCode__").toString(), 200);
		
		try{			
		   File outFile = new File(StringUtilsExt.environmentSubstituteByMapObj("${DIRPATH}${__FILE_SEPARATOR__}${XML_NAME}.${INTERFACEMODE}", params));
		   is = FileUtils.openInputStream(outFile);
		   response.setHeader("Content-disposition","attachment; filename=" + new String((outFileName + "." + ext).getBytes("GBK"),"ISO-8859-1"));
		   response.setHeader("Content-Length",Long.toString(outFile.length()));			
		   os = response.getOutputStream();
		   IOUtilsExt.copy(is, os);			 
		} catch(IOException io){
			logger.debug("导出异常...{0}...",io.getMessage());	
			response.setContentType("text/html");
			response.setCharacterEncoding("GBK");
			result = ResultUtils.ERROR("导出异常:" + io.getMessage());
		} finally {
			//释放资源
			try {
				response.flushBuffer();
				IOUtilsExt.closeQuietly(is);
				IOUtilsExt.closeQuietly(os);
			} catch (IOException e) {
				
			}
        	FileUtilsExt.delete(new File(exportPath));
		}
        return result;

	}

	/**
	 * <p>功能说明：获取拼接后的单位编码
	 * </p>
	 * <p>参数说明：
	 * @param orgCodes 单位编码
	 * @param oper 操作符
	 * @return
	 * </p>
	 *
	 * @date	创建时间：2018年2月2日
	 * @author	作者：chenzhibin
	 */
	private String getOrgCodeLikes(String[] orgCodes, String oper) {
		StringBuffer sb = new StringBuffer();
		
		if("like".equalsIgnoreCase(oper)){
			sb.append("(");
			for(String orgCode : orgCodes){
				sb.append("org_code like '" + orgCode + "%' or ");
			}
			sb.replace(sb.lastIndexOf("or"), sb.length(), ")");
		} else if("=".equalsIgnoreCase(oper) || StringUtilsExt.isBlank(oper)){
			sb.append(" org_code in (");
			for(String orgCode : orgCodes){
				sb.append("'" + orgCode + "',");
			}
			sb.replace(sb.lastIndexOf(","), sb.length(), ")");
		}
		
		return sb.toString();
	}

	/**
	 * 
	 * <p>函数名称：  dataImport      </p>
	 * <p>功能说明： 接口导入
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param importTaskId 导入类型id
	 * @param attachId 导入文件对应附件id
	 * @param importTaskTypes 导入任务列表
	 * @param orgCompareList 对照数据包
	 * @param dbType 直连：数据库类型-Oracle、MySQL、SQLite、MSSQLNative
	 * @param ip 直连：ip
	 * @param dbName 直连：数据库名
	 * @param port 直连：端口号
	 * @param userName 直连：用户名
	 * @param password 直连: 密码
	 * @return
	 *
	 * @date   创建时间：2018年1月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@RequestMapping(value = "dataImport.do",name="数据导入" )
	@AjaxResponseBody
	public AjaxResult dataImport(){
		Map<String, Object> paramsMap = getParameterMap();
		String importTaskId = MapUtilsExt.getString(paramsMap, "importTaskId"); 
		String attachId = MapUtilsExt.getString(paramsMap, "attachId"); 
		String reportQj = MapUtilsExt.getString(paramsMap, "reportQj"); 
		String importOrg = MapUtilsExt.getString(paramsMap, "importOrg");
		String[] importTaskTypes = StringUtilsExt.split(MapUtilsExt.getString(paramsMap, "importTaskTypes"),",");
		long startTime = System.currentTimeMillis();

		if(progress == null){
			progress = new HashMap<String, Integer>();
		}
		if(message == null){
			message = new HashMap<String, String>();
		}
		
		// 导入进度-2： 正在清除数据。。。
		progress.put(getUserParams().get("__userCode__").toString(), -2);
		message.put(getUserParams().get("__userCode__").toString(), "正在清除数据...");
		
		List<AssetDiTaskOrgCompare> orgCompareList = null;
		try{
			orgCompareList = getOrgCompareList(paramsMap);
		} catch(Exception e){
			return ResultUtils.ERROR(e.getMessage());
		}
		
		String dbId = MapUtilsExt.getString(paramsMap, "dbId"); 
		AjaxResult result = ResultUtils.SUCCESS("导入成功");		
		AssetDiTask task = taskService.getAssetDiTask(importTaskId);
		if(task == null){
			return ResultUtils.ERROR(StringUtilsExt.formatString("任务id({0})未找到，请确认！", importTaskId));
		}
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN, "接口任务类型{id:{}}", importTaskId);
		//更新对照单位信息
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN, "更新对照单位信息..开始....", importTaskId);
		orgCompareService.updateOrgCompare(orgCompareList);
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN, "更新对照单位信息..成功....", importTaskId);
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN, "处理业务数据..开始.......", importTaskId);
		diService.bussinessProcess(importTaskId, MapUtilsExt.getString(paramsMap, "importTaskTypes"), reportQj, orgCompareList);
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN, "处理业务数据..成功.......", importTaskId);
		
		// 插入操作日志
        insertBizLog(task, "import", StringUtilsExt.join(importTaskTypes,","));
		
		//判断是数据库直连 还是文件导入
		Map<String,Object> params = getParams();
		//单位对照数据包地址
	    String dataPath = StringUtilsExt.environmentSubstituteByMapObj("${__ETL_IN_FULL_PATH__}", params);		  			   
	    KettleHelper.writerDataToFile(dataPath,  BeanUtilsExt.converBean2List(orgCompareList));
	    params.put("dbConfigParam", dataPath);
	    params.put("FILETYPE", task.getTaskSrcConn());
    	params.put("TASK_ID", task.getTaskId());
    	params.put("TASK_TYPE", task.getTaskType());
        params.put("ISORGCOMPARE", "false");
        params.put("SRCCONTYPE",task.getTaskSrcConn());//源数据来源类型
		params.put("INTERFACEMODE",task.getTaskDestConn());//目标数据 转换类型 dzp dzf 等
		
		// 拼接单位对照id
		List<String> orgCompareIds = new ArrayList<String>();
		for(AssetDiTaskOrgCompare orgCompare : orgCompareList){
			orgCompareIds.add(orgCompare.getDestOrgId());
		}
		params.put("orgCompareIds", orgCompareIds);
		
        if(StringUtilsExt.isNotBlank(attachId) && StringUtilsExt.isBlank(dbId)){
			result = importFromFile(importTaskId, attachId, importTaskTypes, params);
		} else {
			params.put("ORG_CODE_LIKES", getOrgCodeLikes(importOrg.split(","), task.getTaskSrcAuxiliary()));
			params.put("ORG_CODE", getCurrUser().getOrgCode());
            result = importFromDatabase(importTaskId, importTaskTypes, params, dbId);
		}
        if(result.getStatusCode() == AjaxResult.STATUS_ERROR){
			return result;
		}
        
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"数据导入完成!耗时" + (System.currentTimeMillis()-startTime) + "毫秒" + ".......");
		// 重置状态
		progress.put(getUserParams().get("__userCode__").toString(), -2);
		message.put(getUserParams().get("__userCode__").toString(), "正在清除数据...");
        
		return result;
		
	}
	
	/**
	 * <p>功能说明：获取单位对照表，并对没有destOrgId的单位生产destOrgId
	 * </p>
	 * <p>参数说明：
	 * @param paramsMap
	 * @return
	 * </p>
	 *
	 * @date	创建时间：2018年2月6日
	 * @author	作者：chenzhibin
	 */
	private List<AssetDiTaskOrgCompare> getOrgCompareList(Map<String, Object> paramsMap) {
		List<AssetDiTaskOrgCompare> orgCompareList = JSONArray.parseArray(MapUtilsExt.getString(paramsMap, "orgCompareList"), AssetDiTaskOrgCompare.class);
		for(AssetDiTaskOrgCompare orgCompare : orgCompareList){
			String sql = "SELECT ORG_ID, ORG_CODE FROM AIMS_BASIC_ORG WHERE ORG_CODE = '" + orgCompare.getDestOrgCode() + "'";
			// 校验destOrgId是否与系统中的一样
			List<Map<String,Object>> result = generalService.queryCommon(sql);
			if(result != null && result.size() > 0){
				// 系统已有该单位，直接获取ORG_ID
				Map<String, Object> orgData = result.get(0);
				orgCompare.setDestOrgId(orgData.get("ORG_ID") + "");
			} else{
				// 新单位生成新的ORG_ID
				orgCompare.setDestOrgId(ComponetIdGen.newWKID() + "");
			}
		}
		
		return setOrgComparePid(orgCompareList);
	}

	private List<AssetDiTaskOrgCompare> setOrgComparePid(List<AssetDiTaskOrgCompare> orgCompareList) {
		for(AssetDiTaskOrgCompare orgCompare : orgCompareList){
			if(StringUtilsExt.isBlank(orgCompare.getDestOrgPid())){
				// 在列表中查找上级
				orgCompare.setDestOrgPid(getOrgComparePid(orgCompareList, orgCompare));
			}
		}
		return orgCompareList;
	}

	/**
	 * 获取单位对照表上级ID
	 * @param orgCompareList
	 * @param orgCompare
	 * @return
	 */
	private String getOrgComparePid(List<AssetDiTaskOrgCompare> orgCompareList, AssetDiTaskOrgCompare orgCompare) {
		if(StringUtilsExt.isBlank(orgCompare.getDestOrgPcode())){
			throw new RuntimeException("单位编码【" + orgCompare.getDestOrgCode() + "】的上级编码不能为空！");
		} else {
			// 根据上级编码补充上级ID
			String sql = "SELECT ORG_ID, ORG_CODE FROM AIMS_BASIC_ORG WHERE ORG_CODE = '" + orgCompare.getDestOrgPcode() + "'";
			List<Map<String,Object>> result = generalService.queryCommon(sql);
			if(result != null && result.size() > 0){
				Map<String,Object> orgData = result.get(0);
				return orgData.get("ORG_ID") + "";
			} else{
				// 验证对照列表中是否有上级单位
				for(AssetDiTaskOrgCompare org : orgCompareList){
					if(orgCompare.getDestOrgPcode().equals(org.getDestOrgCode())){
						return org.getDestOrgId();
					}
				}
			}
		}
		throw new RuntimeException("单位编码【" + orgCompare.getDestOrgCode() + "】的上级编码不存在！");
	}

	/**
	 * 
	 * <p>函数名称：importFromDatabase        </p>
	 * <p>功能说明：数据库直连方式导入
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param taskMxId
	 * @param params
	 * @param dbType
	 * @param ip
	 * @param dbName
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 *
	 * @date   创建时间：2018年1月18日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 * @param importTaskTypes 
	 * @param importTaskTypes 
	 */
	private AjaxResult importFromDatabase(String importTaskId, String[] importTaskTypes, Map<String,Object> params, String dbId){
		AjaxResult result = ResultUtils.SUCCESS("导入成功");	
		
		progress.put(getUserParams().get("__userCode__").toString(), -1);
		message.put(getUserParams().get("__userCode__").toString(), "正在导入数据...");
		
		params.put("TASK_TYPE", StringUtilsExt.join(importTaskTypes, ","));
		List<DatabaseMeta> dataMetaList = new ArrayList<DatabaseMeta>();
		AssetDiTaskDataSource dataSource = dataSourceService.getDataSourceById(dbId);
		dataMetaList.add(KettleHelper.buildDatabaseMeta("Source_Data", dataSource.getDbType(), dataSource.getIp(), dataSource.getDbName(), dataSource.getPort(), dataSource.getUserName(), dataSource.getPassword()));
		dataMetaList.add(EtlHelper.getDefaultDatabaseMeta("System_data_Native"));
		//task 参数
        params.put("__CONFIG_CODE__", "DI_IMP");
        params.put("__CONFIG_NAME__", "数据接口导入(数据库直连)");  
        params.put("DIRPATH", "import");
        params.put("XML_NAME", UUIDUtils.getRandomUUID());
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"开始导入{}任务数据(数据库直连).......",importTaskId);
        TransResult transResult = KettleHelper.runJob(DiConsts.DI_ETL_RESOURCE_PATH + "Trans_OutActionJob.kjb", dataMetaList,params);
        
        // 修复数据
        Condition qryCondtion = new Condition(AssetDiTaskDetails.class);
		Criteria criteria = qryCondtion.createCriteria().andIsNotNull("repairSql").andEqualTo("state", 1).andEqualTo("taskId", importTaskId);
		
		if(importTaskTypes != null && importTaskTypes.length > 0){
			criteria.andIn("taskType", Arrays.asList(importTaskTypes));
		}
		qryCondtion.orderBy("taskNo");
		List<AssetDiTaskDetails> taskDetails = taskDetailsService.getTaskDetailsListByCondition(qryCondtion);
		for(AssetDiTaskDetails taskDetail : taskDetails){
			String repairSql = taskDetail.getRepairSql();
			
			if(StringUtilsExt.isNotBlank(repairSql)){
				@SuppressWarnings("unchecked")
				List<String> orgCompareIds = (List<String>) params.get("orgCompareIds");
				repairSql = repairSql.replaceAll("\\__ORG_ID__", StringUtilsExt.join(orgCompareIds, ","));
				repairSql = repairSql.replaceAll("\\__ID__", ComponetIdGen.newWKID() + "");
				repairSql = repairSql.replaceAll("\\__USER_CODE__", getUserParams().get("__userCode__").toString());
				String[] sqls = repairSql.split("__SEPARATOR__");
				
				for(String sql : sqls){
					logger.debug("修复数据=> {}", StringUtilsExt.trim(sql));
					generalService.commonUpdate(StringUtilsExt.trim(sql));
		        	message.put(getUserParams().get("__userCode__").toString(), StringUtilsExt.trim(sql));
				}
			}
		}
        
    	if(transResult.isException()){
    		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"导入{}任务数据(数据库直连)异常：{}",importTaskId, transResult.getMessage());
    		result = ResultUtils.ERROR(StringUtilsExt.formatString("导入{0}任务数据(数据库直连)异常：{1}", importTaskId, transResult.getMessage()));
    	}
		
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：importFromFile        </p>
	 * <p>功能说明：文件方式导入
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param importTaskId
	 * @param task
	 * @param attachId
	 * @param orgCompareList
	 * @param exportTaskTypes
	 * @param dbType
	 * @param ip
	 * @param dbName
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 *
	 * @date   创建时间：2018年1月18日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@SuppressWarnings("unchecked")
	private AjaxResult importFromFile(String importTaskId, String attachId, String[] exportTaskTypes,Map<String,Object> params){
		AjaxResult result = ResultUtils.SUCCESS("导入成功");		
		String importPath = StringUtilsExt.environmentSubstituteByMapObj("${__IN_FILE_DIR__}${__ETL_OUT_UUID_FILE__}${__FILE_SEPARATOR__}", params);
		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"解压文件(附件id:{}).......", attachId);			
		try {
			// 导入状态-1：正在解压文件。。。
			progress.put(getUserParams().get("__userCode__").toString(), -1);
			message.put(getUserParams().get("__userCode__").toString(), "正在解压文件...");
			
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
				return ResultUtils.ERROR("未找到xml配置文件或者配置文件名含中文，请先调整后再导入！");
			}
			
			Document doc = XmlUtilsExt.createDocument(fileList.get(0));
			
			Condition qryCondtion = new Condition(AssetDiTaskDetails.class);
			Criteria criteria = qryCondtion.createCriteria().andEqualTo("state", 1)
			.andEqualTo("taskId", importTaskId);
			if(exportTaskTypes != null && exportTaskTypes.length > 0){
				criteria.andIn("taskType", Arrays.asList(exportTaskTypes));
			}
			qryCondtion.orderBy("taskNo");
			List<AssetDiTaskDetails> taskDetails = taskDetailsService.getTaskDetailsListByCondition(qryCondtion);
			TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"开始循环导入数据.......");
			List<DatabaseMeta> databaseMetas = new ArrayList<DatabaseMeta>();		   
			databaseMetas.add(EtlHelper.getDefaultDatabaseMeta("Source_Data"));
		    databaseMetas.add(EtlHelper.getDefaultDatabaseMeta("System_data_Native"));		    
		  	params.put("F_SQL", "SELECT 1 FROM DUAL WHERE 1=2");		  	

			progress.put(getUserParams().get("__userCode__").toString(), 0);
		  	BigDecimal curProgress = new BigDecimal(0);
		  	
		  	BigDecimal prog = new BigDecimal(100).divide(new BigDecimal(taskDetails.size()), 2, RoundingMode.FLOOR);
		    for(AssetDiTaskDetails taskDetail : taskDetails) {
				String tableName = taskDetail.getSrcData();
	            String srcFiltType = taskDetail.getSrcFilter(); 
	            String taskMxId = taskDetail.getTaskMxId();
	            String auxiliary = taskDetail.getSrcAuxiliary();//源目标 辅助
	            String destTableName = taskDetail.getDestData(); // 目标表
	            //查找出存放单位信息的文件名
	            Element node = getElement(doc,tableName,auxiliary);
	            if (node == null){
	            	curProgress = curProgress.add(prog);
    	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());
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
	    	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());	            		
	            		continue;//未找到此配置文件  
	            	}
	            }
	            //数据文件
	            String dataFileName = childNode.getStringValue();
	            if (dataFileName == null || dataFileName.equals("")){
	            	curProgress = curProgress.add(prog);
    	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());	            	
	            	continue;//继续
	            }
	            //2014-09-03 xds 文件地址有变如： tmp\23232323\xxxx.data
	            //故直接截取 最后一个\到结束的文件名
	            dataFileName = dataFileName.substring(dataFileName.lastIndexOf("\\") + 1);
	            String dealType = taskDetail.getDealType();
	            String keyField = taskDetail.getKeyField();
	            if ( dealType == null) {
	            	return ResultUtils.ERROR("处理类型[F_DEAL_TYPE] 值为空，请填写正确数值(关联表ASSET_DI_TASK_DETAILS)");
	            }
	            if ("1".equals(dealType) && keyField == null) {
	            	return ResultUtils.ERROR("明细ID: " + taskMxId + "处理类型[F_DEAL_TYPE] 值为 完整导入，请填写正确的更新字段[F_KEY_FIELD]数值(关联表ASSET_DI_TASK_DETAILS)");
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
    	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());
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
        		User user = getCurrUser();
        		if(StringUtilsExt.isNotBlank(repairSql)){
        			curProgress = curProgress.add(prog.divide(new BigDecimal(2), 2, RoundingMode.FLOOR));
    	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());
    	        	message.put(getUserParams().get("__userCode__").toString(), "修复表[" + taskDetail.getDestData() + "]数据...");
        			logger.debug("修复表[{}]数据...", taskDetail.getDestData());
        			List<String> orgCompareIds = (List<String>) params.get("orgCompareIds");
        			repairSql = repairSql.replaceAll("\\__ORG_ID__", StringUtilsExt.join(orgCompareIds, ","));
        			repairSql = repairSql.replaceAll("\\__ID__", ComponetIdGen.newWKID() + "");
        			repairSql = repairSql.replaceAll("\\__USER_CODE__", user.getUserCode());
        			String[] sqls = repairSql.split("__SEPARATOR__");
        			
        			BigDecimal sqlProg = prog.divide(new BigDecimal(2), 2, RoundingMode.FLOOR).divide(new BigDecimal(sqls.length), 2, RoundingMode.FLOOR);
        			for(String sql : sqls){
        				logger.debug("修复数据=> {}", StringUtilsExt.trim(sql));
        				generalService.commonUpdate(StringUtilsExt.trim(sql));
        				curProgress = curProgress.add(sqlProg);
        	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());
        	        	message.put(getUserParams().get("__userCode__").toString(), StringUtilsExt.trim(sql));
        			}
        		} else{
        			curProgress = curProgress.add(prog);
    	        	progress.put(getUserParams().get("__userCode__").toString(), curProgress.intValue());	
        		}
        		
	        	if(transResult.isException()){
	        		TaskExecLog.getInstance().writeToLog(TaskExecLog.TYPE_RUN,"导入{}任务数据异常：{}",taskMxId, transResult.getMessage());
	        		result = ResultUtils.ERROR(StringUtilsExt.formatString("导入{0}任务数据异常：{1}", taskMxId, transResult.getMessage()));
	        		message.put(getUserParams().get("__userCode__").toString(), "导入[" + transResult.getMessage() + "]任务数据异常");
	        		break;
	        	}
			}
	    } finally {
	    	FileUtilsExt.delete(new File(importPath));
	    }
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：importOrgCompare        </p>
	 * <p>功能说明：数据导入 单位对照
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param importTaskId 导入类型
	 * @param attachId 数据包id 
	 * @return
	 *
	 * @date   创建时间：2018年1月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */	
	@RequestMapping(value = "importOrgCompare.do",name="数据导入单位对照" )
	@AjaxResponseBody
	public AjaxResult importOrgCompare(String importTaskId,String attachId, String dbId, String importOrg){
		AjaxResult result = ResultUtils.SUCCESS("对照成功");
		AssetDiTaskDetails conditionTaskDetails = new AssetDiTaskDetails();
		conditionTaskDetails.setTaskId(importTaskId);
		conditionTaskDetails.setState(1);
		conditionTaskDetails.setDestData("AIMS_BASIC_ORG");
		//获取单位信息
		AssetDiTaskDetails orgTaskDetails = taskDetailsService.getAssetDiTaskDetails(conditionTaskDetails);		
		try {
			Long batchId = ComponetIdGen.newWKID();
	        User user = getCurrUser();
	        Map<String, Object> params = getUserParams();
	    	params.put("F_TASK_MX_ID", orgTaskDetails.getTaskMxId());//明细ID 即 对应配置ID
	    	params.put("BATCH_ID", batchId);
	    	params.put("ORG_ID", user.getOrgId());
	    	params.put("ORG_CODE", user.getOrgCode());
	    	params.put("ORG_NAME", user.getOrgName());
	    	
	        if(StringUtilsExt.isBlank(attachId) && StringUtilsExt.isNotBlank(dbId)){
				AssetDiTask task = taskService.getAssetDiTask(importTaskId);
				result = orgCompareFromDatabase(user, task, dbId, params, StringUtilsExt.isNotBlank(importOrg) ? importOrg.split(",") : null);
			} else {
				//文件方式
				result = orgCompareFromFile(user, attachId, orgTaskDetails, params);				
			}
	        
	        if(result.getStatusCode() == AjaxResult.STATUS_ERROR){
				return result;
			}
			//表名数据已经入里临时表 ，进行更新操作 存在就跳过 不存在新增
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ASSET_DI_TASK_ORG_COMPARE(ORG_ID, ORG_CODE, ORG_NAME, SRC_ORG_ID, SRC_ORG_CODE, SRC_ORG_NAME)");
			sql.append(" SELECT ORG_ID, ORG_CODE, ORG_NAME, SRC_ORG_ID, SRC_ORG_CODE, SRC_ORG_NAME ");
			sql.append(" FROM ASSET_DI_TASK_ORG_COMPARE_TMP TMP ");
			sql.append(" WHERE TMP.ID = ").append(batchId);
			sql.append(" AND NOT EXISTS (");
			sql.append(" SELECT 1 FROM ASSET_DI_TASK_ORG_COMPARE ORG ");
			sql.append(" WHERE TMP.ORG_ID=ORG.ORG_ID ");
			sql.append(" AND TMP.SRC_ORG_ID=ORG.SRC_ORG_ID ");
			sql.append(")");
			generalService.commonUpdate(sql.toString());
			
			//获取本次导入对照信息
			List<AssetDiTaskOrgCompare> orgList = orgCompareService.getAssetDiTaskOrgCompareFromTmp(user.getOrgLongId(), batchId);
			result.setData(orgList);
			//删除临时表数据			
			sql.delete(0, sql.length());
			sql.append(" DELETE FROM ASSET_DI_TASK_ORG_COMPARE_TMP WHERE ID =").append(batchId);
			generalService.commonUpdate(sql.toString());
            
		} finally {
        	
		}
		
    	return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getOrgListFromDataBase.do", name="根据数据源获取数据源单位信息")
	public List<Map<String,Object>> getOrgListFromDataBase(String importTaskId, String dbId){
		AssetDiTaskDetails conditionTaskDetails = new AssetDiTaskDetails();
		conditionTaskDetails.setTaskId(importTaskId);
		conditionTaskDetails.setState(1);
		conditionTaskDetails.setDestData("AIMS_BASIC_ORG");
		//获取单位信息
		AssetDiTaskDetails orgTaskDetails = taskDetailsService.getAssetDiTaskDetails(conditionTaskDetails);
		
		User user = getCurrUser();
		Long batchId = ComponetIdGen.newWKID();
		// 参数列表
		Map<String, Object> params = getUserParams();
    	params.put("F_TASK_MX_ID", orgTaskDetails.getTaskMxId());//明细ID 即 对应配置ID
    	params.put("BATCH_ID", batchId);
    	params.put("ORG_ID", user.getOrgId());
    	params.put("ORG_CODE", user.getOrgCode());
    	params.put("ORG_NAME", user.getOrgName());
    	AssetDiTask task = taskService.getAssetDiTask(importTaskId);
		
		AjaxResult result = orgCompareFromDatabase(user, task, dbId, params, null);
		
		if(result.getStatusCode() == AjaxResult.STATUS_ERROR){
			throw new RuntimeException(result.getMessage());
		}
		
		// 获取临时表对照单位信息
		List<Map<String,Object>> data = generalService.queryCommon("SELECT T.*, T.SRC_FINANCE_BUDGET_CODE || T.SRC_ORG_NAME SRC_ORG_TITLE FROM ASSET_DI_TASK_ORG_COMPARE_TMP T WHERE ID = " + batchId);
		// 清除临时表数据
		generalService.commonUpdate("DELETE FROM ASSET_DI_TASK_ORG_COMPARE_TMP WHERE ID =" + batchId);
		
		return data;
	}
	
	/**
	 * 
	 * <p>函数名称：    orgCompareFromDatabase    </p>
	 * <p>功能说明： 数据库直连时 直接返回数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param user
	 * @param batchId
	 * @param task
	 * @param dbType
	 * @param ip
	 * @param dbName
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 *
	 * @date   创建时间：2018年1月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private AjaxResult orgCompareFromDatabase(User user, AssetDiTask task, String dbId,Map<String,Object> params, String[] importOrgs){
		AjaxResult result = ResultUtils.SUCCESS("对照成功");
		StringBuilder sql = new StringBuilder(StringUtilsExt.toString(task.getTaskSrcAuxiliary2()).replaceAll("\\[", "\\${").replaceAll("\\]", "}"));
		if(StringUtilsExt.isBlank(sql)){
			return ResultUtils.ERROR("单位对照取数语句不能为空!");
		}
		
		if(importOrgs != null){
			sql.append(" and ").append(getOrgCodeLikes(importOrgs, task.getTaskSrcAuxiliary()));
		}
		
		List<DatabaseMeta> dataMetaList = new ArrayList<DatabaseMeta>();
		AssetDiTaskDataSource dataSource = dataSourceService.getDataSourceById(dbId);
		
		dataMetaList.add(KettleHelper.buildDatabaseMeta("Source_Data", dataSource.getDbType(), dataSource.getIp(), dataSource.getDbName(), dataSource.getPort(), dataSource.getUserName(), dataSource.getPassword()));
		dataMetaList.add(EtlHelper.getDefaultDatabaseMeta("System_data_Native"));
		params.put("__CONFIG_CODE__", "DI_IMP_ORG_COMPARE");
        params.put("__CONFIG_NAME__", "数据接口导入-单位对照(数据库直连)");
        params.put("sql", sql.toString());
		TransResult transResult = KettleHelper.runTrans(DiConsts.DI_ETL_RESOURCE_PATH + "Trans_OrgCompareFromDatabase.ktr", params, dataMetaList);
		if(transResult.isException()){
			return ResultUtils.ERROR("获取单位文件信息异常，请先确认取数语句是否正确！");
		}
		return result;
	}

	
	/**
	 * 
	 * <p>函数名称： orgCompareFromFile       </p>
	 * <p>功能说明：对照-从文件中获取
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param importTaskId 任务id
	 * @param attachId 数据包对应 附件id
	 * @param orgTaskDetails 任务明细对象
	 * @return
	 *
	 * @date   创建时间：2018年1月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private AjaxResult orgCompareFromFile(User user, String attachId, AssetDiTaskDetails orgTaskDetails,Map<String,Object> params){
		String tmpDir = PathUtil.getTempDirectory();
		try {
				//解压压缩包
			ZipFileUtilExt.decompressFile(attachFileService.downloadFile(attachId), tmpDir, "^.+((\\.data)|(\\.xml))$");
			List<File> fileList = FileUtilsExt.listFiles(new File(tmpDir), new FileFilter(){
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().toLowerCase().endsWith(".xml");
				}			
			});
			if(fileList == null || fileList.isEmpty()){
				return ResultUtils.ERROR("未找到xml配置文件或者配置文件名含中文，请先调整后再导入！");
			}
			Document doc = XmlUtilsExt.createDocument(fileList.get(0));
			//查找出存放单位信息的文件名
	        Element node = getElement(doc,orgTaskDetails.getSrcData(),orgTaskDetails.getSrcAuxiliary());
	        if ( node == null){
	        	return ResultUtils.ERROR("未找到单位文件，请先调整后再导入！");
	        }
			String orgFileName = "";
	        //查找子节点dataFilePath 的值即 相应导入信息的源文件名
	        boolean hasNode = false;
	        for(Object element : (List<?>)node.selectNodes("./*")){
	        	Element dataNode = (Element) element;
	        	//查找节点
	        	if ( dataNode.getName().indexOf("dataFilePath") >=0 ){
	        		hasNode = true;
	        		if (StringUtilsExt.isNotBlank(dataNode.getStringValue())){
	        			 orgFileName = dataNode.getStringValue();
	        			 orgFileName = orgFileName.substring(orgFileName.lastIndexOf("\\") + 1);
		        		 orgFileName = tmpDir + orgFileName;
		        		 break;
	        		}
	        	}	
	        }
			if (!hasNode) {
				return ResultUtils.ERROR("未找到存放单位文件文件节点，请先确认xml配置文件是否正确！");  
	        }
			
	        if (StringUtilsExt.isBlank(orgFileName))
	        	return ResultUtils.ERROR("未找到单位文件信息,请先确认xml配置文件是否正确！");
	        
	        params.put("ORGFILENAME", orgFileName);	      
	    	params.put("__CONFIG_CODE__", "DI_IMP_ORG_COMPARE");
            params.put("__CONFIG_NAME__", "数据接口导入-单位对照");
	    	TransResult transResult = KettleHelper.runTrans(DiConsts.DI_ETL_RESOURCE_PATH + "Trans_OrgCompareFromDzp.ktr", params);
			if(transResult.isException()){
				return ResultUtils.ERROR("获取单位文件信息异常，请先确认数据包xml配置文件是否正确！");
			}			
            
		} finally {
        	FileUtilsExt.delete(new File(tmpDir));
		}
		
		return ResultUtils.SUCCESS("对照成功");
	}
	
	private Map<String,Object> getParams(){
		Map<String, Object> params = AppContext.getAppContext().getRequestParamsMap();
		//默认加入当前登录单位信息		
		params.putAll(getUserParams());
		params.putAll(EtlHelper.getWebParams());
		params.put("DRC_SQL", getCurrUser().getDrcSql());
		return params;
	}
	
	/**
	 * 
	 * <p>函数名称：  getElement      </p>
	 * <p>功能说明： 查找节点
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param doc
	 * @param name
	 * @param destAuiliary
	 * @return
	 *
	 * @date   创建时间：2013-5-22
	 * @author 作者：谢德寿
	 */
	@SuppressWarnings("unchecked")
	public static Element getElement(Document doc,String name,String destAuiliary){
		
		if (destAuiliary == null)
			destAuiliary = "";
		//translate(string1,string2,string3) 把 string1 中的 string2 替换为 string3。
		//这里将节点转成大写 进行判断
		List<Element> nodes = doc.selectNodes("//table[translate(@name,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='" + name.toUpperCase() +"']");
		for(Element node : nodes){
			if (node != null){
				if ("".equals(destAuiliary) ||
						(node.getParent() != null
							&& node.getParent().getParent() != null)
							&& (node.getParent().getParent().attribute("billTag") == null)
							|| node.getParent().getParent().attribute("billTag").getValue().equals(destAuiliary)){
					return node;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * <p>函数名称：  getParameterMap      </p>
	 * <p>功能说明： 获取参数信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2018年1月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private static Map<String, Object> getParameterMap(){
		//获取request
    	Map<String,Object> paramsMap = WebApplicationContext.getContext().getWebRequestContext().getRequestParamsMap();
        if(paramsMap != null && !paramsMap.isEmpty()) return paramsMap;
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			BufferedReader br = request.getReader();
	    	if(br == null) return paramsMap;
			String str;
	    	StringBuilder wholeStr = new StringBuilder();
	    	while((str = br.readLine()) != null){
	    	wholeStr.append(str);
	    	}
	    	String paramsArray [] = StringUtilsExt.split(wholeStr.toString(),'&');
	    	for(String p:paramsArray){
	    		String deCodeStr = URLDecoder.decode(p,"utf-8");
	    		int splitIndex = deCodeStr.indexOf("=");
	            String key = deCodeStr.substring(0,splitIndex);
	            String value = deCodeStr.substring(splitIndex+1);
	            paramsMap.put(key,value);
	        }  
    	} catch(IOException io){
    		throw new BusinessException("解析参数异常",io);
    	}
		return paramsMap;
	}
	
	@RequestMapping("getCurProgress.do")
	@ResponseBody
	public AjaxResult getCurProgress(){
		AjaxResult result = new AjaxResult();
		if(progress == null){
			result.setData(-2);
		} else{
			Integer prog = progress.get(getUserParams().get("__userCode__"));
			result.setMessage(message.get(getUserParams().get("__userCode__")));
			if(prog <= -10){
				result.setStatusCode(AjaxResult.STATUS_ERROR);
			}
			result.setData(prog == null ? 0 : prog);
		}
		result.setStatusCode(AjaxResult.STATUS_SUCCESS);
		return result;
	}
	
	private void insertBizLog(AssetDiTask task, String operType, String taskTypes) {
		ApiBizLog bizLog = new ApiBizLog();
		bizLog.setBizOperType(BizOperConst.OTHER);
		bizLog.setBizType("DATA_INTERFACE");
		bizLog.setBizTypeName("数据接口");
		bizLog.setOperDesc(("IMPORT".equalsIgnoreCase(operType) ? "导入" : "导出")
			+ "接口【" + task.getTaskName() + "】任务类型：" + taskTypes);
		BizLogHelper.saveBizLog(bizLog);
	}
}
