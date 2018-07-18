package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;

/**
 *
 * @ClassName 类名：ButtonShow
 * @Description 功能说明：判断驳回与退回按钮是否显示
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月19日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年12月19日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
public class ActivitiButtonShow implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7153473443281946781L;
	private boolean returnBtn;
	private boolean rejectBtn;

	public boolean isReturnBtn() {
		return returnBtn;
	}

	public void setReturnBtn(boolean returnBtn) {
		this.returnBtn = returnBtn;
	}

	public boolean isRejectBtn() {
		return rejectBtn;
	}

	public void setRejectBtn(boolean rejectBtn) {
		this.rejectBtn = rejectBtn;
	}

}
