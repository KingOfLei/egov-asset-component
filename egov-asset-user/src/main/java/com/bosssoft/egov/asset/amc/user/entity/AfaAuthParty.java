package com.bosssoft.egov.asset.amc.user.entity;

import java.util.Date;

/** 
 *
 * @ClassName   类名：AfaAuthParty 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年4月9日
 * @author      创建人：黄振雄
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年4月9日   黄振雄   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class AfaAuthParty implements java.io.Serializable{

	private static final long serialVersionUID = -5303323525413899814L;
	
	private String roleCode;
	
	private String roleType;
	
	private String roleId;

	private String partyCode;

	private String partyType;

	private String tenantId;

	private String createUser;

	private Date createTime;

	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleCodeName(){
		return this.roleCode + " " + this.roleName;
	}
	
}
