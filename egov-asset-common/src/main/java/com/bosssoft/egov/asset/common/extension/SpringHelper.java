package com.bosssoft.egov.asset.common.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.platform.common.classloader.ThreadClassLoaderFactory;
import com.bosssoft.platform.common.classloader.ThreadClassLoaderProxy;
import com.bosssoft.platform.common.extension.Activate;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;
import com.bosssoft.platform.common.extension.support.SpringExtensionLoader;
import com.bosssoft.platform.common.repository.AnnotationRepository;
import com.bosssoft.platform.common.utils.ClassUtils;

/** 
*
* @ClassName   类名：SpringHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@SuppressWarnings("rawtypes")
public class SpringHelper {
    private static Logger logger=LoggerFactory.getLogger(SpringExtensionHelper.class);
	
	private static SpringExtensionLoader springExtensionLoader=null;
	
	public static Object lookup(Class<?> objClass){
		getSpringExtension();			
		return springExtensionLoader.lookup(objClass);
	}
	
	private static void getSpringExtension(){
		AnnotationRepository respository;
		Class[] classes;
		if (springExtensionLoader == null) {
		      ThreadClassLoaderProxy proxy = ThreadClassLoaderFactory.getThreadClassLoaderProxy(Thread.currentThread().getContextClassLoader());
		      respository = new AnnotationRepository(proxy.getCurrentThreadClassLoader());
		      respository.scanAnnotation(new String[] { "com.bosssoft.*" });
		      classes = respository.getAnnotatedClasses(Activate.class);
		      for (Class clazz : classes) {
		        if (SpringExtensionLoader.class.isAssignableFrom(clazz)) {
		          springExtensionLoader = (SpringExtensionLoader)ClassUtils.newInstance(clazz, null);
		          break;
		        }
		      }
		}		
	}

}
