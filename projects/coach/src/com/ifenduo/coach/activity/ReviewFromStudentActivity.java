package com.ifenduo.coach.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ifenduo.coach.API;
import com.ifenduo.coach.R;
import com.ifenduo.coach.bean.Comment;
import com.ifenduo.coach.http.Request;
import com.ifenduo.coach.http.Request.OnStateListener;
import com.ifenduo.coach.view.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReviewFromStudentActivity extends BaseActivity implements OnClickListener {
	private TextView centerTv, leftTv, nameTv, timeTv, dateTv, contentTv, numTv, tipTv, reviewTagTv;
	private ImageView starIv;
	private String id;
	private ImageLoader imageLoader;
	private RoundedImageView roundedImageView;
	private FrameLayout emptyFl;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_review_from_student);
		leftTv = (TextView) findViewById(R.id.tv_left_top_bar);
		centerTv = (TextView) findViewById(R.id.tv_center_top_bar);
		nameTv = (TextView) findViewById(R.id.tv_name_review_from_student);
		timeTv = (TextView) findViewById(R.id.tv_time_review_from_student);
		dateTv = (TextView) findViewById(R.id.tv_date_review_from_student);
		contentTv = (TextView) findViewById(R.id.tv_comment_content);
		starIv = (ImageView) findViewById(R.id.iv_star_review_from_student);
		numTv = (TextView) findViewById(R.id.tv_num_review_from_student);
		tipTv = (TextView) findViewById(R.id.tv_tip_message);
		emptyFl = (FrameLayout) findViewById(R.id.fl_empty);
		reviewTagTv = (TextView) findViewById(R.id.tv_review_tag);
		id = getIntent().getStringExtra("id");
		roundedImageView = (RoundedImageView) findViewById(R.id.ri_head_pic_from_student);
		imageLoader = ImageLoader.getInstance();
		initViews();
	}
	private void initViews() {
		centerTv.setText("对我的评价");
		leftTv.setVisibility(View.VISIBLE);
		@SuppressWarnings("deprecation")
		Drawable drawable = getResources().getDrawable(R.drawable.back_arrow);
		leftTv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
		leftTv.setOnClickListener(this);
		getData();
	}
	private void getData() {
		dialog.setMessage("正在获取评论");
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		new Request<String>(this).stateRequest(API.Book.urlCommentFromStudent, params, false, new OnStateListener() {

			@Override
			public void onSuccess(String response) {
				Log.i("", response);
				dialog.dismiss();
				JSONObject object;
				try {
					object = new JSONObject(response);
					String str = object.getString("data");
					Comment comment = new Gson().fromJson(str, Comment.class);
					setupCommnet(comment);
				} catch (Exception e) {
					e.printStackTrace();
					tipTv.setText("暂无评论");
					emptyFl.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onError(String error) {
				dialog.dismiss();
				emptyFl.setVisibility(View.VISIBLE);
			}
		});
	}
	// 根据服务器返回的信完善界面信息
	private void setupCommnet(Comment comment) {
		nameTv.setText(comment.getName());
		dateTv.setText(comment.getDay());
		timeTv.setText(comment.getStartTime() + "-" + comment.getEndTime());
		contentTv.setText(comment.getComment());
		numTv.setText("学员号：" + comment.getNum());
		imageLoader.displayImage(API.IMG_SERVER + comment.getHeadpic(), roundedImageView);
		String[] strings = getResources().getStringArray(R.array.review_tag);
		switch (comment.getStar()) {
			case 1 :
				starIv.setImageResource(R.drawable.one_star);
				reviewTagTv.setText(strings[0]);
				break;
			case 2 :
				starIv.setImageResource(R.drawable.two_stars);
				reviewTagTv.setText(strings[1]);
				break;
			case 3 :
				starIv.setImageResource(R.drawable.three_stars);
				reviewTagTv.setText(strings[2]);
				break;
			case 4 :
				starIv.setImageResource(R.drawable.four_stars);
				reviewTagTv.setText(strings[3]);
				break;
			case 5 :
				starIv.setImageResource(R.drawable.five_stars);
				reviewTagTv.setText(strings[4]);
				break;

			default :
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_left_top_bar :
				finish();
				break;

			default :
				break;
		}
	}
}
