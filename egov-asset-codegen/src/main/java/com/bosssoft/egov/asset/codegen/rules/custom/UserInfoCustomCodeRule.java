package com.bosssoft.egov.asset.codegen.rules.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.codegen.entity.AssetCodegenRule;
import com.bosssoft.egov.asset.codegen.entity.CodegenContext;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;

/** 
*
* @ClassName   类名：UserInfoCustomCodeRule 
* @Description 功能说明：获取用户信息类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service("userInfo")
public class UserInfoCustomCodeRule implements ICustomCodeRule{

	@Override
	public String handle(CodegenContext codegenContext) {
		AssetCodegenRule rule = codegenContext.getRule();
		//获取参数 str01 字段存储字段信息
		String[] fields = StringUtilsExt.split(rule.getStr01(), ',');
		String speStr = rule.getStr02();//组合字段的符号
		User user = AppContext.getAppContext().getSessionUser();
		List<String> result = new ArrayList<String>();
		try{
			for(String field : fields){
				result.add(BeanUtilsExt.getProperty(user, field));
			}
		} catch(Exception e){
			//不存在此字段信息
			result.add("");
		} 
		return StringUtilsExt.join(result.toArray(), speStr);
	}

}
