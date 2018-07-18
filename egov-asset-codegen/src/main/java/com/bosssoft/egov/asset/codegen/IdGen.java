package com.bosssoft.egov.asset.codegen;

import com.bosssoft.egov.asset.common.idgenerator.IdWorker;

/** 
*
* @ClassName   类名：IdGen 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月2日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月2日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class IdGen {
	
    private static IdWorker idWorker;
    
    private static IdGen idGen;
    
    private IdGen(){
    	idWorker = new IdWorker(6, 7);
    }
    private static synchronized IdGen getInstance(){
    	 if(idGen == null){
    		 idGen = new IdGen();
    	 }
    	 return idGen;
    }

    public static long newWKID(){
    	return getInstance()._newWKID();
    }
        
    private long _newWKID(){
    	return idWorker.generate();
    }
}
