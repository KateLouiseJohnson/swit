package com.swit;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity {

    private Additives store = new Additives();

    private String[] mMenuItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store.loadContent(this);

        mMenuItems = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.sliding_menu);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, mMenuItems));  //use custom textview menu_list
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void userSearch(View view) {

        ArrayList<String> input = getInput();
        TextView label = (TextView) findViewById(R.id.result);
        label.setText(store.getAdditive(input));
    }

    public ArrayList<String> getInput(){

        ArrayList<String> list = new ArrayList<>();

        EditText eText = (EditText) findViewById(R.id.editText);
        String value = eText.getText().toString();
        String[] splitString = value.split(",");

        for(int i = 0; i < splitString.length; i++){
            list.add(splitString[i]);
        }

        return list;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //set new intent
        }
    }
}
