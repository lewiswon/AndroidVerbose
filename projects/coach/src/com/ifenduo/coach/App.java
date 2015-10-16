package com.ifenduo.coach;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ifenduo.coach.activity.AlertDialogActivity;
import com.ifenduo.coach.bean.Comment;
import com.ifenduo.coach.bean.User;
import com.ifenduo.coach.util.CustomException;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.util.Md5;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.update.UmengUpdateAgent;

public class App extends Application {
	public final int VERIFY_CODE = 1001;
	public final int REFRESH = 1002;
	private int time = 60;
	private static App instance;
	private static Context context;
	public void onCreate() {
		instance = this;
		context = getApplicationContext();
		long time = System.currentTimeMillis();
		JPushInterface.init(this);
		// CustomException exception = CustomException.getInstance();
		// exception.init(this);
		UmengUpdateAgent.update(this);
		JPushInterface.setDebugMode(true);
		File cacheDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "coach/images/cache");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(false) // default
				.cacheOnDisc(true).showStubImage(R.drawable.default_head).showImageOnFail(R.drawable.default_head).showImageForEmptyUri(R.drawable.default_head).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).memoryCacheExtraOptions(250, 188).threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory().memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)).discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).defaultDisplayImageOptions(defaultOptions).build();
		imageLoader.init(config);
		setPushAlias();
	};

	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	public void setPushAlias() {

		if (!getCurrentUser().getId().equals("") && "".equals(getValue("push_alias"))) {
			JPushInterface.resumePush(getApplicationContext());
			JPushInterface.setAlias(this, Md5.getMD5String(getCurrentUser().getId()), new TagAliasCallback() {

				@Override
				public void gotResult(int arg0, String arg1, Set<String> arg2) {
					LogUtil.i("" + arg0, arg1);
					switch (arg0) {
						case 0 :

							setValue("push_alias", arg1);
							break;
						case 1 :

							break;

					}
				}
			});

		}
	}
	public void startAlertDialog() {
		Intent intent = new Intent(this, AlertDialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(intent);
		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000 * 10, pendingIntent);
	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case VERIFY_CODE :
					if (time > 0) {
						handler.sendEmptyMessageDelayed(VERIFY_CODE, 1000);
						time--;
					} else {
						time = 60;
					}
					break;
				case REFRESH :
					PullToRefreshListView listView = (PullToRefreshListView) msg.obj;
					listView.onRefreshComplete();
					Toast.makeText(App.this, "已经是最后一页了", Toast.LENGTH_SHORT).show();
					break;
				default :
					break;
			}
		};
	};
	/**
	 * 获取倒计时
	 * 
	 * @return time
	 */
	public int getTime() {

		return this.time;
	}

	public void setValue(String key, String value) {
		SharedPreferences preferences = getSharedPreferences("coach", 0);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public void login(User user) {
		setValue("user.id", user.getId());
		setValue("user.name", user.getName());
		setValue("user.phone", user.getPhone());
	}
	public void logout() {
		login(new User());
		setValue("push_alias", "");
		JPushInterface.stopPush(getApplicationContext());
	}
	public User getCurrentUser() {
		User user = new User();
		user.setId(getValue("user.id"));
		user.setName(getValue("user.name"));
		user.setPhone(getValue("user.phone"));
		return user;
	}
	public String getValue(String key) {
		SharedPreferences preferences = getSharedPreferences("coach", 0);
		return preferences.getString(key, "");
	}
	public static App getInstance() {
		return instance;
	}
	public static Context getContext() {

		return context;
	}
	Stack<Comment> stack;
	public void setStack(ArrayList<Comment> list) {
		stack = new Stack<Comment>();
		for (Comment comment : list) {
			stack.push(comment);
		}
	}
	public Comment getComment() {
		if (stack.empty()) {
			return null;
		}
		return stack.pop();
	}
}
