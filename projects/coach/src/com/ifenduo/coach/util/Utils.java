package com.ifenduo.coach.util;

import java.util.ArrayList;

import android.content.Context;

import com.handmark.pulltorefresh.library.LoadingLayoutProxy;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.adapter.WeekGridViewAdapter;

public class Utils {
	private static ArrayList<WeekGridViewAdapter> adapters = new ArrayList<WeekGridViewAdapter>();
	public static void setPullToFreshListViewHeader(PullToRefreshListView listView) {
		LoadingLayoutProxy loadingLayoutProxy = (LoadingLayoutProxy) listView.getLoadingLayoutProxy(true, true);
		loadingLayoutProxy.setRefreshingLabel("");
		loadingLayoutProxy.setReleaseLabel("");
		loadingLayoutProxy.setPullLabel("");
	}
	/*
	 * public static void addAdapter(WeekGridViewAdapter adapter) { if
	 * (!adapters.contains(adapter)) { adapters.add(adapter); } }
	 */
	public static void notifyAapter() {
		for (WeekGridViewAdapter adapter : adapters) {
			if (adapter != null) {
				adapter.notifyDataSetChanged();
			}
		}
	}
	public static void addAdapter(WeekGridViewAdapter adapter) {
		if (!adapters.contains(adapter)) {
			adapters.add(adapter);
		}
	}
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
