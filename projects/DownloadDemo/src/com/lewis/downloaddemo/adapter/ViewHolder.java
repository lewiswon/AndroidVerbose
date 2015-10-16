package com.lewis.downloaddemo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
	private SparseArray<View> mViews;
	private View mConvertView;
	private int mPosition;

	public ViewHolder(int layoutId, View convertView, ViewGroup parent, Context context) {
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	};

	// ViewHolder鐨勫叆鍙ｆ柟娉�

	public static ViewHolder get(int position, int layoutId, View convertView, ViewGroup parent, Context context) {
		if (convertView == null) {
			return new ViewHolder(layoutId, convertView, parent, context);
		} else {

			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mPosition = position;
			return viewHolder;
		}
	}

	/**
	 * 鏍规嵁id鑾峰彇view锛屽鏋渧iew鍦╯parsearray涓笉瀛樺湪锛屽氨灏唙iew鏀惧叆sparsearray涓�
	 * 
	 * @param resId
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int resId) {
		View view = mViews.get(resId);
		if (view == null) {
			view = mConvertView.findViewById(resId);
			mViews.put(resId, view);
		}
		return (T) view;
	}

	public View getConvertView() {
		return mConvertView;
	};

	// 璁剧疆textview鐨勫瓧绗�
	public ViewHolder setTextView(int resId, String text) {
		TextView textView = (TextView) this.getView(resId);
		textView.setText(text);
		return this;
	}

	public ViewHolder setImageFromWeb(int resId, String url) {
		ImageView imageView = (ImageView) getView(resId);
		return this;
	}

	public ViewHolder setImageFromFile(int resId, String url) {
		ImageView imageView = (ImageView) getView(resId);
		return this;
	}
}
