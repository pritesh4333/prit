package com.acumengroup.ui;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Arcadia
 */
public class GreekCustomFont {

    private static final Hashtable<String, Typeface> typefaceCache = new Hashtable<>();

    public static Typeface getCustomTypeface(Context c, String assetPath) {
        synchronized (typefaceCache) {
            if (!typefaceCache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), assetPath);
                    typefaceCache.put(assetPath, t);
                } catch (Exception e) {
                    //e.printStackTrace();
                    /*Log.e("", "Could not get typeface '" + assetPath
							+ "' because " + e.getMessage());*/
                    return null;
                }
            }
            return typefaceCache.get(assetPath);
        }
    }

}
