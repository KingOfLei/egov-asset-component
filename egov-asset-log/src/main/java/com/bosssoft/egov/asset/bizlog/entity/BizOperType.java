package com.bosssoft.egov.asset.bizlog.entity;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：BizOperType 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月28日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月28日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public enum BizOperType {
	
	
	ADD("ADD", "新增"), 
	UPDATE("UPDATE", "修改"), 
	DELETE("DELETE", "删除"),
	AUDIT("AUDIT","审核"),
	OTHER("OTHER","其他");
	
	String code;
	String name;

	private BizOperType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getCode() + " " + this.getName();
	}

	public static BizOperType parse(String operType) {
		try {
			return BizOperType.valueOf(StringUtilsExt.upperCase(operType));
		} catch (Exception e) {
			return BizOperType.OTHER;
		}
	}
}
