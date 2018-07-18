package com.bosssoft.egov.asset.codegen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.codegen.IdGen;
import com.bosssoft.egov.asset.codegen.entity.AssetCodegenConfig;
import com.bosssoft.egov.asset.codegen.entity.AssetCodegenRule;
import com.bosssoft.egov.asset.codegen.mapper.AssetCodegenConfigMapper;
import com.bosssoft.egov.asset.codegen.mapper.AssetCodegenRuleMapper;
import com.bosssoft.egov.asset.codegen.service.AssetCodegenConfigService;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.persistence.entity.Condition;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：AssetCodegenConfigServiceImpl 
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
@Transactional
public class AssetCodegenConfigServiceImpl implements AssetCodegenConfigService{

	@Autowired
	private AssetCodegenConfigMapper configMapper;
	
	@Autowired
	private AssetCodegenRuleMapper ruleMapper;
	
	@Override
	public void addAssetCodegenConfig(AssetCodegenConfig assetCodegenConfig) {		
		configMapper.insertSelective(assetCodegenConfig);
	}
	

	@Override
	public void addAssetCodegenConfig(AssetCodegenConfig assetCodegenConfig,
			List<AssetCodegenRule> codegenRules) {
		configMapper.insertSelective(assetCodegenConfig);
        int sxh = 0;
		for(AssetCodegenRule rule : codegenRules){
        	rule.setCfgId(assetCodegenConfig.getCfgId());
			rule.setStatus("1");
			rule.setId(IdGen.newWKID());
			rule.setSxh(++sxh);
			rule.setCreateTime(DateUtilsExt.getNowDateTime4());
			ruleMapper.insertSelective(rule);
        }
		
	}

	@Override
	public void delAssetCodegenConfig(AssetCodegenConfig assetCodegenConfig) {
		//删除主表
		configMapper.deleteByPrimaryKey(assetCodegenConfig);
		//删除明细信息
		Condition condition = new Condition(AssetCodegenRule.class);
		condition.createCriteria().andEqualTo("cfgId", assetCodegenConfig.getCfgId());
		ruleMapper.deleteByExample(condition);		
	}

	@Override
	public void updateAssetCodegenConfig(
			AssetCodegenConfig assetCodegenConfig) {
		configMapper.updateByPrimaryKey(assetCodegenConfig);
	}

	@Override
	public List<AssetCodegenConfig> getAssetCodegenConfigList(
			AssetCodegenConfig assetCodegenConfig) {		
		return configMapper.select(assetCodegenConfig);
	}

	@Override
	public Page<AssetCodegenConfig> queryAssetCodegenConfigPage(
			Searcher searcher, Page<AssetCodegenConfig> page) {
		return configMapper.queryAssetCodegenConfigPage(searcher, page);
	}


	@Override
	public AssetCodegenConfig getAssetCodegenConfig(Object orgId,
			String bizType) {
        return getAssetCodegenConfig(orgId, bizType, 1);         	
	}
	@Override
	public AssetCodegenConfig getAssetCodegenConfig(Object orgId,
			String bizType,int batchCount) {
		if(batchCount == 0 ) batchCount = 1;
		 //一进来先更新 最大值		
		int updRowCount = configMapper.updateMax(StringUtilsExt.convertNullToString(orgId), bizType,batchCount);		
        if(updRowCount == 0){ //未更新成功 说明没有数据
        	//正常情况下 此节点不会执行(/ □ \)
        	//不存在 根据默认此业务生成配置 （orgId + bizType 唯一）
        	AssetCodegenConfig config = createConfig(orgId, bizType);
        	configMapper.updateMax(StringUtilsExt.convertNullToString(orgId), bizType,batchCount);
            return config;
            
        }
        //一定有数据 因为前面更新时 有成功条数
        Condition condition = new Condition(AssetCodegenConfig.class);
        condition.createCriteria().andEqualTo("orgId", StringUtilsExt.convertNullToString(orgId))
        .andEqualTo("bizType", bizType)
        .andEqualTo("status", 1);
        List<AssetCodegenConfig> list = configMapper.selectByExample(condition);
        //已存在此单位此业务的配置信息
        AssetCodegenConfig newConfig = list.get(0);
       //获取编码规则
        Condition ruleCon = new Condition(AssetCodegenRule.class);
        ruleCon.createCriteria().andEqualTo("cfgId", newConfig.getCfgId())
        .andEqualTo("status", 1); 
		List<AssetCodegenRule> RuleList = ruleMapper.selectByExample(ruleCon);
		newConfig.setRules(RuleList);
        return newConfig;
	}

