package com.bosssoft.egov.asset.dictionary.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-11-11   xiedeshou　　　新建
 * </pre>
 */
public class Dict implements Serializable {
	

	private static final long serialVersionUID = -6036634709360149032L;

	private String appId;

	private Long dictId;

	private String dictCode;
	
	private String dictName;
	
	private String codeMode;
	
	private String codeRule;
	
	private String isbuiltin;

	private String remark;

	private String creator;
	
	private String createDate;
	
	private String str1;

	private String str2;

	private String str3;

	private String str4;

	private String str5;
	
	private List<DictItem> dictItems;
	// Constructors
 
    /** default constructor */
	public Dict() {
		dictItems = new ArrayList<DictItem>();
	}

	/**
	 * .
	 * @return
	 */
	public String getAppId() {
		return this.appId;
	}

	/**
	 * .
	 * @param appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * .
	 * @return
	 */
	public Long getDictId() {
		return this.dictId;
	}

	/**
	 * .
	 * @param dictId
	 */
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	/**
	 * .
	 * @return
	 */
	public String getDictCode() {
		return this.dictCode;
	}

	/**
	 * .
	 * @param dictCode
	 */
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getDictName() {
		return this.dictName;
	}

	/**
	 * .
	 * @param dictName
	 */
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	/**
	 * .
	 * @return
	 */
	public String getCodeMode() {
		return this.codeMode;
	}

	/**
	 * .
	 * @param codeMode
	 */
	public void setCodeMode(String codeMode) {
		this.codeMode = codeMode;
	}

	/**
	 * .
	 * @return
	 */
	public String getCodeRule() {
		return this.codeRule;
	}

	/**
	 * .
	 * @param codeRule
	 */
	public void setCodeRule(String codeRule) {
		this.codeRule = codeRule;
	}

	/**
	 * .
	 * @return
	 */
	public String getIsbuiltin() {
		return this.isbuiltin;
	}

	/**
	 * .
	 * @param isbuiltin
	 */
	public void setIsbuiltin(String isbuiltin) {
		this.isbuiltin = isbuiltin;
	}

	/**
	 * .
	 * @return
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * .
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * .
	 * @return
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * .
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * .
	 * @return
	 */
	public String getCreateDate() {
		return this.createDate;
	}

	/**
	 * .
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public List<DictItem> getDictItems() {
		return dictItems;
	}

	public void setDictItems(List<DictItem> dictItems) {
		this.dictItems = dictItems;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public String getStr5() {
		return str5;
	}

	public void setStr5(String str5) {
		this.str5 = str5;
	}
	
}
