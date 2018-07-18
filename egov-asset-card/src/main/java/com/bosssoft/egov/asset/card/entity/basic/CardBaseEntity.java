package com.bosssoft.egov.asset.card.entity.basic;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Id;

import com.bosssoft.egov.asset.basic.entity.BizEntity;

/** 
*
* @ClassName   类名：BaseCardEntity 
* @Description 功能说明：卡片实体基类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public abstract class CardBaseEntity extends BizEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -539440534269578066L;

	/**
	 * 资产ID.
	 */
	@Id
	@Column(name = "ASSET_ID")
	private Long assetId;
	/**
	 * 资产编码.
	 */
	@Column(name = "ASSET_CODE")
	private String assetCode;
	/**
	 * 资产名称.
	 */
	@Column(name = "ASSET_NAME")
	private String assetName;
	/**
	 * 资产类型编码.
	 */
	@Column(name = "ASSET_TYPE6_CODE")
	private String assetType6Code;
	/**
	 * 资产类型名称.
	 */
	@Column(name = "ASSET_TYPE6_NAME")
	private String assetType6Name;
	/**
	 * 资产分类编码.
	 */
	@Column(name = "ASSET_CLASSIFY6_CODE")
	private String assetClassify6Code;
	/**
	 * 资产分类名称.
	 */
	@Column(name = "ASSET_CLASSIFY6_NAME")
	private String assetClassify6Name;
	/**
	 * 资产类型编码.
	 */
	@Column(name = "ASSET_TYPE16_CODE")
	private String assetType16Code;
	/**
	 * 资产类型名称.
	 */
	@Column(name = "ASSET_TYPE16_NAME")
	private String assetType16Name;
	/**
	 * 资产分类编码.
	 */
	@Column(name = "ASSET_CLASSIFY16_CODE")
	private String assetClassify16Code;
	/**
	 * 资产分类名称.
	 */
	@Column(name = "ASSET_CLASSIFY16_NAME")
	private String assetClassify16Name;
	
	/**
	 * 业务状态.
	 */
	@Column(name = "BIZ_STATUS")
	private Integer bizStatus;
	/**
	 * 业务状态名称.
	 */
	@Column(name = "BIZ_STATUS_NAME")
	private String bizStatusName;
	
	/**
	 * 初始数量.
	 */
	@Column(name = "INIT_AMOUNT")
	private BigDecimal initAmount;
	/**
	 * 初始金额.
	 */
	@Column(name = "INIT_MONEY")
	private BigDecimal initMoney;
	/**
	 * 当前数量.
	 */
	@Column(name = "CUR_AMOUNT")
	private BigDecimal curAmount;
	/**
	 * 当前金额.
	 */
	@Column(name = "CUR_MONEY")
	private BigDecimal curMoney;
	/**
	 * 财政性资金.
	 */
	@Column(name = "FINANCIAL_FUND")
	private BigDecimal financialFund;
	/**
	 * 非财政性资金.
	 */
	@Column(name = "OTHER_FUND")
	private BigDecimal otherFund;
	
	public Long getAssetId() {
		return assetId;
	}
	public void setAssetId(Long assetId) {
		this.assetId = assetId;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getAssetType6Code() {
		return assetType6Code;
	}
	public void setAssetType6Code(String assetType6Code) {
		this.assetType6Code = assetType6Code;
	}
	public String getAssetType6Name() {
		return assetType6Name;
	}
	public void setAssetType6Name(String assetType6Name) {
		this.assetType6Name = assetType6Name;
	}
	public String getAssetClassify6Code() {
		return assetClassify6Code;
	}
	public void setAssetClassify6Code(String assetClassify6Code) {
		this.assetClassify6Code = assetClassify6Code;
	}
	public String getAssetClassify6Name() {
		return assetClassify6Name;
	}
	public void setAssetClassify6Name(String assetClassify6Name) {
		this.assetClassify6Name = assetClassify6Name;
	}
	public String getAssetType16Code() {
		return assetType16Code;
	}
	public void setAssetType16Code(String assetType16Code) {
		this.assetType16Code = assetType16Code;
	}
	public String getAssetType16Name() {
		return assetType16Name;
	}
	public void setAssetType16Name(String assetType16Name) {
		this.assetType16Name = assetType16Name;
	}
	public String getAssetClassify16Code() {
		return assetClassify16Code;
	}
	public void setAssetClassify16Code(String assetClassify16Code) {
		this.assetClassify16Code = assetClassify16Code;
	}
	public String getAssetClassify16Name() {
		return assetClassify16Name;
	}
	public void setAssetClassify16Name(String assetClassify16Name) {
		this.assetClassify16Name = assetClassify16Name;
	}
	public BigDecimal getInitAmount() {
		return initAmount;
	}
	public void setInitAmount(BigDecimal initAmount) {
		this.initAmount = initAmount;
	}
	public BigDecimal getInitMoney() {
		return initMoney;
	}
	public void setInitMoney(BigDecimal initMoney) {
		this.initMoney = initMoney;
	}
	public BigDecimal getCurAmount() {
		return curAmount;
	}
	public void setCurAmount(BigDecimal curAmount) {
		this.curAmount = curAmount;
	}
	public BigDecimal getCurMoney() {
		return curMoney;
	}
	public void setCurMoney(BigDecimal curMoney) {
		this.curMoney = curMoney;
	}
	public BigDecimal getFinancialFund() {
		return financialFund;
	}
	public void setFinancialFund(BigDecimal financialFund) {
		this.financialFund = financialFund;
	}
	public BigDecimal getOtherFund() {
		return otherFund;
	}
	public void setOtherFund(BigDecimal otherFund) {
		this.otherFund = otherFund;
	}
	public Integer getBizStatus() {
		return bizStatus;
	}
	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}
	public String getBizStatusName() {
		return bizStatusName;
	}
	public void setBizStatusName(String bizStatusName) {
		this.bizStatusName = bizStatusName;
	}
	
}