	@Override
	public AssetCodegenConfig getAssetCodegenConfig(
			AssetCodegenConfig assetCodegenConfig) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int updateAssetCodegenConfigSelective(
			AssetCodegenConfig assetCodegenConfig) {		
		return configMapper.updateByPrimaryKeySelective(assetCodegenConfig);
	}


	@Override
	public String getAssetCodegenConfigGenRule(Object orgId, String bizType) {
		  Condition condition = new Condition(AssetCodegenConfig.class);
	      condition.createCriteria().andEqualTo("orgId", StringUtilsExt.convertNullToString(orgId))
	      .andEqualTo("bizType", bizType)
	      .andEqualTo("status", 1); 
		  int count = configMapper.selectCountByExample(condition);
		  if(count == 0){ //不存在
			//不存在 根据默认此业务生成配置 （orgId + bizType 唯一）
			  createConfig(orgId, bizType);
		  }
		  String genRule = configMapper.getGenRule(StringUtilsExt.convertNullToString(orgId), bizType);
		  return genRule;
	}
	
	public boolean isGobel(String bizType){
		  AssetCodegenConfig config = getDefaultConfig(bizType);
		  return "G".equalsIgnoreCase(config.getGenType());
	}
	
	/**
	 * 
	 * <p>函数名称：  getDefaultConfig      </p>
	 * <p>功能说明： 获取默认的系统配置规则
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType
	 * @return
	 *
	 * @date   创建时间：2017年1月8日
	 * @author 作者：xds
	 */
	private AssetCodegenConfig getDefaultConfig(String bizType){
		AssetCodegenConfig config = new AssetCodegenConfig();
        config.setBizType(bizType);
        config.setOrgId("-");   
        config.setStatus("1");
        AssetCodegenConfig newConfig = new AssetCodegenConfig();
        List<AssetCodegenConfig> defaultList = configMapper.select(config);
        if(defaultList != null && defaultList.size() > 0){
        	newConfig = BeanUtilsExt.copyToNewBean(defaultList.get(0), AssetCodegenConfig.class);
        } else {
    		throw new IllegalArgumentException("业务类型 [" + bizType + "]编码规则不存在，请设置!");
        }
        
        return newConfig;
	}

	private AssetCodegenConfig createConfig(Object orgId, String bizType){
//    	AssetCodegenConfig config = new AssetCodegenConfig();
//        config.setBizType(bizType);
//        config.setOrgId("-");            
//        AssetCodegenConfig newConfig = new AssetCodegenConfig();
//        List<AssetCodegenConfig> defaultList = configMapper.select(config);
//        if(defaultList != null && defaultList.size() > 0){
//        	newConfig = BeanUtilsExt.copyToNewBean(defaultList.get(0), AssetCodegenConfig.class);
//        } else {
//    		throw new IllegalArgumentException("Not found bizType is " + bizType + " bizType! Please set it!");
//        }
        AssetCodegenConfig newConfig = getDefaultConfig(bizType);
        //判断是否是全局变量 全局的不按单位走 整个系统公用
        //附表也查询
        Condition ruleCon = new Condition(AssetCodegenRule.class);
        ruleCon.createCriteria().andEqualTo("cfgId", newConfig.getCfgId())
        .andEqualTo("status", 1);            
		List<AssetCodegenRule> defaultRule = ruleMapper.selectByExample(ruleCon);
        //重新赋值
		newConfig.setCfgId(IdGen.newWKID());
		//全局变量时对应orgId = 0123456789 外部以统一处理
        newConfig.setOrgId(StringUtilsExt.convertNullToString(orgId));
        newConfig.setBizType(bizType);
        newConfig.setCurNo(0);
        newConfig.setCurCode("1");
        newConfig.setCurReset("");
        newConfig.setCreator("系统自动生成");
        newConfig.setCreatorId("-");
        newConfig.setCreateTime(DateUtilsExt.getNowDateTime4());
        newConfig.setStatus("1");
        newConfig.setRemark("系统自动生成");        
        configMapper.insertSelective(newConfig);
        
        for(AssetCodegenRule rule : defaultRule){
        	rule.setCfgId(newConfig.getCfgId());
            rule.setId(IdGen.newWKID());
            rule.setCreator("系统自动生成");
            rule.setCreatorId("-");
            rule.setCreateTime(DateUtilsExt.getNowDateTime4());
            rule.setStatus("1");            
            ruleMapper.insertSelective(rule);
        }
        newConfig.setRules(defaultRule);
		return newConfig;
	}
}
