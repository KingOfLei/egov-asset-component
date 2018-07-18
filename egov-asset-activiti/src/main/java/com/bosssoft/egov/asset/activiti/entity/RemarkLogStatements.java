package com.bosssoft.egov.asset.activiti.entity;
/** 
 *
 * @ClassName   类名：RemarkLogStatements 
 * @Description 功能说明：存储批注已经生成的操作日志语句，用于前端onclick事件查看批注等
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月23日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月23日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class RemarkLogStatements {
	
	private String statement;
	
	private String remark;
	
	private String time;
	
	private String yearTime;
	
	private String hourTime;
	
	private String nodeName;
	
	private String executor;
	
	private String excutorPhoneNumber;
	
	private String startTime;

	private String endTime;
	
	private String totalCostTime;
	
	private String description;
	
	private Integer type;
	
	private String roleCode;
	
	private String roleName;

	
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getYearTime() {
		return yearTime;
	}

	public void setYearTime(String yearTime) {
		this.yearTime = yearTime;
	}

	public String getHourTime() {
		return hourTime;
	}

	public void setHourTime(String hourTime) {
		this.hourTime = hourTime;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getExcutorPhoneNumber() {
		return excutorPhoneNumber;
	}

	public void setExcutorPhoneNumber(String excutorPhoneNumber) {
		this.excutorPhoneNumber = excutorPhoneNumber;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTotalCostTime() {
		return totalCostTime;
	}

	public void setTotalCostTime(String totalCostTime) {
		this.totalCostTime = totalCostTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
