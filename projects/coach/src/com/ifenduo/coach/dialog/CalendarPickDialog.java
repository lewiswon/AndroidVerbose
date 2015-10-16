package com.ifenduo.coach.dialog;

import com.ifenduo.coach.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

public class CalendarPickDialog extends Dialog {

	public CalendarPickDialog(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.item_student_list);
	}

}
