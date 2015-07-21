package com.ifenduo.coach.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.API;
import com.ifenduo.coach.App;
import com.ifenduo.coach.Constant;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.StudentRecordActivity;
import com.ifenduo.coach.adapter.StudentListAdapter;
import com.ifenduo.coach.bean.Student;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnListResponse;
import com.ifenduo.coach.qr.CaptureActivity;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;

public class StudentFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener2<ListView>, OnClickListener {
	private PullToRefreshListView studentListView;
	private StudentListAdapter adapter;
	private ArrayList<Student> list;
	private TextView centerTv, rightTv;
	private int page = 1, total;
	@Override
	public int setLayout() {
		return R.layout.fragment_student;
	}
	@Override
	public void initViews() {
		list = new ArrayList<Student>();
		studentListView = (PullToRefreshListView) findViewById(R.id.lv_student_student_f);
		// studentListView.setEmptyView(findViewById(R.id.rl_loading));
		centerTv = (TextView) findViewById(R.id.tv_center_top_bar);
		rightTv = (TextView) findViewById(R.id.tv_right_top_bar);
		centerTv.setText("学员列表");
		rightTv.setVisibility(View.VISIBLE);
		rightTv.setOnClickListener(this);
		@SuppressWarnings("deprecation")
		Drawable drawable = getResources().getDrawable(R.drawable.qr_code_scan);
		rightTv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
		adapter = new StudentListAdapter(getActivity(), list, R.layout.item_student_list);
		studentListView.setAdapter(adapter);
		studentListView.setMode(Mode.BOTH);
		studentListView.setOnRefreshListener(this);
		studentListView.setOnItemClickListener(this);
		getData();
	}
	private void getData() {
		Type type = new TypeToken<BaseResponse<Student>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("pageNow", page + "");
		new Request<Student>(getActivity()).getList(API.Student.urlList, params, true, TimeOutModel.MODEL_FOREVER, type, new OnListResponse<Student>() {

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
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_right_top_bar :
				startActivity(new Intent(getActivity(), CaptureActivity.class));
				break;

			default :
				break;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (position > 0) {
			position -= 1;
		}
		Student student = list.get(position);
		Intent intent = new Intent(getActivity(), StudentRecordActivity.class);
		String[] studentInfos = new String[]{student.getTraineeId(), student.getName(), student.getHeadpic(), student.getNum()};
		intent.putExtra("student_infos", studentInfos);
		intent.putExtra("vip", student.isVIP());
		startActivityForResult(intent, 10001);
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		page = 1;
		getData();
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
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			if (app == null) {
				app = (App) getActivity().getApplication();
			}
			getData();
		}
	}
}
