package com.bosssoft.egov.asset.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class OrderByUtil {

	/**
	 * 
	 * <p>
	 * 函数名称： sort
	 * </p>
	 * <p>
	 * 功能说明： o rderBy 类似sql语句中的order by 语句。比如，传来[name desc,code]，表示：
	 * 对bean对象，先按name属性的降序排列，然后按code属性的升序排列
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param collection
	 * @param orderBy
	 * @return
	 *
	 * @date 创建时间：2016年11月18日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> sort(Collection<T> collection, String orderBy) {
		if(collection == null) return null;
		List<T> list = (collection instanceof List) ? (List<T>) collection
				: new ArrayList<T>(collection);
		Collections.sort(list, new BeanComparor(orderBy));
		return list;

	}
}
