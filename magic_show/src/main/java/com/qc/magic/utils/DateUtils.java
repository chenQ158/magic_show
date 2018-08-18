package com.qc.magic.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author john
 *
 */
public class DateUtils {
	
	public static String getStringOfFromDateToNow(Date date) {
		long distance = System.currentTimeMillis()/1000 - date.getTime()/1000;
		// 秒比较
		if (distance < 60) return String.format("%d秒", distance);
		distance /= 60;
		// 分比较
		if (distance < 60) return String.format("%d分钟", distance);
		distance /= 60;
		// 小时比较
		if (distance < 24) return String.format("%d小时", distance);
		distance /= 24;
		// 天比较
		if (distance < 7) return String.format("%d天", distance);
		
		// 周比较,即大于4周
		if (distance / 7 <= 5) return String.format("%d周", distance / 7);
		
		// 超过一个月小于一年的显示日期, 前面为了不减少精度,没有除,即distance仍然是天数
		if (distance / 30 < 12) return new SimpleDateFormat("mm/dd").format(date);
		
		// 如果超过一年显示全日期
		if (distance / 30 >= 12) return new SimpleDateFormat("yy/mm/dd").format(date);
		
		return new SimpleDateFormat("yy/mm/dd").format(date);
	}
}
