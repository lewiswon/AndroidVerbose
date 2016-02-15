package com.yaozh.download.com.yaozh.download.ui;

//import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.yaozh.download.R;

import java.io.InputStream;

public class ImageDemoActivty extends AppCompatActivity implements Palette.PaletteAsyncListener{
    private ImageView small, large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DataBindingUtil.setContentView(this,R.layout.activity_image_demo_activty);
        setContentView(R.layout.activity_image_demo_activty);
        small = (ImageView) findViewById(R.id.small);
        large = (ImageView) findViewById(R.id.large);
        setImage();
    }

    private void setImage() {
        try {

            InputStream inputStream = getAssets().open("pic.jpg");
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            int width = options.outWidth;
            int height = options.outHeight;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            large.setImageBitmap(bitmap);
            Palette.from(bitmap).generate(this);
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap2 = decoder.decodeRegion(new Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options2);
            small.setImageBitmap(bitmap2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGenerated(Palette palette) {
        if (palette!=null){

            findViewById(R.id.view1).setBackgroundColor(palette.getMutedColor(Color.RED));
            findViewById(R.id.view2).setBackgroundColor(palette.getVibrantColor(Color.BLUE));
            findViewById(R.id.view3).setBackgroundColor(palette.getDarkMutedSwatch().getRgb());
//            findViewById(R.id.view4).setBackgroundColor(palette.getLightVibrantSwatch().getRgb());
            findViewById(R.id.view5).setBackgroundColor(palette.getDarkVibrantSwatch().getRgb());
            findViewById(R.id.view6).setBackgroundColor(palette.getDarkMutedColor(Color.GREEN));
        }
    }
}
