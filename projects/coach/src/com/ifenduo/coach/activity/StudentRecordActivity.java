package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.textservice.TextInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.API;
import com.ifenduo.coach.Constant;
import com.ifenduo.coach.R;
import com.ifenduo.coach.adapter.StudentRecordListAdapter;
import com.ifenduo.coach.bean.Student;
import com.ifenduo.coach.bean.StudentRecord;
import com.ifenduo.coach.fragment.CalendarDialogFragment;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnListResponse;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class StudentRecordActivity extends BaseActivity implements OnClickListener {
	private TextView leftTv, centerTv, nameTv, noTv;
	private RoundedImageView headPicRi;
	static private FrameLayout fragmentContainer;
	private CalendarDialogFragment fragment;
	private PullToRefreshListView recordListView;
	private StudentRecordListAdapter adapter;
	private ArrayList<StudentRecord> list;
	private LinearLayout ll_pushPlan;
	private ImageLoader imageLoader;
	private boolean VIP;
	private String[] studentInfos;
	// @params from 来源页面标识
	private int page = 1, total, from;
	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			fragmentContainer.setVisibility(View.GONE);
		};
	};
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_student_record);
		leftTv = (TextView) findViewById(R.id.top_bar_student_record).findViewById(R.id.tv_left_top_bar);
		centerTv = (TextView) findViewById(R.id.top_bar_student_record).findViewById(R.id.tv_center_top_bar);
		nameTv = (TextView) findViewById(R.id.tv_name_student_record);
		noTv = (TextView) findViewById(R.id.tv_no_student_record);
		headPicRi = (RoundedImageView) findViewById(R.id.ri_head_student_record);
		fragmentContainer = (FrameLayout) findViewById(R.id.ll_calendar_container);
		recordListView = (PullToRefreshListView) findViewById(R.id.lv_record_student);
		// 从intent中取出数据
		VIP = getIntent().getBooleanExtra("vip", false);
		studentInfos = getIntent().getStringArrayExtra("student_infos");
		from = getIntent().getIntExtra("from", 0);
		imageLoader = ImageLoader.getInstance();
		initViews();
	}
	@SuppressWarnings("deprecation")
	private void initViews() {
		list = new ArrayList<StudentRecord>();
		centerTv.setText("学员记录");
		leftTv.setVisibility(View.VISIBLE);
		leftTv.setOnClickListener(this);
		leftTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.back_arrow), null, null, null);
		nameTv.setText(studentInfos[1]);
		String num = studentInfos[3] == null ? "普通学员" : "学员编号：" + studentInfos[3];
		noTv.setText(num);
		imageLoader.displayImage(API.IMG_SERVER + studentInfos[2], headPicRi);
		ll_pushPlan = (LinearLayout) findViewById(R.id.ll_push_plan_student_record);
		ll_pushPlan.setOnClickListener(this);
		if (from == 1001) {
			ll_pushPlan.setVisibility(View.GONE);
		}
		adapter = new StudentRecordListAdapter(this, list, R.layout.item_arround_train);
		recordListView.setEmptyView(findViewById(R.id.rl_loading));
		recordListView.setMode(Mode.BOTH);
		recordListView.setAdapter(adapter);
		getData();
	}
	public void getData() {
		Type type = new TypeToken<BaseResponse<StudentRecord>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", studentInfos[0]);
		params.put("type", "2");
		params.put("pageNow", page + "");
		new Request<StudentRecord>(this).getList(API.Student.urlDetail, params, true, TimeOutModel.MODEL_FOREVER, type, new OnListResponse<StudentRecord>() {

			@Override
			public void onSuccess(ArrayList<StudentRecord> studentList, int totalCount) {
				total = totalCount;
				if (studentList.size() > 0) {
					if (page == 1) {
						list = new ArrayList<StudentRecord>();
						list.addAll(studentList);
					} else {
						list.addAll(studentList);
					}
					adapter.setList(list);
				} else {
					recordListView.setEmptyView(findViewById(R.id.tv_empty_student_record));
				}

			}

			@Override
			public void onError(String error) {
			}
		});
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_left_top_bar :
				finish();
				setResult(RESULT_OK);
				break;
			case R.id.ll_push_plan_student_record :
				fragmentContainer.setVisibility(View.VISIBLE);
				fragment = new CalendarDialogFragment();
				Bundle bundle = new Bundle();
				bundle.putBoolean("vip", VIP);
				bundle.putStringArray("student_infos", studentInfos);
				fragment.setArguments(bundle);
				getSupportFragmentManager().beginTransaction().add(R.id.ll_calendar_container, fragment).commit();
				break;
			default :
				break;
		}
	}
	@Override
	public void onBackPressed() {
		if (fragmentContainer.getVisibility() == View.VISIBLE) {
			getSupportFragmentManager().beginTransaction().remove(fragment).commit();
			fragmentContainer.setVisibility(View.GONE);
			fragment = null;
		} else {
			super.onBackPressed();
			setResult(RESULT_OK);
		}
	}
}
