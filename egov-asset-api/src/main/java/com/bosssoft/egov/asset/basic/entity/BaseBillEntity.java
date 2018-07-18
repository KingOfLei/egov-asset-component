package com.bosssoft.egov.asset.basic.entity;

import javax.persistence.Column;

/** 
*
* @ClassName   类名：BaseBillEntity 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public abstract class BaseBillEntity extends BizEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4668536594382118197L;
	
	/**
	 * 业务状态.
	 */
	@Column(name = "BIZ_STATUS")
	private Integer bizStatus;
	/**
	 * 业务状态名称.
	 */
	@Column(name = "BIZ_STATUS_NAME")
	private String bizStatusName;
	public Integer getBizStatus() {
		return bizStatus;
	}
	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}
	public String getBizStatusName() {
		return bizStatusName;
	}
	public void setBizStatusName(String bizStatusName) {
		this.bizStatusName = bizStatusName;
	}
	
}
