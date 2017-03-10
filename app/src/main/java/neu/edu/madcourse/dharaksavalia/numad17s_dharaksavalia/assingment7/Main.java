package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.WordGame;

/**
 * Created by Dharak on 3/8/2017.
 */

public class Main extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offlineonlineoptions);
        variousoptions();
    }
    public void variousoptions(){
        Button offline=(Button)findViewById(R.id.offlinewordgame);
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (Main.this, WordGame.class);
                startActivity(intent);
            }
        });
        Button online=(Button)findViewById(R.id.onlinewordgame);
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (Main.this,Communication.class);
                startActivity(intent);
            }
        });
    }
}
