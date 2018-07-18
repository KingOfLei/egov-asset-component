/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Fri Sep 22 23:56:01 CST 2017
 */
package com.bosssoft.asset.etl.service;

import java.util.List;

import com.bosssoft.asset.etl.entity.AssetEtlExportTask;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * 转换导出任务表Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-09-22   xiedeshou　　　新建
 * </pre>
 */
public interface AssetEtlExportTaskService {

/**
	 * 增加转换导出任务表.
	 * 
	 * @param assetEtlExportTask 转换导出任务表
	 */
	public void addAssetEtlExportTask(AssetEtlExportTask assetEtlExportTask);

	/**
	 * 删除转换导出任务表及相关信息.
	 * 
	 * @param assetEtlExportTask 转换导出任务表
	 */
	public void delAssetEtlExportTask(AssetEtlExportTask assetEtlExportTask);

	/**
	 * 修改转换导出任务表.
	 * 
	 * @param assetEtlExportTask 转换导出任务表
	 */
	public void updateAssetEtlExportTask(AssetEtlExportTask assetEtlExportTask);

	/**
	 * 获取转换导出任务表列表.
	 * 
	 */
	public List<AssetEtlExportTask> getAssetEtlExportTaskList(AssetEtlExportTask assetEtlExportTask);

	/**
	 * 
	 * <p>函数名称： getAssetEtlExportTask       </p>
	 * <p>功能说明： 根据实体获取对象
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetEtlExportTask
	 * @return
	 *
	 * @date   创建时间：2017年9月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
    public AssetEtlExportTask getAssetEtlExportTask(Long id);


   	/**
	 * 获取转换导出任务表分页.
	 * 
		 */
	public Page<AssetEtlExportTask> queryAssetEtlExportTaskPage(Searcher searcher, Page<AssetEtlExportTask> page);

	/**
	 * <p>功能说明：删除过期任务
	 * </p>
	 * <p>参数说明：
	 * @param time 时间点
	 * @return
	 * </p>
	 *
	 * @date	创建时间：2017年12月6日
	 * @author	作者：chenzhibin
	 */
	public int deleteTimeoutExportTask(String time);

	/**
	 * <p>功能说明：清除等待或正在执行的任务
	 * </p>
	 * <p>参数说明：
	 * @return
	 * </p>
	 *
	 * @date	创建时间：2017年12月7日
	 * @author	作者：chenzhibin
	 */
	public int clearWaitOrExecutingTask();
    
}
 