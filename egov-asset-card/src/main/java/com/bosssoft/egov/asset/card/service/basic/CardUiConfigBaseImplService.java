package com.bosssoft.egov.asset.card.service.basic;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.card.cache.CardCacheManager;
import com.bosssoft.egov.asset.card.entity.basic.CardUiConfigBaseEntity;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：CardUiConfigBaseImplService 
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
@Service
public class CardUiConfigBaseImplService implements CardUiConfigBaseService {

	@Override
	public CardUiConfigBaseEntity getUiByClassifyCode(
			CardUiConfigBaseEntity uiConfig) {
		//从缓存中获取 缓存无数据则从数据库中获取
		return CardCacheManager.getInstance().getCardUiConfig(uiConfig);
	}

}
