package com.ifenduo.coach.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.ifenduo.coach.R;
import com.ifenduo.coach.adapter.WeekGridViewAdapter;
import com.ifenduo.coach.bean.DateItem;
import com.ifenduo.coach.util.CalendarUtil;
import com.ifenduo.coach.util.Utils;

public class WeekCalendarFragment extends Fragment implements OnItemClickListener {
	public static final String ARG_PAGE = "PAGE";
	public static final String CALENDAR = "CALENDAR";
	public WeekGridViewAdapter adapter;
	private ArrayList<DateItem> list;
	private GridView weekGv;
	public static Fragment create(int pageNumber) {
		WeekCalendarFragment fragment = new WeekCalendarFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_week_calendar, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		list = new ArrayList<DateItem>();
		Calendar call = CalendarUtil.getMonDayOfCurrentWeek();
		int arg0 = getArguments().getInt(ARG_PAGE);
		if (arg0 > 500) {
			call.set(Calendar.DATE, call.get(Calendar.DATE) + (arg0 - 500) * 7);
		} else if (arg0 < 500) {
			call.set(Calendar.DATE, call.get(Calendar.DATE) - (500 - arg0) * 7);
		} else if (arg0 == 500) {
			
		}
		for (int j = 0; j < 7; j++) {
			DateItem dateItem = new DateItem();
			dateItem.day = call.get(Calendar.DAY_OF_MONTH) + "";
			dateItem.dayOfWeek = (call.get(Calendar.DAY_OF_WEEK) - 1) + "";
			String month = (call.get(Calendar.MONTH) + 1) + "";
			String day = call.get(Calendar.DAY_OF_MONTH) + "";
			if (month.length() < 2) {
				month = "0" + month;
			}
			if (day.length() < 2) {
				day = "0" + day;
			}
			dateItem.checkTag = call.get(Calendar.YEAR) + month + day;
			list.add(dateItem);
			call.set(Calendar.DATE, call.get(Calendar.DATE) + 1);
		}
		weekGv = (GridView) view.findViewById(R.id.gv_week_calendar);
		adapter = new WeekGridViewAdapter(getActivity(), list);
		weekGv.setAdapter(adapter);
		weekGv.setOnItemClickListener(this);
		Utils.addAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		DateItem item = list.get(position);
		CalendarFragment.CHECDE_DAY = item.checkTag;
		CalendarFragment.getInstance().mHandler.sendEmptyMessage(CalendarFragment.CHECK);
		adapter.notifyDataSetChanged();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}
}
