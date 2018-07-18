package com.bosssoft.egov.asset.dispatch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bosssoft.egov.asset.common.idgenerator.GUID;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.dispatch.entity.AssetDispatchLog;
import com.bosssoft.egov.asset.dispatch.service.AssetDispatchLogService;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;

/** 
*
* @ClassName   类名：JobListener 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class JobListener implements org.quartz.JobListener {
	
	private static Logger logger = LoggerFactory.getLogger(JobListener.class);

	// 线程池
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	private static String JOB_LOG = "jobLog";

	@Autowired
	private AssetDispatchLogService assetDispatchLogService;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "taskListener";
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		
	}
	
	// 任务开始前
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		SpringExtensionHelper.initAutowireFields(context.getJobInstance());
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
//		String targetObject = jobDataMap.getString("targetObject");
//		String targetMethod = jobDataMap.getString("targetMethod");
		String taskId = jobDataMap.getString("id");
//		String taskId = jobDataMap.getString("taskId");
		if (logger.isInfoEnabled()) {
//			logger.info("定时任务开始执行：{}.{}", targetObject, targetMethod);
		}
		AssetDispatchLog log = new AssetDispatchLog();
		log.setId(GUID.newGUID());
		log.setStartTime(DateUtilsExt.getNowDateTime());
		log.setTaskId(taskId);
		log.setReamrk("任务开始执行......");
		log.setExecuteStatus("-1");
		jobDataMap.put(JOB_LOG, log);
	}
	// 任务结束后
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException exp) {
		final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String targetObject = jobDataMap.getString("targetObject");
		String targetMethod = jobDataMap.getString("targetMethod");
		if (logger.isInfoEnabled()) {
			logger.info("定时任务执行结束：{}.{}", targetObject, targetMethod);
		}
		// 更新任务执行状态

		final AssetDispatchLog log = (AssetDispatchLog) jobDataMap.get(JOB_LOG);
		if (log != null) {
			log.setEndTime(DateUtilsExt.getNowDateTime());
			if (exp != null) {				
				log.setExecuteStatus("0");;
				log.setReamrk(exp.getMessage());
			} else {
				if ("-1".equals(log.getExecuteStatus())) {
					log.setExecuteStatus("1");
				}
			}
		}
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				//异步记录日志
				
			}
		});
	}

}
