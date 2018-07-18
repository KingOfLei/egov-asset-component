package com.bosssoft.egov.asset.activiti.membershipmanage;


import org.openwebflow.identity.UserDetailsEntity;
import org.openwebflow.identity.UserDetailsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.activiti.entity.BosssoftActivitiUser;
import com.bosssoft.egov.asset.drc.DrcHepler;
import com.bosssoft.platform.appframe.api.entity.ApiUser;


/** 
 *
 * @ClassName   类名：UserDetailsManage 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年11月2日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年11月2日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class UserDetailsManage implements UserDetailsManager {

	private static Logger logger = LoggerFactory.getLogger(UserDetailsManage.class);
	
//	private AimsLeavePeopleService aimsleavepeopleservice;
//
//	public AimsLeavePeopleService getAimsleavepeopleservice() {
//		return aimsleavepeopleservice;
//	}
//
//	public void setAimsleavepeopleservice(AimsLeavePeopleService aimsleavepeopleservice) {
//		this.aimsleavepeopleservice = aimsleavepeopleservice;
//	}

	public UserDetailsEntity findUserDetails(String userCode) throws Exception {
//		logger.debug("activiti查找用户详情");
		
		BosssoftActivitiUser user = new BosssoftActivitiUser();
		ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(userCode);
		user.setApiUser(apiUser);
		return user;
	}

}
