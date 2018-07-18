package com.bosssoft.egov.asset.aims.api.config.entity;

import java.io.Serializable;

/** 
 *
 * @ClassName   类名：ApiPage 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月21日
 * @author      创建人：黄振雄
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月21日   黄振雄   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ApiPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4777034858293803280L;

	 /**
     * 页码，从1开始
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;
    /**
     * 起始行
     */
    private int startRow;
    /**
     * 末行
     */
    private int endRow;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 包含count查询
     */
    private boolean count;
    /**
     * count信号，3种情况，null的时候执行默认BoundSql,true的时候执行count，false执行分页
     */
    private Boolean countSignal;
    /**
     * 排序
     */
    private String orderBy;
    /**
     * 只增加排序
     */
    private boolean orderByOnly;
    /**
     * 分页合理化
     */
    private Boolean reasonable;
    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     */
    private Boolean pageSizeZero;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public boolean isCount() {
		return count;
	}
	public void setCount(boolean count) {
		this.count = count;
	}
	public Boolean getCountSignal() {
		return countSignal;
	}
	public void setCountSignal(Boolean countSignal) {
		this.countSignal = countSignal;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public boolean isOrderByOnly() {
		return orderByOnly;
	}
	public void setOrderByOnly(boolean orderByOnly) {
		this.orderByOnly = orderByOnly;
	}
	public Boolean getReasonable() {
		return reasonable;
	}
	public void setReasonable(Boolean reasonable) {
		this.reasonable = reasonable;
	}
	public Boolean getPageSizeZero() {
		return pageSizeZero;
	}
	public void setPageSizeZero(Boolean pageSizeZero) {
		this.pageSizeZero = pageSizeZero;
	}

    
    
}
