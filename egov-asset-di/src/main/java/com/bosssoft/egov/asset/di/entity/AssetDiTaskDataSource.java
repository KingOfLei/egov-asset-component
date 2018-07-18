package com.bosssoft.egov.asset.di.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据源配置
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-03-27   BIN　　　新建
 * </pre>
 */
@Table(name="ASSET_DI_TASK_DATA_SOURCE")
public class AssetDiTaskDataSource implements Serializable {
	
	private static final long serialVersionUID = -35235741031503603L;
	
	/**
	 * 数据源配置ID
	 */
	@Id
	@Column(name="F_DB_ID")
	private String dbId;
	
	/**
	 * 数据源类型
	 */
	@Column(name="F_DB_TYPE")
	private String dbType;
	
	/**
	 * 数据源标题
	 */
	@Column(name="F_TITLE")
	private String title;
	
	/**
	 * 数据源IP地址
	 */
	@Column(name="F_IP")
	private String ip;
	
	/**
	 * 数据源端口号
	 */
	@Column(name="F_PORT")
	private String port;
	
	/**
	 * 数据源名称
	 */
	@Column(name="F_DB_NAME")
	private String dbName;
	
	/**
	 * 数据源用户名
	 */
	@Column(name="F_USER_NAME")
	private String userName;
	
	/**
	 * 密码
	 */
	@Column(name="F_PASSWORD")
	private String password;
	
	public String getDbId() {
		return dbId;
	}
	
	public void setDbId(String dbId) {
		this.dbId = dbId;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
