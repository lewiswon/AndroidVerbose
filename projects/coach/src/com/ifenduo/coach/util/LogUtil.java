package com.ifenduo.coach.util;

import android.util.Log;

public class LogUtil {
	private static boolean debug = true;

	public static void e(String tag, String str) {
		if (debug) {
			Log.e(tag, str);
		}
	}
	public static void i(String tag, String str) {
		if (debug) {
			Log.i(tag, str);
		}
	}
	public static void d(String tag, String str) {
		if (debug) {
			Log.d(tag, str);
		}
	}
	public static void w(String tag, String str) {
		if (debug) {
			Log.w(tag, str);
		}
	}
}
