/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 22 17:20:52 CST 2017
 */
package com.bosssoft.egov.asset.dispatch.entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 调度执行日志对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-22   Administrator　　　新建
 * </pre>
 */
 @Table(name = "ASSET_DISPATCH_LOG")
public class AssetDispatchLog implements java.io.Serializable {

	private static final long serialVersionUID = 170122172126973L;
	
	// Fields
	
	/**
	 * id.
	 */
	@Id
    @Column(name = "ID")
	private String id;
	/**
	 * 任务id.
	 */
    @Column(name = "TASK_ID")
	private String taskId;
	/**
	 * 开始时间.
	 */
    @Column(name = "START_TIME")
	private String startTime;
	/**
	 * 结束时间.
	 */
    @Column(name = "END_TIME")
	private String endTime;
	/**
	 * 执行结果0 失败 1 成功 2 阻塞.
	 */
    @Column(name = "EXECUTE_STATUS")
	private String executeStatus;
	/**
	 * 执行结果说明.
	 */
    @Column(name = "REAMRK")
	private String reamrk;
	
	// Constructors
 
    /** default constructor */
	public AssetDispatchLog() {
	}

	/**
	 * id.
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * id.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 任务id.
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 * 任务id.
	 * @param taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 开始时间.
	 * @return
	 */
	public String getStartTime() {
		return this.startTime;
	}

	/**
	 * 开始时间.
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * 结束时间.
	 * @return
	 */
	public String getEndTime() {
		return this.endTime;
	}

	/**
	 * 结束时间.
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 执行结果0 失败 1 成功 2 阻塞.
	 * @return
	 */
	public String getExecuteStatus() {
		return this.executeStatus;
	}

	/**
	 * 执行结果0 失败 1 成功 2 阻塞.
	 * @param executeStatus
	 */
	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}

	/**
	 * 执行结果说明.
	 * @return
	 */
	public String getReamrk() {
		return this.reamrk;
	}

	/**
	 * 执行结果说明.
	 * @param reamrk
	 */
	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}

}