package com.bosssoft.egov.asset.activiti.entity;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 *
 * @ClassName 类名：TaskStatus
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
public enum TaskStatus {

	CONST(-1, "错误"), 
	ZANCUN(1,"暂存"),
	SUBMIT(0, "新建"), 
	CHECKING(11, "正在审核"), FINISH(19, "已终审"),
	DELETE(12,"删除"),YILURU(10,"已录入"),
	REJECT(9,"退回"),ENDREJECT(12,"已终审驳回"),
	RETURNANDREJECT(13,"退回/驳回"),
	DEPLOYSUCCESS(14,"部署流程成功！"),
	DAITIJIAO(15,"待提交"),
	RETUNR(50,"退回"),RUNNING(20,"正在运行！"),
	BUSINESSSUCCESS(999999,"已入账"),
	NEGATE(3,"报废"),
	REJECTTOSTART(7,"驳回"),
	NEW(8,"新建"),
	
	END(21,"结束！");

	Integer status;
	String name;

	private TaskStatus(Integer status, String name) {
		this.status = status;
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getStatus() + this.getName();
	}

	public static TaskStatus parse(String sideType) {
		try {
			return TaskStatus.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return TaskStatus.CONST;
		}
	}

}
