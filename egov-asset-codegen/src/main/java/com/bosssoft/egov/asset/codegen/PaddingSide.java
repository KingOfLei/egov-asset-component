package com.bosssoft.egov.asset.codegen;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：PaddingSide 
* @Description 功能说明：补齐方式枚举类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public enum PaddingSide {
	
    LEFT("LEFT","左补齐"),
    RIGHT("RIGHT","右补齐"),
    NULL("NULL","无");
	
	String code;
	String name;
	
	private PaddingSide(String code, String name){
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
		return this.getCode() + this.getName();
	}
	
	public static PaddingSide parse(String sideType){
		try {
			return PaddingSide.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return PaddingSide.NULL;
		}
	}
}
