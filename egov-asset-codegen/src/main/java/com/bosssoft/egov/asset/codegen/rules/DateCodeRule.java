package com.bosssoft.egov.asset.codegen.rules;

import java.util.Date;

import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：DateCodeRule 
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
public class DateCodeRule extends AbstractCodeRule implements ICodeRule{

	@Override
	public CodeRuleType getCodeRuleType() {
		// TODO Auto-generated method stub
		return CodeRuleType.DATE;
	}

	@Override
	protected String handle() {
		//格式化值 如YYYY-MM-DD
		return DateUtilsExt.formatDate(new Date(), this.ruleValue);
	}

}
