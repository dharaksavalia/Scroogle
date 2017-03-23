package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment8;

/**
 * Created by Dharak on 1/20/2017.
 */
/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
 ***/

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
//import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.GameActivity;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.TestDictionary;
//import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.Tile;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.WordGame;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.GameBoard;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.GameBoardTest1;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.OnlineTile;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.Tiles;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user.User;

import static java.lang.Thread.sleep;
import static neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader.words9long;

public class GameFragment extends Fragment implements SensorEventListener {

    static private int mLargeIds[] = {R.id.wordlarge1, R.id.wordlarge2, R.id.wordlarge3,
            R.id.wordlarge4, R.id.wordlarge5, R.id.wordlarge6, R.id.wordlarge7, R.id.wordlarge8,
            R.id.wordlarge9,};
    static private int mSmallIds[] = {R.id.wordsmall1, R.id.wordsmall2, R.id.wordsmall3,
            R.id.wordsmall4, R.id.wordsmall5, R.id.wordsmall6, R.id.wordsmall7, R.id.wordsmall8,
            R.id.wordsmall9,};
    TestDictionary dr;
    User user;

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public enum Player{
        player1,player2
    }
    SensorManager msensorManager;
    Sensor msensor;

    private static final String SERVER_KEY = "key=AAAApVpoVJU:APA91bGjYQDhFe24ljWuSD6tNIIZ9Y_D3UqphyOZNz8Gt8fKelpNKHMS1NAvI98is4KcBt8rvW5kQmaz-KNcbMRHwkh9F-Aj9ZFH_IWdqhT2-91mJJt49Y8ELjLNX9L7HHkCW5lspbvS";
    Boolean GameRunning=true;
    Player player;
    DatabaseReference reference;
    boolean flagRandom=false;
    boolean firstMove=true;
    public boolean tutorial=WordGame.tutorialflag;
    int n=90;
    private boolean myinternet=true;
    boolean secondTutorial=false;
    public boolean musicValue=true;
    private AlertDialog mDialog;
    static String accumulator="";
    static String patternInput="";
    private Handler mHandler = new Handler();
    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    private int ToastCount=0;
   // private OnlineTile.Owner mPlayer = OnlineTile.Owner.X;
    private Tile currentLarge;
    private Set<Tile> mAvailable = new HashSet<Tile>();
    private Set<Tile>mLargeUsed=new HashSet<Tile>();
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    private int mLastLarge;
    private int mLastSmall;
    String player1;
    String player2;
    Tiles tiles;
     private enum TurnValue{
        my_Turn,Other_Player
    }
    TurnValue turnValue=TurnValue.my_Turn;
    DatabaseReference tilesReference;
    ValueEventListener tilesReferenceValue;
    GameBoardTest1.Turn turn;
    TextView txt;
    DatabaseReference connected;
    ValueEventListener connectedValue;
    private  boolean internetConnectivity;
    DatabaseReference referenceScore;
    ValueEventListener referenceScoreValue;
    private DatabaseReference internetConnectivityReference;
    private ValueEventListener interenetConnectivityValue;
    private boolean otherplayer=true;
    Handler internetHandler=new Handler();
    Thread internetThread;
    AlertDialog InternetDilaog;
    Integer oppositenumber,count,selfnumber,oldnumber;
    AlertDialog Internet;
    private int Score1=0;
    private int Score2=0;

    private boolean firstLevel=true;
    private boolean secondlevel=false;
    TextView ScoreView;
    boolean pauseTimer=false;
     boolean Flag=false;
    private Handler handler=new Handler();
    private Runnable runnable;
    private Set<String>detectedWord=new HashSet<String>();
    int numberCorrectWord=0;
    ValueEventListener referenceValue;
    DatabaseReference timer;
    ValueEventListener timerValue;
    DatabaseReference playerTurn;
    ValueEventListener playerTurnValue;

    HashMap<Character,Integer> ScoreMap=new HashMap<>();
    static private String [] pattern={"036784512", "036478512", "401367852", "425103678", "748521036", "037852146", "036785214", "214587630", "254103678",
                "043678521", "630124785", "031467852"};
    private void addDetectedWord(String Str){
        detectedWord.add(Str);
    }
    private boolean inDetectedWord(String Str){
        return detectedWord.contains(Str);
    }
    public String getDetectedWord(){

        return detectedWord.toString();
    }
    private void clearDetectedWord(){
        detectedWord.clear();
    }
    private void updateTextView(){
       // Log.d("accumulator",accumulator);
        txt=(TextView)getActivity().findViewById(R.id.wordTextView);
        txt.setText(accumulator.toUpperCase());
        ScoreView=(TextView)getActivity().findViewById(R.id.wordScore);

        ScoreView.setText("Score  "+String.valueOf(Score1));
        if(internetConnectivity)
        if(player==Player.player1)reference.child("scores").child(player1).setValue(Score1);
        else
        {
            reference.child("scores").child(player2).setValue(Score1);
        }

    }
    public void  setSoundPool(){
        if(mSoundPool==null)
            mSoundPool=null;
    }

    public void TimeFinished(){
        mAvailable.clear();
        //if(firstLevel)
        //Done();
        if(firstLevel==false)GameFinished();
        if(firstLevel)
        secondLevelInitialze();
    }

