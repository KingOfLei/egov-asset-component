package com.bosssoft.egov.asset.aims.api.account.service;

import java.util.Map;

/** 
*
* @ClassName   类名：AccountService 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月4日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月4日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface AccountService {
	
	/**
	 * 
	 * <p>函数名称： isDockingAccount       </p>
	 * <p>功能说明：是否对接核算接口
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param orgId
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public boolean isDockingAccount(Long orgId);
	
	/**
	 * 
	 * <p>函数名称：isDockingAccount        </p>
	 * <p>功能说明：是否已经对接 根据单位id和业务发生时间
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param orgId
	 * @param bizDate
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public boolean isDockingAccount(Long orgId,String bizDate);
    
	/**
	 * 
	 * <p>函数名称：doRevoke        </p>
	 * <p>功能说明：记账撤回操作
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busId
	 * @param orgCode
	 * @param busType
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public Map<String,Object> doRevoke(Long busId,String orgCode,String busType); 
	
	/**
	 * 
	 * <p>函数名称：   doSubmit     </p>
	 * <p>功能说明：  记账提交
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busId
	 * @param orgCode
	 * @param busType
	 * @return
	 *
	 * @date   创建时间：2017年12月7日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public Map<String,Object> doSubmit(Long busId,String orgCode,String busType); 
}
