/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bosssoft.egov.asset.common.util;

import java.io.File;

import com.bosssoft.egov.asset.common.idgenerator.GUID;
/**
 * new File("..\path\abc.txt") 中的三个方法获取路径的方法
 * 1： getPath() 获取相对路径，例如   ..\path\abc.txt
 * 2： getAbslutlyPath() 获取绝对路径，但可能包含 ".." 或 "." 字符，例如  D:\otherPath\..\path\abc.txt
 * 3： getCanonicalPath() 获取绝对路径，但不包含 ".." 或 "." 字符，例如  D:\path\abc.txt
 */
public class PathUtil {
	
	private static String webRootPath;
	private static String rootClassPath;
	private static String contextPath = "";

	
	@SuppressWarnings("rawtypes")
	public static String getPath(Class clazz) {
		String path = clazz.getResource("").getPath();
		return new File(path).getAbsolutePath();
	}
	
	public static String getPath(Object object) {
		String path = object.getClass().getResource("").getPath();
		return new File(path).getAbsolutePath();
	}
	
	/**
	 * 
	 * <p>函数名称：  getRootClassPath      </p>
	 * <p>功能说明： 获取rootClass 路径
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getRootClassPath() {
		if (rootClassPath == null) {
			try {
				String path = PathUtil.class.getClassLoader().getResource("").toURI().getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
			catch (Exception e) {
				String path = PathUtil.class.getClassLoader().getResource("").getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
		}
		return rootClassPath;
	}
	
	public void setRootClassPath(String rootClassPath) {
		PathUtil.rootClassPath = rootClassPath;
	}
	
	public static String getPackagePath(Object object) {
		Package p = object.getClass().getPackage();
		return p != null ? p.getName().replaceAll("\\.", "/") : "";
	}
	
	public static File getFileFromJar(String file) {
		throw new RuntimeException("Not finish. Do not use this method.");
	}
	
	/**
	 * 
	 * <p>函数名称：getWebRootPath        </p>
	 * <p>功能说明： 获取 webRoot路径 
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getWebRootPath() {
		if (webRootPath == null)
			webRootPath = detectWebRootPath();
		return webRootPath;
	}
	
	public static void setWebRootPath(String webRootPath) {
		if (webRootPath == null)
			return ;
		
		if (webRootPath.endsWith(File.separator))
			webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
		PathUtil.webRootPath = webRootPath;
	}
	
	private static String detectWebRootPath() {
		try {
			String path = PathUtil.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * <p>函数名称： getTempDirectory       </p>
	 * <p>功能说明：获取一个临时下32位guid的目录路径
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getTempDirectory() {
		return System.getProperty("java.io.tmpdir") + File.separator  + GUID.newGUID() + File.separator;
	}
	
	public static String getTempDirectory(String directory) {
		return System.getProperty("java.io.tmpdir") + File.separator  + directory + File.separator;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		PathUtil.contextPath = contextPath;
	}
	
	/**
	 * 
	 * <p>函数名称：  getFileRecv      </p>
	 * <p>功能说明： 获取文件存放目录
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getFileRecv(){
		return PathUtil.getWebRootPath() + File.separator + "FileRecv" + File.separator;
	}
	
	/**
	 * 
	 * <p>函数名称：  getDateDirectory      </p>
	 * <p>功能说明： 返回日期格式目录结构 ：2017\09\24\
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2017年9月17日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getDateDirectory(){
		return DateUtilsExt.getNowYear() 
        + File.separator + DateUtilsExt.getNowMonth()
        + File.separator + DateUtilsExt.getNowDay() + File.separator;
	}
	
	public static void main(String[] args) {
		System.out.println(getWebRootPath());
	}
}


