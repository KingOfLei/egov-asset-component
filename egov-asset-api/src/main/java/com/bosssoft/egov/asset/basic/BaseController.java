package com.bosssoft.egov.asset.basic;

import java.util.HashMap;
import java.util.Map;

import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;


/** 
*
* @ClassName   类名：BaseController 
* @Description 功能说明：控制层基类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public abstract class BaseController {
	
	/**
	 * 
	 * <p>函数名称：  getCurrUser      </p>
	 * <p>功能说明：获取当前登录用户信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年6月8日
	 * @author 作者：xds
	 */
	protected User getCurrUser(){
		return AppContext.getAppContext().getSessionUser();
	}
	
	/**
	 * 
	 * <p>函数名称：  getUserParams      </p>
	 * <p>功能说明： 获取用户参数
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2018年1月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	protected Map<String,Object> getUserParams(){
		Map<String,Object> params = new HashMap<String,Object>();
		User user = getCurrUser();		
		params.put("__userId__", user.getUserId());
		params.put("__userCode__", user.getUserCode());
		params.put("__userName__", user.getUserName());
		params.put("__userPhone__", user.getMobileNo());
		params.put("__orgId__", user.getOrgId());
		params.put("__orgCode__", user.getOrgCode());
		params.put("__orgName__", user.getOrgName());
		params.put("__rgnId__", user.getRgnId());
		params.put("__rgnCode__", user.getRgnCode());
		params.put("__rgnName__", user.getRgnName());		
		params.put("__roleCode__", StringUtilsExt.join(user.getRoleCodes(), ","));
		params.put("__roleName__", StringUtilsExt.join(user.getRoleNames(), ","));
		params.put("__creator__", user.getUserCode() + " " + user.getUserName());
		params.put("__creatorId__", user.getUserId());
		params.put("__financeBudgetCode__", user.getOrg().getFinanceBudgetCode());
		params.put("__createDate__", DateUtilsExt.getNowDateTime());
		params.put("__maker__", user.getUserName());
		params.put("__makerDate__", DateUtilsExt.getCurrentDate());
		params.put("__ip__", WebApplicationContext.getContext().getWebRequestContext().getRemoteAddress());
		
		return params;
	}
}
