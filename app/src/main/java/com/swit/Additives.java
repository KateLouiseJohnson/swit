package com.swit;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Miss Johnson on 19/05/2016.
 */
public class Additives {

    public HashMap<String, Additive> list = new HashMap<String,Additive>();

    public Additives(){

    }

    public void LoadAdditives(List<String> lines){

        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split("\\t");
            Additive additive = new Additive(split[0],split[1],split[2],split[3]);
            this.list.put(split[0],additive);
        }
    }
}
