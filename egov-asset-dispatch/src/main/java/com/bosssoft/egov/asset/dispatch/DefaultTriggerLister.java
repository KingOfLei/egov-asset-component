package com.bosssoft.egov.asset.dispatch;


import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bosssoft.egov.asset.common.idgenerator.GUID;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.dispatch.entity.AssetDispatchLog;
import com.bosssoft.egov.asset.dispatch.service.AssetDispatchLogService;
import com.bosssoft.egov.asset.guava.EventBusHelper;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;
import com.google.common.eventbus.EventBus;

/** 
 *
 * @ClassName   类名：DefaultTriggerLister 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年4月8日
 * @author      创建人：谢德寿
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年4月8日   谢德寿   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class DefaultTriggerLister implements TriggerListener{
	
	private static Logger logger = LoggerFactory.getLogger(DefaultTriggerLister.class);
	
	@Autowired
	private AssetDispatchLogService assetDispatchLogService;

	@Override
	public String getName() {
		return "DEFAULT_TRIGGER";
	}

	// 触发器完成后 触发
	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		EventBus eventbus = EventBusHelper.getEventBus();
		JobDataMap trigerMap = trigger.getJobDataMap();
		String taskId = trigerMap.getString("id");
		AssetDispatchLog log = (AssetDispatchLog) trigerMap.get("log");
		if (logger.isInfoEnabled()) {
//			logger.info("定时任务开始执行：{}.{}", targetObject, targetMethod);
		}
		log.setEndTime(DateUtilsExt.getNowDateTime());
		log.setTaskId(taskId);
		log.setReamrk("任务结束......");
		log.setExecuteStatus("1");
		eventbus.post(log);
		logger.debug("Trigger:[" + trigger.getKey().getName() + "] is Complete.");
	}

	/**
	 * 触发器触发之前触发 晚于 vetoJobExecution
	 */
	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		SpringExtensionHelper.initAutowireFields(context.getJobInstance());
		JobDataMap trigerMap = trigger.getJobDataMap();
		String taskId = trigerMap.getString("id");
//		if (logger.isInfoEnabled()) {
//			logger.info("定时任务开始执行：{}.{}", targetObject, targetMethod);
//		}
		AssetDispatchLog log = new AssetDispatchLog();
		log.setId(GUID.newGUID());
		log.setStartTime(DateUtilsExt.getNowDateTime());
		log.setTaskId(taskId);
		log.setReamrk("任务开始执行......");
		log.setExecuteStatus("0");
		trigger.getJobDataMap().put("log", log);
		logger.debug("Trigger:[" + trigger.getKey().getName() + "] is Fired.");
	}

	// 当触发器错过触发时启动
	@Override
	public void triggerMisfired(Trigger trigger) {
		logger.debug("Trigger:[" + trigger.getKey().getName() + "] is Misfired.");
	}

	//当触发器触发时 运行 返回true 表名此次触发job 不执行
	@Override
	public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
		return false;
	}

}
