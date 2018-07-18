package com.bosssoft.egov.asset.di.service;

import java.util.List;

import com.bosssoft.egov.asset.di.entity.AssetDiTaskOrgCompare;

/** 
*
* @ClassName   类名：AssetDiService 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2018年1月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2018年1月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface AssetDiService {
	
	/**
	 * 
	 * <p>函数名称：  bussinessProcess      </p>
	 * <p>功能说明： 处理或删除业务表数据
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param taskId
	 * @param taskType
	 * @param reportQj
	 *
	 * @date   创建时间：2018年1月20日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
   public void bussinessProcess(String taskId,String taskType,String reportQj,List<AssetDiTaskOrgCompare> orgCompareList);
}
