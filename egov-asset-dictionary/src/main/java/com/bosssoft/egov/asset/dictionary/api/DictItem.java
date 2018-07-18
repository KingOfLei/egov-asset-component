package com.bosssoft.egov.asset.dictionary.api;

import java.io.Serializable;

/** 
 * 字典明细
 * @author mofei
 * @version 2014-5-29
 * @since 1.6
 */
public class DictItem implements Serializable{
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -2800107940374320419L;
	
	/**
	 * 应用ID
	 */
	private String appId;
	
	/**
	 * 节点字典类型id
	 */
	private Long dictId;
	
	/**
	 * 节点字典类型编码
	 */
	private String dictCode;
	
	private String dictName;
	
	/**
	 * 节点id
	 */
	private Long itemId;

	/**
	 * 节点编码
	 */
	private String itemCode;

	/**
	 * 节点名称
	 */		
	private String itemName;

	/**
	 * 节点父类id
	 */
	private Long itemPid;

	/**
	 * 节点父类编码
	 */
	private String itemPcode;

	/**
	 * 节点级次
	 */
	private Integer itemLevel;

	/**
	 * 是否最细级
	 */
	private Integer itemIsleaf;

	private String creator;

	private String createDate;

	private String remark;

	private String isenable;

	private String str01;

	private String str02;

	private String str03;

	private String str04;

	private String str05;
	
	private String str06;
	private String str07;
	private String str08;
	private String str09;
	private String str10;
	
	private String str11;
	private String str12;
	private String str13;
	private String str14;
	private String str15;
	private String str16;
	private String str17;
	private String str18;
	private String str19;
	private String str20;
	

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
	public Long getItemId() {
		return this.itemId;
	}

	/**
	 * .
	 * @param itemId
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * .
	 * @return
	 */
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * .
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * .
	 * @return
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * .
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * .
	 * @return
	 */
	public Long getItemPid() {
		return this.itemPid;
	}

	/**
	 * .
	 * @param itemPid
	 */
	public void setItemPid(Long itemPid) {
		this.itemPid = itemPid;
	}

	/**
	 * .
	 * @return
	 */
	public String getItemPcode() {
		return this.itemPcode;
	}

	/**
	 * .
	 * @param itemPcode
	 */
	public void setItemPcode(String itemPcode) {
		this.itemPcode = itemPcode;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getItemLevel() {
		return this.itemLevel;
	}

	/**
	 * .
	 * @param itemLevel
	 */
	public void setItemLevel(Integer itemLevel) {
		this.itemLevel = itemLevel;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getItemIsleaf() {
		return this.itemIsleaf;
	}

	/**
	 * .
	 * @param itemIsleaf
	 */
	public void setItemIsleaf(Integer itemIsleaf) {
		this.itemIsleaf = itemIsleaf;
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
	public String getIsenable() {
		return this.isenable;
	}

	/**
	 * .
	 * @param isenable
	 */
	public void setIsenable(String isenable) {
		this.isenable = isenable;
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

	/**
     * 一下用在下拉树上
     * 
	 */
	public boolean isParent(){
		return !isLeaf();
	}
	
	public boolean getIsParent() {		
		return 0 == (itemIsleaf ==null? 0 : itemIsleaf);
	}
	
	public boolean isLeaf(){
		return (itemIsleaf ==null? 0 : itemIsleaf) == 1;
	}
	
	@Override
	public String toString() {
		return "DicItem [itemId=" + itemId + ", itemCode=" + itemCode + ", itemName=" + itemName
				+ ", itemPid=" + itemPid + ", itemPCode=" + itemPcode + ", itemLevel=" + itemLevel
				+ ", itemIsLeaf=" + itemIsleaf + ", isParent=" + isParent() + ", isLeaf=" + isLeaf()
				+ "]";
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getStr06() {
		return str06;
	}

	public void setStr06(String str06) {
		this.str06 = str06;
	}

	public String getStr07() {
		return str07;
	}

	public void setStr07(String str07) {
		this.str07 = str07;
	}

	public String getStr08() {
		return str08;
	}

	public void setStr08(String str08) {
		this.str08 = str08;
	}

	public String getStr09() {
		return str09;
	}

	public void setStr09(String str09) {
		this.str09 = str09;
	}

	public String getStr10() {
		return str10;
	}

	public void setStr10(String str10) {
		this.str10 = str10;
	}

	public String getStr11() {
		return str11;
	}

	public void setStr11(String str11) {
		this.str11 = str11;
	}

	public String getStr12() {
		return str12;
	}

	public void setStr12(String str12) {
		this.str12 = str12;
	}

	public String getStr13() {
		return str13;
	}

	public void setStr13(String str13) {
		this.str13 = str13;
	}

	public String getStr14() {
		return str14;
	}

	public void setStr14(String str14) {
		this.str14 = str14;
	}

	public String getStr15() {
		return str15;
	}

	public void setStr15(String str15) {
		this.str15 = str15;
	}

	public String getStr16() {
		return str16;
	}

	public void setStr16(String str16) {
		this.str16 = str16;
	}

	public String getStr17() {
		return str17;
	}

	public void setStr17(String str17) {
		this.str17 = str17;
	}

	public String getStr18() {
		return str18;
	}

	public void setStr18(String str18) {
		this.str18 = str18;
	}

	public String getStr19() {
		return str19;
	}

	public void setStr19(String str19) {
		this.str19 = str19;
	}

	public String getStr20() {
		return str20;
	}

	public void setStr20(String str20) {
		this.str20 = str20;
	}
	
}
