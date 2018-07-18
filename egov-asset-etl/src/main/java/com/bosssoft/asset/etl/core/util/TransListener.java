package com.bosssoft.asset.etl.core.util;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;

/** 
*
* @ClassName   类名：TransListener 
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
public class TransListener implements org.pentaho.di.trans.TransListener{

	@Override
	public void transActive(Trans trans) {
		System.out.println("transActive:" + trans.getName());		
	}

	@Override
	public void transIdle(Trans trans) {
		System.out.println("transIdle:" + trans.getName());		
	}

	@Override
	public void transFinished(Trans trans) throws KettleException {
		System.out.println("transFinished:" + trans.getName());		
	}

}
