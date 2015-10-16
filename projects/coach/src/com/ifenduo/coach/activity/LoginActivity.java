package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.User;
import com.ifenduo.coach.http.BaseDataResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.Md5;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText etPass, etPhone;
	private TextView tvForgot, tvLogin, tvBack;
	private String password, phone;
	private ImageView ivShowPass;
	private boolean isShowPassword = false;
	private AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		tvForgot = (TextView) findViewById(R.id.tv_forgot_pass_login);
		ivShowPass = (ImageView) findViewById(R.id.iv_show_pass);
		tvLogin = (TextView) findViewById(R.id.tv_login_login);
		etPass = (EditText) findViewById(R.id.et_pass_login);
		etPhone = (EditText) findViewById(R.id.et_phone_login);
		tvBack = (TextView) findViewById(R.id.tv_left_login);
		tvBack.setOnClickListener(this);
		ivShowPass.setOnClickListener(this);
		tvLogin.setOnClickListener(this);
		tvForgot.setOnClickListener(this);

		alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT).setMessage("该账号正在审核中,请耐心等待").setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create();
	}
	@Override
	public void onClick(View v) {
		password = etPass.getText().toString();
		switch (v.getId()) {
			case R.id.iv_show_pass :
				if (password.length() > 0) {
					if (isShowPassword) {
						etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
						isShowPassword = false;
						ivShowPass.setImageResource(R.drawable.show_pass);

					} else {
						etPass.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
						ivShowPass.setImageResource(R.drawable.show_pass_open);
						isShowPassword = true;
					}
				}
				break;
			case R.id.tv_login_login :
				login();
				break;
			case R.id.tv_forgot_pass_login :
				startActivity(new Intent(this, ResetPassActivity.class));
				break;
			case R.id.tv_left_login :
				finish();
				overridePendingTransition(R.anim.end_activity_in, R.anim.end_activity_out);
				break;
			default :
				break;
		}
	}
	private void login() {
		password = etPass.getText().toString();
		phone = etPhone.getText().toString();
		if (phone.length() != 11) {
			Toast.makeText(getApplicationContext(), "手机号码格式错误", Toast.LENGTH_SHORT).show();
			return;
		}
		if (password.length() < 1) {
			Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("phone", phone);
		params.put("password", Md5.getMD5String(password + "{ofd.ptms}"));
		Type type = new TypeToken<BaseDataResponse<User>>() {
		}.getType();
		dialog.setMessage("正在登陆");
		dialog.show();
		new Request<User>(this).getData(API.User.login, params, type, new Request.OnDataResponse<User>() {

			@Override
			public void onSuccess(User data) {
				dialog.dismiss();
				app.login(data);
				app.setValue("first", "first");
				Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				app.setPushAlias();
			}
			@Override
			public void onError(String error) {
				dialog.dismiss();
				if (error.length() > 10) {
					error = "登陆失败";
				}
				if (error == null) {
					error = "网络错误";
				}
				Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, WelcomeActivity.class));
		finish();
	}
}
