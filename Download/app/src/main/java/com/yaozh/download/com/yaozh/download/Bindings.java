package com.yaozh.download.com.yaozh.download;

//import android.databinding.BindingAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 10/23/2015.
 */
public class Bindings {

//    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView,String fontName){
        textView.setTypeface(FontCache.getInstance().get(fontName));
    }
}
