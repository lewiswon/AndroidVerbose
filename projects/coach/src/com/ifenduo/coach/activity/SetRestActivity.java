package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.activity.BaseTimePickActivity.DateCheck;
import com.ifenduo.coach.bean.TimeItem;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnStateListener;
import com.ifenduo.coach.util.CacheUtils.TimeOutModel;
import com.ifenduo.coach.util.Utils;

public class SetRestActivity extends BaseActivity implements OnClickListener {
	private TextView centerTv, leftTv, enterTV;
	private RadioGroup radioGroup;
	private RadioButton allRadioButton, partRadioButton;
	private LinearLayout timerPickerLL;
	private int[] timeLLs, peoples, times;
	protected ArrayList<DateCheck> dateChecks;
	protected ArrayList<DateCheck> checked;
	protected String startTime = "", endTime = "";
	private String date;
	private String[] timeStrs;
	private int restCount;
	// 是否有人预约标识符
	private boolean hasBook = false;
	// 是否全天休息
	private boolean isFullDay = true;
	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_set_rest);
		timeLLs = new int[]{R.id.ll_time_01, R.id.ll_time_02, R.id.ll_time_03, R.id.ll_time_04, R.id.ll_time_05, R.id.ll_time_06, R.id.ll_time_07, R.id.ll_time_08, R.id.ll_time_09, R.id.ll_time_10,
				R.id.ll_time_11, R.id.ll_time_12, R.id.ll_time_13, R.id.ll_time_14};
		peoples = new int[]{R.id.tv_people_01, R.id.tv_people_02, R.id.tv_people_03, R.id.tv_people_04, R.id.tv_people_05, R.id.tv_people_06, R.id.tv_people_07, R.id.tv_people_08, R.id.tv_people_09,
				R.id.tv_people_10, R.id.tv_people_11, R.id.tv_people_12, R.id.tv_people_13, R.id.tv_people_14};
		times = new int[]{R.id.tv_time_01, R.id.tv_time_02, R.id.tv_time_03, R.id.tv_time_04, R.id.tv_time_05, R.id.tv_time_06, R.id.tv_time_07, R.id.tv_time_08, R.id.tv_time_09, R.id.tv_time_10,
				R.id.tv_time_11, R.id.tv_time_12, R.id.tv_time_13, R.id.tv_time_14};
		leftTv = (TextView) findViewById(R.id.set_rest_top).findViewById(R.id.tv_left_top_bar);
		centerTv = (TextView) findViewById(R.id.set_rest_top).findViewById(R.id.tv_center_top_bar);
		enterTV = (TextView) findViewById(R.id.tv_enter_set_rest);
		enterTV.setOnClickListener(this);
		radioGroup = (RadioGroup) findViewById(R.id.rg_rest_activity);
		allRadioButton = (RadioButton) findViewById(R.id.rb_rest_all_day);
		partRadioButton = (RadioButton) findViewById(R.id.rb_rest_part);
		timerPickerLL = (LinearLayout) findViewById(R.id.timer_picker);
		date = getIntent().getStringExtra("date");
		timeStrs = getResources().getStringArray(R.array.times);
		initViews();
		initTimePick();
		getData();
	}
	private void initViews() {
		centerTv.setText(date.substring(5, 7) + "月" + date.substring(8, 10) + "日");
		leftTv.setVisibility(View.VISIBLE);
		leftTv.setOnClickListener(this);
		leftTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.back_arrow), null, null, null);
		initListener();
	}
	private void initListener() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.rb_rest_all_day && hasBook) {
					Toast.makeText(getApplicationContext(), "今天已有预约,不能全天休息", Toast.LENGTH_SHORT).show();
					return;
				}
				allRadioButton.setBackgroundColor(getResources().getColor(R.color.unfocus));
				partRadioButton.setBackgroundColor(getResources().getColor(R.color.unfocus));
				switch (checkedId) {
					case R.id.rb_rest_all_day :
						isFullDay = true;
						timerPickerLL.setAlpha(0.5f);
						allRadioButton.setBackgroundColor(getResources().getColor(R.color.brand_blue));
						break;

					case R.id.rb_rest_part :
						timerPickerLL.setAlpha(1f);
						isFullDay = false;
						partRadioButton.setBackgroundColor(getResources().getColor(R.color.brand_blue));
						for (DateCheck item : dateChecks) {
							item.timeTv.setText(item.time);
							item.peopleTv.setVisibility(View.VISIBLE);
							item.parentLL.setBackgroundResource(R.drawable.book_not_full);
							item.peopleTv.setTextColor(getResources().getColor(R.color.text_black));
							item.timeTv.setTextColor(getResources().getColor(R.color.text_black));
							if (item.type == 2) {

								item.peopleTv.setText("0人");
							} else {
								item.peopleTv.setText(item.people + "人");
							}
						}

						break;

					default :
						break;
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_left_top_bar :
				finish();
				setResult(RESULT_CANCELED);
				overridePendingTransition(R.anim.end_activity_in, R.anim.end_activity_out);
				break;
			case R.id.tv_enter_set_rest :
				setRest();
				break;
			case R.id.rb_rest_all_day :
				break;
			case R.id.rb_rest_part :
				break;
			default :
				break;
		}
	};
	private void setRest() {
		dialog.setMessage("正在设置");
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("day", date);
		if (isFullDay) {
			params.put("type", "2");
		} else {
			params.put("type", "1");
			String time = "";
			for (int i = 0; i < checked.size(); i++) {
				DateCheck check = checked.get(i);
				if (i == (checked.size() - 1)) {
					time = time + check.timeTv.getText().toString();
				} else {
					time = time + check.timeTv.getText() + ",";
				}
			}
			params.put("time", time);
		}
		dialog.show();
		new Request<String>(this).stateRequest(API.User.setRest, params, false, new OnStateListener() {

			@Override
			public void onSuccess(String response) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_SHORT).show();
				setResult(RESULT_OK);
				finish();
			}

			@Override
			public void onError(String error) {
				Toast.makeText(getApplicationContext(), "设置失败,请稍后再试", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});

	}
	private void initTimePick() {
		dateChecks = new ArrayList<DateCheck>();
		checked = new ArrayList<DateCheck>();
		for (int i = 0; i < timeLLs.length; i++) {
			final int index = i;
			DateCheck dateCheck = new DateCheck();
			dateCheck.isCheck = false;
			dateCheck.peopleTv = (TextView) findViewById(peoples[i]);
			dateCheck.timeTv = (TextView) findViewById(times[i]);
			dateCheck.index = i + 1;
			dateCheck.time = timeStrs[i];
			if (i == 4) {
				dateCheck.isRest = true;
				dateCheck.timeTv.setText("休息");
				dateCheck.peopleTv.setVisibility(View.GONE);
				dateCheck.timeTv.setHeight(Utils.dip2px(getApplicationContext(), 60));
				dateCheck.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
				dateCheck.type = 2;
			} else {
				dateCheck.isRest = false;
			}
			dateCheck.time = dateCheck.timeTv.getText().toString();
			dateCheck.parentLL = (LinearLayout) findViewById(timeLLs[i]);
			dateChecks.add(dateCheck);
			dateCheck.peopleTv.setText(dateCheck.people + "人");
			findViewById(timeLLs[i]).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (isFullDay) {
						Toast.makeText(getApplicationContext(), "全天休息,不需要选择时间段", Toast.LENGTH_SHORT).show();
						return;
					}
					DateCheck item = dateChecks.get(index);
					if (item.people != 0 && item.type != 2) {
						Toast.makeText(getApplicationContext(), "该时段有预约，不能休息", Toast.LENGTH_SHORT).show();
						return;
					}
					if (item.isCheck) {
						item.isCheck = false;
						checked.remove(item);
						item.parentLL.setBackgroundResource(R.drawable.book_not_full);
						item.peopleTv.setTextColor(getResources().getColor(R.color.text_black));
						item.timeTv.setTextColor(getResources().getColor(R.color.text_black));

					} else if (!item.isCheck) {
						item.isCheck = true;
						checked.add(item);
						item.parentLL.setBackgroundResource(R.drawable.color_blue_check);
						item.peopleTv.setTextColor(getResources().getColor(R.color.white));
						item.timeTv.setTextColor(getResources().getColor(R.color.white));
					}
				}
			});
		}

	}
	private void getData() {
		Type type = new TypeToken<BaseResponse<TimeItem>>() {
		}.getType();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", app.getCurrentUser().getId());
		params.put("time", date);
		params.put("pageSize", "20");
		new Request<TimeItem>(this).getList(API.Book.urlTime, params, true, TimeOutModel.MODEL_SHORT, type, new Request.OnListResponse<TimeItem>() {
			@Override
			public void onSuccess(ArrayList<TimeItem> bookList, int total) {
				if (bookList.size() > 0) {
					for (TimeItem item : bookList) {
						if (item.getType() == 1) {
							hasBook = true;
						}
						if (item.getType() == 0) {
							hasBook = true;
						}
						if (item.getType() == 2) {
							restCount += 1;
						}
						String str = item.getStartTime().substring(0, 2);
						int start = Integer.parseInt(str);
						int offset = (start - 8);
						DateCheck check = dateChecks.get(offset);
						check.people = item.getPeoples();
						check.isVip = item.isVIP();
						check.type = item.getType();
					}
					if (restCount == 13) {
						isFullDay = true;
						radioGroup.check(R.id.rb_rest_all_day);
					} else {
						radioGroup.check(R.id.rb_rest_part);
					}
					refreshPicker();
				} else {
				}
			}

			private void refreshPicker() {
				for (DateCheck item : dateChecks) {
					item.peopleTv.setText(item.people + "人");
					if (item.type == 2) {
						item.timeTv.setText("休息");
						item.peopleTv.setVisibility(View.GONE);
						item.timeTv.setHeight(Utils.dip2px(getApplicationContext(), 60));
						item.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
					}
					if (item.people == 4) {
						item.peopleTv.setTextColor(getResources().getColor(R.color.unfocus));
						item.timeTv.setTextColor(getResources().getColor(R.color.unfocus));
					}

				}
			}

			@Override
			public void onError(String error) {
			}
		});
	}

	class DateCheck {
		TextView peopleTv;
		TextView timeTv;
		String time;
		boolean isCheck;
		boolean isRest;
		int type;
		boolean isVip = false;
		LinearLayout parentLL;
		int index;
		int people = 0;

	}
}
