package com.ifenduo.coach.adapter;

import java.util.ArrayList;

import android.content.Context;

import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.Student;
import com.ifenduo.coach.view.RoundedImageView;
import com.ifod.viewholder.CommonAdapter;
import com.ifod.viewholder.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class StudentListAdapter extends CommonAdapter<Student> {
	private ImageLoader imageLoader;
	public StudentListAdapter(Context context, ArrayList<Student> list, int layoutId) {
		super(context, list, layoutId);
		imageLoader = ImageLoader.getInstance();
	}
	@Override
	public void setView(ViewHolder viewHolder, int position, Student item) {
		String num = item.getNum() == null ? "普通学员" : "学员编号:" + item.getNum();
		viewHolder.setTextView(R.id.tv_student_name, item.getName()).setTextView(R.id.tv_student_no, num).setTextView(R.id.tv_book_times, "已预约" + item.getTimes() + "次");
		RoundedImageView imageView = (RoundedImageView) viewHolder.getView(R.id.ri_student_image);
		imageLoader.displayImage(API.IMG_SERVER + item.getHeadpic(), imageView);
	}
}
