package com.bosssoft.asset.etl.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bosssoft.asset.etl.core.EtlConst;
import com.bosssoft.asset.etl.core.Launcher;
import com.bosssoft.asset.etl.core.util.EtlHelper;
import com.bosssoft.asset.etl.entity.AssetEtlConfig;
import com.bosssoft.asset.etl.entity.AssetEtlExportTask;
import com.bosssoft.asset.etl.entity.DetailHeaders;
import com.bosssoft.asset.etl.entity.ReportDetail;
import com.bosssoft.asset.etl.entity.TransResult;
import com.bosssoft.asset.etl.service.AssetEtlConfigService;
import com.bosssoft.asset.etl.service.AssetEtlExportTaskService;
import com.bosssoft.egov.asset.attach.entity.AssetAttachFile;
import com.bosssoft.egov.asset.attach.service.AssetAttachFileService;
import com.bosssoft.egov.asset.attach.service.AttachmentService;
import com.bosssoft.egov.asset.attach.web.AtttachHelper;
import com.bosssoft.egov.asset.basic.BaseController;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.idgenerator.UUIDUtils;
import com.bosssoft.egov.asset.common.util.IOUtilsExt;
import com.bosssoft.egov.asset.common.util.MIMETypeUtils;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;
import com.bosssoft.platform.runtime.web.binding.annotation.AjaxResponseBody;
import com.bosssoft.platform.runtime.web.response.AjaxResult;

