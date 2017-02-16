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
import android.app.Fragment;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

public class GameFragment extends Fragment {
    static private int mLargeIds[] = {R.id.wordlarge1, R.id.wordlarge2, R.id.wordlarge3,
            R.id.wordlarge4, R.id.wordlarge5, R.id.wordlarge6, R.id.wordlarge7, R.id.wordlarge8,
            R.id.wordlarge9,};
    static private int mSmallIds[] = {R.id.wordsmall1, R.id.wordsmall2, R.id.wordsmall3,
            R.id.wordsmall4, R.id.wordsmall5, R.id.wordsmall6, R.id.wordsmall7, R.id.wordsmall8,
            R.id.wordsmall9,};
    TestDictionary dr;
    private AlertDialog mDialog;
    static String accumulator="";
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
    private String [] pattern={"036784512", "036478512", "401367852", "425103678", "748521036", "037852146", "036785214", "214587630", "254103678",
                "043678521", "630124785", "031467852"};
    private void updateTextView(){
        Log.d("accumulator",accumulator);
        txt=(TextView)getActivity().findViewById(R.id.wordTextView);
        txt.setText(accumulator);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        initGame();
        dr=new TestDictionary();
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        mSoundX = mSoundPool.load(getActivity(), R.raw.sergenious_movex, 1);
        mSoundO = mSoundPool.load(getActivity(), R.raw.sergenious_moveo, 1);
        mSoundMiss = mSoundPool.load(getActivity(), R.raw.erkanozan_miss, 1);
        mSoundRewind = mSoundPool.load(getActivity(), R.raw.joanne_rewind, 1);
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
            String str="";
            String str1=""+pattern.charAt(i);
           // Log.d("str1",str1);
            str=""+word.charAt(i);
            result[Character.getNumericValue(pattern.charAt(i))]=word.charAt(i);
        }
        //Log.d("result",result.toString());
        return result;
    }
    private void initViews(View rootView) {
        mEntireBoard.setView(rootView);
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
            char[] str=arrange(pattern[n],word);
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
    private void StringAccumulator(String string){
    accumulator+=string;
        dr.verifyInput(accumulator);
        updateTextView();
    }

    private void makeMove(int large, int small) {
        mLastLarge = large;
        mLastSmall = small;
        currentLarge=mLargeTiles[large];
        Tile smallTile = mSmallTiles[large][small];
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
        setAvailableFromLastMove(large);
        Tile.Owner oldWinner = largeTile.getOwner();
        largeTile.setStatus(Tile.Status.selected);
        Tile.Owner winner = largeTile.findWinner();
        if (winner != oldWinner) {
            largeTile.animate();
            largeTile.setOwner(winner);
        }
        winner = mEntireBoard.findWinner();
        mEntireBoard.setOwner(winner);
        updateAllTiles();
        if (winner != Tile.Owner.NEITHER) {
            ((GameActivity)getActivity()).reportWinner(winner);
        }
    }

    public void restartGame() {
        mSoundPool.play(mSoundRewind, mVolume, mVolume, 1, 0, 1f);
        // ...
        initGame();
        initViews(getView());
        updateAllTiles();
    }
    public void Done(){
        if(accumulator.length()<3){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Please select atleast  three letters to form word");
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // nothing
                        }
                    });
            mDialog = builder.show();
            return;
        }
        if(dr.verifyInput(accumulator)){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("CORRECT!!!\n Click OK ");
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
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("WRONG WORD");
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.ok_label,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // nothing
                        }
                    });
            mDialog = builder.show();
            for (int large = 0; large < 9; large++) {
                if (mLargeTiles[large].equals(currentLarge)) {
                    Log.d("fadas","to reset");
                    for (int small = 0; small < 9; small++) {
                        if (mSmallTiles[large][small].getStatus() == Tile.Status.selected)
                            mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                            addAvailable(mSmallTiles[large][small]);
                    }
                }
                accumulator="";
                updateTextView();
                updateAllTiles();

            }
            return;
        }
        accumulator="";
        clearAvailable();
       // mLargeUsed.add(currentLarge);
        for (int large = 0; large < 9; large++) {
            if (mLargeTiles[large] == currentLarge) {
                for (int small = 0; small < 9; small++) {
                    if (mSmallTiles[large][small].getStatus() == Tile.Status.notselected)
                        mSmallTiles[large][small].setStatus(Tile.Status.empty);
                }
            } else {
                if (mLargeUsed.contains(mLargeTiles[large])) continue;
                for (int small = 0; small < 9; small++) {
                    mSmallTiles[large][small].setStatus(Tile.Status.notselected);
                    mAvailable.add(mSmallTiles[large][small]);
                }
            }
        }
        mLargeUsed.add(currentLarge);
        updateAllTiles();
        updateTextView();
    }
    public void initGame() {
        Log.d("UT3", "init game");
        //Log.d("Called","1");
        accumulator="";
        mEntireBoard = new Tile(this);
        // Create all the tiles
        for (int large = 0; large < 9; large++) {
            mLargeTiles[large] = new Tile(this);
            for (int small = 0; small < 9; small++) {
                mSmallTiles[large][small] = new Tile(this);
            }
            mLargeTiles[large].setSubTiles(mSmallTiles[large]);
        }

        mEntireBoard.setSubTiles(mLargeTiles);

        // If the player moves first, set which spots are available
        mLastSmall = -1;
        mLastLarge = -1;
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
        Random random=new Random();
        int a=DictionaryLoader.words9long.size();
        String[] wordString=new String[a];
        wordString=DictionaryLoader.words9long.keySet().toArray(wordString);

        ArrayList<Integer> integers=new ArrayList<>(9);
        for (int i=0;i<9;i++) {

            while (true) {
                int j=random.nextInt(a);
                if (integers.contains(j) == false) {
                    integers.add(j);
                    break;
                }
            }
        }
        ArrayList<String>word=new ArrayList<>();
        for (int i=0;i<9;i++){

            word.add(wordString[integers.get(i)]);
        }

        return word;

    }

    private void setAllAvailable() {
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                Tile tile = mSmallTiles[large][small];
                if (tile.getOwner() == Tile.Owner.NEITHER)
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
        for (int large = 0; large < 9; large++) {
            for (int small = 0; small < 9; small++) {
                builder.append(mSmallTiles[large][small].getStatus().name());
                builder.append(',');
                builder.append(mSmallTiles[large][small].str);
                builder.append(',');
            }
        }
        return builder.toString();
    }

    private void setTheGame() {

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
        setAvailableFromLastMove(mLastSmall);
        setTheGame();
    }
}

