package com.bosssoft.egov.asset.bizlog.entity;
/** 
*
* @ClassName   类名：BizOperConst 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月28日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月28日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class BizOperConst {
	
	public static final String ADD = "ADD";//新增
	public static final String UPDATE = "UPDATE";//更新
	public static final String DELETE = "DELETE";//删除
	public static final String AUDIT = "AUDIT";//审核
	public static final String CANCELPASS = "CANCELPASS";//取消审核	
	public static final String COMMIT = "COMMIT";//提交
	public static final String CANCELCOMMIT = "CANCELCOMMIT";//取消提交
	
	public static final String RETURN = "RETURN";//退回
	public static final String CANCELRETURN = "CANCELRETURN";//取消退回
	public static final String REJECT = "REJECT";//驳回
	public static final String TMP_SAVE = "TMP_SAVE";//暂存
	public static final String OTHER = "OTHER";//其他未知操作
}
