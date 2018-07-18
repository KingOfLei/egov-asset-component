package com.bosssoft.asset.etl.core.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.vfs.FileObject;
import org.apache.log4j.spi.LoggingEvent;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseFactory;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.GenericDatabaseMeta;
import org.pentaho.di.core.database.PartitionDatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.logging.CentralLogStore;
import org.pentaho.di.core.logging.Log4jBufferAppender;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LogMessage;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.variables.Variables;
import org.pentaho.di.core.vfs.KettleVFS;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.deletefolders.JobEntryDeleteFolders;
import org.pentaho.di.trans.RowProducer;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.injector.InjectorMeta;
import org.pentaho.di.trans.steps.textfileoutput.TextFileOutputMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.asset.etl.core.EtlConst;
import com.bosssoft.asset.etl.core.Launcher;
import com.bosssoft.asset.etl.entity.AssetEtlLog;
import com.bosssoft.asset.etl.entity.DetailHeaders;
import com.bosssoft.asset.etl.entity.ReportDetail;
import com.bosssoft.asset.etl.entity.TransResult;
import com.bosssoft.egov.asset.common.PropertiesHelper;
import com.bosssoft.egov.asset.common.idgenerator.ComponetIdGen;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.JsonUtilsExt;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.egov.asset.common.util.PathUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;
import com.bs.common.MyJobEntryListener;

/** 
 *
 * @ClassName   类名：KettleHelper 
 * @Description 功能说明：
 * <p>
 * TODO
 *</p>
 ************************************************************************
 * @date        创建日期：2016年4月15日
 * @author      创建人：谢德寿
 * @version     版本号：V1.0
 *<p>
 ***************************修订记录*************************************
 * 
 *   2016年4月15日   谢德寿   创建该类功能。
 *
 ***********************************************************************
 *</p>
 */
public class KettleHelper {
	
	private static Logger logger = LoggerFactory.getLogger(Launcher.class);

	
	public static final int LIST_TYPE_TAB = 1; //获取所有表
	public static final int LIST_TYPE_VIES = 2; //获取所有视图
	public static final int LIST_TYPE_ALL = 0; //获取所有变及视图
	
	static {
		initKettle();
	}
	
	public static void initKettle(){
		try {
			if (!KettleEnvironment.isInitialized()){
			    //重新设置kettle 加载插件目录文件
				//默认格式 plugins,本地路径
		        System.setProperty(Const.PLUGIN_BASE_FOLDERS_PROP, (Const.isEmpty(Const.getDIHomeDirectory())?"":Const.getDIHomeDirectory() + Const.FILE_SEPARATOR  + "plugins,") + Const.getKettleDirectory() + Const.FILE_SEPARATOR + "plugins," + PathUtil.getWebRootPath() + Const.FILE_SEPARATOR + "plugins");
				KettleEnvironment.init(false);
			     
			}
		} catch (KettleException e) {
		
		}
	}
	
	
	public static String[] getTableList(String name, String type, String host, String db, String port, String user, String pass){
		return getTableList(buildDatabaseMeta(name, type, host, db, port, user, pass));
	}
	
	public static String[] getTableList(String name, String type, String access, String host, String db, String port, String user, String pass){
		return getTableList(buildDatabaseMeta(name, type, access, host, db, port, user, pass));
	}
	
