package com.bosssoft.egov.asset.dictionary.impl.cacheloader;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBizAction;
import com.bosssoft.egov.asset.dictionary.impl.mapper.AssetBizActionMapper;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;

/**
 *
 * @ClassName 类名：AssetDictCacheLoader
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月11日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年11月11日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class AssetBizActionCacheLoader implements CacheLoader {

	@Resource
	private AssetBizActionMapper assetBizActionMapper;

	@Override
	public Serializable get(Serializable key) {

		String lookupKey = (String) key;

		StringBuffer buffer = new StringBuffer();
		buffer.append(" biz_action_code ='" + lookupKey + "'");
		AssetBizAction assetBizAction = assetBizActionMapper.getAssetBizActionByCondition(buffer.toString());

		Map<String, AssetBizAction> result = new HashMap<String, AssetBizAction>();

		if (assetBizAction != null) {
			result.put(lookupKey, assetBizAction);
		}

		return result.get(lookupKey);
	}

	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {
		List<AssetBizAction> assetBizActionsList = assetBizActionMapper.selectAll();
		Map<Serializable, Serializable> result = new HashMap<Serializable, Serializable>();
		for (AssetBizAction assetBizAction : assetBizActionsList) {
			result.put(assetBizAction.getBizActionCode(), assetBizAction);
		}
		return result;
	}

	@Override
	public void put(Serializable arg0, Serializable arg1) {

	}

	@Override
	public Serializable remove(Serializable arg0) {
		return null;
	}


}
