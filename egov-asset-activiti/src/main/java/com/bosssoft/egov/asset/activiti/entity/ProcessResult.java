package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bosssoft.platform.appframe.api.entity.ApiUser;

/** 
 *
 * @ClassName   类名：ProcessResult 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月1日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月1日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ProcessResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1314877141098408310L;
	
	private Object busId;
	
	private AssetBizStatus info;

	private AssetProcessRemark log;

	private String message;
	
	private String code;
	
	private List<DetailsRecord> records;
	
	private List<ApiUser> nextNodeUser;
	
	private ActivitiParams params;
	
	private String activitiName;
	
	private String currentRoleName;
	
	private String currentRoleCode;
	
	private Integer next2Status;
	
	private String next2UserTaskId;
	
	private String next2TaskName;
	
//	private AssetActivitiConfig aac;
	
	private Map<String,Object> action;
	
	private AssetActivitiBus activitibus;
	
	private List<String> roleCodes;
	
	/**
	 * 有类型则显示相应的类型，当没有类型的时候则显示为null
	 */
	private String formType;
	/**
	 * 未完成前表单类型
	 */
	private String preFormType;
	
	public ProcessResult(){
		this.busId = "";
		this.info = new AssetBizStatus();
		this.message = ActivitiException.ERROR.getName();
		this.code = ActivitiException.ERROR.getCode();
		this.log = new AssetProcessRemark();
		this.records = new ArrayList<DetailsRecord>();
		this.nextNodeUser = new ArrayList<ApiUser>();
		this.params = new ActivitiParams();
		this.next2Status = 0;
		this.currentRoleName = "";
		this.activitibus = new AssetActivitiBus();
		this.roleCodes = new ArrayList<String>();
//		this.aac = new AssetActivitiConfig();
		
	}
	
	public List<ApiUser> getNextNodeUser() {
		return nextNodeUser;
	}

	public void setNextNodeUser(List<ApiUser> nextNodeUser) {
		this.nextNodeUser = nextNodeUser;
	}


	public ActivitiParams getParams() {
		return params;
	}

	public void setParams(ActivitiParams params) {
		this.params = params;
	}

	public void setInfo(AssetBizStatus info) {
		this.info = info;
	}

	public Object getBusId() {
		return busId;
	}

	public void setBusId(Object busId) {
		this.busId = busId;
	}

	public AssetProcessRemark getLog() {
		return log;
	}

	public void setLog(AssetProcessRemark log) {
		this.log = log;
	}
 
	public Integer getBizStatus(){
		return info.getBizStatus();
	}
	
	public String getBIzStatusName(){
		return info.getBizStatusName();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<DetailsRecord> getRecords() {
		return records;
	}

	public void setRecords(List<DetailsRecord> records) {
		this.records = records;
	}
	
	public void setRecordsDetail(DetailsRecord records){
		getRecords().add(records);
	}

	public String getActivitiName() {
		return activitiName;
	}

	public void setActivitiName(String activitiName) {
		this.activitiName = activitiName;
	}

	public String getCurrentRoleName() {
		return currentRoleName;
	}

	public void setCurrentRoleName(String currentRoleName) {
		this.currentRoleName = currentRoleName;
	}

	public Integer getNext2Status() {
		return next2Status;
	}

	public void setNext2Status(Integer next2Status) {
		this.next2Status = next2Status;
	}

//	public AssetActivitiConfig getPermissionStatus() {
//		return aac;
//	}
//
//	public void setPermissionStatus(AssetActivitiConfig aac) {
//		this.aac = aac;
//	}

	public String getCurrentRoleCode() {
		return currentRoleCode;
	}

	public void setCurrentRoleCode(String currentRoleCode) {
		this.currentRoleCode = currentRoleCode;
	}

	public Map<String, Object> getAction() {
		return action;
	}

	public void setAction(Map<String, Object> action) {
		this.action = action;
	}
	
	//获取动作
	public Integer getAction(String actionCode){
		return (Integer) action.get(actionCode);
	}
//	public Integer getAction(){
//		return
//	}

	public AssetActivitiBus getActivitibus() {
		return activitibus;
	}

	public void setActivitibus(AssetActivitiBus activitibus) {
		this.activitibus = activitibus;
	}

	public List<String> getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(List<String> roleCodes) {
		this.roleCodes = roleCodes;
	}

	public String getNext2UserTaskId() {
		return next2UserTaskId;
	}

	public void setNext2UserTaskId(String next2UserTaskId) {
		this.next2UserTaskId = next2UserTaskId;
	}

	public String getNext2TaskName() {
		return next2TaskName;
	}

	public void setNext2TaskName(String next2TaskName) {
		this.next2TaskName = next2TaskName;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getPreFormType() {
		return preFormType;
	}

	public void setPreFormType(String preFormType) {
		this.preFormType = preFormType;
	}
	
}
