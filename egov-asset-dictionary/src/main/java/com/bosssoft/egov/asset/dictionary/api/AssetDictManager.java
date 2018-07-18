package com.bosssoft.egov.asset.dictionary.api;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.cache.CacheConfigParser;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheProperty;

/**
 *
 * @ClassName 类名：AssetDictManager
 * @Description 功能说明：对应表名asset_basic_dict_item 
 *                             asset_basic_dict
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月12日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年11月12日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class AssetDictManager {
	
	private static final String CACHE_NAME = "EgovAssetDictCache";

	//key格式
	private static final String DICT_PATTERN = "{0}:{1}";

	private static AssetDictManager INSTANCE;

	// 数据字典缓存集合，按key从小到大排序
	private TreeMap<String, Cache> dictionaryCaches = new TreeMap<String, Cache>(
			new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					return o1.compareTo(o2);
				}
			});

	private AssetDictManager() {
		String[] cacheNames = CacheConfigParser.getInstance().getCacheNames();
		for (String cacheName : cacheNames) {
			if (cacheName.startsWith(CACHE_NAME)) {
				Cache dictionaryCache = CacheFactory.getInstance().findCache(cacheName);				
				CacheProperty config = dictionaryCache.getCacheConfig();
				// 缓存处理的优先级顺序，值越小越优先查找
				dictionaryCaches.put(config.getPriority() + cacheName,dictionaryCache);
			}
		}
	}

	public static synchronized AssetDictManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AssetDictManager();
		}
		return INSTANCE;
	}

	private String buildKey(String appId, String dictCode) {
		return MessageFormat.format(DICT_PATTERN, appId, dictCode);
	}

	/**
	 * 清除指定类型的缓存数据，使之下次加载从数据库中获取
	 * 
	 * @param appId
	 * @param dictTypeId
	 */
	public void removeDict(String appId, String dictCode) {
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			entry.getValue().remove(buildKey(appId, dictCode));
		}
	}

	/**
	 * 清除所有字典缓存，下次加载全部从数据库获取
	 */
	public void cleanCache() {
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			entry.getValue().clear();
		}
	}

	/**
	 * 
	 * <p>函数名称：  getDictName      </p>
	 * <p>功能说明： 获取字典类型名称
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param appId 应用ID
	 * @param dictCode 字典类型编码
	 * @return
	 *
	 * @date   创建时间：2016年11月12日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getDictName(String appId, String dictCode) {
		Dict dictType = null;
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			dictType = (Dict) entry.getValue().get(buildKey(appId, dictCode));
			if (dictType != null)
				break;
		}
		return dictType != null ? dictType.getDictName() : "";
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
	public List<DictItem> getDictItems(String appId, String dictCode) {
		Dict dict = null;
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			dict = (Dict) entry.getValue().get(buildKey(appId, dictCode));
			if (dict != null)				
				break;
		}
		return dict != null ? dict.getDictItems(): new ArrayList<DictItem>();
	}
	
	/**
	 * 
	 * <p>函数名称：   getDictItems     </p>
	 * <p>功能说明：批量获取字典项
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param appId
	 * @param dictCodes
	 * @return
	 *
	 * @date   创建时间：2017年3月23日
	 * @author 作者：xds
	 */
	public Map<String,List<DictItem>> getDictItems(String appId,String[] dictCodes){
		Map<String,List<DictItem>> result = new HashMap<String,List<DictItem>>();
		if(dictCodes == null) return result;
		for(String dictCode : dictCodes){
			Dict dict = null;
			for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
				dict = (Dict) entry.getValue().get(buildKey(appId, dictCode));
				if (dict != null)				
					break;
			}
			result.put(dictCode, dict != null ? dict.getDictItems(): new ArrayList<DictItem>());
		}		
		return result;
	}

	/**
	 * 
	 * <p>函数名称： getDictItemName       </p>
	 * <p>功能说明： 获取单个字典项名称
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param appId 应用id
	 * @param dictCode 字典项所属类型编码
	 * @param dictItemCode 字典项编码
	 * @return
	 *
	 * @date   创建时间：2016年11月12日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getDictItemName(String appId, String dictCode,String dictItemCode) {
		DictItem dictItem = getDictItem(appId, dictCode, dictItemCode); 
		return (dictItem == null) ? "" : dictItem.getItemName();
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param appId
	 * @param dictCode
	 * @param dictItemCode
	 * @return
	 *
	 * @date   创建时间：2017年6月19日
	 * @author 作者：xds
	 */
	
	public DictItem getDictItem(String appId, String dictCode, String dictItemCode){
		List<DictItem> entries = getDictItems(appId, dictCode);
		for (DictItem entry : entries) {
			if(StringUtilsExt.equals(entry.getItemCode(), dictItemCode)){
				return entry;
			}			
		}
		return null;
	}
}
