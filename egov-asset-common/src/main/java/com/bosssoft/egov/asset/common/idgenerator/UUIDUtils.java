package com.bosssoft.egov.asset.common.idgenerator;

import java.util.UUID;

/** 
*
* @ClassName   类名：UUIDUtils 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年10月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年10月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class UUIDUtils {
	/**
	 * 
	 * <p>函数名称： getRandomUUID       </p>
	 * <p>功能说明： 获取32位ID 小写
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月20日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getRandomUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
	}
	
	/**
	 * 
	 * <p>函数名称：getRandomUUIDToUpperCase        </p>
	 * <p>功能说明：32位guid 大写
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月20日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getRandomUUIDToUpperCase(){
		return getRandomUUID().toUpperCase();
	}
}
