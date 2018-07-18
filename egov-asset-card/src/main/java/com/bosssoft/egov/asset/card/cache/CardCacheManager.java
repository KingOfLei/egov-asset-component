package com.bosssoft.egov.asset.card.cache;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.bosssoft.egov.asset.card.entity.basic.CardUiConfigBaseEntity;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;

/** 
*
* @ClassName   类名：CardCacheManager 
* @Description 功能说明：卡片缓存管理类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CardCacheManager {
	
	private static final String CARD_CACHE_Prefix = "ASSET-CARD";
	
	private static final String CARD_CACHE_UI_CONFIG = "ASSET-CARD-UI-CONFIG";
	
	private TreeMap<String, Cache> cardCaches = new TreeMap<String, Cache>(
			new Comparator<String>() {
				public int compare(String o1, String o2) {
					return o1.compareTo(o2);
				}
			});
	
	private static CardCacheManager INSTANCE;	
	
	public static synchronized CardCacheManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CardCacheManager();
		}
		return INSTANCE;
	}
	
	private CardCacheManager(){
		//初始化所有关于卡片的缓存
		Set<String> cacheNameSet = CacheFactory.getInstance().keySet();
		for(String cacheName : cacheNameSet){
			if(cacheName.startsWith(CARD_CACHE_Prefix)){
				Cache cache = CacheFactory.getInstance().findCache(cacheName);
				cardCaches.put(cacheName, cache);
			}
		}
	}
	
	public CardUiConfigBaseEntity getCardUiConfig(CardUiConfigBaseEntity uiConfig){
		Cache cache = cardCaches.get(CARD_CACHE_UI_CONFIG);
		if(cache != null){
			return (CardUiConfigBaseEntity) cache.get(buildUiConfigKey(uiConfig));	
		}
		return null;		
	}
	
	public void clearCardUiConfig(){
		Cache cache = cardCaches.get(CARD_CACHE_UI_CONFIG);
		if(cache != null){
			cache.clear();	
		}
	}
	

	public void cleanCache() {
		for (Entry<String, Cache> entry : this.cardCaches.entrySet())
			((Cache) entry.getValue()).clean();
	}
	
	public void clearCache() {
		for (Entry<String, Cache> entry : this.cardCaches.entrySet())
			((Cache) entry.getValue()).clear();
	}
	
	private String buildUiConfigKey(CardUiConfigBaseEntity uiConfig){
		//key格式:ASSET_CARD_UI_CONFIG::typeCode:classifyCode
		return MessageFormat.format("{0}::{1}::{2}", new Object[] { uiConfig.getUiConfigTable(), uiConfig.getAssetTypeCode(),uiConfig.getAssetClassifyCode()});
	}
	
}
