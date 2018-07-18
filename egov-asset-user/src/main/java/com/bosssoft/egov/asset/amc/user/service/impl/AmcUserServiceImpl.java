package com.bosssoft.egov.asset.amc.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.activiti.entity.ActivitiException;
import com.bosssoft.egov.asset.activiti.entity.ActivitiParams;
import com.bosssoft.egov.asset.activiti.entity.HandleType;
import com.bosssoft.egov.asset.activiti.entity.ProcessResult;
import com.bosssoft.egov.asset.activiti.service.ActivitiProcessService;
import com.bosssoft.egov.asset.amc.user.api.AimsUserMainService;
import com.bosssoft.egov.asset.amc.user.common.BusType;
import com.bosssoft.egov.asset.amc.user.entity.AfaAuthParty;
import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRule;
import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRuleOrg;
import com.bosssoft.egov.asset.amc.user.mapper.AmcDrcAuthRuleMapper;
import com.bosssoft.egov.asset.amc.user.mapper.AmcUserMapper;
import com.bosssoft.egov.asset.amc.user.model.AimsUserMain;
import com.bosssoft.egov.asset.amc.user.service.AmcUserService;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.util.SpringObjectUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.egov.asset.runtime.User;
import com.bosssoft.egov.asset.todo.api.TodoTaskService;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.appframe.api.entity.ApiUserOrgPos;
import com.bosssoft.platform.appframe.api.service.UserService;
import com.bosssoft.platform.cache.CacheFactory;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;
import com.bosssoft.platform.runtime.exception.BusinessException;

