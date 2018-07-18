/**
 * 
 */
package com.bosssoft.egov.asset.deprtiming.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Linxt
 *
 */
@Table(name = "AIMS_DEPR_TIMING")
public class AimsDeprTiming {
	
	
	@Id
    @Column(name = "ID")
	private Long id ;
	
	/**
	 * 鍗曚綅缂栫爜
	 */
	@Column(name = "ORG_CODE")
	private String orgCode;
	
	/**
	 * 鍗曚綅鍚嶇О
	 */
	@Column(name = "ORG_NAME")
	private String orgName ;
	
	/**
	 * 鍗曚綅id
	 */
	@Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * 缁忓姙浜�
	 */
	@Column(name = "CREATOR_NAME")
	private String creatorName;
	/**
	 * 缁忓姙浜虹數璇�
	 */
	@Column(name = "CREATOR_TEL")
	private String creatorTel;
	
	/**
	 * 鍗曚綅璐熻矗浜�
	 */
	@Column(name = "ORG_PRINCIPAL")
	private String orgPrincipal;
	/**
	 * 鏄惁鍚敤瀹氭椂鎶樻棫
	 * 1寮�鍚� 0鍏抽棴
	 */
	@Column(name = "IS_DEPR")
	private String isDepr;

	public Long getId() {
		return id;
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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}


	public String getCreatorTel() {
		return creatorTel;
	}

	public void setCreatorTel(String creatorTel) {
		this.creatorTel = creatorTel;
	}

	public String getOrgPrincipal() {
		return orgPrincipal;
	}

	public void setOrgPrincipal(String orgPrincipal) {
		this.orgPrincipal = orgPrincipal;
	}

	public String getIsDepr() {
		return isDepr;
	}

	public void setIsDepr(String isDepr) {
		this.isDepr = isDepr;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
