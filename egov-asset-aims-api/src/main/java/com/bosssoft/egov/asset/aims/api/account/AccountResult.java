package com.bosssoft.egov.asset.aims.api.account;
/** 
*
* @ClassName   类名：AccountResult 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月4日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月4日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AccountResult implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2016246093189380342L;

	/**
	 * 结果集编码
	 */
	private String code;
	
	/**
	 * 结果说明
	 */
	private String message;
	
	/**
	 * 
	 */
	private boolean success;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
