package com.bosssoft.egov.asset.fastdfs;

import java.io.Serializable;

/** 
*
* @ClassName   类名：Config 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月28日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月28日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public interface Config extends Serializable {
    public static final String FILE_DEFAULT_WIDTH = "120";
    public static final String FILE_DEFAULT_HEIGHT = "120";
    public static final String FILE_DEFAULT_AUTHOR = "bosssoft-egov";

    public static final String PROTOCOL = "http://";
    public static final String SEPARATOR = "/";

    public static final String TRACKER_NGNIX_PORT = "8080";
}
