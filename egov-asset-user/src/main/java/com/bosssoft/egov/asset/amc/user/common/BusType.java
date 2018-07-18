package com.bosssoft.egov.asset.amc.user.common;
/** 
*
* @ClassName   类名：BizStatus 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月15日
* @author      创建人：hzx
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月15日   hzx   创建该类功能。
*
***********************************************************************
*</p>
*/
public class BusType {
	
	public static final BusType ADD = new BusType("ADD_USER", "新增用户") ;
	public static final BusType EDIT = new BusType("EDIT_USER", "修改用户");
	public static final BusType DELETE = new BusType("DEL_USER", "删除用户");

	public String TYPE;
	public String NAME;
	
	private BusType(String type,String name){
		this.TYPE = type;
		this.NAME = name;
	}
	
}
