package com.bosssoft.egov.asset.monitor.entity;

import java.io.Serializable;


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
public class MonitorResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1314877141098408310L;
	

	private String message;
	
	private String code;
	
	private FabMonitorEvt fabMonitorEvt;
	
	private FabMonitorModel fabMonitorModel;
	
	public MonitorResult(){
		this.code = MonitorException.SUCCESS.getCode();
		this.message = MonitorException.SUCCESS.getName();
		this.fabMonitorEvt = new FabMonitorEvt();
		this.fabMonitorModel = new FabMonitorModel();
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

	public FabMonitorEvt getFabMonitorEvt() {
		return fabMonitorEvt;
	}

	public void setFabMonitorEvt(FabMonitorEvt fabMonitorEvt) {
		this.fabMonitorEvt = fabMonitorEvt;
	}

	public FabMonitorModel getFabMonitorModel() {
		return fabMonitorModel;
	}

	public void setFabMonitorModel(FabMonitorModel fabMonitorModel) {
		this.fabMonitorModel = fabMonitorModel;
	}
	
	
}
