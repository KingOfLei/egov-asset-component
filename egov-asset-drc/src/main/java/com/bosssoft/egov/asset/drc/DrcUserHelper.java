package com.bosssoft.egov.asset.drc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsBasicOrg;
import com.bosssoft.egov.asset.aims.api.org.service.OrgService;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.platform.appframe.api.entity.ApiPosition;
import com.bosssoft.platform.appframe.api.entity.ApiRole;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.appframe.api.service.PositionService;
import com.bosssoft.platform.appframe.api.service.RoleService;
import com.bosssoft.platform.appframe.api.service.UserService;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：UserHelper 
* @Description 功能说明：
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
public class DrcUserHelper {
	
	private static UserService getUserServcie(){
		return (UserService)RuntimeApplicationContext.getBean(UserService.REFERENCE_BEAN_NAME);
	}
	
	
	private static RoleService getRoleService(){
		return (RoleService)RuntimeApplicationContext.getBean(RoleService.REFERENCE_BEAN_NAME);
	}
	
	private static PositionService getPositionService(){
		return (PositionService)RuntimeApplicationContext.getBean(PositionService.REFERENCE_BEAN_NAME);
	}
	
	private static AssetGeneralService getGeneralService(){
		return RuntimeApplicationContext.getBean(AssetGeneralService.class);
	}
	 
	  /**
	   * 
	   * <p>函数名称：     getAssetUser   </p>
	   * <p>功能说明：  获取用户信息
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年1月6日
	   * @author 作者：xds
	   */
	  public static User getAssetUser(){
		  
			ApiUser apiUser =  (ApiUser) WebApplicationContext.getContext().getCurrentUser();	
			User myUser = BeanUtilsExt.copyToNewBean(apiUser, com.bosssoft.egov.asset.runtime.User.class);
			OrgService orgService = (OrgService) AppContext.getAppContext().getBeanContext().getBean(OrgService.REFERENCE_BEAN_NAME);
			AimsBasicOrg orgInfo = orgService.queryOneById(NumberUtilsExt.toLong(apiUser.getMainOrgid()));						
			if(orgInfo != null){
				myUser.setRgnId(orgInfo.getRgnId());
				myUser.setRgnCode(orgInfo.getRgnCode());
				myUser.setRgnName(orgInfo.getRgnName());
				myUser.setOrgId(orgInfo.getOrgId());
				myUser.setOrgCode(orgInfo.getOrgCode());
				myUser.setOrgName(orgInfo.getOrgName());
			}
			List<ApiRole> roles = getUserRole();
			List<String> roleName = new ArrayList<String>();
			if(roles != null){
				for(ApiRole role : roles){
					//存在获取到角色编码 但差不多角色问题。
					if(role != null) {
					  roleName.add(role.getRoleName());
					}
				}
			}
			myUser.setRoleNames(roleName.toArray(new String[0]));
			//
			return myUser;
	  }
	  	
	  /**
	   * 
	   * <p>函数名称：    getMaxUserInfo    </p>
	   * <p>功能说明：获取用户对应岗位信息 最大岗位（权限）
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年3月20日
	   * @author 作者：xds
	   */
	  public static Map<String,Object> getMaxUserInfo(){
		  ApiUser apiUser =  (ApiUser) WebApplicationContext.getContext().getCurrentUser();	
		  StringBuffer sql = new StringBuffer();
		  sql.append(" SELECT * ");
		  sql.append(" FROM VAFA_MAXUSERROLE ");
		  sql.append(" WHERE USER_CODE='" + apiUser.getUserCode() + "' ");
		  List<Map<String, Object>> list = getGeneralService()
				.queryCommon(sql.toString());
		  if (list != null && !list.isEmpty()) {
			return list.get(0);
		  }
		  return new HashMap<String, Object>();
	  }
	  
	  /**
	   * 
	   * <p>函数名称：        </p>
	   * <p>功能说明：
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年5月2日
	   * @author 作者：xds
	   */
	  public static Map<String,Object> getDefaultUserInfo(){
		  ApiUser apiUser =  (ApiUser) WebApplicationContext.getContext().getCurrentUser();
//		  User user = AppContext.getAppContext().getSessionUser();
		  StringBuffer sql = new StringBuffer();
		  sql.append(" SELECT * ");
		  sql.append(" FROM VDRC_RULE_USER_ORG ");
		  sql.append(" WHERE USER_CODE='" + apiUser.getUserCode() + "' ");
//		  if(user.isChoseOrg()){
//			  sql.append(" AND ORG_CODE = '").append(user.getOrgCode()).append("'");
//		  }
		  sql.append(" ORDER BY ROLE_CODE DESC ");
		  List<Map<String, Object>> list = getGeneralService()
				.queryCommon(sql.toString());
		  if (list != null && !list.isEmpty()) {
			return list.get(0);
		  }
		  return new HashMap<String, Object>();
	  }
	  
