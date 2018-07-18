package com.bosssoft.egov.asset.todo.api;

import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.runtime.User;

/** 
*
* @ClassName   类名：TodoTaskService 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月18日
* @author      创建人：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月18日   黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface TodoTaskService {
	
	public static final String REFERENCE_BEAN_NAME = "com.bosssoft.egov.reference.asset.api.TodoTaskService";

	public void assetTodo(Object object, int editMode, User user);
	
	public void assetTodo(Object object,ProcessResult results,ActivitiParams actParams, User user);
	
	public void assetTodo(Object object,ProcessResult results, ActivitiParams actParams, boolean isPushNext,
			User user);
}
