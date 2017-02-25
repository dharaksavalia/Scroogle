package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

import static java.lang.Thread.sleep;
import static neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader.words9long;

public class GameFragment extends Fragment {

    static private int mLargeIds[] = {R.id.wordlarge1, R.id.wordlarge2, R.id.wordlarge3,
            R.id.wordlarge4, R.id.wordlarge5, R.id.wordlarge6, R.id.wordlarge7, R.id.wordlarge8,
            R.id.wordlarge9,};
    static private int mSmallIds[] = {R.id.wordsmall1, R.id.wordsmall2, R.id.wordsmall3,
            R.id.wordsmall4, R.id.wordsmall5, R.id.wordsmall6, R.id.wordsmall7, R.id.wordsmall8,
            R.id.wordsmall9,};
    TestDictionary dr;
    boolean flagRandom=false;
    boolean firstMove=true;
    public boolean tutorial=WordGame.tutorialflag;
    int n=90;
    boolean secondTutorial=false;
    public boolean musicValue=true;
    private AlertDialog mDialog;
    static String accumulator="";
    static String patternInput="";
    private Handler mHandler = new Handler();
    private Tile mEntireBoard = new Tile(this);
    private Tile mLargeTiles[] = new Tile[9];
    private Tile mSmallTiles[][] = new Tile[9][9];
    private Tile.Owner mPlayer = Tile.Owner.X;
    private Tile currentLarge;
    private Set<Tile> mAvailable = new HashSet<Tile>();
    private Set<Tile>mLargeUsed=new HashSet<Tile>();
    private int mSoundX, mSoundO, mSoundMiss, mSoundRewind;
    private SoundPool mSoundPool;
    private float mVolume = 1f;
    private int mLastLarge;
    private int mLastSmall;
    TextView txt;
    private int Score=0;
    private boolean firstLevel=true;
    private boolean secondlevel=true;
    TextView ScoreView;
    boolean pauseTimer=false;
     boolean Flag=false;
    private Handler handler=new Handler();
    private Runnable runnable;
    private Set<String>detectedWord=new HashSet<String>();
    int numberCorrectWord=0;
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

