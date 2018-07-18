package com.bosssoft.egov.asset.attach.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bosssoft.egov.asset.attach.web.AtttachHelper;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.PathUtil;

/** 
*
* @ClassName   类名：AttachmentQuartz 
* @Description 功能说明：附件定时任务 处理类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年8月26日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年8月26日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
@Component("attachmentQuartz")
public class AttachmentQuartz {
	private static Logger logger = LoggerFactory.getLogger(AttachmentQuartz.class);

	 /**
	  * 
	  * <p>函数名称：   delPreviewFolder     </p>
	  * <p>功能说明： 动态清除 预览的临时文件
	  *
	  * </p>
	  *<p>参数说明：</p>
	  *
	  * @date   创建时间：2017年8月25日
	  * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 * @throws IOException 
	  */
	 public void delPreviewFolder() throws IOException{
		// 清除前一天的文件
		String tempFile = PathUtil.getWebRootPath() + File.separator
				+ AtttachHelper.TEMP_FOLDER;
		if(!FileUtilsExt.exists(tempFile)){
			return;//不存在文件夹直接退出
		}
		String nowDate = DateUtilsExt.getNowDate();
		File[] fileList = new File(tempFile).listFiles();
		if(fileList == null) return;
		for (File file : fileList) {
			if (file.isDirectory() && !nowDate.equals(file.getName())) {
				FileUtils.deleteDirectory(file);
			}
		}
		logger.info("--{}----定时清除附件预览目录成功----",DateUtilsExt.formatDate(new Date()));
	 }

}
