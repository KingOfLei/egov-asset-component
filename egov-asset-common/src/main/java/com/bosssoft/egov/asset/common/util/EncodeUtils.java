/**
 * Copyright (c) 2005-2012 springside.org.cn
 */
package com.bosssoft.egov.asset.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * 封装各种格式的编码解码工具类.
 * 1.Commons-Codec的 hex/base64 编码
 * 3.Commons-Lang的xml/html escape
 * 4.JDK提供的URLEncoder
 * @author calvin
 * @version 2013-01-15
 */
public class EncodeUtils {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}
	
	/**
	 * Base64编码.
	 */
	public static String encodeBase64(String input) {
		return encodeBase64(input,DEFAULT_URL_ENCODING);
	}
	
	public static String encodeBase64(String input, String charset) {
		try {
			return new String(Base64.encodeBase64(input.getBytes(charset)));
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

//	/**
//	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
//	 */
//	public static String encodeUrlSafeBase64(byte[] input) {
//		return Base64.encodeBase64URLSafe(input);
//	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input.getBytes());
	}
	
	public static byte[] decodeBase64(String input,String charset) {
		try {
			return Base64.decodeBase64(input.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return Base64.decodeBase64(input.getBytes());
		}
	}
	
	/**
	 * Base64解码.
	 */
	public static String decodeBase64String(String input) {
		return decodeBase64String(input, DEFAULT_URL_ENCODING);
	}
	
	public static String decodeBase64String(String input, String charset) {
		try {
			return new String(Base64.decodeBase64(input.getBytes()), charset);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String part) {
		return urlEncode(part, DEFAULT_URL_ENCODING);
	}
	
	public static String urlEncode(String part, String charset) {
		try {
			return URLEncoder.encode(part, charset);
		} catch (UnsupportedEncodingException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String part) {
       return urlDecode(part, DEFAULT_URL_ENCODING);		
	}
	
	public static String urlDecode(String part,String charset) {

		try {
			return URLDecoder.decode(part, charset);
		} catch (UnsupportedEncodingException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}
	
	public static void main(String[] args) {
		String str = "谢德寿";
		System.out.println(encodeBase64(str,"GBK"));
		System.out.println(decodeBase64String(encodeBase64(str,"GBK"),"GBK"));
	}
}
