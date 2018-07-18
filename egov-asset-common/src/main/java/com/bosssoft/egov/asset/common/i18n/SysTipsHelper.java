package com.bosssoft.egov.asset.common.i18n;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：SysTipsHelper 
* @Description 功能说明：系统提示帮助类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年11月8日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年11月8日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class SysTipsHelper {

	//路径地址 默认规则为：
	//messages/card/messages_zh_CN.properties
	//此变量为 messages/{0}/messages_{1}.properties
	private static final String BUNDLE_NAME  = "messages/{0}/messages"; 
	
	public static String getString(BizType bizType,String key){
		return getString(bizType, key,new String[] {});
	}
	
	public static String getString(BizType bizType, String key, String... parameters){
		String name = StringUtilsExt.formatString(BUNDLE_NAME, bizType.getCode());
		//messages/card/messages_zh_CN.properties
		String _key = key;
		return getString(bizType.getCode(),_key, name, parameters);
	}
	
	private static String getString(String bizCode, String key, String fileName, String... parameters){
		String string = ResourceMessagesResolver.getInstance().getString(key, fileName, parameters); 
		return StringUtilsExt.isBlank(string) ? "" : StringUtilsExt.join(string,"(" + bizCode + key + ")");
	}

	
	public enum BizType {
		CARD("KP","卡片"),		
		USE("SY","使用"),
		DISPOSE("CZ","处置"),
		DEPR("ZJ","折旧"),
		INVENTORY("PD","盘点"),		
		WORKFLOW("GZL","工作流"),
		ACCOUNT("HS","核算"),
		REPORT("BB","报表");
		
		String code;
		String name;
		
		private BizType(String code, String name){
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
}
