package com.ifenduo.coach.dialog;

import com.ifenduo.coach.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

public class TimeAlertDialog extends Dialog {

	public TimeAlertDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_time_alert);
	}

}
