package com.bosssoft.egov.asset.guava;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/** 
*
* @ClassName   类名：LoggingSubscriberExceptionHandler 
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
public class LoggingSubscriberExceptionHandler implements SubscriberExceptionHandler{
    private final Logger logger;
    
    public LoggingSubscriberExceptionHandler(String identifier)
    {
      this.logger = Logger.getLogger(EventBus.class.getName() + "." + (String)Preconditions.checkNotNull(identifier));
    }

    public void handleException(Throwable exception, SubscriberExceptionContext context)
    {
      this.logger.log(Level.SEVERE, "Could not dispatch event: " + context.getSubscriber() + " to " + context.getSubscriberMethod(), exception.getCause());
    }
}
