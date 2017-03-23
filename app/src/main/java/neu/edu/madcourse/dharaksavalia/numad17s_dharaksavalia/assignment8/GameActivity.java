package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment8;

/**
 * Created by Dharak on 1/20/2017.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.GameBoardTest1;
//import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.GameFragment;


public class GameActivity extends Activity implements SensorEventListener {
    public static final String KEY_RESTORE = "key_restore";
    public static final String PREF_RESTORE = "pref_restore";
   // public static final String TUTORIAL="tutorialrestore";
   // public boolean tutorial=true;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();
    private GameFragment mGameFragment;
    private ImageButton mute;
    private SensorManager mSensorManger;
    private Sensor msensor;
    private Boolean internetConnectivity=false;
    DatabaseReference internetConnectivityReference;
    ValueEventListener internetConnectivityValue;
    DatabaseReference message;
    DatabaseReference messageListen;
    ValueEventListener messageValue;
    private String oppositePlayerMessage;
    String Token;
    Long LastShake;
    Long CurrentShake;
    Long CurrentTime;
    Long PreviousTime;
    private static final int shakeThreshold = 800;
    float accelerometer_X=-0.1f;
    float accelerometer_Y=-0.1f;
    float accelerometer_Z=-0.1f;
    float accelerometerLast_X=-0.1f;
    float accelerometerLast_Y=-0.1f;
    float accelerometerLast_Z=-0.1f;
    int count=0;

//    private static TimerCountDown Time = new TimerCountDown(45000, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.twowordactivity_game);
        mGameFragment = (GameFragment) getFragmentManager()
                .findFragmentById(R.id.wordfragment_game);
        boolean restore = getIntent().getBooleanExtra(KEY_RESTORE, false);
      //  Time.start(this);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*
        if (restore) {
            String gameData = getPreferences(MODE_PRIVATE)
                    .getString(PREF_RESTORE, null);
            if (gameData != null) {
                mGameFragment.putState(gameData);
            }
        }
        */
        String str=getIntent().getStringExtra("Token");
        Token=str;
        if(str!=null){
            Log.d("inside the game data","Yipee");
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("GameBoard").child(str);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    GameBoardTest1 test1=dataSnapshot.getValue(GameBoardTest1.class);
                    if(test1!=null){
                        Toast.makeText(getApplicationContext(),"YIPEE",Toast.LENGTH_SHORT).show();
                        mGameFragment.putOnlineData(test1);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Log.d("UT3", "restore = " + restore);
        final ImageButton mute=(ImageButton)findViewById(R.id.stopMusic);
        this.mute=mute;
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStartMusic();
            }
        });
        ImageButton resume=(ImageButton) findViewById(R.id.wordbutton_pause);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameFragment.pauseTimer=true;
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setMessage("Detected words are "+mGameFragment.getDetectedWord());
                builder.setCancelable(false);
                final View view1=findViewById(R.id.wordfragment_game);
                view1.setVisibility(View.GONE);
                if(mMediaPlayer.isPlaying())mMediaPlayer.pause();
                builder.setPositiveButton("Resume",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mGameFragment.pauseTimer=false;
                                view1.setVisibility(View.VISIBLE);
                                if(mGameFragment.musicValue)mMediaPlayer.start();

                            }
                        });

                Dialog mDialog = builder.show();
            }
        });

    if(mGameFragment.musicValue)mute.setImageResource(R.drawable.soundon);
        else mute.setImageResource(R.drawable.soundoff);
        mute.getDrawable().setLevel(1);
    }
    private void internetConnectivityCheck(){
        internetConnectivityReference=FirebaseDatabase.getInstance().getReference(".info/connected");
        internetConnectivityValue=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean connected=dataSnapshot.getValue(Boolean.class);
                if(connected){
                    internetConnectivity=true;
                    if(messageValue!=null){
                    SetDataReference();

                    }

                }
                else{
                    internetConnectivity=false;
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        internetConnectivityReference.addValueEventListener(internetConnectivityValue);
    }
    private void removeInternetConnectivityListner(){

    }
    private void SetDataReference(){
        if(Token!=null)
            if(mGameFragment.getPlayer()!=null) {
                if (mGameFragment.getPlayer() == GameFragment.Player.player1) {
                    message = FirebaseDatabase.getInstance().getReference("GameBoard").child(Token).child("myMessage").child("player1");
                    messageListen = FirebaseDatabase.getInstance().getReference("GameBoard").child(Token).child("myMessage").child("player2");

                } else {
                    message = FirebaseDatabase.getInstance().getReference("GameBoard").child(Token).child("myMessage").child("player2");
                    messageListen = FirebaseDatabase.getInstance().getReference("GameBoard").child(Token).child("myMessage").child("player1");

                }
                messageValue = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null){
                        oppositePlayerMessage=dataSnapshot.getValue(String.class);

                        Toast.makeText(getBaseContext(),oppositePlayerMessage,Toast.LENGTH_SHORT).show();
                        Vibrator v = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(500);
                    }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                messageListen.addValueEventListener(messageValue);
            }
    }

    private void stopStartMusic() {
       // EditText e = (EditText) findViewById(R.id.text_view_dialogbox);

        if(mMediaPlayer.isPlaying()){mMediaPlayer.pause();
            mute.setImageResource(R.drawable.soundoff);
        mGameFragment.musicValue=false;}
        else {mMediaPlayer.start();
            mute.setImageResource(R.drawable.soundon);
        mGameFragment.musicValue=true;}
    }
    public void stopMusic(){
        if(mMediaPlayer.isPlaying()

                ){
            mMediaPlayer.pause();
            mute.setImageResource(R.drawable.soundoff);
            mGameFragment.musicValue=false;
        }
    }
    public void startMusic(){
            if(mMediaPlayer!=null){
                mMediaPlayer.start();
                mute.setImageResource(R.drawable.soundon);
                mGameFragment.musicValue=true;
            }

    }

    public void restartGame() {
        mGameFragment.restartGame();
    }
   // public void TimeFinished(){mGameFragment.TimeFinished();}

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
        PreviousTime = System.currentTimeMillis();
        LastShake = System.currentTimeMillis();

        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        if(mGameFragment.musicValue==false)mMediaPlayer.pause();
        mSensorManger=(SensorManager)getSystemService(this.SENSOR_SERVICE);
        msensor=mSensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(msensor!=null){
            mSensorManger.registerListener(this,msensor,SensorManager.SENSOR_DELAY_GAME);
        }else{
            Toast.makeText(this,"No sensor available",Toast.LENGTH_SHORT).show();
        }
        internetConnectivityCheck();


    }


    @Override
    protected void onPause() {
        super.onPause();
        if(msensor!=null){

        }
        mHandler.removeCallbacks(null);
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        String gameData = mGameFragment.getState();
        getPreferences(MODE_PRIVATE).edit()
                .putString(PREF_RESTORE, gameData)
                .commit();
     //   getPreferences(MODE_APPEND).edit().putString(TUTORIAL,"false").commit();
        Log.d("UT3", "state = " + gameData);
        if(msensor!=null)
        mSensorManger.unregisterListener(this);
        if(internetConnectivityValue!=null)
            internetConnectivityReference.removeEventListener(internetConnectivityValue);
        if(messageValue!=null)message.removeEventListener(messageValue);
    }
    public void Done(){
        mGameFragment.Done();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
           // event.equals(ACCELE)
            CurrentShake = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((CurrentShake - PreviousTime) > 100) {
                accelerometer_X=event.values[0];
                accelerometer_Y=event.values[1];
                accelerometer_Z=event.values[2];
                long diffTime = (CurrentShake - PreviousTime);
                float Speed=(Math.abs(accelerometer_X+accelerometer_Y+accelerometer_Z -accelerometerLast_X - accelerometerLast_Y - accelerometerLast_Z) / diffTime * 10000);
                //Toast.makeText(this,String.valueOf(Speed),Toast.LENGTH_SHORT).show();

                if ((Math.abs(accelerometer_X+accelerometer_Y+accelerometer_Z -accelerometerLast_X - accelerometerLast_Y - accelerometerLast_Z) / diffTime * 10000) > 100) {
                    if(LastShake-CurrentShake>4000){
                        LastShake=CurrentShake;
                        Toast.makeText(this,String.valueOf(Speed),Toast.LENGTH_SHORT).show();
                        if(message!=null)
                        message.setValue("Play fast ",String.valueOf(count++));
                }
                accelerometerLast_X = accelerometer_X;
                accelerometerLast_Y = accelerometer_Y;
                accelerometerLast_Z = accelerometer_Z;
                    PreviousTime=CurrentShake;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}