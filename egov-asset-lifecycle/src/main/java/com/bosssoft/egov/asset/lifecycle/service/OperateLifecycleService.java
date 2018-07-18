package com.bosssoft.egov.asset.lifecycle.service;

import java.util.List;

/**
 *
 * @ClassName 类名：OperateLifecycleService
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
public interface OperateLifecycleService {
	public void doWriteAimsCard6Life(Long orgId, String billType, 
			Integer isAudit,List<Long> billIdsList) ;

	public void doWriteAimsCard6LifeLog(Long orgId, String billType, Integer isAudit, Long billId) ;
}
