/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Wed Nov 30 11:47:21 CST 2016
 */
package com.bosssoft.egov.asset.card.service.basic;

import java.util.List;

import com.bosssoft.egov.asset.card.entity.basic.CardUiConfigBaseEntity;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-11-30   cyx　　　新建
 * </pre>
 */
public interface CardUiConfigBaseService {
			
    /**
     * 根据分类编码获取对应表单
     */
	public CardUiConfigBaseEntity getUiByClassifyCode(CardUiConfigBaseEntity UiConfigBaseEntity);
}
 