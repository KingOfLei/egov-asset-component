package com.bosssoft.egov.asset.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmEvent;
import com.bosssoft.egov.asset.guava.EventBusHelper;
import com.google.common.eventbus.EventBus;

/** 
 *
 * @ClassName   类名：TaskTrigger 
 * @Description 功能说明：
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
public class TaskTriggerDeleteByTask implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6191773710942054027L;
	
	@Override
	public void notify(DelegateTask delegateTask) {
//		ActivitiAlarmService aas = RuntimeApplicationContext.getBeanByType(ActivitiAlarmService.class);
//		aas.deleteAlarmByTask(delegateTask);
		ActivitiAlarmEvent alarm = new ActivitiAlarmEvent();
		EventBus eventbus = EventBusHelper.getEventBus();
		alarm.setTask(delegateTask);
		alarm.setOpType("deleteByTask");//删除用异步
//		aas.addAlarm(task);
		eventbus.post(alarm);
	}
}
