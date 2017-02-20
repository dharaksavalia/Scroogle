package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

/**
 * Created by Dharak on 1/20/2017.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;


public class GameActivity extends Activity {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;
//    private static TimerCountDown Time = new TimerCountDown(45000, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wordactivity_game);
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.wordfragment_game);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
      //  Time.start(this);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }
        Log.d("UT3", "restore = " + restore);
        final Button mute=(Button)findViewById(R.id.stopMusic);
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStartMusic();
            }
        });
        Button resume=(Button) findViewById(R.id.wordbutton_pause);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameFragment.pauseTimer=true;
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setMessage("Detected words are "+mGameFragment.getDetectedWord());
                builder.setCancelable(false);
                final View view1=findViewById(R.id.wordfragment_game);
                view1.setVisibility(View.GONE);
                builder.setPositiveButton("Resume",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mGameFragment.pauseTimer=false;
                                view1.setVisibility(View.VISIBLE);

                            }
                        });

                Dialog mDialog = builder.show();
            }
        });

    }

    private void stopStartMusic() {
        if(mMediaPlayer.isPlaying()){mMediaPlayer.pause();
        mGameFragment.musicValue=false;}
        else {mMediaPlayer.start();
        mGameFragment.musicValue=true;}
    }
    public void stopMusic(){
        if(mMediaPlayer.isPlaying()
                ){
            mMediaPlayer.pause();
            mGameFragment.musicValue=false;
        }
    }
    public void startMusic(){
            if(mMediaPlayer!=null){
                mMediaPlayer.start();
                mGameFragment.musicValue=true;
            }


    }

    public void restartGame() {
        mGameFragment.restartGame();
    }
   // public void TimeFinished(){mGameFragment.TimeFinished();}
    public void reportWinner(final Tile.Owner winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
        builder.setMessage(getString(R.string.declare_winner, winner));
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok_label,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        final Dialog dialog = builder.create();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
               // mMediaPlayer = MediaPlayer.create(GameActivity.this,
                       // winner == Tile.Owner.X ? R.raw.bensound_funnysong
                         //       : winner == Tile.Owner.O ? R.raw.notr_loser
                           //     : R.raw.bensound_cute
               // );
                mMediaPlayer.start();
                dialog.show();
            }
        }, 500);

        // Reset the board to the initial position
        mGameFragment.initGame();
    }

    public void startThinking() {
        View thinkView = findViewById(R.id.wordthinking);
        thinkView.setVisibility(View.VISIBLE);
    }

    public void stopThinking() {
        View thinkView = findViewById(R.id.wordthinking);
        thinkView.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.bensound_littleidea);

        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        if(mGameFragment.musicValue==false)mMediaPlayer.pause();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(null);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        String gameData = mGameFragment.getState();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .commit();
        Log.d("UT3", "state = " + gameData);
    }
    public void Done(){
        mGameFragment.Done();
    }


}