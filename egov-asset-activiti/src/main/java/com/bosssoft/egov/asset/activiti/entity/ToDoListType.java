package com.bosssoft.egov.asset.activiti.entity;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 *
 * @ClassName 类名：AuditType
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年12月15日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2016年12月15日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */

// @param 5.handle

public enum ToDoListType {
	CONST(-1, "错误"), BIG(1, "大类"), SMALL(2, "小类"),
	DETAIL(3, "细级"),
	;

	Integer type;
	String name;

	private ToDoListType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getType() + this.getName();
	}

	public static ToDoListType parse(String sideType) {
		try {
			return ToDoListType.valueOf(StringUtilsExt.upperCase(sideType));
		} catch (Exception e) {
			return ToDoListType.CONST;
		}
	}

}
