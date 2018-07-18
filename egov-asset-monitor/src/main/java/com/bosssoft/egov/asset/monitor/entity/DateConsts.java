package com.bosssoft.egov.asset.monitor.entity;
/** 
 *
 * @ClassName   类名：DateConsts 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年1月18日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年1月18日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public interface DateConsts {
	//精确到毫秒的日期格式化串
	String DEFAULT_FORMAT = "yyyyMMddHHmmssSSS";
	
	String YYYYMMDD = "yyyyMMdd";
	
	String HHMMSS = "HH:mm:ss";
	/**
	 * 失效日期 默认值
	 */
	String DEFAULT_EXPDATE ="2099-12-31";
}
