package com.bosssoft.egov.asset.activiti.activitiKit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @ClassName 类名：ActivitiToolsService
 * @Description 功能说明：
 *              <p>
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年6月10日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2017年6月10日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
@Service
public class StartQuartz {
	@Autowired
	private AlarmToolsService alarmToolsService;

	public void createAlarmRecord() {
		alarmToolsService.createAlarmRecord();
	}

}
