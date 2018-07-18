package com.bosssoft.egov.asset.template.service;

import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.runtime.User;

public interface PrintDataProcessService {
	 
	/**
	 * 
	 * @param user 登录用户信息
	 * @param params  打印模板信息
	 * @param printData  打印数据
	 * @return
	 */
	public Map<String,List<Map<String, Object>>> doProcess(User user,Map<String,Object> params,Map<String,List<Map<String, Object>>> printData);
  
}
