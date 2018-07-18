package com.bosssoft.egov.asset.amc.user.common;

/** 
*
* @ClassName   类名：EditStatus 
* @Description 功能说明：编辑状态
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
public class EditStatus {
	
	public static final EditStatus ADD = new EditStatus("ADD", "新增");
	public static final EditStatus EDIT = new EditStatus("EDIT", "编辑");
	public static final EditStatus VIEW = new EditStatus("VIEW", "查看");
	public static final EditStatus AUDIT = new EditStatus("AUDIT", "审核");
	public static final EditStatus AUDIT_BACK = new EditStatus("AUDIT_BACK", "退回");
	
	public String STATUS;
	public String NAME;
	
	private EditStatus(String status,String name){
		this.STATUS = status;
		this.NAME = name;
	}

	public static EditStatus getEditStatus(Integer bizStatus,String action){		
		EditStatus returnStatus = EditStatus.VIEW;
		switch (bizStatus) {
		case 1://暂存
		case 7://驳回
		case 9://退回
			//编辑状态 都是查看
			if(EditStatus.EDIT.STATUS.equalsIgnoreCase(action)){
				returnStatus = EditStatus.EDIT;
			} else if("0".equals(action) || "wait".equalsIgnoreCase(action)){
				returnStatus = EditStatus.EDIT;				
			} else {
				returnStatus = EditStatus.VIEW;
			}
			break;			
		case 999999://终审
//				returnStatus = EditStatus.VIEW;
				if(EditStatus.EDIT.STATUS.equalsIgnoreCase(action)){
					returnStatus = EditStatus.EDIT;
				}else{
					returnStatus = EditStatus.VIEW;
				}
			break;  
		default:
			//其他状态 直接查看
			if(bizStatus < 0){
				returnStatus = EditStatus.VIEW;
			} else {
				//加入待办的判断
				if("0".equalsIgnoreCase(action)){//待办
					return EditStatus.AUDIT;
				}
				//审核中
				if(EditStatus.AUDIT.STATUS.equalsIgnoreCase(action)){
					returnStatus = EditStatus.AUDIT;
				} else {
					//只能查看
					returnStatus = EditStatus.VIEW;
				}
			}
			break;
		}
		return returnStatus;
	}

}
