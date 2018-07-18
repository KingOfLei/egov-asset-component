package com.bosssoft.egov.asset.activiti.activitiKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.activiti.ActivitiIdGen;
import com.bosssoft.egov.asset.activiti.entity.ActivitiAlarmMonitor;
import com.bosssoft.egov.asset.activiti.service.ActivitiAlarmMonitorDataService;
import com.bosssoft.egov.asset.activiti.service.ActivitiAlarmMonitorService;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/**
 *
 * @ClassName 类名：ActivitiToolsService
 * @Description 功能说明：
 *              <p>
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年2月10日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          修订记录*************************************
 * 
 *          2017年2月10日 jinbiao 创建该类功能。
 *
 *
 *          </p>
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class ActivitiAlarmService {
	private static Logger logger = LoggerFactory.getLogger(ActivitiAlarmService.class);
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private ActivitiAlarmMonitorService activitiAlarmMonitorService;
	@Autowired
	private ActivitiToolsService actTools;
	@Autowired
	private ActivitiAlarmMonitorDataService activitiAlarmMonitorDataService;


	@SuppressWarnings("deprecation")
	public void addAlarm(DelegateTask task, String zone, String processDesc, Long isAlarm) {
		ActivitiAlarmMonitor record = new ActivitiAlarmMonitor();
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return;
		}
//		String businessKey = e.getProcessBusinessKey();
		String busType = businessKey.split("\\.")[0];
		Task taskk = (Task) task;//强制转换，获取单据类型
		String formType = StringUtilsExt.convertNullToString(actTools.getFormType(taskk.getFormKey()));
		String busId = businessKey.split("\\.")[1];
		String taskDefKey = task.getTaskDefinitionKey();
		String pid = task.getProcessInstanceId();
		String prodefId = e.getProcessDefinitionId();
		Map<String, Object> startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "startKeyMap");
		String orgCode = (String) startKeyMap.get("orgCode");
		Integer busStatus = actTools.getFormKey(taskk.getFormKey());
		if(busStatus > 8500 && busStatus < 10000){//大于8500的时候，取跳转单位org
			//取出map中跳转单位的orgCode
			Map<String, Object> map =  (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "map");
			orgCode = (String) map.get("bill_org");
		}
		String nodeDesc = task.getDescription();
		String nodeName = task.getName();
		String roleCode = actTools.getCurrentRoleCode(task);
		String time = DateUtilsExt.getNowDateTime();
		record.setId(ActivitiIdGen.newWKID());
		record.setBusId(busId);
		record.setBusType(busType);
		record.setCreateTime(time);
		record.setIsComplete(0l);
		record.setOrgCode(orgCode);
		record.setPid(pid);
		record.setProdefId(prodefId);
		record.setProcessDesc(processDesc);
		record.setTaskdefkeybegin(taskDefKey);
		record.setZone(zone);
		record.setAuthrolecode(roleCode);
		record.setCurrenttaskdef(taskDefKey);
		record.setReceivetime(time);
		record.setDescription(nodeDesc);
		record.setTaskname(nodeName);
		record.setIsalarm(isAlarm);
		record.setFormtype(formType);
		
		activitiAlarmMonitorService.addActivitiAlarmMonitor(record);
	}
	
	@SuppressWarnings("deprecation")
	public void deleteAlarmByTask(DelegateTask task) {
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return;
		}
//		String businessKey = e.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		
		activitiAlarmMonitorService.deleteByBusIdNoPid(busId);
	}

	@SuppressWarnings("deprecation")
	public void deleteAlarmByExe(DelegateExecution execution) {
		String businessKey = execution.getBusinessKey();
		if(businessKey == null){
			return;
		}
		String pid = execution.getProcessInstanceId();
//		String businessKey = execution.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		
		activitiAlarmMonitorService.deleteByBusIdNoPid(busId);
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明： 边界结束调用
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param task
	 * @param zone
	 * @param processDesc
	 *
	 * @date   创建时间：2017年6月21日
	 * @author 作者：jinbiao
	 */
	@SuppressWarnings("deprecation")
	public void updateBorderEnd(DelegateTask task, String zone, String processDesc) {//当前区域的最后一个节点
		ActivitiAlarmMonitor record = new ActivitiAlarmMonitor();
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return;
		}
		Task taskk = (Task) task;//强制转换，获取单据类型
		String formType = StringUtilsExt.convertNullToString(actTools.getFormType(taskk.getFormKey()));
