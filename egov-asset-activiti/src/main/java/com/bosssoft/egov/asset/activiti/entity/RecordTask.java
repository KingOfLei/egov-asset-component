package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;

/** 
 *
 * @ClassName   类名：RecordTask 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年1月13日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年1月13日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class RecordTask  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2957575004583255095L;

	private String taskDefId;
	
	private String taskId;

	public String getTaskDefId() {
		return taskDefId;
	}

	public void setTaskDefId(String taskDefId) {
		this.taskDefId = taskDefId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskDefId == null) ? 0 : taskDefId.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		RecordTask other = (RecordTask) obj;
		if (taskDefId == null) {
			if (other.taskDefId != null)
				return false;
		} else if (!taskDefId.equals(other.taskDefId))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}
	
}
