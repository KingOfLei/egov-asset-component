/*******************************************************************************
 * 福建博思软件1997-2017 版权所有
 * 
 * Auto generated by Bosssoft Studio version 1.0 beta
 * 2017年12月29日上午11:14:24
 *******************************************************************************/
package com.bosssoft.egov.asset.ext.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName	类名：AssetGridExtCacheTemplate.java
 * @Description	功能说明：缓存数据传递管理工具
 * <pre>
 * @date		创建日期：2017年12月29日
 * @author		创建人：chenzhibin
 * @version		版本号：V1.0
 * <pre>
 *-------------------修订记录---------------------
 */
public class AssetGridExtCacheUtil<T> implements Serializable {

	private static final long serialVersionUID = 7208401657469066338L;

	/**
	 * 网格ID
	 */
	private String gridId;

	/**
	 * 用户编码
	 */
	private String userCode;
	
	/**
	 * 业务类型
	 */
	private String bizType;
	
	/**
	 * 操作方式
	 */
	private String type;
	
	/**
	 * 数据列表
	 */
	private List<T> listObj;
	
	/**
	 * 当前操作实例
	 */
	private T oper;
	
	public AssetGridExtCacheUtil(){
		
	}

	public AssetGridExtCacheUtil(String userCode, String gridId, String bizType){
		this.userCode = userCode;
		this.gridId = gridId;
		this.bizType = bizType;
	}
	
	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * 操作方式
	 */
	public String getType() {
		return type;
	}

	/**
	 * 操作方式
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 数据列表
	 */
	public List<T> getListObj() {
		return listObj;
	}

	/**
	 * 数据列表
	 */
	public void setListObj(List<T> listObj) {
		this.listObj = listObj;
	}

	/**
	 * 当前操作实例
	 */
	public T getOper() {
		return oper;
	}

	/**
	 * 当前操作实例
	 */
	public void setOper(T oper) {
		this.oper = oper;
	}
}