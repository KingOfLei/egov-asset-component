package com.bosssoft.egov.asset.amc.user.service;

import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.amc.user.entity.AfaAuthParty;
import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRule;
import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRuleOrg;
import com.bosssoft.egov.asset.amc.user.model.AimsUserMain;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.appframe.api.entity.ApiUserOrgPos;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
 *
 * @ClassName   类名：AmcUserService 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2017年4月8日
 * @author      创建人：黄振雄
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2017年4月8日   黄振雄   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public interface AmcUserService {
	/**
	 * <p>函数名称：querySubPosList        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月22日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public List<AfaAuthParty> querySubPosList();
	
	/**
	 * <p>函数名称：querySubOrgByUserCode        </p>
	 * <p>功能说明：获取机构列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param searcher
	 * @param page
	 * @return
	 *
	 * @date   创建时间：2017年4月9日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Page<DrcAuthRuleOrg> querySubOrgByUserCode(Searcher searcher,Page<DrcAuthRuleOrg> page);
	
	/**
	 * <p>函数名称：insertSubOrg        </p>
	 * <p>功能说明：新增机构
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param drcAuthRuleOrg
	 * @param userId
	 * @param userCode
	 * @param userName
	 * @return
	 *
	 * @date   创建时间：2017年4月9日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> insertSubOrg(DrcAuthRuleOrg drcAuthRuleOrg,
			String userId, String userCode,String userName);
	
	/**
	 * <p>函数名称：delSubOrg        </p>
	 * <p>功能说明：删除一个机构
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCode
	 * @param orgId
	 * @param roleId
	 * @param roleCode
	 * @return
	 *
	 * @date   创建时间：2017年4月10日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> delSubOrg(String userCode, String orgId, String roleId, String roleCode);

	/**
	 * <p>函数名称：   delSubPos     </p>
	 * <p>功能说明：删除一个岗位信息（对DRC_AUTH_RULE表做更新操作）
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param drcAuthRule
	 * @return
	 *
	 * @date   创建时间：2017年4月10日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> delSubPos(DrcAuthRule drcAuthRule);
	
	public List<DrcAuthRule> getDrcAuthRules(String userCode);
	
	/**
	 * <p>函数名称：batchInsertSubOrg        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param drcAuthRuleOrgs
	 * @param userId
	 * @param userCode
	 * @param userName
	 * @return
	 *
	 * @date   创建时间：2017年4月11日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> batchInsertSubOrg(List<DrcAuthRuleOrg> drcAuthRuleOrgs,  
			String userId, String userCode,String userName);
	
	/**
	 * <p>函数名称： doInsert       </p>
	 * <p>功能说明：新增用户， 新增机构岗位信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param apiUser
	 * @param drcAuthRuleOrgs
	 * @return
	 *
	 * @date   创建时间：2017年4月13日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> doInsert(ApiUser apiUser, List<DrcAuthRuleOrg> drcAuthRuleOrgs);

	/**
	 * <p>函数名称：doUpdate        </p>
	 * <p>功能说明：修改用户，修改机构岗位信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param apiUser
	 * @param drcAuthRuleOrgs
	 * @return
	 *
	 * @date   创建时间：2017年4月14日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> doUpdate(ApiUser apiUser, List<DrcAuthRuleOrg> drcAuthRuleOrgs);
	
	/**
	 * <p>函数名称：doAuthorize        </p>
	 * <p>功能说明：对岗位进行授权
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param afaAuthParty
	 * @return
	 *
	 * @date   创建时间：2017年4月14日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> doAuthorize(List<AfaAuthParty> afaAuthPartys);
	
	/**
	 * <p>函数名称：doDelete        </p>
	 * <p>功能说明：删除用户信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param apiUser
	 * @return
	 *
	 * @date   创建时间：2017年4月14日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> doDelete(ApiUser apiUser);
	
	public Page<ApiUserOrgPos> queryApiUserPage(Searcher searcher, Page<ApiUserOrgPos> page);
	
	/**
	 * <p>函数名称：batchDeleteUsers        </p>
	 * <p>功能说明：批量删除用户
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param afasUsers
	 * @return
	 *
	 * @date   创建时间：2017年4月24日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> batchDeleteUsers(List<ApiUser> afasUsers);
	
	/**
	 * <p>函数名称：resetPassWord        </p>
	 * <p>功能说明：重置密码
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param apiUser
	 * @param newPassword
	 * @return
	 *
	 * @date   创建时间：2017年4月26日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Map<String, Object> resetPassWord(ApiUser apiUser, String newPassword);
	
	public Map<String, Object> queryByOrgAndRole(String orgCode, String roleCode);
	
	
	/**
	 * <p>函数名称：querAssetTodoUsers        </p>
	 * <p>功能说明：根据用户编码查询是否有待办事项
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCode
	 * @return
	 *
	 * @date   创建时间：2017年5月3日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public int querAssetTodoUsers(String userCode);
	
	public ApiUser selectApiUser(ApiUser ApiUser);
	
	/**
	 * <p>函数名称：doTempSave        </p>
	 * <p>功能说明：暂存用户信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param aimsUserMain
	 * @param apiUser
	 * @param amcSubPosPanel_subPosByUser
	 * @param params
	 * @param editFlag
	 * @param user
	 * @return
	 *
	 * @date   创建时间：2017年9月18日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public Map<String, Object> doTempSave(AimsUserMain aimsUserMain, ApiUser apiUser,List<DrcAuthRuleOrg> amcSubPosPanel_subPosByUser,
			ActivitiParams params, int editFlag, User user);

	public ProcessResult doAuditOperation(AimsUserMain aimsUserMain, ApiUser ApiUser,List<DrcAuthRuleOrg> amcSubPosPanel_subPosByUser,
			ActivitiParams params, String commitOrAudit, User user);
	
	/**
	 * <p>函数名称： validateUserCode       </p>
	 * <p>功能说明：校验用户编码唯一
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCode
	 * @return
	 *
	 * @date   创建时间：2017年9月21日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public int validateUserCode(String userCode);
	
	/**
	 * <p>函数名称：  queryShowTodoPage      </p>
	 * <p>功能说明：查询用户管理办结库分页信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param searcher
	 * @param page
	 * @return
	 *
	 * @date   创建时间：2017年9月27日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn)
	 */
	public Page<ApiUser> queryShowTodoPage(Searcher searcher, Page<ApiUser> page);
}
