package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;

/**
 *
 * @ClassName 类名：ActivitiNode
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月20日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年12月20日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
public class LastNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6183398521664508090L;

	private String nodeName;
	/**
	 * 网关或者正常的节点，进入检测
	 */
	private String userTaskId;
	
	private boolean isLast;

	
	public LastNode() {
		this.isLast = false;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}



	public boolean isLast() {
		return isLast;
	}


	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}


	public String getUserTaskId() {
		return userTaskId;
	}


	public void setUserTaskId(String userTaskId) {
		this.userTaskId = userTaskId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isLast ? 1231 : 1237);
		result = prime * result + ((nodeName == null) ? 0 : nodeName.hashCode());
		result = prime * result + ((userTaskId == null) ? 0 : userTaskId.hashCode());
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
		LastNode other = (LastNode) obj;
		if (isLast != other.isLast)
			return false;
		if (nodeName == null) {
			if (other.nodeName != null)
				return false;
		} else if (!nodeName.equals(other.nodeName))
			return false;
		if (userTaskId == null) {
			if (other.userTaskId != null)
				return false;
		} else if (!userTaskId.equals(other.userTaskId))
			return false;
		return true;
	}


}
