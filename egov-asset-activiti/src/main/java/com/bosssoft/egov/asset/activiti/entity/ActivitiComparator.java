package com.bosssoft.egov.asset.activiti.entity;

import java.util.Comparator;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
 *
 * @ClassName   类名：ActivitiComparator 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月20日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月20日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ActivitiComparator implements Comparator<ActivitiTaskMoveComboboxShow> {

	public static void main(String[] args) {

	}

	@Override
	public int compare(ActivitiTaskMoveComboboxShow o1, ActivitiTaskMoveComboboxShow o2) {
		String u1 = o1.getUserTaskId();
		String u2 = o2.getUserTaskId();
		
		Integer i1 = Integer.parseInt(StringUtilsExt.substring(u1, 8, u1.length()));
		Integer i2 = Integer.parseInt(StringUtilsExt.substring(u2, 8, u2.length()));
		
        if (i1 < i2) {  
            return -1;  
        } else if (i1 > i2) {  
            return 1;  
        } else {  
            return 0;  
        }  
	}

}
