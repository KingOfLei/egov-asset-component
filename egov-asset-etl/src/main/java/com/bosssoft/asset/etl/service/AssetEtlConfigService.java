/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Mon Sep 11 21:10:27 CST 2017
 */
package com.bosssoft.asset.etl.service;

import java.util.List;

import com.bosssoft.asset.etl.entity.AssetEtlConfig;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-09-11   xiedeshou　　　新建
 * </pre>
 */
public interface AssetEtlConfigService {

/**
	 * 增加.
	 * 
	 * @param assetEtlConfig 
	 */
	public void addAssetEtlConfig(AssetEtlConfig assetEtlConfig);

	/**
	 * 删除及相关信息.
	 * 
	 * @param assetEtlConfig 
	 */
	public void delAssetEtlConfig(AssetEtlConfig assetEtlConfig);

	/**
	 * 修改.
	 * 
	 * @param assetEtlConfig 
	 */
	public void updateAssetEtlConfig(AssetEtlConfig assetEtlConfig);

	/**
	 * 获取列表.
	 * 
	 */
	public List<AssetEtlConfig> getAssetEtlConfigList(AssetEtlConfig assetEtlConfig);


	/**
	 * 
	 * <p>函数名称： getAssetEtlConfig       </p>
	 * <p>功能说明： 获取单条配置信息, 多条异常
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param config
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
    public AssetEtlConfig getAssetEtlConfig(AssetEtlConfig config);

   	/**
	 * 获取分页.
	 * 
		 */
	public Page<AssetEtlConfig> queryAssetEtlConfigPage(Searcher searcher, Page<AssetEtlConfig> page);
			
    
}
 