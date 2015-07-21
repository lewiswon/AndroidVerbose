package com.ifenduo.coach.bean;

import java.io.Serializable;
import java.util.Calendar;

import android.R.integer;
import android.util.Log;

public class DateItem implements Serializable {
	public String day;
	public String dayOfWeek;
	public String checkTag;
	public String year;
	public String month;
	public Calendar cal;
	public int type = -1;
	public int flag = -1;
	public String getDate() {
		String dayStr = day;
		String monthStr = month;
		if (day.length() < 2) {
			dayStr = "0" + dayStr;
		}
		if (month.length() < 2) {
			monthStr = "0" + monthStr;
		}
		return year + "-" + monthStr + "-" + dayStr;
	}
	public Calendar getCal() {
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, (Integer.parseInt(month) - 1));
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
		return cal;
	}
}
