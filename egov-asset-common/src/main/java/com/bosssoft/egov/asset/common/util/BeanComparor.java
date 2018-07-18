package com.bosssoft.egov.asset.common.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class BeanComparor implements Comparator {

	public List order = new ArrayList();

	/**
	 * 构造方法，将传来的字符串解析成排序对象。<br>
	 * 
	 * @param orderBy
	 *            类似sql语句中的order by 语句。比如，传来[name desc,code]，表示：<br>
	 *            对bean对象，先按name属性的降序排列，然后按code属性的升序排列
	 */
	@SuppressWarnings("unchecked")
	public BeanComparor(String orderBy) {
		String tmp = orderBy.trim();
		String[] orders = tmp.split(",");
		for (int i = 0; i < orders.length; i++) {
			order.add(new orderHolder(orders[i]));
		}

	}

	/**
	 * 根据给定的属性条件比较两个对象，以便进行对象排序。<br>
	 * 比较的依据是将属性的值转换为字符串，再进行字符串比较。
	 */
	
	public int compare(Object o1, Object o2) {
		String p1, p2;
		for (Iterator each = this.order.iterator(); each.hasNext();) {
			orderHolder order = (orderHolder) each.next();
			try {
				// 增加对Map类的排序 
				if (o1 instanceof Map) {
					Map mapTemp = (Map) o1;
					p1 = (String) mapTemp.get(order.getPropName());
				} else { // 对VO对象的排序
					p1 = BeanUtilsExt.getSimpleProperty(o1, order.getPropName());
				}
				if (o2 instanceof Map) {
					Map mapTemp = (Map) o2;
					p2 = (String) mapTemp.get(order.getPropName());
				} else {
					p2 = BeanUtilsExt.getSimpleProperty(o2, order.getPropName());
				}
				p1 = (p1 == null) ? "" : p1;
				p2 = (p2 == null) ? "" : p2;
				if (!p1.equals(p2)) {
					//是否升序
					return order.isAscend() ? p1.compareTo(p2) : p2.compareTo(p1);
				}
			} catch (Exception e) {
				throw new RuntimeException("获得对象属性时出错", e);
			}

		}

		return 0;

	}

	/**
	 * 排序属性持有对象。
	 * 
	 */
	private class orderHolder {
		private String propName; // 要排序的属性名称

		private boolean ascend = true; // 是否升序排列

		/**
		 * 构造方法，传来要排序的属性名，以及按升序还是降序排列
		 */
		public orderHolder(String propOrder) {
			String str = propOrder.trim();
			if (str.indexOf(" ") > -1) {
				int split = str.indexOf(" ");
				this.propName = str.substring(0, split);
				if ("desc".equalsIgnoreCase(str.substring(split + 1, str
						.length()))) {
					this.ascend = false;
				}
			} else {
				this.propName = str;
			}
		}

		public boolean isAscend() {
			return ascend;
		}


		public String getPropName() {
			return propName;
		}

	}
}
