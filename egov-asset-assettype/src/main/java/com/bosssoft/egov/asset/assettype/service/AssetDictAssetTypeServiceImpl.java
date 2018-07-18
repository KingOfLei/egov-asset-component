package com.bosssoft.egov.asset.assettype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.assettype.entity.AssetDictAssetType;
import com.bosssoft.egov.asset.assettype.mapper.AssetDictAssetTypeMapper;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.persistence.entity.Example;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：AssetDictAssetTypeServiceImpl 
* @Description 功能说明：
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
@Service
public class AssetDictAssetTypeServiceImpl implements AssetDictAssetTypeService{

	@Autowired
	private AssetDictAssetTypeMapper typeMapper;
	
	@Override
	public void addAssetDictAssetType(AssetDictAssetType assetDictAssetType) {
		// TODO Auto-generated method stub
		typeMapper.insert(assetDictAssetType);
	}

	@Override
	public void delAssetDictAssetType(AssetDictAssetType assetDictAssetType) {
		typeMapper.deleteByPrimaryKey(assetDictAssetType);
		
	}

	@Override
	public void updateAssetDictAssetType(
			AssetDictAssetType assetDictAssetType) {
		typeMapper.updateByPrimaryKey(assetDictAssetType);
		
	}

	@Override
	public List<AssetDictAssetType> getAssetDictAssetTypeList(
			AssetDictAssetType assetDictAssetType) {
		// TODO Auto-generated method stub
		return typeMapper.select(assetDictAssetType);
	}

	@Override
	public Page<AssetDictAssetType> queryAssetDictAssetTypePage(
			Searcher searcher, Page<AssetDictAssetType> page) {
		// TODO Auto-generated method stub
		return typeMapper.queryAssetDictAssetTypePage(searcher, page);
	}

	@Override
	public List<AssetDictAssetType> getAssetDictAssetTypeByTypeCodes(
			String... typeCodes) {
		//获取对应type节点下的所有数据含本级 编码升序
		Example example = new Example(AssetDictAssetType.class);
		for(String type : typeCodes){
			example.or().andLike("assetTypeCode", type + "%");
		}		
		example.orderBy("assetTypeCode").asc();
		return typeMapper.selectByExample(example);
	}

	@Override
	public AssetDictAssetType getAssetDictAssetType(
			AssetDictAssetType assetDictAssetType) {
		// TODO Auto-generated method stub
		return typeMapper.selectOne(assetDictAssetType);
	}
}
