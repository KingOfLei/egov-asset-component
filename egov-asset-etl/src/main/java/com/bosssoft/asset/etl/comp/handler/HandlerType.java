package com.bosssoft.asset.etl.comp.handler;

import java.util.Set;

import com.bosssoft.asset.etl.comp.handler.Handler;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.extension.ExtensionLoader;

/**
 *
 * @ClassName 类名：SupportType
 * @Description 功能说明：支持的类型
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年9月13日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2017年9月13日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public enum HandlerType {

	DUMMY("DummyHandler", "空"),
	
	TXT("TxtHandler", "文本类型"),

	XLS("XlsHandler", "Excel类型"),

	DB("DbHandler", "数据库类型");

	private String typeCode;

	private String typeName;


	private HandlerType(String typeCode, String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public String getTypeName() {
		return typeName;
	}	

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Handler getHandler(){
		Set<String> typeCodes = ExtensionLoader.getExtensionLoader(Handler.class).getSupportedExtensions();
        for(String name : typeCodes){
        	Handler codeRule = ExtensionLoader.getExtensionLoader(Handler.class).getExtension(name);
        	if(StringUtilsExt.equalsIgnoreCase(name, typeCode)){
        		return codeRule;
        	}
        }
        throw new IllegalArgumentException("Unknown typeCode of " + this.typeCode);
	}

	public static HandlerType parse(String configTypeCode){
		try {
			return HandlerType.valueOf(StringUtilsExt.upperCase(configTypeCode));
		} catch (Exception e) {
			return HandlerType.DUMMY;
		}
	}
}
