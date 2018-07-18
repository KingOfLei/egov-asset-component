package com.bosssoft.egov.asset.activiti.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.openwebflow.ctrl.TaskFlowControlService;
import org.openwebflow.ctrl.TaskFlowControlServiceFactory;
import org.openwebflow.mgr.mybatis.service.SqlActivityPermissionManager;
import org.openwebflow.mgr.mybatis.service.SqlDelegationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bosssoft.egov.asset.activiti.activitiKit.ActivitiToolsService;
import com.bosssoft.egov.asset.activiti.activitiKit.AlarmToolsService;
import com.bosssoft.egov.asset.activiti.entity.ActivitiBranchControl;
import com.bosssoft.egov.asset.activiti.entity.ActivitiCondition;
import com.bosssoft.egov.asset.activiti.entity.ActivitiException;
import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiTaskMoveComboboxShow;
import com.bosssoft.egov.asset.activiti.entity.AssetActivitiBus;
import com.bosssoft.egov.asset.activiti.entity.OwfActivityPermission;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.activiti.service.ActivitiAmcService;
import com.bosssoft.egov.asset.activiti.service.ActivitiBranchControlService;
import com.bosssoft.egov.asset.activiti.service.ActivitiProcessService;
import com.bosssoft.egov.asset.activiti.service.AssetActivitiBusService;
import com.bosssoft.egov.asset.activiti.service.AssetBusHistoryService;
import com.bosssoft.egov.asset.activiti.service.AssetProcessRemarkService;
import com.bosssoft.egov.asset.activiti.service.OwfActivityPermissionService;
import com.bosssoft.egov.asset.common.idgenerator.GUID;
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
 * @ClassName 类名：ActivitiAmcServiceImpl
 * @Description 功能说明：activiti后台管理
 ************************************************************************
 * @date 创建日期：2016年11月2日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          2016年11月2日 jinbiao 创建该类功能。
 */
@Service
@Transactional
public class ActivitiAmcServiceImpl implements ActivitiAmcService {

	private static Logger logger = LoggerFactory.getLogger(ActivitiAmcServiceImpl.class);
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
	private SqlActivityPermissionManager activityPermissionManager;
	@Autowired
	private SqlDelegationManager sqlDelegationManager;
	@Autowired
	private AssetActivitiBusService assetActivitiBusService;
	@Autowired
	private OwfActivityPermissionService owfActivityPermissionService;
	@Autowired
	private ActivitiToolsService activitiToolsService;
	@Autowired
	private ActivitiProcessService activitiProcessService;
	@Autowired
	private TaskFlowControlServiceFactory taskFlowControlServiceFactory;
	@Autowired
	private AssetBusHistoryService assetBusHistoryService;
	@Autowired
	private AssetProcessRemarkService assetProcessRemarkService;
	@Autowired
	private ActivitiBranchControlService activitiBranchControlService;
	@Autowired
	private AlarmToolsService alarmToolsService;