//		String businessKey = e.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		String busType = businessKey.split("\\.")[0];
		String taskDefKey = task.getTaskDefinitionKey();
		String pid = task.getProcessInstanceId();
		String roleCode = actTools.getCurrentRoleCode(task);
		String nodeName = task.getName();
		String nodeDesc = task.getDescription();
		//查找是否存在
		record.setBusId(busId);
		record.setTaskdefkeyend(taskDefKey);
		if (activitiAlarmMonitorService.hasRecord(busId, taskDefKey,pid)) {
			//存在：删除所有未结束的，更新之前残留记录，说明是退回回来的数据
			ActivitiAlarmMonitor delRecord = new ActivitiAlarmMonitor();
			delRecord.setBusId(busId);
			delRecord.setIsComplete(0l);
			//查找本区域是否有未结束的
			boolean b = activitiAlarmMonitorService.hasRecordNoComplete(busId, taskDefKey,pid, 0l);
			//方法1：删除退回之后的预警记录
//			activitiAlarmMonitorService.delActivitiAlarmMonitorByRecord(delRecord);
			//方法2：修改数据，不删除，complete改
			activitiAlarmMonitorService.updateCompleteByBusId(1l, busId,pid);
			//跟新退回后的区域，并置位未完成状态
			if(!b){
				//不存在，可能是退回来的，或者跳岗，将未完成的区域置位完成，更新完成时间
				activitiAlarmMonitorService.updateCompleteEndTimeByBusIdByIsComplete(1l, busId, pid, 0l, DateUtilsExt.getNowDateTime());
				activitiAlarmMonitorService.updateReceiveTimeCreateTimeCompleteByBusIdAndTaskdef(DateUtilsExt.getNowDateTime(),DateUtilsExt.getNowDateTime(), busId, taskDefKey, 0l,pid);
			}else{
				activitiAlarmMonitorService.updateReceiveTimeByBusIdAndTaskdef(DateUtilsExt.getNowDateTime(), busId, taskDefKey, 0l,pid);
			}
//			activitiAlarmMonitorService.updateReceiveTimeCreateTimeCompleteByBusIdAndTaskdef(DateUtilsExt.getNowDateTime(),DateUtilsExt.getNowDateTime(), busId, taskDefKey, 0l, pid);
//			activitiAlarmMonitorService.updateReceiveTimeByBusIdAndTaskdef(DateUtilsExt.getNowDateTime(), busId, taskDefKey, 0l, pid);
//			activitiAlarmMonitorService.updateTimeByBusIdAndTaskdef(DateUtilsExt.getNowDateTime(), busId, taskDefKey, 0l, pid);
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeTaskName(busId, nodeName, 0l, taskDefKey, roleCode,pid);
//			activitiAlarmMonitorService.updateCurrentTaskDefRoleCode(busId, 0l, taskDefKey, roleCode);
		}else{
			//不存在：说明是正向走过来的，将自己的taskdef更新进去，覆盖到该区域内
			activitiAlarmMonitorService.updateTaskDefByBusId(busId, 0l, task.getTaskDefinitionKey(),pid);
//			activitiAlarmMonitorService.updateTaskDefByBusId(busId, 0l, task.getTaskDefinitionKey(), pid);
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTime(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime(), nodeName, nodeDesc,pid);
//			activitiAlarmMonitorService.updateCurrentTaskDefRoleCode(busId, 0l, taskDefKey, roleCode);
			activitiAlarmMonitorDataService.updateRoleCodeBusId(roleCode, busId,pid);
		}
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("zoneFlag", zone);
		alarmMap.put("busType", busType);
		alarmMap.put("busId", busId);
		activitiAlarmMonitorService.dynamicAlarmPro(alarmMap );
	}
	

	@SuppressWarnings({ "deprecation", "unused" })
	public void updateBorderStart(DelegateTask task, String zone, String processDesc, Long isAlarm) {//夸区域后的第一个节点
		ActivitiAlarmMonitor record = new ActivitiAlarmMonitor();
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return ;
		}
		Task taskk = (Task) task;//强制转换，获取单据类型
		String formType = StringUtilsExt.convertNullToString(actTools.getFormType(taskk.getFormKey()));
