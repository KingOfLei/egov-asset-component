package com.bosssoft.egov.asset.codegen.rules;

import com.bosssoft.egov.asset.codegen.entity.CodegenContext;
import com.bosssoft.platform.common.extension.SPI;

/** 
*
* @ClassName   类名：ICodeRule 
* @Description 功能说明：
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
@SPI
public interface ICodeRule {
	
	public String getResult();	
	public CodeRuleType getCodeRuleType();
	public void init(CodegenContext codegenContext);
}
