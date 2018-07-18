package com.bosssoft.egov.asset.activiti.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.openwebflow.ctrl.TaskFlowControlService;
import org.openwebflow.ctrl.TaskFlowControlServiceFactory;
import org.openwebflow.util.ProcessEngineTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiToolsService;
import com.bosssoft.egov.asset.activiti.entity.ActivitiBranchControl;
import com.bosssoft.egov.asset.activiti.entity.ActivitiCondition;
import com.bosssoft.egov.asset.activiti.entity.ActivitiException;
import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiShowPic;
import com.bosssoft.egov.asset.activiti.entity.ActivitiTaskMoveComboboxShow;
import com.bosssoft.egov.asset.activiti.entity.ActivitiUserTaskNode;
import com.bosssoft.egov.asset.activiti.entity.AssetActivitiBus;
import com.bosssoft.egov.asset.activiti.entity.AssetBizStatus;
import com.bosssoft.egov.asset.activiti.entity.AssetBusHistory;
import com.bosssoft.egov.asset.activiti.entity.AssetProcessRemark;
import com.bosssoft.egov.asset.activiti.entity.HandleType;
import com.bosssoft.egov.asset.activiti.entity.LastNode;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.activiti.entity.RecordTask;
import com.bosssoft.egov.asset.activiti.entity.ShowUndoTask;
import com.bosssoft.egov.asset.activiti.entity.TaskStatus;
import com.bosssoft.egov.asset.activiti.entity.ToDoListType;
import com.bosssoft.egov.asset.activiti.service.ActivitiBranchControlService;
import com.bosssoft.egov.asset.activiti.service.ActivitiPermissionExtService;
import com.bosssoft.egov.asset.activiti.service.ActivitiProcessService;
import com.bosssoft.egov.asset.activiti.service.AssetActivitiBusService;
import com.bosssoft.egov.asset.activiti.service.AssetBusHistoryService;
import com.bosssoft.egov.asset.activiti.service.AssetProcessRemarkService;
import com.bosssoft.egov.asset.activiti.service.OwfActivityPermissionService;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.common.util.XmlUtilsExt;
import com.bosssoft.egov.asset.drc.DrcHepler;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.utils.StringUtils;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 *
 * @ClassName 类名：ActivitiProcessServiceImpl
 * @Description 功能说明：activiti核心类
 ************************************************************************
 * @date 创建日期：2016年11月2日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          2016年11月2日 jinbiao 创建该类功能。
 */
@SuppressWarnings("unchecked")
@Service
@Transactional
public class ActivitiProcessServiceImpl implements ActivitiProcessService {
	private static Logger logger = LoggerFactory.getLogger(ActivitiProcessServiceImpl.class);
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProcessEngineTool engineTools;
	@Autowired
	private TaskFlowControlServiceFactory taskFlowControlServiceFactory;
	@Autowired
	private AssetProcessRemarkService assetProcessRemarkService;
//	@Autowired
//	private AssetBizStatusService assetBizStatusService;
	@Autowired
	private AssetActivitiBusService assetActivitiBusService;
	@Autowired
	private OwfActivityPermissionService owfPermissionService;
	@Autowired
	private AssetBusHistoryService assetBusHistoryService;
	@Autowired
	private ActivitiToolsService activitiToolsService;
	@Autowired
	private ActivitiBranchControlService activitiBranchControlService;
	@Autowired
	private ActivitiPermissionExtService activitiPermissionExtService;
	
	
	/**
	 * 
	 * <p>
	 * 函数名称： 审核记录，审核情况
	 * 
	 * @param page
	 * @param userCode
	 *            登陆者信息
	 * @param busType
	 *            业务类型
	 * @return
	 *
	 * @date 创建时间：2016年12月2日
	 * @author 作者：jinbiao
	 */
	public Page<AssetProcessRemark> queryProcessRemarkHistoryPage(Page<AssetProcessRemark> page, String userCode, String busType) {
		return assetProcessRemarkService.queryAssetProcessRemarkDesigatedBusCheckPage(page, userCode, busType);
	}

	/**
	 * <p>
	 * 功能说明：审核流程图，该方法，返回所有节点，通过isPass区分
	 * 参数说明：
	 * </p>
	 * @param params
	 * @return
	 * @throws DocumentException
	 *
	 * @date 创建时间：2016年12月20日
	 * @author 作者：jinbiao
	 */
	public ActivitiShowPic getHistoryListPic(ActivitiParams params) throws DocumentException {
		return activitiToolsService.getHistoryListPic(params);
	}


	public Page<AssetProcessRemark> queryProcessRemarkHistoryByBusinessIdPage(Page<AssetProcessRemark> page, String businessId) {
		return assetProcessRemarkService.queryAssetProcessRemarkByBusinessIdPage(page, businessId);
	}

	public List<Object> queryProcessRemarkHistory(String userCode, String busType, Integer checkresult) {
		return assetProcessRemarkService.queryAuditorDesignatedBusRemarkByResult(userCode, busType, StringUtilsExt.convertNullToString(checkresult));
	}

	public List<Object> queryTaskByUserCode(ActivitiParams params) {

		List<Object> status = new ArrayList<Object>();
		List<RecordTask> recordTask = new ArrayList<RecordTask>();
		// 登陆者信息
		String userCode = params.getUserCode();

		List<Task> list = activitiToolsService.queryTaskByUserCodeOnlyTask(userCode);
		String formKey = "";
		if (list != null && list.size() > 0) {
			for (int i = 0, len = list.size(); i < len; i++) {
				RecordTask rt = new RecordTask();
				rt.setTaskDefId(list.get(i).getProcessDefinitionId());
				rt.setTaskId(list.get(i).getTaskDefinitionKey());
				if (recordTask.contains(rt)) {
					continue;
				}
				recordTask.add(rt);
				formKey = StringUtilsExt.convertNullToString(list.get(i).getFormKey());
				if(StringUtils.isNullOrBlank(formKey)){
					continue;
				}
				formKey = StringUtilsExt.convertNullToString(activitiToolsService.getFormKey(formKey));
				if (!status.contains(formKey)) {
					status.add(formKey);
				}
			}
		}
		return status;
	}

