/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Fri Dec 23 12:31:26 CST 2016
 */
package com.bosssoft.egov.cms.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bosssoft.platform.common.utils.DateUtils;
import com.bosssoft.platform.common.utils.StringUtils;

/**
 * 通知公告基本信息表对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-23   Administrator　　　新建
 * </pre>
 */
 @Table(name = "CMS_NOTICE")
public class CmsNotice implements java.io.Serializable {

	private static final long serialVersionUID = 161223123210301L;
	
	// Fields
	
	/**
	 * ID.
	 */
	@Id
    @Column(name = "ID")
	private Long id;
	/**
	 * .
	 */
    @Column(name = "RGN_ID")
	private Long rgnId;
	/**
	 * .
	 */
    @Column(name = "ORG_ID")
	private Long orgId;
	/**
	 * 标题.
	 */
    @Column(name = "TITLE")
	private String title;
	/**
	 * 内容.
	 */
    @Column(name = "CONTENT")
	private String content;
	/**
	 * 发布者id
	 */
    @Column(name = "REALEASE_USER_ID")
	private String realeaseUserId;
	/**
	 * 发布者
	 */
    @Column(name = "REALEASE_USER")
	private String realeaseUser;
	/**
	 * 发布日期
	 */
    @Column(name = "REALEASE_DATE")
	private String realeaseDate;
	/**
	 * 接收类型
	 */
    @Column(name = "RECEIVE_TYPE")
	private String receiveType;
	/**
	 * 审核人
	 */
    @Column(name = "AUDITOR")
	private String auditor;
	/**
	 * 审核时间
	 */
    @Column(name = "AUDITTIME")
	private String audittime;
	/**
	 * 审核意见
	 */
    @Column(name = "AUDITOPIN")
	private String auditopin;
	/**
	 * 状态 ：0为未审核1为已审核未发布2为已发布
	 */
    @Column(name = "STATUS")
	private Integer status;
	/**
	 * 通知公告类型: 通知0、公告1.
	 */
    @Column(name = "TYPE")
	private String type;
	/**
	 * 是否发送至门户
	 */
    @Column(name = "IS_PORTAL")
	private Integer isPortal;
	/**
	 * 是否置顶
	 */
    @Column(name = "IS_TOP")
	private Integer isTop;
	/**
	 * 备注
	 */
    @Column(name = "REMARK")
	private String remark;
	/**
	 * 置顶过期时间
	 */
    @Column(name = "TOP_EXPIR")
	private String topExpir;
    
    @Column(name="START_DATE")
    private String startDate;
    
    @Column(name="END_DATE")
    private String endDate;
    
    @Column(name="WEN_HAO")
    private String wenHao;
    
    @Transient
    private Integer attachCnt;
    
    @Transient
    private Integer read;
    
    /**
     * 是否首页弹窗标识
     */
    @Transient
    private String isShow;
	
	// Constructors
 
    /** default constructor */
	public CmsNotice() {
	}

	/**
	 * ID.
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * ID.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * .
	 * @return
	 */
	public Long getRgnId() {
		return this.rgnId;
	}

	/**
	 * .
	 * @param rgnId
	 */
	public void setRgnId(Long rgnId) {
		this.rgnId = rgnId;
	}

	/**
	 * .
	 * @return
	 */
	public Long getOrgId() {
		return this.orgId;
	}

	/**
	 * .
	 * @param orgId
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * 标题.
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 标题.
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 内容.
	 * @return
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * 内容.
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * .
	 * @return
	 */
	public String getRealeaseUserId() {
		return this.realeaseUserId;
	}

	/**
	 * .
	 * @param realeaseUserId
	 */
	public void setRealeaseUserId(String realeaseUserId) {
		this.realeaseUserId = realeaseUserId;
	}

	/**
	 * .
	 * @return
	 */
	public String getRealeaseUser() {
		return this.realeaseUser;
	}

	/**
	 * .
	 * @param realeaseUser
	 */
	public void setRealeaseUser(String realeaseUser) {
		this.realeaseUser = realeaseUser;
	}

	/**
	 * .
	 * @return
	 */
	public String getRealeaseDate() {
		return this.realeaseDate;
	}

	/**
	 * .
	 * @param realeaseDate
	 */
	public void setRealeaseDate(String realeaseDate) {
		this.realeaseDate = realeaseDate;
	}

	/**
	 * .
	 * @return
	 */
	public String getReceiveType() {
		return this.receiveType;
	}

	/**
	 * .
	 * @param receiveType
	 */
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	/**
	 * .
	 * @return
	 */
	public String getAuditor() {
		return this.auditor;
	}

	/**
	 * .
	 * @param auditor
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	 * .
	 * @return
	 */
	public String getAudittime() {
		return this.audittime;
	}

	/**
	 * .
	 * @param audittime
	 */
	public void setAudittime(String audittime) {
		this.audittime = audittime;
	}

	/**
	 * .
	 * @return
	 */
	public String getAuditopin() {
		return this.auditopin;
	}

	/**
	 * .
	 * @param auditopin
	 */
	public void setAuditopin(String auditopin) {
		this.auditopin = auditopin;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * .
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 通知公告类型:1下发,2回复 通知、公告.
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 通知公告类型:1下发,2回复 通知、公告.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getIsPortal() {
		return this.isPortal;
	}

	/**
	 * .
	 * @param isPortal
	 */
	public void setIsPortal(Integer isPortal) {
		this.isPortal = isPortal;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getIsTop() {
		return this.isTop;
	}

	/**
	 * .
	 * @param isTop
	 */
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
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
	public String getTopExpir() {
		return this.topExpir;
	}

	/**
	 * .
	 * @param topExpir
	 */
	public void setTopExpir(String topExpir) {
		this.topExpir = topExpir;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getWenHao() {
		return wenHao;
	}

	public void setWenHao(String wenHao) {
		this.wenHao = wenHao;
	}
	
	public String getRealeaseDate2(){
		if(StringUtils.isNotNullAndBlank(this.getRealeaseDate())){
			if(this.getRealeaseDate().startsWith(DateUtils.formatDate(new Date(), "yyyy"))){
				return DateUtils.formatDate(DateUtils.parseDate(this.getRealeaseDate(), "yyyy-MM-dd"), "MM-dd");
			} else {
				return this.getRealeaseDate();
			}
		}
		return "";
	}

	public Integer getAttachCnt() {
		return attachCnt;
	}

	public void setAttachCnt(Integer attachCnt) {
		this.attachCnt = attachCnt;
	}

	public Integer getRead() {
		return read == null ? 0 : read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

}