package com.bosssoft.egov.asset.di.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.di.entity.AssetDiTaskDataSource;
import com.bosssoft.egov.asset.di.mapper.AssetDiTaskDataSourceMapper;
/**
 * 数据源配置接口实现类
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-03-27   BIN　　　新建
 * </pre>
 */
@Service
public class AssetDiTaskDataSourceServiceImpl implements AssetDiTaskDataSourceService {

	@Autowired
	private AssetDiTaskDataSourceMapper dataSourceMapper;
	
	@Override
	public List<AssetDiTaskDataSource> getDataSourceList() {
		return dataSourceMapper.getDataSourceList();
	}

	@Override
	public AssetDiTaskDataSource getDataSourceById(String dbId) {
		AssetDiTaskDataSource dataSource = new AssetDiTaskDataSource();
		dataSource.setDbId(dbId);
		return dataSourceMapper.selectByPrimaryKey(dataSource);
	}

}
