package com.bosssoft.asset.etl.core.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.core.database.DatabaseMeta;

import com.bosssoft.asset.etl.core.EtlConst;
import com.bosssoft.egov.asset.common.PropertiesHelper;
import com.bosssoft.egov.asset.common.idgenerator.UUIDUtils;
import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.PathUtil;

/** 
*
* @ClassName   类名：EtlHelper 
* @Description 功能说明： 备注类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class EtlHelper {
	
   public final static String DEFAULT_DATA = "_defaultData_";

   public static DatabaseMeta getDefaultDatabaseMeta(String dbName){
		
	    String jdbcUrl = PropertiesHelper.getString("jdbc.url");
	    String driverClassName = PropertiesHelper.getString("jdbc.classname");
	    String userName = PropertiesHelper.getString("jdbc.username");
	    String password = PropertiesHelper.getString("jdbc.password");
	    return KettleHelper.buildDatabaseMeta(dbName, jdbcUrl, driverClassName, userName, password);
   }
   
   public static DatabaseMeta getDefaultDatabaseMeta(){
	   return getDefaultDatabaseMeta(DEFAULT_DATA);
  }
   
   /**
    * 
    * <p>函数名称：    getWebParams    </p>
    * <p>功能说明：获取项目信息:路径，classes路径 等
    *
    * </p>
    *<p>参数说明：</p>
    * @return
    *
    * @date   创建时间：2018年1月13日
    * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
    */
   public static Map<String,Object> getWebParams(){
		Map<String,Object> params = new HashMap<String,Object>();
        params.put(EtlConst.ETL_WEB_ROOT_PATH, PathUtil.getWebRootPath() + File.separator);
        params.put(EtlConst.ETL_WEB_CLASSES_PATH, PathUtil.getRootClassPath() + File.separator);
        params.put(EtlConst.ETL_FILE_SEPARATOR, File.separator);
        //导出目录
        String outPath = EtlConst.getExportTaskPathDirectory();
        params.put(EtlConst.ETL_EXPORT_TASK_PATH_DIRECTORY, outPath);
        String uuidOutFile = UUIDUtils.getRandomUUID();
        params.put(EtlConst.ETL_OUT_UUID_FILE, uuidOutFile);
        //导入目录
        String inPath = EtlConst.getImportTaskPathDirectory();
        params.put(EtlConst.ETL_IMPORT_TASK_PATH_DIRECTORY, inPath);
        
        //导入时默认生成的文件名
        String uuidFile = EtlConst.getUUIDFileName();
        params.put(EtlConst.ETL_IN_UUID_FILE, uuidFile);        
        
        //全路径 目录+文件名 导入时 内容放入此文件中
        String fullPath = inPath + uuidFile;
        params.put(EtlConst.ETL_IN_FULL_PATH, fullPath);
        FileUtilsExt.mkdirs(outPath,true);
        FileUtilsExt.mkdirs(fullPath,false);
        return params;
	}
   
   /**
    * 
    * <p>函数名称：getEtlResourcePath        </p>
    * <p>功能说明：获取etl资源路径
    *
    * </p>
    *<p>参数说明：</p>
    * @return
    *
    * @date   创建时间：2018年1月14日
    * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
    */
   public static String getEtlResourcePath(){
	   return PathUtil.getFileRecv() + "KETTLE" + File.separator;
   }
}
