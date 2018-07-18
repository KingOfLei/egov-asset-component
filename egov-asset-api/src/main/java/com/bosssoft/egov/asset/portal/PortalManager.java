package com.bosssoft.egov.asset.portal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bosssoft.egov.asset.cache.CacheHelper;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.platform.appframe.api.entity.ApiApplication;
import com.bosssoft.platform.appframe.api.entity.ApiMenu;
import com.bosssoft.platform.appframe.api.service.ApplicationService;
import com.bosssoft.platform.appframe.api.util.MenuUtils;
import com.bosssoft.platform.portal.spi.entity.PortalApplication;
import com.bosssoft.platform.portal.spi.entity.PortalMenu;
import com.bosssoft.platform.portal.spi.entity.PortalUser;
import com.bosssoft.platform.portal.spi.service.PortalService;
import com.bosssoft.platform.runtime.spi.User;
import com.bosssoft.platform.runtime.spring.RuntimeApplicationContext;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;
import com.bosssoft.platform.runtime.web.context.WebSessionContext;
import com.bosssoft.platform.shiro.token.TokenManager;

/** 
*
* @ClassName   类名：PortalManager 
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
public class PortalManager {

	public static PortalService getPortalProvider() {
		return (PortalService) RuntimeApplicationContext.getBean(PortalService.REFERENCE_BEAN_NAME);
	}
	
	public static ApplicationService getApplicationProvider() {
		return (ApplicationService) RuntimeApplicationContext.getBean(ApplicationService.REFERENCE_BEAN_NAME);
	}
	
	public static AssetGeneralService getAssetGeneralService(){
		return RuntimeApplicationContext.getBean(AssetGeneralService.class);
	}
	/**
	 * 
	 * <p>函数名称：getApplications        </p>
	 * <p>功能说明：获取当前用户对应应用列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年2月18日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	 public static List<PortalApplication> getApplications()
	  {
		User user = WebApplicationContext.getContext().getCurrentUser();
		if (user == null) {
			return new ArrayList<PortalApplication>();
		}
		return getPortalProvider().getApplications(user.getUserCode());
	  }
	 
	 /**
	  * 
	  * <p>函数名称：getAllMenu        </p>
	  * <p>功能说明：获取所有应用对应的所有菜单
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @return
	  *
	  * @date   创建时间：2017年2月18日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 public static Map<String,List<ApiMenu>> getAllMenu(){
		 Map<String,List<ApiMenu>> result = new LinkedHashMap<String, List<ApiMenu>>();
	     User user = WebApplicationContext.getContext().getCurrentUser();
         if(user == null) return result;
		 List<PortalApplication> appList = getAllApplicatons();
		 //
		 OrderByUtil.sort(appList, "appDesc");
		 for(PortalApplication application : appList){
			 List<ApiMenu> menu = MenuUtils.getMenus(user.getUserCode(), application.getAppId());
			 result.put(application.getAppId(), menu);
		 }		 

		 return result;
				
	 }
	 
	 public static Map<String,List<ApiMenu>> getMenuByUserAndRole(String... roles){
		 Map<String,List<ApiMenu>> result = new LinkedHashMap<String, List<ApiMenu>>();
	     User user = WebApplicationContext.getContext().getCurrentUser();
         if(user == null) return result;
		 List<PortalApplication> appList = getAllApplicatons();
		 //
		 OrderByUtil.sort(appList, "appDesc");
		 
//		 for(PortalApplication application : appList){
//			 List<ApiMenu> menu = MenuUtils.getMenusByRoleCodes(Arrays.asList(roles), application.getAppId());
//			 result.put(application.getAppId(), menu);
//		 }	
		 result.putAll(handleMenu(appList, roles, user.getUserCode()));
		 return result;
	 }
	 
	 public static Map<String,List<ApiMenu>> getMenuByUserAndRole(HttpServletRequest request){
		 Map<String,List<ApiMenu>> result = new LinkedHashMap<String, List<ApiMenu>>();
		 //获取当前单位 
		 WebSessionContext sessionContext = new WebSessionContext(request.getSession(true));
		 com.bosssoft.egov.asset.runtime.User user = (com.bosssoft.egov.asset.runtime.User) sessionContext.get(AppContext.USER_SESSION_KEY);
		 User _user = WebApplicationContext.getContext().getCurrentUser();
		 if(user == null && _user == null) return result;
		 
		 List<PortalApplication> appList = getAllApplicatons();
		 //
		 OrderByUtil.sort(appList, "appDesc");
		 String roleCodes[] = null;
		 String roleNames[] = null;
		 if(user != null){
		   roleCodes = user.getRoleCodes();
		   roleNames = user.getRoleNames();
		 }
		 if(roleCodes == null || roleCodes.length == 0){
			 //默认获取登录后获取的默认角色
			 String roleCode = MapUtilsExt.getString(_user.getProperties(), "ROLE_CODE");
			 roleCodes = new String[]{roleCode};
		 }
		 if(roleNames == null || roleNames.length == 0){
			 //默认获取登录后获取的默认角色
			 String roleName = MapUtilsExt.getString(_user.getProperties(), "ROLE_NAME");
			 roleNames = new String[]{roleName};
		 }
		 if(user != null){
			 //重置user信息
			// user.setOrgCode(_user.getOrgCode());
			// user.setState(_user.getState());
			// user.setUserCode(_user.getUserCode());
			// user.setUserName(_user.getUserName());
			 user.setRoleCodes(roleCodes);
			 user.setRoleNames(roleNames);
			 if(roleCodes != null && roleCodes.length > 0){
				 user.addProperties("ROLE_CODE", roleCodes[0]); 
			 }
			 if(roleNames != null && roleNames.length > 0){
				 user.addProperties("ROLE_NAME", roleNames[0]); 
			 }
			 Map<String,Object> paramsAttr = _user.getProperties();
			 paramsAttr.putAll(user.getProperties());
			 _user.setProperties(paramsAttr);
			 sessionContext.set(AppContext.USER_SESSION_KEY, user);
		 }
		
//		 for(PortalApplication application : appList){
//			 List<ApiMenu> menu = MenuUtils.getMenusByRoleCodes(Arrays.asList(roleCodes),application.getAppId());
//			 result.put(application.getAppId(), menu);
//		 }	
		 result.putAll(handleMenu(appList, roleCodes, _user.getUserCode()));
				
		 return result;
	 }
	 
	 /**
	  * 
	  * <p>函数名称： handleMenu       </p>
	  * <p>功能说明： 根据实际情况过滤特定人特定角色对应特定菜单
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @param appList
	  * @param roleCodes 
	  * @param user
	  * @return
	  *
	  * @date   创建时间：2018年1月12日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 private static Map<String,List<ApiMenu>> handleMenu(List<PortalApplication> appList,String[] roleCodes,String... userInfo){
		 Map<String,List<ApiMenu>> result = new LinkedHashMap<String, List<ApiMenu>>();
		 List<String> userInfoList = Arrays.asList(userInfo);
		 CacheHelper cacheHelper = CacheHelper.getInstance("EGOV-DRC-AUTH-MENU-USER");
		 Map<String,List<Map<String,Object>>> excludeMenu = new HashMap<String,List<Map<String,Object>>>();
		 List<Map<String, Object>> cacheList = cacheHelper.getListMapCache(userInfoList.get(0));
		 if(cacheList == null ){
			 cacheHelper.clear();
			 StringBuffer sql = new StringBuffer();
			 sql.append(" SELECT * FROM DRC_AUTH_MENU_USER ");
			 sql.append(" WHERE STATUS = 1 AND USER_CODE ='").append(userInfoList.get(0)).append("'");
			 cacheList = getAssetGeneralService().queryCommon(sql.toString());
			 cacheHelper.putCache(userInfoList.get(0), cacheList);
		 }
		 //按appId 进行分组存放
		 for(Map<String,Object> cacheMenu : cacheList){
			 String appId = MapUtilsExt.getString(cacheMenu, "APP_ID");
			 List<Map<String, Object>> tmpList = new ArrayList<Map<String, Object>>();
			 if(excludeMenu.containsKey(appId)){
				 tmpList =  excludeMenu.get(appId);
			 }
			 tmpList.add(cacheMenu);
			 excludeMenu.put(appId, tmpList);			 
		 }
		 for(PortalApplication application : appList){
			 List<ApiMenu> menu = MenuUtils.getMenusByRoleCodes(Arrays.asList(roleCodes),application.getAppId());
			 if(excludeMenu.isEmpty()){
				 result.put(application.getAppId(), menu);
				 continue;
			 }
			 //过滤
			 List<Map<String,Object>> excludeMenus = excludeMenu.get(application.getAppId());
			 if(excludeMenus != null){
				for (Map<String, Object> menuData : excludeMenus) {
					String appId = MapUtilsExt.getString(menuData, "APP_ID");
					String menuCode = MapUtilsExt.getString(menuData,"MENU_CODE");
					// 当 menu_code 为空时 直接屏蔽appId 对应的菜单
					if (StringUtilsExt.isBlank(menuCode) && StringUtilsExt.isNotBlank(appId)) {
						menu.clear();
						break;
					}
					//否则直接过滤里面的menuCode
					Iterator<ApiMenu> menuIterator = menu.iterator();
					//list 的删除 使用 迭代器进行
					while(menuIterator.hasNext()){
						if(StringUtilsExt.startsWithIgnoreCase(menuIterator.next().getMenuCode(),menuCode)){
							menuIterator.remove();
						}
					}
				}
			 }
			 result.put(application.getAppId(), menu);
		 }	
		 return result;
	 }
	 
	 /**
	  * 
	  * <p>函数名称：    获取所有应用列表    </p>
	  * <p>功能说明：
	  *
	  * </p>
	  *<p>参数说明：</p>
	  * @return
	  *
	  * @date   创建时间：2017年2月18日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	  */
	 public static List<PortalApplication> getAllApplicatons(){
		 List<ApiApplication> appList = getApplicationProvider().getApplications();
		 List<PortalApplication> portalAppList = new ArrayList<PortalApplication>();

		 for(ApiApplication api : appList){
			 if("APPCENTER".equals(api.getAppId())){
				continue;
			 }
			 PortalApplication portalAppInfo = new PortalApplication();
		     portalAppInfo.setAppDesc(api.getAppDesc());
		     portalAppInfo.setAppName(api.getAppName());
		     portalAppInfo.setContext(api.getContext());
		     portalAppInfo.setAppId(api.getAppId());
		     portalAppInfo.setIp(api.getIp());
		     portalAppInfo.setPort(api.getPort());
		     portalAppInfo.setProtocol(api.getProtocol());
		     portalAppInfo.setIsDefault(false);
		     portalAppList.add(portalAppInfo);
		 }
		 OrderByUtil.sort(portalAppList, "appDesc");
		 return portalAppList;
	 }

	  public static PortalApplication getApplication(String appId) {
	    return getPortalProvider().getApplication(appId);
	  }

	  public List<PortalMenu> getMenus()
	  {
	    return getPortalProvider().getAppMenus();
	  }

	  public static List<PortalMenu> getMenu(String appId) {
	    return getPortalProvider().getAppMenu(appId);
	  }

	  public static PortalUser getPortalUser(String usercode)
	  {
	    PortalUser portalUser = new PortalUser();
	    User user = TokenManager.getToken();
	    portalUser.setUserCode(user.getUserCode());
	    portalUser.setUserName(user.getUserName());
	    return portalUser;
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
	   * @date   创建时间：2017年1月6日
	   * @author 作者：xds
	   */
	  public static com.bosssoft.egov.asset.runtime.User getAppContextUser(){
		  return AppContext.getAppContext().getSessionUser();
	  }

	  public static String toJson(Object obj)
	  {
	    return null;
	  }
}
