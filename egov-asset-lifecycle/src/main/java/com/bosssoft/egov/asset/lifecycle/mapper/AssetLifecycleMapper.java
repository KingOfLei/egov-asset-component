package com.bosssoft.egov.asset.lifecycle.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/** 
*
* @ClassName   类名：AssetLifecycleMapper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月27日
* @author      创建人：wujian
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月27日   wujian   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface AssetLifecycleMapper {

	public void insertLife(@Param("destField") String destField, @Param("srcField") String srcField,
			@Param("srcViewName") String srcViewName, @Param("billIdsList") List<Long> billIdsList);

	public void insertLifeLog(@Param("destField") String destField, @Param("srcField") String srcField,
			@Param("srcViewName") String srcViewName, @Param("billId") Long billId);
	
	public void updateLife(@Param("destField") String destField, @Param("srcField") String srcField,
			@Param("billIdsList") List<Long> billIdsList, @Param("srcViewName") String srcViewName);

	public void updateLifeSimple(@Param("destField") String destField, @Param("srcField") String srcField,
			@Param("billIdsList") List<Long> billIdsList,@Param("srcViewName") String srcViewName);

	public void deleteLife(@Param("billIdsList") List<Long> billIdsList,
			@Param("srcViewName") String srcViewName);
	
	public void deleteLifeLog(@Param("billId") Long billId);
}
