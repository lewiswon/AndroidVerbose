package com.ifenduo.coach.activity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ifenduo.coach.AppManager;
import com.ifenduo.coach.R;

public class WelcomeActivity extends BaseActivity implements OnClickListener {
	private TextView	loginTv, registerTv;

	@Override
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		if (!"".equals(app.getCurrentUser().getId())) {
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
		setContentView(R.layout.activity_welcome);
		loginTv = (TextView) findViewById(R.id.tv_login_welcom);
		registerTv = (TextView) findViewById(R.id.tv_register_welcom);
		loginTv.setOnClickListener(this);
		registerTv.setOnClickListener(this);

	};

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.tv_login_welcom:
			intent.setClass(this, LoginActivity.class);
			break;
		case R.id.tv_register_welcom:
			intent.setClass(this, RegisterOneActivity.class);
			break;
		default:
			break;
		}
		if (intent.getComponent() != null) {
			startActivity(intent);
			overridePendingTransition(R.anim.start_activity_in, R.anim.start_activity_out);
		}
	}

	@Override
	public void onBackPressed() {
		AppManager.exit();
	}
}
