package com.bosssoft.egov.asset.attach.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.IOUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.utils.Assert;
import com.bosssoft.platform.runtime.exception.BusinessException;

/** 
*
* @ClassName   类名：FastDfsAttachServiceImpl 
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
@Service("FastDfsAttachService")
public class FastDfsAttachServiceImpl extends AbstractAttachmentService{
	/**
	 * FastDFS服务器地址
	 */
	@Value("${fastdfs.hostName}")
	private String hostName;
	/**
	 * 端口
	 */
	@Value("${fastdfs.port}")	
	private  Integer port;
	/**
	 * 编码
	 */
	@Value("${fastdfs.charset}")	
	private String charset;
	/**
	 * 超时时间
	 */
	@Value("${fastdfs.networkTimeout}")	
	private Integer networkTimeout;

	@Value("${fastdfs.http}")
	private String http;
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getNetworkTimeout() {
		return networkTimeout;
	}

	public void setNetworkTimeout(Integer networkTimeout) {
		this.networkTimeout = networkTimeout;
	}

	public String getHttp() {
		return http;
	}

	public void setHttp(String http) {
		this.http = http;
	}

	private TrackerServer trackerServer;

	private StorageServer storageServer;

	private StorageClient storageClient;

	public TrackerServer getTrackerServer() {
		return trackerServer;
	}

	public void setTrackerServer(TrackerServer trackerServer) {
		this.trackerServer = trackerServer;
	}

	public StorageServer getStorageServer() {
		return storageServer;
	}

	public void setStorageServer(StorageServer storageServer) {
		this.storageServer = storageServer;
	}

	public StorageClient getStorageClient() {
		return storageClient;
	}

	public void setStorageClient(StorageClient storageClient) {
		this.storageClient = storageClient;
	}

	private  void init(){
		ClientGlobal.setG_charset(charset);
		ClientGlobal.setG_network_timeout(networkTimeout);
		TrackerGroup tg = new TrackerGroup(new InetSocketAddress[]{new InetSocketAddress(hostName, port)});
		TrackerClient tc = new TrackerClient(tg);
		try{
			trackerServer = tc.getConnection();
		}catch (IOException e){
			e.printStackTrace();
			return;
		}
		if(trackerServer!=null){
			try {
				storageServer = tc.getStoreStorage(trackerServer);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		if(storageServer!=null) {
			storageClient = new StorageClient(trackerServer, storageServer);
		}
	}
	
	private void close(){
		if(trackerServer!=null) {
			try {
				trackerServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			trackerServer = null;
		}
		if(storageServer!=null){
			try {
				storageServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			storageServer=null;
		}
		storageClient=null;
	}
	
	@Override
	public String doUpload(String attachId, InputStream inputStream,
			String bizType, String suffix) {
		init();
		Assert.notNull(storageClient,"文件服务器没有创建成功！");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			IOUtilsExt.copy(inputStream, os);
			os.close();
		} catch (IOException e) {
			throw new BusinessException(e);
		}
		byte[] data = os.toByteArray();
		NameValuePair[] metaList = null;
		String[] sArray=null; 
		//  <ul><li>results[0]: the group name to store the file</li></ul>
		//  <ul><li>results[1]: the new created filename</li></ul>
        try {
        	sArray=storageClient.upload_appender_file(data,suffix,metaList);
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage());
        } catch (MyException e) {
        	e.printStackTrace();
        	throw new BusinessException(e.getMessage());
        }finally {
        	this.close();
        }        
		return StringUtilsExt.join(sArray, "-");
	}

	@Override
	public boolean doDelete(String filePath) {
		this.init();
		Assert.notNull(storageClient,"文件服务器没有创建成功！");
		List<String> list= Arrays.asList(StringUtilsExt.split(filePath, "-"));
		if(list.size()==2) {
			String groupName = (String) list.get(0);
			String remoteFilename= (String) list.get(1);
			try {
				storageClient.delete_file(groupName, remoteFilename);
			} catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			} catch (MyException e) {
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}finally {
				this.close();
			}
		}
		return true;
	}

	@Override
	public InputStream doDowload(String filePath) {
		String[] sArray = filePath.split("-");
		if(sArray.length==2) {
			StringBuffer url = new StringBuffer(http);
			url.append("/");
			url.append(sArray[0]);
			url.append("/");
			url.append(sArray[1]);
			try {
				int i=sArray[1].lastIndexOf("/");
				String name=sArray[1].substring(i+1,sArray[1].length());
				return FileUtilsExt.urlToInputStream(url.toString(), name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String doGetType() {
		return "FastDfs";
	}

	@Override
	public String doGetPath() {
		// TODO Auto-generated method stub
		return getHttp();
	}

}
