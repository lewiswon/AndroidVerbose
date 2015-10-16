package com.lewis.downloaddemo.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CommonAdapter<T> extends BaseAdapter {
	private ArrayList<T> mList;
	protected Context mContext;
	private int mLayoutId;

	public CommonAdapter(Context context, ArrayList<T> list, int layoutId) {
		this.mList = list;
		this.mContext = context;
		this.mLayoutId = layoutId;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	// 刷新列表
	public void setList(ArrayList<T> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	// 在现有列表中追加数据
	public void appendList(ArrayList<T> list) {
		this.mList.addAll(list);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder = ViewHolder.get(arg0, mLayoutId, arg1, arg2, mContext);
		setView(viewHolder, arg0, mList.get(arg0));
		return viewHolder.getConvertView();
	}

	public abstract void setView(ViewHolder viewHolder, int position, T item);
}
