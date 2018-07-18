package com.bosssoft.asset.etl.core.util;
/** 
*
* @ClassName   类名：DialectHelper 
* @Description 功能说明：方言帮助类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DialectHelper {

	public static String dbTypeFromJdbcUrl(String jdbcUrl){
		////jdbc:oracle:thin:@192.168.10.37:1521/orcl
		if(jdbcUrl.indexOf(":oracle:") >= 0){
			return "Oracle";
		} else if (jdbcUrl.indexOf(":mysql:") >=0 ){
			return "MySQL";
		} else if(jdbcUrl.indexOf(":sqlserver:") >= 0){
			return "MSSQLNative";
		} else if(jdbcUrl.indexOf(":sqlite:") >= 0){
			return "SQLite";
		}
		
		return "Oracle";
	}
}
