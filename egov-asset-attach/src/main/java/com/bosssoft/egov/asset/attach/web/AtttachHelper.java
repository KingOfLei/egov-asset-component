package com.bosssoft.egov.asset.attach.web;

import java.io.File;
import java.util.Set;

import com.bosssoft.egov.asset.attach.service.AttachmentService;
import com.bosssoft.platform.common.extension.ExtensionLoader;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;

/** 
*
* @ClassName   类名：AtttachHelper 
* @Description 功能说明：帮助类 
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
public class AtttachHelper {
  
	public static final String ATTACH_TABLE = "ASSET_ATTACH_FILE";
	
	public static final String UPLOAD_TYPE_LOCALHOST = "LocalHostAttachService";
	public static final String UPLOAD_TYPE_DATABASE = "DatabaseAttachService";
	public static final String UPLOAD_TYPE_FASTDFS = "FastDfsAttachService";
	
	//预览时临时存放的目录 会定时清空
	public static final String TEMP_FOLDER = "temp" + File.separator + "attachment" + File.separator;
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param type
	 * @return
	 *
	 * @date   创建时间：2016年12月2日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	
	public static AttachmentService getAttachmentService(String type){
		Set<String> services = ExtensionLoader.getExtensionLoader(AttachmentService.class).getSupportedExtensions();
		for(String service : services){
			AttachmentService attachService = ExtensionLoader.getExtensionLoader(AttachmentService.class).getExtension(service);
			SpringExtensionHelper.initAutowireFields(attachService);
			if(service.equalsIgnoreCase(type)){
				return attachService;
			}
		}
		throw new IllegalArgumentException("未知的附件处理服务类: " + type);
	}
}
