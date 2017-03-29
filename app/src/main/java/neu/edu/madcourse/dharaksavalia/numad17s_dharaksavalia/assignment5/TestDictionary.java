package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;

import static java.lang.Thread.sleep;

/**
 * Created by Dharak on 2/16/2017.
 */

public class TestDictionary {
    String wordInput;
    ArrayList<String> detectedWord;
    HashMap<Character,Integer> encoder=new HashMap<Character,Integer>();
    public void Encoder(){
        String aphabet="abcdefghijklmnopqrstuvwxyz";
        for(int i=0;i<aphabet.length();i++){
            encoder.put(aphabet.charAt(i),i+1);
        }}
    public TestDictionary(){
        detectedWord=new ArrayList<String>(30);
        Encoder();
    }
    public boolean verifyInput(String verifyInput){
        int len=verifyInput.length();
        // Log.d(verifyInput,verifyInput);
        if(detectedWord.contains(verifyInput)){
            return true;
        }
        if(len<3)return false;
        wordInput=verifyInput;
//Log.d("verifyInput",verifyInput);
        if (len>=3&&len<7){
            int result=0;

            for (int i=0;i<wordInput.length();i++){
                //result1=result1*32;S
                //System.out.println(result1);
                if(encoder.containsKey(verifyInput.charAt(i))==false)break;
                //	System.out.println("result after multiplication= by 32="+result*32+"CharAt(i)="+Word.charAt(i)+encoder.get(Word.charAt(i)));
                result=result*32+encoder.get(wordInput.charAt(i));

                //	System.out.println("result= after adding char="+result);
            }
            while(DictionaryLoader.intDataLoaded==false){
                try {

                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean br= DictionaryLoader.intData.containsKey(result);
            if(br){
                appendText(verifyInput);

                makebeep();
                //detectedWord.add(verifyInput);
                return true;
            }
        }else if(7<=len&&len<13){
            long result=0;
            //long result1=1;
            for (int i=0;i<verifyInput.length();i++){
                //result1=result1*32;S
                //System.out.println(result1);
                //System.out.println("result after multiplication= by 32="+result*32+"CharAt(i)="+Word.charAt(i)+encoder.get(Word.charAt(i)));
                if(encoder.containsKey(wordInput.charAt(i))==false)break;
                result=result*32+encoder.get(verifyInput.charAt(i));

                //System.out.println("result= after adding char="+result);
            }

            boolean br=find7to13(len,verifyInput,result);
            if(br){
                appendText(verifyInput);
               makebeep();
                detectedWord.add(verifyInput);
                return true;
            }
        }else if(13<=len){
            // Log.d("Inside the 13 to 24","dharak");
            while(DictionaryLoader.words13longLoaded==false){
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            if(DictionaryLoader.words13long.containsKey(verifyInput)){
                appendText(verifyInput);
                makebeep();
                detectedWord.add(verifyInput);
                return true;
            }
        }
        return false;
    }

    private boolean find7to13(int len, String verifyInput,long result) {
        switch(len){
            case 7:
                if(DictionaryLoader.longDataLoaded7==false){
                    while(DictionaryLoader.longDataLoaded7==false){
                        try {

                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(DictionaryLoader.longData7.containsKey(result))return true;
                else return false;
            case 8:
                if(DictionaryLoader.longDataLoaded8==false){
                    DictionaryLoader dr=DictionaryLoader.getInstance();
                    while(DictionaryLoader.longDataLoaded8==false){
                        try {

                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(DictionaryLoader.longData8.containsKey(result))return true;
                else return false;
            case 9:
                if(DictionaryLoader.words9longLoaded==false) {
                    DictionaryLoader dr = DictionaryLoader.getInstance();
                    while(DictionaryLoader.words9longLoaded==false){
                        try {

                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(DictionaryLoader.words9long.contains(verifyInput))return true;
                else return false;
            case 10:
                if(DictionaryLoader.longDataLoaded10==false){
                    while(DictionaryLoader.longDataLoaded10==false){
                        try {

                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(DictionaryLoader.longData10.containsKey(result))return true;
                else return false;
            case 11:
                if(DictionaryLoader.longDataLoaded11==false){
                    while(DictionaryLoader.longDataLoaded11==false){
                        try {

                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(DictionaryLoader.longData11.containsKey(result))return true;
                else return false;
            case 12:
                if(DictionaryLoader.longDataLoaded12==false){
                    while(DictionaryLoader.longDataLoaded12==false){
                        try {

                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(DictionaryLoader.longData12.containsKey(result))return true;
                else return false;

        }
        return false;
    }
    public void appendText(String Word){
        Word=Word+'\n';
    }
    public void clearText(){
        detectedWord=new ArrayList<>();
    }
    public void makebeep(){
        ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
       // Log.d("input_verified","YIPEE");
    }
}
