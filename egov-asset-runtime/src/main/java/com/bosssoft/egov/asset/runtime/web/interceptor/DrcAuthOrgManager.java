package com.bosssoft.egov.asset.runtime.web.interceptor;

import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;

/** 
*
* @ClassName   类名：DrcAuthOrgManager 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年4月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年4月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DrcAuthOrgManager {
	 private static final String CACHE_CODE = "DrcOrgCache";
	 
	  public static Cache getCache()
	  {
	    return CacheFactory.getInstance().findCache(CACHE_CODE);
	  }
	  
	  public static void clear() {
		    getCache().clear();
	  }
	  
	  public static String getDrcOrgData(String userCode){
		  return  (String) getCache().get(userCode);
	  }
	 
}
