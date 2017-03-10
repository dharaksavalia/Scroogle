package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInput;
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
    public static HashMap<Long,Integer>longData7;
    public static boolean longDataLoaded7=false;
    public static HashMap<Long,Integer>longData8;
    public static boolean longDataLoaded8=false;
    public static boolean longDataLoaded9=false;
    public static boolean longDataLoaded13=false;
    public static HashMap<String,String>GameRequest=new HashMap<>();
    public static HashMap<String,String>GameReply=new HashMap<>();
    public static HashMap<Long,Integer>longData10;
    public static boolean longDataLoaded10=false;
    public static HashMap<Long,Integer>longData11;
    public static boolean longDataLoaded11=false;
    public static HashMap<Long,Integer>longData12;
    public static boolean longDataLoaded12=false;
    public static HashMap<Long,Integer>longData;
    public static boolean longDataLoaded=false;

    public static HashMap<String,Integer> words13long;
    public static boolean words13longLoaded=false;
    public static  ArrayList<String> words9long;
    public static boolean words9longLoaded=false;

    DataInputStream dataInt;
     DataInputStream dataLong;
    DataInputStream dataLong7;
    DataInputStream dataLong8;
    DataInputStream dataLong10;
    DataInputStream dataLong11;
    DataInputStream dataLong12;
     DataInputStream dataLong2;
    BufferedReader br;
    BufferedReader word;
    long tsLong;
    public static DictionaryLoader getInstance(DataInputStream dataInt,
                                               DataInputStream dataLong7,
                                               DataInputStream dataLong8, DataInputStream dataLong10, DataInputStream dataLong11, DataInputStream dataLong12, BufferedReader br, BufferedReader word13) {
        if (ourInstance==null){ourInstance=new DictionaryLoader(dataInt,
                dataLong7,
                dataLong8,dataLong10,dataLong11,dataLong12,br,word13);

                return ourInstance;
        }

        return ourInstance;
    }
    public static DictionaryLoader getInstance() {

        return ourInstance;
    }

    private DictionaryLoader(DataInputStream dataInt, DataInputStream dataLong7, DataInputStream dataLong8, DataInputStream dataLong10,
                             DataInputStream dataLong11,DataInputStream dataLong12, BufferedReader br, BufferedReader word) {
        this.dataInt=dataInt;
        this.dataLong7=dataLong7;
        this.dataLong8=dataLong8;
        this.dataLong10=dataLong10;
        this.dataLong11=dataLong11;
        this.dataLong12=dataLong12;
        this.br=br;
        this.word=word;
        intData=new HashMap<Integer, Integer>(60713);
        longData7=new HashMap<Long, Integer>(47523);
        longData8=new HashMap<Long, Integer>(58447);
        longData10=new HashMap<Long, Integer>(55061);
        longData11=new HashMap<Long, Integer>(45675);
        longData12=new HashMap<Long, Integer>(35470);
        words9long=new ArrayList<>(60121);
        words13long=new HashMap<>(69325);
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
        //tsLong=System.currentTimeMillis()/1000;
        //ts=tsLong.toString();
        //Log.d(ts,"2 nd time");
        //tsLong=System.currentTimeMillis()/1000;
        //ts=tsLong.toString();
        //Log.d(ts,"3 lacks files");
         tsLong = System.currentTimeMillis()/1000;
         ts = tsLong.toString();
        Log.d(ts,"2st time");
        run7();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"3st time");
        run8();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"4st time");
        run10();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"5st time");
        run11();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"6st time");
        run12();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"7st time");
        run13();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"8st time");

        //tsLong=System.currentTimeMillis()/1000;
        //ts=tsLong.toString();
        //Log.d(ts,"60 thousand");

        //Log.d("loading Succesfull","Loading.....ended");
        try {
            dataInt.close();
            dataLong7.close();
            dataLong8.close();
            dataLong10.close();
            dataLong11.close();
            dataLong12.close();
            br.close();
            word.close();
            Log.d("everything Loaded","YIPEE");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public synchronized void run7(){
        if(longDataLoaded7){
            return;
        }
        try {
            for (int i=0;i<47523;i++){

                long c=dataLong7.readLong();
                longData7.put(c,1);
            }
        }catch (IOException e) {
            Log.d("dictionary","");
        }
        longDataLoaded7=true;
    }
    public synchronized void run8(){
        if(longDataLoaded8){
            return;
        }
        try {
            for (int i=0;i<58447;i++){

                long c=dataLong8.readLong();
                longData8.put(c,1);
            }
        }catch (IOException e) {
            Log.d("dictionary","");
        }
        longDataLoaded8=true;
    }
    public  void run9(){
        if(words9longLoaded){
            return;
        }
        for(int i=0;i<60121;i++){
            try {

                words9long.add(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        words9longLoaded=true;
    }
    public synchronized void run10(){
        if(longDataLoaded10){
            return;
        }
        try {
            for (int i=0;i<55061;i++){

                long c=dataLong10.readLong();
                longData10.put(c,1);
            }
        }catch (IOException e) {
            Log.d("dictionary10","");
        }
        longDataLoaded10=true;
    }
    public synchronized void run11(){
        if(longDataLoaded11){
            return;
        }
        try {
            for (int i=0;i<45675;i++){

                long c=dataLong11.readLong();
                longData11.put(c,1);
            }
        }catch (IOException e) {
            Log.d("dictionary10","");
        }
        longDataLoaded11=true;
    }
    public synchronized void run12(){
        if(longDataLoaded12){
            return;
        }
        try {
            for (int i=0;i<35470;i++){

                long c=dataLong12.readLong();
                longData12.put(c,1);
            }
        }catch (IOException e) {
            Log.d("dictionary10","");
        }
        longDataLoaded12=true;
    }
    public synchronized void run13(){
        if(words13longLoaded){
            return;
        }
        for(int i=0;i<60121;i++){
            try {

                words13long.put(word.readLine(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        words13longLoaded=true;
    }
}
