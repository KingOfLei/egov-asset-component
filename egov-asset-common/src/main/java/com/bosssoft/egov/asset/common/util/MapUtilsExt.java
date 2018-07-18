package com.bosssoft.egov.asset.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

/** 
*
* @ClassName   类名：MapUtilsExt 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月1日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月1日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class MapUtilsExt extends MapUtils{

	public static Map<String,Object> copyNewMap(Map<String,Object> map){
		Map<String,Object> result = new HashMap<String,Object>();
		
		for(String key : map.keySet()){
			result.put(key, map.get(key));
		}
		return result;
	}
	
	public static Map<String,String> ObjectToStringMap(Map<String,Object> map){
        Map<String,String> result = new HashMap<String,String>();
		
		for(String key : map.keySet()){
			result.put(key, StringUtilsExt.toString(map.get(key), ""));
		}
		return result;
	} 
	
	public static Map<String,Object> StringToObjectMap(Map<String,String> map){
        Map<String,Object> result = new HashMap<String,Object>();
		
		for(String key : map.keySet()){
			result.put(key, StringUtilsExt.toString(map.get(key), ""));
		}
		return result;
	}
	
	public static Map<String,String> ObjectToStringMapWithQuotes(Map<String,Object> map){
        Map<String,String> result = new HashMap<String,String>();
		
		for(String key : map.keySet()){
			result.put(key, "'"+StringUtilsExt.toString(map.get(key), "")+"'");
		}
		return result;
	} 
}
