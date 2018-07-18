package com.bosssoft.egov.asset.basic.entity;

import java.io.Serializable;

/** 
*
* @ClassName   类名：ResultEntity 
* @Description 功能说明：返回结果集基类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年11月25日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年11月25日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ResultEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4122138955845537781L;

	/**
	 * 结果集编码
	 */
	private String code;
	
	/**
	 * 结果说明
	 */
	private String message;
	/**
	 * 是否异常
	 */
	private boolean exception;
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public boolean isException() {
		return exception;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setException(boolean exception) {
		this.exception = exception;
	}
	
}
