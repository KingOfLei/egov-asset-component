package com.bosssoft.egov.asset.aims.api.account;

/** 
*
* @ClassName   类名：AccountBizTypeEnum 
* @Description 功能说明：业务类型
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月4日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月4日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public enum AccountBizTypeEnum {
	NULL("","空类型"),
	CARD("CARD","卡片新增"),
	SPLIT("SPLIT","卡片拆分"),
	CHANGE("CHANGE","卡片变动"),	
	DISPOSE("DISPOSE","资产处置"),
	DEPR("DEPR","累计折旧"),
	USE("USE","使用"),
	RECOVER("RECOVER","收回"),
	ASSET_ACCOUNT("ASSET_ACCOUNT","对账申请");
	  
	private String bizTypeCode;

	private String bizTypeName;

	private AccountBizTypeEnum(String bizTypeCode, String bizTypeName) {
		this.bizTypeCode = bizTypeCode;
		this.bizTypeName = bizTypeName;
	}

	public String getBizTypeCode() {
		return bizTypeCode;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeCode(String bizTypeCode) {
		this.bizTypeCode = bizTypeCode;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	  
	@Override
	public String toString() {
		return this.getBizTypeCode() + this.getBizTypeName();
	}
  
	public static AccountBizTypeEnum parse(String bizTypeCode){
		try {
			return AccountBizTypeEnum.valueOf((bizTypeCode == null ) ? null : bizTypeCode.toUpperCase());
		} catch (Exception e) {
			return AccountBizTypeEnum.NULL;
		}
	}
}
