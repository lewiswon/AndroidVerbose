package com.ifenduo.coach;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;

public class AppManager {
	private static ArrayList<FragmentActivity> activities = new ArrayList<FragmentActivity>();
	public static void add(FragmentActivity activity) {
		if (activities.contains(activity)) {

		} else {
			activities.add(activity);
		}
	}
	public static void exit() {
		for (FragmentActivity activity : activities) {
			activity.finish();
		}
	}
}
