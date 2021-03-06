
/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Sep 12 20:34:58 CST 2017
 */
package com.bosssoft.asset.etl.comp.service;

import java.util.List;
import com.bosssoft.asset.etl.comp.entity.AssetEtlCompConfigDb;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bosssoft.asset.etl.comp.service.AssetEtlCompConfigDbService;
import com.bosssoft.asset.etl.comp.mapper.AssetEtlCompConfigDbMapper;


/**
 * 类说明: AssetEtlCompConfigDbService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-09-12   xiedeshou　　　新建
 * </pre>
 */
@Service
public class AssetEtlCompConfigDbServiceImpl implements AssetEtlCompConfigDbService {

	private static Logger logger = LoggerFactory.getLogger(AssetEtlCompConfigDbServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private AssetEtlCompConfigDbMapper assetEtlCompConfigDbMapper;
	
	/**
	 *
	 * @param assetEtlCompConfigDb
	 */
	public void addAssetEtlCompConfigDb(AssetEtlCompConfigDb assetEtlCompConfigDb)  {
		assetEtlCompConfigDbMapper.insert(assetEtlCompConfigDb);
	}

	/**
	 *
	 * @param assetEtlCompConfigDb
	 */
	public void delAssetEtlCompConfigDb(AssetEtlCompConfigDb assetEtlCompConfigDb)  {
		assetEtlCompConfigDbMapper.deleteByPrimaryKey(assetEtlCompConfigDb);
	}

	/**
	 *
	 * @param assetEtlCompConfigDb
	 */
	public void updateAssetEtlCompConfigDb(AssetEtlCompConfigDb assetEtlCompConfigDb)  {
		assetEtlCompConfigDbMapper.updateByPrimaryKey(assetEtlCompConfigDb);
	}

	/**
	 *
	 * @param assetEtlCompConfigDb
	 * @retrun
	 */
	public List<AssetEtlCompConfigDb> getAssetEtlCompConfigDbList(AssetEtlCompConfigDb assetEtlCompConfigDb)  {
		return assetEtlCompConfigDbMapper.select(assetEtlCompConfigDb);
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AssetEtlCompConfigDb> queryAssetEtlCompConfigDbPage(Searcher searcher, Page<AssetEtlCompConfigDb> page)  {
		return assetEtlCompConfigDbMapper.queryAssetEtlCompConfigDbPage(searcher, page);
	}

	@Override
	public AssetEtlCompConfigDb getAssetEtlCompConfigDb(
			AssetEtlCompConfigDb assetEtlCompConfigDb) {
		// TODO Auto-generated method stub
		return assetEtlCompConfigDbMapper.selectOne(assetEtlCompConfigDb);
	}


}