    private void GameFinished() {
        Log.d("this","in Game finsihed");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mAvailable.clear();
        GameRunning=false;
        //handler.removeCallbacks(runnable);
        String Str="";
        if(Score1>Score2){
            Str="\n  YOU LOST";

        }else if(Score1<Score2){
            Str="\n YOU WON";
        }else{
            Str="\nIT WAS A TIE";
        }


        builder.setMessage("Game Finished \nYour Score is: "+String.valueOf(Score1)+"\nOther Player Score is: "+String.valueOf(Score2)+Str);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ok_label,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing
                    }
                });
        mDialog = builder.show();
        secondlevel=false;
        handler.removeCallbacks(runnable);
        Locale.getDefault().getCountry();
        DateFormat df = DateFormat.getTimeInstance();
        df.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = df.format(new Date());
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Leaderboard");
            Scores scores = new Scores(user.getUsername(), Locale.getDefault().getCountry(), gmtTime,Score1);
            reference1.push().setValue(scores);
        return;
    }

    public void calculateScore(String str){
        if(str.length()==9)
            Score1+=20;
        for (int i=0;i<str.length();i++){
            Score1+=ScoreMap.get(str.charAt(i));
        }


    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        1 point: E, A, I, O, N, R, T, L, S; 2 points: D, G; 3 points: B, C, M, P; 4 points: F, H, V, W, Y; 5 points: K; 8 points: J, X; 10 points: Q, Z.
         */
        ScoreMap.put('e',1);
        ScoreMap.put('a',1);
        ScoreMap.put('i',1);
        ScoreMap.put('o',1);
        ScoreMap.put('u',1);
        ScoreMap.put('n',1);
        ScoreMap.put('r',1);
        ScoreMap.put('t',1);
        ScoreMap.put('l',1);
        ScoreMap.put('s',1);
        ScoreMap.put('d',2);
        ScoreMap.put('g',2);
        ScoreMap.put('b',3);
        ScoreMap.put('c',3);
        ScoreMap.put('m',3);
        ScoreMap.put('p',3);
        ScoreMap.put('f',4);
        ScoreMap.put('h',4);
        ScoreMap.put('v',4);
        ScoreMap.put('w',4);
        ScoreMap.put('y',4);
        ScoreMap.put('k',5);
        ScoreMap.put('j',8);
        ScoreMap.put('x',8);
        ScoreMap.put('q',10);
        ScoreMap.put('z',10);
    Log.d("length=",String.valueOf(ScoreMap.size()));
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        initGame();
        dr=new TestDictionary();
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
        mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
        mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
        mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);
        ImageButton music=(ImageButton)getActivity().findViewById(R.id.stopMusic);

    }

    private void clearAvailable() {
        mAvailable.clear();
    }

    private void addAvailable(Tile tile) {
       tile.animate();
        mAvailable.add(tile);
    }

    public boolean isAvailable(Tile tile) {
        return mAvailable.contains(tile);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.wordlarge_board, container, false);
        initViews(rootView);
        updateAllTiles();
        return rootView;
    }

    private char[] arrange(String pattern, String word){
//        ArrayList<String> result=new ArrayList<>();
        char[] result= new char[9];
        for (int i=0;i<9;i++){
           // String str="";
            //String str1=""+pattern.charAt(i);
           //Log.d("str1",str1);
            //str=""+word.charAt(i);
            result[Character.getNumericValue(pattern.charAt(i))]=word.charAt(i);
        }
        //Log.d("result",result.toString());
        return result;
    }
    private boolean countFinshed(){

        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if(mSmallTiles[i][j].getStatus()==Tile.Status.notselected||mSmallTiles[i][j].getStatus()==Tile.Status.intermediate)return false;
            }

        }
        return true;
    }
    private boolean countFinished(){

        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if(mSmallTiles[i][j].getStatus()==Tile.Status.notselected||mSmallTiles[i][j].getStatus()==Tile.Status.intermediate)return false;
            }

        }
        return true;
    }
    private void pushNotification() {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData=new JSONObject();
        try {
            jNotification.put("title", "Game Play invite from "+user.getUsername().toString());
            jNotification.put("body", "Come fast, I am waiting!! ");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "Two Player");

            // If sending to a single client
            String key;
            if(player.equals(Player.player1))
                key=player2;
            else key=player1;

            jPayload.put("to",key );
            jData.put("Token",player1);
            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */
            jPayload.put("data",jData);
            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("Hello", "run: " + resp);
                    Toast.makeText(getActivity(),resp,Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
    private void initViews(View rootView) {
        mEntireBoard.setView(rootView);
        accumulator="";
        patternInput="";
        clearDetectedWord();
        Log.d("d","hi1");
        //Random random=new Random();
        ArrayList<String> words;
        //words=randomWord();

        //Log.d("words",words.toString());
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            //String word=words.get(large);
            mLargeTiles[large].setView(outer);
            //int n=random.nextInt(pattern.length);
            char[] str;
            //str=arrange(pattern[n],word);

            for (int small = 0; small < 9; small++) {
                Button inner = (Button) outer.findViewById
                        (mSmallIds[small]);
                final int fLarge = large;
                final int fSmall = small;
                final Tile smallTile = mSmallTiles[large][small];
                smallTile.setView(inner);
              //  smallTile.setStr(Character.toString(str[small]));
                //smallTile.setStr(randomWord());
                // ...
                inner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        smallTile.animate();
                        // ...
                        if(firstLevel==false)
                            Log.d("Second ","YIPEE");

                        if (isAvailable(smallTile)) {
                            //((GameActivity)getActivity()).startThinking();
                            mSoundPool.play(mSoundX, mVolume, mVolume, 1, 0, 1f);
                            if(!internetConnectivity) {
                                //AlertDialog builder=new AlertDialog();
                                if (InternetDilaog != null)
                                    if (InternetDilaog.isShowing()) InternetDilaog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("Internet not available");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                InternetDilaog = builder.show();
                                return;
                            }
                            if(turnValue!=TurnValue.my_Turn){
                                DialogBox("Not Your turn",1000);
                                return;
                            }
                            makeMove(fLarge, fSmall);


                            //think();
                        } else {
                            mSoundPool.play(mSoundMiss, mVolume, mVolume, 1, 0, 1f);
                        }
                    }
                });
                // ...
            }
        }
    }
