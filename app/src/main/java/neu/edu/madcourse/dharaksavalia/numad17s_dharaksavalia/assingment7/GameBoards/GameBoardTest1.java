package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dharak on 3/16/2017.
 */

public class GameBoardTest1  {
    private ArrayList<String> words;
    private String player1;
    private String player2;
    private HashMap<String,Integer> scores=new HashMap<>();
    private MyMessage myMessage;
    //DatabaseReference ValueScore1;
    //ValueEventListener listenerScore1;
    //DatabaseReference ValueScore2;
    //ValueEventListener listenerScore2;
    //this will scores of two player<player,score enared>
    private Turn turn;//this will store whose turn it is
    //DatabaseReference valueTurn;
    //ValueEventListener listenerTurn;
    //baseReference valueTile9;
    //ValueEventListener ListenerTile9;
    private String selected1;
    //baseReference valueselected1;
    //ValueEventListener ListenerSelected2;
    private String selected2;
    //baseReference valueselected2;
    //ValueEventListener Listenerselected2;
    private String word1;

    public MyMessage getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(MyMessage myMessage) {
        this.myMessage = myMessage;
    }

    //baseReference valueword1;
    //ValueEventListener Listenerword1;
    private String word2;
    //baseReference //word2;
   // ValueEventListener Listenerword2;
    private int timer;
    private int active1;
    private int active2;
    private Tiles tiles;

    public int getActive1() {
        return active1;
    }

    public void setActive1(int active1) {
        this.active1 = active1;
    }

    public int getActive2() {
        return active2;
    }

    public void setActive2(int active2) {
        this.active2 = active2;
    }

    public Tiles getTiles() {
        return tiles;
    }

    public void setTiles(Tiles tiles) {
        this.tiles = tiles;
    }

    public static enum Turn{
        player1,player2,no_one
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public GameBoardTest1(ArrayList<String> words, String player1, String player2, HashMap<String, Integer> scores, Turn turn, String selected1, String selected2, String word1, String word2, int timer, int active1, int active2, Tiles tiles,MyMessage myMessage) {
        this.words = words;
        this.player1 = player1;
        this.player2 = player2;
        this.scores = scores;
        this.turn = turn;
        this.selected1 = selected1;
        this.selected2 = selected2;
        this.word1 = word1;
        this.word2 = word2;
        this.timer = timer;
        this.active1 = active1;
        this.active2 = active2;
        this.tiles = tiles;
        this.myMessage=myMessage;
    }

    private void setEventListiner(){

    }

    public void setWords(ArrayList<String> words) {
        this.words = words;

    }




    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, Integer> scores) {
        this.scores = scores;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public String getSelected1() {
        return selected1;
    }

    public void setSelected1(String selected1) {
        this.selected1 = selected1;
    }

    public String getSelected2() {
        return selected2;
    }

    public void setSelected2(String selected2) {
        this.selected2 = selected2;
    }

    public String getWord1() {
        return word1;
    }

    public void setWord1(String word1) {
        this.word1 = word1;
    }

    public String getWord2() {
        return word2;
    }

    public void setWord2(String word2) {
        this.word2 = word2;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public GameBoardTest1(){

    }
    public GameBoardTest1(ArrayList<String> words,String player1,String player2,String mode){
        this.words=words;
        this.player1=player1;
        this.player2=player2;
        scores.put(player1,0);
        scores.put(player2,0);
        if(mode.equalsIgnoreCase("Asynchronous")) {
            turn = Turn.player1;
            timer=10;
        }
        else{
            turn=Turn.no_one;
            timer=90;
        }
        selected1="";
        selected2="";
        word1="";
        word2="";
        //timer=90;
        active1=0;
        active2=0;
        tiles=new Tiles(words);
        this.myMessage=new MyMessage();

    }
}
