/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 22 17:20:52 CST 2017
 */
package com.bosssoft.egov.asset.dispatch.entity;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.monitor.service.FabMonitorRecordService;

/**
 * 调度-调度表 存放具体作业关联定时配置信息对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-22   Administrator　　　新建
 * </pre>
 */
public class AssetMonitorTaskJob implements Job {

	@Resource
	private FabMonitorRecordService fabMonitorRecordService;
//	@Autowired
//	private 
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		fabMonitorRecordService.executeEnableEvt();
		
//		
//		EventBus eventbus = EventBusHelper.getEventBus();
//		AssetDispatchLog log1 = new AssetDispatchLog();
//		JobDataMap dataMap = context.getTrigger().getJobDataMap();
//		log1.setId(GUID.newGUID());
//		//jobName，用taskid标识
//		String jobName = dataMap.getString("id");
//		log1.setTaskId(jobName);
//		log1.setStartTime(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
//		eventbus.post(log1);
		
//		System.out.println(DateUtilsExt.getNowDateTime4());
//		fabMonitorRecordService.executeEnableEvt();
		
		System.out.println("你好，定时器");
//		
//		log1.setEndTime(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
//		log1.setExecuteStatus("2");
//		eventbus.post(log1);
	}

}