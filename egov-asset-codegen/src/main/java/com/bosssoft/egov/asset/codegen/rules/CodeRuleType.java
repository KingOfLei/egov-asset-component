package com.bosssoft.egov.asset.codegen.rules;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：CodeRuleType 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public enum CodeRuleType {
    CONST("ConstCodeRule","常量"),
    SEQ("SeqCodeRule","流水号"),
    DATE("DateCodeRule","日期类型"),
    CUSTOM("CustomCodeRule","自定义");
	
	String code;
	String name;
	
	private CodeRuleType(String code, String name){
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
	
	public static CodeRuleType parse(String sideType){
		try {
			return CodeRuleType.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return CodeRuleType.CONST;
		}
	}
}
