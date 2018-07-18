package com.bosssoft.egov.asset.codegen.rules;

import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：SeqCodeRule 
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
public class SeqCodeRule extends AbstractCodeRule implements ICodeRule{

	@Override
	public CodeRuleType getCodeRuleType() {
		return CodeRuleType.SEQ;
	}

	@Override
	protected String handle() {
		//序列 = 当前编码 + 步长 
//		this.curNo = this.curNo + this.step;
		return this.curNo + "";
	}

}
