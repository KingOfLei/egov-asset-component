/**
 * 福建博思软件 1997-2018 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 14 19:21:37 CST 2018
 */
package com.bosssoft.egov.asset.di.service;

import java.util.List;

import com.bosssoft.egov.asset.di.entity.AssetDiTaskConfig;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-01-14   xiedeshou　　　新建
 * </pre>
 */
public interface AssetDiTaskConfigService {

/**
	 * 增加.
	 * 
	 * @param assetDiTaskConfig 
	 */
	public void addAssetDiTaskConfig(AssetDiTaskConfig assetDiTaskConfig);

	/**
	 * 删除及相关信息.
	 * 
	 * @param assetDiTaskConfig 
	 */
	public void delAssetDiTaskConfig(AssetDiTaskConfig assetDiTaskConfig);

	/**
	 * 修改.
	 * 
	 * @param assetDiTaskConfig 
	 */
	public void updateAssetDiTaskConfig(AssetDiTaskConfig assetDiTaskConfig);

	/**
	 * 获取列表.
	 * 
	 */
	public List<AssetDiTaskConfig> getAssetDiTaskConfigList(AssetDiTaskConfig assetDiTaskConfig);

   	/**
	 * 获取分页.
	 * 
		 */
	public Page<AssetDiTaskConfig> queryAssetDiTaskConfigPage(Searcher searcher, Page<AssetDiTaskConfig> page);
			
    
}
 