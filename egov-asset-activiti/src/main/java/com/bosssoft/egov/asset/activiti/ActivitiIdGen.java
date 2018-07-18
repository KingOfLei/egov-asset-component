package com.bosssoft.egov.asset.activiti;

import org.activiti.engine.impl.cfg.IdGenerator;

import com.bosssoft.egov.asset.common.idgenerator.IdWorker;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
 *
 * @ClassName   类名：ActivitiIdGen 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年12月14日
 * @author      创建人：jinbiao
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年12月14日   jinbiao   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class ActivitiIdGen implements IdGenerator{
	 private static ActivitiIdGen activitiIdGen;
	    private static IdWorker idWorker;
	    
	    private ActivitiIdGen(){    	
			init();
	    }
	    
	    private void init() {
	    	idWorker = new IdWorker(30L,29L);
		}

		private static synchronized ActivitiIdGen getInstance(){
	    	 if(activitiIdGen == null){
	    		 activitiIdGen = new ActivitiIdGen();    		 
	    	 }
	    	 return activitiIdGen;
	    }
	    
	    public static long newWKID(){
	    	return getInstance()._newWKID();
	    }
	    
	    private long _newWKID(){
	    	return idWorker.generate();
	    }

		@Override
		public String getNextId() {
			return StringUtilsExt.convertNullToString(ActivitiIdGen.newWKID());
		}
}
