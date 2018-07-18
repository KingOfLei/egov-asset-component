package com.bosssoft.egov.asset.amc.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bosssoft.egov.asset.amc.user.entity.AfaAuthParty;
import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRule;
import com.bosssoft.egov.asset.amc.user.entity.DrcAuthRuleOrg;
import com.bosssoft.platform.appframe.api.entity.ApiUser;
import com.bosssoft.platform.appframe.api.entity.ApiUserOrgPos;
import com.bosssoft.platform.common.lang.data.Page;
import com.bosssoft.platform.common.lang.data.Searcher;

/** 
 *
 * @ClassName   类名：AmcUserMapper 
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
public interface AmcUserMapper {
	
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
	public Page<DrcAuthRuleOrg> querySubOrgByUserCode(@Param("searcher") Searcher searcher, @Param("page") Page<DrcAuthRuleOrg> page);
	
	public List<DrcAuthRule> getDrcAuthRules(@Param("partyCode") String partyCode);
	
	/**
	 * <p>函数名称：deleteAuthPartyByPartyCode        </p>
	 * <p>功能说明：根据用户编码删除授权信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param afaAuthParty
	 * @return
	 *
	 * @date   创建时间：2017年4月14日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public int deleteAuthPartyByPartyCode(AfaAuthParty afaAuthParty);
	
	/**
	 * <p>函数名称：insertAfaAuthParty        </p>
	 * <p>功能说明：新增岗位授权信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param afaAuthParty
	 * @return
	 *
	 * @date   创建时间：2017年4月14日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public int insertAfaAuthParty(AfaAuthParty afaAuthParty);
	
	/**
	 * <p>函数名称：queryApiUserPage        </p>
	 * <p>功能说明：查询用户分页信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param searcher
	 * @param page
	 * @return
	 *
	 * @date   创建时间：2017年4月24日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public Page<ApiUserOrgPos> queryApiUserPage(@Param("searcher") Searcher searcher, @Param("page") Page<ApiUserOrgPos> page);

	/**
	 * <p>函数名称：querAssetTodoUsers        </p>
	 * <p>功能说明：根据用户编码查询用户是否有待办事项
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCode  用户编码
	 * @return
	 *
	 * @date   创建时间：2017年4月24日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public int querAssetTodoUsers(@Param("userCode") String userCode);
	
	/**
	 * <p>函数名称： batchDeleteAuthParty       </p>
	 * <p>功能说明：根据用户编码批量删除授权信息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param userCodes  用户编码
	 *
	 * @date   创建时间：2017年5月3日
	 * @author 作者：黄振雄 (mailto:huangzhenxiong@bosssoft.com.cn) 
	 */
	public void batchDeleteAuthParty(@Param("userCodes") List<String> userCodes);
	
	public ApiUser selectApiUser(ApiUser afaUser);
	
	/**
	 * <p>函数名称：queryShowTodoPage        </p>
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
	public Page<ApiUser> queryShowTodoPage(@Param("searcher")Searcher searcher,@Param("page")Page<ApiUser> page);
}
