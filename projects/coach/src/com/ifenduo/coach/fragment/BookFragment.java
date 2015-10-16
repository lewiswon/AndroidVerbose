package com.ifenduo.coach.fragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.ifenduo.coach.R;

public class BookFragment extends BaseFragment implements OnPageChangeListener, OnCheckedChangeListener {
	private BookAdapter adapter;
	private TextView centerTv;
	private ArrayList<Fragment> fragments;
	private ViewPager viewPager;
	private RadioGroup radioGroup;

	private RadioButton leftRbtn, rightRbtn;
	@Override
	public int setLayout() {
		return R.layout.fragment_book;
	}

	@Override
	public void initViews() {
		viewPager = (ViewPager) findViewById(R.id.vp_book);
		centerTv = (TextView) findViewById(R.id.tv_center_top_bar);
		radioGroup = (RadioGroup) findViewById(R.id.rg_book_f);
		leftRbtn = (RadioButton) findViewById(R.id.rb_left_book_f);
		rightRbtn = (RadioButton) findViewById(R.id.rb_right_book_f);
		centerTv.setText("预约处理");
		fragments = new ArrayList<Fragment>();
		fragments.add(new UnhandleBookFragment());
		fragments.add(new HandledBookFragment());
		adapter = new BookAdapter(getFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(this);
		radioGroup.setOnCheckedChangeListener(this);
	}

	class BookAdapter extends FragmentPagerAdapter {

		public BookAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return 2;
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		leftRbtn.setTextColor(getResources().getColor(R.color.text_gray));
		rightRbtn.setTextColor(getResources().getColor(R.color.text_gray));
		switch (checkedId) {
			case R.id.rb_left_book_f :
				viewPager.setCurrentItem(0);
				leftRbtn.setTextColor(getResources().getColor(R.color.text_black));
				findViewById(R.id.left_view).setVisibility(View.VISIBLE);
				findViewById(R.id.right_view).setVisibility(View.INVISIBLE);
				break;
			case R.id.rb_right_book_f :
				viewPager.setCurrentItem(1);
				rightRbtn.setTextColor(getResources().getColor(R.color.text_black));
				findViewById(R.id.left_view).setVisibility(View.INVISIBLE);
				findViewById(R.id.right_view).setVisibility(View.VISIBLE);
				break;

			default :
				break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
			case 0 :
				radioGroup.check(R.id.rb_left_book_f);
				break;
			case 1 :
				radioGroup.check(R.id.rb_right_book_f);
				break;
			default :
				break;
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			if (radioGroup != null) {
				radioGroup.check(R.id.rb_left_book_f);
			}
		}

	}
}
