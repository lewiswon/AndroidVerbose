package com.ifenduo.coach.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.widget.TextView;

import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.StudentRecord;
import com.ifenduo.coach.bean.Tag;
import com.ifenduo.coach.view.MyGridView;
import com.ifod.viewholder.CommonAdapter;
import com.ifod.viewholder.ViewHolder;

public class StudentRecordListAdapter extends CommonAdapter<StudentRecord> {

	public StudentRecordListAdapter(Context context, ArrayList<StudentRecord> list, int layoutId) {
		super(context, list, layoutId);
	}

	@Override
	public void setView(ViewHolder viewHolder, int position, StudentRecord item) {
		MyGridView gridView = (MyGridView) viewHolder.getView(R.id.gv_problem_train);
		String comment = item.getComment();
		String tags[];
		if (comment.contains(",")) {
			tags = comment.split(",");
		} else {
			tags = new String[]{comment};
		}
		ArrayList<Tag> list = new ArrayList<Tag>();
		for (int i = 0; i < tags.length; i++) {
			list.add(new Tag(tags[i]));
		}
		TagAdapter adapter = new TagAdapter(mContext, list, R.layout.item_tag);
		gridView.setAdapter(adapter);
		viewHolder.setTextView(R.id.tv_coach_item_record, item.getName() + "教练").setTextView(R.id.tv_time_item_record, item.getStartTime() + "-" + item.getEndTime())
				.setTextView(R.id.tv_train_type_item_record, item.getLearnInfo());
		String trainType = "";
		switch (item.getTrainType()) {
			case 0 :
				trainType = "场地教练";
				break;
			case 1 :
				trainType = "道路教练";
				break;
			case 2 :
				trainType = "场地/道路";
				break;
			default :
				break;
		}
		String str = item.getDay().substring(5, 7) + "月" + item.getDay().substring(8, 10) + "日";
		viewHolder.setTextView(R.id.tv_project_item_record, trainType).setTextView(R.id.tv_date_item_record, str);

	}
	class TagAdapter extends CommonAdapter<Tag> {

		public TagAdapter(Context context, ArrayList<Tag> list, int layoutId) {
			super(context, list, layoutId);
		}

		@Override
		public void setView(ViewHolder viewHolder, int position, Tag item) {
			viewHolder.setTextView(R.id.tv_tag, item.name);
			viewHolder.getConvertView().setBackgroundResource(R.color.brand_blue);
			((TextView) viewHolder.getView(R.id.tv_tag)).setTextColor(mContext.getResources().getColor(R.color.white));
		}
	}
}
