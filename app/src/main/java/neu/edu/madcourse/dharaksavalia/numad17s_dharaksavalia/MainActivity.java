package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.ScrollingView;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.aboutFunction;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.assignment1MainActivity;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2.TestDictionary;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.WordMainActivity;


public class MainActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    DataInputStream dataInt;
    DataInputStream dataLong7;
    DataInputStream dataLong8;
    DataInputStream dataLong10;
    DataInputStream dataLong11;
    DataInputStream dataLong12;
    Scanner dataWord;
    Scanner dataWord9;


    @Override
    public void startActivityFromChild(Activity child, Intent intent, int requestCode) {

    }

    public static HashMap<Long,Long>longData;
    public static ArrayList <Long>long1Data;
    public static ArrayList<Long> long2Data;

    public static HashMap<String,String> words24long=new HashMap<String,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variousMenu();
        //DictionaryLoader dl=new DictionaryLoader();
        //dl.run();
        this.setTitle("Dharak Savalia");
        /*
        intData=new HashMap<Integer, Integer>(60713);
        longData=new HashMap<Long, Long>(302298);
        long1Data=new ArrayList<>(69268);
        long2Data=new ArrayList<>(69268);*/
        super.onCreate(savedInstanceState);
        InputStream is=getResources().openRawResource(R.raw.intfile);
        dataInt=new DataInputStream(is);
        InputStream inputlongfile7=getResources().openRawResource(R.raw.longfile7);
        InputStream inputlongfile8=getResources().openRawResource(R.raw.longfile8);
        InputStream inputlongfile10=getResources().openRawResource(R.raw.longfile10);
        InputStream inputlongfile11=getResources().openRawResource(R.raw.longfile11);
        InputStream inputlongfile12=getResources().openRawResource(R.raw.longfile12);

        dataLong7=new DataInputStream(inputlongfile7);
        dataLong8=new DataInputStream(inputlongfile8);
        dataLong10=new DataInputStream(inputlongfile10);
        dataLong11=new DataInputStream(inputlongfile11);
        dataLong12=new DataInputStream(inputlongfile12);

        InputStream inputwordfile=getResources().openRawResource(R.raw.wordfile);
        BufferedReader word13= new BufferedReader(new InputStreamReader(inputwordfile));
        InputStream inputwordfile9=getResources().openRawResource(R.raw.wordfile9);
        BufferedReader word9= new BufferedReader(new InputStreamReader(inputwordfile9));
        Log.d("loading started","loading............");
        DictionaryLoader dr=DictionaryLoader.getInstance(dataInt,
                dataLong7,dataLong8,dataLong10,dataLong11,
                dataLong12,word9,word13);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        Log.d(ts,"Main");
        dr.run9();
        tsLong = System.currentTimeMillis()/1000;
        ts = tsLong.toString();
        Log.d(ts,"Main2");
        /*Long tsLong = System.currentTimeMillis()/1000;
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
        tsLong=System.currentTimeMillis()/1000;
        ts=tsLong.toString();
        Log.d(ts,"2 nd time");
        try {
            for (int i=0;i<302297;i++){

             long c=dataLong.readLong();
              longData.put(c,c);
            }
        }catch (IOException e) {
            Log.d("dictionary","");
        }
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
        tsLong=System.currentTimeMillis()/1000;
        ts=tsLong.toString();
        Log.d(ts,"60 thousand");
        for(int i=0;i<58;i++){
            try {
                words24long.put(br.readLine(),br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
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
        */
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void variousMenu() {
        final Button tickTackToeButton = (Button) findViewById(R.id.utimateTicToeButton);
        Button aboutButton = (Button) findViewById(R.id.aboutButton);
        Button generateErrorButton = (Button) findViewById(R.id.generateErrorButton);
        Button quit = (Button) findViewById(R.id.quitButton);
        Button dictionary=(Button)findViewById(R.id.dictionary);
        Button wordGame=(Button)findViewById(R.id.wordGame);
        wordGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenet1=new Intent(MainActivity.this,WordMainActivity.class);
                startActivity(intenet1);
            }
        });

        tickTackToeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intenet1=new Intent(MainActivity.this,assignment1MainActivity.class);
                startActivity(intenet1);
            }
        });
        dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (MainActivity.this,TestDictionary.class);
                startActivity(intent);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,aboutFunction.class);
                startActivity(intent);


            }
        });
        generateErrorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            int d=0/0;
            }
        });
        quit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.exit(0);
            }

        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
