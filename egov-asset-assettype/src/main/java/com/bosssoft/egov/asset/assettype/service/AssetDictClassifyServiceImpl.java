package com.bosssoft.egov.asset.assettype.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.assettype.entity.AssetDictClassify;
import com.bosssoft.egov.asset.assettype.mapper.AssetDictClassifyMapper;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：AssetDictClassifyServiceImpl 
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
public class AssetDictClassifyServiceImpl implements AssetDictClassifyService{

	@Autowired
    private AssetDictClassifyMapper classifyMapper;
	@Override
	public void addAssetDictAssetClassify(
			AssetDictClassify assetDictAssetClassify) {
		classifyMapper.insertSelective(assetDictAssetClassify);
	}

	@Override
	public void delAssetDictAssetClassify(
			AssetDictClassify assetDictAssetClassify) {
		classifyMapper.deleteByPrimaryKey(assetDictAssetClassify);
		
	}

	@Override
	public void updateAssetDictAssetClassify(
			AssetDictClassify assetDictAssetClassify) {
		
		classifyMapper.updateByPrimaryKey(assetDictAssetClassify);
	}

	@Override
	public List<AssetDictClassify> getAssetDictAssetClassifyList(
			AssetDictClassify assetDictAssetClassify) {
		
		return classifyMapper.select(assetDictAssetClassify);
	}

	@Override
	public Page<AssetDictClassify> queryAssetDictAssetClassifyPage(
			Searcher searcher, Page<AssetDictClassify> page) {		
		return classifyMapper.queryAssetDictClassifyPage(searcher, page);
	}

	@Override
	public AssetDictClassify getAssetDictAssetClassify(
			AssetDictClassify assetDictAssetClassify) {
		// TODO Auto-generated method stub
		return classifyMapper.selectOne(assetDictAssetClassify);
	}

	@Override
	public List<AssetDictClassify> queryAssertClassifyBySqlCondition(
			String sql,Map<String, String> params) {
		// TODO Auto-generated method stub
		return classifyMapper.queryAssertClassifyBySqlCondition(sql, params);
	}

	@Override
	public List<AssetDictClassify> getAssetDictAssetClassify(String tableName,
			String typeCode, String classifyCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM " + tableName + " ");
		sql.append(" WHERE ASSET_TYPE_CODE = #{params.typeCode} ");
		sql.append(" AND ASSET_CLASSIFY_CODE = #{params.classifyCode} ");
		Map<String,String> params = new HashMap<String, String>();
		params.put("typeCode", typeCode);
		params.put("classifyCode", classifyCode);				
		return classifyMapper.queryAssertClassifyBySqlCondition(sql.toString(), params);
	}

}
