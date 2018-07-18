/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Jun 20 09:50:40 CST 2017
 */
package com.bosssoft.egov.asset.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmMonitorData;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.persistence.common.Mapper;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 * 类说明:  Mapper接口（ Mapper层） .
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-06-20   jinbiao　　　新建
 * </pre>

 */

public interface ActivitiAlarmMonitorDataMapper extends Mapper<ActivitiAlarmMonitorData>{



/**
	 * 
	 * 查询分页.
	 *	`
	 * @param searcher 查询条件
	 * @param pageable 分页信息
	 * @return ActivitiAlarmMonitorDataPage列表
	 * @throws BusinessException 自定义异常
	 *
	 */
	public Page<ActivitiAlarmMonitorData> queryActivitiAlarmMonitorDataPage(@Param("searcher")Searcher searcher,@Param("page")Page<ActivitiAlarmMonitorData> page);
	
	public List<ActivitiAlarmMonitorData> queryBySql(@Param("querysql")String querysql);
	
	public void updateCompleteTimeByBusId(@Param("isComplete")int isComplete,@Param("endTime") String endTime, @Param("busId") String busId, @Param("pid") String pid);
	
	public void updateCompleteTimeByBusIdNoPid(@Param("isComplete")int isComplete,@Param("endTime") String endTime, @Param("busId") String busId);
	
	public void updateRoleCodeBusId(@Param("roleCode")String roleCode, @Param("busId") String busId, @Param("pid") String pid,@Param("isComplete")int isComplete);
	
	public void updateRoleCodeBusIdNoPid(@Param("roleCode")String roleCode, @Param("busId") String busId,@Param("isComplete")int isComplete);
	
	public void delActivitiAlarmMonitorDataAll();
	
	public void insertBySql(@Param("insertsql")String insertsql);
	
	public void addAlarmPro();

	public void delActivitiAlarmMonitorDataByComplete(@Param("c")int c);

}