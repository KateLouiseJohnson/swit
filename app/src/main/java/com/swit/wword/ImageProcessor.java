package com.swit.wword;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alan on 18/05/16.
 */
public class ImageProcessor {
    public static WWord loadFromWord(AssetManager assetManager, String filename) {
        return loadFromWord(assetManager, filename, null);
    }
    public static WWord loadFromWord(AssetManager assetManager, String filename, Rect rect) {

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
            Log.e("Error", "Unable to load asset: " + filename);
            return null;
        }

        try {
            if (rect != null) {
                bitmap = bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height());
            }
        }
        catch (Exception exp) {
            Log.e("Error", "Unable to create subimage: " + exp.toString());
            return null;
        }

        return WWord.createFromScaledImage("", bitmap);
    }

}