	  /**
	   * 
	   * <p>函数名称：  getUserRole      </p>
	   * <p>功能说明：获取当前用户角色列表
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年1月14日
	   * @author 作者：xds
	   */
	  public static List<ApiRole> getUserRole(){
		  ApiUser apiUser =  (ApiUser) WebApplicationContext.getContext().getCurrentUser();	
		  if(apiUser == null) return new ArrayList<ApiRole>();
//		  return getRoleService().getRoleByUserCode(apiUser.getUserCode(), WebApplicationContext.getContext().getAppID());		  		  
		  return getRolesByUserCode(apiUser.getUserCode(), WebApplicationContext.getContext().getAppID());
	  }
	  
	  /**
	   * 
	   * <p>函数名称： isAdminUsr       </p>
	   * <p>功能说明：是否是管理员
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return
	   *
	   * @date   创建时间：2017年1月19日
	   * @author 作者：xds
	   */
	  public static boolean isAdminUser(){
		  List<ApiRole> roles = getUserRole();
		  if(roles != null && !roles.isEmpty()){
			  for(ApiRole role : roles){
				  if(StringUtilsExt.indexOf(role.getRoleType(), "admin") >=0){
					  return true;
				  }
			  }
		  }
		  
		  return false;
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
	   * <p>函数名称：getUserPosition        </p>
	   * <p>功能说明：获取用户对应岗位信息
	   *
	   * </p>
	   *<p>参数说明：</p>
	   * @return 岗位列表
	   *
	   * @date   创建时间：2017年2月16日
	   * @author 作者：xds
	   */
	  public static List<ApiPosition> getUserPosition(){
		  ApiUser apiUser =  (ApiUser) WebApplicationContext.getContext().getCurrentUser();	
		  if(apiUser == null) return new ArrayList<ApiPosition>();
		  return getPositionService().getApiPositionByUserCode(apiUser.getUserCode());
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
		 * <p>函数名称：getRolesByUserCode        </p>
		 * <p>功能说明：获取用户角色
		 *
		 * </p>
		 *<p>参数说明：</p>
		 * @param userCode
		 * @param appId
		 * @return
		 *
		 * @date   创建时间：2017年3月6日
		 * @author 作者：xds
		 */
		public static List<ApiRole> getRolesByUserCode(String userCode,String appId){
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT a.ROLE_ID,a.TENANT_ID,a.ROLE_CODE,a.ROLE_NAME,a.ROLE_DESC,a.ROLE_TYPE,a.CREATE_USER,a.CREATE_TIME ");
			sql.append(" FROM afa_role a RIGHT JOIN afa_auth_party b ON a.ROLE_CODE = b.ROLE_CODE");
			sql.append(" WHERE b.PARTY_CODE='" + userCode + "' ORDER BY a.ROLE_CODE ");
			AssetGeneralService generalService = WebApplicationContext.getContext().lookup(AssetGeneralService.class);
			List<Map<String,Object>> list = generalService.queryCommon(sql.toString());
			List<ApiRole> roles = new ArrayList<ApiRole>();
			if(list != null && !list.isEmpty() ){
				for(Map<String,Object> role : list ){
					ApiRole newRole = new ApiRole();
					newRole.setRoleId(MapUtilsExt.getString(role, "ROLE_ID"));
					newRole.setRoleCode(MapUtilsExt.getString(role, "ROLE_CODE"));
					newRole.setRoleName(MapUtilsExt.getString(role, "ROLE_NAME"));
					newRole.setRoleDesc(MapUtilsExt.getString(role, "ROLE_DESC"));
					newRole.setRoleType(MapUtilsExt.getString(role, "ROLE_TYPE"));
					newRole.setCreateUser(MapUtilsExt.getString(role, "CREATE_USER"));
					roles.add(newRole);
				}
			}
			return roles;
		}
}
