package com.bosssoft.egov.asset.template.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bosssoft.egov.aims.config.service.SysConfigHelper;
import com.bosssoft.egov.asset.aims.api.config.entity.AssetSysConfig;
import com.bosssoft.egov.asset.basic.BaseController;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.egov.asset.service.AssetGeneralService;
import com.bosssoft.egov.asset.template.entity.AimsTemplateData;
import com.bosssoft.egov.asset.template.entity.AimsTemplateDetail;
import com.bosssoft.egov.asset.template.service.AimsTemplateDataService;
import com.bosssoft.egov.asset.template.service.AimsTemplateDetailService;
import com.bosssoft.egov.asset.template.service.PrintDataProcessService;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

@Controller
@RequestMapping("egov/asset/template/print")
public class AimsTemplateManagerController extends BaseController {

	@Autowired
	private AimsTemplateDetailService detailService;
	@Autowired
	private AssetGeneralService generalService;
	@Autowired
	private AimsTemplateDataService aimsTemplateDataService;
	
	/**
	 * 获取打印数据
	 * 
	 * @param moduleItemId
	 *            打印模板ID
	 * @param params
	 *            用户信息
	 * @return
	 */
	@RequestMapping(value = "getPrintData.do", name = "获取打印数据")
	@ResponseBody
	public Map<String, Object> getPrintData(String moduleItemId,String bizId, Map<String, Object> params) {
		params = WebApplicationContext.getContext().getWebRequestContext().getRequestParamsMap();
		Long itemId = NumberUtilsExt.toLong(moduleItemId);
		AimsTemplateDetail aimsTemplateDetail = detailService.queryOne(itemId);
		if(aimsTemplateDetail == null){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("__recordset__", new ArrayList<>());
			return setParams(result);
		}
		String moudleSql = aimsTemplateDetail.getModuleSql();
		//String status = aimsTemplateData.getStatus();
		// 如果moduleSql为空，返回一个空的map
//		if (StringUtilsExt.isBlank(moudleSql)) {
//			AimsTemplateData aimsTemplateData = new AimsTemplateData();
//			if(StringUtilsExt.isBlank(aimsTemplateData.getModuleSql())) {
//				return new HashMap<String, Object>();
//			}
//		}
		List<AimsTemplateData> aimsTemplateData = new ArrayList<AimsTemplateData>(); 
		if(bizId == null || bizId == "") {		
			aimsTemplateData = aimsTemplateDataService.queryByModuleItemId(itemId);
		} else {
		   aimsTemplateData = aimsTemplateDataService.geTemplateDatasByBizId(itemId, bizId);
		}
        if(aimsTemplateData == null){
        	aimsTemplateData = new ArrayList<AimsTemplateData>();        	
        } 
        if(!StringUtilsExt.isBlank(moudleSql)){
	        AimsTemplateData _data = new AimsTemplateData();
	    	_data.setModuleItemId(itemId);
	    	_data.setModuleSql(moudleSql);
	    	_data.setBizId(bizId);
	    	_data.setModuleType("__recordset__");
	    	aimsTemplateData.add(_data);
        }
		Map<String, List<Map<String, Object>>> dataResult = new HashMap<String, List<Map<String, Object>>>();
		// 如果moduleSql不为空的话，则对moduleSql进行二次处理
		// 获取当前用户信息的map值
		params.putAll(getCurrUser().getProperties());
		for(AimsTemplateData data: aimsTemplateData){		
			List<Map<String, Object>> datas = generalService.queryCommon(StringUtilsExt.environmentSubstituteByMapObj(data.getModuleSql(), getSqlParams(params)), params);
			
			// 当返回数据为空时，加入当前单位名称和单位
			if (datas.size() == 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("ORGNAME", getCurrUser().getOrgName());
				String switchFlag = (String)params.get("switchFlag");
				if ("1".equals(switchFlag)) {
					map.put("UNIT", "万元");
				} else {
					map.put("UNIT", "元");
				}
				datas.add(map);
			}
			dataResult.put(data.getModuleType(), datas);
		}
		
		//List<Map<String, Object>> data = generalService.queryCommon(StringUtilsExt.environmentSubstituteByMapObj(moudleSql, sqlParams), params);
		String moduleProcessBean = aimsTemplateDetail.getModuleProcessBean();
		// 判断moduleProcessBean是否为空，是否要对数据进行二次处理
		if (StringUtilsExt.isNotBlank(moduleProcessBean)) {
			// 不需要二次处理的，直接返回打印数据
			PrintDataProcessService process = AppContext.getAppContext().lookup(moduleProcessBean,PrintDataProcessService.class);
			dataResult = process.doProcess(getCurrUser(), params, dataResult);
		}
		Map<String, Object> result = new HashMap<String,Object>();
		List<Map<String, Object>> data = dataResult.get("__recordset__");
		if(data != null && data.size() >0){
			result.putAll(data.get(0));
		}
		result.putAll(dataResult);		
		
		//加入用户信息
		Map<String, Object> resultMap = BeanUtilsExt.objectKeyNullToEmptyByCamel(result);		
		return setParams(resultMap);
	}
	
