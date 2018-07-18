package com.bosssoft.egov.asset.attach.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.bosssoft.platform.runtime.exception.BusinessException;

/** 
*
* @ClassName   类名：AbstractAttachmentService 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月7日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月7日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public abstract class AbstractAttachmentService implements AttachmentService{
	private static Logger logger = LoggerFactory.getLogger(AbstractAttachmentService.class);

	@Override
	public String upload(String attachId, MultipartFile multipartFile,
			String bizType, String suffix) throws BusinessException {
		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();
		} catch (IOException e) {
			logger.error("上传文件[" + multipartFile.getOriginalFilename() + "]异常。");
			throw new BusinessException("获取上传文件异常",e);
		}
		return this.upload(attachId, inputStream, bizType, suffix);
	}

	@Override
	public String upload(String attachId, InputStream inputStream,
			String bizType, String suffix) throws BusinessException {
		
		return doUpload(attachId, inputStream, bizType, suffix);
	}

	@Override
	public String upload(String attachId, File file, String bizType,
			String suffix) throws BusinessException {
		InputStream inputStream = null;
		try {
			inputStream = FileUtils.openInputStream(file);
		} catch (IOException e) {
			logger.error("上传文件[" + file.getName() + "]异常。");			
			throw new BusinessException("获取上传文件异常",e);
		}		
		return this.upload(attachId, inputStream, bizType, suffix);
	}

	@Override
	public boolean delete(String filePath) {		
		return doDelete(filePath);
	}

	@Override
	public InputStream dowload(String filePath) {
		return this.doDowload(filePath);
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return this.doGetType();
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return this.doGetPath();
	}

	
	public abstract String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix);
	
	public abstract boolean doDelete(String filePath);
	
	public abstract InputStream doDowload(String filePath);
	
	public abstract String doGetType();
	
	public abstract String doGetPath();
}
