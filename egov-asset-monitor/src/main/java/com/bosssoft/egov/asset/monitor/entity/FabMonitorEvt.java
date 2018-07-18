/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Mon Jan 16 11:24:16 CST 2017
 */
package com.bosssoft.egov.asset.monitor.entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 监控事项表对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-16   Administrator　　　新建
 * </pre>
 */
 @Table(name = "FAB_MONITOR_EVT")
public class FabMonitorEvt implements java.io.Serializable {

	private static final long serialVersionUID = 170116112458964L;
	
	// Fields
	
	/**
	 * 主键.
	 */
	@Id
    @Column(name = "FID")
	private String fid;
	/**
	 * 区划ID.
	 */
    @Column(name = "FRGNID")
	private String frgnid;
	/**
	 * 监控事项编码.
	 */
    @Column(name = "FEVTCODE")
	private String fevtcode;
	/**
	 * 监控事项名称.
	 */
    @Column(name = "FEVTNAME")
	private String fevtname;
	/**
	 * 监控数据模型ID.
	 */
    @Column(name = "FMODELID")
	private String fmodelid;
	/**
	 * 预警级别.
	 */
    @Column(name = "FLEVEL")
	private Long flevel;
	/**
	 * 附加条件.
	 */
    @Column(name = "FADDSQL")
	private String faddsql;
	/**
	 * 预警监控机构ID.
	 */
    @Column(name = "FMCMID")
	private String fmcmid;
	/**
	 * 预警监控机构编码.
	 */
    @Column(name = "FMCMCODE")
	private String fmcmcode;
	/**
	 * 预警监控机构名称.
	 */
    @Column(name = "FMCMNAME")
	private String fmcmname;
	/**
	 * 预警触发条件.
	 */
    @Column(name = "FTRIGCOND")
	private Integer ftrigcond;
	/**
	 * 预警提示内容.
	 */
    @Column(name = "FASITCONT")
	private String fasitcont;
	/**
	 * 预警后是否短信通知（0：否，1：是）.
	 */
    @Column(name = "FISSMSNOTI")
	private Integer fissmsnoti;
	/**
	 * 预警通知手机号码.
	 */
    @Column(name = "FNOTITEL")
	private String fnotitel;
	/**
	 * 发生（处理）机构ID.
	 */
    @Column(name = "FHNDLORGID")
	private String fhndlorgid;
	/**
	 * 发生（处理）机构编码.
	 */
    @Column(name = "FHNDLORGCODE")
	private String fhndlorgcode;
	/**
	 * 发生（处理）机构名称.
	 */
    @Column(name = "FHNDLORGNAME")
	private String fhndlorgname;
	/**
	 * 预警处理后是否自动关闭（0：关闭，1：不关闭）.
	 */
    @Column(name = "FISAUTOCLOSE")
	private Integer fisautoclose;
	/**
	 * 预警处理方式(0:处理业务数据,1:填写情况说明).
	 */
    @Column(name = "FHNDLWAY")
	private Long fhndlway;
	/**
	 * 是否启用.
	 */
    @Column(name = "FISENABLE")
	private Integer fisenable;
	/**
	 * 经办人ID.
	 */
    @Column(name = "FOPERID")
	private String foperid;
	/**
	 * 经办人.
	 */
    @Column(name = "FOPERATOR")
	private String foperator;
	/**
	 * 经办日期.
	 */
    @Column(name = "FOPEDATE")
	private String fopedate;
	/**
	 * 备注.
	 */
    @Column(name = "FMEMO")
	private String fmemo;
	/**
	 * 创建时间.
	 */
    @Column(name = "FCREATETIME")
	private String fcreatetime;
	/**
	 * 最后修改时间.
	 */
    @Column(name = "FUPDATETIME")
	private String fupdatetime;
	/**
	 * 变更类型编码.
	 */
    @Column(name = "FALTERCODE")
	private String faltercode;
	/**
	 * 版本号.
	 */
    @Column(name = "FVERSION")
	private Long fversion;
	/**
	 * 自定义1.
	 */
    @Column(name = "FCUSTOM1")
	private String fcustom1;
	/**
	 * 自定义2.
	 */
    @Column(name = "FCUSTOM2")
	private String fcustom2;
	/**
	 * 自定义3.
	 */
    @Column(name = "FCUSTOM3")
	private String fcustom3;
	/**
	 * 自定义4.
	 */
    @Column(name = "FCUSTOM4")
	private String fcustom4;
	/**
	 * 自定义5.
	 */
    @Column(name = "FCUSTOM5")
	private String fcustom5;
	/**
	 * 自定义6.
	 */
    @Column(name = "FCUSTOM6")
	private String fcustom6;
	/**
	 * 自定义7.
	 */
    @Column(name = "FCUSTOM7")
	private String fcustom7;
	/**
	 * 自定义8.
	 */
    @Column(name = "FCUSTOM8")
	private String fcustom8;
	/**
	 * 自定义9.
	 */
    @Column(name = "FCUSTOM9")
	private String fcustom9;
	/**
	 * 自定义10.
	 */
    @Column(name = "FCUSTOM10")
	private String fcustom10;
	/**
	 * 是否通知预警发生机构.
	 */
    @Column(name = "FISNOTICEHNDLORG")
	private Integer fisnoticehndlorg;
    /**
     * 产生的记录即为统计结果
     */
    @Column(name = "ISRESULT")
    private String isResult;
    
