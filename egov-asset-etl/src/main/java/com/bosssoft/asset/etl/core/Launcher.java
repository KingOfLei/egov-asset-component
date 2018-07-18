package com.bosssoft.asset.etl.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.bosssoft.asset.etl.comp.handler.Handler;
import com.bosssoft.asset.etl.comp.handler.HandlerType;
import com.bosssoft.asset.etl.comp.handler.ThreadHandler;
import com.bosssoft.asset.etl.core.util.KettleHelper;
import com.bosssoft.asset.etl.core.util.StepMetaHelper;
import com.bosssoft.asset.etl.entity.AssetEtlConfig;
import com.bosssoft.asset.etl.entity.AssetEtlConfigAttr;
import com.bosssoft.asset.etl.entity.AssetEtlExportTask;
import com.bosssoft.asset.etl.entity.DetailHeaders;
import com.bosssoft.asset.etl.entity.ReportDetail;
import com.bosssoft.asset.etl.entity.TransResult;
import com.bosssoft.asset.etl.service.AssetEtlConfigAttrService;
import com.bosssoft.asset.etl.service.AssetEtlConfigService;
import com.bosssoft.asset.etl.service.AssetEtlExportTaskService;
import com.bosssoft.asset.etl.service.AssetEtlRuleConfigService;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.util.AssertExt;
import com.bosssoft.egov.asset.common.util.ColumnUtilsExt;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.PathUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;

