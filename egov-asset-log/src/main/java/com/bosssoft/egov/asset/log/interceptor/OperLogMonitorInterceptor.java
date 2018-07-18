package com.bosssoft.egov.asset.log.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractTraceInterceptor;

/** 
*
* @ClassName   类名：OperLogMonitorInterceptor 
* @Description 功能说明：操作日志拦截器
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class OperLogMonitorInterceptor extends AbstractTraceInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245861253258419296L;

	@Override
	protected Object invokeUnderTrace(MethodInvocation invocation, Log logger)
			throws Throwable {
		
		return null;
	}

}
