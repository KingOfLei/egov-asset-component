package com.bosssoft.egov.asset.drc;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：DRCIn 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月7日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月7日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Intercepts({
	@Signature(
				type = Executor.class, method = "query", args = {
						MappedStatement.class, Object.class, RowBounds.class,
						ResultHandler.class }			),
	@Signature(
			type = Executor.class, method = "update", args = {
					MappedStatement.class, Object.class }			)
})
public class DrcSqlInterceptor implements Interceptor{

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		 Object object = invocation.getArgs()[1];
		 if(object == null){
			 object = new ParamMap<Object>();
		 }
		 if(object instanceof ParamMap) {
			 Map<String,Object> params = (Map<String,Object>)object;
			 try{				 
//				 User user = AppContext.getAppContext().getSessionUser();					
//				 //动态新增一个参数	
//				 if(user != null){				   
//				   params.put("DRC_SQL", user.getDrcSql());
//				 }
				 if(WebApplicationContext.getContext().getCurrentUser() != null){
					 params.put("DRC_SQL", WebApplicationContext.getContext().getCurrentUser().getProperty("DRC_SQL"));
				 }
			 } catch(Exception e){
				 //判断是否前端发起请求服务
				 if(WebApplicationContext.getContext().getCurrentUser() == null){
					 //非前端请求 可能是后端定时
					 params.put("DRC_SQL", " AND 1=1 ");
				 } else {
				     params.put("DRC_SQL", " AND 1=2 ");
				 }
		     }		    	 
		 }
		 return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub
		
	}

}
