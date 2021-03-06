/**
 * 福建博思软件 1997-2018 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Jan 16 10:14:04 CST 2018
 */
package com.bosssoft.egov.asset.di.entity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 接口导入单位对照表对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-01-16   xiedeshou　　　新建
 * </pre>
 */
 @Table(name = "ASSET_DI_TASK_ORG_COMPARE")
public class AssetDiTaskOrgCompare implements java.io.Serializable {

	private static final long serialVersionUID = 180116101631659L;
	
	// Fields
	
	/**
	 * 所属单位id.
	 */
    @Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * 所属单位编码.
	 */
    @Column(name = "ORG_CODE")
	private String orgCode;
	/**
	 * 所属单位名称.
	 */
    @Column(name = "ORG_NAME")
	private String orgName;
	/**
	 * 源单位id.
	 */
    @Column(name = "SRC_ORG_ID")
	private String srcOrgId;
	/**
	 * 源单位编码.
	 */
    @Column(name = "SRC_ORG_CODE")
	private String srcOrgCode;
	/**
	 * 源单位名称.
	 */
    @Column(name = "SRC_ORG_NAME")
	private String srcOrgName;
	/**
	 * 目标单位id.
	 */
    @Column(name = "DEST_ORG_ID")
	private String destOrgId;
	/**
	 * 目标单位编码.
	 */
    @Column(name = "DEST_ORG_CODE")
	private String destOrgCode;
	/**
	 * 目标单位名称.
	 */
    @Column(name = "DEST_ORG_NAME")
	private String destOrgName;
	/**
	 * 目标单位pid.
	 */
    @Column(name = "DEST_ORG_PID")
	private String destOrgPid;
	/**
	 * 目标单位pcode.
	 */
    @Column(name = "DEST_ORG_PCODE")
	private String destOrgPcode;
	/**
	 * 级次.
	 */
    @Column(name = "DEST_ORG_RANK")
	private Long destOrgRank;
	/**
	 * 是否最细级.
	 */
    @Column(name = "DEST_ORG_ISLEAF")
	private String destOrgIsleaf;
	
	// Constructors
 
    /** default constructor */
	public AssetDiTaskOrgCompare() {
	}

	/**
	 * 所属单位id.
	 * @return
	 */
	public Long getOrgId() {
		return this.orgId;
	}

	/**
	 * 所属单位id.
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 所属单位编码.
	 * @return
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * 所属单位编码.
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 所属单位名称.
	 * @return
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * 所属单位名称.
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 源单位id.
	 * @return
	 */
	public String getSrcOrgId() {
		return this.srcOrgId;
	}

	/**
	 * 源单位id.
	 * @param srcOrgId
	 */
	public void setSrcOrgId(String srcOrgId) {
		this.srcOrgId = srcOrgId;
	}

	/**
	 * 源单位编码.
	 * @return
	 */
	public String getSrcOrgCode() {
		return this.srcOrgCode;
	}

	/**
	 * 源单位编码.
	 * @param srcOrgCode
	 */
	public void setSrcOrgCode(String srcOrgCode) {
		this.srcOrgCode = srcOrgCode;
	}

	/**
	 * 源单位名称.
	 * @return
	 */
	public String getSrcOrgName() {
		return this.srcOrgName;
	}

	/**
	 * 源单位名称.
	 * @param srcOrgName
	 */
	public void setSrcOrgName(String srcOrgName) {
		this.srcOrgName = srcOrgName;
	}

	/**
	 * 目标单位id.
	 * @return
	 */
	public String getDestOrgId() {
		return this.destOrgId;
	}

	/**
	 * 目标单位id.
	 * @param destOrgId
	 */
	public void setDestOrgId(String destOrgId) {
		this.destOrgId = destOrgId;
	}

	/**
	 * 目标单位编码.
	 * @return
	 */
	public String getDestOrgCode() {
		return this.destOrgCode;
	}

	/**
	 * 目标单位编码.
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 目标单位名称.
	 * @return
	 */
	public String getDestOrgName() {
		return this.destOrgName;
	}

	/**
	 * 目标单位名称.
	 * @param destOrgName
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	/**
	 * 目标单位pid.
	 * @return
	 */
	public String getDestOrgPid() {
		return this.destOrgPid;
	}

	/**
	 * 目标单位pid.
	 * @param destOrgPid
	 */
	public void setDestOrgPid(String destOrgPid) {
		this.destOrgPid = destOrgPid;
	}

	/**
	 * 目标单位pcode.
	 * @return
	 */
	public String getDestOrgPcode() {
		return this.destOrgPcode;
	}

	/**
	 * 目标单位pcode.
	 * @param destOrgPcode
	 */
	public void setDestOrgPcode(String destOrgPcode) {
		this.destOrgPcode = destOrgPcode;
	}

	/**
	 * 级次.
	 * @return
	 */
	public Long getDestOrgRank() {
		return this.destOrgRank;
	}

	/**
	 * 级次.
	 * @param destOrgRank
	 */
	public void setDestOrgRank(Long destOrgRank) {
		this.destOrgRank = destOrgRank;
	}

	/**
	 * 是否最细级.
	 * @return
	 */
	public String getDestOrgIsleaf() {
		return this.destOrgIsleaf;
	}

	/**
	 * 是否最细级.
	 * @param destOrgIsleaf
	 */
	public void setDestOrgIsleaf(String destOrgIsleaf) {
		this.destOrgIsleaf = destOrgIsleaf;
	}

}