	public String queryTaskIdByBusinessId(ActivitiParams params) {

		String busType = params.getBusType();
		String businessId = StringUtilsExt.convertNullToString(params.getBusinessId());
		String businessKey = busType + "." + businessId;
		// 多执行的呢。？ 购物流程,无法成立
		Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
		if (task == null) {
			return "";
		}
		return task.getId();
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

		String taskId = this.queryTaskIdByBusinessId(params);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// String taskId = this.queryTaskIdByBusinessId(map);

		Map<String, Object> picLocationXY = this.queryCoordingByTask(taskId);
		Integer i = Integer.parseInt(picLocationXY.get("y").toString()) + 60;
		// 根据页面调整
		picLocationXY.put("y", i);

		ProcessDefinition pd = this.queryProcessDefinitionById(task.getProcessDefinitionId());
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
		ProcessDefinition pd = this.queryProcessDefinitionByDeploymentId(deploymentId);
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
		String taskId = this.queryTaskIdByBusinessId(params);
		String id = "";
		if (StringUtils.isNullOrBlank(taskId)) {
			AssetActivitiBus activitibus = assetActivitiBusService.queryAssetActivitiBus(params.getBusType());
			if (activitibus == null) {
				// proresult.setMessage(ActivitiException.BUSNAMENOTFOUND.getName());
				// proresult.setCode(ActivitiException.BUSNAMENOTFOUND.getCode());
				// return proresult;
				return list;
			}

			String businessKey = activitibus.getDeploymentKey() + "." + params.getBusinessId();
			List<HistoricTaskInstance> hisl = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).list();
			if (hisl == null) {
				logger.debug(ActivitiException.HISTORYNOTFOUND.getName());
				return list;
			}
			if (!(hisl.size() > 0)) {
				logger.debug(ActivitiException.HISTORYNOTFOUND.getName());
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
			logger.debug(ActivitiException.VERSIONUPDATE.getName());
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
		return list;
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
	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByDeploymentId(ActivitiParams params) throws DocumentException {
		List<ActivitiTaskMoveComboboxShow> list = new ArrayList<ActivitiTaskMoveComboboxShow>();
		ProcessDefinition pd = null;
		// 查询出当前的任务，不在跳转列表内
		// Task nowUserTask =
		// taskService.createTaskQuery().taskId(taskId).singleResult();
		pd = this.queryProcessDefinitionByDeploymentId(params.getDeploymentId());
		if (pd == null) {
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
			// id = buniness_key.split("\\.")[1];
			//便于前端授权查看，显示名称为，name+id
			show.setUserTaskName(name);
			list.add(show);
			// }
		}
		return list;
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
	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByDeploymentIdShowNameAndId(ActivitiParams params) throws DocumentException {
		List<ActivitiTaskMoveComboboxShow> list = new ArrayList<ActivitiTaskMoveComboboxShow>();
		ProcessDefinition pd = null;
		// 查询出当前的任务，不在跳转列表内
		// Task nowUserTask =
		// taskService.createTaskQuery().taskId(taskId).singleResult();
		pd = this.queryProcessDefinitionByDeploymentId(params.getDeploymentId());
		if (pd == null) {
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
			// id = buniness_key.split("\\.")[1];
			//便于前端授权查看，显示名称为，name+id
			show.setUserTaskName(name+"("+id1+")");
			list.add(show);
			// }
		}
		return list;
	}

	/** 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象 */
	public ProcessDefinition queryProcessDefinitionById(String id) {
		// 查询流程定义的对象，使用key模糊查询，防止版本更新造成查询不到流程定义实体
		String key = id.split("\\:")[0];
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKeyLike(key).latestVersion().singleResult();
		return pd;

	}

	/** 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象 */
	public ProcessDefinition queryProcessDefinitionByDeploymentId(String deploymentId) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
		return pd;
	}

	public List<ProcessDefinition> queryProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
		return list;
	}

	/**
	 * 使用部署对象ID和资源图片名称，获取图片的输入流 或者部署名称
	 * */
	public InputStream queryResourceInputStream(ActivitiParams params) {
		return repositoryService.getResourceAsStream(params.getDeploymentId(), params.getResourceName());
	}

	/**
	 * 添加授权信息 授权信息，包括授权组或者授权人员
	 * 
	 * @throws Exception
	 * */
	public void setPermission(Map<String, Object> map) throws Exception {

		/*
		 * 获取流程key，写死算了，固定从为业务实体的simpleName，从controller赋值
		 */
		String bpmnIskey = (String) map.get("deploymentKey");
		// 需要授权的节点名称taskDefinitionKey
		String userTaskId = (String) map.get("userTaskId");

		String[] authorizeGroupId = (String[]) map.get("authorizeGroupId");

		String[] authorizeUser = (String[]) map.get("authorizeUser");

		// 需要处理数据
		// 从前端获取授权组
		String[] candidateGroupIds = new String[] { "1" };
		// String[] candidateGroupIds = new String[] { authorizeGroupId };
		// 从前端获取授权人员
		// String[] candidateUserIds = new String[] { };
		String[] candidateUserIds = authorizeUser;

		String assignee = "";
		// assignee = authorizeUser;

		// 有问题。key要改
		// bpmnIskey = form.getClass().getSimpleName();

		String processDefId = repositoryService.createProcessDefinitionQuery().processDefinitionKey(bpmnIskey).singleResult().getId();
		// 最新版本
		String processDefId1 = repositoryService.createProcessDefinitionQuery().processDefinitionKey(bpmnIskey).latestVersion().singleResult().getId();

		// System.out.println(repositoryService.createProcessDefinitionQuery().processDefinitionKey("LeaveBill").latestVersion().singleResult().getId());
		// System.out.println(repositoryService.createProcessDefinitionQuery().processDefinitionKey("LeaveBill").singleResult().getId());

		activityPermissionManager.save(processDefId1, userTaskId, assignee, candidateGroupIds, candidateUserIds);
		// activitypermissionmanager.save(processDefId1, taskDefinitionKey,
		// null, new String[] { "1" }, new String[] { "关羽", "赵云" });
	}

	/**
	 * 添加授权信息 授权信息，包括授权组或者授权人员
	 * 
	 * @throws Exception
	 * */
	public void taskPermission(Map<String, Object> map) throws BusinessException {

		String deploymentId = MapUtilsExt.getString(map, "deplyomentId");
		String userTaskId = MapUtilsExt.getString(map, "userTaskId");
		String authorizeGroupId = MapUtilsExt.getString(map, "authorizeGroupIds");
		String authorizeUser = MapUtilsExt.getString(map, "authorizeUsers");
		String assignee = MapUtilsExt.getString(map, "assignee");
		if(StringUtils.isNullOrBlank(authorizeUser)){
			authorizeUser = "";
		}
		if(StringUtils.isNullOrBlank(assignee)){	
			assignee = "";
		}
			
		
		assignee = StringUtilsExt.convertNullToString(assignee);

		ProcessDefinition pdef1 = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();

		ProcessDefinition pdef = repositoryService.createProcessDefinitionQuery().latestVersion().processDefinitionKeyLike(pdef1.getKey()).singleResult();
		// ProcessDefinition pdef =
		// repositoryService.createProcessDefinitionQuery().latestVersion().deploymentId(deploymentId).singleResult();

		String pdefId = pdef.getId();

		String[] authorizeGroupIds = authorizeGroupId.split("\\,");
		String[] authorizeUsers = authorizeUser.split("\\,");

//		OwfActivityPermission owfap = new OwfActivityPermission();
//		owfap.setActivityKey(userTaskId);
//		owfap.setAssignedUser(authorizeUser);
//		owfap.setAssigneeName(assignee);
//		owfap.setGrantedGroups(authorizeGroupId);
//		owfap.setGrantedUsers(authorizeUser);
//		owfap.set
		
		List<OwfActivityPermission> list = owfActivityPermissionService.queryOwfActivityPermissionByProcessDefId(pdefId, userTaskId);
		if (list != null && list.size() > 0) {
			throw new BusinessException(ActivitiException.ALREADYPERMISSIONERROR.getCode(), ActivitiException.ALREADYPERMISSIONERROR.getName());
		}
		
		try {
			activityPermissionManager.save(pdefId, userTaskId, assignee, authorizeGroupIds, authorizeUsers);
		} catch (Exception e) {
			throw new BusinessException(ActivitiException.PERMISSIONERROR.getCode(), ActivitiException.PERMISSIONERROR.getName());
		}
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

	//判断流程是否在运行存在
	public boolean _isExistProcess(String deploymentKey) {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey(deploymentKey).active().list();
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	// 判断流程是否正在运行
	public boolean _processIsRunning(String deploymentKey) {
		long count = 0;
		count = runtimeService.createExecutionQuery().processDefinitionKey(deploymentKey).count();
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 文件流方式 添加部署流程，强制上传
	 * 
	 */
	public void saveNewDeploye(InputStream file, String filename) {
//		try {
//			Document document = XmlUtilsExt.createDocument(file);
//			Element root = document.getRootElement();
			// 获取xml的节点
//			Element process = root.element("process");
			//获取名字
//			String bpmnName = process.attributeValue("name");
			// 直接规定filename就是业务类型，然后查看是否有流程在跑，如果有，就不让上传
			ZipInputStream zipInputStream = new ZipInputStream(file);
			String deploymentId = repositoryService.createDeployment().name(filename).addZipInputStream(zipInputStream).deploy().getId();
			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
			String processDefId = pd.getId();
			String nowKeyLike = processDefId.split("\\:")[0];
			owfActivityPermissionService.updateVersion(processDefId, nowKeyLike);
			// repositoryService.createDeployment().name(filename).addZipInputStream(zipInputStream).deploy();
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 文件流方式 添加部署流程，判断是否有流程在运行后在进行上传
	 * 
	 */
	public ProcessResult saveNewDeployeCheck(InputStream file, String filename) {
		ProcessResult result = new ProcessResult();
		Document document = XmlUtilsExt.createDocument(file);
		Element root = document.getRootElement();
		// 获取xml的节点
		Element process = root.element("process");
		// 获取名字
		String bpmnName = process.attributeValue("name");
		// 获取key
		String deploymentKey = process.attributeValue("id");
		// 直接规定filename就是业务类型，然后查看是否有流程在跑，如果有，就不让上传
		if (_processIsRunning(deploymentKey)) {
			result.setCode(ActivitiException.DEPLOYROCESSFAIL.getCode());
			result.setMessage(ActivitiException.DEPLOYROCESSFAIL.getName());
			return result;
		} else {
			ZipInputStream zipInputStream = new ZipInputStream(file);
			repositoryService.createDeployment().name(bpmnName).addZipInputStream(zipInputStream).deploy();
			result.setMessage("部署成功！" + "流程名称为：" + bpmnName);
			return result;
		}
	}

	/**
	 * 使用部署对象ID 删除流程定义
	 * 
	 * isForceDelete 强制删除，不管当前是否有流程任务是否未结束。 不是强制删除的话，如果有未结束流程，进行删除，会报错
	 * 
	 * */
	public void deleteProcessDefinitionByDeploymentId(String deploymentId, boolean isForceDelete) {

		// 取出最新版本的相关信息
		Deployment latestDeployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
		// 获取到他的名字
		String name = latestDeployment.getName().split("\\.")[0];

		ProcessDefinition p = repositoryService.createProcessDefinitionQuery().deploymentId(latestDeployment.getId()).active().singleResult();
		if (p == null) {
			repositoryService.deleteDeployment(deploymentId, isForceDelete);
		} else {
			String key = repositoryService.createProcessDefinitionQuery().deploymentId(latestDeployment.getId()).active().singleResult().getKey();
			String pedfId = p.getId();
			String likeId = pedfId.split("\\:")[0];
			owfActivityPermissionService.deletePermissionWhenDeleteProcess(likeId);
			// 名字一样的全部取出
			// 删除全部类似
			List<Deployment> activitiList = repositoryService.createDeploymentQuery().processDefinitionKeyLike(key).list();
			// List<Deployment> activitiList =
			// repositoryService.createDeploymentQuery().deploymentNameLike(name).list();
			if (activitiList.size() > 0) {
				for (int i = 0, len = activitiList.size(); i < len; i++) {
					repositoryService.deleteDeployment(activitiList.get(i).getId(), isForceDelete);
				}
			}
		}
	}

	public Map<String, Object> deleteProcessInstacne(ActivitiParams params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String taskId = params.getTaskId();
		// String taskId = MapUtilsExt.getString(map, "taskId");
		if (StringUtils.isNullOrBlank(taskId)) {
			taskId = this.queryTaskIdByBusinessId(params);
		}
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String pid = task.getProcessInstanceId();

		runtimeService.deleteProcessInstance(pid, "手动删除任务!");
		result.put("message", "删除成功！");

		return result;
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
		// String time = "201612071605";
		// Date d = DateUtilsExt.parseDate(time);
		// JobExecutorContext j = new JobExecutorContext();
		repositoryService.suspendProcessDefinitionByKey(processDefinitionKey);
		result.put("message", "挂起流程");

		return result;
	}

	public Map<String, Object> activateProcess(String processDefinitionKey) {
		Map<String, Object> result = new HashMap<String, Object>();
		repositoryService.activateProcessDefinitionByKey(processDefinitionKey);

		result.put("message", "挂起流程");

		return result;

	}

	public void submitTask3(Map<String, Object> map) {
		String taskId = (String) map.get("taskId");
		Integer handle = (Integer) map.get("handle");

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		// userId
		String taa = (String) runtimeService.getVariable(task.getExecutionId(), "userId");
		String test = (String) runtimeService.getVariable(task.getExecutionId(), "test");
		// TbusLeaveApplication t= (TbusLeaveApplication)
		// runtimeService.getVariable(task.getExecutionId(), "t");
		Map<String, Object> mapt = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "mapt");
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		Map<String, Object> variables = new HashMap<String, Object>();
		// variables.put("handle", "1.1");
		variables.put("handle", handle);

		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 获取当前活动的id,taskdefKey
		// String localStep = pi.getActivityId();
		// 3：使用任务ID，完成当前人的个人任务，同时流程变量
		taskService.complete(taskId, variables);

		pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

		System.out.println("submitTask3临时测试");
	}

	public List<ApiUser> getUserByGroupIds(String groupIds, Page<ApiUser> page) {
		String[] ids = groupIds.split("\\,");
//		List<String> a = new ArrayList<String>();
//		a.add("SJ_CZ");
//		a.add("LU");
//		a.add("ZB");
//		a.add("normal");
//		a.add("ADMIN");
//		a.add("SUPER_ADMIN");
//		List<ApiUser> users = DrcHepler.getInstance().findUserByGroups(a);
		List<ApiUser> users = DrcHepler.getInstance().findUserByGroups(Arrays.asList(ids));
//		List<ApiUser> allUsers = DrcHepler.getInstance().findAllUser();
		return users;

	}

	public List<ApiUser> getAllUser(Page<ApiUser> page) {
		List<ApiUser> allUsers = DrcHepler.getInstance().findAllUser();
		return allUsers;
	}

	public void saveDelegation(String delegated, String delegate) {
		sqlDelegationManager.saveDelegation(delegated, delegate);
	}
	
	@SuppressWarnings("unused")
	public void startProcess(String key){
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", "1");
		variables.put("applyuser", "4");
		variables.put("map", map);
//		variables.put("condition1", 1);
//		variables.put("applyuser", userCode);
//		variables.put("handle", HandleType.PASS.getHandle().toString());
		// 格式：LeaveBill.id的形式（使用流程变量）
//		String businessKey = activitibus.getDeploymentKey() + "." + businessId;
//		variables.put("businessKey", businessKey);
//		variables.put("startKeyMap", params.getStartKeyMap());
		// 6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, GUID.newGUID(), variables);
		Task nowTask = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).singleResult();
		
//		taskService.addCandidateGroup(nowTask.getId(), "XTGLY");
//		taskService.addCandidateGroup(nowTask.getId(), "SUPER_ADMIN");
		
//		taskService.setAssignee(nowTask.getId(), "1");
//		taskService.setAssignee(nowTask.getId(), "2");   //同时授权的话，只会赋值最后一次
		
		List<Task> list = taskService.createTaskQuery().taskAssignee("350204198307133055").orderByTaskCreateTime().asc().list();
		// 如果指定任务没有，就通过授权者查找任务
		List<Task> listPermission = taskService.createTaskQuery().taskCandidateUser("350204198307133055").list();
		List<Task> listPermission1 = taskService.createTaskQuery().taskCandidateUser("system").list();
		

		System.out.println(111);
		System.out.println(111);
		System.out.println(111);
		
		//指定又提交者来做这件事情
//		taskService.setAssignee(nowTask.getId(), "jjjjj");
	}
	
	public void executeTask(String taskId, String i) {
		Map<String, Object> variables = new HashMap<String, Object>();
//		ActivitiBranchService afc = new ActivitiBranchService();
//		variables.put("afc", afc);
		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("bill_type", "tdfw");
//		map.put("bill_system", "in1");
		List<Long> list = new ArrayList<Long>();
		list.add(1l);
		list.add(4l);
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<String> list3 = new ArrayList<String>();
		List<String> list4 = new ArrayList<String>();
		list4.add("100");
		list3.add("yes");
//		list2.add("in1");
		list2.add("1");
		list1.add("TDFW");
		list1.add("FDSA");
//		list1.add("101010201AAA");
		list1.add("101010201aa");
		variables.put("handle", i.toString());
//		variables.put("list", list);
//		variables.put("list1", list1);
		
		//取出流程分支控制中的条件
		ActivitiBranchControl activitiBranchControl = new ActivitiBranchControl();
		activitiBranchControl.setProcessKey("testBranch");
		activitiBranchControl.setUserTaskDefkey("usertask1");
		List<ActivitiBranchControl> branchList =  activitiBranchControlService.getActivitiBranchControlList(activitiBranchControl);
		if(branchList != null && branchList.size() > 0){
			map.put(ActivitiCondition.BRANCH_CONTROL.getCode(), branchList);
		}
		
		
		map.put(ActivitiCondition.BILL_TYPE.getCode(), list1);
		map.put(ActivitiCondition.BILL_SYSTEM.getCode(), list2);
		map.put(ActivitiCondition.YES_OR_NOT.getCode(), list3);
		map.put(ActivitiCondition.ORG_CODE.getCode(), "SJ401009001a");
//		map.put(ActivitiCondition.ORG_CODE.getCode(), "SJ402001");
		map.put(ActivitiCondition.REASON_CONDITION.getCode(), "分aaa立");
		map.put("bizType", list4);
//		map.put(ActivitiCondition.ORG_CODE.getCode(), "SJ401009002");
		map.put("start", "修改过了啊啊啊啊啊啊啊啊啊啊啊啊");
		variables.put("map", map);
		variables.put("i", 0);
		variables.put("j", 5);
		variables.put("k", 0);
		variables.put("applyuser", "4");
		taskService.complete(taskId, variables);
		
	}
	
	public void syso(){
		System.out.printf("分支函数测试注入");
	}

	
	
	@SuppressWarnings("unused")
	public void test(String id,String handle) {
		alarmToolsService.createAlarmRecordInner1();
//		assetProcessRemarkService.updateBusTypeByBusIdAndPid("2175597150209025", "1", "1");
//		assetBusHistoryService.updateBusTypeByBusIdAndPid("2175597150209025", "1", "1");
		

		//5285106
		
		
//		HistoricVariableInstance hisVariable = historyService.createHistoricVariableInstanceQuery().processInstanceId("5285106").variableName("map").singleResult();
//		if (hisVariable != null) {
//			Map<String,Object> value = (Map<String,Object>) hisVariable.getValue();
//			System.out.println(111);
//		}
//		
//		
//		ActivitiParams params = new ActivitiParams();
//		
//		params.setBusinessId(123);
//		params.setUserCode("system");
//		params.setDeploymentKey("CARD");
//		params.setBusType("CARD");
//
//		Task task = taskService.createTaskQuery().taskId(id).singleResult();
//		
//		Map<String,Object> startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "map");
		
//		activitiToolsService.hasRejectEndOption(params);
		
//		activitiToolsService.deleteTempData(params);
		
//		params.setDeploymentKey("reject");
//		params.setUserCode("system");
//		params.setBusType("");
		
//			luceneToolsService.searchIndex();
//		luceneInputHelpService.initCreateIndex();
//		List<AssetInputHelp> listaaaa  = luceneInputHelpService.search("厦门", "", 1, 4,false);
//		List<AssetInputHelp> listaaa  = luceneInputHelpService.search("厦门ccc", "", 1, 4,false);
//		List<AssetInputHelp> listaa  = luceneInputHelpService.search("厦门", "", 1, 4,false);
//		List<AssetInputHelp> lista  = luceneInputHelpService.search("zic", "", 1, 4,false);
//		
		
//		boolean b = activitiToolsService.hasRejectEndOption(params);
		
		System.out.println(1);
//		activitiToolsService.hasTempDataRemark("123");
//		String busType = activitiToolsService.queryBusinessTypeByBusinessId("123");
//		activitiToolsService.tempSave(params);
		
//		AssetInputHelp assetInputHelp = new AssetInputHelp();
//		List<AssetInputHelp> list = assetInputHelpService.getAssetInputHelpList(assetInputHelp);
//		System.out.println(111);
//		AssetInputHelp assetInputHelp = new AssetInputHelp();
//		List<AssetInputHelp> list = assetInputHelpService.getAssetInputHelpList(assetInputHelp);
//	
//		taskService.setAssignee(nowTask.getId(), "1");
//		taskService.setAssignee(nowTask.getId(), "2");   //同时授权的话，只会赋值最后一次
		
		// 如果指定任务没有，就通过授权者查找任务
//		List<Task> listPermission = taskService.createTaskQuery().taskCandidateUser("350500196502257012").list();
//		List<Task> listPermission1 = taskService.createTaskQuery().taskCandidateUser("system").list();
//		taskService.addCandidateGroup(id, "DW_SH");
//		taskService.addCandidateGroup(id, "SUPER_ADMIN");
//		String key = "USE_APPLY.8796523765694471";
//		List<HistoricProcessInstance> hisPro = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(key).orderByProcessInstanceStartTime().asc().list();
//		HistoricProcessInstance hiiii = hisPro.get(hisPro.size()-1);
		
//		this.executeTask(id,handle);
//		this.startProcess(id);
//		String assignLike = "test,test2,tets3";
//		taskService.setAssignee(id, "test");
//		taskService.setAssignee(id, "test1");
//		taskService.setAssignee(id, "test2");
//		taskService.setAssignee(id, assignLike);
//		List<Task> list = taskService.createTaskQuery().taskAssigneeLike("%"+"test"+"%").orderByTaskCreateTime().asc().list();
//		List<Object> listALL = activitiProcessService.queryTaskByUserCode(params);
		//		taskService.add
		
//		taskService.addCandidateGroup("2425140", "XTGLY");
//		List<Task> list = taskService.createTaskQuery().taskAssignee("350204198307133055").orderByTaskCreateTime().asc().list();
		// 如果指定任务没有，就通过授权者查找任务
//		List<Task> listPermission2 = taskService.createTaskQuery().taskCandidateUser("350500196502257012").list();
//		List<Task> listPermission13 = taskService.createTaskQuery().taskCandidateUser("system").list();
////		taskService.addCandidateGroup(taskId, groupId);
		
	}
	
	public Map<String,Object> getMap(String id){
		String[] ids = id.split("\\@");
		Map<String,Object> startKeyMap = new HashMap<String, Object>();
		if(ids.length == 1){
			Task task = taskService.createTaskQuery().taskId(ids[0]).singleResult();
			startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "startKeyMap");
		}else if(ids.length == 2){
			Task task = taskService.createTaskQuery().taskId(ids[0]).singleResult();
			startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "map");
		}
//		Map<String,Object> startKeyMap = (Map<String, Object>) runtimeService.getVariable(task.getExecutionId(), "map");//map为流程分支控制的map
		return startKeyMap;
	}
	
	@SuppressWarnings("unused")
	public void start(String id,String handle) {

//	
//		taskService.setAssignee(nowTask.getId(), "1");
//		taskService.setAssignee(nowTask.getId(), "2");   //同时授权的话，只会赋值最后一次
		
		// 如果指定任务没有，就通过授权者查找任务
//		List<Task> listPermission = taskService.createTaskQuery().taskCandidateUser("350500196502257012").list();
//		List<Task> listPermission1 = taskService.createTaskQuery().taskCandidateUser("system").list();
//		taskService.addCandidateGroup(id, "DW_SH");
//		taskService.addCandidateGroup(id, "SUPER_ADMIN");
//		String key = "USE_APPLY.8796523765694471";
//		List<HistoricProcessInstance> hisPro = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(key).orderByProcessInstanceStartTime().asc().list();
//		HistoricProcessInstance hiiii = hisPro.get(hisPro.size()-1);
		
//		this.executeTask(id,handle);
		this.startProcess(id);
//		String assignLike = "test,test2,tets3";
//		taskService.setAssignee(id, "test");
//		taskService.setAssignee(id, "test1");
//		taskService.setAssignee(id, "test2");
//		taskService.setAssignee(id, assignLike);
//		List<Task> list = taskService.createTaskQuery().taskAssigneeLike("%"+"test"+"%").orderByTaskCreateTime().asc().list();
//		List<Object> listALL = activitiProcessService.queryTaskByUserCode(params);
		//		taskService.add
		
//		taskService.addCandidateGroup("2425140", "XTGLY");
//		List<Task> list = taskService.createTaskQuery().taskAssignee("350204198307133055").orderByTaskCreateTime().asc().list();
		// 如果指定任务没有，就通过授权者查找任务
//		List<Task> listPermission2 = taskService.createTaskQuery().taskCandidateUser("350500196502257012").list();
//		List<Task> listPermission13 = taskService.createTaskQuery().taskCandidateUser("system").list();
////		taskService.addCandidateGroup(taskId, groupId);
		
	}
	
	@SuppressWarnings("unused")
	public void execute(String id, String handle) {
		this.executeTask(id, handle);
	}
	
	@SuppressWarnings("unused")
	public void recover(String busId, String deleteTaskId,String userTaskId, String creatorCode, String orgCode, String orgId,String busType, String key) {
		
		Map<String, Object> startKeyMap = new HashMap<String, Object>();
		startKeyMap.put("creator", creatorCode);
		startKeyMap.put("orgCode", orgCode);
		startKeyMap.put("orgId", orgId);
		startKeyMap.put("bizId", busId);
		
		String businessKey = key + "." + busId;
		
		//删除原有的流程记录
		ActivitiParams deleteParams = new ActivitiParams();
		deleteParams.setTaskId(deleteTaskId);
		activitiToolsService.deleteProcessInstacne(deleteParams);

		//开启新的流程
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("businessKey", businessKey);
		variables.put("startKeyMap", startKeyMap);
		variables.put("applyuser", creatorCode);
		ProcessInstance pi = runtimeService.startProcessInstanceByKey(key, businessKey, variables);
		//创建转移工厂，跳转任务
		String processInstanceId = pi.getId();
		TaskFlowControlService tfcs = taskFlowControlServiceFactory.create(processInstanceId);
		//跳转至相应的节点任务
		try {
			tfcs.moveTo(userTaskId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//修复历史相关数据
		//1.修复history表
		assetBusHistoryService.updatePIdByBusIdAndBusType(busId, processInstanceId, busType);
//		assetBusHistoryService.updatePIdByBusId(busId, processInstanceId, busType);
		//2.修复remark表
		assetProcessRemarkService.updatePIdByBusIdAndBusType(busId, processInstanceId, key);
//		assetProcessRemarkService.updatePIdByBusId(busId, processInstanceId, key);
		
	}

	public void forceDelete(String id) {
		ActivitiParams params = new ActivitiParams();
		String[] ids = id.split("\\,");
		for (String string : ids) {
			params.setTaskId(string);
			activitiToolsService.deleteProcessInstacne(params );
		}
		
	}
	
	public void deleteDeploymentId(String deploymentId, String force) {
		force = StringUtilsExt.convertNullToString(force);
		if ("1".equals(force)) {
			String[] deploymentIds = deploymentId.split("\\,");
			for (String string : deploymentIds) {
				repositoryService.deleteDeployment(string, true);
			}
		} else {
			String[] deploymentIds = deploymentId.split("\\,");
			for (String string : deploymentIds) {
				repositoryService.deleteDeployment(string);
			}
		}
	}
}
