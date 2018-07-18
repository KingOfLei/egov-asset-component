package com.bosssoft.egov.asset.log.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.ExceptionUtils;
import com.bosssoft.egov.asset.common.util.JsonUtilsExt;
import com.bosssoft.egov.asset.log.IdGen;
import com.bosssoft.egov.asset.log.LogConfigurationConstants;
import com.bosssoft.egov.asset.log.annotations.Operation;
import com.bosssoft.egov.asset.log.api.OperLogHelper;
import com.bosssoft.egov.asset.log.impl.entity.AssetOperLog;
import com.bosssoft.platform.runtime.spi.User;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：LogAopAspect 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class LogAopAspect {
	
    private static Logger logger = LoggerFactory.getLogger(LogAopAspect.class);

    public Object invokeInterceptor(ProceedingJoinPoint pjp) throws Throwable{
    	//计算时间
		WebApplicationContext context = WebApplicationContext.getContext();
    	long beginTime = System.currentTimeMillis();//1、开始时间 
    	if (logger.isDebugEnabled()){
			//线程绑定变量（该数据只有当前请求的线程可见）
	        logger.debug("开始计时: {}  URI: {}", DateUtilsExt.formatDate(beginTime, "hh:mm:ss.SSS"), context.getWebRequestContext().getRequestURI());
		}
    	
    	Signature sign = pjp.getSignature();
    	String beanName = sign.getDeclaringTypeName();
    	String methodName = sign.getName();    	
    	String error = "";
    	Object obj = null;
    	try {
    		//执行目标方法
    	   obj = pjp.proceed();
		} catch (Throwable e) {
    		error = ExceptionUtils.getExceptionMessage(e);
    		//继续外抛异常 不做处理 获取异常信息
    		throw e;
    	} finally{
        	long endTime = System.currentTimeMillis();
        	//判断是否需要记录操作日志    	
        	if(LogConfigurationConstants.logEnable()){    	
            	//获取方法及bean
//        		MenuContext menuContext = context.getWebRequestContext().get
        		AssetOperLog operLog = new AssetOperLog();
        		operLog.setAppId(context.getAppID());
        		operLog.setBeanName(beanName);
        		operLog.setBeanMethodName(methodName);
        		if(LogConfigurationConstants.beanParamsEnable()){
        		  operLog.setBeanParams(JsonUtilsExt.toJson(context.getWebRequestContext().getRequestParamsMap()));
        		}
        		operLog.setOperDate(DateUtilsExt.getCurrentDate());
        		operLog.setOperTime(new Date());
        		operLog.setLogId(IdGen.newWKID());
//        		if(menuContext != null){
//        		  operLog.setMenuId(menuContext.getMenuId());
//        		}
        		operLog.setMenuName("");
        		operLog.setUrl(context.getWebRequestContext().getRequestURI());
        		//ip
        		operLog.setOperIp(context.getWebRequestContext().getRemoteAddress());
        		User user = context.getCurrentUser();
        		if(user != null){
        		  operLog.setUserCode(user.getUserCode());
        		  operLog.setUserName(user.getUserName());
        		}
            	operLog.setStartTime(new Date(beginTime));
        		operLog.setEndTime(new Date(endTime));
        		//总耗时 毫秒
        		operLog.setTotalTimemillis(endTime - beginTime);
        		if(LogConfigurationConstants.returnParamsEnable()){
        		  operLog.setReturnParams(JsonUtilsExt.toJson(obj));
        		}
        		String executionNote = "";
				for (int i = 0; i < LogConfigurationConstants.rule.length; i++) {
					if (methodName.matches(LogConfigurationConstants.rule[i][0])) {
						executionNote = LogConfigurationConstants.rule[i][1];
					}
				}
				//获取方法注解
				if(sign instanceof MethodSignature){
					MethodSignature methodSign = (MethodSignature) sign;
					Method method = methodSign.getMethod();
					if(method.getAnnotation(Operation.class) != null){
						Operation operation = method.getAnnotation(Operation.class);
						executionNote = operation.comment();
					} else if(method.getAnnotation(RequestMapping.class) != null ){				
						RequestMapping mapperInfo = method.getAnnotation(RequestMapping.class);
						executionNote = mapperInfo.name();
					}
				}
        	    operLog.setExtParams(executionNote);
        	    operLog.setException(error);
        		//发送日志信息
        		OperLogHelper.saveOperLog(operLog);	
        	}
        	
    		if (logger.isDebugEnabled()){		
    		        logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
    		        		DateUtilsExt.formatDate(endTime, "hh:mm:ss.SSS"), DateUtilsExt.formatDateTime(endTime - beginTime),
    		        		context.getWebRequestContext().getRequestURI(), Runtime.getRuntime().maxMemory()/1024/1024, Runtime.getRuntime().totalMemory()/1024/1024, Runtime.getRuntime().freeMemory()/1024/1024, 
    						(Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory())/1024/1024); 
    		}
    	}
    	
    	//返回对象
        return obj;
    }
}