	public List<ShowUndoTask> getToDoList(ActivitiParams params) {
		List<ShowUndoTask> total = new ArrayList<ShowUndoTask>();
		// 登陆者信息
		String userCode = params.getUserCode();
		String userName = params.getUserName();

		// 获取正常情况下的登陆者任务
		List<Task> list = activitiToolsService.queryTaskByUserCodeOnlyTask(userCode);
		if (list == null) {
			return total;
		}
		if (!(list.size() > 0)) {
			return total;
		}
		/**
		 * 数据权限控制
		 */
		Map<String, Object> findKeyMap = params.getFindKeyMap();
		List<Task> taskList = new ArrayList<Task>();
		for (Task task : list) {
			Map<String, Object> startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "startKeyMap");
			if (DrcHepler.getInstance().matchUserTodoList(startKeyMap, findKeyMap)) {
				taskList.add(task);
			}
		}
		if (params.getToDoType() == ToDoListType.DETAIL.getType()) {
			return activitiToolsService.showUndoTaskDetail(params, total, taskList, userName);
		}
		if (params.getToDoType() == ToDoListType.SMALL.getType()) {
			return activitiToolsService.showUndoTaskSmall(params, total, taskList);
		} else {
			return activitiToolsService.showUndoTaskBig(params, total, taskList);
		}
	}

	/**
	 * 
	 * 功能说明：根据业务id获取当前流程进度
	 * 
	 * @param businessId
	 * @date 创建时间：2016年11月11日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> queryProgressPicByBusinessId(ActivitiParams params) {

		Map<String, Object> map1 = new HashMap<String, Object>();

		if (assetActivitiBusService.queryAssetActivitiBus(params.getBusType()) == null) {
			map1.put("code", "001");
			map1.put("message", "业务类型异常");
			return map1;
		}
		String taskId = activitiToolsService.queryTaskIdByBusinessIdLike(params);
//		String taskId = activitiToolsService.queryTaskIdByBusinessId2(params);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// String taskId = this.queryTaskIdByBusinessId2(map);

		Map<String, Object> picLocationXY = activitiToolsService.queryCoordingByTask(taskId);
		Integer i = Integer.parseInt(StringUtilsExt.convertNullToString(picLocationXY.get("y"))) + 60;
		// 根据页面调整
		picLocationXY.put("y", i);

		ProcessDefinition pd = activitiToolsService.queryProcessDefinitionById(task.getProcessDefinitionId());
		String deploymentId = pd.getDeploymentId();
		// 这个是获取bpmn
		// String imageName = pd.getResourceName();
		// 这个是图片名称
		String imageName = pd.getDiagramResourceName();
		// String deploymentName = pd.getResourceName();
		map1.put("deploymentId", deploymentId);
		map1.put("imageName", imageName);
		map1.put("location", picLocationXY);

		return map1;
	}

	/**
	 * 流程图查看,获取流程图的名字
	 * 
	 * @date 创建时间：2016年11月11日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> queryProgressPic(String deploymentId) {

		Map<String, Object> map = new HashMap<String, Object>();
		ProcessDefinition pd = activitiToolsService.queryProcessDefinitionByDeploymentId(deploymentId);
		// String deploymentId = pd.getDeploymentId();
		// 这个是获取bpmn
		// String imageName = pd.getResourceName();
		// 这个是图片名称
		String imageName = pd.getDiagramResourceName();
		// String deploymentName = pd.getResourceName();

		// InputStream picInstream = this.queryImageInputStream(deploymentId,
		// imageName);
		// map.put("picture", picInstream);
		map.put("deploymentId", deploymentId);
		map.put("imageName", imageName);

		return map;
	}


	/**
	 * 功能说明：通过业务id获取可以后退的列表节点选择，从而进行调整，资产退回列表，资产专属退回 参数说明：退后
	 * 
	 * 如果需要的话，任意后退，就直接使用
	 * 
	 * @param taskId
	 * @return
	 * @throws DocumentException
	 * @date 创建时间：2016年11月14日
	 * @author 作者：jinbiao
	 */
	public List<ActivitiTaskMoveComboboxShow> queryBackUserTaskListByBusinessId(ActivitiParams params) throws DocumentException {
		List<ActivitiTaskMoveComboboxShow> list = new ArrayList<ActivitiTaskMoveComboboxShow>();
		// 按实际需求画图，目前只要退回一步即可
		String backCode = params.getBackCode();
		//通过某种操作码来获得下拉列表
		if (StringUtils.isNullOrBlank(backCode)) {
			// backNumber = "1.1";
			backCode = "1";
		}
		String taskId = activitiToolsService.queryTaskIdByBusinessIdLike(params);
//		String taskId = activitiToolsService.queryTaskIdByBusinessId2(params);
		Task nowUserTask = taskService.createTaskQuery().taskId(taskId).singleResult();
		String nowUserTaskName = nowUserTask.getTaskDefinitionKey();

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessDefinition pd = activitiToolsService.queryProcessDefinitionById(task.getProcessDefinitionId());
		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = activitiToolsService.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		List<Element> userTaskList = process.elements("userTask");
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		// 第一个for 获取到所有的目标节点id，第二个for，用这个id去查找出该id对应的名字，用于前端显示
		for (Element sfl : sequenceFlowList) {
			Element e = sfl.element("conditionExpression");
			if (e == null) {
				continue;
			} else {
				String isback = e.getText();
				// 判断是否含有1，有1则包含退回handle=='1
				if (!(isback.contains(backCode)&&(isback.contains("handle=='1")))) {
					continue;
				}
			}
			ActivitiTaskMoveComboboxShow show = new ActivitiTaskMoveComboboxShow();
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			if (nowUserTaskName.equals(sourceRef)) {
				show.setUserTaskId(targetRef);
				for (Element utl : userTaskList) {
					String name = utl.attributeValue("name");
					String id1 = utl.attributeValue("id");
					if (id1.equals(targetRef)) {
						show.setUserTaskName(name);
					}
				}
				list.add(show);
			}
		}
		return list;
	}

	public ProcessResult cancelTask(ActivitiParams params) throws Exception {
//		Integer currentTaskStatus;
		Integer nextTaskStatus;
//		Integer next2TaskStatus = 0;
		AssetProcessRemark actlog = new AssetProcessRemark();
		AssetBusHistory history = new AssetBusHistory();
		ProcessResult proresult = new ProcessResult();
		AssetBizStatus bizstatus = new AssetBizStatus();
		Integer reverseHandle = 0;
		String description = "";
		
		
		String taskId = activitiToolsService.queryTaskIdByBusinessIdLike(params);
//		String taskId = activitiToolsService.queryTaskIdByBusinessId2(params);
		// 查出当前任务
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
//		List<ApiUser> apiUserListByOrgCodeListBeforeDo = activitiToolsService.getNextExcetorApiUsers(task.getId());
		
//		currentTaskStatus = NumberUtilsExt.toInt(task.getFormKey(), 0);
		ActivitiTaskMoveComboboxShow nowNode = new ActivitiTaskMoveComboboxShow();
		nowNode.setUserTaskId(task.getTaskDefinitionKey());
		nowNode.setUserTaskName(task.getName());
		description = StringUtilsExt.convertNullToString(task.getDescription());
		actlog.setDescription(description);
		// 从已有得taskId获取业务id
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		AssetActivitiBus activitibus = params.getActivitiBus();
		if(activitibus == null){
			assetActivitiBusService.queryAssetActivitiBus(busType);
		}
		// 获取当前任务执行者，也可能是登陆者
		String userCode = params.getUserCode();
		String userName = params.getUserName();
		
		String proInsId = task.getProcessInstanceId();
		//取消提交
		params.setProcessInstanceId(proInsId);
		List<AssetProcessRemark> submitList = assetProcessRemarkService.queryHisByUserIdPidBusIdByCheck(userCode, proInsId, HandleType.SUBMIT.getHandle());
		List<AssetProcessRemark> oneAllListExceptSubmit = assetProcessRemarkService.queryHisByUserIdPidBusId(userCode, proInsId, businessId);
		int lenSubmitList = submitList.size();
		int lenOneAllListExceptSubmit = oneAllListExceptSubmit.size();
		/**
		 * 对于某个人而言，取出他的提交任务，以及所有的任务，如果此时size相同，则认为是取消提交
		 * 如果不是相同的，则是其他的取消操作
		 */
		if (lenSubmitList == lenOneAllListExceptSubmit) {
			if (submitList != null && submitList.size() > 0) {
				if (activitiToolsService.canCancelSubmit(params)) {
					return activitiToolsService.cancelSubmit(params);
				}
				proresult.setMessage(ActivitiException.INAUDIT.getName());
				proresult.setCode(ActivitiException.INAUDIT.getCode());
				logger.error(ActivitiException.INAUDIT.getName());
				return proresult;
			}
		}
		Integer handle = params.getHandle();
		String creatorId = params.getCreatorId();
		String creatorName = params.getCreatorName();

		String idInXml = "";
		String nameInXml = "";
		AssetBusHistory record = new AssetBusHistory();
		record.setBusId(businessId);
		record.setOperatorId(userCode);
		if(handle == HandleType.CANCELRETURN.getHandle()){
			reverseHandle = HandleType.RETURN.getHandle();
			record.setCheckResult(HandleType.RETURN.getHandle());
		}else if(handle == HandleType.CANCELPASS.getHandle()){
			reverseHandle = HandleType.PASS.getHandle();
			record.setCheckResult(HandleType.PASS.getHandle());
		}else if(handle == HandleType.CANCELREJECT.getHandle()){
			reverseHandle = HandleType.REJECT.getHandle();
			record.setCheckResult(HandleType.REJECT.getHandle());
		}
		//对于一人授权多节点，可能存在问题
		List<AssetBusHistory> hisList = new ArrayList<AssetBusHistory>();
		List<AssetBusHistory> hisListAll = new ArrayList<AssetBusHistory>();
		//查询某个任务的所有历史
		hisListAll = assetBusHistoryService.queryCheckListByBusinessIdUserCode(businessId,userCode);
		//查出某个用户的所有某种操作
		record.setCheckResult(null);
		hisList = assetBusHistoryService.queryCheckListByBusinessIdUserCode(record.getBusId(), record.getOperatorId());
//		hisList = assetBusHistoryService.queryCheckList(record);
		AssetBusHistory his = new AssetBusHistory();
		AssetBusHistory hisAll = new AssetBusHistory();
		if (hisListAll != null && hisListAll.size() == 0) {
			proresult.setMessage(ActivitiException.HISTORYOPTIONNOFOUND.getName());
			proresult.setCode(ActivitiException.HISTORYOPTIONNOFOUND.getCode());
			logger.error(ActivitiException.HISTORYOPTIONNOFOUND.getName());
			return proresult;
		}
		if (hisList != null && hisList.size() == 0) {
			proresult.setMessage(ActivitiException.NOREVERSEHANDLE.getName());
			proresult.setCode(ActivitiException.NOREVERSEHANDLE.getCode());
			logger.error(ActivitiException.NOREVERSEHANDLE.getName());
			return proresult;
		}
		//全部历史
		hisAll = hisListAll.get(0);
		his = hisList.get(0);
//		if (reverseHandle != his.getCheckResult()) {
//			proresult.setMessage(ActivitiException.NOREVERSEHANDLESIMPLE.getName() + reverseHandle + "操作！");
//			proresult.setCode(ActivitiException.NOREVERSEHANDLESIMPLE.getCode());
//			return proresult;
//		}
		Integer beforeCancelCurrentCheck = his.getCheckResult();
		if(beforeCancelCurrentCheck == HandleType.PASS.getHandle()){
			params.setHandle(HandleType.CANCELPASS.getHandle());
		}
		if(beforeCancelCurrentCheck == HandleType.RETURN.getHandle()){
			//取消退回操作，关闭
			params.setHandle(HandleType.CANCELRETURN.getHandle());
			proresult.setMessage(ActivitiException.NORETURNACTION.getName());
			proresult.setCode(ActivitiException.NORETURNACTION.getCode());
			logger.error(ActivitiException.NORETURNACTION.getName());
			return proresult;
		}
		if(beforeCancelCurrentCheck == HandleType.REJECT.getHandle()){
			//取消驳回操作，关闭
			params.setHandle(HandleType.CANCELREJECT.getHandle());
			proresult.setMessage(ActivitiException.NOPASSCANT.getName());
			proresult.setCode(ActivitiException.NOPASSCANT.getCode());
			logger.error(ActivitiException.NOPASSCANT.getName());
			return proresult;
		}
		//读取到用户在操作词条任务时候的当前操作userTaskId，取出来，把任务退回到此节点上
		idInXml = his.getCurrentTask();
		Integer beforeTaskStatus = his.getBeforeStatus();
		if (beforeTaskStatus == TaskStatus.FINISH.getStatus()) {
			String processInstanceId = task.getProcessInstanceId();
			ActivitiParams pa = new ActivitiParams();
			pa.setTaskId(taskId);
			// 取消至已完成
			activitiToolsService.cancelToEnd(pa);

			Integer status = TaskStatus.BUSINESSSUCCESS.getStatus();
			actlog.setNextExecutorName("");
			//取消退回
			actlog.setCheckResult(HandleType.CANCELRETURN.getHandle());
			String optionMessage = HandleType.CANCELRETURN.getName();
			history.setCheckResult(beforeCancelCurrentCheck);
			actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
			actlog.setOperatorName(userName);
			actlog.setBusType(activitibus.getDeploymentKey());
			actlog.setProcInstId(processInstanceId);
			
			actlog.setTaskId(taskId);
			actlog.setNextNodeName(nameInXml);
			actlog.setNextNodeId(idInXml);
			actlog.setNextTaskStatus(TaskStatus.BUSINESSSUCCESS.getStatus());
			actlog.setCreatorId(creatorId);
			actlog.setCreatorName(creatorName);
			actlog.setAssignee(TaskStatus.END.getStatus().toString());
			actlog.setDescription("");
			
			actlog.setTaskStatus(TaskStatus.FINISH.getStatus());
			actlog.setNextNodeName("办结");
			history.setTaskStatus(TaskStatus.FINISH.getStatus());
			// 任务结束，下一节点待办人为空
			actlog.setNextExecutorCode("");
			bizstatus.setBizStatus(status);
			bizstatus.setBizStatusName(TaskStatus.BUSINESSSUCCESS.getName());
//			bizstatus = assetBizStatusService.queryAssetBizStatus(status);
			// bizstatus.setBizStatusName(nowTaskName);

			history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
			history.setProcInstId(processInstanceId);
			history.setBusId(businessId);
			history.setOperatorId(userCode);
			history.setBizType(busType);
			history.setCurrentTask(idInXml);

			optionMessage = optionMessage + ActivitiException.SUCCESS.getName();
//			optionMessage = optionMessage + proresult.getMessage();
			// 修改状态字
			String originName = bizstatus.getBizStatusName();
			String finalName = nameInXml + originName;
			bizstatus.setBizStatusName(finalName);
			
			assetProcessRemarkService.addAssetProcessRemark(actlog);
			assetBusHistoryService.updateHistoryLog(history, HandleType.CANCELTASK.getHandle());
			proresult.setMessage(optionMessage);
			proresult.setCode(ActivitiException.SUCCESS.getCode());
			proresult.setLog(actlog);
			proresult.setInfo(bizstatus);
			proresult.setBusId(businessId);
			
			return proresult;
		}
		List<ActivitiTaskMoveComboboxShow> nodeList = activitiToolsService.queryUserTaskIdListByBusinessId(params);
		for (ActivitiTaskMoveComboboxShow activitiTaskMoveComboboxShow : nodeList) {
			if(activitiTaskMoveComboboxShow.getUserTaskId().equals(idInXml)){
				nameInXml = activitiTaskMoveComboboxShow.getUserTaskName();
				break;
			}
		}
		String processDefinitionId = task.getProcessDefinitionId();
		String processInstanceId = task.getProcessInstanceId();
		// 取消，意味着跳回到当前节点，userTaskId即为当前节点id
		String userTaskId = idInXml;
		// 当XML文件里面没有当前任务者所对应的节点的时候，也就是他可能是个代理，那就直接取出一个没有结束的流程中他刚刚执行过的任务节点
		if (StringUtils.isNullOrBlank(idInXml)) {
			List<HistoricTaskInstance> taskHistory = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).taskAssignee(userCode).processUnfinished().list();
			for (int i = 0, len = taskHistory.size(); i < len; i++) {
				String pid = taskHistory.get(i).getProcessInstanceId();
				if (pid.equals(task.getProcessInstanceId())) {
					userTaskId = taskHistory.get(i).getTaskDefinitionKey();
				}
			}
		}
		actlog.setUserTaskId(task.getTaskDefinitionKey());
		actlog.setTaskName(task.getName());
		actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		actlog.setBusId(businessId);
		actlog.setOperatorId(userCode);
		actlog.setOperatorName(userName);
		actlog.setBusType(activitibus.getDeploymentKey());
		actlog.setProcInstId(processInstanceId);
		actlog.setCheckResult(handle);
		actlog.setTaskId(taskId);
		actlog.setTaskName(task.getName());
		actlog.setNextNodeName(nameInXml);
		actlog.setNextNodeId(idInXml);
		actlog.setCreatorId(creatorId);
		actlog.setCreatorName(creatorName);

		history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		history.setCheckResult(beforeCancelCurrentCheck);
		history.setProcInstId(processInstanceId);
		history.setBusId(businessId);
		history.setOperatorId(userCode);
		history.setBizType(busType);
		//执行的点
		history.setCurrentTask(userTaskId);
		if(!this.existInActiviti(params)){
			history.setTaskStatus(TaskStatus.FINISH.getStatus());
		}else{
			history.setTaskStatus(TaskStatus.RUNNING.getStatus());
		}
		
		/**
		 * 越权判断， 1-2-3
		 * 对于2而言，如果是通过审核，但是在3处，被驳回操作，现在的任务在1处，此时对于2而言代码可以进行取消操作，但是这算非法操作，不能让他进行
		 */
		String nowTaskDef = task.getTaskDefinitionKey();
		String a1 = nowTaskDef.substring(8, nowTaskDef.length());
		String a2 = userTaskId.substring(8, userTaskId.length());
		Integer i1 = Integer.parseInt(a1);
		Integer i2 = Integer.parseInt(a2);
