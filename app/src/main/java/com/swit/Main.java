package com.swit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    public Additives store = new Additives();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Kate's Sections --------------*/
        loadContent();
    }

    /*
    * Kate's Sections --------------
    */

    public String loadContent() {
        try {
            InputStream file = getAssets().open("SuperCoolMessage.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String line;
            List<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            this.store.LoadAdditives(list);
        } catch (IOException e) {
            Log.e("Error", "Unable to load contents!");
        }
        return null;
    }

    public String GetAdditive(String searchTerm){

        if(store.list.containsKey(searchTerm)){
            Additive additive = store.list.get(searchTerm);
            String result = additive.code + "\n" +
                    additive.name + "\n" +
                    additive.desc + "\n" +
                    additive.warn + "\n";
            return result;
        }

        return "No Result Found -  You Lose!";
    }

    public void userSearch(View view) {
        TextView label = (TextView) findViewById(R.id.lbl_world);
        EditText userInput = (EditText) findViewById(R.id.editText);
        String input = userInput.getText().toString();
        label.setText(GetAdditive(input));
    }
}
