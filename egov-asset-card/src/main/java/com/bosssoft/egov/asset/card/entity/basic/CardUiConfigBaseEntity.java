package com.bosssoft.egov.asset.card.entity.basic;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bosssoft.egov.asset.basic.entity.Entity;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：UiConfig 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Table(name="ASSET_CARD_UI_CONFIG")
public abstract class CardUiConfigBaseEntity extends Entity {
	
	// Fields
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3837734184533192279L;
	/**
	 * 资产类型ID.
	 */
    @Column(name = "ASSET_TYPE_ID")
	private Long assetTypeId;
	/**
	 * 资产类型编码.
	 */
    @Column(name = "ASSET_TYPE_CODE")
	private String assetTypeCode;
	/**
	 * 资产类型名称.
	 */
    @Column(name = "ASSET_TYPE_NAME")
	private String assetTypeName;
	/**
	 * 资产分类ID.
	 */
    @Column(name = "ASSET_CLASSIFY_ID")
	private Long assetClassifyId;
	/**
	 * 资产分类编码.
	 */
    @Column(name = "ASSET_CLASSIFY_CODE")
	private String assetClassifyCode;
	/**
	 * 资产分类名称.
	 */
    @Column(name = "ASSET_CLASSIFY_NAME")
	private String assetClassifyName;
	/**
	 * ui表单地址.
	 */
    @Column(name = "UI_ADDRESS")
	private String uiAddress;
	
    /**
	 * 对应编码生成方式.
	 */
    @Column(name = "BIZ_TYPE")
	private String bizType;
	
    /**
	 * ui标志位.
	 */
    @Column(name = "UI_FLAG")
	private String uiFlag;
    
    /**
	 * 表名.
	 */
    @Column(name = "TABLE_NAME")
	private String tableName;
	
    /**
	 * 条件.
	 */
    @Column(name = "CONDITION")
	private String condition;
    
    @Transient
    private String uiConfigTable;
	
	// Constructors
 
    /** default constructor */
	public CardUiConfigBaseEntity() {
	}

	/**
	 * 资产类型ID.
	 * @return
	 */
	public Long getAssetTypeId() {
		return this.assetTypeId;
	}

	/**
	 * 资产类型ID.
	 * @param assetTypeId
	 */
	public void setAssetTypeId(Long assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	/**
	 * 资产类型编码.
	 * @return
	 */
	public String getAssetTypeCode() {
		return this.assetTypeCode;
	}

	/**
	 * 资产类型编码.
	 * @param assetTypeCode
	 */
	public void setAssetTypeCode(String assetTypeCode) {
		this.assetTypeCode = assetTypeCode;
	}

	/**
	 * 资产类型名称.
	 * @return
	 */
	public String getAssetTypeName() {
		return this.assetTypeName;
	}

	/**
	 * 资产类型名称.
	 * @param assetTypeName
	 */
	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	/**
	 * 资产分类ID.
	 * @return
	 */
	public Long getAssetClassifyId() {
		return this.assetClassifyId;
	}

	/**
	 * 资产分类ID.
	 * @param assetClassifyId
	 */
	public void setAssetClassifyId(Long assetClassifyId) {
		this.assetClassifyId = assetClassifyId;
	}

	/**
	 * 资产分类编码.
	 * @return
	 */
	public String getAssetClassifyCode() {
		return this.assetClassifyCode;
	}

	/**
	 * 资产分类编码.
	 * @param assetClassifyCode
	 */
	public void setAssetClassifyCode(String assetClassifyCode) {
		this.assetClassifyCode = assetClassifyCode;
	}

	/**
	 * 资产分类名称.
	 * @return
	 */
	public String getAssetClassifyName() {
		return this.assetClassifyName;
	}

	/**
	 * 资产分类名称.
	 * @param assetClassifyName
	 */
	public void setAssetClassifyName(String assetClassifyName) {
		this.assetClassifyName = assetClassifyName;
	}

	/**
	 * ui表单地址.
	 * @return
	 */
	public String getUiAddress() {
		return this.uiAddress;
	}

	/**
	 * ui表单地址.
	 * @param uiAddress
	 */
	public void setUiAddress(String uiAddress) {
		this.uiAddress = uiAddress;
	}
	/**
	 * 对应编码生成方式.
	 * @return
	 */
	public String getBizType() {
		return bizType;
	}
	/**
	 * 对应编码生成方式.
	 * @param uiAddress
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	/**
	 * ui标志位.
	 * @return
	 */
	public String getUiFlag() {
		return uiFlag;
	}
	/**
	 * ui标志位.
	 * @param uiAddress
	 */
	public void setUiFlag(String uiFlag) {
		this.uiFlag = uiFlag;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public String getUiConfigTable() {
		return StringUtilsExt.isEmpty(uiConfigTable, getUiConfigTableName());
	}

	public void setUiConfigTable(String uiConfigTable) {
		this.uiConfigTable = uiConfigTable;
	}

	protected abstract String getUiConfigTableName();
}
