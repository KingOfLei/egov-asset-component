/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Dec 27 22:55:50 CST 2016
 */
package com.bosssoft.egov.asset.bizlog.entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-27   Administrator　　　新建
 * </pre>
 */
 @Table(name = "ASSET_BIZ_LOG")
public class AssetBizLog implements java.io.Serializable {

	private static final long serialVersionUID = 161227225617655L;
	
	// Fields
	
	/**
	 * .
	 */
	@Id
    @Column(name = "ID")
	private Long id;
	/**
	 * .
	 */
    @Column(name = "APP_ID")
	private String appId;
	/**
	 * .
	 */
    @Column(name = "USER_ID")
	private String userId;
	/**
	 * .
	 */
    @Column(name = "USER_CODE")
	private String userCode;
	/**
	 * .
	 */
    @Column(name = "USER_NAME")
	private String userName;
	/**
	 * .
	 */
    @Column(name = "RGN_ID")
	private Long rgnId;
	/**
	 * .
	 */
    @Column(name = "RGN_CODE")
	private String rgnCode;
	/**
	 * .
	 */
    @Column(name = "RGN_NAME")
	private String rgnName;
	/**
	 * .
	 */
    @Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * .
	 */
    @Column(name = "ORG_CODE")
	private String orgCode;
	/**
	 * .
	 */
    @Column(name = "ORG_NAME")
	private String orgName;
	/**
	 * .
	 */
    @Column(name = "BIZ_TYPE")
	private String bizType;
	/**
	 * .
	 */
    @Column(name = "BIZ_TYPE_NAME")
	private String bizTypeName;
    
    @Column(name = "BIZ_OPER_TYPE")
    private String bizOperType;
	/**
	 * .
	 */
    @Column(name = "OPER_DESC")
	private String operDesc;
	/**
	 * .
	 */
    @Column(name = "OPER_DATE")
	private String operDate;
	/**
	 * .
	 */
    @Column(name = "CREATE_TIME")
	private String createTime;
	/**
	 * .
	 */
    @Column(name = "STR01")
	private String str01;
	/**
	 * .
	 */
    @Column(name = "STR02")
	private String str02;
	/**
	 * .
	 */
    @Column(name = "STR03")
	private String str03;
	/**
	 * .
	 */
    @Column(name = "STR04")
	private String str04;
	/**
	 * .
	 */
    @Column(name = "STR05")
	private String str05;
	
	// Constructors
 
    /** default constructor */
	public AssetBizLog() {
	}

	/**
	 * .
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * .
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * .
	 * @return
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * .
	 * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * .
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * .
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * .
	 * @return
	 */
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * .
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * .
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * .
	 * @return
	 */
	public Long getRgnId() {
		return this.rgnId;
	}

	/**
	 * .
	 * @param rgnId
	 */
	public void setRgnId(Long rgnId) {
		this.rgnId = rgnId;
	}

	/**
	 * .
	 * @return
	 */
	public String getRgnCode() {
		return this.rgnCode;
	}

	/**
	 * .
	 * @param rgnCode
	 */
	public void setRgnCode(String rgnCode) {
		this.rgnCode = rgnCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getRgnName() {
		return this.rgnName;
	}

	/**
	 * .
	 * @param rgnName
	 */
	public void setRgnName(String rgnName) {
		this.rgnName = rgnName;
	}

	/**
	 * .
	 * @return
	 */
	public Long getOrgId() {
		return this.orgId;
	}

	/**
	 * .
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * .
	 * @return
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * .
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * .
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * .
	 * @return
	 */
	public String getBizType() {
		return this.bizType;
	}

	/**
	 * .
	 * @param bizType
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * .
	 * @return
	 */
	public String getBizTypeName() {
		return this.bizTypeName;
	}

	/**
	 * .
	 * @param bizTypeName
	 */
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	
	public String getBizOperType() {
		return bizOperType;
	}

	public void setBizOperType(String bizOperType) {
		this.bizOperType = bizOperType;
	}

	/**
	 * .
	 * @return
	 */
	public String getOperDesc() {
		return this.operDesc;
	}

	/**
	 * .
	 * @param operDesc
	 */
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}

	/**
	 * .
	 * @return
	 */
	public String getOperDate() {
		return this.operDate;
	}

	/**
	 * .
	 * @param operDate
	 */
	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}

	/**
	 * .
	 * @return
	 */
	public String getCreateTime() {
		return this.createTime;
	}

	/**
	 * .
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * .
	 * @return
	 */
	public String getStr01() {
		return this.str01;
	}

	/**
	 * .
	 * @param str01
	 */
	public void setStr01(String str01) {
		this.str01 = str01;
	}

	/**
	 * .
	 * @return
	 */
	public String getStr02() {
		return this.str02;
	}

	/**
	 * .
	 * @param str02
	 */
	public void setStr02(String str02) {
		this.str02 = str02;
	}

	/**
	 * .
	 * @return
	 */
	public String getStr03() {
		return this.str03;
	}

	/**
	 * .
	 * @param str03
	 */
	public void setStr03(String str03) {
		this.str03 = str03;
	}

	/**
	 * .
	 * @return
	 */
	public String getStr04() {
		return this.str04;
	}

	/**
	 * .
	 * @param str04
	 */
	public void setStr04(String str04) {
		this.str04 = str04;
	}

	/**
	 * .
	 * @return
	 */
	public String getStr05() {
		return this.str05;
	}

	/**
	 * .
	 * @param str05
	 */
	public void setStr05(String str05) {
		this.str05 = str05;
	}

}