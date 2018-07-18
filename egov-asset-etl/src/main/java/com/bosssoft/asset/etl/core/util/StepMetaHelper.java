package com.bosssoft.asset.etl.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.step.errorhandling.StreamInterface;
import org.pentaho.di.trans.steps.constant.ConstantMeta;
import org.pentaho.di.trans.steps.excelwriter.ExcelWriterStepField;
import org.pentaho.di.trans.steps.excelwriter.ExcelWriterStepMeta;
import org.pentaho.di.trans.steps.getvariable.GetVariableMeta;
import org.pentaho.di.trans.steps.injector.InjectorMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassDef;
import org.pentaho.di.trans.steps.userdefinedjavaclass.UserDefinedJavaClassMeta;

import com.bosssoft.asset.etl.core.EtlConst;
import com.bosssoft.asset.etl.entity.DetailHeaders;
import com.bosssoft.asset.etl.entity.ReportDetail;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bs.custom.trans.zcInferface.zcInferfaceMeta;

/** 
*
* @ClassName   类名：StepMetaHelper 
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
@SuppressWarnings("unchecked")
public class StepMetaHelper {

	private static final String TYPE_TRANS_PROCESS_SOURCE= 
			"private RowMetaInterface tmpRowMeta;\n" +
					"public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException\n" + 
					"{\n" + 
					"  Object[] r = getRow();\n" + 
					"  if (r == null) {\n" + 
					"    setOutputDone();\n" + 
					"    return false;\n" + 
					"  }\n" + 
					"\n" + 
					"    if (first)\n" + 
					"    {\n" + 
					"        //赋值\n" + 
					"        data.outputRowMeta = getInputRowMeta().clone();\n" + 
					"\n" + 
					"        tmpRowMeta = getInputRowMeta().clone();\n" + 
					"        first = false;\n" + 
					"    }\n" + 
					"    Object[] outputData = RowDataUtil.allocateRowData(tmpRowMeta.size());\n" + 
					"    for (int i = 1,size = tmpRowMeta.size(); i <= size ; i++){\n" + 
					"\n" + 
					"         ValueMetaInterface value =  tmpRowMeta.getValueMeta(i-1);\n" + 
					"           value.setType(ValueMetaInterface.TYPE_STRING);\n" + 
					"        ValueMetaInterface toMeta = data.outputRowMeta.getValueMeta(i-1);\n" + 
					"           switch(toMeta.getType()){\n" + 
					"              case ValueMetaInterface.TYPE_STRING    : outputData[i-1] = value.getString(r[i-1]); break;\n" + 
					"              case ValueMetaInterface.TYPE_NUMBER    : outputData[i-1] = value.getNumber(r[i-1]); break;\n" + 
					"              case ValueMetaInterface.TYPE_INTEGER   : outputData[i-1] = value.getInteger(r[i-1]); break;\n" + 
					"              case ValueMetaInterface.TYPE_DATE      : outputData[i-1] = value.getDate(r[i-1]); break;\n" + 
					"              case ValueMetaInterface.TYPE_BIGNUMBER : outputData[i-1] = value.getBigNumber(r[i-1]); break;\n" + 
					"              case ValueMetaInterface.TYPE_BOOLEAN   : outputData[i-1] = value.getBoolean(r[i-1]); break;\n" + 
					"              case ValueMetaInterface.TYPE_BINARY    : outputData[i-1] = value.getBinary(r[i-1]); break;\n" + 
					"              default: throw new KettleValueException(\"不能转换 '\"+value+\"' 成类型  \"+toMeta.getType());\n" + 
					"        }\n" + 
					"    }\n" + 
					"\n" + 
					"    putRow(data.outputRowMeta, outputData);\n" + 
					"\n" + 
					"  return true;\n" + 
					"}";
	/**
	 * 获取配置表内容
	 */
	public static final String RULE_CONFIG_SQL= 
											"SELECT ID AS F_TASK_MX_ID,\n" +
											"       CONFIG_NO AS F_CONFIG_NO,\n" + 
											"       CONFIG_NAME AS F_SRCTYPE,\n" + 
											"       '' AS F_TITLE,\n" + 
											"       SRC_FIELD_NAME AS F_SRCFIELDNAME,\n" + 
											"       SRC_FIELD_TYPE AS F_SRCFIELDTYPE,\n" + 
											"       SRC_FIELD_SIZE AS F_SRCSIZE,\n" + 
											"       DEST_FIELD_NAME AS F_DESTFIELDNAME,\n" + 
											"       DEST_FIELD_TYPE AS F_DESTFIELDTYPE,\n" + 
											"       DEST_FIELD_SIZE AS F_DESTSIZE,\n" + 
											"       CONVERT_TYPE AS F_CONVERTTYPE,\n" + 
											"       CONVERT_TABLE AS F_CONVERTTABLE,\n" + 
											"       CONVERT_ID AS F_CONVERTID,\n" + 
											"       FILTER_TYPE AS F_FILTERTYPE,\n" + 
											"       COMP_FIELD AS F_COMPFIELD,\n" + 
											"       VALUE_FIELD AS F_VALUEFIELD,\n" + 
											"       VALUE AS F_VALUE,\n" + 
											"       VALUE_SRC_FIELD AS F_VALUESRCFIELD\n" + 
											"  FROM ASSET_ETL_RULE_CONFIG\n" + 
											" WHERE ID = '${_CONFIG_ID_}'";
	
	public static StepMetaInterface getUserDefinedJavaClass(){
		UserDefinedJavaClassMeta stepMeta = new UserDefinedJavaClassMeta();
		stepMeta.setDefault();
		stepMeta.setClearingResultFields(false);
		UserDefinedJavaClassDef def = new UserDefinedJavaClassDef(UserDefinedJavaClassDef.ClassType.TRANSFORM_CLASS, "Processor", TYPE_TRANS_PROCESS_SOURCE); 
		stepMeta.replaceDefinitions(Arrays.asList(def));
		return stepMeta;		
	}
	
	/**
	 * 
	 * <p>函数名称：   配置信息     </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static StepMetaInterface getRuleConfigTableInputMeta(){
		TableInputMeta stepMeta = new TableInputMeta();
		stepMeta.setDefault();
		stepMeta.setDatabaseMeta(EtlHelper.getDefaultDatabaseMeta(EtlHelper.DEFAULT_DATA));
		stepMeta.setSQL(RULE_CONFIG_SQL);
		stepMeta.setVariableReplacementActive(true);//参数替换
		return stepMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：getVariableMeta        </p>
	 * <p>功能说明：获取参数变量
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@SuppressWarnings("rawtypes")
	public static StepMetaInterface getVariableMeta(final Map params){
		GetVariableMeta stepMeta = new GetVariableMeta();
		stepMeta.setDefault();		
		if (params != null) {
			stepMeta.allocate(params.size());
			String[] fieldName = stepMeta.getFieldName();
			String[] variableStr = stepMeta.getVariableString();
			int[] trimType = stepMeta.getTrimType();
			int[] fieldType = stepMeta.getFieldType();
			int[] fieldLength = stepMeta.getFieldLength();
			int[] fieldPrecision = stepMeta.getFieldPrecision();
			int i=0;
			for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				fieldName[i] = StringUtilsExt.toString(entry.getKey());
				variableStr[i] = StringUtilsExt.formatString("$'{'{0}'}'",entry.getKey());
				trimType[i] = ValueMeta.getTrimTypeByCode("none");
				fieldType[i] = ValueMetaInterface.TYPE_STRING;
				fieldLength[i] = -1;
				fieldPrecision[i] = -1;
				i++;
			}
			stepMeta.setFieldName(fieldName);
			stepMeta.setVariableString(variableStr);
			stepMeta.setTrimType(trimType);
			stepMeta.setFieldType(fieldType);
			stepMeta.setFieldLength(fieldLength);
			stepMeta.setFieldPrecision(fieldPrecision);
	    }
		
		return stepMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：getCusomTransStepMeta        </p>
	 * <p>功能说明：数据转换接口
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月14日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static StepMetaInterface getCusomTransStepMeta(String subjectName){
		zcInferfaceMeta stepMeta =  new zcInferfaceMeta();
		stepMeta.setCached(true);
		stepMeta.setCacheSize(50000);
		StreamInterface infoStream = stepMeta.getStepIOMeta().getInfoStreams().get(0);
		infoStream.setSubject(subjectName);//固定值
		return stepMeta;
	}
	
	/**
	 * 
	 * <p>函数名称： getConstStepMeta       </p>
	 * <p>功能说明： 返回常量组件
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@SuppressWarnings("rawtypes")	
	public static StepMetaInterface getConstStepMeta(Map<String, Object> params){
		ConstantMeta stepMeta = new ConstantMeta();
		stepMeta.setDefault();
		if(params != null){
			stepMeta.allocate(params.size());
			String[] fieldName = stepMeta.getFieldName();
			String[] valStr = stepMeta.getValue();
			String[] fieldType = stepMeta.getFieldType();
			int[] fieldLength = stepMeta.getFieldLength();
			int[] fieldPrecision = stepMeta.getFieldPrecision();			
			int i=0;
			for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				fieldName[i] = StringUtilsExt.toString(entry.getKey());
				valStr[i] = StringUtilsExt.toString(entry.getValue());				
				fieldType[i] = ValueMeta.getTypeDesc(ValueMetaInterface.TYPE_STRING);
				fieldLength[i] = -1;
				fieldPrecision[i] = -1;
				i++;
			}
			stepMeta.setFieldName(fieldName);
			stepMeta.setValue(valStr);
			stepMeta.setFieldType(fieldType);
			stepMeta.setFieldLength(fieldLength);
			stepMeta.setFieldPrecision(fieldPrecision);
			
		}
		return stepMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：   createCustomTransHop     </p>
	 * <p>功能说明：  获取自定义组件步骤节点列表
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static List<TransHopMeta> createCustomTransHop(Map<String, Object> params){
		List<TransHopMeta> list = new ArrayList<>();
		//from 配置信息 to 数据转换接口
		//from 数据转换接口 to 类型转换
		//from 类型转换 to 变量设置
		StepMeta ruleConfig = KettleHelper.createStepMeta(getRuleConfigTableInputMeta(), "记录集配置输出");
		StepMeta customTrans = KettleHelper.createStepMeta(getCusomTransStepMeta("记录集配置输出"), "记录集转换");
		StepMeta definedJavaClass = KettleHelper.createStepMeta(getUserDefinedJavaClass(), "类型转换");
		StepMeta var = KettleHelper.createStepMeta(getVariableMeta((Map<String,Object>)params.get(EtlConst.EXTRA_VAR_FIELD)), "变量设置");
		StepMeta constant = KettleHelper.createStepMeta(getVariableMeta((Map<String,Object>)params.get(EtlConst.EXTRA_CONST_FIELD)), "常量设置");
		
		TransHopMeta ruleToCustomHopMeta = new TransHopMeta(ruleConfig,customTrans);
		list.add(ruleToCustomHopMeta);
		TransHopMeta CustomToDefineMeta = new TransHopMeta(customTrans, definedJavaClass);
        list.add(CustomToDefineMeta);
    	TransHopMeta defineToVarMeta = new TransHopMeta(definedJavaClass,var);
        list.add(defineToVarMeta);
        TransHopMeta varToConstMeta = new TransHopMeta(var, constant);
        list.add(varToConstMeta);
        
		return list;
	}
	
	/**
	 * 
	 * <p>函数名称：getVarAndConstHopMeta       </p>
	 * <p>功能说明：一次性获取变量及常量步骤值
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param params
	 * @return
	 *
	 * @date   创建时间：2017年9月16日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransHopMeta getVarAndConstHopMeta(Map<String, Object> params){
		StepMeta var = KettleHelper.createStepMeta(getVariableMeta((Map<String,Object>)params.get(EtlConst.EXTRA_VAR_FIELD)), "变量设置");
		StepMeta constant = KettleHelper.createStepMeta(getConstStepMeta((Map<String,Object>)params.get(EtlConst.EXTRA_CONST_FIELD)), "常量设置");
		TransHopMeta varToConstMeta = new TransHopMeta(var, constant);
	    return varToConstMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：getInjectorStepMeta        </p>
	 * <p>功能说明：获取记录注射器
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static StepMetaInterface getInjectorStepMeta(){
		InjectorMeta stepMeta = new InjectorMeta();		
		return stepMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：getExcelWriteByData        </p>
	 * <p>功能说明：根据数据，获取excel导出Meta
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param data
	 * @return
	 *
	 * @date   创建时间：2017年9月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static StepMetaInterface getExcelWriteByData(ReportDetail data){
		ExcelWriterStepMeta stepMeta = new ExcelWriterStepMeta();
		stepMeta.setDefault();
		stepMeta.setFileName("${excelFilePath}");
		stepMeta.setExtension(data.getExtension());//扩展名		
		stepMeta.setSheetname(data.getSheetName());//sheet名称
		stepMeta.setMakeSheetActive(true);

		//文件存在 直接覆盖
		stepMeta.setIfFileExists(ExcelWriterStepMeta.IF_FILE_EXISTS_CREATE_NEW);
		stepMeta.setDoNotOpenNewFileInit(true);//转换开始时创建文件
		//sheetName操作
		stepMeta.setIfSheetExists(ExcelWriterStepMeta.IF_SHEET_EXISTS_REUSE);
		//设置一个模版 模版中设置了 网格类型 此组件可以根据模版中的单元格样式进行关联设置
		stepMeta.setTemplateEnabled(false);
		stepMeta.setTemplateFileName("${execelTemplate}");
		
		//输出开始单元格
		stepMeta.setStartingCell("A1");
		//记录存在覆盖
		stepMeta.setRowWritingMethod(ExcelWriterStepMeta.ROW_WRITE_OVERWRITE);
		stepMeta.setAppendOmitHeader(false);
		
		//设置输出字段信息 重点 存在合并列情况 故不直接创建字段数组 直接用列表去动态生成
	//	stepMeta.allocate(data.getDetailHeaders().size());
	//	ExcelWriterStepField[] outputFields = stepMeta.getOutputFields();
		List<ExcelWriterStepField> outputFieldsList = new ArrayList<ExcelWriterStepField>();
		for(int i = 0; i < data.getDetailHeaders().size() ; i++){
			DetailHeaders head = data.getDetailHeaders().get(i);			
            outputFieldsList.addAll(getExcelWriterStepField(head));
		}
		stepMeta.setOutputFields(outputFieldsList.toArray(new ExcelWriterStepField[0]));
		return stepMeta;
	}
	
	private static List<ExcelWriterStepField> getExcelWriterStepField(DetailHeaders header){
		List<ExcelWriterStepField> list = new ArrayList<ExcelWriterStepField>();
		List<DetailHeaders> headerList  = header.getSubHeaders();
		if(headerList.isEmpty()){
			ExcelWriterStepField outputFields = new ExcelWriterStepField();
			//可能包含子列 故直接加载子列数据 //递归直接返回直接行			
			outputFields = new ExcelWriterStepField();
            outputFields.setName(header.getName());
            outputFields.setType(ValueMetaInterface.TYPE_STRING);
            outputFields.setTitle(header.getTitle());
            //模版中 A1 左对齐 A2 居中   A3 右对齐
            //outputFields.setTitleStyleCell("styleSetting!A2");//标题都居中
            outputFields.setTitleAlignCell("center");
            //设置宽度
            outputFields.setWidthCell(header.getWidth());            
            outputFields.setFormula(false);
        	/**
        	 * 	var ALIGN = {
        			left: 4,
        			center: 5,
        			right: 6
        		};
        		var ALIGN_UPPER = {
        			left: 'Left',
        			center: 'Center',
        			right: 'Right'
        		};
        	 */
            switch(header.getAlign()){
            case 4:
            	  outputFields.setAlignCell("left");
         	      // outputFields.setStyleCell("styleSetting!A1");//左对齐
            	   break;
            case 5:
            	  outputFields.setAlignCell("center");
             	  // outputFields.setStyleCell("styleSetting!A2");//居中           
            	   break;
            case 6:
          	       outputFields.setAlignCell("right");            	
          	      // outputFields.setStyleCell("styleSetting!A3");//右对齐                       	
            	   break;
           default:
             	  outputFields.setAlignCell("left");
        	  // outputFields.setStyleCell("styleSetting!A1");//默认左对齐
            }
            
            list.add(outputFields);
		} else {
			for(DetailHeaders head: headerList){
				list.addAll(getExcelWriterStepField(head));
			}
		}
		
		return list;
	} 
	
	/**
	 * 
	 * <p>函数名称：  getExportExcelTransMeta      </p>
	 * <p>功能说明： 获取excel transMeta
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param exportData 数据参数
	 * @return
	 *
	 * @date   创建时间：2017年9月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransMeta getExportExcelTransMeta(ReportDetail exportData){
		TransMeta transMeta = new TransMeta();
		transMeta.setName("通用Excel导出");
		StepMeta injectStepMeta = KettleHelper.createStepMeta(getInjectorStepMeta(),"数据集注入");
		StepMeta excelWriterStepMeta = KettleHelper.createStepMeta(getExcelWriteByData(exportData), "excel导出");
		transMeta.addStep(injectStepMeta);
		transMeta.addStep(excelWriterStepMeta);
		TransHopMeta hopMeta = new TransHopMeta(injectStepMeta, excelWriterStepMeta);
		transMeta.addTransHop(hopMeta);		
		return transMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：  getExportExcelWithSqlTransMeta      </p>
	 * <p>功能说明： 数据库数据导出excel
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param exportData
	 * @return
	 *
	 * @date   创建时间：2017年12月10日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static TransMeta getExportExcelWithSqlTransMeta(ReportDetail exportData){
		TransMeta transMeta = new TransMeta();
		transMeta.setName("表数据Excel导出");
		//跟着系统走
		StepMeta tableInputStep = KettleHelper.createStepMeta(getTableInputStepMeta("${SQL}", EtlHelper.getDefaultDatabaseMeta()),"数据输出");
		StepMeta excelWriterStepMeta = KettleHelper.createStepMeta(getExcelWriteByData(exportData), "excel导出");
		transMeta.addStep(tableInputStep);
		transMeta.addStep(excelWriterStepMeta);
		TransHopMeta hopMeta = new TransHopMeta(tableInputStep, excelWriterStepMeta);
		transMeta.addTransHop(hopMeta);		
		return transMeta;
	}
	
	/**
	 * 
	 * <p>函数名称：getTableInputStepMeta        </p>
	 * <p>功能说明：获取表输出步骤meta
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年12月10日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static StepMetaInterface getTableInputStepMeta(String sql,DatabaseMeta databaseMeta){
		TableInputMeta tableInputMeta = new TableInputMeta();
		tableInputMeta.setDefault();
		tableInputMeta.setSQL(StringUtilsExt.toString(sql, "SELECT 1 FROM DUAL "));
		tableInputMeta.setVariableReplacementActive(true);
		tableInputMeta.setRowLimit("0");
		tableInputMeta.setDatabaseMeta(databaseMeta);
		return tableInputMeta;
	}
}
