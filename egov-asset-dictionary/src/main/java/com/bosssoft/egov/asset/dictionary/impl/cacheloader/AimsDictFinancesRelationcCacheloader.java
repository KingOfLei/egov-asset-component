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
import com.bosssoft.egov.asset.dictionary.impl.entity.AimsDictFinancesRelation;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AimsDictFinancesRelateMapper;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;
import com.bosssoft.platform.persistence.entity.Example;

/** 
*
* @ClassName   类名：AimsDictFinancesRelationcCacheloader 
* @Description 功能说明： 所属财政关系
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
public class AimsDictFinancesRelationcCacheloader implements CacheLoader{
	@Resource
	private AimsDictFinancesRelateMapper mapper;

	
	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {
		cache.clear();
        return getFinanRootNode();
	}
	
	private Map<Serializable, Serializable> getFinanRootNode(){
		//默认先加载第一级
		Example example = new Example(AimsDictFinancesRelation.class);
		example.createCriteria().andIsNull("financesRelationPid").andEqualTo("financesRelationStatus", 1);	
		//预留数据权限
//		example.createCriteria().andCondition("");
		example.orderBy("financesRelationCode").asc();
		List<AimsDictFinancesRelation> dictFinaItem = mapper.selectByExample(example);	
		AimsDictFinancesRelation dictFina = new AimsDictFinancesRelation();
		dictFina.setFinancesRelationId(937599045019650L);		                   
		dictFina.setFinancesRelationCode("-");
		dictFina.setFinancesRelationName("所属财政关系");
		dictFina.setFinancesRelationIsleaf(0);
		dictFina.setFinancesRelationRank(1);
		dictFina.setFinancesRelationStatus("1");		
		return buildDictionary("egov-asset-aims", "",dictFina,dictFinaItem);
	}

	@Override
	public Serializable get(Serializable key) {
		 //_ASSET_DICT_IND_::APP_ID::ID
		String lookupKey = (String)key;
		String flag = StringUtilsExt.substringAfter(lookupKey, "::");
        String appId = StringUtilsExt.substringBefore(flag, "::");
        String indId = StringUtilsExt.substringAfter(flag, "::");
        //根据id 查找
        if(StringUtilsExt.isEmpty(indId)){
        	return getFinanRootNode().get(lookupKey);
        }
        AimsDictFinancesRelation dict = new AimsDictFinancesRelation();
        dict.setFinancesRelationId(NumberUtilsExt.toLong(indId));        
        AimsDictFinancesRelation fina = mapper.selectOne(dict);        
        //根据id 查找其下级
        Example example = new Example(AimsDictFinancesRelation.class);
        example.createCriteria().andEqualTo("financesRelationPid", indId);
        example.orderBy("financesRelationCode").asc();
        List<AimsDictFinancesRelation> dictFinaItem = mapper.selectByExample(example);
        Map<Serializable,Serializable> result = buildDictionary(appId, indId, fina, dictFinaItem);
		return result.get(lookupKey);
	}

	@Override
	public void put(Serializable key, Serializable value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable remove(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Serializable, Serializable> buildDictionary(String appId, String id,AimsDictFinancesRelation dictFinancesRelation,List<AimsDictFinancesRelation> dictFinancesRelationItem){
		Map<Serializable,Serializable> result = new HashMap<Serializable,Serializable>();
        if(dictFinancesRelation == null) return result;
		Dict dict = new Dict();
        dict.setAppId(appId);
        dict.setDictId(dictFinancesRelation.getFinancesRelationId());
        dict.setDictCode(dictFinancesRelation.getFinancesRelationCode());
        dict.setDictName(dictFinancesRelation.getFinancesRelationName());
        dict.setStr1(StringUtilsExt.convertNullToString(dictFinancesRelation.getFinancesRelationPid()));
        dict.setStr2(dictFinancesRelation.getFinancesRelationPcode());
        dict.setStr3(dictFinancesRelation.getFinancesRelationPname());        
        dict.setIsbuiltin("0");
        dict.setDictId(dictFinancesRelation.getFinancesRelationId());
        List<DictItem> dictItemsMap = new ArrayList<DictItem>();
        for(AimsDictFinancesRelation items : dictFinancesRelationItem){
        	DictItem item = new DictItem();
        	item.setAppId(appId);
        	item.setDictId(dict.getDictId());
        	item.setDictCode(dict.getDictCode());        	
        	item.setItemId(items.getFinancesRelationId());
        	item.setItemCode(items.getFinancesRelationCode());
        	item.setItemName(items.getFinancesRelationCode() + " " + items.getFinancesRelationName());
        	item.setIsenable(items.getFinancesRelationStatus());
        	item.setItemIsleaf(items.getFinancesRelationIsleaf());
        	item.setItemLevel(items.getFinancesRelationRank());
        	item.setItemPid(items.getFinancesRelationPid());
        	item.setItemPcode(items.getFinancesRelationPcode());
        	item.setStr01(items.getFinancesRelationName());
        	item.setCreateDate(items.getCreatedate());
        	item.setCreator(items.getCreator());
        	dictItemsMap.add(item);        	
        }
        dict.setDictItems(dictItemsMap);
        String key = AimsDictHelper.buildKeyByFinan(appId,id);
        result.put(key, dict);		
        return result;
	}	
}
