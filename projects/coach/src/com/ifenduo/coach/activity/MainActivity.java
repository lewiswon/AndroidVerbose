package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.AppManager;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.Book;
import com.ifenduo.coach.bean.Comment;
import com.ifenduo.coach.fragment.BookFragment;
import com.ifenduo.coach.fragment.CalendarFragment;
import com.ifenduo.coach.fragment.CoachFragment;
import com.ifenduo.coach.fragment.StudentFragment;
import com.ifenduo.coach.fragment.UnhandleBookFragment;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnListResponse;
import com.ifenduo.coach.util.LogUtil;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.view.BadgeView;

public class MainActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_01, iv_02, iv_03, iv_04;
	private boolean isExit = false, isNavi;
	private FragmentManager fragmentManager;
	private Fragment calendarFragment, bookFragment, studentFragment, coachFragment;
	private static MainActivity instance;
	private ArrayList<OnResultListener> onResultListeners;
	private BadgeView badgeView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragmentManager = getSupportFragmentManager();
		calendarFragment = new CalendarFragment();
		bookFragment = new BookFragment();
		studentFragment = new StudentFragment();
		instance = this;
		coachFragment = new CoachFragment();

		fragmentManager.beginTransaction().add(R.id.fragment_container, calendarFragment).commit();
		initViews();
		getComment();
		getUnhandleCount();
		isNavi = getIntent().getBooleanExtra("navi", false);
		if (isNavi) {
			navi2Unhandle();
		}
	}

	private void initViews() {
		if (app.getValue("calendar_first").equals("")) {
			findViewById(R.id.iv_guide_calendar).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					findViewById(R.id.iv_guide_calendar).setVisibility(View.GONE);
					app.setValue("calendar_first", "first");
				}
			});
		} else {
			findViewById(R.id.iv_guide_calendar).setVisibility(View.GONE);
		};
		iv_01 = (ImageView) findViewById(R.id.iv_home_01);
		iv_02 = (ImageView) findViewById(R.id.iv_home_02);
		iv_03 = (ImageView) findViewById(R.id.iv_home_03);
		iv_04 = (ImageView) findViewById(R.id.iv_home_04);
		findViewById(R.id.ll_home_01).setOnClickListener(this);
		findViewById(R.id.ll_home_02).setOnClickListener(this);
		findViewById(R.id.ll_home_03).setOnClickListener(this);
		findViewById(R.id.ll_home_04).setOnClickListener(this);
		LinearLayout ll_home_02 = (LinearLayout) findViewById(R.id.ll_badgeview);
		badgeView = new BadgeView(getApplicationContext());
		badgeView.setTargetView(ll_home_02);
		badgeView.setGravity(Gravity.TOP | Gravity.RIGHT);
	}
	@Override
	public void onClick(View v) {
		clearAll();
		Fragment currentFragment = getCurrentFragment();
		if (calendarFragment.isVisible()) {

		}
		switch (v.getId()) {
			case R.id.ll_home_01 :
				iv_01.setBackgroundResource(R.drawable.calendar_pressed);
				switchFragment(currentFragment, calendarFragment);
				break;
			case R.id.ll_home_02 :
				iv_02.setBackgroundResource(R.drawable.book_pressed);
				switchFragment(currentFragment, bookFragment);
				break;
			case R.id.ll_home_03 :
				iv_03.setBackgroundResource(R.drawable.student_pressed);
				switchFragment(currentFragment, studentFragment);
				break;
			case R.id.ll_home_04 :
				iv_04.setBackgroundResource(R.drawable.coach_pressed);
				switchFragment(currentFragment, coachFragment);
				break;

			default :
				break;
		}

	}

	private void clearAll() {
		iv_01.setBackgroundResource(R.drawable.calendar);
		iv_02.setBackgroundResource(R.drawable.book);
		iv_03.setBackgroundResource(R.drawable.student);
		iv_04.setBackgroundResource(R.drawable.coach);
	}
	public Fragment getCurrentFragment() {
		if (calendarFragment.isVisible()) {
			return calendarFragment;
		} else if (bookFragment.isVisible()) {
			return bookFragment;
		} else if (studentFragment.isVisible()) {
			return studentFragment;
		} else if (coachFragment.isVisible()) {
			return coachFragment;
		}
		return null;
	}
	public void navi2Unhandle() {
		clearAll();
		iv_02.setBackgroundResource(R.drawable.book_pressed);
		Fragment currentFragment = getCurrentFragment();
		switchFragment(calendarFragment, bookFragment);
	}
	public void switchFragment(Fragment from, Fragment to) {
		if (from == null || to == null)
			return;
		FragmentTransaction transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out);
		if (!to.isAdded()) {
			// 隐藏当前的fragment，add下一个到Activity中
			transaction.hide(from).add(R.id.fragment_container, to).commit();
		} else {
			// 隐藏当前的fragment，显示下一个
			transaction.hide(from).show(to).commit();
		}
		// 让menu回去
	}
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			isExit = false;
		};
	};
	@Override
	public void onBackPressed() {
		if (isExit) {
			AppManager.exit();
		} else {
			isExit = true;
			Toast.makeText(this, "再按一次退出找个教练", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 3 * 1000);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			app.logout();
			startActivity(new Intent(this, WelcomeActivity.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getUnhandleCount();
		LogUtil.i("onactivityresult", resultCode + "");
		if (onResultListeners != null) {

			for (int i = 0; i < onResultListeners.size(); i++) {
				onResultListeners.get(i).onresult();
			}
		}
	}
	public void getComment() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		Type type = new TypeToken<BaseResponse<Comment>>() {
		}.getType();
		new Request<Comment>(this).getList(API.Comment.urlCommentList, params, true, null, type, new OnListResponse<Comment>() {

			@Override
			public void onSuccess(ArrayList<Comment> commentList, int total) {
				if (commentList.size() > 0) {
					app.setStack(commentList);
					Comment comment = app.getComment();
					if (comment != null) {
						Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
						intent.putExtra("comment", comment);
						startActivity(intent);
					}
				}
			}

			@Override
			public void onError(String error) {
			}
		});
	}
	public void getUnhandleCount() {
		Type type = new TypeToken<BaseResponse<Book>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put(API.Book.key_id, app.getCurrentUser().getId());
		params.put(API.Book.key_type, API.Book.unhandle);
		params.put("pageNow", 1 + "");
		new Request<Book>(this).getList(API.Book.url, params, true, TimeOutModel.MODEL_SHORT, type, new Request.OnListResponse<Book>() {

			@Override
			public void onSuccess(ArrayList<Book> bookList, int total) {

				badgeView.setBadgeCount(total);
			}

			@Override
			public void onError(String error) {
			}
		});
	}
	public void setOnResultListener(OnResultListener listener) {
		if (onResultListeners == null) {
			onResultListeners = new ArrayList<MainActivity.OnResultListener>();
		}
		onResultListeners.add(listener);
	}
	public interface OnResultListener {
		public void onresult();
	}
	public static MainActivity getInstance() {
		return instance;
	}
}
