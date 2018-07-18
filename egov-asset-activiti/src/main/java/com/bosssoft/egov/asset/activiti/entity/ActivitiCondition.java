package com.bosssoft.egov.asset.activiti.entity;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 *
 * @ClassName 类名：AuditType
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @，date 创建日期：2016年12月15日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年12月15日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */

// @param 5.handle
// 操作审核处理结果(1退回，2跳转，3取消审核，4取消退回，5提交任务，6取消提交,8:驳回,9通过审核,10批量驳回,11批量退回，12批量通过,13批量取消审核，14批量取消退回，批量取消提交15）

public enum ActivitiCondition {
	ERROR("0","错误"),
	BILL_TYPE("bill_type","单据类型"),
	BILL_SYSTEM("bill_system","系统内外"),
	BILL_MONEY("bill_money","金额总数"),
	BILL_AREA("bill_area","面积总数"),
	BILL_MAX("bill_max","最大值"),
	BILL_MIN("bill_min","最小值"),
	YES_OR_NOT("yesOrNo","有或没有"),
	BRANCH_CONTROL("branch_control","分支控制"),
	ORG_CODE("org_code","单位信息"),
	REASON_CONDITION("reason_condition","原因条件"),
	SUBMIT_SOURCE("submit_source","提交来源"),
	SUBMIT_ROLE_CODE("submit_role_code","提交者角色编码"),
	
	;
	String code;
	String name;

	private ActivitiCondition(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getCode() + this.getName();
	}

	public static ActivitiCondition parse(String sideType) {
		try {
			return ActivitiCondition.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return ActivitiCondition.ERROR;
		}
	}

}
