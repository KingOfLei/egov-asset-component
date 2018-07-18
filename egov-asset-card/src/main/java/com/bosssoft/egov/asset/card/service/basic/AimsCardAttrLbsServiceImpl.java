package com.bosssoft.egov.asset.card.service.basic;
/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jul 16 11:02:53 CST 2017
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.basic.BaseService;
import com.bosssoft.egov.asset.card.entity.basic.AimsCardAttrLbs;
import com.bosssoft.egov.asset.card.mapper.basic.AimsCardAttrLbsMapper;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.persistence.entity.Condition;


/**
 * 类说明: AimsCardAttrLbsService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-07-16   xiedeshou　　　新建
 * </pre>
 */
@Service
public class AimsCardAttrLbsServiceImpl extends BaseService implements AimsCardAttrLbsService {

	private static Logger logger = LoggerFactory.getLogger(AimsCardAttrLbsServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private AimsCardAttrLbsMapper aimsCardAttrLbsMapper;
	
	/**
	 *
	 * @param aimsCardAttrLbs
	 */
	public void addAimsCardAttrLbs(AimsCardAttrLbs aimsCardAttrLbs)  {
		Condition conditon = new Condition(AimsCardAttrLbs.class);
		conditon.createCriteria().andEqualTo("assetId", aimsCardAttrLbs.getAssetId());
		aimsCardAttrLbsMapper.deleteByExample(conditon);//先删除后插入
		aimsCardAttrLbsMapper.insertSelective(aimsCardAttrLbs);
	}

	/**
	 *
	 * @param aimsCardAttrLbs
	 */
	public void delAimsCardAttrLbs(AimsCardAttrLbs aimsCardAttrLbs)  {
		aimsCardAttrLbsMapper.deleteByPrimaryKey(aimsCardAttrLbs);
	}

	/**
	 *
	 * @param aimsCardAttrLbs
	 */
	public void updateAimsCardAttrLbs(AimsCardAttrLbs aimsCardAttrLbs)  {
		aimsCardAttrLbsMapper.updateByPrimaryKey(aimsCardAttrLbs);
	}

	/**
	 *
	 * @param aimsCardAttrLbs
	 * @retrun
	 */
	public List<AimsCardAttrLbs> getAimsCardAttrLbsList(AimsCardAttrLbs aimsCardAttrLbs)  {
		return aimsCardAttrLbsMapper.select(aimsCardAttrLbs);
	}
	

	@Override
	public AimsCardAttrLbs getAimsCardAttrLbs(Long assetId) {
		AimsCardAttrLbs lbs = new AimsCardAttrLbs();
		lbs.setAssetId(assetId);		
		return aimsCardAttrLbsMapper.selectOne(lbs);
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AimsCardAttrLbs> queryAimsCardAttrLbsPage(Searcher searcher, Page<AimsCardAttrLbs> page)  {
		return aimsCardAttrLbsMapper.queryAimsCardAttrLbsPage(searcher, page);
	}

	@Override
	public int delAimsCardAttrLbs(Long bizId) {
		AimsCardAttrLbs lbs = new AimsCardAttrLbs();
        lbs.setAssetId(bizId);
		return aimsCardAttrLbsMapper.delete(lbs);
		
	}


}