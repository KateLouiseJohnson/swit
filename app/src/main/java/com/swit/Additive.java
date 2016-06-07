package com.swit;

/**
 * Created by Miss Johnson on 19/05/2016.
 */
public class Additive {

    public String code;
    public String eCode;
    public String name;
    public String desc;
    public String warn;

    public Additive(String eCode, String name, String desc, String warn){

        this.eCode = eCode;
        this.code = eCode.replace("E","");
        this.name = name;
        this.desc = desc;
        this.warn = warn;

    }
}
