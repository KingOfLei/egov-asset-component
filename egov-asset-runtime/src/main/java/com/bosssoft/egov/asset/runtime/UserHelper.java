package com.bosssoft.egov.asset.runtime;

import java.util.List;

import com.bosssoft.egov.aims.config.service.SysConfigHelper;
import com.bosssoft.egov.asset.aims.api.config.entity.AssetSysConfig;
import com.bosssoft.egov.asset.aims.api.org.entity.AimsBasicOrg;
import com.bosssoft.egov.asset.aims.api.org.service.OrgService;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.appframe.api.service.PositionService;
import com.bosssoft.platform.appframe.api.service.RoleService;
import com.bosssoft.platform.appframe.api.service.UserService;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;

/** 
*
* @ClassName   类名：UserHelper 
* @Description 功能说明：此公共类只能在控制层使用
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class UserHelper {
	
	private static UserService getUserServcie(){
		return (UserService)RuntimeApplicationContext.getBean(UserService.REFERENCE_BEAN_NAME);
	}
	
	
	private static RoleService getRoleService(){
		return (RoleService)RuntimeApplicationContext.getBean(RoleService.REFERENCE_BEAN_NAME);
	}
	
	private static PositionService getPositionService(){
		return (PositionService)RuntimeApplicationContext.getBean(PositionService.REFERENCE_BEAN_NAME);
	}
	  /**
	   * 
	   * <p>函数名称：   getOrgInfo     </p>
	   * <p>功能说明： 根据单位id获取单位信息
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @param orgId 单位id
	   * @return
	   *
	   * @date   创建时间：2017年1月8日
	   * @author 作者：xds
	   */
	  public static AimsBasicOrg getOrgInfo(Long orgId){
		  OrgService orgService = (OrgService) AppContext.getAppContext().getBeanContext().getBean(OrgService.REFERENCE_BEAN_NAME);
		  return orgService.queryOneById(orgId);	
	  }
	  
	  /**
	   * 
	   * <p>函数名称： getOrgInfo       </p>
	   * <p>功能说明： 根据单位编码获取单位信息
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @param orgCode
	   * @return
	   *
	   * @date   创建时间：2017年2月18日
	   * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	   */
	  public static AimsBasicOrg getOrgInfo(String orgCode){
		  OrgService orgService = (OrgService) AppContext.getAppContext().getBeanContext().getBean(OrgService.REFERENCE_BEAN_NAME);
		  return orgService.queryOneByCode(orgCode);
	  }
	  
	  
	  /**
	   * 
	   * <p>函数名称：   isMgrOrg     </p>
	   * <p>功能说明：获取当前登录单位是否为统管单位
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年2月24日
	   * @author 作者：xds
	   */
	  public static boolean isMgrOrg(){
		  User user = AppContext.getAppContext().getSessionUser();
		  return user.currentIsParent();
	  }
	  
	  /**
	   * 
	   * <p>函数名称：  isCzRole     </p>
	   * <p>功能说明： 当前用户是否处于财政角色岗位
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年11月14日
	   * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	   */
	  public static boolean isCzRole(){
		  List<AssetSysConfig> sysconfig = SysConfigHelper.getInstance().getSysConfig("CZ_ROLE_CODE_PREFIX", "ROLE_CONFIG");
		  if(sysconfig != null && sysconfig.size() > 0){
			  String configValue = sysconfig.get(0).getConfigValue();
			  User user = AppContext.getAppContext().getSessionUser();
			  String roleCode = user.getRoleCodes()[0];
			  for(String config : configValue.split("\\|")){
				  if(StringUtilsExt.startsWithIgnoreCase(roleCode, config)){
					  return true;
				  }
			  }
		  }
		  return false;
	  }
}
