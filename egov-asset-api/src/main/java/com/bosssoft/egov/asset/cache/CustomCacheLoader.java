package com.bosssoft.egov.asset.cache;

import java.io.Serializable;
import java.util.Map;

import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;

/** 
*
* @ClassName   类名：CacheLoader 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月7日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月7日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CustomCacheLoader implements CacheLoader{

	@Override
	public Serializable get(Serializable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Serializable, Serializable> preLoad(Cache arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(Serializable arg0, Serializable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable remove(Serializable arg0) {
		return arg0;
	}

}
