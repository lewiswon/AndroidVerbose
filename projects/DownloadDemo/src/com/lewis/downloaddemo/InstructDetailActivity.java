package com.lewis.downloaddemo;

import com.google.gson.Gson;
import com.lewis.downloaddemo.bean.Instruct;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.widget.TextView;

public class InstructDetailActivity extends Activity implements DatabaseInterface {
	private Instruct instruct, instrucDetail = null;
	private InstructDAO instructDAO;
	private TextView nameTv, sourceTv, contextTv;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitiviy_instruct_detail);
		nameTv = (TextView) findViewById(R.id.tv_name_detail);
		sourceTv = (TextView) findViewById(R.id.tv_source_detail);
		contextTv = (TextView) findViewById(R.id.tv_context_detail);
		instruct = (Instruct) getIntent().getSerializableExtra("instruct");
		instructDAO = new InstructDAO();
		instructDAO.setListener(this);
		dialog = new ProgressDialog(this);
		dialog.setMessage("加载中....");
		dialog.show();
		getDetail();
	}

	private void getDetail() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				instructDAO.queryById(instruct.getMe_uid());
			}
		}, 100);

	};

	@Override
	public void onSuccess(String json) {
		instrucDetail = new Gson().fromJson(json, Instruct.class);
		dialog.dismiss();
		nameTv.setText("药品名称：" + instrucDetail.getMe_name());
		sourceTv.setText("来源:" + instrucDetail.getMe_source());
		contextTv.setText(Html.fromHtml(instrucDetail.getMe_context()));
	}
}
