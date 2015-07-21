package com.ifenduo.coach.fragment;

import java.util.ArrayList;

import android.os.Message;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.R;
import com.ifenduo.coach.adapter.StudentRecordListAdapter;

public class AroundTrainFragment extends BaseFragment implements OnRefreshListener2<ListView> {
	private PullToRefreshListView listView;
	private StudentRecordListAdapter adapter;
	private ArrayList<String> list;
	@Override
	public int setLayout() {
		return R.layout.fragment_around_train;
	}

	@Override
	public void initViews() {
		list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		listView = (PullToRefreshListView) findViewById(R.id.lv_arround_train_student_record);
		listView.setAdapter(adapter);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

	}
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		Message msg = new Message();
		msg.what = app.REFRESH;
		msg.obj = listView;
		app.handler.sendMessage(msg);
	}
}
