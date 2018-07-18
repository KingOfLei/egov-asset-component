package com.bosssoft.egov.asset.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bosssoft.egov.asset.basic.BaseController;
import com.bosssoft.egov.asset.cache.CacheUtils;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：ApiController 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月10日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月10日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Controller
@RequestMapping(value="egov/asset/pub/api/",name="公共api接口")
public class ApiController extends BaseController{
 
	@RequestMapping(value="clearCahce.do",name="清除指定缓存")
	@ResponseBody
	public Map<String,Object> clearCache(String cacheName){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("flag", true);
		result.put("msg", StringUtilsExt.formatString("清除[{0}]缓存成功", cacheName));
		CacheUtils.clearCache(cacheName);
		return result;
	}
}
