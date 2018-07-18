package com.bosssoft.egov.asset.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bosssoft.platform.common.utils.FileUtils;

/** 
*
* @ClassName   类名：FileUtilsEx 
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
public class FileUtilsExt extends FileUtils{
    
	/**
	 * 
	 * <p>函数名称：   getFileType     </p>
	 * <p>功能说明： 返回文件类型 即后缀名
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param fileName
	 * @return
	 *
	 * @date   创建时间：2016年12月2日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getFileType(String fileName){
		int i = fileName.lastIndexOf(".");
        return fileName.substring(i+1);
	}
	
	public static InputStream urlToInputStream(String url,String fileName) throws IOException {
		URL httpurl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)httpurl.openConnection();
//		//设置超时间为3秒
		conn.setConnectTimeout(3*1000);
//		//防止屏蔽程序抓取而返回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//		//得到输入流
		InputStream inputStream = conn.getInputStream();
		try {
			return new ByteArrayInputStream(IOUtilsExt.toByteArray(inputStream));
		} finally{
			inputStream.close();
		}
	}
	
	/**
	 * 获取文本格式
	 * @param path
	 * @return
	 */
	public static String getFileEncode(String path) {
		String charset ="asci";
        byte[] first3Bytes = new byte[3];
        BufferedInputStream bis = null;
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(path));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "Unicode";//UTF-16LE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "Unicode";//UTF-16BE
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int len = 0;
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) //单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) 
                        //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { //也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException ex) {
                }
            }
        }
        return charset;
	}
	
	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 */
	public static String readFileContent(String path){
		String data = null;
		// 判断文件是否存在
		File file = new File(path);
		if(!file.exists()){
			return data;
		}
		// 获取文件编码格式
		String code = FileUtilsExt.getFileEncode(path);
		InputStreamReader isr = null;
		try{
			// 根据编码格式解析文件
			if("asci".equals(code)){
				// 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码 
				// code = System.getProperty("file.encoding");
				code = "GBK";
			}
			isr = new InputStreamReader(new FileInputStream(file),code);
			// 读取文件内容
			int length = -1 ;
			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			while((length = isr.read(buffer, 0, 1024) ) != -1){
				sb.append(buffer,0,length);
			}
			data = new String(sb);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(isr != null){
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	public static String readFileContent(String path,String code){
		String data = null;
		// 判断文件是否存在
		File file = new File(path);
		if(!file.exists()){
			return data;
		}
		InputStreamReader isr = null;
		try{
			// 根据编码格式解析文件
			if("asci".equals(code)){
				// 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码 
				code = "GBK";
			}
			isr = new InputStreamReader(new FileInputStream(file),code);
			// 读取文件内容
			int length = -1 ;
			char[] buffer = new char[1024];
			StringBuffer sb = new StringBuffer();
			while((length = isr.read(buffer, 0, 1024) ) != -1){
				sb.append(buffer,0,length);
			}
			data = new String(sb);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(isr != null){
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
	
	/**
	 * 
	 * <p>函数名称：  mkdirs      </p>
	 * <p>功能说明：创建父类文件夹
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param fileName
	 * @return
	 *
	 * @date   创建时间：2017年5月9日
	 * @author 作者：xds
	 */
	public static boolean mkdirs(String fileName,boolean _isDir){
		return mkdirs(new File(fileName),_isDir);
	}
	
	public static boolean exists(String fileName){
		return new File(fileName).exists();
	}
	
	public static String getFileName(String fileName){
		return new File(fileName).getName();
	}
	
	public static boolean mkdirs(File file,boolean _isDir){
		return mkdir(file, _isDir);				
	}
	
	public static boolean createNewFile(String fileName){
		return createNewFile(new File(fileName));
	}
	
	/**
	 * 
	 * <p>函数名称： createNewFile       </p>
	 * <p>功能说明：创建文件
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param file
	 * @return
	 *
	 * @date   创建时间：2017年5月9日
	 * @author 作者：xds
	 */
	public static boolean createNewFile(File file){
		mkdirs(file,false);
		try {
			return file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
