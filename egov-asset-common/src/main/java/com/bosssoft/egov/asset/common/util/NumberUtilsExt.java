package com.bosssoft.egov.asset.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;

/** 
*
* @ClassName   类名：NumberUtilsExt 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年11月15日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年11月15日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class NumberUtilsExt extends NumberUtils{

	/**
	 * 
	 * <p>函数名称：    add    </p>
	 * <p>功能说明： 参数为null 时 默认为0 
	 *    为返回原值 请重新接收此对象
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param values
	 * @return
	 *
	 * @date   创建时间：2016年11月27日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static BigDecimal add(BigDecimal... values){
		BigDecimal result = BigDecimal.ZERO;
		for(BigDecimal value : values){
			if(value == null) continue;
			result = result.add(value);
		}
		return result;
	}
	
	/**
	 * 
	 * <p>函数名称：  subtract      </p>
	 * <p>功能说明： 第一个参数为第一个计算元素	 *    
	 *       参数为null 时 默认为0 
	 *    为返回原值 请重新接收此对象
	 * </p>
	 *<p>参数说明：</p>
	 * @param values
	 * @return
	 *
	 * @date   创建时间：2016年11月27日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static BigDecimal subtract(BigDecimal... values){
		if(values == null || values.length == 0){
			return BigDecimal.ZERO;
		}
		BigDecimal result = values[0] == null ? BigDecimal.ZERO : values[0];
		for(int i = 1; i < values.length ; i++){
			if(values[i] == null) continue;
			result = result.subtract(values[i]);
		}
		return result;
	}
	
	public static String formatterNumber(BigDecimal bigDecimal){
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern(",##0.00");
		return myformat.format(bigDecimal.doubleValue());
	}
	
	public static BigDecimal nullToZero(BigDecimal bigDecimal){
		return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
	}
	
	public static int toInt(Integer value, int defaultValue){
		if(value == null) return defaultValue;
		return value.intValue();
	};
	
	public static void main(String[] args) {
		System.out.println(formatterNumber(new BigDecimal("1478524545.88")));
		System.out.println(formatterNumber(new BigDecimal("0.25")));
		System.out.println(formatterNumber(new BigDecimal("1450.25")));
		System.out.println(subtract(null,new BigDecimal("98")));
		System.out.println(add(null,null,new BigDecimal("98")));
	}

}

