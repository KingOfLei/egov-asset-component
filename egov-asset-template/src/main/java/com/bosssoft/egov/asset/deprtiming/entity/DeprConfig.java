/**
 * 
 */
package com.bosssoft.egov.asset.deprtiming.entity;

/**
 * @author Linxt
 *
 */
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bosssoft.egov.asset.basic.entity.Entity;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-09   Administrator　　　新建
 * </pre>
 */
 @Table(name = "AIMS_DEPR_CONFIG")
public class DeprConfig extends Entity {

	private static final long serialVersionUID = 170109142526455L;
	
	// Fields
	
	/**
	 * 单位ID.
	 */
    @Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * 单位编码.
	 */
    @Column(name = "ORG_CODE")
	private String orgCode;
	/**
	 * 单位名称.
	 */
    @Column(name = "ORG_NAME")
	private String orgName;
	/**
	 * 开始折旧月份.
	 */
    @Column(name = "BEGIN_DEPR_MONTH")
	private String beginDeprMonth;
	/**
	 * 累计折旧.
	 */
    @Column(name = "DEPR_TOTAL")
	private BigDecimal deprTotal;
	/**
	 * 资产原值.
	 */
    @Column(name = "INIT_MONEY")
	private BigDecimal initMoney;
    
    //@Transient
    //private String assetType6Code;
    
    @Transient
    private Long deprId;
    
    /**
     * 区划ID
     */
    @Column(name="RGN_ID")
    private Long rgnId;

    /**
     * 区划编码
     */
    @Column(name="RGN_CODE")
    private String rgnCode;
    
    /**
     * 区划名称
     */
    @Column(name="RGN_NAME")
    private String rgnName;
    
    /**
     * 单位预算编码
     */
    @Column(name="FINANCE_BUDGET_CODE")
    private String financeBudgetCode;
    
    /**
     * 当前折旧日期(准备折的：即还为进行折旧的)
     */
    @Column(name="CUR_DEPR_MONTH")
    private String curDeprMonth;
    
    /**
     * 会计准则
     */
    @Column(name="ACCOUNT_STANDARD")
    private String accountStandard;
    
    /**
     * 是否折旧建账
     */
    @Column(name="INIT_DEPR")
    private String initDepr;
    
    /**
     * 资产类型编码
     */
    @Column(name="ASSET_TYPE6_CODE")
    private String assetType6Code;
    
    /**
     * 资产类型名称
     */
    @Column(name="ASSET_TYPE6_NAME")
    private String assetType6Name;
    
    /**
     * 折旧日期来源字段:是以取得日期还是入账日期等，可配置
     */
    @Column(name="DEPR_DATE_SRC")
    private String deprDateSrc;
    
    /**
     * 备注
     */
    @Column(name="REMARK")
    private String remark;
    
    /**
     * 折旧日期来源字段名称
     */
    @Column(name="DEPR_DATE_SRC_NAME")
    private String deprDateSrcName;
    
    /**
     * 业务处理的bean名称
     */
    @Column(name="BUS_PROCESS_BEAN")
    private String busProcessBean;
    
    /**
     * 取得当月开始折旧的表达式
     */
    @Column(name="START_DEPR_ASSET_TYPE")
    private String startDeprAssetType;
    
    /**
     * 取得当月开始折旧的资产分类编码
     */
    @Column(name="START_DEPR_ASSET_TYPE_CODE")
    private String startDeprAssetTypeCode;
    
    /**
     * 取得当月开始折旧的资产分类名称
     */
    @Column(name="START_DEPR_ASSET_TYPE_NAME")
    private String startDeprAssetTypeName;
    
    /**
     * 处置当月不折旧的表达式
     */
    @Column(name="END_DEPR_ASSET_TYPE")
    private String endDeprAssetType;
    
    /**
     * 处置当月不折旧的资产分类编码
     */
    @Column(name="END_DEPR_ASSET_TYPE_CODE")
    private String endDeprAssetTypeCode;
    
    /**
     * 处置当月不折旧的资产分类名称
     */
    @Column(name="END_DEPR_ASSET_TYPE_NAME")
    private String endDeprAssetTypeName;
    
	// Constructors
 
    /** default constructor */
	public DeprConfig() {
	}

	/**
	 * 单位ID.
	 * @return
	 */
	public Long getOrgId() {
		return this.orgId;
	}

	/**
	 * 单位ID.
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 单位编码.
	 * @return
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * 单位编码.
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 单位名称.
	 * @return
	 */
	public String getOrgName() {
		return this.orgName;
	}

	/**
	 * 单位名称.
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * 开始折旧月份.
	 * @return
	 */
	public String getBeginDeprMonth() {
		return this.beginDeprMonth;
	}

	/**
	 * 开始折旧月份.
	 * @param beginDeprMonth
	 */
	public void setBeginDeprMonth(String beginDeprMonth) {
		this.beginDeprMonth = beginDeprMonth;
	}

	/**
	 * 累计折旧.
	 * @return
	 */
	public BigDecimal getDeprTotal() {
		return this.deprTotal;
	}

	/**
	 * 累计折旧.
	 * @param deprTotal
	 */
	public void setDeprTotal(BigDecimal deprTotal) {
		this.deprTotal = deprTotal;
	}

