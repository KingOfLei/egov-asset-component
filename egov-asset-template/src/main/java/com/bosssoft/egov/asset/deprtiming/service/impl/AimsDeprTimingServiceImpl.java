/**
 * 
 */
package com.bosssoft.egov.asset.deprtiming.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.bosssoft.egov.asset.deprtiming.entity.AimsDeprTiming;
import com.bosssoft.egov.asset.deprtiming.mapper.AimsDeprTimingMapper;
import com.bosssoft.egov.asset.deprtiming.service.AimsDeprTimingService;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * @author Linxt
 *
 */
@Service
public class AimsDeprTimingServiceImpl implements AimsDeprTimingService {


	private static Logger logger =  LoggerFactory.getLogger(AimsDeprTiming.class);
	
	@Autowired
	private AimsDeprTimingMapper aimsDeprTimingMapper;
	
	
	public Page<AimsDeprTiming> queryAimsDeprTimingPage(Searcher searcher, Page<AimsDeprTiming> page) {
		
		return aimsDeprTimingMapper.queryAimsDeprTimingPage(searcher, page);
	}


	public List<AimsDeprTiming> queryByOrgCode(String orgCode) {
		
		return aimsDeprTimingMapper.queryByOrgCode(orgCode);
	}


	public AimsDeprTiming queryOne(Long id) {

		return aimsDeprTimingMapper.queryOne(id);
	}


	public List<AimsDeprTiming> geAimsDeprTimings() {

		return aimsDeprTimingMapper.selectAll();
	}


	public void addAimsDeprTiming(AimsDeprTiming aimsDeprTiming) {
		aimsDeprTimingMapper.insert(aimsDeprTiming);

	}


	public void delAimsDeprTiming(AimsDeprTiming aimsDeprTiming) {
		
		aimsDeprTimingMapper.delete(aimsDeprTiming);

	}


	public void updateAimsDeprTiming(AimsDeprTiming aimsDeprTiming) {

		aimsDeprTimingMapper.updateByOrgCode(aimsDeprTiming);
	}

	public int selectCount(AimsDeprTiming aimsDeprTiming) {
		// TODO Auto-generated method stub
		return 0;
	}


	public AimsDeprTiming geTemplateData(AimsDeprTiming aimsDeprTiming) {
		// TODO Auto-generated method stub
		return null;
	}

	public AimsDeprTiming queryByOrgId(Long orgId) {
		return aimsDeprTimingMapper.queryByOrgId(orgId);
	}


	
	public int delAimsDeprTiming(Long id) {
		return aimsDeprTimingMapper.deleteById(id);
	}

}
