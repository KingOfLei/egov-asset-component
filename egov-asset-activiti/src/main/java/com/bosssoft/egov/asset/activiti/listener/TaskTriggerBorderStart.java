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
 * @Description 功能说明： 边界起始点调用该触发类，区域单节点也调用该方法
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
public class TaskTriggerBorderStart implements org.activiti.engine.delegate.TaskListener, Serializable {
	private static Logger logger = LoggerFactory.getLogger(TaskTriggerBorderStart.class);
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
//		aas.updateBorderStart(task);
		ActivitiAlarmEvent alarm = new ActivitiAlarmEvent();
//		EventBus eventbus = EventBusHelper.getEventBus();
		alarm.setTask(task);
		alarm.setOpType("borderStart");
		alarm.setZone((String) zone.getValue(task));
		alarm.setProcessDesc((String) processDesc.getValue(task));
		String flag = (String) isAlarm.getValue(task);
		if("0".equals(flag)){//设置是否为监控区域
			alarm.setIsAlarm(0l);
		}else{
			alarm.setIsAlarm(1l);
		}
//		aas.addAlarm(task);
//		eventbus.post(alarm);
		try {
			aas.updateBorderStart(task, alarm.getZone(), alarm.getProcessDesc(), alarm.getIsAlarm());
		} catch (Exception e) {
			logger.error("监听类操作数据库报错_TaskTriggerMiddle_updateMiddle__" + e.getMessage());
			throw new BusinessException("728","监听类操作数据库报错_TaskTriggerBorderStart_updateBorderStart__" + e.getMessage());
		}
		//查找是否存在
		//存在：什么都不做
		//不存在：将之前所有busid一样的complete置位，重新添加一条记录
	}

}
