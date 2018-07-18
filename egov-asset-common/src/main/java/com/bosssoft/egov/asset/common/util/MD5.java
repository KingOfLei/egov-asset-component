package com.bosssoft.egov.asset.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
*
* @ClassName   类名：MD5 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月12日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月12日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class MD5 {


	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else {
				hs = hs + stmp;
			}

		}

		return hs.toUpperCase();
	}
	
	public static String md5(String plain) throws NoSuchAlgorithmException {
		byte[] input = plain.getBytes();

		byte[] encrypted = md5(input);

		return byte2hex(encrypted);
	}

	public static byte[] md5(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.update(input);
		byte[] digest = alg.digest();
		return digest;
	}
	
	public static String crtMd5Value(String source){
		try {
			return md5(source);
		} catch(NoSuchAlgorithmException e) {
			
		}
		return "";
	}
}
