package com.bosssoft.egov.asset.dictionary.api;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：AimsDictHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class AimsDictHelper {

	/**
	 * 
	 * <p>函数名称：buildKey        </p>
	 * <p>功能说明：创建key 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param bizType
	 * @param key
	 * @return
	 *
	 * @date   创建时间：2016年11月19日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String buildKey(String bizType, String... key){
		return bizType + "::" + StringUtilsExt.join(key, "::");
	}
	
	public static String buildKeyByInd(String... key){
		return buildKey("_ASSET_AIMS_DICT_IND_",key);
	}
	
	public static String buildKeyByArea(String... key){
		return buildKey("_ASSET_AIMS_DICT_AREA_",key);
	}
	
	public static String buildKeyByFinan(String... key){
		return buildKey("_ASSET_AIMS_DICT_FINANCESRELATION_",key);
	}

}
