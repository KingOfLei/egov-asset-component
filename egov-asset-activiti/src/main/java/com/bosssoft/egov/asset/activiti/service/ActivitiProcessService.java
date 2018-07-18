package com.bosssoft.egov.asset.activiti.service;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ActivitiShowPic;
import com.bosssoft.egov.asset.activiti.entity.AssetProcessRemark;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.activiti.entity.ShowUndoTask;
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
public interface ActivitiProcessService {

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

	/**
	 * 使用当前用户名查询与之相关的任务表 获取当前任务的集合List<Task> 查找个人任务
	 * 
	 * @return List,里面放着状态码
	 * @param 1.登陆者信息（必带）
	 * @param 2.busType （必带）
	 * @param 3.相关的登陆者信息 （必带）
	 */
	public List<Object> queryTaskByUserCode(ActivitiParams params);

	/**
	 * 获取当前用户所有业务的条目总数，名称数目
	 */
	public List<ShowUndoTask> getToDoList(ActivitiParams params);

	/**
	 * 
	 * <p>
	 * 函数名称： 审核记录
	 * 
	 * @param page
	 * @param login
	 *            登陆者信息
	 * @param busType
	 *            业务类型
	 * @return
	 *
	 * @date 创建时间：2016年12月2日
	 * @author 作者：jinbiao
	 */
	public Page<AssetProcessRemark> queryProcessRemarkHistoryPage(Page<AssetProcessRemark> page, String userCode, String busType);

	
	/**
	 * 
	 * <p>函数名称：     getHistoryListPic   </p>
	 * <p>功能说明：	拼凑审核记录流程图
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 * @throws DocumentException
	 *
	 * @date   创建时间：2016年12月21日
	 * @author 作者：jinbiao
	 */
	public ActivitiShowPic getHistoryListPic(ActivitiParams params) throws DocumentException;
	/**
	 * 
	 * <p>函数名称：    获取某个业务的所有操作记录，分页    </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param page
	 * @param businessId
	 * @return
	 *
	 * @date   创建时间：2016年12月18日
	 * @author 作者：jinbiao
	 */
	public Page<AssetProcessRemark> queryProcessRemarkHistoryByBusinessIdPage(Page<AssetProcessRemark> page, String businessId);
	/**
	 * 
	 * <p>
	 * 函数名称： 通过审核和退回 后面再完善，现在还没分清楚，已审核已经退回，有重叠，后面再细分。现在只查询 1 退回，9通过。
	 * 
	 * @param checkresult 审核操作：HandleType 枚举类型  RETURN已退回，PASS已审核
	 * @param 登陆者信息
	 * @param busType  业务类型
	 * @return List 其中有业务id
	 * @date 创建时间：2016年12月2日
	 * @author 作者：jinbiao
	 */
	public List<Object> queryProcessRemarkHistory(String userCode, String busType, Integer checkresult);

	/**
	 * 
	 * <p>函数名称：     流程处理   </p>
	 *<p>参数说明：</p>
	 * @param map
	 * @return
	 * @throws Exception
	 * @return List<ProcessResult> 包括相应的业务id 状态 日志
	 * @param 1.businessId（必带）
	 * @param 2.busType （必带）
	 * @param 3.相关的登陆者信息 （必带） userName,userCode
	 * @param 4.remark 批注信息
	 * @param 5.handle HandleType 枚举类型（必带）
	 * @date   创建时间：2016年12月13日
	 * @author 作者：jinbiao
	 */
	//操作审核处理结果(0:驳回，1退回，2跳转，3取消审核，4取消退回，5提交任务，6取消提交，9通过审核,10批量驳回,11批量退回，12批量通过,13批量取消审核，14批量取消退回，批量取消提交15）
	public ProcessResult handleTask(ActivitiParams params) throws Exception;
	
//	public void cancelReturnToEnd(ActivitiParams params) throws Exception;
	
	public boolean isFirstSubmit(ActivitiParams params) throws BusinessException;
	
	/**
	 * 
	 * <p>函数名称：     判断按钮 </p>
	 * <p>功能说明：判断某个当前的岗位是否有驳回和退回操作  
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params   businessId，busType
	 * @return
	 * @throws DocumentException
	 *
	 * @date   创建时间：2016年12月19日
	 * @author 作者：jinbiao
	 */
//	public ActivitiButtonShow ActivitiButtonShowOrDisable(ActivitiParams params) throws DocumentException;

