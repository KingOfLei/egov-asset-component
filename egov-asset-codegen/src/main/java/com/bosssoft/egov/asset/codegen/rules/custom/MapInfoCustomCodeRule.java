package com.bosssoft.egov.asset.codegen.rules.custom;

import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.codegen.entity.AssetCodegenRule;
import com.bosssoft.egov.asset.codegen.entity.CodegenContext;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：MapInfoCustomCodeRule 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年10月11日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年10月11日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service("mapInfo")
public class MapInfoCustomCodeRule implements ICustomCodeRule {

	@Override
	public String handle(CodegenContext codegenContext) {
		AssetCodegenRule rule = codegenContext.getRule();
		return StringUtilsExt.toString(MapUtilsExt.getString(codegenContext.getParams(), rule.getStr01()));
	}

}
