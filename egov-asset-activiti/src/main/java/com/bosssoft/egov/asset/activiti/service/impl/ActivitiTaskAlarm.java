package com.bosssoft.egov.asset.activiti.service.impl;

import org.activiti.engine.task.Task;
import org.openwebflow.alarm.MessageNotifier;
import org.openwebflow.identity.UserDetailsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/** 
 *
 * @ClassName   类名：Test 
 * @Description 功能说明：任务到期前通知。在相应的notify中写入方法。配置文件中的myTaskAlarmService  配置
 * <p>	
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年11月1日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年11月1日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ActivitiTaskAlarm implements MessageNotifier, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(ActivitiTaskAlarm.class);

	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void notify(UserDetailsEntity[] users, Task task) throws Exception {
		
		logger.info("你好，世界！");
		
	}

}
