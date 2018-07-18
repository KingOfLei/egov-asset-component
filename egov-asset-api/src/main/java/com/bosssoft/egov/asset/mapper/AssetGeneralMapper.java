package com.bosssoft.egov.asset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/** 
*
* @ClassName   类名：GeneralMapper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月22日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月22日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface AssetGeneralMapper {
	
   public List<Map<String,Object>> queryCommon(@Param("sql") String sql,@Param("params")Map<String,Object> params);
   
   public int commonUpdate(@Param("sql") String sql,@Param("params")Map<String,Object> params);
   
   @Select(" ${sql} ")
   public int selectCount(@Param("sql") String sql,@Param("params")Map<String,Object> params);
}
