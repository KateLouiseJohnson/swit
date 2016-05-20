package com.swit.wword;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alan on 18/05/16.
 */
public class ImageProcessor {

    public static class LoadResult {
        public boolean success;
        public WWord word;
        public Bitmap bitmap;

        private LoadResult(boolean success) {
            this.success = success;
        }
        public static LoadResult Error = new LoadResult(false);

        public LoadResult(WWord word, Bitmap bitmap) {
            this.success = true;
            this.word = word;
            this.bitmap = bitmap;
        }
    }

    public static LoadResult loadFromWord(AssetManager assetManager, String filename) {
        return loadFromWord(assetManager, filename, null);
    }
    public static LoadResult loadFromWord(AssetManager assetManager, String filename, Rect rect) {
        Bitmap bitmap = loadAsset(assetManager, filename);
        if (bitmap == null) {
            return LoadResult.Error;
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

        return new LoadResult(WWord.createFromScaledImage("", bitmap), bitmap);
    }

    public static Bitmap loadAsset(AssetManager assetManager, String filename) {
        try {
            InputStream istr = assetManager.open(filename);
            return BitmapFactory.decodeStream(istr);
        }
        catch (IOException e) {
            Log.e("Error", "Unable to load asset: " + filename);
            return null;
        }
    }

    public static Bitmap cleanupImage(Bitmap input) {
        ColorMatrix matrix = new ColorMatrix();
        setContrast(matrix, 5f);
        //matrix.setSaturation(0);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        Bitmap result = Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(input, 0, 0, paint);

        return result;
    }
    private static void setContrast(ColorMatrix cm, float contrast) {
        float scale = contrast + 1.f;
        scale *= -1;
        float translate = (-.5f * scale + .5f) * 255.f;

        final float R = 0.213f;
        final float G = 0.715f;
        final float B = 0.072f;

        cm.set(new float[] {
                scale, G, B, 0, translate,
                R, scale, B, 0, translate,
                R, G, scale, 0, translate,
                0, 0, 0, 1, 0 });
    }
}
