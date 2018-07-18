package com.bosssoft.egov.asset.common.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.platform.common.resolver.PathMatchingResourcePatternResolver;

/** 
*
* @ClassName   类名：ResourceMessagesHolder 
* @Description 功能说明：提示信息资源管理类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年11月7日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年11月7日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ResourceMessagesResolver {

	private static Logger logger = LoggerFactory.getLogger(ResourceMessagesResolver.class);
	
	private static final Locale DEFAULT_LOCALE = new Locale("zh_CN");                              

	//存放配置文件map
    protected static final Map<String,ResourceBundle>  locales  = Collections.synchronizedMap(new HashMap<String,ResourceBundle>());
	
	private static ResourceMessagesResolver instance;
	
	public synchronized static ResourceMessagesResolver getInstance() {
		if (instance == null) {
			instance = new ResourceMessagesResolver();
		}
		return instance;
	}
    
    protected static Map<String, ResourceBundle> getLocales()
    {
        return locales;
    }
    
    public String getString(String key, String fileName){
    	return calculateString(key, fileName);
	}
	
	public String getString(String key, String fileName,String... parameters){
		return calculateString(key, fileName, DEFAULT_LOCALE,parameters);
	}
	
	public String getString(String key, String fileName,Locale locale,String... parameters){
		return calculateString(key, fileName, locale,parameters);
	}
	
    protected String calculateString(String key, String bizType){
        Object[] parameters = new Object[] {};
		return calculateString(key, bizType, DEFAULT_LOCALE,parameters);
	}
	
	protected String calculateString(String key, String fileName,Locale locale, Object[] parameters){
		String string = null;
		try { string = findString(fileName, locale, key, parameters); } catch(MissingResourceException e){}
		if (string!=null) return string;	

		try { string = findString("messages/default", locale, key, parameters); } catch(MissingResourceException e){}
		if (string!=null) return string;	
        
		string = "Message not found in the properties:key：" + key  + ",locale:" + locale.toString();
		return string;
	}

   protected String findString(String fileName, Locale locale, String key, Object[] parameters) throws MissingResourceException
    {
        try
        {
            ResourceBundle bundle = getBundle(locale, fileName);
            String unformattedString = bundle.getString(key);
            String string = MessageFormat.format(unformattedString, parameters);
            return string;
        }
        catch(IllegalArgumentException e)
        {
            String message = "Format problem with key=["+key+"], locale=["+locale+"], fileName=" + fileName + " : "+e.toString();
            logger.error(message);
            throw new MissingResourceException(message, fileName, key);
        }
    }
   protected static String buildHashKey(Locale locale, String packageName)
   {
       return packageName + "_" + getLocaleString(locale);
   }
   
   protected static String getLocaleString(Locale locale)
   {
       String locString = locale.toString();
       if (locString.length()==5 && locString.charAt(2)=='_')
       {
           locString=locString.substring(0,2).toLowerCase()+"_"+locString.substring(3).toUpperCase();
       }
       return locString;
   }
   
   public static ResourceBundle getBundle(Locale locale, String fileName) throws MissingResourceException
   {
   	String filename = buildHashKey(locale, fileName);
   	filename = "classpath*:"+filename.replace('.', '/') + ".properties";
   	
   	try
   	{
   	    ResourceBundle bundle = locales.get(filename);
        if (bundle == null) {
	   		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	   		URL[] url = resolver.getResources(filename);
	   		if(url == null || url.length < 1) {
	       		throw new MissingResourceException("Unable to find properties file ["+filename+"]", locale.toString(), fileName);
	   		} 
	    	InputStream inputStream = url[0].openStream();
	       	if (inputStream!=null) {
	       		bundle = new PropertyResourceBundle(new InputStreamReader(inputStream, "UTF-8"));
	       		locales.put(filename, bundle);
	       	} else {
	       		throw new MissingResourceException("Unable to find properties file ["+filename+"]", locale.toString(), fileName);
	       	}
       }
       return bundle;
   	}
   	catch(IOException e)
   	{
   		throw new MissingResourceException("Unable to find properties file ["+filename+"] : "+e.toString(), locale.toString(), fileName);
   	}
   }
	
	public static void main(String[] args) {
		System.out.println(Locale.getDefault());
	}
}
