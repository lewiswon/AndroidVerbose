package com.ifenduo.coach.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.DateItem;
import com.ifenduo.coach.fragment.CalendarFragment;

public class WeekGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<DateItem> list;
	public WeekGridViewAdapter(Context context, ArrayList<DateItem> list) {
		this.mContext = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_week_grid, parent, false);
			viewHolder.tv_day = (TextView) convertView.findViewById(R.id.tv_day_item_week);
			viewHolder.tv_week = (TextView) convertView.findViewById(R.id.tv_week_item_week);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String[] weeks = mContext.getResources().getStringArray(R.array.week);
		viewHolder.tv_week.setText(weeks[position]);
		viewHolder.tv_day.setText(list.get(position).day);
		switch (position) {
			case 5 :
				viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.text_red));
				viewHolder.tv_week.setTextColor(mContext.getResources().getColor(R.color.text_red));
				break;
			case 6 :
				viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.text_red));
				viewHolder.tv_week.setTextColor(mContext.getResources().getColor(R.color.text_red));
				break;
			default :
				break;
		}
		if (list.get(position).checkTag.equals(CalendarFragment.CHECDE_DAY)) {
			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.brand_blue));
			viewHolder.tv_week.setTextColor(mContext.getResources().getColor(R.color.white));
			viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.white));
		} else {

			convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
			if (position > 4) {
				viewHolder.tv_week.setTextColor(mContext.getResources().getColor(R.color.text_red));
				viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.text_red));
			} else {
				viewHolder.tv_week.setTextColor(mContext.getResources().getColor(R.color.text_black));
				viewHolder.tv_day.setTextColor(mContext.getResources().getColor(R.color.text_black));
			}

		}
		return convertView;
	}
	class ViewHolder {
		TextView tv_week;
		TextView tv_day;
	}	
	public void calendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
	}
}
