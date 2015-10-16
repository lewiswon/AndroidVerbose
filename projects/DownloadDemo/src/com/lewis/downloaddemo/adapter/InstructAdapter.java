package com.lewis.downloaddemo.adapter;

import java.util.ArrayList;

import com.lewis.downloaddemo.R;
import com.lewis.downloaddemo.bean.Instruct;

import android.content.Context;

public class InstructAdapter extends CommonAdapter<Instruct> {

	public InstructAdapter(Context context, ArrayList<Instruct> list, int layoutId) {
		super(context, list, layoutId);
	}

	@Override
	public void setView(ViewHolder viewHolder, int position, Instruct item) {
		viewHolder.setTextView(R.id.tv_name, item.getMe_name()).setTextView(R.id.tv_source, item.getMe_source());
	}

}
