package com.bosssoft.egov.asset.guava;

import javax.annotation.PostConstruct;
import javax.persistence.Transient;

/** 
*
* @ClassName   类名：EventBusBase 
* @Description 功能说明：eventbus 订阅者基类 
*                      <p>所有订阅者都需要继承此类 
*                      且加上{@link org.springframework.stereotype.Component @Component}
*                      和
*                       {@link org.springframework.context.annotation.Lazy @Lazy(false)}
*                      因为框架默认是懒加载形式加载 在没有初始化订阅者类时 是不会预初始化的
*                      </p>
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class EventBusBase {
	
	@PostConstruct
	public void init(){
		EventBusHelper.getEventBus().register(this);
	}	
}
