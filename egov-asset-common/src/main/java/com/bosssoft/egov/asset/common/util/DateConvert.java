package com.bosssoft.egov.asset.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 *
 * @ClassName 类名：DateConvert
 * @Description 功能说明：
 *              <p>
 *              TODO
 *              </p>
 ************************************************************************
 * @date 创建日期：2016年11月11日
 * @author 创建人：xds
 * @version 版本号：V1.0
 *          <p>
 ***************************          修订记录*************************************
 * 
 *          2016年11月11日 xds 创建该类功能。
 *
 ***********************************************************************
 *          </p>
 */
public class DateConvert implements Converter {

	public Object convert(Class type, Object value) {
		String p = (String) value;
		if (p == null || p.trim().length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(p.trim());
		} catch (Exception e) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(p.trim());
			} catch (ParseException ex) {
				return null;
			}
		}
	}

}
