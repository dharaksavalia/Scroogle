package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

import static java.lang.Thread.sleep;

/**
 * Created by Dharak on 2/2/2017.
 */


public class TestDictionary extends Activity {
    TextView outputText;
    String wordInput;
    EditText inputText;
    ArrayList<String> detectedWord;
    HashMap<Character,Integer> encoder=new HashMap<Character,Integer>();
    public void Encoder(){
        String aphabet="abcdefghijklmnopqrstuvwxyz";
        for(int i=0;i<aphabet.length();i++){
            encoder.put(aphabet.charAt(i),i+1);
        }}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detectedWord=new ArrayList<String>(30);
        setContentView(R.layout.dictionary);
        this.setTitle("Test Dictionary");
        outputText = (TextView) findViewById(R.id.dictiionaryOutput);
        inputText = (EditText) findViewById(R.id.dictionaryInput);
        Button clearButton=(Button)findViewById(R.id.dictionaryClear);
        Button dictionaryAcknowlegements=(Button)findViewById(R.id.dictionaryAcknowlegements);
        Button Return=(Button)findViewById(R.id.dictionaryReturn);
        outputText.setMovementMethod(new ScrollingMovementMethod());
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dictionaryAcknowlegements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenet1=new Intent(TestDictionary.this,DictionaryAcknowledge.class);
                startActivity(intenet1);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });
        Encoder();
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                wordInput=inputText.getText().toString();
                //appendText(wordInput);
                verifyInput(wordInput);
            }
        });
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Log.d(ts,"time taken to run13");

    }
    public void verifyInput(String verifyInput){
        int len=verifyInput.length();
       // Log.d(verifyInput,verifyInput);
        if(detectedWord.contains(verifyInput)){
            return;
        }

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
                detectedWord.add(verifyInput);
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
            }
        }
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
        outputText.append(Word);
    }
    public void clearText(){
    outputText.setText("");detectedWord=new ArrayList<>();
        inputText.setText("");
    }
public void makebeep(){
    Log.d("Word Detected","YIPEE");
    ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
}
}
