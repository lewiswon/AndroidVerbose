package com.lewis.downloaddemo;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewis.downloaddemo.adapter.InstructAdapter;
import com.lewis.downloaddemo.bean.Instruct;
import com.lewis.downloaddemo.bean.InstructListBean;

public class ListActivity extends Activity implements DatabaseInterface, OnItemClickListener, OnScrollListener {
	private ProgressDialog dialog;
	private ListView listView;
	private InstructAdapter adapter;
	private InstructDAO instructDAO;
	private String name, zhuzhi, jinji, xianghuzuoyong;;
	ArrayList<Instruct> list, list2 = null;
	private InstructListBean bean = null;
	private int page = 1;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		dialog = new ProgressDialog(this);
		dialog.show();
		dialog.setMessage("正在搜索...");
		name = getIntent().getStringExtra("name");
		zhuzhi = getIntent().getStringExtra("zhuzhi");
		jinji = getIntent().getStringExtra("jinji");
		xianghuzuoyong = getIntent().getStringExtra("xianghuzuoyong");
		listView = (ListView) findViewById(R.id.lv_instruct);
		dialog.show();
		instructDAO = new InstructDAO();
		instructDAO.setListener(this);
		list = new ArrayList<>();
		adapter = new InstructAdapter(this, list, R.layout.item_list);
		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(this);
		getdata();
	}

	private void getdata() {
		new Thread() {
			public void run() {
				instructDAO.query(page, name);
			};
		}.start();
	}

	@Override
	public void onSuccess(String json) {
		Log.i("json---", json);
		Type type = new TypeToken<InstructListBean>() {
		}.getType();
		bean = new Gson().fromJson(json, type);
		if (bean.getList() != null && bean.getList().size() > 0) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (page == 1) {
						list = new ArrayList<>();
					}
					list.addAll(bean.getList());
					adapter.setList(list);
					//					adapter.notifyDataSetChanged();
					dialog.dismiss();
				}
			});

		} else {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "已经是最后一页啦", Toast.LENGTH_SHORT).show();
				}
			});

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, InstructDetailActivity.class);
		intent.putExtra("instruct", (Instruct) parent.getAdapter().getItem(position));
		startActivity(intent);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (totalItemCount - visibleItemCount == firstVisibleItem) {
			if (totalItemCount >= 20) {
				page += 1;
				dialog.show();
				getdata();
			}
		}
	};

}
