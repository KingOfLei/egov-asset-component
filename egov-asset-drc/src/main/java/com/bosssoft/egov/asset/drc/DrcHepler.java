package com.bosssoft.egov.asset.drc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.drc.service.DrcAuthRuleService;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.platform.appframe.api.entity.ApiRole;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.appframe.api.service.PositionService;
import com.bosssoft.platform.appframe.api.service.RoleService;
import com.bosssoft.platform.appframe.api.service.UserService;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;

/** 
*
* @ClassName   类名：DrcHepler 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月28日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月28日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DrcHepler {
	
//	@Resource
//	private UserService userService;
//	
//	@Resource 
//	private RoleService roleService;
//
//	@Resource
//	private PositionService positionService;
//	
	private static DrcHepler INSTANCE;
	
	@Resource
	private DrcAuthRuleService drcAuthRuleService;
	
	@Resource
	private AssetGeneralService generalService;
	
	private DrcHepler(){		
		SpringExtensionHelper.initAutowireFields(this);
	}
	
	private static UserService getUserServcie(){
		return (UserService)RuntimeApplicationContext.getBean(UserService.REFERENCE_BEAN_NAME);
	}
	
	
	private static RoleService getRoleService(){
		return (RoleService)RuntimeApplicationContext.getBean(RoleService.REFERENCE_BEAN_NAME);
	}
	
	private static PositionService getPositionService(){
		return (PositionService)RuntimeApplicationContext.getBean(PositionService.REFERENCE_BEAN_NAME);
	}
	
	public static DrcHepler getInstance() {
		if (INSTANCE == null) {
			synchronized(DrcHepler.class){
			   if(INSTANCE == null){
				   INSTANCE = new DrcHepler();
			   }
			}
		}
		return INSTANCE;
	}
	
	
	/**
	 * 
	 * <p>函数名称：  findGroupUser      </p>
	 * <p>功能说明： 根据组标识查找对应用户
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param groupIds
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2016年12月28日
	 * @author 作者：xds
	 */
	public List<String> findUserIdByGroups(List<String> groupIds,Map<String,Object> params){
		List<ApiUser> userList = findUserByGroups(groupIds, params);
		Set<String> hashSet = new HashSet<String>();
		for(ApiUser user : userList){
			hashSet.add(user.getUserCode());
		}		
		return new ArrayList<String>(hashSet);
	}
	
	public List<ApiUser> findUserByGroupWithSingle(List<String> groupIds,Map<String,Object> params){
		List<ApiUser> userList = new ArrayList<ApiUser>();
		//获取单位
		String orgCode = MapUtilsExt.getString(params, "orgCode");
        //直接获取单位下面所有人
        StringBuffer sql = new StringBuffer();
//        sql.append(" SELECT USR.USER_ID,PARTY_CODE AS USER_CODE,USR.USER_NAME,AUTH.ROLE_CODE,ROL.ROLE_NAME,DRC.RGN_ID,DRC.RGN_CODE,DRC.RGN_NAME,DRC.ORG_ID,DRC.ORG_CODE,DRC.ORG_NAME ");
//        sql.append(" FROM AFA_AUTH_PARTY auth ");
//        sql.append(" LEFT JOIN AFA_USER USR ON (AUTH.PARTY_CODE=USR.USER_CODE) ");
//        sql.append(" LEFT JOIN AFA_ROLE ROL ON (AUTH.ROLE_CODE=ROL.ROLE_CODE) ");
//        sql.append(" JOIN VDRC_RULE_USER_ORG DRC ON ('${orgCode}' LIKE DRC.ORG_CODE||'%' AND AUTH.PARTY_CODE=DRC.USER_CODE)");
//        sql.append(" WHERE AUTH.PARTY_TYPE='user' AND AUTH.ROLE_CODE IN (${roles})");
//      
        Map<String,String> sqlParams = new HashMap<String,String>();
        sqlParams.put("orgCode", orgCode);
        String roles = "";
        for(String groupId : groupIds){
        	if(StringUtilsExt.isNotEmpty(roles)){
        		roles += ",'" + groupId + "'";
        	} else {
        		roles += "'" + groupId + "'";
        	}
        }
        sqlParams.put("roles", roles);
        sql.append(" SELECT USER_ID,USER_CODE,USER_NAME,RGN_ID,RGN_CODE,RGN_NAME,ORG_ID,ORG_CODE,ORG_NAME,ROLE_ID,ROLE_CODE,ROLE_NAME ");
        sql.append(" FROM VDRC_AUTH_RULE T ");
        sql.append(" WHERE T.ORG_CODE = '${orgCode}' AND T.ROLE_CODE IN (${roles})");        
        //获取这个角色 这个单位下的人员信息
        List<Map<String,Object>> orgUserList = generalService.queryCommon(StringUtilsExt.environmentSubstitute(sql.toString(), sqlParams));
        if(orgUserList != null && !orgUserList.isEmpty()){
            Map<String,ApiUser> mapUser = new HashMap<String,ApiUser>();
        	for(Map<String,Object> orgUser : orgUserList){	
        		String userCode = MapUtilsExt.getString(orgUser, "USER_CODE");
        		if(mapUser.containsKey(orgUser)){
        			ApiUser user = mapUser.get(userCode);
        			String[] userRoles = user.getRoles();
        			if(userRoles != null){
        				List<String> list = Arrays.asList(userRoles);
        				list.add(MapUtilsExt.getString(orgUser, "ROLE_CODE"));
        				userRoles = list.toArray(new String[0]);
        				user.setRoles(userRoles);
        			}
        			continue;
        		}        		
        		ApiUser user = new ApiUser();
	    		user.setUserId(MapUtilsExt.getString(orgUser, "USER_ID"));
	    		user.setUserCode(MapUtilsExt.getString(orgUser, "USER_CODE"));
	    		user.setUserName(MapUtilsExt.getString(orgUser, "USER_NAME"));	    		
	    		user.addProperties("rgnId", MapUtilsExt.getString(orgUser, "RGN_ID"));
	    		user.addProperties("rgnCode", MapUtilsExt.getString(orgUser, "RGN_CODE"));
	    		user.addProperties("rgnName", MapUtilsExt.getString(orgUser, "RGN_NAME"));
	    		user.addProperties("orgId", MapUtilsExt.getString(orgUser, "ORG_ID"));
	    		user.addProperties("orgCode", MapUtilsExt.getString(orgUser, "ORG_CODE"));
	    		user.addProperties("orgName", MapUtilsExt.getString(orgUser, "ORG_NAME"));	
	    		user.setRoles(Arrays.asList(MapUtilsExt.getString(orgUser, "ROLE_CODE")).toArray(new String[0]));
	    		mapUser.put(userCode, user);
    	   }
        	userList.addAll(mapUser.values());
        }
        
        return userList;
	}
	
	public List<ApiUser> findUserByGroups(List<String> groupIds,Map<String,Object> params){
		List<ApiUser> userList = new ArrayList<ApiUser>();
		//获取单位
		String orgCode = MapUtilsExt.getString(params, "orgCode");
        //直接获取单位下面所有人
        StringBuffer sql = new StringBuffer();
//        sql.append(" SELECT USR.USER_ID,PARTY_CODE AS USER_CODE,USR.USER_NAME,AUTH.ROLE_CODE,ROL.ROLE_NAME,DRC.RGN_ID,DRC.RGN_CODE,DRC.RGN_NAME,DRC.ORG_ID,DRC.ORG_CODE,DRC.ORG_NAME ");
//        sql.append(" FROM AFA_AUTH_PARTY auth ");
//        sql.append(" LEFT JOIN AFA_USER USR ON (AUTH.PARTY_CODE=USR.USER_CODE) ");
//        sql.append(" LEFT JOIN AFA_ROLE ROL ON (AUTH.ROLE_CODE=ROL.ROLE_CODE) ");
//        sql.append(" JOIN VDRC_RULE_USER_ORG DRC ON ('${orgCode}' LIKE DRC.ORG_CODE||'%' AND AUTH.PARTY_CODE=DRC.USER_CODE)");
//        sql.append(" WHERE AUTH.PARTY_TYPE='user' AND AUTH.ROLE_CODE IN (${roles})");
//      
        Map<String,String> sqlParams = new HashMap<String,String>();
        sqlParams.put("orgCode", orgCode);
        String roles = "";
        for(String groupId : groupIds){
        	if(StringUtilsExt.isNotEmpty(roles)){
        		roles += ",'" + groupId + "'";
        	} else {
        		roles += "'" + groupId + "'";
        	}
        }
        sqlParams.put("roles", roles);
        sql.append(" SELECT USER_ID,USER_CODE,USER_NAME,RGN_ID,RGN_CODE,RGN_NAME,ORG_ID,ORG_CODE,ORG_NAME,ROLE_ID,ROLE_CODE,ROLE_NAME ");
        sql.append(" FROM VDRC_AUTH_RULE T ");
        sql.append(" WHERE '${orgCode}' LIKE T.ORG_CODE||'%' AND T.ROLE_CODE IN (${roles})");        
        //获取这个角色 这个单位下的人员信息
        List<Map<String,Object>> orgUserList = generalService.queryCommon(StringUtilsExt.environmentSubstitute(sql.toString(), sqlParams));
        if(orgUserList != null && !orgUserList.isEmpty()){
            Map<String,ApiUser> mapUser = new HashMap<String,ApiUser>();
        	for(Map<String,Object> orgUser : orgUserList){	
        		String userCode = MapUtilsExt.getString(orgUser, "USER_CODE");
        		if(mapUser.containsKey(orgUser)){
        			ApiUser user = mapUser.get(userCode);
        			String[] userRoles = user.getRoles();
        			if(userRoles != null){
        				List<String> list = Arrays.asList(userRoles);
        				list.add(MapUtilsExt.getString(orgUser, "ROLE_CODE"));
        				userRoles = list.toArray(new String[0]);
        				user.setRoles(userRoles);
        			}
        			continue;
        		}        		
        		ApiUser user = new ApiUser();
	    		user.setUserId(MapUtilsExt.getString(orgUser, "USER_ID"));
	    		user.setUserCode(MapUtilsExt.getString(orgUser, "USER_CODE"));
	    		user.setUserName(MapUtilsExt.getString(orgUser, "USER_NAME"));	    		
	    		user.addProperties("rgnId", MapUtilsExt.getString(orgUser, "RGN_ID"));
	    		user.addProperties("rgnCode", MapUtilsExt.getString(orgUser, "RGN_CODE"));
	    		user.addProperties("rgnName", MapUtilsExt.getString(orgUser, "RGN_NAME"));
	    		user.addProperties("orgId", MapUtilsExt.getString(orgUser, "ORG_ID"));
	    		user.addProperties("orgCode", MapUtilsExt.getString(orgUser, "ORG_CODE"));
	    		user.addProperties("orgName", MapUtilsExt.getString(orgUser, "ORG_NAME"));	
	    		user.setRoles(Arrays.asList(MapUtilsExt.getString(orgUser, "ROLE_CODE")).toArray(new String[0]));
	    		mapUser.put(userCode, user);
    	   }
        	userList.addAll(mapUser.values());
        }
//        sql.append(" SELECT USER_ID,USER_CODE,USER_NAME,RGN_ID,RGN_CODE,RGN_NAME,ORG_ID,ORG_CODE,ORG_NAME FROM VDRC_RULE_USER_ORG ");
//        sql.append(" WHERE '").append(orgCode).append("' LIKE ORG_CODE||'%' AND ORG_CODE IS NOT NULL ");
//        List<Map<String,Object>> orgUserList = generalService.queryCommon(sql.toString());
//        if(orgUserList != null && !orgUserList.isEmpty()){
//        	for(Map<String,Object> orgUser : orgUserList){
//        		ApiUser user = getUserServcie().getUserByUserId(MapUtilsExt.getString(orgUser, "USER_ID"));
//        		user.addProperties("rgnId", MapUtilsExt.getString(orgUser, "RGN_ID"));
//        		user.addProperties("rgnCode", MapUtilsExt.getString(orgUser, "RGN_CODE"));
//        		user.addProperties("rgnName", MapUtilsExt.getString(orgUser, "RGN_NAME"));
//        		user.addProperties("orgId", MapUtilsExt.getString(orgUser, "ORG_ID"));
//        		user.addProperties("orgCode", MapUtilsExt.getString(orgUser, "ORG_CODE"));
//        		user.addProperties("orgName", MapUtilsExt.getString(orgUser, "ORG_NAME"));
//    			userCode.add(user);
//        	}
//        }
//        if(!userCode.isEmpty()){
//        	for(ApiUser code : userCode){
//        		List<ApiRole> roles = DrcUserHelper.getRolesByUserCode(code.getUserCode(), WebApplicationContext.getContext().getAppID());
//        		if(roles != null && !roles.isEmpty()){
//        			for(ApiRole role : roles){
//        				//找不到角色情况 (afa_auth_party) 
//        				if(role == null) continue;
//        				for(String groupId : groupIds){
//        					if(groupId.equals(StringUtilsExt.toString(role.getRoleCode()))){
//        						code.addProperties("roleName", role.getRoleName());
//        						code.addProperties("roleCode", role.getRoleCode());
////        						code.getProperty(key)
//        						userList.add(code);
//        					}
//        				}
//        			}
//        		}
//        	}
//        }	
        
		return userList;
	}	
	
	/**
	 * 
	 * <p>函数名称：  findGroupUser      </p>
	 * <p>功能说明： 根据组标识查找对应用户
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param groupId
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2016年12月28日
	 * @author 作者：xds
	 */
	public List<String> findUserByGroup(String groupId,Map<String,Object> params){
		return findUserIdByGroups(Arrays.asList(groupId), params);
	}
	
	public ApiUser findUseriInfoByUserCode(String userCode){
		return getUserServcie().getUserByUserCode(userCode);
	}
	
	public ApiUser findUseriInfoByUserId(String userId){
		return getUserServcie().getUserByUserId(userId);
	}
	
	/**
	 * 
	 * <p>函数名称：    findUserRolesByUserCode    </p>
	 * <p>功能说明： 根据用户id
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCode
	 * @return
	 *
	 * @date   创建时间：2016年12月28日
	 * @author 作者：xds
	 */
	public List<ApiRole> findUserRolesByUserCode(String userCode,String appId){		
		return getRoleService().getRoleByUserCode(userCode, appId);
	}
	
	/**
	 * 
	 * <p>函数名称：  findUserByGroups      </p>
	 * <p>功能说明： 获取用户信息 根据组
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param groupIds
	 * @return
	 *
	 * @date   创建时间：2016年12月28日
	 * @author 作者：xds
	 */
	public List<ApiUser> findUserByGroups(List<String> groupIds){	
		return findUserByGroups(groupIds, new HashMap<String,Object>());
	}
	
	/**
	 * 
	 * <p>函数名称： findUserByGroup       </p>
	 * <p>功能说明： 根据组获取用户
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param groupId
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public List<ApiUser> findUserByGroup(String groupId){
		return findUserByGroups(Arrays.asList(groupId));
	}
 
	/**
	 * 
	 * <p>函数名称：findAllRoles        </p>
	 * <p>功能说明：获取所有角色信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public List<ApiRole> findAllRoles(){
		return getRoleService().getAllRoleList();
	}
	
	/**
	 * 
	 * <p>函数名称：  findAllUser      </p>
	 * <p>功能说明： 获取所有用户信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public List<ApiUser> findAllUser(){
		return getUserServcie().getAllUserList();
	}
	
	public ApiRole findRoleByRoleCode(String roleCode){
		return null; 
	}
	
	/**
	 * 
	 * <p>函数名称：   matchUserTodoList     </p>
	 * <p>功能说明： 匹配用户对应登录单位对应的代办事项
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param actParams
	 * @param findKeyMap
	 * @return
	 *
	 * @date   创建时间：2017年1月16日
	 * @author 作者：xds
	 */
	public boolean matchUserTodoList(Map<String,Object> actParams,Map<String,Object> findKeyMap){
		if(findKeyMap == null || findKeyMap.isEmpty()){
			return true;
		}
		int flagTrue = 0;
		for(String key : findKeyMap.keySet()){
			//当前用户的单位参数
			String value = MapUtilsExt.getString(findKeyMap, key,"");
			//工作流里面带入的参数
			String actValue =  MapUtilsExt.getString(actParams, key,"");
			if(matchValue(value, actValue)){
				flagTrue++;
			}			
		}
		if(flagTrue == findKeyMap.size()){
			return true;
		}
		return false;
	}
	
	private boolean matchValue(String value,String actValue){
		//工作流无参数 则暂时返回false
		if(StringUtilsExt.isEmpty(actValue)){
			return false;
		} else if(value.equals(actValue)){
			return true;
		} else if (actValue.startsWith(value)){
			return true;
		}		
		return false;
	}
}
