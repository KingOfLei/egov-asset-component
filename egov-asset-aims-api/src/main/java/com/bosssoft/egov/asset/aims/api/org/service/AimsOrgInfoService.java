package com.bosssoft.egov.asset.aims.api.org.service;

import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsOrgInfo;

public interface AimsOrgInfoService {
	
	public List<AimsOrgInfo> loadOrgInfoTree(String orgCode);
	
	public List<AimsOrgInfo> searchListByKey(Long orgId, String _key);
	
	public Map<String, Object> doUpdateOrgInfo(AimsOrgInfo aimsOrgInfo);
}
