package com.bosssoft.egov.asset.assettype.cacheloader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.assettype.api.AssetTypeAndClassifyManager;
import com.bosssoft.egov.asset.assettype.entity.AssetDictAssetType;
import com.bosssoft.egov.asset.assettype.entity.AssetDictClassify;
import com.bosssoft.egov.asset.assettype.mapper.AssetDictAssetTypeMapper;
import com.bosssoft.egov.asset.assettype.mapper.AssetDictClassifyMapper;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.dictionary.api.Dict;
import com.bosssoft.egov.asset.dictionary.api.DictItem;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;
import com.bosssoft.platform.persistence.entity.Example;

/** 
*
* @ClassName   类名：AssetTypeCacheLoader 
* @Description 功能说明：资产类型及分类CacheLoader类
* <p>  id 拼凑规则
*      
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
public class AssetTypeCacheLoader implements CacheLoader{

	@Resource
	private AssetDictAssetTypeMapper typeMapper;
	
	@Resource
	private AssetDictClassifyMapper classifyMapper;
	
	//1. 分别存储 资产类型 及 资产类型对应的分类
	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {		       
		return getAssetType();
	}
	
	private Map<Serializable,Serializable> getAssetType(){
		 //实例化时 预加载特定参数
		AssetDictAssetType rootType = new AssetDictAssetType();
		rootType.setAssetTypeCode("-");
		rootType.setAssetTypeName("政府资产");
		rootType.setIsleaf(0);
		rootType.setRank(0);
		rootType.setStatus("0");
		Dict dict = new Dict();
	    dict.setDictId(1107980543404034L);
	    dict.setDictCode("-");
	    dict.setDictName("政府资产");
	    dict.setIsbuiltin("1");
	    //获取所有资产类型 按资产类型编码升序
		Example example = new Example(AssetDictAssetType.class);
		example.createCriteria().andEqualTo("status", "1");		
		example.orderBy("assetTypeCode").asc();	    
		List<AssetDictAssetType> allType = typeMapper.selectByExample(example);
		List<DictItem> dictItem = new ArrayList<DictItem>();
		Map<Long,String> targetMap = new HashMap<Long,String>();
        for(AssetDictAssetType type: allType){
        	DictItem item = new DictItem();        	
        	item.setDictId(dict.getDictId());
        	item.setDictCode(dict.getDictCode());
        	item.setDictName(dict.getDictName());
        	item.setItemId(type.getAssetTypeId());
        	item.setItemCode(type.getAssetTypeCode());
        	item.setItemName(type.getAssetTypeName());
        	item.setItemPid(type.getAssetTypePid());
        	item.setItemPcode(type.getAssetTypePcode());
        	item.setItemIsleaf(0);
        	item.setItemLevel(type.getRank());
        	item.setIsenable(type.getStatus());
            //获取当前节点的类型的分类表名
        	//存放表名字典
        	if(StringUtilsExt.isNotEmpty(type.getStr01())){
        		item.setStr01(type.getStr01());
        		targetMap.put(item.getItemId(), type.getStr01());
        	} else {
        		item.setStr01(targetMap.get(item.getItemPid()));
        	}
        	//区别字段 转换查询都用这个字段查询
        	item.setStr02("type::" + item.getItemId() + "_!@_target::" + StringUtilsExt.convertNullToString(item.getStr01()));
        	item.setStr03(type.getAssetTypeName());
        	item.setStr04("");
        	item.setStr05("");  
        	item.setStr06(type.getStr02());
        	item.setStr07("type");
        	item.setStr08(type.getAssetTypeCode());//与类型区分
        	item.setStr09("");//classify 值
        	
        	item.setStr10(type.getAssetTypeTitle());
        	item.setStr20("");
        	
        	//折旧
        	item.setStr11("");
        	item.setStr12("");
        	item.setStr13("");
        	item.setStr14("");
        	
        	//16 拼接类型+分类
        	item.setStr16(type.getAssetTypeCode());
        	dictItem.add(item);
        }
        dict.setDictItems(dictItem);
		Map<Serializable,Serializable> result = new HashMap<Serializable,Serializable>();		
		result.put(AssetTypeAndClassifyManager.ASSET_TYPE_KEY, dict);
		return result;
	}

	@Override
	public Serializable get(Serializable key) {
		String lookupKey = (String) key;
		//获取资产类型
		if(AssetTypeAndClassifyManager.ASSET_TYPE_KEY.equalsIgnoreCase(lookupKey)){
			return getAssetType().get(lookupKey);
		}
		//type::913268368812033_!@#_target::AIMS_DICT_ASSET_GD
		//"::ASSET_CLASSIFY_KEY::" + 913268368812033
		String _key = StringUtilsExt.remove(lookupKey, AssetTypeAndClassifyManager.ASSET_CLASSIFY_KEY);
		String typeIdStr = StringUtilsExt.substringBefore(_key, "_!@_");
		String tableStr = StringUtilsExt.substringAfter(_key, "_!@_");
		String typeId = StringUtilsExt.removeStart(typeIdStr, "type::");
		String table = StringUtilsExt.removeStart(tableStr, "target::");
		//获取此ID信息
		AssetDictAssetType type = new AssetDictAssetType();
		type.setAssetTypeId(NumberUtilsExt.toLong(typeId));
		AssetDictAssetType assetType = typeMapper.selectOne(type);
		if(assetType == null) return null;//无此分类
		//此类型下所有分类信息
		List<AssetDictClassify> classifyItem = classifyMapper.queryAssetClassifyMapper(typeId,StringUtilsExt.isEmpty(table) ? AssetTypeAndClassifyManager.ASSET_CLASSIFY_TABLE : table);
		//资产分类
		Map<Serializable,Serializable> result = buildDictionary(lookupKey,assetType, classifyItem);
		return result.get(lookupKey);
	}

	@Override
	public void put(Serializable key, Serializable value) {
		
	}

	@Override
	public Serializable remove(Serializable key) {
		
		return null;
	}
	public Map<Serializable, Serializable> buildDictionary(String lookupKey,AssetDictAssetType dictType,List<AssetDictClassify> classifyItem){
		Map<Serializable,Serializable> result = new HashMap<Serializable,Serializable>();
        Dict dict = new Dict();
        dict.setDictId(dictType.getAssetTypeId());
        dict.setDictCode(dictType.getAssetTypeCode());
        dict.setDictName(dictType.getAssetTypeName());
        dict.setStr1(dictType.getAssetTypePid()+"");
        dict.setStr2(dictType.getAssetTypePcode());
        dict.setStr3(dictType.getAssetTypeName());
        dict.setStr4("");
        dict.setStr5("");
        List<DictItem> dictItemsMap = new ArrayList<DictItem>();

        for(AssetDictClassify classify: classifyItem){
        	DictItem item = new DictItem();
        	item.setDictId(dict.getDictId());
        	item.setDictCode(dict.getDictCode());
        	item.setDictName(dict.getDictName());
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
        	item.setStr02(item.getDictId()+"");
        	item.setStr03(classify.getAssetClassifyCode() + " " + classify.getAssetClassifyName());
        	item.setStr04(classify.getUnit());//计量单位
        	item.setStr05(classify.getExpectedDate());//使用年限
        	item.setStr06(classify.getAssetClassifyCode());//分类编码 为了下拉树组合用
        	item.setStr07("classify");
        	
        	//加以辨别 加上大类
        	item.setStr08(classify.getAssetTypeCode());
        	item.setStr09(classify.getAssetClassifyCode());
        	//加上类型长名称
        	item.setStr10(dictType.getAssetTypeTitle());
        	//分类长名称
        	item.setStr20(classify.getAssetClassifyTitle());
        	
        	//分类折旧类型
        	item.setStr11(classify.getDeprCode());
        	item.setStr12(classify.getDeprName());
        	item.setStr13(classify.getDeprModeCode());
        	item.setStr14(classify.getDeprModeName());
        	
        	//16 拼接类型+分类
        	item.setStr16(classify.getAssetTypeCode() + "-" + classify.getAssetClassifyCode());
        	dictItemsMap.add(item);
        }
        dict.setDictItems(dictItemsMap);
        ////type::913268368812033_!@#_target::AIMS_DICT_ASSET_GD
        result.put(lookupKey, dict);
		return result;
	}
}
