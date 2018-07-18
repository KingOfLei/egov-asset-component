package com.bosssoft.egov.asset.bizlog.entity;

import java.io.Serializable;

/** 
*
* @ClassName   类名：BizLog 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ApiBizLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 161227225617654L;
	private String bizType;
	private String bizTypeName;
	private String bizOperType;
	private String operDesc;
	private BizOperType operType;
	
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	public String getBizOperType() {
		return bizOperType;
	}
	public void setBizOperType(String bizOperType) {
		this.bizOperType = bizOperType;
	}
	public BizOperType getOperType() {
		return operType;
	}
	public void setOperType(BizOperType operType) {
		this.operType = operType;
	}

}
