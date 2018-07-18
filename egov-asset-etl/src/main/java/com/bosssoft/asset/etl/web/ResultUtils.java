package com.bosssoft.asset.etl.web;

import com.bosssoft.platform.runtime.web.response.AjaxResult;

/** 
*
* @ClassName   类名：ResultUtils 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ResultUtils {

	
	/**
	 * 
	 * <p>函数名称：  SUCCESS      </p>
	 * <p>功能说明：  返回成功
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param msg 成功提示语
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public static AjaxResult SUCCESS(String msg){
		return SUCCESS(msg, "");
	}
	
	/**
	 * 
	 * <p>函数名称： SUCCESS       </p>
	 * <p>功能说明：成功
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param msg
	 * @param args
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
//	public static AjaxResult SUCCESS(String msg,Object... args){
//		return SUCCESS(String.format(msg, args), new Object());
//	}
	
	/**
	 * 
	 * <p>函数名称：  SUCCESS      </p>
	 * <p>功能说明：返回成功
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param msg 成功提示语
	 * @param data 额外数据对象
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public static AjaxResult SUCCESS(String msg,Object data){
		AjaxResult result = AjaxResult.SUCCESS;
		result.setMessage(msg);
		result.setData(data);
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：  ERROR      </p>
	 * <p>功能说明： 返回失败信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param errorMsg 失败信息提示
	 * @param data 额外数据对象
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public static AjaxResult ERROR(String errorMsg, Object data){
		AjaxResult result = AjaxResult.ERROR;
		result.setMessage(errorMsg);
		result.setData(data);	
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：  ERROR      </p>
	 * <p>功能说明： 返回错误信息 利用 String.format 格式化信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param errorMsg
	 * @param args
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
//	public static AjaxResult ERROR(String errorMsg,Object... args){
//		return ERROR(String.format(errorMsg, args), new Object());
//	}
	
	/**
	 * 
	 * <p>函数名称：   ERROR     </p>
	 * <p>功能说明： 返回失败信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param errorMsg 失败提示信息
	 * @return
	 *
	 * @date   创建时间：2017年1月6日
	 * @author 作者：xds
	 */
	public static AjaxResult ERROR(String errorMsg){
		return ERROR(errorMsg, "");
	}

}
