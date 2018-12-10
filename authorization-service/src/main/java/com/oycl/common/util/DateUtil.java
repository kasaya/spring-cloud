package com.oycl.common.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil extends DateUtils {

	/**
	 * 以时间戳的方式添加时间（单位：小时）
	 * @param timestamp 时间戳
	 * @param hours 小时
	 * @return 添加后的时间戳
	 */
    public static long addTimeStampbyHours(long timestamp, int hours){
		return DateUtil.toCalendar(DateUtil.addHours(new Date(timestamp),hours)).getTimeInMillis()/1000;
	}
}