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
*   2017年9月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class BizStatus {
	
	public static final BizStatus TMP_SAVE = new BizStatus(1, "暂存") ;
	public static final BizStatus YET_SAVE = new BizStatus(10, "新建");
	public static final BizStatus RETURN = new BizStatus(9, "已退回");
	public static final BizStatus REJECT = new BizStatus(7, "已驳回");
	public static final BizStatus COMMIT_AUDIT = new BizStatus(100, "审核中") ;
	public static final BizStatus END_AUDIT = new BizStatus(999999, "已入账") ;
	public static final BizStatus SPLIT_AUDIT = new BizStatus(40, "已细化") ;
	public static final BizStatus LOGIC_DEL = new BizStatus(51, "已删除") ;

	public Integer STATUS;
	public String NAME;
	
	private BizStatus(Integer status,String name){
		this.STATUS = status;
		this.NAME = name;
	}
	
}
