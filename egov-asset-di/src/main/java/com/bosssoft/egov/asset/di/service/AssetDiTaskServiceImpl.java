
/**
 * 福建博思软件 1997-2018 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 14 22:30:43 CST 2018
 */

package com.bosssoft.egov.asset.di.service;

import java.util.List;
import com.bosssoft.egov.asset.di.entity.AssetDiTask;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bosssoft.egov.asset.di.service.AssetDiTaskService;
import com.bosssoft.egov.asset.di.mapper.AssetDiTaskMapper;


/**
 * 类说明: AssetDiTaskService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-01-14   xiedeshou　　　新建
 * </pre>
 */
@Service
public class AssetDiTaskServiceImpl implements AssetDiTaskService {

	private static Logger logger = LoggerFactory.getLogger(AssetDiTaskServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private AssetDiTaskMapper assetDiTaskMapper;
	
	/**
	 *
	 * @param assetDiTask
	 */
	public void addAssetDiTask(AssetDiTask assetDiTask)  {
		assetDiTaskMapper.insert(assetDiTask);
	}

	/**
	 *
	 * @param assetDiTask
	 */
	public void delAssetDiTask(AssetDiTask assetDiTask)  {
		assetDiTaskMapper.deleteByPrimaryKey(assetDiTask);
	}

	/**
	 *
	 * @param assetDiTask
	 */
	public void updateAssetDiTask(AssetDiTask assetDiTask)  {
		assetDiTaskMapper.updateByPrimaryKey(assetDiTask);
	}

	/**
	 *
	 * @param assetDiTask
	 * @retrun
	 */
	public List<AssetDiTask> getAssetDiTaskList(AssetDiTask assetDiTask)  {
		return assetDiTaskMapper.select(assetDiTask);
	}

	@Override
	public AssetDiTask getAssetDiTask(String taskId) {
		// TODO Auto-generated method stub
		return assetDiTaskMapper.selectByPrimaryKey(taskId);
	}
	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AssetDiTask> queryAssetDiTaskPage(Searcher searcher, Page<AssetDiTask> page)  {
		return null;
	}

}