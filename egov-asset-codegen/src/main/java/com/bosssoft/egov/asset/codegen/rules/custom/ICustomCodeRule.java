package com.bosssoft.egov.asset.codegen.rules.custom;

import com.bosssoft.egov.asset.codegen.entity.CodegenContext;

/** 
*
* @ClassName   类名：ICustomCodeRule 
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
public interface ICustomCodeRule {
    public String handle(CodegenContext codegenContext);
}
