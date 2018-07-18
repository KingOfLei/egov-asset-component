package com.bosssoft.egov.asset.codegen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.codegen.CodeGenRule;
import com.bosssoft.egov.asset.codegen.CodeRuleHelper;
import com.bosssoft.egov.asset.codegen.CurrentStatus;
import com.bosssoft.egov.asset.codegen.PaddingSide;
import com.bosssoft.egov.asset.codegen.entity.AssetCodegenConfig;
import com.bosssoft.egov.asset.codegen.entity.AssetCodegenRule;
import com.bosssoft.egov.asset.codegen.entity.CodegenContext;
import com.bosssoft.egov.asset.codegen.rules.DateCodeRule;
import com.bosssoft.egov.asset.codegen.rules.ICodeRule;
import com.bosssoft.egov.asset.codegen.service.ApiCodegenConfigService;
import com.bosssoft.egov.asset.codegen.service.AssetCodegenConfigService;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/** 
*
* @ClassName   类名：ApiCodegenConfigServiceImpl 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月8日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月8日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Service
public class ApiCodegenConfigServiceImpl implements ApiCodegenConfigService{

	@Autowired
	private AssetCodegenConfigService codeConfigService;
	private Interner<String> lock = Interners.newWeakInterner();

	@Override
	public String newBillCodeByAdd(String bizType, Object orgId) {
		return newBillCode(bizType, orgId, CurrentStatus.ADD, "", new HashMap<String,Object>(0));
	}

	@Override
	public String newBillCodeByAdd(String bizType, Object orgId,
			Map<String, Object> ext) {
		return newBillCode(bizType, orgId, CurrentStatus.ADD, "", ext);
	}

	@Override
	public String newBillCodeBySave(String bizType, Object orgId,
			String curBillCode) {
		return newBillCode(bizType, orgId, CurrentStatus.ADD2SAVE, curBillCode, new HashMap<String,Object>(0)) ;
	}

	@Override
	public String newBillCodeBySave(String bizType, Object orgId,
			String curBillCode, Map<String, Object> ext) {
		 return newBillCode(bizType, orgId, CurrentStatus.ADD2SAVE, curBillCode, ext);
	}

    
	/**
	 * 
	 * <p>函数名称：  newBillCode      </p>
	 * <p>功能说明： 真正处理编码方法
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizId
	 * @param orgId
	 * @param curStatus
	 * @param curBillCode
	 * @param ext
	 * @return
	 *
	 * @date   创建时间：2017年1月8日
	 * @author 作者：xds
	 */
	private String newBillCode(String bizType, Object orgId,CurrentStatus curStatus,String curBillCode,Map<String,Object> ext){
		//获取参数
		//判断是否为全局变量类型 目前认为系统配置的默认规则为全局则认定所有此类型都为全局 ..先这么处理 后面再来 
		boolean isGobel = codeConfigService.isGobel(bizType);
		if(isGobel) orgId = "0123456789"; //全局时 所有单位通用一个配置
		orgId = StringUtilsExt.convertNullToString(orgId);
		CodeGenRule genRule = CodeGenRule.parse(codeConfigService.getAssetCodegenConfigGenRule(orgId, bizType));	
		//先判断当前状态 
		if(curStatus == CurrentStatus.ADD){
			//新增状态			
			if(genRule == CodeGenRule.SAVE){
				return "";//此时编码不计算
			}
		} else {
			//新增保存状态
			if(genRule == CodeGenRule.ADD && !StringUtilsExt.isBlank(curBillCode)){
				return curBillCode;//返回当前编码 当前状态不计算 因为在新增是已经生成编码 //2017-08-16 若编码没有生成 直接生成
			}			
		}
		
		//加上同步操作 感觉锁不锁 都无感。。 本身update 就是会锁记录的
	//	synchronized(lock.intern(StringUtilsExt.join(bizType,orgId))){			
			AssetCodegenConfig config = codeConfigService.getAssetCodegenConfig(orgId, bizType);
			//开始计算编码
	
			//1.重置规则 暂时默认当配置规则有日期规则时 当日期变动流水号重新开始
			
			//2.生成编码 循环规则列表
			List<AssetCodegenRule> rules = config.getRules();
			//计算是否有重置规则
			if(StringUtilsExt.isNotBlank(config.getSeqReset())){
				//暂时默认寻找配置规则的日期规则  以后考虑建立工厂类统一处理 目前先这么用。。
	            for(AssetCodegenRule rule : rules){
	    			ICodeRule codeRule = CodeRuleHelper.getCodeRule(rule.getRuleName());
	                if(codeRule instanceof DateCodeRule){
	                	CodegenContext context = new CodegenContext();
	        		    context.setParams(ext);
	        		    context.setConfig(config);
	        		    context.setRule(rule);
	        		    context.setCurNo(config.getCurNo());
	        		    context.setFillChar(rule.getFillChar());
	        		    context.setLength(rule.getLength());
	        		    context.setPaddingSide(PaddingSide.parse(rule.getFillWay()));
	        		    context.setCodeGenRule(CodeGenRule.parse(config.getGenRule()));
	        		    context.setRuleValue(rule.getRuleValue());
	        		    //获取日期
	        		    codeRule.init(context);
	        		    String date = codeRule.getResult();
	        		    if(!StringUtilsExt.toString(config.getCurReset()).equals(date)){
	        		    	config.setCurNo(1);
	        		    	config.setCurReset(date);
	        		    }
	                }
	            }
			}
			//保证升序排列 
			OrderByUtil.sort(rules, "sxh asc");
			//默认把单位id放入参数中
			ext.put(CodegenContext.ORG_ID_KEY, orgId);
			List<String> ruleResule = new ArrayList<String>();
			for(AssetCodegenRule rule : rules){
				//获取编码规则
				ICodeRule codeRule = CodeRuleHelper.getCodeRule(rule.getRuleName());
				CodegenContext context = new CodegenContext();
			    context.setParams(ext);
			    context.setConfig(config);
			    context.setRule(rule);
			    context.setCurNo(config.getCurNo());
			    context.setFillChar(rule.getFillChar());
			    context.setLength(rule.getLength());
			    context.setPaddingSide(PaddingSide.parse(rule.getFillWay()));
			    context.setCodeGenRule(CodeGenRule.parse(config.getGenRule()));
			    context.setRuleValue(rule.getRuleValue());
			    codeRule.init(context);
			    ruleResule.add(codeRule.getResult());		   
			}		
			//组合各结果值
			String billCode = StringUtilsExt.join(ruleResule.toArray(), config.getSeparator());
			//更新 当前编码及重置规则值
			AssetCodegenConfig updConfig = new AssetCodegenConfig();
			updConfig.setCfgId(config.getCfgId());
			updConfig.setCurCode(billCode);
			updConfig.setCurReset(config.getCurReset());
			//重置后 流水号变了 故再更新一次 
			updConfig.setCurNo(config.getCurNo());
			codeConfigService.updateAssetCodegenConfigSelective(updConfig);	
			
			return billCode;
		//}
	}
	
	/**
	 * 
	 * <p>函数名称：   newBatchBillCode     </p>
	 * <p>功能说明： 批量获取编码  直接返回新的编码
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType 业务类型
	 * @param orgId 单位编码
	 * @param bachtCount 返回编码个数
	 * @param ext 额外参数
	 * @return
	 *
	 * @date   创建时间：2017年10月21日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String[] newBatchBillCode(String bizType, Object orgId, int batchCount, Map<String,Object> ext){
	   return newBillCodeByBatch(bizType, orgId, batchCount, ext);	
	}
	public String[] newBatchBillCode(String bizType, Object orgId, int batchCount){
	   return newBillCodeByBatch(bizType, orgId, batchCount, new HashMap<String,Object>());
	}
	private String[] newBillCodeByBatch(String bizType, Object orgId,int batchCount,Map<String,Object> ext){
		//获取参数
		//判断是否为全局变量类型 目前认为系统配置的默认规则为全局则认定所有此类型都为全局 ..先这么处理 后面再来 
		boolean isGobel = codeConfigService.isGobel(bizType);
		if(isGobel) orgId = "0123456789"; //全局时 所有单位通用一个配置
		orgId = StringUtilsExt.convertNullToString(orgId);
		//加上同步操作
	//	synchronized(lock.intern(StringUtilsExt.join(bizType,orgId))){
			if(batchCount == 0) batchCount = 1;
			AssetCodegenConfig config = codeConfigService.getAssetCodegenConfig(orgId, bizType,batchCount);
			//开始计算编码
	
			//1.重置规则 暂时默认当配置规则有日期规则时 当日期变动流水号重新开始
			
			//2.生成编码 循环规则列表
			List<AssetCodegenRule> rules = config.getRules();
			//计算是否有重置规则
			if(StringUtilsExt.isNotBlank(config.getSeqReset())){
				//暂时默认寻找配置规则的日期规则  以后考虑建立工厂类统一处理 目前先这么用。。
	            for(AssetCodegenRule rule : rules){
	    			ICodeRule codeRule = CodeRuleHelper.getCodeRule(rule.getRuleName());
	                if(codeRule instanceof DateCodeRule){
	                	CodegenContext context = new CodegenContext();
	        		    context.setParams(ext);
	        		    context.setConfig(config);
	        		    context.setRule(rule);
	        		    context.setCurNo(config.getCurNo());
	        		    context.setFillChar(rule.getFillChar());
	        		    context.setLength(rule.getLength());
	        		    context.setPaddingSide(PaddingSide.parse(rule.getFillWay()));
	        		    context.setCodeGenRule(CodeGenRule.parse(config.getGenRule()));
	        		    context.setRuleValue(rule.getRuleValue());
	        		    //获取日期
	        		    codeRule.init(context);
	        		    String date = codeRule.getResult();
	        		    if(!StringUtilsExt.toString(config.getCurReset()).equals(date)){
	        		    	//本次流水号 = 当前流水号+步长*(此次需要返回的个数)
	        		    	config.setCurNo(0+1*(NumberUtilsExt.toInt(StringUtilsExt.toString(config.getStep()), 1)*batchCount));
	        		    	config.setCurReset(date);			        		    	
	        		    }
	                }
	            }
			}
			//保证升序排列 
			OrderByUtil.sort(rules, "sxh asc");
			//默认把单位id放入参数中
			ext.put(CodegenContext.ORG_ID_KEY, orgId);
			TreeSet<String> batchBillCodeList = new TreeSet<String>();
			for(int i = 0; i < batchCount; i++){
				List<String> ruleResule = new ArrayList<String>();
				for(AssetCodegenRule rule : rules){
					//获取编码规则
					ICodeRule codeRule = CodeRuleHelper.getCodeRule(rule.getRuleName());
					CodegenContext context = new CodegenContext();
				    context.setParams(ext);
				    context.setConfig(config);
				    context.setRule(rule);
				    context.setCurNo(config.getCurNo() - i);
				    context.setFillChar(rule.getFillChar());
				    context.setLength(rule.getLength());
				    context.setPaddingSide(PaddingSide.parse(rule.getFillWay()));
				    context.setCodeGenRule(CodeGenRule.parse(config.getGenRule()));
				    context.setRuleValue(rule.getRuleValue());
				    codeRule.init(context);
				    ruleResule.add(codeRule.getResult());		   
				}		
				//组合各结果值
				String billCode = StringUtilsExt.join(ruleResule.toArray(), config.getSeparator());
				batchBillCodeList.add(billCode);
			}
			//更新 当前编码及重置规则值
			AssetCodegenConfig updConfig = new AssetCodegenConfig();
			updConfig.setCfgId(config.getCfgId());
			updConfig.setCurCode(batchBillCodeList.last());//最大的值即最后一个数
			updConfig.setCurReset(config.getCurReset());
			//重置后 流水号变了 故再更新一次 
			updConfig.setCurNo(config.getCurNo());
			codeConfigService.updateAssetCodegenConfigSelective(updConfig);				
			return batchBillCodeList.toArray(new String[0]);
		//} 
	}
}