        ScoreView.setText("Score  "+String.valueOf(Score));
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
        handler.removeCallbacks(runnable);
        mAvailable.clear();
        builder.setMessage("Game Finished \nYour Score is: "+String.valueOf(Score));
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

    }

    public void calculateScore(String str){
        if(str.length()==9)
            Score+=20;
        for (int i=0;i<str.length();i++){
            Score+=ScoreMap.get(str.charAt(i));
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
    private void initViews(View rootView) {
        mEntireBoard.setView(rootView);
        accumulator="";
        patternInput="";
        clearDetectedWord();
        Log.d("d","hi1");
        Random random=new Random();
        ArrayList<String> words;
        words=randomWord();

        Log.d("words",words.toString());
        for (int large = 0; large < 9; large++) {
            View outer = rootView.findViewById(mLargeIds[large]);
            String word=words.get(large);
            mLargeTiles[large].setView(outer);
            int n=random.nextInt(pattern.length);
            char[] str;
            str=arrange(pattern[n],word);

            for (int small = 0; small < 9; small++) {
                Button inner = (Button) outer.findViewById
                        (mSmallIds[small]);
                final int fLarge = large;
                final int fSmall = small;
                final Tile smallTile = mSmallTiles[large][small];
                smallTile.setView(inner);
                smallTile.setStr(Character.toString(str[small]));
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
                if (mEntireBoard.getOwner() == Tile.Owner.NEITHER) {
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
        Tile.Owner opponent = mPlayer == Tile.Owner.X ? Tile.Owner.O : Tile
                .Owner.X;
        int bestLarge = -1;
        int bestSmall = -1;
        int bestValue = Integer.MAX_VALUE;
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile smallTile = mSmallTiles[large][small];
                if (isAvailable(smallTile)) {
                    // Try the move and get the score
                    Tile newBoard = mEntireBoard.deepCopy();
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

    private void switchTurns() {
       // mPlayer = mPlayer == Tile.Owner.X ? Tile.Owner.O : Tile
                //.Owner.X;
    }
    */
    private void patternAccumulator(int s){
        patternInput=patternInput+ String.valueOf( s);
        //Log.d(String.valueOf(s),patternInput);
        //Log.d(patternInput,":Pattern");
    }
    private void StringAccumulator(String string){
    accumulator+=string;
        dr.verifyInput(accumulator);
        updateTextView();
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
        smallTile.setStatus(Tile.Status.selected);
        if(mAvailable.contains(smallTile)==false)return;
        currentLarge=mLargeTiles[large];
       // Log.d("pattern:",String.valueOf(small));
       // if(first)

        if(firstLevel)
        patternAccumulator(small);
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
        //Tile.Owner oldWinner = largeTile.getOwner();
        if(firstLevel==false)setAavailableforsecond();
        largeTile.setStatus(Tile.Status.selected);
        /*Tile.Owner winner = largeTile.findWinner();
        if (winner != oldWinner) {
            largeTile.animate();
            largeTile.setOwner(winner);
        }
        */
        updateAllTiles();
        if(firstLevel)
        if(patternInput.length()==9)Done();
        /*
        if (winner != Tile.Owner.NEITHER) {
            ((GameActivity)getActivity()).reportWinner(winner);
        }
        */
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
        Score=0;
        initGame();
        this.n=90;
        initViews(getView());
        updateAllTiles();
        updateTextView();
        setAllAvailable();

    }

    private void updateTime() {
        TextView txt=(TextView) getActivity().findViewById(R.id.wordTimer);
        if(secondlevel){
        if(n>-1){
            if(n<10){
                txt.setTextColor(Color.RED);

            }else{
                txt.setTextColor(Color.GREEN);
            }

            txt.setText(String.valueOf(n));

        }
        if(n==10){
            DialogBox( String.valueOf(n)+" Seconds to GO ",100);
        }
        if(pauseTimer==false)
        n--;
        if(n==-1&&firstLevel)secondLevelInitialze();
        if(n==-1&&firstLevel==false)GameFinished();}
        if(n==88) {
            if (tutorial) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_box);
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
        if(secondlevel==false)return;
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
        }
        else{
            String message;
            if(inDetectedWord(accumulator)){
                message =accumulator.toUpperCase()+" this word already was used.";
            }else {
                Score -= 3;
                message="WRONG WORD " + "\n THREE Point Substracted from Total";
            }
            DialogBox(message,3000);
            if(firstLevel)
            for (int large = 0; large < 9; large++) {
                if (mLargeTiles[large].equals(currentLarge)) {
                   // Log.d("fadas","to reset");
                    for (int small = 0; small < 9; small++) {
                        if (mSmallTiles[large][small].getStatus() == Tile.Status.selected)
                            mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                            addAvailable(mSmallTiles[large][small]);
                    }}
                    else{
                    if (mLargeUsed.contains(mLargeTiles[large])) continue;
                    for (int small = 0; small < 9; small++) {
                        mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                        mAvailable.add(mSmallTiles[large][small]);
                    }
                }


            }
            accumulator="";
            patternInput="";
            if(firstLevel==false){
                for(int large=0;large<9;large++){
                    for(int small=0;small<9;small++){
                        Tile.Status st=mSmallTiles[large][small].getStatus();
                                if(st==Tile.Status.notselected||st==Tile.Status.selected){
                                    mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                                    mAvailable.add(mSmallTiles[large][small]);
                                }
                    }
                }
            }
            updateTextView();
            updateAllTiles();
            return;
        }
        accumulator="";
        patternInput="";
        clearAvailable();
       // mLargeUsed.add(currentLarge);
        if(firstLevel)
        for (int large
             = 0; large < 9; large++) {
            if (mLargeTiles[large] == currentLarge) {
                for (int small = 0; small < 9; small++) {
                    if (mSmallTiles[large][small].getStatus() == Tile.Status.notselected)
                        mSmallTiles[large][small].setStatus(Tile.Status.empty);
                    else mSmallTiles[large][small].setStatus(Tile.Status.correct);
                }
            } else {
                if (mLargeUsed.contains(mLargeTiles[large])) continue;
                for (int small = 0; small < 9; small++) {
                    mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                    mAvailable.add(mSmallTiles[large][small]);
                }
            }
        }
        if(firstLevel==false)
            for (int large = 0; large < 9; large++) {
                for (int small = 0; small < 9; small++) {
                    if (mSmallTiles[large][small].getStatus() == Tile.Status.notselected)
                        addAvailable(mSmallTiles[large][small]);
                    else if(mSmallTiles[large][small].getStatus()==Tile.Status.selected)
                        mSmallTiles[large][small].setStatus(Tile.Status.correct);
                }
            }
        mLargeUsed.add(currentLarge);
        updateAllTiles();
        updateTextView();
        if(countFinshed()) {
            if(firstLevel)TimeFinished();
            else if(secondlevel)GameFinished();

        }
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
       // updateTextView();
        firstLevel=false;
        if(countFinshed()){
            Log.d("Never executed","Dont Know y");
            GameFinished();
        }
    }
    public void initGame() {
        Log.d("UT3", "init game");
        //Log.d("Called","1");
        accumulator="";
        patternInput="";
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
                updateTime();
                handler.postDelayed(this,1000);
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
//        Tile Ltile=mLargeTiles[large];
  //      Ltile.setStatus(Tile.Status.selected);
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
        builder.append(Score);
        builder.append(',');
        builder.append(accumulator);
        builder.append(',');
        builder.append(patternInput);
        builder.append(',');
        return builder.toString();
    }

    private void setTheGame() {
        updateAllTiles();

        mAvailable.clear();
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                Tile.Status str=mSmallTiles[i][j].getStatus();
             //   Log.d("Status",str.toString());
                switch(str){
                    case selected :
                        currentLarge=mLargeTiles[i];
                        break;
                    case notselected:
                        mAvailable.add(mSmallTiles[i][j]);
                        currentLarge=mLargeTiles[i];
                        break;
                    case intermediate:
                        break;
                    case empty:
                        if(mLargeUsed.contains(mLargeTiles[i])==false)
                        mLargeUsed.add(mLargeTiles[i]);
                        break;
                    case correct:
                        if(mLargeUsed.contains(mLargeTiles[i])==false)
                            mLargeUsed.add(mLargeTiles[i]);
                        break;


                }

            }
            updateAllTiles();
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
        Score=Integer.parseInt(fields[index++]);

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

