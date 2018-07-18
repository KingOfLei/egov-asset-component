package com.bosssoft.egov.asset.common.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/** 
*
* @ClassName   类名：JsonUtilsExt 
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
public class JsonUtilsExt {

	
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static String toJson(Object obj,SerializerFeature... features){
		return JSON.toJSONString(obj,features);
	}
	
	  /**
	   * 
	   * <p>函数名称： json2Map       </p>
	   * <p>功能说明： json 转 map 
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @param json
	   * @return
	   *
	   * @date   创建时间：2016年5月12日
	   * @author 作者：谢德寿
	   */
	  public static Map<String, Object> json2Map(String json,boolean camel) {
			JsonParser parser = new JsonParser();		    
			JsonReader reader = new JsonReader(new StringReader(json));
			reader.setLenient(true);
			JsonObject object = (JsonObject) parser.parse(reader);
		    Set<Map.Entry<String, JsonElement>> set = object.entrySet();
		    Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
		    HashMap<String, Object> map = new HashMap<String, Object>();
		    while (iterator.hasNext()) {
		        Map.Entry<String, JsonElement> entry = iterator.next();
		        String key = camel ? ColumnUtilsExt.underlineToCamel(entry.getKey()) : entry.getKey();
		        JsonElement value = entry.getValue();
		        if(value.isJsonNull()){ //null 值
		        	map.put(key, value.toString());
		        } else if (!value.isJsonPrimitive()) {
		        	if(value.isJsonArray()){
		              map.put(key, json2List(value.toString(),camel));
		            } else if(value.isJsonObject()){
		              map.put(key, json2Map(value.toString(),camel));
		            }
		        } else {
		            map.put(key, value.getAsString());
		        }
		    }
		    return map;
		}
	  
	  public static Map<String, Object> json2Map(String json){
		  return json2Map(json, false);
	  }
	  
	  public static List<Object> json2List(String json){
		  return json2List(json, false);
	  }
	  
	  public static List<Object> json2List(String json,boolean camel){
		  List<Object> list = new ArrayList<Object>();
		  JsonParser parser = new JsonParser();		
		  JsonReader reader = new JsonReader(new StringReader(json));
		  reader.setLenient(true);
		  JsonArray object = parser.parse(reader).getAsJsonArray();
		  Iterator<JsonElement> iterator = object.iterator();
		  while (iterator.hasNext()) {
			  JsonElement entry = iterator.next();		
			  if(entry.isJsonNull()){ //null 值
				  list.add(entry.toString());
			  } else if(!entry.isJsonPrimitive()){
				  if(entry.isJsonObject()){
					  list.add(json2Map(entry.toString(),camel));
				  } else { 					  
					  list.add(json2List(entry.toString(),camel));
				  }
			  }  else {
				  list.add(entry.getAsString());
			  }
		  }
		  return list;
	  }
	  
	  
	  public static List<Object> json2List(JsonReader reader,boolean camel){
		  List<Object> list = new ArrayList<Object>();
		  JsonParser parser = new JsonParser();		
		  reader.setLenient(true);
		  JsonArray object = parser.parse(reader).getAsJsonArray();
		  Iterator<JsonElement> iterator = object.iterator();
		  while (iterator.hasNext()) {
			  JsonElement entry = iterator.next();		
			  if(entry.isJsonNull()){ //null 值
				  list.add(entry.toString());
			  } else if(!entry.isJsonPrimitive()){
				  if(entry.isJsonObject()){
				    list.add(json2Map(entry.toString(),camel));
				  } else {
					  list.add(json2List(entry.toString(),camel));
				  }
			  }  else {
				  list.add(entry.getAsString());
			  }
		  }
		  return list;
	  }
	  
	  public static String obj2Json(Object obj){
		  Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
		  return gson.toJson(obj);
	  }
}
