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
public class ActivitiNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2439650824866174938L;

	private String nodeName;

	private String executor;
	
	private String userTaskId;

	private boolean isActive;
	
	private boolean isPass;
	
	private String option;
	
	public ActivitiNode() {
		this.isActive = false;
		this.isPass = false;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public String getUserTaskId() {
		return userTaskId;
	}

	public void setUserTaskId(String userTaskId) {
		this.userTaskId = userTaskId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

}
