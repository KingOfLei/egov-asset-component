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
 * @date 创建日期：2016年12月15日
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

public enum CheckResult {
	CONST(-1, "错误"), RETURN(1, "退回"), JUMP(2, "跳转"), CANCELPASS(3, "取消审核"), CANCELRETURN(4, "取消退回"), SUBMIT(5, "提交任务"), CANCELSUBMIT(6, "取消提交"), REJECT(8, "驳回"), PASS(9, "通过审核")/*, BATCHREJECT(10,
			"批量驳回"), BATCHRETURN(11, "批量退回"), BATCHPASS(12, "批量通过"), BATCHCANCELPASS(13, "批量取消通过"), BATCHCANCELRETURN(14, "批量取消退回"), BATCHCANCELSUBMIT(15, "批量取消提交")*/;

	Integer handle;
	String name;

	private CheckResult(Integer handle, String name) {
		this.handle = handle;
		this.name = name;
	}

	public Integer getHandle() {
		return handle;
	}

	public void setHandle(Integer handle) {
		this.handle = handle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getHandle() + this.getName();
	}

	public static CheckResult parse(String sideType) {
		try {
			return CheckResult.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return CheckResult.CONST;
		}
	}

}
