package com.bosssoft.egov.asset.di.service;

import java.util.List;

import com.bosssoft.egov.asset.di.entity.AssetDiTaskDataSource;

/**
 * 数据源配置接口
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-03-27   BIN　　　新建
 * </pre>
 */
public interface AssetDiTaskDataSourceService {

	/**
	 * 获取数据源配置列表
	 * @return
	 */
	public List<AssetDiTaskDataSource> getDataSourceList();

	/**
	 * 根据ID获取数据源配置
	 * @param dbId
	 * @return
	 */
	public AssetDiTaskDataSource getDataSourceById(String dbId);
}
