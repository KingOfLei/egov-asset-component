package com.bosssoft.egov.asset.attach.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.idgenerator.UUIDUtils;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.runtime.exception.BusinessException;
import com.bosssoft.platform.runtime.server.config.AppConfiguration;
import com.bosssoft.platform.runtime.web.context.WebApplicationContext;

/** 
*
* @ClassName   类名：LocalHostAttachServiceImpl 
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
@Service("LocalHostAttachService")
public class LocalHostAttachServiceImpl extends AbstractAttachmentService{
	
	private static Logger logger = LoggerFactory.getLogger(LocalHostAttachServiceImpl.class);

	
    private static final String SEPARATOR = File.separator;
	
	private String directory(String bizType){
		//根据类型获取目录 应用上下文/FileRecv/bizType/年/月/日
		String directory = bizType
				           + SEPARATOR + DateUtilsExt.getNowYear() 
				           + SEPARATOR + DateUtilsExt.getNowMonth()
		                   + SEPARATOR + DateUtilsExt.getNowDay();
		return directory + SEPARATOR;
	}
	
	private String getFileName(String bizType, String suffix){
		String directory = this.directory(bizType)
				           + UUIDUtils.getRandomUUID();
		return directory += "." + suffix;
		
	}

	@Override
	public String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix) {
		//文件路径
		String fileName = this.getFileName(bizType, suffix);
		try {
			FileUtils.copyInputStreamToFile(inputStream,
					new File(doGetPath() + SEPARATOR + fileName));
		} catch (IOException e) {
			throw new BusinessException("文件上传失败", e);
		}
		return fileName;
	}

	@Override
	public boolean doDelete(String filePath) {
		File file = new File(this.doGetPath() + SEPARATOR + filePath);
		if(file.isFile()){
		   return FileUtils.deleteQuietly(file);
		}
		return false;
	}

	@Override
	public InputStream doDowload(String filePath) {
		File file = new File(doGetPath() + SEPARATOR + filePath);
		InputStream inputStream = null;
		try {
			inputStream = FileUtils.openInputStream(file);
		} catch (IOException e) {
			logger.debug("未找到对应路径[{}]的附件信息。",file);
			return null;
		}
		return inputStream;
	}

	@Override
	public String doGetType() {
		return "LocalHost";
	}

	@Override
	public String doGetPath() {
		if(StringUtilsExt.isNotBlank(rootDir)){
		   return rootDir + SEPARATOR + "FileRecv";
		} else {
		  AppConfiguration appConfig = WebApplicationContext.getContext().getAppConfiguration();
		  return appConfig.getAppRealPath() + SEPARATOR + "FileRecv";
		}
	}
	
	@Value("${attachment.dir.root}")
	private String rootDir;
	
}
