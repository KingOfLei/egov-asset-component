package com.bosssoft.egov.asset.activiti;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

import com.bosssoft.egov.asset.activiti.entity.ActivitiComparator;
import com.bosssoft.egov.asset.activiti.entity.ActivitiTaskMoveComboboxShow;
import com.bosssoft.egov.asset.activiti.entity.HandleType;
import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.egov.asset.common.util.OrderByUtil;
import com.bosssoft.egov.asset.common.util.StringUtilsExt;
import com.bosssoft.platform.common.utils.StringUtils;

/**
 * Hello world!
 *
 */
public class App {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		String rl = "1,2,3,4,5";
		
		String[] aa = rl.split("\\,");

		System.out.println(aa.length);
		
		List<String> list = new  ArrayList<String>();
		
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		for(int i =0,len = list.size();i<len;i++){
			list.add(i+"");
			System.out.println(i);
			System.out.println(list.size());
		}
		
		
		// BigDecimal bd = new BigDecimal("111111111");
		//
		// String result = null;
		//
		// List<String> aaa = new ArrayList<String>();
		//
		// aaa.add("1");
		// aaa.add("2");
		// aaa.add("3");
		// aaa.add("4");
		//
		// Map<String,Object> map = new HashMap<String, Object>();
		//
		// System.out.println(map.isEmpty());
		//
		// String names = "";
		// for (int i = 0, len = aaa.size(); i < len; i++) {
		// names += aaa.get(i);
		// if(len > 1 && i < len - 1){
		// names += ",";
		// }
		// }
		//
		// String bbb = aaa.toString();
		// String[]ccc = bbb.split("\\,");
		// System.out.println(aaa.toString());
		//
		//
		// // String[]grantedGroups = result.split("\\;");
		// // String[]grantedUsers = result.split("\\;");
		//
		// String id = "USERewfq:1:FJDLASJ";
		//
		//
		//
		// // String aaa = id.split("\\:")[0];
		//
		// System.out.println(aaa);
		//
		// List<ActivitiTaskMoveComboboxShow> userTaskNameList = new
		// ArrayList<ActivitiTaskMoveComboboxShow>();
		// /*
		// * 排序问题， 字符
		// */
		// // 取出流程中按顺序的排列节点，然后排序
		//
		// // ActivitiTaskMoveComboboxShow a = new
		// ActivitiTaskMoveComboboxShow();
		// // a.setUserTaskId("u1");
		// // a.setUserTaskName("1");
		// // System.out.println(a.getUserTaskId()+"_____"+a.getUserTaskName());
		// // userTaskNameList.add(a);
		// // ActivitiTaskMoveComboboxShow b = new
		// ActivitiTaskMoveComboboxShow();
		// // b.setUserTaskId("u2");
		// // b.setUserTaskName("2");
		// // System.out.println(b.getUserTaskId()+"_____"+b.getUserTaskName());
		// // userTaskNameList.add(b);
		// // ActivitiTaskMoveComboboxShow c = new
		// ActivitiTaskMoveComboboxShow();
		// // c.setUserTaskId("u13");
		// // c.setUserTaskName("3");
		// // System.out.println(c.getUserTaskId()+"_____"+c.getUserTaskName());
		// // userTaskNameList.add(c);
		// // ActivitiTaskMoveComboboxShow d = new
		// ActivitiTaskMoveComboboxShow();
		// // d.setUserTaskId("u11");
		// // d.setUserTaskName("4");
		// //// StringUtilsExt.l
		// // System.out.println(d.getUserTaskId()+"_____"+d.getUserTaskName());
		// // userTaskNameList.add(d);
		//
		// // List<SortShowNode> ss = new ArrayList<SortShowNode>();
		// // SortShowNode s = new SortShowNode();
		// // s.setName("String1");
		// // s.setSequence(1);
		// // ss.add(s);
		// // SortShowNode s2 = new SortShowNode();
		// // s2.setName("String3");
		// // s2.setSequence(11);
		// // ss.add(s2);
		// // SortShowNode s1 = new SortShowNode();
		// // s1.setName("String2");
		// // s1.setSequence(2);
		// // ss.add(s1);
		//
		// // for(int i=0,len=ss.size();i<len;i++){
		// //
		// System.out.println(ss.get(i).getName()+"~~~~~~"+ss.get(i).getSequence());
		// // }
		// //
		//
		// String t = "20161221102224859";
		//
		// String time = DateUtilsExt.formatDate(DateUtilsExt.parseDate(t),
		// "yyyy-MM-dd HH:mm");
		//
		// System.out.println(time);
		//
		//
		//
		//
		// System.out.println("_______________________________");
		//
		// // Comparator comparator = new ActivitiComparator();
		// // Collections.sort(ss, comparator);
		//
		//
		// String us = "userTask11";
		//
		// String a = StringUtilsExt.substring(us, 8, us.length());
		//
		// System.out.println(a);
		//
		// // ss = OrderByUtil.sort(ss, "sequence desc");
		//
		//
		// // for(int i=0,len=ss.size();i<len;i++){
		// //
		// System.out.println(ss.get(i).getName()+"~~~~~~"+ss.get(i).getSequence());
		// // }
		// ////
		//
		//
		// // List<ActivitiTaskMoveComboboxShow> copy =
		// OrderByUtil.sort(userTaskNameList, "userTaskId desc");
		// //
		// // for(int i=0,len=userTaskNameList.size();i<len;i++){
		// //
		// System.out.println(copy.get(i).getUserTaskId()+"___"+copy.get(i).getUserTaskName());
		// // }
		//
		// System.out.println( "Hello World!" );
	}
}
