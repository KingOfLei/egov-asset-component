package com.bosssoft.egov.asset.dispatch;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 *
 * @ClassName   类名：DefaultJobLister 
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
public class DefaultJobLister implements JobListener{
	private static Logger logger = LoggerFactory.getLogger(DefaultJobLister.class);

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "DEFAULT-JOB-LISTER";
	}

	//Job执行被拒接
	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		logger.debug("Job:[" + arg0.getJobDetail().getKey().getName() + "] is Vetoed.");
	}

	//job执行之前
	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		// TODO Auto-generated method stub
		logger.debug("Job:[" + arg0.getJobDetail().getKey().getName() + "] is to be executed.");
	}

	//job执行之后
	@Override
	public void jobWasExecuted(JobExecutionContext arg0,
			JobExecutionException arg1) {
		// TODO Auto-generated method stub
		logger.debug("Job:[" + arg0.getJobDetail().getKey().getName() + "] was executed.");
	}

}
