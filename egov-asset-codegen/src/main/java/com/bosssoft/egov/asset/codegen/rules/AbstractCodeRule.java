package com.bosssoft.egov.asset.codegen.rules;

import com.bosssoft.egov.asset.codegen.PaddingSide;
import com.bosssoft.egov.asset.codegen.entity.CodegenContext;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：AbstractCodeRule 
* @Description 功能说明：规则抽象类
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
public abstract class AbstractCodeRule implements ICodeRule{
	
	protected PaddingSide paddingSide = PaddingSide.NULL;//填充样式
	protected int curNo = 0;//当前流水号值
	protected String ruleValue = "";
	protected int step = 1;//步长
	
	protected int length = 0;
	//填充字符
	protected String fillChar = "";
	
	protected CodegenContext codegenContext;
	
	@Override
	public void init(CodegenContext codegenContext) {		
		//初始化赋值	
		this.codegenContext = codegenContext;
		this.paddingSide = codegenContext.getPaddingSide();
		this.curNo = codegenContext.getCurNo();
		this.ruleValue = StringUtilsExt.isEmpty(codegenContext.getRuleValue()) ? "" : codegenContext.getRuleValue();
		this.step = NumberUtilsExt.toInt(codegenContext.getStep() + "", 1);
		this.length = NumberUtilsExt.toInt(codegenContext.getLength()+"", 0);
		this.fillChar = StringUtilsExt.isEmpty(codegenContext.getFillChar()) ? "" : codegenContext.getFillChar();	
	}
	
	@Override
	public String getResult() {
		String result = handle();		
		//空值处理
		result = StringUtilsExt.isEmpty(result) ? "" : result;
		//根据获取的值 格式化
        //填充字符
		if(this.paddingSide == PaddingSide.LEFT && this.length > 0){
			result = StringUtilsExt.lPad(result, this.fillChar, this.length);
		} else if (this.paddingSide == PaddingSide.RIGHT && this.length > 0){
			result = StringUtilsExt.rPad(result, this.fillChar, this.length);
		}
		return result;
	}
	
    protected abstract String handle();

}
