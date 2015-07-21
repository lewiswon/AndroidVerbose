package com.ifenduo.coach.view;

import java.util.HashMap;
import java.util.Map;

import android.R.menu;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.MainActivity;
import com.ifenduo.coach.bean.Book;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnStateListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ConfirmPopWindow extends PopupWindow implements OnClickListener {
	private EditText mEdit;
	private TextView cancleTv, enterTv, nameTv;
	private RoundedImageView headRI;
	private Context mContext;
	private String num, appointId;
	private ImageLoader imageLoader;
	public ConfirmPopWindow(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.pop_confirm_code, null);
		this.mContext = context;
		setContentView(view);
		setOutsideTouchable(true);
		setFocusable(true);
		ColorDrawable drawable = new ColorDrawable(00000000);
		setBackgroundDrawable(drawable);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
		imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
		initViews(view);

	}
	public void setProperty(Book book) {
		this.appointId = book.getId();
		nameTv.setText(book.getName());
		imageLoader.displayImage(API.IMG_SERVER + book.getHeadpic(), headRI);
	}
	private void initViews(View view) {
		mEdit = (EditText) view.findViewById(R.id.et_code_pop);
		cancleTv = (TextView) view.findViewById(R.id.tv_cancle_pop);
		enterTv = (TextView) view.findViewById(R.id.tv_enter_pop);
		nameTv = (TextView) view.findViewById(R.id.tv_name_pop);
		headRI = (RoundedImageView) view.findViewById(R.id.ri_head_pop);
		enterTv.setOnClickListener(this);
		cancleTv.setOnClickListener(this);
		// 唤起键盘
		mEdit.requestFocus();
		mEdit.setFocusable(true);
		mEdit.setFocusableInTouchMode(true);
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.showSoftInput(mEdit, InputMethodManager.RESULT_SHOWN);
				inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		};
		handler.sendEmptyMessageDelayed(0, 100);

	}
	public void confirm() {
		((MainActivity) mContext).getDialog().setMessage("正在确认报名");
		((MainActivity) mContext).getDialog().show();
		Map<String, String> params = new HashMap<String, String>();
		params.put("appointmentId", appointId);
		params.put("num", num);
		new Request<String>(mContext).stateRequest(API.Book.urlConfirmRegister, params, false, new OnStateListener() {

			@Override
			public void onSuccess(String response) {
				((MainActivity) mContext).getDialog().dismiss();
				Toast.makeText(mContext, "报名确认成功", Toast.LENGTH_SHORT).show();
				dismiss();
			}

			@Override
			public void onError(String error) {
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_enter_pop :
				num = mEdit.getText().toString();
				if (num.length() < 1) {
					Toast.makeText(mContext, "请输入学时卡后再确认", Toast.LENGTH_SHORT).show();
					return;
				}
				confirm();
				break;
			case R.id.tv_cancle_pop :
				dismiss();
				break;
			default :
				break;
		}
	}
}
