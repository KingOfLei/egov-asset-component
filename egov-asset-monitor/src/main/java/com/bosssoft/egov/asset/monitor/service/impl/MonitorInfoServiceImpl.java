package com.bosssoft.egov.asset.monitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.monitor.entity.MointorInfo;
import com.bosssoft.egov.asset.monitor.mapper.MointorInfoMapper;
import com.bosssoft.egov.asset.monitor.service.MonitorInfoService;
import com.bosssoft.platform.persistence.util.StringUtil;

/**
 * 根据监控数据模型和监控事项查询出预警信息
 * @author gaocs
 *
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class MonitorInfoServiceImpl implements MonitorInfoService{
	
	@Autowired
	private MointorInfoMapper mointorInfoMapper;
	
	public List<MointorInfo> queryInfoList(String querySql)	{
		List<MointorInfo> infoList = new ArrayList<MointorInfo>();
		if(StringUtil.isEmpty(querySql)){
			return infoList;
		}
		infoList = mointorInfoMapper.queryList(querySql);
		return infoList;
	}

	public Integer queryList(String querySql){
		if(StringUtil.isEmpty(querySql)){
			return 0;
		}
		return mointorInfoMapper.queryCount(querySql);
	}

}
