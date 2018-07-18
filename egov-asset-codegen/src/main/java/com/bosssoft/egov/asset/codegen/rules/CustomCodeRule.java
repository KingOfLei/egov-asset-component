package com.bosssoft.egov.asset.codegen.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.codegen.rules.custom.ICustomCodeRule;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：CustomCodeRule 
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
@Activate
public class CustomCodeRule extends AbstractCodeRule implements ICodeRule{
	
	protected static Logger logger = LoggerFactory.getLogger(CustomCodeRule.class);

	@Override
	public CodeRuleType getCodeRuleType() {
		return CodeRuleType.CUSTOM;
	}

	@Override
	protected String handle() {
        //获取到 对应的自定义class 类		
		ICustomCodeRule codeRule = new AbstractCustomCodeRule();
		Object customClass = AppContext.getAppContext().getBeanContext().getBean(this.ruleValue);
		if (customClass instanceof ICustomCodeRule) {
			codeRule = (ICustomCodeRule) customClass;
		} else {
			logger.warn("====类对象{}未找到!=====",this.ruleValue);
		}		
		return codeRule.handle(codegenContext);
	}

}
