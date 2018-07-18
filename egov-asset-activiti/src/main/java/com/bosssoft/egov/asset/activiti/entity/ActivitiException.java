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

public enum ActivitiException {
	ERROR("0", "请联系技术人员"), BUSNAMENOTFOUND("1", "流程类型不存在！"), 
	TASKNOTFOUND("2", "不当操作！（未提交或者是未操作过的任务进行取消操作！对于当前用户来说任务不存在）"),
	TASKEXIST("3", "该任务已存在!"), NOTREJECT("4", "没有驳回操作！"), 
	NOTRETURN("5", "没有退回操作！"), HISTORYNOTFOUND("6", "历史流程不存在，无法查询节点列表"), 
	GETNODELISEFAIL("7", "获取节点列表失败！"), GETEMPTYREMARK("8", "获取备注历史记录已被删除！"), 
	VERSIONUPDATE("9", "流程版本更新，无法获取相关信息！"), 
	HANDLEERROR("10", "操作码异常，请正确输入！"), BUSINESSIDERROR("11", "无效业务id"),
	USERIDISNULL("12","提交用户不能为空"),GETNEXTEXECUTORFAIL("13","无法获取下一个环节操作者！"), 
	NOTEFFECT("14","无效操作！"),DUPLICATESUBMIT("15","请勿重复提交！"),
	TASKHANDLED("16","任务已被处理"),TEMPDATA("17","暂存数据无法提交"),
	PERMISSIONERROR("18","授权异常！"),DEPLOYROCESSFAIL("19","部署流程失败，可能有流程正在运行中！"),
	FILEISEMPTY("20","上传文件不能为空！"),CREATORISNULL("21","创建者不能为空！"),
	PROCESSNOTEXIT("22","流程暂时无法使用，请稍后！"),
	INAUDIT("23","流程已在其他审核岗位被操作过，无法撤回！"), NOTLASTNOTRETURN("24", "不是最后节点，没有退回操作！"),
	HISTORYOPTIONNOFOUND("25","无法进行取消操作！"),
	GETPERMISSION("26","授权失效，请重新授权！"),
	NOREVERSEHANDLE("27","非法操作，无法进行取消操作！"),
	NOREVERSEHANDLESIMPLE("28","无法进行取消操作"),
	ALREADYPERMISSIONERROR("29","该节点已授权，请删除后再重新授权！"),
	ROLEERROR("30","当前用户没有角色权限！"),
	CANCELERROR("31","不允许撤回！"),
	GETSTATUSFAIL("32","获取状态失败！"),
	GETRIGHTFAIL("33","获取权限失败！"),
	FORWARDUSERISNULL("34","转发任务者不能为空！"),
	DESIGNGROUPSISNULL("35","角色不能为空"),
	GETNEXTUSESRFAIL("36","未找到指定人员，请新增人员！"),
	CURRENTUSERNOTRIGHT("37","无此权限，无法进行该操作"),
	NOPASSCANT("38","已审核，无法进行撤回！"),
	NORETURNACTION("39","已退回的单据无法撤回"),
	
	SUCCESS("888888", "操作成功！");

	String code;
	String name;

	private ActivitiException(String code, String name) {
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

	public static ActivitiException parse(String sideType) {
		try {
			return ActivitiException.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return ActivitiException.ERROR;
		}
	}

}
