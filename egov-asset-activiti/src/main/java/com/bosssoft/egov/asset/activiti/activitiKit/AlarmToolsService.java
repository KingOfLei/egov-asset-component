package com.bosssoft.egov.asset.activiti.activitiKit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.activiti.ActivitiIdGen;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmMonitor;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmMonitorConfig;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmMonitorData;
import com.bosssoft.egov.asset.activiti.service.ActivitiAlarmMonitorConfigService;
import com.bosssoft.egov.asset.activiti.service.ActivitiAlarmMonitorDataService;
import com.bosssoft.egov.asset.activiti.service.ActivitiAlarmMonitorService;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

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
public class AlarmToolsService {
	@Autowired
	private ActivitiAlarmMonitorConfigService activitiAlarmMonitorConfigService;
	@Autowired
	private ActivitiAlarmMonitorDataService activitiAlarmMonitorDataService;
	@Autowired
	private ActivitiAlarmMonitorService activitiAlarmMonitorService;
	
	public void createAlarmRecord(){
		this.createAlarmRecordInner();
	}
	
	private void createAlarmRecordInner() {
		// 先删除全部信息
		activitiAlarmMonitorDataService.delActivitiAlarmMonitorDataAll();
		// 取出配置信息
		ActivitiAlarmMonitorConfig activitiAlarmMonitorConfig = new ActivitiAlarmMonitorConfig();
		List<ActivitiAlarmMonitorConfig> activitiAlarmConfigList = activitiAlarmMonitorConfigService.getActivitiAlarmMonitorConfigList(activitiAlarmMonitorConfig);
		if (activitiAlarmConfigList != null && activitiAlarmConfigList.size() > 0) {
			for (ActivitiAlarmMonitorConfig activitiAlarmMonitorConfig2 : activitiAlarmConfigList) {
				ActivitiAlarmMonitor activitiAlarmMonitor = new ActivitiAlarmMonitor();
				activitiAlarmMonitor.setIsalarm(1l);// 取出有预警记录的
				activitiAlarmMonitor.setIsComplete(0l);// 取出未完成的
				activitiAlarmMonitor.setBusType(activitiAlarmMonitorConfig2.getBusType());// 类型是配置中的才进行监控
				activitiAlarmMonitor.setZone(activitiAlarmMonitorConfig2.getAlarmZone());// 区域也要是监控中的区域才进行监控
				String sqlOrign = activitiAlarmMonitorConfig2.getSql();
				String bizCode = activitiAlarmMonitorConfig2.getBizCode();// 用于页面跳转参数使用
				// 取出工作流预警信息
				List<ActivitiAlarmMonitor> activitiAlarmList = activitiAlarmMonitorService.getActivitiAlarmMonitorList(activitiAlarmMonitor);
				if (activitiAlarmList != null && activitiAlarmList.size() > 0) {
					for (ActivitiAlarmMonitor activitiAlarmMonitor2 : activitiAlarmList) {
						String processName = activitiAlarmMonitor2.getProcessDesc();
						String processCreateTime = activitiAlarmMonitor2.getCreateTime();
						String roleCode = activitiAlarmMonitor2.getAuthrolecode();
						String receiveTime = activitiAlarmMonitor2.getReceivetime();
						int checkDay = NumberUtilsExt.toInt(Long.toString(activitiAlarmMonitorConfig2.getExpiredDay()));
						String formType = activitiAlarmMonitor2.getFormtype();
						if(StringUtilsExt.isNotEmpty(formType)){
							bizCode = formType;
						}
						int intevalDay = this.workDayCalculate(receiveTime, DateUtilsExt.getNowDateTime());
//						int intevalDay = WorkdayUtils.getWorkDay(receiveTime);
						if (intevalDay >= checkDay) {
							// 生成业务预警信息
							// 1.产生数据
							String busId = activitiAlarmMonitor2.getBusId();
							Map<String, String> map = new HashMap<String, String>();
							map.put("busId", busId);//业务id，用于单据查找
							map.put("roleCode", roleCode);//角色信息，赋值到语句中
							map.put("processCreateTime", processCreateTime);//流程创建时间
							map.put("processName", processName);//流程名称
							map.put("bizCode", bizCode);//跳转参数
							map.put("intevalDay", StringUtilsExt.convertNullToString(intevalDay));//间隔时间
							String sql = sqlOrign;
							sql = StringUtilsExt.environmentSubstitute(sql, map);//语句替换
							List<ActivitiAlarmMonitorData> data = activitiAlarmMonitorDataService.queryBySql(sql);
							// 2.入库
							for (ActivitiAlarmMonitorData activitiAlarmMonitorData : data) {
								activitiAlarmMonitorData.setId(StringUtilsExt.convertNullToString(ActivitiIdGen.newWKID()));
								activitiAlarmMonitorDataService.addActivitiAlarmMonitorData(activitiAlarmMonitorData);
							}
						}
					}
				}
			}
		}
	}
	
