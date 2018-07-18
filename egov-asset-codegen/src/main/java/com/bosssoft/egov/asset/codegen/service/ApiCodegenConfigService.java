package com.bosssoft.egov.asset.codegen.service;

import java.util.Map;

/** 
*
* @ClassName   类名：ApiCodegenConfigService 
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
public interface ApiCodegenConfigService {

	/**
	 * 
	 * <p>函数名称：  newBillCodeByAdd      </p>
	 * <p>功能说明： 在新增时调用此编码 若此业务规则配置是新增时计算编码 则会返回编码信息 
	 *             否则返回""
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizId 业务id
	 * @param orgId 单位id
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String newBillCodeByAdd(String bizType, Object orgId);
	
	/**
	 * 
	 * <p>函数名称：  newBillCodeByAdd      </p>
	 * <p>功能说明： 在新增时调用此编码 若此业务规则配置是新增时计算编码 则会返回编码信息 
	 *             否则返回""
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizId 业务id
	 * @param orgId 单位id
	 * @param ext 额外参数
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String newBillCodeByAdd(String bizType, Object orgId, Map<String,Object> ext);
	
	/**
	 * 
	 * <p>函数名称：  newBillCodeBySave      </p>
	 * <p>功能说明： 在新增且在保存时调用此方法生成单据编码
	 *             若此业务规则配置是新增切保存时计算编码 则会返回编码信息 
	 *             否则返回 传入的 curBillCode值 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizId
	 * @param orgId
	 * @param curBillCode 当前编码值
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String newBillCodeBySave(String bizType, Object orgId, String curBillCode);
	
	/**
	 * 
	 * <p>函数名称：  newBillCodeBySave      </p>
	 * <p>功能说明： 在新增且在保存时调用此方法生成单据编码
	 *             若此业务规则配置是新增切保存时计算编码 则会返回编码信息 
	 *             否则返回 传入的 curBillCode值 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizId
	 * @param orgId
	 * @param curBillCode 当前编码值
	 * @param ext 额外参数
	 * @return
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String newBillCodeBySave(String bizType, Object orgId, String curBillCode,Map<String,Object> ext);
	
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
	public String[] newBatchBillCode(String bizType, Object orgId, int batchCount, Map<String,Object> ext);
	public String[] newBatchBillCode(String bizType, Object orgId, int batchCount);
	
}
