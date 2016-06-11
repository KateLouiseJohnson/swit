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

        loadContent();

        mMenuItems = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.sliding_menu);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, mMenuItems));  //use custom textview menu_list
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

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

        for(int i = 0; i < store.list.size(); i++) {

            if (store.list.get(i).eCode.equalsIgnoreCase(searchTerm)) {
                Additive additive = store.list.get(i);
                String result = additive.eCode + "\n" +
                        additive.name + "\n" +
                        additive.desc + "\n" +
                        additive.warn + "\n";
                return result;
            }else if(store.list.get(i).code.equalsIgnoreCase(searchTerm)){
                Additive additive = store.list.get(i);
                String result = additive.eCode + "\n" +
                        additive.name + "\n" +
                        additive.desc + "\n" +
                        additive.warn + "\n";
                return result;
            }else if(store.list.get(i).name.toLowerCase().contains(searchTerm.toLowerCase())){
                Additive additive = store.list.get(i);
                String result = additive.eCode + "\n" +
                        additive.name + "\n" +
                        additive.desc + "\n" +
                        additive.warn + "\n";
                return result;
            }
        }

        return "No Result Found -  You Lose!";
    }

    public void userSearch(View view) {
        TextView label = (TextView) findViewById(R.id.lbl_world);
        EditText userInput = (EditText) findViewById(R.id.editText);
        String input = userInput.getText().toString();
        label.setText(GetAdditive(input));
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //set new intent
        }
    }
}
