package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

/**
 * Created by Dharak on 1/20/2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.MainActivity;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.assignment1MainActivity;


public class WordGame extends Activity {
    static boolean music;
    public static String TUTORIAL="tutorialboolean";
    public static boolean tutorialflag=true;

    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordativity_main);
        setTitle("Word Game");
        tutorialflag=getPreferences(MODE_PRIVATE).getBoolean(TUTORIAL,tutorialflag);

        final Button Tutorial=(Button) findViewById(R.id.tutorial);
        Tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Tutorial.getText()=="Tutorial On"){Tutorial.setText("Tutorial Off");
                tutorialflag=false;}
                else {Tutorial.setText("Tutorial On");tutorialflag=true;}
            }
        });

        if(tutorialflag){Tutorial.setText("Tutorial On");
        Log.d("setting","true");}
        else Tutorial.setText("Tutorial Off");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button Tutorial=(Button) findViewById(R.id.tutorial);
        if(tutorialflag)Tutorial.setText("Tutorial On");
        else Tutorial.setText("Tutorial Off");
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferences(MODE_PRIVATE).edit()
                .putBoolean(TUTORIAL, tutorialflag)
                .commit();
        Log.d("comminting",String.valueOf(tutorialflag));
    }
}