	/**
	 * 
	 * <p>函数名称：  getTableList      </p>
	 * <p>功能说明： 获取所有表名
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param databaseMeta
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static String[] getTableList(DatabaseMeta databaseMeta){
        return getTableOrViewList(databaseMeta, KettleHelper.LIST_TYPE_TAB);
	}
	
	public static String[] getViewList(DatabaseMeta databaseMeta){
		return getTableOrViewList(databaseMeta, KettleHelper.LIST_TYPE_VIES);
	}
	
	public static String[] getViewList(String name, String type, String access, String host, String db, String port, String user, String pass){
		return getViewList(buildDatabaseMeta(name, type, access, host, db, port, user, pass));
	}
	
	public static String[] getViewList(String name, String type, String host, String db, String port, String user, String pass){
		return getViewList(buildDatabaseMeta(name, type, host, db, port, user, pass));
	}
	
	public static String[] getTableAndViewList(DatabaseMeta databaseMeta){
		return getTableOrViewList(databaseMeta, KettleHelper.LIST_TYPE_ALL);
	}
	
	public static String[] getTableAndViewList(String name, String type, String access, String host, String db, String port, String user, String pass){
		return getTableAndViewList(buildDatabaseMeta(name, type, host, db, port, user, pass));
	}
	
	public static String[] getTableAndViewList(String name, String type, String host, String db, String port, String user, String pass){
		return getTableAndViewList(buildDatabaseMeta(name, type, host, db, port, user, pass));
	}
	
	public static String[] getTableOrViewList(String name, String type, String host, String db, String port, String user, String pass, int types){
		   return getTableOrViewList(buildDatabaseMeta(name, type, host, db, port, user, pass),types);
		}
	
	public static String[] getTableOrViewList(String name, String type, String access, String host, String db, String port, String user, String pass,int types){
	   return getTableOrViewList(buildDatabaseMeta(name, type, access, host, db, port, user, pass),types);
	}
	
	/**
	 * 
	 * <p>函数名称：getTableOrViewList       </p>
	 * <p>功能说明：获取表或视图列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param databaseMeta
	 * @param type 1 表 2 视图 0 所有
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static String[] getTableOrViewList(DatabaseMeta databaseMeta, int type){
		List<String> list = new ArrayList<String>();
		Database db = new Database(DatabaseFactory.loggingObject, databaseMeta);
		if (databaseMeta.isPartitioned()){
               PartitionDatabaseMeta[] partitioningInformation = databaseMeta.getPartitioningInformation();
               for (int i = 0; i < partitioningInformation.length; i++)
               {
                   try
                   {
                       db.connect(partitioningInformation[i].getPartitionId());
                       if (KettleHelper.LIST_TYPE_ALL == type){
                           list.addAll(Arrays.asList(db.getTablenames()));
                           list.addAll(Arrays.asList(db.getViews()));
                       } else if (KettleHelper.LIST_TYPE_TAB == type){
                    	   list.addAll(Arrays.asList(db.getTablenames()));
                       } else if (KettleHelper.LIST_TYPE_VIES == type) {
                    	   list.addAll(Arrays.asList(db.getViews()));
                       }
                   }
                   catch (KettleException e)
                   {
                      e.printStackTrace();
                   }
                   finally
                   {
                       db.disconnect();
                   }
                   
               }
		   } else {
               try
               {
                   db.connect();
                   if (KettleHelper.LIST_TYPE_ALL == type){
                       list.addAll(Arrays.asList(db.getTablenames()));
                       list.addAll(Arrays.asList(db.getViews()));
                   } else if (KettleHelper.LIST_TYPE_TAB == type){
                	   list.addAll(Arrays.asList(db.getTablenames()));
                   } else if (KettleHelper.LIST_TYPE_VIES == type) {
                	   list.addAll(Arrays.asList(db.getViews()));
                   }               }
               catch (KettleException e)
               {
                   e.printStackTrace();
               }
               finally
               {
                   db.disconnect();
               }
		   }
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * 
	 * <p>函数名称： testConnection       </p>
	 * <p>功能说明： 测试数据库连接
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param dataMeta 数据源meta
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static String testConnection(DatabaseMeta dataMeta){
		return dataMeta.testConnection(); 
	}
	
	/**
	 * 
	 * <p>函数名称：buildDatabaseMeta       </p>
	 * <p>功能说明： 创建数据源Meta 已Native访问方式
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name 名称
	 * @param type 类型：Oracle、MySQL、SQLite、MSSQLNative等
	 * @param host ip
	 * @param db 实例名
	 * @param port 端口号
	 * @param user 用户
	 * @param pass 密码
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static DatabaseMeta buildDatabaseMeta(String name, String type, String host, String db, String port, String user, String pass){
	
	   return buildDatabaseMeta(name, type, "Native", host, db, port, user, pass);
	}
	
	/**
	 * 
	 * <p>函数名称：  buildDatabaseMeta      </p>
	 * <p>功能说明： 创建数据源Meta
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name 名称
	 * @param type 类型：Oracle、MySQL、SQLite、MSSQLNative等
	 * @param access 访问方式：Native(默认)、ODBC、OCI、JNDI等
	 * @param host ip
	 * @param db 实例名 databaseName
	 * @param port 端口号
	 * @param user 用户
	 * @param pass 密码
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static DatabaseMeta buildDatabaseMeta(String name, String type, String access, String host, String db, String port, String user, String pass){
		DatabaseMeta meta = new DatabaseMeta();
		meta.setValues(name, type, access, host, db, port, user, pass);
		meta.addOptions();
		return meta;
	}
	
	/**
	 * 
	 * <p>函数名称：buildDatabaseMeta        </p>
	 * <p>功能说明：接口直接获取
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param databaseInterface
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static DatabaseMeta buildDatabaseMeta(String name, DatabaseInterface databaseInterface){
		DatabaseMeta meta = new DatabaseMeta();
        meta.setDatabaseInterface(databaseInterface);
        meta.setName(name);
		return meta;
	}
	
	/**
	 * 
	 * <p>函数名称：buildDatabaseMeta        </p>
	 * <p>功能说明：根据参数获取数据连接
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name
	 * @param jdbcUrl
	 * @param driverClassName
	 * @param userName
	 * @param password
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static DatabaseMeta buildDatabaseMeta(String name, String jdbcUrl,String driverClassName,String userName, String password ){
		return buildDatabaseMeta(name, getDefaultDatabaseInterface(jdbcUrl, driverClassName, userName, password));
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name 名称
	 * @param type 类型：Oracle、MySQL、SQLite、MSSQLNative等
	 * @param access 访问方式：Native(默认)、ODBC、OCI、JNDI等
	 * @param host ip
	 * @param db 实例名
	 * @param port 端口号
	 * @param user 用户
	 * @param pass 密码
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static String testConnection(String name, String type, String access, 
			String host, String db, String port, String user, String pass) {
		return testConnection(buildDatabaseMeta(name, type, access, host, db,
				port, user, pass));
	}
	
	public static String testConnection(String name, String type, 
			String host, String db, String port, String user, String pass) {
		return testConnection(buildDatabaseMeta(name, type, host, db,
				port, user, pass));
	}
	
	/**
	 * 
	 * <p>函数名称： getDatabase       </p>
	 * <p>功能说明： 获取db
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param databaseMeta
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static Database getDatabase(DatabaseMeta databaseMeta){
		Database db = new Database(DatabaseFactory.loggingObject, databaseMeta);
		try {
			db.connect(); //连接
		} catch (KettleDatabaseException e) {
			e.printStackTrace();
		}
		return db;
	}
	
	/**
	 * 
	 * <p>函数名称：  getDatabase      </p>
	 * <p>功能说明： 获取db
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name 名称
	 * @param type 类型：Oracle、MySQL、SQLite、MSSQLNative等
	 * @param host ip
	 * @param db 实例名
	 * @param port 端口号
	 * @param user 用户
	 * @param pass 密码
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static Database getDatabase(String name, String type, String host, String db, String port, String user, String pass){
		return getDatabase(buildDatabaseMeta(name, type, host, db, port, user, pass));
	}
	
	/**
	 * 
	 * <p>函数名称：  getDatabase      </p>
	 * <p>功能说明： 获取db
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param name 名称
	 * @param type 类型：Oracle、MySQL、SQLite、MSSQLNative等
	 * @param access 访问方式：Native(默认)、ODBC、OCI、JNDI等
	 * @param host ip
	 * @param db 实例名
	 * @param port 端口号
	 * @param user 用户
	 * @param pass 密码
	 * @return
	 *
	 * @date   创建时间：2015-11-11
	 * @author 作者：谢德寿
	 */
	public static Database getDatabase(String name, String type, String access, String host, String db, String port, String user, String pass){
		return getDatabase(buildDatabaseMeta(name, type, access, host, db, port, user, pass));
	}
       
