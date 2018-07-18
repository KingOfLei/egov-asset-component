package com.bosssoft.egov.asset.common.util;

import org.apache.commons.lang.StringUtils;

import com.bosssoft.platform.common.utils.IdGenerator;
/** 
*
* @ClassName   类名：CodeGenerator 
* @Description 功能说明：生成编码公告工具类 如级长编码等
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
public class CodeGenerator {
	
	/**
	 * 
	 * <p>函数名称：getSubCodeByPcode        </p>
	 * <p>功能说明：设置子类的编码
	 *
	 * </p>
	 * @log 修订历史
	 * <p>xds 2016-11-05 修改 bug 
	 *   1、当使用replaceAll 函数时，分隔符是正则表达式特殊字符时替换问题
	 *   2、当前级次为最后一个级次时切有分隔符时 报错异常
	 *</p>
	 *<p>参数说明：</p>
	 * @param pCode        父类编码
	 * @param maxCode      最大编码
	 * @param ruleConfigValue  级长
	 * @param rank         子类级次
	 * @param splitStr     分隔符
	 * @return
	 *
	 * @date   创建时间：2016年4月8日
	 * @author 作者：JTT
	 * 
	 */
	public static String getNextOrderCode(String pCode, String maxCode, String ruleConfigValue, Integer rank, String splitStr) {
		
		if(StringUtilsExt.isEmpty(pCode) || "-".equals(pCode)){
			pCode = "0";
		} 
		// 去掉编码间特殊分割符
		if (StringUtilsExt.isNotEmpty(splitStr)) {
			//
//			if (pCode.contains(splitStr)) {
//				pCode = pCode.replaceAll(splitStr, "");
//			}
			pCode = StringUtilsExt.remove(StringUtilsExt.convertNullToString(pCode), splitStr);
			maxCode = StringUtilsExt.remove(StringUtilsExt.convertNullToString(maxCode), splitStr);
		}
		String subCode = "";
		// 根据级次获取子类自身编码长度
		int curLen = Integer.parseInt(ruleConfigValue.substring((rank-1), rank));
		// 根据最大编码获取子类编码
		if (maxCode == null || "".equals(maxCode)) {
			subCode = StringUtilsExt.lPad("1", "0", curLen);
		}else {
			if ("0".equals(pCode)) {
				subCode = StringUtilsExt.lPad(String.valueOf(Integer.parseInt(maxCode) + 1), "0", curLen);
			}else {
				subCode = StringUtilsExt.lPad(String.valueOf(Integer.parseInt(maxCode.substring(pCode.length())) + 1), "0", curLen);
			}
		}
		// 根据父类编码判断是否根节点
		if (!"0".equals(pCode)) {
			subCode = pCode.concat(subCode);
		}
		// 判断编码之间是否需要用分隔符分开，如：01.001.001
		int beginIndex = 0;
		int endIndex = Integer.parseInt(ruleConfigValue.substring(0, 1));
		String[] codes = new String[rank];
		if (StringUtilsExt.isNotEmpty(splitStr)) {
			for (int i = 0; i < rank; i++) {
				int j = i+1;
				codes[i] = subCode.substring(beginIndex, endIndex);
				beginIndex = endIndex; 				
				if( j+1 <= rank) {//最后一位时加保护
				  endIndex += Integer.parseInt(ruleConfigValue.substring(j, j+1));
				}
			}
			subCode = "";
			for (int i = 0; i < codes.length; i++) {
				subCode += codes[i].concat(splitStr);
			}
			//删除最后一个分隔符
			subCode = subCode.substring(0, subCode.length() - 1);
		}
		return subCode;
	}
	
	/**
	 * 
	 * <p>函数名称：getSubCodeByPcode        </p>
	 * <p>功能说明：根据父类编码和级长获取子类编码
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param pCode        父类编码
	 * @param maxCode      最大编码
	 * @param ruleConfigValue  级长
	 * @param rank         子类级次
	 * @return
	 *
	 * @date   创建时间：2016年4月27日
	 * @author 作者：JTT
	 */
	public static String getSubCodeByPcode(String pCode, String maxCode, String configValue, Integer rank){
		return getNextOrderCode(pCode, maxCode, configValue, rank, null);
	}
	
    public static void main(String[] args) {
    	// TODO Auto-generated method stub
        System.out.println("123".substring(3-1, 3));
        System.out.println("".length());
        System.out.println(StringUtils.remove("1.12", "."));
        System.out.println(CodeGenerator.getNextOrderCode("11.122.111", "11.122.111.1892", "2334", 4,"."));
        
        System.out.println(IdGenerator.getInstance(3).nextLongId());
    }
}
