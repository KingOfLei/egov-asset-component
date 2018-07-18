package com.bosssoft.egov.asset.activiti.listener;

import java.io.Serializable;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.el.FixedValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiAlarmService;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmEvent;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;

/** 
 *
 * @ClassName   类名：TaskTrigger 
 * @Description 功能说明：  边界结束处
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
public class TaskTriggerBorderEnd implements org.activiti.engine.delegate.TaskListener, Serializable {
	private static Logger logger = LoggerFactory.getLogger(TaskTriggerBorderEnd.class);
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
//		aas.updateBorderEnd(task);
		ActivitiAlarmEvent alarm = new ActivitiAlarmEvent();
//		EventBus eventbus = EventBusHelper.getEventBus();
		alarm.setTask(task);
		alarm.setOpType("borderEnd");
		alarm.setZone((String) zone.getValue(task));
		alarm.setProcessDesc((String) processDesc.getValue(task));
		String flag = (String) isAlarm.getValue(task);
		if("0".equals(flag)){
			alarm.setIsAlarm(0l);
		}else{
			alarm.setIsAlarm(1l);
		}
//		aas.addAlarm(task);
//		eventbus.post(alarm);
		try {
			aas.updateBorderEnd(task, alarm.getZone(), alarm.getProcessDesc());
		} catch (Exception e) {
			logger.error("监听类操作数据库报错_TaskTriggerMiddle_updateMiddle__" + e.getMessage());
			throw new BusinessException("728", "监听类操作数据库报错_TaskTriggerBorderEnd_updateBorderEnd__" + e.getMessage());
		}
		//查找是否存在
		//存在：删除所有，创建一条记录
		//不存在：将自己的taskdef更新进去
	}

}
