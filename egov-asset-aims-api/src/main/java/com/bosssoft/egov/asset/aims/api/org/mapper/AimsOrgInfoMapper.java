package com.bosssoft.egov.asset.aims.api.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsOrgInfo;
import com.bosssoft.platform.persistence.common.Mapper;

public interface AimsOrgInfoMapper extends Mapper<AimsOrgInfo>{
	
	public List<AimsOrgInfo> loadOrgInfoTree(@Param("orgCode") String orgCode);
	
	public List<AimsOrgInfo> searchListByKey(@Param("orgId") Long orgId, @Param("_key") String _key);
	
	public void doUpdateOrgInfo(AimsOrgInfo aimsOrgInfo);
}
