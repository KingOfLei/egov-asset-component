package com.bosssoft.egov.asset.monitor.entity;

public interface MonitorHndlWayConsts {
	/**
	 * 预警处理方式:0为处理业务数据,1为填写情况说明
	 */
	Long MONITOR_HNDLWAY_BUS = 0l;
	Long MONITOR_HNDLWAY_EXPLAIN = 1l;
	
	/**
	 * 首页显示预警记录数:10条
	 */
	Long HOME_MONITOR_RECORD_COUNT = 10l;
}
