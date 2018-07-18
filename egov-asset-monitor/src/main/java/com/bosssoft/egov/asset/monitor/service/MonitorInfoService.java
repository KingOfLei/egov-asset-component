package com.bosssoft.egov.asset.monitor.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.monitor.entity.MointorInfo;

@Service
@Transactional(rollbackFor = Throwable.class)
public interface MonitorInfoService {
	/**
	 * 根据监控数据模型和监控事项查询出预警信息(明细)
	 * @param querySql
	 * @return
	 * @throws BusinessException
	 */
	List<MointorInfo> queryInfoList(String querySql);
	/**
	 * 根据监控数据模型和监控事项查询出预警信息(汇总)
	 * @param querySql
	 * @return
	 * @throws BusinessException
	 */
	Integer queryList(String querySql);
}
