package com.bosssoft.egov.aims.config.service;

import java.util.List;

import javax.annotation.Resource;

import com.bosssoft.egov.asset.aims.api.SpringExtensionExtHelper;
import com.bosssoft.egov.asset.aims.api.config.entity.AssetSysConfig;
import com.bosssoft.egov.asset.aims.api.config.service.ConfigService;

/** 
*
* @ClassName   类名：SysConfigHelper 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年2月8日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年2月8日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class SysConfigHelper {
	
	@Resource(name=ConfigService.REFERENCE_BEAN_NAME)
	private ConfigService sysConfigService;
	
	private static SysConfigHelper INSTANCE;
	
	private SysConfigHelper(){
		SpringExtensionExtHelper.initAutowireFields(this);
//		sysConfigService = SpringExtensionHelper.initAutowireFields(this);
	}
	
	public static synchronized SysConfigHelper getInstance(){
		if (INSTANCE == null) {
			INSTANCE = new SysConfigHelper();
		}
		return INSTANCE;
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
	 * @date   创建时间：2017年2月8日
	 * @author 作者：xds
	 */
	public static boolean saveAndCommit(){
		AssetSysConfig sysConfig = new AssetSysConfig();
		sysConfig.setConfigCode("BILL_COMMIT_MODE");
		sysConfig.setConfigType("SYS_CONFIG");
		List<AssetSysConfig> list = getInstance().sysConfigService.getAssetSysConfigList(sysConfig);
		if(list != null && !list.isEmpty()){
			AssetSysConfig config = list.get(0);
			if("1".equals(config.getConfigValue())){
				return true;
			}
		}
		return false;
	}
	
	public List<AssetSysConfig> getSysConfig(String configCode, String configType){
		AssetSysConfig sysConfig = new AssetSysConfig();
		sysConfig.setConfigCode(configCode);
		sysConfig.setConfigType(configType);
		return sysConfigService.getAssetSysConfigList(sysConfig);
	}

}
