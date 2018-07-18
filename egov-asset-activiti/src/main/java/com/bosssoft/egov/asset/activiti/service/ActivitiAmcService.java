package com.bosssoft.egov.asset.activiti.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiTaskMoveComboboxShow;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.runtime.exception.BusinessException;

/**
 * Service类接口 .
 * 
 * <pre>
 * 修改记录：
 * 修改日期　　　修改人　　　修改原因
 * 2016-10-26   jinbiao　　　新建
 * </pre>
 */
public interface ActivitiAmcService {

	/**
	 * 功能说明：上传部署文件
	 * 
	 * @param InputStream
	 *            file
	 * @param filename
	 *
	 * @date 创建时间：2016年12月3日
	 * @author 作者：jinbiao
	 */
	public void saveNewDeploye(InputStream file, String filename);
	
	public ProcessResult saveNewDeployeCheck(InputStream file, String filename);

	public void deleteProcessDefinitionByDeploymentId(String deploymentId, boolean isForceDelete);


	/**
	 * 
	 * <p>
	 * 函数名称： 通过业务类型和业务id，强制删除某流程
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param map
	 *            businessId，busType
	 * @return
	 *
	 * @date 创建时间：2016年12月7日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> deleteProcessInstacne(ActivitiParams params);

	/**
	 * 
	 * <p>函数名称：     根据id删除历史任务   </p>
	 * <p>功能说明：
	 *<p>参数说明：</p>
	 * @param taskIds
	 * @return
	 *
	 * @date   创建时间：2016年12月13日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> deleteHistoryTaskByTask(List<String>ids) ;
	/**
	 * 
	 * <p>
	 * 函数名称：删除指定时间且已完成的实例相关，包括其中产生的variables变量，task任务
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param date
	 * @return
	 *
	 * @date 创建时间：2016年12月7日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> deleteHistoryTaskByDate(Date date);

	/**
	 * 
	 * <p>
	 * 函数名称：激活已停止的部署
	 * </p>
	 * <p>
	 * 功能说明：
	 * 
	 * @param processDefinitionKey
	 *
	 * @date 创建时间：2016年12月7日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> activateProcess(String processDefinitionKey);

	/**
	 * 
	 * <p>
	 * 函数名称：停止指定的部署
	 * </p>
	 * <p>
	 * 功能说明：
	 * 
	 * @param processDefinitionKey
	 *
	 * @date 创建时间：2016年12月7日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> suspendProcess(String processDefinitionKey);

	// 仅供测试测试完成任务
	public void submitTask3(Map<String, Object> map);

	/**
	 * 
	 * <p>函数名称：     流程图查看步骤1   </p>
	 * <p>功能说明：流程图查看
	 * @param businessId
	 * @param busType
	 * @return	获取图片名称，以及流程现在所在的位置
	 * @date   创建时间：2016年12月15日
	 * @author 作者：jinbiao
	 */
	public Map<String, Object> queryProgressPicByBusinessId(ActivitiParams params);

	//后台管理流程图查看
	public Map<String, Object> queryProgressPic(String deploymentId);
	
	// 查看流程资源  步骤2
	/**
	 * 
	 * <p>函数名称：   流程图查看步骤2 </p>
	 * <p>功能说明：	流程图片流获取
	 *<p>参数说明：</p>  
	 * @param deploymentId
	 * @param resourceName
	 * @param ActivitiParams
	 * @return InputStream
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：jinbiao
	 */
	public InputStream queryResourceInputStream(ActivitiParams params);

	/**
	 * <p>函数名称： </p>
	 * <p>功能说明：获取节点列表
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 * @throws DocumentException
	 *
	 * @date   创建时间：2016年12月15日
	 * @author 作者：jinbiao
	 */
	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByBusinessId(ActivitiParams params) throws DocumentException;

	
	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByDeploymentId(ActivitiParams params) throws DocumentException; 

	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByDeploymentIdShowNameAndId(ActivitiParams params) throws DocumentException;
	
	public List<ApiUser> getUserByGroupIds(String groupIds,Page<ApiUser> page);

	public List<ApiUser> getAllUser(Page<ApiUser> page);
	
	public void saveDelegation(String delegated,String delegate);
	
	public void taskPermission(Map<String, Object> map) throws BusinessException;
	
	public void startProcess(String key);
	
	public void executeTask(String taskId, String i);
	
	public void test(String extParam,String handle);

	public void syso();
	
	public void recover(String busId, String deleteTaskId, String userTaskId, String creatorCode, String orgCode, String orgId, String busType, String key);
	
	public void start(String id,String handle);
	
	public void execute(String id,String handle);
	
	public void forceDelete(String id);
	
	public void deleteDeploymentId(String deploymentId, String force);
	
	public Map<String,Object> getMap(String id);
}
