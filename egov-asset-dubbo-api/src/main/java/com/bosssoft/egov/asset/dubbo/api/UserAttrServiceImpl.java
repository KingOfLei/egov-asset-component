package com.bosssoft.egov.asset.dubbo.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.platform.shiro.spi.UserAttributeInitializer;


/** 
*
* @ClassName   类名：UserAttrServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年3月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年3月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service
public class UserAttrServiceImpl implements UserAttributeInitializer{

	@Autowired
	private AssetGeneralService generalService;
	@Override
	public Map<String, Object> getUserAttributes(String userCode) {		
		//根据用户编码获取用户对应单位信息
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT RGN_ID,RGN_CODE,RGN_NAME,ORG_ID,ORG_CODE,ORG_NAME,USER_CODE,USER_NAME,ROLE_CODE,ROLE_NAME ");
		sql.append(" FROM VAFA_MAXUSERROLE T");
		sql.append(" WHERE USER_CODE = '${USER_CODE}'");
		sql.append(" ORDER BY ROLE_CODE ");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("USER_CODE", userCode);
		List<Map<String, Object>> orgInfo = generalService.queryCommon(StringUtilsExt.environmentSubstituteByMapObj(sql.toString(), params));
		if(orgInfo != null && !orgInfo.isEmpty()){
			params.putAll(MapUtilsExt.ObjectToStringMap(orgInfo.get(0)));
		}
		return params;
	}

}
