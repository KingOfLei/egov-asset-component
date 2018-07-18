package com.bosssoft.egov.asset.grid.entity;

import java.io.Serializable;

/** 
*
* @ClassName   类名：Order 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年10月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年10月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726092776302876670L;

	private String field;

	private OrderDirection order = OrderDirection.asc;

	public Order() {
	}

	public Order(String field) {
		this.field = field;
	}

	public Order(String field, OrderDirection order) {
		this.field = field;
		this.order = order;
	}

	public String getField() {
		return field;
	}

	public OrderDirection getOrder() {
		return order;
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setOrder(OrderDirection order) {
		this.order = order;
	}

	public String toString() {
		return this.field + " " + this.order;
	}
}
