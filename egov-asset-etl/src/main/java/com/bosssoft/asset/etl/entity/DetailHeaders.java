package com.bosssoft.asset.etl.entity;

import java.util.ArrayList;
import java.util.List;

import com.bosssoft.egov.asset.basic.entity.Entity;

/** 
*
* @ClassName   类名：DetailHeaders 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月17日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月17日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DetailHeaders extends Entity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4242757046576158529L;
	private String name;
	/**
	 * 	var ALIGN = {
			left: 4,
			center: 5,
			right: 6
		};
		var ALIGN_UPPER = {
			left: 'Left',
			center: 'Center',
			right: 'Right'
		};
	 */
	private Integer align;
	private String title;
	
	private Integer width;
	
	private List<DetailHeaders> subHeaders = new ArrayList<DetailHeaders>();
	
	public String getName() {
		return name;
	}
	public Integer getAlign() {
		return align;
	}
	public String getTitle() {
		return title;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAlign(Integer align) {
		this.align = align;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getWidth() {
		return width == null ? 200 : width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public List<DetailHeaders> getSubHeaders() {
		return subHeaders;
	}
	public void setSubHeaders(List<DetailHeaders> subHeaders) {
		this.subHeaders = subHeaders;
	}
	
	
}
