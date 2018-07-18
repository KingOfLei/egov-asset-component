package com.bosssoft.asset.etl.core;

import java.io.File;

import com.bosssoft.egov.asset.common.PropertiesHelper;
import com.bosssoft.egov.asset.common.idgenerator.UUIDUtils;
import com.bosssoft.egov.asset.common.util.PathUtil;

/** 
*
* @ClassName   类名：EtlConst 
* @Description 功能说明：常量类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月16日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月16日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public abstract class EtlConst {

	public static final String ETL_USER_INFO = "__ETL_USER_INFO__";
	
	public static final String EXTRA_VAR_FIELD = "extra_var_field";//额外需要添加的变量
	public static final String EXTRA_CONST_FIELD = "extra_const_field";//额外需要添加的常量
		
	//当有导出文件时 导出的文件变量值 
	public static final String ETL_OUT_FILE_NAME = "__ETL_OUT_FILE_NAME__";
	public static final String ETL_OUT_UUID_FILE = "__ETL_OUT_UUID_FILE__";
	public static final String ETL_OUT_FULL_PATH = "__ETL_OUT_FULL_PATH__";
	
	public static final String ETL_IN_FULL_PATH = "__ETL_IN_FULL_PATH__";
	public static final String ETL_IN_UUID_FILE = "__ETL_IN_UUID_FILE__";
	
	/**
	 * 任务状态：等待WAIT、执行中EXECUTING、成功SUCCESS、异常ERROR
	 */
	public static final String ETL_EXPORT_TASK_STATUS_WAIT = "WAIT";
	public static final String ETL_EXPORT_TASK_STATUS_EXECUTING = "EXECUTING";
	public static final String ETL_EXPORT_TASK_STATUS_SUCCESS = "SUCCESS";	
	public static final String ETL_EXPORT_TASK_STATUS_ERROR = "ERROR";
	
	/**
	 * 任务运行状态
	 */
	public static final String ETL_CONFIG_LOG_STATUS_SUCCESS = "SUCCESS";
	public static final String ETL_CONFIG_LOG_STATUS_ERROR = "ERROR";
	
	/**
	 * 变量类型
	 */
	public static final String ETL_CONFIG_ATTR_BIZ_TYPE_VAR= "VAR";
	public static final String ETL_CONFIG_ATTR_BIZ_TYPE_CONST= "CONST";
	
	/**
	 * 用户相关信息
	 */
	
	public static final String ETL_USER_ATTR_USER_ID = "__userId__";
	public static final String ETL_USER_ATTR_USER_CODE = "__userCode__";
	public static final String ETL_USER_ATTR_USER_NAME = "__userName__";
	public static final String ETL_USER_ATTR_USER_PHONE = "__userPhone__";
	public static final String ETL_USER_ATTR_ORG_ID = "__orgId__";
	public static final String ETL_USER_ATTR_ORG_CODE = "__orgCode__";
	public static final String ETL_USER_ATTR_ORG_NAME = "__orgName__";
	public static final String ETL_USER_ATTR_RGN_ID = "__rgnId__";
	public static final String ETL_USER_ATTR_RGN_CODE = "__rgnCode__";
	public static final String ETL_USER_ATTR_RGN_NAME = "__rgnName__";
	public static final String ETL_USER_ATTR_ROLE_CODE = "__roleCode__";
	public static final String ETL_USER_ATTR_ROLE_NAME = "__roleName__";
	public static final String ETL_USER_ATTR_IP = "__ip__";
	
	/**
	 * 统一导出路径
	 */
	public static final String ETL_EXPORT_TASK_PATH_DIRECTORY = "__OUT_FILE_DIR__";
	public static final String ETL_EXPORT_TASK_PATH = PropertiesHelper.getProperty("etl.export.dir") + File.separator;
	
	/**
	 * 导入
	 */
	public static final String ETL_IMPORT_TASK_PATH_DIRECTORY = "__IN_FILE_DIR__";
	public static final String ETL_IMPORT_TASK_PATH = PropertiesHelper.getProperty("etl.import.dir") + File.separator;
		
	/**
	 * 项目信息 路径名称等
	 */
	public static final String ETL_WEB_ROOT_PATH  = "__WEB_ROOT_PATH__"; 
	public static final String ETL_WEB_CLASSES_PATH = "__WEB_CLASSES_PATH__";
	public static final String ETL_FILE_SEPARATOR = "__FILE_SEPARATOR__";
	
	/**
	 * 导出列占位符
	 */
	public static final String ETL_EXPORT_COLUMNS_SQL = "__EXPORT_COLUMNS_SQL__";
	/**
	 * 用户权限、查询条件、排序占位符
	 */
	public static final String ETL_EXPORT_CONDITION_SQL = "__CONDITION_SQL__";
	/**
	 * 查询条件占位符
	 */
	public static final String ETL_EXPORT_WHERE_SQL = "WHERE_SQL";
	/**
	 * 排序占位符
	 */
	public static final String ETL_EXPORT_ORDER_SQL = "ORDER_BY_SQL";
	
	/**
	 * 
	 * <p>函数名称： getExportTaskPathDirectory       </p>
	 * <p>功能说明：获取导出的目录路径 不包含文件名 只包含路径
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getExportTaskPathDirectory(){
		return ETL_EXPORT_TASK_PATH + PathUtil.getDateDirectory();
	}

	
	/**
	 * 
	 * <p>函数名称： getImportTaskPathDirectory       </p>
	 * <p>功能说明： 获取导入文件存放路径
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getImportTaskPathDirectory(){
		return ETL_IMPORT_TASK_PATH + PathUtil.getDateDirectory();
	}
	
	/**
	 * 
	 * <p>函数名称：getUUIDFileName        </p>
	 * <p>功能说明：返回uuid文件名xxxx.tmp
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getUUIDFileName(){
		return UUIDUtils.getRandomUUID() + ".tmp";
	}
}
