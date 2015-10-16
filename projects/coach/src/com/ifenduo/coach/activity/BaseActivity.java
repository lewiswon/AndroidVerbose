package com.ifenduo.coach.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushReceiver;

import com.ifenduo.coach.App;
import com.ifenduo.coach.AppManager;
import com.ifenduo.coach.R;
import com.ifenduo.coach.receiver.PushReceiver.OnRefresh;
import com.ifenduo.coach.util.LogUtil;

public class BaseActivity extends FragmentActivity implements OnRefresh {
	protected App				app;
	protected ProgressDialog	dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		AppManager.add(this);
		app = (App) getApplication();
		dialog = new ProgressDialog(this);
		com.ifenduo.coach.receiver.PushReceiver.registerRefreshListener(this);
	}

	public ProgressDialog getDialog() {
		return this.dialog;
	}

	@Override
	protected void onPause() {
		super.onPause();
		// jpush 统计代码
		JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// jpush 统计代码
		JPushInterface.onResume(this);
	}

	/**
	 * 点击页面空白地方 dimiss软键盘
	 * 
	 * @param ev
	 * @return
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent();
		intent.setAction(com.ifenduo.coach.receiver.PushReceiver.ACTION_REFRESH);
		sendBroadcast(intent);
		overridePendingTransition(R.anim.end_activity_in, R.anim.end_activity_out);
	}

	@Override
	public void refresh() {

	}
}