//		if (i1 <= i2) {
//			proresult.setMessage(ActivitiException.CANCELERROR.getName() + "此时流程在" + task.getName());
//			proresult.setCode(ActivitiException.CANCELERROR.getCode());
//			return proresult;
//		}
		//查出某个业务的所有操作
		List<AssetBusHistory> oneBusinessAll = assetBusHistoryService.queryCheckListByBusinessId(businessId);
		//查看最新操作是否是他操作过的，如果不是他操作过的，就不让他进行操作
		String latestOperateCode = StringUtilsExt.convertNullToString(oneBusinessAll.get(0).getOperatorId());
		if (!userCode.equals(latestOperateCode)) {
			proresult.setMessage(ActivitiException.CANCELERROR.getName()/* + "此时流程在" + task.getName()+"环节"*/);
			proresult.setCode(ActivitiException.CANCELERROR.getCode());
			logger.error(ActivitiException.CANCELERROR.getName());
			return proresult;
		}

		String formkeytemp = formService.getTaskFormKey(processDefinitionId, userTaskId);
		Integer status = activitiToolsService.getFormKey(formkeytemp);
//		Integer status = Integer.parseInt(formkeytemp);

		bizstatus.setBizStatus(status);
		bizstatus.setBizStatusName(task.getName());
//		bizstatus = assetBizStatusService.queryAssetBizStatus(status);

		TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);
		// 撤回
		tfcs.moveTo(userTaskId);
		// outresult.add(proresult);
		Task nowTask = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		String formKey = nowTask.getFormKey();
		Integer formKeyInteger = activitiToolsService.getFormKey(formKey);
//		Integer formKeyInteger = Integer.parseInt(formKey);
		String billOrgCode = null ;
		if(formKeyInteger > 8500 && formKeyInteger < 10000){//单位变换，调拨区间
//			if(formKeyInteger > 8800 && formKeyInteger < 10000){//单位变换，调拨区间
			billOrgCode = params.getBillOrgCode();
		}
		//获取取消后的人员列表
		params.setTaskId(nowTask.getId());
		params.setBillOrgCode(billOrgCode);;
		List<ApiUser> apiUserListByOrgCodeList = activitiToolsService.getNextExcetorApiUsers(params);
//		List<ApiUser> apiUserListByOrgCodeList = activitiToolsService.getNextExcetorApiUsers(nowTask.getId(),billOrgCode);
		proresult.setLog(actlog);
		proresult.setNextNodeUser(apiUserListByOrgCodeList);
		nextTaskStatus = activitiToolsService.getFormKey(nowTask.getFormKey());
//		nextTaskStatus = NumberUtilsExt.toInt(nowTask.getFormKey(), 0);
		actlog.setNextTaskStatus(nextTaskStatus);
		actlog.setAssignee(nowTask.getDescription());
		actlog.setNextNodeName(nowTask.getName());
		actlog.setNextNodeId(nowTask.getTaskDefinitionKey());
		actlog.setTaskStatus(TaskStatus.CHECKING.getStatus());
		String exactUsers = activitiToolsService.getNextExcetor(nowTask.getId());
