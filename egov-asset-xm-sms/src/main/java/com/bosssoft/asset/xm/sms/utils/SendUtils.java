package com.bosssoft.asset.xm.sms.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bosssoft.asset.xm.sms.DxptWeb;
import com.bosssoft.asset.xm.sms.DxptWebService;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：SendUtils 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年3月14日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年3月14日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class SendUtils {
	private static Logger logger=LoggerFactory.getLogger(SendUtils.class);

	public static Properties env = new Properties();
	
	static{
		InputStreamReader is=null;
		try{
			is=new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"),"utf-8");
			env.load(is);
		}catch(Exception e){
			logger.error("Can't load application.properties in classpath!",e);
		}finally{
			IOUtils.closeQuietly(is);
		}
	}
	
	public static Map<String,Object> sendMsg(String phone,String receive,String msg){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("flag", false);
		result.put("msg", "发送异常");
		DxptWebService dws = new DxptWebService();
		DxptWeb dw = dws.getDxptWebPort();
		try {
			// 一个接收人 验证码
			String resMsg = dw.sendMsg(env.getProperty("xm.sms.sendName"), env.getProperty("xm.sms.sendDept"), msg, null,
					env.getProperty("xm.sms.xtsqm"), receive, receive, phone,
					String.valueOf(new Date().getTime()), env.getProperty("xm.sms.qm"), "0");
			// 获取回执
			if(resMsg.startsWith("success")){
				result.put("flag", true);
				result.put("msg", "发送成功");
			} else if (resMsg.startsWith("error")){
				String errorCode = StringUtilsExt.substringAfter(resMsg, ":");
				result.put("flag", false);
				if(errorCode.equals("0")){
					result.put("msg", "发送人姓名为空");
				} else if(errorCode.equals("1")){
					result.put("msg", "发送人字数超过25");
				} else if(errorCode.equals("2")){
					result.put("msg", "发送人单位、部门为空");
				} else if(errorCode.equals("3")){
					result.put("msg", "发送人单位、部门字数超过100");
				} else if(errorCode.equals("4")){
					result.put("msg", "发送内容为空");
				} else if(errorCode.equals("5")){
					result.put("msg", "发送内容字数超过500");
				} else if(errorCode.equals("6")){
					result.put("msg", "日期格式不正确");
				} else if(errorCode.equals("7")){
					result.put("msg", "给出的时间参数小于现在的时间");
				} else if(errorCode.equals("8")){
					result.put("msg", "系统授权码为空");
				} else if(errorCode.equals("9")){
					result.put("msg", "系统授权码非法或被禁止");
				} else if(errorCode.equals("10")){
					result.put("msg", "请求来源非法");
				} else if(errorCode.equals("11")){
					result.put("msg", "请求IP地址非法");
				} else if(errorCode.equals("12")){
					result.put("msg", "接收人为空");
				} else if(errorCode.equals("13")){
					result.put("msg", "接收人字符超过25");
				} else if(errorCode.equals("14")){
					result.put("msg", "接收人单位、部门为空");
				} else if(errorCode.equals("15")){
					result.put("msg", "接收人单位、部门字数超过100");
				} else if(errorCode.equals("16")){
					result.put("msg", "接收人手机号码为空");
				} else if(errorCode.equals("17")){
					result.put("msg", "接收人手机号码格式不正确");
				} else if(errorCode.equals("18")){
					result.put("msg", "接收人手机号码段未配置，请联系管理员");
				} else if(errorCode.equals("19")){
					result.put("msg", "接收人手机号码在黑名单中,不予发送");
				} else if(errorCode.equals("20")){
					result.put("msg", "客户端时间为空");
				} else if(errorCode.equals("21")){
					result.put("msg", "参数非法");
				} else if(errorCode.equals("22")){
					result.put("msg", "客户端时间与短信平台时间相差过大，禁止发送短信");
				} else if(errorCode.equals("23")){
					result.put("msg", "签名为空");
				} else if(errorCode.equals("24")){
					result.put("msg", "签名长度超过20个字符");
				} else if(errorCode.equals("25")){
					result.put("msg", "启用回复支持为空");
				} else if(errorCode.equals("26")){
					result.put("msg", "启用回复参数非法");
				} else {
					result.put("msg", "发送异常");
				}
				
			}
			System.out.println("发送验证码原信息：" + resMsg);
			System.out.println(result);
			// String rptResult =
			// dw.getRptBySendId("d9de0669607f4236b72ae1dcf92eb360");
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return result;
	}
}
