package com.bosssoft.egov.asset.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bosssoft.platform.common.utils.DateUtils;

/** 
*
* @ClassName   类名：DateUtilsExt 
* @Description 功能说明：扩展com.bosssoft.platform.common.utils.DateUtils工具类
* <p>
* TODO
*</p>
************************************************************************
* @date        创建日期：2016年10月24日
* @author      创建人：xds
* @version     版本号：V1.0
*<p>
***************************修订记录*************************************
* 
*   2016年10月24日   xds   创建该类功能。
*
***********************************************************************
*</p>
*/
public class DateUtilsExt extends DateUtils{
   
	/**
	 * 
	 * <p>函数名称：  getNowDate      </p>
	 * <p>功能说明： 获取当前日期（yyyyMMdd）格式
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
	public static String getNowDate(){
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		return simple.format(date);
	}
	
	/**
	 * 
	 * <p>函数名称：  getNowDateTime      </p>
	 * <p>功能说明： 获取统一格式化后的当前系统时间（yyyyMMddHHmmssSSS）
	 *
	 * </p>
	 *<p>参数说明：</p>
	 * @return
	 *
	 * @date   创建时间：2016年10月24日
	 * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
	 */
    public static String getNowDateTime() {
    	SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	Date date=new Date();
    	return formatter.format(date);
    }
    
    /**
     * 
     * <p>函数名称：getNowYear        </p>
     * <p>功能说明：获取当前年份
     *
     * </p>
     *<p>参数说明：</p>
     * @return
     *
     * @date   创建时间：2016年10月24日
     * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
     */
    public static String getNowYear(){
    	return formatDate(new Date(), "yyyy");
    }
    
    /**
     * 
     * <p>函数名称：   getNowMonth     </p>
     * <p>功能说明：  获取当前月份
     *
     * </p>
     *<p>参数说明：</p>
     * @return
     *
     * @date   创建时间：2016年10月24日
     * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
     */
    public static String getNowMonth(){
    	return formatDate(new Date(), "MM");
    }
    
    /**
     * 
     * <p>函数名称：  getNowDay      </p>
     * <p>功能说明： 获取当前日期（日）
     *
     * </p>
     *<p>参数说明：</p>
     * @return
     *
     * @date   创建时间：2016年10月24日
     * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
     */
    public static String getNowDay(){
    	return formatDate(new Date(), "dd");
    }
    
    /**
     * 
     * <p>函数名称：getNowDateTime2        </p>
     * <p>功能说明：获取当前时间（HHmmss）
     *
     * </p>
     *<p>参数说明：</p>
     * @return
     *
     * @date   创建时间：2016年10月24日
     * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
     */
    public static String getNowDateTime2(){
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		Date date = new Date();
		return formatter.format(date);
    }
    
    /**
     * 
     * <p>函数名称： getNowDateTime3       </p>
     * <p>功能说明： 获取当前时间（HH:mm:ss）
     *
     * </p>
     *<p>参数说明：</p>
     * @return
     *
     * @date   创建时间：2016年10月24日
     * @author 作者：xds (mailto:xiedeshou@bosssoft.com.cn)
     */
    public static String getNowDateTime3(){
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return formatter.format(date);
    }
    public static String getNowDateTime4(){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    	Date date = new Date();
    	return formatter.format(date);
    }
    
    public static String formatDate(long dateStr,String pattern){
    	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    	return formatter.format(dateStr);
    }
    
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
    
    public static String[] getCurrentSeasonDate(){
    	int nSeason = getSeason(new Date());
    	String[] seasonDate = new String[2];
        if (nSeason == 1) {// 第一季度  
        	seasonDate[0] = "0101";
        	seasonDate[1] = "0331";
        } else if (nSeason == 2) {// 第二季度  
        	seasonDate[0] = "0401";
        	seasonDate[1] = "0630";
        } else if (nSeason == 3) {// 第三季度  
        	seasonDate[0] = "0701";
        	seasonDate[1] = "0930";
        } else if (nSeason == 4) {// 第四季度  
        	seasonDate[0] = "1001";
        	seasonDate[1] = "1231";
        }  
        return seasonDate;  
    }
    
    public static String[] getCurrentMonthRange(){
        String[] range = {getCurrentMonthFirstDay(),getCurrentMonthLastDay()};
    	return range;
    }
    
