package com.bosssoft.egov.asset.aims.api.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.aims.api.account.mapper.AccountMapper;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;

/** 
*
* @ClassName   类名：AccountServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月4日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月4日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountMapper accountMapper;
	@Override
	public boolean isDockingAccount(Long orgId) {
		return _isDockingAccount(orgId, null);
	}

	@Override
	public boolean isDockingAccount(Long orgId, String bizDate) {
	   return _isDockingAccount(orgId, bizDate);
	}
	
	private boolean _isDockingAccount(Long orgId, String bizDate){
        List<Map<String,Object>> list = accountMapper.queryDockingAccount(orgId, bizDate);
		
		if(list != null && list.size()>0){
			return true;
		}
		
		return false;
	}
	
	@Override
	public Map<String, Object> doRevoke(Long busId, String orgCode,
			String busType) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("busId", busId);
		paramsMap.put("orgCode", orgCode);
		paramsMap.put("busType", busType);	
		accountMapper.doRevoke(paramsMap);
		return paramsMap;
	}

	@Override
	public Map<String, Object> doSubmit(Long busId, String orgCode,
			String busType) {
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("busId", busId);
		paramsMap.put("vNewId", ComponetIdGen.newWKID());
		paramsMap.put("busType", busType);	
		paramsMap.put("vIsRevoke", 0);//1 撤销业务，0 提交业务 	
		accountMapper.doSubmit(paramsMap);
		return paramsMap;
	}

}
