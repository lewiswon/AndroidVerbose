package com.ifenduo.coach.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.DateItem;
import com.ifod.viewholder.ViewHolder;

import android.content.Context;
import android.graphics.Picture;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MonthGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<DateItem> list;
	private int start, end;

	public MonthGridViewAdapter(Context context, ArrayList<DateItem> list, int start, int end) {
		this.mContext = context;
		this.list = list;
		this.start = start;
		this.end = end;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	public void setList(ArrayList<DateItem> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_month_calendar, parent, false);
			viewHolder.tv_day = (TextView) convertView.findViewById(R.id.tv_day_calendar);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position < (start - 1)) {
			viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.not_current_month));
		}
		if (position > (getCount() - end)) {
			viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.not_current_month));
		}
		viewHolder.tv_day.setText(list.get(position).day);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		if (list.get(position).getCal().getTimeInMillis() < calendar.getTimeInMillis()) {
			viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.book_full));
		} else {
			if (list.get(position).flag == 1) {
				convertView.setBackgroundResource(R.drawable.calendar_test);
				viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.white));
			}
			if (list.get(position).flag == 2) {
				convertView.setBackgroundResource(R.drawable.calendar_full);
			}
		}

		return convertView;
	}

	class ViewHolder {
		TextView tv_week;
		TextView tv_day;
	}
}
