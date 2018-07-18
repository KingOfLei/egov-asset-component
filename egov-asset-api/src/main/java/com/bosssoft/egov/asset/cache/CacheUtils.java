package com.bosssoft.egov.asset.cache;

import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;

/** 
*
* @ClassName   类名：CacheUtils 
* @Description 功能说明：缓存工具类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月5日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月5日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CacheUtils {
	
	/***
	 * 
	 * <p>函数名称：   clearCache     </p>
	 * <p>功能说明： 清除指定缓存信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param cacheName
	 *
	 * @date   创建时间：2017年9月5日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static void clearCache(String cacheName){
		Cache cache = CacheFactory.getInstance().findCache(cacheName);
		if(cache != null){
			cache.clear();
		}
	}

}
