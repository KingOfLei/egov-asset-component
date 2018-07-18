package com.bosssoft.egov.asset.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.mapper.AssetGeneralMapper;

/** 
*
* @ClassName   类名：GeneralServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service
public class AssetGeneralServiceImpl implements AssetGeneralService{

	@Autowired
	private AssetGeneralMapper generalMapper;
	
	@Override
	public List<Map<String, Object>> queryCommon(String sql) {
		// TODO Auto-generated method stub
		return generalMapper.queryCommon(sql,new HashMap<String,Object>());
	}

	@Override
	public int commonUpdate(String sql) {
		// TODO Auto-generated method stub
		return generalMapper.commonUpdate(sql,new HashMap<String,Object>());
	}

	@Override
	
	public int selectCount(String sql) {
		// TODO Auto-generated method stub
		return generalMapper.selectCount(sql,new HashMap<String,Object>());
	}

	@Override
	public List<Map<String, Object>> queryCommon(String sql,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return generalMapper.queryCommon(sql, params);
	}

	@Override
	public int commonUpdate(String sql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return generalMapper.commonUpdate(sql, params);
	}

	@Override
	public int selectCount(String sql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return generalMapper.selectCount(sql, params);
	}
	 
}