	public void createAlarmRecordInner1(){
		activitiAlarmMonitorDataService.addAlarmPro();//调用存储过程
	}
//	public void createAlarmRecordInner1(){
//		//将未完成的数据删除后，重新生成
//		activitiAlarmMonitorDataService.delActivitiAlarmMonitorDataByComplete(0);
//		
//		ActivitiAlarmMonitorConfig activitiAlarmMonitorConfig = new ActivitiAlarmMonitorConfig();
//		List<ActivitiAlarmMonitorConfig> activitiAlarmConfigList = activitiAlarmMonitorConfigService.getActivitiAlarmMonitorConfigList(activitiAlarmMonitorConfig);
//		for (ActivitiAlarmMonitorConfig activitiAlarmMonitorConfig2 : activitiAlarmConfigList) {
//			//执行语句
//			String sql = activitiAlarmMonitorConfig2.getSql();
//			int expiredDay = NumberUtilsExt.toInt(Long.toString(activitiAlarmMonitorConfig2.getExpiredDay()));
//			int checkDay = NumberUtilsExt.toInt(Long.toString(activitiAlarmMonitorConfig2.getAlarmDay()));
//			String busDetail = activitiAlarmMonitorConfig2.getBusType();
//			String zone = activitiAlarmMonitorConfig2.getAlarmZone();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("zoneFlag", zone);//统计某种区域的监控信息
//			map.put("intevalDay", checkDay);//快到期
//			map.put("expiredDay", expiredDay);//业务类型
//			map.put("busType", sql);//业务大类
//			map.put("busDetail", busDetail);//业务类型
//			if (StringUtilsExt.isNotBlank(sql)) {
//				activitiAlarmMonitorDataService.addAlarmPro();//调用存储过程
//			}
//		}
//	}
//	public void createAlarmRecordInner1(){
//		//将未完成的数据删除后，重新生成
//		ActivitiAlarmMonitorData activitiAlarmMonitorData = new ActivitiAlarmMonitorData();
//		activitiAlarmMonitorData.setIsComplete(1);
//		activitiAlarmMonitorDataService.delActivitiAlarmMonitorData(activitiAlarmMonitorData );
//		
//		ActivitiAlarmMonitorConfig activitiAlarmMonitorConfig = new ActivitiAlarmMonitorConfig();
//		List<ActivitiAlarmMonitorConfig> activitiAlarmConfigList = activitiAlarmMonitorConfigService.getActivitiAlarmMonitorConfigList(activitiAlarmMonitorConfig);
//		for (ActivitiAlarmMonitorConfig activitiAlarmMonitorConfig2 : activitiAlarmConfigList) {
//			//执行语句
//			String sql = activitiAlarmMonitorConfig2.getSql();
//			int checkDay = NumberUtilsExt.toInt(Long.toString(activitiAlarmMonitorConfig2.getExpiredDay()));
//			String zone = activitiAlarmMonitorConfig2.getAlarmZone();
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("zone", zone);//统计某种区域的监控信息
//			map.put("inteval", StringUtilsExt.convertNullToString(checkDay));//延迟期限
//			String newsql = StringUtilsExt.environmentSubstitute(sql, map);// 语句替换
//			if (StringUtilsExt.isNotBlank(sql)) {
//				activitiAlarmMonitorDataService.insertBySql(newsql);
//			}
//		}
//	}
	
	private int workDayCalculate(String beginTime, String endTime){
		//计算总天数
		int totalDays = WorkdayUtils.getDays(beginTime, endTime);
		//需要扣除的工作日
		//日期换算
		Date beginDate = DateUtilsExt.parseDate(beginTime, "yyyyMMddHHmmssSSS");
		Date endDate = DateUtilsExt.parseDate(endTime, "yyyyMMddHHmmssSSS");
		
		String begin = DateUtilsExt.formatDate(beginDate, "yyyyMMdd");
		String end = DateUtilsExt.formatDate(endDate, "yyyyMMdd");
		int notWorkDay = activitiAlarmMonitorConfigService.queryNotWorkDay(begin, end);
		int workDay = totalDays - notWorkDay;
		
		if(workDay > 0){
			return workDay;
		}
		return 0;
	}
	
	//类型转换
	public String typeTransform(String oriType){
		//1.固定写死判断
		String targetType = "";
		if(true){
			
		}
		return targetType;
		//2.读取数据库配置
	}
}
