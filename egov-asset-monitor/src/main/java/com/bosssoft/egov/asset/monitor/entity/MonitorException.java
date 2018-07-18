package com.bosssoft.egov.asset.monitor.entity;

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

public enum MonitorException {
	ERROR("0", "错误"), MONITOREVTISEMPTY("1", "监控事项ID不能为空！"),  MONITOREVTIDISEMPTY("2", "监控事项ID不能为空！"), NOEXCUTEMONITOR("3", "当前没有要执行的监控事项"), MONITORMODELIDISEMPTY("4", "监控数据模型ID不能为空！"), MODELDATANOTEXIST("5", "监控数据模型不存在"),
		GETATTRERROR("6", "获取属性值时出错"), CHOOSEMONITOR("7", "请选择要启用的监控事项"), GETEMPTYREMARK("8", "获取remark历史表为空"), VERSIONUPDATE("9", "可能由于流程版本更新导致无法获取相关信息获取出错！"), HANDLEERROR("10", "操作码异常，请正确输入！"), BUSINESSIDERROR(
			"11", "无效业务id"),USERIDISNULL("12","提交用户不能为空"),GETNEXTEXECUTORFAIL("13","获取下一个节点操作者失败！"), NOTEFFECT("14","无效操作！"),DUPLICATESUBMIT("15","请勿重复提交！"),TASKHANDLED("16","任务已被处理"),TEMPDATA("17","暂存数据无法提交"),PERMISSIONERROR("18","授权异常！")
			,DEPLOYROCESSFAIL("19","部署流程失败！"),FILEISEMPTY("20","上传文件不能为空！"),CREATORISNULL("21","创建者不能为空！"),SUCCESS("999999", "成功操作！");

	String code;
	String name;

	private MonitorException(String code, String name) {
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

	public static MonitorException parse(String sideType) {
		try {
			return MonitorException.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return MonitorException.ERROR;
		}
	}

}
