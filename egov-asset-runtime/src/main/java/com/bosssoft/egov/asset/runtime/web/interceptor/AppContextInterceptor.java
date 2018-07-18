package com.bosssoft.egov.asset.runtime.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsBasicOrg;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.EncodeUtils;
import com.bosssoft.egov.asset.common.util.EncryptUtils;
import com.bosssoft.egov.asset.common.util.MD5;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.UserHelper;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.common.extension.Activate;
import com.bosssoft.platform.runtime.spi.UserState;
import com.bosssoft.platform.runtime.spi.web.interceptor.WebInterceptor;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：AppContextInterceptor 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Activate
public class AppContextInterceptor implements WebInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		AppContext appContext = new AppContext(request);
		//注册
		AppContext.registerAppContext(appContext);
		User myUser = AppContext.getAppContext().getSessionUser();	
		ApiUser user =  (ApiUser) WebApplicationContext.getContext().getCurrentUser();			
		//user是否有变
		if(user == null || myUser == null || !StringUtilsExt.equals(myUser.getUserCode(), user.getUserCode())){
			myUser = BeanUtilsExt.copyToNewBean(user, User.class);
			//根据用户对应单位信息获取区划等信息
			if(myUser == null) {
				myUser = new User();			
				myUser.setOrgId(0L);
				myUser.setOrgCode("0");
				myUser.setOrgName("0");
				myUser.setRgnCode("0");
				myUser.setRgnId(0L);
				myUser.setRgnName("0");
				myUser.setUserCode("userCode");
				myUser.setUserName("userName");
				myUser.setState(UserState.NORMAL);
			} else {		
				myUser.setEmail(user.getEmail());
				myUser.setMobileNo(user.getMobileNo());
				//获取登录单位信息
				try {			
					AimsBasicOrg orgInfo = UserHelper.getOrgInfo(NumberUtilsExt.toLong(user.getMainOrgid()));						
					if(orgInfo != null) {
						myUser.setOrg(orgInfo);
						myUser.setRgnId(orgInfo.getRgnId());
						myUser.setRgnCode(orgInfo.getRgnCode());
						myUser.setRgnName(orgInfo.getRgnName());
						myUser.setOrgId(orgInfo.getOrgId());
						myUser.setOrgCode(orgInfo.getOrgCode());
						myUser.setOrgName(orgInfo.getOrgName());
						myUser.setAgenPrincipal(orgInfo.getAgenPrincipal());
					}
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			if(myUser != null){
				String authOrg = DrcAuthOrgManager.getDrcOrgData(myUser.getUserCode());
				if(StringUtilsExt.isNotEmpty(authOrg) && StringUtilsExt.isEmpty(myUser.getUserDrcOrgs())){					   
					myUser.setUserDrcOrgs(authOrg);
				}
				//加载默认的角色
				 //默认获取登录后获取的默认角色
				if(user != null){
					 String roleCode = MapUtilsExt.getString(user.getProperties(), "ROLE_CODE");
					 String roleName = MapUtilsExt.getString(user.getProperties(), "ROLE_NAME");
					 myUser.setRoleCodes(new String[]{roleCode});
					 myUser.setRoleNames(new String[]{roleName});
				 }	 
			}
			if(user != null){
				Map<String,Object> params = user.getProperties();
				params.putAll(myUser.getProperties());
				user.setProperties(params);
			}
			AppContext.getAppContext().resetSessionUser(myUser);
		}
		String md5Key = "lyKsMLxYfbluTgW8zpQC7/Sni2LqR67tKdL6kTSwMhtAYkx2lb/Aivc2YVzYpSFQkByKgaFnZAaPKb7fxTwvPw==";
		response.setHeader("Bosssoft_Authorized",AppContext.getAppContext().getEnvironment().getProperty("project.authorization.code", "").equals(MD5.crtMd5Value(EncodeUtils.encodeBase64(EncryptUtils.hmac(md5Key, AppContext.getAppContext().getEnvironment().getProperty("project.name")))))+"");			
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		 //未授权 加入标识
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		AppContext.unregisterAppContext();		
	}

	@Override
	public int order() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String[] patternUrl() {
		// TODO Auto-generated method stub
		return new String[]{"/**/*.do"};
	}

}
