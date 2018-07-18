/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Thu Apr 27 15:52:24 CST 2017
 */
package com.bosssoft.egov.asset.drc.service;

import java.util.List;

import com.bosssoft.egov.asset.drc.entity.DrcAuthOrg;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;



/**
 * 用户对应单位权限表Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-04-27   Administrator　　　新建
 * </pre>
 */
public interface DrcAuthOrgService {

/**
	 * 增加用户对应单位权限表.
	 * 
	 * @param drcAuthOrg 用户对应单位权限表
	 */
	public void addDrcAuthOrg(DrcAuthOrg drcAuthOrg);

	/**
	 * 删除用户对应单位权限表及相关信息.
	 * 
	 * @param drcAuthOrg 用户对应单位权限表
	 */
	public void delDrcAuthOrg(DrcAuthOrg drcAuthOrg);

	/**
	 * 修改用户对应单位权限表.
	 * 
	 * @param drcAuthOrg 用户对应单位权限表
	 */
	public void updateDrcAuthOrg(DrcAuthOrg drcAuthOrg);

	/**
	 * 获取用户对应单位权限表列表.
	 * 
	 */
	public List<DrcAuthOrg> getDrcAuthOrgList(DrcAuthOrg drcAuthOrg);




   	/**
	 * 获取用户对应单位权限表分页.
	 * 
		 */
	public Page<DrcAuthOrg> queryDrcAuthOrgPage(Searcher searcher, Page<DrcAuthOrg> page);
			
    
}
 