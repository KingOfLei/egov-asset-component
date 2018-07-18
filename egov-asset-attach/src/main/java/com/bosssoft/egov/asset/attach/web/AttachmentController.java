package com.bosssoft.egov.asset.attach.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bosssoft.egov.asset.attach.entity.AssetAttachFile;
import com.bosssoft.egov.asset.attach.entity.AssetAttachItem;
import com.bosssoft.egov.asset.attach.entity.AssetAttachRemark;
import com.bosssoft.egov.asset.attach.service.AssetAttachFileService;
import com.bosssoft.egov.asset.attach.service.AssetAttachRemarkService;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.IOUtilsExt;
import com.bosssoft.egov.asset.common.util.JsonUtilsExt;
import com.bosssoft.egov.asset.common.util.MIMETypeUtils;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.PathUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.common.util.collection.CollectionUtilsExt;
import com.bosssoft.egov.asset.common.util.file.ExcelToHtmlUtilExt;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.exception.FrameworkException;
import com.bosssoft.platform.runtime.spi.User;
import com.bosssoft.platform.runtime.web.binding.annotation.AjaxResponseBody;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;
import com.bosssoft.platform.runtime.web.response.AjaxResult;

/** 
*
* @ClassName   类名：AttachmentController 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月2日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月2日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@RequestMapping("attachment")
@Controller
public class AttachmentController {
	 
	private static Logger logger = LoggerFactory.getLogger(AttachmentController.class);

	 @Autowired
	 private AssetAttachFileService attachFileService;
	 
	 @Autowired
	 private AssetAttachRemarkService attachRemarkService;
		 
	 /**
	  * 
	  * <p>函数名称：showIndex        </p>
	  * <p>功能说明：上传附件页面
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param model
	  * @return
	  *
	  * @date   创建时间：2017年1月8日
	  * @author 作者：xds
	  */
	 @RequestMapping(value="showUpload.do",name="附件上传")
	 public String showIndex(Model model){		 
		 Map<String,Object> params = WebApplicationContext.getContext().getWebRequestContext().getRequestParamsMap();
		 Map<String,Object> data = new HashMap<String,Object>();		 
		 data.put("params", MapUtilsExt.copyNewMap(params));
		 model.addAttribute("_UIPageModel", data);	
		 model.addAllAttributes(data);
		 return "egov/attach/attach_index.ui";
	 }
	 /**
	  * 
	  * <p>函数名称：        </p>
	  * <p>功能说明：
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param file 文件
	  * @param bizId 附件所属id
	  * @param bizType 业务类型
	  * @param response 
	  * @param session 
	  * @throws IOException
	  *
	  * @date   创建时间：2016年12月2日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 @RequestMapping(value = "/upload.do")
	 @ResponseBody
	 public void upload(@RequestParam MultipartFile file, 
	    		String groupId, String bizType, HttpServletResponse response) throws IOException {
		 if(file.isEmpty()){
	    		throw new BusinessException("A600","上传文件大小不能为空!");
	     }
	     try{
			 //当前登录用户
			 User user = WebApplicationContext.getContext().getCurrentUser();
			 AssetAttachFile fileInfo = new AssetAttachFile();
			 fileInfo.setAttachId(IdGen.newWKID());
			 fileInfo.setName(file.getOriginalFilename());
			 fileInfo.setFileSize(file.getSize());
			 fileInfo.setContentType(FileUtilsExt.getFileType(file.getOriginalFilename()));
			 fileInfo.setBizType(bizType);
			 fileInfo.setBizId(groupId);
			 fileInfo.setAppId(WebApplicationContext.getContext().getAppID());
			 fileInfo.setCreateDate(new Date());
			 fileInfo.setStatus(1);
			 fileInfo.setCreatorIp(WebApplicationContext.getContext().getWebRequestContext().getRemoteAddress());
			 if(user != null) {
				 fileInfo.setCreator(StringUtilsExt.join(new String[]{user.getUserCode(),user.getUserName()}," "));
			 }
			 attachFileService.saveAttachFile(fileInfo, file.getInputStream());
			 response.setContentType("text/html");
			 response.getOutputStream().write(JsonUtilsExt.toJson(fileInfo).getBytes("utf-8"));
//			 return fileInfo;
		 }catch (Exception e){
		 	 e.printStackTrace();
			 response.getOutputStream().write("上传失败！".getBytes());
		 }
	     
//	     return null;
	 }
	 
	 @RequestMapping(value = "/delete.do")  
	 @ResponseBody
	 public Boolean delete(Long fileId){
		 if(fileId != null){
			 User user = WebApplicationContext.getContext().getCurrentUser();
			 Map<String,Object> params = new HashMap<String,Object>();
			 params.put("ip", WebApplicationContext.getContext().getWebRequestContext().getRemoteAddress());
			 user.setProperties(params);
			 attachFileService.delAttachFile(fileId,user);
		 }		 
		 return true;
	 } 
	 
	 @RequestMapping(value = "/getFiles.do")  
	 @ResponseBody
	 public List<AssetAttachFile> getFiles(String groupId) {
	    	List<AssetAttachFile> list = null;
	    	if(StringUtilsExt.isNotEmpty(groupId)){
	    		list = attachFileService.queryAttachFileInfosByBizId(groupId);
	    	}else{
	    		list = new ArrayList<AssetAttachFile>();
	    	}
	        return list;
	 }
	 
	 /**
	  * 
	  * <p>函数名称： getAttachItems       </p>
	  * <p>功能说明： 根据业务类型获取附件项
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param bizTpye 业务类型 可多个 返回时会删除重复项（itemCode一致） 
	  * @return
	  *
	  * @date   创建时间：2016年12月19日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 @RequestMapping(value = "/getItems.do")
	 @ResponseBody
	 public List<AssetAttachItem> getAttachItems(String[] bizType,String[] itemType,String grade,String[] excludeItemCode,String[] includeItemType){
		 List<AssetAttachItem> list = new ArrayList<AssetAttachItem>();
		 if(bizType != null && bizType.length > 0){
			 //获取附件项
			 list = attachFileService.getAssetAttachItems(WebApplicationContext.getContext().getAppID(), bizType,itemType,grade,excludeItemCode,includeItemType);
			// OrderByUtil.sort(list, "itemCode");
			 //删除重复项 保留一条
			 CollectionUtilsExt.removeRepeat(list, "itemName");
			 OrderByUtil.sort(list, "sxh");
		 }
		 return list;
	 }
	 
	/**
	 * 文件下载
	 * 
	 * @param id
	 *            文件id
	 * @param response
	 *            相应对象
	 */
	@RequestMapping(value = "/download.do")
	@ResponseBody
	public void download(Long id, HttpServletResponse response) {
		AssetAttachFile fileInfo = attachFileService.queryOneAttachFile(id);
		if(fileInfo == null){
			throw new BusinessException("F404","附件不存在!");
		}
		String contentType = fileInfo.getContentType();
		if (StringUtilsExt.isNotBlank(contentType)) {
			contentType = MIMETypeUtils.getContentType(contentType);
		}
		if (StringUtilsExt.isBlank(contentType)) {
			contentType = "application/octet-stream";
		}
		response.setContentType(contentType);
		response.setCharacterEncoding("GBK");
		OutputStream os = null;
		InputStream is = attachFileService.downloadFile(id);		
		try {
			response.setHeader("Content-disposition","attachment; filename=" + new String(fileInfo.getName().getBytes("GBK"),"ISO-8859-1"));
			response.setHeader("Content-Length",Long.toString(fileInfo.getFileSize()));
			
			os = response.getOutputStream();
			IOUtilsExt.copy(is, os);			
		} catch (Exception e) {
			try {
				os.write("文件不存在！".getBytes());
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Content-disposition","attachment; filename=" + new String((fileInfo.getName()+"_文件不存在").getBytes("GBK"),"ISO-8859-1"));
				response.setHeader("Content-Length",Integer.toString("文件不存在！".getBytes().length));
			} catch (IOException e1) {				
				e1.printStackTrace();
			}
			//throw new BusinessException("F404","文件下载失败");
		} finally {
			try {
				response.flushBuffer();
				is.close();
				os.close();
			} catch (IOException e) {}
			
		}
	}
	
    /**
     * 文件下载
     * @param bizId 业务id
     * @param response 相应对象
     * @throws BusinessException
     */
    @RequestMapping(value = "/downloadAll.do")
    @ResponseBody
    public void downloadAll(String bizId,HttpServletResponse response) throws BusinessException{
    	List<AssetAttachFile> fileInfos = attachFileService.queryAttachFileInfosByBizId(bizId);
    	if(fileInfos.size() == 0)
    		throw new BusinessException("F404","文件不存在!");
    	//打包此组下面的附件信息 返回一个zip文件包
    	response.setContentType(MIMETypeUtils.getContentType("zip"));
        response.setHeader("Content-disposition", "attachment; filename=all.zip");  
        ZipArchiveOutputStream os = null;
		try {
			os = new ZipArchiveOutputStream(response.getOutputStream());
	        for(AssetAttachFile file :fileInfos){
		        InputStream is = attachFileService.downloadFile(file.getAttachId());
		      //当文件不存在是 返回的是null 是否需要保护 直接说明文件为null
		        //如 xxx.jpg 文件不存在.txt 文件内容为空
		        if(is == null) {
			        os.putArchiveEntry(new ZipArchiveEntry(file.getName() + "_文件不存在"));  
			        os.closeArchiveEntry();
		        } else {
			        os.putArchiveEntry(new ZipArchiveEntry(file.getName()));  
			        IOUtilsExt.copy(is,os);
					os.closeArchiveEntry(); 
					is.close();
		        }
	        }
	        os.flush();
			os.close();
		} catch (IOException e) {
			throw new FrameworkException("下载失败",e);
		}
    }
    
	 @RequestMapping(value = "/checkItems.do")
	 @ResponseBody
	 public List<AssetAttachItem> checkItems(String[] bizType,String[] itemType,String grade,String[] excludeItemCode,String[] includeItemType){
		 List<AssetAttachItem> list = new ArrayList<AssetAttachItem>();
		 if(bizType != null && bizType.length > 0){
			 //获取附件项
			 list = attachFileService.getAssetAttachItems(WebApplicationContext.getContext().getAppID(), bizType,itemType,grade,excludeItemCode,includeItemType);
			 //删除重复项 保留一条
			 CollectionUtilsExt.removeRepeat(list, "itemName");
			 OrderByUtil.sort(list, "sxh");
		 }
		 return list;
	 }
	 
	 @RequestMapping(value = "doInsertRemark.do",name="上传附件备注信息")
	 @AjaxResponseBody
	 public AjaxResult insertAttachmentRemark(List<AssetAttachRemark> list){
		 if(list != null && !list.isEmpty()){
		   attachRemarkService.addAssetAttachRemark(list);
		 }		 
		 return AjaxResult.success(list);
	 }
	 
	 @RequestMapping(value = "getInsertRemark.do",name="上传附件备注信息")
	 @ResponseBody
	 public List<AssetAttachRemark> getAttachmentRemark(String groupIds){
		  if(StringUtilsExt.isNotBlank(groupIds)){
			  groupIds = StringUtilsExt.substringBefore(groupIds, "_");
		  }
		  List<AssetAttachRemark> list = attachRemarkService.getAttachRemarkList(groupIds);
		  return (list == null || list.isEmpty()) ? new ArrayList<AssetAttachRemark>() : list;
	 }
	 
	 /**
	  * 
	  * <p>函数名称： checkAttachmentRequired       </p>
	  * <p>功能说明：核验附件是否有必填项未填写 ：有文件或者填写了文字说明
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param bizType
	  * @param itemType
	  * @param grade
	  * @param bizId
	  * @return
	  *
	  * @date   创建时间：2017年8月13日
	  * @author 作者：xds
	  */
	 @RequestMapping(value = "checkAttachmentRequired.do",name="核验附件是否有必填项未填写")
	 @ResponseBody
	 public List<AssetAttachItem> checkAttachmentRequired(String[] bizType,String[] itemType,String grade,String[] excludeItemCode,String[] includeItemType,String bizId){
		 List<AssetAttachItem> list = new ArrayList<AssetAttachItem>();
		 if(bizType != null && bizType.length > 0){
			//获取附件项
//			 list = attachFileService.getAssetAttachItems(WebApplicationContext.getContext().getAppID(), bizType,itemType,grade);
//			 //删除重复项 保留一条
//			 CollectionUtilsExt.removeRepeat(list, "itemName");
//			 OrderByUtil.sort(list, "sxh");
//			 //获取到项。。
			 list = attachFileService.checkAttachmentRequired(WebApplicationContext.getContext().getAppID(), bizType, itemType, grade, excludeItemCode,includeItemType,bizId);
		 }
		 return list;
	 }
	 
	 @RequestMapping(value = "checkIncludeAtLeastOneAttachment.do",name="核验附件是否有文件和必填项未填写")
	 @ResponseBody
	 public Map<String,Object> checkIncludeAtLeastOneAttachment(String[] bizType,String[] itemType,String grade,String[] excludeItemCode,String[] includeItemType,String bizId,String atleastCnt){
		 Map<String,Object> result = new HashMap<String,Object>();
		 result.put("flag", false);
		 result.put("msg", "检验不通过");
		 if(bizType != null && bizType.length > 0){
			 boolean flag = attachFileService.checkIncludeAtLeastOneAttachment(WebApplicationContext.getContext().getAppID(), bizType, itemType, grade, excludeItemCode, includeItemType, bizId,NumberUtilsExt.toInt(atleastCnt,1));
			 result.put("flag", flag);	
			 result.put("msg", flag ? "核验通过" : "核验不通过");
		 } 
		 return result;
	 }
	 
	 /**
	  * 
	  * <p>函数名称：getAttachmentPreviewPath        </p>
	  * <p>功能说明：在线预览附件
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param id
	  * @return Map flag = 1,可在线预览，返回path 有值（可访问的临时文件url）
	  *             flag = 0,不能在线预览，前端直接下载
	  *             flag = -1,文件不存在
	  *             flag = 9,转换异常 也按下载处理
	  *
	  * @date   创建时间：2017年8月23日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 @RequestMapping(value = "preview.do")
	 @ResponseBody
	 public Map<String,String> getAttachmentPreviewPath(String id){
		 Map<String,String> result = new HashMap<String, String>();
    	 result.put("flag", "0");
    	 result.put("path", "");
    	 result.put("id", id);    	 
		 AssetAttachFile fileInfo = attachFileService.queryOneAttachFile(NumberUtilsExt.toLong(id));
         if(fileInfo == null){
        	 //文件不存在
        	 result.put("flag", "-1");
         } else {
        	 //加载附件 到一个临时文件中 （定时动态删除）//        	         	 
        	 //在支持类型中的，直接返回临时文件 否则直接返回id 前端下载
        	//获取类型
        	 String contentType = fileInfo.getContentType();        	 
        	 String tempFile = AtttachHelper.TEMP_FOLDER + DateUtilsExt.getNowDate() + File.separator + fileInfo.getAttachId() + "." + contentType;
        	 String filePath = PathUtil.getWebRootPath()  + File.separator  + tempFile;
        	 // 生成临时文件 返回地址
			 InputStream input = attachFileService.downloadFile(fileInfo.getAttachId());
			 if(input == null){
				 result.put("flag", "-1");
				 return result;
			 }
        	 try{
				switch (contentType.toUpperCase()) {
				case "BMP":
				case "JPG":
				case "JPEG":
				case "PNG":
				case "GIF":
				case "PDF":
				case "HTML":
					//判断是否已经存在 存在直接返回了
					if(!FileUtilsExt.exists(filePath)){
					   FileUtils.copyInputStreamToFile(input,new File(filePath));
					}
			    	result.put("flag", "1");
					result.put("path", tempFile);
					break; 
			    //暂时关闭word在线预览功能
//				case "DOC":
//				case "DOCX":
//					//保存目录 可能存在图片 故需要再创建一级目录
//					tempFile = AtttachHelper.TEMP_FOLDER + DateUtilsExt.getNowDate() + File.separator  + fileInfo.getAttachId() + File.separator + fileInfo.getAttachId() + ".html";
//		        	filePath = PathUtil.getWebRootPath()  + File.separator  + tempFile;
//		        	//判断是否已经存在 存在直接返回了
//					if(!FileUtilsExt.exists(filePath)){
//					   WordToHtmlUtilExt.toHtml(input, filePath);
//					}
//			    	result.put("flag", "1");
//					result.put("path", tempFile);
//					break;
				case "XLS":
				case "XLSX":
                    //转换成HTML 然后返回前端 暂时未实现
					//判断是否已经存在 存在直接返回了
					//替换后缀
					tempFile = StringUtilsExt.replace(tempFile, "." + contentType, ".html");
					filePath = StringUtilsExt.replace(filePath, "." + contentType, ".html");
					if(!FileUtilsExt.exists(filePath)){
					   ExcelToHtmlUtilExt.toHtml(input, filePath);
					}
			    	result.put("flag", "1");
					result.put("path", tempFile);
				default:
					break;
				}
    		 } catch(Exception ioException){
    			 logger.error("文件预览异常", ioException);
            	 result.put("flag", "9");
            	 result.put("path", "");            	 
    			 result.put("msg", StringUtilsExt.toString(ioException.getMessage(),"文件预览异常，请直接下载！"));
    			 //删除文件
    			 FileUtils.deleteQuietly(new File(filePath));
    		 }
         }
         //替换路径path
         result.put("path", StringUtilsExt.replace(MapUtilsExt.getString(result, "path"), "\\", "/"));
		 return result;
	 }
	 
}
