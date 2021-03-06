/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 22 17:20:52 CST 2017
 */
package com.bosssoft.egov.asset.dispatch.service;

import java.util.List;

import com.bosssoft.egov.asset.dispatch.entity.AssetDispatchTask;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * 调度-调度表 存放具体作业关联定时配置信息Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-22   Administrator　　　新建
 * </pre>
 */
public interface AssetDispatchTaskService {

/**
	 * 增加调度-调度表 存放具体作业关联定时配置信息.
	 * 
	 * @param assetDispatchTask 调度-调度表 存放具体作业关联定时配置信息
	 */
	public void addAssetDispatchTask(AssetDispatchTask assetDispatchTask);

	/**
	 * 删除调度-调度表 存放具体作业关联定时配置信息及相关信息.
	 * 
	 * @param assetDispatchTask 调度-调度表 存放具体作业关联定时配置信息
	 */
	public void delAssetDispatchTask(AssetDispatchTask assetDispatchTask);

	/**
	 * 修改调度-调度表 存放具体作业关联定时配置信息.
	 * 
	 * @param assetDispatchTask 调度-调度表 存放具体作业关联定时配置信息
	 */
	public void updateAssetDispatchTask(AssetDispatchTask assetDispatchTask);

	/**
	 * 获取调度-调度表 存放具体作业关联定时配置信息列表.
	 * 
	 */
	public List<AssetDispatchTask> getAssetDispatchTaskList(AssetDispatchTask assetDispatchTask);

    public AssetDispatchTask getAssetDispatchTask(AssetDispatchTask assetDispatchTask);
    
    public AssetDispatchTask getAssetDispatchTaskById(AssetDispatchTask assetDispatchTask);
    
    public AssetDispatchTask getAssetDispatchTaskById(Long taskId);


   	/**
	 * 获取调度-调度表 存放具体作业关联定时配置信息分页.
	 * 
		 */
	public Page<AssetDispatchTask> queryAssetDispatchTaskPage(Searcher searcher, Page<AssetDispatchTask> page);
			
    
}
 