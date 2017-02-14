package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.GameActivity;

/**
 * Created by Dharak on 2/14/2017.
 */

public class WordGame extends Activity {
    private AlertDialog mDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Word Game");
    setContentView(R.layout.mainwordgame);
        Button newButton = (Button)findViewById(R.id.wordGamenew_button);
        Button continueButton = (Button)findViewById(R.id.wordGamecontinue_button);
        Button aboutButton = (Button)findViewById(R.id.wordGameabout_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordGame.this, GameActivity.class);
                startActivity(intent);
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordGame.this, GameActivity.class);
                intent.putExtra(GameActivity.KEY_RESTORE, true);
                startActivity(intent);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WordGame.this);
                builder.setMessage(R.string.wordgameabout_text);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        if(mDialog!=null)
            mDialog.dismiss();
    }
}



