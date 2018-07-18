package com.bosssoft.egov.asset.attach.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.bosssoft.platform.common.extension.SPI;
import com.bosssoft.platform.runtime.exception.BusinessException;

/** 
*
* @ClassName   类名：AttachmentService 
* @Description 功能说明：父类上传下载公共服务接口
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
@SPI
public interface AttachmentService {

	/**
	 * 
	 * <p>函数名称：   upload     </p>
	 * <p>功能说明： 利用MultipartFile 进行文件上传
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param multipartFile
	 * @param bizType 业务类型
	 * @param 
	 * @return 返回文件路径或者标识
	 * @throws IOException
	 *
	 * @date   创建时间：2016年12月2日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String upload(String attachId, MultipartFile multipartFile,String bizType, String suffix) throws BusinessException;
	/*
	 * upload 文件上传
	 */
	public String upload(String attachId,InputStream inputStream, String bizType, String suffix) throws BusinessException;
	/*
	 * upload 文件上传
	 */
	public String upload(String attachId,File file,String bizType, String suffix) throws BusinessException;
	
	/**
	 * 
	 * <p>函数名称： delete       </p>
	 * <p>功能说明：文件删除
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param filePath 文件名称
	 * @return
	 *
	 * @date   创建时间：2016年12月2日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public boolean delete(String filePath);
		
	public InputStream dowload(String filePath);
	
	/**
	 * 
	 * <p>函数名称：  getType      </p>
	 * <p>功能说明： 处理类型
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年12月2日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getType();
	
	/**
	 * 
	 * <p>函数名称： getPath       </p>
	 * <p>功能说明： 返回文件上传目录
	 *
	 * </p>
	 *<p>参数说明：</p> 
	 * @return
	 *
	 * @date   创建时间：2016年12月2日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public String getPath();
	
}
