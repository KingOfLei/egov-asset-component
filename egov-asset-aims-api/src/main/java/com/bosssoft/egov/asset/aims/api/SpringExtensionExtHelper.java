package com.bosssoft.egov.asset.aims.api;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.apache.commons.lang.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;
import com.bosssoft.platform.common.utils.AnnotationUtils;
import com.bosssoft.platform.common.utils.ClassUtils;

/** 
*
* @ClassName   类名：ApiSpringExtensionHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月4日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月4日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class SpringExtensionExtHelper {
	
    private static Logger logger=LoggerFactory.getLogger(SpringExtensionExtHelper.class);
	
	private static Class<?> springLoaderClass=null;
	
	private static final String SPRING_EXTENSION_LOADER_CLASS="com.bosssoft.platform.runtime.spi.configuration.SpringContextUtil";

	/**
	 * 初始化Spring注解属性字段
	 * @param instance
	 */
	public static void initAutowireFields(Object instance){
		if(springLoaderClass==null){
			try{
				springLoaderClass=ClassUtils.forName(SPRING_EXTENSION_LOADER_CLASS, Thread.currentThread().getContextClassLoader());
			}catch(Throwable t){
				logger.warn("Can't load class {}",SPRING_EXTENSION_LOADER_CLASS,t);
			}
		}
		
		if(springLoaderClass!=null){
			Field[] fields=AnnotationUtils.getAnnotatedFields(instance.getClass(), Resource.class);
			for(Field field:fields){
				try{
					Object value = null;
					String name = field.getAnnotation(Resource.class).name();
					if(name == null || "".equals(name)){
					   value = MethodUtils.invokeStaticMethod(springLoaderClass, "getBeanByType", field.getType());
					} else {
					   value = MethodUtils.invokeStaticMethod(springLoaderClass, "getBean", name);
					}
					if(value!=null){
						field.setAccessible(true);
						field.set(instance, value);
					}
				}catch(Exception e){
					logger.error("Set spring bean to field "+field.getDeclaringClass()+"."+field.getName()+" error!",e);
				}
			}
		}
	}
	
}
