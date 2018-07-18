package com.bosssoft.egov.asset.codegen.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.bosssoft.egov.asset.codegen.CodeGenRule;
import com.bosssoft.egov.asset.codegen.PaddingSide;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 *
 * @ClassName 类名：CodegenContext
 * @Description 功能说明：编码规则上下文
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月7日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年12月7日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class CodegenContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6887731958936860167L;
	public static final String ORG_ID_KEY = "orgId";

	private AssetCodegenConfig config;

	private AssetCodegenRule rule;

	private PaddingSide paddingSide = PaddingSide.NULL;// 填充样式

	private CodeGenRule codeGenRule = CodeGenRule.ADD;// 生成策略

	private int curNo = 0;// 当前流水号值

	private String ruleValue = "";

	private int step = 1;// 步长

	private int length = 0;

	// 填充字符
	private String fillChar = "";

	// 额外参数
	private Map<String, Object> params = new HashMap<String, Object>();

	public AssetCodegenConfig getConfig() {
		return config;
	}

	public void setConfig(AssetCodegenConfig config) {
		this.config = config;
	}

	public AssetCodegenRule getRule() {
		return rule;
	}

	public void setRule(AssetCodegenRule rule) {
		this.rule = rule;
	}

	public PaddingSide getPaddingSide() {
		return paddingSide;
	}

	public void setPaddingSide(PaddingSide paddingSide) {
		this.paddingSide = paddingSide;
	}

	public CodeGenRule getCodeGenRule() {
		return codeGenRule;
	}

	public void setCodeGenRule(CodeGenRule codeGenRule) {
		this.codeGenRule = codeGenRule;
	}

	public int getCurNo() {
		return curNo;
	}

	public void setCurNo(int curNo) {
		this.curNo = curNo;
	}

	public String getRuleValue() {
		return ruleValue;
	}

	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getFillChar() {
		return fillChar;
	}

	public void setFillChar(String fillChar) {
		this.fillChar = fillChar;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Long getOrgId(){
		return NumberUtilsExt.toLong(StringUtilsExt.convertNullToString(this.params.get(CodegenContext.ORG_ID_KEY)),0);
	}
}
