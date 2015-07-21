package com.ifenduo.coach.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenduo.coach.fragment.IOnMonthSelectedListener;
import com.ifenduo.coach.fragment.MonthCalendarFragment;

public class MonthViewPagrAdapter extends FragmentPagerAdapter {
	private IOnMonthSelectedListener onMonthSelected;

	public MonthViewPagrAdapter(FragmentManager fm, IOnMonthSelectedListener onMonthSelected) {
		super(fm);
		this.onMonthSelected = onMonthSelected;
	}

	@Override
	public MonthCalendarFragment getItem(int arg0) {
		MonthCalendarFragment fragment = MonthCalendarFragment.create(arg0);
		fragment.setOnMonthSelectedListener(onMonthSelected, MonthCalendarFragment.FROM_COACH, null);
		return fragment;
	}

	@Override
	public int getCount() {
		return 10000;
	}

}
