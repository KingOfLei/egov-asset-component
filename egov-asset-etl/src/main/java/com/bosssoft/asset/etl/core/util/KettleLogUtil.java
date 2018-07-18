package com.bosssoft.asset.etl.core.util;

import com.bosssoft.asset.etl.entity.AssetEtlLog;
import com.bosssoft.asset.etl.service.AssetEtlLogService;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：KettleLogUtil 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月24日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月24日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class KettleLogUtil {
		
	private static AssetEtlLogService logService = WebApplicationContext.getContext().lookup(AssetEtlLogService.class);

	public static void saveLog(AssetEtlLog log){
		// 异步保存日志
		new SaveLogThread(log).start();
	}
	
	public static class SaveLogThread extends Thread{
		private AssetEtlLog log;
		
	    public SaveLogThread(AssetEtlLog log) {
		  this.log = log;
	    }
	    
		@Override
		public void run() {
			logService.addAssetEtlLog(this.log);
		}
	}
}