/** 
*
* @ClassName   类名：EtlController 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping(value = "egov/asset/etl", name="数据转换接口")
public class EtlController extends BaseController{
	
	private static Logger logger = LoggerFactory.getLogger(EtlController.class);
	
	@Autowired
	private AssetEtlExportTaskService expTaskService;
	
	@Autowired
	private AssetEtlConfigService configService;
	
	@Autowired
	private AssetAttachFileService attachFileService;

	private AttachmentService getAttachmentService(String type){
		return RuntimeApplicationContext.getBean(type);
	}
	
	
	/**
	 * 
	 * <p>函数名称：runTask        </p>
	 * <p>功能说明：运行转换任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode 任务编码
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 * @throws IOException 
	 */
	@RequestMapping(value="runTask.do",name="运行转换任务")
	@ResponseBody
	public TransResult runTask(String configCode) throws IOException{		
		logger.debug("执行任务...{}...", configCode);	
		Map<String,Object> params = getParams();
		TransResult result = Launcher.getInstance().runTask(configCode, params);
		return result;
	}
	
	@RequestMapping(value = "download.do",name="下载文件")
	@AjaxResponseBody
	public AjaxResult download(String id, HttpServletResponse response){
		AssetEtlExportTask expTaskObj = expTaskService.getAssetEtlExportTask(NumberUtilsExt.toLong(id,0L));
		if(expTaskObj == null){
			//未找到
			return ResultUtils.ERROR(StringUtilsExt.formatString("文件[{0}]配置不存在!", id),id);
		}
		//下载加1
		expTaskObj.setDownloadCount(expTaskObj.getDownloadCount() == null ? 1 : expTaskObj.getDownloadCount() + 1);
		expTaskService.updateAssetEtlExportTask(expTaskObj);
		String fileName = StringUtilsExt.toString(expTaskObj.getFileName(),"文件导出");
		String ext = StringUtilsExt.toString(expTaskObj.getFileType());  
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
		try{
		   //在这里加入设置Cookie   -------------
		   Cookie fileDownload=new Cookie("fileDownload", "true");
		   fileDownload.setPath("/");
		   response.addCookie(fileDownload);	
		   if(StringUtilsExt.isBlank(expTaskObj.getFilePath())){
				return ResultUtils.ERROR(StringUtilsExt.formatString("文件[{0}]路径不存在!", id),id);
		   }
		   is = FileUtils.openInputStream(new File(EtlConst.ETL_EXPORT_TASK_PATH + expTaskObj.getFilePath()));
		   response.setHeader("Content-disposition","attachment; filename=" + new String((fileName + "." + ext).getBytes("GBK"),"ISO-8859-1"));
		   response.setHeader("Content-Length",Long.toString(expTaskObj.getFileSize()));			
		   os = response.getOutputStream();
		   IOUtilsExt.copy(is, os);			   
		} catch(IOException io){
			logger.debug("下载文件异常...{0}...",io.getMessage());	
			response.setContentType("text/html");
			response.setCharacterEncoding("GBK");
			ResultUtils.ERROR("下载异常:" + io.getMessage());
			//throw new BusinessException("ETL-1000","下载异常");				
		} finally {
			try {
				response.flushBuffer();
				IOUtilsExt.closeQuietly(is);
				IOUtilsExt.closeQuietly(os);
			} catch (IOException e) {
				
			}
		}
		return ResultUtils.SUCCESS("下载成功");
	}
	
	/**
	 * 
	 * <p>函数名称： showImportFile       </p>
	 * <p>功能说明： 导入界面url
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param model
	 * @param configCode 配置编码
	 * @return
	 *
	 * @date   创建时间：2017年10月21日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@RequestMapping(value="showImpFile.do",name="文件导入界面")
	public String showImportFile(Model model,String configCode){
		Map<String, Object> params = AppContext.getAppContext().getRequestParamsMap();
		if(StringUtilsExt.isBlank(configCode)){
			params.put("__flag__", false);
			params.put("__msg__","转换编码为空，请先进行配置!(ETL000)");
		} else {
			AssetEtlConfig config = new AssetEtlConfig();
			config.setConfigCode(configCode);
			config.setStatus("1");
			config = configService.getAssetEtlConfig(config);
			if(config != null ){
				params.put("__flag__", true);
				params.put("__id__",ComponetIdGen.newWKID());
                params.put("__config__", config);
			} else {
				params.put("__flag__", false);
				params.put("__msg__",StringUtilsExt.formatString("【{0}】编码不存在，请先检查配置编码正确!(ETL404)", configCode));
			}
		}
		model.addAttribute("_UIPageModel", params);
		return "egov/asset/etl/impFile";
	}
	
	/**
	 * 
	 * <p>函数名称： importExcel       </p>
	 * <p>功能说明： 导出excel,根据前端传入的网格参数
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@RequestMapping(value="expExcel.do",name="导出excel")
	@ResponseBody
	public TransResult exportExcel(String exportType,String fileName,String sheetName, String detailDatas,List<DetailHeaders> detailHeaders,HttpServletResponse response){
		TransResult result = new TransResult();
		ReportDetail exportData = new ReportDetail();
		exportData.setExtension(StringUtilsExt.equalsIgnoreCase("xls", exportType) ? "xls" : "xlsx");
		exportData.setSheetName(sheetName);
		exportData.setDetailHeaders(detailHeaders);
		//转换对象
		List<Map> dataList = JSON.parseArray(detailDatas, Map.class);
		exportData.setDetailDatas(dataList);		
		try{
		   Map<String,Object> params = getUserParams();
		   params.putAll(EtlHelper.getWebParams());
		 //  params.put(EtlConst.ETL_USER_INFO, getCurrUser());
		   params.put(EtlConst.ETL_OUT_FILE_NAME, fileName);
		   params.put("DRC_SQL", getCurrUser().getDrcSql());
		   String uuidFileName = UUIDUtils.getRandomUUID();
		   String excelFilePath = MapUtilsExt.getString(params, EtlConst.ETL_EXPORT_TASK_PATH_DIRECTORY) + uuidFileName;
		   result = Launcher.getInstance().exportExcel(excelFilePath, exportData, params, null);		  		   		   
		} catch(Exception io){			
			logger.debug("导出excel文件异常...{}...",io.getMessage());
			result.setException(false);
			result.setMessage("导出excel文件异常：{}",io.getMessage());
//			throw new BusinessException("ETL-1000",JsonUtilsExt.obj2Json(result));				
		}
				
		return result;
	}
	
	/**
	 * <p>函数说明：runTaskExpExcel</p>
	 * <p>功能说明：运行转换任务根据前台传入网格参数导出数据到excel</p>
	 * 
	 * @param configCode 配置编码
	 * @param exportType 导出文件类型，默认xlsx
	 * @param fileName 导出文件名称
	 * @param sheetName 工作簿名称
	 * @param detailHeaders 导出表头
	 * @param response
	 * @return
	 * 
	 * @date   创建时间：2017年11月30日
	 * @author 作者：chenzhibin
	 */
	@RequestMapping(value="threadExpExcel.do",name="运行转换任务")
	@ResponseBody
	public TransResult threadExpExcel(String configCode, String exportType, String fileName,
			String sheetName, List<DetailHeaders> detailHeaders, String condition, String order){
		TransResult result = new TransResult();
		if(StringUtilsExt.isBlank(configCode)){
			result.setException(true);
			result.setMessage("导出配置不存在！");
			return result;
		}
		
		logger.debug("执行任务...{}...", configCode);
		Map<String,Object> params = getParams();
		// 添加用户权限、查询条件、排序配置
		params.put(EtlConst.ETL_EXPORT_CONDITION_SQL, " ${WHERE_SQL} ${ORDER_BY_SQL}");
		
		// 添加查询条件
		if(condition == null){
			condition = "";
		}
		// 添加排序
		if(order == null){
			order = "";
		}
		params.put(EtlConst.ETL_EXPORT_WHERE_SQL, condition);
		params.put(EtlConst.ETL_EXPORT_ORDER_SQL, order);
		ReportDetail exportData = new ReportDetail();
		exportData.setExtension(StringUtilsExt.equalsIgnoreCase("xls", exportType) ? "xls" : "xlsx");
		exportData.setSheetName(sheetName);
		exportData.setFileName(fileName);
		exportData.setDetailHeaders(detailHeaders);
		Long taskId = ComponetIdGen.newWKID();
		try{
		   String uuidFileName = UUIDUtils.getRandomUUID();
		   String excelFilePath = MapUtilsExt.getString(params, EtlConst.ETL_EXPORT_TASK_PATH_DIRECTORY) + uuidFileName;
		   result = Launcher.getInstance().threadExportExcel(excelFilePath, exportData, params, configCode, taskId);		  		   		   
		} catch(Exception io){
			AssetEtlExportTask exportTask = new AssetEtlExportTask();
			exportTask.setId(taskId);
			String remark = StringUtilsExt.isEmpty(io.getMessage(), "导出异常!");
			exportTask.setRemark(remark.length() > 200 ? (remark.substring(0, 180) + "......") : remark);
			exportTask.setStatus(EtlConst.ETL_EXPORT_TASK_STATUS_ERROR);
			expTaskService.updateAssetEtlExportTask(exportTask);
			logger.debug("导出excel文件异常...{}...",io.getMessage());
			result.setException(true);
			result.setMessage("导出excel文件异常：" + io.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：  exportFile      </p>
	 * <p>功能说明： 文件导入，且根据传入的任务编码，进行任务导入功能 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode 任务编码
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 * @throws IOException 
	 */
	@RequestMapping(value="impFile.do",name="文件导入")
	@ResponseBody
	public TransResult importFile(List<String> fileIds,String configCode){
		TransResult result = new TransResult();
		logger.debug("任务[{}],导入文件开始...{}...", configCode,fileIds);	
		StringBuilder message = new StringBuilder();
		try {
			for(String fileId : fileIds){
				AssetAttachFile fileInfo = attachFileService.queryOneAttachFile(NumberUtilsExt.toLong(fileId, 0L));
				String uploadType = AtttachHelper.UPLOAD_TYPE_LOCALHOST;
				if(StringUtilsExt.isNotBlank(fileInfo.getUploadType())){
					uploadType = fileInfo.getUploadType();
				}
				String rootPath = getAttachmentService(uploadType).getPath();
				logger.debug("开始导入文件数据...{}...", fileInfo.getName());				
				Map<String,Object> params = getParams();
				params.put("__fileName__", fileInfo.getName());
				params.put("__filePath__", rootPath + File.separator + fileInfo.getFilePath());
				params.put("__fileSize__", fileInfo.getFileSize());
				logger.debug("执行任务...{}...", configCode);			
				TransResult _result = Launcher.getInstance().runTask(configCode, params);
				if(!_result.isException()){
//				     result.setException(true);
					 message.append(StringUtilsExt.formatString("文件【{0}】导入成功！</br>", fileInfo.getName())); 
					 
				 } else {
					 result.setException(true);
					 logger.error(StringUtilsExt.formatString("导入[{0}]文件，异常：{1}", configCode,_result.getDevMessage()));
					 message.append(StringUtilsExt.formatString("文件【{0}】导入发生异常，请检查导入文件数据是否正确！</br>", fileInfo.getName())); 
				 }
			}
		} catch(Exception io){
			io.printStackTrace();
			logger.error(StringUtilsExt.formatString("任务[{0}],导入文件结束...{1}...", configCode,fileIds), io);
			result.setException(false);
			result.setMessage("文件导入异常：{}",io.getMessage());
		} finally{
			result.setMessage(message.toString());
			logger.debug("任务[{}],导入文件结束...{}...", configCode,fileIds);			
		}		
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：getParams        </p>
	 * <p>功能说明：获取参数
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private Map<String,Object> getParams(){
		Map<String, Object> params = AppContext.getAppContext().getRequestParamsMap();
		//默认加入当前登录单位信息		
		params.putAll(getUserParams());
		params.putAll(EtlHelper.getWebParams());
		params.put("DRC_SQL", getCurrUser().getDrcSql());
		//params.put(EtlConst.ETL_USER_INFO, getCurrUser());
		return params;
	}
	
}
