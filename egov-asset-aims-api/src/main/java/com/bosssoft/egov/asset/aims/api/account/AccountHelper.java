package com.bosssoft.egov.asset.aims.api.account;

import java.util.Map;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.aims.api.SpringExtensionExtHelper;
import com.bosssoft.egov.asset.aims.api.account.service.AccountService;
import com.bosssoft.egov.asset.common.i18n.SysTipsHelper;
import com.bosssoft.egov.asset.common.i18n.SysTipsHelper.BizType;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;

/** 
*
* @ClassName   类名：AccountHelper 
* @Description 功能说明：核算接口工具类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年12月4日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年12月4日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/

public class AccountHelper {
	
	@Resource
	private AccountService accountService;
	
	private static AccountHelper INSTANCE;

	
	public static synchronized AccountHelper getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new AccountHelper();
		}
		return INSTANCE;
   }
	
	private AccountHelper(){
		SpringExtensionExtHelper.initAutowireFields(this);
	}
	
	/**
	 * 
	 * <p>函数名称：isDockingAccount        </p>
	 * <p>功能说明： 是否对接核算接口服务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param orgId 查询单位的orgId
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public boolean isDockingAccount(Long orgId){
		return accountService.isDockingAccount(orgId);
	}
	
	/**
	 * 
	 * <p>函数名称：isDockingAccount        </p>
	 * <p>功能说明：是否对接核算接口
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param orgId 单位id
	 * @param bizDate 对接业务的业务日期
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public boolean isDockingAccount(Long orgId, String bizDate){
		return accountService.isDockingAccount(orgId, bizDate); 
	} 
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：是否能驳回
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busId
	 * @param orgCode
	 * @param busType
	 * @return
	 *
	 * @date   创建时间：2017年12月4日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public AccountResult doRevoke(Long busId, Long orgId, String orgCode,String bizDate,Integer accountStatus,
			AccountBizTypeEnum busType){
		AccountResult accountResult = new AccountResult();
		// isDockingAccount 是否对接财务系统true对接 false未对接
		// accountStatus 40财务记账 41财务撤销记账 42财务重新记账
		if(!accountService.isDockingAccount(orgId,bizDate) || (accountStatus == 40 || accountStatus == 42)){			
			accountResult.setCode("HS000");
			accountResult.setSuccess(true);
			accountResult.setMessage(SysTipsHelper.getString(BizType.ACCOUNT, "000"));
			return accountResult;
		}
		//访问接口方法 UP_ASSET_REVOKE_TO_ACCOUNT 存储过程
		Map<String,Object> result = accountService.doRevoke(busId, orgCode, busType.getBizTypeCode());
		return setAccountResult(accountResult, result);
	}
	
	/**
	 * 
	 * <p>函数名称：  doSubmit      </p>
	 * <p>功能说明： 动态提交到财务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param busId
	 * @param orgId
	 * @param orgCode
	 * @param busType
	 * @return
	 *
	 * @date   创建时间：2017年12月10日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public AccountResult doSubmit(Long busId, Long orgId, String orgCode,AccountBizTypeEnum busType){
		AccountResult accountResult = new AccountResult();
		Map<String,Object> result = accountService.doSubmit(busId, orgCode, busType.getBizTypeCode());
		return setAccountResult(accountResult, result);
	}
	
	/**
	 * 
	 * <p>函数名称： setAccountResult       </p>
	 * <p>功能说明： 设置返回值
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param accountResult
	 * @param result
	 * @return
	 *
	 * @date   创建时间：2017年12月7日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private AccountResult setAccountResult(AccountResult accountResult, Map<String,Object> result){
		if(MapUtilsExt.getInteger(result, "returnValue", 0) == 0){
			accountResult.setCode("HS009");
			accountResult.setSuccess(false);
			accountResult.setMessage(MapUtilsExt.getString(result, "oErrStr",SysTipsHelper.getString(BizType.ACCOUNT, "009")));
		} else {
			accountResult.setCode("HS001");
			accountResult.setSuccess(true);
			accountResult.setMessage(SysTipsHelper.getString(BizType.ACCOUNT, "001"));
		}		
		return accountResult;
	}
	
}