//		activitiToolsService.getNextExcetorApiUsers(nowTask.getId());
		proresult.getLog().setNextExecutorCode(exactUsers);
		ApiUser nextUser = null;
		if (apiUserListByOrgCodeList != null && apiUserListByOrgCodeList.size() > 0) {
			nextUser = activitiToolsService.getRandomUser(apiUserListByOrgCodeList);
//			nextUser = proresult.getNextNodeUser().get(0);
		}
		if(nextUser != null){
			String pin = "";
			if(StringUtils.isNullOrBlank(nextUser.getMobileNo())){
				pin = nextUser.getUserName()/*+" "+"！"*/;
			}else{
				pin = nextUser.getUserName()/*+" "+nextUser.getMobileNo()*/;
			}
			actlog.setNextExecutorName(pin);
		}else{
			proresult.setMessage(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName()/*+"("+busType+"_"+businessId+")"*/);
			proresult.setCode(ActivitiException.GETNEXTUSESRFAIL.getCode());
			logger.error(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName()+"("+busType+"_"+businessId+")");
			return proresult;
		}
		assetProcessRemarkService.addAssetProcessRemark(actlog);
		assetBusHistoryService.updateHistoryLog(history,HandleType.CANCELTASK.getHandle());
		proresult.setBusId(businessId);
		proresult.setInfo(bizstatus);
		//待办专用
		if (params.getHandle() == HandleType.CANCELPASS.getHandle()) {
//			actlog.setNextNodeName(nameInXml);
//			actlog.setTaskName(nameInXml);
			actlog.setNextNodeId(idInXml);
			actlog.setUserTaskId(idInXml);
		}
		if (params.getHandle() == HandleType.CANCELRETURN.getHandle()) {
			actlog.setUserTaskId(task.getTaskDefinitionKey());
			actlog.setNextNodeId(idInXml);
//			actlog.setTaskName(task.getName());
//			actlog.setNextNodeName(nameInXml);
		
		}
		
		// 待办专用处理 保证流程的指向永远是向前的，只保证名称向前，其余所有的参数保持正常，不论什么情况下，都是由上一个岗位指向下一岗位
//		actlog.setNextNodeId(idInXml);
		actlog.setNextNodeName(nameInXml);
		String beforeNodeId = "";
		String beforeNodeName = "";
		List<AssetProcessRemark> beforeNodeList = assetProcessRemarkService.queryBusinessHistory(businessId, processInstanceId);
		for (AssetProcessRemark assetProcessRemark : beforeNodeList) {
			//取出remark中的历史记录，找出下一节点指向当前节点的的记录，然后把当前节点拿出来，进而间接找出当前节点的上一节点
			if (nowTask.getTaskDefinitionKey().equals(assetProcessRemark.getNextNodeId())) {
				beforeNodeId = assetProcessRemark.getUserTaskId();//单前节点usertaskid
				beforeNodeName = assetProcessRemark.getTaskName();//当前节点名称
			}
		}
		actlog.setTaskName(beforeNodeName);
