/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Dec 27 22:37:09 CST 2016
 */
package com.bosssoft.egov.asset.assettype.entity;
import javax.persistence.Column;
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
 @Table(name = "ASSET_CLASSIFY_OFTEN")
public class AssetClassifyOften implements java.io.Serializable {

	private static final long serialVersionUID = 161227224359712L;
	
	// Fields
	
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
    @Column(name = "ASSET_TYPE_ID")
	private Long assetTypeId;
	/**
	 * .
	 */
    @Column(name = "ASSET_TYPE_CODE")
	private String assetTypeCode;
	/**
	 * .
	 */
    @Column(name = "ASSET_TYPE_NAME")
	private String assetTypeName;
	/**
	 * .
	 */
    @Column(name = "ASSET_CLASSIFY_ID")
	private Long assetClassifyId;
	/**
	 * .
	 */
    @Column(name = "ASSET_CLASSIFY_CODE")
	private String assetClassifyCode;
	/**
	 * .
	 */
    @Column(name = "ASSET_CLASSIFY_NAME")
	private String assetClassifyName;
	/**
	 * .
	 */
    @Column(name = "ASSET_CLASSIFY_PID")
	private Long assetClassifyPid;
	/**
	 * .
	 */
    @Column(name = "ASSET_CLASSIFY_PCODE")
	private String assetClassifyPcode;
    
    @Column(name = "ASSET_CLASSIFY_TITLE")
    private String assetClassifyTitle;
    
    /**
     * 折旧方式编码
     */
    @Column(name = "DEPR_MODE_CODE")
    private String deprModeCode;
    
    /**
     * 折旧方式名称
     */
    @Column(name = "DEPR_MODE_NAME")
    private String deprModeName;
    
    /**
     * 折旧状态编码
     */
    @Column(name = "DEPR_CODE")
    private String deprCode;
    
    /**
     * 折旧状态名称
     */
    @Column(name = "DEPR_NAME")
    private String deprName;
    
    
	/**
	 * .
	 */
    @Column(name = "RANK")
	private Integer rank;
	/**
	 * .
	 */
    @Column(name = "ISLEAF")
	private Integer isleaf;
	/**
	 * .
	 */
    @Column(name = "STATUS")
	private String status;
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
	/**
	 * .
	 */
    @Column(name = "JQ_ASSET_TYPE_ID")
	private String jqAssetTypeId;
	/**
	 * .
	 */
    @Column(name = "JQ_ASSET_CLASSIFY_PID")
	private String jqAssetClassifyPid;
	/**
	 * .
	 */
    @Column(name = "JQ_ASSET_CLASSIFY_ID")
	private String jqAssetClassifyId;
    
    @Column(name = "UNIT")
    private String unit;
    
    @Column(name = "EXPECTED_DATE")
    private String expectedDate;
	
	// Constructors
 
    /** default constructor */
	public AssetClassifyOften() {
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
	public Long getAssetTypeId() {
		return this.assetTypeId;
	}

	/**
	 * .
	 * @param assetTypeId
	 */
	public void setAssetTypeId(Long assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	/**
	 * .
	 * @return
	 */
	public String getAssetTypeCode() {
		return this.assetTypeCode;
	}

	/**
	 * .
	 * @param assetTypeCode
	 */
	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getAssetTypeName() {
		return this.assetTypeName;
	}

	/**
	 * .
	 * @param assetTypeName
	 */
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	/**
	 * .
	 * @return
	 */
	public Long getAssetClassifyId() {
		return this.assetClassifyId;
	}

	/**
	 * .
	 * @param assetClassifyId
	 */
	public void setAssetClassifyId(Long assetClassifyId) {
		this.assetClassifyId = assetClassifyId;
	}

	/**
	 * .
	 * @return
	 */
	public String getAssetClassifyCode() {
		return this.assetClassifyCode;
	}

	/**
	 * .
	 * @param assetClassifyCode
	 */
	public void setAssetClassifyCode(String assetClassifyCode) {
		this.assetClassifyCode = assetClassifyCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getAssetClassifyName() {
		return this.assetClassifyName;
	}

	/**
	 * .
	 * @param assetClassifyName
	 */
	public void setAssetClassifyName(String assetClassifyName) {
		this.assetClassifyName = assetClassifyName;
	}

	/**
	 * .
	 * @return
	 */
	public Long getAssetClassifyPid() {
		return this.assetClassifyPid;
	}

	/**
	 * .
	 * @param assetClassifyPid
	 */
	public void setAssetClassifyPid(Long assetClassifyPid) {
		this.assetClassifyPid = assetClassifyPid;
	}

	/**
	 * .
	 * @return
	 */
	public String getAssetClassifyPcode() {
		return this.assetClassifyPcode;
	}

	/**
	 * .
	 * @param assetClassifyPcode
	 */
	public void setAssetClassifyPcode(String assetClassifyPcode) {
		this.assetClassifyPcode = assetClassifyPcode;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getRank() {
		return this.rank;
	}

	/**
	 * .
	 * @param rank
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getIsleaf() {
		return this.isleaf;
	}

	/**
	 * .
	 * @param isleaf
	 */
	public void setIsleaf(Integer isleaf) {
		this.isleaf = isleaf;
	}

	/**
	 * .
	 * @return
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * .
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
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

	/**
	 * .
	 * @return
	 */
	public String getJqAssetTypeId() {
		return this.jqAssetTypeId;
	}

	/**
	 * .
	 * @param jqAssetTypeId
	 */
	public void setJqAssetTypeId(String jqAssetTypeId) {
		this.jqAssetTypeId = jqAssetTypeId;
	}

	/**
	 * .
	 * @return
	 */
	public String getJqAssetClassifyPid() {
		return this.jqAssetClassifyPid;
	}

	/**
	 * .
	 * @param jqAssetClassifyPid
	 */
	public void setJqAssetClassifyPid(String jqAssetClassifyPid) {
		this.jqAssetClassifyPid = jqAssetClassifyPid;
	}

	/**
	 * .
	 * @return
	 */
	public String getJqAssetClassifyId() {
		return this.jqAssetClassifyId;
	}

	/**
	 * .
	 * @param jqAssetClassifyId
	 */
	public void setJqAssetClassifyId(String jqAssetClassifyId) {
		this.jqAssetClassifyId = jqAssetClassifyId;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getAssetClassifyTitle() {
		return assetClassifyTitle;
	}

	public void setAssetClassifyTitle(String assetClassifyTitle) {
		this.assetClassifyTitle = assetClassifyTitle;
	}

	public String getDeprModeCode() {
		return deprModeCode;
	}

	public void setDeprModeCode(String deprModeCode) {
		this.deprModeCode = deprModeCode;
	}

	public String getDeprModeName() {
		return deprModeName;
	}

	public void setDeprModeName(String deprModeName) {
		this.deprModeName = deprModeName;
	}

	public String getDeprCode() {
		return deprCode;
	}

	public void setDeprCode(String deprCode) {
		this.deprCode = deprCode;
	}

	public String getDeprName() {
		return deprName;
	}

	public void setDeprName(String deprName) {
		this.deprName = deprName;
	}

	
}