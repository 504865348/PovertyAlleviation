package com.example.jayny.povertyalleviation;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jayny on 2016/12/7.
 */

public class BitmapCoast {

    public static Bitmap bitmap;

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        BitmapCoast.bitmap = bitmap;
    }
}
