package com.bosssoft.egov.asset.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
*
* @ClassName   类名：ColumnUtilsExt 
* @Description 功能说明：字段处理工具类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年6月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年6月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ColumnUtilsExt {
	
	/**
	 * 
	 * <p>函数名称：  underlineToCamel      </p>
	 * <p>功能说明： 下划线转驼峰 UNDERLINE_TO_CAMEL---> underlineToCamel
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param column
	 * @return
	 *
	 * @date   创建时间：2017年6月27日
	 * @author 作者：xds
	 */
	public static String underlineToCamel(String column){
		 StringBuilder sb = new StringBuilder();

	        boolean nextUpperCase = false;
	        for (int i = 0; i < column.length(); i++) {
	            char c = column.charAt(i);
	            if(c == '_'){
	            	if (sb.length() > 0) {
	                    nextUpperCase = true;
	                }
	            } else {
	            	if (nextUpperCase) {
	                    sb.append(Character.toUpperCase(c));
	                    nextUpperCase = false;
	                } else {
	                    sb.append(Character.toLowerCase(c));
	                }
	            }
	        }
	        return sb.toString();
	}

     /**
      * 	
      * <p>函数名称：  camelUnderline      </p>
      * <p>功能说明： 驼峰转下划线
      *
      * </p>
      *<p>参数说明：</p>
      * @param line
      * @return
      *
      * @date   创建时间：2017年6月27日
      * @author 作者：xds
      */
	 public static String camelUnderline(String line){
		if (line == null || "".equals(line)) {
			return "";
		}
		line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(word.toUpperCase());
			sb.append(matcher.end() == line.length() ? "" : "_");
		}
		return sb.toString();
	}
}
