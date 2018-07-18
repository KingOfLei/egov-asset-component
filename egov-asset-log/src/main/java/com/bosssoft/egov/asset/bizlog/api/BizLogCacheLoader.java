package com.bosssoft.egov.asset.bizlog.api;

import java.io.Serializable;
import java.util.Map;

import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;

/** 
*
* @ClassName   类名：OperLogCacheloader 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class BizLogCacheLoader implements CacheLoader{

	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {		
		return null;
	}

	@Override
	public Serializable get(Serializable key) {		
		return null;
	}

	@Override
	public void put(Serializable key, Serializable value) {
		
	}

	@Override
	public Serializable remove(Serializable key) {		
		return null;
	}

}
