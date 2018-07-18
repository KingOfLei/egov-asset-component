package com.bosssoft.egov.asset.basic.entity;

import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/** 
*
* @ClassName   类名：Entity 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class Entity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6628162771661932679L;

	@Override
	public String toString() {
	   String tableName = "";
	   if(this.getClass().isAnnotationPresent(Table.class)){
		   Table table = this.getClass().getAnnotation(Table.class);
		   if(!table.name().equals("")){
			   tableName = table.name();
		   }
	   }
	   return "TableName:[" + tableName + "]," + ReflectionToStringBuilder.toString(this);
	}

}
