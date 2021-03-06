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
 @Table(name = "ASSET_DI_TASK_DETAILS")
public class AssetDiTaskDetails implements java.io.Serializable {

	private static final long serialVersionUID = 180114192205162L;
	
	// Fields
	
	/**
	 * 任务id.
	 */
    @Column(name = "F_TASK_ID")
	private String taskId;
	/**
	 * 任务明细id.
	 */
    @Column(name = "F_TASK_MX_ID")
	private String taskMxId;
	/**
	 * 任务明细名称.
	 */
    @Column(name = "F_TASK_MX_NAME")
	private String taskMxName;
	/**
	 * 顺序号.
	 */
    @Column(name = "F_TASK_NO")
	private Long taskNo;
	/**
	 * 源表名;导出时是取数sql语句.
	 */
    @Column(name = "F_SRC_DATA")
	private String srcData;
	/**
	 * .
	 */
    @Column(name = "F_SRC_FILTER_TYPE")
	private Integer srcFilterType;
	/**
	 * .
	 */
    @Column(name = "F_SRC_FILTER")
	private String srcFilter;
	/**
	 * 目标表名.
	 */
    @Column(name = "F_DEST_DATA")
	private String destData;
	/**
	 * .
	 */
    @Column(name = "F_DEST_FILTER_TYPE")
	private Integer destFilterType;
	/**
	 * .
	 */
    @Column(name = "F_DEST_FILTER")
	private String destFilter;
	/**
	 * .
	 */
    @Column(name = "F_SRC_AUXILIARY")
	private String srcAuxiliary;
	/**
	 * .
	 */
    @Column(name = "F_DEST_AUXILIARY")
	private String destAuxiliary;
	/**
	 * 是否必须.
	 */
    @Column(name = "F_ISMUST")
	private Integer ismust;
	/**
	 * 业务类型，同一类的会合并.
	 */
    @Column(name = "F_TASK_TYPE")
	private Integer taskType;
	/**
	 * .
	 */
    @Column(name = "F_TASK_TYPE_NAME")
	private String taskTypeName;
	/**
	 * .
	 */
    @Column(name = "F_VERIFY_TYPE")
	private String verifyType;
	/**
	 * 启用状态.
	 */
    @Column(name = "F_STATE")
	private Integer state;
	/**
	 * .
	 */
    @Column(name = "F_DEAL_TYPE")
	private String dealType;
	/**
	 * 主键字段，主要针对对照时 关联关系的字段.
	 */
    @Column(name = "F_KEY_FIELD")
	private String keyField;
	
    /**
     * 数据修复sql
     */
    @Column(name = "F_REPAIR_SQL")
    private String repairSql;
    
	// Constructors
 
    /** default constructor */
	public AssetDiTaskDetails() {
	}

	/**
	 * 任务id.
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}

	/**
	 * 任务id.
	 * @param taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 任务明细id.
	 * @return
	 */
	public String getTaskMxId() {
		return this.taskMxId;
	}

	/**
	 * 任务明细id.
	 * @param taskMxId
	 */
	public void setTaskMxId(String taskMxId) {
		this.taskMxId = taskMxId;
	}

	/**
	 * 任务明细名称.
	 * @return
	 */
	public String getTaskMxName() {
		return this.taskMxName;
	}

	/**
	 * 任务明细名称.
	 * @param taskMxName
	 */
	public void setTaskMxName(String taskMxName) {
		this.taskMxName = taskMxName;
	}

	/**
	 * 顺序号.
	 * @return
	 */
	public Long getTaskNo() {
		return this.taskNo;
	}

	/**
	 * 顺序号.
	 * @param taskNo
	 */
	public void setTaskNo(Long taskNo) {
		this.taskNo = taskNo;
	}

	/**
	 * 源表名;导出时是取数sql语句.
	 * @return
	 */
	public String getSrcData() {
		return this.srcData;
	}

	/**
	 * 源表名;导出时是取数sql语句.
	 * @param srcData
	 */
	public void setSrcData(String srcData) {
		this.srcData = srcData;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getSrcFilterType() {
		return this.srcFilterType;
	}

	/**
	 * .
	 * @param srcFilterType
	 */
	public void setSrcFilterType(Integer srcFilterType) {
		this.srcFilterType = srcFilterType;
	}

	/**
	 * .
	 * @return
	 */
	public String getSrcFilter() {
		return this.srcFilter;
	}

	/**
	 * .
	 * @param srcFilter
	 */
	public void setSrcFilter(String srcFilter) {
		this.srcFilter = srcFilter;
	}

	/**
	 * 目标表名.
	 * @return
	 */
	public String getDestData() {
		return this.destData;
	}

	/**
	 * 目标表名.
	 * @param destData
	 */
	public void setDestData(String destData) {
		this.destData = destData;
	}

	/**
	 * .
	 * @return
	 */
	public Integer getDestFilterType() {
		return this.destFilterType;
	}

	/**
	 * .
	 * @param destFilterType
	 */
	public void setDestFilterType(Integer destFilterType) {
		this.destFilterType = destFilterType;
	}

	/**
	 * .
	 * @return
	 */
	public String getDestFilter() {
		return this.destFilter;
	}

	/**
	 * .
	 * @param destFilter
	 */
	public void setDestFilter(String destFilter) {
		this.destFilter = destFilter;
	}

	/**
	 * .
	 * @return
	 */
	public String getSrcAuxiliary() {
		return this.srcAuxiliary;
	}

	/**
	 * .
	 * @param srcAuxiliary
	 */
	public void setSrcAuxiliary(String srcAuxiliary) {
		this.srcAuxiliary = srcAuxiliary;
	}

	/**
	 * .
	 * @return
	 */
	public String getDestAuxiliary() {
		return this.destAuxiliary;
	}

	/**
	 * .
	 * @param destAuxiliary
	 */
	public void setDestAuxiliary(String destAuxiliary) {
		this.destAuxiliary = destAuxiliary;
	}

	/**
	 * 是否必须.
	 * @return
	 */
	public Integer getIsmust() {
		return this.ismust;
	}

	/**
	 * 是否必须.
	 * @param ismust
	 */
	public void setIsmust(Integer ismust) {
		this.ismust = ismust;
	}

	/**
	 * 业务类型，同一类的会合并.
	 * @return
	 */
	public Integer getTaskType() {
		return this.taskType;
	}

	/**
	 * 业务类型，同一类的会合并.
	 * @param taskType
	 */
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	/**
	 * .
	 * @return
	 */
	public String getTaskTypeName() {
		return this.taskTypeName;
	}

	/**
	 * .
	 * @param taskTypeName
	 */
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	/**
	 * .
	 * @return
	 */
	public String getVerifyType() {
		return this.verifyType;
	}

	/**
	 * .
	 * @param verifyType
	 */
	public void setVerifyType(String verifyType) {
		this.verifyType = verifyType;
	}

	/**
	 * 启用状态.
	 * @return
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 * 启用状态.
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * .
	 * @return
	 */
	public String getDealType() {
		return this.dealType;
	}

	/**
	 * .
	 * @param dealType
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	/**
	 * 主键字段，主要针对对照时 关联关系的字段.
	 * @return
	 */
	public String getKeyField() {
		return this.keyField;
	}

	/**
	 * 主键字段，主要针对对照时 关联关系的字段.
	 * @param keyField
	 */
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getRepairSql() {
		return repairSql;
	}
	
	public void setRepairSql(String repairSql) {
		this.repairSql = repairSql;
	}
}