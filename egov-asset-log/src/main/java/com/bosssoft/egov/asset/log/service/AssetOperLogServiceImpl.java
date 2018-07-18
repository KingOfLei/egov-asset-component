package com.bosssoft.egov.asset.log.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.log.LogConfigurationConstants;
import com.bosssoft.egov.asset.log.api.OperLogHelper;
import com.bosssoft.egov.asset.log.impl.entity.AssetOperLog;
import com.bosssoft.egov.asset.log.impl.mapper.AssetOperLogMapper;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
*
* @ClassName   类名：AssetOperLogServiceImpl 
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
@Service("assetOperLogService")
@Lazy(false)
public class AssetOperLogServiceImpl implements AssetOperLogService {
	
	private static Logger logger = LoggerFactory.getLogger(AssetOperLogServiceImpl.class);

	@Autowired
    private AssetOperLogMapper operLogMapper;
	@Override
	public void addAssetOperLog(AssetOperLog assetOperLog) {
		operLogMapper.insert(assetOperLog);
	}

	@Override
	public List<AssetOperLog> getAssetOperLogList(AssetOperLog assetOperLog) {
		return operLogMapper.select(assetOperLog);
	}
	

	@Override
	public List<AssetOperLog> getAllAssetOperLogList() {
		return operLogMapper.selectAll();
	}

	@Override
	public Page<AssetOperLog> queryAssetOperLogPage(Searcher searcher,
			Page<AssetOperLog> page) {
		return operLogMapper.queryAssetOperLogPage(searcher, page);
	}
	
	@Value("${" + LogConfigurationConstants.OPER_LOG_TIMING + "}")
	private String cron;

	@Scheduled(cron="${" + LogConfigurationConstants.OPER_LOG_TIMING + "}")
//	@Transactional(rollbackFor = Throwable.class)
	public void saveOperLog(){
		if(LogConfigurationConstants.logEnable()){ 
			List<AssetOperLog> operLogs = new ArrayList<AssetOperLog>();
			// 线程安全
			synchronized (OperLogHelper.class) {
				// 获取缓存中的信息
				operLogs = OperLogHelper.getOperLogList();
				if (logger.isDebugEnabled()) {
					logger.debug("批量保存操作日志");
					if (operLogs != null) {
						logger.debug("日志条数{}", operLogs.size());
					}
				}
				
				if (operLogs != null) {
					for (AssetOperLog log : operLogs) {
						operLogMapper.insertSelective(log);
					}
				}
				OperLogHelper.clearOperLog();
			}

		} else {
//			List<AssetOperLog> operLogs = OperLogHelper.getOperLogList();
//			if (logger.isDebugEnabled()) {
//				logger.debug("批量保存操作日志未开启，自动清除日志信息");
//				if (operLogs != null) {
//					logger.debug("日志条数{}", operLogs.size());
//				}
//			}
//			OperLogHelper.clearOperLog();			
		}
	}
}