    /**
     * 是否保存最新记录，默认1只保存最新记录
     */
    @Column(name = "ISNEW")
    private String isNew;
	
	// Constructors
 
    /** default constructor */
	public FabMonitorEvt() {
		this.fissmsnoti = 0;
		this.isResult = "0";
	}

	/**
	 * 主键.
	 * @return
	 */
	public String getFid() {
		return this.fid;
	}

	/**
	 * 主键.
	 * @param fid
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * 区划ID.
	 * @return
	 */
	public String getFrgnid() {
		return this.frgnid;
	}

	/**
	 * 区划ID.
	 * @param frgnid
	 */
	public void setFrgnid(String frgnid) {
		this.frgnid = frgnid;
	}

	/**
	 * 监控事项编码.
	 * @return
	 */
	public String getFevtcode() {
		return this.fevtcode;
	}

	/**
	 * 监控事项编码.
	 * @param fevtcode
	 */
	public void setFevtcode(String fevtcode) {
		this.fevtcode = fevtcode;
	}

	/**
	 * 监控事项名称.
	 * @return
	 */
	public String getFevtname() {
		return this.fevtname;
	}

	/**
	 * 监控事项名称.
	 * @param fevtname
	 */
	public void setFevtname(String fevtname) {
		this.fevtname = fevtname;
	}

	/**
	 * 监控数据模型ID.
	 * @return
	 */
	public String getFmodelid() {
		return this.fmodelid;
	}

	/**
	 * 监控数据模型ID.
	 * @param fmodelid
	 */
	public void setFmodelid(String fmodelid) {
		this.fmodelid = fmodelid;
	}

	/**
	 * 预警级别.
	 * @return
	 */
	public Long getFlevel() {
		return this.flevel;
	}

	/**
	 * 预警级别.
	 * @param flevel
	 */
	public void setFlevel(Long flevel) {
		this.flevel = flevel;
	}

	/**
	 * 附加条件.
	 * @return
	 */
	public String getFaddsql() {
		return this.faddsql;
	}

	/**
	 * 附加条件.
	 * @param faddsql
	 */
	public void setFaddsql(String faddsql) {
		this.faddsql = faddsql;
	}

	/**
	 * 预警监控机构ID.
	 * @return
	 */
	public String getFmcmid() {
		return this.fmcmid;
	}

	/**
	 * 预警监控机构ID.
	 * @param fmcmid
	 */
	public void setFmcmid(String fmcmid) {
		this.fmcmid = fmcmid;
	}

	/**
	 * 预警监控机构编码.
	 * @return
	 */
	public String getFmcmcode() {
		return this.fmcmcode;
	}

	/**
	 * 预警监控机构编码.
	 * @param fmcmcode
	 */
	public void setFmcmcode(String fmcmcode) {
		this.fmcmcode = fmcmcode;
	}

	/**
	 * 预警监控机构名称.
	 * @return
	 */
	public String getFmcmname() {
		return this.fmcmname;
	}

	/**
	 * 预警监控机构名称.
	 * @param fmcmname
	 */
	public void setFmcmname(String fmcmname) {
		this.fmcmname = fmcmname;
	}

	/**
	 * 预警触发条件.
	 * @return
	 */
	public Integer getFtrigcond() {
		return this.ftrigcond;
	}

	/**
	 * 预警触发条件.
	 * @param ftrigcond
	 */
	public void setFtrigcond(Integer ftrigcond) {
		this.ftrigcond = ftrigcond;
	}

