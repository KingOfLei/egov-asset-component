package com.bosssoft.egov.asset.common.idgenerator;

/** 
*
* @ClassName   类名：ComponetIdGen 
* @Description 功能说明：组件类通用id生成器
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月20日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月20日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ComponetIdGen {
	 private static IdWorker idWorker;
	    
	    private static ComponetIdGen idGen;
	    
	    private ComponetIdGen(){
	    	idWorker = new IdWorker(16, 1);
	    }
	    private static synchronized ComponetIdGen getInstance(){
	    	 if(idGen == null){
	    		 idGen = new ComponetIdGen();
	    	 }
	    	 return idGen;
	    }

	    public static long newWKID(){
	    	return getInstance()._newWKID();
	    }
	    
	    public static long[] newWKID(int batchCount){
	    	return getInstance()._newWKID(batchCount);
	    }
	        
	    private long _newWKID(){
	    	return idWorker.generate();
	    }
	    private long[] _newWKID(int batchCount){
	    	long[] result = new long[batchCount];
	    	for(int i=0;i<batchCount;i++){
	    		result[i] = idWorker.generate();
	    	}	    	
	    	return result;
	    }
}
