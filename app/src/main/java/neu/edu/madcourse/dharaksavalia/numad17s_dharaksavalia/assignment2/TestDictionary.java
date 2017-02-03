package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.MainActivity;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 2/2/2017.
 */

public class TestDictionary extends Activity {
    TextView outputText;
    String wordInput;
    EditText inputText;
    HashMap<Character,Integer> encoder=new HashMap<Character,Integer>();
    public void Encoder(){
        String aphabet="abcdefghijklmnopqrstuvwxyz";
        for(int i=0;i<aphabet.length();i++){
            encoder.put(aphabet.charAt(i),i+1);
        }}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary);
        outputText = (TextView) findViewById(R.id.dictiionaryOutput);
        inputText = (EditText) findViewById(R.id.dictionaryInput);
        Button clearButton=(Button)findViewById(R.id.dictionaryClear);
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

    }
    public void verifyInput(String verifyInput){
        int len=verifyInput.length();
        Log.d(verifyInput,verifyInput);
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

            boolean br=MainActivity.intData.containsKey(result);
            if(br){
                appendText(verifyInput);
                makebeep();
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
            boolean br=MainActivity.longData.containsKey(result);
            if(br){
                appendText(verifyInput);
                makebeep();
            }
        }else if(13<=len&&len<24){
        String string1=verifyInput.substring(0,11);
            String string2=verifyInput.substring(12);
            long result1=0;
            //long result1=1;
            for (int i=0;i<string1.length();i++){
                //result1=result1*32;S
                //System.out.println(result1);
                //System.out.println("result after multiplication= by 32="+result*32+"CharAt(i)="+Word.charAt(i)+encoder.get(Word.charAt(i)));
                if(encoder.containsKey(string1.charAt(i))==false)break;
                result1=result1*32+encoder.get(string1.charAt(i));

                //System.out.println("result= after adding char="+result);
            }
            long result2=0;
            //long result1=1;
            for (int i=0;i<string2.length();i++){
                //result1=result1*32;S
                //System.out.println(result1);
                //System.out.println("result after multiplication= by 32="+result*32+"CharAt(i)="+Word.charAt(i)+encoder.get(Word.charAt(i)));
                if(encoder.containsKey(string2.charAt(i))==false)break;
                result2=result2*32+encoder.get(string2.charAt(i));
                //System.out.println("result= after adding char="+result);
            }
            if(MainActivity.long1Data.contains(result1)&&MainActivity.long2Data.contains(result2)){
                if(MainActivity.long1Data.indexOf(result1)==MainActivity.long2Data.indexOf(result2))
                {appendText(verifyInput);makebeep();}

            }
        }else if(24<=len){
            if(MainActivity.words24long.containsKey(verifyInput)){appendText(verifyInput);
            makebeep();}
        }
    }
    public void appendText(String Word){
        Word=Word+'\n';
        outputText.append(Word);
    }
    public void clearText(){
    outputText.setText("");
    }
public void makebeep(){
    ToneGenerator beepsound = new ToneGenerator(AudioManager.STREAM_MUSIC, 250);
    beepsound.startTone(ToneGenerator.TONE_CDMA_PIP,250);
}
}
