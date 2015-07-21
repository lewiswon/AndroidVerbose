package com.ifenduo.coach.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.ReviewFromStudentActivity;
import com.ifenduo.coach.activity.StudentRecordActivity;
import com.ifenduo.coach.bean.Record;
import com.ifenduo.coach.fragment.CalendarFragment;
import com.ifod.viewholder.CommonAdapter;
import com.ifod.viewholder.ViewHolder;

public class CalendarListAdapter extends CommonAdapter<Record> {

	public CalendarListAdapter(Context context, ArrayList<Record> list, int layoutId) {
		super(context, list, layoutId);
	}

	@Override
	public void setView(ViewHolder viewHolder, int position, final Record item) {
		String learnType = "";
		switch (item.getLearnType()) {
			case 0 :
				learnType = "初次训练";
				break;
			case 1 :
				learnType = "一般训练";
				break;
			case 2 :
				learnType = "初次训练";
				break;
			default :
				break;
		}
		String startTime = item.getStartTime(), endTime = item.getEndTime();
		if (startTime == null) {
			startTime = "";
		}
		if (endTime == null) {
			endTime = "";
		}
		viewHolder.setTextView(R.id.tv_name_item_calendar, item.getName()).setTextView(R.id.tv_type_item_calendar, learnType).setTextView(R.id.tv_start_item_calendar, startTime)
				.setTextView(R.id.tv_end_item_calendar, endTime);

		viewHolder.getView(R.id.ll_calendar_card).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 如果时间过期，则进入评论列表
				int today = Integer.parseInt(CalendarFragment.TODAY_DATE);
				int currentday = Integer.parseInt(CalendarFragment.CHECDE_DAY);
				if (currentday < today) {
					Intent intent = new Intent(mContext, ReviewFromStudentActivity.class);
					intent.putExtra("id", item.getAppointmentId());
					mContext.startActivity(intent);
				} else {
					// 如果时间未到 ，进入学员详情页面
					Intent intent = new Intent(mContext, StudentRecordActivity.class);
					String[] studentInfos = new String[]{item.getTraineeId(), item.getName(), item.getHeadpic(), item.getNum()};
					intent.putExtra("student_infos", studentInfos);
					intent.putExtra("vip", false);
					intent.putExtra("from", 1001);
					mContext.startActivity(intent);
				}
			}
		});
	}

}
