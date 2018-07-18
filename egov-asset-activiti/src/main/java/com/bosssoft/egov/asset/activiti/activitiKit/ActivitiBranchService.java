package com.bosssoft.egov.asset.activiti.activitiKit;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.bosssoft.egov.asset.activiti.entity.ActivitiBranchControl;
import com.bosssoft.egov.asset.activiti.entity.ActivitiCondition;
import com.bosssoft.egov.asset.common.util.MapUtilsExt;
import com.bosssoft.platform.common.utils.StringUtils;

/**
 *
 * @ClassName 类名：ActivitiAmcServiceImpl
 * @Description 功能说明：流程分支图判断条件
 ************************************************************************
 * @date 创建日期：2016年11月2日
 * @author 创建人：jinbiao
 * @version 版本号：V1.0
 *          <p>
 *          2016年11月2日 jinbiao 创建该类功能。
 */

@SuppressWarnings("unchecked")
public class ActivitiBranchService implements Serializable {

	private static final long serialVersionUID = 5944929764321390635L;

	public Integer print(Integer v) {
		return v;
	}

	public Integer type(Map<String, Object> map) {
		String type = MapUtilsExt.getString(map, "bill_type");
		if (type.equals("tdfw")) {
			return 1;
		} else
			return 0;
	}
	
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 *  a==1&&b!=2&&c!=3
		b==2&&c!=3
		c==3
		c的优先级最高
		b的优先级其次
		a的优先级最低
	 *
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param map
	 * @return
	 *
	 * @date   创建时间：2017年3月5日
	 * @author 作者：jinbiao
	 */
	
	public Integer system(Map<String, Object> map) {
		String type = MapUtilsExt.getString(map, "bill_system");
		if (type.equals("in")) {
			return 1;
		} else
			return 0;
	}

	/**
	 * 
	 * <p>
	 * 函数名称：返回金额大小，用于金额大小比较
	 * </p>
	 * <p>
	 * 功能说明：
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param map
	 * @return
	 *
	 * @date 创建时间：2017年3月3日
	 * @author 作者：jinbiao
	 */
	public double moneyAmount(Map<String, Object> map, String key) {
		Object total = MapUtilsExt.getObject(map, key);
		if (total instanceof Number) {
			return Double.parseDouble(total.toString());
		}
		return 0;
	}

