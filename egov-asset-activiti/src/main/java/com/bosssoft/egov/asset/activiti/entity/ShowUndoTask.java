package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;

/**
 *
 * @ClassName 类名：ShowUndoTask
 * @Description 功能说明： 用户首页待办任务显示
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月2日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年12月2日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
public class ShowUndoTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5663644645949041695L;

	private Integer count;

	private AssetBizStatus assetBizStatus;

	private AssetActivitiBus activitiBus;

	private String busTime;
	
	private String applyuser;

	public ShowUndoTask() {

		assetBizStatus = new AssetBizStatus();

		activitiBus = new AssetActivitiBus();
	}

	public String getBusTime() {
		return busTime;
	}

	public void setBusTime(String busTime) {
		this.busTime = busTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public AssetBizStatus getAssetBizStatus() {
		return assetBizStatus;
	}

	public void setAssetBizStatus(AssetBizStatus assetBizStatus) {
		this.assetBizStatus = assetBizStatus;
	}

	public AssetActivitiBus getActivitiBus() {
		return activitiBus;
	}

	public void setActivitiBus(AssetActivitiBus activitiBus) {
		this.activitiBus = activitiBus;
	}

	public String getBusType() {
		return assetBizStatus.getBizType();
	}

	public Integer getStatus() {
		return assetBizStatus.getBizStatus();
	}

	public String getApplyuser() {
		return applyuser;
	}

	public void setApplyuser(String applyuser) {
		this.applyuser = applyuser;
	}
}
