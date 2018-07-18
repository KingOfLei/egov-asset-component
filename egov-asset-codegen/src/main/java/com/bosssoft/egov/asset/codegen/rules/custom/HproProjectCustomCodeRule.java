package com.bosssoft.egov.asset.codegen.rules.custom;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.codegen.entity.CodegenContext;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;

/** 
*
* @ClassName   类名：HproProjectCustomCodeRule 
* @Description 功能说明：项目自定义
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年8月18日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年8月18日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service("hproProjectInfo")
public class HproProjectCustomCodeRule implements ICustomCodeRule{

	@Override
	public String handle(CodegenContext codegenContext) {
		 Map<String, Object> params = codegenContext.getParams();
		return MapUtilsExt.getString(params, "projectCode");
	}

}
