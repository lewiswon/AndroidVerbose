package com.ifenduo.coach.util;

import com.ifenduo.coach.App;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.StaticLayout;

public class NetWorkUtils {
	public static boolean getConnectivity() {
		Context context = App.getContext();
		ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}
}
