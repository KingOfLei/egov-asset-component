package com.bosssoft.egov.asset.common.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.bosssoft.egov.asset.common.util.file.excel.ToHtml;

/** 
*
* @ClassName   类名：ExcelToHtmlUtil 
* @Description 功能说明：excel 转 htoml
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年8月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年8月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ExcelToHtmlUtilExt {
   public static void main(String[] args) throws IOException {
	   ToHtml toHtml = ToHtml.create("C:\\Users\\xiedeshou\\Desktop\\资产报废明细表1.xls", new PrintWriter(new FileWriter("C:\\Users\\xiedeshou\\Desktop\\xds1.html")));
       toHtml.setCompleteHTML(true);
       toHtml.printPage();
  }
   
   /**
    * 
    * <p>函数名称：        </p>
    * <p>功能说明：
    *
    * </p>
    *<p>参数说明：</p>
    * @param sourceFile 源文件
    * @param targetPath 目标文件路径
    *
    * @date   创建时间：2017年8月27日
    * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
    */
   public static void toHtml(String sourceFile,String targetPath){
		try {
			 createFile(new File(targetPath));
			 ToHtml toHtml = ToHtml.create(sourceFile, getPrintWriter(targetPath));
			 toHtml.setCompleteHTML(true);
			 toHtml.setOutpath(targetPath);
	         toHtml.printPage();
		} catch (IOException e) {
			throw new IllegalArgumentException("Excel转换异常", e);
		}
   }
   
   /**
    * 
    * <p>函数名称：toHtml        </p>
    * <p>功能说明：excel 转 html
    *
    * </p>
    *<p>参数说明：</p>
    * @param in 输入流
    * @param targetPath 输出路径
    *
    * @date   创建时间：2017年8月27日
    * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
    */
   public static void toHtml(InputStream in, String targetPath){
	   try {
		   //创建文件
		   createFile(new File(targetPath));
		   ToHtml toHtml = ToHtml.create(in, getPrintWriter(targetPath));
		   toHtml.setCompleteHTML(true);
		   toHtml.setOutpath(targetPath);		   
	       toHtml.printPage();
	   } catch (IOException e) {
		    e.printStackTrace();
			throw new IllegalArgumentException("Excel转换异常", e);
	   }
   }
   
   private static PrintWriter getPrintWriter(String fileName) throws UnsupportedEncodingException, FileNotFoundException{
	   return new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)), "utf-8"));
   }
   
   private static void createFile(File file) throws IOException{
	   if (file.exists()) {
           if (file.isDirectory()) {
               throw new IOException("File '" + file + "' exists but is a directory");
           }
           if (file.canWrite() == false) {
               throw new IOException("File '" + file + "' cannot be written to");
           }
       } else {
           File parent = file.getParentFile();
           if (parent != null) {
               if (!parent.mkdirs() && !parent.isDirectory()) {
                   throw new IOException("Directory '" + parent + "' could not be created");
               }
           }
       }
   }
}
