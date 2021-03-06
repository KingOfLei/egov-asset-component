/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Wed Feb 22 21:52:22 CST 2017
 */
package com.bosssoft.egov.asset.monitor.service;

import java.util.List;

import com.bosssoft.egov.asset.monitor.entity.FabMonitorRecordDetail;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-02-22   jinbiao　　　新建
 * </pre>
 */
public interface FabMonitorRecordDetailService {

	/**
	 * 增加.
	 * 
	 * @param fabMonitorRecordDetail
	 */
	public void addFabMonitorRecordDetail(FabMonitorRecordDetail fabMonitorRecordDetail);

	/**
	 * 增加.
	 * 
	 * @param fabMonitorRecordDetail
	 */
	public void addFabMonitorRecordDetailNull(FabMonitorRecordDetail fabMonitorRecordDetail);

	/**
	 * 删除及相关信息.
	 * 
	 * @param fabMonitorRecordDetail
	 */
	public void delFabMonitorRecordDetail(FabMonitorRecordDetail fabMonitorRecordDetail);

	/**
	 * 修改.
	 * 
	 * @param fabMonitorRecordDetail
	 */
	public void updateFabMonitorRecordDetail(FabMonitorRecordDetail fabMonitorRecordDetail);

	/**
	 * 获取列表.
	 * 
	 */
	public List<FabMonitorRecordDetail> getFabMonitorRecordDetailList(FabMonitorRecordDetail fabMonitorRecordDetail);

	/**
	 * 获取分页.
	 * 
	 */
	public Page<FabMonitorRecordDetail> queryFabMonitorRecordDetailPage(Searcher searcher, Page<FabMonitorRecordDetail> page);

	public List<FabMonitorRecordDetail> getFabMonitorRecordDetailListByOrgCodeMorgCode(String orgCode,String morgCode);

	public FabMonitorRecordDetail getFabMonitorRecordDetailListByBillIdOrgCodeMorgCode(FabMonitorRecordDetail record);
	
	public void deleteFabMonitorRecordDetailListByBillId(String billId) ;
	
	public void updateFabMonitorRecordDetailByBillId(FabMonitorRecordDetail fabMonitorRecordDetail) ;
	
	public void updateFabMonitorClickByBillId(String billId,String orgCode,String morgCode) ;
	
	public List<FabMonitorRecordDetail> getFabMonitorRecordDetailListByMorgCode(String morgCode,String orgCode) ;
	
	public List<FabMonitorRecordDetail> getDetailsByTypeMorgCode(String type,String morgCode);
	
	public void deleteDetailBy3Condition(FabMonitorRecordDetail recordDetail);
}
