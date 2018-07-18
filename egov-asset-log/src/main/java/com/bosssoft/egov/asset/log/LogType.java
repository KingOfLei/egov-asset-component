package com.bosssoft.egov.asset.log;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 *
 * @ClassName 类名：LogType
 * @Description 功能说明： 日志类型枚举类
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月15日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年12月15日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public enum LogType {
	OPERLOG("OPERLOG", "操作日志"), 
	BIZLOG("BIZLOG", "业务日志"), 
	OTHER("OTHER","其他日志");

	String code;
	String name;

	private LogType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getCode() + " " + this.getName();
	}

	public static LogType parse(String logType) {
		try {
			return LogType.valueOf(StringUtilsExt.upperCase(logType));
		} catch (Exception e) {
			return LogType.OTHER;
		}
	}
}
