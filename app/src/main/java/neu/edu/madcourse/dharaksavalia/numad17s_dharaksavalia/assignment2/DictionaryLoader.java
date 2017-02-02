package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 1/29/2017.
 */

public class DictionaryLoader extends Activity {
    DataInputStream dataInt;
    DataInputStream dataLong;
    DataInputStream dataLong2;
    Scanner dataWord;
    int[] intData;
    long []longData;
    long []long1Data;
    long []long2Data;

    protected void onCreate(Bundle savedInstanceState) {
        intData=new int[60713];
        longData=new long[302298];
        long1Data=new long[69268];
        long2Data=new long[69268];
        super.onCreate(savedInstanceState);
        InputStream is=getResources().openRawResource(R.raw.intfile);
        dataInt=new DataInputStream(is);
        InputStream inputlongfile=getResources().openRawResource(R.raw.longfile);
        InputStream inputlongfile2=getResources().openRawResource(R.raw.longfile2);
        dataLong=new DataInputStream(inputlongfile);
        dataLong2=new DataInputStream(inputlongfile2);
    Log.d("loading started","loading............");
        for(int i=0;i<60712;i++) {

            try {
                intData[i] = dataInt.readInt();
            } catch (IOException e) {
                Log.d("dictionary", "file int dictionary unable to load");
            }

        }
        try {
            for (int i=0;i<302297;i++){

                    longData[i]=dataLong.readLong();
                }
            }catch (IOException e) {
            Log.d("dictionary","");
        }
        for (int i=0;i<69267;i++){
            try {
                long1Data[i]=dataLong2.readLong();
                long2Data[i]=dataLong2.readLong();

            } catch (IOException e) {
                Log.d("dictionary","file long2 dictionary unable to load");
            }
        }
        Log.d("loading.......","Loading.....ended");
        try {
            dataInt.close();
            dataLong.close();
            dataLong2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.dictionary);
 //       int i=0;
    }

}