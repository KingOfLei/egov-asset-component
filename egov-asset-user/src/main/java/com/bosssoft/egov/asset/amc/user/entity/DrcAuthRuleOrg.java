package com.bosssoft.egov.asset.amc.user.entity;
/** 
 *
 * @ClassName   类名：DrcAuthRuleOrg 
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
public class DrcAuthRuleOrg implements java.io.Serializable{
	private static final long serialVersionUID = -630971719187617244L;

	private String id;
	private String orgId;
	private String orgCode;
	private String orgName;
	private String financeBudgetCode;
	private String orgPid;
	private String ruleValueId;
	private String ruleValueCode;
	private String ruleValueName;
	private String ruleOperator;
	private String roleId;
	private String roleCode;
	private String roleName;
	private String roleType;
	private String rgnId;
	private String rgnCode;
	private String rgnName;
	private String partyId;
	private String partyCode;
	private String partyName;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
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
	public String getFinanceBudgetCode() {
		return financeBudgetCode;
	}
	public void setFinanceBudgetCode(String financeBudgetCode) {
		this.financeBudgetCode = financeBudgetCode;
	}
	public String getOrgPid() {
		return orgPid;
	}
	public void setOrgPid(String orgPid) {
		this.orgPid = orgPid;
	}
	public String getRuleValueId() {
		return ruleValueId;
	}
	public void setRuleValueId(String ruleValueId) {
		this.ruleValueId = ruleValueId;
	}
	public String getRuleValueName() {
		return ruleValueName;
	}
	public void setRuleValueName(String ruleValueName) {
		this.ruleValueName = ruleValueName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public String getRgnId() {
		return rgnId;
	}
	public void setRgnId(String rgnId) {
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
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuleValueCode() {
		return ruleValueCode;
	}
	public void setRuleValueCode(String ruleValueCode) {
		this.ruleValueCode = ruleValueCode;
	}
	public String getRuleOperator() {
		return ruleOperator;
	}
	public void setRuleOperator(String ruleOperator) {
		this.ruleOperator = ruleOperator;
	}
	
}
