package com.bosssoft.egov.asset.di.mapper;

import java.util.List;

import com.bosssoft.egov.asset.di.entity.AssetDiTaskDataSource;
import com.bosssoft.platform.persistence.common.BaseMapper;

/**
 * 数据源配置mapper接口
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-03-27   BIN　　　新建
 * </pre>
 */
public interface AssetDiTaskDataSourceMapper extends BaseMapper<AssetDiTaskDataSource> {

	/**
	 * 获取数据源配置列表
	 * @return
	 */
	public List<AssetDiTaskDataSource> getDataSourceList();
}
