package com.bosssoft.egov.asset.dispatch;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.runtime.web.context.AppContext;

/** 
*
* @ClassName   类名：BosssoftJobFactory 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service("bosssoftJobFactory")
public class BosssoftJobFactory extends AdaptableJobFactory{
	  
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
	    	//quartz 启动job 时 重新实例化了 jobDeail 故直接注入的话属性会为null 不由spring 管理
	    	//此处 在实例化后动态注入属性值 让spring接口注入的service
	    	//调用父类方法 实例化jobDeail类
	    	Object jobInstance = super.createJobInstance(bundle);
	    	//注入 使其支持@Autowired 自动注入
	        AppContext.getAppContext().getBeanContext().getAutowireCapableBeanFactory().autowireBean(jobInstance);    	
	    	return jobInstance;
	    } 
}
