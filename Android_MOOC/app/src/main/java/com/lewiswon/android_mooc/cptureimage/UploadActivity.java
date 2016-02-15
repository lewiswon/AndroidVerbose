package com.lewiswon.android_mooc.cptureimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.lewiswon.android_mooc.BaseActivity;
import com.lewiswon.android_mooc.R;

/**
 * Created by Administrator on 02/15/2016.
 */
public class UploadActivity extends BaseActivity {
    private String path;
    private ProgressBar progressBar;
    private ImageView imageView;
    private VideoView videoView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        progressBar = (ProgressBar) findViewById(R.id.upload_progress);
        imageView = (ImageView) findViewById(R.id.upload_image_view);
        videoView = (VideoView) findViewById(R.id.upload_video_view);
        textView = (TextView) findViewById(R.id.upload_percent);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        boolean isImage = intent.getBooleanExtra("image", false);
        if (path != null) {
            preview(isImage);
        } else {

        }
    }

    private void preview(boolean isImage) {
        if (isImage) {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(path);
            videoView.start();
        }
    }

    public void upload(View v) {

    }
}
