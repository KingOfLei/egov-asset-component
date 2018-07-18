package com.bosssoft.egov.asset.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiAlarmService;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;

/** 
 *
 * @ClassName   类名：TaskTrigger 
 * @Description 功能说明： 主要用于流程结束时候，将相关的
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
public class TaskTriggerDeleteByExe implements ExecutionListener {
	private static Logger logger = LoggerFactory.getLogger(TaskTriggerDeleteByExe.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -6191773710942054027L;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		ActivitiAlarmService aas = RuntimeApplicationContext.getBeanByType(ActivitiAlarmService.class);
//		aas.deleteAlarmByExe(execution);
		try {
			aas.updateCompleteByExe(execution);
		} catch (Exception e) {
			logger.error("监听类操作数据库报错_TaskTriggerMiddle_updateMiddle__" + e.getMessage());
			throw new BusinessException("728","监听类操作数据库报错_TaskTriggerDeleteByExe_updateCompleteByExe__" + e.getMessage());
		}
	}
/*	public void notify(DelegateExecution execution) throws Exception {
//		ActivitiAlarmService aas = RuntimeApplicationContext.getBeanByType(ActivitiAlarmService.class);
//		aas.deleteAlarmByExe(execution);
		ActivitiAlarmEvent alarm = new ActivitiAlarmEvent();
		EventBus eventbus = EventBusHelper.getEventBus();
		alarm.setExecution(execution);
		alarm.setOpType("deleteByExe");//删除用异步删除
//		aas.addAlarm(task);
		eventbus.post(alarm);
	}
*/
}