//		String businessKey = e.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		String busType = businessKey.split("\\.")[0];
//		this.updateCompleteByExe(e);//更新之前的所有未完成的
		String taskDefKey = task.getTaskDefinitionKey();
		String pid = task.getProcessInstanceId();
		String roleCode = actTools.getCurrentRoleCode(task);
		String nodeName = task.getName();
		String nodeDesc = task.getDescription();
		//查找是否存在
		record.setBusId(busId);
		record.setTaskdefkeybegin(taskDefKey);
		//将所有未完成的打上结束时间
		activitiAlarmMonitorService.updateTimeByBusIdComplete(DateUtilsExt.getNowDateTime(), busId, 0l);
		//查找是否存在
		if (activitiAlarmMonitorService.hasRecord(busId, taskDefKey,pid)){
			//以下块注释为20180112之前更新代码
			/*	//将所有置位
			activitiAlarmMonitorService.updateCompleteByBusId(1l, busId,pid);
			//如果存在，那么更新接收时间
			//2017年11月15日18:09:25
			*//**
			 * 先不更新时间，比如财政三岗的时候，如果从第二岗退回来，相当于当前的时间会被覆盖掉，成为当前最新的时间，不正确，就保留原始区间时间，最正确的做法应该是，判断是否
			 * 本区间的审核，1.如果是，保留时间；2.如果不是，相当于从其他区间跳过来的，就更新创建时间
			 * 
			 * bug1.未将0更新进去，上一步全部置位1之后，未将0更新。mybatis的xml也需要更新
			 *//*
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTime(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime(), nodeName,nodeDesc,pid);
			*/
			//完成除了本区域以外的所有，防止跳岗
			activitiAlarmMonitorService.updateCompleteByBusIdExceptNowZone(1l, busId, pid, taskDefKey);
			//1.存在，但是未完成，更新两次。但是只会中一次，要么完成，要么不完成，不可能都执行
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTime(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime(), nodeName,nodeDesc,pid);
			//2.存在，但是已完成
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTimeBack2Zone(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime(), nodeName,nodeDesc,pid);
		}else{
			//不存在：将之前所有busid一样的complete置位，重新添加一条记录
			activitiAlarmMonitorService.updateCompleteEndTimeByBusIdByIsComplete(1l, busId,pid, 0l, DateUtilsExt.getNowDateTime());
			//更新之前未完成的data数据
			activitiAlarmMonitorDataService.updateCompleteTimeByBusId(1, DateUtilsExt.getNowDateTime(), busId,pid);
//			activitiAlarmMonitorService.updateCompleteByBusId(1l, busId, pid);
			this.addAlarm(task, zone, processDesc, isAlarm);
		}
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("zoneFlag", zone);
		alarmMap.put("busType", busType);
		alarmMap.put("busId", busId);
		activitiAlarmMonitorService.dynamicAlarmPro(alarmMap );
	}
	
	
	@SuppressWarnings("deprecation")
	public void updateSingleNode(DelegateTask task, String zone, String processDesc, Long isAlarm) {//夸区域后的第一个节点
		ActivitiAlarmMonitor record = new ActivitiAlarmMonitor();
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return;
		}
		Task taskk = (Task) task;//强制转换，获取单据类型
		String formType = StringUtilsExt.convertNullToString(actTools.getFormType(taskk.getFormKey()));
//		String businessKey = e.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		String taskDefKey = task.getTaskDefinitionKey();
		String pid = task.getProcessInstanceId();
		String roleCode = actTools.getCurrentRoleCode(task);
		String nodeName = task.getName();
		String nodeDesc = task.getDescription();
		//查找是否存在
		record.setBusId(busId);
		record.setTaskdefkeybegin(taskDefKey);
		//查找是否存在
		if (activitiAlarmMonitorService.hasRecord(busId, taskDefKey,pid)){
			//如果存在，那么更新接收时间
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTime(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime(), nodeName,nodeDesc,pid);
			//存在：什么都不做
		}else{
			//不存在：将之前所有busid一样的complete置位，重新添加一条记录
			activitiAlarmMonitorService.updateCompleteEndTimeByBusIdByIsComplete(1l, busId,pid, 0l, DateUtilsExt.getNowDateTime());
//			activitiAlarmMonitorService.updateCompleteByBusId(1l, busId, pid);
			this.addAlarm(task, zone, processDesc, isAlarm);
		}
		//更新data表里面的数据，执行updateComplete方法，说明已经完成任务，更新相应的监控数据data字段
		activitiAlarmMonitorDataService.updateCompleteTimeByBusId(1, DateUtilsExt.getNowDateTime(), busId,pid);
	}
	
	
	
	@SuppressWarnings({ "deprecation", "unused" })
	public void updateMiddle(DelegateTask task, String zone, String processDesc, Long isAlarm) {//夸区域后的第一个节点
		ActivitiAlarmMonitor record = new ActivitiAlarmMonitor();
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return;
		}
		Task taskk = (Task) task;//强制转换，获取单据类型
		String formType = StringUtilsExt.convertNullToString(actTools.getFormType(taskk.getFormKey()));
