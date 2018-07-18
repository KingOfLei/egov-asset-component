package com.bosssoft.egov.asset.codegen;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.codegen.service.ApiCodegenConfigService;
import com.bosssoft.egov.asset.codegen.service.AssetCodegenConfigService;
import com.bosssoft.egov.asset.codegen.service.AssetCodegenRuleService;
import com.bosssoft.platform.common.extension.support.SpringExtensionHelper;

/** 
*
* @ClassName   类名：CodeGen 
* @Description 功能说明：生成一个新的单据号 
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CodeGen {

	@Resource
	private AssetCodegenConfigService codeConfigService;
	@Resource
	private AssetCodegenRuleService codeRuleService;
	
	@Resource
	private ApiCodegenConfigService apiConfigServcie;
	
	private static CodeGen INSTANCE;
	
	private CodeGen(){
		SpringExtensionHelper.initAutowireFields(this);
	}
	
	private static synchronized CodeGen getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new CodeGen();
		}
		return INSTANCE;
   }
	
	/**
	 * 
	 * <p>函数名称： newBillCodeByAdd       </p>
	 * <p>功能说明： 在新增时调用此编码 若此业务规则配置是新增时计算编码 则会返回编码信息 
	 *             否则返回""
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType 业务类型
	 * @param orgId 单位id
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String newBillCodeByAdd(String bizType, Object orgId){
//		return getInstance().newCode(bizId, orgId, CurrentStatus.ADD, "", new HashMap<String,Object>(0));
		return getInstance()._newBillCodeByAdd(bizType, orgId);
	}
	
	private String _newBillCodeByAdd(String bizType, Object orgId){
		return apiConfigServcie.newBillCodeByAdd(bizType, orgId);
	}
	
	/**
	 * 
	 * <p>函数名称：  newBillCodeByAdd      </p>
	 * <p>功能说明： 在新增时调用此编码 若此业务规则配置是新增时计算编码 则会返回编码信息 
	 *             否则返回""
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType 业务类型
	 * @param orgId 单位id
	 * @param ext 额外参数
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String newBillCodeByAdd(String bizType, Object orgId, Map<String,Object> ext){
//		return getInstance().newCode(bizId, orgId, CurrentStatus.ADD, "", ext);
		return getInstance()._newBillCodeByAdd(bizType, orgId, ext);
	}	
	
	private String _newBillCodeByAdd(String bizType, Object orgId, Map<String,Object> ext){
		return apiConfigServcie.newBillCodeByAdd(bizType, orgId, ext);
	}
	
	/**
	 * 
	 * <p>函数名称：newBillCodeBySave        </p>
	 * <p>功能说明： 在新增且在保存时调用此编码 若此业务规则配置是新增切保存时计算编码 则会返回编码信息 
	 *             否则返回 传入的 curBillCode值 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType 业务类型
	 * @param orgId
	 * @param curBillCode 当前编码值
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String newBillCodeBySave(String bizType, Object orgId, String curBillCode){
//		return getInstance().newCode(bizId, orgId, CurrentStatus.ADD2SAVE, curBillCode, new HashMap<String,Object>(0));
		return getInstance()._newBillCodeBySave(bizType, orgId, curBillCode);
	}
	
	private String _newBillCodeBySave(String bizType, Object orgId, String curBillCode){
		return apiConfigServcie.newBillCodeBySave(bizType, orgId,curBillCode);
	}
	
	/**
	 * 
	 * <p>函数名称：  newBillCodeBySave      </p>
	 * <p>功能说明： 在新增且在保存时调用此方法生成单据编码
	 *             若此业务规则配置是新增切保存时计算编码 则会返回编码信息 
	 *             否则返回 传入的 curBillCode值 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType 业务类型
	 * @param orgId
	 * @param curBillCode 当前编码值
	 * @param ext 额外参数
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String newBillCodeBySave(String bizType, Object orgId, String curBillCode,Map<String,Object> ext){
//	   return getInstance().newCode(bizId, orgId, CurrentStatus.ADD2SAVE, curBillCode, ext);
	   return getInstance()._newBillCodeBySave(bizType, orgId,curBillCode, ext);
	}
	
	private String _newBillCodeBySave(String bizType, Object orgId, String curBillCode, Map<String,Object> ext){
		return apiConfigServcie.newBillCodeBySave(bizType, orgId, curBillCode, ext);
	}
	
	/**
	 * 
	 * <p>函数名称：newBillCodeByBatch        </p>
	 * <p>功能说明： 获取批量编码
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType 业务类型
	 * @param orgId 单位id
	 * @param batchCount 个数
	 * @param ext 额外参数
	 * @return
	 *
	 * @date   创建时间：2017年10月21日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String[] newBillCodeByBatch(String bizType, Object orgId, int batchCount, Map<String,Object> ext){
		return getInstance()._newBillCodeByBatch(bizType, orgId, batchCount, ext);
	}
	
	public static String[] newBillCodeByBatch(String bizType, Object orgId, int batchCount){
		return getInstance()._newBillCodeByBatch(bizType, orgId, batchCount, new HashMap<String,Object>());
	}
	
	private String[] _newBillCodeByBatch(String bizType, Object orgId, int batchCount, Map<String,Object> ext){
		return apiConfigServcie.newBatchBillCode(bizType, orgId, batchCount, ext);
	}
}
