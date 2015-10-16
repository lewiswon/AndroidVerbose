package com.ifenduo.coach.fragment;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.StudentRecordActivity;

public class CalendarDialogFragment extends BaseFragment implements OnClickListener, IOnMonthSelectedListener {
	private RelativeLayout rootRl;
	private ViewPager viewPager;
	private TextView monthTv;
	private LinearLayout closeCalendarLL;
	private Bundle studentInfoBundle;
	@Override
	public int setLayout() {
		return R.layout.fragment_dialog_calendar;
	}

	@Override
	public void initViews() {
		rootRl = (RelativeLayout) findViewById(R.id.rl_root_dialog_calendar);
		viewPager = (ViewPager) findViewById(R.id.vp_calendar_dialog_f);
		monthTv = (TextView) findViewById(R.id.tv_month_calendar_dialog);
		closeCalendarLL = (LinearLayout) findViewById(R.id.ll_choose_calendar);
		PushPlanAdapter adapter = new PushPlanAdapter(getFragmentManager(), getArguments(), this);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(500);
		rootRl.setOnClickListener(this);
		closeCalendarLL.setOnClickListener(this);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		studentInfoBundle = getArguments();
		initViews();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_root_dialog_calendar :
				getFragmentManager().beginTransaction().setCustomAnimations(R.anim.calendar_out, R.anim.calendar_out).remove(this).commit();
				StudentRecordActivity.handler.sendEmptyMessage(0);
				break;
			case R.id.ll_choose_calendar :
				getFragmentManager().beginTransaction().setCustomAnimations(R.anim.calendar_out, R.anim.calendar_out).remove(this).commit();
				StudentRecordActivity.handler.sendEmptyMessage(0);
				break;
			default :
				break;
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			initViews();
			viewPager.setCurrentItem(500);
		}
	}
	@Override
	public void onMonthSelected(int month, int page) {
		String str = "";
		if (page > 0) {
		} else {
			month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		}
		switch (month) {
			case 1 :
				str = "一月";
				break;
			case 2 :
				str = "二月";
				break;
			case 3 :
				str = "三月";
				break;
			case 4 :
				str = "四月";
				break;
			case 5 :
				str = "五月";
				break;
			case 6 :
				str = "六月";
				break;
			case 7 :
				str = "七月";
				break;
			case 8 :
				str = "八月";
				break;
			case 9 :
				str = "九月";
				break;
			case 10 :
				str = "十月";
				break;
			case 11 :
				str = "十一月";
				break;
			case 12 :
				str = "十二月";
				break;

			default :
				break;
		}
		monthTv.setText(str);
	}
}
class PushPlanAdapter extends FragmentStatePagerAdapter {
	private Bundle mBundle;
	private IOnMonthSelectedListener mListener;
	public PushPlanAdapter(FragmentManager fm, Bundle bundle, IOnMonthSelectedListener listener) {
		super(fm);
		this.mBundle = bundle;
		this.mListener = listener;
	}

	@Override
	public Fragment getItem(int arg0) {
		MonthCalendarFragment fragment = MonthCalendarFragment.create(arg0);
		fragment.setOnMonthSelectedListener(mListener, MonthCalendarFragment.FROM_STUDENT, mBundle);
		return fragment;
	}

	@Override
	public int getCount() {
		return 10000;
	}

}
