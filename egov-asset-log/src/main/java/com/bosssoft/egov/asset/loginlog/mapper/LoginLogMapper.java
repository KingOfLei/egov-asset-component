package com.bosssoft.egov.asset.loginlog.mapper;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.platform.appframe.api.entity.ApiLoginLog;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.persistence.common.Mapper;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：LoginLogMapper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月26日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月26日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface LoginLogMapper extends Mapper<ApiLoginLog>{

	/**
	 * 
	 * 查询系统操作日志 分页.
	 *	`
	 * @param searcher 查询条件
	 * @param pageable 分页信息
	 * @return AssetOperLogPage列表
	 * @throws BusinessException 自定义异常
	 *
	 */
	public Page<ApiLoginLog> queryApiSysLoginLogPage(@Param("searcher")Searcher searcher,@Param("page")Page<ApiLoginLog> page);
			

}
