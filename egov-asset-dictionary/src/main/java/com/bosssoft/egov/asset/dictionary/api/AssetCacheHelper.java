package com.bosssoft.egov.asset.dictionary.api;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.bosssoft.platform.cache.CacheConfigParser;
import com.bosssoft.platform.cache.CacheRuntimeException;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheConfiguration;
import com.bosssoft.platform.common.config.JSONResourceConfigurer;

/** 
*
* @ClassName   类名：AssetCacheHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AssetCacheHelper {
	
	private static Logger logger = LoggerFactory.getLogger(AssetCacheHelper.class);
	
	//缓存已经注册的cache文件 避免重复加载 造成浪费
	private static List<String> cacheConfigs = new ArrayList<String>();
	
	/**
	 * 
	 * <p>函数名称：  createCacheMap      </p>
	 * <p>功能说明： 根据json名称 获取都要的Cache类
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param cacheConfig
	 * @return
	 *
	 * @date   创建时间：2016年11月19日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static void registerCache(String cacheConfig){
		if(cacheConfigs.contains(cacheConfig)){
			return;
		}

		CacheConfigParser cfgParser = CacheConfigParser.getInstance();
		try {
			List<URL> cacheFileUrls = JSONResourceConfigurer.getInstance().getJSONFile(cacheConfig);
			
			for (URL cacheFileUrl : cacheFileUrls) {
				String jsonConfig = IOUtils.toString(cacheFileUrl, "UTF-8");
				List<CacheConfiguration> cacheCfgs = JSONObject.parseArray(jsonConfig, CacheConfiguration.class);
				if(cacheCfgs != null){
					for(CacheConfiguration cfg : cacheCfgs){
						if(!cfgParser.containsCacheConfig(cfg.getCacheName())){
							logger.info("Load cache configuration {}",cfg.getCacheName());
							cfg.getCacheProperty().setCacheName(cfg.getCacheName());
							cfgParser.putCacheProperty(cfg.getCacheName(),cfg.getCacheProperty());
						}
					}
				}				
			}
			cacheConfigs.add(cacheConfig);
		} catch (IOException e) {
			throw new CacheRuntimeException("Can't load cache configuration file:" + cacheConfig, e);
		}		
	}	
	
	public static TreeMap<String, Cache> doCreateTreeMap(){
		return new TreeMap<String, Cache>(
				new Comparator<String>() {
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});
	}
	
}
