package com.swit;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main extends Activity {

    Button camButton;
    ImageView imgView;
    static final int CAM_REQUEST = 1;

    private Additives store = new Additives();

    private String[] mMenuItems;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        store.loadContent(this);

        camButton = (Button) findViewById(R.id.btn_camera_capture);
        imgView = (ImageView) findViewById(R.id.image_view);
        camButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getImage();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent,CAM_REQUEST);

            }
        });

        mMenuItems = getResources().getStringArray(R.array.nav_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.sliding_menu);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, mMenuItems));  //use custom textview menu_list
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private File getImage(){

        File folder = new File("sdcard/SWIT");

        if(!folder.exists()){
            folder.mkdir();
        }

        File image_file = new File(folder, "swit.jpg");

        return image_file;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        String path = "sdcard/SWIT/swit.jpg";
        imgView.setImageDrawable(Drawable.createFromPath(path));
    }

}
