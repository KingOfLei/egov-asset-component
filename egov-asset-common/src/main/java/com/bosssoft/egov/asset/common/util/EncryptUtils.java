package com.bosssoft.egov.asset.common.util;

import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @ClassName 类名：EncryptUtils
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2017年12月25日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2017年12月25日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class EncryptUtils {
	
	private static Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

	/** 
     * 定义加密方式 
     * MAC算法可选以下多种算法 
     * <pre> 
     * HmacMD5 
     * HmacSHA1 
     * HmacSHA256 
     * HmacSHA384 
     * HmacSHA512 
     * </pre> 
     */  
	private static final String HMAC_ALGORITHM = "HmacMD5";

	public static final Charset ENCODING = Charset.forName("UTF-8");

	public static String generateSecurityCode() {
		return null;

	}

	public static String createMacKey() {
		return createKey(HMAC_ALGORITHM);
	}
	
	/**
	 * 
	 * <p>函数名称：createMacKey        </p>
	 * <p>功能说明：创建macKey
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param key
	 * @return
	 *
	 * @date   创建时间：2017年12月25日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String createKey(String key){
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(key);
			SecretKey generateKey = keyGenerator.generateKey();
			return EncodeUtils.encodeBase64(generateKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			logger.error("创建macKey异常", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static byte[] hmac(String hmacKey, String data) {
		// Assert.assertFalse("hmacKey may arg not be null",
		// StringUtils.isEmpty(data));
		// Assert.assertNotNull("data may arg not be null", data);
		Mac mac;
		try {
			SecretKey key = new SecretKeySpec(EncodeUtils.decodeBase64(hmacKey),
					HMAC_ALGORITHM);
			mac = Mac.getInstance(HMAC_ALGORITHM);
			mac.init(key);
			byte[] doFinal = mac.doFinal(data.getBytes(ENCODING));
			return doFinal;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/* byte数组转换为HexString */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(createMacKey());
		System.out.println(createKey("HmacMD5"));
		System.out.println(createKey("HmacSHA256"));
		String md5Key = "lyKsMLxYfbluTgW8zpQC7/Sni2LqR67tKdL6kTSwMhtAYkx2lb/Aivc2YVzYpSFQkByKgaFnZAaPKb7fxTwvPw==";
		System.out.println(MD5.crtMd5Value(EncodeUtils.encodeBase64(EncryptUtils.hmac(md5Key, "厦门市政府资产综合管理平台"))));
		System.out.println(MD5.crtMd5Value(EncodeUtils.encodeBase64(EncryptUtils.hmac(md5Key, "厦门市政府资产综合管理平台1"))));
	}

}
