package com.ifenduo.coach.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.ifenduo.coach.bean.DateItem;

public class CalendarUtil {

	public static ArrayList<DateItem> getCurrentCalendar() {
		ArrayList<DateItem> list = new ArrayList<DateItem>();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
		Calendar currentWeekCal = getMonday(calendar.get(Calendar.DAY_OF_WEEK) - 1, (Calendar) calendar.clone());
		for (int j = 0; j < 7; j++) {
			DateItem dateItem = new DateItem();
			dateItem.day = currentWeekCal.get(Calendar.DAY_OF_MONTH) + "";
			dateItem.dayOfWeek = (currentWeekCal.get(Calendar.DAY_OF_WEEK) - 1) + "";
			list.add(dateItem);
			currentWeekCal.set(Calendar.DATE, currentWeekCal.get(Calendar.DATE) + 1);
		}
		return list;
	}
	public static Calendar getMonday(int dayOfWeek, Calendar calendar) {
		int current = calendar.get(Calendar.DATE);
		switch (dayOfWeek) {
			case 0 :
				calendar.set(Calendar.DATE, current - 6);
				break;
			case 1 :
				break;
			case 2 :
				calendar.set(Calendar.DATE, current - 1);
				break;
			case 3 :
				calendar.set(Calendar.DATE, current - 2);
				break;
			case 4 :
				calendar.set(Calendar.DATE, current - 3);
				break;
			case 5 :
				calendar.set(Calendar.DATE, current - 4);
				break;
			case 6 :
				calendar.set(Calendar.DATE, current - 5);
				break;

			default :
				break;
		}
		return calendar;
	}
	public static Calendar getMonDayOfCurrentWeek() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
		Calendar currentWeekCal = getMonday(calendar.get(Calendar.DAY_OF_WEEK) - 1, (Calendar) calendar.clone());
		return currentWeekCal;
	}

}
