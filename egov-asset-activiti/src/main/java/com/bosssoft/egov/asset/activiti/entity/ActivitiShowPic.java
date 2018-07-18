package com.bosssoft.egov.asset.activiti.entity;

import java.util.ArrayList;
import java.util.List;

/** 
 *
 * @ClassName   类名：A 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月20日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月20日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ActivitiShowPic {
	
	private List<ActivitiNode> nodeSequence;
	
	private List<RemarkLogStatements> list;
	
	private String nowLocation;
	
	private boolean isEnd;
	
	private String message ;
	
	private String code;
	
	public  ActivitiShowPic(){
		this.list = new ArrayList<RemarkLogStatements>();
		this.code = ActivitiException.ERROR.getCode();
		this.message = ActivitiException.ERROR.getName();
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

	public List<ActivitiNode> getNodeSequence() {
		return nodeSequence;
	}


	
	public void setNodeSequence(List<ActivitiNode> nodeSequence) {
		this.nodeSequence = nodeSequence;
	}

	public List<RemarkLogStatements> getList() {
		return list;
	}

	public void setList(List<RemarkLogStatements> list) {
		this.list = list;
	}

	public void setListDetail(RemarkLogStatements detail) {
		this.list.add(detail);
	}

	public String getNowLocation() {
		return nowLocation;
	}

	public void setNowLocation(String nowLocation) {
		this.nowLocation = nowLocation;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

}
