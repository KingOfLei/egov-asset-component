package com.bosssoft.egov.asset.common.util;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

/** 
*
* @ClassName   类名：MIMETypeUtils 
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
public class MIMETypeUtils {
	/**
	 * MIME类型映射
	 */
	private static MimetypesFileTypeMap mimetypesFileTypeMap = null;
	
	/**
	 * 初始化MIME类型
	 */
	public static void init(){
		if(mimetypesFileTypeMap != null)
			return;
		mimetypesFileTypeMap = new MimetypesFileTypeMap();
		mimetypesFileTypeMap.addMimeTypes("text/css css");
		mimetypesFileTypeMap.addMimeTypes("text/javascript js");
		mimetypesFileTypeMap.addMimeTypes("text/xml xml");
	}
	
	/**
	 * 添加MIME类型
	 * @param mimeTypes MIME类型串（如text/html html）
	 */
	public static void addMimeTypes(String mimeTypes){
		if(mimetypesFileTypeMap == null)
			init();
		mimetypesFileTypeMap.addMimeTypes(mimeTypes);
	}
	
	/**
	 * 取得文件名的内容类型
	 * @param fileName 文件名
	 * @return 内容类型
	 */
	public static String getContentType(String fileName){
		if(mimetypesFileTypeMap == null)
			init();
		return mimetypesFileTypeMap.getContentType(fileName);
	}
	
	/**
	 * 取得文件的内容类型
	 * @param file 文件
	 * @return 内容类型
	 */
	public static String getContentType(File file){
		if(mimetypesFileTypeMap == null)
			init();
		return mimetypesFileTypeMap.getContentType(file);
	}
}
