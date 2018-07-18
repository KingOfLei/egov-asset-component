package com.bosssoft.egov.asset.drc.entity;

import java.util.HashMap;
import java.util.Map;

/** 
*
* @ClassName   类名：Operator 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2017年1月10日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2017年1月10日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
/**
 * 操作符枚举
 * @author yangbo
 * @version 1.0 2014-2-27
 * @since 1.6
 */
public enum Operator {
	/*操作符常量*/
	/**
	 * =操作符
	 */
	EQUAL("=","等于"),
	
	/**
	 * <>操作符
	 */
	NOTEQUAL("<>","不等于"),
	
	/**
	 * >操作符
	 */
	GREATER(">","大于"),
	
	/**
	 * <操作符
	 */
	LESS("<","小于"),
	
	/**
	 * >=操作符
	 */
	GREATEREQUAL(">=","大于等于"),
	
	/**
	 * <=操作符
	 */
	LESSEQAUL("<=","小于等于"),
	
	
	/**
	 * like操作符，全模糊匹配
	 */
	LIKEANY("like","包含"),
	
	/**
	 * like操作符，开始匹配，结尾模糊
	 */
	LIKESTART("likes","左包含"),
	
	/**
	 * like操作符，结尾匹配，开始模糊
	 */
	LIKEEND("likee","右包含"),
	
	/**
	 * is null操作符
	 */
	ISNULL("isnull","为空值"),
	
	/**
	 * is not null操作符
	 */
	ISNOTNULL("isnotnull","不为空值"),
	
	/**
	 * not like ‘%...%’
	 */
	NOTLIKEANY("notlike","不包含"),

	/**
	 * in操作符号
	 */
	IN("in","列举");
	
	/**
	 * 操作符编码
	 */
	private String code;
	
	/**
	 * 操作符名称
	 */
	private String name;
	
	/**
	 * 构造方法
	 * @param code 编码
	 * @param name 名称
	 */
	private Operator(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	/**
	 * 取得类型编码
	 * @return 类型编码
	 */
	public String getCode(){
		return code;
	}
	
	/**
	 * 取得类型名称
	 * @return 类型名称
	 */
	public String getName(){
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getCode();
	}
	
	/**
	 * 根据编码获取操作符
	 * @param code 操作符编码
	 * @return 操作符
	 */
	public static Operator parse(String code) {
		code = code.toLowerCase();
        if(!strValMap.containsKey(code)) {
            throw new IllegalArgumentException("Unknown String Value: " + code);
        }
        return strValMap.get(code);
    }

    private static final Map<String, Operator> strValMap;
    static {
    	strValMap = new HashMap<String,Operator>();
        for(final Operator en : Operator.values()) {
        	strValMap.put(en.getCode(), en);
        }
    }
}