	public double areaAmount(Map<String, Object> map, String key) {
		Object total = MapUtilsExt.getObject(map, key);
		if (total instanceof Number) {
			return Double.parseDouble(total.toString());
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * 函数名称： Number类比较大小
	 * </p>
	 * <p>
	 * 功能说明：
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param list
	 * @return
	 *
	 * @date 创建时间：2017年3月3日
	 * @author 作者：jinbiao
	 */
	public double max(Map<String, Object> map, String key) {
		List<Object> list = (List<Object>) map.get(key);
		double record = 0;
		if (list.size() > 0) {
			if (list.get(0) instanceof Number) {
				record = Double.parseDouble(list.get(0).toString());
			} else {
				return 0;
			}
		}
		for (Object object : list) {
			double number = Double.parseDouble(object.toString());
			if (record < number) {
				record = number;
			}
		}
		return record;
	}

	public double min(Map<String, Object> map, String key) {
		List<Object> list = (List<Object>) map.get(key);
		double record = 0;
		// 判断为Number类型，否则返回0
		if (list.size() > 0) {
			if (list.get(0) instanceof Number) {
				record = Double.parseDouble(list.get(0).toString());
			} else {
				return 0;
			}
		}
		for (Object object : list) {
			double number = Double.parseDouble(object.toString());
			if (record > number) {
				record = number;
			}
		}
		return record;
	}

	/**
	 * 
	 * <p>
	 * 函数名称：判断是否为某种类型的业务标识
	 * </p>
	 * <p>
	 * 功能说明：
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param list
	 *            业务数据带来的可能包含的类型
	 * @param target
	 *            配置xml文件里面
	 * @return
	 *
	 * @date 创建时间：2017年3月3日
	 * @author 作者：jinbiao
	 */
	public Object judgeType(Map<String, Object> map, Object target, String key) {
		List<Object> list = (List<Object>) map.get(key);
		if (list != null && list.size() > 0) {
			if (list != null && list.size() > 0) {
				if (list.contains(target)) {
					return 1;
				}
			}
		}
		return 0;
	}

	/**
	 * 
	 * <p>
	 * 函数名称：判断系统内外标识
	 * </p>
	 * <p>
	 * 功能说明：
	 *
	 * </p>
	 * <p>
	 * 参数说明：
	 * </p>
	 * 
	 * @param list
	 *            业务数据带来的可能包含的类型
	 * @param target
	 *            配置xml文件里面
	 * @return
	 *
	 * @date 创建时间：2017年3月3日
	 * @author 作者：jinbiao
	 */
	public Object judgeSystem(Map<String, Object> map, Object target, String key) {
		List<Object> list = (List<Object>) map.get(key);
		if (list != null && list.size() > 0) {
			if (list.contains(target)) {
				return 1;
			}
		}
		return 0;
	}
	
	public Object yesOrNo(Map<String, Object> map, Object target, String key){
		List<Object> list = (List<Object>) map.get(key);
		if (list != null && list.size() > 0) {
			if (list.contains(target)) {
				return 1;
			}
		}
		return 0;
	}
	//流程分支条件
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明：
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param map  条件函数
	 * @param keyWord 目标所属的orgCode
	 * @param typeCode 取出控制List中，某个对应的实体，包含功能
	 * @return
	 *
	 * @date   创建时间：2017年3月27日
	 * @author 作者：jinbiao
	 */
	public Object branchControl(Map<String, Object> map, String keyWord, String typeCode) {
		// 取出数据库中相应的条件，在list中
		List<ActivitiBranchControl> list = (List<ActivitiBranchControl>) map.get(ActivitiCondition.BRANCH_CONTROL.getCode());
		// 取出业务关键字，10100001
		Object obj = map.get(keyWord);
		if (obj instanceof String) {
			String targetValue = MapUtilsExt.getString(map, keyWord);
			if (StringUtils.isNotNullAndBlank(targetValue)) {
				ActivitiBranchControl targetControl = null;
				for (ActivitiBranchControl activitiBranchControl : list) {
					if (typeCode.equals(activitiBranchControl.getTypeCode())) {
						targetControl = activitiBranchControl;
						break;
					}
				}
				if (targetControl != null) {
					String[] targetValues = targetControl.getTypeValue().split("\\,");
					List<String> targetList = Arrays.asList(targetValues);
					for (String string : targetList) {
						if (targetValue.contains(string)) {
							return 1;
						}
					}
				}
			}
			return 0;
		}
		if(obj instanceof List){
			//
			List<String> objList = (List<String>) map.get(keyWord);
			if (objList != null && objList.size() > 0) {
				ActivitiBranchControl targetControl = null;
				for (ActivitiBranchControl activitiBranchControl : list) {
					if (typeCode.equals(activitiBranchControl.getTypeCode())) {
						targetControl = activitiBranchControl;
						break;
					}
				}
				if (targetControl != null) {
					String[] targetValues = targetControl.getTypeValue().split("\\,");
					List<String> targetList = Arrays.asList(targetValues);
					for (String string : targetList) {
						if (objList.contains(string)) {
							return 1;
						}
					}
				}
			}
			return 0;
		}
		return 0;
	}
	
	public Object equal(Map<String, Object> map, String keyWord, String typeCode) {
		// 取出数据库中相应的条件，在list中
		List<ActivitiBranchControl> list = (List<ActivitiBranchControl>) map.get(ActivitiCondition.BRANCH_CONTROL.getCode());
		// 取出业务关键字，10100001
		Object obj = map.get(keyWord);
		if (obj instanceof String) {
			String targetValue = MapUtilsExt.getString(map, keyWord);
			if (StringUtils.isNotNullAndBlank(targetValue)) {
				ActivitiBranchControl targetControl = null;
				for (ActivitiBranchControl activitiBranchControl : list) {
					if (typeCode.equals(activitiBranchControl.getTypeCode())) {
						targetControl = activitiBranchControl;
						break;
					}
				}
				if (targetControl != null) {
					String[] targetValues = targetControl.getTypeValue().split("\\,");
					List<String> targetList = Arrays.asList(targetValues);
					for (String string : targetList) {
						if (targetValue.equals(string)) {
							return 1;
						}
					}
				}
			}
			return 0;
		}
		if(obj instanceof List){
			//
			List<String> objList = (List<String>) map.get(keyWord);
			if (objList != null && objList.size() > 0) {
				ActivitiBranchControl targetControl = null;
				for (ActivitiBranchControl activitiBranchControl : list) {
					if (typeCode.equals(activitiBranchControl.getTypeCode())) {
						targetControl = activitiBranchControl;
						break;
					}
				}
				if (targetControl != null) {
					String[] targetValues = targetControl.getTypeValue().split("\\,");
					List<String> targetList = Arrays.asList(targetValues);
					for (String string : targetList) {
						if (objList.contains(string)) {
							return 1;
						}
					}
				}
			}
			return 0;
		}
		return 0;
	}
	
	/**
	 * 单位判断，目测没什么用，先写着
	 */
//	public Object orgCode(Map<String, Object> map, Object target, String key){
//		List<Object> list = (List<Object>) map.get(key);
//		if (list.contains(target)) {
//			return 1;
//		}
//		return 0;
//	} 
	/**
	 * 根据单位判断
	 */
	public Object orgCode(Map<String, Object> map, String key, String orgCodeList){
		String[] orgCodes = orgCodeList.split("\\,");
		List<String> listsss = Arrays.asList(orgCodes);//将符合条件的单位list
		if (listsss != null && listsss.size() > 0) {
			String startOrgCode = (String) map.get(key);
			if (StringUtils.isNotNullAndBlank(startOrgCode)) {
				if (listsss.contains(startOrgCode)) {
					return 1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * 根据数据库中的配置项进行条件判断
	 */
	public Object orgCodeBySql(Map<String, Object> map, String key, String sqlKey){
		String startOrgCode = (String)map.get(key);
		List<Object> sqlList = (List<Object>) map.get(sqlKey);
		if (sqlList != null && sqlList.size() > 0) {
			if (sqlList.contains(startOrgCode)) {
				return 1;
			}
		}
		return 0;
	}
	
	public Object reason(Map<String, Object> map, String key, String reasonList) {
		String[] reasons = reasonList.split("\\,");//匹配的关键字从流程文件里面读取
		List<String> reasonListss = Arrays.asList(reasons);// 将原因转为list
		if (reasonListss != null && reasonListss.size() > 0) {
			String reason = (String) map.get(key);//前端获取的原因
			if (StringUtils.isNotNullAndBlank(reason)) {
				for (String keyWord : reasonListss) {
					if(reason.contains(keyWord)){//如果前端获取的某个句子里面包含关键字
						return 1;
					}
				}
			}
		}
		return 0;
	}
	
	public Object beginWith(Map<String, Object> map, String keyWord, String typeCode) {
		List<ActivitiBranchControl> list = (List<ActivitiBranchControl>) map.get(ActivitiCondition.BRANCH_CONTROL.getCode());
		// 取出业务关键字，10100001
		Object obj = map.get(keyWord);
		if (obj instanceof String) {
			String targetValue = MapUtilsExt.getString(map, keyWord);
			if (StringUtils.isNotNullAndBlank(targetValue)) {
				ActivitiBranchControl targetControl = null;
				for (ActivitiBranchControl activitiBranchControl : list) {
					if (typeCode.equals(activitiBranchControl.getTypeCode())) {
						targetControl = activitiBranchControl;
						break;
					}
				}
				if (targetControl != null) {
					String[] targetValues = targetControl.getTypeValue().split("\\,");
					List<String> targetList = Arrays.asList(targetValues);
					for (String string : targetList) {
						if (targetValue.startsWith(string)) {
//							if (targetValue.contains(string)) {
							return 1;
						}
					}
				}
			}
			return 0;
		}
		if(obj instanceof List){
			List<String> listBeginSource = (List<String>) map.get(keyWord);
			if (listBeginSource != null && listBeginSource.size() > 0) {
				ActivitiBranchControl targetControl = null;
				for (ActivitiBranchControl activitiBranchControl : list) {
					if (typeCode.equals(activitiBranchControl.getTypeCode())) {
						targetControl = activitiBranchControl;
						break;
					}
				}
				if (targetControl != null) {
					String[] targetValues = targetControl.getTypeValue().split("\\,");
					List<String> targetList = Arrays.asList(targetValues);
					for (int i = 0, leni = listBeginSource.size(); i < leni; i++) {
						for (int j = 0, lenj = targetList.size(); j < lenj; j++) {
							if (listBeginSource.get(i).startsWith(targetList.get(j))) {
								return 1;
							}
						}
					}
				}
			}
		}
		return 0;
	}
}
