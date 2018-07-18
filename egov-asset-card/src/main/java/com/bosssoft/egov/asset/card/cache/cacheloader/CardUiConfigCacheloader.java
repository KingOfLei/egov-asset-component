package com.bosssoft.egov.asset.card.cache.cacheloader;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.card.entity.basic.CardUiConfigBaseEntity;
import com.bosssoft.egov.asset.card.mapper.basic.CardUiConfigBaseMapper;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.cache.api.CacheLoader;

/** 
*
* @ClassName   类名：CardUiConfigCacheloader 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CardUiConfigCacheloader implements CacheLoader{
	
	@Resource
    private CardUiConfigBaseMapper uiConfigMapper;
	
	@Override
	public Map<Serializable, Serializable> preLoad(Cache cache) {		
		return null;
	}

	@Override
	public Serializable get(Serializable key) {
		//key格式:ASSET_CARD_UI_CONFIG::typeCode:classifyCode
		String lookup = (String) key;
		String[] _data = StringUtilsExt.split(lookup, "::");
		CardUiConfigBaseEntity uiConfig = new AbstractCardUiConfigEntity();
		uiConfig.setUiConfigTable(_data[0]);
		uiConfig.setAssetTypeCode(_data[1]);
		uiConfig.setAssetClassifyCode(_data[2]);
		List<CardUiConfigBaseEntity> uiList = uiConfigMapper.getUiByClassifyCode(uiConfig);
		return (uiList != null && !uiList.isEmpty()) ? uiList.get(0) : null;
	}

	@Override
	public void put(Serializable key, Serializable value) {
		
	}

	@Override
	public Serializable remove(Serializable key) {
		// TODO Auto-generated method stub
		return null;
	}

	public class AbstractCardUiConfigEntity extends CardUiConfigBaseEntity{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4749742649473846763L;

		@Override
		public String getUiConfigTableName() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
