package com.bosssoft.egov.asset.basic.entity;

import javax.persistence.Column;

/** 
*
* @ClassName   类名：BaseAuditEntity 
* @Description 功能说明：审核类基类
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
public abstract class BaseAuditEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6030891536959178989L;

	/**
	 * 审批人.
	 */
	@Column(name = "AUDITOR")
	private String auditor;
	/**
	 * 审核时间.
	 */
	@Column(name = "AUDIT_DATE")
	private String auditDate;
	/**
	 * 下一个环节.
	 */
	@Column(name = "NEXT_SEG")
	private String nextSeg;
	
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	public String getNextSeg() {
		return nextSeg;
	}
	public void setNextSeg(String nextSeg) {
		this.nextSeg = nextSeg;
	}
		
}
