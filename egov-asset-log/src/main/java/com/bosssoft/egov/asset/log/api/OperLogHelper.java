package com.bosssoft.egov.asset.log.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.log.LogConfigurationConstants;
import com.bosssoft.egov.asset.log.LogType;
import com.bosssoft.egov.asset.log.impl.entity.AssetOperLog;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.cache.api.Cache;

/** 
*
* @ClassName   类名：OperLogHelper 
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
public class OperLogHelper {
	
    private static Logger logger = LoggerFactory.getLogger(OperLogHelper.class);

	private static final String CACHE_NAME = "__EGOV_ASSET_OPER_LOG__";
		
	private static OperLogHelper INSTANCE;
	
	//可重入读写锁
	private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock(false);
    //写锁 
	private static ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	//读锁 可同时读 存在写锁时 会等待写锁 释放
	private static ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	
	private Cache cache;
	
	private OperLogHelper(){	
		cache = CacheFactory.getInstance().findCache(CACHE_NAME);	    
	}
	
	public static OperLogHelper getInstance() {
		if (INSTANCE == null) {
			synchronized(OperLogHelper.class){
			   if(INSTANCE == null){
				   INSTANCE = new OperLogHelper();
			   }
			}
		}
		return INSTANCE;
	}
	
	public static void saveOperLog(AssetOperLog operLog){
		if(LogConfigurationConstants.logEnable()){
			logger.debug("缓存日志：" + operLog.toString());
			getInstance().saveLog(operLog,LogType.OPERLOG);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void saveLog(AssetOperLog operLog,LogType logType){
		writeLock.lock();
		try{
			Serializable list = new ArrayList<Serializable>();
		    Serializable exists = cache.get(buildKey(logType));
		    if(exists != null){
		    	list = exists;
			}
			((ArrayList<Serializable>) list).add(operLog);	    
		    cache.put(buildKey(logType), list);
	    } finally {
	    	writeLock.unlock();
	    }
	}
	
	@SuppressWarnings("unchecked")
	private List<AssetOperLog> getLogList(LogType logType){
		readLock.lock();
        try {
		  Serializable list = cache.get(buildKey(logType));
		  return (List<AssetOperLog>) (list != null ? new ArrayList<AssetOperLog>((List<AssetOperLog>)list) : new ArrayList<AssetOperLog>());
		} finally{
			readLock.unlock();
		}		
	}
	
	public static List<AssetOperLog> getOperLogList(){
		return getInstance().getLogList(LogType.OPERLOG);
	}
	
	public static void clearOperLog(){
		getInstance().clearLog(LogType.OPERLOG);
	}
	
	/**
	 * 
	 * <p>函数名称： clearOtherLog       </p>
	 * <p>功能说明：清理其他业务类型日志
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2016年12月16日
	 * @author 作者：xds
	 */
	public static void clearOtherLog(){
		getInstance().clearLog(LogType.OTHER);
	}
	
	/**
	 * 
	 * <p>函数名称： clearBizLog       </p>
	 * <p>功能说明：清理业务日志
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2016年12月16日
	 * @author 作者：xds
	 */
	public static void clearBizLog(){
		getInstance().clearLog(LogType.BIZLOG);
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
	private void clearLog(LogType logType){
		writeLock.lock();
		try{
	      //移除日志
		  logger.debug("清除日志：" + buildKey(logType));
		  cache.remove(buildKey(logType));
		} finally{
			writeLock.unlock();
		}
	}
	
	private String buildKey(LogType logType){
		return CACHE_NAME + AppContext.getAppContext().getEnvironment().getProperty("appId.num") + "_" + logType.getCode();
	}
}
