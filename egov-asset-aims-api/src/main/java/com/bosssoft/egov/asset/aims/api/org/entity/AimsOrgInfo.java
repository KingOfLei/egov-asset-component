package com.bosssoft.egov.asset.aims.api.org.entity;

import javax.persistence.Column;
import javax.persistence.Table;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsBasicOrg;

@Table(name = "AIMS_BASIC_ORG")
public class AimsOrgInfo extends AimsBasicOrg implements java.io.Serializable {

	private static final long serialVersionUID = 5426880478725938418L;
	
	/**
	 * 局级及以上编制人数
	 */
	@Column(name = "BUREAU_LEVEL_COUNT")
	private Integer bureauLevelCount;
	
	/**
	 * 处级编制人数
	 */
	@Column(name = "PART_LEVEL_COUNT")
	private Integer partLevelCount;
	
	/**
	 * 科级编制人数
	 */
	@Column(name = "BRANCH_LEVEL_COUNT")
	private Integer branchLevelCount;
	
	/**
	 * 离退休人员
	 */
	@Column(name = "RETIRED_COUNT")
	private Integer retiredCount;
	
	/**
	 * 遗嘱人员
	 */
	@Column(name = "WILL_COUNT")
	private Integer willCount;
	
	/**
	 * 其他人员
	 */
	@Column(name = "OTHER_COUNT")
	private Integer otherCount;
	
	/**
	 * 经费供给方式编码
	 */
	@Column(name="FUNDS_PROVISION_TYPE_CODE")
	private String fundsProvisionTypeCode;

	/**
	 * 经费供给方式名称
	 */
	@Column(name="FUNDS_PROVISION_TYPE_NAME")
	private String fundsProvisionTypeName;
	
	public Integer getBureauLevelCount() {
		return bureauLevelCount;
	}

	public void setBureauLevelCount(Integer bureauLevelCount) {
		this.bureauLevelCount = bureauLevelCount;
	}

	public Integer getPartLevelCount() {
		return partLevelCount;
	}

	public void setPartLevelCount(Integer partLevelCount) {
		this.partLevelCount = partLevelCount;
	}

	public Integer getBranchLevelCount() {
		return branchLevelCount;
	}

	public void setBranchLevelCount(Integer branchLevelCount) {
		this.branchLevelCount = branchLevelCount;
	}

	public Integer getRetiredCount() {
		return retiredCount;
	}

	public void setRetiredCount(Integer retiredCount) {
		this.retiredCount = retiredCount;
	}

	public Integer getWillCount() {
		return willCount;
	}

	public void setWillCount(Integer willCount) {
		this.willCount = willCount;
	}

	public Integer getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(Integer otherCount) {
		this.otherCount = otherCount;
	}

	public String getFundsProvisionTypeCode() {
		return fundsProvisionTypeCode;
	}

	public void setFundsProvisionTypeCode(String fundsProvisionTypeCode) {
		this.fundsProvisionTypeCode = fundsProvisionTypeCode;
	}

	public String getFundsProvisionTypeName() {
		return fundsProvisionTypeName;
	}

	public void setFundsProvisionTypeName(String fundsProvisionTypeName) {
		this.fundsProvisionTypeName = fundsProvisionTypeName;
	}
	
}