/*
    private void think() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() == null) return;
                if (mEntireBoard.getOwner() == OnlineTile.Owner.NEITHER) {
                    int move[] = new int[2];
                    pickMove(move);
                    if (move[0] != -1 && move[1] != -1) {
                        switchTurns();
                        mSoundPool.play(mSoundO, mVolume, mVolume,
                                1, 0, 1f);
                        makeMove(move[0], move[1]);
                        switchTurns();
                    }
                }
                ((GameActivity) getActivity()).stopThinking();
            }
        }, 1000);
    }*/
/*
    private void pickMove(int move[]) {
        OnlineTile.Owner opponent = mPlayer == OnlineTile.Owner.X ? OnlineTile.Owner.O : OnlineTile
                .Owner.X;
        int bestLarge = -1;
        int bestSmall = -1;
        int bestValue = Integer.MAX_VALUE;
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                OnlineTile smallTile = mSmallTiles[large][small];
                if (isAvailable(smallTile)) {
                    // Try the move and get the score
                    OnlineTile newBoard = mEntireBoard.deepCopy();
                    newBoard.getSubTiles()[large].getSubTiles()[small]
                            .setOwner(opponent);
                    int value = newBoard.evaluate();
                    Log.d("UT3",
                            "Moving to " + large + ", " + small + " gives value " +
                                    "" + value
                    );
                    if (value < bestValue) {
                        bestLarge = large;
                        bestSmall = small;
                        bestValue = value;
                    }
                }
            }
        }
        move[0] = bestLarge;
        move[1] = bestSmall;
        Log.d("UT3", "Best move is " + bestLarge + ", " + bestSmall);
    }
    */

    private void switchTurns() {
        //turn = turn == GameBoardTest1.Turn.player1 ? GameBoardTest1.Turn.player2 : GameBoardTest1.Turn.player2;
        if (turn == GameBoardTest1.Turn.player1)
            if (player.equals(Player.player1)) {
                reference.child("timer").setValue(15);
                reference.child("turn").setValue(turn == GameBoardTest1.Turn.player1 ? GameBoardTest1.Turn.player2 : GameBoardTest1.Turn.player1);

            }
        if(turn== GameBoardTest1.Turn.player2)
            if(player.equals(Player.player2)){
                reference.child("timer").setValue(15);
                reference.child("turn").setValue(turn == GameBoardTest1.Turn.player1 ? GameBoardTest1.Turn.player2 : GameBoardTest1.Turn.player1);
            }
    }
    private void patternAccumulator(int s){
        patternInput=patternInput+ String.valueOf( s);
        //Log.d(String.valueOf(s),patternInput);
        //Log.d(patternInput,":Pattern");
        if(internetConnectivity)
        if(player.equals(Player.player1))
        reference.child("selected1").setValue(patternInput);
        else{
            reference.child("selected2").setValue(patternInput);
        }
    }
    private void StringAccumulator(String string){
    accumulator+=string;
        dr.verifyInput(accumulator);
        updateTextView();
        if(internetConnectivity)
        if(player.equals(Player.player1))
            reference.child("word1").setValue(accumulator);
        else{
            reference.child("word2").setValue(accumulator);
        }
    }

    private void makeMove(int large, int small) {
        if(firstLevel==false)patternInput="";
        if(patternInput.length()<1 ){

        }
        else{
            //Log.d("pattern",patternInput);
            //Log.d("inside the other","yipee");
            if(firstLevel)
                if(findAdj(Integer.parseInt(Character.toString(patternInput.charAt(patternInput.length()-1))),small)==false)return ;

            }
            if(firstLevel==false){
              //  Log.d("Inide","second Level");
             //   clearAvailable();

            }


        mLastLarge = large;
        mLastSmall = small;
        Flag=false;
        Tile smallTile = mSmallTiles[large][small];
        //smallTile.setStatus(Tile.Status.selected);
        updateAParticularTile(large,small,OnlineTile.Status.selected);
        //if(mAvailable.contains(smallTile)==false)return;
        currentLarge=mLargeTiles[large];
       // Log.d("pattern:",String.valueOf(small));
       // if(first)

        if(firstLevel) {
            patternAccumulator(small);
        }
        if(secondTutorial&&(patternInput.length()==3)){
            Log.d("inside","the thing");showDialog2();
            firstMove=false;}
        if(firstLevel)
        for (int big=0;big<9;big++){
            if(mLargeTiles[big]==currentLarge)continue;
            if(mLargeUsed.contains(mLargeTiles[big]))continue;
            for (int i=0;i<9;i++){
                mSmallTiles[big][i].setStatus(Tile.Status.intermediate);
            }

        }

        String st=smallTile.getStr();
        StringAccumulator(st);

        Tile largeTile = mLargeTiles[large];
        //smallTile.setOwner(mPlayer);
        smallTile.setStatus(Tile.Status.selected);
        if(firstLevel)setAvailableFromLastMove(large);
        //OnlineTile.Owner oldWinner = largeTile.getOwner();
        if(firstLevel==false)setAavailableforsecond();
        largeTile.setStatus(Tile.Status.selected);
        /*OnlineTile.Owner winner = largeTile.findWinner();
        if (winner != oldWinner) {
            largeTile.animate();
            largeTile.setOwner(winner);
        }
        */
        updateAllTiles();
        if(firstLevel)
        if(patternInput.length()==9)Done();
        /*
        if (winner != OnlineTile.Owner.NEITHER) {
            ((GameActivity)getActivity()).reportWinner(winner);
        }
        */
    }
    public void updateAParticularTile(int large,int small,OnlineTile.Status status){
    if(player==Player.player2)if(status.equals(OnlineTile.Status.selected))status=OnlineTile.Status.oppositePlayer;
    switch (large){
        case 0:
            DatabaseReference ref1=reference.child("tiles").child("tiles1");
            ref1.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 1:
            DatabaseReference ref2=reference.child("tiles").child("tiles2");
            ref2.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 2:DatabaseReference ref3=reference.child("tiles").child("tiles3");
            ref3.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 3:
        DatabaseReference ref4=reference.child("tiles").child("tiles4");
        ref4.child(String.valueOf(small)).child("status").setValue(status);
        break;
        case 4:DatabaseReference ref5=reference.child("tiles").child("tiles5");
            ref5.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 5:DatabaseReference ref6=reference.child("tiles").child("tiles6");
            ref6.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 6:DatabaseReference ref7=reference.child("tiles").child("tiles7");
            ref7.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 7:DatabaseReference ref8=reference.child("tiles").child("tiles8");
            ref8.child(String.valueOf(small)).child("status").setValue(status);
            break;
        case 8:DatabaseReference ref9=reference.child("tiles").child("tiles9");
            ref9.child(String.valueOf(small)).child("status").setValue(status);
            break;


    }

    }

    private void setAavailableforsecond() {
        clearAvailable();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
            if(mSmallTiles[i][j].getStatus()==Tile.Status.notselected)
                mAvailable.add(mSmallTiles[i][j]);
            }
        }

    }

    public void restartGame() {
        mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
        handler.removeCallbacks(runnable);
        // ...
        firstLevel=true;
        secondlevel=true;
        Score1=0;
        initGame();
        this.n=90;
        initViews(getView());
        updateAllTiles();
        updateTextView();
        setAllAvailable();

    }

    private void updateTime() {
        TextView txt=(TextView) getActivity().findViewById(R.id.twowordTimer);
        if(txt!=null)
            if(n>-1){
            if(n<10){
                txt.setTextColor(Color.RED);

            }else{
                txt.setTextColor(Color.GREEN);
            }

            txt.setText(String.valueOf(n));
            txt.setVisibility(View.VISIBLE);
        }
        if(n==10){
            DialogBox( String.valueOf(n)+" Seconds to GO ",1000);
        }
        if(pauseTimer==false)
       // n--;
        if(turn== GameBoardTest1.Turn.no_one) {
            if (n == 0) GameFinished();
        }
        else{

        }
        if(n==88) {
            if (tutorial) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_box);
                boolean value=true;
                if(InternetDilaog!=null)if(InternetDilaog.isShowing())
                    value=false;
                if(value)
                dialog.show();
                Button continueTut=(Button)dialog.findViewById(R.id.continuetutorialins1);
                continueTut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        secondTutorial=true;
                        dialog.dismiss();
                    }
                });
                Button stopTut=(Button)dialog.findViewById(R.id.stoptutorialins1);
                stopTut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        tutorial=false;
                        WordGame.tutorialflag=false;
                        secondTutorial=false;
                    }
                });
            }
        }
    }

    public void showDialog2(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_box1);
        dialog.show();
        Button continueTut=(Button)dialog.findViewById(R.id.continuetutorialins2);
        continueTut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                secondTutorial=false;

            }
        });
        Button stopTut=(Button)dialog.findViewById(R.id.stoptutorialins2);
        stopTut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tutorial=false;
                secondTutorial=false;
                WordGame.tutorialflag=false;
            }
        });
    }