	/**
	 * 资产原值.
	 * @return
	 */
	public BigDecimal getInitMoney() {
		return this.initMoney;
	}

	/**
	 * 资产原值.
	 * @param initMoney
	 */
	public void setInitMoney(BigDecimal initMoney) {
		this.initMoney = initMoney;
	}

	public String getAssetType6Code() {
		return assetType6Code;
	}

	public void setAssetType6Code(String assetType6Code) {
		this.assetType6Code = assetType6Code;
	}
	/**
	 * 区划ID.
	 * @return
	 */
	public Long getRgnId() {
		return rgnId;
	}
	/**
	 * 区划ID.
	 * @param rgnId
	 */
	public void setRgnId(Long rgnId) {
		this.rgnId = rgnId;
	}
	/**
	 * 区划编码.
	 * @return
	 */
	public String getRgnCode() {
		return rgnCode;
	}
	/**
	 * 区划编码.
	 * @param rgnCode
	 */
	public void setRgnCode(String rgnCode) {
		this.rgnCode = rgnCode;
	}
	/**
	 * 区划名称.
	 * @return
	 */
	public String getRgnName() {
		return rgnName;
	}
	/**
	 * 区划名称.
	 * @param rgnName
	 */
	public void setRgnName(String rgnName) {
		this.rgnName = rgnName;
	}
	/**
	 * 单位预算编码.
	 * @return
	 */
	public String getFinanceBudgetCode() {
		return financeBudgetCode;
	}
	/**
	 * 单位预算编码.
	 * @param financeBudetCode
	 */
	public void setFinanceBudgetCode(String financeBudgetCode) {
		this.financeBudgetCode = financeBudgetCode;
	}
	/**
	 * 当前折旧日期.
	 * @return
	 */
	public String getCurDeprMonth() {
		return curDeprMonth;
	}
	/**
	 * 当前折旧日期.
	 * @param curDeprMonth
	 */
	public void setCurDeprMonth(String curDeprMonth) {
		this.curDeprMonth = curDeprMonth;
	}
	/**
	 * 会计准则.
	 * @return
	 */
	public String getAccountStandard() {
		return accountStandard;
	}
	/**
	 * 会计准则.
	 * @param accountStandard
	 */
	public void setAccountStandard(String accountStandard) {
		this.accountStandard = accountStandard;
	}
	
	/**
	 * 是否折旧建账.
	 * @return
	 */
	public String getInitDepr() {
		return initDepr;
	}
	/**
	 * 是否折旧建账.
	 * @param initDepr
	 */
	public void setInitDepr(String initDepr) {
		this.initDepr = initDepr;
	}
	/**
	 * .
	 * @return
	 */
	public String getAssetType6Name() {
		return assetType6Name;
	}
	/**
	 * .
	 * @param assetType6Name
	 */
	public void setAssetType6Name(String assetType6Name) {
		this.assetType6Name = assetType6Name;
	}

	public String getDeprDateSrc() {
		return deprDateSrc;
	}

	public String getRemark() {
		return remark;
	}

	public void setDeprDateSrc(String deprDateSrc) {
		this.deprDateSrc = deprDateSrc;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeprDateSrcName() {
		return deprDateSrcName;
	}

	public void setDeprDateSrcName(String deprDateSrcName) {
		this.deprDateSrcName = deprDateSrcName;
	}

	public String getBusProcessBean() {
		return busProcessBean;
	}

	public void setBusProcessBean(String busProcessBean) {
		this.busProcessBean = busProcessBean;
	}

	public Long getDeprId() {
		return deprId;
	}

	public void setDeprId(Long deprId) {
		this.deprId = deprId;
	}

	public String getStartDeprAssetType() {
		return startDeprAssetType;
	}

	public void setStartDeprAssetType(String startDeprAssetType) {
		this.startDeprAssetType = startDeprAssetType;
	}

	public String getEndDeprAssetType() {
		return endDeprAssetType;
	}

	public void setEndDeprAssetType(String endDeprAssetType) {
		this.endDeprAssetType = endDeprAssetType;
	}

	public String getStartDeprAssetTypeCode() {
		return startDeprAssetTypeCode;
	}

	public void setStartDeprAssetTypeCode(String startDeprAssetTypeCode) {
		this.startDeprAssetTypeCode = startDeprAssetTypeCode;
	}

	public String getStartDeprAssetTypeName() {
		return startDeprAssetTypeName;
	}

	public void setStartDeprAssetTypeName(String startDeprAssetTypeName) {
		this.startDeprAssetTypeName = startDeprAssetTypeName;
	}

	public String getEndDeprAssetTypeCode() {
		return endDeprAssetTypeCode;
	}

	public void setEndDeprAssetTypeCode(String endDeprAssetTypeCode) {
		this.endDeprAssetTypeCode = endDeprAssetTypeCode;
	}

	public String getEndDeprAssetTypeName() {
		return endDeprAssetTypeName;
	}

	public void setEndDeprAssetTypeName(String endDeprAssetTypeName) {
		this.endDeprAssetTypeName = endDeprAssetTypeName;
	}
	
}