	/**
	 * 预警提示内容.
	 * @return
	 */
	public String getFasitcont() {
		return this.fasitcont;
	}

	/**
	 * 预警提示内容.
	 * @param fasitcont
	 */
	public void setFasitcont(String fasitcont) {
		this.fasitcont = fasitcont;
	}

	/**
	 * 预警后是否短信通知（0：否，1：是）.
	 * @return
	 */
	public Integer getFissmsnoti() {
		return this.fissmsnoti;
	}

	/**
	 * 预警后是否短信通知（0：否，1：是）.
	 * @param fissmsnoti
	 */
	public void setFissmsnoti(Integer fissmsnoti) {
		this.fissmsnoti = fissmsnoti;
	}

	/**
	 * 预警通知手机号码.
	 * @return
	 */
	public String getFnotitel() {
		return this.fnotitel;
	}

	/**
	 * 预警通知手机号码.
	 * @param fnotitel
	 */
	public void setFnotitel(String fnotitel) {
		this.fnotitel = fnotitel;
	}

	/**
	 * 备用处理机构ID.
	 * @return
	 */
	public String getFhndlorgid() {
		return this.fhndlorgid;
	}

	/**
	 * 备用处理机构ID.
	 * @param fhndlorgid
	 */
	public void setFhndlorgid(String fhndlorgid) {
		this.fhndlorgid = fhndlorgid;
	}

	/**
	 * 备用处理机构编码.
	 * @return
	 */
	public String getFhndlorgcode() {
		return this.fhndlorgcode;
	}

	/**
	 * 备用处理机构编码.
	 * @param fhndlorgcode
	 */
	public void setFhndlorgcode(String fhndlorgcode) {
		this.fhndlorgcode = fhndlorgcode;
	}

	/**
	 * 备用处理机构名称.
	 * @return
	 */
	public String getFhndlorgname() {
		return this.fhndlorgname;
	}

	/**
	 * 备用处理机构名称.
	 * @param fhndlorgname
	 */
	public void setFhndlorgname(String fhndlorgname) {
		this.fhndlorgname = fhndlorgname;
	}

	/**
	 * 预警处理后是否自动关闭（0：关闭，1：不关闭）.
	 * @return
	 */
	public Integer getFisautoclose() {
		return this.fisautoclose;
	}

	/**
	 * 预警处理后是否自动关闭（0：关闭，1：不关闭）.
	 * @param fisautoclose
	 */
	public void setFisautoclose(Integer fisautoclose) {
		this.fisautoclose = fisautoclose;
	}

	/**
	 * 预警处理方式(0:处理业务数据,1:填写情况说明).
	 * @return
	 */
	public Long getFhndlway() {
		return this.fhndlway;
	}

	/**
	 * 预警处理方式(0:处理业务数据,1:填写情况说明).
	 * @param fhndlway
	 */
	public void setFhndlway(Long fhndlway) {
		this.fhndlway = fhndlway;
	}

	/**
	 * 是否启用.
	 * @return
	 */
	public Integer getFisenable() {
		return this.fisenable;
	}

	/**
	 * 是否启用.
	 * @param fisenable
	 */
	public void setFisenable(Integer fisenable) {
		this.fisenable = fisenable;
	}

	/**
	 * 经办人ID.
	 * @return
	 */
	public String getFoperid() {
		return this.foperid;
	}

	/**
	 * 经办人ID.
	 * @param foperid
	 */
	public void setFoperid(String foperid) {
		this.foperid = foperid;
	}

	/**
	 * 经办人.
	 * @return
	 */
	public String getFoperator() {
		return this.foperator;
	}

	/**
	 * 经办人.
	 * @param foperator
	 */
	public void setFoperator(String foperator) {
		this.foperator = foperator;
	}

	/**
	 * 经办日期.
	 * @return
	 */
	public String getFopedate() {
		return this.fopedate;
	}

	/**
	 * 经办日期.
	 * @param fopedate
	 */
	public void setFopedate(String fopedate) {
		this.fopedate = fopedate;
	}

	/**
	 * 备注.
	 * @return
	 */
	public String getFmemo() {
		return this.fmemo;
	}

	/**
	 * 备注.
	 * @param fmemo
	 */
	public void setFmemo(String fmemo) {
		this.fmemo = fmemo;
	}

	/**
	 * 创建时间.
	 * @return
	 */
	public String getFcreatetime() {
		return this.fcreatetime;
	}

