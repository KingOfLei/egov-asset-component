package com.bosssoft.egov.asset.portal.entity;

import java.io.Serializable;

/** 
*
* @ClassName   类名：UserTodoOutline 
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
public class UserTodoOutline implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1113145095621824337L;

	/**
	 * 待办分类描述
	 */
	String description;
	
	/**
	 * 分类中的待办工作数
	 */
	Integer todoCount;
	
	/**
	 * 待办分类的功能ID
	 */
	String funcId;
	
	/**
	 * 最新待办工作的时间
	 */
	String lastUpdate;
	
	String params;

	public String getDescription() {
		return description;
	}

	public Integer getTodoCount() {
		return todoCount;
	}

	public String getFuncId() {
		return funcId;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTodoCount(Integer todoCount) {
		this.todoCount = todoCount;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
}
