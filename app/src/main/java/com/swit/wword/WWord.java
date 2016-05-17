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
        int subWidth = (int)(Math.ceil(image.getWidth() / 30.0) * 30.0);

        image = Bitmap.createScaledBitmap(image, subWidth, image.getHeight(), true);
        return createFromImage(word, image);
    }

    public static WWord createFromImage(String word, Bitmap image) {
        WWord result = new WWord();
        result.word = word;

        for (int x = 0; x < image.getWidth(); x++) {
            int []columnValues = new int[image.getHeight()];

            int yi = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                int argb = image.getPixel(x, y);
                int value = (0xFF - (0xFF & argb)) / 16;
                columnValues[yi++] = value;
            }
            result.values.add(columnValues);
        }

        return result;
    }

    /*
    public static WWord createFromImage(String word, BufferedImage image) {
        return createFromImage(word, image, null);
    }

    public static WWord createTestString(String word) {
        BufferedImage scaled = new BufferedImage(100, 7, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, scaled.getWidth(), scaled.getHeight());
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 7));
        g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.drawString(word, -1, 6);

        int wordWidth = g.getFontMetrics().stringWidth(word);

        return createFromImage(word, scaled, new Rectangle(0, 0, wordWidth, 7));
    }

    public static WWord createFromImage(String word, BufferedImage image, java.awt.Rectangle rectangle) {
        WWord result = new WWord();
        result.word = word;

        int width = rectangle != null ? (int)rectangle.getWidth() : image.getWidth();
        int subWidth = (int)(Math.ceil(width / 30.0) * 30.0);
        BufferedImage subimage = image;
        BufferedImage scaled = image;
        if (rectangle != null) {
            subimage = image.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }

        if (subimage.getWidth() != subWidth) {
            scaled = new BufferedImage(subWidth, subimage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            AffineTransform transform = new AffineTransform();
            transform.scale(subWidth / (double) subimage.getWidth(), 1.0);

            AffineTransformOp transformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
            scaled = transformOp.filter(subimage, scaled);

            try {
                File output = new File(word + ".png");
                ImageIO.write(scaled, "png", output);
            }
            catch (IOException exp) {
                System.out.println("Output exception!");
            }
        }

        for (int x = 0; x < scaled.getWidth(); x++) {
            int []columnValues = new int[scaled.getHeight()];

            int yi = 0;
            for (int y = 0; y < scaled.getHeight(); y++) {
                int argb = scaled.getRGB(x, y);
                int value = (0xFF - (0xFF & argb)) / 16;
                columnValues[yi++] = value;
            }
            result.values.add(columnValues);
        }

        return result;
    }
    */

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
