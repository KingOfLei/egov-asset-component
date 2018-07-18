package com.bosssoft.egov.asset.basic.entity;

import javax.persistence.Column;

/** 
*
* @ClassName   类名：BaseEntity 
* @Description 功能说明：基本类公共属性
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
public abstract class BizEntity extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7927399719539202166L;
	
	/**
	 * 区划ID.
	 */
	@Column(name = "RGN_ID")
	private Long rgnId;
	/**
	 * 区划编码.
	 */
	@Column(name = "RGN_CODE")
	private String rgnCode;
	/**
	 * 区划名称.
	 */
	@Column(name = "RGN_NAME")
	private String rgnName;
	/**
	 * 组织机构ID.
	 */
	@Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * 组织机构编码.
	 */
	@Column(name = "ORG_CODE")
	private String orgCode;
	/**
	 * 组织机构名称.
	 */
	@Column(name = "ORG_NAME")
	private String orgName;
	
	/**
	 * 创建者ID.
	 */
	@Column(name = "CREATOR_ID")
	private String creatorId;

	/**
	 * 创建者.
	 */
	@Column(name = "CREATOR")
	private String creator;

	/**
	 * 创建时间.
	 */
	@Column(name = "CREATE_DATE")
	private String createDate;
	/**
	 * 修改时间.
	 */
	@Column(name = "UPDATE_DATE")
	private String updateDate;
	
	public Long getRgnId() {
		return rgnId;
	}
	public void setRgnId(Long rgnId) {
		this.rgnId = rgnId;
	}
	public String getRgnCode() {
		return rgnCode;
	}
	public void setRgnCode(String rgnCode) {
		this.rgnCode = rgnCode;
	}
	public String getRgnName() {
		return rgnName;
	}
	public void setRgnName(String rgnName) {
		this.rgnName = rgnName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