	private Map<String,Object> setParams(Map<String,Object> resultMap){
		resultMap.put("__userCode__", getCurrUser().getUserCode());
		resultMap.put("__userName__", getCurrUser().getUserName());
		resultMap.put("__orgCode__", getCurrUser().getOrgCode());
		resultMap.put("__orgName__", getCurrUser().getOrgName());
		resultMap.put("__rgnCode__", getCurrUser().getRgnCode());
		resultMap.put("__rgnName__", getCurrUser().getRgnName());		
		resultMap.put("__roleCode__", StringUtilsExt.join(getCurrUser().getRoleCodes(), ","));
		resultMap.put("__roleName__", StringUtilsExt.join(getCurrUser().getRoleNames(), ","));

		//从数据库中配置打印信息
		List<AssetSysConfig> cfgList = SysConfigHelper.getInstance().getSysConfig(null, "PRINT");
		if (cfgList != null) {
			for(AssetSysConfig assetSysConfig : cfgList) {
				String configValue = assetSysConfig.getConfigValue();
				String configCode = assetSysConfig.getConfigCode();
				//当configCode 为空时，跳出本次循环
				if(StringUtilsExt.isBlank(configCode)) {
					continue;
				}
				resultMap.put(configCode, configValue);
			}
		}
		return resultMap;
	}
	
	/**
	 * 获得打印数据的sql语句
	 * @param params
	 * @return
	 */
	private  Map<String, Object> getSqlParams(Map<String, Object> params) {
			Map<String, Object> sqlParams = new HashMap<String,Object>();
			for (String key : params.keySet()) {
				Object value = params.get(key);
				// 判断参数是用什么类型的方式传输的
				// 如果参数类型是字符串
				if (value instanceof String[]) {
					String[] _value = (String[]) value;
					String[] __v = new String[_value.length];

					for (int i = 0; i < _value.length; i++) {
						__v[i] = "#{params." + key + "[" + i + "]}";
					}
					// 取出sqlParams的value值，用逗号隔开
					sqlParams.put(key, StringUtilsExt.join(__v, ","));
				} else if (value instanceof List) { // 参数类型是数组
					@SuppressWarnings("unchecked")
					List<Object> _value = (List<Object>) value;
					String[] __v = new String[_value.size()];
					// 循环遍历 取出value 重新拼装成 #{params.key}的形式R
					for (int i = 0; i < _value.size(); i++) {
						__v[i] = "#{params." + key + "[" + i + "]}";
					}
					sqlParams.put(key, StringUtilsExt.join(__v, ","));
				} else {
					sqlParams.put(key, "#{params." + key + "}");
				}
			}
		return sqlParams;
	}
	
}
