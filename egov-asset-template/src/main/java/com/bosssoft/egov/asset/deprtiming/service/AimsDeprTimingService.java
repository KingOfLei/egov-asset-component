/**
 * 
 */
package com.bosssoft.egov.asset.deprtiming.service;

import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.deprtiming.entity.AimsDeprTiming;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * @author Linxt
 * service类型接口
 */
public interface AimsDeprTimingService {

	/**
	 * 获取分页
	 * @param searcher
	 * @param page
	 * @return
	 */
	public Page<AimsDeprTiming> queryAimsDeprTimingPage(Searcher searcher,Page<AimsDeprTiming> page);
	/**
	 * 根据orgCode 查询
	 * @param orgCode
	 * @return
	 */
	public List<AimsDeprTiming> queryByOrgCode(String orgCode);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public AimsDeprTiming queryOne(Long id);
	
	public AimsDeprTiming queryByOrgId(Long orgId);
	/**
	 * 查询所有取数模板信息
	 * @return
	 */
	public List<AimsDeprTiming> geAimsDeprTimings();
	/**
	 * 增加
	 * @param AimsDeprTiming
	 * @return
	 */
	public void addAimsDeprTiming(AimsDeprTiming aimsDeprTiming);
//	public Map<String, Object> addDeprTiming(AimsDeprTiming aimsDeprTiming);
	/**
	 * 删除
	 * @param AimsDeprTiming
	 */
	public void delAimsDeprTiming(AimsDeprTiming aimsDeprTiming);
	public int delAimsDeprTiming(Long id);

	/**
	 * 修改
	 * @param AimsDeprTiming
	 * @return
	 */
	public void updateAimsDeprTiming(AimsDeprTiming aimsDeprTiming);
	
	public int selectCount(AimsDeprTiming aimsDeprTiming);
	/**
	 * 获取单位信息
	 * @param AimsDeprTiming
	 * @return
	 */
	public AimsDeprTiming geTemplateData(AimsDeprTiming aimsDeprTiming);
	

}
