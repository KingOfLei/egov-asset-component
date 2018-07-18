package com.bosssoft.egov.asset.activiti.entity;

import java.io.Serializable;

import org.openwebflow.identity.UserDetailsEntity;

import com.bosssoft.platform.appframe.api.entity.ApiUser;

/**
 *
 * @ClassName 类名：ActivitiUser
 * @Description 
 *              功能说明：工作流于用户信息的中间用户实体，实现activiti本身的UserDetailsEntity，继承bosssoft的user
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月12日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年12月12日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
public class BosssoftActivitiUser implements Serializable, UserDetailsEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8644640065235486174L;

	private ApiUser apiUser;

	@Override
	public <T> T getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPropertyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.apiUser.getUserId();
	}

	@Override
	public <T> void setProperty(String arg0, T arg1) {
		// TODO Auto-generated method stub
		
	}

	public ApiUser getApiUser() {
		return apiUser;
	}

	public void setApiUser(ApiUser apiUser) {
		this.apiUser = apiUser;
	}

	
	
}
