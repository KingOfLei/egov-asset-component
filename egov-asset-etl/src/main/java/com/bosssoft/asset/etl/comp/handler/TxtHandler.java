package com.bosssoft.asset.etl.comp.handler;

import javax.annotation.Resource;

import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.textfileoutput.TextFileOutputMeta;

import com.bosssoft.asset.etl.comp.entity.AssetEtlCompConfigTxt;
import com.bosssoft.asset.etl.comp.service.AssetEtlCompConfigTxtService;
import com.bosssoft.platform.common.extension.Activate;
import com.bs.second.trans.TextFileInput.TextFileInputMeta;

/** 
*
* @ClassName   类名：TxtHandler 
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
public class TxtHandler extends AbstractHandler implements Handler{

	@Resource
	private AssetEtlCompConfigTxtService txtService;
	
	@Override
	public HandlerType getHandler() {
		return HandlerType.TXT;
	}

	@Override
	public StepMetaInterface createStepMeta(Long configId) {
		AssetEtlCompConfigTxt config = getConfig(configId);
		if(config == null) return getDefaultStepMeta();
		if("OUT".equalsIgnoreCase(config.getBizType())){
			TextFileOutputMeta stepMeta = new TextFileOutputMeta();
			stepMeta.setDefault();
			stepMeta.setFileName(config.getFilePath());//输入文件路径
			stepMeta.setSeparator(config.getFileSeparator());//分隔符
			stepMeta.setFileFormat(config.getFileFormat());//文件格式 DOS\UNIS
			stepMeta.setEncoding(config.getFileEncoding());//编码	
			stepMeta.setCreateParentFolder(true);//创建父类文件夹
			stepMeta.setDoNotOpenNewFileInit(true);
			stepMeta.setHeaderEnabled(true);//字段输出头部
			stepMeta.setFastDump(true);//快速输出 不带格式化
			stepMeta.setExtension("");//扩展名设置为空 直接认filePath
			return stepMeta;
		} else if("IN".equalsIgnoreCase(config.getBizType())){
			TextFileInputMeta stepMeta = new TextFileInputMeta();
			stepMeta.setDefault();
			stepMeta.allocate(1, 0, 0);
			stepMeta.setFileName(new String[]{config.getFilePath()});//导入文件
			stepMeta.setFileMask(new String[]{config.getFileMask()});//导入文件通配符 文件为文件夹时 有效	
			stepMeta.setFileType(config.getFileType());//文件类型CSV、Fiexd
			stepMeta.setEncoding(config.getFileEncoding());//编码格式
			stepMeta.setSeparator(config.getFileSeparator());//分隔符
			stepMeta.setFileFormat(config.getFileFormat());//文件格式DOS\UNIS
			return stepMeta;
		}
		return getDefaultStepMeta();
	}
	
	private AssetEtlCompConfigTxt getConfig(Long configId){
		AssetEtlCompConfigTxt config = new AssetEtlCompConfigTxt();
		config.setConfigId(configId);
		config.setStatus("1");
		return txtService.getAssetEtlCompConfigTxt(config);
	}

}
