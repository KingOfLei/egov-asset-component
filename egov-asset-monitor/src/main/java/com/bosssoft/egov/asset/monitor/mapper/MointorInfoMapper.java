package com.bosssoft.egov.asset.monitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.monitor.entity.MointorInfo;


/**
 * 根据监控数据模型和监控事项查询出预警信息
 * @author gaocs
 *
 */
public interface MointorInfoMapper {
	/**
	 * 根据监控数据模型和监控事项查询出预警信息(明细)
	 * @param querySql
	 * @return
	 * @throws BusinessException
	 */
	List<MointorInfo> queryList(@Param("querysql")String querysql);
	/**
	 * 根据监控数据模型和监控事项查询出预警信息(汇总)
	 * @param querySql
	 * @return
	 * @throws BusinessException
	 */
	Integer queryCount(@Param("querySql")String querySql);
}
