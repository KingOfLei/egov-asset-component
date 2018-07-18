package com.bosssoft.egov.asset.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.Trigger;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/** 
*
* @ClassName   类名：LogQuartzConfiguration 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年8月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年8月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class QuartzSchedulerFactoryBean extends SchedulerFactoryBean{
	
	private Map<String,Trigger> defaultTrigger;
	
	private Map<String,Boolean> startUpStatus;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//根据状态 动态开启对应日志信息
		
		List<Trigger> triggers = new ArrayList<>();
		for(Entry<String, Trigger>  trigger : defaultTrigger.entrySet()){
			if(startUpStatus.get(trigger.getKey())){						
			  triggers.add(trigger.getValue());
			}
		}
		setTriggers(triggers.toArray(new Trigger[0]));
		super.afterPropertiesSet();
	}
	
	@Override
	public boolean isAutoStartup() {
		for(Entry<String, Boolean>  entry : startUpStatus.entrySet()){
			if(entry.getValue()){
				return true;
			}
		}		
		return false;
	}

	public Map<String, Trigger> getDefaultTrigger() {
		return defaultTrigger;
	}

	public void setDefaultTrigger(Map<String, Trigger> defaultTrigger) {
		this.defaultTrigger = defaultTrigger;
	}

	public Map<String, Boolean> getStartUpStatus() {
		return startUpStatus;
	}

	public void setStartUpStatus(Map<String, Boolean> startUpStatus) {
		this.startUpStatus = startUpStatus;
	}

	
}
