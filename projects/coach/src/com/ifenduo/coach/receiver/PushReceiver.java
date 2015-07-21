package com.ifenduo.coach.receiver;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.ifenduo.coach.activity.BaseActivity;
import com.ifenduo.coach.activity.MainActivity;
import com.ifenduo.coach.util.LogUtil;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

public class PushReceiver extends BroadcastReceiver {
	private NotificationManager			notificationManager;
	private String						TAG				= PushReceiver.class.getName();
	public static final String			ACTION_REFRESH	= "com.ifenduo.refresh";
	public static ArrayList<OnRefresh>	refreshList		= new ArrayList<OnRefresh>();

	@Override
	public void onReceive(Context context, Intent intent) {
		if (notificationManager == null) {
			notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		Bundle bundle = intent.getExtras();
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			Log.d(TAG, "JPush用户注册成功");

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "接受到推送下来的自定义消息");

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "接受到推送下来的通知");
			MainActivity.getInstance().getUnhandleCount();
			MainActivity.getInstance().onActivityResult(0, 0, null);
			refresh();
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "用户点击打开了通知");
			Intent i = new Intent(context, MainActivity.class);
			i.putExtra("navi", true);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);

		} else if (ACTION_REFRESH.equals(intent.getAction())) {
			LogUtil.d("refresh", "refresh");
			refresh();
		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
	}

	public static void registerRefreshListener(OnRefresh refresh) {
		if (!refreshList.contains(refresh)) {
			refreshList.add(refresh);
		}
	}

	private void refresh() {
		if (refreshList.size() > 0) {
			for (OnRefresh refresh : refreshList) {
				refresh.refresh();
			}
		}
	}

	public interface OnRefresh {
		public void refresh();
	}
}
