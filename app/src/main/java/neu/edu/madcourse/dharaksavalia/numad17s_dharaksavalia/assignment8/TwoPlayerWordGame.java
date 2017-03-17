package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.WordGame;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.Communication;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.assignment7acknowlegements;

/**
 * Created by Dharak on 3/15/2017.
 */

public class TwoPlayerWordGame extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlineonlineoptions);
        variousoptions();
        setTitle("Two Player word Game");
    }
    public void variousoptions(){
        Button offline=(Button)findViewById(R.id.offlinewordgame);
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (TwoPlayerWordGame.this, WordGame.class);
                startActivity(intent);
            }
        });
        Button online=(Button)findViewById(R.id.onlinewordgame);
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (TwoPlayerWordGame.this,Communication.class);
                startActivity(intent);
            }
        });
        Button acknowledgments=(Button)findViewById(R.id.ass7acknowlegments);
        acknowledgments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (TwoPlayerWordGame.this,assignment7acknowlegements.class);
                startActivity(intent);
            }
        });
    }

}
