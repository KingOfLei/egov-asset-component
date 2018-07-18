package com.bosssoft.egov.asset.runtime.web.context;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.platform.runtime.server.config.AppConfiguration;
import com.bosssoft.platform.runtime.web.context.FileUploaded;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;
import com.bosssoft.platform.runtime.web.context.WebRequestContext;
import com.bosssoft.platform.runtime.web.context.WebSessionContext;

/** 
*
* @ClassName   类名：AppContext 
* @Description 功能说明：访问应用的上下文类，主要负责上下文的属性和参数的操作
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
public class AppContext implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -122968401468716007L;
	
	public static final String USER_SESSION_KEY = "_user_session_key_";
	public static final String LOGIN_ORG_SESSION_KEY = "_login_org_session_key_";
	
	protected static Logger logger = LoggerFactory.getLogger(AppContext.class);
	
	private HttpServletRequest request;
	
	private Environment evn;
	/**
	 * 应用上下文值
	 */
	protected Map<String,Object> values = new ConcurrentHashMap<String, Object>();
	
	private static ThreadLocal<AppContext> appContext = new ThreadLocal<AppContext>();
	
	public AppContext(){
		
	}
	
	public AppContext(HttpServletRequest request){
		this();
		this.request = request;
		init();
	}
	
	protected void init(){
		
	}
	
	public ApplicationContext getBeanContext(){
		return getWebApplicationContext().getBeanContext();
	}
	
	public WebApplicationContext getWebApplicationContext(){
		return WebApplicationContext.getContext();
	}

	/**
	 * 注册当前上下文对象
	 * @param context 上下文对象
	 */
	public static void registerAppContext(AppContext context){
		appContext.set(context);
	}
	
	/**
	 * 反注册当前上下文对象
	 */
	public static void unregisterAppContext(){
		appContext.remove();
	}

	public static AppContext getAppContext(){
		AppContext context = appContext.get();
		if(context != null)
			return context;
		synchronized(appContext){
			context =  appContext.get();
			if(context != null)
				return context;
			context = new AppContext();
			registerAppContext(context);
		}
		return context;
	}
	
	/**
	 * 根据类名搜索相应的bean对象
	 * @param <T> 类模板标识返回值类型
	 * @param clazz 模板类
	 * @return bean对象
	 */
	public <T>T lookup(Class<T> clazz){
		if(getBeanContext() == null)
			return null;
		return getBeanContext().getBean(clazz);
	}

	/**
	 * 根据类型取得所有
	 * @param type 接口类
	 * @return 取得所有的实现bean
	 */
	public <T> Map<String, T> getBeansOfType(Class<T> type){
		return getBeanContext().getBeansOfType(type);
	}
	/**
	 * 根据bean名称和bean类型搜索相应的bean对象
	 * @param <T> 类模板标识返回值类型
	 * @param beanName bean名称
	 * @param clazz 模板类
	 * @return bean对象
	 */
	public <T>T lookup(String beanName,Class<T> clazz){
		if(getBeanContext() == null)
			return null;
		return getBeanContext().getBean(beanName, clazz);
	}
	
	/**
	 * 根据bean名称搜索相应的bean对象
	 * @param beanName bean名称
	 * @return bean对象
	 */
	public Object lookup(String beanName){
		if(getBeanContext() == null)
			return null;
		return getBeanContext().getBean(beanName);
	}
	
	public Map<String, Object> getRequestParamsMap(){
		return getWebApplicationContext().getWebRequestContext().getRequestParamsMap();
	}
	
	public WebRequestContext getWebRequestContext(){
		return getWebApplicationContext().getWebRequestContext();
	}
	
	public WebSessionContext getWebSessionContext(){
		return getWebRequestContext().getWebSessionContext();
	}
	
	/**
	 * 获取上传文件的集合Map
	 * @return
	 */
	public Map<String,FileUploaded> getFileUploadedMap(){
		return getWebRequestContext().getFileUploadedMap();
	}
	
	/**
	 * 取得系统配置信息
	 * @return 系统配置信息
	 */
	public AppConfiguration getAppConfiguration(){
		return getWebApplicationContext().getAppConfiguration();
	}
	
	/**
	 * 
	 * <p>函数名称： getSessionUser       </p>
	 * <p>功能说明：获取登录用户信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年11月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public User getSessionUser() {		
		return (User) AppContext.getAppContext().getWebSessionContext().get(USER_SESSION_KEY);
	}
	
	public void resetSessionUser(User user){
		getWebSessionContext().set(AppContext.USER_SESSION_KEY, user);
	}
	
/*	
	public AimsBasicOrg getLoginOrgInfo1(){
		return (AimsBasicOrg) getWebSessionContext().get(AppContext.LOGIN_ORG_SESSION_KEY);
	}
	
	public void setLoginOrgInfo1(AimsBasicOrg org){
		getWebSessionContext().set(AppContext.LOGIN_ORG_SESSION_KEY, org);
	}*/
	
	/**
	 * 
	 * <p>函数名称：  autoWireBean      </p>
	 * <p>功能说明： 自动注入属性值
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param beanClass
	 *
	 * @date   创建时间：2016年11月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public void autoWireBean(Object beanClass){
		getBeanContext().getAutowireCapableBeanFactory().autowireBean(beanClass);
	}
	
	public HttpServletRequest getHttpServletRequest(){
		return this.request;
	}
	
	/**
	 * 
	 * <p>函数名称：  getEnvironment      </p>
	 * <p>功能说明： 获取系统配置文件key-value
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年11月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public Environment getEnvironment(){
		if(evn == null) {
			evn = getBeanContext().getEnvironment();
		}
		
		return evn;
	}
	
}
