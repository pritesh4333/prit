package com.acumengroup.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Arcadia
 */
public class UIOperations {

    /**
     * Rotate Image
     *
     * @param context
     * @param imgRscId
     * @param degrees
     * @return
     */
    public static Drawable rotateImage(Context context, int imgRscId, float degrees) {

        Bitmap sprite = BitmapFactory.decodeResource(context.getResources(), imgRscId);

        Matrix rotate = new Matrix();

        rotate.preRotate(degrees);

        Bitmap rSprite = Bitmap.createBitmap(sprite, 0, 0, sprite.getWidth(), sprite.getHeight(), rotate, true);

        return new BitmapDrawable(rSprite);
    }

}