//		actlog.setTaskId(beforeNodeId);
		//以下为判断是否为首节点，但是是多余的，取消任务如果是首节点的话，就只有取消审核，所以以下代码可以注释，没有意义
		Map<String, Object> map11 = new HashMap<String, Object>();
		map11.put("userTaskId", idInXml);
		map11.put("taskId", nowTask.getId());
		if (activitiToolsService.isFirstNode(map11)) {
			actlog.setTaskName("");
//			actlog.setTaskId("");
		}
		Map<String,Object> next2Map = new HashMap<String, Object>();
		next2Map.put("taskId", nowTask.getId());
		next2Map.put("currentTaskDefKey", nowTask.getTaskDefinitionKey());
		List<ActivitiUserTaskNode>next2List = activitiToolsService.getNextNode(next2Map);
		if(next2List.size() == 0){
			proresult.setNext2TaskName(null);
		}else{
			ActivitiUserTaskNode activitiUserTaskNode = next2List.get(0);
			//结束节点的名称为End
			if("nd".equalsIgnoreCase(activitiUserTaskNode.getUserTaskId())){
				proresult.setNext2TaskName(null);
			}else{
				proresult.setNext2TaskName(activitiUserTaskNode.getUserTaskName());
			}
		}

		proresult.setFormType(activitiToolsService.getFormType(nowTask.getFormKey()));
		proresult.setLog(actlog);
		proresult.setCode(ActivitiException.SUCCESS.getCode());
		proresult.setMessage(HandleType.CANCELTASK.getName() + ActivitiException.SUCCESS.getName());
		return proresult;
	}
	
	public ProcessResult handleTask(ActivitiParams params) throws Exception {
		String roleName = "";
		String roleNameInner = "";
		String roleCode = "";
		AssetProcessRemark actlog = new AssetProcessRemark();
		actlog.setType(1);//正常批注类型
		AssetProcessRemark receiveActlog = new AssetProcessRemark();//接收日志
		receiveActlog.setType(0);//接收批注内容
		AssetBusHistory history = new AssetBusHistory();
		ProcessResult proresult = new ProcessResult();
		AssetBizStatus bizstatus = new AssetBizStatus();
		Integer currentTaskStatus;
		Integer nextTaskStatus;
		Integer next2TaskStatus = 0;
		boolean isReturnOrRejectStatus = false;
		String defId = "";
		String optionMessage = "";
		String remark = params.getComment();
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		String creatorId = params.getCreatorId();
		String creatorName = params.getCreatorName();
		String creatorCode = params.getCreatorCode();
		// 获取当前任务执行者，也可能是登陆者
		String userName = params.getUserName();
		String userCode = params.getUserCode();
		String duedate = params.getDueDate();
		Integer handle = params.getHandle();
		String deploymentKey = StringUtilsExt.convertNullToString(params.getDeploymentKey());
		// 业务当前状态
		String description = "";
		
		ProcessResult checkResult = activitiToolsService.beforeHandleCheck(params);
		//输入参数校验
		if (!checkResult.getCode().equals(ActivitiException.SUCCESS.getCode())) {
			return checkResult;
		}
		if(StringUtils.isNullOrBlank(creatorCode)){
			ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserId(creatorId);
			if (apiUser != null) {
				creatorCode = apiUser.getUserCode();
				creatorName = apiUser.getUserName();
				params.setCreatorCode(creatorCode);
				params.setCreatorName(creatorName);
			}
		}
		AssetActivitiBus activitibus = checkResult.getActivitibus();
		params.setActivitiBus(activitibus);
		proresult.setActivitibus(activitibus);
		if(StringUtils.isNullOrBlank(deploymentKey)){
			deploymentKey = activitibus.getDeploymentKey();
		}
		params.setDeploymentKey(deploymentKey);
		String taskId = activitiToolsService.queryTaskIdByBusinessIdLike(params);
		// 判断任务是否存在，默认存在
		boolean isExist = true;
		if (StringUtils.isNullOrBlank(taskId)) {
			isExist = false;
		}
		if (handle == HandleType.VIEW.getHandle()) {// 接收日志查看存储
			if (isExist) {
				params.setTaskId(taskId);
				activitiToolsService.isView(params);
			}
			proresult.setMessage("添加查看记录");
			proresult.setCode(ActivitiException.SUCCESS.getCode());
			return proresult;//不论成功与否，都返回成功记录
		}
		if (handle == HandleType.CANCELPASS.getHandle() || handle == HandleType.CANCELRETURN.getHandle()|| handle == HandleType.CANCELTASK.getHandle()) {
			if (isExist) {//后台校验能否退回任务
				return this.cancelTask(params);
			} else {
				proresult.setMessage(ActivitiException.TASKNOTFOUND.getName()/*+"("+busType+"_"+businessId+")"*/);
				proresult.setCode(ActivitiException.TASKNOTFOUND.getCode());
				logger.error(ActivitiException.TASKNOTFOUND.getName()+"("+busType+"_"+businessId+")");
				return proresult;
			}
		} else if (handle == HandleType.CANCELSUBMIT.getHandle()) {
			if (isExist) {
				params.setTaskId(taskId);
				String deletePid = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
				params.setProcessInstanceId(deletePid);
				if (!activitiToolsService.canCancelSubmit(params)) {
					proresult.setMessage(ActivitiException.INAUDIT.getName()/*+"("+busType+"_"+businessId+")"*/);
					proresult.setCode(ActivitiException.INAUDIT.getCode());
					logger.error(ActivitiException.INAUDIT.getName()+"("+busType+"_"+businessId+")");
					return proresult;
				}
				return activitiToolsService.cancelSubmit(params);
			} else {
				proresult.setMessage(ActivitiException.TASKNOTFOUND.getName()/*+"("+busType+"_"+businessId+")"*/);
				proresult.setCode(ActivitiException.TASKNOTFOUND.getCode());
				logger.error(ActivitiException.TASKNOTFOUND.getName()+"("+busType+"_"+businessId+")");
				return proresult;
			}
		} else {
			if (handle == HandleType.SUBMIT.getHandle()) {
				if (!isExist) {
					return activitiToolsService.startSubmit(params, false);
				}
			}
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if (task == null) {
				// 先判断是否是驳回操作
				if (handle == HandleType.REJECT.getHandle()) {
					// 如果是驳回操作，继续
					// 然后判断有历史记录，没有话，相应抛错。有的话重新发起流程，否则就跑任务不存在。无法进行相应的操作
//					List<HistoricTaskInstance> his = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(deploymenKey + "." + StringUtilsExt.convertNullToString(params.getBusinessId())).list();
					List<HistoricTaskInstance> his = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(deploymentKey + "." + StringUtilsExt.convertNullToString(params.getBusinessId())).list();
					if (his != null) {
						if (his.size() > 0) {//1.1.1判断是否有驳回权限
							if(activitiToolsService.hasRejectEndOption(params)){
								return activitiToolsService.rejectStartSubmit(params);
							}
						} else {//1.1.2未启动过报错
							// 该流程未曾启动过，无效操作
							proresult.setMessage(ActivitiException.NOTEFFECT.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
							proresult.setCode(ActivitiException.NOTEFFECT.getCode());
							logger.error(ActivitiException.NOTEFFECT.getName()+"("+busType+"-"+businessId+"_"+handle+")");
							return proresult;
						}
					}
				}
				// 已终审的退回操作，先判断是否为最后一个节点，只能由最后一个岗位才能退回
				if (handle == HandleType.RETURN.getHandle()) {
					LastNode lastNode = activitiToolsService.isLastNode(params);
					if (lastNode.isLast()) {
						params.setTaskId(lastNode.getUserTaskId());
						activitiToolsService.returnEndTask(params);
					}else{
						proresult.setMessage(ActivitiException.NOTLASTNOTRETURN.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
						proresult.setCode(ActivitiException.NOTLASTNOTRETURN.getCode());
						logger.error(ActivitiException.NOTLASTNOTRETURN.getName()+"("+busType+"-"+businessId+"_"+handle+")");
						return proresult;
					}
				}
				proresult.setMessage(ActivitiException.TASKNOTFOUND.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
				proresult.setCode(ActivitiException.TASKNOTFOUND.getCode());
				logger.error(ActivitiException.TASKNOTFOUND.getName()+"("+busType+"-"+businessId+"_"+handle+")");
				return proresult;
			}
			String busStateFormKey = task.getFormKey();
			Integer busStateFormKeyInteger = activitiToolsService.getFormKey(busStateFormKey);
//			Integer busStateFormKeyInteger = Integer.parseInt(busStateFormKey);
			boolean betweenJump = false;//是否处于跳转单位的状态值内，或者单位录入进行相关的操作
			if (busStateFormKeyInteger > 8000 && busStateFormKeyInteger < 10000) {
				betweenJump = true;
			}
			//取出流程分支控制中的条件
			ActivitiBranchControl activitiBranchControl = new ActivitiBranchControl();
			activitiBranchControl.setProcessKey(deploymentKey);
			activitiBranchControl.setUserTaskDefkey(task.getTaskDefinitionKey());
			List<ActivitiBranchControl> branchList =  activitiBranchControlService.getActivitiBranchControlList(activitiBranchControl);
			if (branchList != null && branchList.size() > 0) {
				params.addCondtion(ActivitiCondition.BRANCH_CONTROL.getCode(), branchList);
			}
			// 将当前节点记录
			ActivitiTaskMoveComboboxShow nowNode = new ActivitiTaskMoveComboboxShow();
			nowNode.setUserTaskId(task.getTaskDefinitionKey());
			nowNode.setUserTaskName(task.getName());

			defId = task.getProcessDefinitionId();
			String processInstanceId = task.getProcessInstanceId();
			// 设置到期时间，如果没有设置，则默认无限大，不到期
//			Date date = null;
//			if (StringUtils.isNullOrBlank(duedate)) {
//				date = DateUtilsExt.parseDate("20991213", "yyyyMMdd");
//			} else {
//				date = DateUtilsExt.parseDate(duedate, "yyyy-MM-dd");
//			}
			// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			// 获取当前活动的id,taskdefKey
			// String localStep = pi.getActivityId();
			actlog.setUserTaskId(pi.getActivityId());
			actlog.setTaskName(task.getName());
			actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
			history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
			actlog.setBusId(businessId);
			receiveActlog.setBusId(businessId);
			actlog.setOperatorId(userCode);
			actlog.setOperatorName(userName);
			actlog.setBusType(deploymentKey);
			receiveActlog.setBusType(deploymentKey);
			actlog.setProcInstId(processInstanceId);
			receiveActlog.setProcInstId(processInstanceId);
			history.setProcInstId(processInstanceId);
			// 判断是否为第一个节点，如果是第一个节点，就改名为提交,对于驳回后的提交
			actlog.setRemark(remark);
			actlog.setTaskId(taskId);
			actlog.setCreatorId(StringUtilsExt.convertNullToString(creatorId));
			actlog.setCreatorName(StringUtilsExt.convertNullToString(creatorName));
			actlog.setUserTaskId(pi.getActivityId());
			actlog.setAssignee(task.getDescription());
			// 已终审
			Integer status = (Integer) TaskStatus.BUSINESSSUCCESS.getStatus();;
			Map<String, Object> variables = new HashMap<String, Object>();
//			variables.put("duedate", date);
			// 目前情况，1就退一步。如果要改掉变成跳转列表，就变成跳转列表，直接使用任务转移
			if (handle == HandleType.RETURN.getHandle()) {
				variables.put("handle", "1.1");
			} else if (handle == HandleType.SUBMIT.getHandle()) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("taskId", task.getId());
				m.put("userTaskId", task.getTaskDefinitionKey());
				if (!activitiToolsService.isFirstNode(m) && !betweenJump) {// 如果不是第一个节点的提交，且不处于跳转区间内，就是重复提交
					proresult.setMessage(ActivitiException.DUPLICATESUBMIT.getName());
					proresult.setCode(ActivitiException.DUPLICATESUBMIT.getCode());
					logger.error(ActivitiException.DUPLICATESUBMIT.getName() + businessId + busType);
					return proresult;
				}
				//如果是提交，将提交的标识改为pass
				variables.put("handle", HandleType.PASS.getHandle().toString());
			} else {
				variables.put("handle", handle.toString());
			}
			//以下注释将不用了，以下注释通过线条是否有驳回和退回操作，但是现在不通过线条来操作，所以不用再进行判断了
//			if (handle != HandleType.PASS.getHandle()) {
//				if (!activitiToolsService.hasRejectOrReturn(params)) {
//					if (handle == HandleType.REJECT.getHandle()) {
//						proresult.setMessage(ActivitiException.NOTREJECT.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
//						proresult.setCode(ActivitiException.NOTREJECT.getCode());
//						logger.error(ActivitiException.NOTREJECT.getName()+"("+busType+"-"+businessId+"_"+handle+")");
//						return proresult;
//					}
//					if (handle == HandleType.RETURN.getHandle()) {
//						proresult.setCode(ActivitiException.NOTRETURN.getCode());
//						proresult.setMessage(ActivitiException.NOTRETURN.getName());
//						logger.error(ActivitiException.NOTRETURN.getCode());
//						return proresult;
//					}
//				}
//			}
			// 记录具体操作日志
			// 如果是第一个节点，就将pass改成submit
			boolean isFirstNode = false;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("taskId", task.getId());
			map.put("userTaskId", task.getTaskDefinitionKey());
			if (handle == HandleType.PASS.getHandle()) {
				if (activitiToolsService.isFirstNode(map)) {
					isFirstNode = true;
				}
			}
			//判断是否为转换后的提交，如果第一个节点，且是pass操作，又是切换类型的提交，必须进行判断，然后将相应的历史删除以及更新
			if (isFirstNode || handle == HandleType.SUBMIT.getHandle()) {
				String pdefid = task.getProcessDefinitionId();
				String beforeType = pdefid.split("\\:")[0];
				AssetActivitiBus aaa = assetActivitiBusService.queryAssetActivitiBusByKey(beforeType);
				beforeType = aaa.getBusType();//可能存在key与type不一样，导致死循环。所以此处必须重新查询一次
				if (!busType.equals(beforeType)) {//判断是否要转换类型进行提交
					ActivitiParams changeProcessParams = new ActivitiParams();
					changeProcessParams.setTaskId(taskId);
					//先将错误的更新到正确的流程上，主要是更新类型，因为此时类型换了，然后在提交里面更改pid
					assetBusHistoryService.updateBusTypeByBusIdAndPid(businessId, processInstanceId, busType);
					assetProcessRemarkService.updateBusTypeByBusIdAndPid(businessId, processInstanceId, deploymentKey);
//					assetBusHistoryService.updatePIdByBusIdAndBusType(businessId, processInstanceId, busType);
//					assetProcessRemarkService.updatePIdByBusIdAndBusType(businessId, processInstanceId, deploymenKey);
					//删除流程流程，错误类型
					activitiToolsService.deleteProcessInstacne(changeProcessParams);//删除原来的历史历程记录
					return activitiToolsService.startSubmit(params, true);//重新启动一个流程
				}
			}
			String nowTaskName = task.getName();
			//获取未完全前的表单类型
			proresult.setPreFormType(activitiToolsService.getFormType(task.getFormKey()));
			//获取未驳回前的userTaskId；
			String rejectOrReturnUserTaskId = task.getTaskDefinitionKey();
			//记录完成的节点id
			history.setCurrentTask(rejectOrReturnUserTaskId);
			//放入条件
			Map<String,Object> condition = new HashMap<String, Object>();
			//获取keymap，拿出单位
			Map<String,Object> startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "startKeyMap");
			
			//实时拿出传来的map，实时更新map
			Map<String,Object> onLineMap = params.getStartKeyMap();
			String onLineOrgCode = MapUtilsExt.getString(onLineMap, "orgCode");
			String onLineOrgId = MapUtilsExt.getString(onLineMap, "orgId");
			String onLineCreator = MapUtilsExt.getString(onLineMap, "creator");
			//放入最新的startKeyMapmap
			if(StringUtils.isNotNullAndBlank(onLineOrgCode)){
				startKeyMap.put("orgCode", onLineOrgCode);
			}
			if(StringUtils.isNotNullAndBlank(onLineOrgId)){
				startKeyMap.put("orgId", onLineOrgId);
			}
			if(StringUtils.isNotNullAndBlank(onLineCreator)){
				startKeyMap.put("creator", onLineCreator);
			}
			variables.put("startKeyMap", startKeyMap);//覆盖原本的map
			
			//放入参数中，用于流程判断
			params.addCondtion(ActivitiCondition.ORG_CODE.getCode(), startKeyMap.get("orgCode"));
			condition = params.getCondition();
			if (!condition.isEmpty()) {
				variables.put("map", condition);
			}
			currentTaskStatus = activitiToolsService.getFormKey(task.getFormKey());
//			currentTaskStatus = NumberUtilsExt.toInt(task.getFormKey(), 0);
			Integer beforeCompleteStatus = activitiToolsService.getFormKey(task.getFormKey());
//			Integer beforeCompleteStatus = Integer.parseInt(task.getFormKey());
			ProcessResult beforeCompleteCheck = activitiToolsService.beforeCompleteCheck(userCode, task, businessId, busType);
			if (!beforeCompleteCheck.getCode().equals(ActivitiException.SUCCESS.getCode())) {
				return beforeCompleteCheck;
			}
			description = StringUtilsExt.convertNullToString(task.getDescription());
			//获取发起者
			String applyuser = activitiToolsService.getSubmitUser(processInstanceId, "applyuser");
			//设置角色相关信息
			roleCode = activitiToolsService.getCurrentRoleCodeIner(businessId);
			roleNameInner = activitiToolsService.getCurrentRoleName(roleCode);
			actlog.setRoleName(roleNameInner);
			actlog.setRoleCode(roleCode);
			params.setApplyuserCode(applyuser);
			//放入参数中，用于流程判断
			params.addCondtion("role_code", roleCode);//角色信息
			params.addCondtion("user_code", userCode);//usercode
			params.addCondtion("bill_org", params.getBillOrgCode());//单据组织编码，可能是跳转的
			//完成任务，除了退回特殊处理
			if(handle == HandleType.RETURNTOSTART.getHandle()){//完全退回用任务跳转，减轻流程图绘制的压力
				//如果不采取这种做法，就必须在每个节点上画一条线到首节点，然后再写上condition。增加流程绘制难度
				ActivitiTaskMoveComboboxShow atmc = activitiToolsService.findFirstNode(task);
				String firstNodeId = atmc.getUserTaskId();
				TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);
				if(StringUtils.isNullOrBlank(firstNodeId)){//如果找不到首节点，直接退回
					ProcessResult error = new ProcessResult();
					error.setCode(ActivitiException.ERROR.getCode());
					error.setMessage("暂时无法直接退回首节点，请尝试退回上一节点");
					return error;
				}
				tfcs.moveTo(atmc.getUserTaskId());
			} else if (handle == HandleType.RETURN.getHandle()) {// 退回上一步，可能存在误差
				TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);
				tfcs.moveBack();
			} else if(handle == HandleType.MOVEBACK.getHandle()){
				TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);
				String moveTaskDef = params.getMoveUserTaskDef();
				if(StringUtils.isNullOrBlank(moveTaskDef)){//如果找不到首节点，直接退回
					ProcessResult error = new ProcessResult();
					error.setCode(ActivitiException.ERROR.getCode());
					error.setMessage("暂时无法操作");
					logger.error("跳转标识为空，类型：" + busType + "业务id：" + businessId);
					return error;
				}
				tfcs.moveTo(moveTaskDef);
			}else {
				try {
					taskService.complete(taskId, variables);
				} catch (Exception e) {
					ProcessResult error = new ProcessResult();
					error.setCode(ActivitiException.ERROR.getCode());
					error.setMessage(ActivitiException.ERROR.getName());
					e.printStackTrace();
					logger.error(e.getMessage());
					return error;
				}
			}
			// 再次查询，查找出现在任务在哪里了，也可能不存在
			pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			actlog.setTaskStatus(TaskStatus.FINISH.getStatus());
			// 首节点，退回驳回重叠标记
			boolean firstNode = false;
			actlog.setCurrentTaskStatus(currentTaskStatus);
			if (pi != null) {
				Task nowTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
				// 记录下一节点名称
				nowTask.getAssignee();
				nextTaskStatus = activitiToolsService.getFormKey(nowTask.getFormKey());
//				nextTaskStatus = NumberUtilsExt.toInt(nowTask.getFormKey(), 0);
				actlog.setNextNodeName(nowTask.getName());
				receiveActlog.setTaskName(nowTask.getName());
				receiveActlog.setTaskId(nowTask.getId());
				actlog.setNextNodeId(nowTask.getTaskDefinitionKey());
				receiveActlog.setUserTaskId(rejectOrReturnUserTaskId);
				actlog.setTaskId(nowTask.getId());
				history.setTaskStatus(TaskStatus.RUNNING.getStatus());

				String currentTaskDef = pi.getActivityId();
				String nextTaskState = nowTask.getFormKey();
				status = activitiToolsService.getFormKey(nextTaskState);
//				status = Integer.parseInt(nextTaskState);
				actlog.setTaskStatus(TaskStatus.CHECKING.getStatus());
				receiveActlog.setTaskStatus(TaskStatus.CHECKING.getStatus());
				if (handle == HandleType.REJECT.getHandle()) {
					isReturnOrRejectStatus = true;
					status = TaskStatus.REJECT.getStatus();
				}
				try {
					bizstatus.setBizStatus(status);
					bizstatus.setBizStatusName(nowTask.getName());
//					bizstatus = assetBizStatusService.queryAssetBizStatus(status);
				} catch (Exception e) {
					proresult.setCode(ActivitiException.ERROR.getCode());
					proresult.setMessage(ActivitiException.ERROR.getName());
					e.printStackTrace();
					return proresult;
				}
//				if(bizstatus == null){
//					proresult.setCode(ActivitiException.ERROR.getCode());
//					proresult.setMessage(ActivitiException.ERROR.getName());
//					logger.error(nowTask.getProcessDefinitionId() + nowTask.getTaskDefinitionKey() + "转态码：" + status + "记录未在数据库中添加");
//					return proresult;
//				}
				//找到未完成前的状态，未审核通过，取未完成前的状态
				AssetBizStatus beforeStatus = new AssetBizStatus();
				beforeStatus.setBizStatus(beforeCompleteStatus);
				beforeStatus.setBizStatusName(task.getName());
//				AssetBizStatus beforeStatus = assetBizStatusService.queryAssetBizStatus(beforeCompleteStatus);
				if(beforeStatus != null){
					bizstatus.setBizStatusName(beforeStatus.getBizStatusName());
				}
//				else{
//					bizstatus.setBizStatusName(nowTaskName);
//				}
				// 退回到首节点，等同于驳回处理，按照 驳回/退回 状态
				if (handle == HandleType.RETURN.getHandle() || handle == HandleType.RETURNTOSTART.getHandle() || handle == HandleType.MOVEBACK.getHandle()) {
					bizstatus.setBizStatusName(TaskStatus.RETUNR.getName());
					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("taskId", nowTask.getId());
					map2.put("userTaskId", nowTask.getTaskDefinitionKey());
					if (handle == HandleType.RETURN.getHandle() || handle == HandleType.RETURNTOSTART.getHandle() || handle == HandleType.MOVEBACK.getHandle()) {
						if (activitiToolsService.isFirstNode(map2)) {
							firstNode = true;
							isReturnOrRejectStatus = true;
							bizstatus.setBizStatus(TaskStatus.REJECT.getStatus());
						}
					}
				}
				 //退回，退回的节点不是首节点
				if (handle == HandleType.RETURN.getHandle() && !firstNode) {
					bizstatus.setBizStatusName(TaskStatus.RETUNR.getName());
				}
				if (handle == HandleType.REJECT.getHandle()) {
					bizstatus.setBizStatusName(TaskStatus.REJECT.getName());
				}
				// 记录具体操作日志
				// 如果是第一个节点，就将pass改成submit
				if (handle == HandleType.PASS.getHandle() && isFirstNode && !betweenJump) {
					actlog.setCheckResult(HandleType.SUBMIT.getHandle());
					optionMessage = HandleType.SUBMIT.getName();
					history.setCheckResult(HandleType.SUBMIT.getHandle());
				} else {
					actlog.setCheckResult(handle);
					
					for (HandleType type : HandleType.values()) {
						if (type.getHandle() == handle) {
							optionMessage = type.getName();
							break;
						}
					}
					history.setCheckResult(handle);
				}
				receiveActlog.setCheckResult(HandleType.RECEIVE.getHandle());

				if(isReturnOrRejectStatus){
					nextTaskStatus = TaskStatus.REJECT.getStatus();
				}
				actlog.setNextTaskStatus(nextTaskStatus);
				receiveActlog.setTaskStatus(nextTaskStatus);
				proresult.setLog(actlog);
				List<ApiUser> apiUserListByOrgCodeList = new ArrayList<ApiUser>();
				//退回到首节点的话，就给发起的人，如果不是首节点，就是全部
				if (handle == HandleType.RETURN.getHandle() && firstNode) {
					//获取到提交者
					ApiUser returnUser = DrcHepler.getInstance().findUseriInfoByUserCode(applyuser);
					apiUserListByOrgCodeList.add(returnUser);
				}else{
					String formKey = nowTask.getFormKey();
					Integer formKeyInteger = activitiToolsService.getFormKey(formKey);
//					Integer formKeyInteger = Integer.parseInt(formKey);
					String billOrgCode = null ;
					if(formKeyInteger > 8500 && formKeyInteger < 10000){//单位变换，调拨区间
//						if(formKeyInteger > 8800 && formKeyInteger < 10000){//单位变换，调拨区间
						billOrgCode = params.getBillOrgCode();
					}
					params.setTaskId(nowTask.getId());
					params.setBillOrgCode(billOrgCode);
					apiUserListByOrgCodeList = activitiToolsService.getNextExcetorApiUsers(params);
//					apiUserListByOrgCodeList = activitiToolsService.getNextExcetorApiUsers(nowTask.getId(), billOrgCode);
				}
				proresult.setNextNodeUser(apiUserListByOrgCodeList);
//				String exactUsers = activitiToolsService.getNextExcetor(nowTask.getId());
				String exactUsers = "";
				if (apiUserListByOrgCodeList != null && apiUserListByOrgCodeList.size() > 0) {
					for (int i = 0, leni = apiUserListByOrgCodeList.size(); i < leni; i++) {
						if(apiUserListByOrgCodeList.get(i) != null){
							exactUsers = exactUsers + apiUserListByOrgCodeList.get(i).getUserCode();
							if(i != leni -1){
								exactUsers += ",";
							}
						}
					}
				}
				if(StringUtils.isNotNullAndBlank(exactUsers)){
					proresult.getLog().setNextExecutorCode(exactUsers);
					ActivitiParams pa = new ActivitiParams();
					pa.setDefId(defId);
					pa.setTaskId(nowTask.getId());
					String findUserCode = "";
					if(apiUserListByOrgCodeList != null && apiUserListByOrgCodeList.size()>0){
						findUserCode = apiUserListByOrgCodeList.get(0).getUserCode();
					}else{
						findUserCode = exactUsers.split("\\,")[0];
					}
//					ProcessResult roleResult1 = activitiToolsService.getCurrentRoleName(findUserCode, pa, nowTask.getTaskDefinitionKey());
//					String roleCode1 = StringUtilsExt.convertNullToString(roleResult1.getCurrentRoleCode());
//					String roleName1 = StringUtilsExt.convertNullToString(roleResult1.getCurrentRoleName());
//					proresult.setNextNodeUser(apiUserList);
				}
				
				ApiUser nextUser = null;
				if (proresult.getNextNodeUser() != null && proresult.getNextNodeUser().size() > 0) {
					nextUser = activitiToolsService.getRandomUser(apiUserListByOrgCodeList);
				}
				if(nextUser != null){
					String pin = "";
					if(StringUtils.isNullOrBlank(nextUser.getMobileNo())){
						pin = nextUser.getUserName()/*+" "+"暂无号码！"*/;
					}else{
						pin = nextUser.getUserName()/*+" "+nextUser.getMobileNo()*/;
					}
					actlog.setNextExecutorName(pin);
					receiveActlog.setOperatorId(nextUser.getUserCode());
					receiveActlog.setOperatorName(nextUser.getUserName());
					receiveActlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
					
				}else{
					proresult.setMessage(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName()/*+"("+busType+"_"+businessId+")"*/);
					proresult.setCode(ActivitiException.GETNEXTUSESRFAIL.getCode());
					logger.error(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName()+"("+busType+"_"+businessId+")");
					return proresult;
				}
				if (handle == HandleType.REJECT.getHandle() || handle == HandleType.RETURN.getHandle()) {
					// 获取角色
					ActivitiParams pa1 = new ActivitiParams();
					pa1.setDefId(defId);
					pa1.setTaskId(nowTask.getId());
//					ProcessResult roleResult = activitiToolsService.getCurrentRoleName(userCode, pa1, rejectOrReturnUserTaskId);
//					roleName = roleResult.getCurrentRoleName();
//					if (StringUtils.isNullOrBlank(roleName)) {
//						roleResult.setMessage(ActivitiException.CURRENTUSERNOTRIGHT.getName()/*+"("+busType+"_"+businessId+")"*/);
//						roleResult.setCode(ActivitiException.CURRENTUSERNOTRIGHT.getCode());
//						logger.error(ActivitiException.CURRENTUSERNOTRIGHT.getName()+"("+busType+"_"+businessId+")");
//						return roleResult;
//					}
				}
				next2TaskStatus = status;
//				if (busState1 > 8000 && busState1 < 10000) {// 如果是在上传附件区间的提交，是通过审核操作，默认先定8000到9000
//					if(handle == HandleType.SUBMIT.getHandle() || handle == HandleType.PASS.getHandle()){
//						actlog.setCheckResult(HandleType.SUBMITNOFIRST.getHandle());//区分于submit
//					}else{
//						actlog.setCheckResult(handle);
//					}
//				}
				actlog.setDescription(description);
				receiveActlog.setDescription(description);
				assetProcessRemarkService.addAssetProcessRemark(actlog);
//				assetProcessRemarkService.addAssetProcessRemark(receiveActlog);//存储接收记录
				// 待办专用处理 保证流程的指向永远是向前的，只保证名称向前，其余所有的参数保持正常，不论什么情况下，都是由上一个岗位指向下一岗位
				if (handle == HandleType.RETURN.getHandle() || handle == HandleType.REJECT.getHandle()) {
					// actlog.setNextNodeId(nowTask.getTaskDefinitionKey());
					actlog.setNextNodeName(nowTask.getName());
					String beforeNodeId = "";
					String beforeNodeName = "";
					List<AssetProcessRemark> beforeNodeList = assetProcessRemarkService.queryBusinessHistory(businessId, processInstanceId);
					//取出remark中的历史记录，找出下一节点指向当前节点的的记录，然后把当前节点拿出来，进而间接找出当前节点的上一节点
					for (AssetProcessRemark assetProcessRemark : beforeNodeList) {
						if (nowTask.getTaskDefinitionKey().equals(assetProcessRemark.getNextNodeId())) {
							beforeNodeId = assetProcessRemark.getUserTaskId();//单前节点usertaskid
							beforeNodeName = assetProcessRemark.getTaskName();//当前节点名称
						}
					}
					actlog.setTaskName(beforeNodeName);
				}
				//如果是第一个岗位，置空，模拟成未进入流程的状态
				if (firstNode) {
					actlog.setTaskName("");
				}
				Map<String,Object> next2Map = new HashMap<String, Object>();
				next2Map.put("taskId", nowTask.getId());
				next2Map.put("currentTaskDefKey", nowTask.getTaskDefinitionKey());
				List<ActivitiUserTaskNode>next2List = activitiToolsService.getNextNode(next2Map);
				nowTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
				if(next2List.size() == 0){
					proresult.setNext2TaskName(null);
				}else{
					//如果多节点
					if (next2List.size() > 1) {
						if (!StringUtilsExt.convertNullToString(next2List.get(0).getUserTaskName()).equals(StringUtilsExt.convertNullToString(next2List.get(1).getUserTaskName()))) {
							if (!StringUtilsExt.convertNullToString(next2List.get(1).getUserTaskName()).equals(StringUtilsExt.convertNullToString(next2List.get(0).getUserTaskName()))) {
								String tempName = next2List.get(0).getUserTaskName() + "(" + next2List.get(1).getUserTaskName() + ")";
								next2List.get(0).setUserTaskName(tempName);
							}
						}
					}
					ActivitiUserTaskNode activitiUserTaskNode = next2List.get(0);
					//结束节点的名称为End
					if("nd".equalsIgnoreCase(activitiUserTaskNode.getUserTaskId())){
						proresult.setNext2TaskName(null);
					}else{
						proresult.setNext2TaskName(activitiUserTaskNode.getUserTaskName());
					}
				}
				
				proresult.setFormType(activitiToolsService.getFormType(nowTask.getFormKey()));

			} else {
				status = TaskStatus.BUSINESSSUCCESS.getStatus();;
				actlog.setNextTaskStatus(TaskStatus.BUSINESSSUCCESS.getStatus());
				actlog.setNextExecutorName(" ");
				actlog.setCheckResult(HandleType.PASS.getHandle());
				optionMessage = HandleType.PASS.getName();
				history.setCheckResult(HandleType.PASS.getHandle());
				actlog.setTaskStatus(TaskStatus.FINISH.getStatus());
				actlog.setNextNodeName(null);
				actlog.setNextNodeId(" ");
				history.setTaskStatus(TaskStatus.FINISH.getStatus());
				// 任务结束，下一节点待办人为空
				actlog.setNextExecutorCode(" ");
				bizstatus.setBizStatus(status);
				bizstatus.setBizStatusName(TaskStatus.BUSINESSSUCCESS.getName());
//				bizstatus = assetBizStatusService.queryAssetBizStatus(status);
				proresult.setNext2Status(0);
				actlog.setDescription(description);
				proresult.setNext2TaskName(null);
				assetProcessRemarkService.addAssetProcessRemark(actlog);
				// bizstatus.setBizStatusName(nowTaskName);
				
				proresult.setFormType(null);
			}
