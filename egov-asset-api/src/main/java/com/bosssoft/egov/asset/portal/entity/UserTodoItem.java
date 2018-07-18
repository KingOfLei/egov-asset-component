package com.bosssoft.egov.asset.portal.entity;

import java.io.Serializable;

/** 
*
* @ClassName   类名：UserTodoItem 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class UserTodoItem implements Serializable,Comparable<UserTodoItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8742674512849544268L;

	public static enum OpenType{
		Dialog,//对话框方式
		NavTab//导航方式
	}
	
	private String id; 
	
	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 打开tab页名称
	 */
	private String tabTitle;

	/**
	 * URL
	 */
	private String url;
	
	/**
	 * 菜单ID（对应URL的菜单ID）
	 */
	private String funcId;
	
	/**
	 * 时间 yyyy-mm-dd
	 */
	private String createdTime;
	
	/**
	 * 链接打开方式
	 */
	private OpenType openType;
	
	/**
	 * url参数 调用时自动带上这个参数
	 */
	private String urlParam;
	
	/**
	 * 待办数
	 */
	private int count;
	
	//业务类型
	private String type;
	
	private String str01;
	
	private String str02;
	
	private String str03;
	
	private String str04;
	
	private String str05;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public OpenType getOpenType() {
		return openType;
	}

	public void setOpenType(OpenType openType) {
		this.openType = openType;
	}
	
	@Override
	public int compareTo(UserTodoItem obj) {
		UserTodoItem item = (UserTodoItem)obj;
		if(item==null){
			return -1;
		}
		if(this.getCreatedTime()==null){
			return -1;
		}
		return this.getCreatedTime().compareTo(item.getCreatedTime());
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public String getStr01() {
		return str01;
	}

	public void setStr01(String str01) {
		this.str01 = str01;
	}

	public String getStr02() {
		return str02;
	}

	public void setStr02(String str02) {
		this.str02 = str02;
	}

	public String getStr03() {
		return str03;
	}

	public void setStr03(String str03) {
		this.str03 = str03;
	}

	public String getStr04() {
		return str04;
	}

	public void setStr04(String str04) {
		this.str04 = str04;
	}

	public String getStr05() {
		return str05;
	}

	public void setStr05(String str05) {
		this.str05 = str05;
	}
 
}
