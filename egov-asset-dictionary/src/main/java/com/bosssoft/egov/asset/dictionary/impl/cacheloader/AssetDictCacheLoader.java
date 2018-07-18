package com.bosssoft.egov.asset.dictionary.impl.cacheloader;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;

import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.dictionary.api.Dict;
import com.bosssoft.egov.asset.dictionary.api.DictItem;
import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBasicDict;
import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBasicDictItem;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AssetBasicDictItemMapper;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AssetBasicDictMapper;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;
import com.bosssoft.platform.persistence.entity.Example;

/** 
*
* @ClassName   类名：AssetDictCacheLoader 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月11日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月11日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AssetDictCacheLoader implements CacheLoader{

	@Resource
	private AssetBasicDictItemMapper dictItemMapper;
	@Resource
	private AssetBasicDictMapper dictMapper;
	
	@Override
	public Serializable get(Serializable key) {

		String lookupKey = (String)key;
		String appId = StringUtilsExt.substringBefore(lookupKey, ":");
		String dictCode = StringUtilsExt.substringAfter(lookupKey, ":");
		
		//查找
		AssetBasicDict dict = new AssetBasicDict();
		dict.setAppId(appId);
		dict.setDictCode(dictCode);
		AssetBasicDict assetDict = dictMapper.selectOne(dict);
		List<AssetBasicDict> dictTypes = new ArrayList<AssetBasicDict>();
        if(assetDict != null){
        	 dictTypes.add(assetDict);
        }
        
        //查询字典条目信息
		Example example = new Example(AssetBasicDictItem.class);
        example.createCriteria().andEqualTo("dictCode", dictCode).andEqualTo("appId", appId).andEqualTo("isenable", "1");
		example.orderBy("itemCode").asc();
		
		List<AssetBasicDictItem> dictItems = dictItemMapper.selectByExample(example);
		Map<Serializable,Serializable> result = buildDictionary(dictTypes, dictItems);
		return result.get(lookupKey);
	}
	
	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {
		List<AssetBasicDict> dict = dictMapper.selectAll();
		Example example = new Example(AssetBasicDictItem.class);
        example.createCriteria().andEqualTo("isenable", "1");
		example.orderBy("itemCode").asc();
		List<AssetBasicDictItem> dictItems = dictItemMapper.selectByExample(example);
		return buildDictionary(dict, dictItems);
	}
	
	@Override
	public void put(Serializable arg0, Serializable arg1) {
		
	}
	
	@Override
	public Serializable remove(Serializable arg0) {
		return null;
	}

	public Map<Serializable, Serializable> buildDictionary(List<AssetBasicDict> allDict,List<AssetBasicDictItem> allDictItems) {
		Map<Serializable,Serializable> result = new HashMap<Serializable,Serializable>();
		//字典类型id-->字典条目
		Map<String,List<DictItem>> dictItemsMap = new HashMap<String,List<DictItem>>();
		//返回的形式
		// Map<String,Dict> 
		for(AssetBasicDictItem dictItem : allDictItems){
			String appId = dictItem.getAppId();
			String dictCode = dictItem.getDictCode();
			String key = buildKey(appId,dictCode);
		    
			//判断是否已经存在此类型key
			List<DictItem> dictItemEntry = dictItemsMap.get(key);
			if(dictItemEntry == null){
				 dictItemEntry = new ArrayList<DictItem>();
				 dictItemsMap.put(key, dictItemEntry);
			}
			DictItem item = new DictItem();
//			item.setAppId(appId);
//			item.setDictId(dictItem.getDictId());
//			item.setDictCode(dictItem.getDictCode());
//			item.setItemId(dictItem.getItemId());			
//			item.setItemCode(dictItem.getItemCode());
//			item.setItemName(dictItem.getItemName());			
//			item.setItemPid(dictItem.getItemPid());
//			item.setItemPcode(dictItem.getItemPcode());
//			item.setItemLevel(dictItem.getItemLevel());
//			item.setItemIsleaf(dictItem.getItemIsleaf());
			BeanUtilsExt.copyBean(dictItem, item);
			item.setStr01(dictItem.getItemCode() + " " + dictItem.getItemName());
			item.setStr06(dictItem.getCustomField1());
			item.setStr07(dictItem.getCustomField2());
			item.setStr08(dictItem.getCustomField3());
			item.setStr09(dictItem.getCustomField4());
			item.setStr10(dictItem.getCustomField5());
			dictItemEntry.add(item);
			dictItemsMap.put(key , dictItemEntry);
		}
		for(AssetBasicDict assetDict : allDict){
		   Dict dict = new Dict();
		   //赋值属性		   
		   BeanUtilsExt.copyBean(assetDict, dict);
		   String key = buildKey(assetDict.getAppId(),assetDict.getDictCode());
           List<DictItem> dictItems = dictItemsMap.get(key);
		   if(dictItems != null) {
			   dict.setDictItems(dictItems);
		   }
		   result.put(key, dict);
		}
		return result;
	}

	private String buildKey(String... key){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<key.length;i++){
			sb.append(key[i]);
			if(i<key.length-1){
				sb.append(":");
			}
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		DictItem item = new DictItem();
		item.setAppId("ddd");
		item.setItemPid(1L);
		DictItem item1 = new DictItem();
		BeanUtils.copyProperties(item1, item);
		System.out.println(item1.getAppId());
		System.out.println(item1.getItemPid());
	}
}
