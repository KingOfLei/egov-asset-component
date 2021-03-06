/**
 * 福建博思软件 1997-2018 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Mar 25 13:12:33 CST 2018
 */
package com.bosssoft.egov.asset.activiti.service;

import java.util.List;

import com.bosssoft.egov.asset.activiti.entity.ActivitiPermissionExt;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-03-25   jinbiao　　　新建
 * </pre>
 */
public interface ActivitiPermissionExtService {

/**
	 * 增加.
	 * 
	 * @param activitiPermissionExt 
	 */
	public void addActivitiPermissionExt(ActivitiPermissionExt activitiPermissionExt);

	/**
	 * 删除及相关信息.
	 * 
	 * @param activitiPermissionExt 
	 */
	public void delActivitiPermissionExt(ActivitiPermissionExt activitiPermissionExt);
	
	public void batchDelete(List<ActivitiPermissionExt> activitiPermissionExt);
	
	public int delActivitiPermissionExtOrg(String activitiKey,String busType,String assignUsers);
	
	public int delActivitiPermissionExtBus(String activitiKey,String busType,String assignUsers);

	/**
	 * 修改.
	 * 
	 * @param activitiPermissionExt 
	 */
	public void updateActivitiPermissionExt(ActivitiPermissionExt activitiPermissionExt);

	/**
	 * 获取列表.
	 * 
	 */
	public List<ActivitiPermissionExt> getActivitiPermissionExtList(ActivitiPermissionExt activitiPermissionExt);




   	/**
	 * 获取分页.
	 * 
		 */
	public Page<ActivitiPermissionExt> queryActivitiPermissionExtPage(Searcher searcher, Page<ActivitiPermissionExt> page);
			
    
}
 