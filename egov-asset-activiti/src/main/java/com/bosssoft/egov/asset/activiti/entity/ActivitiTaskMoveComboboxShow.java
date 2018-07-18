package com.bosssoft.egov.asset.activiti.entity;

/**
 *
 * @ClassName 类名：ActivitiTaskMoveComboboxShow
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月14日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年11月14日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
public class ActivitiTaskMoveComboboxShow implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4396194357487774864L;

	// 给流程的id
	private String userTaskId;
	// 给用户看的name
	private String userTaskName;

	public String getUserTaskId() {
		return userTaskId;
	}

	public void setUserTaskId(String userTaskId) {
		this.userTaskId = userTaskId;
	}

	public String getUserTaskName() {
		return userTaskName;
	}

	public void setUserTaskName(String userTaskName) {
		this.userTaskName = userTaskName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userTaskId == null) ? 0 : userTaskId.hashCode());
		result = prime * result + ((userTaskName == null) ? 0 : userTaskName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivitiTaskMoveComboboxShow other = (ActivitiTaskMoveComboboxShow) obj;
		if (userTaskId == null) {
			if (other.userTaskId != null)
				return false;
		} else if (!userTaskId.equals(other.userTaskId))
			return false;
		if (userTaskName == null) {
			if (other.userTaskName != null)
				return false;
		} else if (!userTaskName.equals(other.userTaskName))
			return false;
		return true;
	}

}
