package com.bosssoft.egov.asset.attach.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.attach.entity.AssetAttachFilestore;
import com.bosssoft.egov.asset.attach.mapper.AssetAttachFilestoreMapper;
import com.bosssoft.egov.asset.attach.web.IdGen;
import com.bosssoft.egov.asset.common.util.IOUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.runtime.exception.BusinessException;

/** 
*
* @ClassName   类名：DatabaseAttachServiceImpl 
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
@Service("DatabaseAttachService")
public class DatabaseAttachServiceImpl extends AbstractAttachmentService{

	@Autowired
	private AssetAttachFilestoreMapper mapper;
	
	@Override
	public String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			IOUtilsExt.copy(inputStream, os);
			os.close();
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		String guidId = StringUtilsExt.toString(IdGen.newWKID());
		mapper.saveFiles(guidId, os.toByteArray());
		return guidId;
	}

	@Override
	public boolean doDelete(String filePath) {
		mapper.deleteByPrimaryKey(filePath);
		return true;
	}

	@Override
	public InputStream doDowload(String filePath) {
		AssetAttachFilestore search = new AssetAttachFilestore();
		search.setId(filePath);
		AssetAttachFilestore fileStore = mapper.selectByPrimaryKey(search);
		if(fileStore == null){
		  return null;
		}
		return new ByteArrayInputStream(fileStore.getContent());
	}

	@Override
	public String doGetType() {
		// TODO Auto-generated method stub
		return "Database";
	}

	@Override
	public String doGetPath() {
		// TODO Auto-generated method stub
		return "";
	}

}
