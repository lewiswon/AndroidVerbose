package com.ifenduo.coach.fragment;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.WelcomeActivity;
import com.ifenduo.coach.adapter.MonthViewPagrAdapter;
import com.ifenduo.coach.bean.User;
import com.ifenduo.coach.data.CoachCenterData;
import com.ifenduo.coach.data.CoachCenterData.OnGet;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CoachFragment extends BaseFragment implements IOnMonthSelectedListener, OnClickListener {
	private ViewPager calendarViewPager;
	private TextView rightTv, centerTv, monthTv, coachNameTv, coachNoTv, coachTypeTv, driveAgeTv, teachAgeTv, coachAgeTv;
	private MonthViewPagrAdapter adapter;
	private ImageLoader imageLoader;
	private RoundedImageView headRi;
	@Override
	public int setLayout() {
		return R.layout.fragment_coach;
	}

	@Override
	public void initViews() {
		imageLoader = ImageLoader.getInstance();
		headRi = (RoundedImageView) findViewById(R.id.ri_coach_headppic);
		calendarViewPager = (ViewPager) findViewById(R.id.vp_calendar_coach_f);
		coachNameTv = (TextView) findViewById(R.id.tv_coach_name);
		teachAgeTv = (TextView) findViewById(R.id.tv_teach_age);
		driveAgeTv = (TextView) findViewById(R.id.tv_drive_age);
		coachTypeTv = (TextView) findViewById(R.id.tv_coach_type);
		coachNoTv = (TextView) findViewById(R.id.tv_coach_no);
		coachAgeTv = (TextView) findViewById(R.id.tv_coach_age);
		adapter = new MonthViewPagrAdapter(getFragmentManager(), this);
		calendarViewPager.setAdapter(adapter);
		calendarViewPager.setCurrentItem(500);
		centerTv = (TextView) findViewById(R.id.tv_center_top_bar);
		rightTv = (TextView) findViewById(R.id.tv_right_top_bar);
		monthTv = (TextView) findViewById(R.id.tv_month_coach);
		centerTv.setText("教练中心");
		rightTv.setText("注销");
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setTextColor(getResources().getColor(R.color.text_red));
		rightTv.setOnClickListener(this);
		initData();
	}

	private void initData() {
		new CoachCenterData().getUser(app, new OnGet<User>() {
			@Override
			public void get(User user) {
				coachNameTv.setText(user.getName());

				imageLoader.displayImage(API.IMG_SERVER + user.getHeadpic(), headRi);
				long birthDay = user.getBirthDay();
				Calendar calendar = Calendar.getInstance();
				long currentTime = calendar.getTimeInMillis();
				long age = currentTime - birthDay;

				String trainType;
				if (user.getTrainType() != null) {
					int trainerType = Integer.parseInt(user.getTrainType());

					switch (trainerType) {
						case 0 :
							trainType = "场地教练";
							break;
						case 1 :
							trainType = "道路教练";
							break;
						case 2 :
							trainType = "场地/道路";
							break;

						default :
							trainType = "审核中";
							break;
					}
				} else {
					trainType = "审核中";
				}
				coachTypeTv.setText(trainType);
				if (user.isOpen()) {
					coachNoTv.setText("教练号:" + user.getNum());
					driveAgeTv.setText(Html.fromHtml("驾龄:<font color=#525252> " + user.getDriveYear() + "</font>"));
					teachAgeTv.setText(Html.fromHtml("教龄:<font color=#525252> " + user.getTeachYear() + "</font>"));
				}

				coachAgeTv.setText(Html.fromHtml("年龄:<font color=#525252> " + ((int) (age / 31536000000l)) + "</font>"));
				findViewById(R.id.rl_loading).setVisibility(View.GONE);
			}
		});
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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogUtil.i("onactivityresult", resultCode + "");
		switch (requestCode) {
			case MonthCalendarFragment.SET_REST_REQEUST :
				if (resultCode == Activity.RESULT_OK) {
				}
				break;

			default :
				break;
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			calendarViewPager.setCurrentItem(500);
			initData();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_right_top_bar :
				AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT).setMessage("确定退出当前账号?").setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						app.logout();
						startActivity(new Intent(getActivity(), WelcomeActivity.class));
						getActivity().finish();
					}
				}).create();
				alertDialog.show();
				break;

			default :
				break;
		}
	}

}
