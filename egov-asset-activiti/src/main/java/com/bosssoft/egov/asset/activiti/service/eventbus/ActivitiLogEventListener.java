package com.bosssoft.egov.asset.activiti.service.eventbus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.bosssoft.egov.asset.activiti.entity.AssetBusHistory;
import com.bosssoft.egov.asset.activiti.entity.AssetProcessRemark;
import com.bosssoft.egov.asset.activiti.service.AssetBusHistoryService;
import com.bosssoft.egov.asset.activiti.service.AssetProcessRemarkService;
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
	private AssetProcessRemarkService assetProcessRemarkService;
	@Autowired
	private AssetBusHistoryService assetBusHistoryService;
	
	@Subscribe
	@AllowConcurrentEvents
	public void ActivitiLog(AssetProcessRemark remarkLog) {
//		logger.debug("工作流批注表异步存储！实体类为："+remarkLog.toString());
//		remarkLog.setAppId(AppContext.getAppContext().getAppConfiguration().getAppId());
//		remarkLog.setAppName(AppContext.getAppContext().getAppConfiguration().getAppName());
//		assetProcessRemarkService.addAssetProcessRemark(remarkLog);
    }
	
	@Subscribe
	@AllowConcurrentEvents
	public void ActivitiHistoryLog(AssetBusHistory history) {
//		logger.debug("业务历史记录表异步更新存储！实体类为："+history.toString());
//		//删除存在记录
//		assetBusHistoryService.deleteByBusIdUserCode(history);
//		//保留最新记录
//		assetBusHistoryService.addAssetBusHistory(history);
    }
}
