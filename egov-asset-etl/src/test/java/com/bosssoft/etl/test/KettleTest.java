package com.bosssoft.etl.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bosssoft.egov.asset.common.util.MapUtilsExt;

/** 
*
* @ClassName   类名：KettleTest 
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
public class KettleTest {
  public static void main(String[] args) throws IOException {
//	System.out.println(KettleHelper.getRandomUUID());
//	System.out.println(StringUtilsExt.formatString("666'{'{0}'}'","3")); 
////	System.out.println( StringUtilsExt.join(StringUtilsExt.split("AAA|BB","\\|"),"."));
//	  File file = new File("F:\\A\\B\\" + PathUtil.getDateDirectory());
//	  FileUtilsExt.mkdirs(file, true);
//	  File temp = File.createTempFile("tmp", ".html", file);
//	  temp.deleteOnExit();
//	  System.out.println("ddd");
	  Map<String,Object> params = new HashMap<>();
	  System.out.println(MapUtilsExt.getInteger(params, "isTodo", 0));
	  params.put("isTodo", "xds");
	  System.out.println(MapUtilsExt.getInteger(params, "isTodo", 0));
	  params.put("isTodo", "1");
	  System.out.println(MapUtilsExt.getInteger(params, "isTodo", 0));	  

}  
}
