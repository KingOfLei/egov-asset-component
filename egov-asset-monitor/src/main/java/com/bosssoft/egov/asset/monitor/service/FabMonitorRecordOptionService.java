/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Mon Feb 27 09:09:14 CST 2017
 */
package com.bosssoft.egov.asset.monitor.service;

import java.util.List;

import com.bosssoft.egov.asset.monitor.entity.FabMonitorRecordDetail;
import com.bosssoft.egov.asset.monitor.entity.FabMonitorRecordOption;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * 收藏关注表Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-02-27   jinbiao　　　新建
 * </pre>
 */
public interface FabMonitorRecordOptionService {

/**
	 * 增加收藏关注表.
	 * 
	 * @param fabMonitorRecordOption 收藏关注表
	 */
	public void addFabMonitorRecordOption(FabMonitorRecordOption fabMonitorRecordOption);

	/**
	 * 删除收藏关注表及相关信息.
	 * 
	 * @param fabMonitorRecordOption 收藏关注表
	 */
	public void delFabMonitorRecordOption(FabMonitorRecordOption fabMonitorRecordOption);

	/**
	 * 修改收藏关注表.
	 * 
	 * @param fabMonitorRecordOption 收藏关注表
	 */
	public void updateFabMonitorRecordOption(FabMonitorRecordOption fabMonitorRecordOption);

	/**
	 * 获取收藏关注表列表.
	 * 
	 */
	public List<FabMonitorRecordOption> getFabMonitorRecordOptionList(FabMonitorRecordOption fabMonitorRecordOption);




   	/**
	 * 获取收藏关注表分页.
	 * 
		 */
	public Page<FabMonitorRecordOption> queryFabMonitorRecordOptionPage(Searcher searcher, Page<FabMonitorRecordOption> page);
			
 
	public void addOneFocusRecord(FabMonitorRecordDetail detail, User user);
	
	public void cancelOneFocusRecord(FabMonitorRecordDetail detail, User user);
	
}
 