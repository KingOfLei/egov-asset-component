package com.bosssoft.egov.asset.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/** 
*
* @ClassName   类名：CommonFileUtilsExt 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年7月5日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年7月5日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CommonFileUtilsExt extends FileUtils {

   public static void copyInputStreamToFile(InputStream source, String destination) throws IOException{
	   copyInputStreamToFile(source, new File(destination));
   }
}
