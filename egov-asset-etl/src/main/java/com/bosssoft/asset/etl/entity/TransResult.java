package com.bosssoft.asset.etl.entity;

import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.RowMetaAndData;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：TransResult 
* @Description 功能说明：转换结果
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class TransResult implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6938558269546523242L;

	/**
	 * message 转换结果
	 */
	private String message;
	
	private String devMessage;
	
	/**
	 * 是否异常
	 */
	private boolean exception;
	
	/**
	 * 返回结果集
	 */
	private List<RowMetaAndData> rows;
	
	private Object data;
	
	private String resultFile;
	
	public TransResult(){
		this.message = "处理成功";
		this.exception = false;
		this.resultFile = "";
		rows = new ArrayList<RowMetaAndData>();
	}

	public String getMessage() {
		return message;
	}

	public boolean isException() {
		return exception;
	}

	public List<RowMetaAndData> getRows() {
		return rows;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(String format,Object... objs) {
		this.message = StringUtilsExt.formatString(format, objs);
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

	public void setRows(List<RowMetaAndData> rows) {
		this.rows = rows;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getResultFile() {
		return resultFile;
	}

	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}

	public String getDevMessage() {
		return devMessage;
	}

	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}
	
	public void setDevMessage(String format,Object... objs) {
		this.devMessage = StringUtilsExt.formatString(format, objs);;
	}
	
}
