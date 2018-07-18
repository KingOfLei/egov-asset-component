package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ClassName 类名：ActivitiParams
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
public class ActivitiParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 242136555264026235L;
	/**
	 * 业务id
	 */
	private Object businessId;
	/**
	 * 登陆者标识
	 */
	private String userCode;
	/**
	 * 登陆者标识
	 */
	private String userName;
	/**
	 * 流程条件，业务换算成200左右的int
	 */
	//private Integer condition;
	/**
	 * 流程条件控制
	 */
	private Map<String,Object> condition;
	/**
	 * 操作码，退回，驳回，通过等等
	 */
	private Integer handle;
	/**
	 * 业务类型，业务与流程对应的标识
	 */
	private String busType;
	/**
	 * 审核结果，用于查询历史操作等
	 */
	private Integer checkResult;

	/**
	 * 流程启动数据权限唯一标识
	 */
	private Map<String, Object> startKeyMap;

	/**
	 * 待办查询与startKeyMap是否一致，数据权限控制
	 */
	private Map<String, Object> findKeyMap;

	/**
	 * 到期时间
	 */
	private String dueDate;
	/**
	 * 任务Id
	 */
	private String taskId;
	/**
	 * 流程id
	 */
	private String processInstanceId;
	// 只退一步编号
	private String backCode;
	// 移动的目标节点id
	private String userTaskId;

	/**
	 * 任务批注
	 */
	private String comment;

	private String deploymentId;

	/**
	 * 图片名称或者部署文件名称
	 */
	private String resourceName;

	/**
	 * 业务单据创建者
	 */
	private String creatorName;
	/**
	 * 业务单据创建者
	 */
	private String creatorId;
	/**
	 * 业务单据创建者code
	 */
	private String creatorCode;

	private List<DetailsRecord> records;

	/**
	 * 业务当前状态
	 */
	private Integer busState;

	/**
	 * 待办输出类型
	 */
	private Integer toDoType;

	/**
	 * 批注日期格式化
	 */
	private String commentTimePattern;
	
	/**
	 * 耗时格式
	 */
	private String spendTimePattern;

	
	private String defId;
	
	private String deploymentKey;
	
	private String message;
	/**
	 * 创建时间（原始单据创建时间）
	 */
	private String createTime;
	
	private String rgnId;
	
	private String rgnCode;
	
	private String rgnName;
	
	/**
	 * 任务转发者，用英文逗号隔开
	 */
	private String forwardOrDesignUsers;
	/**
	 * 任务转发组，用英文逗号隔开
	 */
	private String forwardOrDesignGroups;
	/**
	 * 当前用户角色 String[]
	 */
	private String [] currentRoles;
	/**
	 * busType
	 */
	private AssetActivitiBus activitiBus;

	/**
	 * 任务转发者，用英文逗号隔开
	 */
	private String applyuserCode;
	
	/**
	 * 单据orgCode，用于不同单位间的调拨
	 */
	private String billOrgCode;
	/**
	 * 单据orgCode，用于不同单位间的调拨
	 */
	private String orgCode;
	
	/**
	 * 暂存还是直接提交
	 * true 暂存
	 * false 直接提交
	 */
	private boolean temp;
	
	/**
	 * 跳转标识
	 */
	private String moveUserTaskDef;
	
	private String userRoleName;
	
	private String userRoleCode;
	
	public ActivitiParams() {
		this.startKeyMap = new HashMap<String, Object>();
		this.findKeyMap = new HashMap<String, Object>();
		this.records = new ArrayList<DetailsRecord>();
		this.toDoType = ToDoListType.BIG.getType();
		this.condition = new HashMap<String,Object>();
		this.commentTimePattern = "yyyy-MM-dd HH:mm";
		this.spendTimePattern = "yyyy-MM-dd HH:mm";
		this.temp = true;
		this.moveUserTaskDef = null;
//		this.activitiBus = new AssetActivitiBus();
		//this.condition = 0;
	}

	public String getCommentTimePattern() {
		return commentTimePattern;
	}

	public void setCommentTimePattern(String commentTimePattern) {
		this.commentTimePattern = commentTimePattern;
	}

	public Integer getToDoType() {
		return toDoType;
	}

	public void setToDoType(Integer toDoType) {
		this.toDoType = toDoType;
	}

	public Map<String, Object> getStartKeyMap() {
		return startKeyMap;
	}

	public void setStartKeyMap(Map<String, Object> startKeyMap) {
		this.startKeyMap = startKeyMap;
	}

	public Map<String, Object> getFindKeyMap() {
		return findKeyMap;
	}

	public void setFindKeyMap(Map<String, Object> findKeyMap) {
		this.findKeyMap = findKeyMap;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public Integer getBusState() {
		return busState;
	}

	public void setBusState(Integer busState) {
		this.busState = busState;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

//	public Integer getCondition() {
//		return condition;
//	}
//
//	public void setCondition(Integer condition) {
//		this.condition = condition;
//	}

	public List<DetailsRecord> getRecords() {
		return records;
	}

	public void setRecords(List<DetailsRecord> records) {
		this.records = records;
	}

	public Object getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Object businessId) {
		this.businessId = businessId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getHandle() {
		return handle;
	}

	public void setHandle(Integer handle) {
		this.handle = handle;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	// public String getRemark() {
	// return remark;
	// }
	//
	// public void setRemark(String remark) {
	// this.remark = remark;
	// }

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	// public Object[] getBatchIds() {
	// return batchIds;
	// }
	//
	// public void setBatchIds(Object[] batchIds) {
	// this.batchIds = batchIds;
	// }

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public String getUserTaskId() {
		return userTaskId;
	}

	public void setUserTaskId(String userTaskId) {
		this.userTaskId = userTaskId;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorCode() {
		return creatorCode;
	}

	public void setCreatorCode(String creatorCode) {
		this.creatorCode = creatorCode;
	}

	public Map<String,Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String,Object> condition) {
		this.condition = condition;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getSpendTimePattern() {
		return spendTimePattern;
	}

	public void setSpendTimePattern(String spendTimePattern) {
		this.spendTimePattern = spendTimePattern;
	}
	
	public void addCondtion(String key,Object object){
		this.condition.put(key, object);
	}

	public String getDeploymentKey() {
		return deploymentKey;
	}

	public void setDeploymentKey(String deploymentKey) {
		this.deploymentKey = deploymentKey;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getForwardOrDesignUsers() {
		return forwardOrDesignUsers;
	}

	public void setForwardOrDesignUsers(String forwardOrDesignUser) {
		this.forwardOrDesignUsers = forwardOrDesignUser;
	}

	public String getForwardOrDesignGroups() {
		return forwardOrDesignGroups;
	}

	public void setForwardOrDesignGroups(String forwardOrDesignGroups) {
		this.forwardOrDesignGroups = forwardOrDesignGroups;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRgnId() {
		return rgnId;
	}

	public void setRgnId(String rgnId) {
		this.rgnId = rgnId;
	}

	public String getRgnCode() {
		return rgnCode;
	}

	public void setRgnCode(String rgnCode) {
		this.rgnCode = rgnCode;
	}

	public String getRgnName() {
		return rgnName;
	}

	public void setRgnName(String rgnName) {
		this.rgnName = rgnName;
	}

	public String[] getCurrentRoles() {
		return currentRoles;
	}

	public void setCurrentRoles(String[] currentRoles) {
		this.currentRoles = currentRoles;
	}

	public AssetActivitiBus getActivitiBus() {
		return activitiBus;
	}

	public void setActivitiBus(AssetActivitiBus activitiBus) {
		this.activitiBus = activitiBus;
	}

	public String getApplyuserCode() {
		return applyuserCode;
	}

	public void setApplyuserCode(String applyuserCode) {
		this.applyuserCode = applyuserCode;
	}

	public String getBillOrgCode() {
		return billOrgCode;
	}

	public void setBillOrgCode(String billOrgCode) {
		this.billOrgCode = billOrgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public boolean isTemp() {
		return temp;
	}

	public void setTemp(boolean temp) {
		this.temp = temp;
	}

	public String getMoveUserTaskDef() {
		return moveUserTaskDef;
	}

	public void setMoveUserTaskDef(String moveUserTaskDef) {
		this.moveUserTaskDef = moveUserTaskDef;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public String getUserRoleCode() {
		return userRoleCode;
	}

	public void setUserRoleCode(String userRoleCode) {
		this.userRoleCode = userRoleCode;
	}
	
}
