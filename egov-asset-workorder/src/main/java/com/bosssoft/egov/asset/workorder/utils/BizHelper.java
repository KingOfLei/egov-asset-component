package com.bosssoft.egov.asset.workorder.utils;

import java.util.HashMap;
import java.util.Map;

import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：BizHelper 
* @Description 功能说明：业务帮助类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年6月1日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年6月1日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class BizHelper {

	private final static Map<String,String> SYSTEM_PREFIX = new HashMap<String,String>();
	private final static Map<String,String> assetTypeMap = new HashMap<String,String>();
	public final static Map<String,String> classifyTableMap = new HashMap<String,String>();


	static {
		SYSTEM_PREFIX.put("10101", "");//行政事业单位资产
		SYSTEM_PREFIX.put("10102", "JG");//行政事业单位占有固定资产
		SYSTEM_PREFIX.put("101", "XZSY");//行政事业单位委托代理资产
		SYSTEM_PREFIX.put("102", "JG");//经管资产
		SYSTEM_PREFIX.put("103", "GY");//企业国有权益
		SYSTEM_PREFIX.put("201", "DZ");//低值易耗
		
		classifyTableMap.put("GD", "ASSET_DICT_ASSET_CLASSIFY_GD");
		classifyTableMap.put("10101", "ASSET_DICT_ASSET_CLASSIFY_GD");
		classifyTableMap.put("JG", "ASSET_DICT_ASSET_CLASSIFY_JG");
		classifyTableMap.put("102", "ASSET_DICT_ASSET_CLASSIFY_JG");
		classifyTableMap.put("GY", "ASSET_DICT_ASSET_CLASSIFY_GY");
		classifyTableMap.put("103", "ASSET_DICT_ASSET_CLASSIFY_GY");
		classifyTableMap.put("DZ", "ASSET_DICT_ASSET_CLASSIFY_DZ");
		classifyTableMap.put("201", "ASSET_DICT_ASSET_CLASSIFY_DZ");
		classifyTableMap.put("DEFAULT", "ASSET_DICT_ASSET_CLASSIFY_GD");
		
		assetTypeMap.put("10101", "10101");//行政事业单位资产
		assetTypeMap.put("10102", "101");//行政事业单位占有固定资产
		assetTypeMap.put("101", "101");//行政事业单位委托代理资产
		assetTypeMap.put("102", "102");//经管资产
		assetTypeMap.put("103", "103");//企业国有权益
		assetTypeMap.put("201", "201");//低值易耗
	}
	
	/**
	 * 
	 * <p>函数名称：    getBizTypeByAssetTypeCode    </p>
	 * <p>功能说明： 根据assetTypeCode 值，返回其对应大类简称 固定资产 默认为null
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetTypeCode
	 * @param bizType
	 * @return JG_BIZTYPE
	 *
	 * @date   创建时间：2017年6月1日
	 * @author 作者：xds
	 */
	public static String getBizTypeByAssetTypeCode(String assetTypeCode,String bizType){		
		String prefix = getBizTypeByAssetTypeCode(assetTypeCode);
		if("".equals(prefix)){
			return bizType;
		}
		return StringUtilsExt.join(new String[]{prefix, bizType}, "_");
	}
	
	/**
	 * 
	 * <p>函数名称：    getBizTypeByAssetTypeCode    </p>
	 * <p>功能说明： 根据assetTypeCode 值，返回其对应大类简称 固定资产 默认为null
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetTypeCode
	 * @return 
	 *
	 * @date   创建时间：2017年6月1日
	 * @author 作者：xds
	 */
	public static String getBizTypeByAssetTypeCode(String assetTypeCode){
		if(StringUtilsExt.isEmpty(assetTypeCode)){
			return "";
		}
		String prefix = "";
		for(String key : SYSTEM_PREFIX.keySet()){
			if(assetTypeCode.startsWith(key)){
				prefix = SYSTEM_PREFIX.get(key);
				break;
			}
		}
		return prefix;
	}
	
	/**
	 * 
	 * <p>函数名称：getClassifyTable        </p>
	 * <p>功能说明：根据类型获取
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetTypeCode
	 * @return
	 *
	 * @date   创建时间：2017年6月27日
	 * @author 作者：xds
	 */
	public static String getClassifyTable(String assetTypeCode){
		return MapUtilsExt.getString(classifyTableMap, assetTypeCode, classifyTableMap.get("DEFAULT"));
	}
	
	/**
	 * 
	 * <p>函数名称：getAssetTypePrefix        </p>
	 * <p>功能说明：根据大类编码 获取 其大类类型前缀
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param assetTypeCode
	 * @return
	 *
	 * @date   创建时间：2017年8月22日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getAssetTypePrefix(String assetTypeCode){
		if(StringUtilsExt.isEmpty(assetTypeCode)){
			return "";
		}
		String prefix = "";
		for(String key : assetTypeMap.keySet()){
			if(assetTypeCode.startsWith(key)){
				prefix = assetTypeMap.get(key);
				break;
			}
		}
		return prefix;
	}
	
}
