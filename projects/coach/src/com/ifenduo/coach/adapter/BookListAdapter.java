package com.ifenduo.coach.adapter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.BookProcessActivity;
import com.ifenduo.coach.activity.MainActivity;
import com.ifenduo.coach.bean.Book;
import com.ifenduo.coach.fragment.HandledBookFragment;
import com.ifenduo.coach.fragment.UnhandleBookFragment;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.view.ConfirmPopWindow;
import com.ifod.viewholder.CommonAdapter;
import com.ifod.viewholder.ViewHolder;

public class BookListAdapter extends CommonAdapter<Book> {
	private int tag;
	private Context mContext;
	private String appointType, type;
	public BookListAdapter(Context context, ArrayList<Book> list, int layoutId, int tag) {
		super(context, list, layoutId);
		this.tag = tag;
		mContext = context;
	}

	@Override
	public void setView(final ViewHolder viewHolder, int position, final Book item) {

		TextView textView = (TextView) viewHolder.getView(R.id.tv_type_item_book);
		switch (item.getAppointmentType()) {
			case 0 :
				appointType = "报名";
				textView.setVisibility(View.GONE);
				break;
			case 2 :
				appointType = item.getDay().substring(5, 7) + "月" + item.getDay().substring(8, 10) + "日";
				break;
			case 3 :
				appointType = "待学员确认";
				break;
			case 4 :
				appointType = "学习中";
				// date(item.getDay());
				break;
			case 7 :
				appointType = "已完成";
				break;
			case 8 :
				appointType = "已被拒绝";
				break;
			default :
				break;
		}
		type = "训练";
		switch (item.getLearnType()) {
			case 0 :
				type = "初次" + type;
				break;
			case 1 :
				type = "一般" + type;
				break;
			case 2 :

				type = "复习" + type;
				break;

			default :
				break;
		}
		if (item.getAppointmentType() == 3) {
			type = type + "(推送计划)";
		}
		textView.setText(type);
		TextView nameTv = viewHolder.getView(R.id.tv_name_item_book);
		String day = item.getDay();
		if (day == null) {
			day = "";
		}
		nameTv.setText(Html.fromHtml(item.getName() + " <font color=gray>" + day + "</font>"));
		viewHolder.setTextView(R.id.tv_appoint_type_item_book, appointType);
		if (tag == UnhandleBookFragment.TAG_UNHANDLE) {

			viewHolder.getView(R.id.ll_card_view_book).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					Intent intent = new Intent(mContext, BookProcessActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("type", UnhandleBookFragment.FROM_BOOK_LIST);
					intent.putExtras(bundle);
					switch (item.getAppointmentType()) {
						case 0 :
							ConfirmPopWindow popWindow = new ConfirmPopWindow(mContext);
							popWindow.setProperty(item);
							popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
							popWindow.setOnDismissListener(new OnDismissListener() {

								@Override
								public void onDismiss() {
									((MainActivity) mContext).onActivityResult(0, 0, null);
									InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
									if (imm != null) {
										imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
									}
								}
							});
							break;
						case 2 :

							String[] strs = new String[]{item.getName(), type, item.getDay(), item.getId()};
							intent.putExtra("bookinfo", strs);
							intent.putExtra("vip", item.isVip());
							((MainActivity) mContext).startActivityForResult(intent, UnhandleBookFragment.REQUEST_CODE);
							break;
						case 3 :
							return;
						default :
							break;
					}

				}
			});
		}
	}
	public void date(String day) {
		try {
			java.util.Date date = new SimpleDateFormat().parse(day);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			Calendar currentCal = Calendar.getInstance();
			LogUtil.i("currnt", currentCal.getTimeInMillis() + ":" + cal.getTimeInMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
