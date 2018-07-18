package com.bosssoft.egov.asset.portal.service;

import java.util.Date;
import java.util.List;

import com.bosssoft.egov.asset.portal.entity.UserTodoItem;
import com.bosssoft.egov.asset.runtime.User;

/** 
*
* @ClassName   类名：UserTodoService 
* @Description 功能说明：
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
public interface UserTodoService {
	
	/**
	 * 获取待办列表
	 * @param user 用户信息
	 * @param limit 限制数
	 * @return
	 */
	public List<UserTodoItem> queryTodoList(User user,String type);
	

	/**
	 * 获取待办数
	 * @param user
	 * @return
	 */
	public int queryTodoCount(User user,String type);
	
	/**
	 * 待办分类描述
	 * @param user
	 * @param subSystemId
	 * @return
	 */
	public String getServiceDescription(User user,String type);
	
	/**
	 * 具体待办内容的URL
	 * @param item
	 * @param user
	 * @return
	 */
	public String getDetailUrl(UserTodoItem item,User user,String type);
	
	/**
	 * 最新待办项的时间
	 * @param user
	 * @param subSystemId
	 * @return
	 */
	public Date getLastUpdateTime(User user,String type);
	
	public String getTodoType();
}
