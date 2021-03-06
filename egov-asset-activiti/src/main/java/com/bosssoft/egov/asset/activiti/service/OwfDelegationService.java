/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Nov 15 10:42:05 CST 2016
 */
package com.bosssoft.egov.asset.activiti.service;

import java.util.List;

import com.bosssoft.egov.asset.activiti.entity.OwfDelegation;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-11-15   jinbiao　　　新建
 * </pre>
 */
public interface OwfDelegationService {

/**
	 * 增加.
	 * 
	 * @param owfDelegation 
	 */
	public void addOwfDelegation(OwfDelegation owfDelegation);

	/**
	 * 删除及相关信息.
	 * 
	 * @param owfDelegation 
	 */
	public void delOwfDelegation(OwfDelegation owfDelegation);

	/**
	 * 修改.
	 * 
	 * @param owfDelegation 
	 */
	public void updateOwfDelegation(OwfDelegation owfDelegation);

	/**
	 * 获取列表.
	 * 
	 */
	public List<OwfDelegation> getOwfDelegationList(OwfDelegation owfDelegation);




   	/**
	 * 获取分页.
	 * 
		 */
	public Page<OwfDelegation> queryOwfDelegationPage(Searcher searcher, Page<OwfDelegation> page);
		
	/**
	 * 通过名字获取代理情况
	 */
    
	public List<OwfDelegation> queryOwfDelegationPageByUserCode(Searcher searcher,Page<OwfDelegation> page,String name);
	
	//不没有id添加记录
	public void addOwfDelegationNoId(OwfDelegation owfDelegation);

	public void batchDelOwfDelegation(List<OwfDelegation> owfDelegations);
}
 