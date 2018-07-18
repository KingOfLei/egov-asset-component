package com.bosssoft.egov.asset.codegen;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：CodeGenRule 
* @Description 功能说明：编码生成规则枚举 是点击新增时计算编码 还是新增保存时才计算编码
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
public enum CodeGenRule {
    ADD("ADD","新增时生成编码"),
    SAVE("SAVE","保存时生成编码");
    
	String code;
	String name;
	
	private CodeGenRule(String code, String name){
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
	
	public static CodeGenRule parse(String ruleType){
		try {
			return CodeGenRule.valueOf(StringUtilsExt.upperCase(ruleType));
		} catch (Exception e) {
			return CodeGenRule.ADD;
		}
	}
}
