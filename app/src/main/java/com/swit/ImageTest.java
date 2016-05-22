package com.swit;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.swit.wword.ImageProcessor;
import com.swit.wword.WTree;
import com.swit.wword.WWord;
import com.swit.wword.WWordContext;

public class ImageTest extends AppCompatActivity {

    private Typeface testFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test2);

        testFont = Typeface.createFromAsset(getAssets(), "LiberationSans-Regular.ttf");

        WWord flourWord = WWord.createTestString("Flour", testFont);
        WWord flakeWord = WWord.createTestString("Flakes", testFont);
        WWord cranberriesWord = WWord.createTestString("Cranberries", testFont);

        WTree tree = new WTree();
        tree.addWord(flourWord);
        tree.addWord(flakeWord);
        tree.addWord(cranberriesWord);

        ImageProcessor.LoadResult testFlour = ImageProcessor.loadFromWord(getAssets(), "flour_small.jpg");
        searchFor(tree, testFlour.word, "Flour");

        ImageProcessor.LoadResult testCranberries = ImageProcessor.loadFromWord(getAssets(), "cranberries_small.jpg");
        searchFor(tree, testCranberries.word, "Cranberries");

        ImageProcessor.LoadResult testCranberries2 = ImageProcessor.loadFromWord(getAssets(), "test2.jpg", new Rect(90, 118, 90 + 170, 118 +  30));
        searchFor(tree, testCranberries2.word, "Cranberries 2");

        ImageProcessor.LoadResult testFlakes = ImageProcessor.loadFromWord(getAssets(), "flakes_small.jpg");
        searchFor(tree, testFlakes.word, "Flakes");

        ImageView imageTest = (ImageView)findViewById(R.id.img_test);
        Bitmap testImage = ImageProcessor.loadAsset(getAssets(), "testOrig.jpg");
        Bitmap cleanedImage = ImageProcessor.cleanupImage(testImage);
        imageTest.setImageBitmap(cleanedImage);
    }

    public static void searchFor(WTree tree, WWord word, String expectedWord) {
        Log.i("Search", "\nSearching for " + expectedWord);
        WWordContext searchContext = new WWordContext(word);
        tree.match(searchContext);

        for (WWordContext.ScoredWordMatch match : searchContext.matches) {
            Log.i("Search", "Match: " + match.score + " " + match.word.word);
        }
    }

}
