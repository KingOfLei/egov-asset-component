package com.bosssoft.egov.asset.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：TaskExecLog 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2018年1月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2018年1月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class TaskExecLog {
	
	private static Logger createlog;	
	private static Logger runlog;	
    public static final String LOGNAME_CREATE = "bosssoft.task.create";
    public static final String LOGNAME_RUN = "bosssoft.task.run";
    public static final int TYPE_CREATE = 0;
    public static final int TYPE_RUN = 1;
	private static TaskExecLog taskExecLog;
	
	public static final synchronized TaskExecLog getInstance(){
    	if (taskExecLog != null)
    		return taskExecLog;
    	taskExecLog = new TaskExecLog();
    	return taskExecLog;
    }
	
	static {
		createlog = LoggerFactory.getLogger(LOGNAME_CREATE);
		
		runlog = LoggerFactory.getLogger(LOGNAME_RUN);
		
	}
	
	public void writeToLog(int type,Object obj, Object... parmas){
			switch (type) {
			case TYPE_CREATE:
				createlog.info(StringUtilsExt.toString(obj),parmas);
				break;
			case TYPE_RUN:
				runlog.info(StringUtilsExt.toString(obj),parmas);
				break;
			}
	}
	    
    public void writeToLog(Object obj){
		writeToLog(TYPE_CREATE,obj);
    }
       
}
