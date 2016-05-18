package com.swit;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.swit.wword.ImageProcessor;
import com.swit.wword.WTree;
import com.swit.wword.WWord;
import com.swit.wword.WWordContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends AppCompatActivity {

    private Typeface testFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testFont = Typeface.createFromAsset(getAssets(), "LiberationSans-Regular.ttf");

        WWord flourWord = WWord.createTestString("Flour", testFont);
        WWord flakeWord = WWord.createTestString("Flakes", testFont);
        WWord cranberriesWord = WWord.createTestString("Cranberries", testFont);
        WTree tree = new WTree();
        tree.addWord(flourWord);
        tree.addWord(flakeWord);
        tree.addWord(cranberriesWord);

        WWord testFlour = ImageProcessor.loadFromWord(getAssets(), "flour_small.jpg");
        searchFor(tree, testFlour, "Flour");

        WWord testCranberries = ImageProcessor.loadFromWord(getAssets(), "cranberries_small.jpg");
        searchFor(tree, testCranberries, "Cranberries");

        WWord testCranberries2 = ImageProcessor.loadFromWord(getAssets(), "test2.jpg", new Rect(90, 118, 90 + 170, 118 +  30));
        searchFor(tree, testCranberries2, "Cranberries 2");

        WWord testFlakes = ImageProcessor.loadFromWord(getAssets(), "flakes_small.jpg");
        searchFor(tree, testFlakes, "Flakes");
    }

    public static void searchFor(WTree tree, WWord word, String expectedWord) {
        Log.i("Search", "\nSearching for " + expectedWord);
        WWordContext searchContext = new WWordContext(word);
        tree.match(searchContext);

        for (WWordContext.ScoredWordMatch match : searchContext.matches) {
            Log.i("Search", "Match: " + match.score + " " + match.word.word);
        }
    }

    public String loadContents() {
        try {
            InputStream file = getAssets().open("SuperCoolMessage.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(file));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            return total.toString();
        } catch (IOException e) {
            Log.e("Error", "Unable to load contents!");
        }
        return null;
    }

    public void makeItThing(View view) {
        TextView label = (TextView) findViewById(R.id.lbl_world);
        label.setText(loadContents());
    }
}
