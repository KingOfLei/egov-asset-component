package com.bosssoft.egov.asset.common.util.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;

import com.bosssoft.egov.asset.common.util.FileUtilsExt;
import com.bosssoft.egov.asset.common.util.IOUtilsExt;
/** 
*
* @ClassName   类名：ZipFileUtilExt 
* @Description 功能说明：压缩及解压公共类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年5月9日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年5月9日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class ZipFileUtilExt {
   
	/**
	 * 
	 * <p>函数名称：   compressFile     </p>
	 * <p>功能说明： 压缩文件
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param zipFile 压缩zip文件路径（全路径）
	 * @param files 需要压缩的文件列表
	 *
	 * @date   创建时间：2017年5月9日
	 * @author 作者：xds
	 */
	public static void compressFile(String zipFile,String... files){
		ZipArchiveOutputStream os = null;
		try {
			FileUtilsExt.createNewFile(zipFile);
			os = new ZipArchiveOutputStream(new File(zipFile));
	        for(String file :files){
	        	File fileName = new File(file);
		        InputStream is = new FileInputStream(fileName);
		        os.putArchiveEntry(new ZipArchiveEntry(fileName.getName()));  
		        IOUtilsExt.copy(is,os);
				os.closeArchiveEntry(); 
				is.close();
	        }
	        os.flush();
			os.close();
		} catch (IOException e) {
			throw new RuntimeException("压缩失败",e);
		}
	}
	
	/**
	 * 
	 * <p>函数名称： compressFolder       </p>
	 * <p>功能说明： 压缩文件夹
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param zipFile 压缩文件路径
	 * @param folderName 待压缩的文件夹
	 * @param recursive 是否递归文件夹（包含子文件）
	 *
	 * @date   创建时间：2017年5月9日
	 * @author 作者：xds
	 */
    public static void compressFolder(String zipFile,String folderName,boolean recursive){
    	ZipArchiveOutputStream os = null;
    	try{
    		FileUtilsExt.createNewFile(zipFile);
			os = new ZipArchiveOutputStream(new File(zipFile));
			for(File file :  FileUtils.listFiles(new File(folderName), null, recursive)){
				InputStream is = new FileInputStream(file);
			    os.putArchiveEntry(new ZipArchiveEntry(file.getName()));  
			    IOUtilsExt.copy(is,os);
			    os.closeArchiveEntry(); 
			    is.close();
			}
			os.flush();
		    os.close();
    	} catch (IOException e) {
			throw new RuntimeException("压缩失败",e);
		}
	}
    
    /**
     * 
     * <p>函数名称：  decompressFile      </p>
     * <p>功能说明： 解压文件
     *
     * </p>
     *<p>参数说明：</p>
     * @param zipFile 待解压zip文件
     * @param targetFolder 解压目标文件夹
     *
     * @date   创建时间：2017年5月9日
     * @author 作者：xds
     */
    public static void decompressFile(String zipFile,String targetFolder){
    	decompressFile(zipFile, targetFolder, null);
    }
    
    /**
     * 
     * <p>函数名称：  decompressFile      </p>
     * <p>功能说明： 解压文件
     *
     * </p>
     *<p>参数说明：</p>
     * @param zipFile 待解压zip文件
     * @param targetFolder 解压目标文件夹
     * @param inCludeRegExp 
     * 指定解压特定文件 （正则表达式） 解压全部请赋值为 null 或者为 ""
	 *  1.指定文件名：".*Reg.*" 表示含Reg字符的文件名 若不区分大小写 则 可写成  ".*(?i)Reg.*"
	 *  2.特定文件名结尾： ".*\.data$" 以.data 结尾的文件名
	 *  3.特定字符开头: "^A.*"以A字母开头的字符串
     *
     * @date   创建时间：2017年5月9日
     * @author 作者：xds
     */
    public static void decompressFile(String zipFile,String targetFolder, String inCludeRegExp){
    	if(!FileUtilsExt.exists(zipFile)){
			throw new RuntimeException("文件：" + FileUtilsExt.getFileName(zipFile) + " 未找到！");
		}
    	try {
    	   decompressFile(FileUtils.openInputStream(new File(zipFile)), targetFolder, inCludeRegExp);
    	} catch(IOException e){
    		throw new RuntimeException("解压文件异常",e);
    	}
    }
    
    public static void decompressFile(InputStream zipFileInputStream,String targetFolder, String inCludeRegExp){
    	Pattern pattern = null;
		//设置表达式
		if (inCludeRegExp != null && inCludeRegExp.length() != 0){
			pattern = Pattern.compile(inCludeRegExp);
		}
		FileUtilsExt.mkdirs(targetFolder,true);
    	ZipArchiveInputStream in = null;
    	
    	try{
    		
       		in = new ZipArchiveInputStream(zipFileInputStream);
    		ArchiveEntry archiveEntry = null;
    		//循环读取zip文件
    		while((archiveEntry = in.getNextEntry()) != null) {
    			// 获取文件名  
                String entryFileName = archiveEntry.getName();  
              //判断是否有过滤文件
    			if (pattern != null) {
    				Matcher matcher = pattern.matcher(entryFileName);
    				if (!matcher.matches()){
    					continue;
    				}
    			}
                // 构造解压出来的文件存放路径  
                String entryFilePath = targetFolder + File.separator + entryFileName;  
                OutputStream os = null;  
                try {  
                    // 把解压出来的文件写到指定路径  
                    File entryFile = new File(entryFilePath);
                    if(entryFile.getParentFile() != null){
                      entryFile.getParentFile().mkdirs();
                    }
                    if(entryFileName.endsWith("/")){  
                        entryFile.mkdirs();  
                    }else{                      	
                        os = new BufferedOutputStream(new FileOutputStream(entryFile));                              
                        byte[] buffer = new byte[1024];   
                        int len = -1;   
                        while((len = in.read(buffer)) != -1) {  
                            os.write(buffer, 0, len);   
                        }  
                    }  
                } catch (IOException e) {  
                    throw new IOException(e);  
                } finally {  
                    if (os != null) {  
                        os.flush();  
                        os.close();  
                    }  
                } 
    		}
    	} catch (IOException e) {
			throw new RuntimeException("解压文件异常",e);
		} finally {
			//IOUtilsExt.closeQuietly(is);
			IOUtilsExt.closeQuietly(in);						
		}
    }
    
    public static void decompressFile(InputStream zipFileInputStream,String targetFolder){
    	decompressFile(zipFileInputStream, targetFolder, null);
    }
  }
