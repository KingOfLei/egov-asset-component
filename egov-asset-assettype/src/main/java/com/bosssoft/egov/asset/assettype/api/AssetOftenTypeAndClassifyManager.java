package com.bosssoft.egov.asset.assettype.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.assettype.entity.AssetDictAssetType;
import com.bosssoft.egov.asset.assettype.entity.AssetDictClassify;
import com.bosssoft.egov.asset.assettype.service.AssetDictAssetTypeOftenService;
import com.bosssoft.egov.asset.assettype.service.AssetDictAssetTypeService;
import com.bosssoft.egov.asset.assettype.service.AssetDictClassifyService;
import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.dictionary.api.AssetCacheHelper;
import com.bosssoft.egov.asset.dictionary.api.Dict;
import com.bosssoft.egov.asset.dictionary.api.DictItem;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.cache.CacheConfigParser;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;

/** 
*
* @ClassName   类名：AssetTypeAndClassifyManager 
* @Description 功能说明：获取 资产类型 及 资产分类管理类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AssetOftenTypeAndClassifyManager {
	
	private static final String CACHE_NAME = "ASSET-DICT-ONFTEN-TYPE-CLASSIFY-CACHE";
	/*
	 * 常用分类表 
	 */
	public static final String ASSET_TYPE_ONFTEN_KEY = "::_ASSET_TYPE_ONFTEN_KEY_::"; 	
	public static final String ASSET_CLASSIFY_ONFTEN_KEY = "::ASSET_CLASSIFY_ONFTEN_KEY::"; 
	public static final String ASSET_CLASSIFY_ONFTEN_TABLE = "ASSET_CLASSIFY_OFTEN"; 

	private TreeMap<String, Cache> dictionaryCaches = AssetCacheHelper.doCreateTreeMap();
	
	private static AssetOftenTypeAndClassifyManager INSTANCE;	
	
	@Resource
	private AssetDictAssetTypeService typeService; 
	
	@Resource
	private AssetDictClassifyService classifyService;
	
	@Resource
	private AssetDictAssetTypeOftenService typeOftenService;
	
	public static synchronized AssetOftenTypeAndClassifyManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AssetOftenTypeAndClassifyManager();
		}
		return INSTANCE;
	}
	
	private AssetOftenTypeAndClassifyManager(){
		Cache cache = CacheFactory.getInstance().createCache(CacheConfigParser.getInstance().getCacheProperty(CACHE_NAME));
		if(cache != null) {
		  dictionaryCaches.put(CACHE_NAME, cache);
		}	
		//注入依赖
		SpringExtensionHelper.initAutowireFields(this);
	}
	
	/**
	 * 
	 * <p>函数名称：   getAssetDictAssetTypeListByTypeCode     </p>
	 * <p>功能说明： 根据资产类型获取其类型列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param typeCodes
	 * @return
	 *
	 * @date   创建时间：2016年11月20日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public List<AssetDictAssetType> getAssetDictAssetTypeListByTypeCode(String... typeCodes){
		return typeService.getAssetDictAssetTypeByTypeCodes(typeCodes);
	}
	
	/**
	 * 
	 * <p>函数名称：   getClassifyDictItemBySql     </p>
	 * <p>功能说明：根据sql获取对应资产分类数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param sql
	 * @return
	 *
	 * @date   创建时间：2016年12月25日
	 * @author 作者：xds
	 */
	public List<DictItem> getClassifyDictItemBySql(String sql){
		Map<String, String> params = new HashMap<String, String>();
		List<AssetDictClassify> list = classifyService.queryAssertClassifyBySqlCondition(sql,params);
		List<DictItem> listItem = new ArrayList<DictItem>();
		for (AssetDictClassify classify : list) {
			DictItem item = new DictItem();
			item.setDictId(classify.getAssetTypeId());
			item.setDictCode(classify.getAssetTypeCode());
			item.setDictName(classify.getAssetTypeName());
			item.setItemId(classify.getAssetClassifyId());
			item.setItemCode(classify.getAssetClassifyCode());
			item.setItemName(classify.getAssetClassifyName());
			item.setItemPid(classify.getAssetClassifyPid());
			item.setItemPcode(classify.getAssetClassifyPcode());
			item.setItemIsleaf(classify.getIsleaf());
			item.setItemLevel(classify.getRank());
			item.setIsenable(classify.getStatus());
			item.setCreator(classify.getCreator());
			item.setCreateDate(classify.getCreatedate());
			item.setStr01("classify::" + item.getItemId());
			item.setStr02(item.getDictId() + "");
        	item.setStr03(classify.getAssetClassifyCode() + " " + classify.getAssetClassifyName());
        	item.setStr04("");
        	item.setStr05("");
			listItem.add(item);
		}
		return listItem;
		
	}
	
	/**
	 * 
	 * <p>函数名称：getClassifyOftenDictItem        </p>
	 * <p>功能说明： 获取当前单位对应的常用分类数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param id
	 * @param typeCodes
	 * @return
	 *
	 * @date   创建时间：2016年12月27日
	 * @author 作者：xds
	 */
	
	public List<DictItem> getClassifyOftenDictItem(String id, String... typeCodes){
		List<DictItem> dictItem = new ArrayList<DictItem>();
		//获取资产类型
		if(StringUtilsExt.isEmpty(id)){
			List<DictItem> allDictItem = getAssetTypeOften();
			OrderByUtil.sort(allDictItem, "dictCode desc");
			if(typeCodes != null && typeCodes.length >0){
				for(DictItem item : allDictItem){
					for(String type : typeCodes){				
						if(StringUtilsExt.startsWith(item.getItemCode(), type)){
							dictItem.add(item);
						}
					}				
				}
			} else {
				dictItem.addAll(allDictItem);
			}
		} else { //分类
			dictItem.addAll(getClassifyOftenByTypeId(id));
		}
		return dictItem;
	}
	
	public List<DictItem> getClassifyOftenByTypeId(String typeId){
		User user = AppContext.getAppContext().getSessionUser();
		Dict dict = null;
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			dict = (Dict) entry.getValue().get(ASSET_CLASSIFY_ONFTEN_KEY + user.getOrgId() + typeId);
			if (dict != null)				
				break;
		}
		return dict != null ? dict.getDictItems(): new ArrayList<DictItem>();
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年12月27日
	 * @author 作者：xds
	 */
	public List<DictItem> getAssetTypeOften(){
		User user = AppContext.getAppContext().getSessionUser();
		Dict dict = null;
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			dict = (Dict) entry.getValue().get(ASSET_TYPE_ONFTEN_KEY + user.getOrgId());
			if (dict != null)				
				break;
		}
		return dict != null ? dict.getDictItems(): new ArrayList<DictItem>();
	}
	
	/**
	 * 
	 * <p>函数名称： addOftenTypeAndClassify       </p>
	 * <p>功能说明： 新增常用资产分类
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param classifyCode
	 *
	 * @date   创建时间：2017年1月7日
	 * @author 作者：xds
	 */
	public void addOftenTypeAndClassify(String typeCode, String classifyCode){
		User user = AppContext.getAppContext().getSessionUser();
		typeOftenService.addOftenInfo(user, typeCode,classifyCode);
	}
	
	/**
	 * 
	 * <p>函数名称：  clearCache      </p>
	 * <p>功能说明： 清除缓存
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param cacheNames
	 *
	 * @date   创建时间：2017年1月7日
	 * @author 作者：xds
	 */
	public void clearCache(String... cacheNames){
		for (Map.Entry<String, Cache> entry : dictionaryCaches.entrySet()) {
			if(cacheNames == null || cacheNames.length == 0){
				entry.getValue().clear();
			} else {
				for(String cacheName : cacheNames){
					if(entry.getKey().equalsIgnoreCase(cacheName)){
						entry.getValue().clear();
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <p>函数名称：clearAllCache        </p>
	 * <p>功能说明： 清除全部缓存
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2017年1月7日
	 * @author 作者：xds
	 */
	public void clearAllCache(){
		clearCache();
	}
}
