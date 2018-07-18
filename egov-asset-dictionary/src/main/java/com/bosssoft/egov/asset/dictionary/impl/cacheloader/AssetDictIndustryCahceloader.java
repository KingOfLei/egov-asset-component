package com.bosssoft.egov.asset.dictionary.impl.cacheloader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.dictionary.api.AimsDictHelper;
import com.bosssoft.egov.asset.dictionary.api.Dict;
import com.bosssoft.egov.asset.dictionary.api.DictItem;
import com.bosssoft.egov.asset.dictionary.impl.entity.AssetDictIndustry;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AssetDictIndustryMapper;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;
import com.bosssoft.platform.persistence.entity.Example;

/** 
*
* @ClassName   类名：AssetDictIndustryCahceloader 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月23日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月23日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AssetDictIndustryCahceloader implements CacheLoader{

	@Resource
	private AssetDictIndustryMapper mapper;
	
	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {
		//cache.clear();
		return null;
	}

	private Map<Serializable, Serializable> getRootNode(String appId){
		//默认先加载第一级
		Example example = new Example(AssetDictIndustry.class);
		example.createCriteria().andEqualTo("industryPcode", "-");
		//预留数据权限
//						example.createCriteria().andCondition("");
		example.orderBy("industryCode").asc();
		List<AssetDictIndustry> dictIndItem = mapper.selectByExample(example);	
		AssetDictIndustry dictInd = new AssetDictIndustry();
		dictInd.setIndustryId(19511575770291231L);
		dictInd.setIndustryCode("-");
		dictInd.setIndustryName("行业分类");
		dictInd.setIndustryIsleaf("0");
		dictInd.setIndustryRank(1);
		dictInd.setIndustryState("1");
		return buildDictionary(appId, "",dictInd,dictIndItem);
	}
	
	@Override
	public Serializable get(Serializable key) {
		 //_ASSET_DICT_IND_::ID
		String lookupKey = (String)key;
		String flag = StringUtilsExt.substringAfter(lookupKey, "::");
        String appId = StringUtilsExt.substringBefore(flag, "::");
        String indId = StringUtilsExt.substringAfter(flag, "::");
        //根据id 查找
        if(StringUtilsExt.isEmpty(indId)) {
        	Map<Serializable,Serializable> result = getRootNode(appId);
        	return result.get(lookupKey);
        }
        AssetDictIndustry dictInd = new AssetDictIndustry();
        dictInd.setIndustryId(NumberUtilsExt.toLong(indId));        
        AssetDictIndustry ind = mapper.selectOne(dictInd);        
        //根据id 查找其下级
        Example example = new Example(AssetDictIndustry.class);
        example.createCriteria().andEqualTo("industryPid", indId);
        example.orderBy("industryCode").asc();
        List<AssetDictIndustry> dictIndItem = mapper.selectByExample(example);
        Map<Serializable,Serializable> result = buildDictionary(appId, indId, ind, dictIndItem);
		return result.get(lookupKey);
	}

	@Override
	public void put(Serializable key, Serializable value) {
		
		
	}

	@Override
	public Serializable remove(Serializable key) {
		
		return null;
	}
	
	public Map<Serializable, Serializable> buildDictionary(String appId, String id,AssetDictIndustry dictInd,List<AssetDictIndustry> dictIndItem){
		Map<Serializable,Serializable> result = new HashMap<Serializable,Serializable>();
        if(dictInd == null) return result;
		Dict dict = new Dict();
        dict.setAppId(appId);
        dict.setDictId(dictInd.getIndustryId());        
        dict.setDictCode(dictInd.getIndustryCode());
        dict.setDictName(dictInd.getIndustryName());
        dict.setStr1(StringUtilsExt.convertNullToString(dictInd.getIndustryPid()));
        dict.setStr2(dictInd.getIndustryPcode());
        dict.setStr3("");
        dict.setIsbuiltin("0");
        List<DictItem> dictItemsMap = new ArrayList<DictItem>();
        for(AssetDictIndustry indItem : dictIndItem){
        	DictItem item = new DictItem();
        	item.setAppId(appId);
        	item.setDictId(dict.getDictId());
        	item.setDictCode(dict.getDictCode());         	
        	item.setItemId(indItem.getIndustryId());
        	item.setItemCode(indItem.getIndustryCode());
        	item.setItemName(indItem.getIndustryCode() + " " + indItem.getIndustryName());
        	item.setIsenable(indItem.getIndustryState());
        	item.setItemIsleaf(NumberUtilsExt.toInt(indItem.getIndustryIsleaf()));
        	item.setItemLevel(indItem.getIndustryRank());
        	item.setItemPid(indItem.getIndustryPid());
        	item.setItemPcode(indItem.getIndustryPcode());
        	item.setStr01(indItem.getIndustryName());
        	item.setCreateDate(indItem.getCreatedate());
        	item.setCreator(indItem.getCreator());
        	dictItemsMap.add(item);        	
        }
        dict.setDictItems(dictItemsMap);
        String key = AimsDictHelper.buildKeyByInd(appId, id);
        result.put(key, dict);		
        return result;
	}	

}
