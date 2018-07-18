package com.bosssoft.egov.asset.dispatch.service.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bosssoft.egov.asset.dispatch.entity.AssetDispatchLog;
import com.bosssoft.egov.asset.dispatch.service.AssetDispatchLogService;
import com.bosssoft.egov.asset.guava.EventBusBase;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/** 
 *
 * @ClassName   类名：ActivitiLogEventListener 
 * @Description 功能说明：爬虫日志监听类
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月12日
 * @author      创建人：Administrator
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年8月8日   金标   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
/**
 * @author 金标
 *
 */
@Component
@Lazy(false)
public class ActivitiLogEventListener extends EventBusBase{
	private static Logger logger = LoggerFactory.getLogger(ActivitiLogEventListener.class);

	@Autowired
	private AssetDispatchLogService assetDispatchLogService;
	
	@Subscribe
	@AllowConcurrentEvents
	public void quartzTaskLog(AssetDispatchLog log) {
		
//		AssetDispatchLog queryLog = assetDispatchLogService.queryOneIsExist(log.getId());
//		if(queryLog == null){
			assetDispatchLogService.addAssetDispatchLog(log);
//		}else{
//			queryLog.setEndTime(log.getEndTime());
//			queryLog.setExecuteStatus(log.getStartTime());
//			queryLog.setReamrk(log.getReamrk());
//			assetDispatchLogService.updateAssetDispatchLog(queryLog);
//		}
		
    }
	
}