/** 
*
* @ClassName   类名：Launcher 
* @Description 功能说明：任务调用入口
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月12日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月12日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public final class Launcher {
	
	private static Logger logger = LoggerFactory.getLogger(Launcher.class);

	private static Launcher INSTANCE;
	
	@Resource
	private AssetEtlConfigService configService;
	
	@Resource
	private AssetEtlRuleConfigService ruleConfigService;
	
	@Resource
	private AssetEtlConfigAttrService conifgAttrService;
	
	@Resource
	private AssetEtlExportTaskService exportTaskService;
	
	private Launcher(){
		//注入
		SpringExtensionHelper.initAutowireFields(this);
	}

	public static synchronized Launcher getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new Launcher();
		}
		return INSTANCE;
   }
	
	
	/**
	 * 
	 * <p>函数名称：exportExcel        </p>
	 * <p>功能说明：根据数据格式，导出excel。
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param excelFilePath
	 * @param exportData
	 * @param taskId	任务ID，用于区分是直接导出还是启用线程导出，导出完成后新建或更新任务信息
	 *
	 * @date   创建时间：2017年9月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public TransResult exportExcel(String excelFilePath, ReportDetail exportData,Map<String,Object> params, Long taskId){
		if(params == null){
			params = new HashMap<String,Object>();
		}
		//设置变量
		params.put("__CONFIG_CODE__", "gridExecelExport");
		params.put("__CONFIG_NAME__", "网格数据excel导出");
		params.put("excelFilePath", excelFilePath);
		params.put(EtlConst.ETL_OUT_FULL_PATH, excelFilePath + "." + exportData.getExtension());
		params.put("execelTemplate", PathUtil.getWebRootPath() + File.separator + "resources" + File.separator + "template" + File.separator + "exportTemplate." + exportData.getExtension());//模版地址 这个固定的
		
		TransResult result = KettleHelper.runExportExcelTask(exportData, params);
		saveFile(result, params, taskId);
		return result;
	}

	/**
	 * 
	 * <p>函数名称： runTrans       </p>
	 * <p>功能说明：运行转换任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param transFileName 转换任务路径
	 * @param params 所需要的变量参数
	 * @return
	 *
	 * @date   创建时间：2017年9月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public TransResult runTrans(String transFileName, Map<String, Object> params){
		return KettleHelper.runTrans(transFileName, params);
	}
	
	/**
	 * 
	 * <p>函数名称： runTask       </p>
	 * <p>功能说明：任务运行入口
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode 配置任务编码
	 * @param params 参数信息 会自动注入
	 *
	 * @date   创建时间：2017年9月12日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public TransResult runTask(String configCode,Map<String,Object> params){
		return runTask(configCode, params, null);
	}
	
	/**
	 * 
	 * <p>函数名称：   runTask     </p>
	 * <p>功能说明：任务运行入口
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode 配置任务编码
	 * @param params 参数信息 会自动注入
	 * @param afterService 回调事件
	 * @return
	 *
	 * @date   创建时间：2017年9月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public TransResult runTask(String configCode,Map<String,Object> params,AfterProcessService afterService){
		if(params == null){
			params = new HashMap<String, Object>();
		}
		//1.先票据configCode 获取配置 ，没有进行提示。
		//2.根据配置信息，获取 输入和输出的类型分别是什么。
		//3.组合转换过程。
		//4.运行转换
		//5.运行后 调用回调接口
		AssetEtlConfig config = getEtlConfig(configCode);
		params.put("__CONFIG_ID__", config.getId());
		params.put("__CONFIG_CODE__", config.getConfigCode());
		params.put("__CONFIG_NAME__", config.getConfigName());
		//判断是否有设置常量 从变量表中获取
		settingVariable(config, params);			
		TransResult result = new TransResult();
		//先判断ktr 字段是否有值 ，有这直接运行
		if(StringUtilsExt.isBlank(config.getKtr()) || StringUtilsExt.startsWith(config.getKtr(), "0")){
			logger.debug("动态拼接方式运行......");

			//动态语句
			Handler inHandler = HandlerType.parse(config.getInType()).getHandler();
			Handler outHandler = HandlerType.parse(config.getOutType()).getHandler();
			inHandler.setEtlConfig(config);
			outHandler.setEtlConfig(config);
			StepMeta inStepMeta =  KettleHelper.createStepMeta(inHandler.getStepMeta(config.getId()),inHandler.getHandler().getTypeCode());
			StepMeta outStepMeta =  KettleHelper.createStepMeta(outHandler.getStepMeta(config.getId()),outHandler.getHandler().getTypeCode());
			List<TransHopMeta> flowHopMeta = new ArrayList<>();
			//获取组件
			logger.debug("动态拼接组件...开始...");
					
			if(ruleConfigService.existsRuleConfigById(config.getId())){
				logger.debug("动态拼接组件...包含字段转换...");
				//自定义的
				//from 配置信息 to 数据转换接口
				//from 数据转换接口 to 类型转换
				//from 类型转换 to 变量设置				
				List<TransHopMeta> customHopMeta = StepMetaHelper.createCustomTransHop(params);
				//接上 头和尾巴
				TransHopMeta firstHopMeta = new TransHopMeta(inStepMeta, customHopMeta.get(0).getFromStep());
				flowHopMeta.add(firstHopMeta);
				TransHopMeta lastHopMeta = new TransHopMeta(customHopMeta.get(customHopMeta.size() - 1).getToStep(), outStepMeta);
				flowHopMeta.add(lastHopMeta);
				flowHopMeta.addAll(customHopMeta);
			} else {		
				//无需转换
				//流程为 输入---> 变量组件---> 常量 ---> 输出
				TransHopMeta varToConstStepMeta = StepMetaHelper.getVarAndConstHopMeta(params);
				TransHopMeta inToVarHopMeta = new TransHopMeta(inStepMeta, varToConstStepMeta.getFromStep());
				flowHopMeta.add(inToVarHopMeta);
				flowHopMeta.add(varToConstStepMeta);				
				TransHopMeta varToOutHopMeta = new TransHopMeta(varToConstStepMeta.getToStep(), outStepMeta);
				flowHopMeta.add(varToOutHopMeta);
			}
			logger.debug("动态拼接组件...结束...");
			result = KettleHelper.runTrans(config.getConfigName(),flowHopMeta, params);			
		} else {
			logger.debug("ktr方式运行......");
			String fileName = PathUtil.getFileRecv();
			//ktr 规则 启用:1,PD|HNWE-PDA-OUT.ktr 关闭：0,PD|HNWE-PDA-OUT.ktr
			String transFilePath = config.getKtr().startsWith("1,") ? config.getKtr().replace("1,", "") : config.getKtr();
			result = KettleHelper.runTrans(fileName + "KETTLE" + File.separator + StringUtilsExt.replace(transFilePath, "|", File.separator) , params);
			
		}	
		//调用回调函数 或者指定的接口
		if(!StringUtilsExt.isBlank(config.getBeanName()) && afterService == null){
			logger.debug("回调接口运行......");
			AfterProcessService configAfterService = AppContext.getAppContext().getBeanContext().getBean(config.getBeanName(),AfterProcessService.class);
			configAfterService.doProcess(configCode, params, result);
		} else {
			if(afterService != null){
				logger.debug("回调函数运行......");
				afterService.doProcess(configCode, params, result);
			}
		}
		saveFile(result, params, null);
		return result;
	}
	
	private AssetEtlConfig getEtlConfig(String configCode){
		AssertExt.notNull(configCode, "配置信息为空!!!");
		AssetEtlConfig config = new AssetEtlConfig();
		config.setConfigCode(configCode);
		config.setStatus("1");
		config = configService.getAssetEtlConfig(config);
		if(config == null){
			return null;
		}
		AssertExt.notNull(config, StringUtilsExt.formatString("配置[{0}]信息未找到!!!", configCode));
		AssertExt.notNull(config.getId(), StringUtilsExt.formatString("配置[{0}]信息未找到!!!", configCode));
        //校验输入和输出
		AssertExt.notNull(config.getInType(), StringUtilsExt.formatString("配置[{0}]信息输入源类型未找到!!!", configCode));
		AssertExt.notNull(config.getOutType(), StringUtilsExt.formatString("配置[{0}]信息输入源类型未找到!!!", configCode));
	    return config;
	}
	
	@SuppressWarnings("unchecked")
	private void settingVariable(AssetEtlConfig config, Map<String,Object> params){
		logger.debug("动态拼接方式运行...获取变量...");
		AssetEtlConfigAttr queryObj = new AssetEtlConfigAttr();
		queryObj.setStatus("1");
		queryObj.setConfigId(config.getId());
		List<AssetEtlConfigAttr> configAttr = conifgAttrService.getAssetEtlConfigAttrList(queryObj);
		if(configAttr != null && configAttr.size() >0){
			Map<String,Object> constParams = new HashMap<String,Object>();
			//判断是否外部有额外设置常量值，有则先获取，再添加
			if(params.containsKey(EtlConst.EXTRA_CONST_FIELD)){
				constParams.putAll(MapUtilsExt.getMap(params, EtlConst.EXTRA_CONST_FIELD));
			}
			//开始赋值
			for(AssetEtlConfigAttr attr : configAttr){
				if(EtlConst.ETL_CONFIG_ATTR_BIZ_TYPE_CONST.equalsIgnoreCase(attr.getBizType())){
					//常量输出赋值
					constParams.put(attr.getAttrName(), attr.getAttrValue());
				} else if(EtlConst.ETL_CONFIG_ATTR_BIZ_TYPE_VAR.equalsIgnoreCase(attr.getBizType())){
					//额外变量 过滤雷同额外常量的字段类，避免冲突
					if(!EtlConst.EXTRA_CONST_FIELD.equalsIgnoreCase(attr.getAttrName())){
						//已经存在的变量  以外部的为主 2017-11-21
						if(!params.containsKey(attr.getAttrName())){
						   params.put(attr.getAttrName(), StringUtilsExt.toString(attr.getAttrValue(),""));
						}
					}
				}
			}
			//加入变量中
			params.put(EtlConst.EXTRA_CONST_FIELD,constParams);
		}		
	}
	
	/**
	 * <p>函数说明：获取头部信息
	 *</p>
	 * <p>功能说明：获取头部信息
	 * </p>
	 * <p>参数说明：
	 * @param detailHeaders
	 * @return	List<String>
	 * </p>
	 *
	 * @date	创建时间：2017年12月1日
	 * @author	作者：chenzhibin
	 */
	private List<String> getHeaders(List<DetailHeaders> detailHeaders) {
		List<String> headers = new ArrayList<String>();
		for(DetailHeaders detailHeader : detailHeaders){
			List<DetailHeaders> subHeaders = detailHeader.getSubHeaders();
			if(subHeaders == null || subHeaders.size() == 0){
				detailHeader.setName(ColumnUtilsExt.camelUnderline(detailHeader.getName()));
				headers.add(detailHeader.getName());
			} else{
				headers.addAll(getHeaders(subHeaders));
			}
		}
		return headers;
	}

	/**
	 * 
	 * <p>函数名称：  saveFile      </p>
	 * <p>功能说明： 保存需要下载的文件信息 返回id
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param result
	 * @param params
	 * @param taskId 任务ID，若有则更新任务状态为SUCCESS，若无则新建任务
	 *
	 * @date   创建时间：2017年9月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private void saveFile(TransResult result, Map<String,Object> params, Long taskId){
		if(StringUtilsExt.isBlank(result.getResultFile())){
			return;
		}
		AssetEtlExportTask expTaskObj = null;
		if(taskId == null){
			expTaskObj = getExportTask(params);
		} else{
			expTaskObj = exportTaskService.getAssetEtlExportTask(taskId);
		}
		
		File file = new File(StringUtilsExt.environmentSubstituteByMapObj(result.getResultFile(), params));
		String realPath = StringUtilsExt.replaceOnce(file.getPath(), EtlConst.ETL_EXPORT_TASK_PATH, "");
		
		//替换 导出路径
		expTaskObj.setFilePath(realPath);
		expTaskObj.setFileType(FileUtilsExt.getFileType(file.getPath()));
		expTaskObj.setFileSize(file.length());
		expTaskObj.setStatus(EtlConst.ETL_EXPORT_TASK_STATUS_SUCCESS);
		expTaskObj.setRemark("导出成功");
		
		// 更新异常状态
		if(result.isException() || StringUtilsExt.isBlank(result.getResultFile())){
			String remark = result.getMessage();
			expTaskObj.setRemark(remark.length() > 200 ? (remark.substring(0, 180) + "......") : remark);
			expTaskObj.setStatus(EtlConst.ETL_EXPORT_TASK_STATUS_ERROR);
			exportTaskService.updateAssetEtlExportTask(expTaskObj);
			return;
		}
		
		if(taskId == null){
			exportTaskService.addAssetEtlExportTask(expTaskObj);
		} else{
			exportTaskService.updateAssetEtlExportTask(expTaskObj);
		}
		
		result.setResultFile("");
		result.setData(expTaskObj.getId());	    
	}

	/**
	 * 功能说明：创建导出任务，初始化基础信息
	 * @param params
	 * @param taskId 
	 * @return
	 */
	private AssetEtlExportTask getExportTask(Map<String,Object> params){
		AssetEtlExportTask expTaskObj = new AssetEtlExportTask();
		expTaskObj.setConfigCode(MapUtilsExt.getString(params, "__CONFIG_CODE__"));
		expTaskObj.setConfigName(MapUtilsExt.getString(params, "__CONFIG_NAME__"));
		expTaskObj.setDownloadCount(0);
		expTaskObj.setStatus(EtlConst.ETL_EXPORT_TASK_STATUS_WAIT);
		expTaskObj.setCreateTime(DateUtilsExt.getNowDateTime());
		expTaskObj.setCreator(MapUtilsExt.getString(params, "__userCode__"));
		expTaskObj.setId(ComponetIdGen.newWKID());
		expTaskObj.setFileName(StringUtilsExt.environmentSubstituteByMapObj(MapUtilsExt.getString(params, EtlConst.ETL_OUT_FILE_NAME), params));
		expTaskObj.setRemark("等待导出");
		return expTaskObj;
	}
	
	/**
	 * <p>功能说明：启用线程导出数据</p>
	 * @param excelFilePath
	 * @param exportData
	 * @param params
	 * @param configCode
	 * @param taskId 
	 * @return
	 * 
	 * @date   创建时间：2017年11月30日
	 * @author 作者：chenzhibin
	 */
	public TransResult threadExportExcel(String excelFilePath, ReportDetail exportData, 
			Map<String, Object> params, String configCode, Long taskId) {
		if(params == null){
			params = new HashMap<String, Object>();
		}
		
		//1.先获取configCode 获取配置 ，没有进行提示。
		AssetEtlConfig config = getEtlConfig(configCode);
		// 返回结果集
		TransResult result = new TransResult();
		if(config == null){
			throw new RuntimeException("导出配置不存在!");
		}
		
		params.put("__CONFIG_ID__", config.getId());
		params.put("__CONFIG_CODE__", config.getConfigCode());
		params.put("__CONFIG_NAME__", config.getConfigName());
		params.put("excelFilePath", excelFilePath);
		params.put(EtlConst.ETL_OUT_FULL_PATH, excelFilePath + "." + exportData.getExtension());		
		//判断是否有设置常量 从变量表中获取
		settingVariable(config, params);
		// 配置网格列信息注入
		List<String> detailHeaders = getHeaders(exportData.getDetailHeaders());
		params.put(EtlConst.ETL_EXPORT_COLUMNS_SQL, StringUtilsExt.join(detailHeaders, ","));
		
		// 创建初始任务
		AssetEtlExportTask exportTask = getExportTask(params);
		exportTask.setId(taskId);
		exportTask.setFileType(exportData.getExtension());
		exportTaskService.addAssetEtlExportTask(exportTask);
		
		ThreadPoolTaskExecutor taskExecutor = AppContext.getAppContext().getBeanContext()
			.getBean("lifeThreadPoolTaskExecutor", ThreadPoolTaskExecutor.class);
		// 启用线程执行导出
		ThreadHandler threadHandler = new ThreadHandler(exportData, params, taskId);
		logger.debug("启用线程导出任务[{}]...当前活动线程数{}...", taskId, taskExecutor.getActiveCount());
		taskExecutor.execute(threadHandler);
		
		result.setException(false);
		result.setMessage("正在导出数据");
		result.setData(exportTask.getId());
		result.setResultFile(exportData.getFileName());
		return result;
	}

	/**
	 * <p>功能说明：运行线程导出任务
	 * </p>
	 * <p>参数说明：
	 * @param exportData
	 * @param configCode
	 * @param params
	 * </p>
	 *
	 * @date	创建时间：2017年12月11日
	 * @author	作者：chenzhibin
	 * @param taskId 
	 */
	public void runThreadTask(ReportDetail exportData, Map<String, Object> params, Long taskId) {
		// 根据配置信息，获取 输入和输出的类型分别是什么。
		TransResult result = KettleHelper.runTrans(StepMetaHelper.getExportExcelWithSqlTransMeta(exportData), params);
		
		// 更新任务状态
		saveFile(result, params, taskId);
	}
}
