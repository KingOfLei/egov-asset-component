package com.bosssoft.egov.asset.dictionary.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.bosssoft.egov.asset.dictionary.api.AssetCacheHelper;
import com.bosssoft.egov.asset.dictionary.api.Dict;
import com.bosssoft.egov.asset.dictionary.api.DictItem;
import com.bosssoft.platform.cache.CacheConfigParser;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;

/** 
*
* @ClassName   类名：AimsIndDictManger 
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
public class AimsDictIndustryDictManger {
	
	private static final String CACHE_NAME = "EgovAssetDictIndustryCache";
	
	private TreeMap<String, Cache> dictionaryCaches = AssetCacheHelper.doCreateTreeMap();
	private static AimsDictIndustryDictManger INSTANCE;
	
	public static synchronized AimsDictIndustryDictManger getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AimsDictIndustryDictManger();
		}
		return INSTANCE;
	}
	
	private AimsDictIndustryDictManger(){
		dictionaryCaches.put(CACHE_NAME, CacheFactory.getInstance().createCache(CacheConfigParser.getInstance().getCacheProperty(CACHE_NAME)));		
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
	 * <p>功能说明： 获取此indId的名称
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
	public String getDictName(String appId, String indId) {
		Dict dictType = null;
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			dictType = (Dict) entry.getValue().get(buildKey(appId, indId));
			if (dictType != null)
				break;
		}
		return dictType != null ? dictType.getDictName() : "";
	}

	private String buildKey(String appId, String id) {
		return AimsDictHelper.buildKeyByInd(appId,id);
	}

	/**
	 * 
	 * <p>函数名称：   getDictItems     </p>
	 * <p>功能说明： 获取此indId的子级数据项
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
	public List<DictItem> getDictItems(String appId, String indId) {
		Dict dict = null;
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			dict = (Dict) entry.getValue().get(buildKey(appId, indId));
			if (dict != null)				
				break;
		}
		return dict != null ? dict.getDictItems(): new ArrayList<DictItem>();
	}

	/**
	 * 
	 * <p>函数名称： getDictItemName       </p>
	 * <p>功能说明： 获取此indId下级对应indCode的字典项名称
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
	public String getDictItemName(String appId, String indId,String childIndCode) {
		List<DictItem> entries = getDictItems(appId, childIndCode);
		for (DictItem entry : entries) {
			if (entry.getItemCode().equals(childIndCode)) {
				return entry.getItemName();
			}
		}
		return null;
	}	
}
