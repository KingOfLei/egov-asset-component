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
public enum ActivitiNextUserStatus {
	CONST(-1, "错误"), UNDID(0, "未办"), 
	DID(1, "已办"), FINISH(9, "办结"),
	
	DELETE(2,"删除任务"),YILURU(10,"已录入"),
	REJECT(9,"驳回"),ENDREJECT(12,"已终审卡片驳回！"),
	RETURNANDREJECT(13,"退回/驳回"),
	DEPLOYSUCCESS(14,"部署流程成功！"),
	RETUNR(50,"退回"),RUNNING(20,"正在运行！"),
	BUSINESSSUCCESS(999999,"业务成功！"),
	
	END(21,"结束！");

	Integer status;
	String name;

	private ActivitiNextUserStatus(Integer status, String name) {
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

	public static ActivitiNextUserStatus parse(String sideType) {
		try {
			return ActivitiNextUserStatus.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return ActivitiNextUserStatus.CONST;
		}
	}

}
