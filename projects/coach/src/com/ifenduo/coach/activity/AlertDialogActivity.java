package com.ifenduo.coach.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

import com.ifenduo.coach.R;

public class AlertDialogActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.dialog_time_alert);
		// app.startAlertDialog();
		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(2000);
		// setFinishOnTouchOutside(false);
	}
}
