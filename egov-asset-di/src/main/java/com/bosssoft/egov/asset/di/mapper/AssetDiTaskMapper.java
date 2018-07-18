/**
 * 福建博思软件 1997-2018 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 14 22:24:55 CST 2018
 */
package com.bosssoft.egov.asset.di.mapper;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.di.entity.AssetDiTask;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.persistence.common.Mapper;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 * 类说明:  Mapper接口（ Mapper层） .
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-01-14   xiedeshou　　　新建
 * </pre>

 */

public interface AssetDiTaskMapper extends Mapper<AssetDiTask>{



/**
	 * 
	 * 查询分页.
	 *	`
	 * @param searcher 查询条件
	 * @param pageable 分页信息
	 * @return AssetDiTaskPage列表
	 * @throws BusinessException 自定义异常
	 *
	 */
	public Page<AssetDiTask> queryAssetDiTaskPage(@Param("searcher")Searcher searcher,@Param("page")Page<AssetDiTask> page);
			


}