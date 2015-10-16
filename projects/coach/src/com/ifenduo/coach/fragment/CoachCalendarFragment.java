package com.ifenduo.coach.fragment;

import android.widget.GridView;

import com.ifenduo.coach.R;
import com.ifenduo.coach.adapter.WeekGridViewAdapter;

public class CoachCalendarFragment extends BaseFragment {
	private GridView coachGv;
	private WeekGridViewAdapter adapter;
	@Override
	public int setLayout() {
		return R.layout.fragment_coach_calendar;
	}

	@Override
	public void initViews() {
		coachGv = (GridView) findViewById(R.id.gv_calendar_fragemnt);
		adapter = new WeekGridViewAdapter(getActivity(), null);
		coachGv.setAdapter(adapter);
	}

}
