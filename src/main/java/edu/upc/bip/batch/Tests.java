package edu.upc.bip.batch;

import java.io.IOException;

/**
 * Created by osboxes on 08/01/17.
 */
public class Tests {

    public static void main(String[] args){
        try {
            //String val = HBaseUtils.getOneRecordValue("dataminingupc", "r2017-01-01T06:00e2017-01-03T21:05:05");
            //HBaseUtils.addRecord("dataminingupc", "r2017-01-01T06:00:00e2017-01-03T21:05:05","data", "", val);
            String val = HBaseUtils.getOneRecordValue("dataminingupc", "s2017-01-01T07:55:00e2017-01-03T16:05:10");
            val.replace("148734", "14873");
            System.out.println(val);
            HBaseUtils.addRecord("dataminingupc", "s2017-01-01T06:00:00e2017-01-03T21:05:05","data", "", val);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
