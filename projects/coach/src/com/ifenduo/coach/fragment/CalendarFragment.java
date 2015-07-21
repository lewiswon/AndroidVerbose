package com.ifenduo.coach.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.API;
import com.ifenduo.coach.Constant;
import com.ifenduo.coach.R;
import com.ifenduo.coach.adapter.CalendarListAdapter;
import com.ifenduo.coach.adapter.WeekCalendarAdapter;
import com.ifenduo.coach.bean.Record;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.util.Utils;

public class CalendarFragment extends BaseFragment implements OnClickListener, OnRefreshListener2<ListView>, OnItemClickListener {
	private static final int TODAY = 1001;
	public static final int CHECK = 1002;
	private static ViewPager calendarViewPager;
	private PullToRefreshListView calendarListView;
	private CalendarListAdapter adapter;
	private ArrayList<Record> list;
	public static String CHECDE_DAY = "", TODAY_DATE;
	private static TextView todayTv, backTodayTv;
	private WeekCalendarAdapter weekAdapter;
	private String time;
	private static CalendarFragment instance;
	private int page = 1, total;
	@Override
	public int setLayout() {
		instance = this;
		return R.layout.fragment_calendar;
	}
	public static CalendarFragment getInstance() {
		return instance;
	}
	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case TODAY :
					Calendar cal = Calendar.getInstance();
					String str = cal.get(Calendar.YEAR) + "年" + (cal.get(Calendar.MONTH) + 1) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日";
					String minute = cal.get(Calendar.MINUTE) + "";
					if (minute.length() < 2) {
						minute = "0" + minute;
					}
					String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + minute;
					todayTv.setText(str + "   " + time);
					mHandler.sendEmptyMessageDelayed(TODAY, 1000);
					break;
				case CHECK :
					Utils.notifyAapter();
					if (!CHECDE_DAY.equals(TODAY_DATE)) {
						todayTv.setVisibility(View.INVISIBLE);
						backTodayTv.setVisibility(View.VISIBLE);
					} else {
						todayTv.setVisibility(View.VISIBLE);
						backTodayTv.setVisibility(View.INVISIBLE);
					}
					page = 1;
					list.clear();
					getData();
					break;
				default :
					break;
			}

		};
	};

	@Override
	public void initViews() {
		
		Calendar cal = Calendar.getInstance();
		String month = (cal.get(Calendar.MONTH) + 1) + "";
		String day = cal.get(Calendar.DAY_OF_MONTH) + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		if (day.length() < 2) {
			day = "0" + day;
		}
		TODAY_DATE = cal.get(Calendar.YEAR) + month + day;
		CHECDE_DAY = TODAY_DATE;
		calendarViewPager = (ViewPager) findViewById(R.id.vp_calendar_calendar_f);
		calendarListView = (PullToRefreshListView) findViewById(R.id.lv_list_calendar);
		todayTv = (TextView) findViewById(R.id.tv_current_date_calendar_f);
		backTodayTv = (TextView) findViewById(R.id.tv_back_to_today);
		backTodayTv.setOnClickListener(this);
		weekAdapter = new WeekCalendarAdapter(getFragmentManager());
		calendarViewPager.setAdapter(weekAdapter);
		calendarViewPager.setCurrentItem(500, true);
		mHandler.sendEmptyMessage(TODAY);

		calendarViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 != 500) {
					todayTv.setVisibility(View.INVISIBLE);
					backTodayTv.setVisibility(View.VISIBLE);
				} else if (CHECDE_DAY.equals(TODAY_DATE)) {
					todayTv.setVisibility(View.VISIBLE);
					backTodayTv.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		list = new ArrayList<Record>();
		adapter = new CalendarListAdapter(getActivity(), list, R.layout.item_calendar_list);
		calendarListView.setAdapter(adapter);
		calendarListView.setOnRefreshListener(this);
		calendarListView.setMode(Mode.BOTH);
		calendarListView.setOnItemClickListener(this);
		// calendarListView.setEmptyView(findViewById(R.id.rl_loading));
		getData();
	}
	private void getData() {
		time = CHECDE_DAY.substring(0, 4) + "-" + CHECDE_DAY.substring(4, 6) + "-" + CHECDE_DAY.substring(6, 8);
		Type type = new TypeToken<BaseResponse<Record>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("pageNow", page + "");
		params.put("time", time);
		new Request<Record>(getActivity()).getList(API.Calendar.url, params, true, TimeOutModel.MODEL_SHORT, type, new Request.OnListResponse<Record>() {

			@Override
			public void onSuccess(ArrayList<Record> recordList, int totalCount) {
				calendarListView.onRefreshComplete();
				if (recordList.size() > 0) {
					total = totalCount;
					if (page == 1) {
						list = new ArrayList<Record>();
						list.addAll(recordList);
					} else {
						list.addAll(recordList);
					}
					adapter.setList(list);
				} else {
					calendarListView.setEmptyView(findViewById(R.id.tv_empty));
				}
			}

			@Override
			public void onError(String error) {
				if (list.size() < 1) {
					calendarListView.setEmptyView(findViewById(R.id.tv_empty));
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_back_to_today :
				calendarViewPager.setCurrentItem(500, true);
				CHECDE_DAY = TODAY_DATE;
				mHandler.sendEmptyMessage(CHECK);
				calendarListView.setEmptyView(findViewById(R.id.tv_empty));
				break;

			default :
				break;
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		getData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (Constant.PAGE_SIZE * page < total) {
			page += 1;
			getData();
		} else {
			Message msg = new Message();
			msg.what = app.REFRESH;
			msg.obj = calendarListView;
			app.handler.sendMessage(msg);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
}
