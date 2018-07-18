package com.bosssoft.asset.etl.core;

import java.util.Map;

import com.bosssoft.asset.etl.entity.TransResult;

/** 
*
* @ClassName   类名：AfterProcessService 
* @Description 功能说明：任务完成后回调接口
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月12日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月12日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface AfterProcessService {

	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param configCode
	 * @param params
	 *
	 * @date   创建时间：2017年9月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public void doProcess(String configCode, Map<String,Object> params,TransResult result);
}
