package com.bosssoft.egov.asset.common;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;

import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;

/** 
*
* @ClassName   类名：PropertiesHelper 
* @Description 功能说明：读取 系统 bosssoft.properties  application.properties 属性
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年6月18日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年6月18日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class PropertiesHelper {
	
	@Resource
    private Environment environment;
	
	private static PropertiesHelper INSTANCE;
	
	public PropertiesHelper(){
		SpringExtensionHelper.initAutowireFields(this);
	}
	
	private static synchronized PropertiesHelper getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new PropertiesHelper();
		}
		return INSTANCE;
   }
	
	public static String getProperty(String key){
		return getInstance().environment.getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue){
		return getInstance().environment.getProperty(key,defaultValue);
	}
	
	public static <T> T getPropetry(String key, Class<T> targetType){
		return getInstance().environment.getProperty(key, targetType);
	}
	
	public static Boolean getBoolean(String key){
		return getInstance().environment.getProperty(key, Boolean.class);
	}
	
	public static Boolean getBoolean(String key, Boolean defaultValue){
		return getInstance().environment.getProperty(key, Boolean.class, defaultValue);
	}
	
	public static Integer getInteger(String key){
		return getInstance().environment.getProperty(key, Integer.class);
	}
	
	public static Integer getInteger(String key, Integer defaultValue){
		return getInstance().environment.getProperty(key, Integer.class,defaultValue);
	}
	
	public static String getString(String key){
		return getInstance().environment.getProperty(key, String.class);
	}
	
	public static Double getDouble(String key){
		return getInstance().environment.getProperty(key, Double.class);
	}
	
	public static Double getDouble(String key,Double defaultValue){
		return getInstance().environment.getProperty(key,Double.class,defaultValue);
	}
	
	public static Long getLong(String key){
		return getInstance().environment.getProperty(key, Long.class);
	}
	public static Long getLong(String key, Long defaultValue){
		return getInstance().environment.getProperty(key, Long.class,defaultValue);
	}
}
