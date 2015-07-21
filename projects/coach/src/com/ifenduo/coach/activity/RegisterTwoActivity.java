package com.ifenduo.coach.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.SessionStringRequest;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.util.NetWorkUtils;

public class RegisterTwoActivity extends BaseActivity implements OnClickListener {
	private TextView tvComplete, tvBack;
	private EditText etAge, etName;
	private String age, name, pass, phone, code, cookie;
	private SessionStringRequest sessionStringRequest;
	@Override
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_register_two);
		tvBack = (TextView) findViewById(R.id.tv_left_register_two);
		tvComplete = (TextView) findViewById(R.id.tv_next_register_two);
		etName = (EditText) findViewById(R.id.et_name_register_two);
		etAge = (EditText) findViewById(R.id.et_aget_register);
		tvBack.setOnClickListener(this);
		tvComplete.setOnClickListener(this);
		Bundle bundle = getIntent().getExtras();
		phone = bundle.getString("phone");
		pass = bundle.getString("password");
		code = bundle.getString("code");
		cookie = bundle.getString("cookie");
		etAge.setOnClickListener(this);
		dialog.setMessage("正在注册...");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_left_register_two :
				finish();
				break;
			case R.id.tv_next_register_two :
				register();
				break;
			case R.id.et_aget_register :
				Calendar calendar = Calendar.getInstance();
				new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						etAge.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
					}
				}, 1990, 5, 1).show();
				break;
			default :
				break;
		}
	}
	private void register() {
		name = etName.getText().toString();
		age = etAge.getText().toString();
		if (name.length() < 2) {
			Toast.makeText(getApplicationContext(), "请输入真实姓名", Toast.LENGTH_SHORT).show();
			return;
		}
		if (age.length() < 1) {
			Toast.makeText(getApplicationContext(), "请选择出生年月", Toast.LENGTH_SHORT).show();
			return;
		}
		dialog.show();
		sessionStringRequest = new SessionStringRequest(Method.POST, API.User.register, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				LogUtil.i("response", response);
				try {
					JSONObject object = new JSONObject(response);
					int code = object.getInt("code");
					if (code == 200) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(getApplicationContext(), LoginActivity.class));
						finish();
					} else {
						String str = object.getString("msg");
						Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
						if (str.equals("验证码错误")) {
							finish();
						}
						dialog.dismiss();
					}
				} catch (JSONException e) {
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.dismiss();
				LogUtil.i("response", error.getMessage() + "");
				Toast.makeText(getApplicationContext(), "注册失败,请稍后再试", Toast.LENGTH_SHORT).show();
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", name);
				params.put("phone", phone);
				params.put("password", pass);
				params.put("birthday", age);
				params.put("code", code);
				return params;
			}
		};
		if (NetWorkUtils.getConnectivity()) {
			sessionStringRequest.setHeader(cookie);
			Request.getQueue(this).add(sessionStringRequest);
		} else {
			Toast.makeText(getApplicationContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
		}

		/*
		 * new Request<String>(this).stateRequest(API.User.register, params,
		 * false, new Request.OnStateListener() {
		 * 
		 * @Override public void onSuccess(String response) {
		 * Toast.makeText(getApplicationContext(), "注册成功",
		 * Toast.LENGTH_SHORT).show(); startActivity(new
		 * Intent(getApplicationContext(), LoginActivity.class)); }
		 * 
		 * @Override public void onError(String error) {
		 * Toast.makeText(getApplicationContext(), error,
		 * Toast.LENGTH_SHORT).show(); } });
		 */

	};
}
