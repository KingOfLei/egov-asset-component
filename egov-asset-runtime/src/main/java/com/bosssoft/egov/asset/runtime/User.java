package com.bosssoft.egov.asset.runtime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.bosssoft.egov.asset.aims.api.org.entity.AimsBasicOrg;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.NumberUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.runtime.spi.UserState;
import com.bosssoft.platform.shiro.session.Shareable;

/**
 *
 * @ClassName 类名：User
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月15日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年11月15日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
@Shareable
public class User implements com.bosssoft.platform.runtime.spi.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2178853743012301268L;

	private String userId;

	private String userCode;

	private String userName;

	private String nickName;

	private Long rgnId;

	private String rgnCode;

	private String rgnName;

	private Long orgId;

	private String orgCode;

	private String orgName;

	private String[] roleIds;

	private UserState state;
	
	private String identityCode;

	private Date lastLoginTime;
	
	//用户对应操作单位信息
	private AimsBasicOrg org;
	
	//新增其他属性
	private String mobileNo;
	
	private String email;
	
	//单位负责人
	private String agenPrincipal;
	
	//岗位列表
	private String[] positions = new String[0];
	private String[] roleCodes = new String[0];
	private String[] roleNames = new String[0];

	private Map<String, Object> properties = new HashMap<String, Object>();

	//是否已经默认选择单位 因为有刷新问题。。。
	private boolean hasChoseOrg;
	
	//默认用户对应数据权限
	private String userDrcOrgs;
	private String condition;
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.addProperties("userCode", userCode);																						
		this.addProperties("USER_CODE", userCode);																						
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.addProperties("userName", userName);																				
		this.addProperties("USER_NAME", userName);																				
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.addProperties("nickName", nickName);																		
		this.addProperties("NICK_NAME", nickName);																		
		this.nickName = nickName;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.addProperties("roleIds", StringUtilsExt.join(roleIds,","));																										
		this.roleIds = roleIds;
	}

	public UserState getState() {
		return state;
	}

	public void setState(UserState state) {
		this.state = state;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getOrgId() {
		return StringUtilsExt.convertNullToString(this.orgId);
	}

	public void setOrgId(String orgId) {
		this.addProperties("orgId", orgId);																
		this.addProperties("ORG_ID", orgId);																
		this.orgId = NumberUtilsExt.toLong(orgId);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.addProperties("userId", userId);														
		this.addProperties("USER_ID", userId);														
		this.userId = userId;
	}

	public Long getRgnId() {
		return rgnId;
	}

	public void setRgnId(Long rgnId) {
		this.addProperties("rgnId", StringUtilsExt.toString(rgnId));												
		this.addProperties("RGN_ID", StringUtilsExt.toString(rgnId));												
		this.rgnId = rgnId;
	}

	public String getRgnCode() {
		return rgnCode;
	}

	public void setRgnCode(String rgnCode) {
		this.addProperties("rgnCode", rgnCode);										
		this.addProperties("RGN_CODE", rgnCode);										
		this.rgnCode = rgnCode;
	}

	public String getRgnName() {
		return rgnName;
	}

	public void setRgnName(String rgnName) {
		this.addProperties("rgnName", rgnName);								
		this.addProperties("RGN_NAME", rgnName);								
		this.rgnName = rgnName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.addProperties("orgCode", orgCode);						
		this.addProperties("ORG_CODE", orgCode);						
		this.orgCode = orgCode;
		this.properties.put("DRC_SQL", getDrcSql());
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.addProperties("orgName", orgName);				
		this.addProperties("ORG_NAME", orgName);				
		this.orgName = orgName;
	}

	public void setOrgId(Long orgId) {
		this.addProperties("orgId", StringUtilsExt.toString(orgId));		
		this.addProperties("ORG_ID", StringUtilsExt.toString(orgId));		
		this.orgId = orgId;
	}
	
	public Long getOrgLongId(){
		return this.orgId;
	}

	@Override
	public String[] getRoles() {
		return this.roleIds;
	}

	@Override
	public String getIdentityCode() {
		return this.identityCode;
	}
	
	public void setIdentityCode(String identityCode){
		this.addProperties("identityCode", identityCode);
		this.addProperties("IDENTITY_CODE", identityCode);
		this.identityCode = identityCode;
	}

	@Override
	public Map<String, Object> getProperties() {		
		return this.properties;
	}

	@Override
	public String getProperty(String prop) {		
		return MapUtilsExt.getString(this.getProperties(), prop);
	}
	
	public void addProperties(String key, String value) {
		this.properties.put(key, value);
	}

	public AimsBasicOrg getOrg() {
		return org;
	}

	public void setOrg(AimsBasicOrg org) {
		this.org = org;
	}
	

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.addProperties("mobileNo", mobileNo);
		this.addProperties("MOBILE_NO", mobileNo);
		this.mobileNo = mobileNo;
	}

	public String getAgenPrincipal() {
		return agenPrincipal;
	}

	public void setAgenPrincipal(String agenPrincipal) {
		this.addProperties("agenPrincipal", agenPrincipal);
		this.agenPrincipal = agenPrincipal;
	}

	public Boolean isSydw(){
		if(this.org != null){
		  return !"01".equals(this.org.getAccSysCode());
		}
		return false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.addProperties("email", email);																								
		this.addProperties("EMAIL", email);																								
		this.email = email;
	}
	
	//数据权限
	public String getDrcSql() {
		String drcSql = "";
		if(!StringUtilsExt.isEmpty(orgCode)){
			//判断用户对应单位是否为最细级 最细级是 直接用等于判断否则用like
			if(org != null){
			  if(org.getIsParent()){
				  drcSql = " AND (ORG_CODE >='" + orgCode + "' AND ORG_CODE <='" + orgCode + "z')";
			  } else {
				  drcSql = " AND ORG_CODE = '" + orgCode + "' ";
			  }
			} else {
				  drcSql = " AND (ORG_CODE >='" + orgCode + "' AND ORG_CODE <='" + orgCode + "z')";
			}
		}
		
		if(StringUtilsExt.isEmpty(drcSql)){
			return " AND 1=2 ";
		} else {
			if(StringUtilsExt.isNotEmpty(this.userDrcOrgs) && StringUtilsExt.isNotEmpty(this.condition)){
				drcSql += " AND ORG_CODE IN (" + this.condition + ")";
			}
		}
		return drcSql;
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}

	public boolean isChoseOrg() {
		return hasChoseOrg;
	}

	public void setChoseOrg(boolean hasChoseOrg) {
		this.hasChoseOrg = hasChoseOrg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(String[] roleCodes) {
		this.roleCodes = roleCodes;
	}

	public String[] getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}
	
	public String getUseRoleName(){
		if(roleNames != null && roleNames.length > 0){
			return roleNames[0];
		} else {
			return "";
		}
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年2月22日
	 * @author 作者：xds
	 */
	public boolean currentIsParent(){
		if(this.org!=null){
		   return this.org.isParent();
		}
		return true;
	}

	@Override
	public void setProperties(Map<String, Object> prop) {
		// TODO Auto-generated method stub
		if(this.properties!= null){
			this.properties.putAll(prop);
		}
	}

	public String getUserDrcOrgs() {
		return userDrcOrgs;
	}

	public void setUserDrcOrgs(String userDrcOrgs) {
		this.userDrcOrgs = userDrcOrgs;
		this.condition = StringUtilsExt.strWithQuotes(StringUtilsExt.split(userDrcOrgs,","));
		this.properties.put("DRC_SQL", getDrcSql());
	}
	
}
