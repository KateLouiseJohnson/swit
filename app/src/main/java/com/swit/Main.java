package com.swit;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            String[] files = getAssets().list("");
            InputStream file = getAssets().open("SuperCoolMessage.txt");
            BufferedReader r = new BufferedReader(new InputStreamReader(file));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            Log.d("Info",total.toString());

        }
        catch(IOException e){
            Log.e("error",e.getMessage());
        }
    }
}
