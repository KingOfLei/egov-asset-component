/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Mar 05 11:25:11 CST 2017
 */
package com.bosssoft.egov.asset.dictionary.impl.mapper;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.dictionary.impl.entity.AssetBizAction;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.persistence.common.Mapper;

/**
 * 类说明: Mapper接口（ Mapper层） .
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-03-05   PJ　　　新建
 * </pre>
 * 
 */

public interface AssetBizActionMapper extends Mapper<AssetBizAction> {

	/**
	 * 
	 * 查询分页. `
	 * 
	 * @param searcher
	 *            查询条件
	 * @param pageable
	 *            分页信息
	 * @return AssetBizActionPage列表
	 * @throws BusinessException
	 *             自定义异常
	 *
	 */
	public Page<AssetBizAction> queryAssetBizActionPage(@Param("searcher") Searcher searcher,
			@Param("page") Page<AssetBizAction> page);

	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：获取资产业务行为实体
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param condition
	 * @return
	 *
	 * @date   创建时间：2017年9月1日
	 * @author 作者：wujian
	 */
	public AssetBizAction getAssetBizActionByCondition(@Param("condition") String condition);

}