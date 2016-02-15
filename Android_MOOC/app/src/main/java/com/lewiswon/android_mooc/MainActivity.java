package com.lewiswon.android_mooc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lewiswon.android_mooc.cptureimage.CaptureImageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open(View v) {
        switch (v.getId()) {

            case R.id.btn_image_capture:
                startActivity(new Intent(getApplicationContext(), CaptureImageActivity.class));

                break;
        }
    }
}
