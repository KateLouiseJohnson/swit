package com.swit;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Miss Johnson on 19/05/2016.
 */
public class Additives {

    public List<Additive> list = new ArrayList<>();

    public Additives(){

    }

    public String loadContent(Context context) {

        try {

            InputStream file = context.getAssets().open("SuperCoolMessage.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String line;
            List<String> list = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            loadAdditives(list);
        } catch (IOException e) {

            Log.e("Error", "Unable to load contents!");

        }

        return null;
    }

    public void loadAdditives(List<String> lines){

        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split("\\t");
            Additive additive = new Additive(split[0],split[1],split[2],split[3]);
            this.list.add(additive);
        }
    }

    public String getAdditive(List<String> searchTerms){

        Map<String, String> found = new HashMap<>();

        for(int j = 0; j < searchTerms.size(); j++ ) {

            String searchTerm = searchTerms.get(j);

            for (int i = 0; i < list.size(); i++) {

                Additive additive = list.get(i);

                if (additive.eCode.equalsIgnoreCase(searchTerm)) {

                    String key = getKey(additive);
                    String value = getValue(additive);
                    found.put(key,value);

                } else if (additive.code.equalsIgnoreCase(searchTerm)) {

                    String key = getKey(additive);
                    String value = getValue(additive);
                    found.put(key,value);

                } else if (additive.name.toLowerCase().contains(searchTerm.toLowerCase())) {

                    String key = getKey(additive);
                    String value = getValue(additive);
                    found.put(key,value);

                }
            }
        }

        if(found.size() == 0){
            return "No Result Found -  You Lose!";
        }

        String result = "";
        for (Map.Entry<String, String> entry : found.entrySet()) {
            result += entry.getValue();
        }

        return result;
    }

    public String getValue(Additive additive){

        String value = additive.eCode + "\n" +
                additive.name + "\n" +
                additive.desc + "\n" +
                additive.warn + "\n\n";

        return value;
    }

    public String getKey(Additive additive){

        String key = additive.code +
                additive.name;
        return key;
    }
}
