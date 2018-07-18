package com.bosssoft.egov.asset.codegen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.codegen.entity.AssetCodegenRule;
import com.bosssoft.egov.asset.codegen.mapper.AssetCodegenRuleMapper;
import com.bosssoft.egov.asset.codegen.service.AssetCodegenRuleService;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.persistence.entity.Condition;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：AssetCodegenRuleServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service
public class AssetCodegenRuleServiceImpl implements AssetCodegenRuleService{
	
	@Autowired
	private AssetCodegenRuleMapper ruleMapper;
	
	@Override
	public void addAssetCodegenRule(AssetCodegenRule assetCodegenRule) {
         		
	}

	@Override
	public void delAssetCodegenRule(AssetCodegenRule assetCodegenRule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAssetCodegenRule(AssetCodegenRule assetCodegenRule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AssetCodegenRule> getAssetCodegenRuleList(
			AssetCodegenRule assetCodegenRule) {		
		return null;
	}


	@Override
	public List<AssetCodegenRule> getAssetCodegenRuleList(Object cfgId) {
		Condition condtion = new Condition(AssetCodegenRule.class);
		condtion.createCriteria().andEqualTo("cfgId", cfgId);
		return ruleMapper.selectByExample(condtion);
	}
	@Override
	public Page<AssetCodegenRule> queryAssetCodegenRulePage(Searcher searcher,
			Page<AssetCodegenRule> page) {
		// TODO Auto-generated method stub
		return null;
	}

}
