/**
 * 
 */
package com.bosssoft.egov.asset.template.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.template.entity.AimsTemplateData;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * @author Linxt
 * service类型接口
 */
public interface AimsTemplateDataService {

	/**
	 * 获取分页
	 * @param searcher
	 * @param page
	 * @return
	 */
	public Page<AimsTemplateData> queryAimsTemplateDataPage(Searcher searcher,Page<AimsTemplateData> page);
	/**
	 * 根据moduleItemId 查询
	 * @param moduleItemId
	 * @return
	 */
	public List<AimsTemplateData> queryByModuleItemId(Long moduleItemId);
	/**
	 * 根据moduleItemId 和 bizId 查询
	 * @param moduleItemId
	 * @param BizId
	 * @return
	 */
	public List<AimsTemplateData> geTemplateDatasByBizId(long moduleItemId,String bizId);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public AimsTemplateData queryOne(Long id);
	/**
	 * 查询所有取数模板信息
	 * @return
	 */
	public List<AimsTemplateData> geAimsTemplateDatas();
	/**
	 * 增加
	 * @param aimsTemplateData
	 * @return
	 */
	public void addAimsTemplateData(AimsTemplateData aimsTemplateData);
//	public Map<String, Object> addAimsTemplateData(AimsTemplateData aimsTemplateData);
	/**
	 * 删除
	 * @param aimsTemplateData
	 */
	public void delAimsTemplateData(AimsTemplateData aimsTemplateData);

	/**
	 * 修改
	 * @param aimsTemplateData
	 * @return
	 */
	public void updateAimsTemplateData(AimsTemplateData aimsTemplateData);
	
	public int selectCount(AimsTemplateData aimsTemplateData);
	/**
	 * 获取单条取数模板信息
	 * @param aimsTemplateData
	 * @return
	 */
	public AimsTemplateData geTemplateData(AimsTemplateData aimsTemplateData);
	

}