/** 
 *
 * @ClassName   类名：AmcUserServiceImpl 
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
@Service
public class AmcUserServiceImpl implements AmcUserService{
	private static Logger logger = LoggerFactory.getLogger(AmcUserServiceImpl.class);
	@Autowired
	private AmcUserMapper amcUserMapper;
	
	@Autowired
	private AmcDrcAuthRuleMapper drcAuthRuleMapper;
	
	@Autowired
	private UserService afaUserService;
	
	@Autowired
	private ActivitiProcessService activitiProcessService;
	
	@Autowired
	private AimsUserMainService aimsUserMainService;
	
	@Autowired
	@Qualifier(TodoTaskService.REFERENCE_BEAN_NAME)
	private TodoTaskService assetTodoTaskService;

	@Override
	public Page<DrcAuthRuleOrg> querySubOrgByUserCode(Searcher searcher, Page<DrcAuthRuleOrg> page) {
		return amcUserMapper.querySubOrgByUserCode(searcher,page);
	}

	@Override
	public Map<String, Object> insertSubOrg(DrcAuthRuleOrg drcAuthRuleOrg, String userId,
			String userCode, String userName) {
		Map<String, Object> result = new HashMap<String, Object>();
		DrcAuthRule drcAuthRule = new DrcAuthRule();
		drcAuthRule.setId(ComponetIdGen.newWKID());
		drcAuthRule.setCfgId(ComponetIdGen.newWKID());
		String partyId = userId;
		String ruleValueId = drcAuthRuleOrg.getRuleValueId();
		String rgnId = drcAuthRuleOrg.getRgnId();
		String roleId = drcAuthRuleOrg.getRoleId();
		if(partyId != null && partyId != ""){
			drcAuthRule.setPartyId(partyId);
		}
		drcAuthRule.setPartyCode(userCode);
		drcAuthRule.setPartyName(userName);
		if(ruleValueId != null && ruleValueId != ""){
			drcAuthRule.setRuleValueId(new Long(ruleValueId));
		}
		drcAuthRule.setRuleValueCode(drcAuthRuleOrg.getRuleValueCode());
		drcAuthRule.setRuleValue(drcAuthRuleOrg.getRuleValueCode());
		drcAuthRule.setRuleValueName(drcAuthRuleOrg.getRuleValueName());
		drcAuthRule.setRemark(drcAuthRuleOrg.getRuleValueName());
		if(rgnId != null && rgnId != ""){
			drcAuthRule.setRgnId(new Long(rgnId));
		}
		drcAuthRule.setRgnCode(drcAuthRuleOrg.getRgnCode());
		drcAuthRule.setRgnName(drcAuthRuleOrg.getRgnName());
		drcAuthRule.setStatus("1");
		drcAuthRule.setPartyType("user");
		drcAuthRule.setRuleType("org");
		drcAuthRule.setRuleField("ORG_CODE");
		drcAuthRule.setRuleOperator("system");
		drcAuthRule.setRoleId(roleId);
		drcAuthRule.setRoleCode(drcAuthRuleOrg.getRoleCode());
		drcAuthRule.setRoleName(drcAuthRuleOrg.getRoleName());
		drcAuthRule.setRoleType(drcAuthRuleOrg.getRoleType());
		drcAuthRuleMapper.insertSelective(drcAuthRule);
		result.put("tag", true);
		result.put("msg", "机构添加成功！");
		/*if(drcAuthRuleMapper.insertSelective(drcAuthRule) <= 0){
			result.put("tag", false);
			result.put("msg", "机构添加失败！");
		}else {
			result.put("tag", true);
			result.put("msg", "机构添加成功！");
		}*/
		return result;
	}

	@Override
	public Map<String, Object> delSubOrg(String userCode, String orgId, String roleId, String roleCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		DrcAuthRule drcAuthRule = new DrcAuthRule();
		drcAuthRule.setPartyCode(userCode);
		if(orgId != null && !"".equals(orgId)){
			drcAuthRule.setRuleValueId(new Long(orgId));
		}
		drcAuthRule.setRoleId(roleId);
		int resultCount = drcAuthRuleMapper.delSubPos(drcAuthRule);
		if(resultCount > 0){
			//如果roleId为空，则不进行遍历权限操作
			if("".equals(StringUtilsExt.convertNullToString(roleId))){
				result.put("tag", true);
				result.put("msg", userCode + "对应【" + roleCode + "】机构岗位信息删除成功！");
			}else{
				//遍历记录 判断是否DRC_AUTH_RULE表中该用户是否还有该权限
				DrcAuthRule queryDrcRule = new DrcAuthRule();
				queryDrcRule.setPartyCode(userCode);
				queryDrcRule.setRoleId(roleId);
				List<DrcAuthRule> resultRules = drcAuthRuleMapper.select(queryDrcRule);
				//如果该用户没有相应岗位则需要删除 相应的授权信息
				if(resultRules == null || resultRules.size() == 0){
					AfaAuthParty queryParty = new AfaAuthParty();
					queryParty.setPartyCode(userCode);
					queryParty.setRoleCode(roleCode);
					amcUserMapper.deleteAuthPartyByPartyCode(queryParty);
					result.put("tag", true);
					result.put("msg", userCode + "对应【" + roleCode + "】授权信息删除成功！");
					//清除缓存
					CacheFactory.getInstance().findCache("RoleCache").clear();
					CacheFactory.getInstance().findCache("MenuCache").clear();
					CacheFactory.getInstance().findCache("FuncCache").clear();
					/*if(amcUserMapper.deleteAuthPartyByPartyCode(queryParty) > 0){
						result.put("tag", true);
						result.put("msg", userCode + "对应【" + roleCode + "】授权信息删除成功！");
						//清除缓存
						CacheFactory.getInstance().findCache("RoleCache").clear();
						CacheFactory.getInstance().findCache("MenuCache").clear();
						CacheFactory.getInstance().findCache("FuncCache").clear();
					}else{
						result.put("tag", false);
						result.put("msg", userCode + "对应【" + roleCode + "】授权信息删除失败！");
						throw new BusinessException(userCode + "对应【" + roleCode + "】授权信息删除失败！");
					}*/
				}else{
					result.put("tag", true);
					result.put("msg", userCode + "对应【" + roleCode + "】机构岗位信息删除成功！");
				}
			}
		}else{
			result.put("tag", false);
			result.put("msg", userCode + "对应【" + roleCode + "】机构岗位信息删除失败！");
			logger.error(userCode + "对应【" + roleCode + "】机构岗位信息删除失败！");
			throw new BusinessException(userCode + "对应【" + roleCode + "】机构岗位信息删除失败！");
		}
		return result;
	}

	@Override
	public Map<String, Object> delSubPos(DrcAuthRule drcAuthRule) {
		Map<String, Object> result = new HashMap<String, Object>();
		DrcAuthRule queryDrcAuthRule = new DrcAuthRule();
		queryDrcAuthRule.setPartyCode(drcAuthRule.getPartyCode());
		queryDrcAuthRule.setRuleValueId(drcAuthRule.getRuleValueId());
		queryDrcAuthRule.setRoleId(drcAuthRule.getRoleId());
		DrcAuthRule resultDrcAuthRule = drcAuthRuleMapper.selectOne(queryDrcAuthRule);
		resultDrcAuthRule.setRoleId("");
		resultDrcAuthRule.setRoleCode("");
		resultDrcAuthRule.setRoleName("");
		resultDrcAuthRule.setRoleType("");
		if(drcAuthRuleMapper.updateById(resultDrcAuthRule) <= 0){
			result.put("tag", false);
			result.put("msg", "岗位删除失败！");
		}else {
			result.put("tag", true);
			result.put("msg", "岗位删除成功！");
		}
		return result;
	}

	@Override
	public List<DrcAuthRule> getDrcAuthRules(String userCode) {
		return amcUserMapper.getDrcAuthRules(userCode);
	}

	@Override
	public Map<String, Object> batchInsertSubOrg(
			List<DrcAuthRuleOrg> drcAuthRuleOrgs, String userId, String userCode,
			String userName) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(drcAuthRuleOrgs == null){
			result.put("tag",true);
			result.put("msg", "暂无机构岗位信息！");
			return result;
		}
		DrcAuthRule drcAuthRule = new DrcAuthRule();
		//判断用户编码是否为空
		if("".equals(StringUtilsExt.convertNullToString(userCode))){
			result.put("tag", false);
			result.put("msg", "新增机构岗位信息时用户编码为空！");
			logger.error("新增机构岗位信息时用户编码为空！");
			return result;
		}
		drcAuthRule.setPartyCode(userCode);
		
		List<DrcAuthRuleOrg> authRuleOrgs = new ArrayList<DrcAuthRuleOrg>();
		List<String> ruleValueIds = new ArrayList<String>();
		//批量增加前去除重复的机构岗位信息
		for(int i = 0;i < drcAuthRuleOrgs.size();i++){
			DrcAuthRuleOrg authRuleOrg = drcAuthRuleOrgs.get(i);
			if(authRuleOrg != null){
				String ruleValueId = StringUtilsExt.convertNullToString(authRuleOrg.getRuleValueId());
				String roleCode = StringUtilsExt.convertNullToString(authRuleOrg.getRoleCode());
				//将单位ID和角色编码拼接成字符串进行是否重复判断
				String orgIdAndRoleCode = ruleValueId + "," + roleCode;
				//如果单位ID和角色编码同时为空，表明是空行，跳过该记录
				if("".equals(ruleValueId) && "".equals(roleCode)){
					continue;
				}else if("".equals(ruleValueId)){
					result.put("tag", false);
					result.put("msg", "新增机构岗位信息时单位ID为空！");
					logger.error("新增机构岗位信息时单位ID为空！");
					return result;
				}
				//如果单位ID和角色编码组合  不重复  则添加到List集合中
				if(!ruleValueIds.contains(orgIdAndRoleCode)){
					ruleValueIds.add(orgIdAndRoleCode);
					authRuleOrgs.add(authRuleOrg);
				}
			}
		}
		//新增前先根据用户编码将DRC_AUTH_RULE表中数据清空
		drcAuthRuleMapper.deleteById(drcAuthRule);
		//遍历筛选重复之后的List<DrcAuthRuleOrg>集合 
		if(authRuleOrgs != null && authRuleOrgs.size() > 0){
			for(DrcAuthRuleOrg drcAuthRuleOrg : authRuleOrgs){
				result = this.insertSubOrg(drcAuthRuleOrg, userId, userCode, userName);
				if(result.get("tag") == Boolean.FALSE){
					return result;
				}
			}
		}
		result.put("tag", true);
		result.put("msg", "批量增加成功！");
		return result;
	}

	@Override
	public Map<String, Object> doInsert(ApiUser afaUser,
			List<DrcAuthRuleOrg> drcAuthRuleOrgs) {
		Map<String, Object> result = new HashMap<String, Object>();
		//调用平台Service新增用户信息
		afaUserService.doInsert(afaUser);
		String userId = afaUser.getUserId();
		String userCode = afaUser.getUserCode();
		String userName = afaUser.getUserName();
		//新增机构岗位信息
		result = this.batchInsertSubOrg(drcAuthRuleOrgs, userId, userCode, userName);
		if(result.get("tag") == Boolean.TRUE){
			//对机构岗位信息进行授权
			//根据用户编码删除用户的授权信息
			AfaAuthParty afaAuthParty = new AfaAuthParty();
			afaAuthParty.setPartyCode(userCode);
			amcUserMapper.deleteAuthPartyByPartyCode(afaAuthParty);
			List<AfaAuthParty> afaAuthParties = new ArrayList<AfaAuthParty>();
			List<String> roles = new ArrayList<String>();
			for(int i = 0;i < drcAuthRuleOrgs.size();i++){
				AfaAuthParty authParty = new AfaAuthParty();
				DrcAuthRuleOrg drcAuthRuleOrg = drcAuthRuleOrgs.get(i);
				if(drcAuthRuleOrg == null){
					continue;
				}
				//数据库表中 roleCode roleType partyCode不能为null,如果为null跳过这条记录
				String roleCode = drcAuthRuleOrg.getRoleCode();
				String roleType = drcAuthRuleOrg.getRoleType();
				if(!StringUtilsExt.isNotBlank(roleCode) || !StringUtilsExt.isNotBlank(roleType)
						|| !StringUtilsExt.isNotBlank(userCode)){
					continue;
				}
				//根据角色编码筛选掉重复的
				if(!roles.contains(roleCode)){
					roles.add(roleCode);
					authParty.setRoleCode(roleCode);
					authParty.setRoleType(StringUtilsExt.convertNullToString(drcAuthRuleOrg.getRoleType()));
					authParty.setPartyCode(StringUtilsExt.convertNullToString(userCode));
					authParty.setPartyType("user");
					authParty.setCreateUser("system");
					afaAuthParties.add(authParty);
				}
			}
			//新增用户授权信息  并返回操作提示
			result.clear();
			result = this.doAuthorize(afaAuthParties);
			if(result.get("tag") == Boolean.FALSE){
				result.put("tag", false);
				result.put("msg", "批量授权失败！");
				throw new BusinessException("批量授权失败！");
			}else{
				result.put("tag", true);
				result.put("msg", "批量授权成功！");
			}
		}else{
			result.put("tag", false);
			result.put("msg", "请填写机构名称！");
			throw new BusinessException("请填写机构名称！");
		}
		return result;
	}

	@Override
	public Map<String, Object> doUpdate(ApiUser afaUser,
			List<DrcAuthRuleOrg> drcAuthRuleOrgs) {
		Map<String, Object> result = new HashMap<String, Object>();
		//调用平台Service修改用户信息
		afaUserService.doUpdate(afaUser);
		String userId = afaUser.getUserId();
		String userCode = afaUser.getUserCode();
		String userName = afaUser.getUserName();
		if(drcAuthRuleOrgs == null){
			logger.warn("机构岗位信息为null");
			result.put("tag", true);
			result.put("msg", "机构岗位信息为null！");
			return result;
		}
		//新增机构岗位信息
		result = this.batchInsertSubOrg(drcAuthRuleOrgs, userId, userCode, userName);
		
		if(result.get("tag") == Boolean.TRUE){
			//根据用户编码删除用户的授权信息
			AfaAuthParty afaAuthParty = new AfaAuthParty();
			afaAuthParty.setPartyCode(userCode);
			amcUserMapper.deleteAuthPartyByPartyCode(afaAuthParty);
			List<AfaAuthParty> afaAuthParties = new ArrayList<AfaAuthParty>();
			List<String> roles = new ArrayList<String>();
			for(int i = 0;i < drcAuthRuleOrgs.size();i++){
				AfaAuthParty authParty = new AfaAuthParty();
				DrcAuthRuleOrg drcAuthRuleOrg = drcAuthRuleOrgs.get(i);
				//数据库表中 roleCode roleType partyCode不能为null,如果为null跳过这条记录
				userCode = StringUtilsExt.convertNullToString(userCode);
				String roleCode = StringUtilsExt.convertNullToString(drcAuthRuleOrg.getRoleCode());
				String roleType = StringUtilsExt.convertNullToString(drcAuthRuleOrg.getRoleType());
				if("".equals(roleCode) || "".equals(roleType) || "".equals(userCode)){
					continue;
				}
				//根据角色编码筛选掉重复的
				if(!roles.contains(roleCode)){
					roles.add(roleCode);
					authParty.setRoleCode(roleCode);
					authParty.setRoleType(StringUtilsExt.convertNullToString(drcAuthRuleOrg.getRoleType()));
					authParty.setPartyCode(StringUtilsExt.convertNullToString(userCode));
					authParty.setPartyType("user");
					authParty.setCreateUser("system");
					authParty.setCreateTime(new Date());
					afaAuthParties.add(authParty);
				}
			}
			//新增用户授权信息  并返回操作提示
			result.clear();
			result = this.doAuthorize(afaAuthParties);
			if(result.get("tag") == Boolean.TRUE){
				result.put("tag", true);
				result.put("msg", "批量授权成功！");
			}else {
				result.put("tag", false);
				result.put("msg", "批量授权失败！");
				throw new BusinessException("批量授权失败！");
			}
		}else{
			result.put("tag", false);
			result.put("msg", "请填写机构名称！");
			throw new BusinessException("请填写机构名称！");
		}
		return result;
	}

	@Override
	public Map<String, Object> doAuthorize(List<AfaAuthParty> afaAuthPartys) {
		Map<String, Object> result = new HashMap<String, Object>();
		int j = 0;
		for(int i = 0;i < afaAuthPartys.size();i++){
			AfaAuthParty afaAuthParty = afaAuthPartys.get(i);
			j += amcUserMapper.insertAfaAuthParty(afaAuthParty);
		}
		if(j < afaAuthPartys.size()){
			result.put("tag", false);
			result.put("msg", "批量授权失败！");
		}else{
			result.put("tag", true);
			result.put("msg", "批量授权成功！");
			//清除缓存
			CacheFactory.getInstance().findCache("RoleCache").clear();
			CacheFactory.getInstance().findCache("MenuCache").clear();
			CacheFactory.getInstance().findCache("FuncCache").clear();
		}
		return result;
	}

	@Override
	public Map<String, Object> doDelete(ApiUser afaUser) {
		Map<String, Object> result = new HashMap<String, Object>();		
		//删除用户前判断该用户是否有待办事项
		String userCode = afaUser.getUserCode();
		String userName = StringUtilsExt.convertNullToString(afaUser.getUserName());
		//如果没有待办事项则进行删除操作
		if(amcUserMapper.querAssetTodoUsers(userCode) <= 0){
			//逻辑删除用户信息  ——通过更新用户表的状态为lock实现
			afaUser.setStatus("lock");
			afaUserService.doUpdate(afaUser);
			//根据用户编码删除机构岗位信息
			DrcAuthRule drcAuthRule = new DrcAuthRule();
			drcAuthRule.setPartyCode(StringUtilsExt.convertNullToString(userCode));
			drcAuthRuleMapper.deleteById(drcAuthRule);
			//根据用户编码删除授权信息
			AfaAuthParty afaAuthParty = new AfaAuthParty();
			afaAuthParty.setPartyCode(userCode);
			amcUserMapper.deleteAuthPartyByPartyCode(afaAuthParty);
			result.put("tag", true);
			result.put("msg", "用户信息删除成功！（状态设置为锁定）");
		}else{
			result.put("tag", false);
			result.put("msg", userCode + "存在待办事项，不能删除！");
			logger.error(userCode + "存在待办事项，不能删除！");
			throw new BusinessException("【" + userCode + "】"+ userName +"存在待办事项，不能删除！");
		}
		return result;
	}

	@Override
	public Page<ApiUserOrgPos> queryApiUserPage(Searcher searcher,
			Page<ApiUserOrgPos> page) {
		return amcUserMapper.queryApiUserPage(searcher, page);
	}

	@Override
	public Map<String, Object> batchDeleteUsers(List<ApiUser> afasUsers) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> userCodes = new ArrayList<String>();
 		//遍历修改用户状态
		for(ApiUser afaUser:afasUsers){
			//逻辑删除用户信息  ——通过更新用户表的状态为lock实现
			afaUser.setStatus("lock");
			afaUserService.doUpdate(afaUser);
			String userCode = StringUtilsExt.convertNullToString(afaUser.getUserCode());
			if(!"".equals(userCode)){
				userCodes.add(userCode);
			}
		}
		//批量删除机构岗位信息
		drcAuthRuleMapper.batchDeleteAuthRule(userCodes);
