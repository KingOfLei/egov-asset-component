package com.bosssoft.asset.etl.comp.handler;

import javax.annotation.Resource;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;

import com.bosssoft.asset.etl.comp.entity.AssetEtlCompConfigDb;
import com.bosssoft.asset.etl.comp.service.AssetEtlCompConfigDbService;
import com.bosssoft.asset.etl.core.util.EtlHelper;
import com.bosssoft.asset.etl.core.util.KettleHelper;
import com.bosssoft.egov.asset.common.util.BooleanUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.extension.Activate;

/** 
*
* @ClassName   类名：DbHandler 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年9月13日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年9月13日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Activate
public class DbHandler extends AbstractHandler implements Handler{

	@Resource
	private AssetEtlCompConfigDbService dbService;		
	
	@Override
	public StepMetaInterface createStepMeta(Long configId) {
		//先获取方法对象
		AssetEtlCompConfigDb config = getConfig(configId);
		if(config == null) return getDefaultStepMeta();
		//判断是输入还是输出
		if("OUT".equalsIgnoreCase(config.getBizType())){
			//导入数据库
			TableOutputMeta stepMeta = new TableOutputMeta();
			stepMeta.setDefault();
			stepMeta.setCommitSize(StringUtilsExt.toString(config.getOutputCommit(), "4000"));
			stepMeta.setTablename(config.getOutputTable());
			//'true', 'on', 'y', 't' or 'yes' (case insensitive) will return true. Otherwise, false is returned
			stepMeta.setCreateTable(BooleanUtilsExt.toBoolean(config.getOutputCrttbl()));
			stepMeta.setDatabaseMeta(getDatabaseMeta(config));
			stepMeta.setExcludeFields(StringUtilsExt.toString(config.getOutputExclude()));
			return stepMeta;
		} else if("IN".equalsIgnoreCase(config.getBizType())){
			//查询数据库
			TableInputMeta stepMeta = new TableInputMeta();
			stepMeta.setDefault();
			stepMeta.setSQL(StringUtilsExt.toString(config.getInputSql(), "SELECT 1 FROM DUAL "));
			stepMeta.setVariableReplacementActive(true);
			stepMeta.setRowLimit(StringUtilsExt.toString(config.getInputRowLimt(), "0"));
			stepMeta.setDatabaseMeta(getDatabaseMeta(config));
			return stepMeta;
		}
		
		return getDefaultStepMeta();
	}
	
	private DatabaseMeta getDatabaseMeta(AssetEtlCompConfigDb configDb){
		//默认_defaultData_
		if (EtlHelper.DEFAULT_DATA.equals(configDb.getDbName())) {
			return EtlHelper.getDefaultDatabaseMeta(EtlHelper.DEFAULT_DATA);
		} else {
			return KettleHelper.buildDatabaseMeta(configDb.getDbName(),
					configDb.getDbType(), configDb.getDbHost(),
					configDb.getDbInst(), configDb.getDbPort(),
					configDb.getDbUser(), configDb.getDbPass());
		}
	}
	
	
	private AssetEtlCompConfigDb getConfig(Long configId){
		AssetEtlCompConfigDb config = new AssetEtlCompConfigDb();
		config.setConfigId(configId);
		config.setStatus("1");
		return dbService.getAssetEtlCompConfigDb(config);		
	}

	@Override
	public HandlerType getHandler() {
		// TODO Auto-generated method stub
		return HandlerType.DB;
	}

}
