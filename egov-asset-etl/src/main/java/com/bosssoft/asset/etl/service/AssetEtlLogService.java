/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Fri Sep 22 23:56:01 CST 2017
 */
package com.bosssoft.asset.etl.service;

import java.util.List;

import com.bosssoft.asset.etl.entity.AssetEtlLog;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * 任务转换操作日志Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-09-22   xiedeshou　　　新建
 * </pre>
 */
public interface AssetEtlLogService {

/**
	 * 增加任务转换操作日志.
	 * 
	 * @param assetEtlLog 任务转换操作日志
	 */
	public void addAssetEtlLog(AssetEtlLog assetEtlLog);

	/**
	 * 删除任务转换操作日志及相关信息.
	 * 
	 * @param assetEtlLog 任务转换操作日志
	 */
	public void delAssetEtlLog(AssetEtlLog assetEtlLog);

	/**
	 * 修改任务转换操作日志.
	 * 
	 * @param assetEtlLog 任务转换操作日志
	 */
	public void updateAssetEtlLog(AssetEtlLog assetEtlLog);

	/**
	 * 获取任务转换操作日志列表.
	 * 
	 */
	public List<AssetEtlLog> getAssetEtlLogList(AssetEtlLog assetEtlLog);




   	/**
	 * 获取任务转换操作日志分页.
	 * 
		 */
	public Page<AssetEtlLog> queryAssetEtlLogPage(Searcher searcher, Page<AssetEtlLog> page);
			
    
}
 