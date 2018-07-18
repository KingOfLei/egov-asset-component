package com.bosssoft.egov.asset.codegen.rules;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：ConstCodeRule 
* @Description 功能说明：
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
@Activate
public class ConstCodeRule extends AbstractCodeRule implements ICodeRule{

	@Override
	public CodeRuleType getCodeRuleType() {
		return CodeRuleType.CONST;
	}

	@Override
	protected String handle() {
        //返回规则值 常量
		return StringUtilsExt.isNotEmpty(this.ruleValue)
				? this.ruleValue : StringUtilsExt.EMPTY;
	}

}