	/**
	 * 
	 * <p>函数名称：   existInActiviti     </p>
	 * <p>功能说明： 判断是否在工作流中运行
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params   
	 * @return
	 *
	 * @date   创建时间：2016年12月19日
	 * @author 作者：jinbiao
	 */
	public boolean existInActiviti(ActivitiParams params);

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
//	public Map<String, Object> queryProgressPic(String deploymentId);
	
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
//	public InputStream queryResourceInputStream(ActivitiParams params);

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
//	public List<ActivitiTaskMoveComboboxShow> queryUserTaskIdListByBusinessId(ActivitiParams params) throws DocumentException;

	/**
	 * 功能说明：通过业务id获取可以后退的列表节点选择，从而进行调整，退回列表，退回 参数说明：退后 （现在只退回一步）
	 * 
	 * @param taskId
	 * @return
	 * @date 创建时间：2016年11月14日
	 * @author 作者：jinbiao
	 */
//	public List<ActivitiTaskMoveComboboxShow> queryBackUserTaskListByBusinessId(ActivitiParams params) throws DocumentException;

	/**
	 * 
	 * <p>
	 * 功能说明：设置节点跳转
	 * 
	 * @param map
	 *            businessId，业务id，busType业务类型，跳转节点id
	 * @throws Exception
	 *
	 * @date 创建时间：2016年12月6日
	 * @author 作者：jinbiao
	 * @return
	 */
	public ProcessResult taskMove(ActivitiParams params) throws BusinessException;

	public String queryOutBusType(Object busId);
	/**
	 * 
	 * <p>函数名称：获取状态  </p>
	 * <p>功能说明：在当前任务未完成的情况下获取下下一节点的状态
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年2月25日
	 * @author 作者：jinbiao
	 */
	public ProcessResult getNext2TaskStatus(ActivitiParams params);
	
	/**
	 * 指派或者转发任务，临时一次,逗号分割,放入forwardOrDesignUser
	 */
	public ProcessResult forwardOrDesignTaskUsers(ActivitiParams params);
	
	/**
	 * 指派或者转发任务，临时一次,指定角色，逗号分割，可以是多个组，放入forwardOrDesignGroups
	 */
	public ProcessResult forwardOrDesignTaskGroups(ActivitiParams params);
	/**
	 * 
	 * <p>函数名称：      强制删除一个流程，根据业务id  </p>
	 * <p>功能说明：	如果存在流程，则删除，否则不影响，只删除工作流产生的数据，不删除业务系统产生的相关日志
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param pa
	 * @return
	 *
	 * @date   创建时间：2017年3月4日
	 * @author 作者：jinbiao
	 */
	public ProcessResult forceDeleteProcessInstance(ActivitiParams pa);
	/**
	 * 
	 * <p>函数名称：获取业务表单当前节点当前权限</p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：业务id</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年3月5日
	 * @author 作者：jinbiao
	 */
	public ProcessResult getBuinessFormAction(ActivitiParams params);
	
	/**
	 * 
	 * <p>函数名称： 获取当前业务单据的节点的授权用户code</p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param businessId
	 * @return
	 *
	 * @date   创建时间：2017年3月11日
	 * @author 作者：jinbiao
	 */
	public ProcessResult getCurrentRoleCode(Object businessId);
	
	public boolean hasHistoryLife(Object businessId);
	
	public boolean hasRejectOption(ActivitiParams params);
	
	public ProcessResult deleteProcessAndRecord(ActivitiParams params);
	
	/**
	 * 
	 * <p>函数名称： 查看日志插入</p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param  userName   
	 * 			usercode
	 * 			businessId		
	 *
	 * @date   创建时间：2017年4月27日
	 * @author 作者：jinbiao
	 */
	public void viewRecrod(ActivitiParams params);
	
	/**
	 * 
	 * <p>函数名称：  暂存数据</p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param businessId
	 * 			userCode
	 * 				userName
	 *
	 * @date   创建时间：2017年4月27日
	 * @author 作者：jinbiao
	 */
	public void tempSave(ActivitiParams params);
	
	/**
	 * 
	 * <p>函数名称：  删除数据</p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param businessId
	 * 			userCode
	 * 				userName
	 *
	 * @date   创建时间：2017年4月27日
	 * @author 作者：jinbiao
	 */
	public void deleteRecord(ActivitiParams params);
	
	public boolean canReject(ActivitiParams params);
	
}
