/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Wed Dec 07 08:28:12 CST 2016
 */
package com.bosssoft.egov.asset.codegen.service;

import java.util.List;

import com.bosssoft.egov.asset.codegen.entity.AssetCodegenRule;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-07   xiedeshou　　　新建
 * </pre>
 */
public interface AssetCodegenRuleService {

/**
	 * 增加.
	 * 
	 * @param assetCodegenRule 
	 */
	public void addAssetCodegenRule(AssetCodegenRule assetCodegenRule);

	/**
	 * 删除及相关信息.
	 * 
	 * @param assetCodegenRule 
	 */
	public void delAssetCodegenRule(AssetCodegenRule assetCodegenRule);

	/**
	 * 修改.
	 * 
	 * @param assetCodegenRule 
	 */
	public void updateAssetCodegenRule(AssetCodegenRule assetCodegenRule);

	/**
	 * 获取列表.
	 * 
	 */
	public List<AssetCodegenRule> getAssetCodegenRuleList(AssetCodegenRule assetCodegenRule);

	/**
	 * 
	 * <p>函数名称：  getAssetCodegenRuleList      </p>
	 * <p>功能说明： 根据配置获取其规则列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param cfgId
	 * @return
	 *
	 * @date   创建时间：2016年12月14日
	 * @author 作者：xds
	 */
    public List<AssetCodegenRule> getAssetCodegenRuleList(Object cfgId);


   	/**
	 * 获取分页.
	 * 
		 */
	public Page<AssetCodegenRule> queryAssetCodegenRulePage(Searcher searcher, Page<AssetCodegenRule> page);
			
    
}
 