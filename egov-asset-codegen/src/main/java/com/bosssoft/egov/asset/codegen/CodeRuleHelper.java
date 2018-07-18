package com.bosssoft.egov.asset.codegen;

import java.util.Set;

import com.bosssoft.egov.asset.codegen.rules.ICodeRule;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.extension.ExtensionLoader;

/** 
*
* @ClassName   类名：CodeRuleHelper 
* @Description 功能说明：规则工具类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月7日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月7日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CodeRuleHelper {
   
	public static ICodeRule getCodeRule(String ruleName){
		Set<String> ruleNames = ExtensionLoader.getExtensionLoader(ICodeRule.class).getSupportedExtensions();
        for(String name : ruleNames){
        	ICodeRule codeRule = ExtensionLoader.getExtensionLoader(ICodeRule.class).getExtension(name);
        	if(StringUtilsExt.equalsIgnoreCase(name,ruleName)){
        		return codeRule;
        	}
        }        
		throw new IllegalArgumentException("Unknown CodeRuleType of " + ruleName);
	}
}
