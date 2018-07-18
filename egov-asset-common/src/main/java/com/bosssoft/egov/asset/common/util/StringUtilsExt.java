package com.bosssoft.egov.asset.common.util;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/** 
*
* @ClassName   类名：StringUtilsExt 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月6日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月6日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class StringUtilsExt extends StringUtils{

	public static final String UNIX_OPEN = "${";

	public static final String UNIX_CLOSE = "}";

	public static final String WINDOWS_OPEN = "%%";

	public static final String WINDOWS_CLOSE = "%%";
	
	public static final String HEX_OPEN = "$[";
	public static final String HEX_CLOSE = "]";
  
    public static final String CRLF = "\r\n"; //$NON-NLS-1$

    public static final String INDENTCHARS = "                    "; //$NON-NLS-1$
	
	 /**
     * 如果为null 返回 ""空字符串，否则返回原值
     * @param str
     * @return
     */
    public static String convertNullToString(String str){
    	return str == null ? "" : str;
    }
    
    public static String convertNullToString(Object obj){
    	return obj == null ? "" : obj.toString();
    }
    
    public static String toString(Object obj,String def){
    	return obj == null ? def : obj.toString();
    }
    
    public static String isEmpty(Object obj,String def){
    	return isEmpty(convertNullToString(obj)) ? def : obj.toString();
    }
    
    public static String toString(Object obj){
    	return toString(obj,"");
    }
    
    /**
   	 * 给字符串补足到指定的长度，从左边补足chr指定的字符
   	 * 
   	 * @param source
   	 *            源字符串
   	 * @param chr
   	 *            左边补足的字符
   	 * @param len
   	 *            最终长度
   	 * @return
   	 */
   	public static String lPad(String source, String chr, int len) {
   		int lenleft = len - source.length();
   		if (lenleft < 0) {
   			lenleft = 0;
   		}
   		return (strAdd(chr, lenleft) + source);
   	}

   	/**
   	 * 给字符串补足到指定的长度，从右边补足chr指定的字符
   	 * 
   	 * @param source
   	 *            源字符串
   	 * @param chr
   	 *            右边补足的字符
   	 * @param len
   	 *            最终长度
   	 * @return
   	 */
   	public static String rPad(String source, String chr, int len) {
   		int lenleft = len - source.length();
   		if (lenleft < 0) {
   			lenleft = 0;
   		}
   		return (source + strAdd(chr, lenleft));
   	}
   	
   	// 指定的字符串累加
   	public static String strAdd(String chr, int len) {
   		if (len > 0) {
   			StringBuffer ret = new StringBuffer(len);
   			for (int i = 0; i < len; i++) {
   				ret.append(chr);
   			}
   			return (ret.toString());
   		} else {
   			return "";
   		}
   	}
   	
   	/**
   	 * 得到字符串的字节长度。汉字占两个字节，字母占一个字节
   	 * 
   	 * @param source
   	 *            字符串
   	 * @return 字符串的字节长度
   	 */
   	public static int getLength(String source) {
   		if (source == null || source.equals("")) {
   			return 0;
   		}
   		int len = 0;
   		for (int i = 0; i < source.length(); i++) {
   			char c = source.charAt(i);
   			int highByte = c >>> 8;
   			len += highByte == 0 ? 1 : 2;
   		}
   		return len;
   	} 	
    
   	/**
   	 * 
   	 * <p>函数名称：joinWithoutNull        </p>
   	 * <p>功能说明：取出null值join
   	 *
   	 * </p>
   	 * * <pre>
     * StringUtils.join(null, *)                = ""
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = "a"
     * </pre>
   	 *<p>参数说明：</p>
   	 * @param array
   	 * @param separator
   	 * @return
   	 *
   	 * @date   创建时间：2017年8月11日
   	 * @author 作者：xds
   	 */
   	public static String joinWithoutNull(Object[] array, String separator){
		List<Object> newList = new ArrayList<Object>();
		for(Object obj : array){
			if(!isBlank(toString(obj))){
				newList.add(obj);
			}
		}
		return join(newList.toArray(), separator);
   		
   	}
   	
   	public static String joinWithoutNull(Object... array){
		List<Object> newList = new ArrayList<Object>();
		for(Object obj : array){
			if(!isBlank(toString(obj))){
				newList.add(obj);
			}
		}
		return join(newList.toArray());
   	}
   	
  	public static String joinWithoutNull(String separator,Object... array){
		List<Object> newList = new ArrayList<Object>();
		for(Object obj : array){
			if(!isBlank(toString(obj))){
				newList.add(obj);
			}
		}
		return join(newList.toArray(),separator);
   	}
   	
    /**
	 * Substitutes variables in <code>aString</code> with the environment
	 * values in the system properties
	 * 
	 * @param aString
	 *            the string on which to apply the substitution.
	 * @param systemProperties
	 *            the system properties to use
	 * @return the string with the substitution applied.
	 */
	public synchronized static final String environmentSubstitute(String aString, Map<String, String> systemProperties)
	{
		Map<String, String> sysMap = new HashMap<String, String>();
		
		synchronized(sysMap) {
			sysMap.putAll(Collections.synchronizedMap(systemProperties));
			
			aString = substituteWindows(aString, sysMap);
			aString = substituteUnix(aString, sysMap);
			aString = substituteHex(aString);
			return aString;
		}
	}
	
	public synchronized static final String environmentSubstituteByMapObj(String aString, Map<String, Object> systemProperties)
	{
		Map<String, String> sysMap = new HashMap<String, String>();
		
		synchronized(sysMap) {
			sysMap.putAll(Collections.synchronizedMap(MapUtilsExt.ObjectToStringMap(systemProperties)));
			
			aString = substituteWindows(aString, sysMap);
			aString = substituteUnix(aString, sysMap);
			aString = substituteHex(aString);
			return aString;
		}
	}
	
	public synchronized static final String environmentSubstituteByBean(String aString,Object objectBean){
		try {
			return environmentSubstituteByMapObj(aString, BeanUtilsExt.forceGetPropertyMap(objectBean));
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException e) {
			// TODO Auto-generated catch block
			return aString;
		}
	}
 
	
	/**
	 * Substitutes hex values in <code>aString</code> and convert them to operating system char equivalents in the return string.
	 * Format is $[01] or $[6F,FF,00,1F] 
	 * Example: "This is a hex encoded six digits number 123456 in this string: $[31,32,33,34,35,36]"
	 * 
	 * @param aString
	 *            the string on which to apply the substitution.
	 * @return the string with the substitution applied.
	 */
	public static String substituteHex(String aString)
	{
		if (aString == null)
			return null;

		StringBuffer buffer = new StringBuffer();

		String rest = aString;

		// search for opening string
		int i = rest.indexOf(HEX_OPEN);
		while (i > -1)
		{
			int j = rest.indexOf(HEX_CLOSE, i + HEX_OPEN.length());
			// search for closing string
			if (j > -1)
			{
				buffer.append(rest.substring(0, i));
				String hexString = rest.substring(i + HEX_OPEN.length(), j);
				String[] hexStringArray=hexString.split(",");
				int hexInt;
				byte[] hexByte=new byte[1];
				for (int pos = 0; pos < hexStringArray.length; pos++) {
					try {
						hexInt = Integer.parseInt(hexStringArray[pos],16);
					} catch (NumberFormatException e) {
						hexInt=0; // in case we get an invalid hex value, ignore: we can not log here
					}
					hexByte[0]=(byte)hexInt;
					buffer.append(new String(hexByte));
				}
				rest = rest.substring(j + HEX_CLOSE.length());
			} else
			{
				// no closing tag found; end the search
				buffer.append(rest);
				rest = "";
			}
			// keep searching
			i = rest.indexOf(HEX_OPEN);
		}
		buffer.append(rest);
		return buffer.toString();
	}
	
	/**
	 * Substitutes variables in <code>aString</code>. Variables are of the
	 * form "${<variable name>}", following the usin convention. The values are
	 * retrieved from the given map.
	 * 
	 * @param aString
	 *            the string on which to apply the substitution.
	 * @param variables
	 *            a map containg the variable values. The keys are the variable
	 *            names, the values are the variable values.
	 * @return the string with the substitution applied.
	 */
	public static String substituteUnix(String aString, Map<String, String> variables)
	{
		return substitute(aString, variables, UNIX_OPEN, UNIX_CLOSE);
	}

	/**
	 * Substitutes variables in <code>aString</code>. Variables are of the
	 * form "%%<variable name>%%", following the windows convention. The values
	 * are retrieved from the given map.
	 * 
	 * @param aString
	 *            the string on which to apply the substitution.
	 * @param variables
	 *            a map containg the variable values. The keys are the variable
	 *            names, the values are the variable values.
	 * @return the string with the substitution applied.
	 */
	public static String substituteWindows(String aString, Map<String, String> variables)
	{
		return substitute(aString, variables, WINDOWS_OPEN, WINDOWS_CLOSE);
	}
	
	/**
	 * Substitutes variables in <code>aString</code>. Variable names are
	 * delimited by open and close strings. The values are retrieved from the
	 * given map.
	 * 
	 * @param aString
	 *            the string on which to apply the substitution.
	 * @param variablesValues
	 *            a map containg the variable values. The keys are the variable
	 *            names, the values are the variable values.
	 * @param open
	 *            the open delimiter for variables.
	 * @param close
	 *            the close delimiter for variables.
	 * @param recursion
	 *            the number of recursion (internal counter to avoid endless
	 *            loops)
	 * @return the string with the substitution applied.
	 */
	public static String substitute(String aString, Map<String, String> variablesValues, String open, String close,
			int recursion)
	{
		if (aString == null)
			return null;

		StringBuffer buffer = new StringBuffer();

		String rest = aString;

		// search for opening string
		int i = rest.indexOf(open);
		while (i > -1)
		{
			int j = rest.indexOf(close, i + open.length());
			// search for closing string
			if (j > -1)
			{
				String varName = rest.substring(i + open.length(), j);
				Object value = variablesValues.get(varName);
				if (value == null)
				{
					value = open + varName + close;
				} else
				{
					// check for another variable inside this value
					int another = ((String) value).indexOf(open); // check
					// here
					// first for
					// speed
					if (another > -1)
					{
						if (recursion > 50) // for safety: avoid recursive
						// endless loops with stack overflow
						{
							throw new RuntimeException("Endless loop detected for substitution of variable: " + (String) value);
						}
						value = substitute((String) value, variablesValues, open, close, ++recursion);
					}
				}
				buffer.append(rest.substring(0, i));
				buffer.append(value);
				rest = rest.substring(j + close.length());
			} else
			{
				// no closing tag found; end the search
				buffer.append(rest);
				rest = "";
			}
			// keep searching
			i = rest.indexOf(open);
		}
		buffer.append(rest);
		return buffer.toString();
	}
	
	/**
	 * Substitutes variables in <code>aString</code>. Variable names are
	 * delimited by open and close strings. The values are retrieved from the
	 * given map.
	 * 
	 * @param aString
	 *            the string on which to apply the substitution.
	 * @param variablesValues
	 *            a map containing the variable values. The keys are the variable
	 *            names, the values are the variable values.
	 * @param open
	 *            the open delimiter for variables.
	 * @param close
	 *            the close delimiter for variables.
	 * @return the string with the substitution applied.
	 */
	public static String substitute(String aString, Map<String, String> variablesValues, String open, String close)
	{
		return substitute(aString, variablesValues, open, close, 0);
	}
	
	/**
     * 
     * <p>函数名称： moblieFormat       </p>
     * <p>功能说明： 手机号格式化 隐藏中间四位
     *
     * </p>
     *<p>参数说明：</p>
     * @param moblie
     * @return
     *
     * @date   创建时间：2016年5月30日
     * @author 作者：谢德寿
     */
    public static String moblieFormat(String moblie){
    	if(!isMoblie(moblie)) return moblie;
    	return moblie.substring(0, 3) + "****" + moblie.substring(7);
    	
    }
    /**
     * 
     * <p>函数名称：        </p>
     * <p>功能说明：判断是否是一个手机号
     *       13,14,15,17,18开头手机号
     * </p>
     *<p>参数说明：</p>
     * @param moblie
     * @return
     *
     * @date   创建时间：2016年5月30日
     * @author 作者：谢德寿
     */
    public static boolean isMoblie(String moblie){
    	if(moblie == null || moblie.trim().length() ==0){
    		return false;
    	}
    	return REGEX_MOBLIE.matcher(moblie).matches();
    }
    
    public static boolean isIdCard(String idCard){
    	if(idCard == null || idCard.trim().length() == 0){
    		return false;
    	}
    	return REGEX_ID_CARD.matcher(idCard).matches();
    }
    
    /**
     * 判断是不是一个合法的电子邮件地址
     * 
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }
    
	private static final Pattern REGEX_MOBLIE = Pattern.compile("^((1[3-5,7-8][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    /**
     * 正则表达式：验证身份证
     */
    private static final Pattern REGEX_ID_CARD = Pattern.compile("(^\\d{18}$)|(^\\d{15}$)");
           
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    public static String strWithQuotes(String... strs){
    	List<String> list = new ArrayList<String>();
    	for(String str : strs){    		
    		list.add("'" + str +"'");
    	}
    	return join(list.toArray(), ",");
    }
    
    /**
     * 
     * <p>函数名称：  formatString      </p>
     * <p>功能说明：替换{0}{1}为指定的字符串 args 
     *
     * </p>
     *<p>参数说明：</p>
     * @param pattern
     * @param args
     * @return
     *
     * @date   创建时间：2017年6月28日
     * @author 作者：xds
     */
    public static String formatString(String pattern,Object... args){
    	return MessageFormat.format(pattern, args);
    }
    
   	public static void main(String[] args) {
		System.out.println(convertNullToString(3));
		System.out.println(lPad("d", "0", 3));
		System.out.println(getLength("测试"));
		System.out.println("测试".length());
		System.out.println();
		Map<String,String> params = new HashMap<>();
		params.put("a", "a");
		params.put("b", "b");
		params.put("c", "${a}-${b}");
		System.out.println(environmentSubstitute("${a}", params));
		System.out.println(environmentSubstitute("${b}", params));
		System.out.println(environmentSubstitute("${c}", params));
		
	}
}
