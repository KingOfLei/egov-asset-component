package com.bosssoft.egov.asset.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiAlarmService;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmEvent;
import com.bosssoft.egov.asset.guava.EventBusHelper;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;
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
public class TaskTriggerAdd implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6191773710942054027L;
	
	private FixedValue zone;
	
	private FixedValue processDesc;
	
	private FixedValue isAlarm;
	
	@Override
	public void notify(DelegateTask task) {
		ActivitiAlarmService aas = RuntimeApplicationContext.getBeanByType(ActivitiAlarmService.class);
		ActivitiAlarmEvent alarm = new ActivitiAlarmEvent();
		EventBus eventbus = EventBusHelper.getEventBus();
		String flag = (String) isAlarm.getValue(task);
//		Object obj = message.getValue(task);
		alarm.setTask(task);
		alarm.setOpType("add");
		alarm.setZone((String) zone.getValue(task));
		alarm.setProcessDesc((String) processDesc.getValue(task));
		if("0".equals(flag)){
			alarm.setIsAlarm(0l);
		}else{
			alarm.setIsAlarm(1l);
		}
//		aas.addAlarm(task);
		eventbus.post(alarm);
		System.out.println(1111+"完成后触发任务add方法");
	}

}
