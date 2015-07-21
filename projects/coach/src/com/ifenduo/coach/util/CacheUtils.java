package com.ifenduo.coach.util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

import com.ifenduo.coach.App;
import com.ifenduo.coach.Constant;

public class CacheUtils {
	private static final int SHORT_TIMEOUT = 1000 * 60 * 5; // 五分钟超时
	private static final int MEDIUM_TIMEOUT = 1000 * 60 * 60 * 2; // 2小时超时
	private static final int MEDIUM_LARGE_TIMEOUT = 1000 * 60 * 60 * 24; // 一天超时；
	private static final int MAX_TIMEOUT = 1000 * 60 * 60 * 24 * 7; // 七天超时；

	public enum TimeOutModel {
		MODEL_SHORT, MODEL_MEDIUM, MODEL_MEDIUM_LARGE, MODEL_MAX, MODEL_FOREVER;
	};

	public static String getCache(String url, TimeOutModel model) {
		if (url == null) {
			return null;
		}
		String result = null;
		String path = Constant.CACHE_DIR + Md5.getMD5String(StringUtils.removeDot(url));
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			long expireTime = System.currentTimeMillis() - file.lastModified();
			if (NetWorkUtils.getConnectivity() == false) {
				return null;
				/*
				 * if (expireTime < 0) { return null; } else if (model ==
				 * TimeOutModel.MODEL_SHORT) { if (expireTime > SHORT_TIMEOUT) {
				 * return null; } } else if (model == TimeOutModel.MODEL_MEDIUM)
				 * { if (expireTime > MEDIUM_TIMEOUT) { return null; } } else if
				 * (model == TimeOutModel.MODEL_MEDIUM_LARGE) { if (expireTime >
				 * MEDIUM_LARGE_TIMEOUT) { return null; } } else if (model ==
				 * TimeOutModel.MODEL_MAX) { if (expireTime > MAX_TIMEOUT) {
				 * return null; } } else if (model ==
				 * TimeOutModel.MODEL_FOREVER) { return null; }
				 */
			}
			try {
				result = FileUtil.File2String(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void saveCache(String url, String data) {
		if (Constant.CACHE_DIR == null) {
			return;
		}
		File dir = new File(Constant.CACHE_DIR);
		if (!dir.exists() && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			dir.mkdir();
		}
		File file = new File(Constant.CACHE_DIR + Md5.getMD5String(StringUtils.removeDot(url)));
		try {
			FileUtil.string2File(data, file);
		} catch (IOException e) {
			Log.i("save", "save error occur");
			e.printStackTrace();
		}

	}
}