public void DialogBox(String Message,int time){
    if(getActivity()==null)return;
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage(Message);
    builder.setCancelable(false);
    builder.setPositiveButton(R.string.ok_label,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // nothing
                }
            });
    final Timer innerTimer=new Timer();
    innerTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            if(mDialog.isShowing())
            mDialog.dismiss();
            innerTimer.cancel();
        }
    },time);
    mDialog = builder.show();
}
    public void Done(){
        //if(secondlevel==false)return;
        if(!internetConnectivity) {
            //AlertDialog builder=new AlertDialog();
            if (InternetDilaog != null)
                if (InternetDilaog.isShowing()) InternetDilaog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Internet not available");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            InternetDilaog = builder.show();
            return;
        }
        if(turnValue!=TurnValue.my_Turn) {
            DialogBox("Not Your Trun", 1000);
            return;
        }
        if(accumulator.length()<3){
            String message="Select atleast THREE letter";
            DialogBox(message,1000);
            return;
        }
        if(dr.verifyInput(accumulator)&& inDetectedWord(accumulator)==false){
            addDetectedWord(accumulator);
            String message="CORRECT!!!\n Click OK ";
            DialogBox(message,2000);
            calculateScore(accumulator);
            for(int large=0;large<9;large++)
            for (int small = 0; small < 9; small++) {
                if (mSmallTiles[large][small].getStatus() == Tile.Status.selected) {
                   // mSmallTiles[large][small].setStatus(Tile.Status.correct);
                    updateAParticularTile(large, small, OnlineTile.Status.correct);
                }else if(mSmallTiles[large][small].getStatus() == Tile.Status.notselected){
                    updateAParticularTile(large, small, OnlineTile.Status.empty);

                }
        }}
        else{
            String message;
            if(inDetectedWord(accumulator)){
                message =accumulator.toUpperCase()+" this word already was used.";
            }else {
                Score1 -= 3;
                message="WRONG WORD " + "\n THREE Point Substracted from Total";
            }
            DialogBox(message,3000);
            for (int large = 0; large < 9; large++) {
                if (mLargeTiles[large].equals(currentLarge)) {
                   // Log.d("fadas","to reset");

                    for (int small = 0; small < 9; small++) {
                        if (mSmallTiles[large][small].getStatus() == Tile.Status.selected)
                     //       mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                            updateAParticularTile(large,small, OnlineTile.Status.notselected);
                           // addAvailable(mSmallTiles[large][small]);
                    }}

                }


            }
            accumulator="";
        if(player.equals(Player.player1))
            reference.child("selected1").setValue(accumulator);
        else{
            reference.child("selected2").setValue(accumulator);
        }
            patternInput="";
        if(player.equals(Player.player1))
            reference.child("word1").setValue(patternInput);
        else{
            reference.child("word2").setValue(patternInput);
        }
            switchTurns();
            updateTextView();
            updateAllTiles();
            return;
        }

    public void updateEntireBlock(int i){

    }
    public void secondLevelInitialze(){
        n=30;
        accumulator="";
        updateTextView();
        DialogBox("LEVEL 2 STARTED ",3000);
        mAvailable.clear();
        for (int i=0;i<9;i++){
        for(int j=0;j<9;j++){
            Tile t1=mSmallTiles[i][j];
            if(t1.getStatus()==Tile.Status.correct){
                //Log.d("iniside","the availabe ");
                t1.setStatus(Tile.Status.notselected);
                mAvailable.add(t1);

            }else{
                t1.setStatus(Tile.Status.empty);
            }

        }

        }

        updateAllTiles();

        patternInput="";
        accumulator="";
        if(player.equals(Player.player1))
            reference.child("selected1").setValue(accumulator);
        else{
            reference.child("selected2").setValue(accumulator);
        }
        patternInput="";
        if(player.equals(Player.player1))
            reference.child("word1").setValue(patternInput);
        else{
            reference.child("word2").setValue(patternInput);
        }
       // updateTextView();
        firstLevel=false;
        if(countFinshed()){
           // Log.d("Never executed","Dont Know y");
            GameFinished();
        }
    }
    public void initGame() {
        Log.d("UT3", "init game");
        //Log.d("Called","1");
        accumulator="";
        patternInput="";
        accumulator="";

        if(musicValue)((GameActivity)getActivity()).startMusic();
        else ((GameActivity)getActivity()).stopMusic();
        mEntireBoard = new Tile(this);
        //((GameActivity)(GameActivity)getActivity()).startMusic();
       // getActivity().
        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }

        runnable=new Runnable(){

            @Override
            public void run() {
                //Toast.makeText(getActivity(),"Timer"+String.valueOf(n),Toast.LENGTH_SHORT).show();
                if(turn== GameBoardTest1.Turn.no_one) {
                    if (player != null) if (player.equals(Player.player1))
                        if (timer != null) {
                            if (n > -1)
                                if (internetConnectivity) if (otherplayer)
                                    timer.setValue(--n);


                        } else {
                            handler.removeCallbacks(runnable);
                            return;
                        }
                    if(n!=0)
                        handler.postDelayed(this,1000);
                }else{
                    if (turn == GameBoardTest1.Turn.player1)
                        if (player.equals(Player.player1)) {
                            if (internetConnectivity)
                                timer.setValue(--n);
                            if(n<0)switchTurns();
                        }
                    if(turn== GameBoardTest1.Turn.player2)
                        if(player.equals(Player.player2)){
                            if (internetConnectivity)
                                timer.setValue(--n);
                            if(n<0)switchTurns();
                        }
                       // updateTime();
                    handler.postDelayed(this,1000);
                }

            }
        };
        handler.postDelayed(runnable,1000);

        mEntireBoard.setSubTiles(mLargeTiles);

        // If the player moves first, set which spots are available
        mLastSmall = -1;
        mLastLarge = -1;
        if(Flag)return;
       setAvailableFromLastMove(mLastSmall);

    }
    private void setAvailableFromLastMove(int large) {
        clearAvailable();
        // Make all the tiles at the destination available
//        OnlineTile Ltile=mLargeTiles[large];
  //      Ltile.setStatus(OnlineTile.Status.selected);
        if (large != -1) {
            for (int dest = 0; dest < 9; dest++) {
                Tile tile = mSmallTiles[large][dest];
                if (tile.getStatus() == Tile.Status.notselected)
                    addAvailable(tile);
            }
        }
        // If there were none available, make all squares available
        if (mAvailable.isEmpty()) {
            if(large!=-1)
            mLargeUsed.add(mLargeTiles[large]);

            setAllAvailable();
        }
    }
    public ArrayList<String> randomWord(){
        while(flagRandom)
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        flagRandom=true;
        Random random=new Random();
        int a= words9long.size();

        ArrayList<Integer> integers=new ArrayList<>(9);
        for (int i=0;i<9;i++) {

            while (true) {
                int j;
                j=random.nextInt(a-1);

                if (integers.contains(j) == false) {
                    integers.add(j);
                    break;
                }
            }
        }
//        Log.d("integers=",integers.toString());
        ArrayList<String>word=new ArrayList<>();
        for (int i=0;i<9;i++){
            while(DictionaryLoader.words9long.get(integers.get(i))==null)integers.set(i,random.nextInt(a));
            word.add(DictionaryLoader.words9long.get(integers.get(i)));
        }
        flagRandom=false;
        return word;

    }

    private void setAllAvailable() {
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile tile = mSmallTiles[large][small];
                    addAvailable(tile);
            }
        }
    }


    private void updateAllTiles() {

        mEntireBoard.updateDrawableState();
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large].updateDrawableState();
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small].updateDrawableState();
            }
        }
    }

    /** Create a string containing the state of the game. */
    public String getState() {
        if(reference!=null)if(referenceValue!=null)
        reference.removeEventListener(referenceValue);
        if(timer!=null)if(timerValue!=null)
        timer.removeEventListener(timerValue);
        if(tilesReferenceValue!=null)tilesReference.removeEventListener(tilesReferenceValue);
        if(referenceScoreValue!=null)referenceScore.removeEventListener(referenceScoreValue);
        if(interenetConnectivityValue!=null){
            internetConnectivityReference.removeEventListener(interenetConnectivityValue);
        }
        if(playerTurn!=null)playerTurn.removeEventListener(playerTurnValue);
        internetHandler.removeCallbacks(internetThread);
        if(connectedValue!=null)connected.removeEventListener(connectedValue);
        StringBuilder builder = new StringBuilder();

        builder.append(mLastLarge);
        builder.append(',');
        builder.append(mLastSmall);
        builder.append(',');
        handler.removeCallbacks(runnable);
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getStatus().name());
                builder.append(',');
                builder.append(mSmallTiles[large][small].str);
                builder.append(',');
            }
        }
        //Log.d("d=",String.valueOf(detectedWord.size()));
        builder.append(detectedWord.size());
        builder.append(',');
        for(int j=0;j<detectedWord.size();j++){
            builder.append(detectedWord.toArray()[j]);
            builder.append(',');
        }
        builder.append(musicValue);
        builder.append(',');
        builder.append(n);
        builder.append(',');
        builder.append(firstLevel);
        builder.append(',');
        builder.append(secondlevel);
        builder.append(',');
        builder.append(Score1);
        builder.append(',');
        builder.append(accumulator);
        builder.append(',');
        builder.append(patternInput);
        builder.append(',');
        return builder.toString();
    }

    private void setTheGame() {
       // updateAllTiles();
       // Log.d("inside the set the Game","YIpee");
        currentLarge=null;
        mLargeUsed.clear();
       // Toast.makeText(getActivity(),"Yipee="+String.valueOf(ToastCount++),Toast.LENGTH_SHORT).show();
        int currentInt=-1;
        mAvailable.clear();
        int gameCount=0;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Tile.Status str=mSmallTiles[i][j].getStatus();
             //   Log.d("Status",str.toString());
                switch(str){
                    case selected :
                        currentLarge=mLargeTiles[i];
                        currentInt=i;
                        gameCount++;
                        //setAvailableFromLastMove1(i);
                        break;
                    case notselected:
                        gameCount++;
                        //mAvailable.add(mSmallTiles[i][j]);
                        //currentLarge=mLargeTiles[i];
                        break;
                    case intermediate:
                        break;
                    case empty:
                        if(mLargeUsed.contains(mLargeTiles[i])==false)
                        mLargeUsed.add(mLargeTiles[i]);
                        break;
                    case oppositePlayer:
                        remove(i);
                        gameCount++;
                            break;
                    case correct:
                        if(mLargeUsed.contains(mLargeTiles[i])==false)
                            mLargeUsed.add(mLargeTiles[i]);
                        break;


                }

            }
            }
        setAvailableFromLastMove1(currentInt);
            updateAllTiles();
        if(gameCount==0)GameFinished();
        if(!GameRunning)mAvailable.clear();
        }
        public void setAvailableFromLastMove1(int large){
            if (large != -1) {
                for (int dest = 0; dest < 9; dest++) {
                    Tile tile = mSmallTiles[large][dest];
                    if (tile.getStatus() == Tile.Status.notselected)
                        addAvailable(tile);
                }
                for (int big=0;big<9;big++){
                    if(big==large)continue;
                    if(mLargeUsed.contains(mLargeTiles[big]))continue;
                    for (int i=0;i<9;i++){
                        mSmallTiles[big][i].setStatus(Tile.Status.intermediate);
                    }

                }
            }
            else{
                for(int big=0;big<9;big++)
                    for(int i=0;i<9;i++){
                        if(mSmallTiles[big][i].getStatus().equals(Tile.Status.notselected))
                            if(!mAvailable.contains(mSmallTiles[big][i]))mAvailable.add(mSmallTiles[big][i]);
                    }
            }
    }

    public void remove(int i){
        if(mLargeUsed.contains(mLargeTiles[i])==false)
            mLargeUsed.add(mLargeTiles[i]);
        for(int j=0;j<9;j++){
            if(mAvailable.contains(mSmallTiles))
                mAvailable.remove(mSmallTiles[i][j]);
            if(mSmallTiles[i][j].getStatus().equals(Tile.Status.notselected))mSmallTiles[i][j].setStatus(Tile.Status.intermediate);
        }

    }


    public ArrayList<Integer> computeint(){
        ArrayList<Integer> intArray=new ArrayList<>(9);
        Random random=new Random();
        for (int i=0;i<9;i++){
            int k;
            if(i==0){
                k=random.nextInt(9);
                intArray.add(k);
                continue;
            }

            while(true){
                k=random.nextInt(9);
                if(intArray.contains(k)==false &&findAdj(intArray.get(i-1),k)){
                    intArray.add(k);
                    break;
                }

            }
        }
        return intArray;
    }
    public boolean findAdj(int i,int j){
      //  Log.d(String.valueOf(i),String.valueOf(j));
        switch(i){
            case 0:
                if(j==1||j==3||j==4)
                    return true;
                else
                return false;
            case 1:
                if(j==6||j==7||j==8)
                    return false;
                else
                    return true;
            case 2:
                if(j==1||j==4||j==5)
                    return true;
                else
                    return false;
            case 3:
                if(j==2||j==5||j==8)
                    return false;
                else
                    return true;
            case 4:
                return true;
            case 5:
                if(j==0||j==3||j==6)
                    return false;
                else
                    return true;
            case 6:
                if(j==3||j==4||j==7)
                    return true;
                else
                    return false;
            case 7:
                if(j==0||j==1||j==2)
                    return false;
                else
                    return true;
            case 8:
                if(j==7||j==4||j==5)
                    return true;
                else
                    return false;
        }
        return false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
        put the online data into game
         */
    public void putOnlineData(final GameBoardTest1 gameBoardTest1){
        reference=FirebaseDatabase.getInstance().getReference("GameBoard").child(gameBoardTest1.getPlayer1());
        timer=FirebaseDatabase.getInstance().getReference("GameBoard").child(gameBoardTest1.getPlayer1()).child("timer");
        String token = FirebaseInstanceId.getInstance().getToken();
        if(gameBoardTest1.getPlayer1().equalsIgnoreCase(token))player=Player.player1;
        else player=Player.player2;
        //DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        turn=gameBoardTest1.getTurn();
        //if()
        if(turn!= GameBoardTest1.Turn.no_one)
        {
            if(player.equals(Player.player1))
                if(turn.equals(GameBoardTest1.Turn.player1))
                    turnValue=TurnValue.my_Turn;
                else turnValue=TurnValue.Other_Player;

            else
                if(player.equals(Player.player2))
                    if(turn.equals(GameBoardTest1.Turn.player2))turnValue=TurnValue.my_Turn;
                            else
                                turnValue=TurnValue.Other_Player;


        }

        playerTurn=reference.child("turn");
        playerTurnValue=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                turn=dataSnapshot.getValue(GameBoardTest1.Turn.class);
                if(turn!= GameBoardTest1.Turn.no_one)
                if(player.equals(Player.player1))
                    if(turn.equals(GameBoardTest1.Turn.player1)){
                        turnValue=TurnValue.my_Turn;}
                    else
                        turnValue=TurnValue.Other_Player;
                else
                if(player.equals(Player.player2))
                    if(turn.equals(GameBoardTest1.Turn.player2))turnValue=TurnValue.my_Turn;
                    else
                        turnValue=TurnValue.Other_Player;
                if(turnValue==TurnValue.my_Turn){
                    DialogBox("Your Turn",1000);

                }
                else{
                    DialogBox("Opposite Player Turn",1000);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        playerTurn.addValueEventListener(playerTurnValue);
        timer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer time=dataSnapshot.getValue(Integer.class);
                if(time!=null){
                   // Toast.makeText(getActivity(),"inside timer",Toast.LENGTH_LONG);
                    n=time;
                    timerValue=new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int n1=dataSnapshot.getValue(Integer.class);
                            if(turn== GameBoardTest1.Turn.no_one){
                            if(player==Player.player2) {
                                n = n1;
                                updateTime();
                            }
                            else updateTime();}
                                else if (turn== GameBoardTest1.Turn.player1)
                                {
                                    if(player==Player.player2) {
                                        n = n1;
                                        updateTime();
                                    }
                                    else{
                                        updateTime();
                                    }

                                }
                                else {
                                    if(player==Player.player1){
                                        n=n1;
                                        updateTime();
                                    }
                                    else{
                                        updateTime();
                                    }

                                }
                            }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    timer.addValueEventListener(timerValue);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        connected=FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedValue=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                internetConnectivity = dataSnapshot.getValue(Boolean.class);
                if (internetConnectivity) {
                    if (InternetDilaog != null)
                        if (InternetDilaog.isShowing()) InternetDilaog.dismiss();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Connection to internet failed");
                    if (InternetDilaog != null) if (InternetDilaog.isShowing()) {
                        InternetDilaog.dismiss();

                    }
                    InternetDilaog = builder.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        connected.addValueEventListener(connectedValue);
        player1=gameBoardTest1.getPlayer1();
        player2=gameBoardTest1.getPlayer2();
        if(player==Player.player1) {
            Score1 = gameBoardTest1.getScores().get(player1);
            patternInput=gameBoardTest1.getSelected1();
            accumulator=gameBoardTest1.getWord1();
            oppositenumber=gameBoardTest1.getActive2();
            count=0;
            selfnumber=gameBoardTest1.getActive1();
            referenceScore=reference.child("scores").child(player2);

        }

        else {
            Score1 = gameBoardTest1.getScores().get(player2);
            patternInput=gameBoardTest1.getSelected2();
            accumulator=gameBoardTest1.getWord2();
            oppositenumber=gameBoardTest1.getActive1();
            count=0;
            selfnumber=gameBoardTest1.getActive2();
            referenceScore=reference.child("scores").child(player1);
        }
        referenceScoreValue=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            Score2=dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        referenceScore.addValueEventListener(referenceScoreValue);
        if(player.equals(Player.player1)){
            internetConnectivityReference=reference.child("active2");
        }
        else{
            internetConnectivityReference=reference.child("active1");
        }
        interenetConnectivityValue=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             oppositenumber=dataSnapshot.getValue(Integer.class);
                if(oppositenumber!=null){
                    //count++;
                    count=0;
                    otherplayer=true;
                    if(InternetDilaog!=null)if(InternetDilaog.isShowing()){
                      InternetDilaog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        String playerUser;
        if(player.equals(Player.player1)){
            playerUser=player1;
        }
        else{
            playerUser=player2;
        }
        DatabaseReference userReference=FirebaseDatabase.getInstance().getReference("Users").child(playerUser);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        internetThread=new Thread(){
            @Override
            public void run() {
                super.run();
                if(player.equals(Player.player1)){
                    if(internetConnectivity)
                    reference.child("active1").setValue(selfnumber++);
                }
                else{
                    if(internetConnectivity)
                    reference.child("active2").setValue(selfnumber++);
                }
                count++;
                if(internetConnectivity)
                if(count>2){
                    if(turn== GameBoardTest1.Turn.no_one||turnValue!=TurnValue.my_Turn)
                    if(InternetDilaog==null){
                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setMessage("Opposite Player offline");
                        builder.setPositiveButton("Send notification to opposide player", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(){
                                  public void run(){
                                      pushNotification();
                                  }
                                }.start();
                            }
                        });
                        builder.setNegativeButton("Go to Home",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog,int which){
                                getActivity().finish();
                            }
                        });
                        new Thread(){
                            public void run(){
                                pushNotification();
                            }
                        }.start();
                        InternetDilaog=builder.show();
                        otherplayer=false;
                    }
                    else{
                        if(turn== GameBoardTest1.Turn.no_one||turnValue!=TurnValue.my_Turn)
                        if(!InternetDilaog.isShowing()){
                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setMessage("Opposite Player offline");
                            builder.setPositiveButton("Send notification to other player", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(){
                                        public void run(){
                                            pushNotification();
                                        }
                                    }.start();
                                }
                            });
                            builder.setNegativeButton("Go to Home",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog,int which){
                                    getActivity().finish();
                                }
                            });
                            new Thread(){
                                public void run(){
                                    pushNotification();
                                }
                            }.start();
                            InternetDilaog=builder.show();
                            otherplayer=false;
                        }
                    }


                }
                internetHandler.postDelayed(this,1000);
            }

        };
        internetHandler.postDelayed(internetThread,1000);
        internetConnectivityReference.addValueEventListener(interenetConnectivityValue);
       // Toast.makeText(getActivity(),player.toString(),Toast.LENGTH_LONG).show();
