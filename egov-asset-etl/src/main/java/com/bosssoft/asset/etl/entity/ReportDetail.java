package com.bosssoft.asset.etl.entity;

import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.basic.entity.Entity;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：ReportDetail 
* @Description 功能说明：网格导出实体
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
@SuppressWarnings("rawtypes")
public class ReportDetail extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1703682821639491878L;

    private List<Map> detailDatas;
    
    private List<DetailHeaders> detailHeaders;
    
    /**
     * fileName 导出文件名
     */
    private String fileName;
    
    /**
     * sheetName sheet 名称
     */
    private String sheetName;
    
    /**
     * extension 扩展名 xls xlsx
     */
    private String extension;
    
    private Integer fixCols;

    public ReportDetail(){
    	extension = "xlsx";
    	sheetName = "Sheet1";
    }

	public List<Map> getDetailDatas() {
		return detailDatas;
	}

	public void setDetailDatas(List<Map> detailDatas) {
		this.detailDatas = detailDatas;
	}

	public List<DetailHeaders> getDetailHeaders() {
		return detailHeaders;
	}

	public void setDetailHeaders(List<DetailHeaders> detailHeaders) {
		this.detailHeaders = detailHeaders;
	}

	public Integer getFixCols() {
		return fixCols;
	}

	public void setFixCols(Integer fixCols) {
		this.fixCols = fixCols;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSheetName() {
		return StringUtilsExt.toString(sheetName, "sheet1");
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
