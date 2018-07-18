package com.bosssoft.egov.asset.log.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
*
* @ClassName   类名：Operation 
* @Description 功能说明：注解在方法上 来记录此方法操作信息
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Operation {
    String comment() default "";
}
