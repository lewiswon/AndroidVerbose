package com.ifenduo.coach.fragment;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.BaseActivity;
import com.ifenduo.coach.activity.BookProcessActivity;
import com.ifenduo.coach.activity.PushBookActivity;
import com.ifenduo.coach.adapter.MonthGridViewAdapter;
import com.ifenduo.coach.bean.DateItem;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.util.LogUtil;

public class MonthCalendarFragment extends BaseFragment implements OnItemClickListener {
	public static final String PAGE = "PAGE";
	private GridView gridView;
	private MonthGridViewAdapter adapter;
	private ArrayList<DateItem> list;
	private IOnMonthSelectedListener onMonthSelected;
	private int start, end, month, page, type;
	public static final int FROM_COACH = 1, FROM_STUDENT = 2, SET_REST_REQEUST = 10002;
	// 当前月 用于查询时间空闲
	private String currentMonth;
	private Bundle mBundle;

	public static MonthCalendarFragment create(int page) {
		MonthCalendarFragment fragment = new MonthCalendarFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(PAGE, page);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public int setLayout() {
		return R.layout.fragment_coach_calendar;
	}

	public void setOnMonthSelectedListener(IOnMonthSelectedListener onMonthSelected, int type, Bundle bundle) {
		this.onMonthSelected = onMonthSelected;
		this.type = type;
		this.mBundle = bundle;
	}

	@Override
	public void initViews() {
		list = new ArrayList<DateItem>();
		page = getArguments().getInt(PAGE);
		Calendar cal = Calendar.getInstance();
		if (page > 500) {
			int divide = page - 500;
			cal.add(Calendar.MONTH, divide);
		} else if (page < 500) {
			int divide = 500 - page;
			cal.add(Calendar.MONTH, -divide);
		} else if (page == 500) {
		}
		month = cal.get(Calendar.MONTH);
		String str = (month + 1) + "";
		if (str.length() < 2) {
			str = "0" + str;
		}
		currentMonth = cal.get(Calendar.YEAR) + "-" + str;
		ArrayList<DateItem> items = new ArrayList<DateItem>();
		for (int i = 0; i < cal.getActualMaximum(Calendar.DATE); i++) {
			DateItem item = new DateItem();
			item.day = (i + 1) + "";
			item.year = cal.get(Calendar.YEAR) + "";
			item.month = (cal.get(Calendar.MONTH) + 1) + "";
			item.cal = cal;
			items.add(item);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		int lastweek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.MONTH, -1);
		if (firstWeek == Calendar.MONDAY) {
			start = 0;
		} else if (firstWeek == Calendar.SUNDAY) {
			start = 7;
			for (int i = 5; i >= 0; i--) {
				DateItem item = new DateItem();
				item.year = cal.get(Calendar.YEAR) + "";
				item.day = (cal.getActualMaximum(Calendar.DATE) - i) + "";
				item.cal = cal;
				item.month = (cal.get(Calendar.MONTH) + 1) + "";
				list.add(item);
			}
		} else {
			start = firstWeek - 1;
			for (int i = (firstWeek - 3); i >= 0; i--) {
				DateItem item = new DateItem();
				item.year = cal.get(Calendar.YEAR) + "";
				item.day = (cal.getActualMaximum(Calendar.DATE) - i) + "";
				item.cal = cal;
				item.month = (cal.get(Calendar.MONTH) + 1) + "";
				list.add(item);
			}
		}
		list.addAll(items);
		if (lastweek == Calendar.SUNDAY) {
			end = 0;
		} else {
			end = 7 - (lastweek - 2);
			for (int i = 0; i < 7 - (lastweek - 1); i++) {
				DateItem item = new DateItem();
				item.year = cal.get(Calendar.YEAR) + "";
				item.day = (i + 1) + "";
				item.cal = cal;
				item.month = (cal.get(Calendar.MONTH) + 1) + "";
				list.add(item);
			}
		}
		gridView = (GridView) findViewById(R.id.gv_calendar_fragemnt);
		adapter = new MonthGridViewAdapter(getActivity(), list, start, end);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(this);
		getTime();
	}

	public void getTime() {
		Type type = new TypeToken<BaseResponse<DateItem>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("time", currentMonth);
		new Request<DateItem>(getActivity()).getList(API.Book.urlTime, params, true, TimeOutModel.MODEL_SHORT, type, new Request.OnListResponse<DateItem>() {
			@Override
			public void onSuccess(ArrayList<DateItem> dateList, int total) {
				if (dateList.size() > 0) {
					for (int i = 0; i < dateList.size(); i++) {
						DateItem dateItem = dateList.get(i);
						Calendar call = Calendar.getInstance();
						try {
							Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateItem.day);
							call.setTime(date);
							int day = call.get(Calendar.DAY_OF_MONTH);
							for (DateItem item : list) {
								int time = Integer.parseInt(item.day);
								if (time == day) {
									if (dateItem.flag != -1) {
										item.flag = dateItem.flag;
									}
									if (dateItem.type != -1) {
										item.type = dateItem.type;
									}
								}
							}

						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
					adapter.setList(list);
				}
			}

			@Override
			public void onError(String error) {
				Log.i("error", error + "");
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		if (position < (start - 1) || position > (list.size() - end)) {
			return;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
			if (list.get(position).getCal().getTimeInMillis() < calendar.getTimeInMillis()) {
				Toast.makeText(getActivity(), "请选择今天之后的日期", Toast.LENGTH_SHORT).show();
				return;
			}
			if (list.get(position).flag == 2) {
			}
			Intent intent = new Intent();
			if (type == FROM_STUDENT) {
				Bundle bundle1 = mBundle;
				intent.setClass(getActivity(), BookProcessActivity.class);
				Bundle bundle = new Bundle();
				String[] strs = bundle1.getStringArray("student_infos");
				intent.putExtra("vip", bundle1.getBoolean("vip"));
				bundle.putInt("type", type);
				intent.putExtras(bundle);
				String name = strs[1];
				String studentId = strs[0];
				String[] studentInfos = new String[]{name, "", list.get(position).getDate(), studentId};
				intent.putExtra("bookinfo", studentInfos);
				startActivityForResult(intent, SET_REST_REQEUST);
				((BaseActivity) getActivity()).overridePendingTransition(R.anim.start_activity_in, R.anim.start_activity_out);
			} else if (type == FROM_COACH) {
				intent.setClass(getActivity(), PushBookActivity.class);
				Bundle bundle = new Bundle();
				// bundle.putString("date", DateItem);
				bundle.putSerializable("date", list.get(position));
				bundle.putInt("type", type);
				intent.putExtras(bundle);
				startActivityForResult(intent, SET_REST_REQEUST);
			}

		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getTime();
		LogUtil.i("onactivityresult", resultCode + "");
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (page > 0) {
				onMonthSelected.onMonthSelected(month + 1, page);
			} else {
				onMonthSelected.onMonthSelected(0, 0);
			}
		}
	}
}
