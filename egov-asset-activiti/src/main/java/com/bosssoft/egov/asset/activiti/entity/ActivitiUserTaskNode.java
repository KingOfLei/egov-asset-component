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
public class ActivitiUserTaskNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4396194357487774864L;

	// 给流程的id
	private String userTaskId;
	// 给用户看的name
	private String userTaskName;
	//forKey 
	private String formKey ;
	//documentmentation
	private String documentmentation;
	//条件
	private String condition;
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

	public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getDocumentmentation() {
		return documentmentation;
	}

	public void setDocumentmentation(String documentmentation) {
		this.documentmentation = documentmentation;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