    public static int getSeason(Date date) {  
    	  
        int season = 0;  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = 1;  
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = 2;  
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = 3;  
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = 4;  
            break;  
        default:  
            break;  
        }  
        return season;  
    }  
    
    public static String getCurrentMonthFirstDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd"); 
    	Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return format.format(c.getTime());
    }
    
    public static String getCurrentMonthLastDay(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(ca.getTime());
    }
        
    
    public static Date addMilliseconds(final Date date, final int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }
    
    public static Date addSeconds(final Date date, final int amount) {
        return add(date, Calendar.SECOND, amount);
    }
    
    public static Date addMinutes(final Date date, final int amount) {
        return add(date, Calendar.MINUTE, amount);
    }
    
    public static Date addHours(final Date date, final int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }
    
    public static Date addDays(final Date date, final int amount) {
    	return add(date, Calendar.DAY_OF_MONTH, amount);
    }
    
    /**
     * 
     * <p>函数名称：    addDays    </p>
     * <p>功能说明： 日期加减
     *
     * </p>
     *<p>参数说明：</p>
     * @param dateStr 支持格式 
     *  * 支持格式：
	 * <li>yyyy-MM-dd HH:mm:ss</li>
	 * <li>yyyy-MM-dd HH:mm</li>
	 * <li>yyyy-MM-dd</li>
	 * <li>yyyy/MM/dd HH:mm:ss</li>
	 * <li>yyyy/MM/dd HH:mm</li>
	 * <li>yyyy/MM/dd</li>
	 * <li>yyyyMMddHHmmss</li>
	 * <li>yyyyMMddHHmm</li>
	 * <li>yyyyMMdd</li>
	 * <li>E MMM dd HH:mm:ss z yyyy (Locale.US)</li>
     * @param amount 0 当天 +1 加一天 -1 减一天 以此类推
     * @return
     *
     * @date   创建时间：2017年1月3日
     * @author 作者：xds
     */
    public static Date addDays(String dateStr,final int amount){
    	return addDays(parseDate(dateStr), amount);
    }
    
    /**
     * 
     * <p>函数名称：addDays        </p>
     * <p>功能说明： 日期加减
     *
     * </p>
     *<p>参数说明：</p>
     * @param dateStr 日期值
     * @param pattern 日期值表达式
     * @param amount 加减天数
     * @return
     *
     * @date   创建时间：2017年1月3日
     * @author 作者：xds
     */
    public static Date addDays(String dateStr,String pattern,final int amount){
    	return addDays(parseDate(dateStr, pattern), amount);
    }
    
    public static Date addWeeks(final Date date, final int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }
    
    public static Date addMonths(final Date date, final int amount) {
        return add(date, Calendar.MONTH, amount);
    }
    
    public static Date addMonths(String dateStr, final int amount){
    	return addMonths(parseDate(dateStr), amount);
    }
    
    public static Date addMonths(String dateStr, String pattern, final int amount){
    	return addMonths(parseDate(dateStr, pattern), amount);
    }
    
    public static Date addYears(final Date date, final int amount) {
        return add(date, Calendar.YEAR, amount);
    }
    
    private static Date add(final Date date, final int calendarField, final int amount){
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
    }

    
    public static void main(String[] args) {
    	System.out.println(getNowDate());
    	System.out.println(getNowDateTime());
    	System.out.println(getNowDateTime2());
    	System.out.println(getNowDateTime3());
    	System.out.println(getNowDateTime4());
		System.out.println(getNowYear());
		System.out.println(getNowMonth());
		System.out.println(getNowDay());
		System.out.println(getCurrentDate());
		System.out.println(getCurrentTime());
		System.out.println(DateUtilsExt.formatDate(new Date(), "yyyyMMddHHmmssSSS"));
		System.out.println(formatDate(20161215232123L, "yyyy-MM-dd HH:mm:ss"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

		  Calendar c = Calendar.getInstance();    
	        c.add(Calendar.MONTH, 0);
	        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	        String first = format.format(c.getTime());
	        System.out.println(first);
	        
	        Calendar ca = Calendar.getInstance();    
	        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	        String last = format.format(ca.getTime());
	        System.out.println(last);
	        
	        System.out.println(DateUtilsExt.formatDate(parseDate("20170111154617808", "yyyyMMddHHmmssSSS"), "yyyy-MM-dd HH:mm"));
	}
}
