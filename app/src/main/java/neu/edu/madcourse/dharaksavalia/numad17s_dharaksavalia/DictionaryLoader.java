package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Dharak on 2/6/2017.
 */
public class DictionaryLoader implements Runnable {
    private static DictionaryLoader ourInstance;
    public static HashMap<Integer,Integer> intData;
    public static boolean intDataLoaded=false;
    public static HashMap<Long,Integer>longData;
    public static boolean longDataLoaded=false;
    public static ArrayList<Long> long1Data;
    public static boolean long1DataLoaded=false;
    public static ArrayList<Long> long2Data;
    public static HashMap<String,Integer> words24long;
    public static boolean words24longLoaded=false;
     DataInputStream dataInt;
     DataInputStream dataLong;
     DataInputStream dataLong2;
    BufferedReader br;
    public static DictionaryLoader getInstance(DataInputStream dataInt,
                                               DataInputStream dataLong,
                                               DataInputStream dataLong2,BufferedReader br) {
        if (ourInstance==null){ourInstance=new DictionaryLoader(dataInt,
                dataLong,
                dataLong2,br);

                return ourInstance;
        }

        return ourInstance;
    }

    private DictionaryLoader(DataInputStream dataInt,
            DataInputStream dataLong,
            DataInputStream dataLong2,BufferedReader br) {
        this.dataInt=dataInt;
        this.dataLong2=dataLong2;
        this.dataLong=dataLong;
        this.br=br;
        intData=new HashMap<Integer, Integer>(60713);
        longData=new HashMap<Long, Integer>(302298);
        long1Data=new ArrayList<>(69268);
        long2Data=new ArrayList<>(69268);
        words24long=new HashMap<>(58);
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Log.d(ts,"1st time");
        for(int i=0;i<60712;i++) {

            try {
                int a=dataInt.readInt();
                intData.put(a,a);
            } catch (IOException e) {
                Log.d("dictionary", "file int dictionary unable to load");
            }

        }
        intDataLoaded=true;
        tsLong=System.currentTimeMillis()/1000;
        ts=tsLong.toString();
        Log.d(ts,"2 nd time");

        try {
            for (int i=0;i<302297;i++){

                long c=dataLong.readLong();
                longData.put(c,1);
            }
        }catch (IOException e) {
            Log.d("dictionary","");
        }
        longDataLoaded=true;
        tsLong=System.currentTimeMillis()/1000;
        ts=tsLong.toString();
        Log.d(ts,"3 lacks files");
        for (int i=0;i<69267;i++){
            try {
                long1Data.add(dataLong2.readLong());
                long1Data.add(dataLong2.readLong());

            } catch (IOException e) {
                Log.d("dictionary","file long2 dictionary unable to load");
            }
        }
        long1DataLoaded=true;
        tsLong=System.currentTimeMillis()/1000;
        ts=tsLong.toString();
        Log.d(ts,"60 thousand");
        for(int i=0;i<58;i++){
            try {

                words24long.put(br.readLine(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        words24longLoaded=true;
        Log.d("loading Succesfull","Loading.....ended");
        try {
            dataInt.close();
            dataLong.close();
            dataLong2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
