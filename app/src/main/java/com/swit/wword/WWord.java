package com.swit.wword;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WWord {
    public ArrayList<int[]> values = new ArrayList<>();
    public String word;
    public static final int TEST_HEIGHT = 7;

    public static WWord createTestString(String word, Typeface typeface) {
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);

        TextPaint paint = new TextPaint();
        paint.setTextSize(7.0f);
        paint.setAntiAlias(true);
        paint.setTypeface(typeface);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        float baseline = -paint.ascent(); // ascent() is negative

        int width = (int) (paint.measureText(word) + 0.5f); // round
        //int height = (int) (baseline + paint.descent() + 0.5f);
        int height = 7;
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawRect(0, 0, image.getWidth(), image.getHeight(), whitePaint);

        canvas.drawText(word, 0, 6, paint);
        canvas.save();

        return createFromScaledImage(word, image);
    }

    public static WWord createFromScaledImage(String word, Bitmap image) {
        int imageWidth = image.getWidth();
        if (image.getHeight() != TEST_HEIGHT) {
            float dh = (float)TEST_HEIGHT / (float)image.getHeight();
            imageWidth = (int)(image.getWidth() * dh);
        }
        int subWidth = (int)(Math.ceil(imageWidth / 30.0) * 30.0);

        image = Bitmap.createScaledBitmap(image, subWidth, TEST_HEIGHT, true);
        return createFromImage(word, image);
    }

    public static WWord createFromImage(String word, Bitmap image) {
        WWord result = new WWord();
        result.word = word;

        for (int x = 1; x < image.getWidth(); x++) {
            int []columnValues = new int[image.getHeight()];

            int yi = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                int argb = image.getPixel(x, y);
                int prevArgb = image.getPixel(x - 1, y);
                int value = (0xFF - (0xFF & argb)) / 16;
                int prevValue = (0xFF - (0xFF & prevArgb)) / 16;
                columnValues[yi++] = Math.abs(value - prevValue);
            }
            result.values.add(columnValues);
        }

        return result;
    }

    public String debugDisplay() {
        String result = "Word: " + word + "\n";
        int height = values.get(0).length;
        for (int x = 0; x < values.size(); x++) {
            result += " V ";
        }
        result += "\n";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < values.size(); x++) {
                int value = values.get(x)[y];
                String v = value < 10 ? " " + value : Integer.toString(value);
                result += v + " ";
                //result += String.format("%08X", value) + " ";
            }
            result += "\n";
        }

        return result;
    }
}
