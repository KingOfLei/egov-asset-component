/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Thu Dec 08 14:14:45 CST 2016
 */
package com.bosssoft.egov.asset.activiti.mapper;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.activiti.entity.ActReProcdef;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.persistence.common.Mapper;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 * 类说明: Mapper接口（ Mapper层） .
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-08   jinbiao　　　新建
 * </pre>
 */

public interface ActReProcdefMapper extends Mapper<ActReProcdef> {

	/**
	 * 
	 * 查询分页. `
	 * 
	 * @param searcher
	 *            查询条件
	 * @param pageable
	 *            分页信息
	 * @return ActReProcdefPage列表
	 * @throws BusinessException
	 *             自定义异常
	 *
	 */
	public Page<ActReProcdef> queryActReProcdefPage(@Param("searcher") Searcher searcher, @Param("page") Page<ActReProcdef> page);

	public ActReProcdef queryActReProcdefById(@Param("id")String id);

}