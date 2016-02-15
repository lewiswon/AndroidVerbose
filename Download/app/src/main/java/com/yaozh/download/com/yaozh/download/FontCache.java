package com.yaozh.download.com.yaozh.download;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 10/23/2015.
 */
public class FontCache {
    public static final String FONT_DIR = "fonts";
    private static Map<String, Typeface> cache = new HashMap<>();
    private static Map<String, String> fontMapping = new HashMap<>();
    private static FontCache instance;

    public static FontCache getInstance() {
        if (instance == null) {
            instance = new FontCache();
        }

        return instance;
    }

    private FontCache() {
        AssetManager am = App.getContext().getResources().getAssets();
        String fileList[];
        try {
            fileList = am.list(FONT_DIR);
        } catch (Exception e) {
            Log.i("No such fontName", "No such font");
            return;
        }
        for (String fileName : fileList) {
            String alias = fileName.substring(0, fileName.lastIndexOf('.'));
            fontMapping.put(alias, fileName);
            fontMapping.put(alias.toLowerCase(), fileName);
        }
    }

    public Typeface get(String fontName) {

        String fontFileName = fontMapping.get(fontName);
        if (fontFileName == null) {
            return null;
        }
        if (cache.containsKey(fontFileName)) {
            return cache.get(fontFileName);
        } else {
            Typeface typeface = Typeface.createFromAsset(App.getContext().getAssets(), FONT_DIR + "/" + fontFileName);
            cache.put(fontFileName, typeface);
            return typeface;
        }


    }
}

