package com.bosssoft.egov.asset.basic;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsBasicOrg;
import com.bosssoft.egov.asset.aims.api.org.service.AimsBasicOrgService;
import com.bosssoft.egov.asset.common.util.JsonUtilsExt;
import com.bosssoft.egov.asset.common.util.MD5;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.runtime.spring.configuration.SpringContextConfiguration;
import com.bosssoft.platform.shiro.realm.ShiroRealm;

/** 
*
* @ClassName   类名：BaseJunitTest 
* @Description 功能说明：测试基类 所有单元测试类需要继承此类
* <p>
* TODO 利用 mockMvc 可以进行模拟 get post 等常用http请求服务，亦可设置请求参数等。
*   详细可见 开涛博客：http://jinnianshilongnian.iteye.com/blog/2004660 
*</p>
************************************************************************
* @date        创建日期：2017年6月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年6月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
//调用Spring单元测试类
@RunWith(SpringJUnit4ClassRunner.class)
//注入web系统上下文环境
@WebAppConfiguration(value = "src/main/webapp")
//加载spring启动类 框架自身使用了注解启动 
@ContextConfiguration(classes = SpringContextConfiguration.class)
@Rollback(value=true)
@Transactional(transactionManager = "transactionManager")
public abstract class BaseJunitTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private  AimsBasicOrgService orgService;

	//Controller 测试类 可以模拟 http请求
	protected MockMvc mockMvc;
	
	protected MockHttpServletRequest mockReuqest = null;

	@Before
	public void setUp() {
		mockMvc = webAppContextSetup(wac).build();	
		resetSecurityManager();
	}
	
	protected MockHttpServletRequestBuilder post(String urlTemplate){
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(urlTemplate);
		builder.with(new defaultRequestPostProcessor(mockReuqest));
		return builder;
	}
	
	protected MockHttpServletRequestBuilder post(String urlTemplate, Object... urlVars){
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(UriComponentsBuilder.fromUriString(urlTemplate).buildAndExpand(urlVars).encode().toUri());
		builder.with(new defaultRequestPostProcessor(mockReuqest));
		return builder;
	}
	
	protected MockHttpServletRequestBuilder get(String urlTemplate){
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(urlTemplate);
		builder.with(new defaultRequestPostProcessor(mockReuqest));
		return builder;
	}
	
	protected MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVars){
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(UriComponentsBuilder.fromUriString(urlTemplate).buildAndExpand(urlVars).encode().toUri());
		builder.with(new defaultRequestPostProcessor(mockReuqest));
		return builder;
	}
	
	/**
	 * 
	 * <p>函数名称： getUserSession       </p>
	 * <p>功能说明：获取session
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param username
	 * @param passwrod
	 * @return
	 * @throws Exception
	 *
	 * @date   创建时间：2017年8月13日
	 * @author 作者：xds
	 */
	protected HttpSession getUserSession(String username,String passwrod) throws Exception{        
		return login(username, passwrod).getRequest().getSession();
	}
	
	/**
	 * 
	 * <p>函数名称：login        </p>
	 * <p>功能说明：登录
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param username
	 * @param passwrod
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 *
	 * @date   创建时间：2017年8月13日
	 * @author 作者：xds
	 */
	protected MvcResult login(String username,String passwrod) throws NoSuchAlgorithmException, Exception{
		MvcResult result = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/u/submitLogin.do")
				 .param("username", username)
				 .param("password",MD5.md5(username + "#" + passwrod).toLowerCase())
				 .param("rememberMe", "false")
				).andReturn();
		
		//获取返回的ResponseBody 值
		Map<String, Object> resultMap = JsonUtilsExt.json2Map(result.getResponse().getContentAsString());
		//判断是否用户名密码正确	
		Assert.assertEquals(MapUtilsExt.getString(resultMap, "message"), Integer.valueOf(200), MapUtilsExt.getInteger(resultMap, "status"));
		this.mockReuqest = result.getRequest();
		return result;
	};
	
	/**
	 * 
	 * <p>函数名称： toggleOrg       </p>
	 * <p>功能说明： 切换单位
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param orgCode
	 * @param roleCode
	 * @param roleName
	 * @throws Exception
	 *
	 * @date   创建时间：2017年8月13日
	 * @author 作者：xds
	 */
	protected void toggleOrg(String orgCode,String roleCode,String roleName) throws Exception{
		AimsBasicOrg org = new AimsBasicOrg();
		org.setOrgCode(orgCode);
		org.setRoleCode(roleCode);
		org.setRoleName(roleName);
		toggleOrg(org);
	}
	
	/**
	 * 
	 * <p>函数名称：     toggleOrg   </p>
	 * <p>功能说明： 切换单位
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param org
	 * @throws Exception
	 *
	 * @date   创建时间：2017年8月13日
	 * @author 作者：xds
	 */
	protected void toggleOrg(AimsBasicOrg org) throws Exception{
		Assert.assertNotNull("单位编码(orgCode)不能为null", org.getOrgCode());
		Assert.assertNotNull("用户此单位对应角色编码(roleCode)不能为null", org.getRoleCode());
		Assert.assertNotNull("用户此单位对应角色名称(roleName)不能为null", org.getRoleName());
		//先请求一个.do 方法
		this.mockMvc.perform(post("/egov/asset/aims/basic/aimsbasicorg/showIndex.do"));
		User myUser = (User) this.mockReuqest.getSession().getAttribute(AppContext.USER_SESSION_KEY);
		AimsBasicOrg orgInfo = orgService.queryOneByCode(org.getOrgCode());
		if(orgInfo != null){
			 myUser.setRgnId(orgInfo.getRgnId());
			 myUser.setRgnCode(orgInfo.getRgnCode());
			 myUser.setRgnName(orgInfo.getRgnName());
			 myUser.setOrgId(orgInfo.getOrgId());
			 myUser.setOrgCode(orgInfo.getOrgCode());
			 myUser.setOrgName(orgInfo.getOrgName());
			 myUser.setAgenPrincipal(orgInfo.getAgenPrincipal());
			 myUser.setOrg(orgInfo);
		 }
		 myUser.setChoseOrg(true);
		 myUser.setRoleCodes(new String[]{org.getRoleCode()});
		 myUser.setRoleNames(new String[]{org.getRoleName()});
		 //重置用户信息
		this.mockReuqest.getServletContext().setAttribute(AppContext.USER_SESSION_KEY, myUser);
	}
	
	/**
	 * 
	 * <p>函数名称：  logout      </p>
	 * <p>功能说明： 退出
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @throws Exception
	 *
	 * @date   创建时间：2017年8月13日
	 * @author 作者：xds
	 */
	protected void logout() throws Exception{
		MvcResult result = this.mockMvc.perform(get("/u/logout.do")).andReturn();		
		Assert.assertEquals("退出失败", "redirect:/login.do", result.getModelAndView().getViewName());
	}
			
	private void resetSecurityManager(){
		ShiroRealm resultRealm = new ShiroRealm();
		//关闭缓存SecurityUtils.setSecurityManager
		resultRealm.setAuthorizationCachingEnabled(false);		
		DefaultWebSecurityManager manager = wac.getBean("securityManager", DefaultWebSecurityManager.class);
		manager.setRealm(resultRealm);		
		//静态调用
		SecurityUtils.setSecurityManager(manager);
	}
	
	final class defaultRequestPostProcessor implements RequestPostProcessor{

		protected MockHttpServletRequest mockReuqest = null;

		
		public defaultRequestPostProcessor(MockHttpServletRequest request) {
			 this.mockReuqest = request;
		}
		
		@Override
		public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
			//參數請求在原來的 request 中，故複製一份
			request.setCookies(mockReuqest.getCookies());
			request.setSession(mockReuqest.getSession());
			request.setRequestedSessionId(mockReuqest.getRequestedSessionId());
			return request;
		}
		
	} 
	
}
