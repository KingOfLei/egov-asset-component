package com.bosssoft.egov.asset.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.common.PropertiesHelper;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheProperty;

/**
 *
 * @ClassName 类名：CacheHelper
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年5月7日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2017年5月7日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
@SuppressWarnings("unchecked")
public class CacheHelper {

	private static final  String CACHE_CODE = "AssetCommonCache";

	private static Map<String,CacheHelper> INSTANCE = new HashMap<String, CacheHelper>();

	private Cache cache;

	private CacheHelper(String cacheName) {
		this.cache = CacheFactory.getInstance().findCache(cacheName);
		if(this.cache == null){
			//根据状态修改
			CacheProperty cacheProperty = new CacheProperty();
			cacheProperty.setCacheName(cacheName);
			cacheProperty.setCacheMode("LOCAL");
			cacheProperty.setCacheLoader("com.bosssoft.egov.asset.cache.CustomCacheLoader");
			cacheProperty.setCacheProvider("com.bosssoft.platform.cache.redis.RedisCacheProvider");
			cacheProperty.setServerAddress(PropertiesHelper.getProperty("redis.host", "127.0.0.1:6379"));
			cacheProperty.setMaxElementsInMemory(10000);
			cacheProperty.setTimeToLiveSeconds(12000);
			cacheProperty.setTimeToIdleSeconds(12000);
			cacheProperty.setPriority(100);
			cacheProperty.setClustered(false);
			this.cache = CacheFactory.getInstance().createCache(cacheProperty);
		}
	}

	public static synchronized CacheHelper getInstance(String cacheName) {
		cacheName = StringUtilsExt.toString(cacheName, CACHE_CODE);
		if (INSTANCE.get(cacheName) == null) {
			CacheHelper _new = new CacheHelper(cacheName);
			INSTANCE.put(cacheName, _new);
		}
		return INSTANCE.get(cacheName);
	}
	
	public static synchronized CacheHelper getInstance() {
		if (INSTANCE.get(CACHE_CODE) == null) {
			CacheHelper _new = new CacheHelper(CACHE_CODE);
			INSTANCE.put(CACHE_CODE, _new);
		}
		return INSTANCE.get(CACHE_CODE);
	}

	public String getCache(String key) {
		return (String) this.cache.get(key);
	}

	public Map<String,Object> getCacheMap(String key) {
		return (Map<String,Object>) this.cache.get(key);
	}
	
	public <T> List<T> getCache(String key, Class<T> entityClass) {
		return (List<T>) this.cache.get(key);
	}

	public Map<String, Object> getMapCache(String key) {
		return (Map<String, Object>) this.cache.get(key);

	}

	public <T> void putSingleToList(String key, Class<T> entityClass, T obj) {
		List<T> list = (List<T>) this.cache.get(key);
		list.add(obj);
		putCache(key, list);
	}

	public void putSingleToList(String key, String val) {
		putSingleToList(key, String.class, val);
	}

	public void putCache(String key, Object obj) {
		this.cache.put(key, (Serializable) obj);
	}
	
	public List<Map<String,Object>> getListMapCache(String key){
		return (List<Map<String, Object>>) this.cache.get(key);
	}

	/**
	 * 
	 * <p>
	 * 函数名称：
	 * </p>
	 * <p>
	 * 功能说明：设置过期时间
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 *
	 * @date 创建时间：2017年7月7日
	 * @author 作者：wujian
	 */
	public void putExpired(String key,Object value, int expireTime) {
		this.cache.putExpired(key, (Serializable)value, expireTime);
	}

	/**
	 * 
	 * <p>
	 * 函数名称：
	 * </p>
	 * <p>
	 * 功能说明：移除相应的key
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param key
	 *
	 * @date 创建时间：2017年7月7日
	 * @author 作者：wujian
	 */
	public void remove(String key) {
		this.cache.remove(key);
	}

	/**
	 * 
	 * <p>
	 * 函数名称：
	 * </p>
	 * <p>
	 * 功能说明：判断是否包含key
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param key
	 * @return
	 *
	 * @date 创建时间：2017年7月6日
	 * @author 作者：wujian
	 */
	public boolean containsKey(Serializable key) {
		return this.cache.get(key) != null;

	}

	/**
	 * 
	 * <p>函数名称：   clean     </p>
	 * <p>功能说明： 清除过期数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2017年12月8日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public void clean() {
		this.cache.clean();
	}
	
	/**
	 * 
	 * <p>函数名称：  clear      </p>
	 * <p>功能说明： 清空数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2017年12月8日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public void clear(){
		this.cache.clear();
	}
}