//		//批量删除授权信息
		amcUserMapper.batchDeleteAuthParty(userCodes);
		result.put("tag", true);
		result.put("msg", "用户信息批量删除成功！（状态设置为锁定）");
		return result;
	}

	@Override
	public Map<String, Object> resetPassWord(ApiUser afaUser, String newPassword) {
		Map<String, Object> result = new HashMap<String, Object>();
		String userCode = StringUtilsExt.convertNullToString(afaUser.getUserCode());
		String userName = StringUtilsExt.convertNullToString(afaUser.getUserName());
		afaUser.setPassword(newPassword);
		try{
//			afaUserService.resetPassWord(afaUser, newPassword);
			afaUserService.resetUserPassWord(afaUser);
			result.put("tag", true);
			result.put("msg", "【" + userCode+"】" + userName + "重置密码成功！");
		}catch(Exception exception){
			result.put("tag", false);
			result.put("msg", "【" + userCode+"】" + userName + "重置密码失败！");
			throw new BusinessException("【" + userCode+"】" + userName + "重置密码失败！");
		}
		return result;
	}

	@Override
	public Map<String, Object> queryByOrgAndRole(String orgCode, String roleCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		DrcAuthRule queryDrcAuthRule = new DrcAuthRule();
		queryDrcAuthRule.setRuleValueCode(orgCode);
		queryDrcAuthRule.setRoleCode(roleCode);
		List<DrcAuthRule> resultDrcAuthRules = drcAuthRuleMapper.select(queryDrcAuthRule);
		if(resultDrcAuthRules != null && resultDrcAuthRules.size() > 0){
			result.put("tag",true);
			result.put("msg","已有人拥有【"+ orgCode +"】" + roleCode + "相应权限！");
		}else{
			result.put("tag",false);
			result.put("msg","暂无人拥有【"+ orgCode +"】" + roleCode + "相应权限！");
		}
		return result;
	}

	@Override
	public int querAssetTodoUsers(String userCode) {
		return amcUserMapper.querAssetTodoUsers(userCode);
	}

	@Override
	public ApiUser selectApiUser(ApiUser afaUser) {
		return amcUserMapper.selectApiUser(afaUser);
	}

	@Override
	public Map<String, Object> doTempSave(AimsUserMain aimsUserMain, ApiUser afaUser, List<DrcAuthRuleOrg> amcSubPosPanel_subPosByUser,
			ActivitiParams params, int editFlag, User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(editFlag == 0){
			//校验用户账号是否存在
			String userCode = afaUser.getUserCode();
			if(this.validateUserCode(userCode) != 0){
				result.put("tag", false);
				result.put("msg", "用户账号已存在！");
				return result;
			}
			result = this.doInsert(afaUser, amcSubPosPanel_subPosByUser);
		}else{
			result = this.doUpdate(afaUser, amcSubPosPanel_subPosByUser);
		}
		if(result.get("tag") == Boolean.TRUE){
			// 暂存的时候调用工作流的暂存方法 
			if(!SpringObjectUtilsExt.isEmpty(params)){
				activitiProcessService.tempSave(params);
			}
			//暂存的时候写待办 (0 新增 1修改)
			assetTodoTaskService.assetTodo(aimsUserMain,editFlag, user);
		}
		return result;
	}

	@Override
	public ProcessResult doAuditOperation(AimsUserMain aimsUserMain, ApiUser afaUser, List<DrcAuthRuleOrg> amcSubPosPanel_subPosByUser,
			ActivitiParams params, String commitOrAudit, User user) {
		ProcessResult results = new ProcessResult();
		try {
			results = activitiProcessService.handleTask(params);
			if(ActivitiException.SUCCESS.getCode().equals(results.getCode())){
				//修改AimsUserMain
				aimsUserMain.setBizStatus(results.getBizStatus());
				aimsUserMain.setBizStatusName(results.getBIzStatusName());
				
				//修改AIMS_USER_MAIN表
				aimsUserMainService.updateAimsUserMain(aimsUserMain);
				
				//根据commitOrAudit的值 判断是提交操作还是审核操作
				if("USER_AUDIT".equals(commitOrAudit)){
					//执行审核操作
					
					//审核  通过
					if(HandleType.PASS.getHandle().intValue() == params.getHandle().intValue()){
						//需要修改AIMS_USER_MAIN表STATUS
						aimsUserMain.setStatus(0);
						aimsUserMainService.updateAimsUserMain(aimsUserMain);
						String busType = StringUtilsExt.convertNullToString(aimsUserMain.getBusType());
						//新增用户
//						if("ADD_USER".equals(busType)){
						if(BusType.ADD.TYPE.equals(busType)){
							//需要修改用户状态
							afaUser.setStatus("normal");
							afaUserService.doUpdate(afaUser);
						}
						//删除用户的审核  需要修改用户的状态
						if(BusType.DELETE.TYPE.equals(busType)){
							//调用删除的方法
							this.doDelete(afaUser);
							//删除相应待办
							assetTodoTaskService.assetTodo(aimsUserMain, 2, user);
						}
						//不推下一岗
						assetTodoTaskService.assetTodo(aimsUserMain, results, params, false, user);
					}
					//退回
					if(HandleType.RETURN.getHandle().intValue() == params.getHandle().intValue()){
						assetTodoTaskService.assetTodo(aimsUserMain, results, params, user);
					}
				}else{
					assetTodoTaskService.assetTodo(aimsUserMain, results, params, user);
				}
			}else{
				throw new BusinessException("AT404", results.getMessage());
			}
		} catch (Exception e) {
			throw new BusinessException("AT404", e.getMessage());
		}
		return results;
	}

	@Override
	public int validateUserCode(String userCode) {
		userCode = StringUtilsExt.convertNullToString(userCode);
		ApiUser apiUser = afaUserService.getUserByUserCode(userCode);
		if(apiUser != null){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public List<AfaAuthParty> querySubPosList() {
		return amcUserMapper.querySubPosList();
	}

	@Override
	public Page<ApiUser> queryShowTodoPage(Searcher searcher, Page<ApiUser> page) {
		return amcUserMapper.queryShowTodoPage(searcher,page);
	}
	
}