// final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("GameBoard").child(gameBoardTest1.getPlayer1()).child("tiles1");
        //setTheGame();
        tiles();


    }

    public void tiles() {

        tilesReference=reference.child("tiles");
        tilesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tiles tiles2 = dataSnapshot.getValue(Tiles.class);
                tiles=tiles2;
                onlineStae(tiles2.getTiles1(), 0);
                onlineStae(tiles2.getTiles2(), 1);
                onlineStae(tiles2.getTiles3(), 2);
                onlineStae(tiles2.getTiles4(), 3);
                onlineStae(tiles2.getTiles5(), 4);
                onlineStae(tiles2.getTiles6(), 5);
                onlineStae(tiles2.getTiles7(), 6);
                onlineStae(tiles2.getTiles8(), 7);
                onlineStae(tiles2.getTiles9(), 8);
                setTheGame();

                updateAllTiles();
                updateTextView();
                tilesReferenceValue=new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Toast.makeText(getActivity(),"inside value event listner",Toast.LENGTH_LONG).show();
                        //Toast.makeText(getActivity(),"after value event listner",Toast.LENGTH_LONG).show();
                        Tiles tiles2 = dataSnapshot.getValue(Tiles.class);
                        tiles=tiles2;
                        onlineStae(tiles2.getTiles1(), 0);
                        onlineStae(tiles2.getTiles2(), 1);
                        onlineStae(tiles2.getTiles3(), 2);
                        onlineStae(tiles2.getTiles4(), 3);
                        onlineStae(tiles2.getTiles5(), 4);
                        onlineStae(tiles2.getTiles6(), 5);
                        onlineStae(tiles2.getTiles7(), 6);
                        onlineStae(tiles2.getTiles8(), 7);
                        onlineStae(tiles2.getTiles9(), 8);
                        setTheGame();

                        updateAllTiles();
                        updateTextView();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                tilesReference.addValueEventListener(tilesReferenceValue);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }






    private void onlineStae(ArrayList<OnlineTile> tile,final int large){
        for(int i=0;i<9;i++){
            OnlineTile.Status status=tile.get(i).getStatus();
            if(player.equals(Player.player2)){
                if(status.equals(OnlineTile.Status.selected))status= OnlineTile.Status.oppositePlayer;
                else if(status.equals(OnlineTile.Status.oppositePlayer))status= OnlineTile.Status.selected;
            }

            mSmallTiles[large][i].setStatus(Tile.Status.valueOf(status.toString()));
            mSmallTiles[large][i].setStr(tile.get(i).getStr());
            mSmallTiles[large][i].updateDrawableState();
        }
    }

    /** Restore the state of the game from the given string. */
    public void putState(String gameData) {
        String[] fields = gameData.split(",");
       // handler.removeCallbacks(runnable);
        int index = 0;
        mLastLarge = Integer.parseInt(fields[index++]);
        mLastSmall = Integer.parseInt(fields[index++]);
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile.Status owner = Tile.Status.valueOf(fields[index++]);
                mSmallTiles[large][small].setStatus(owner);
                mSmallTiles[large][small].setStr(fields[index++]);
            }
        }
        int k=Integer.parseInt(fields[index++]);
        //Log.d("K=",String.valueOf(k));
        for(int j=0;j<k;j++){
            detectedWord.add(fields[index++]);
        }
        musicValue=Boolean.valueOf(fields[index++]);
        n=Integer.parseInt(fields[index++]);
        firstLevel=Boolean.parseBoolean(fields[index++]);
        secondlevel=Boolean.parseBoolean(fields[index++]);
        Score1=Integer.parseInt(fields[index++]);

        try {
            accumulator = fields[index++];
            patternInput=fields[index++];
        }
        catch(ArrayIndexOutOfBoundsException exe){
            accumulator="";
            patternInput="";
        }
        //Log.d(accumulator,"what is in accumulator");
       // Log.d(patternInput,"what is in patter");
        setTheGame();
        Flag=true;
        updateTextView();


    }
}

