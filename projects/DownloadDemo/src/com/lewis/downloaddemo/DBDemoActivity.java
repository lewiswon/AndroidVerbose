package com.lewis.downloaddemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DBDemoActivity extends Activity {
	private EditText editText, idEt, zhuzhiEt, jinjiEt, xianghuzuoyongEt;
	private Button searchBtn, searchIdBtn;
	private String name, id, zhuzhi, jinji, xianghuzuoyong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db_demo);
		editText = (EditText) findViewById(R.id.et_name);
		searchBtn = (Button) findViewById(R.id.btn_search);
		idEt = (EditText) findViewById(R.id.et_id);
		searchIdBtn = (Button) findViewById(R.id.btn_search_id);
		zhuzhiEt = (EditText) findViewById(R.id.et_zhuzhi);
		jinjiEt = (EditText) findViewById(R.id.et_jingji);
		xianghuzuoyongEt = (EditText) findViewById(R.id.et_xianghguzuoyong);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		searchIdBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				id = idEt.getText().toString().trim();
				new Handler().post(new Runnable() {

					@Override
					public void run() {
						new InstructDAO().queryById(id);
					}
				});
			}
		});
		searchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				search();
			}
		});

	}

	private void search() {

		name = editText.getText().toString().trim();
		zhuzhi = zhuzhiEt.getText().toString().trim();
		jinji = jinjiEt.getText().toString().trim();
		xianghuzuoyong = xianghuzuoyongEt.getText().toString().trim();
		Intent intent = new Intent(DBDemoActivity.this, ListActivity.class);
		intent.putExtra("name", name);
		intent.putExtra("zhuzhi", zhuzhi);
		intent.putExtra("jinji", jinji);
		intent.putExtra("xianghuzuoyong", xianghuzuoyong);
		startActivity(intent);
	}

}
