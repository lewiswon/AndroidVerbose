package com.ifenduo.coach.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.DateItem;
import com.ifenduo.coach.bean.Student;
import com.ifenduo.coach.fragment.MonthCalendarFragment;
import com.ifenduo.coach.http.Request;

public class PushBookActivity extends BaseTimePickActivity implements OnClickListener {
	private TextView centerTv, rightTv, leftTv, nameTv, leanTypeTv, dateTv, enterTv;
	private String date, studentId = "";
	private int type;
	private final int PICK_REQUEST = 10001, SET_REST = 10002;
	private LinearLayout ll_pick;
	private RelativeLayout rl_studentInfo;
	private DateItem dateItem;
	private static PushBookActivity instance;
	protected void onCreate(android.os.Bundle arg0) {
		instance = this;
		setContentView(R.layout.activity_push_book);
		Bundle bundle = getIntent().getExtras();
		dateItem = (DateItem) bundle.getSerializable("date");
		super.onCreate(arg0);
		leftTv = (TextView) findViewById(R.id.push_book_top).findViewById(R.id.tv_left_top_bar);
		centerTv = (TextView) findViewById(R.id.push_book_top).findViewById(R.id.tv_center_top_bar);
		rightTv = (TextView) findViewById(R.id.push_book_top).findViewById(R.id.tv_right_top_bar);
		ll_pick = (LinearLayout) findViewById(R.id.ll_pick_student);
		rl_studentInfo = (RelativeLayout) findViewById(R.id.rl_student_info_book_process);
		rl_studentInfo.setOnClickListener(this);
		nameTv = (TextView) findViewById(R.id.tv_name_push_book);
		leanTypeTv = (TextView) findViewById(R.id.tv_type_push_book);
		dateTv = (TextView) findViewById(R.id.tv_date_push_book);
		enterTv = (TextView) findViewById(R.id.tv_enter_push_book);
		enterTv.setOnClickListener(this);

		date = dateItem.month + "月" + dateItem.day + "日";
		type = bundle.getInt("type");
		initViews();

	}
	private void initViews() {
		centerTv.setText(date);
		leftTv.setVisibility(View.VISIBLE);
		leftTv.setOnClickListener(this);
		ll_pick.setOnClickListener(this);
		if (type == MonthCalendarFragment.FROM_COACH) {
			rightTv.setOnClickListener(this);
			rightTv.setVisibility(View.VISIBLE);
			rightTv.setText("休息");
		}
		leftTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.back_arrow), null, null, null);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case PICK_REQUEST :
				if (resultCode == RESULT_OK) {
					Student student = (Student) data.getSerializableExtra("student");
					ll_pick.setVisibility(View.GONE);
					rl_studentInfo.setVisibility(View.VISIBLE);
					setUpStudentInfo(student);
				}
				break;
			case SET_REST :
				if (resultCode == RESULT_OK) {
					getData();
				}
				break;
			default :
				break;
		}
	}
	private void setUpStudentInfo(Student student) {
		nameTv.setText(student.getName());
		dateTv.setText(date);
		leanTypeTv.setText("一般训练");
		studentId = student.getTraineeId();
		VIP = student.isVIP();
	};
	private void pushPlan() {
		if (studentId.equals("")) {
			Toast.makeText(this, "请先选择学员", Toast.LENGTH_SHORT).show();
			return;
		}
		setUpTime();
		if (checked.size() == 0) {
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("trainerId", app.getCurrentUser().getId());
		params.put("traineeId", studentId);
		params.put("learnType", "1");
		params.put("day", formatDate());
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
				Toast.makeText(getApplicationContext(), "处理失败 " + error, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
	}
	public String formatDate() {
		String monthStr = dateItem.month;
		String dayStr = dateItem.day;
		if (monthStr.length() < 2) {
			monthStr = "0" + monthStr;
		}
		if (dayStr.length() < 2) {
			dayStr = "0" + dayStr;
		}

		return dateItem.year + "-" + monthStr + "-" + dayStr;
	}
	@Override
	public void onClick(View v) {
		Intent i = new Intent(this, PickStudentActivity.class);
		switch (v.getId()) {
			case R.id.tv_left_top_bar :
				setResult(RESULT_OK);
				finish();
				break;
			case R.id.tv_right_top_bar :
				Intent intent = new Intent(this, SetRestActivity.class);
				intent.putExtra("date", formatDate());
				startActivityForResult(intent, SET_REST);
				break;
			case R.id.ll_pick_student :
				startActivityForResult(i, PICK_REQUEST);
				break;
			case R.id.rl_student_info_book_process :
				startActivityForResult(i, PICK_REQUEST);
				break;
			case R.id.tv_enter_push_book :
				pushPlan();
				break;
			default :
				break;
		}
	};
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_OK);
		overridePendingTransition(R.anim.end_activity_in, R.anim.end_activity_out);
	}
	public static PushBookActivity getInstance() {
		return instance;
	}
	public boolean isStudentEmpty() {
		if (studentId.equals("")) {
			return true;
		}
		return false;
	}
}
