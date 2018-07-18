package com.bosssoft.egov.asset.activiti.activitiKit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.openwebflow.ctrl.TaskFlowControlService;
import org.openwebflow.ctrl.TaskFlowControlServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.activiti.entity.ActivitiBizAction;
import com.bosssoft.egov.asset.activiti.entity.ActivitiBranchControl;
import com.bosssoft.egov.asset.activiti.entity.ActivitiButtonShow;
import com.bosssoft.egov.asset.activiti.entity.ActivitiComparator;
import com.bosssoft.egov.asset.activiti.entity.ActivitiCondition;
import com.bosssoft.egov.asset.activiti.entity.ActivitiException;
import com.bosssoft.egov.asset.activiti.entity.ActivitiNode;
import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiPermissionExt;
import com.bosssoft.egov.asset.activiti.entity.ActivitiShowPic;
import com.bosssoft.egov.asset.activiti.entity.ActivitiTaskMoveComboboxShow;
import com.bosssoft.egov.asset.activiti.entity.ActivitiUserTaskNode;
import com.bosssoft.egov.asset.activiti.entity.AssetActivitiBus;
import com.bosssoft.egov.asset.activiti.entity.AssetActivitiConfig;
import com.bosssoft.egov.asset.activiti.entity.AssetBizStatus;
import com.bosssoft.egov.asset.activiti.entity.AssetBusHistory;
import com.bosssoft.egov.asset.activiti.entity.AssetProcessRemark;
import com.bosssoft.egov.asset.activiti.entity.HandleType;
import com.bosssoft.egov.asset.activiti.entity.LastNode;
import com.bosssoft.egov.asset.activiti.entity.OwfActivityPermission;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.activiti.entity.RemarkLogStatements;
import com.bosssoft.egov.asset.activiti.entity.ShowUndoTask;
import com.bosssoft.egov.asset.activiti.entity.TaskStatus;
import com.bosssoft.egov.asset.activiti.service.ActivitiBizActionService;
import com.bosssoft.egov.asset.activiti.service.ActivitiBranchControlService;
import com.bosssoft.egov.asset.activiti.service.ActivitiNextUserService;
import com.bosssoft.egov.asset.activiti.service.ActivitiPermissionExtService;
import com.bosssoft.egov.asset.activiti.service.ActivitiProcessService;
import com.bosssoft.egov.asset.activiti.service.AssetActivitiBusService;
import com.bosssoft.egov.asset.activiti.service.AssetActivitiConfigService;
import com.bosssoft.egov.asset.activiti.service.AssetBizStatusService;
import com.bosssoft.egov.asset.activiti.service.AssetBusHistoryService;
import com.bosssoft.egov.asset.activiti.service.AssetProcessRemarkService;
import com.bosssoft.egov.asset.activiti.service.OwfActivityPermissionService;
import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.common.util.XmlUtilsExt;
import com.bosssoft.egov.asset.drc.DrcHepler;
import com.bosssoft.egov.asset.runtime.web.context.AppContext;
import com.bosssoft.platform.appframe.api.entity.ApiRole;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.common.utils.StringUtils;
import com.bosssoft.platform.page.utils.StringUtil;
import com.bosssoft.platform.runtime.exception.BusinessException;

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
public class ActivitiToolsService {
	private static Logger logger = LoggerFactory.getLogger(ActivitiToolsService.class);

	static final Integer NORMAL_MODIFY = 2;//修正前
	static final Integer TEMP_MODIFY = 3;//如果修正
	static final Integer TEMP_SIZE  = 1;//如果修正
	static final String TEMP_POSITION = "单位录入岗";//暂存岗位名称
	static final String TEMP_NAME = "单据";//暂存名称
	static final String CARD_BILL = "卡片单据";//暂存名称
	static final String USE_BILL = "使用单据";//暂存名称
	static final String USER_BILL = "录入信息";//暂存名称
	static final String DISPOSE_BILL = "处置单据";//暂存名称
	static final String BCPM_BILL = "投融资单据";//暂存名称
	static final String INVENTORY_BILL = "盘点单据";//暂存名称
	static final String REPORT = "报表";
	static final String NB_REPORT = "财政年报";
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
	private TaskFlowControlServiceFactory taskFlowControlServiceFactory;
	@Autowired
	private AssetProcessRemarkService assetProcessRemarkService;
	@Autowired
	private AssetBizStatusService assetBizStatusService;
	@Autowired
	private AssetActivitiBusService assetActivitiBusService;
	@Autowired
	private OwfActivityPermissionService owfPermissionService;
	@Autowired
	private AssetBusHistoryService assetBusHistoryService;
	@Autowired
	private ActivitiProcessService activitiProcessService;
	@Autowired
	private AssetActivitiConfigService activitiConfigService;
	@Autowired
	private ActivitiNextUserService activitiNextUserService;
	@Autowired
	private ActivitiBizActionService activitiBizActionService;
	@Autowired
	private ActivitiBranchControlService activitiBranchControlService;
	@Autowired
	private ActivitiPermissionExtService activitiPermissionExtService;

	/**
	 * @param taskId
	 *            当前任务ID
	 * @param variables
	 *            流程变量
	 * @param activityId
	 *            流程转向执行任务节点ID<br>
	 *            此参数为空，默认为提交操作
	 * @throws Exception
	 */
	private void commitProcess(String taskId, Map<String, Object> variables, String activityId) throws Exception {
		if (variables == null) {
			variables = new HashMap<String, Object>();
		}
		// 跳转节点为空，默认提交操作
		if (StringUtils.isNull(activityId)) {
			taskService.complete(taskId, variables);
		} else {// 流程转向操作
			turnTransition(taskId, activityId, variables);
		}
	}

