package com.bosssoft.egov.asset.dispatch;

import java.text.ParseException;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.simpl.SimpleThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 *
 * @ClassName 类名：DispatchManger
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年2月20日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2017年2月20日 xds 创建该类功能。
 *
 *
 *          </p>
 */
@Service
public class DispatchManger implements InitializingBean, DisposableBean {

	private static Logger logger = LoggerFactory.getLogger(DefaultTriggerLister.class);

	private static String JOB_GROUP_NAME = "hivegroup_1";
	private static String TRIGGER_GROUP_NAME = "hivetrigger_1";

	private Scheduler scheduler;

	private boolean isStarted = false;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (isStarted)
			return;
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Properties mergedProps = new Properties();

			mergedProps.put("org.quartz.threadPool.class", SimpleThreadPool.class.getName());
			mergedProps.put("org.quartz.threadPool.threadCount", Integer.toString(20));		
			//加载参数配置
			((StdSchedulerFactory)schedulerFactory).initialize(mergedProps);
			
			this.scheduler = schedulerFactory.getScheduler();
			
			QuartzKit.setScheduler(this.scheduler);
			Scheduler scheduler = QuartzKit.getScheduler();
			scheduler.start();// 启动
			logger.debug("Start quartz plugin success.");
		} catch (SchedulerException e) {
			logger.debug("Start quartz plugin fail.", e);
			throw new RuntimeException("Can't start quartz plugin.", e);
		}
		isStarted = true;
	}

	@Override
	public void destroy() throws Exception {
		try {
			QuartzKit.getScheduler().shutdown();
			isStarted = false;
			logger.debug("Stop quartz plugin success.");
		} catch (SchedulerException e) {
			logger.debug("Stop quartz plugin fail.", e);
			throw new RuntimeException("Can't stop quartz plugin.", e);
		}
	}

	public static void addJob(String jobName, Class<? extends Job> job, String time) throws SchedulerException, ParseException {
		Trigger trigger = (Trigger) QuartzKit.getScheduler().getTrigger(new TriggerKey(jobName, JOB_GROUP_NAME));
		boolean exists = trigger != null; // 是否已经存在此定时器
		if (exists) {
			return; // 存在且无需重置 直接返回
		}
		JobDetail jobDetail = QuartzKit.getScheduler().getJobDetail(new JobKey(jobName, JOB_GROUP_NAME));

		if (jobDetail == null) {
			jobDetail = JobBuilder.newJob(job).withIdentity(jobName, JOB_GROUP_NAME).storeDurably(true).build();
//			jobDetail = new JobDetail(, job);
			// 是否连续性 true 当job 无trigger与其关联时，不会从JobStore移除 可待下次关联trigger再触发
//			jobDetail.setDurability(true);
			// addJob(JobDetail job,boolean replace) //替换
			// @param replaceExisting
			QuartzKit.getScheduler().addJob(jobDetail, false);
			// (!jobDetail.isDurable()) && (!replace)
		}
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id", jobName);
        
		DefaultTriggerLister triggerListener = new DefaultTriggerLister();
		//替换2.3.0 版本写法
		trigger = (CronTrigger)TriggerBuilder.newTrigger()
				   .withIdentity(jobName, JOB_GROUP_NAME)
				   .withSchedule(CronScheduleBuilder.cronSchedule(time))
				   .forJob(jobName, JOB_GROUP_NAME)
				   .usingJobData(jobDataMap)
				   .build();
				
//		trigger.setJobName(jobName);
//		trigger.setJobGroup();
		//新增触发器监听
		if (!QuartzKit.getScheduler().getListenerManager().getTriggerListeners().contains(triggerListener.getName())) {
			KeyMatcher<TriggerKey> matcher = KeyMatcher.keyEquals(trigger.getKey());
			QuartzKit.getScheduler().getListenerManager().addTriggerListener(triggerListener, matcher);
		}
		//新增任务监听
		JobListener jobListener = new JobListener();
		if(!QuartzKit.getScheduler().getListenerManager().getJobListeners().contains(jobListener.getName())){
			KeyMatcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
			QuartzKit.getScheduler().getListenerManager().addJobListener(jobListener, matcher);
		}
		//trigger.addTriggerListener(triggerListener.getName());
//		QuartzKit.getScheduler().addJobListener(jobListener);
		//jobDetail.addJobListener(jobListener.getName());
		
		if (exists) { // 存在重置
			QuartzKit.getScheduler().rescheduleJob(new TriggerKey(jobName, JOB_GROUP_NAME), trigger);
		} else {
			QuartzKit.getScheduler().scheduleJob(trigger);
		}
		if (!QuartzKit.getScheduler().isStarted()) {
			QuartzKit.getScheduler().start();
		}
	}
	
	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String jobName, String time) throws SchedulerException, ParseException {
		Trigger trigger = QuartzKit.getScheduler().getTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
		//trigger.setJobDataMap(dataMap);
		if (trigger != null) {
		//	JobDataMap dataMap = new JobDataMap();
			CronTrigger ct = (CronTrigger) trigger;
			ct.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule(time));
			QuartzKit.getScheduler().resumeTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
		}
	}

	/** */
	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName) throws SchedulerException {
		Trigger trigger = QuartzKit.getScheduler().getTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));
		if(trigger!=null){
			QuartzKit.getScheduler().pauseTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 停止触发器
			QuartzKit.getScheduler().unscheduleJob(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 移除触发器
			QuartzKit.getScheduler().deleteJob(new JobKey(jobName, TRIGGER_GROUP_NAME));// 删除任务
		}
	}
	
	/**
	 * 停止一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void pauseJob(String jobName) throws SchedulerException {
		QuartzKit.getScheduler().pauseTrigger(new TriggerKey(jobName, TRIGGER_GROUP_NAME));// 停止触发器
	}
	
	/**
	 * 停止一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @throws SchedulerException
	 */
	public static void resumeJob(String jobName) throws SchedulerException {
		QuartzKit.getScheduler().resumeJob(new JobKey(jobName, TRIGGER_GROUP_NAME));// 停止触发器
	}
}
