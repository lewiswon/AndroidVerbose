package com.lewiswon.android_mooc.cptureimage;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.lewiswon.android_mooc.BaseActivity;
import com.lewiswon.android_mooc.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 02/15/2016.
 */
public class CaptureImageActivity extends BaseActivity {
    public static final int IMAGE_REQ = 1001, VIDEO_REQ = 1002;
    public static final int MEDIA_TYPE_IMAGE = 1003, MEDIA_TYPE_VIDEO = 1004;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
    }


    public void capture(View v) {
        if (!isSupportCamera()) {
            Snackbar.make(v, "Sorry,your device do not have a camera.", Snackbar.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.btn_image:
                captureImage();
                break;
            case R.id.btn_video:
                captureVideo();
                break;
        }
    }

    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getMediaOutputUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, IMAGE_REQ);
    }

    private void captureVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        fileUri = getMediaOutputUri(MEDIA_TYPE_VIDEO);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, VIDEO_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQ) {
            String message;
            switch (resultCode) {
                case Activity.RESULT_OK:
                    message = "Image has benn store in " + fileUri.getPath();
                    launchUpload(true);
                    break;
                case Activity.RESULT_CANCELED:
                    message = "You have abort";
                    break;
                default:
                    message = "Capture image faild";
                    break;
            }
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        } else if (requestCode == VIDEO_REQ) {
            String message;
            switch (resultCode) {
                case Activity.RESULT_OK:
                    message = "Video has benn store in " + fileUri.getPath();
                    launchUpload(false);
                    break;
                case Activity.RESULT_CANCELED:
                    message = "You have abort";
                    break;
                default:
                    message = "Capture video faild";
                    break;
            }
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

    }

    private void launchUpload(boolean isImage) {
        Intent intent = new Intent(CaptureImageActivity.this, UploadActivity.class);
        intent.putExtra("path", fileUri.getPath());
        intent.putExtra("image", isImage);
        startActivity(intent);
    }

    private boolean isSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        return false;
    }

    private Uri getMediaOutputUri(int type) {

        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        File mediaStoreageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FileUpload");
        if (!mediaStoreageDir.exists()) {
            if (!mediaStoreageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStoreageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStoreageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
