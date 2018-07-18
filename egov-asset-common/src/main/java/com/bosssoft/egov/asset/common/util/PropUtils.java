package com.bosssoft.egov.asset.common.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropUtils {

    private static Map<String, Map<String, String>> prop = new HashMap<String, Map<String, String>>();

    public static String getString(String propName, String key) {
        Map<String, String> propCfg = getProp(propName);
        if (propCfg == null || !propCfg.containsKey(key)) {
            return null;
        }
        return propCfg.get(key);
    }

    private static synchronized void loadProperties(String propName) {
        try {

            InputStream is = PropUtils.class.getClassLoader().getResourceAsStream(propName + ".properties");
            Properties p = new Properties();
            p.load(is);
            Set<Object> keySet = p.keySet();
            Map<String, String> pValue = new HashMap<String, String>();
            for (Object key : keySet) {
                pValue.put((String) key, p.getProperty(key.toString()));
            }
            prop.put(propName, pValue);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Integer getInt(String propName, String key) {
        return getInt(propName, key, null);
    }
    
    public static Integer getInt(String propName, String key, Integer def){
    	 String strVal = getString(propName, key);
         if (StringUtilsExt.isEmpty(strVal)) {
             return def;
         }
         try{
        	return Integer.parseInt(strVal);
         } catch(NumberFormatException e){
        	 return def;
         }
    }

    public static Boolean getBoolean(String propName, String key) {
        return getBoolean(propName, key, false);
    }
    
    public static Boolean getBoolean(String propName, String key, boolean def){
    	  String strVal = getString(propName, key);
          if (StringUtilsExt.isEmpty(strVal)) {
              return def;
          }
          return Boolean.parseBoolean(strVal);
    }

    public static Map<String, String> getProp(String propName) {
        if (!prop.containsKey(propName)) {
            loadProperties(propName);
        }
        return prop.get(propName);
    }

}
