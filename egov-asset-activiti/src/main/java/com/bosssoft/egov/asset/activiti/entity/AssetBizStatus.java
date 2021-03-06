/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Thu Dec 01 11:29:29 CST 2016
 */
package com.bosssoft.egov.asset.activiti.entity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-01   jinbiao　　　新建
 * </pre>
 */
 @Table(name = "ASSET_BIZ_STATUS")
public class AssetBizStatus implements java.io.Serializable {

	private static final long serialVersionUID = 161201113001283L;
	
	// Fields
	
	/**
	 * ID.
	 */
    @Column(name = "BIZ_ID")
	private Long bizId;
	/**
	 * 是否启用.
	 */
    @Column(name = "ISENABLE")
	private String isenable;
	/**
	 * .
	 */
    @Column(name = "BIZ_TYPE")
	private String bizType;
	/**
	 * 状态编码.
	 */
    @Column(name = "BIZ_STATUS")
	private Integer bizStatus;
	/**
	 * 状态名称.
	 */
    @Column(name = "BIZ_STATUS_NAME")
	private String bizStatusName;
	
	// Constructors
 
    /** default constructor */
	public AssetBizStatus() {
	}

	/**
	 * ID.
	 * @return
	 */
	public Long getBizId() {
		return this.bizId;
	}

	/**
	 * ID.
	 * @param bizId
	 */
	public void setBizId(Long bizId) {
		this.bizId = bizId;
	}

	/**
	 * 是否启用.
	 * @return
	 */
	public String getIsenable() {
		return this.isenable;
	}

	/**
	 * 是否启用.
	 * @param isenable
	 */
	public void setIsenable(String isenable) {
		this.isenable = isenable;
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
	 * 状态编码.
	 * @return
	 */
	public Integer getBizStatus() {
		return this.bizStatus;
	}

	/**
	 * 状态编码.
	 * @param bizStatus
	 */
	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}

	/**
	 * 状态名称.
	 * @return
	 */
	public String getBizStatusName() {
		return this.bizStatusName;
	}

	/**
	 * 状态名称.
	 * @param bizStatusName
	 */
	public void setBizStatusName(String bizStatusName) {
		this.bizStatusName = bizStatusName;
	}

}