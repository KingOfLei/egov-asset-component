package com.bosssoft.egov.asset.common.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.bosssoft.egov.asset.common.util.BeanUtilsExt;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;

/** 
*
* @ClassName   类名：CollectionUtilsExt 
* @Description 功能说明：
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年12月19日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年12月19日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class CollectionUtilsExt extends CollectionUtils{

	/**
	 * 
	 * <p>函数名称：  removeRepeat      </p>
	 * <p>功能说明： 删除集合中某些字段重复的记录
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param list
	 * @param params
	 *
	 * @date   创建时间：2016年12月19日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static <T> void removeRepeat(Collection<T> list, String... params){
		Iterator<T> iter = list.iterator();
		Set<String> hashSet = new HashSet<String>();
		List<T> newList = new ArrayList<>();
		while(iter.hasNext()){
			 T item = iter.next();
			 String[] values = {};
			try {
				values = BeanUtilsExt.getBeanValues(item, params);
			} catch (Exception e) {
				
			}
			 if(hashSet.add(StringUtilsExt.join(values))){
				 newList.add(item);
			 }
		 }
		list.clear();
		list.addAll(newList);
	}
}
