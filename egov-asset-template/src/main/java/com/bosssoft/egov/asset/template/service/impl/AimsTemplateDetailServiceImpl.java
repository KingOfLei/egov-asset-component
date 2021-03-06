package com.bosssoft.egov.asset.template.service.impl;


/**
 * 福建博思软件 1997-2017 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Sun Jan 22 13:31:36 CST 2017
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.template.entity.AimsTemplateData;
import com.bosssoft.egov.asset.template.entity.AimsTemplateDetail;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.template.service.AimsTemplateDetailService;
import com.bosssoft.egov.asset.template.mapper.AimsTemplateDataMapper;
import com.bosssoft.egov.asset.template.mapper.AimsTemplateDetailMapper;


/**
 * 类说明: AimsTemplateDetailService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2017-01-22   Administrator　　　新建
 * </pre>
 */
@Service
public class AimsTemplateDetailServiceImpl implements AimsTemplateDetailService {

	//private static Logger logger = LoggerFactory.getLogger(AimsTemplateDetailServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private AimsTemplateDetailMapper aimsTemplateDetailMapper;
	
	@Autowired
	private AimsTemplateDataMapper aimsTemplateDataMapper;
	/**
	 *
	 * @param aimsTemplateDetail
	 */
	public Map<String, Object> delAimsTemplateDetail(AimsTemplateDetail aimsTemplateDetail)  {
		Map<String, Object> result = new HashMap<String, Object>();
		Long moduleItemId = aimsTemplateDetail.getModuleItemId();
		String moduleItemCode = aimsTemplateDetail.getModuleItemCode();
		String moduleItemName = aimsTemplateDetail.getModuleItemName();
		if(!StringUtilsExt.isNotBlank(moduleItemCode)){
			moduleItemCode = "";
		}
		if(!StringUtilsExt.isNotBlank(moduleItemName)){
			moduleItemName = "";
		}
		//删除模板之后  还需要删除  aims_template_data 表中的数据
		aimsTemplateDetailMapper.deleteById(aimsTemplateDetail);
		AimsTemplateData queryData = new AimsTemplateData();
		queryData.setModuleItemId(moduleItemId);
		List<AimsTemplateData> datas = aimsTemplateDataMapper.select(queryData);
		if(datas != null && datas.size() > 0){
			aimsTemplateDataMapper.delete(queryData);
		}
		result.put("tag", true);
		result.put("msg", "模板【"+ moduleItemCode +"】"+ moduleItemName +"删除成功！");
		return result;
	}

	/**
	 *
	 * @param aimsTemplateDetail
	 */
	public Map<String, Object> updateAimsTemplateDetail(AimsTemplateDetail aimsTemplateDetail)  {
		Map<String, Object> result = new HashMap<String, Object>();
		String moduleItemCode = aimsTemplateDetail.getModuleItemCode();
		String moduleItemName = aimsTemplateDetail.getModuleItemName();
		if(!StringUtilsExt.isNotBlank(moduleItemCode)){
			moduleItemCode = "";
		}
		if(!StringUtilsExt.isNotBlank(moduleItemName)){
			moduleItemName = "";
		}
		//判断是否存在多个默认模板
		/*Long moduleId = aimsTemplateDetail.getModuleId();
		String moduleCode = aimsTemplateDetail.getModuleCode();
		String moduleName = aimsTemplateDetail.getModuleName();
		//修改模板设置为默认模板的时候进行校验
		if("1".equals(aimsTemplateDetail.getIsDefault()) ){
			//判断是否存在多个默认模板
			AimsTemplateDetail defaultTemplate = this.queryDefaultTemplate(moduleId);
			//判断是否是同一个模板
			if(defaultTemplate != null){
				if(!aimsTemplateDetail.getModuleItemId().equals(defaultTemplate.getModuleItemId())){
					result.put("tag", false);
					result.put("msg", "[" + moduleCode + "]"+ moduleName +"已存在默认模板!");
					logger.warn("[" + moduleCode + "]"+ moduleName +"已存在默认模板!");
					return result;
				}
			}
		}*/
		aimsTemplateDetailMapper.updateById(aimsTemplateDetail);
		result.put("tag", true);
		result.put("msg", "模板【"+ moduleItemCode +"】"+ moduleItemName +"修改成功!");
		return result;
	}

