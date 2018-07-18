package com.bosssoft.egov.asset.aims.api.account.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/** 
*
* @ClassName   类名：AccountMapper 
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
public interface AccountMapper {

	/**
	 * 
	 * <p>函数名称：isDockingAccount        </p>
	 * <p>功能说明：查询核算对接情况
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param orgId
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public List<Map<String,Object>> queryDockingAccount(@Param("orgId")Long orgId,@Param("bizDate")String bizDate);
	
	/**
	 * 
	 * <p>函数名称： doRevoke      </p>
	 * <p>功能说明： 记账撤回操作
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param paramsMap
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public void doRevoke(Map<String,Object> paramsMap);
	

	/**
	 * 
	 * <p>函数名称： doSubmit      </p>
	 * <p>功能说明： 记账提交
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param paramsMap
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public void doSubmit(Map<String,Object> paramsMap);
}
