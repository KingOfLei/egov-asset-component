package com.bosssoft.egov.asset.activiti.membershipmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwebflow.identity.IdentityMembershipManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.drc.DrcHepler;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.appframe.api.entity.ApiRole;

/**
 *
 * @ClassName 类名：UserMembershipManage
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月2日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年11月2日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
public class UserMembershipManage implements IdentityMembershipManager {
	private static Logger logger = LoggerFactory.getLogger(UserMembershipManage.class);

	//
	// private AimsGroupUserService aimsgroupserserice;
	//
	// public void setAimsgroupserserice(AimsGroupUserService
	// aimsgroupserserice) {
	// this.aimsgroupserserice = aimsgroupserserice;
	// }

	public List<String> findGroupIdsByUser(String userCode) {

//		logger.debug("activiti通过用户查找组");
		List<String> rolesCodeList = new ArrayList<String>();
		String appId = AppContext.getAppContext().getAppConfiguration().getAppId();
		List<ApiRole> apiRoles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, appId);
		if (apiRoles != null && apiRoles.size() > 0) {
			for (ApiRole apiRole : apiRoles) {
				rolesCodeList.add(apiRole.getRoleCode());
			}
		}
		return rolesCodeList;
	}

	public List<String> findUserIdsByGroup(String group) {

//		logger.debug("activiti通过组查找用户");
		Map<String, Object> params = new HashMap<String, Object>();
		return DrcHepler.getInstance().findUserByGroup(group, params);

	}

}
