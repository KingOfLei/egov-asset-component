package com.bosssoft.egov.asset.dispatch.service.impl;

/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 22 17:22:00 CST 2017
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.dispatch.entity.AssetDispatchTask;
import com.bosssoft.egov.asset.dispatch.mapper.AssetDispatchTaskMapper;
import com.bosssoft.egov.asset.dispatch.service.AssetDispatchTaskService;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;


/**
 * 类说明: AssetDispatchTaskService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-22   Administrator　　　新建
 * </pre>
 */
@Service
public class AssetDispatchTaskServiceImpl implements AssetDispatchTaskService {

	private static Logger logger = LoggerFactory.getLogger(AssetDispatchTaskServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private AssetDispatchTaskMapper assetDispatchTaskMapper;
	
	/**
	 *
	 * @param assetDispatchTask
	 */
	public void addAssetDispatchTask(AssetDispatchTask assetDispatchTask)  {
		assetDispatchTaskMapper.insert(assetDispatchTask);
	}

	/**
	 *
	 * @param assetDispatchTask
	 */
	public void delAssetDispatchTask(AssetDispatchTask assetDispatchTask)  {
		assetDispatchTaskMapper.deleteByPrimaryKey(assetDispatchTask);
	}

	/**
	 *
	 * @param assetDispatchTask
	 */
	public void updateAssetDispatchTask(AssetDispatchTask assetDispatchTask)  {
		assetDispatchTaskMapper.updateByPrimaryKey(assetDispatchTask);
	}

	/**
	 *
	 * @param assetDispatchTask
	 * @retrun
	 */
	public List<AssetDispatchTask> getAssetDispatchTaskList(AssetDispatchTask assetDispatchTask)  {
		return assetDispatchTaskMapper.select(assetDispatchTask);
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AssetDispatchTask> queryAssetDispatchTaskPage(Searcher searcher, Page<AssetDispatchTask> page)  {
		return assetDispatchTaskMapper.queryAssetDispatchTaskPage(searcher, page);
	}

	@Override
	public AssetDispatchTask getAssetDispatchTask(
			AssetDispatchTask assetDispatchTask) {
		// TODO Auto-generated method stub
		return assetDispatchTaskMapper.selectOne(assetDispatchTask);
	}

	@Override
	public AssetDispatchTask getAssetDispatchTaskById(
			AssetDispatchTask assetDispatchTask) {
		// TODO Auto-generated method stub
		return assetDispatchTaskMapper.selectByPrimaryKey(assetDispatchTask);
	}

	@Override
	public AssetDispatchTask getAssetDispatchTaskById(Long taskId) {
		// TODO Auto-generated method stub
		return assetDispatchTaskMapper.selectByPrimaryKey(taskId);
	}


}