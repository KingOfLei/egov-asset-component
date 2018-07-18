package com.bosssoft.egov.asset.portal.service;

import java.util.List;

import com.bosssoft.egov.asset.portal.entity.UserTodoItem;
import com.bosssoft.egov.asset.portal.entity.UserTodoOutline;
import com.bosssoft.egov.asset.runtime.User;

/** 
*
* @ClassName   类名：UserTodoPortalService 
* @Description 功能说明：代办接口
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface UserTodoPortalService {

	/**
	 * 
	 * <p>函数名称：  queryAllTodoList      </p>
	 * <p>功能说明： 获取用户代办事项类表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param user
	 * @return
	 *
	 * @date   创建时间：2017年2月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public List<UserTodoItem> queryAllTodoList(User user,String type);
	
	/**
	 * 
	 * <p>函数名称：  获取代办内容（大纲）      </p>
	 * <p>功能说明： 获取单个业务代办信息列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param user
	 * @return
	 *
	 * @date   创建时间：2017年2月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public List<UserTodoOutline> queryAllTodoOutlineList(User user,String type);
}
