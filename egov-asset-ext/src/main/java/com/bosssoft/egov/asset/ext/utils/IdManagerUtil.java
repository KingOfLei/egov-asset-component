/*******************************************************************************
 * 福建博思软件1997-2017 版权所有
 * 
 * Auto generated by Bosssoft Studio version 1.0 beta
 * 2017年12月29日上午10:31:35
 *******************************************************************************/
package com.bosssoft.egov.asset.ext.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;

import com.bosssoft.egov.asset.common.idgenerator.IdWorker;

/**
 * @ClassName	类名：IdManagerUtil.java
 * @Description	功能说明：ID生成器
 * <pre>
 * @date		创建日期：2017年12月29日
 * @author		创建人：chenzhibin
 * @version		版本号：V1.0
 * <pre>
 *-------------------修订记录---------------------
 */
public class IdManagerUtil {
	@Value("${idWork.appId.aims}")
	private String idWorkAppId;
	
	@Value("${idWork.workerId.aims}")
	private String idWorkWorkerId;
	
	private static IdWorker worker;
	
	private static IdManagerUtil instance;
	
	private IdManagerUtil(){
		long workerId = NumberUtils.toLong(idWorkWorkerId);
    	long regionId = NumberUtils.toLong(idWorkAppId);
		worker = new IdWorker(workerId, regionId);
	}
	
	public static IdManagerUtil getInstance(){
		if(instance == null){
			instance = new IdManagerUtil();
		}
		return instance;
	}
	
	/**
	 * 生成ID
	 * <p>功能说明：生成ID
	 * </p>
	 * <p>参数说明：
	 * @return
	 * </p>
	 *
	 * @date	创建时间：2017年12月19日
	 * @author	作者：chenzhibin
	 */
	public synchronized Long newId(){
		return worker.generate();
	}
}
