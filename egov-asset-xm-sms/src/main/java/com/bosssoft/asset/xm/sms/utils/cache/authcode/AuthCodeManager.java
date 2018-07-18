package com.bosssoft.asset.xm.sms.utils.cache.authcode;

import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;

/** 
*
* @ClassName   类名：AuthCodeManager 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年3月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年3月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AuthCodeManager {
	 private static final String AUTH_CODE = "AuthCode";

	  public static Cache getCache()
	  {
	    return CacheFactory.getInstance().findCache(AUTH_CODE);
	  }

	  public static String getUserAuthCode(String userCode) {
	    String key = "_PHONE_FORGETPW_CODE_" +  userCode + "_";
	    return (String) getCache().get(key);
	  }
	  
	  
	  public static void setUserAuthCode(String userCode,String authCode,int expired){
		    String key = "_PHONE_FORGETPW_CODE_" +  userCode + "_";
            getCache().put(key, authCode);
	  }
	  
	  public static String getUserPhone(String userCode){
		  String key = "_PHONE_USERPHONE_" +  userCode + "_";
		  return (String) getCache().get(key);
	  }
	  
	  public static void setUserPhone(String userCode,String phone,int expired){
		  String key = "_PHONE_USERPHONE_" +  userCode + "_";
		  getCache().put(key, phone);
	  }

	  public static String getUserName(String userCode){
		  String key = "_PHONE_USERNAME_" +  userCode + "_";
		  return (String) getCache().get(key);
	  }
	  
	  public static void setUserName(String userCode,String userName,int expired){
		  String key = "_PHONE_USERNAME_" + userCode + "_";
		  getCache().put(key, userName);		 
	  }
	  
	  public static void clear() {
	    getCache().clear();
	  }
	  
	  public static String getUserCheckCode(String userCode) {
		    String key = "_PHONE_CHECK_CODE_" +  userCode + "_";
		    return (String) getCache().get(key);
		  }
		  
	  public static void setUserCheckCode(String userCode,String checkCode,int expired) {
		    String key = "_PHONE_CHECK_CODE_" +  userCode + "_";
		    getCache().put(key, checkCode);
	  }
	  
	  public static void clearUserInfo(String userCode){		 
		  getCache().remove("_PHONE_CHECK_CODE_" + userCode + "_");
		  getCache().remove("_PHONE_FORGETPW_CODE_" + userCode + "_");
		  getCache().remove("_PHONE_USERNAME_" + userCode + "_");
		  getCache().remove("_PHONE_USERPHONE_" + userCode + "_");
	  }
}
