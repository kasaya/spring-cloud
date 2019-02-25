package com.oycl.util.normalutil;



import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间方法类
 * @author cango
 */
public class DateUtil extends DateUtils {

	private DateUtil(){
		throw new AssertionError();
	}

	public final static String YYYY_MM_DD = "yyyy-MM-dd";

	public final static String YYYYMMDD = "yyyyMMdd";

	public final static String YYMMDDHHMMSS = "yyMMddHHmmss";

	public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public final static String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";

	public final static String YYYYMMDD_T_HHMMSS_SSSXXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public final static String YYYYMM = "yyyyMM";
    
    public final static String YYYYMMDDHHMMSS2="yyyy/MM/dd HH:mm:ss";

	public final static String YYYY_MM_DD_CN = "yyyy年MM月dd日";


	/**开始时间*/
	public final static String FROM_TIME = "00:00:00";
    /**结束时间*/
	public final static String END_TIME = "23:59:59";



	public final static String DD = "dd";

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/** 返回带有中文 年、月、日日期 */
	public static String getDateCN() {
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return simdf.format(cal.getTime());
	}

	/** 根据传入的参数获得当前日期的day天后的日期 */
	public static String getDateByday(int day) {
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (day > 0) {
			return df.format(new Date(d.getTime() + (long) day * 24 * 60 * 60 * 1000)).replace("-", "");
		} else {
			return df.format(new Date(d.getTime() - (long) day * 24 * 60 * 60 * 1000)).replace("-", "");
		}

	}

	/**
	 * 根据传入的参数 获取 指定日期的指定天数后的日期
	 * 
	 * @param d
	 *            指定日期
	 * @param day
	 *            指定天数
	 */
	public static String getDateByday(Date d, int day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (day > 0) {
			return df.format(new Date(d.getTime() + (long) day * 24 * 60 * 60 * 1000)).replace("-", "");
		} else {
			return df.format(new Date(d.getTime() - (long) day * 24 * 60 * 60 * 1000)).replace("-", "");
		}
	}

	/**
	 * 所有区间日期
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> allBetweenDate(String startDate, String endDate) {
		List<String> allDate = new ArrayList<String>();
		SimpleDateFormat sfmt = new SimpleDateFormat(YYYY_MM_DD);
		try {
			Date dateStart = sfmt.parse(startDate);
			Date dateEnd = sfmt.parse(endDate);
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(dateStart);
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(dateEnd);
			calEnd.add(Calendar.DATE, 1);
			String date = "";
			while (!calStart.equals(calEnd)) {
				date = "";
				date = calStart.get(Calendar.YEAR) + "-";
				if ((calStart.get(Calendar.MONTH) + 1) < 10) {
					date += "0";
				}
				date += (calStart.get(Calendar.MONTH) + 1) + "-";
				if (calStart.get(Calendar.DAY_OF_MONTH) < 10) {
					date += "0";
				}
				date += calStart.get(Calendar.DAY_OF_MONTH);
				allDate.add(date);
				calStart.add(Calendar.DATE, 1);
			}
		} catch (ParseException e) {
		}
		return allDate;
	}

	/**
	 * 将指定格式的字符串化为时间格式
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date strToDate(String dateStr, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			Date datetime = dateFormat.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);

			return cal.getTime();

		} catch (ParseException e) {
			logger.error("系统异常",e);
		}
		return null;
	}

    /**
     * yyyyMMdd 转化为 Date类型
     * @param yyyyMMdd
     * @return
     */
    public static Date strToDate_yyyyMMdd(String yyyyMMdd){
        if(StringUtil.isEmpty(yyyyMMdd)){
            return null;
        }
        Calendar ca = Calendar.getInstance();
        ca.set(Integer.parseInt(yyyyMMdd.substring(0, 4)),
                Integer.parseInt(yyyyMMdd.substring(4, 6)) - 1,
                Integer.parseInt(yyyyMMdd.substring(6, 8)));

        return ca.getTime();
    }

    /**
     * yyyyMMdd HH:mm:ss 转化为 Date类型
     * @param yyyyMMddHHmmss
     * @return
     */
    public static Date strToDate_yyyyMMddHHmmss(String yyyyMMddHHmmss){
        if(StringUtil.isEmpty(yyyyMMddHHmmss)){
            return null;
        }
        Calendar ca = Calendar.getInstance();
        ca.set(Integer.parseInt(yyyyMMddHHmmss.substring(0, 4)),
                Integer.parseInt(yyyyMMddHHmmss.substring(4, 6)) - 1,
                Integer.parseInt(yyyyMMddHHmmss.substring(6, 8)),
                Integer.parseInt(yyyyMMddHHmmss.substring(8,10)),
                Integer.parseInt(yyyyMMddHHmmss.substring(10,12)),
                Integer.parseInt(yyyyMMddHHmmss.substring(12,14)));

        return ca.getTime();
    }

	/**
	 * 将时间格式化为指定格式的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 获取当前系统日期
	 *
	 * @return Date 当前系统日期
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 获取当前系统日期获取格式
	 *
	 * @return Date 当前系统日期
	 */
	public static Date getTime(int calendarType, int value) {
		Calendar calDay = Calendar.getInstance();
		calDay.add(calendarType, value);
		return calDay.getTime();
	}

	/**
	 * 获取当前系统日期获取格式
	 *
	 * @return Date 当前系统日期
	 */
	public static Date getTime(Date time, int calendarType, int value) {
		Calendar calDay = Calendar.getInstance();
		calDay.setTime(time);
		calDay.add(calendarType, value);
		return calDay.getTime();
	}

	/**
	 * 获取特定格式的当前系统日期
	 *
	 * @return Date 当前系统日期
	 */
	public static String getCurrentDate(String formatType) {
		Date now = Calendar.getInstance().getTime();
		return dateToStr(now, formatType);
	}

	/**
	 * 两个日期中相差的分钟数
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return 分钟数
	 */
	public static long minutesBetween(Date date1, Date date2) {

		long date1Time = 0;
		long date2Time = 0;
		if (date1 != null) {
			date1Time = date1.getTime();
		}
		if (date2 != null) {
			date2Time = date2.getTime();
		}
		return (long) ((date2Time - date1Time) / (1000 * 60));
	}

	/**
	 * 将时间转为 yyyyMMdd 格式
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToYYYMMDD(Date date) {
		return dateToStr(date, YYYYMMDD);
	}

	/**
	 * 将时间转为 yyyyMMddHHmmss 格式
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToYYYYMMDDHHMMSS(Date date) {
		return dateToStr(date, YYYYMMDDHHMMSS);
	}

    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr)
    {
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day =(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
        } catch (ParseException e)
        {
            // TODO 自动生成 catch 块
			logger.error("系统异常",e);
        }
        return day;
    }
}