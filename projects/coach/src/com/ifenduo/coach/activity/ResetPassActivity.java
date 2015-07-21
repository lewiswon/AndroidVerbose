package com.ifenduo.coach.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.SessionStringRequest;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.util.Md5;
import com.ifenduo.coach.util.NetWorkUtils;

public class ResetPassActivity extends BaseActivity implements OnClickListener {
	private TextView tvVerifyCode, tvNext, tvLeftBack, centerTv;
	private EditText etPhone, etVerifyCode, etPass1, etPass2;
	private int time;
	private String phone, code, pass1, pass2, cookie;
	private SessionStringRequest getcodeRequest, registerRequest;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 1001 :
					if (time > 0) {
						time -= 1;
						mHandler.sendEmptyMessageDelayed(1001, 1000);
						tvVerifyCode.setText(time + "秒后重试");
					} else {
						tvVerifyCode.setText("获取验证码");
						tvVerifyCode.setClickable(true);
					}
					break;

				default :
					break;
			}
		};

	};
	@Override
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_register_one);
		tvLeftBack = (TextView) findViewById(R.id.tv_left_register_one);
		tvNext = (TextView) findViewById(R.id.tv_next_register_one);
		tvVerifyCode = (TextView) findViewById(R.id.tv_verify_code);
		etPass1 = (EditText) findViewById(R.id.et_pass1_register);
		etPass2 = (EditText) findViewById(R.id.et_pass2_register);
		etPhone = (EditText) findViewById(R.id.et_phone_register_one);
		etVerifyCode = (EditText) findViewById(R.id.et_verify_code);
		centerTv = (TextView) findViewById(R.id.tv_center_top_bar);
		centerTv.setText("重置密码");
		tvLeftBack.setOnClickListener(this);
		tvNext.setOnClickListener(this);
		tvVerifyCode.setOnClickListener(this);
	};

	@Override
	public void onClick(View v) {
		phone = etPhone.getText().toString();

		switch (v.getId()) {
			case R.id.tv_left_register_one :
				finish();
				break;
			case R.id.tv_next_register_one :
				next();
				break;
			case R.id.tv_verify_code :
				if (phone.length() < 1 || phone.length() != 11) {
					Toast.makeText(getApplicationContext(), "请先输入电话号码", Toast.LENGTH_SHORT).show();
					return;
				}
				getCode();
				break;
			default :
				break;
		}
	}
	private void getCode() {
		getcodeRequest = new SessionStringRequest(Method.POST, API.User.urlVerify, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				LogUtil.i("response", response);
				try {
					JSONObject object = new JSONObject(response);
					int code = object.getInt("code");
					if (code == 200) {
						cookie = getcodeRequest.getCookie();
						LogUtil.i("session", cookie);
						Toast.makeText(getApplicationContext(), "获取验证码成功", Toast.LENGTH_SHORT).show();
						time = 60;
						app.handler.sendEmptyMessage(app.VERIFY_CODE);
						mHandler.sendEmptyMessage(app.VERIFY_CODE);
						tvVerifyCode.setClickable(false);
					} else {
						Toast.makeText(getApplicationContext(), "获取验证码失败，请重新获取", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getApplicationContext(), "获取验证码失败，请重新获取", Toast.LENGTH_SHORT).show();
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("phone", phone);
				return params;
			}
		};
		if (NetWorkUtils.getConnectivity()) {
			Request.getQueue(this).add(getcodeRequest);
		} else {
			Toast.makeText(getApplicationContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
		}
	}
	private void next() {
		code = etVerifyCode.getText().toString();
		pass1 = etPass1.getText().toString();
		pass2 = etPass2.getText().toString();
		if (phone.length() != 11) {
			Toast.makeText(getApplicationContext(), "手机号码格式错误", Toast.LENGTH_SHORT).show();
			return;
		}
		if (code.length() < 1 || code.length() != 6) {
			Toast.makeText(getApplicationContext(), "请输入收到的6位验证码", Toast.LENGTH_SHORT).show();
			return;
		}
		if (pass1.length() < 6) {
			Toast.makeText(getApplicationContext(), "密码长度过短", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!pass1.equals(pass2)) {
			Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
			return;
		}

		dialog.setMessage("正在重置密码");
		dialog.show();
		registerRequest = new SessionStringRequest(Method.POST, API.User.urlForgot, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				LogUtil.i("response", response);
				JSONObject object;
				try {
					object = new JSONObject(response);

					int code = object.getInt("code");
					if (code == 200) {
						Toast.makeText(getApplicationContext(), "重置密码成功", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					dialog.dismiss();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.dismiss();
				LogUtil.i("response", error.getMessage() + "");
				Toast.makeText(getApplicationContext(), "重置密码失败,请稍后再试", Toast.LENGTH_SHORT).show();
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("phone", phone);
				params.put("password", Md5.getMD5String(pass1 + "{ofd.ptms}"));
				params.put("code", code);
				return params;
			}
		};
		registerRequest.setHeader(cookie);
		Request.getQueue(this).add(registerRequest);
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (app.getTime() != 60) {
			time = app.getTime();
			mHandler.sendEmptyMessage(app.VERIFY_CODE);
			tvVerifyCode.setClickable(false);
		}
	}
}