//		String businessKey = e.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		String busType = businessKey.split("\\.")[0];
		String taskDefKey = task.getTaskDefinitionKey();
		String pid = task.getProcessInstanceId();
		String roleCode = actTools.getCurrentRoleCode(task);
		String nodeName = task.getName();
		String nodeDesc = task.getDescription();
		//查找是否存在
		record.setBusId(busId);
//		record.setTaskdefkeybegin(taskDefKey);
		record.setIsComplete(0l);
		record.setZone(zone);
		//这里有bug，比如主管是3个岗位审核，然后又从其他地方跳岗过来的
		List<ActivitiAlarmMonitor> list = activitiAlarmMonitorService.getActivitiAlarmMonitorList(record);
		if(list != null && list.size() > 0){
			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTimeMiddle(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime(),nodeName,nodeDesc,pid);
//			activitiAlarmMonitorService.updateCurrentTaskDefRoleCodeReceiveTime(busId, 0l, taskDefKey, roleCode, DateUtilsExt.getNowDateTime());
		}else{
			//找不到的话，可能是跳岗，更新相关信息，然后新加一条当前的节点信息
			activitiAlarmMonitorService.updateCompleteEndTimeByBusId(1l, busId,pid, DateUtilsExt.getNowDateTime(), nodeName, nodeDesc);
//			activitiAlarmMonitorService.updateCompleteEndTimeByBusId(1l, busId, pid, DateUtilsExt.getNowDateTime());
			this.addAlarm(task, zone, processDesc, isAlarm);
		}
		//执行存储过程
		Map<String, Object> alarmMap = new HashMap<String, Object>();
		alarmMap.put("zoneFlag", zone);
		alarmMap.put("busType", busType);
		alarmMap.put("busId", busId);
		activitiAlarmMonitorService.dynamicAlarmPro(alarmMap );
	}

	@SuppressWarnings("deprecation")
	public void updateComplete(DelegateTask task) {
		DelegateExecution e = task.getExecution();
		String businessKey = e.getBusinessKey();
		if(businessKey == null){
			return;
		}
		String busId = businessKey.split("\\.")[1];
		String pid = task.getProcessInstanceId();
		activitiAlarmMonitorService.updateCompleteEndTimeByBusIdByIsComplete(1l, busId,pid, 0l, DateUtilsExt.getNowDateTime());
		
		//更新data表里面的数据，执行updateComplete方法，说明已经完成任务，更新相应的监控数据data字段
		activitiAlarmMonitorDataService.updateCompleteTimeByBusId(1, DateUtilsExt.getNowDateTime(), busId,pid);
	}
	@SuppressWarnings("deprecation")
	public void updateCompleteByExe(DelegateExecution execution) {
		String businessKey = execution.getBusinessKey();
		if(businessKey == null){
			return;
		}
//		String businessKey = execution.getProcessBusinessKey();
		String busId = businessKey.split("\\.")[1];
		
		String pid = execution.getProcessInstanceId();
		activitiAlarmMonitorService.updateCompleteEndTimeByBusIdByIsComplete(1l, busId,pid, 0l, DateUtilsExt.getNowDateTime());
		
		//更新data表里面的数据，执行updateComplete方法，说明已经完成任务，更新相应的监控数据data字段
		activitiAlarmMonitorDataService.updateCompleteTimeByBusIdNoPid(1, DateUtilsExt.getNowDateTime(), busId);
	}

}
