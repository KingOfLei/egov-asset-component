package com.bosssoft.egov.asset.bizlog.service;
/**
 * 福建博思软件 1997-2016 版权所有
 * Auto generated by Bosssoft Studio version 1.0 beta
 * Tue Dec 27 22:57:52 CST 2016
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.bizlog.api.BizLogHelper;
import com.bosssoft.egov.asset.bizlog.entity.AssetBizLog;
import com.bosssoft.egov.asset.bizlog.mapper.AssetBizLogMapper;
import com.bosssoft.egov.asset.log.LogConfigurationConstants;
import com.bosssoft.egov.asset.log.api.OperLogHelper;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;


/**
 * 类说明: AssetBizLogService接口实现类. 
 *
 * 类用途：
 *
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-12-27   Administrator　　　新建
 * </pre>
 */
@Service("assetBizLogService")
@Lazy(false)
public class AssetBizLogServiceImpl implements AssetBizLogService {

	private static Logger logger = LoggerFactory.getLogger(AssetBizLogServiceImpl.class);

	// TODO 添加需要的Mapper
	@Autowired
	private AssetBizLogMapper assetBizLogMapper;
	
	/**
	 *
	 * @param assetBizLog
	 */
	public void addAssetBizLog(AssetBizLog assetBizLog)  {
		assetBizLogMapper.insertSelective(assetBizLog);
	}

	/**
	 *
	 * @param assetBizLog
	 */
	public void delAssetBizLog(AssetBizLog assetBizLog)  {
		assetBizLogMapper.deleteByPrimaryKey(assetBizLog);
	}

	/**
	 *
	 * @param assetBizLog
	 */
	public void updateAssetBizLog(AssetBizLog assetBizLog)  {
		assetBizLogMapper.updateByPrimaryKey(assetBizLog);
	}

	/**
	 *
	 * @param assetBizLog
	 * @retrun
	 */
	public List<AssetBizLog> getAssetBizLogList(AssetBizLog bizLog)  {
		return assetBizLogMapper.select(bizLog);
	}

	/**
	 *
	 * @param searcher
	 * @param page
	 * @retrun
	 */
	public Page<AssetBizLog> queryAssetBizLogPage(Searcher searcher, Page<AssetBizLog> page)  {
		return assetBizLogMapper.queryAssetBizLogPage(searcher, page);
	}

	@Override
	public AssetBizLog getAssetBizLogById(Long bizLogId) {
		// TODO Auto-generated method stub
		return assetBizLogMapper.selectByPrimaryKey(bizLogId);
	}

	@Override
	public AssetBizLog getAssetBizLogById(AssetBizLog assetBizLog) {
		// TODO Auto-generated method stub
		return assetBizLogMapper.selectByPrimaryKey(assetBizLog);
	}

	@Value("${" + LogConfigurationConstants.BIZ_LOG_TIMING + "}")
	private String cron;

	@Scheduled(cron="${" + LogConfigurationConstants.BIZ_LOG_TIMING + "}")
	@Transactional(rollbackFor = Throwable.class)
	public void saveBizLog(){
		if(LogConfigurationConstants.bizLogEnable()){ 
			List<AssetBizLog> operLogs = new ArrayList<AssetBizLog>();
			// 线程安全
			synchronized (OperLogHelper.class) {
				// 获取缓存中的信息
				operLogs = BizLogHelper.getBizLogList();
				if (logger.isDebugEnabled()) {
					logger.debug("批量保存业务操作日志");
					if (operLogs != null) {
						logger.debug("日志条数{}", operLogs.size());
					}
				}
				if (operLogs != null) {
					for (AssetBizLog log : operLogs) {
						assetBizLogMapper.insertSelective(log);
					}
				}
				BizLogHelper.clearBizLog();
			}

		} else {
//			List<AssetBizLog> operLogs = BizLogHelper.getBizLogList();
//			if (logger.isDebugEnabled()) {
//				//logger.debug("批量保存业务操作日志未开启，自动清除日志信息");
//				if (operLogs != null) {
//					//logger.debug("日志条数{}", operLogs.size());
//				}
//			}
//			BizLogHelper.clearBizLog();			
		}
	}

	@Override
	public List<AssetBizLog> getAssetBizLogListTop10(AssetBizLog assetBizLog) {

		return null;
	}

	@Override
	public List<Map<String, Object>> queryPortalBizLogList(
			AssetBizLog bizLog, int limit) {
		return assetBizLogMapper.queryPortalBizLogList(bizLog,limit);
	}
	
}