	/**
	 * 创建时间.
	 * @param fcreatetime
	 */
	public void setFcreatetime(String fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	/**
	 * 最后修改时间.
	 * @return
	 */
	public String getFupdatetime() {
		return this.fupdatetime;
	}

	/**
	 * 最后修改时间.
	 * @param fupdatetime
	 */
	public void setFupdatetime(String fupdatetime) {
		this.fupdatetime = fupdatetime;
	}

	/**
	 * 变更类型编码.
	 * @return
	 */
	public String getFaltercode() {
		return this.faltercode;
	}

	/**
	 * 变更类型编码.
	 * @param faltercode
	 */
	public void setFaltercode(String faltercode) {
		this.faltercode = faltercode;
	}

	/**
	 * 版本号.
	 * @return
	 */
	public Long getFversion() {
		return this.fversion;
	}

	/**
	 * 版本号.
	 * @param fversion
	 */
	public void setFversion(Long fversion) {
		this.fversion = fversion;
	}

	/**
	 * 自定义1.
	 * @return
	 */
	public String getFcustom1() {
		return this.fcustom1;
	}

	/**
	 * 自定义1.
	 * @param fcustom1
	 */
	public void setFcustom1(String fcustom1) {
		this.fcustom1 = fcustom1;
	}

	/**
	 * 自定义2.
	 * @return
	 */
	public String getFcustom2() {
		return this.fcustom2;
	}

	/**
	 * 自定义2.
	 * @param fcustom2
	 */
	public void setFcustom2(String fcustom2) {
		this.fcustom2 = fcustom2;
	}

	/**
	 * 自定义3.
	 * @return
	 */
	public String getFcustom3() {
		return this.fcustom3;
	}

	/**
	 * 自定义3.
	 * @param fcustom3
	 */
	public void setFcustom3(String fcustom3) {
		this.fcustom3 = fcustom3;
	}

	/**
	 * 自定义4.
	 * @return
	 */
	public String getFcustom4() {
		return this.fcustom4;
	}

	/**
	 * 自定义4.
	 * @param fcustom4
	 */
	public void setFcustom4(String fcustom4) {
		this.fcustom4 = fcustom4;
	}

	/**
	 * 自定义5.
	 * @return
	 */
	public String getFcustom5() {
		return this.fcustom5;
	}

	/**
	 * 自定义5.
	 * @param fcustom5
	 */
	public void setFcustom5(String fcustom5) {
		this.fcustom5 = fcustom5;
	}

	/**
	 * 自定义6.
	 * @return
	 */
	public String getFcustom6() {
		return this.fcustom6;
	}

	/**
	 * 自定义6.
	 * @param fcustom6
	 */
	public void setFcustom6(String fcustom6) {
		this.fcustom6 = fcustom6;
	}

	/**
	 * 自定义7.
	 * @return
	 */
	public String getFcustom7() {
		return this.fcustom7;
	}

	/**
	 * 自定义7.
	 * @param fcustom7
	 */
	public void setFcustom7(String fcustom7) {
		this.fcustom7 = fcustom7;
	}

	/**
	 * 自定义8.
	 * @return
	 */
	public String getFcustom8() {
		return this.fcustom8;
	}

	/**
	 * 自定义8.
	 * @param fcustom8
	 */
	public void setFcustom8(String fcustom8) {
		this.fcustom8 = fcustom8;
	}

	/**
	 * 自定义9.
	 * @return
	 */
	public String getFcustom9() {
		return this.fcustom9;
	}

	/**
	 * 自定义9.
	 * @param fcustom9
	 */
	public void setFcustom9(String fcustom9) {
		this.fcustom9 = fcustom9;
	}

	/**
	 * 自定义10.
	 * @return
	 */
	public String getFcustom10() {
		return this.fcustom10;
	}

	/**
	 * 自定义10.
	 * @param fcustom10
	 */
	public void setFcustom10(String fcustom10) {
		this.fcustom10 = fcustom10;
	}

	/**
	 * 是否通知预警发生机构.
	 * @return
	 */
	public Integer getFisnoticehndlorg() {
		return this.fisnoticehndlorg;
	}

	/**
	 * 是否通知预警发生机构.
	 * @param fisnoticehndlorg
	 */
	public void setFisnoticehndlorg(Integer fisnoticehndlorg) {
		this.fisnoticehndlorg = fisnoticehndlorg;
	}

	public String getIsResult() {
		return isResult;
	}

	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
}