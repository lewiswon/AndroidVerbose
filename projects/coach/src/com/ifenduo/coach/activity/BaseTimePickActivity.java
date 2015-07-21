package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.DateItem;
import com.ifenduo.coach.bean.TimeItem;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.util.Utils;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
/**
 * 
 * 
 * @Filename BaseTimePickActivity.java
 * 
 * @Description 推送教学计划页面底部时间段选择公用部分
 * 
 * @Version 1.0
 * 
 * @Author Lewis Luo
 * 
 * 
 * @History <li>Author: Lewis Luo</li> <li>Date: Jun 29, 2015</li> <li>Version:
 *          1.0</li> <li>Content: create</li>
 * 
 */
public abstract class BaseTimePickActivity extends BaseActivity {
	protected boolean VIP;
	// 提交的类型 0为同意 1 为拒绝
	private int[] timeLLs, peoples, times;
	protected ArrayList<DateCheck> dateChecks;
	protected ArrayList<DateCheck> checked;
	protected String startTime = "", endTime = "";
	private ImageView secondIv, minuteIv, hourIv;
	// 从前一个页面接收的个人信息
	protected String[] bookInfos;
	// 用于判断是否为当天
	private String pickDate, currentDate;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		timeLLs = new int[]{R.id.ll_time_01, R.id.ll_time_02, R.id.ll_time_03, R.id.ll_time_04, R.id.ll_time_05, R.id.ll_time_06, R.id.ll_time_07, R.id.ll_time_08, R.id.ll_time_09, R.id.ll_time_10,
				R.id.ll_time_11, R.id.ll_time_12, R.id.ll_time_13, R.id.ll_time_14};
		peoples = new int[]{R.id.tv_people_01, R.id.tv_people_02, R.id.tv_people_03, R.id.tv_people_04, R.id.tv_people_05, R.id.tv_people_06, R.id.tv_people_07, R.id.tv_people_08, R.id.tv_people_09,
				R.id.tv_people_10, R.id.tv_people_11, R.id.tv_people_12, R.id.tv_people_13, R.id.tv_people_14};
		times = new int[]{R.id.tv_time_01, R.id.tv_time_02, R.id.tv_time_03, R.id.tv_time_04, R.id.tv_time_05, R.id.tv_time_06, R.id.tv_time_07, R.id.tv_time_08, R.id.tv_time_09, R.id.tv_time_10,
				R.id.tv_time_11, R.id.tv_time_12, R.id.tv_time_13, R.id.tv_time_14};
		secondIv = (ImageView) findViewById(R.id.iv_second);
		minuteIv = (ImageView) findViewById(R.id.iv_minute);
		hourIv = (ImageView) findViewById(R.id.iv_hour);
		try {
			VIP = getIntent().getBooleanExtra("vip", false);
			bookInfos = getIntent().getStringArrayExtra("bookinfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bookInfos == null) {
			DateItem dateItem = (DateItem) getIntent().getExtras().getSerializable("date");
			String monthStr = dateItem.month;
			String dayStr = dateItem.day;
			if (monthStr.length() < 2) {
				monthStr = "0" + monthStr;
			}
			if (dayStr.length() < 2) {
				dayStr = "0" + dayStr;
			}

			bookInfos = new String[]{"", "", dateItem.year + "-" + monthStr + "-" + dayStr};
		}
		pickDate = bookInfos[2];
		initTimePick();
		getData();
		initAnimation();
		dialog.setMessage("正在处理...");
	}
	public void setUpTime() {
		switch (checked.size()) {
			case 0 :
				Toast.makeText(this, "请选择时间段后再提交", Toast.LENGTH_SHORT).show();
				return;
			case 1 :
				startTime = checked.get(0).time.substring(0, 5);
				endTime = checked.get(0).time.substring(6, 11);
				break;
			case 2 :
				DateCheck item1 = checked.get(0);
				DateCheck item2 = checked.get(1);
				if (item1.index > item2.index) {
					startTime = item2.time.substring(0, 5);
					endTime = item1.time.substring(6, 11);
				} else {
					startTime = item1.time.substring(0, 5);
					endTime = item2.time.substring(6, 11);
				}
				break;
			default :
				break;
		}
	}
	private void initTimePick() {
		dateChecks = new ArrayList<DateCheck>();
		checked = new ArrayList<DateCheck>();
		for (int i = 0; i < timeLLs.length; i++) {
			final int index = i;
			DateCheck dateCheck = new DateCheck();
			dateCheck.isCheck = false;
			dateCheck.peopleTv = (TextView) findViewById(peoples[i]);
			dateCheck.timeTv = (TextView) findViewById(times[i]);
			dateCheck.index = i + 1;
			if (i == 4) {
				dateCheck.isRest = true;
				dateCheck.timeTv.setText("休息");
				dateCheck.peopleTv.setVisibility(View.GONE);
				dateCheck.timeTv.setHeight(Utils.dip2px(getApplicationContext(), 60));
				dateCheck.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
				dateCheck.type = 2;
			} else {
				dateCheck.isRest = false;
			}
			dateCheck.time = dateCheck.timeTv.getText().toString();
			dateCheck.parentLL = (LinearLayout) findViewById(timeLLs[i]);
			dateChecks.add(dateCheck);
			dateCheck.peopleTv.setText(dateCheck.people + "人");
			if (isDateDeprecate(i)) {
				dateCheck.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
				dateCheck.peopleTv.setTextColor(getResources().getColor(R.color.unfocus));
			}
			// 为每一个时间段绑定点击事件
			findViewById(timeLLs[i]).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (BaseTimePickActivity.this instanceof PushBookActivity) {
						// 在推送教计划页面，如果没有先选择学员，则提示先选择学员
						if (PushBookActivity.getInstance().isStudentEmpty()) {
							Toast.makeText(getApplicationContext(), "请先选择学员", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					DateCheck item = dateChecks.get(index);
					if (isDateDeprecate(index)) {
						Toast.makeText(getApplicationContext(), "该时间已经过期", Toast.LENGTH_SHORT).show();
						return;
					}
					if (item.isRest) {
						Toast.makeText(getApplicationContext(), "该时段为休息时段", Toast.LENGTH_SHORT).show();
						return;
					}
					if (item.isVip & !VIP) {
						Toast.makeText(getApplicationContext(), "该时段只能VIP用户才能预约", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!item.isVip & VIP & item.people != 0) {
						Toast.makeText(getApplicationContext(), "该时段只能非VIP用户才能预约", Toast.LENGTH_SHORT).show();
						return;
					}
					if (checked.size() == 2 && !item.isCheck) {
						Toast.makeText(getApplicationContext(), "最多只能选择两个时间段", Toast.LENGTH_SHORT).show();
						return;
					}
					if (checked.size() == 1) {
						if (Math.abs(checked.get(0).index - item.index) > 1) {
							Toast.makeText(getApplicationContext(), "只能选择相邻时间段", Toast.LENGTH_SHORT).show();
							return;
						}
					}
					if (item.isCheck) {
						item.isCheck = false;
						checked.remove(item);
						item.parentLL.setBackgroundResource(R.drawable.book_not_full);
						item.peopleTv.setTextColor(getResources().getColor(R.color.text_black));
						item.timeTv.setTextColor(getResources().getColor(R.color.text_black));

					} else if (!item.isCheck && !item.isRest) {
						item.isCheck = true;
						if (item.people == 4) {
							Toast.makeText(getApplicationContext(), "该时段人已满,请选择其他时段", Toast.LENGTH_SHORT).show();
							return;
						}
						checked.add(item);
						item.parentLL.setBackgroundResource(R.drawable.color_blue_check);
						item.peopleTv.setTextColor(getResources().getColor(R.color.white));
						item.timeTv.setTextColor(getResources().getColor(R.color.white));
					}
				}
			});
		}

	}
	protected void getData() {
		Type type = new TypeToken<BaseResponse<TimeItem>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("time", bookInfos[2]);
		params.put("pageSize", "20");
		new Request<TimeItem>(this).getList(API.Book.urlTime, params, true, TimeOutModel.MODEL_SHORT, type, new Request.OnListResponse<TimeItem>() {
			@Override
			public void onSuccess(ArrayList<TimeItem> bookList, int total) {
				if (bookList.size() > 0) {
					for (TimeItem item : bookList) {
						String str = item.getStartTime().substring(0, 2);
						int start = Integer.parseInt(str);
						int offset = (start - 8);
						DateCheck check = dateChecks.get(offset);
						check.people = item.getPeoples();
						check.isVip = item.isVIP();
						check.type = item.getType();
						refreshPicker();
					}
				} else {
				}
			}

			private void refreshPicker() {
				for (DateCheck item : dateChecks) {
					item.peopleTv.setText(item.people + "人");
					if (item.type == 2) {
						item.timeTv.setText("休息");
						item.isRest = true;
						item.peopleTv.setVisibility(View.GONE);
						item.timeTv.setHeight(Utils.dip2px(getApplicationContext(), 60));
						item.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
					}
					if (item.people == 4) {
						item.peopleTv.setTextColor(getResources().getColor(R.color.unfocus));
						item.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
					}

				}
			}

			@Override
			public void onError(String error) {
			}
		});
	}
	public boolean isDateDeprecate(int index) {
		Calendar calendar = Calendar.getInstance();
		String monthStr = (calendar.get(Calendar.MONTH) + 1) + "";
		String dayStr = calendar.get(Calendar.DAY_OF_MONTH) + "";
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (monthStr.length() < 2) {
			monthStr = "0" + monthStr;
		}
		if (dayStr.length() < 2) {
			dayStr = "0" + dayStr;
		}
		currentDate = calendar.get(Calendar.YEAR) + "-" + monthStr + "-" + dayStr;
		if (currentDate.equals(pickDate)) {
			LogUtil.i("today", "today" + hour);
			if (index < (hour - 8)) {
				return true;
			}
		}
		return false;
	}
	private void initAnimation() {
		final LinearInterpolator interpolator = new LinearInterpolator();
		// secondIv.startAnimation(secondRotation);
		Calendar calendar = Calendar.getInstance();
		final int second = calendar.get(Calendar.SECOND);
		final int hour = calendar.get(Calendar.HOUR);
		final int minute = calendar.get(Calendar.MINUTE);
		RotateAnimation secondStart = new RotateAnimation(0, 6 * second + 6, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		secondStart.setDuration(1000);
		secondStart.setFillAfter(true);
		secondIv.startAnimation(secondStart);
		RotateAnimation hourStart = new RotateAnimation(0, 30 * hour, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		hourStart.setDuration(1200);
		hourStart.setFillAfter(true);
		hourIv.startAnimation(hourStart);
		RotateAnimation minuteStart = new RotateAnimation(0, 6 * minute, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		minuteStart.setDuration(1500);
		minuteStart.setFillAfter(true);
		minuteIv.startAnimation(minuteStart);
		secondStart.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				RotateAnimation rotateAnimation = new RotateAnimation(6 * second + 6, 6 * second + 6 + 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation.setRepeatCount(Animation.INFINITE);
				rotateAnimation.setRepeatMode(Animation.RESTART);
				rotateAnimation.setDuration(60 * 1000);
				rotateAnimation.setInterpolator(interpolator);
				secondIv.startAnimation(rotateAnimation);
			}
		});
		hourStart.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				RotateAnimation rotateAnimation = new RotateAnimation(30 * hour, 30 * hour + 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation.setRepeatCount(Animation.INFINITE);
				rotateAnimation.setRepeatMode(Animation.RESTART);
				rotateAnimation.setDuration(360 * 60 * 1000);
				rotateAnimation.setInterpolator(interpolator);
				hourIv.startAnimation(rotateAnimation);
			}
		});
		minuteStart.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				RotateAnimation rotateAnimation = new RotateAnimation(6 * minute, 6 * minute + 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				rotateAnimation.setRepeatCount(Animation.INFINITE);
				rotateAnimation.setRepeatMode(Animation.RESTART);
				rotateAnimation.setDuration(60 * 60 * 1000);
				rotateAnimation.setInterpolator(interpolator);
				minuteIv.startAnimation(rotateAnimation);
			}
		});
	}
	class DateCheck {
		TextView peopleTv;
		TextView timeTv;
		String time;
		boolean isCheck;
		boolean isRest;
		int type;
		boolean isVip = false;
		LinearLayout parentLL;
		int index;
		int people = 0;
	}
}
