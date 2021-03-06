/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Dec 26 11:26:04 CST 2017
 */
package com.bosssoft.egov.asset.lifecycle.entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-12-26   Wiesel　　　新建
 * </pre>
 */
 @Table(name = "ASSET_LIFE_LOG")
public class AssetLifeLog implements java.io.Serializable {

	private static final long serialVersionUID = 171226112821364L;
	
	// Fields
	
	/**
	 * .
	 */
	@Id
    @Column(name = "ID")
	private Long id;
	/**
	 * 创建日期.
	 */
    @Column(name = "CREATE_DATE")
	private String createDate;
	/**
	 * 创建时间.
	 */
    @Column(name = "CREATE_TIME")
	private String createTime;
	/**
	 * 组织机构ID.
	 */
    @Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * 业务类型.
	 */
    @Column(name = "BUS_TYPE")
	private String busType;
	/**
	 * 操作结果信息（成功或错误原因）.
	 */
    @Column(name = "MSG")
	private String msg;
	/**
	 * 状态 1成功 0 失败.
	 */
    @Column(name = "STATUS")
	private Integer status;
	/**
	 * 业务ID.
	 */
    @Column(name = "BUS_ID")
	private String busId;
	/**
	 * 1 审核 ，0 反审核.
	 */
    @Column(name = "IS_AUDIT")
	private Integer isAudit;
	
	// Constructors
 
    /** default constructor */
	public AssetLifeLog() {
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
	 * 创建日期.
	 * @return
	 */
	public String getCreateDate() {
		return this.createDate;
	}

	/**
	 * 创建日期.
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 创建时间.
	 * @return
	 */
	public String getCreateTime() {
		return this.createTime;
	}

	/**
	 * 创建时间.
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 组织机构ID.
	 * @return
	 */
	public Long getOrgId() {
		return this.orgId;
	}

	/**
	 * 组织机构ID.
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 业务类型.
	 * @return
	 */
	public String getBusType() {
		return this.busType;
	}

	/**
	 * 业务类型.
	 * @param busType
	 */
	public void setBusType(String busType) {
		this.busType = busType;
	}

	/**
	 * 操作结果信息（成功或错误原因）.
	 * @return
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * 操作结果信息（成功或错误原因）.
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 状态 1成功 0 失败.
	 * @return
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态 1成功 0 失败.
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 业务ID.
	 * @return
	 */
	public String getBusId() {
		return this.busId;
	}

	/**
	 * 业务ID.
	 * @param busId
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}

	/**
	 * 1 审核 ，0 反审核.
	 * @return
	 */
	public Integer getIsAudit() {
		return this.isAudit;
	}

	/**
	 * 1 审核 ，0 反审核.
	 * @param isAudit
	 */
	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

}