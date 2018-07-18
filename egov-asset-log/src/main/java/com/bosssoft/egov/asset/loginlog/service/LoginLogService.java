package com.bosssoft.egov.asset.loginlog.service;

import java.util.List;

import com.bosssoft.platform.appframe.api.entity.ApiLoginLog;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：LoginLogService 
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
public interface LoginLogService {
	
	public Page<ApiLoginLog> queryApiLoginLogPage(Searcher paramSearcher, Page<ApiLoginLog> paramPage);

	public List<ApiLoginLog> getLoginLogList(ApiLoginLog loginLog);
}
