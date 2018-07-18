package com.bosssoft.egov.asset.activiti.service.eventbus;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiAlarmService;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmEvent;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.guava.EventBusBase;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/** 
 *
 * @ClassName   类名：ActivitiLogEventListener 
 * @Description 功能说明：爬虫日志监听类
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月12日
 * @author      创建人：Administrator
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年8月8日   金标   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
/**
 * @author 金标
 *
 */
@Component
@Lazy(false)
public class ActivitiAlarmListener extends EventBusBase{
	private static Logger logger = LoggerFactory.getLogger(ActivitiAlarmListener.class);

	@Autowired
	private ActivitiAlarmService activitiAlarmService;
	
	@Subscribe
	@AllowConcurrentEvents
	public void ActivitiAlarm(ActivitiAlarmEvent alarm) {
		String opType = alarm.getOpType();
		DelegateTask task = alarm.getTask();
		DelegateExecution exe = alarm.getExecution();
		if (StringUtilsExt.isNotBlank(opType)) {
			if ("add".equals(opType)) {
				activitiAlarmService.addAlarm(task, alarm.getZone(),alarm.getProcessDesc(),alarm.getIsAlarm());
			} else if ("borderEnd".equals(opType)) {
				activitiAlarmService.updateBorderEnd(task, alarm.getZone(),alarm.getProcessDesc());
			} else if ("borderStart".equals(opType)) {
				activitiAlarmService.updateBorderStart(task, alarm.getZone(),alarm.getProcessDesc(), alarm.getIsAlarm());
			} else if ("deleteByExe".equals(opType)) {
				activitiAlarmService.deleteAlarmByExe(exe);
			} else if ("deleteByTask".equals(opType)) {
				activitiAlarmService.deleteAlarmByTask(task);
			}else if ("middle".equals(opType)) {
				activitiAlarmService.updateMiddle(task, alarm.getZone(),alarm.getProcessDesc(), alarm.getIsAlarm());
			} 
			else {

			}
		}
    }
}
