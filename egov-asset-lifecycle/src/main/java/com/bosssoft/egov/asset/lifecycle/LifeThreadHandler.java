package com.bosssoft.egov.asset.lifecycle;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.lifecycle.entity.AssetLifeLog;
import com.bosssoft.egov.asset.lifecycle.service.AssetLifeLogService;
import com.bosssoft.egov.asset.lifecycle.service.OperateLifecycleService;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;

/**
 *
 * @ClassName 类名：LifeThreadHandler
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年12月12日
 * @author 创建人：wujian
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2017年12月12日 wujian 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class LifeThreadHandler implements Runnable {

	private static final Log logger = LogFactory.getLog("org.springframework.test.web.servlet.result");

	private Long orgId;

	private String billType;

	private List<Long> billIdsList;

	private int isAudit;

	public LifeThreadHandler(Long orgId, String billType, List<Long> billIdsList, int isAudit) {
		this.orgId = orgId;
		this.billType = billType;
		this.billIdsList = billIdsList;
		this.isAudit = isAudit;
	}

	@Override
	public void run() {
		synchronized (this) {
			
			AssetLifeLogService assetLifeLogService = AppContext.getAppContext().getBeanContext()
					.getBean(AssetLifeLogService.class);

			AssetLifeLog assetLifeLog = getAssetLifeLog(orgId, billType, isAudit, billIdsList);
			try {
				// logger.info(Thread.currentThread().getName() +
				// DateUtilsExt.getNowDateTime());
				// 生命周期处理
				logger.info(Thread.currentThread().getName() + billType + "-" + isAudit + "--开始--"
						+ DateUtilsExt.getNowDateTime());
				doWriteAimsCard6Life(orgId, billType, isAudit, billIdsList);
				// 记录日志
				assetLifeLog.setStatus(1);
				assetLifeLog.setMsg("生命周期写入成功！");
			} catch (Exception e) {
				e.printStackTrace();

				String errorMsg = e.toString() + "\r\n";
				StackTraceElement[] trace = e.getStackTrace();
				for (StackTraceElement s : trace) {
					errorMsg += "\tat " + s + "\r\n";
				}

				// 记录日志
				assetLifeLog.setStatus(0);
				assetLifeLog.setMsg(errorMsg);

			} finally {
				logger.info(Thread.currentThread().getName() + billType + "-" + isAudit + "--结束--"
						+ DateUtilsExt.getNowDateTime());
				if (assetLifeLog.getStatus()==1&& LifecycleLogConfigurationConstants.operLifecycleResultLogEnable()) {
					assetLifeLogService.addAssetLifeLog(assetLifeLog);
				}
				if (assetLifeLog.getStatus()==0) {
					assetLifeLogService.addAssetLifeLog(assetLifeLog);
				}
			}
		}
	}

	public static AssetLifeLog getAssetLifeLog(Long orgId, String billType, int isAudit,
			List<Long> billIdsList) {
		AssetLifeLog assetLifeLog = new AssetLifeLog();
		assetLifeLog.setId(ComponetIdGen.newWKID());

		assetLifeLog.setBusType(billType);
		assetLifeLog.setOrgId(orgId);
		assetLifeLog.setIsAudit(isAudit);
		assetLifeLog.setCreateDate(DateUtilsExt.getCurrentDate());
		assetLifeLog.setCreateTime(DateUtilsExt.getNowDateTime());

		StringBuffer buffer = new StringBuffer();
		int i = 0;
		for (Long billId : billIdsList) {
			buffer.append(billId);
			if (i != billIdsList.size() - 1) {
				buffer.append(",");
			}
		}
		assetLifeLog.setBusId(buffer.toString());
		return assetLifeLog;
	}

	private void doWriteAimsCard6Life(Long orgId, String billType, int isAudit, List<Long> billIdsList) {

		OperateLifecycleService operateLifecycleService = AppContext.getAppContext().getBeanContext()
				.getBean(OperateLifecycleService.class);
		operateLifecycleService.doWriteAimsCard6Life(orgId, billType, isAudit, billIdsList);
	}

}
