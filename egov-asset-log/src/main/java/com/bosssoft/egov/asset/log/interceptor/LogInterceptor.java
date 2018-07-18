package com.bosssoft.egov.asset.log.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.JsonUtilsExt;
import com.bosssoft.egov.asset.log.IdGen;
import com.bosssoft.egov.asset.log.LogConfigurationConstants;
import com.bosssoft.egov.asset.log.api.OperLogHelper;
import com.bosssoft.egov.asset.log.impl.entity.AssetOperLog;
import com.bosssoft.egov.asset.log.service.AssetOperLogService;
import com.bosssoft.platform.common.extension.Activate;
import com.bosssoft.platform.runtime.spi.User;
import com.bosssoft.platform.runtime.spi.web.interceptor.WebInterceptor;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：LogInterceptor 
* @Description 功能说明：操作日志拦截器
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Activate
public class LogInterceptor implements WebInterceptor{
	
	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Resource
	private AssetOperLogService logService;
	
	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
//	private static final ThreadLocal<AssetOperLog> operLogThreadLocal = new NamedThreadLocal<AssetOperLog>("ThreadLocal OperLog");
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//计算时间
		long beginTime = System.currentTimeMillis();//1、开始时间  			
        startTimeThreadLocal.set(beginTime);  
		if (logger.isDebugEnabled()){
			//线程绑定变量（该数据只有当前请求的线程可见）
	        logger.debug("开始计时: {}  URI: {}", DateUtilsExt.formatDate(beginTime, "hh:mm:ss.SSS"), request.getRequestURI());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			logger.debug("ViewName: " + modelAndView.getViewName());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		    // 打印JVM信息。
		WebApplicationContext context = WebApplicationContext.getContext();
//		MenuContext menuContext = context.getWebRequestContext().getMenuContext();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
		long endTime = System.currentTimeMillis(); 	//2、结束时间  
		if(LogConfigurationConstants.logEnable()){
			AssetOperLog operLog = new AssetOperLog();
			String beanName = handlerMethod.getBeanType().getName();
			String methodName = handlerMethod.getMethod().getName();
			operLog.setAppId(context.getAppID());
			operLog.setBeanName(beanName);
			operLog.setBeanMethodName(methodName);
    		if(LogConfigurationConstants.beanParamsEnable()){
      		  operLog.setBeanParams(JsonUtilsExt.toJson(context.getWebRequestContext().getRequestParamsMap()));
      		}
    		operLog.setOperDate(DateUtilsExt.getCurrentDate());
			operLog.setOperTime(new Date());
			operLog.setLogId(IdGen.newWKID());
			/*if(menuContext != null){
	    		  operLog.setMenuId(menuContext.getMenuId());
	    	}*/
			operLog.setUrl(context.getWebRequestContext().getRequestURI());
			operLog.setMenuName("");
			//ip
			operLog.setOperIp(context.getWebRequestContext().getRemoteAddress());
			User user = context.getCurrentUser();
			if(user != null){
			  operLog.setUserCode(user.getUserCode());
			  operLog.setUserName(user.getUserName());
			}
	        //设置开始调用时间
			operLog.setStartTime(new Date(beginTime));
			operLog.setEndTime(new Date(endTime));
			operLog.setTotalTimemillis(endTime - beginTime);
    		String executionNote = "";
  	        for (int i = 0; i < LogConfigurationConstants.rule.length; i++) {
  	        if (methodName.matches(LogConfigurationConstants.rule[i][0])) {
  	          executionNote = LogConfigurationConstants.rule[i][1];
  	        }
  	      }
  	    operLog.setExtParams(executionNote);			
			OperLogHelper.saveOperLog(operLog);
		}
		if (logger.isDebugEnabled()){		
		        logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
		        		DateUtilsExt.formatDate(endTime, "hh:mm:ss.SSS"), DateUtilsExt.formatDateTime(endTime - beginTime),
						request.getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024, 
						(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024); 
		}
		startTimeThreadLocal.remove();
	}

	@Override
	public int order() {
		return 0;
	}

	@Override
	public String[] patternUrl() {
		return new String[]{"/**/*.do"};
	}

}
