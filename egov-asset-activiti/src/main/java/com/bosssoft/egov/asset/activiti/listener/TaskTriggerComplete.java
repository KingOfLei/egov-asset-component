package com.bosssoft.egov.asset.activiti.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiAlarmService;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;

/** 
 *
 * @ClassName   类名：TaskTrigger 
 * @Description 功能说明：  对于后继节点不设置监控间接的时候用
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年5月19日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年5月19日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class TaskTriggerComplete implements org.activiti.engine.delegate.TaskListener, Serializable {
	private static Logger logger = LoggerFactory.getLogger(TaskTriggerComplete.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6191773710942054027L;

	@Override
	public void notify(DelegateTask task) {
		ActivitiAlarmService aas = RuntimeApplicationContext.getBeanByType(ActivitiAlarmService.class);
		try {
			aas.updateComplete(task);
		} catch (Exception e) {
			logger.error("监听类操作数据库报错_TaskTriggerMiddle_updateMiddle__" + e.getMessage());
			throw new BusinessException("728","监听类操作数据库报错_TaskTriggerComplete_updateComplete__" + e.getMessage());
		}
		//查找是否存在
		//存在：删除所有，创建一条记录
		//不存在：将自己的taskdef更新进去
	}

}