	/**
	 * 清空指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @return 节点流向集合
	 */
	private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
		// 存储当前节点所有流向临时变量
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		// 获取当前节点所有流向，存储到临时变量，然后清空
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		return oriPvmTransitionList;
	}

	/**
	 * 流程转向操作
	 * 
	 * @param taskId
	 *            当前任务ID
	 * @param activityId
	 *            目标节点任务ID
	 * @param variables
	 *            流程变量
	 * @throws Exception
	 */
	private void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception {
		// 当前节点
		ActivityImpl currActivity = findActivitiImpl(taskId, null);
		// 清空当前流向
		List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

		// 创建新流向
		TransitionImpl newTransition = currActivity.createOutgoingTransition();
		// 目标节点
		ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
		// 设置新流向的目标节点
		newTransition.setDestination(pointActivity);

		// 执行转向任务
		taskService.complete(taskId, variables);
		// 删除目标节点新流入
		pointActivity.getIncomingTransitions().remove(newTransition);

		// 还原以前流向
		restoreTransition(currActivity, oriPvmTransitionList);
	}

	/**
	 * 还原指定活动节点流向
	 * 
	 * @param activityImpl
	 *            活动节点
	 * @param oriPvmTransitionList
	 *            原有节点流向集合
	 */
	private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
		// 清空现有流向
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
		pvmTransitionList.clear();
		// 还原以前流向
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}

	/**
	 * 根据任务ID获取流程定义
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

		if (processDefinition == null) {
			throw new Exception("流程定义未找到!");
		}

		return processDefinition;
	}

	/**
	 * 根据任务ID和节点ID获取活动节点 <br>
	 * 
	 * @param taskId
	 *            任务ID
	 * @param activityId
	 *            活动节点ID <br>
	 *            如果为null或""，则默认查询当前活动节点 <br>
	 *            如果为"end"，则查询结束节点 <br>
	 * 
	 * @return
	 * @throws Exception
	 */
	private ActivityImpl findActivitiImpl(String taskId, String activityId) throws Exception {
		// 取得流程定义
		ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);
		// 获取当前活动节点ID
		if (StringUtils.isNull(activityId)) {
			activityId = findTaskById(taskId).getTaskDefinitionKey();
		}
		// 根据流程定义，获取该流程实例的结束节点
		if (activityId.toUpperCase().equals("END")) {
			for (ActivityImpl activityImpl : processDefinition.getActivities()) {
				List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
				if (pvmTransitionList.isEmpty()) {
					return activityImpl;
				}
			}
		}

		// 根据节点ID，获取对应的活动节点
		ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);

		return activityImpl;
	}

	/**
	 * 根据任务ID获得任务实例
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 * @throws Exception
	 */
	private TaskEntity findTaskById(String taskId) throws Exception {
		TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			throw new Exception("任务实例未找到!");
		}
		return task;
	}

	/**
	 * 中止流程(特权人直接审批通过等)
	 * 
	 * @param taskId
	 */
	public void endProcess(String taskId) throws Exception {
		ActivityImpl endActivity = findActivitiImpl(taskId, "end");
		commitProcess(taskId, null, endActivity.getId());
	}
	
	public String getSubmitUser(String processInstanceId, String variableName) {
		HistoricVariableInstance hisVariable = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).variableName(variableName).singleResult();
		if (hisVariable != null) {
			String value = (String) hisVariable.getValue();
			return value;
		}
		return "";
	}

	public String getHistoryProcessInstanceId(String busId) {

		List<AssetProcessRemark> list = assetProcessRemarkService.queryListByBusId(busId);
		if (list != null && list.size() > 0) {
			String pId = list.get(0).getProcInstId();
			return pId;
		}
		return "";
	}

	// 判断是否为首节点，如果为首节点，退回或者驳回后将删除流程
	@SuppressWarnings("unused")
	public boolean isFirstNode(Map<String, Object> map) throws DocumentException {
		String taskId = MapUtilsExt.getString(map, "taskId");
		String userTaskId = MapUtilsExt.getString(map, "userTaskId");
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 查询出当前的任务，不在跳转列表内
		ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		for (Element sfl : sequenceFlowList) {
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			if (userTaskId.equals(targetRef)) {
				if (sourceRef.contains("startevent")) {
					return true;
				}
			}
		}
		return false;
	}

	//获取首节点的ID已及名称
	public ActivitiTaskMoveComboboxShow findFirstNode(Task task) throws DocumentException {
		ActivitiTaskMoveComboboxShow firstNode = new ActivitiTaskMoveComboboxShow();
		// 查询出当前的任务，不在跳转列表内
		ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		for (Element sfl : sequenceFlowList) {
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			if (sourceRef.contains("startevent")) {
				firstNode.setUserTaskId(targetRef);
				break;
			}
		}
		if(StringUtils.isNotNullAndBlank(firstNode.getUserTaskId())){
			for (Element e : userTaskList) {
				String name = e.attributeValue("name");
				String id1 = e.attributeValue("id");
				if (firstNode.getUserTaskId().equals(id1)) {
					firstNode.setUserTaskName(name);
					break;
				}
			}
		}
		return firstNode;
	}

	
	// 判断是否为首节点，如果为首节点，退回或者驳回后将删除流程
	public ActivitiTaskMoveComboboxShow getFirstNode(Map<String, Object> map) throws DocumentException {
		String taskId = MapUtilsExt.getString(map, "taskId");
		ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String userTaskId = "";
		// 查询出当前的任务，不在跳转列表内
		ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		for (Element sfl : sequenceFlowList) {
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			if (sourceRef.contains("startevent")) {
				userTaskId = targetRef;
			}
		}
		for (Element e : userTaskList) {
//			String assingee = e.attributeValue("assignee");
			String name = e.attributeValue("name");
			String id1 = e.attributeValue("id");
			if (userTaskId.equals(id1)) {
				node.setUserTaskId(id1);
				node.setUserTaskName(name);
			}
		}
		return node;
	}
	
	// 判断是否为最后一个节点。前提要有历史记录，否则无法操作
	// 查出最新的历史流程
	public LastNode isLastNode(ActivitiParams params) throws DocumentException {
		LastNode node = new LastNode();
		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(params.getBusType());

		String businessKey = activitibus.getDeploymentKey() + "." + params.getBusinessId().toString();
		String userCode = params.getUserCode();
		List<HistoricProcessInstance> hisPro = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().asc().list();
		HistoricProcessInstance h = hisPro.get(hisPro.size() - 1);

		List<AssetProcessRemark> remark = assetProcessRemarkService.queryHisByUserIdPidBusId(userCode, h.getId(), params.getBusinessId().toString());

		for (int i = 0, len = remark.size(); i < len; i++) {
			String userTaskId = remark.get(i).getUserTaskId();
			if (StringUtils.isNullOrBlank(userTaskId)) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", h.getProcessDefinitionId());
			map.put("userTaskId", userTaskId);
			if (this.isLastNodeTwo(map)) {
				node.setUserTaskId(userTaskId);
				node.setLast(true);
				return node;
			}

		}
		return node;
	}
	
	/**
	 * 
	 * <p>函数名称：判断是否为最后一几点，仅通过userTaskId  </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 * @throws DocumentException
	 *
	 * @date   创建时间：2017年2月25日
	 * @author 作者：jinbiao
	 */
	public LastNode isLastNodeByUserTaskDefKey(ActivitiParams params) throws DocumentException {
		LastNode node = new LastNode();
//		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(params.getBusType());

		String proId = params.getProcessInstanceId();
//		String businessKey = activitibus.getDeploymentKey() + "." + params.getBusinessId().toString();
//		List<HistoricProcessInstance> hisPro = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().asc().list();
		//通过流程实例Id来获取流程的历史记录，间接来获取最新的一个流程实例，然后获取流程定义id
		List<HistoricProcessInstance> hisPro = historyService.createHistoricProcessInstanceQuery().processInstanceId(proId).orderByProcessInstanceStartTime().asc().list();
		HistoricProcessInstance h = hisPro.get(hisPro.size() - 1);

		String userTaskId = params.getUserTaskId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", h.getProcessDefinitionId());
		map.put("userTaskId", userTaskId);
		if (this.isLastNodeTwo(map)) {
			node.setUserTaskId(userTaskId);
			node.setLast(true);
			return node;
		}

		return node;
	}

	// 判断是否为最后一个节点
	@SuppressWarnings("unused")
	public boolean isLastNodeTwo(Map<String, Object> map) throws DocumentException {
		LastNode node = new LastNode();
		boolean has = false;
		String id = (String) map.get("id");
		String userTaskId = (String) map.get("userTaskId");
		node.setUserTaskId(userTaskId);
		

		ProcessDefinition pd = this.queryProcessDefinitionById(id);

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		// for (Element sfl : sequenceFlowList) {
		// String sourceRef = sfl.attributeValue("sourceRef");
		// String targetRef = sfl.attributeValue("targetRef");
		// if (userTaskId.equals(sourceRef)) {
		// if (targetRef.contains("endevent")) {
		// return true;
		// }
		// }
		// }
		for (Element sfl : sequenceFlowList) {
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			// System.out.println(sourceRef + "___" + targetRef);
			if (userTaskId.equals(sourceRef)) {
				if (targetRef.contains("endevent")) {
					has = true;
					node.setLast(has);
					return has;
				}
				if (targetRef.contains("exclusivegateway")) {
					Map<String, Object> map11 = new HashMap<String, Object>();
					map11.put("id", id);
					map11.put("userTaskId", targetRef);
					has = this.isLastNodeTwo(map11);
					if (has) {
						return has;
					}
				}
			}
		}
		return has;
	}

	/** 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象 */
	public ProcessDefinition queryProcessDefinitionById(String id) {
		// 查询流程定义的对象，使用key模糊查询，防止版本更新造成查询不到流程定义实体
		String key = id.split("\\:")[0];
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKeyLike(key).latestVersion().singleResult();
		return pd;

	}

	/**
	 * 使用部署对象ID和资源图片名称，获取图片的输入流 或者部署名称
	 * */
	public InputStream queryResourceInputStream(ActivitiParams params) {
		return repositoryService.getResourceAsStream(params.getDeploymentId(), params.getResourceName());
	}

	// 下一个节点执行人   慢
	public String getNextExcetor(String taskId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String names = "";
		List<String> users = new ArrayList<String>();

//		String taskId = processResult.getLog().getTaskId();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		map = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "startKeyMap");
		List<OwfActivityPermission> list = owfPermissionService.queryOwfActivityPermissionByProcessDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey());

		if (list != null && list.size() > 0) {
			OwfActivityPermission result = list.get(0);
			String grantedGroups = result.getGrantedGroups();
			String grantedUsers = result.getGrantedUsers();
			if (StringUtils.isNotNullAndBlank(grantedGroups)) {
				String[] groups = grantedGroups.split("\\;");
				for (int i = 0, len = groups.length; i < len; i++) {
					List<String> ids = DrcHepler.getInstance().findUserByGroup(groups[i], map);
 					if (ids.size() > 0) {
						for (int j = 0, lenj = ids.size(); j < lenj; j++) {
							users.add(ids.get(j));
						}
					}
				}
			}
			if (StringUtils.isNotNullAndBlank(grantedUsers)) {
				String[] splitusers = grantedGroups.split("\\;");
				for (int i = 0, len = splitusers.length; i < len; i++) {
					users.add(splitusers[i]);
				}
			}
			String assingee = result.getAssignedUser();
			if (StringUtils.isNotNullAndBlank(assingee)) {
				users.add(assingee);
			}
			// 如果这些地方都没有人，就把XML指定的给他，正常不可能出现这种情况，必须授权，否则就乱了
//			if (!(users.size() > 0)) {
//				users.add(task.getAssignee());
//			}
		}
		// 如果这些地方都没有人，就把XML指定的给他，正常不可能出现这种情况，必须授权，否则就乱了
		else {
			users.add(task.getAssignee());
		}
		for (int i = 0, len = users.size(); i < len; i++) {
			names += users.get(i);
			if (len > 1 && i < len - 1) {
				names += ",";
			}
		}
		return names;

	}
	
	// 下一个节点执行人   慢
	public List<ApiUser> getNextExcetorApiUsers(ActivitiParams params) {
		boolean isSpecial = false;//是否特殊授权
//		public List<ApiUser> getNextExcetorApiUsers(String taskId,String billOrgCode) {
		List<ApiUser> apiUserList = new ArrayList<ApiUser>();
		boolean isExtPermission = false;//是否特殊授权
		Map<String, Object> mapStart = new HashMap<String, Object>();
		String taskId = params.getTaskId();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//流程启动的时候放入的原始map，包含：单位信息，提交人
		mapStart = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "startKeyMap");
		String orignOrgCode = (String) mapStart.get("orgCode");//原始提交单位编码
		String creatorCode = (String) mapStart.get("creator");//提交人
		//替换成单据的map
		Map<String, Object> billOrgCodeMap = new HashMap<String, Object>();
		String billOrgCode = params.getBillOrgCode();
		if(billOrgCode != null){//如果不为空，则说明单据需要变换单位，则取下一步的时候，从单据中拿出单位进行查询
			billOrgCodeMap.putAll(mapStart);
			billOrgCodeMap.put("orgCode", billOrgCode);
		}
		ActivitiPermissionExt activitiPermissionExt = new ActivitiPermissionExt();//初始化特殊查询实体
		activitiPermissionExt.setActivityKey(task.getTaskDefinitionKey());//taskDefKey，usertask10，当前节点的任务所在
		activitiPermissionExt.setProcessDefinition(params.getBusType());//业务类型
		activitiPermissionExt.setEnable("1");//取出启用的
		List<OwfActivityPermission> list = owfPermissionService.queryOwfActivityPermissionByProcessDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		if (list != null && list.size() > 0) {
			OwfActivityPermission result = list.get(0);
			String grantedGroups = result.getGrantedGroups();
			String grantedUsers = result.getGrantedUsers();
			if (StringUtils.isNotNullAndBlank(grantedGroups)) {
				String[] groups = grantedGroups.split("\\;");
				List<String> gourpList = Arrays.asList(groups);
				activitiPermissionExt.setProcessDefinition(params.getBusType());//当前流程的业务类型
				//O，根据单位进行角色查找
				if(gourpList.contains("O") || gourpList.contains("o") || gourpList.contains("0")){
					isSpecial = true;
					activitiPermissionExt.setDesignByOrg("1");//找出当前节点下，根据单位进行授权的额外记录
					List<ActivitiPermissionExt> activitiPermissionExtList = activitiPermissionExtService.getActivitiPermissionExtList(activitiPermissionExt );
					//额外授权记录可能取出多条记录，根据单位去匹配具体那一条，如果存在多条，那就第一条了~~
					if(activitiPermissionExtList != null && activitiPermissionExtList.size() > 0){
						for (ActivitiPermissionExt activitiPermissionExt2 : activitiPermissionExtList) {
							String[] designOrgs = activitiPermissionExt2.getManageOrgs().split("\\,");
							List<String> designOrgList = Arrays.asList(designOrgs);
							if(designOrgList.contains(orignOrgCode)){//提交单位在所配置的单位里面
								if(StringUtil.isNotEmpty(activitiPermissionExt2.getAssignedUsers())){
									String[] grantUsers = activitiPermissionExt2.getAssignedUsers().split("\\,");
									for (String userCode : grantUsers) {
										ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(userCode);
										if(apiUser != null){
											apiUserList.add(apiUser);
											isExtPermission = true;
										}
									}
								}else{
									logger.info("额外授权人为空，请正确配置！ACTIVITI_PERMISSION_EXT");
								}
								break;
							}
						}
					}
				}
				//B，根据业务进行查找
				if (gourpList.contains("B") || gourpList.contains("b")) {
					isSpecial = true;
					boolean hasRecord = false;
					activitiPermissionExt.setDesignByBus("1");
					List<ActivitiPermissionExt> activitiPermissionExtList = activitiPermissionExtService.getActivitiPermissionExtList(activitiPermissionExt );
					if(activitiPermissionExtList != null && activitiPermissionExtList.size() > 0){
						for (ActivitiPermissionExt activitiPermissionExt2 : activitiPermissionExtList) {
							if (StringUtil.isNotEmpty(activitiPermissionExt2.getAssignedUsers())) {
								String[] grantUsers = activitiPermissionExt2.getAssignedUsers().split("\\,");
								for (String userCode : grantUsers) {
									if(apiUserList.size() > 1){
										for (ApiUser apiUsers : apiUserList) {
											if(apiUsers.getUserCode().equals(userCode)){
												hasRecord = true;
												break;
											}
										}
									}
									if(!hasRecord){//人物不存在时候，加入这个人
										ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(userCode);
										if(apiUser != null){
											apiUserList.add(apiUser);
											isExtPermission = true;
										}
									}
								}
								break;
							}else{
								logger.info("额外授权人为空，请正确配置！ACTIVITI_PERMISSION_EXT");
							}
						}
					}
				}
				//S，根据提交者进行查找
				if (gourpList.contains("S") || gourpList.contains("s")) {
					isSpecial = true;
					activitiPermissionExt.setDesignBySubmit("1");
					List<ActivitiPermissionExt> activitiPermissionExtList = activitiPermissionExtService.getActivitiPermissionExtList(activitiPermissionExt );
					boolean hasRecord = false;
					if(activitiPermissionExtList != null && activitiPermissionExtList.size() > 0){
						for (ActivitiPermissionExt activitiPermissionExt2 : activitiPermissionExtList) {
							String[] designSubmits = activitiPermissionExt2.getSumits().split("\\,");
							List<String> designSubmitList = Arrays.asList(designSubmits);
							if(designSubmitList.contains(creatorCode)){
								if(StringUtil.isNotEmpty(activitiPermissionExt2.getAssignedUsers())){
									String[] grantUsers = activitiPermissionExt2.getAssignedUsers().split("\\,");
									for (String userCode : grantUsers) {
										ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(userCode);
										if(apiUser != null){
											if (apiUserList.size() > 1) {
												for (ApiUser apiUsers : apiUserList) {
													if (apiUsers.getUserCode().equals(userCode)) {
														hasRecord = true;
														break;
													}
												}
											}
											if (!hasRecord) {// 人物不存在时候，加入这个人
												if (apiUser != null) {
													apiUserList.add(apiUser);
													isExtPermission = true;
												}
											}
											isExtPermission = true;
										}
									}
								}
								break;
							}
						}
					}
				}
				if (!isSpecial && apiUserList.size() == 0) {//如果是额外的权限表获取，就默认
//					if (!isExtPermission || apiUserList.size() == 0) {//如果是额外的权限表获取，就默认
					// 否则，默认
					if (billOrgCode != null) {
						apiUserList = DrcHepler.getInstance().findUserByGroups(gourpList, billOrgCodeMap);
					}
					if (billOrgCode == null) {
						apiUserList = DrcHepler.getInstance().findUserByGroups(gourpList, mapStart);
					}
				}
			}
			if (StringUtils.isNotNullAndBlank(grantedUsers)) {
				boolean hasRecord = false;
				String[] splitusers = grantedUsers.split("\\;");
				for (int i = 0, len = splitusers.length; i < len; i++) {
					//不知道属性是否已经add进去？
					ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(splitusers[i]);
					if(apiUserList.size() > 1){
						for (ApiUser apiUsers : apiUserList) {
							if(apiUsers.getUserCode().equals(apiUser.getUserCode())){
								hasRecord = true;
								break;
							}
						}
					}
					if(!hasRecord){//人物不存在时候，加入这个人
						apiUserList.add(apiUser);
					}
					apiUserList.add(apiUser);
				}
			}
		}
		return apiUserList;
	}

	public List<ApiUser> getNextNodeUser(String exactUsers) {
		List<ApiUser> users = new ArrayList<ApiUser>();

		String[] userCodes = exactUsers.split("\\,");
		for (int i = 0, len = userCodes.length; i < len; i++) {
			ApiUser user = DrcHepler.getInstance().findUseriInfoByUserCode(userCodes[i]);
			users.add(user);
		}
		return users;
	}

	public ProcessResult getCurrentRoleName(String userCode,  ActivitiParams params, String taskDefKey) throws DocumentException {
		String appId = AppContext.getAppContext().getAppConfiguration().getAppId();
		ProcessResult proresult = new ProcessResult();
		List<ApiRole> roles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, appId);
		String roleName = "";
		String roleCode = "";
		if(roles == null || roles.size() == 0){
			proresult.setCurrentRoleName(roleName);
			proresult.setCurrentRoleCode(roleCode);
			return proresult; 
		}
		// 获取到当前用户在表中的角色信息
		List<OwfActivityPermission> listPermission = owfPermissionService.queryOwfActivityPermissionByProcessDefId(params.getDefId(), taskDefKey);
		if (listPermission == null || listPermission.size() == 0) {
//			Map<String, Object> info = new HashMap<String, Object>();
//			info.put("id", params.getTaskId());
//			info.put("userCode", userCode);
//			List<ActivitiTaskMoveComboboxShow> userInXml = this.queryUserTaskIdByUserInXml(info);
//			if (userInXml.size() == 0) {
//				proresult.setMessage(ActivitiException.GETPERMISSION.getName());
//				proresult.setCode(ActivitiException.GETPERMISSION.getCode());
//				return proresult;
//			} else {
//				roleName = roles.get(0).getRoleName();
//				roleCode = roles.get(0).getRoleCode();
//			}
		} else {
			String[] groups = listPermission.get(0).getGrantedGroups().split("\\;");
			boolean stop = false;
			for (int i = 0, leni = roles.size(); i < leni; i++) {
				for (int j = 0, lenj = groups.length; j < lenj; j++) {
					if (groups[j].equals(roles.get(i).getRoleCode())) {
						roleName = roles.get(i).getRoleName();
						roleCode = roles.get(i).getRoleCode();
						stop = true;
						break;
					}
				}
				if (stop) {
					break;
				}
			}
		}
		proresult.setCurrentRoleName(roleName);
		proresult.setCurrentRoleCode(roleCode);
		return proresult;
	}

	// 判断任务是否存在
	public boolean existInActiviti(ActivitiParams params) {
		if (StringUtils.isNotNullAndBlank(this.queryTaskIdByBusinessIdLike(params))) {
			return true;
		}
		return false;
	}

	public ActivitiButtonShow activitiButtonShowOrDisable(ActivitiParams params) throws DocumentException {
		ActivitiButtonShow bntShow = new ActivitiButtonShow();

		params.setHandle(HandleType.RETURN.getHandle());
		boolean flag = hasRejectOrReturn(params);
		bntShow.setReturnBtn(flag);

		params.setHandle(HandleType.REJECT.getHandle());
		flag = hasRejectOrReturn(params);
		bntShow.setRejectBtn(flag);

		return bntShow;
	}

	public boolean hasRejectOrReturn(ActivitiParams params) throws DocumentException {

		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String userTaskId = task.getTaskDefinitionKey();
		if (StringUtils.isNotNullAndBlank(params.getTaskId())) {
			userTaskId = params.getTaskId();
		}
		Integer handle = params.getHandle();
		ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams paramss = new ActivitiParams();
		paramss.setDeploymentId(deploymentId);
		paramss.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(paramss);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		@SuppressWarnings("unused")
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		for (Element sfl : sequenceFlowList) {
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			// System.out.println(sourceRef + "___" + targetRef);
			if (userTaskId.equals(sourceRef)) {
				if (targetRef.contains("usertask")) {
					Element e = sfl.element("conditionExpression");
					if (e == null) {
						continue;
					} else {
						String option = StringUtilsExt.convertNullToString(e.getText());
						// System.out.println(option);
						if (option.contains(handle.toString())) {
							return true;
						}
					}
				}
				if (targetRef.contains("exclusivegateway")) {
					ActivitiParams paramsss = params;
					paramsss.setTaskId(targetRef);
					if (hasRejectOrReturn(paramsss)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 功能说明：查找用户（userCode)在XML部署文件中所对应的节点id，递归调用，找出节点名称以及节点id
	 * 
	 * @param info
	 * @return
	 * @throws DocumentException
	 * @date 创建时间：2016年11月14日
	 * @author 作者：jinbiao
	 */

	@SuppressWarnings("rawtypes")
	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdByUserInXml(Map<String, Object> info) throws DocumentException {
		// public Map<String, Object> queryUserTaskIdByUserInXml(Map<String,
		// Object> info) throws DocumentException {
		ProcessDefinition pd = null;
		List<ActivitiTaskMoveComboboxShow> result = new ArrayList<ActivitiTaskMoveComboboxShow>();

		String id = MapUtilsExt.getString(info, "id");
		// 1.新增
		Task task = taskService.createTaskQuery().taskId(id).singleResult();
		String userCode = MapUtilsExt.getString(info, "userCode");
		// 查询出当前的任务，不在跳转列表内
		Task nowUserTask = taskService.createTaskQuery().taskId(id).singleResult();
		if (nowUserTask != null) {
			pd = this.queryProcessDefinitionById(nowUserTask.getProcessDefinitionId());
		} else {
			pd = this.queryProcessDefinitionByDeploymentId(id);
		}

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		for (Element e : userTaskList) {
			String assingee = e.attributeValue("assignee");
			String name = e.attributeValue("name");
			String id1 = e.attributeValue("id");
			if (userCode.equals(assingee)) {
				ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
				node.setUserTaskId(id1);
				node.setUserTaskName(name);
				result.add(node);
				// map.put("userTaskId", id1);
				// map.put("userTaskName", name);
				// break;
			}
		}
		// 如果在xml里面没找到，就必须去授权中找
		// if (map.isEmpty()) {
		if (task != null) {
			List<OwfActivityPermission> listper = owfPermissionService.queryOwfActivityPermissionByProcessDefId(task.getProcessDefinitionId());
			if (listper == null || listper.size() == 0) {
				return result;
			}
			// 通过用户标识取到用户所在的组，然后用该返回
			// 有可能有多个组,调用德寿方法
			List<String> groupIds = new ArrayList<String>();
			String appId = AppContext.getAppContext().getAppConfiguration().getAppId();
			List<ApiRole> apiRoles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, appId);
			if (apiRoles != null && apiRoles.size() > 0) {
				for (ApiRole apiRole : apiRoles) {
					groupIds.add(apiRole.getRoleCode());
				}
			} else {
				return result;
			}
			// 找出组
			String userTaskId = "";
			String userTaskName = "";
			Map<String, Object> infoTemp = new HashMap<String, Object>();
			infoTemp.put("id", task.getProcessDefinitionId());
			for (int j = 0, lenj = groupIds.size(); j < lenj; j++) {
				for (int i = 0, len = listper.size(); i < len; i++) {
					if (StringUtils.isNotNullAndBlank(listper.get(i).getGrantedGroups())) {
						if (listper.get(i).getGrantedGroups().contains(groupIds.get(j))) {
							ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
							userTaskId = listper.get(i).getActivityKey();
							node.setUserTaskId(userTaskId);
							infoTemp.put("userTaskId", userTaskId);
							userTaskName = (String) this.queryUserTaskName(infoTemp).get("userTaskName");
							node.setUserTaskName(userTaskName);
							if (!result.contains(node)) {
								result.add(node);
							}
						}
					}
					if (StringUtils.isNotNullAndBlank(listper.get(i).getAssignedUser())) {
						if (listper.get(i).getAssignedUser().contains(userCode)) {
							userTaskId = listper.get(i).getActivityKey();
							ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
							node.setUserTaskId(userTaskId);
							infoTemp.put("userTaskId", userTaskId);
							userTaskName = (String) this.queryUserTaskName(infoTemp).get("userTaskName");
							node.setUserTaskName(userTaskName);
							if (!result.contains(node)) {
								result.add(node);
							}
						}
					}
					if (StringUtils.isNotNullAndBlank(listper.get(i).getGrantedUsers())) {
						if (listper.get(i).getGrantedUsers().contains(userCode)) {
							ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
							userTaskId = listper.get(i).getActivityKey();
							node.setUserTaskId(userTaskId);
							infoTemp.put("userTaskId", userTaskId);
							userTaskName = (String) this.queryUserTaskName(infoTemp).get("userTaskName");
							node.setUserTaskName(userTaskName);
							if (!result.contains(node)) {
								result.add(node);
							}
						}
					}
				}
			}
		}
		// 将节点排序
		Comparator comparator = new ActivitiComparator();
		Collections.sort(result, comparator);
		// }
		return result;
	}

	/**
	 * 功能说明：某个流程下，某个taskId所对应的username名字
	 * 
	 * @param info
	 * @return
	 * @throws DocumentException
	 * @date 创建时间：2016年11月14日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> queryUserTaskName(Map<String, Object> info) throws DocumentException {
		ProcessDefinition pd = null;
		Map<String, Object> map = new HashMap<String, Object>();

		String id = MapUtilsExt.getString(info, "id");
		// 1.新增
		String userTaskId = MapUtilsExt.getString(info, "userTaskId");
//		String userCode = MapUtilsExt.getString(info, "userCode");
		// 查询出当前的任务，不在跳转列表内
		pd = this.queryProcessDefinitionById(id);

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		for (Element e : userTaskList) {
			String name = e.attributeValue("name");
			String id1 = e.attributeValue("id");
			if (userTaskId.equals(id1)) {
				map.put("userTaskId", id1);
				map.put("userTaskName", name);
				break;
			}
		}
		return map;
	}

	/** 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象 */
	public ProcessDefinition queryProcessDefinitionByDeploymentId(String deploymentId) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
		return pd;
	}

	public String queryTaskIdByBusinessId2(ActivitiParams params) {
		String busType = params.getBusType();
		AssetActivitiBus activitibus = params.getActivitiBus();
		if (activitibus == null) {
			activitibus = assetActivitiBusService.queryAssetActivitiBus(busType);
		}
		if (activitibus == null) {
			return "";
		}
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String businessKey = activitibus.getDeploymentKey() + "." + businessId;
		// 多执行的呢。？ 购物流程,无法成立
		List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).orderByTaskCreateTime().asc().list();
		if (taskList != null && taskList.size() > 1) {
			//如果快速点击，出现两个重复，就删除掉其中多余的一个
			for (int i = 0, len = taskList.size(); i < len - 1; i++) {
				params.setTaskId(taskList.get(i).getId());
				this.deleteProcessInstacne(params);
				assetBusHistoryService.deleteByBusIdAndPId(businessId, taskList.get(i).getProcessInstanceId());
				assetProcessRemarkService.deleteByBusIdAndPId(businessId, taskList.get(i).getProcessInstanceId());
			}
		} else if (taskList != null && taskList.size() == 0) {
			return "";
		} else if (taskList == null) {
			return "";
		}

		List<Task> taskListNew = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).orderByTaskCreateTime().asc().list();

		return taskListNew.get(0).getId();
	}
	
	public String queryTaskIdByBusinessIdLike(ActivitiParams params){
		String busId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String like3 = "%"+busId+"%";
		List<Task> list = taskService.createTaskQuery().processInstanceBusinessKeyLike(like3).list();
		if (list != null && list.size() > 1) {
			//如果快速点击，出现两个重复，就删除掉其中多余的一个
			for (int i = 0, len = list.size(); i < len - 1; i++) {
				ActivitiParams paramss = new ActivitiParams();
				paramss.setTaskId(list.get(i).getId());
				this.deleteProcessInstacne(paramss);
				assetBusHistoryService.deleteByBusIdAndPId(busId, list.get(i).getProcessInstanceId());
				assetProcessRemarkService.deleteByBusIdAndPId(busId, list.get(i).getProcessInstanceId());
			}
		} else if (list != null && list.size() == 0) {
			return "";
		} else if (list == null) {
			return "";
		}
		List<Task> taskListNew = taskService.createTaskQuery().processInstanceBusinessKeyLike(like3).list();

		return taskListNew.get(0).getId();
	}

	/**
	 * 后台部分 后台相关~~
	 */

	public Map<String, Object> deleteProcessInstacne(ActivitiParams params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String taskId = params.getTaskId();
		// String taskId = MapUtilsExt.getString(map, "taskId");
		if (StringUtils.isNullOrBlank(taskId)) {
//			taskId = this.queryTaskIdByBusinessIdLike(params);
//			taskId = this.queryTaskIdByBusinessId2(params);
			result.put("message", "任务id为空");
			return result;
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(task != null){
			String pid = task.getProcessInstanceId();
			
			String message = params.getMessage();
			if(StringUtils.isNotNullAndBlank(message)){
				runtimeService.deleteProcessInstance(pid, message);
			}else{
				runtimeService.deleteProcessInstance(pid, "手动删除任务!");
			}
		}
		result.put("message", "删除成功！");

		return result;
	}
	
	/**
	 * 判断是否为第一次提交
	 */
	public boolean isFirstSubmit(ActivitiParams params) throws BusinessException {
		List<AssetProcessRemark> list =  assetProcessRemarkService.queryListByBusId(StringUtilsExt.convertNullToString(params.getBusinessId()));
		if (list.size() > 2) {
			return false;
		}
		return true;
	}
	
	public boolean canCancelSubmit(ActivitiParams params) {
		List<AssetProcessRemark> list = assetProcessRemarkService.queryBusinessHistory(params.getBusinessId().toString(), params.getProcessInstanceId());
		if (list.size() > 2) {
			return false;
		}
		return true;
	}
	

	/**
	 * 功能说明：通过任务id获取这个流程任务经历那些环节，相应的节点信息,也就是跳转列表，可能需要排除自己所在的节点
	 * 
	 * @param taskId
	 * @return
	 * @throws DocumentException
	 * @date 创建时间：2016年11月14日
	 * @author 作者：jinbiao
	 */
	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByBusinessId(ActivitiParams params) throws DocumentException {
		List<ActivitiTaskMoveComboboxShow> list = new ArrayList<ActivitiTaskMoveComboboxShow>();
		ProcessDefinition pd = null;
		// 查询出当前的任务，不在跳转列表内
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		String id = "";
		if (StringUtils.isNullOrBlank(taskId)) {
			AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(params.getBusType());
//			if (activitibus == null) {
				// proresult.setMessage(ActivitiException.BUSNAMENOTFOUND.getName());
				// proresult.setCode(ActivitiException.BUSNAMENOTFOUND.getCode());
				// return proresult;
//				return list;
//			}

			String businessKey = activitibus.getDeploymentKey() + "." + params.getBusinessId();
			List<HistoricTaskInstance> hisl = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).list();
			if (hisl == null) {
//				logger.debug(ActivitiException.HISTORYNOTFOUND.getName());
				return list;
			}
			if (!(hisl.size() > 0)) {
//				logger.debug(ActivitiException.HISTORYNOTFOUND.getName());
				return list;
			}
			HistoricTaskInstance task = hisl.get(0);
			id = task.getProcessDefinitionId();

		} else {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			id = task.getProcessDefinitionId();
		}
		// Task nowUserTask =
		// taskService.createTaskQuery().taskId(taskId).singleResult();
		pd = this.queryProcessDefinitionById(id);
		if (pd == null) {
//			logger.debug(ActivitiException.VERSIONUPDATE.getName());
			return list;
		}
		// nowUserTaskName = nowUserTask.getTaskDefinitionKey();
		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// key
		// String bpmnIdIsKey = process.attributeValue("id");
		// name
		// String bpmnName = process.attributeValue("name");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		for (Element e : userTaskList) {
			ActivitiTaskMoveComboboxShow show = new ActivitiTaskMoveComboboxShow();
			String name = e.attributeValue("name");
			String id1 = e.attributeValue("id");
			// if (StringUtils.isNotNullAndBlank(nowUserTaskName) &&
			// !nowUserTaskName.equals(id1)) {
			// System.out.println(id1 + name);
			show.setUserTaskId(id1);
			show.setUserTaskName(name);
			// id = buniness_key.split("\\.")[1];
			list.add(show);
			// }
		}
		// 将节点排序
		@SuppressWarnings("rawtypes")
		Comparator comparator = new ActivitiComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public boolean isExistProcess(String deploymentKey) {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(deploymentKey).active().list();
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
	 * map集合的key：表示坐标x,y,width,height map集合的value：表示坐标对应的值
	 */
	public Map<String, Object> queryCoordingByBusinessId(ActivitiParams params) {

		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		// 存放坐标
		Map<String, Object> map = new HashMap<String, Object>();
		// 使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		// 流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 获取当前活动的ID
		String activityId = pi.getActivityId();
		// 获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);// 活动ID

		// 获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		map.put("taskId", activityId);
		map.put("taskName", task.getName());
		return map;
	}
	
	/**
	 * 二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中
	 * map集合的key：表示坐标x,y,width,height map集合的value：表示坐标对应的值
	 */
	public Map<String, Object> queryCoordingByTask(String taskId) {
		// 存放坐标
		Map<String, Object> map = new HashMap<String, Object>();
		// 使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 获取流程定义的ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 获取流程定义的实体对象（对应.bpmn文件中的数据）
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		// 流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 获取当前活动的ID
		String activityId = pi.getActivityId();
		// 获取当前活动对象
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);// 活动ID

		// 获取坐标
		map.put("x", activityImpl.getX());
		map.put("y", activityImpl.getY());
		map.put("width", activityImpl.getWidth());
		map.put("height", activityImpl.getHeight());
		map.put("taskId", activityId);
		map.put("taskName", task.getName());
		return map;
	}
	

	// 不查询数据库，直接使用deploymentKey
	public String queryBusinessTypeByTaskId2(String taskId) {
		// 1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 4：使用流程实例对象获取BUSINESS_KEY，然后获取相应的业务数据
		String businesskey = pi.getBusinessKey();
		// 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String key = "";
		if (StringUtils.isNotNullAndBlank(businesskey)) {
			// 截取字符串，取buniness_key小数点的第1个值
			key = businesskey.split("\\.")[0];
		}
		// AssetActivitiBus activitibus =ffddfd
		// assetActivitiBusService.queryAssetActivitiBusByKey(key);
		// if (activitibus == null) {
		// return "";
		// }
		// return StringUtilsExt.convertNullToString(activitibus.getBusType());
		return key;
	}
	public String queryBusinessIdByTaskId2(String taskId) {
		// 1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 4：使用流程实例对象获取BUSINESS_KEY，然后获取相应的业务数据
		String businesskey = pi.getBusinessKey();
		// 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String key = "";
		if (StringUtils.isNotNullAndBlank(businesskey)) {
			// 截取字符串，取buniness_key小数点的第1个值
			key = businesskey.split("\\.")[1];
		}
		// AssetActivitiBus activitibus =ffddfd
		// assetActivitiBusService.queryAssetActivitiBusByKey(key);
		// if (activitibus == null) {
		// return "";
		// }
		// return StringUtilsExt.convertNullToString(activitibus.getBusType());
		return key;
	}

	public String queryBusinessTypeByBusinessId(String busId){
		List<AssetBusHistory> list = assetBusHistoryService.queryCheckListByBusinessId(busId);
		String  tempBusType = this.getTempBusType(busId);
		if (list == null || list.size() == 0) {
			if(StringUtils.isNotNullAndBlank(tempBusType)){
				return tempBusType;
			}
			return "";
		}
		String busType = list.get(0).getBizType();
		return busType;
	}

	public String queryBusinessTypeByTaskId(String taskId) {
		// 1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 4：使用流程实例对象获取BUSINESS_KEY，然后获取相应的业务数据
		String businesskey = pi.getBusinessKey();
		// 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String key = "";
		if (StringUtils.isNotNullAndBlank(businesskey)) {
			// 截取字符串，取buniness_key小数点的第1个值
			key = businesskey.split("\\.")[0];
		}
		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBusByKey(key);
		if (activitibus == null) {
			return "";
		}
		return StringUtilsExt.convertNullToString(activitibus.getBusType());
	}

	public String remarkProcess(AssetProcessRemark remark) {
		String log = "";
		// String operator = remark.getOperatorName();
		// String time =
		// DateUtilsExt.formatDate(DateUtilsExt.parseDate(remark.getOperateDate()),
		// "yyyy-MM-dd HH:mm");
		// String taskName = remark.getTaskName();
		Integer handle = remark.getCheckResult();

		String checkResult = "";
		for (HandleType type : HandleType.values()) {
			if (type.getHandle() == handle) {
				checkResult = type.getName();
				break;
			}
		}
		log = checkResult/* + "操作"*/;

		return log;
	}
	


	public List<ProcessDefinition> queryProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
		return list;
	}

	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：原本任务已结束，驳回或者退回，但是取消驳回或者退回， 任务又要是结束状态，手动移动
	 *
	 * </p>
	 *<p>参数说明：</p>
	 *
	 * @date   创建时间：2017年2月10日
	 * @author 作者：jinbiao
	 * @throws Exception 
	 */
	public void cancelToEnd(ActivitiParams params) throws Exception{
		String taskId = params.getTaskId();
		this.endProcess(taskId);
		
	}

	public List<ShowUndoTask> showUndoTaskBig(ActivitiParams params, List<ShowUndoTask> total, List<Task> list) {
		String formKey = "";
		List<String> bs = new ArrayList<String>();
		Map<String,AssetActivitiBus> aabMap = new HashMap<String, AssetActivitiBus>();
		Map<Integer,AssetBizStatus> absMap = new HashMap<Integer, AssetBizStatus>();
		List<AssetBizStatus> listAbs = assetBizStatusService.getAssetBizStatusList(null);
		for (AssetBizStatus assetBizStatus : listAbs) {
			absMap.put(assetBizStatus.getBizStatus(), assetBizStatus);
		}
		List<AssetActivitiBus> listAABAll = assetActivitiBusService.getAssetActivitiBusList();
		for (AssetActivitiBus assetActivitiBus : listAABAll) {
			aabMap.put(assetActivitiBus.getDeploymentKey(), assetActivitiBus);
		}
		for (int i = 0, len = list.size(); i < len; i++) {
			String taskId = list.get(i).getId();
			String businessType = this.queryBusinessTypeByTaskId2(taskId);
			if (StringUtils.isNullOrBlank(businessType)) {
				return total;
			}
			String bigType = "";
			AssetActivitiBus big = aabMap.get(businessType);
//			AssetActivitiBus big = assetActivitiBusService.queryAssetActivitiBusByKey(businessType);
			bigType = businessType.split("\\_")[0];
			if (!StringUtils.isNullOrBlank(big.getBusPtype())) {
				bigType = big.getBusPtype();
			}
			formKey = list.get(i).getFormKey();
			// formKey = this.queryFormKeyByTaskId(taskId);
			Integer t = this.getFormKey(formKey);
//			Integer t = Integer.parseInt(formKey);
			if (t <= 1000) {
				bigType += "_REJECT";
			}
			if (!bs.contains(bigType)) {
				ShowUndoTask un = new ShowUndoTask();
//				for (int j = 0, lenj = listAABAll.size(); j < lenj; j++) {
//					if (businessType.equals(listAABAll.get(j).getDeploymentKey())) {
						AssetActivitiBus aab = new AssetActivitiBus();
						AssetActivitiBus aab1 = aabMap.get(businessType);
						aab = (AssetActivitiBus) BeanUtilsExt.copyBean(aab1, aab);
//						aab.setId(listAABAll.get(j).getId());
//						aab.setDeploymentKey(listAABAll.get(j).getDeploymentKey());
//						aab.setBusTypeName(listAABAll.get(j).getBusTypeName());
//						aab.setBusType(listAABAll.get(j).getBusType());
//						aab.setBusRejectUrl(listAABAll.get(j).getBusRejectUrl());
//						aab.setBusPtype(listAABAll.get(j).getBusPtype());
//						aab.setBusCheckUrl(listAABAll.get(j).getBusCheckUrl());
//						aab.setBusRejectName(listAABAll.get(j).getBusRejectName());
//						aab = listAABAll.get(j);   //这种方法获取到的是地址，导致bug
						String fullName = aab.getBusTypeName();
						String bigName = fullName.split("\\-")[0];
						aab.setBusTypeName(bigName);
						aab.setBusType(bigType);
						if (t <= 1000) {
							String rejectUrl = aab.getBusRejectUrl();
							String rejectName = aab.getBusRejectName();
							if (StringUtils.isNotNullAndBlank(rejectName)) {
								aab.setBusTypeName(rejectName);
							} else {
//								String beforeName = aab.getBusTypeName();
								aab.setBusTypeName(fullName + "-驳回");
							}
							aab.setBusCheckUrl(rejectUrl);
						}
						un.setActivitiBus(aab);
						AssetBizStatus abs = absMap.get(t);
//						AssetBizStatus abs = assetBizStatusService.queryAssetBizStatus(t);
						abs.setBizType(bigType);
						abs.setBizStatusName(bigName);
						if(t <= 1000){
							String rejectName = abs.getBizStatusName();
							if (StringUtils.isNotNullAndBlank(rejectName)) {
								abs.setBizStatusName(rejectName);
							} else {
//								String beforeName = abs.getBizStatusName();
								abs.setBizStatusName(fullName + "-驳回");
							}
//							abs.setBizStatusName(bigName+"-驳回");
						}
						un.setAssetBizStatus(abs);
						un.setCount(NumberUtilsExt.toInt(un.getCount() + "", 1));
						un.setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						total.add(un);
						bs.add(bigType);
//						break;
//					}
//				}
			} else {
				for (int j = 0, length = total.size(); j < length; j++) {
					if (bigType.equals(total.get(j).getBusType())) {
						total.get(j).setCount(total.get(j).getCount() + 1);
						total.get(j).setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					}
				}
			}
		}
		return total;
	}

	// public List<ShowUndoTask> showUndoTaskBig(ActivitiParams params,
	// List<ShowUndoTask> total, List<Task> list) {
	// String formKey = "";
	// List<String> bs = new ArrayList<String>();
	// for (int i = 0, len = list.size(); i < len; i++) {
	// String taskId = list.get(i).getId();
	// String businessType = this.queryBusinessTypeByTaskId(taskId);
	// if (StringUtils.isNullOrBlank(businessType)) {
	// return total;
	// }
	// String bigType = businessType.split("\\_")[0];
	// if (!bs.contains(bigType)) {
	// ShowUndoTask un = new ShowUndoTask();
	// AssetActivitiBus aab =
	// assetActivitiBusService.queryAssetActivitiBus(businessType);
	// String bigName = aab.getBusTypeName().split("\\-")[0];
	// aab.setBusTypeName(bigName);
	// aab.setBusType(bigType);
	// un.setActivitiBus(aab);
	// formKey = this.queryFormKeyByTaskId(taskId);
	// Integer t = Integer.parseInt(formKey);
	// AssetBizStatus abs = assetBizStatusService.queryAssetBizStatus(t);
	// abs.setBizType(bigType);
	// abs.setBizStatusName(bigName);
	// un.setAssetBizStatus(abs);
	// un.setCount(NumberUtilsExt.toInt(un.getCount() + "", 1));
	// un.setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(),
	// "yyyy-MM-dd HH:mm:ss"));
	// total.add(un);
	// bs.add(bigType);
	// } else {
	// for (int j = 0, length = total.size(); j < length; j++) {
	// if (bigType.equals(total.get(j).getBusType())) {
	// total.get(j).setCount(total.get(j).getCount() + 1);
	// total.get(j).setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(),
	// "yyyy-MM-dd HH:mm:ss"));
	// }
	// }
	// }
	// }
	//
	// return total;
	// }

	public List<ShowUndoTask> showUndoTaskSmall(ActivitiParams params, List<ShowUndoTask> total, List<Task> list) {
		String formKey = "";
		List<AssetActivitiBus> listAABAll = assetActivitiBusService.getAssetActivitiBusList();
		List<String> bs = new ArrayList<String>();
		for (int i = 0, len = list.size(); i < len; i++) {
			String taskId = list.get(i).getId();
			String businessType = this.queryBusinessTypeByTaskId2(taskId);
			if (StringUtils.isNullOrBlank(businessType)) {
				return total;
			}
			if (!bs.contains(businessType)) {
				ShowUndoTask un = new ShowUndoTask();
				for (int j = 0, lenj = listAABAll.size(); j < lenj; j++) {
					if (businessType.equals(listAABAll.get(j).getDeploymentKey())) {
						AssetActivitiBus aab = listAABAll.get(j);
						un.setActivitiBus(aab);
						// formKey = this.queryFormKeyByTaskId(taskId);
						formKey = list.get(i).getFormKey();
						Integer t = this.getFormKey(formKey);
//						Integer t = Integer.parseInt(formKey);
						AssetBizStatus abs = new AssetBizStatus();
						abs.setBizStatus(t);
						abs.setBizStatusName(list.get(i).getName());
//						AssetBizStatus abs = assetBizStatusService.queryAssetBizStatus(t);
						un.setAssetBizStatus(abs);
						un.setCount(NumberUtilsExt.toInt(un.getCount() + "", 1));
						un.setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
						total.add(un);
						bs.add(businessType);
						break;
					}
				}
			} else {
				for (int j = 0, length = total.size(); j < length; j++) {
					if (businessType.equals(total.get(j).getBusType())) {
						total.get(j).setCount(total.get(j).getCount() + 1);
						total.get(j).setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					}
				}
			}
		}
		return total;
	}

	// public List<ShowUndoTask> showUndoTaskSmall(ActivitiParams params,
	// List<ShowUndoTask> total, List<Task> list) {
	// String formKey = "";
	// List<String> bs = new ArrayList<String>();
	// for (int i = 0, len = list.size(); i < len; i++) {
	// String taskId = list.get(i).getId();
	// String businessType = this.queryBusinessTypeByTaskId(taskId);
	// if (StringUtils.isNullOrBlank(businessType)) {
	// return total;
	// }
	// if (!bs.contains(businessType)) {
	// ShowUndoTask un = new ShowUndoTask();
	// AssetActivitiBus aab =
	// assetActivitiBusService.queryAssetActivitiBus(businessType);
	// un.setActivitiBus(aab);
	// formKey = this.queryFormKeyByTaskId(taskId);
	// Integer t = Integer.parseInt(formKey);
	// AssetBizStatus abs = assetBizStatusService.queryAssetBizStatus(t);
	// un.setAssetBizStatus(abs);
	// un.setCount(NumberUtilsExt.toInt(un.getCount() + "", 1));
	// un.setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(),
	// "yyyy-MM-dd HH:mm:ss"));
	// total.add(un);
	// bs.add(businessType);
	// } else {
	// for (int j = 0, length = total.size(); j < length; j++) {
	// if (businessType.equals(total.get(j).getBusType())) {
	// total.get(j).setCount(total.get(j).getCount() + 1);
	// total.get(j).setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(),
	// "yyyy-MM-dd HH:mm:ss"));
	// }
	// }
	// }
	// }
	//
	// return total;
	// }
	
	
	public List<ShowUndoTask> showUndoTaskDetail(ActivitiParams params, List<ShowUndoTask> total, List<Task> list,String currentUserName) {
		String formKey = "";
		List<String> bs = new ArrayList<String>();
		Map<String,AssetActivitiBus> aabMap = new HashMap<String, AssetActivitiBus>();
		Map<Integer,AssetBizStatus> absMap = new HashMap<Integer, AssetBizStatus>();
		List<AssetBizStatus> listAbs = assetBizStatusService.getAssetBizStatusList(null);
		for (AssetBizStatus assetBizStatus : listAbs) {
			absMap.put(assetBizStatus.getBizStatus(), assetBizStatus);
		}
		List<AssetActivitiBus> listAABAll = assetActivitiBusService.getAssetActivitiBusList();
		for (AssetActivitiBus assetActivitiBus : listAABAll) {
			aabMap.put(assetActivitiBus.getDeploymentKey(), assetActivitiBus);
		}
		for (int i = 0, len = list.size(); i < len; i++) {
			String taskId = list.get(i).getId();
			String businessType = this.queryBusinessTypeByTaskId2(taskId);
			String businessId = this.queryBusinessIdByTaskId2(taskId);
			if(StringUtils.isNullOrBlank(businessId)){
				continue;
			}
			String findPId = this.getHistoryProcessInstanceId(businessId);
			String applyuser = this.getSubmitUser(findPId, "applyuser");
			String applyUserName = "";
			String startTime = "";
			String proInsId = list.get(i).getProcessInstanceId();
			Date startDate = historyService.createHistoricProcessInstanceQuery().processInstanceId(proInsId).singleResult().getStartTime();
			startTime = DateUtilsExt.formatDate(startDate, "yyyy-MM-dd");
			ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(applyuser);
			if(apiUser != null){
				applyUserName = apiUser.getUserName();
			}
			if (StringUtils.isNullOrBlank(businessType)) {
				return total;
			}
			String bigType = "";
			AssetActivitiBus big = aabMap.get(businessType);
			bigType = businessType.split("\\_")[0];
			if (!StringUtils.isNullOrBlank(big.getBusPtype())) {
				bigType = big.getBusPtype();
			}
			formKey = list.get(i).getFormKey();
			Integer t = this.getFormKey(formKey);
//			Integer t = Integer.parseInt(formKey);
			if (t <= 1000) {
				bigType += "_REJECT";
			}
			ShowUndoTask un = new ShowUndoTask();
			AssetActivitiBus aab = new AssetActivitiBus();
			AssetActivitiBus aab1 = aabMap.get(businessType);
			aab = (AssetActivitiBus) BeanUtilsExt.copyBean(aab1, aab);
			String fullName = aab.getBusTypeName();
			String bigName = fullName.split("\\-")[0];
			aab.setBusTypeName(bigName);
			aab.setBusType(bigType);
			if (t <= 1000) {
				String rejectUrl = aab.getBusRejectUrl();
				String rejectName = aab.getBusRejectName();
				if (StringUtils.isNotNullAndBlank(rejectName)) {
					aab.setBusTypeName(rejectName);
				} else {
					String beforeName = aab.getBusTypeName();
					aab.setBusTypeName(fullName + "-驳回");
				}
				aab.setBusCheckUrl(rejectUrl);
			}
			un.setActivitiBus(aab);
			AssetBizStatus abs = absMap.get(t);
			abs.setBizType(bigType);
			abs.setBizStatusName(bigName);
			if (t <= 1000) {
				String rejectName = abs.getBizStatusName();
				if (StringUtils.isNotNullAndBlank(rejectName)) {
					abs.setBizStatusName(rejectName);
				} else {
					String beforeName = abs.getBizStatusName();
					abs.setBizStatusName(fullName + "-驳回");
				}
			}
			un.setAssetBizStatus(abs);
			un.setCount(NumberUtilsExt.toInt(un.getCount() + "", 1));
			un.setBusTime(DateUtilsExt.formatDate(list.get(i).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			total.add(un);
			bs.add(bigType);
		}
		return total;
	}

	public void tempSave(ActivitiParams params){
		String beginName = params.getUserRoleName();
		AssetProcessRemark receiveActlog = new AssetProcessRemark();//接收日志
		receiveActlog.setType(0);//接收批注内容
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		// 获取当前任务执行者，也可能是登陆者
		String userName = params.getUserName();
		String userCode = params.getUserCode();
		String deploymenKey = StringUtilsExt.convertNullToString(params.getDeploymentKey());
		
		if(StringUtils.isNullOrBlank(businessId) || StringUtils.isNullOrBlank(userName) ||
				StringUtils.isNullOrBlank(userCode)){
			return;
		}
//		if(handle == HandleType.CANCELPASS.getHandle()){
//			if(busState > 10 && busState < 1000){
//				handle =  HandleType.CANCELSUBMIT.getHandle();
//			}
//		}
		//如果业务有值，则显示传过来的值，否则使用默认的单位录入岗
		if(StringUtilsExt.isNotBlank(beginName)){
			receiveActlog.setTaskName(beginName);
		}else{
			receiveActlog.setTaskName("单位录入岗");
		}
		receiveActlog.setOperatorId(userCode);
		receiveActlog.setOperatorName(userName);
		receiveActlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		receiveActlog.setTaskId("0");
		receiveActlog.setProcInstId("0");
//		receiveActlog.setTaskName(TEMP_POSITION);    //岗位名称
		receiveActlog.setUserTaskId("usertask0");//添加单据名称
		if(StringUtils.isNullOrBlank(deploymenKey)){
			receiveActlog.setDescription(TEMP_NAME);
		}else if(deploymenKey.contains("USER")){
			receiveActlog.setDescription(USER_BILL);
		}else if(deploymenKey.contains("USE")){
			receiveActlog.setDescription(USE_BILL);
		}else if(deploymenKey.contains("DISPOSE")){
			receiveActlog.setDescription(DISPOSE_BILL);
		}else if(deploymenKey.contains("CARD")){
			receiveActlog.setDescription(CARD_BILL);
		}else if(deploymenKey.contains("BCPM")){
			receiveActlog.setDescription(BCPM_BILL);
		}else if(deploymenKey.contains("INVENTORY")){
			receiveActlog.setDescription(INVENTORY_BILL);
		}else if(deploymenKey.contains("REPORT_NB")){
			receiveActlog.setDescription(NB_REPORT);
		}else if(deploymenKey.contains("REPORT")){
			receiveActlog.setDescription(REPORT);
		}else{
			receiveActlog.setDescription("单据");
		}
		if(params.isTemp()){
			receiveActlog.setCheckResult(HandleType.TEMPSAVE.getHandle());//暂存数据
//			receiveActlog.setTaskName(HandleType.TEMPSAVE.getName());
		}
		else{
			receiveActlog.setCheckResult(HandleType.TEMPSAVENEW.getHandle());
//			receiveActlog.setTaskName(HandleType.TEMPSAVENEW.getName());
		}
		receiveActlog.setType(0);//0的话，没有意见等拼接内容
		receiveActlog.setBusType(deploymenKey);
		receiveActlog.setBusId(businessId);
		receiveActlog.setTaskStatus(TaskStatus.ZANCUN.getStatus());
		
		AssetProcessRemark assetProcessRemark = new AssetProcessRemark();
		assetProcessRemark.setId(null);
		assetProcessRemark.setTaskStatus(TaskStatus.ZANCUN.getStatus());
		assetProcessRemark.setBusId(businessId);
		boolean hasTempData = false;
		if(StringUtils.isNullOrBlank(deploymenKey)){//类型无
			List<AssetProcessRemark> hasTempDataList = assetProcessRemarkService.getAssetProcessRemarkList(assetProcessRemark);
			if(hasTempDataList != null && hasTempDataList.size() > 0){
				hasTempData = true;//存在临时存储的数据，此时暂存不进行任何操作
			}
//			assetProcessRemarkService.deleteRemarkByBusIdTemp(businessId, TaskStatus.ZANCUN.getStatus());//先删除，再更新
		}
		else{//类型有
			assetProcessRemark.setBusType(deploymenKey);
			List<AssetProcessRemark> hasTempDataList = assetProcessRemarkService.getAssetProcessRemarkList(assetProcessRemark);
			if(hasTempDataList != null && hasTempDataList.size() > 0){
				hasTempData = true;//存在临时存储的数据，此时暂存不进行任何操作
			}
//			assetProcessRemarkService.deleteRemarkByBusIdDeploymentTemp(businessId, deploymenKey, TaskStatus.ZANCUN.getStatus());//先删除，再更新
		}
//		assetProcessRemarkService.delAssetProcessRemark(assetProcessRemark);//先删除，再更新
		if (!hasTempData) {
			assetProcessRemarkService.addAssetProcessRemark(receiveActlog);
		}
//		receiveActlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
//		receiveActlog.setDescription("流程");
//		receiveActlog.setCheckResult(HandleType.RECEIVE.getHandle());
//		assetProcessRemarkService.addAssetProcessRemark(receiveActlog);
	}
	
	//用于删除暂存数据
	public void deleteTempData(ActivitiParams params){
		String busId = StringUtilsExt.convertNullToString(params.getBusinessId());
		AssetProcessRemark assetProcessRemark = new AssetProcessRemark();
		assetProcessRemark.setId(null);
		assetProcessRemark.setBusId(StringUtilsExt.convertNullToString(busId));
		assetProcessRemark.setTaskStatus(null);
		String taskId = this.queryTaskIdByBusinessIdLike(params);
		if(StringUtils.isNullOrBlank(taskId)){
			List<AssetProcessRemark> list = assetProcessRemarkService.getAssetProcessRemarkList(assetProcessRemark);
			if (list != null && list.size() == TEMP_SIZE) {
				assetProcessRemarkService.delAssetProcessRemark(assetProcessRemark);
			}
		}
	}
	
	public ProcessResult deleteProcessAndRecord(ActivitiParams params){
		ProcessResult checkResult = this.beforeHandleCheck(params);
		ProcessResult result = new ProcessResult();
		//输入参数校验
		if (!checkResult.getCode().equals(ActivitiException.SUCCESS.getCode())) {
			return checkResult;
		}
		String taskId = this.queryTaskIdByBusinessIdLike(params);
		if(StringUtils.isNullOrBlank(taskId)){
			result.setCode(ActivitiException.SUCCESS.getCode());
			result.setMessage(ActivitiException.SUCCESS.getName());
			logger.error(params.getBusinessId() + "" + "不存在任务");
			return result;
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		AssetProcessRemark receiveActlog = new AssetProcessRemark();//接收日志
		receiveActlog.setType(0);//接收批注内容
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		// 获取当前任务执行者，也可能是登陆者
		String userName = params.getUserName();
		String userCode = params.getUserCode();
		String deploymenKey = StringUtilsExt.convertNullToString(params.getDeploymentKey());
		receiveActlog.setOperatorId(userCode);
		receiveActlog.setOperatorName(userName);
		receiveActlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		receiveActlog.setTaskId(task.getId());
		receiveActlog.setProcInstId(task.getProcessInstanceId());
		receiveActlog.setUserTaskId(task.getTaskDefinitionKey());
		receiveActlog.setDescription(HandleType.DELETE.getName());
		receiveActlog.setTaskName(task.getName());
		receiveActlog.setType(0);//0接收内容
		receiveActlog.setBusType(deploymenKey);
		receiveActlog.setBusId(businessId);
		receiveActlog.setTaskStatus(TaskStatus.DELETE.getStatus());
		
		assetProcessRemarkService.addAssetProcessRemark(receiveActlog);
		
		this.deleteProcessInstacne(params);

		result.setCode(ActivitiException.SUCCESS.getCode());
		result.setMessage(ActivitiException.SUCCESS.getName());
		
		return result;
	}
	
	public void deleteRecord(ActivitiParams params){
		AssetProcessRemark receiveActlog = new AssetProcessRemark();//接收日志
		receiveActlog.setType(0);//接收批注内容
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String userName = params.getUserName();
		String userCode = params.getUserCode();
//		String deploymenKey = StringUtilsExt.convertNullToString(params.getDeploymentKey());
		receiveActlog.setOperatorId(userCode);
		receiveActlog.setOperatorName(userName);
		receiveActlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		receiveActlog.setDescription(HandleType.DELETE.getName());
		receiveActlog.setType(0);//0接收内容
//		receiveActlog.setBusType(deploymenKey);
		receiveActlog.setBusId(businessId);
		receiveActlog.setTaskStatus(TaskStatus.DELETE.getStatus());
		
		assetProcessRemarkService.addAssetProcessRemark(receiveActlog);
	}
	
	
	/**
	 * 
	 * <p>函数名称：  提交方法</p>
	 * <p>功能说明：  
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params 
	 * @param change 如果为true，则是流程类型转换的流程。WASTE--->ALLOT
	 * @return
	 * @throws Exception
	 *
	 * @date   创建时间：2017年3月20日
	 * @author 作者：jinbiao
	 */
	public ProcessResult startSubmit(ActivitiParams params, boolean change) throws Exception {
//		Integer currentTaskStatus;
//		Integer nextTaskStatus;
		String description = "";
		AssetProcessRemark actlog = new AssetProcessRemark();
		AssetBusHistory history = new AssetBusHistory();
		
		String roleName = "";
		String roleCode = "";

		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		String userCode = params.getUserCode();

		String userName = params.getUserName();
		String creatorId = params.getCreatorId();
//		String creatorCode = params.getCreatorCode();
		String creatorName = params.getCreatorName();
		Integer handle = params.getHandle();
		String deploymentKey = params.getDeploymentKey();

//		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(busType);

//		String bpmnIdIsKey = activitibus.getDeploymentKey();
		String bpmnIdIsKey = params.getDeploymentKey();
		
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String,Object> condition = new HashMap<String, Object>();
		Map<String,Object> startKeyMap = new HashMap<String, Object>();
		startKeyMap = params.getStartKeyMap();
		condition = params.getCondition();
		//将单位放入其中
		condition.put(ActivitiCondition.ORG_CODE.getCode(), startKeyMap.get("orgCode"));
		if (!condition.isEmpty()) {
			variables.put("map", condition);
		}
		variables.put("applyuser", userCode);// 表示惟一用户
		variables.put("handle", HandleType.PASS.getHandle().toString());// 表示惟一用户

		// 格式：LeaveBill.id的形式（使用流程变量）
		String businessKey = bpmnIdIsKey + "." + businessId;
//		String businessKey = activitibus.getDeploymentKey() + "." + businessId;
		variables.put("businessKey", businessKey);
		variables.put("startKeyMap", startKeyMap);
		//开始流程
		history.setCurrentTask("start");
		// 6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance pi = null;
		try {
			pi = runtimeService.startProcessInstanceByKey(bpmnIdIsKey, businessKey, variables);
		} catch (Exception e) {
			ProcessResult error = new ProcessResult();
			error.setCode(ActivitiException.ERROR.getCode());
			error.setMessage(ActivitiException.ERROR.getName());
			e.printStackTrace();
			logger.error(e.getMessage());
			return error;
		}
		// ExecutionEntity exe = (ExecutionEntity) instance;
		pi = runtimeService.createProcessInstanceQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();

		Task nowTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		//获取角色相关信息
		roleCode = this.getCurrentRoleCodeIner(businessId);
		roleName = this.getCurrentRoleName(roleCode);
		actlog.setRoleName(roleName);
		actlog.setRoleCode(roleCode);
		//首节点，指定谁来做，由提交者来做
		taskService.setAssignee(nowTask.getId(), userCode);
		actlog.setCurrentTaskStatus(0);
		actlog.setNextTaskStatus(this.getFormKey(nowTask.getFormKey()));
//		actlog.setNextTaskStatus(NumberUtilsExt.toInt(nowTask.getFormKey(), 0));
		description = StringUtilsExt.convertNullToString(nowTask.getDescription());
//		actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		actlog.setBusId(businessId);
		actlog.setOperatorId(userCode);
		actlog.setOperatorName(userName);
		actlog.setBusType(bpmnIdIsKey);
		actlog.setDescription(description);
		actlog.setProcInstId(pi.getProcessInstanceId());
		history.setProcInstId(pi.getProcessInstanceId());
		actlog.setTaskStatus(TaskStatus.SUBMIT.getStatus());
		actlog.setTaskName(TaskStatus.SUBMIT.getName());
		actlog.setNextNodeName(nowTask.getName());
		actlog.setNextNodeId(nowTask.getTaskDefinitionKey());
		actlog.setCheckResult(handle);
		history.setCheckResult(handle);
		actlog.setCreatorName(creatorName);
		actlog.setCreatorId(creatorId);
		actlog.setTaskId(nowTask.getId());
		
		actlog.setType(0);
		
		/**
		 * 获取正确的人。
		 */
		ProcessResult proresult = new ProcessResult();
		proresult.setLog(actlog);
//		List<ApiUser> apiUserListByOrgCodeList = this.getNextExcetorApiUsers(nowTask.getId());
//		proresult.setNextNodeUser(apiUserListByOrgCodeList);
		/**
		 * 2017年1月17日20:10:24，修改下一代办人正确信息
		 */
//		ApiUser nextUser = DrcHepler.getInstance().findUseriInfoByUserCode(nowTask.getAssignee());
		ApiUser nextUser = DrcHepler.getInstance().findUseriInfoByUserCode(userCode);
		
		// 从description中获取下一节点待办
		if(nextUser != null){
			String pin = "";
			if(StringUtils.isNullOrBlank(nextUser.getMobileNo())){
				pin = nextUser.getUserName()/*+" "+"暂无号码！"*/;
			}else{
				pin = nextUser.getUserName()/*+" "+nextUser.getMobileNo()*/;
			}
			actlog.setNextExecutorName(pin);
		}else{
			
			proresult.setMessage(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName());
			proresult.setCode(ActivitiException.GETNEXTUSESRFAIL.getCode());
			logger.error(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName());
			return proresult;
//			actlog.setNextExecutorName(nowTask.getDescription());
		}
		

		history.setBusId(businessId);
		history.setOperatorId(userCode);
		history.setBizType(busType);
		
		if (!change) {//如果非改变流程的提交，则记录日志，否则不记录。
			if (!this.hasTempDataRemark(businessId)) {
				params.setTemp(false);
				this.tempSave(params);
				//更新暂存
				assetProcessRemarkService.updateBusTypeByIdAndStatus(businessId, TaskStatus.ZANCUN.getStatus(), deploymentKey);
				//更新直接提交的
				assetProcessRemarkService.updateBusTypeByIdAndStatus(businessId, HandleType.TEMPSAVENEW.getHandle(), deploymentKey);
				assetProcessRemarkService.updatePIdByBusIdAndBusType(businessId, pi.getId(), deploymentKey);
			}
			actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));//操作日期写在此处，防止根据时间查询的时候查询暂存在提交之后
			assetProcessRemarkService.addAssetProcessRemark(actlog);
		}
		// eventbus.post(history);

		ActivitiParams tempparams = new ActivitiParams();
		tempparams = (ActivitiParams) BeanUtilsExt.copyBean(params, tempparams);
		tempparams.setUserCode(userCode);
		tempparams.setUserName(userName);
		tempparams.setBusType(busType);
		tempparams.setBusinessId(businessId);
		tempparams.setCreatorId(creatorId);
		tempparams.setCreatorName(creatorName);
		tempparams.setHandle(HandleType.PASS.getHandle());

		ProcessResult temp = activitiProcessService.handleTask(tempparams);
		
		if (change) {//判断是否转换类型了，如果转换，更新相应的历史表
			assetProcessRemarkService.updatePIdByBusIdAndBusType(businessId, pi.getId(), deploymentKey);
			assetBusHistoryService.updatePIdByBusIdAndBusType(businessId, pi.getId(), busType);
//			assetProcessRemarkService.updatePIdByBusId(businessId, pi.getId(), deploymentKey);
//			assetBusHistoryService.updatePIdByBusId(businessId, pi.getId(), busType);
//			activitiNextUserService.updateProcessVersionByBusId(businessId, pi.getId());
		}
		
		return temp;
	}

	//发起流程就相当于驳回了，因为到了第一个节点
	public ProcessResult rejectStartSubmit(ActivitiParams params) throws Exception {
		
		AssetBizStatus bizstatus = new AssetBizStatus();
		AssetProcessRemark actlog = new AssetProcessRemark();
		AssetBusHistory history = new AssetBusHistory();
		ProcessResult proresult = new ProcessResult();
//		Integer currentTaskStatus ;
//		Integer nextTaskStatus ;
		Integer next2TaskStatus = 0;
		String description = "";

		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		String userCode = params.getUserCode();
		String lastUserTaskId = StringUtilsExt.convertNullToString(params.getTaskId());
		String deploymentKey = params.getDeploymentKey();

		String userName = params.getUserName();
		String creatorId = params.getCreatorId();
		String creatorCode = params.getCreatorCode();
		String creatorName = params.getCreatorName();
		Integer handle = params.getHandle();
		
		AssetActivitiBus activitibus = params.getActivitiBus();
		if(activitibus == null){
			assetActivitiBusService.queryAssetActivitiBus(busType);
		}
		if(StringUtils.isNullOrBlank(deploymentKey)){
			deploymentKey = activitibus.getDeploymentKey();
		}
		
		String bpmnIdIsKey = deploymentKey;
		
		actlog.setCurrentTaskStatus(TaskStatus.BUSINESSSUCCESS.getStatus());
		String findPId = this.getHistoryProcessInstanceId(businessId);
		String applyuser = this.getSubmitUser(findPId, "applyuser");
		if(StringUtils.isNullOrBlank(applyuser)){
			applyuser = creatorCode;
		}
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("applyuser", applyuser);// 表示惟一用户
		variables.put("handle", HandleType.PASS.getHandle().toString());// 表示惟一用户
		String businessKey = bpmnIdIsKey + "." + businessId;
		variables.put("businessKey", businessKey);
		variables.put("startKeyMap", params.getStartKeyMap());

		// 获取历史流程id
		// 6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(bpmnIdIsKey, businessKey, variables);
		
		/**
		 * 更新历史记录。重新发起至首节点
		 */
		// 更新历史记录，将原来的历史记录覆盖
		assetProcessRemarkService.updatePIdByBusIdAndBusType(businessId, pi.getId(), deploymentKey);
		assetBusHistoryService.updatePIdByBusIdAndBusType(businessId, pi.getId(), busType);
//		assetProcessRemarkService.updatePIdByBusId(businessId, pi.getId(), deploymentKey);
//		assetBusHistoryService.updatePIdByBusId(businessId, pi.getId(), busType);
//		activitiNextUserService.updateProcessVersionByBusId(businessId, pi.getId());
		
		/**
		 * 2017年3月31日18:56:55，未知是否要对历史记录进行删除，暂时无法验证
		 */
		// 删除历史流程记录
//		List<HistoricProcessInstance> histpro = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).finished().list();
//		if (histpro != null && histpro.size() > 0) {
//			for (HistoricProcessInstance historicProcessInstance : histpro) {
//				this.deleteHistoryTaskByPId(historicProcessInstance.getId());
//			}
//		}

		// ExecutionEntity exe = (ExecutionEntity) instance;
		pi = runtimeService.createProcessInstanceQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
//		String defId = pi.getProcessDefinitionId();
//		String processInstanceId = pi.getId();
		Task nowTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		//指定又提交者来做这件事情
		taskService.setAssignee(nowTask.getId(), applyuser);
		// 获取任务
		// for (int i = 0, len = exe.getTasks().size(); i < len; i++) {
		// if (.equals(exe.getTasks().get(i).getAssignee())) {
		// String taskId = exe.getTasks().get(i).getId();
		// }
		// }
		description = StringUtilsExt.convertNullToString(nowTask.getDescription());
		actlog.setDescription(description);
		actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		actlog.setBusId(businessId);
		actlog.setOperatorId(userCode);
		actlog.setOperatorName(userName);
		actlog.setCurrentTaskStatus(TaskStatus.BUSINESSSUCCESS.getStatus());
		actlog.setNextTaskStatus(TaskStatus.REJECTTOSTART.getStatus());
		actlog.setBusType(bpmnIdIsKey);
		actlog.setProcInstId(pi.getProcessInstanceId());
		actlog.setUserTaskId(nowTask.getTaskDefinitionKey());
		actlog.setType(0);//接受类型，非正常批注类型
//		actlog.setUserTaskId(lastUserTaskId);
		history.setProcInstId(pi.getProcessInstanceId());

		// 将参数再次放入，用于跳转等使用
		proresult.setParams(params);

		Integer status = TaskStatus.REJECTTOSTART.getStatus();
		if (handle == HandleType.REJECT.getHandle()) {
			actlog.setTaskStatus(TaskStatus.REJECTTOSTART.getStatus());
			actlog.setTaskName(TaskStatus.ENDREJECT.getName());
			actlog.setNextNodeName(nowTask.getName());
			actlog.setNextNodeId(nowTask.getTaskDefinitionKey());
			actlog.setCheckResult(handle);
			history.setCheckResult(handle);
			actlog.setCreatorName(creatorName);
			actlog.setCreatorId(creatorId);
			actlog.setTaskId(nowTask.getId());
			
			ApiUser nextUser = DrcHepler.getInstance().findUseriInfoByUserCode(applyuser);
			// 从description中获取下一节点待办
			if(nextUser != null){
				String pin = "";
				if(StringUtils.isNullOrBlank(nextUser.getMobileNo())){
					pin = nextUser.getUserName()/*+" "+"暂无号码！"*/;
				}else{
					pin = nextUser.getUserName()/*+" "+nextUser.getMobileNo()*/;
				}
				actlog.setNextExecutorName(pin);
			}else{
				proresult.setMessage(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName());
				proresult.setCode(ActivitiException.GETNEXTUSESRFAIL.getCode());
				logger.error(nowTask.getName()+ActivitiException.GETNEXTUSESRFAIL.getName());
				return proresult;
//				actlog.setNextExecutorName("暂无");
//				actlog.setNextExecutorName(nowTask.getDescription());
			}
			
			status = TaskStatus.REJECTTOSTART.getStatus();

			history.setOperatorId(userCode);
			history.setBusId(businessId);
			history.setBizType(busType);
			history.setTaskStatus(TaskStatus.RUNNING.getStatus());
			history.setCurrentTask(lastUserTaskId);

			bizstatus.setBizStatus(status);
			bizstatus.setBizStatusName(TaskStatus.REJECTTOSTART.getName());
//			bizstatus = assetBizStatusService.queryAssetBizStatus(status);
			
			String message = HandleType.REJECT.getName() + ActivitiException.SUCCESS.getName();

			List<ActivitiTaskMoveComboboxShow> list = this.queryUserTaskIdListByBusinessId(params);
			
			
			String taskDefKey = list.get(list.size()-1).getUserTaskId();
			ActivitiParams pa = new ActivitiParams();
			pa.setDefId(nowTask.getProcessDefinitionId());
			pa.setTaskId(nowTask.getId());
			ProcessResult roleResult = this.getCurrentRoleName(userCode, pa, taskDefKey);
			String roleName = roleResult.getCurrentRoleName();
			String roleCode = StringUtilsExt.convertNullToString(roleResult.getCurrentRoleCode());
		
			proresult.setLog(actlog);
			List<ApiUser> apiUserListByOrgCodeList = new ArrayList<ApiUser>();
//			List<ApiUser> apiUserListByOrgCodeList = this.getNextExcetorApiUsers(nowTask.getId());
			ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(applyuser);
			apiUserListByOrgCodeList.add(apiUser);
			proresult.setNextNodeUser(apiUserListByOrgCodeList);
			
//			String exactUsers = this.getNextExcetor(nowTask.getId());
			String exactUsers = null;
			if(apiUser != null){
				exactUsers = apiUser.getUserCode();
			}
			if(StringUtils.isNotNullAndBlank(exactUsers)){
				proresult.getLog().setNextExecutorCode(exactUsers);
				ActivitiParams pa1 = new ActivitiParams();
				pa1.setDefId(nowTask.getProcessDefinitionId());
				pa1.setTaskId(nowTask.getId());
				String findUserCode = "";
				if(apiUserListByOrgCodeList != null && apiUserListByOrgCodeList.size()>0){
					findUserCode = apiUserListByOrgCodeList.get(0).getUserCode();
				}else{
					findUserCode = exactUsers.split("\\,")[0];
				}
				ProcessResult roleResult1 = this.getCurrentRoleName(findUserCode, pa1, nowTask.getTaskDefinitionKey());
				String roleCode1 = StringUtilsExt.convertNullToString(roleResult1.getCurrentRoleCode());
			}
			
			
//			if(StringUtils.isNullOrBlank(roleName)){
//				roleResult.setCode(ActivitiException.ROLEERROR.getCode());
//				roleResult.setMessage(ActivitiException.ROLEERROR.getName());
//				logger.error(ActivitiException.ROLEERROR.getName());
//				return roleResult;
//			}
			//审核节点名称
//			String lastNodeName = list.get(list.size()-1).getUserTaskName();
			//审核角色
			if(StringUtils.isNullOrBlank(roleName)){
				roleName = "";
			}
			String beforeName = bizstatus.getBizStatusName();
			bizstatus.setBizStatusName(roleName+beforeName);
//			bizstatus.setBizStatusName(lastNodeName+beforeName);

			proresult.setMessage(message);
			proresult.setCode(ActivitiException.SUCCESS.getCode());
			proresult.setInfo(bizstatus);
			proresult.setLog(actlog);
			proresult.setBusId(businessId);
			proresult.setNext2Status(next2TaskStatus);
			
			//退回已终审的记录不记录日志，让handleTask去记录，如果是驳回的话要记录日志
			if(handle != HandleType.RETURN.getHandle()){
				assetProcessRemarkService.addAssetProcessRemark(actlog);
				assetBusHistoryService.updateHistoryLog(history,null);
			}
			actlog.setTaskName("");
			actlog.setNextNodeName(nowTask.getName());
			Map<String,Object> next2Map = new HashMap<String, Object>();
			next2Map.put("taskId", nowTask.getId());
			next2Map.put("currentTaskDefKey", nowTask.getTaskDefinitionKey());
			List<ActivitiUserTaskNode>next2List = this.getNextNode(next2Map);
			nowTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
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

		}
		
		proresult.setFormType(this.getFormType(nowTask.getFormKey()));
		
		return proresult;
	}

	public ProcessResult cancelSubmit(ActivitiParams params) {
		AssetProcessRemark actlog = new AssetProcessRemark();
		AssetBusHistory history = new AssetBusHistory();
		ProcessResult proresult = new ProcessResult();
		AssetBizStatus bizstatus = new AssetBizStatus();
		String busId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String taskId = this.queryTaskIdByBusinessIdLike(params);
		ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("taskId",taskId);
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		// 待办专用处理 保证流程的指向永远是向前的，只保证名称向前，其余所有的参数保持正常，不论什么情况下，都是由上一个岗位指向下一岗位
//		actlog.setNextNodeId(task.getTaskDefinitionKey());
		actlog.setNextNodeName(task.getName());
		String beforeNodeId = "";
		String beforeNodeName = "";
		List<AssetProcessRemark> beforeNodeList = assetProcessRemarkService.queryBusinessHistory(busId, task.getProcessInstanceId());
		for (AssetProcessRemark assetProcessRemark : beforeNodeList) {
			if (task.getTaskDefinitionKey().equals(assetProcessRemark.getNextNodeId())) {
				beforeNodeId = assetProcessRemark.getUserTaskId();//
				beforeNodeName = assetProcessRemark.getTaskName();//这里获取到的是单位录入岗，此时的任务的在单位审核岗
			}
		}
		//按照要求，当前任务置空，模拟未进入前的提交任务
		actlog.setTaskName("");
		//下一环节，变为单位录入
		actlog.setNextNodeName(beforeNodeName);

		proresult.setNext2TaskName(task.getName());
		
		Map<String, Object> map11 = new HashMap<String, Object>();
		map11.put("userTaskId", beforeNodeId);
		map11.put("taskId", task.getId());
		
		map.put("currentTaskDefKey", task.getTaskDefinitionKey());
		try {
			this.getNextNode(map);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		try {
			node = this.getFirstNode(map);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		// 删除相关东西
		assetProcessRemarkService.deleteByBusIdAndPId(StringUtilsExt.convertNullToString(params.getBusinessId()), params.getProcessInstanceId());
		assetBusHistoryService.deleteByBusIdAndPId(StringUtilsExt.convertNullToString(params.getBusinessId()), params.getProcessInstanceId());
		params.setTaskId(taskId);
		this.deleteProcessInstacne(params);

		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		AssetActivitiBus activitibus = params.getActivitiBus();
		if(activitibus == null){
			assetActivitiBusService.queryAssetActivitiBus(busType);
		}
		Integer handle = params.getHandle();
		// 登陆者信息
		String userCode = params.getUserCode();
		String userName = params.getUserName();
		String creatorId = params.getCreatorId();
		String creatorName = params.getCreatorName();

		actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		actlog.setBusId(businessId);
		actlog.setOperatorId(userCode);
		actlog.setOperatorName(userName);
		actlog.setBusType(activitibus.getDeploymentKey());
		actlog.setCheckResult(handle);
		history.setCheckResult(handle);
		actlog.setTaskStatus(TaskStatus.CHECKING.getStatus());
		actlog.setCreatorId(creatorId);
		actlog.setCreatorName(creatorName);
		actlog.setNextExecutorName(creatorName);
		actlog.setNextExecutorCode("");
		actlog.setNextNodeId("");
//		actlog.setNextNodeName(node.getUserTaskName());
		actlog.setUserTaskId(node.getUserTaskId());
		// 不记录日志
		bizstatus.setBizStatus(TaskStatus.ZANCUN.getStatus());
		bizstatus.setBizStatusName(TaskStatus.ZANCUN.getName());
//		bizstatus = assetBizStatusService.queryAssetBizStatus(TaskStatus.ZANCUN.getStatus());
		proresult.setBusId(businessId);
		proresult.setMessage(HandleType.CANCELSUBMIT.getName()+"操作成功！");
		proresult.setCode(ActivitiException.SUCCESS.getCode());
		proresult.setInfo(bizstatus);
		proresult.setNext2Status(TaskStatus.ZANCUN.getStatus());
		proresult.setLog(actlog);
		
		return proresult;
	}

	public ProcessResult returnEndTask(ActivitiParams params) throws Exception {

		// EventBus eventbus = EventBusHelper.getEventBus();
		// AssetProcessRemark actlog = new AssetProcessRemark();
		// ProcessResult proresult = new ProcessResult();
		// AssetBizStatus bizstatus = new AssetBizStatus();
		//
		// String remark = params.getComment();
		// String businessId =
		// StringUtilsExt.convertNullToString(params.getBusinessId());
		// String busType = params.getBusType();
		// String creatorId = params.getCreatorId();
		// String creatorName = params.getCreatorName();

		// 获取当前任务执行者，也可能是登陆者
		// String userName = params.getUserName();
		// String userCode = params.getUserCode();
		// Integer handle = params.getHandle();

		// 重新发起一个任务
		this.rejectStartSubmit(params);

		List<ActivitiTaskMoveComboboxShow> userTaskList = this.queryUserTaskIdListByBusinessId(params);

		String lastNodeTaskId = userTaskList.get(userTaskList.size() - 1).getUserTaskId();

		// ActivitiParams params = result.getParams();
		// Map<String, Object> info = new HashMap<String, Object>();
		// //查询
		// info.put("userCode", result.getLog().getOperatorId());
		// info.put("id", result.getLog().getTaskId());
		// Map<String, Object> map = this.queryUserTaskIdByUserInXml(info);
		// String userTaskId = MapUtilsExt.getString(map, "userTaskId");
		// params.setTaskId(userTaskId);

		
		params.setUserTaskId(lastNodeTaskId);
		// 任务移动到最后一个节点，此时的任务是激活状态，然后继续让该人物进行一个退回操作   退回1
		this.taskMove(params);
		/*
		 * 退回1
		 * */ 
		params.setHandle(HandleType.RETURN.getHandle());
		return activitiProcessService.handleTask(params);

		// String pid = proresult.getLog().getProcInstId();
		// ProcessInstance pi =
		// runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
		//
		// Task task =
		// taskService.createTaskQuery().processInstanceId(pid).singleResult();
		//
		// AssetActivitiBus activitibus =
		// activitibusservice.queryAssetActivitiBus(busType);
		//
		// actlog.setUserTaskId(pi.getActivityId());
		// actlog.setTaskName(task.getName());
		// actlog.setOperateDate(DateUtilsExt.formatDate(new Date(),
		// "yyyyMMddHHmmssSSS"));
		// actlog.setBusId(businessId);
		// actlog.setOperatorId(userCode);
		// actlog.setOperatorName(userName);
		// actlog.setBusType(activitibus.getDeploymentKey());
		// actlog.setProcInstId(pid);

		// 判断是否为第一个节点，如果是第一个节点，就改名为提交,对于驳回后的提交
		// actlog.setRemark(remark);
		// actlog.setTaskId(task.getId());
		// actlog.setCreatorId(creatorId);
		// actlog.setCreatorName(creatorName);
		// actlog.setUserTaskId(pi.getActivityId());
		// actlog.setAssignee(task.getDescription());
		// 已终审
		// Integer status = (Integer) TaskStatus.BUSINESSSUCCESS.getStatus();
		// String nowTaskName = task.getName();
		// String currentTaskDef = pi.getActivityId();
		// String nextTaskState = formService.getTaskFormKey(pid,
		// currentTaskDef);
		// status = Integer.parseInt(nextTaskState);
		// actlog.setTaskStatus(TaskStatus.CHECKING.getStatus());
		// bizstatus = assetBizStatusService.queryAssetBizStatus(status);
		// bizstatus.setBizStatusName(nowTaskName);

		/**
		 * 获取正确的人。
		 */
		// Map<String, Object> getNextMap = (Map<String, Object>)
		// runtimeService.getVariable(nowTask.getExecutionId(), "");
		// String exactUsers = this.getNextExcetor(proresult,
		// getNextMap);
		// proresult.setNextNodeUser(this.getNextNodeUser(exactUsers));
		// proresult.getLog().setNextExecutorCode(exactUsers);
		//dfsa

		// proresult.getLog().getNextExecutorCode();
		// eventbus.post(actlog);

		// return proresult;
	}


	public Map<String, Object> deleteHistoryTaskByPId(String pId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = 0;
		historyService.deleteHistoricTaskInstance(pId);
		result.put("count", count);
		return result;
	}
	

	public ProcessResult taskMove(ActivitiParams params) throws Exception {
		// EventBus eventbus = EventBusHelper.getEventBus();

		AssetProcessRemark actlog = new AssetProcessRemark();
		AssetBusHistory history = new AssetBusHistory();
		ProcessResult proresult = new ProcessResult();
		AssetBizStatus bizstatus = new AssetBizStatus();

		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		String userTaskId = params.getUserTaskId();
		// String userTaskId = MapUtilsExt.getString(map, "userTaskId");

		// 1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2：获取流程定义ID
//		String processDefinitionId = task.getProcessDefinitionId();
		// 3：查询ProcessDefinitionEntiy对象
		// ProcessDefinitionEntity processDefinitionEntity =
		// (ProcessDefinitionEntity)
		// repositoryService.getProcessDefinition(processDefinitionId);
		// 使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();

		TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);

		String remark = params.getComment();
		// String remark = MapUtilsExt.getString(map, "remark");
		Integer handle = HandleType.JUMP.getHandle();
		// 没有赋值
		if (params.getHandle() != 0) {
			handle = params.getHandle();
		}
		// Integer handle = 2;

		// 从已有得taskId获取业务id
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(busType);
		if (activitibus == null) {
			proresult.setMessage(ActivitiException.BUSNAMENOTFOUND.getName());
			proresult.setCode(ActivitiException.BUSNAMENOTFOUND.getCode());
			logger.error(ActivitiException.BUSNAMENOTFOUND.getName());
			return proresult;
		}
		// 获取当前任务执行者，也可能是登陆者
		String userCode = params.getUserCode();
		String userName = params.getUserName();
		String creatorId = params.getCreatorId();
		String creatorName = params.getCreatorName();

		actlog.setUserTaskId(task.getTaskDefinitionKey());
		actlog.setTaskName(task.getName());
		actlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		history.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		actlog.setBusId(businessId);
		actlog.setOperatorId(userCode);
		actlog.setOperatorName(userName);
		actlog.setBusType(activitibus.getDeploymentKey());
		actlog.setProcInstId(processInstanceId);
		history.setProcInstId(processInstanceId);
		actlog.setRemark(remark);
		actlog.setTaskId(taskId);
		actlog.setCreatorId(creatorId);
		actlog.setCreatorName(creatorName);
		actlog.setTaskStatus(TaskStatus.CHECKING.getStatus());

		Integer status;
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("id", taskId);
		map1.put("userTaskId", userTaskId);
		tfcs.moveTo(userTaskId);
		String nextTaskState = task.getFormKey();
		actlog.setCheckResult(handle);
		history.setCheckResult(handle);
		status = this.getFormKey(nextTaskState);
//		status = Integer.parseInt(nextTaskState);

		bizstatus.setBizStatus(status);
		bizstatus.setBizStatusName(task.getName());
		history.setBusId(businessId);
		history.setOperatorId(userCode);
		history.setBizType(busType);
		// 暂时不记录日志，未正式开启移动功能
		// eventbus.post(actlog);
		// eventbus.post(history);

		proresult.setLog(actlog);
		proresult.setInfo(bizstatus);
		proresult.setBusId(businessId);

		return proresult;
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public String formatSecondTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
//		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		StringBuffer result = new StringBuffer();
		if(day > 0){
			result.append(day+"").append("天");
		}if(hour > 0){
			result.append(hour+"").append("时");
		}if(hour == 0 && day > 0){
			result.append(hour+"时");
		}if(min > 0){
			result.append(min+"").append("分");
		}if(min == 0 && hour > 0){
			result.append(min+"分");
		}if(s > 0){
			result.append(s+"").append("秒");
		}if(s == 0 && min > 0){
			result.append(s+"秒");
		}
		return result.toString();
//		return (day > 0 ? day + "天" : "") + hour + "时" + min + "分" + s + "秒" /*+ sss*/;
	}
	
	public ProcessResult getNext2Node(ActivitiParams params){
		ProcessResult proresult = new ProcessResult();
		ActivitiParams pa = new ActivitiParams();//删除参数实体
		String formKey = "";
		String busId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		Integer handle = params.getHandle();
		ProcessResult checkResult = this.beforeHandleCheck(params);
		this.beforeHandleCheck(params);
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		if (StringUtils.isNullOrBlank(taskId)) {
			proresult.setMessage(ActivitiException.TASKNOTFOUND.getName());
			proresult.setCode(ActivitiException.TASKNOTFOUND.getCode());
			proresult.setNext2Status(0);
			proresult.setNext2TaskName(null);
			proresult.setNext2UserTaskId("");
			return proresult;
		}
		if (!checkResult.getCode().equals(ActivitiException.SUCCESS.getCode())) {
			checkResult.setNext2Status(0);
			proresult.setNext2TaskName(null);
			proresult.setNext2UserTaskId("");
			return checkResult;
		}
		
		Task nowTask = taskService.createTaskQuery().taskId(taskId).singleResult();
		
		String nowTaskDefId = nowTask.getTaskDefinitionKey();
		if(handle == HandleType.PASS.getHandle()){
			try {
				ActivitiParams pa1 = new ActivitiParams();
				pa1.setUserTaskId(nowTaskDefId);
				pa1.setBusType(busType);
				pa1.setBusinessId(busId);
				pa1.setProcessInstanceId(nowTask.getProcessInstanceId());
				if (this.isLastNodeByUserTaskDefKey(pa1).isLast()) {// 判断此时的节点是否为最后一个节点，如果最后，就999999
				// formKey = "999999";
				// Integer next2Status = NumberUtilsExt.toInt(formKey);
					proresult.setNext2Status(TaskStatus.BUSINESSSUCCESS.getStatus());
					proresult.setNext2TaskName(null);
					proresult.setNext2UserTaskId("0");
					// 删除那个流程
					pa.setMessage("模拟开启任务后删除！");
					pa.setTaskId(nowTask.getId());
					return proresult;
				}
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map = params.getCondition();
		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(busType);
		// 重启一个流程
		ProcessInstance pi = this._startProcessSimple(activitibus.getDeploymentKey());
		String processInstanceId = pi.getId();
		TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);
		try {
			tfcs.moveTo(nowTaskDefId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Task copyTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		String copyTaskId = copyTask.getId();
		Map<String, Object> variables = new HashMap<String, Object>();
		//根据当前的操作获取下一节点的状态码，退回，驳回，通过等
		if (handle == HandleType.RETURN.getHandle()) {
			variables.put("handle", "1.1");
		}
		else if (handle == HandleType.SUBMIT.getHandle()) {
			variables.put("handle", HandleType.PASS.getHandle().toString());
		} else {
			variables.put("handle", handle.toString());
		}
		
		ActivitiBranchControl activitiBranchControl = new ActivitiBranchControl();
		activitiBranchControl.setProcessKey(activitibus.getDeploymentKey());
		activitiBranchControl.setUserTaskDefkey(copyTask.getTaskDefinitionKey());
		List<ActivitiBranchControl> branchList =  activitiBranchControlService.getActivitiBranchControlList(activitiBranchControl);
		if (branchList != null && branchList.size() > 0) {
			map.put(ActivitiCondition.BRANCH_CONTROL.getCode(), branchList);
		}
		variables.put("map", map);
		
		//完成任务，除了退回特殊处理
		if(handle == HandleType.RETURNTOSTART.getHandle()){//完全退回用任务跳转，减轻流程图绘制的压力
			//如果不采取这种做法，就必须在每个节点上画一条线到首节点，然后再写上condition。增加流程绘制难度
			ActivitiTaskMoveComboboxShow atmc = new ActivitiTaskMoveComboboxShow();
			try {
				atmc = this.findFirstNode(copyTask);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			String firstNodeId = atmc.getUserTaskId();
			if(StringUtils.isNullOrBlank(firstNodeId)){//如果找不到首节点，直接退回
				ProcessResult error = new ProcessResult();
				error.setCode(ActivitiException.ERROR.getCode());
				error.setMessage("暂时无法直接退回首节点，请尝试退回上一节点");
				return error;
			}
			try {
				tfcs.moveTo(atmc.getUserTaskId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (handle == HandleType.RETURN.getHandle()) {// 退回上一步，可能存在误差
			try {
				tfcs.moveBack();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				taskService.complete(copyTaskId, variables);
			} catch (Exception e) {
				ProcessResult error = new ProcessResult();
				error.setCode(ActivitiException.ERROR.getCode());
				error.setMessage(ActivitiException.ERROR.getName());
				e.printStackTrace();
				logger.error(e.getMessage());
				return error;
			}
		}
		pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if(pi != null){
			Task copyTaskNext = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
			formKey = copyTaskNext.getFormKey();
			Integer next2Status = this.getFormKey(formKey);
			proresult.setNext2Status(next2Status);
			proresult.setNext2TaskName(copyTaskNext.getName());
			proresult.setNext2UserTaskId(copyTaskNext.getTaskDefinitionKey());
			// 删除那个流程
			pa.setMessage("模拟开启任务后删除！");
			pa.setTaskId(copyTaskNext.getId());
			this.deleteProcessInstacne(pa);
		}else{
			proresult.setNext2Status(888);
			proresult.setNext2TaskName("");
			proresult.setNext2UserTaskId("");
		}
		proresult.setCode(ActivitiException.SUCCESS.getCode());
		proresult.setMessage(ActivitiException.SUCCESS.getName());
		return proresult;
	}
	
	private ProcessInstance _startProcessSimple(String key){
		
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("applyuser", "1111");//如果后面全部删除掉的话，第一节点没有applyuser后，可以不用了
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key,variables);
		return pi;
	}
	//通过busId，任务在执行
	public void getComplexListNode(String businessId){
		ActivitiParams params = new ActivitiParams();
		
		String deploymentKey = this.queryBusinessTypeByBusinessId(businessId);
		params.setBusinessId(businessId);
		params.setDeploymentKey(deploymentKey);
		String taskId = this.queryTaskIdByBusinessIdComplex(params);
		if(StringUtils.isNotNullAndBlank(taskId)){
			
		}
		
	}
	//根据历史表进行历史节点获取
	public List<ActivitiTaskMoveComboboxShow> getHistoryListNodeByRemark(List<AssetProcessRemark> remarks) {
		List<ActivitiTaskMoveComboboxShow> nodeList = new ArrayList<ActivitiTaskMoveComboboxShow>();
		List<String> recordNode = new ArrayList<String>();
		Collections.reverse(remarks);
		for (AssetProcessRemark r : remarks) {
			String userTaskId = r.getUserTaskId();
			if (StringUtils.isNullOrBlank(userTaskId)) {
				continue;
			}
			if (!recordNode.contains(StringUtilsExt.convertNullToString(userTaskId))) {
				recordNode.add(StringUtilsExt.convertNullToString(userTaskId));
				ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
				node.setUserTaskId(userTaskId);
				node.setUserTaskName(r.getTaskName());
				nodeList.add(node);
			}
		}
		// 将节点排序
//		Comparator comparator = new ActivitiComparator();
//		Collections.sort(nodeList, comparator);
		return nodeList;
	}
	
	public String queryTaskIdByBusinessIdComplex(ActivitiParams params){
		String deploymentKey = params.getDeploymentKey();
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String businessKey = deploymentKey + "." + businessId;
		// 多执行的呢。？ 购物流程,无法成立
		List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).orderByTaskCreateTime().asc().list();
		if (taskList != null && taskList.size() > 1) {
			//如果快速点击，出现两个重复，就删除掉其中多余的一个
			for (int i = 0, len = taskList.size(); i < len - 1; i++) {
				params.setTaskId(taskList.get(i).getId());
				this.deleteProcessInstacne(params);
				assetBusHistoryService.deleteByBusIdAndPId(businessId, taskList.get(i).getProcessInstanceId());
				assetProcessRemarkService.deleteByBusIdAndPId(businessId, taskList.get(i).getProcessInstanceId());
			}
		} else if (taskList != null && taskList.size() == 0) {
			return "";
		} else if (taskList == null) {
			return "";
		}

		List<Task> taskListNew = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).orderByTaskCreateTime().asc().list();

		return taskListNew.get(0).getId();
		
	}
	
	public ProcessResult getCurrentTaskNodePermission(ActivitiParams params){
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		Map<String, Object> action = new HashMap<String, Object>();
		boolean isCheck = false;
//		Integer busState = params.getBusState();
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBussinessIdLike(StringUtilsExt.convertNullToString(params.getBusinessId()));
		String userCode = params.getUserCode();
		List<ApiRole> roles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, null);
		if(StringUtils.isNullOrBlank(taskId)){//如果不在流程里面的权限，需要分配的权限
			
		}
		//任务流程在运行中的权限，分两种，未审核和已审核
		List<String> roleCode = new ArrayList<String>();
		if (roles != null && roles.size() > 0) {
			for (ApiRole apiRole : roles) {
				if (!roleCode.contains(apiRole.getRoleCode())) {
					roleCode.add(apiRole.getRoleCode());
				}
			}
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String prodefid = task.getProcessDefinitionId();
		for (int i = 0, len = roleCode.size(); i < len; i++) {
			List<OwfActivityPermission> listOwfPermission = owfPermissionService.getListByRoleLike(roleCode.get(i), task.getTaskDefinitionKey(), prodefid);
			if (listOwfPermission != null && listOwfPermission.size() > 0) {
				isCheck = true;//当前任务，当前人可以审核
				break;
			}
		}
		
		ProcessResult result = new ProcessResult();
		if(task != null){
//			result.setCode(ActivitiException.TASKNOTFOUND.getCode());
//			result.setCode(ActivitiException.TASKNOTFOUND.getName());
//			return result;
			if (isCheck) {
				String pid = task.getProcessInstanceId();
				String userTaskDefId = task.getTaskDefinitionKey();
				ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
				String proKey = pi.getProcessDefinitionKey();
				AssetActivitiConfig aac = new AssetActivitiConfig();
				aac.setUsertaskdef(userTaskDefId);
				aac.setProdefkey(proKey);
				AssetActivitiConfig config = activitiConfigService.getOneConfigByKeyAndUserTaskDef(aac);
				if(config != null){
					
				}
				String [] groupsId = config.getGroupId().split("\\,");
				// 设置组号
//				activitiBizAction.setGroupId(config.getGroupId());
//				activitiBizAction.setGroupId(config.getGroupId());
				for (int i = 0, leni = groupsId.length; i < leni; i++) {
					ActivitiBizAction activitiBizAction = new ActivitiBizAction();
					activitiBizAction.setGroupId(Long.parseLong(groupsId[i]));
					List<ActivitiBizAction> abaList = activitiBizActionService.getActivitiBizActionList(activitiBizAction);
					if (abaList == null || abaList.size() == 0) {
						result.setCode(ActivitiException.GETRIGHTFAIL.getCode());
						result.setCode(ActivitiException.GETRIGHTFAIL.getName());
						logger.error(ActivitiException.GETRIGHTFAIL.getName());
						return result;
					}
					for (ActivitiBizAction activitiBizAction2 : abaList) {
						action.put(activitiBizAction2.getActionCode(), activitiBizAction2.getStatus());
					}
				}
			}else{//此时任务不在此人可以审核
				//查出此人是否操作这条记录
				List<AssetBusHistory> history = assetBusHistoryService.queryCheckListByBusinessIdUserCode(businessId, userCode);
				if (history == null || history.size() == 0) {
					//返回查看权限
				}
			}
		}
		result.setMessage("获取权限成功！");
		result.setCode(ActivitiException.SUCCESS.getCode());
		result.setAction(action);
		return result;
	}

	/**
	 * 
	 * <p>函数名称：临时指定一次授权，人或者组</p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busId
	 * @param userCode
	 * @return
	 *
	 * @date   创建时间：2017年3月7日
	 * @author 作者：jinbiao
	 */
	public ProcessResult forwardOrDesignTaskUsers(String busId, String userCode){
		ActivitiParams params = new ActivitiParams();
		params.setBusinessId(busId);
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBussinessIdLike(busId);
		ProcessResult result = new ProcessResult();
		if(StringUtils.isNullOrBlank(taskId)){
			result.setCode(ActivitiException.TASKNOTFOUND.getCode());
			result.setCode(ActivitiException.TASKNOTFOUND.getName());
			logger.error(ActivitiException.TASKNOTFOUND.getName());
			return result;
		}
		if(StringUtils.isNullOrBlank(userCode)){
			result.setCode(ActivitiException.FORWARDUSERISNULL.getCode());
			result.setCode(ActivitiException.FORWARDUSERISNULL.getName());
			logger.error(ActivitiException.FORWARDUSERISNULL.getName());
			return result;
		}
		taskService.setAssignee(taskId, userCode);
		
		result.setMessage("指定成功操作！");
		result.setCode(ActivitiException.SUCCESS.getCode());
		return null;
	}
	
	public ProcessResult forwardOrDesignTaskGroups(String busId, String group){
		ActivitiParams params = new ActivitiParams();
		params.setBusinessId(busId);
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBussinessIdLike(busId);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		ProcessResult result = new ProcessResult();
		if(StringUtils.isNullOrBlank(taskId)){
			result.setCode(ActivitiException.TASKNOTFOUND.getCode());
			result.setCode(ActivitiException.TASKNOTFOUND.getName());
			logger.error(ActivitiException.TASKNOTFOUND.getName());
			return result;
		}
//		String group = params.getForwardOrDesignGroups();
		String[] groups  = group.split("\\,");
		if(StringUtils.isNullOrBlank(group)){
			result.setCode(ActivitiException.DESIGNGROUPSISNULL.getCode());
			result.setCode(ActivitiException.DESIGNGROUPSISNULL.getName());
			logger.error(ActivitiException.DESIGNGROUPSISNULL.getName());
			return result;
		}
//		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
//		String pid = task.getProcessInstanceId();
		
//		taskService.claim(taskId, userCode);
		for (String string : groups) {
			taskService.addCandidateGroup(taskId, string);
//			taskService.addCandidateUser(taskId, userId);//代理
		}
		
		result.setMessage("指定成功操作！");
		result.setCode(ActivitiException.SUCCESS.getCode());
		return result;
	}
	
	/**
	 * 
	 * <p>
	 * 功能说明：审核流程图，该方法，返回所有节点，通过isPass区分
	 *
	 * 参数说明：
	 * </p>
	 * 
	 * @param params
	 * @return
	 * @throws DocumentException
	 *
	 * @date 创建时间：2016年12月20日
	 * @author 作者：jinbiao
	 */
	public ActivitiShowPic getHistoryListPic(ActivitiParams params) throws DocumentException {
		ActivitiShowPic pic = new ActivitiShowPic();
		boolean hasTempData = this.hasTempDataRemark(params.getBusinessId());
		//如果暂存情况下没有放入deploymentKey
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		List<AssetProcessRemark> countList = assetProcessRemarkService.queryListByBusId(businessId);
		int countTemp = 0;
		if (countList != null) {
			countTemp = countList.size();
		}
		if(countTemp == 0){
			return pic;
		}
		if(countTemp == TEMP_SIZE){//只有暂存数据的情况下
			return this.getHistoryOnlyOnePic(params, countList);
		}
		// 产出相应的流程Id，如果当前任务正在执行，则直接通过taskId直接查询，否则通过businessKey查询历史任务，再查询出pid
		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(params.getBusType());
		if (activitibus == null) {
			pic.setCode(ActivitiException.BUSNAMENOTFOUND.getCode());
			pic.setMessage(ActivitiException.BUSNAMENOTFOUND.getName());
			logger.error(ActivitiException.BUSNAMENOTFOUND.getName());
			return pic;
		}
		String businessKey = activitibus.getDeploymentKey() + "." + StringUtilsExt.convertNullToString(params.getBusinessId());
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBusinessId2(params);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		String pId = "";
		if (task != null) {
			// task不为空，说明任务正在运行
			pic.setNowLocation(task.getTaskDefinitionKey());
			pic.setEnd(false);
			pId = task.getProcessInstanceId();
		} else {
			// 否则,任务结束
			pic.setNowLocation(TaskStatus.FINISH.getName());
			pic.setEnd(true);
			List<HistoricProcessInstance> hisPro = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).orderByProcessInstanceStartTime().asc().list();
			// 从历史中 取出最后一个，最新的
			if (hisPro == null) {
				// 如果查询不到，直接返回错误
				pic.setCode(ActivitiException.HISTORYNOTFOUND.getCode());
				pic.setMessage(ActivitiException.HISTORYNOTFOUND.getName());
				logger.error(ActivitiException.HISTORYNOTFOUND.getName());
				return pic;
			}
			if (!(hisPro.size() > 0)) {
				pic.setCode(ActivitiException.HISTORYNOTFOUND.getCode());
				pic.setMessage(ActivitiException.HISTORYNOTFOUND.getName());
				logger.error(ActivitiException.HISTORYNOTFOUND.getName());
				return pic;
			}
			HistoricProcessInstance h = hisPro.get(hisPro.size() - 1);
			pId = h.getId();
		}

		// 查询出列表记录，如果记录查不到，就有问题，直接返回
		List<AssetProcessRemark> remarks = countList;
//		List<AssetProcessRemark> remarks = assetProcessRemarkService.queryBusinessHistory(StringUtilsExt.convertNullToString(params.getBusinessId()), pId);
		if (remarks.size() == 0 || remarks == null) {
			pic.setCode(ActivitiException.GETEMPTYREMARK.getCode());
			pic.setMessage(ActivitiException.GETEMPTYREMARK.getName());
			logger.error(ActivitiException.GETEMPTYREMARK.getName());
			return pic;
		}
		// List<AssetProcessRemark> remarkAA = new
		// ArrayList<AssetProcessRemark>();
		// for (AssetProcessRemark assetProcessRemark : remarks) {
		// remarkAA.add(assetProcessRemark);
		// }

		List<ActivitiTaskMoveComboboxShow> userTaskNameList = this.getHistoryListNodeByRemark(remarks);
		// List<ActivitiTaskMoveComboboxShow> userTaskNameList =
		// activitiToolsService.queryUserTaskIdListByBusinessId(params);
		if (!(userTaskNameList.size() > 0) || userTaskNameList == null) {
			// 获取节点失败，直接返回
			pic.setCode(ActivitiException.GETNODELISEFAIL.getCode());
			pic.setMessage(ActivitiException.GETNODELISEFAIL.getName());
			logger.error(ActivitiException.GETNODELISEFAIL.getName());
			return pic;
		}
		Collections.reverse(remarks);
		// 先将所有的节点放入记录，先放入节点列表
		List<ActivitiNode> nodeList = new ArrayList<ActivitiNode>();
		for (int i = 0, len = userTaskNameList.size(); i < len; i++) {
			ActivitiNode node = new ActivitiNode();
			node.setNodeName(userTaskNameList.get(i).getUserTaskName());
			node.setUserTaskId(userTaskNameList.get(i).getUserTaskId());
			nodeList.add(node);
		}
		if (remarks.size() > 0) {
			for (int i = 0, len = remarks.size(); i < len; i++) {
				if (StringUtils.isNullOrBlank(remarks.get(i).getTaskId())) {
					continue;
				}
				if (StringUtils.isNullOrBlank(remarks.get(i).getUserTaskId())) {
					continue;
				}
				// 判断该节点是否已经经过。
				for (int j = 0, lenj = nodeList.size(); j < lenj; j++) {
					// 正好处于活跃的节点，日志还没有记录，日志只记录之前的操作，只能从当前位置来判断，否则会缺失该节点。
					if (nodeList.get(j).getUserTaskId().contains(pic.getNowLocation())) {
						nodeList.get(j).setPass(true);
					}
					// 日志中能查询到的节点一定是经过了
					if (nodeList.get(j).getUserTaskId().contains(StringUtilsExt.convertNullToString(remarks.get(i).getUserTaskId()))) {
						nodeList.get(j).setPass(true);
						if(remarks.get(i).getOperatorName().length() == 2){
							String firstName = remarks.get(i).getOperatorName().substring(0, 1);
							String lastName = remarks.get(i).getOperatorName().substring(1, 2);
							String zuheName = firstName + "      " + lastName;//前端补整齐样式
							remarks.get(i).setOperatorName(zuheName);
						}
						nodeList.get(j).setExecutor(remarks.get(i).getOperatorName());
						break;
					}
				}
				// 将批注以及叙述放入
				RemarkLogStatements rls = new RemarkLogStatements();
				String description = StringUtilsExt.convertNullToString(remarks.get(i).getDescription());
				//忘记为什么要排除退回，退回录入岗的，先注释改换下面方式1，导致退回的信息有问题
//				if(remarks.get(i).getCheckResult() != HandleType.RETURN.getHandle() && remarks.get(i).getCheckResult() != HandleType.RETURNTOSTART.getHandle() ){
//					rls.setDescription(description);
//				}
				rls.setDescription(description);//方式1
				rls.setStatement(this.remarkProcess(remarks.get(i)));
				//类型
				rls.setType(remarks.get(i).getType());
				String pattern = params.getCommentTimePattern();
				
				String historyTaskId = null;
				if (!("0".equals(remarks.get(i).getTaskId()))) {
					if(hasTempData){
						historyTaskId = remarks.get(i).getTaskId();//取下一个点的时间才是正确的
					}else{
						historyTaskId = remarks.get(i+1).getTaskId();//取下一个点的时间才是正确的
					}
				}
				String spendTimePattern = params.getSpendTimePattern();
				String startTime = "";
				String endTime = "";
				String spendTime = "";
				Long span = 0l;
				// 添加每个节点任务耗时
				if (StringUtils.isNotNullAndBlank(historyTaskId) && !("0".equals(historyTaskId))) {
					span = historyService.createHistoricTaskInstanceQuery().taskId(historyTaskId).singleResult().getDurationInMillis();
					Date d1 = historyService.createHistoricTaskInstanceQuery().taskId(historyTaskId).singleResult().getStartTime();
					Date d2 = historyService.createHistoricTaskInstanceQuery().taskId(historyTaskId).singleResult().getEndTime();
					if (span != null) {
						spendTime = this.formatSecondTime(span);
					}
					if (d1 != null) {
						startTime = DateUtilsExt.formatDate(d1, spendTimePattern);
					}
					if (d2 != null) {
						endTime = DateUtilsExt.formatDate(d2, spendTimePattern);
					}
				}
				String time = DateUtilsExt.formatDate(DateUtilsExt.parseDate(remarks.get(i).getOperateDate(), "yyyyMMddHHmmssSSS"), pattern);
				String yearTime = time.split("\\ ")[0];
				String hourTime = time.split("\\ ")[1];

				rls.setTime(time);
				rls.setYearTime(yearTime);
				rls.setHourTime(hourTime);
				rls.setNodeName(remarks.get(i).getTaskName());
				rls.setExecutor(remarks.get(i).getOperatorName());
				rls.setStartTime(startTime);
				rls.setEndTime(endTime);
				//此处的长度用于解决单位录入耗时不准确的bug。len-2
				
//				if (i == len-2) {
//					rls.setTotalCostTime("0秒");
//				}
//				if (i != len-2) {
//					rls.setTotalCostTime(spendTime);
//				}
				Integer modify = NORMAL_MODIFY;
				if(hasTempData){
					modify = TEMP_MODIFY;
				}
				if (i == len-modify) {
					rls.setTotalCostTime("0秒");
				}
				if (i != len-modify) {
					rls.setTotalCostTime(spendTime);
				}
				ApiUser apiUser = DrcHepler.getInstance().findUseriInfoByUserCode(remarks.get(i).getOperatorId());
				if (apiUser != null) {
					if (StringUtils.isNullOrBlank(apiUser.getMobileNo())) {
						rls.setExcutorPhoneNumber("暂无电话号码！");
					} else {
						rls.setExcutorPhoneNumber(apiUser.getMobileNo());
					}
				}
				if (StringUtils.isNotNullAndBlank(remarks.get(i).getRemark())) {
					rls.setRemark("意见：" + remarks.get(i).getRemark());
				} else {
					rls.setRemark("");
//					rls.setRemark("意见：无");
				}
				pic.setListDetail(rls);
			}
		} else {
			pic.setCode(ActivitiException.GETEMPTYREMARK.getCode());
			pic.setMessage(ActivitiException.GETEMPTYREMARK.getName());
			logger.error(ActivitiException.GETEMPTYREMARK.getName());
			return pic;
		}
		// 如果节点还在运行评论数小于等于节点数
		for (int j = 0, lenj = nodeList.size(); j < lenj; j++) {
			// 正好处于活跃的节点，日志还没有记录，日志只记录之前的操作，只能从当前位置来判断，否则会缺失该节点。
			if (nodeList.get(j).getUserTaskId().contains(pic.getNowLocation())) {
				nodeList.get(j).setPass(true);
			}
		}
		// 判断活动节点
		for (int i = 0, len = nodeList.size(); i < len; i++) {
			if (pic.getNowLocation().equals(TaskStatus.FINISH.getName())) {
				break;
			}
			if (nodeList.get(i).getUserTaskId().equals(pic.getNowLocation())) {
				nodeList.get(i).setActive(true);
				break;
			}
		}
		pic.setNodeSequence(nodeList);
		if (nodeList.size() == 1) {
			ActivitiNode addOne = new ActivitiNode();
			addOne.setActive(false);
			addOne.setExecutor(".");
			addOne.setNodeName(".");
			addOne.setPass(false);
			addOne.setUserTaskId(".");
			nodeList.add(addOne);
		}
		pic.setCode(ActivitiException.SUCCESS.getCode());
		pic.setMessage(ActivitiException.SUCCESS.getName());
		//颠倒顺序
		Collections.reverse(pic.getList());
		return pic;
	}
	
	public ActivitiShowPic getHistoryOnlyOnePic(ActivitiParams params,List<AssetProcessRemark> list){
		ActivitiShowPic pic = new ActivitiShowPic();
		
		List<ActivitiTaskMoveComboboxShow> userTaskNameList = this.getHistoryListNodeByRemark(list);
		List<ActivitiNode> nodeList = new ArrayList<ActivitiNode>();
		for (int i = 0, len = userTaskNameList.size(); i < len; i++) {
			ActivitiNode node = new ActivitiNode();
			node.setNodeName(userTaskNameList.get(i).getUserTaskName());
			node.setUserTaskId(userTaskNameList.get(i).getUserTaskId());
			node.setPass(true);
			node.setActive(true);
			nodeList.add(node);
		}
		
		for (int i = 0, len = list.size(); i < len; i++) {
			if (StringUtils.isNullOrBlank(list.get(i).getTaskId())) {
				continue;
			}
			if (StringUtils.isNullOrBlank(list.get(i).getUserTaskId())) {
				continue;
			}
			// 判断该节点是否已经经过。
//			for (int j = 0, lenj = nodeList.size(); j < lenj; j++) {
//				// 正好处于活跃的节点，日志还没有记录，日志只记录之前的操作，只能从当前位置来判断，否则会缺失该节点。
//				if (nodeList.get(j).getUserTaskId().contains(pic.getNowLocation())) {
//					nodeList.get(j).setPass(true);
//				}
//				// 日志中能查询到的节点一定是经过了
//				if (nodeList.get(j).getUserTaskId().contains(StringUtilsExt.convertNullToString(list.get(i).getUserTaskId()))) {
//					nodeList.get(j).setPass(true);
//					nodeList.get(j).setExecutor(list.get(i).getOperatorName());
//					break;
//				}
//			}
			// 将批注以及叙述放入
			RemarkLogStatements rls = new RemarkLogStatements();
			String description = StringUtilsExt.convertNullToString(list.get(i).getDescription());
			rls.setDescription(description);
			rls.setStatement(this.remarkProcess(list.get(i)));
			//类型
			rls.setType(list.get(i).getType());
			String pattern = params.getCommentTimePattern();
			
			String time = DateUtilsExt.formatDate(DateUtilsExt.parseDate(list.get(i).getOperateDate(), "yyyyMMddHHmmssSSS"), pattern);
			String yearTime = time.split("\\ ")[0];
			String hourTime = time.split("\\ ")[1];

			rls.setTime(time);
			rls.setYearTime(yearTime);
			rls.setHourTime(hourTime);
			rls.setNodeName(list.get(i).getTaskName());
			rls.setExecutor(list.get(i).getOperatorName());
			pic.setListDetail(rls);
		}
		//添加一个空白点
		ActivitiNode node = new ActivitiNode();
		node.setNodeName("");
		node.setUserTaskId("");
		node.setPass(true);
		node.setActive(true);
		nodeList.add(node);
		
		pic.setNodeSequence(nodeList);
		pic.setEnd(false);
		pic.setCode(ActivitiException.SUCCESS.getCode());
		pic.setMessage(ActivitiException.SUCCESS.getName());
		return pic;
	}

	public List<Task> queryTaskByUserCodeOnlyTask(String userCode) {
		List<String> recordTask = new ArrayList<String>();
		// 获取正常情况下的登陆者任务
		List<Task> listAll = taskService.createTaskQuery().taskAssignee(userCode).orderByTaskCreateTime().asc().list();
//		List<Task> listAll = new ArrayList<Task>();
		// 专用于转发任务，同时制定多人
		List<Task> listLike = taskService.createTaskQuery().taskAssigneeLike("%" + userCode + "%").orderByTaskCreateTime().asc().list();
		// 如果指定任务没有，就通过授权者查找任务
		List<Task> listPermission = taskService.createTaskQuery().taskCandidateUser(userCode).list();
		for (Task task : listPermission) {
			recordTask.add(task.getExecutionId());
		}
		if (listAll != null && listAll.size() > 0) {
			for (Task task : listAll) {
				if(!recordTask.contains(task.getExecutionId())){
					recordTask.add(task.getExecutionId());
					listPermission.add(task);
				}
			}
		}
		if (listLike != null && listLike.size() > 0) {
			for (Task task : listLike) {
				if(!recordTask.contains(task.getExecutionId())){
					recordTask.add(task.getExecutionId());
					listPermission.add(task);
				}
			}
		}
		return listPermission;
	}
	
	public ProcessResult beforeHandleCheck(ActivitiParams params) {
		ProcessResult proresult = new ProcessResult();
		// 备注
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();
		String creatorId = params.getCreatorId();
		String userCode = params.getUserCode();
		Integer handle = params.getHandle();
		if (StringUtils.isNullOrBlank(userCode)) {
			proresult.setMessage(ActivitiException.USERIDISNULL.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
			proresult.setCode(ActivitiException.USERIDISNULL.getCode());
			logger.error(ActivitiException.USERIDISNULL.getName()+"("+busType+"-"+businessId+"_"+handle+")");
			return proresult;
		}
		if (StringUtils.isNullOrBlank(creatorId)) {
			proresult.setMessage(ActivitiException.CREATORISNULL.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
			proresult.setCode(ActivitiException.CREATORISNULL.getCode());
			logger.error(ActivitiException.CREATORISNULL.getName() + "(" + userCode + busType + "-" + businessId + "_" + handle + ")");
			return proresult;
		}
		
		if (params.getHandle() == null) {
			proresult.setMessage(ActivitiException.HANDLEERROR.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
			proresult.setCode(ActivitiException.HANDLEERROR.getCode());
			logger.error(ActivitiException.HANDLEERROR.getName() + "(" + busType + "-" + businessId + "_" + userCode + ")");
			return proresult;
		}
		if (StringUtils.isNullOrBlank(businessId)) {
			proresult.setMessage(ActivitiException.BUSINESSIDERROR.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
			proresult.setCode(ActivitiException.BUSINESSIDERROR.getCode());
			logger.error(ActivitiException.BUSINESSIDERROR.getName() + "(" + busType + "-" + userCode + "_" + handle + ")");
			return proresult;
		}
		AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(busType);
		if (activitibus == null) {
			proresult.setMessage(ActivitiException.BUSNAMENOTFOUND.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
			proresult.setCode(ActivitiException.BUSNAMENOTFOUND.getCode());
			logger.error(ActivitiException.BUSNAMENOTFOUND.getName() + "(" + userCode + busType + "-" + businessId + "_" + handle + ")");
			return proresult;
		}
		String bpmnIdIsKey = activitibus.getDeploymentKey();
		
		if(!this.isExistProcess(bpmnIdIsKey)){
			proresult.setMessage(ActivitiException.PROCESSNOTEXIT.getName()/*+"("+busType+"-"+businessId+"_"+handle+")"*/);
			proresult.setCode(ActivitiException.PROCESSNOTEXIT.getCode());
			logger.error(ActivitiException.PROCESSNOTEXIT.getCode() + "(" + userCode + busType + "-" + businessId + "_" + handle + ")");
			return proresult;
		}
		proresult.setCode(ActivitiException.SUCCESS.getCode());
		proresult.setMessage(ActivitiException.SUCCESS.getName());
		proresult.setActivitibus(activitibus);
		
		return proresult;
	}

	//随机获取一个人
	public ApiUser getRandomUser(List<ApiUser> listUser){
		int length = listUser.size();
		Random rand = new Random();
		if(length == 1){
			return listUser.get(0);
		}
		int randomIndex = rand.nextInt(length-1);
		return listUser.get(randomIndex);
		
	}
	
	/**
	 * 
	 * <p>函数名称：取消操作检测 </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年3月7日
	 * @author 作者：jinbiao
	 */
//	public ProcessResult cancelOptionCheck(ActivitiParams params){
//		ProcessResult result = new ProcessResult(); 
//		Integer handle = params.getHandle();
//		Integer reverseHandle = params.getHandle();
//		AssetBusHistory record = new AssetBusHistory();
//		String businessId = params.getBusinessId().toString();
//		String userCode = params.getUserCode();
//		record.setBusId(businessId);
//		record.setOperatorId(userCode);
//		
//		if(handle == HandleType.CANCELRETURN.getHandle()){
//			reverseHandle = HandleType.RETURN.getHandle();
//			record.setCheckResult(HandleType.RETURN.getHandle());
//		}else if(handle == HandleType.CANCELPASS.getHandle()){
//			reverseHandle = HandleType.PASS.getHandle();
//			record.setCheckResult(HandleType.PASS.getHandle());
//		}else if(handle == HandleType.CANCELREJECT.getHandle()){
//			reverseHandle = HandleType.REJECT.getHandle();
//			record.setCheckResult(HandleType.REJECT.getHandle());
//		}
//		
//		List<AssetBusHistory> hisListAll = new ArrayList<AssetBusHistory>();
//		List<AssetBusHistory> hisList = new ArrayList<AssetBusHistory>();
//		// 查询某个任务的所有历史
//		hisListAll = assetBusHistoryService.queryCheckListByBusinessIdUserCode(businessId, userCode);
//		// 查出某个用户的所有某种操作
//		hisList = assetBusHistoryService.queryCheckList(record);
//		AssetBusHistory his = new AssetBusHistory();
//		AssetBusHistory hisAll = new AssetBusHistory();
//		if (hisListAll != null && hisListAll.size() == 0) {
//			result.setMessage(ActivitiException.HISTORYOPTIONNOFOUND.getName());
//			result.setCode(ActivitiException.HISTORYOPTIONNOFOUND.getCode());
//			return result;
//		}
//		if (hisList != null && hisList.size() == 0) {
//			result.setMessage(ActivitiException.NOREVERSEHANDLE.getName());
//			result.setCode(ActivitiException.NOREVERSEHANDLE.getCode());
//			return result;
//		}
//		// 全部历史
//		hisAll = hisListAll.get(0);
//		his = hisList.get(0);
//		
//		if (reverseHandle != his.getCheckResult()) {
//			result.setMessage(ActivitiException.NOREVERSEHANDLESIMPLE.getName());
//			result.setCode(ActivitiException.NOREVERSEHANDLESIMPLE.getCode());
//			return result;
//		}
//		
//		result.setCode(ActivitiException.SUCCESS.getCode());
//		result.setMessage(ActivitiException.SUCCESS.getName());
//		return result;
//	}
	/**
	 * 
	 * <p>函数名称： 权限校验</p>
	 * <p>功能说明： 任务完成权限校验
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCode
	 * @param nowTask
	 * @return
	 *
	 * @date   创建时间：2017年3月8日
	 * @author 作者：jinbiao
	 */
	public ProcessResult beforeCompleteCheck(String userCode, Task nowTask, String businessId, String busType) {
		/**
		 *2018年3月25日14:15:48
		 * 添加根据单位或者提交者或者业务，来决定审核的人
		 * 此处校验代码暂不修改，如果遇到标志位为特殊授权的时候
		 * 此人的角色也必定要预先设定在授权表里面，即所授权的人物需包含在所授权的角色里面
		 */
		ProcessResult result = new ProcessResult();
		List<ApiRole> roles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, AppContext.getAppContext().getAppConfiguration().getAppId());
		String roleString = "";
		for (ApiRole apiRole : roles) {
			if(apiRole != null){
				roleString += apiRole.getRoleCode();
				roleString += ",";
			}
		}
		if (roles == null || roles.size() == 0) {
			result.setMessage(ActivitiException.ROLEERROR.getName());
			result.setCode(ActivitiException.ROLEERROR.getCode());
			logger.error(ActivitiException.ROLEERROR.getName());
			return result;
		}
		List<OwfActivityPermission> list = owfPermissionService.queryOwfActivityPermissionByProcessDefId(nowTask.getProcessDefinitionId(), nowTask.getTaskDefinitionKey());
		if (list == null || list.size() == 0) {
			result.setMessage(ActivitiException.GETNEXTUSESRFAIL.getName());
			result.setCode(ActivitiException.GETNEXTUSESRFAIL.getCode());
			logger.error(ActivitiException.GETNEXTUSESRFAIL.getName());
			return result;
		}
		for (ApiRole role : roles) {
			for (int i = 0, leni = list.size(); i < leni; i++) {
				String[] groups = list.get(i).getGrantedGroups().split("\\;");
				List<String> groupsList = Arrays.asList(groups);
				if (groupsList.contains(role.getRoleCode())) {
					result.setCode(ActivitiException.SUCCESS.getCode());
					result.setMessage(ActivitiException.SUCCESS.getName());
					return result;
				}
				if (StringUtils.isNotNullAndBlank(list.get(i).getAssignedUser())) {
					if (list.get(i).getAssignedUser().contains(userCode)) {
						result.setCode(ActivitiException.SUCCESS.getCode());
						result.setMessage(ActivitiException.SUCCESS.getName());
						return result;
					}
				}
				if (StringUtils.isNotNullAndBlank(list.get(i).getGrantedUsers())) {
					String[] users = list.get(i).getGrantedGroups().split("\\;");
					List<String> userLists = Arrays.asList(users);
					if (userLists.contains(userCode)) {
						result.setCode(ActivitiException.SUCCESS.getCode());
						result.setMessage(ActivitiException.SUCCESS.getName());
						return result;
					}
				}
			}
		}
		result.setCode(ActivitiException.CURRENTUSERNOTRIGHT.getCode());
		result.setMessage(ActivitiException.CURRENTUSERNOTRIGHT.getName());
		logger.error(userCode + "角色编码：" + roleString + "_" + "想要执行环节为：(" + nowTask.getName() + ")的流程" + "busId:" + businessId + "业务类型:" + busType);
		return result;
	}
	public ProcessResult getCurrentRoleCode(Object businessId){
		ProcessResult result = new ProcessResult();
		String busId = StringUtilsExt.convertNullToString(businessId);
		if(StringUtils.isNullOrBlank(busId)){
			result.setCode(ActivitiException.BUSINESSIDERROR.getCode());
			result.setMessage(ActivitiException.BUSINESSIDERROR.getName());
//			logger.error(businessId + ActivitiException.BUSINESSIDERROR.getName());
			return result;
		}
		ActivitiParams params = new ActivitiParams();
		params.setBusinessId(busId);
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBussinessIdLike(busId);
		if(StringUtils.isNullOrBlank(taskId)){
			result.setCode(ActivitiException.TASKNOTFOUND.getCode());
			result.setMessage(ActivitiException.TASKNOTFOUND.getName());
//			logger.error(businessId + ActivitiException.TASKNOTFOUND.getName());
			return result;
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String defId = task.getProcessDefinitionId();
		String userTaskId = task.getTaskDefinitionKey();
		List<OwfActivityPermission> list = owfPermissionService.queryOwfActivityPermissionByProcessDefId(defId, userTaskId);
		if(list == null || list.size() == 0){
			result.setCode(ActivitiException.PERMISSIONERROR.getCode());
			result.setMessage(ActivitiException.PERMISSIONERROR.getName());
			return result;
		}
		String roleCodes = list.get(0).getGrantedGroups();
		List<String> roleCode = new ArrayList<String>();
		if (StringUtils.isNotNullAndBlank(roleCodes)) {
			String [] roleCodeArray = roleCodes.split("\\;");
			roleCode = Arrays.asList(roleCodeArray);
			result.setRoleCodes(roleCode);
			result.setCode(ActivitiException.SUCCESS.getCode());
			result.setMessage(ActivitiException.SUCCESS.getName());
		}else{
			result.setCode(ActivitiException.PERMISSIONERROR.getCode());
			result.setMessage(ActivitiException.PERMISSIONERROR.getName());
//			logger.error(ActivitiException.PERMISSIONERROR.getName());
		}
		return result;
	}
	
	public String getCurrentRoleCode(DelegateTask task){
		String roleCode = "";
		List<OwfActivityPermission> list = owfPermissionService.queryOwfActivityPermissionByProcessDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		roleCode = list.get(0).getGrantedGroups();
		
		return roleCode;
	}
	
	//定位某人在流程中的位置
	public ProcessResult getUserTaskDefKey(Object businessId,String userCode,String roleCode){
		ProcessResult result = new ProcessResult();
		List<String> roleCodeList = new ArrayList<String>();
		List<OwfActivityPermission> listOwfActivityPermissionAll = new ArrayList<OwfActivityPermission>();
		String busId = StringUtilsExt.convertNullToString(businessId);
		if(StringUtils.isNullOrBlank(busId)){
			result.setCode(ActivitiException.BUSINESSIDERROR.getCode());
			result.setMessage(ActivitiException.BUSINESSIDERROR.getName());
			logger.error(ActivitiException.BUSINESSIDERROR.getName());
			return result;
		}
		ActivitiParams params = new ActivitiParams();
		params.setBusinessId(businessId);
		String taskId = this.queryTaskIdByBusinessIdLike(params);
//		String taskId = this.queryTaskIdByBussinessIdLike(busId);
		if(StringUtils.isNullOrBlank(taskId)){
			result.setCode(ActivitiException.TASKNOTFOUND.getCode());
			result.setMessage(ActivitiException.TASKNOTFOUND.getName());
			logger.error(ActivitiException.TASKNOTFOUND.getName());
			return result;
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String defId = task.getProcessDefinitionId();
//		String userTaskId = task.getTaskDefinitionKey();
		if(StringUtils.isNotNullAndBlank(roleCode)){
			roleCodeList.add(roleCode);
		}else{
			List<ApiRole> roles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, AppContext.getAppContext().getAppConfiguration().getAppId());
			if (roles != null && roles.size() > 0) {
				for (ApiRole apiRole : roles) {
					roleCodeList.add(apiRole.getRoleCode());
				}
			}
		}
		//定位某人某个流程属于他的节点信息
		for (String roleCode1 : roleCodeList) {
			List<OwfActivityPermission> listOwfActivityPermission = owfPermissionService.getListByRoleCodeLike(roleCode1, defId);
			listOwfActivityPermissionAll.addAll(listOwfActivityPermission);
		}
		
		return result;
	}
	
	// 判断是否为首节点，如果为首节点，退回或者驳回后将删除流程
	@SuppressWarnings({ "unused"})
	public List<ActivitiUserTaskNode> getNextNode(Map<String, Object> map) throws DocumentException {
		List<String> recordNode = new ArrayList<String>();
		String taskId = MapUtilsExt.getString(map, "taskId");
		String currentTaskDefKey = MapUtilsExt.getString(map, "currentTaskDefKey");
		ActivitiUserTaskNode node = new ActivitiUserTaskNode();
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		List<ActivitiUserTaskNode> nextNodeList = new ArrayList<ActivitiUserTaskNode>();
		// 查询出当前的任务，不在跳转列表内
		ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());

		String deploymentName = pd.getResourceName();
		String deploymentId = pd.getDeploymentId();
		ActivitiParams params = new ActivitiParams();
		params.setDeploymentId(deploymentId);
		params.setResourceName(deploymentName);

		InputStream in = this.queryResourceInputStream(params);
		Document document = XmlUtilsExt.createDocument(in);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取任务usertask
		List<Element> userTaskList = process.elements("userTask");
		// 获取名称
		List<Element> sequenceFlowList = process.elements("sequenceFlow");
		//第一步，先找出正向流程中的下一个节点
		for (Element sfl : sequenceFlowList) {
			String sourceRef = sfl.attributeValue("sourceRef");
			String targetRef = sfl.attributeValue("targetRef");
			if (sourceRef.equals(currentTaskDefKey)) {
				ActivitiUserTaskNode actnode = new ActivitiUserTaskNode();
				if (!recordNode.contains(targetRef)) {
					/**
					 * 以下注释原因，去除根据流程条件上的Condition配置来获取下一环节。
					 * 去除之后，流程配置文件可以省略除了第一个节点外的条件配置
					 * handle=='9'
					 */
//					Element e = sfl.element("conditionExpression");
//					if (e == null) {
//						continue;
//					} else {
//						String option = StringUtilsExt.convertNullToString(e.getText());
//						if (option.contains(HandleType.PASS.getHandle().toString())) {
							actnode.setUserTaskId(targetRef);
//							actnode.setCondition(option);
							nextNodeList.add(actnode);
							recordNode.add(targetRef);
						}
//					}
//				}
			}
		}
		for (Element e : userTaskList) {
			String assingee = e.attributeValue("assignee");
			String formKey = e.attributeValue("formKey");
			String name = e.attributeValue("name");
			String id1 = e.attributeValue("id");
			Element documentation = e.element("documentation");
			String description = "";
			if(documentation != null){
				description = StringUtilsExt.convertNullToString(documentation.getTextTrim());
			}
//			System.out.println(documentation.getTextTrim());
			for (ActivitiUserTaskNode node1 : nextNodeList) {
				if (node1.getUserTaskId().equals(id1)) {
					node1.setUserTaskName(name);
					node1.setFormKey(formKey);
					node1.setDocumentmentation(description);
				}
			}
		}
		return nextNodeList;
	}
	
	// 判断是否为首节点，如果为首节点，退回或者驳回后将删除流程
		@SuppressWarnings({ "unused" })
		public List<ActivitiUserTaskNode> getPreviewNode(Map<String, Object> map) throws DocumentException {
			List<String> recordNode = new ArrayList<String>();
			String taskId = MapUtilsExt.getString(map, "taskId");
			String currentTaskDefKey = MapUtilsExt.getString(map, "currentTaskDefKey");
			ActivitiUserTaskNode node = new ActivitiUserTaskNode();
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			List<ActivitiUserTaskNode> nextNodeList = new ArrayList<ActivitiUserTaskNode>();
			// 查询出当前的任务，不在跳转列表内
			ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());

			String deploymentName = pd.getResourceName();
			String deploymentId = pd.getDeploymentId();
			ActivitiParams params = new ActivitiParams();
			params.setDeploymentId(deploymentId);
			params.setResourceName(deploymentName);

			InputStream in = this.queryResourceInputStream(params);
			Document document = XmlUtilsExt.createDocument(in);
			Element root = document.getRootElement();
			// 获取xml的节点
			Element process = root.element("process");
			// 获取任务usertask
			List<Element> userTaskList = process.elements("userTask");
			// 获取名称
			List<Element> sequenceFlowList = process.elements("sequenceFlow");
			//第一步，先找出正向流程中的下一个节点
			for (Element sfl : sequenceFlowList) {
				String sourceRef = sfl.attributeValue("sourceRef");
				String targetRef = sfl.attributeValue("targetRef");
				if (targetRef.equals(currentTaskDefKey)) {
					ActivitiUserTaskNode actnode = new ActivitiUserTaskNode();
					if (!recordNode.contains(sourceRef)) {
						Element e = sfl.element("conditionExpression");
						if (e == null) {
							continue;
						} else {
							String option = StringUtilsExt.convertNullToString(e.getText());
							if (option.contains(HandleType.PASS.getHandle().toString())) {
								actnode.setUserTaskId(targetRef);
								actnode.setCondition(option);
								nextNodeList.add(actnode);
								recordNode.add(targetRef);
							}
						}
					}
				}
			}
			for (Element e : userTaskList) {
				String assingee = e.attributeValue("assignee");
				String formKey = e.attributeValue("formKey");
				String name = e.attributeValue("name");
				String id1 = e.attributeValue("id");
				Element documentation = e.element("documentation");
				String description = StringUtilsExt.convertNullToString(documentation.getTextTrim());
//				System.out.println(documentation.getTextTrim());
				for (ActivitiUserTaskNode node1 : nextNodeList) {
					if (node1.getUserTaskId().equals(id1)) {
						node1.setUserTaskName(name);
						node1.setFormKey(formKey);
						node1.setDocumentmentation(description);
					}
				}
			}
			return nextNodeList;
		}
	//判断是否有暂存数据
	public boolean hasTempDataRemark(Object busId){
		AssetProcessRemark assetProcessRemark = new AssetProcessRemark();
		assetProcessRemark.setId(null);
		assetProcessRemark.setBusId(StringUtilsExt.convertNullToString(busId));
		assetProcessRemark.setTaskStatus(TaskStatus.ZANCUN.getStatus());
		List<AssetProcessRemark> list = assetProcessRemarkService.getAssetProcessRemarkList(assetProcessRemark);
		if(list != null && list.size() > 0){
			return true;
		}
		
		return false;
	}
	//获取暂存状态下的业务类型
	public String getTempBusType(Object busId){
		String busType = "";
		AssetProcessRemark assetProcessRemark = new AssetProcessRemark();
		assetProcessRemark.setId(null);
		assetProcessRemark.setBusId(StringUtilsExt.convertNullToString(busId));
		assetProcessRemark.setTaskStatus(TaskStatus.ZANCUN.getStatus());
		List<AssetProcessRemark> list = assetProcessRemarkService.getAssetProcessRemarkList(assetProcessRemark);
		if(list != null && list.size() > 0){
			AssetActivitiBus aab = assetActivitiBusService.queryAssetActivitiBusByKey(list.get(0).getBusType());
			if(aab != null){
				busType = aab.getBusType();
			}
		}
		return busType;
	}

	public boolean hasRejectEndOption(ActivitiParams params){
		try {
			String userCode = params.getUserCode();
			String busType = params.getBusType();
			String afterSplit = "";
			if(StringUtils.isNotNullAndBlank(busType)){
				afterSplit = busType.split("\\_")[0];
			}
			if(StringUtils.isNullOrBlank(userCode)){
				return false;
			}
			List<ApiRole> roles = DrcHepler.getInstance().findUserRolesByUserCode(userCode, AppContext.getAppContext().getAppConfiguration().getAppId());
			ActivitiBranchControl rejectBranch = new ActivitiBranchControl();
			rejectBranch.setTypeCode("reject");
			List<ActivitiBranchControl> rejectList =  activitiBranchControlService.getActivitiBranchControlList(rejectBranch);
			if(rejectList == null){
				return false;
			}
			if(rejectList.size() == 0){
				return false;
			}
			for (ActivitiBranchControl activitiBranchControl : rejectList) {
				if("*".equals(activitiBranchControl.getProcessKey())){//星号标识所有的流程都有驳回
					String typeValue = activitiBranchControl.getTypeValue();
					if(typeValue.contains(userCode)){
						return true;
					}
					for (ApiRole role : roles) {
						if(typeValue.contains(role.getRoleCode())){
							return true;
						}
					}
				}
				if(StringUtils.isNotNullAndBlank(afterSplit)){
					if(busType.equals(activitiBranchControl.getProcessKey())){//专门针对于某个流程的驳回操作
						String typeValue = activitiBranchControl.getTypeValue();
						if(typeValue.contains(userCode)){
							return true;
						}
						for (ApiRole role : roles) {
							if(typeValue.contains(role.getRoleCode())){
								return true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
		return false;
	}
	
	public List<ActivitiTaskMoveComboboxShow> getReturnList(ActivitiParams params){
		List<ActivitiTaskMoveComboboxShow> nodeList = new ArrayList<ActivitiTaskMoveComboboxShow>();
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String busType = params.getBusType();//获取业务类型
		if(StringUtils.isNullOrBlank(busType) || StringUtils.isNullOrBlank(businessId)){
			return nodeList;
		}
//		AssetActivitiBus aab = assetActivitiBusService.queryAssetActivitiBus(busType);
//		if(aab == null){
//			return nodeList;
//		}
//		String deploymentKey = aab.getDeploymentKey();
		//按照时间顺序排列，去除批注顺序
		List<AssetProcessRemark> remarkList = assetProcessRemarkService.queryListByBusId(businessId);
		if(remarkList == null || remarkList.size() == 0){
			return nodeList;
		}
		String taskId = this.queryTaskIdByBusinessIdLike(params);//找出当前任务位于哪个哪个节点上
		if (StringUtils.isNullOrBlank(taskId)) {
			return nodeList;
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null) {
			return nodeList;
		}
		String nowUserTaskDef = task.getTaskDefinitionKey();//当前的usertask
		
		List<ActivitiTaskMoveComboboxShow> userTaskNameList = this.getHistoryListNodeByRemark(remarkList);
		nodeList = userTaskNameList;
		for (int i=0,leni=userTaskNameList.size();i<leni;i++) {//删除该节点之后的节点，退回只能退回到他之前的节点
			if(nowUserTaskDef.equals(userTaskNameList.get(i).getUserTaskId())){
				break;
			}else{
				nodeList.remove(0);
			}
		}
		if(nodeList.get(nodeList.size()-1).getUserTaskId().equals("usertask0")){//去除暂存数据
			nodeList.remove(nodeList.size()-1);
		}
		return nodeList;
	}
	
	//自由跳转
	public List<ActivitiTaskMoveComboboxShow> getReturnList2(ActivitiParams params){
		List<ActivitiTaskMoveComboboxShow> nodeList = new ArrayList<ActivitiTaskMoveComboboxShow>();
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		//按照时间顺序排列，去除批注顺序
		List<AssetProcessRemark> remarkList = assetProcessRemarkService.queryListByBusIdNoReturn(businessId);
		if(remarkList == null || remarkList.size() == 0){
			return nodeList;
		}
		//这个for取出最新的一次操作，定位到此时的节点在哪里
		List<AssetProcessRemark> remarkLastest = assetProcessRemarkService.queryListByBusId(businessId);
		String nowTaskId = null;
		for (AssetProcessRemark assetProcessRemark : remarkLastest) {
			nowTaskId = assetProcessRemark.getNextNodeId();
			if(StringUtils.isNotNullAndBlank(nowTaskId)){
				break;
			}
		}
//		String nowTaskId = remarkList.get(0).getNextNodeId();
//		String sourceId = remarkList.get(0).getUserTaskId();
//		String nowTaskId = remarkList.get(0).getNextNodeId();
		//这个for去掉当前之后的所有节点
		for (int i = 0, len = remarkList.size(); i < len; i++) {
			if (!nowTaskId.contains(remarkList.get(0).getNextNodeId())) {
				remarkList.remove(0);
			}
		}
		String sourceId = remarkList.get(0).getUserTaskId();
		if(StringUtilsExt.isBlank(nowTaskId)){
			return nodeList;
		}
		List<String> recordList = new ArrayList<String>();//记录是否记录节点
		ActivitiTaskMoveComboboxShow node = new ActivitiTaskMoveComboboxShow();
		node.setUserTaskName(remarkList.get(0).getTaskName());
		node.setUserTaskId(remarkList.get(0).getUserTaskId());
		nodeList.add(node);
		recordList.add(remarkList.get(0).getUserTaskId());
		recordList.add(nowTaskId);
		remarkList.remove(0);
		for (int i = 0, len = remarkList.size(); i < len; i++) {
//			System.out.println(sourceId+"————————"+remarkList.get(0).getNextNodeId());
			if(sourceId.equals(remarkList.get(0).getNextNodeId()) && StringUtilsExt.isNotBlank(sourceId)){
				String taskId = remarkList.get(0).getUserTaskId();
				String name = remarkList.get(0).getTaskName();
				if(StringUtilsExt.isNotBlank(taskId)){
					recordList.add(remarkList.get(0).getUserTaskId());
					ActivitiTaskMoveComboboxShow node1 = new ActivitiTaskMoveComboboxShow();
					node1.setUserTaskId(taskId);
					node1.setUserTaskName(name);
					nodeList.add(node1);
					sourceId = remarkList.get(0).getUserTaskId();
				}
			}
			remarkList.remove(0);
		}
		return nodeList;
	}
	
	
	//建议业务自行判断，工作流判断需要调用3次sql
	public void isView(ActivitiParams params){
		AssetProcessRemark receiveActlog = new AssetProcessRemark();//接收日志
		receiveActlog.setType(0);//接收批注内容
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		// 获取当前任务执行者，也可能是登陆者
		String userName = params.getUserName();
		String userCode = params.getUserCode();
		
		String taskId = this.queryTaskIdByBusinessIdLike(params);
		if(StringUtils.isNullOrBlank(taskId)){
			return ;
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String deploymenKey = task.getProcessDefinitionId().split("\\:")[0];
		receiveActlog.setOperatorId(userCode);
		receiveActlog.setOperatorName(userName);
		receiveActlog.setOperateDate(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		receiveActlog.setTaskId(task.getId());
		receiveActlog.setProcInstId(task.getProcessInstanceId());
		receiveActlog.setUserTaskId(task.getTaskDefinitionKey());
		receiveActlog.setDescription(task.getDescription());
		
		receiveActlog.setCheckResult(HandleType.VIEW.getHandle());
//		receiveActlog.setTaskName(HandleType.VIEW.getName());

		receiveActlog.setBusType(deploymenKey);
		receiveActlog.setBusId(businessId);
		receiveActlog.setTaskStatus(this.getFormKey(task.getFormKey()));
//		receiveActlog.setTaskStatus(Integer.parseInt(task.getFormKey()));
		
		AssetProcessRemark delProRem = new AssetProcessRemark();
		delProRem.setId(null);
		delProRem.setOperatorId(userCode);
		delProRem.setCheckResult(HandleType.VIEW.getHandle());
		delProRem.setUserTaskId(task.getTaskDefinitionKey());
		List<AssetProcessRemark> orignView = assetProcessRemarkService.getAssetProcessRemarkList(delProRem);
//		String orignViewTime = "";
		if(orignView != null && orignView.size() > 0){
			return ;
//			assetProcessRemarkService.delAssetProcessRemark(delProRem);//如果重复调用，删除旧的，再新增
//			orignViewTime = orignView.get(0).getOperateDate();
//			receiveActlog.setOperateDate(orignViewTime);
		}
		assetProcessRemarkService.addAssetProcessRemark(receiveActlog);
	}
	
	/**
	 * 
	 * <p>函数名称： 获取返回节点的准确id </p>
	 * <p>功能说明： 在退回操作中，如果通过owf来退回，只能获取index(0)的，可能不准确而导致问题，通过此来精确定位，备用
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年4月28日
	 * @author 作者：jinbiao
	 */
	public String getAccurateBackUsertaskId(ActivitiParams params){
		String usertaskId = "";
		String nowTaskId = params.getUserTaskId();//当前节点的ID
		
		List<AssetProcessRemark> list = assetProcessRemarkService.queryListByBusId(StringUtilsExt.convertNullToString(params.getBusinessId()));
		for (AssetProcessRemark assetProcessRemark : list) {
			if(nowTaskId.equals(StringUtilsExt.convertNullToString(assetProcessRemark.getNextNodeId()))){
				if(StringUtils.isNotNullAndBlank(assetProcessRemark.getUserTaskId())){
					usertaskId = assetProcessRemark.getUserTaskId();
					break;
				}
			}
		}
		return usertaskId;
	}
	//获取单据状态
	public Integer getFormKey(String formKey){
		if(StringUtils.isNullOrBlank(formKey)){
			return 0;
		}else{
			return NumberUtilsExt.toInt(formKey.split("\\@")[0], 0);
		}
	}
	//获取单据类型
	public String getFormType(String formKey){
		if(StringUtils.isNullOrBlank(formKey)){
			return null;
		}else{
			String[] formkeys = formKey.split("\\@");
			if(formkeys.length > 1){
				return formkeys[1];
			}else{
				return null;
			}
		}
	}
	
	public String getCurrentRoleName(String roleCode){
		if(StringUtils.isNotNullAndBlank(roleCode)){
			return assetProcessRemarkService.queryRoleNameByRoleCode(roleCode);
		}
		return "";
	}
	public String getCurrentRoleCodeIner(String businessId){
		ProcessResult result = this.getCurrentRoleCode(businessId);
		if(ActivitiException.SUCCESS.getCode().equals(result.getCode())){
			List<String> codes = result.getRoleCodes();
			return codes.get(0);
		}
		return "";
	}
}
