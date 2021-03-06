/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Thu Dec 08 10:11:43 CST 2016
 */
package com.bosssoft.egov.asset.activiti.service;

import java.util.List;

import com.bosssoft.egov.asset.activiti.entity.ActReDeployment;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-08   jinbiao　　　新建
 * </pre>
 */
public interface ActReDeploymentService {

/**
	 * 增加.
	 * 
	 * @param actReDeployment 
	 */
	public void addActReDeployment(ActReDeployment actReDeployment);

	/**
	 * 删除及相关信息.
	 * 
	 * @param actReDeployment 
	 */
	public void delActReDeployment(ActReDeployment actReDeployment);

	/**
	 * 修改.
	 * 
	 * @param actReDeployment 
	 */
	public void updateActReDeployment(ActReDeployment actReDeployment);

	/**
	 * 获取列表.
	 * 
	 */
	public List<ActReDeployment> getActReDeploymentList(ActReDeployment actReDeployment);




   	/**
	 * 获取分页.
	 * 
		 */
	public Page<ActReDeployment> queryActReDeploymentPage(Searcher searcher, Page<ActReDeployment> page);
			
    
}
 