package com.bosssoft.egov.asset.dispatch;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.impl.matchers.KeyMatcher;

/** 
 *
 * @ClassName   类名：QuartzKit 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年3月15日
 * @author      创建人：谢德寿
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年3月15日   谢德寿   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class QuartzKit {
	private static Scheduler scheduler;
	
	 /**
     * 
     * <p>函数名称： makeDelay2sTask       </p>
     * <p>功能说明： 任务延迟2秒后执行 
     *
     * </p>
     *<p>参数说明：</p>
     * @param name
     * @param group
     * @param jobClassName
     * @throws SchedulerException
     *
     * @date   创建时间：2015-7-22
     * @author 作者：谢德寿
     */
    public static void makeDelay2sTask(String name,String group,Class<? extends Job> jobClassName) throws SchedulerException{
 	   makeDelay2sTask(name, group, jobClassName, new JobDataMap());
    }
    
    /**
     * 
     * <p>函数名称：  makeDelay2sTask      </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param name
     * @param group
     * @param jobClassName
     * @param jobDataMap
     * @throws SchedulerException
     *
     * @date   创建时间：2015-7-22
     * @author 作者：谢德寿
     */
    public static void makeDelay2sTask(String name,String group, Class<? extends Job> jobClassName, JobDataMap jobDataMap) throws SchedulerException{
 	   makeDelayTrigger(name, group, jobClassName, jobDataMap, 2*1000L);
    }
    
    /**
     * 
     * <p>函数名称：   makeDelayTrigger     </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param name 名称
     * @param group 组名称
     * @param jobClassName job对应class
     * @param jobDataMap 运行数据
     * @param delay 延迟秒数 
     * @throws SchedulerException
     *
     * @date   创建时间：2015-7-22
     * @author 作者：谢德寿
     */
    public static void makeDelayTrigger(String name, String group, Class<? extends Job> jobClassName, JobDataMap jobDataMap, long delay) throws SchedulerException{
    	makeDelayTrigger(name, group, jobClassName, jobDataMap, delay, null);
    }
    
    /**
     * 
     * <p>函数名称：makeDelayTrigger        </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param name
     * @param group
     * @param jobClassName
     * @param jobDataMap
     * @param delay
     * @param triggerListener 调用内部方法监听器
     * @throws SchedulerException
     *
     * @date   创建时间：2016年4月22日
     * @author 作者：JTT
     */
    public static void makeDelayTrigger(String name, String group, Class<? extends Job> jobClassName, JobDataMap jobDataMap, long delay, TriggerListener triggerListener) throws SchedulerException{
		
		//判断是否有此日期任务在执行
		SimpleTrigger trigger = (SimpleTrigger) QuartzKit.getScheduler().getTrigger(new TriggerKey(name, group));
		if (trigger != null) {
			return;
		}
		//延迟执行
		TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger()
				   .withIdentity(name, group)
				   .startAt(new Date(System.currentTimeMillis() + delay))
				   .forJob(name, group);
	    if(jobDataMap != null){
	    	builder.usingJobData(jobDataMap);
	    }
				   
		trigger = (SimpleTrigger) builder.build();
	//	trigger = new SimpleTrigger(name, group, new Date(System.currentTimeMillis() + delay));

		JobDetail jobDetail = QuartzKit.getScheduler().getJobDetail(new JobKey(name, group));
		
		if (jobDetail == null) {
//					jobDetail = new JobDetail(name, group, jobClassName);
			//是否连续性  true 当job 无trigger与其关联时，不会从JobStore移除 可待下次关联trigger再触发
//					jobDetail.setDurability(true);
			//addJob(JobDetail job,boolean replace) //替换
			//@param replaceExisting
		     /**          If <code>true</code>, any <code>Job</code> existing in the
		     *          <code>JobStore</code> with the same name & group should be
		     *          over-written.
		     **/
			jobDetail = JobBuilder.newJob(jobClassName)
					    .withIdentity(name, group)
					    .storeDurably(true)
					    .build();
			QuartzKit.getScheduler().addJob(jobDetail, false);
		}
		QuartzKit.getScheduler().scheduleJob(trigger);
		if (triggerListener != null) {
			if (!QuartzKit.getScheduler().getListenerManager().getTriggerListeners().contains(triggerListener.getName())) {
				KeyMatcher<TriggerKey> matcher = KeyMatcher.keyEquals(new TriggerKey(name, group));
				QuartzKit.getScheduler().getListenerManager().addTriggerListener(triggerListener, matcher);
			}
		}
		if (!QuartzKit.getScheduler().isStarted()) {
			QuartzKit.getScheduler().start();
		}
    }
    
    /**
     * 
     * <p>函数名称：   makeDelayTrigger     </p>
     * <p>功能说明：
     *
     * </p>
     *<p>参数说明：</p>
     * @param name 名称
     * @param group 组名称
     * @param jobClassName job对应class
     * @param jobDataMap 运行数据
     * @param delay 延迟秒数 
     * @throws SchedulerException
     *
     * @date   创建时间：2015-7-22
     * @author 作者：谢德寿
     */
    public static void makeDelayTrigger(String name, String group, Class<? extends Job> jobClassName, long delay) throws SchedulerException{
    	makeDelayTrigger(name, group, jobClassName, new JobDataMap(), delay);
    }
    
    public static void makeCycleCronTrigger(String name, String group, Class<? extends Job> jobClassName, String cronExpression, boolean isReset) throws Exception{
    	makeCycleCronTrigger(name, group, jobClassName, new JobDataMap(), cronExpression, isReset);
    }
    
    /**
     * 
     * <p>函数名称：   makeCycleCronTriggerByOneOclick     </p>
     * <p>功能说明： 每日凌晨一点执行 0 0 1 * * ? 
     *
     * </p>
     *<p>参数说明：</p>
     * @param name
     * @param group
     * @param jobClassName
     * @param isReset 
     * @throws Exception
     *
     * @date   创建时间：2015-7-24
     * @author 作者：谢德寿
     */
    public static void makeCycleCronTriggerByOneOclick(String name, String group, Class<? extends Job> jobClassName, boolean isReset) throws Exception{
    	makeCycleCronTrigger(name, group, jobClassName, new JobDataMap(), "0 0 1 * * ?", isReset);
    }
    
    public static void makeCycleCronTriggerByOneOclick(String name, String group, Class<? extends Job> jobClassName, JobDataMap jobDataMap, boolean isReset) throws Exception{
    	makeCycleCronTrigger(name, group, jobClassName, jobDataMap, "0 0 1 * * ?", isReset);
    }
    
    
    /**
     * 
     * <p>函数名称： makeCycleCronTrigger       </p>
     * <p>功能说明： 周期定时任务
     *
     * </p>
     *<p>参数说明：</p>
     * @param name 
     * @param group
     * @param jobClassName
     * @param CronExpression 周期表达式
     * @param isReset 已经存在此定时器是否重置 true 是  false
     *
     * @date   创建时间：2015-7-24
     * @author 作者：谢德寿
     * @throws SchedulerException 
     * @throws ParseException 
     */
    public static void makeCycleCronTrigger(String name, String group, Class<? extends Job> jobClassName, JobDataMap jobDataMap, String cronExpression, boolean isReset,TriggerListener triggerListener) throws Exception{
    	CronTrigger trigger = (CronTrigger) QuartzKit.getScheduler().getTrigger(new TriggerKey(name, group));
    	boolean exists = trigger != null; //是否已经存在此定时器
    	if (!isReset && exists)
    		return; //存在且无需重置 直接返回
    	//延迟执行
		TriggerBuilder<CronTrigger> builder = TriggerBuilder.newTrigger().withIdentity(name, group)
				   .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				   .forJob(name, group);
	    if(jobDataMap != null){
	    	builder.usingJobData(jobDataMap);
	    }
				   
		trigger = (CronTrigger) builder.build();
        JobDetail jobDetail = QuartzKit.getScheduler().getJobDetail(new JobKey(name, group));
		
		if (jobDetail == null) {
//			jobDetail = new JobDetail(name, group, jobClassName);
			//是否连续性  true 当job 无trigger与其关联时，不会从JobStore移除 可待下次关联trigger再触发
//			jobDetail.setDurability(true);
			//addJob(JobDetail job,boolean replace) //替换
			//@param replaceExisting
			jobDetail = JobBuilder.newJob(jobClassName)
				    .withIdentity(name, group)
				    .storeDurably(true)
				    .build();
			QuartzKit.getScheduler().addJob(jobDetail, false);
			//(!jobDetail.isDurable()) && (!replace) 
		}
		
//		trigger.setJobName(name);
//		trigger.setJobGroup(group);
		if(!QuartzKit.getScheduler().getListenerManager().getTriggerListeners().contains(triggerListener.getName())){
		   KeyMatcher<TriggerKey> matcher = KeyMatcher.keyEquals(new TriggerKey(name, group));
		   QuartzKit.getScheduler().getListenerManager().addTriggerListener(triggerListener, matcher);
		}
//		trigger.addTriggerListener(triggerListener.getName());
		
		if (exists) { //存在重置
		  QuartzKit.getScheduler().rescheduleJob(new TriggerKey(name, group), trigger);
		} else { 
		  QuartzKit.getScheduler().scheduleJob(trigger);
		}
		
		if (!QuartzKit.getScheduler().isStarted()) {
			QuartzKit.getScheduler().start();
		}
    }	

    public static void makeCycleCronTrigger(String name, String group, Class<? extends Job> jobClassName, JobDataMap jobDataMap, String cronExpression, boolean isReset) throws Exception{
    	makeCycleCronTrigger(name, group, jobClassName, jobDataMap, cronExpression, isReset, new DefaultTriggerLister());
    }

    
	public static Scheduler getScheduler() {
		return scheduler;
	}

	public static void setScheduler(Scheduler scheduler) {
		QuartzKit.scheduler = scheduler;
	}
	
	
	/**
	 * <p>函数名称：removeJob        </p>
	 * <p>功能说明：移除一个任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name    任务名
	 * @param group   任务组名
	 * @throws Exception
	 *
	 * @date   创建时间：2016年8月23日
	 * @author 作者：hzx
	 */
	public static void removeJob(String name,String group) throws Exception{
		CronTrigger trigger = (CronTrigger) QuartzKit.getScheduler().getTrigger(new TriggerKey(name, group));
		boolean exists = trigger != null; //是否已经存在此定时器
		if(exists){
			//参数分别为triggerName,triggerGroupName
			QuartzKit.getScheduler().pauseTrigger(trigger.getKey());
			QuartzKit.getScheduler().unscheduleJob(trigger.getKey());
		}
	}
}