//			if (pi != null) {
//				String forwardUsers = params.getForwardOrDesignUsers();
//				String forwardGroups = params.getForwardOrDesignGroups();
//				if (StringUtils.isNotNullAndBlank(forwardUsers)) {
//					activitiToolsService.forwardOrDesignTaskUsers(businessId, forwardUsers);
//				}
//				if(StringUtils.isNotNullAndBlank(forwardGroups)){
//					activitiToolsService.forwardOrDesignTaskGroups(businessId, forwardGroups);
//				}
//			}
		}

		history.setBusId(businessId);
		history.setOperatorId(userCode);
		history.setBizType(busType);

		assetBusHistoryService.updateHistoryLog(history, null);
		
		optionMessage = optionMessage + ActivitiException.SUCCESS.getName();
		//修改为   XX岗位退回    XX岗位驳回
		if(handle == HandleType.REJECT.getHandle() || handle == HandleType.RETURN.getHandle()){
			String originName = bizstatus.getBizStatusName();
			if(StringUtils.isNullOrBlank(roleName)){
				roleName = "";
			}
			//使用节点名称
//			String finalName = beforCompleteTaskName +  originName;
			//使用角色
			String finalName = roleName +  originName;
			bizstatus.setBizStatusName(finalName);
		}
		proresult.setMessage(optionMessage);
		proresult.setCode(ActivitiException.SUCCESS.getCode());
		proresult.setLog(actlog);
		proresult.setInfo(bizstatus);
		proresult.setBusId(businessId);
		proresult.setNext2Status(next2TaskStatus);
		//next2node信息
