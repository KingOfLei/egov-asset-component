package com.bosssoft.egov.asset.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/** 
*
* @ClassName   类名：XmlUtilsExt 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月12日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月12日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class XmlUtilsExt {
	
	/**
	 * 
	 * <p>函数名称：  createDocument      </p>
	 * <p>功能说明： 获取xml操作Doucment
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 *
	 * @date   创建时间：2016年11月12日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static Document createDocument(String fileName) {
		return createDocument(new File(fileName));
	}
	
	public static Document createDocument(InputStream in) 
	{
		SAXReader saxReader = new SAXReader();	    
		try {
			return saxReader.read(in);
		} catch (DocumentException documentException) {
			throw new RuntimeException("创建xml文件异常:" + documentException.getMessage());
		}  
	}
	
	public static Document createDocument(File fileName){
		SAXReader saxReader = new SAXReader();	
	    try {
			return saxReader.read(fileName);    
	    } catch (DocumentException documentException){
	    	throw new RuntimeException("创建xml文件异常:" + documentException.getMessage());
	    }
	}
	
	/**
	 * 
	 * <p>函数名称：   xml输出     </p>
	 * <p>功能说明：createPrettyPrint 原生态xml文件输出含缩进
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param doc  对象
	 * @param filePath 输出地址
	 * @param encode 编码  UTF-8 GB2312 等
	 *
	 * @date   创建时间：2013-3-20
	 * @author 作者：谢德寿
	 */
	public static void createPrettyPrint(Document doc,String filePath,String encode){
		try {
			/** 将document中的内容写入文件中 */
			File file = new File(filePath);
			if(!file.exists())
				file.getParentFile().mkdirs();//创建目录文件
			// 读取文件
			FileOutputStream fileWriter = new FileOutputStream(filePath);
			// 设置文件编码
			OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
			if (encode !=null && !encode.equals(""))
			 xmlFormat.setEncoding(encode);
			// 创建写文件方法
			XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
			// 写入文件
			xmlWriter.write(doc);
			// 关闭
			xmlWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
