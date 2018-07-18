package com.bosssoft.egov.asset.dictionary.api;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBizAction;
import com.bosssoft.platform.cache.CacheConfigParser;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheProperty;

/** 
*
* @ClassName   类名：AssetBizActionManager 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月6日
* @author      创建人：Wiesel
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月6日   wujian   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AssetBizActionManager {
	private static final String CACHE_NAME = "EgovAssetBizActionCache";


	private static AssetBizActionManager INSTANCE;
	
	public static synchronized AssetBizActionManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AssetBizActionManager();
		}
		return INSTANCE;
	}
	
	
	// 数据字典缓存集合，按key从小到大排序
	private TreeMap<String, Cache> assetBizActionCaches = new TreeMap<String, Cache>(
			new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					return o1.compareTo(o2);
				}
			});

	private  AssetBizActionManager()  {
		String[] cacheNames = CacheConfigParser.getInstance().getCacheNames();
		for (String cacheName : cacheNames) {
			if (cacheName.startsWith(CACHE_NAME)) {
				Cache assetBizActionCache = CacheFactory.getInstance().findCache(cacheName);				
				CacheProperty config = assetBizActionCache.getCacheConfig();
				// 缓存处理的优先级顺序，值越小越优先查找
				assetBizActionCaches.put(config.getPriority() + cacheName,assetBizActionCache);
			}
		}
	}


	/**
	 * 清除指定类型的缓存数据，使之下次加载从数据库中获取
	 * 
	 * @param appId
	 * @param dictTypeId
	 */
	public void removeDict(String billType) {
		for (Map.Entry<String, Cache> entry : assetBizActionCaches.entrySet()) {
			entry.getValue().remove(billType);
		}
	}

	/**
	 * 清除所有字典缓存，下次加载全部从数据库获取
	 */
	public void cleanCache() {
		for (Map.Entry<String, Cache> entry : assetBizActionCaches.entrySet()) {
			entry.getValue().clear();
		}
	}



	/**
	 * 
	 * <p>函数名称：   getDictItems     </p>
	 * <p>功能说明： 获取字典项
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param appId 应用id
	 * @param dictCode 字典类型编码
	 * @return
	 *
	 * @date   创建时间：2016年11月12日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public AssetBizAction getDictItems(String billType ) {
     	AssetBizAction assetBizAction =null;

		for (Map.Entry<String, Cache> entry : assetBizActionCaches.entrySet()) {
			assetBizAction = (AssetBizAction) entry.getValue().get(billType);
			if (assetBizAction != null)				
				break;
		}
		return assetBizAction;
	}
	
	



	
}
