package com.bosssoft.egov.asset.activiti.activitiKit;

import java.util.Calendar;
import java.util.Date;

import com.bosssoft.egov.asset.common.util.DateUtilsExt;
import com.bosssoft.platform.runtime.exception.BusinessException;

public class WorkdayUtils {
	
	public static int getWorkDay(String recordTime){
        Date begDate = DateUtilsExt.parseDate(recordTime, "yyyyMMddHHmmssSSS");
        Date endDate = DateUtilsExt.parseDate(DateUtilsExt.getNowDateTime(), "yyyyMMddHHmmssSSS");
        if (begDate.after(endDate))
            throw new BusinessException("日期范围非法");
        // 总天数
        int days = (int) ((endDate.getTime() - begDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
        // 总周数，
        int weeks = days / 7;
        int rs = 0;
        // 整数周
        if (days % 7 == 0) {
            rs = days - 2 * weeks;
        }
        else {
            Calendar begCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();
            begCalendar.setTime(begDate);
            endCalendar.setTime(endDate);
            // 周日为1，周六为7
            int beg = begCalendar.get(Calendar.DAY_OF_WEEK);
            int end = endCalendar.get(Calendar.DAY_OF_WEEK);
            if (beg > end) {
                rs = days - 2 * (weeks + 1);
            } else if (beg < end) {
                if (end == 7) {
                    rs = days - 2 * weeks - 1;
                } else {
                    rs = days - 2 * weeks;
                }
            } else {
                if (beg == 1 || beg == 7) {
                    rs = days - 2 * weeks - 1;
                } else {
                    rs = days - 2 * weeks;
                }
            }
        }
		return rs;
	}
	/**
	 * 
	 * <p>函数名称：        </p>
	 * <p>功能说明： 判断是否达到标准天数
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @param recordTime
	 * @return
	 *
	 * @date   创建时间：2017年6月20日
	 * @author 作者：jinbiao
	 */
	public static boolean isTarget(String recordTime, int checkDay) {
		int rs = getWorkDay(recordTime);
		if (rs >= checkDay) {
			return true;
		}
		return false;
	}
	
	public static int getDays(String beginTime ,String endTime){
		Date begDate = DateUtilsExt.parseDate(beginTime, "yyyyMMddHHmmssSSS");
        Date endDate = DateUtilsExt.parseDate(endTime, "yyyyMMddHHmmssSSS");
        if (begDate.after(endDate)){
        	throw new BusinessException("日期范围非法");
        }
        // 总天数
        int days = (int) ((endDate.getTime() - begDate.getTime()) / (24 * 60 * 60 * 1000)) + 1;
        
		return days;
	}
}
