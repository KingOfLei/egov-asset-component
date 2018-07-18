package com.bosssoft.egov.asset.guava;

import com.bosssoft.platform.runtime.web.context.WebApplicationContext;
import com.google.common.eventbus.EventBus;

/** 
*
* @ClassName   类名：EventBusHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月12日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月12日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class EventBusHelper {
	
	public static EventBus eventBus;

	public static EventBus getEventBus() {
		if(eventBus == null){
			eventBus = WebApplicationContext.getContext().lookup(EventBus.class);
		}
		return eventBus;
	}

}
