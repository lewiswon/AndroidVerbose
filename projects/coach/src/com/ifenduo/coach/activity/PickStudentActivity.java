package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.API;
import com.ifenduo.coach.Constant;
import com.ifenduo.coach.R;
import com.ifenduo.coach.adapter.StudentListAdapter;
import com.ifenduo.coach.bean.Student;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnListResponse;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;

import android.R.integer;
import android.content.Intent;
import android.graphics.pdf.PdfDocument.Page;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PickStudentActivity extends BaseActivity implements OnClickListener, OnItemClickListener, OnRefreshListener2<ListView> {
	private TextView centerTv, leftTv;
	private PullToRefreshListView studentListView;
	private StudentListAdapter adapter;
	private ArrayList<Student> list;
	private int page = 1, total;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pick_student);
		centerTv = (TextView) findViewById(R.id.tv_center_top_bar);
		leftTv = (TextView) findViewById(R.id.tv_left_top_bar);
		leftTv.setVisibility(View.VISIBLE);
		leftTv.setOnClickListener(this);
		leftTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.back_arrow), null, null, null);
		studentListView = (PullToRefreshListView) findViewById(R.id.lv_student_activity);
		initViews();
	}

	private void initViews() {
		list = new ArrayList<Student>();
		centerTv.setText("选择学员");
		adapter = new StudentListAdapter(this, list, R.layout.item_student_list);
		studentListView.setAdapter(adapter);
		// studentListView.setEmptyView(findViewById(R.id.rl_loading));
		studentListView.setMode(Mode.PULL_FROM_END);
		studentListView.setOnItemClickListener(this);
		getData();
	}
	private void getData() {
		Type type = new TypeToken<BaseResponse<Student>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("pageNow", page + "");
		new Request<Student>(this).getList(API.Student.urlList, params, true, TimeOutModel.MODEL_FOREVER, type, new OnListResponse<Student>() {

			@Override
			public void onSuccess(ArrayList<Student> studentList, int totalCount) {
				total = totalCount;
				studentListView.onRefreshComplete();
				if (studentList.size() > 0) {
					if (page == 1) {
						list = new ArrayList<Student>();
						list.addAll(studentList);
					} else {
						list.addAll(studentList);
					}
					adapter.setList(list);
				} else {
					studentListView.setEmptyView(findViewById(R.id.tv_empty_student));
				}
			}

			@Override
			public void onError(String error) {
				studentListView.setEmptyView(findViewById(R.id.tv_empty_student));
			}
		});
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position > 0) {
			position -= 1;
		}
		Student student = list.get(position);
		Intent intent = new Intent();
		intent.putExtra("student", student);
		setResult(RESULT_OK, intent);
		finish();
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (Constant.PAGE_SIZE * page < total) {
			page += 1;
			getData();
		} else {
			Message msg = new Message();
			msg.what = app.REFRESH;
			msg.obj = studentListView;
			app.handler.sendMessage(msg);
		}
	}

	@Override
	public void onClick(View v) {
		finish();
		overridePendingTransition(R.anim.end_activity_in, R.anim.end_activity_out);
	};
}
