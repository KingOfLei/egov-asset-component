package com.bosssoft.egov.asset.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：LogConfig 
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
@Service("_LogConfigurationConstants_")
public class LogConfigurationConstants {

	/**
	 * 是否开启操作日志功能
	 */
	public static final String OPER_LOG_ENABLE = "oper.log.enable";
	/**
	 * 是否开启操作日志功能
	 */
	public static final String BIZ_LOG_ENABLE = "biz.log.enable";
	
	/**
	 * URL 匹配规则 即拦截器url规则
	 */
	public static final String OPER_LOG_PATTERNURL = "oper.log.patternUrl";	
	/**
	 * 需要拦截的包规则
	 */
	public static final String OPER_LOG_PATTERNPACKAGE = "oper.log.patternPackage";
	
	/**
	 * 需要拦截的方法 规则
	 */
	public static final String OPER_LOG_PATTERNMETHOD = "oper.log.patternMethod";
	
	/**
	 * 拦截切入点表达式
	 */
	public static final String OPER_LOG_INTERCEPTOR_EXECUTION = "oper.log.interceptor.execution";
	
	/**
	 * 返回值是否需要记录
	 */
	public static final String OPER_LOG_RETURN_PARAMS_ENABLE = "oper.log.return.params.enable";
	
	/**
	 * 请求参数是否需要记录
	 */
	private static final String OPER_LOG_BEAN_PARAMS_ENABLE = "oper.log.bean.params.enable";

	
	/**
	 * 方法业务规则 参照ActionBeanMethodOperLog 类
	 */
	public static final String[][] rule = {
				{ "^doSave.*", "保存" },{ "^save.*", "保存" }, { "^insert.*", "保存" },{ "doInsert.*", "保存" },
				{ "^doUpdate.*", "修改" }, { "^update.*", "更新" }, 
				{ "^del.*", "删除" },{ "^doDel.*", "删除" },  
				{ "^edit.*", "显示修改" },{ "^showEdit.*", "显示修改" }, { "^showAdd.*", "显示新增" },
				{ "^showIndex.*", "显示列表" }, 
				{ "^query.*", "查询" },{ "^get.*", "查询" }, { "^load.*", "查询" }
			};

	/**
	 * 持久化定时器
	 */
	public static final String OPER_LOG_TIMING = "oper.log.timing";
	
	/**
	 * 业务日志 定时器
	 */
	public static final String BIZ_LOG_TIMING = "biz.log.timing";	
	@Value("${" + BIZ_LOG_ENABLE  + "}")
	private String bizLogEnable;
	
	@Value("${" + OPER_LOG_ENABLE  + "}")
	private String operLogEnable;
	
	@Value("${" + OPER_LOG_PATTERNURL  + "}")
	private String operLogPatternUrl;
	
	@Value("${" + OPER_LOG_PATTERNMETHOD  + "}")
	private String operLogPatternMethod;
	
	@Value("${" + OPER_LOG_INTERCEPTOR_EXECUTION  + "}")
	private String operLogInterceptorExecution;
	
	@Value("${" + OPER_LOG_TIMING  + "}")
	private String operLogTiming;
	
	@Value("${" + OPER_LOG_RETURN_PARAMS_ENABLE  + "}")
	private String operLogReurnparamsEnable;
	
	@Value("${" + OPER_LOG_BEAN_PARAMS_ENABLE  + "}")
	private String operLogBeanparamsEnable;
	
	public String getBizLogEnable() {
		return bizLogEnable;
	}

	public void setBizLogEnable(String bizLogEnable) {
		this.bizLogEnable = bizLogEnable;
	}

	public String getOperLogEnable() {
		return operLogEnable;
	}

	public void setOperLogEnable(String operLogEnable) {
		this.operLogEnable = operLogEnable;
	}

	public String getOperLogPatternUrl() {
		return operLogPatternUrl;
	}

	public void setOperLogPatternUrl(String operLogPatternUrl) {
		this.operLogPatternUrl = operLogPatternUrl;
	}

	public String getOperLogPatternMethod() {
		return operLogPatternMethod;
	}

	public void setOperLogPatternMethod(String operLogPatternMethod) {
		this.operLogPatternMethod = operLogPatternMethod;
	}

	public String getOperLogInterceptorExecution() {
		return operLogInterceptorExecution;
	}

	public void setOperLogInterceptorExecution(String operLogInterceptorExecution) {
		this.operLogInterceptorExecution = operLogInterceptorExecution;
	}

	public String getOperLogTiming() {
		return operLogTiming;
	}

	public void setOperLogTiming(String operLogTiming) {
		this.operLogTiming = operLogTiming;
	}
		
    public static LogConfigurationConstants getInstance(){
    	if(INSTANCE == null){
    		synchronized(LogConfigurationConstants.class){
        		if(INSTANCE == null){
    			   INSTANCE = WebApplicationContext.getContext().getBeanContext().getBean(LogConfigurationConstants.class);
    			}
    		}
    	}    	
    	return INSTANCE;
    }
    
	public String getOperLogReurnparamsEnable() {
		return operLogReurnparamsEnable;
	}

	public void setOperLogReurnparamsEnable(String operLogReurnparamsEnable) {
		this.operLogReurnparamsEnable = operLogReurnparamsEnable;
	}

	public String getOperLogBeanparamsEnable() {
		return operLogBeanparamsEnable;
	}

	public void setOperLogBeanparamsEnable(String operLogBeanparamsEnable) {
		this.operLogBeanparamsEnable = operLogBeanparamsEnable;
	}

	private static LogConfigurationConstants INSTANCE;

	public static boolean logEnable(){
		return "true".equalsIgnoreCase(getInstance().getOperLogEnable());
	}
	
	public static boolean bizLogEnable(){
		return "true".equalsIgnoreCase(getInstance().getBizLogEnable());
	}
	
	public static boolean returnParamsEnable(){
		return "true".equalsIgnoreCase(getInstance().getOperLogReurnparamsEnable());
	}
	
	
	public static boolean beanParamsEnable(){
		return "true".equalsIgnoreCase(getInstance().getOperLogBeanparamsEnable());
	}
}
