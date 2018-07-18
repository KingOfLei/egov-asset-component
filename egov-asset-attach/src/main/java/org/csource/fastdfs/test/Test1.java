
package org.csource.fastdfs.test;

import java.net.InetSocketAddress;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;

public class Test1
{
  public static void main(String args[])
  {
  	try
  	{
  		String configPath = Test1.class.getClass()
  		        .getResource("/fdfs_client.conf").getFile();
  		ClientGlobal.init("C:\\Users\\xiedeshou\\Documents\\Tencent Files\\499452441\\FileRecv\\fastdfs-client-java-master\\fastdfs-client-java-master\\src\\fdfs_client.conf");
		System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
		System.out.println("charset=" + ClientGlobal.g_charset);
  		
		TrackerGroup tg = new TrackerGroup(new InetSocketAddress[]{new InetSocketAddress("192.168.81.128", 22122)});
		TrackerClient tc = new TrackerClient(tg);
		
		TrackerServer ts = tc.getConnection();
		if (ts == null)
		{
			System.out.println("getConnection return null");
			return;
		}

		StorageServer ss = tc.getStoreStorage(ts);
		if (ss == null)
		{
			System.out.println("getStoreStorage return null");
		}
		
		StorageClient1 sc1 = new StorageClient1(ts, ss);
		
		NameValuePair[] meta_list = null;  //new NameValuePair[0];
		String item = "d:/KREW.jpg";
		String fileid = sc1.upload_file1(item, "jpg", meta_list); //�����쳣
		
		System.out.println("Upload local file "+item+" ok, fileid="+fileid);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
	}
}