	private static final ThreadLocal<SimpleDateFormat> LOCAL_SIMPLE_DATE_PARSER = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
     	}
    };
	
	/**
	 * 
	 * <p>函数名称： getLogText       </p>
	 * <p>功能说明：根据日志ChannelID 获取其错误日志新消息
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param LogChannelId 日志ChannelID 
	 * @param isError true 只显示错误日志  false 全部日志类型(仅仅是 message 无时间和对象)
	 * @return
	 *
	 * @date   创建时间：2013-5-29
	 * @author 作者：谢德寿
	 */
	public static StringBuffer getLogText(String LogChannelId,boolean isError){
		 StringBuffer strlog = new StringBuffer(1000);
		 Log4jBufferAppender app =  CentralLogStore.getAppender();
		 List<LoggingEvent> events = app.getLogBufferFromTo(LogChannelId, true, 0, app.getLastBufferLineNr());
		 for (LoggingEvent event : events) {
			 Object object = event.getMessage();
			 String dateTimeString = "";
			 dateTimeString = LOCAL_SIMPLE_DATE_PARSER.get().format(new Date(event.timeStamp)) + " - ";
			 if (object instanceof LogMessage) {
				 LogMessage message = (LogMessage) object;
				 
				//只显示错误类型 
				 if (!isError || (isError && message.isError())){// 判断是否是错误信息
					 strlog.append(dateTimeString);
			         if (message.getSubject() != null) {
			        	 strlog.append(message.getSubject());
			            if (message.getCopy() != null) {
			            	strlog.append(("." + message.getCopy()));
			            }
			            strlog.append(" - ");
			            strlog.append(message.getMessage() + Const.CR);
			          }
				   }
			 }
		 }
		return strlog;
	} 
	
	public static void writeToLog(String fileName,String LogChannelIdOrContext) throws Exception{
		writeToLog(fileName, LogChannelIdOrContext, true);
	}
	/**
	 * 
	 * <p>函数名称：  writeToLog      </p>
	 * <p>功能说明： 将运行详细日志 写入日志文件中
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param fileName 文件名
	 * @param LogChannelIdOrContext logCannelId 或者需要输出的文字
	 * @param isLogCannelId 是否是logCannelId  不是 直接写文本
	 * @throws Exception
	 *
	 * @date   创建时间：2013-7-11
	 * @author 作者：谢德寿
	 */
	public static void writeToLog(String fileName,String LogChannelIdOrContext,boolean isLogCannelId) throws Exception{
		 FileObject fileObject = KettleVFS.getFileObject(fileName);
		 createParentFolder(fileName);
		 OutputStream out = new BufferedOutputStream(KettleVFS.getOutputStream(fileObject, true),5000);
		 StringBuffer line = new StringBuffer();
		 try{
			 if (isLogCannelId){
				 Log4jBufferAppender app =  CentralLogStore.getAppender();
				 List<LoggingEvent> events = app.getLogBufferFromTo(LogChannelIdOrContext, true, 0, app.getLastBufferLineNr());
				 for (LoggingEvent event : events) {
					 line.delete(0, line.length());
					 Object object = event.getMessage();
					 String dateTimeString = "";
					 dateTimeString = LOCAL_SIMPLE_DATE_PARSER.get().format(new Date(event.timeStamp)) + " - ";
					 if (object instanceof LogMessage) {
						 LogMessage message = (LogMessage) object;
						 line.append(dateTimeString);
				         if (message.getSubject() != null) {
				        	 line.append(message.getSubject());
				            if (message.getCopy() != null) {
				            	line.append(("." + message.getCopy()));
				            }
				            line.append(" - ");
				            line.append(message.getMessage() + Const.CR);
				          }
						 out.write(line.toString().getBytes(Charset.forName("UTF-8")));
					 }
				  }
			} else {
				out.write(StringUtilsExt.toString(LogChannelIdOrContext).getBytes(Charset.forName("UTF_8")));
			}
		}
        catch (Exception e) {
			throw new Exception(e);
		}
		finally{
			out.close();
			out = null;
			fileObject.close();
			fileObject = null;
		}
	}

	/**
	 * 
	 * <p>函数名称：createParentFolder        </p>
	 * <p>功能说明：创建父类文件夹
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param filename
	 * @throws Exception
	 *
	 * @date   创建时间：2013-7-11
	 * @author 作者：谢德寿
	 */
	public static void createParentFolder(String filename) throws Exception
	{
		FileObject parentfolder=null;
		try
		{
    		parentfolder=KettleVFS.getFileObject(filename).getParent();	    		
    		if(parentfolder.exists()){
    			;
    		}else{
    			parentfolder.createFolder();
    		}
		} finally {
         	if ( parentfolder != null ){
         		try  {
         			parentfolder.close();
         		}
         		catch ( Exception ex ) {};
         	}
         }	
	}

    
	/**
	 * 
	 * <p>函数名称： deleteDir      </p>
	 * <p>功能说明： 删除指定文件夹以及其下级文件
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param tempFolderName
	 * @throws KettleException
	 *
	 * @date   创建时间：2013-6-7
	 * @author 作者：谢德寿
	 */
	public static void deleteDir(String[] tempFolderNames) throws KettleException{
		JobEntryDeleteFolders deleteFolder = new JobEntryDeleteFolders(" delete tempFolder ");
		deleteFolder.arguments = tempFolderNames;//设置文件夹
		deleteFolder.setLogLevel(LogLevel.NOTHING);
		deleteFolder.setParentJob(new Job());
		deleteFolder.execute(new Result(),1);
	}
	
	/**
	 * 
	 * @param from
	 * @param moveTo
	 * @return
	 * @throws IOException
	 */
	public static boolean copyFile(String from,String moveTo) throws IOException{
		FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;
	    try {  
	        inStream = new FileInputStream(from);
	        outStream = new FileOutputStream(moveTo);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);
	        return true;
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	    	inStream.close();
	    	in.close();
	    	outStream.close();
	    	out.close(); 
	    }  
		return false;
	}
	
	public static VariableSpace createVariableSpace(Map<String, Object> params){
		VariableSpace var = new Variables();
		for(String key : params.keySet()){
			var.setVariable(key, StringUtilsExt.toString(params.get(key)));
		}
		return var;
	}
	
	/**
	 *  返回默认数据库连接属性
	 * @return
	 */
	public static Properties initProperties(){
		Properties	attributes = new Properties();
		attributes.put("USE_POOLING", "N");
		attributes.put("IS_CLUSTERED", "N");
		attributes.put("IS_CLUSTERED", "N");
		attributes.put("SUPPORTS_BOOLEAN_DATA_TYPE", "N");
		attributes.put("FORCE_IDENTIFIERS_TO_UPPERCASE", "N");
		attributes.put("FORCE_IDENTIFIERS_TO_LOWERCASE", "N");
		attributes.put("QUOTE_ALL_FIELDS", "N");
		return attributes;
	}
	
	public static Trans createTrans(List<StepMeta> stepMetaList,List<DatabaseMeta> databaseMetaList){
        TransMeta transMeta = new TransMeta();
        for(DatabaseMeta databaseMeta : databaseMetaList){
        	databaseMeta.copyVariablesFrom(transMeta);
        	transMeta.addOrReplaceDatabase(databaseMeta);
        }
        int i = 0;
    	StepMeta preStepMeta = null;
        for(StepMeta stepMeta : stepMetaList){
        	transMeta.addStep(stepMeta);//加入meta
        	if(i != 0 ){
        		TransHopMeta hopMeta = new TransHopMeta(preStepMeta, stepMeta);
        		transMeta.addTransHop(hopMeta);
        	}
        	preStepMeta = stepMeta;
        	i++;
        }
        Trans trans = new Trans(transMeta);
        
        return trans;
	}
	
	public static Trans createTrans(List<StepMeta> stepMetaList){
		return createTrans(stepMetaList, new ArrayList<DatabaseMeta>());
	}
	
	public static StepMeta createStepMeta(StepMetaInterface stepMetaInterface,String stepName){
		PluginRegistry registry = PluginRegistry.getInstance();
		String pid = registry.getPluginId(StepPluginType.class,stepMetaInterface);
		return new StepMeta(pid, stepName, stepMetaInterface);
	}
	
	public static StepMeta createStepMeta(StepMetaInterface stepMetaInterface){
		return createStepMeta(stepMetaInterface, "tmp_" + getRandomUUID());
	}

	/**
	 * 取得uuid
	 * @return uuid串
	 */
	public static String getRandomUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24);
	}
	
	
	public static TransResult runTrans(TransMeta transMeta, Map<String,Object> params){
		return runTrans(transMeta, null, params);
	}		
	/**
	 * 
	 * <p>函数名称：  runTrans      </p>
	 * <p>功能说明：运行Trans
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param trans
	 * @param params 变量
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransResult runTrans(TransMeta transMeta, List<DatabaseMeta> databaseMetaList,Map<String,Object> params){		
		logger.debug("开始执行转换...{}...",transMeta.getName());
		AssetEtlLog log = new AssetEtlLog();
		log.setStartTime(DateUtilsExt.getNowDateTime());
		TransResult result = new TransResult();
		try {
			VariableSpace varSpace = mapToVariable(params);
			varSpace.setVariable("SYSTEM_CLASSNAME", PropertiesHelper.getProperty("jdbc.classname","oracle.jdbc.driver.OracleDriver"));
			varSpace.setVariable("SYSTEM_URL", PropertiesHelper.getString("jdbc.url"));
			varSpace.setVariable("SYSTEM_USERNAME", PropertiesHelper.getString("jdbc.username"));
			varSpace.setVariable("SYSTEM_PASSWORD", PropertiesHelper.getString("jdbc.password"));
			transMeta.copyVariablesFrom(varSpace);
			DatabaseMeta defaultData = EtlHelper.getDefaultDatabaseMeta();
			defaultData.copyVariablesFrom(transMeta);
			transMeta.addOrReplaceDatabase(defaultData);
			DatabaseMeta systemData = EtlHelper.getDefaultDatabaseMeta("System_data");
			systemData.copyVariablesFrom(transMeta);
			transMeta.addOrReplaceDatabase(systemData);
			//加入自定义的
			if(databaseMetaList != null){
				for(DatabaseMeta databaseMeta : databaseMetaList ){
					databaseMeta.copyVariablesFrom(transMeta);
					transMeta.addOrReplaceDatabase(databaseMeta);
				}				
			}
			Trans trans = new Trans(transMeta);
			trans.copyVariablesFrom(varSpace);
			trans.prepareExecution(null);
		//	trans.addTransListener(new TransListener());			
			trans.startThreads();
			trans.waitUntilFinished();
			result.setException(trans.getResult().getNrErrors() > 0);
			result.setMessage("转换成功");
			log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_SUCCESS);
			if(result.isException()){
				//获取日志
				log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_ERROR);
				result.setMessage(getLogText(trans.getLogChannelId(), true).toString());
			}	
			result.setResultFile(trans.getVariable(EtlConst.ETL_OUT_FULL_PATH));						
		} catch (KettleException e) {
			logger.debug("执行转换...{}..异常..{}..",transMeta.getName(),e.getMessage());			
			result.setException(true);
			result.setMessage("执行转换...{0}..异常..{1}..",transMeta.getName(),e.getMessage());
			result.setDevMessage("执行转换...{0}..异常..{1}..",transMeta.getName(),e.getMessage());
			log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_ERROR);
			e.printStackTrace();
			//throw new BusinessException("ETL-000","转换异常：" + e.getMessage());
		} finally {
			logger.debug("结束执行转换...{}...",transMeta.getName());
			//记录日志
			String endTime = DateUtilsExt.getNowDateTime();
			log.setAppId(WebApplicationContext.getContext().getAppID());
			log.setId(ComponetIdGen.newWKID());
			log.setConfigCode(MapUtilsExt.getString(params, "__CONFIG_CODE__"));
			log.setConfigName(MapUtilsExt.getString(params, "__CONFIG_NAME__"));
			log.setUserCode(MapUtilsExt.getString(params, EtlConst.ETL_USER_ATTR_USER_CODE));
			log.setUserName(MapUtilsExt.getString(params, EtlConst.ETL_USER_ATTR_USER_NAME));
			log.setIp(MapUtilsExt.getString(params, EtlConst.ETL_USER_ATTR_IP));
			log.setEndTime(endTime);
			log.setReqParams(JsonUtilsExt.toJson(params));
			log.setException(result.getMessage());
			KettleLogUtil.saveLog(log);
		}
		
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：   runTrans     </p>
	 * <p>功能说明： 运行ktr文件
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param transFileName
	 * @param params
	 * @throws KettleXMLException
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransResult runTrans(String transFileName, Map<String,Object> params){		
		try {
			TransMeta transMeta = new TransMeta(transFileName);
			return runTrans(transMeta, params);			
		} catch (KettleXMLException e) {
			e.printStackTrace();
			throw new BusinessException("ETL-001","转换异常：" + e.getMessage());
		}
	}
	
	public static TransResult runTrans(String transFileName, Map<String,Object> params,List<DatabaseMeta> databaseMetaList){		
		try {
			TransMeta transMeta = new TransMeta(transFileName);
			return runTrans(transMeta, databaseMetaList, params);			
		} catch (KettleXMLException e) {
			e.printStackTrace();
			throw new BusinessException("ETL-001","转换异常：" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * <p>函数名称： runTrans       </p>
	 * <p>功能说明：根据节点关系列表进行任务运行
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param hopMetas
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年9月15日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransResult runTrans(String transName,List<TransHopMeta> hopMetas, Map<String,Object> params){
		try {
			TransMeta transMeta = new TransMeta();
			transMeta.setName(transName);
			for(TransHopMeta hopMeta : hopMetas){
				//此处注意 !! 不建议使用 addOrReplaceStep 方法，此方法若步骤存在，则去替换，
				//其替换的原理是  访问当前步骤StepMetaInterface的clone 方法
				//若组件xxxMeta 的clone，未对所有参数进行拷贝的话，有些属性会丢失!!!
				//故不建议使用!!改用判断步骤是否已加入来新步骤信息
				//不存在加入
				if(transMeta.indexOfStep(hopMeta.getFromStep()) < 0){
				   transMeta.addStep(hopMeta.getFromStep());
				}				
				if(transMeta.indexOfStep(hopMeta.getToStep()) < 0){
				  transMeta.addStep(hopMeta.getToStep());
				}
				if(transMeta.indexOfTransHop(hopMeta) < 0){
				   transMeta.addTransHop(hopMeta);	
				}
			}
			return runTrans(transMeta, params);
		} catch(Exception e){
			e.printStackTrace();
			throw new BusinessException("ETL-002","转换异常：" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * <p>函数名称：  runJob      </p>
	 * <p>功能说明： 运行任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param jobMeta
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2018年1月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransResult runJob(JobMeta jobMeta, List<DatabaseMeta> databaseMetaList, Map<String,Object> params){
		logger.debug("开始执行任务...{}...",jobMeta.getName());
		AssetEtlLog log = new AssetEtlLog();
		log.setStartTime(DateUtilsExt.getNowDateTime());
		TransResult result = new TransResult();
		//转换参数
        try {
        	VariableSpace varSpace = mapToVariable(params);
            jobMeta.copyVariablesFrom(varSpace);
            
            //设置数据源 由于job无法直接注入其嵌套的 transMeta 数据源信息 故直接使用自定义的 监听类进行注入
            MyJobEntryListener jobEntryListener = new MyJobEntryListener();
            DatabaseMeta defaultData = EtlHelper.getDefaultDatabaseMeta();
    		defaultData.copyVariablesFrom(jobMeta);
    		jobMeta.addOrReplaceDatabase(defaultData);
    		jobEntryListener.addReplaceDatabaseMetaList(defaultData);
    		DatabaseMeta systemData = EtlHelper.getDefaultDatabaseMeta("System_data");
    		systemData.copyVariablesFrom(jobMeta);
    		jobEntryListener.addReplaceDatabaseMetaList(systemData);
            if(databaseMetaList != null){
            	for(DatabaseMeta databaseMeta : databaseMetaList ){
            		jobMeta.addOrReplaceDatabase(databaseMeta);
            		jobEntryListener.addReplaceDatabaseMetaList(databaseMeta);
            	}
            }
    		//设置数据源-结束		
    		
            Job job = new Job(jobMeta.getRepository(), jobMeta);
            job.addJobEntryListener(jobEntryListener);
            job.shareVariablesWith(varSpace);
    		job.setInteractive(true);//共享监听在job组件中(子job同享)
    		job.start();
    		job.waitUntilFinished();
    		result.setException(job.getResult().getNrErrors() > 0);
    		result.setMessage("转换成功");
    		log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_SUCCESS);
			if(result.isException()){
				//获取日志
				log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_ERROR);
				result.setMessage(getLogText(job.getLogChannelId(), true).toString());
			}
			
			result.setResultFile(job.getVariable(EtlConst.ETL_OUT_FULL_PATH));						
		} catch (Exception e) {
			logger.debug("执行任务...{}..异常..{}..",jobMeta.getName(),e.getMessage());			
			result.setException(true);
			result.setMessage("执行任务...{0}..异常..{1}..",jobMeta.getName(),e.getMessage());
			result.setDevMessage("执行任务...{0}..异常..{1}..",jobMeta.getName(),e.getMessage());
			log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_ERROR);
			e.printStackTrace();
		} finally {
			logger.debug("结束执行任务...{}...",jobMeta.getName());
			//记录日志
			String endTime = DateUtilsExt.getNowDateTime();
			log.setAppId(WebApplicationContext.getContext().getAppID());
			log.setId(ComponetIdGen.newWKID());
			log.setConfigCode(MapUtilsExt.getString(params, "__CONFIG_CODE__"));
			log.setConfigName(MapUtilsExt.getString(params, "__CONFIG_NAME__"));
			log.setUserCode(MapUtilsExt.getString(params, EtlConst.ETL_USER_ATTR_USER_CODE));
			log.setUserName(MapUtilsExt.getString(params, EtlConst.ETL_USER_ATTR_USER_NAME));
			log.setIp(MapUtilsExt.getString(params, EtlConst.ETL_USER_ATTR_IP));
			log.setEndTime(endTime);
			log.setReqParams(JsonUtilsExt.toJson(params));
			log.setException(result.getMessage());
			KettleLogUtil.saveLog(log);
		}
        
        return result;
		
	}
	
	public static TransResult runJob(String jobFileName,Map<String,Object> params){
		try {
			JobMeta jobMeta = new JobMeta(jobFileName,null);
			return runJob(jobMeta, new ArrayList<DatabaseMeta>(0), params);
		} catch (KettleXMLException e) {
			e.printStackTrace();
			throw new BusinessException("ETL-001","job执行异常：" + e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * <p>函数名称： runJob       </p>
	 * <p>功能说明： 运行任务
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param jobFileName
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2018年1月13日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransResult runJob(String jobFileName, List<DatabaseMeta> databaseMetaList,Map<String,Object> params){
		try {
			JobMeta jobMeta = new JobMeta(jobFileName,null);
			return runJob(jobMeta, databaseMetaList, params);
		} catch (KettleXMLException e) {
			e.printStackTrace();
			throw new BusinessException("ETL-001","job执行异常：" + e.getMessage());
		}
		
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static VariableSpace mapToVariable(Map<String, Object> params){
		VariableSpace var = new Variables();
		if(params != null){
	            for (Iterator<Entry<String, Object>>  iter = params.entrySet().iterator(); iter.hasNext();) {
	                Entry<String, Object> entry = iter.next();
	                String key = entry.getKey();
	                var.setVariable(key, MapUtilsExt.getString(params, key));
	            }
	     }
		
		return var;
	}
	/**
	 *  返回默认数据库连接属性
	 * @return
	 */
	private static Properties InitProperties(){
		Properties	attributes = new Properties();
		attributes.put("USE_POOLING", "N");
		attributes.put("IS_CLUSTERED", "N");
		attributes.put("IS_CLUSTERED", "N");
		attributes.put("SUPPORTS_BOOLEAN_DATA_TYPE", "N");
		attributes.put("FORCE_IDENTIFIERS_TO_UPPERCASE", "N");
		attributes.put("FORCE_IDENTIFIERS_TO_LOWERCASE", "N");
		attributes.put("QUOTE_ALL_FIELDS", "N");
		return attributes;
	}
	
	/**
	 * 
	 * <p>函数名称： getDefaultDatabaseInterface       </p>
	 * <p>功能说明：获取通用数据配置接口
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param jdbcUrl
	 * @param driverClassName
	 * @param user
	 * @param password
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static DatabaseInterface getDefaultDatabaseInterface(String jdbcUrl,String driverClassName,String user, String password ){
		
		return getDefaultDatabaseInterface(jdbcUrl, driverClassName, user, password, "");
	}
	
	/**
	 * 
	 * <p>函数名称：getDefaultDatabaseInterface        </p>
	 * <p>功能说明：获取通用数据连接
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param jdbcUrl 
	 * @param driverClassName 
	 * @param user
	 * @param password
	 * @param jndi jndi名称 为空时 使用jdbc
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static DatabaseInterface getDefaultDatabaseInterface(String jdbcUrl,String driverClassName,String user, String password, String jndi){
		GenericDatabaseMeta getData = new GenericDatabaseMeta();
		Properties pro = InitProperties();
		if(StringUtilsExt.isBlank(jndi)){
			pro.put("CUSTOM_URL", jdbcUrl);
			pro.put("CUSTOM_DRIVER_CLASS", driverClassName);
			getData.setAccessType(DatabaseMeta.TYPE_ACCESS_NATIVE);//jdbc
		}  else {
			getData.setDatabaseName(jndi);
			getData.setAccessType(DatabaseMeta.TYPE_ACCESS_JNDI);//JNDI
		}
		getData.setAttributes(pro);
		getData.setPassword(password);
		getData.setUsername(user);
		getData.setPluginId("GENERIC");
		getData.setPluginName("Generic database");
		
		return getData;
	}
	
	@SuppressWarnings("rawtypes")
	public static TransResult runExportExcelTask(ReportDetail exportData,Map<String,Object> params){
		logger.debug("开始执行转换...{}...","excel导出");
		AssetEtlLog log = new AssetEtlLog();
		log.setStartTime(DateUtilsExt.getNowDateTime());
		TransResult result = new TransResult();
		try {
			VariableSpace varSpace = mapToVariable(params);
			varSpace.setVariable("SYSTEM_CLASSNAME", PropertiesHelper.getProperty("jdbc.classname","oracle.jdbc.driver.OracleDriver"));
			varSpace.setVariable("SYSTEM_URL", PropertiesHelper.getString("jdbc.url"));
			varSpace.setVariable("SYSTEM_USERNAME", PropertiesHelper.getString("jdbc.username"));
			varSpace.setVariable("SYSTEM_PASSWORD", PropertiesHelper.getString("jdbc.password"));
			TransMeta transMeta = StepMetaHelper.getExportExcelTransMeta(exportData);
			transMeta.copyVariablesFrom(varSpace);
			transMeta.setName("excel导出");
			Trans trans = new Trans(transMeta);
			trans.addTransListener(new TransListener());
			trans.copyVariablesFrom(varSpace);
			trans.prepareExecution(null);
			//重点 注入记录集
			RowProducer rp = trans.addRowProducer("数据集注入", 0);
	        trans.startThreads();
	        //注入
	        int fieldSize = exportData.getDetailHeaders().size();
        	RowMetaInterface rowMeta = new RowMeta();	        
	        for(int i=0; i<fieldSize; i++){
	        	DetailHeaders header = exportData.getDetailHeaders().get(i);
//	        	rowMeta.addValueMeta(new ValueMeta(header.getName(),ValueMetaInterface.TYPE_STRING));
	        	rowMeta.addRowMeta(setExcelRowMetaInterface(header));
	        }
	        for(Map dataMap : exportData.getDetailDatas()){
	        	Object[] data = new Object[rowMeta.size()];
	        	int i = 0;
	        	for(String fileName : rowMeta.getFieldNames()){
	        		data[i] = MapUtilsExt.getString(dataMap, fileName);
	        		i++;
	        		
	        	}
	        	rp.putRow(rowMeta, data);
	        }
	        rp.finished();	        
			trans.waitUntilFinished();
			result.setException(trans.getResult().getNrErrors() > 0);
			result.setMessage("转换成功");
			if(result.isException()){
				//获取日志
				result.setMessage(getLogText(trans.getLogChannelId(), true).toString());
			}
			log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_SUCCESS);
			result.setResultFile(trans.getVariable(EtlConst.ETL_OUT_FULL_PATH));
			return result;
		} catch (KettleException e) {
			e.printStackTrace();
			logger.debug("执行转换...{}..异常..{}..","excel导出",e.getMessage());
			result.setException(true);
			result.setMessage("执行转换...{0}..异常..{1}..","excel导出",e.getMessage());
			log.setStatus(EtlConst.ETL_CONFIG_LOG_STATUS_ERROR);
			//throw new BusinessException("ETL-000","转换异常：" + e.getMessage());
		}finally {
			logger.debug("结束执行转换...{}...","excel导出");
			//记录日志
			String endTime = DateUtilsExt.getNowDateTime();
			log.setAppId(WebApplicationContext.getContext().getAppID());
			log.setId(ComponetIdGen.newWKID());
			log.setConfigCode("gridExecelExport");
			log.setConfigName("网格数据excel导出");
			log.setUserCode(MapUtilsExt.getString(params, "__userCode__"));
			log.setUserName(MapUtilsExt.getString(params, "__userName__"));
			log.setIp(MapUtilsExt.getString(params, "__ip__"));
			log.setEndTime(endTime);
			log.setReqParams(JsonUtilsExt.toJson(params));
			log.setException(result.getMessage());
			KettleLogUtil.saveLog(log);			
		} 
		
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：  writerDataToFile      </p>
	 * <p>功能说明： 讲数据包写入文件中
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param fileName
	 * @param data
	 *
	 * @date   创建时间：2018年1月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static void writerDataToFile(String fileName, List<Map<String,Object>> data){
		TransMeta transMeta = new TransMeta();
		transMeta.setName(" writerFileFromCds ");
		
		PluginRegistry registry = PluginRegistry.getInstance();    
		
		//数据注入器
		InjectorMeta injectorMeta = new InjectorMeta();
		String injectid = registry.getPluginId(StepPluginType.class, injectorMeta);
		StepMeta injectorStep = new StepMeta(injectid, " injectorStep ", injectorMeta);
		transMeta.addStep(injectorStep);
		
		//文本输出 属性设置
		TextFileOutputMeta tfOutMeta = new TextFileOutputMeta();
		tfOutMeta.setDefault();//先设置默认值
		tfOutMeta.setFileName(fileName);//输出文件名
		tfOutMeta.setSeparator("	");//分隔符
		tfOutMeta.setCreateParentFolder(true);//创建父类文件夹
		tfOutMeta.setDoNotOpenNewFileInit(true);//不事先创建文件
		tfOutMeta.setAddToResultFiles(true);//文件名添加到Result中
		tfOutMeta.setFastDump(true);//不进行格式化 快速输出
		tfOutMeta.setExtension("");
		tfOutMeta.allocate(0);
		
		String FileOutId = registry.getPluginId(StepPluginType.class, tfOutMeta);
		StepMeta textFileOutputStep = new StepMeta(FileOutId, " TextFileOutPut ", tfOutMeta);
		transMeta.addStep(textFileOutputStep);
		
		//创建hop 
		TransHopMeta hop_Inhect_Textoutput = new TransHopMeta(injectorStep, textFileOutputStep);
	    transMeta.addTransHop(hop_Inhect_Textoutput);
	    
	    try{
	    	Trans trans = new Trans(transMeta);
	        trans.prepareExecution(null);
	        
	        //记录注入
	        RowProducer rp = trans.addRowProducer(" injectorStep ", 0);
	        trans.startThreads();//开始进程
	        if(data != null && !data.isEmpty()) {
	        	//创建 RowMetaInterface
	        	//遍历数据
	        	RowMetaInterface rm = craeteRowMetaInterface(data.get(0).keySet());
	        	for(Map<String,Object> mapData : data){
	    			Object[] rowData = new Object[rm.size()];
	        		int i = 0;
	    			for(Entry<String, Object> entry : mapData.entrySet()){
	    				rowData[i++] = entry.getValue();
	        		}
	        		rp.putRow(rm, rowData);
	        	}
	        }	
			rp.finished();
			trans.waitUntilFinished();
	    } catch(KettleException e){
			throw new BusinessException("ETL-001","文件写入执行异常：" + e.getMessage());
	    }
	    
        
	}
	
	/**
	 * 
	 * <p>函数名称：  setExcelRowMetaInterface      </p>
	 * <p>功能说明： 递归遍历
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param header
	 * @return
	 *
	 * @date   创建时间：2017年9月26日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	private static RowMetaInterface setExcelRowMetaInterface(DetailHeaders header){
		RowMetaInterface rowMeta = new RowMeta();
		List<DetailHeaders> headerList  = header.getSubHeaders();
		if(headerList.isEmpty()){
        	rowMeta.addValueMeta(new ValueMeta(header.getName(),ValueMetaInterface.TYPE_STRING));
		} else {
			for(DetailHeaders head : headerList){
				rowMeta.addRowMeta(setExcelRowMetaInterface(head));
			}
		}
		return rowMeta;
	}
	
	public static RowMetaInterface craeteRowMetaInterface(Set<String> fieldStr){
		RowMetaInterface rm = new RowMeta();
		Iterator<String> iterData = fieldStr.iterator();
		//遍历数据集
		while(iterData.hasNext()){
			ValueMetaInterface v = new ValueMeta();
			v.setName(iterData.next());//名字
			v.setType(ValueMetaInterface.TYPE_STRING);//类型
			v.setLength(1000);
			rm.addValueMeta(v);
		}
		return rm;
	}
}
