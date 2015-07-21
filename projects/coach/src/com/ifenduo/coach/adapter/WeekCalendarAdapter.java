package com.ifenduo.coach.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifenduo.coach.fragment.WeekCalendarFragment;

public class WeekCalendarAdapter extends FragmentPagerAdapter {

	public WeekCalendarAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		return WeekCalendarFragment.create(arg0);
	}

	@Override
	public int getCount() {
		return 1000;
	}

}
