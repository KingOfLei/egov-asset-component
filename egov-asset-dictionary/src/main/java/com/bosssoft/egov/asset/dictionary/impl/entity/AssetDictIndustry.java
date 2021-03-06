/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sat Sep 23 00:20:06 CST 2017
 */
package com.bosssoft.egov.asset.dictionary.impl.entity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-09-23   xiedeshou　　　新建
 * </pre>
 */
 @Table(name = "ASSET_DICT_INDUSTRY")
public class AssetDictIndustry implements java.io.Serializable {

	private static final long serialVersionUID = 170923002042083L;
	
	// Fields
	
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_ID")
	private Long industryId;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_CODE")
	private String industryCode;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_PID")
	private Long industryPid;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_PCODE")
	private String industryPcode;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_NAME")
	private String industryName;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_TITLE")
	private String industryTitle;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_RANK")
	private Integer industryRank;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_ISLEAF")
	private String industryIsleaf;
	/**
	 * .
	 */
    @Column(name = "INDUSTRY_STATE")
	private String industryState;
	/**
	 * .
	 */
    @Column(name = "REMARK")
	private String remark;
	/**
	 * .
	 */
    @Column(name = "CREATOR")
	private String creator;
	/**
	 * .
	 */
    @Column(name = "CREATEDATE")
	private String createdate;
	
	// Constructors
 
    /** default constructor */
	public AssetDictIndustry() {
	}

	/**
	 * .
	 * @return
	 */
	public Long getIndustryId() {
		return this.industryId;
	}

	/**
	 * .
	 * @param industryId
	 */
	public void setIndustryId(Long industryId) {
		this.industryId = industryId;
	}

	/**
	 * .
	 * @return
	 */
	public String getIndustryCode() {
		return this.industryCode;
	}

	/**
	 * .
	 * @param industryCode
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	/**
	 * .
	 * @return
	 */
	public Long getIndustryPid() {
		return this.industryPid;
	}

	/**
	 * .
	 * @param industryPid
	 */
	public void setIndustryPid(Long industryPid) {
		this.industryPid = industryPid;
	}

	/**
	 * .
	 * @return
	 */
	public String getIndustryPcode() {
		return this.industryPcode;
	}

	/**
	 * .
	 * @param industryPcode
	 */
	public void setIndustryPcode(String industryPcode) {
		this.industryPcode = industryPcode;
	}

	/**
	 * .
	 * @return
	 */
	public String getIndustryName() {
		return this.industryName;
	}

	/**
	 * .
	 * @param industryName
	 */
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	/**
	 * .
	 * @return
	 */
	public String getIndustryTitle() {
		return this.industryTitle;
	}

	/**
	 * .
	 * @param industryTitle
	 */
	public void setIndustryTitle(String industryTitle) {
		this.industryTitle = industryTitle;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getIndustryRank() {
		return this.industryRank;
	}

	/**
	 * .
	 * @param industryRank
	 */
	public void setIndustryRank(Integer industryRank) {
		this.industryRank = industryRank;
	}

	/**
	 * .
	 * @return
	 */
	public String getIndustryIsleaf() {
		return this.industryIsleaf;
	}

	/**
	 * .
	 * @param industryIsleaf
	 */
	public void setIndustryIsleaf(String industryIsleaf) {
		this.industryIsleaf = industryIsleaf;
	}

	/**
	 * .
	 * @return
	 */
	public String getIndustryState() {
		return this.industryState;
	}

	/**
	 * .
	 * @param industryState
	 */
	public void setIndustryState(String industryState) {
		this.industryState = industryState;
	}

	/**
	 * .
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * .
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * .
	 * @return
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * .
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * .
	 * @return
	 */
	public String getCreatedate() {
		return this.createdate;
	}

	/**
	 * .
	 * @param createdate
	 */
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}