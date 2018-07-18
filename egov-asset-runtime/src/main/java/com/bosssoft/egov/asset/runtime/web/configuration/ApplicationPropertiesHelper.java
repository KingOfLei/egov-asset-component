package com.bosssoft.egov.asset.runtime.web.configuration;

import com.bosssoft.egov.asset.runtime.web.context.AppContext;

/** 
*
* @ClassName   类名：ApplicationPropertiesHelper 
* @Description 功能说明：获取bosssoft.properties application.properties 配置文件数据
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月17日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月17日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ApplicationPropertiesHelper {
    
	/**
	 * 
	 * <p>函数名称： getProperty       </p>
	 * <p>功能说明： 获取系统参数
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param key
	 * @return
	 *
	 * @date   创建时间：2016年11月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getProperty(String key){
		return AppContext.getAppContext().getEnvironment().getProperty(key);
	}
	
	/**
	 * 
	 * <p>函数名称： getProperty      </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param key key值
	 * @param defaultValue 默认返回值
	 * @return
	 *
	 * @date   创建时间：2016年11月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getProperty(String key, String defaultValue){
		return AppContext.getAppContext().getEnvironment().getProperty(key, defaultValue);
	}
	
	/**
	 * 
	 * <p>函数名称： getRequiredProperty       </p>
	 * <p>功能说明： 返回指定的key对应的value值 无value值 会报错
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param key
	 * @return
	 *
	 * @date   创建时间：2016年11月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getRequiredProperty(String key){
		return AppContext.getAppContext().getEnvironment().getRequiredProperty(key);
	}
}
