package com.bosssoft.egov.asset.di;

import java.io.File;

import com.bosssoft.asset.etl.core.util.EtlHelper;

/** 
*
* @ClassName   类名：DiConsts 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2018年1月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2018年1月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DiConsts {
	
	//转换文件目录
	public static final String DI_ETL_RESOURCE_PATH = EtlHelper.getEtlResourcePath() + "DI" + File.separator;

	//转换文件job地址
	public static final String DI_ETL_JOB_PATH = DiConsts.DI_ETL_RESOURCE_PATH + "Trans_OutActionJob.kjb";
	
	public final static String MultiKey = "E24061C210A97B6DE043B509A8C02304";
}