	/**
	 *
	 * @param aimsTemplateDetail
	 * @retrun
	 */
	public List<AimsTemplateDetail> getAimsTemplateDetailList(AimsTemplateDetail aimsTemplateDetail)  {
		return null;
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AimsTemplateDetail> queryAimsTemplateDetailPage(Searcher searcher, Page<AimsTemplateDetail> page)  {
		return aimsTemplateDetailMapper.queryAimsTemplateDetailPage(searcher, page);
	}

	@Override
	public Page<AimsTemplateDetail> queryTemplateDetailPage(Searcher searcher,
			Page<AimsTemplateDetail> page, Long moduleId,Long orgId) {
		return aimsTemplateDetailMapper.queryTemplateDetailPage(searcher, page, moduleId, orgId);
	}

	@Override
	public Map<String, Object> saveTemplateFile(Long moduleItemId,
			byte[] templateFile,String fileName) {
		Map<String,Object> result = new HashMap<String, Object>();
		AimsTemplateDetail aimsTemplateDetail = new AimsTemplateDetail();
		aimsTemplateDetail = aimsTemplateDetailMapper.queryOne(moduleItemId);
		aimsTemplateDetail.setModuleFile(templateFile);
		if(fileName != null){
			//将文件名改为模板ID
			fileName = aimsTemplateDetail.getModuleItemId().toString();
			aimsTemplateDetail.setFileName(fileName);
		}
		Long version = aimsTemplateDetail.getVersion();
		Long ver = new Long(version.intValue() + 1);
		aimsTemplateDetail.setVersion(ver);
		if(aimsTemplateDetailMapper.updateById(aimsTemplateDetail) > 0){
			result.put("tag",true);
			result.put("msg", "模板文件上传成功!");
		}else {
			result.put("tag",true);
			result.put("msg", "模板文件上传失败!");
		}
		return result;
	}

	@Override
	public AimsTemplateDetail queryOne(Long moduleItemId) {
		return aimsTemplateDetailMapper.queryOne(moduleItemId);
	}

	@Override
	public Map<String, Object> addAimsTemplateDetail(AimsTemplateDetail aimsTemplateDetail) {
		Map<String, Object> result = new HashMap<String, Object>();
		String moduleItemCode = aimsTemplateDetail.getModuleItemCode();
		String moduleItemName = aimsTemplateDetail.getModuleItemName();
		if(!StringUtilsExt.isNotBlank(moduleItemCode)){
			moduleItemCode = "";
		}
		if(!StringUtilsExt.isNotBlank(moduleItemName)){
			moduleItemName = "";
		}
		aimsTemplateDetailMapper.insertSelective(aimsTemplateDetail);
		result.put("tag", true);
		result.put("msg", "模板【"+ moduleItemCode +"】"+ moduleItemName +"新增成功!");		
		return result;	
	}

	@Override
	public AimsTemplateDetail queryOneByFileName(String fileName) {
		AimsTemplateDetail aimsTemplateDetail = new AimsTemplateDetail();
		aimsTemplateDetail.setFileName(fileName);
		return aimsTemplateDetailMapper.selectOne(aimsTemplateDetail);
	}

	@Override
	public AimsTemplateDetail queryDefaultTemplate(Long moduleId) {
		AimsTemplateDetail defaultTemplate = new AimsTemplateDetail();
		defaultTemplate.setModuleId(moduleId);
		defaultTemplate.setIsDefault("1");
		defaultTemplate = aimsTemplateDetailMapper.selectOne(defaultTemplate);
		return defaultTemplate;
	}

	@Override
	public List<AimsTemplateDetail> select(AimsTemplateDetail aimsTemplateDetail) {
		List<AimsTemplateDetail> templateDetails = aimsTemplateDetailMapper.select(aimsTemplateDetail);
		return templateDetails;
	}

	@Override
	public Page<AimsTemplateDetail> queryTemplateDetailPageByOrgId(Searcher searcher,
			Page<AimsTemplateDetail> page, Long moduleId, Long orgId) {
		return aimsTemplateDetailMapper.queryTemplateDetailPageByOrgId(searcher, page, moduleId, orgId);
	}

}