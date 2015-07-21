package com.ifenduo.coach.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.Comment;
import com.ifenduo.coach.bean.Notice;
import com.ifenduo.coach.bean.Post;
import com.ifenduo.coach.bean.Tag;
import com.ifenduo.coach.http.BaseResponse;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnListResponse;
import com.ifenduo.coach.http.Request.OnStateListener;
import com.ifenduo.coach.view.MyGridView;
import com.ifenduo.coach.view.RoundedImageView;
import com.ifod.viewholder.CommonAdapter;
import com.ifod.viewholder.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReviewActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	private final int TYPE_TRAIN = 1, TYPE_PROBLEM = 2;
	private int currentChecked = -1;
	private ArrayList<Tag> trainChecks, problemChecks, list2, list;
	private TextView reviewTv, nameTv, dateTv, timeTv, numTv;
	private RoundedImageView headRI;
	private ImageLoader imageLoader;
	private GridView gvTrain;
	private MyGridView gvProblem;
	private TagAdapter trainAdapter, problemAdapter;
	private Comment comment;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_review);
		reviewTv = (TextView) findViewById(R.id.tv_review_review);
		gvTrain = (GridView) findViewById(R.id.gv_train);
		gvProblem = (MyGridView) findViewById(R.id.gv_problem);
		nameTv = (TextView) findViewById(R.id.tv_name_review);
		dateTv = (TextView) findViewById(R.id.tv_date_review);
		numTv = (TextView) findViewById(R.id.tv_num_review);
		timeTv = (TextView) findViewById(R.id.tv_time_review);
		headRI = (RoundedImageView) findViewById(R.id.ri_head_review);
		gvProblem.setSelector(android.R.color.transparent);
		gvTrain.setSelector(android.R.color.transparent);
		comment = (Comment) getIntent().getSerializableExtra("comment");
		imageLoader = ImageLoader.getInstance();
		initViews();
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNow", 1 + "");
		params.put("pageSize", 5 + "");
		params.put("type", "guide");
		Type type = new TypeToken<BaseResponse<Post>>() {
		}.getType();
	}
	private void initViews() {
		nameTv.setText(comment.getName());
		numTv.setText("学员号:" + comment.getNum());
		dateTv.setText(comment.getDay());
		timeTv.setText(comment.getStartTime() + "-" + comment.getEndTime());
		imageLoader.displayImage(API.IMG_SERVER + comment.getHeadpic(), headRI);
		reviewTv.setOnClickListener(this);
		list = new ArrayList<Tag>();
		list2 = new ArrayList<Tag>();
		trainChecks = new ArrayList<Tag>();
		problemChecks = new ArrayList<Tag>();
		list.add(new Tag("曲线弯道"));
		list.add(new Tag("侧方停车"));
		list.add(new Tag("倒车入库"));
		list.add(new Tag("直角弯"));
		list.add(new Tag("定点停车"));
		list.add(new Tag("坡道启停"));
		trainAdapter = new TagAdapter(this, list, R.layout.item_tag, TYPE_TRAIN);
		problemAdapter = new TagAdapter(this, list2, R.layout.item_tag, TYPE_PROBLEM);
		gvTrain.setAdapter(trainAdapter);
		gvProblem.setAdapter(problemAdapter);
		gvTrain.setOnItemClickListener(this);
		gvProblem.setOnItemClickListener(this);
		getTag();
	}
	public void getTag() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("type", "1");
		Type type = new TypeToken<BaseResponse<Tag>>() {
		}.getType();
		new Request<Tag>(this).getList(API.Comment.urlTag, params, false, null, type, new OnListResponse<Tag>() {

			@Override
			public void onSuccess(ArrayList<Tag> tagList, int total) {
				if (tagList.size() > 0) {
					list2.addAll(tagList);
					problemAdapter.setList(list2);
				}
			}

			@Override
			public void onError(String error) {
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_review_review :

				review();

				break;
			default :
				break;
		}
	}

	private void review() {
		if (problemChecks.size() < 1) {
			Toast.makeText(this, "请先评论", Toast.LENGTH_SHORT).show();
			return;
		}
		if (trainChecks.size() < 1) {
			Toast.makeText(this, "请先选择训练科目", Toast.LENGTH_SHORT).show();
			return;
		}
		String project = "";
		String review = "";
		if (trainChecks.size() > 1) {
			for (int i = 0; i < trainChecks.size(); i++) {
				Tag tag = trainChecks.get(i);
				if (i == trainChecks.size() - 1) {
					project = project + tag.content;
				} else {
					project += tag.content + ",";
				}
			}
		} else {
			project = trainChecks.get(0).content;
		}
		if (problemChecks.size() > 1) {
			for (int i = 0; i < problemChecks.size(); i++) {
				Tag tag = problemChecks.get(i);
				if (i == problemChecks.size() - 1) {
					review = review + tag.content;
				} else {
					review += tag.content + ",";
				}
			}
		} else {
			review = problemChecks.get(0).content;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", comment.getId());
		params.put("comment", review);
		params.put("learnInfo", project);
		new Request<String>(this).stateRequest(API.Comment.urlConfirmComment, params, false, new OnStateListener() {

			@Override
			public void onSuccess(String response) {
				Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
				Comment comt = app.getComment();
				if (comt != null) {
					Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
					intent.putExtra("comment", comt);
					startActivity(intent);
				}
				finish();
			}

			@Override
			public void onError(String error) {
				Log.i("error", error);
			}
		});

	}
	@Override
	public void onBackPressed() {

	}

	class TagAdapter extends CommonAdapter<Tag> {
		public int type;

		public TagAdapter(Context context, ArrayList<Tag> list, int layoutId, int type) {
			super(context, list, layoutId);
			this.type = type;
		}

		@Override
		public void setView(ViewHolder viewHolder, int position, Tag item) {
			viewHolder.setTextView(R.id.tv_tag, item.content);
			if (this.type == TYPE_TRAIN) {
				if (item.checkedPosition == position) {
					viewHolder.getConvertView().setBackgroundResource(R.drawable.tag_checked_selector);
					((TextView) viewHolder.getView(R.id.tv_tag)).setTextColor(getResources().getColor(R.color.white));
				} else {
					viewHolder.getConvertView().setBackgroundResource(R.drawable.tag_selector);
					((TextView) viewHolder.getView(R.id.tv_tag)).setTextColor(getResources().getColor(R.color.text_black));

				}
			} else if (this.type == TYPE_PROBLEM) {
				if (item.checkedPosition == position) {
					viewHolder.getConvertView().setBackgroundResource(R.drawable.tag_checked_selector);
					((TextView) viewHolder.getView(R.id.tv_tag)).setTextColor(getResources().getColor(R.color.white));
				} else {
					viewHolder.getConvertView().setBackgroundResource(R.drawable.tag_selector);
					((TextView) viewHolder.getView(R.id.tv_tag)).setTextColor(getResources().getColor(R.color.text_black));
				}
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (((TagAdapter) arg0.getAdapter()).type == TYPE_PROBLEM) {
			Tag tag = list2.get(arg2);
			if (tag.check) {
				tag.check = false;
				tag.checkedPosition = -1;
				if (problemChecks.contains(tag)) {
					problemChecks.remove(tag);
				}
			} else {
				tag.check = true;
				tag.checkedPosition = arg2;
				if (!problemChecks.contains(tag)) {
					problemChecks.add(tag);
				}
			}
			problemAdapter.notifyDataSetChanged();
		} else if (((TagAdapter) arg0.getAdapter()).type == TYPE_TRAIN) {
			Tag tag = list.get(arg2);
			if (tag.check) {
				tag.check = false;
				tag.checkedPosition = -1;
				if (trainChecks.contains(tag)) {
					trainChecks.remove(tag);
				}
			} else {
				tag.check = true;
				tag.checkedPosition = arg2;
				if (!trainChecks.contains(tag)) {
					trainChecks.add(tag);
				}
			}
			trainAdapter.notifyDataSetChanged();
		}
	}
}
