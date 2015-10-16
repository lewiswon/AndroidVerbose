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
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.TimeItem;
import com.ifenduo.coach.fragment.MonthCalendarFragment;
import com.ifenduo.coach.fragment.UnhandleBookFragment;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.util.Utils;

public class BookProcessActivity extends BaseTimePickActivity implements OnClickListener {
	private TextView leftTv, centerTv, rightTv, nameTv, learnTypeTv, dateTv, confirmTv;
	private int type;
	// 提交的类型 0为同意 1 为拒绝
	private String confirmType = "0";
	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.activity_book_process);
		super.onCreate(arg0);
		leftTv = (TextView) findViewById(R.id.book_process_top).findViewById(R.id.tv_left_top_bar);
		centerTv = (TextView) findViewById(R.id.book_process_top).findViewById(R.id.tv_center_top_bar);
		rightTv = (TextView) findViewById(R.id.book_process_top).findViewById(R.id.tv_right_top_bar);
		nameTv = (TextView) findViewById(R.id.tv_name_book_process);
		confirmTv = (TextView) findViewById(R.id.tv_confirm_book_process);
		learnTypeTv = (TextView) findViewById(R.id.tv_learn_book_process);
		dateTv = (TextView) findViewById(R.id.tv_date_book_process);
		confirmTv.setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		type = bundle.getInt("type");

		initViews();
	}

	@SuppressWarnings("deprecation")
	private void initViews() {
		dialog.setMessage("正在处理...");
		leftTv.setVisibility(View.VISIBLE);
		leftTv.setOnClickListener(this);
		leftTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.back_arrow), null, null, null);
		nameTv.setText(bookInfos[0]);
		learnTypeTv.setText(bookInfos[1]);
		String day = bookInfos[2];
		String str = day.substring(5, 7) + "月" + day.substring(8, 10) + "日";
		dateTv.setText(str);
		if (type == UnhandleBookFragment.FROM_BOOK_LIST) {
			centerTv.setText("预约处理");
			rightTv.setText("拒绝");
			rightTv.setVisibility(View.VISIBLE);
			rightTv.setOnClickListener(this);
			if (app.getValue("book_process_first").equals("")) {
				findViewById(R.id.iv_first_book_process).setVisibility(View.VISIBLE);
				findViewById(R.id.iv_first_book_process).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						findViewById(R.id.iv_first_book_process).setVisibility(View.GONE);
						app.setValue("book_process_first", "first");
					}
				});
			} else {
				findViewById(R.id.iv_first_book_process).setVisibility(View.GONE);
			};
		} else if (type == MonthCalendarFragment.FROM_STUDENT) {
			centerTv.setText("推送教学计划");
		}

	}
	public void commitData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appointmentId", bookInfos[3]);
		params.put("type", confirmType);
		if (confirmType.equals("0")) {
			setUpTime();
			// Log.i("start and end", startTime + "---" + endTime);
			params.put("startTime", startTime);
			params.put("endTime", endTime);
		}

		dialog.show();
		new Request<String>(this).stateRequest(API.Book.urlCommit, params, false, new Request.OnStateListener() {

			@Override
			public void onSuccess(String response) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(getApplicationContext(), "处理成功", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				finish();
			}

			@Override
			public void onError(String error) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 从学员详细信息的推送计划进入后，用此方法推送计划
	 */
	private void pushPlan() {
		setUpTime();
		Map<String, String> params = new HashMap<String, String>();
		params.put("trainerId", app.getCurrentUser().getId());
		params.put("traineeId", bookInfos[3]);
		params.put("learnType", "1");
		params.put("day", bookInfos[2]);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		dialog.show();
		new Request<String>(this).stateRequest(API.Book.urlPushPlan, params, false, new Request.OnStateListener() {

			@Override
			public void onSuccess(String response) {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast.makeText(getApplicationContext(), "处理成功", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				finish();
			}

			@Override
			public void onError(String error) {
				Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_left_top_bar :
				finish();
				break;
			case R.id.tv_right_top_bar :
				confirmType = "1";
				commitData();
				break;
			case R.id.tv_confirm_book_process :

				switch (type) {
					case UnhandleBookFragment.FROM_BOOK_LIST :
						confirmType = "0";
						commitData();
						break;
					case MonthCalendarFragment.FROM_STUDENT :
						pushPlan();
						break;
					default :
						break;
				}
				break;
			default :
				break;
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}
}
