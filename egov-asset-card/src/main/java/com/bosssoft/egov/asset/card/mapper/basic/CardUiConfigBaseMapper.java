package com.bosssoft.egov.asset.card.mapper.basic;

import java.util.List;

import com.bosssoft.egov.asset.card.entity.basic.CardUiConfigBaseEntity;
import com.bosssoft.platform.persistence.common.Mapper;

/** 
*
* @ClassName   类名：CardUiConfigBaseMapper 
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
public interface CardUiConfigBaseMapper extends Mapper<CardUiConfigBaseEntity>{
	
	/**
	 * 
	 * <p>函数名称：getUiByClassifyCode        </p>
	 * <p>功能说明：根据分类编码获取对应的ui表单
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param aimsUiConfig
	 * @return
	 *
	 * @date   创建时间：2016年11月30日
	 * @author 作者：cyx
	 */
	public List<CardUiConfigBaseEntity> getUiByClassifyCode(CardUiConfigBaseEntity aimsUiConfig);
}
