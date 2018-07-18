package com.bosssoft.asset.xm.sms.utils.cache.authcode;

import java.io.Serializable;
import java.util.Map;

import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;

/** 
*
* @ClassName   类名：AuthCode 
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
public class AuthCodeCacheLoader implements CacheLoader{
	
	@Override
	public Serializable get(Serializable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {		
		return null;
	}

	@Override
	public void put(Serializable key, Serializable value) {		
		
	}

	@Override
	public Serializable remove(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

}
