package me.imid.swipebacklayout.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


import java.util.Random;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class SecondActivity extends SwipeBackActivity {
    private int[] colors=new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.GRAY,Color.DKGRAY};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Random  random=new Random();
        findViewById(Window.ID_ANDROID_CONTENT).setBackgroundColor(colors[Math.abs(random.nextInt(5))]);
        findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SecondActivity.this,SecondActivity.class));
            }
        });
    }
}