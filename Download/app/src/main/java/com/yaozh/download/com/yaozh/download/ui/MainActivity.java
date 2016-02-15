package com.yaozh.download.com.yaozh.download.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.yaozh.download.R;

public class MainActivity extends AppCompatActivity{
    private ScrollView scrollView;
    private Toolbar toolbar;
    private TextView tvToolbar,tv;
    private boolean toogled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AVObject demoobject=new AVObject("demoObject");
        demoobject.put("Hello","world");
        AVAnalytics.trackAppOpened(getIntent());
        demoobject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e!=null) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        setContentView(R.layout.activity_main);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tvToolbar=(TextView)findViewById(R.id.tv_toolbar);
        tv= (TextView) findViewById(R.id.tv_01);
        Resources  res=getResources();
        int padding= (int) res.getDimension(R.dimen.activity_horizontal_margin);
        Drawable  drawable=res.getDrawable(R.mipmap.me_help_01);
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        tvToolbar.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        tv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        tvToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent=new Intent(MainActivity.this,ImageDemoActivty.class);
                ActivityOptions  options=ActivityOptions.makeScaleUpAnimation(view,(int)view.getX(), (int) view.getY(),20,20);
                startActivity(intent,options.toBundle());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        if (id == R.id.download) {
            startActivity(new Intent(this, DownloadActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggleToolbar(View v) {
        if (toogled) {
            toogled = false;
            toolbar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tranlate_up_off));

        } else {
            toogled = true;
            toolbar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tranlate_down_in));
        }
    }
}
