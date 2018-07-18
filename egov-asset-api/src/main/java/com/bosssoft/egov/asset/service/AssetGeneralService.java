package com.bosssoft.egov.asset.service;

import java.util.List;
import java.util.Map;

/** 
*
* @ClassName   类名：GeneralService 
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
public interface AssetGeneralService {
	
	   public List<Map<String,Object>> queryCommon(String sql);
	   
	   public int commonUpdate(String sql);
	   public int commonUpdate(String sql,Map<String,Object> params);
	   
	   public int selectCount(String sql);
	   public int selectCount(String sql,Map<String,Object> params);
	   
	   public List<Map<String,Object>> queryCommon(String sql,Map<String,Object> params);
}
