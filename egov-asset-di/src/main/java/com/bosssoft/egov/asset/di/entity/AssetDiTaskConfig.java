/**
 * 福建博思软件 1997-2018 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 14 19:21:37 CST 2018
 */
package com.bosssoft.egov.asset.di.entity;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 对象.
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2018-01-14   xiedeshou　　　新建
 * </pre>
 */
 @Table(name = "ASSET_DI_TASK_CONFIG")
public class AssetDiTaskConfig implements java.io.Serializable {

	private static final long serialVersionUID = 180114192206983L;
	
	// Fields
	
	/**
	 * .
	 */
    @Column(name = "F_TASK_MX_ID")
	private String taskMxId;
	/**
	 * .
	 */
    @Column(name = "F_CONFIG_NO")
	private Long configNo;
	/**
	 * .
	 */
    @Column(name = "F_SRCTYPE")
	private String srctype;
	/**
	 * .
	 */
    @Column(name = "F_TITLE")
	private String title;
	/**
	 * .
	 */
    @Column(name = "F_SRCFIELDNAME")
	private String srcfieldname;
	/**
	 * .
	 */
    @Column(name = "F_SRCFIELDTYPE")
	private String srcfieldtype;
	/**
	 * .
	 */
    @Column(name = "F_SRCSIZE")
	private Integer srcsize;
	/**
	 * .
	 */
    @Column(name = "F_DESTFIELDNAME")
	private String destfieldname;
	/**
	 * .
	 */
    @Column(name = "F_DESTFIELDTYPE")
	private String destfieldtype;
	/**
	 * .
	 */
    @Column(name = "F_DESTSIZE")
	private Integer destsize;
	/**
	 * //          0: 无对照,自己取源字段内容；.
	 */
    @Column(name = "F_CONVERTTYPE")
	private Integer converttype;
	/**
	 * .
	 */
    @Column(name = "F_CONVERTTABLE")
	private String converttable;
	/**
	 * .
	 */
    @Column(name = "F_CONVERTID")
	private String convertid;
	/**
	 * .
	 */
    @Column(name = "F_FILTERTYPE")
	private String filtertype;
	/**
	 * .
	 */
    @Column(name = "F_COMPFIELD")
	private String compfield;
	/**
	 * .
	 */
    @Column(name = "F_VALUEFIELD")
	private String valuefield;
	/**
	 * .
	 */
    @Column(name = "F_VALUE")
	private String value;
	/**
	 * .
	 */
    @Column(name = "F_VALUESRCFIELD")
	private String valuesrcfield;
	
	// Constructors
 
    /** default constructor */
	public AssetDiTaskConfig() {
	}

	/**
	 * .
	 * @return
	 */
	public String getTaskMxId() {
		return this.taskMxId;
	}

	/**
	 * .
	 * @param taskMxId
	 */
	public void setTaskMxId(String taskMxId) {
		this.taskMxId = taskMxId;
	}

	/**
	 * .
	 * @return
	 */
	public Long getConfigNo() {
		return this.configNo;
	}

	/**
	 * .
	 * @param configNo
	 */
	public void setConfigNo(Long configNo) {
		this.configNo = configNo;
	}

	/**
	 * .
	 * @return
	 */
	public String getSrctype() {
		return this.srctype;
	}

	/**
	 * .
	 * @param srctype
	 */
	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}

	/**
	 * .
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * .
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * .
	 * @return
	 */
	public String getSrcfieldname() {
		return this.srcfieldname;
	}

	/**
	 * .
	 * @param srcfieldname
	 */
	public void setSrcfieldname(String srcfieldname) {
		this.srcfieldname = srcfieldname;
	}

	/**
	 * .
	 * @return
	 */
	public String getSrcfieldtype() {
		return this.srcfieldtype;
	}

	/**
	 * .
	 * @param srcfieldtype
	 */
	public void setSrcfieldtype(String srcfieldtype) {
		this.srcfieldtype = srcfieldtype;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getSrcsize() {
		return this.srcsize;
	}

	/**
	 * .
	 * @param srcsize
	 */
	public void setSrcsize(Integer srcsize) {
		this.srcsize = srcsize;
	}

	/**
	 * .
	 * @return
	 */
	public String getDestfieldname() {
		return this.destfieldname;
	}

	/**
	 * .
	 * @param destfieldname
	 */
	public void setDestfieldname(String destfieldname) {
		this.destfieldname = destfieldname;
	}

	/**
	 * .
	 * @return
	 */
	public String getDestfieldtype() {
		return this.destfieldtype;
	}

	/**
	 * .
	 * @param destfieldtype
	 */
	public void setDestfieldtype(String destfieldtype) {
		this.destfieldtype = destfieldtype;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getDestsize() {
		return this.destsize;
	}

	/**
	 * .
	 * @param destsize
	 */
	public void setDestsize(Integer destsize) {
		this.destsize = destsize;
	}

	/**
	 * //          0: 无对照,自己取源字段内容；.
	 * @return
	 */
	public Integer getConverttype() {
		return this.converttype;
	}

	/**
	 * //          0: 无对照,自己取源字段内容；.
	 * @param converttype
	 */
	public void setConverttype(Integer converttype) {
		this.converttype = converttype;
	}

	/**
	 * .
	 * @return
	 */
	public String getConverttable() {
		return this.converttable;
	}

	/**
	 * .
	 * @param converttable
	 */
	public void setConverttable(String converttable) {
		this.converttable = converttable;
	}

	/**
	 * .
	 * @return
	 */
	public String getConvertid() {
		return this.convertid;
	}

	/**
	 * .
	 * @param convertid
	 */
	public void setConvertid(String convertid) {
		this.convertid = convertid;
	}

	/**
	 * .
	 * @return
	 */
	public String getFiltertype() {
		return this.filtertype;
	}

	/**
	 * .
	 * @param filtertype
	 */
	public void setFiltertype(String filtertype) {
		this.filtertype = filtertype;
	}

	/**
	 * .
	 * @return
	 */
	public String getCompfield() {
		return this.compfield;
	}

	/**
	 * .
	 * @param compfield
	 */
	public void setCompfield(String compfield) {
		this.compfield = compfield;
	}

	/**
	 * .
	 * @return
	 */
	public String getValuefield() {
		return this.valuefield;
	}

	/**
	 * .
	 * @param valuefield
	 */
	public void setValuefield(String valuefield) {
		this.valuefield = valuefield;
	}

	/**
	 * .
	 * @return
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * .
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * .
	 * @return
	 */
	public String getValuesrcfield() {
		return this.valuesrcfield;
	}

	/**
	 * .
	 * @param valuesrcfield
	 */
	public void setValuesrcfield(String valuesrcfield) {
		this.valuesrcfield = valuesrcfield;
	}

}