//		ProcessResult next2Result = activitiToolsService.getNext2Node(params);
//		proresult.setNext2Status(next2Result.getNext2Status());
//		proresult.setNext2TaskName(next2Result.getNext2TaskName());
//		proresult.setNext2UserTaskId(next2Result.getNext2UserTaskId());
		
		return proresult;
	}

	public Map<String, Object> deleteHistoryTaskByDate(Date date) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<HistoricProcessInstance> hispi = historyService.createHistoricProcessInstanceQuery().finishedBefore(date).list();
		result.put("count", hispi.size());
		for (int i = 0, len = hispi.size(); i < len; i++) {
			String pid = hispi.get(i).getId();
			historyService.deleteHistoricProcessInstance(pid);
		}
		return result;
	}

	public Map<String, Object> deleteHistoryTaskByTask(List<String> ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		for (int i = 0, len = ids.size(); i < len; i++) {
			HistoricTaskInstance histask = historyService.createHistoricTaskInstanceQuery().taskId(ids.get(i)).singleResult();
			String processInstanceId = histask.getProcessInstanceId();
			ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (pi != null) {
				continue;
			}
			count++;
			historyService.deleteHistoricTaskInstance(histask.getId());
		}
		result.put("count", count);
		return result;
	}

	public Map<String, Object> suspendProcess(String processDefinitionKey) {
		Map<String, Object> result = new HashMap<String, Object>();
		repositoryService.suspendProcessDefinitionByKey(processDefinitionKey);
		runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		result.put("message", "挂起流程");

		return result;
	}

	public Map<String, Object> activateProcess(String processDefinitionKey) {
		Map<String, Object> result = new HashMap<String, Object>();
		repositoryService.activateProcessDefinitionByKey(processDefinitionKey);
		result.put("message", "挂起流程");
		return result;

	}

	public boolean existInActiviti(ActivitiParams params) {
		boolean flag = activitiToolsService.existInActiviti(params);
		if(flag){
			return flag;
		}
		return false;
	}
	
	public boolean isFirstSubmit(ActivitiParams params) throws BusinessException{
		return activitiToolsService.isFirstSubmit(params);
	}

	public ProcessResult taskMove(ActivitiParams params) throws BusinessException {
		ProcessResult result = new ProcessResult();
		try {
			result = activitiToolsService.taskMove(params);
		} catch (Exception e) {
			throw new BusinessException(result.getCode(), result.getMessage());
		}
		return result;
	}
	
	public String queryOutBusType(Object busId){
		return activitiToolsService.queryBusinessTypeByBusinessId(StringUtilsExt.convertNullToString(busId));
	}
	
	public ProcessResult getNext2TaskStatus(ActivitiParams params){
		ProcessResult result = new ProcessResult();
		result = activitiToolsService.getNext2Node(params);
		
		return result;
		
	}
	
	public ProcessResult forwardOrDesignTaskUsers(ActivitiParams params){
		ProcessResult checkResult = activitiToolsService.beforeHandleCheck(params);
		if(!checkResult.getCode().equals(ActivitiException.SUCCESS.getCode())){
			return checkResult;
		}
		return activitiToolsService.forwardOrDesignTaskUsers(params.getBusinessId().toString(),params.getForwardOrDesignUsers());
//		return activitiToolsService.forwardOrDesignTaskUsers(params);
	}

	public ProcessResult forwardOrDesignTaskGroups(ActivitiParams params){
		ProcessResult checkResult = activitiToolsService.beforeHandleCheck(params);
		if(!checkResult.getCode().equals(ActivitiException.SUCCESS.getCode())){
			return checkResult;
		}
		return activitiToolsService.forwardOrDesignTaskGroups(params.getBusinessId().toString(), params.getForwardOrDesignGroups());
//		return activitiToolsService.forwardOrDesignTaskGroups(params);
	}
	
	public ProcessResult forceDeleteProcessInstance(ActivitiParams pa){
		String busId = StringUtilsExt.convertNullToString(pa.getBusinessId());
		String taskId = activitiToolsService.queryTaskIdByBusinessIdLike(pa);
//		String taskId = activitiToolsService.queryTaskIdByBussinessIdLike(busId);
		ProcessResult result =new ProcessResult();
		if(StringUtils.isNullOrBlank(taskId)){
			result.setCode(ActivitiException.TASKNOTFOUND.getCode());
			result.setCode(ActivitiException.TASKNOTFOUND.getName());
			logger.error(ActivitiException.TASKNOTFOUND.getName());
			return result;
		}
		ActivitiParams params = new ActivitiParams();
		params.setTaskId(taskId);
		activitiToolsService.deleteProcessInstacne(params);
		
		result.setCode(ActivitiException.SUCCESS.getCode());
		result.setMessage("执行一次删除操作成功，有流程则删除，无流程则不影响！");
		
		return result;
	}
	
	public boolean canReject(ActivitiParams params){
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		List<HistoricTaskInstance> his = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(busType + "." + businessId).list();
		if (his != null) {
			if (his.size() > 0) {
				return true;
			} 
		}
		logger.error("历史中没有进入工作流，无工作流数据，无法驳回");
		return false;
	}
	
	public ProcessResult getBuinessFormAction(ActivitiParams params){
		return activitiToolsService.getCurrentTaskNodePermission(params);
		
	}
	
	public ProcessResult getCurrentRoleCode(Object businessId){
		return activitiToolsService.getCurrentRoleCode(businessId);
	}
	
	public boolean hasHistoryLife(Object businessId) {
		String busId = StringUtilsExt.convertNullToString(businessId);
		if (StringUtils.isNotNullAndBlank(busId)) {
			List<AssetProcessRemark> list = assetProcessRemarkService.queryListByBusId(busId);
			if (list == null || list.size() == 0) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean hasRejectOption(ActivitiParams params){
		return activitiToolsService.hasRejectEndOption(params);
	}
	
	public ProcessResult deleteProcessAndRecord(ActivitiParams params) {
		return activitiToolsService.deleteProcessAndRecord(params);  
		
	}
	
	public void viewRecrod(ActivitiParams params){
		activitiToolsService.isView(params);
		
	}
	
	public void tempSave(ActivitiParams params){
		params.setTemp(true);
		activitiToolsService.tempSave(params);
	}
	
	public void deleteRecord(ActivitiParams params) {
		activitiToolsService.deleteRecord(params);  
	}
}
