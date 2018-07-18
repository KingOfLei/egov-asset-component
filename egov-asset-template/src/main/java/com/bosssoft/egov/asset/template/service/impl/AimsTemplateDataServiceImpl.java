/**
 * 
 */
package com.bosssoft.egov.asset.template.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.bosssoft.egov.asset.template.entity.AimsTemplateData;
import com.bosssoft.egov.asset.template.entity.AimsTemplateDetail;
import com.bosssoft.egov.asset.template.mapper.AimsTemplateDataMapper;
import com.bosssoft.egov.asset.template.mapper.AimsTemplateDetailMapper;
import com.bosssoft.egov.asset.template.service.AimsTemplateDataService;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/**
 * @author Linxt
 *类说明：AimsTemplateDataServie接口实现类
 */
@Service
public class AimsTemplateDataServiceImpl implements AimsTemplateDataService {
	
	private static Logger logger =  LoggerFactory.getLogger(AimsTemplateDataService.class);

	@Autowired
	private AimsTemplateDataMapper aimsTemplateDataMapper;
	@Autowired
	private AimsTemplateDetailMapper aimsTemplateDetailMapper;
	
	public Page<AimsTemplateData> queryAimsTemplateDataPage(Searcher searcher, Page<AimsTemplateData> page) {
		
		return aimsTemplateDataMapper.queryAimsTemplateDataPage(searcher, page);
	}


	/**
	 * 根据moduleItemId 查询
	 */
	public List<AimsTemplateData> queryByModuleItemId(Long moduleItemId) {
		return  aimsTemplateDataMapper.queryByModuleItemId(moduleItemId);
	}
	/**
	 * 获取取数模板列表信息
	 * @return
	 */
	public List<AimsTemplateData> geAimsTemplateDatas() {
		return aimsTemplateDataMapper.selectAll();
	}
	/**
	 * 获取取数语句单条信息
	 */
	public AimsTemplateData geTemplateData(AimsTemplateData aimsTemplateData) {
		return aimsTemplateDataMapper.selectOne(aimsTemplateData);
	}
	/**
	 * 增加
	 */
	public void addAimsTemplateData(AimsTemplateData aimsTemplateData) {
		aimsTemplateDataMapper.insert(aimsTemplateData);
	}
//	public Map<String, Object> addAimsTemplateData(AimsTemplateData aimsTemplateData) {
//		Map<String, Object> result = new HashMap<String,Object>();
//		//判断id是否重复
//		AimsTemplateData data = new AimsTemplateData();
//		data.setId(aimsTemplateData.getId());
//		if(aimsTemplateDataMapper.selectOne(data) != null) {
//			result.put("tag", false);
//			result.put("msg", "取数模板id重复");
//			logger.warn("取数模板id重复！");
//			return result;
//		}
		//配置取数模板时进行校验
//		if(aimsTemplateDataMapper.insertSelective(aimsTemplateData) > 0) {
//			result.put("tag", true);
//			result.put("msg", "配置成功！");
//		} else {
//			result.put("tag", true);
//			result.put("msg", "配置失败！");
//		}
//		
//		return result;
//	}
	/**
	 * 删除
	 */
	public void delAimsTemplateData(AimsTemplateData aimsTemplateData) {		
		aimsTemplateDataMapper.deleteByPrimaryKey(aimsTemplateData);
	}
	/**
	 * 修改
	 */
//	public Map<String, Object> updateAimsTemplateData(AimsTemplateData aimsTemplateData) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		if (aimsTemplateDataMapper.updateById(aimsTemplateData) > 0) {
//			result.put("tag", true);
//			result.put("msg", "修改成功！");
//		} else {
//			result.put("tag", false);
//			result.put("msg", "修改失败！");
//		}
//		return result;
//	}
	public void updateAimsTemplateData(AimsTemplateData aimsTemplateData) {
		aimsTemplateDataMapper.updateByPrimaryKey(aimsTemplateData);
	}
	/**
	 * 根据id查询
	 */
	public AimsTemplateData queryOne(Long id) {
		return aimsTemplateDataMapper.queryOne(id);
	}
	/**
	 * 判断是否有默认取数模板
	 * @param id
	 * @return
	 */	
	public AimsTemplateDetail queryDefaultTemplate(Long moduleItemId) {
		AimsTemplateDetail defaultTemplate = new AimsTemplateDetail();
		defaultTemplate.setModuleItemId(moduleItemId);
		defaultTemplate.setIsDefault("1");
		defaultTemplate = aimsTemplateDetailMapper.selectOne(defaultTemplate);
		return defaultTemplate;
	}
	/**
	 * 判断角色唯一性
	 * @param aimsTemplateData
	 * @return
	 */
	public int selectCount(AimsTemplateData aimsTemplateData) {
		return aimsTemplateDataMapper.selectCount(aimsTemplateData);
	}

	/**
	 * 
	 * 根据bizId 和 moduleItemId 查询
	 */
	public List<AimsTemplateData> geTemplateDatasByBizId(long moduleItemId, String bizId) {
		return aimsTemplateDataMapper.geTemplateDatasByBizId(moduleItemId, bizId);
	}
}
