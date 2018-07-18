package com.bosssoft.egov.asset.bizlog.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.bizlog.entity.ApiBizLog;
import com.bosssoft.egov.asset.bizlog.entity.AssetBizLog;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.log.IdGen;
import com.bosssoft.egov.asset.log.LogConfigurationConstants;
import com.bosssoft.egov.asset.log.api.OperLogHelper;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：BizLogHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月27日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月27日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class BizLogHelper {
    private static Logger logger = LoggerFactory.getLogger(BizLogHelper.class);

    private static final String CACHE_NAME = "__EGOV_ASSET_BIZ_LOG__";
	
	private static BizLogHelper INSTANCE;
	
	//可重入读写锁
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    //写锁 
	private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	//读锁 可同时读 存在写锁时 会等待写锁 释放
	private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	
	private Cache cache;
	
	private BizLogHelper(){		
		//cache 名称 应该加上 本机器及本应用的标识为依据
		cache = CacheFactory.getInstance().findCache(CACHE_NAME);	    
	}
	
	public static BizLogHelper getInstance() {
		if (INSTANCE == null) {
			synchronized(OperLogHelper.class){
			   if(INSTANCE == null){
				   INSTANCE = new BizLogHelper();
			   }
			}
		}
		return INSTANCE;
	}
	
	public static void saveBizLog(AssetBizLog bizLog){
		if(LogConfigurationConstants.bizLogEnable()){
			logger.debug("缓存日志：" + bizLog.toString());
			getInstance().saveLog(bizLog);
		}
	}
	
	/**
	 * 
	 * <p>函数名称：    saveBizLog    </p>
	 * <p>功能说明： 供业务系统调用
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param log
	 *
	 * @date   创建时间：2016年12月27日
	 * @author 作者：xds
	 */
	public static void saveBizLog(ApiBizLog log){
		if(LogConfigurationConstants.bizLogEnable()){
			User user = AppContext.getAppContext().getSessionUser();
			WebApplicationContext context = WebApplicationContext.getContext();
			AssetBizLog bizLog = BeanUtilsExt.copyToNewBean(log, AssetBizLog.class);
			bizLog.setAppId(context.getAppID());
			bizLog.setId(IdGen.newWKID());
			bizLog.setUserId(user.getUserId());
			bizLog.setUserCode(user.getUserCode());
			bizLog.setUserName(user.getUserName());
			bizLog.setOrgCode(user.getOrgCode());
			bizLog.setOrgName(user.getOrgName());
			bizLog.setOrgId(user.getOrgLongId());
			bizLog.setRgnId(user.getRgnId());
			bizLog.setRgnCode(user.getRgnCode());
			bizLog.setRgnName(user.getRgnName());
			bizLog.setOperDate(DateUtilsExt.getCurrentDate());
			bizLog.setCreateTime(DateUtilsExt.getNowDateTime());
			logger.debug("缓存业务日志：" + bizLog.toString());
			getInstance().saveLog(bizLog);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void saveLog(AssetBizLog operLog){
		writeLock.lock();
		try{
			Serializable list = new ArrayList<Serializable>();
		    Serializable exists = cache.get(buildKey());
		    if(exists != null){
		    	list = exists;
			}
			((ArrayList<Serializable>) list).add(operLog);	    
		    cache.put(buildKey(), list);
	    } finally{
	    	writeLock.unlock();
	    }
	}
	
	@SuppressWarnings("unchecked")
	private List<AssetBizLog> getLogList(){
	    Serializable list = getInstance().cache.get(buildKey());
		return (list != null ? new ArrayList<AssetBizLog>((List<AssetBizLog>)list) : new ArrayList<AssetBizLog>());		
	}
	
	public static List<AssetBizLog> getBizLogList(){
		readLock.lock();
		try{
		    return getInstance().getLogList();
		} finally{
			readLock.unlock();
		}
	}
	
	public static void clearBizLog(){
		//清空 
		getInstance().clearLog();
	}
	
	
	/**
	 * 
	 * <p>函数名称： clearLog       </p>
	 * <p>功能说明： 按类型清理日志信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param logType
	 *
	 * @date   创建时间：2016年12月16日
	 * @author 作者：xds
	 */
	private void clearLog(){
		readLock.lock();
		try{
		  //移除日志
		  logger.debug("清除日志：" + buildKey());
		  cache.remove(buildKey());
		} finally{
			readLock.unlock();
		}
	}
	
	private String buildKey(){
		return CACHE_NAME + AppContext.getAppContext().getEnvironment().getProperty("appId.num");
	}
}
