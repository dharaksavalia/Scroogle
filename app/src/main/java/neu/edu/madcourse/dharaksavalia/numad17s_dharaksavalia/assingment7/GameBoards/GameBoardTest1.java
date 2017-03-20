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
    //DatabaseReference ValueScore1;
    //ValueEventListener listenerScore1;
    //DatabaseReference ValueScore2;
    //ValueEventListener listenerScore2;
    //this will scores of two player<player,score enared>
    private Turn turn;//this will store whose turn it is
    //DatabaseReference valueTurn;
    //ValueEventListener listenerTurn;
    private ArrayList<OnlineTile>Tiles1=new ArrayList<>();
    //DatabaseReference valueTile1;
    //ValueEventListener listenerTile1;
    private ArrayList<OnlineTile>Tiles2=new ArrayList<>();
    //DatabaseReference valueTile2;
    //ValueEventListener listenerTile2;
    private ArrayList<OnlineTile>Tiles3=new ArrayList<>();
    //baseReference valueTile3;
    //ValueEventListener ListenerTile3;
    private ArrayList<OnlineTile>Tiles4=new ArrayList<>();
    //baseReference valueTile4;
    //ValueEventListener ListenerTile4;
    private ArrayList<OnlineTile>Tiles5=new ArrayList<>();
    //baseReference valueTile5;
    //ValueEventListener ListenerTile5;
    private ArrayList<OnlineTile>Tiles6=new ArrayList<>();
    //baseReference valueTile6;
    //ValueEventListener ListenerTile6;
    private ArrayList<OnlineTile>Tiles7=new ArrayList<>();
    //baseReference valueTile7;
    //ValueEventListener ListenerTile7;
    private ArrayList<OnlineTile>Tiles8=new ArrayList<>();
    //baseReference valueTile8;
    //ValueEventListener ListenerTile8;
    private ArrayList<OnlineTile>Tiles9=new ArrayList<>();
    //baseReference valueTile9;
    //ValueEventListener ListenerTile9;
    private String selected1;
    //baseReference valueselected1;
    //ValueEventListener ListenerSelected2;
    private String selected2;
    //baseReference valueselected2;
    //ValueEventListener Listenerselected2;
    private String word1;
    //baseReference valueword1;
    //ValueEventListener Listenerword1;
    private String word2;
    //baseReference //word2;
   // ValueEventListener Listenerword2;
    private int timer;
    private int active1;
    private int active2;

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

    public static enum Turn{
        player1,player2,no_one
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public GameBoardTest1(int timer, ArrayList<String> words, String player1, String player2, HashMap<String, Integer> scores, Turn turn, ArrayList<OnlineTile> tiles1, ArrayList<OnlineTile> tiles2, ArrayList<OnlineTile> tiles3, ArrayList<OnlineTile> tiles4, ArrayList<OnlineTile> tiles5, ArrayList<OnlineTile> tiles6, ArrayList<OnlineTile> tiles7, ArrayList<OnlineTile> tiles8, ArrayList<OnlineTile> tiles9,int active1,int active2) {
        this.timer = timer;
        this.words = words;
        this.player1 = player1;
        this.player2 = player2;
        this.scores = scores;
        this.turn = turn;
        Tiles1 = tiles1;
        Tiles2 = tiles2;
        Tiles3 = tiles3;
        Tiles4 = tiles4;
        Tiles5 = tiles5;
        Tiles6 = tiles6;
        Tiles7 = tiles7;
        Tiles8 = tiles8;
        Tiles9 = tiles9;
        active1=0;
        active2=0;
    }
    private void setEventListiner(){

    }

    public void setWords(ArrayList<String> words) {
        this.words = words;

    }
    private void TileInitize(){
        TileInitize(words.get(0),Tiles1);
        TileInitize(words.get(1),Tiles2);
        TileInitize(words.get(2),Tiles3);
        TileInitize(words.get(3),Tiles4);
        TileInitize(words.get(4),Tiles5);
        TileInitize(words.get(5),Tiles6);
        TileInitize(words.get(6),Tiles7);
        TileInitize(words.get(7),Tiles8);
        TileInitize(words.get(8),Tiles9);

    }
    private void TileInitize(String word,ArrayList<OnlineTile>Tiles){
        for (int i=0;i<9;i++){
           OnlineTile tile=new OnlineTile(OnlineTile.Status.notselected,Character.toString(word.charAt(i)));
            Tiles.add(tile);
        }
    }

    public ArrayList<OnlineTile> getTiles1() {
        return Tiles1;
    }

    public void setTiles1(ArrayList<OnlineTile> tiles1) {
        Tiles1 = tiles1;
    }

    public ArrayList<OnlineTile> getTiles2() {
        return Tiles2;
    }

    public void setTiles2(ArrayList<OnlineTile> tiles2) {
        Tiles2 = tiles2;
    }

    public ArrayList<OnlineTile> getTiles3() {
        return Tiles3;
    }

    public void setTiles3(ArrayList<OnlineTile> tiles3) {
        Tiles3 = tiles3;
    }

    public ArrayList<OnlineTile> getTiles7() {
        return Tiles7;
    }

    public void setTiles7(ArrayList<OnlineTile> tiles7) {
        Tiles7 = tiles7;
    }

    public ArrayList<OnlineTile> getTiles6() {
        return Tiles6;
    }

    public void setTiles6(ArrayList<OnlineTile> tiles6) {
        Tiles6 = tiles6;
    }

    public ArrayList<OnlineTile> getTiles5() {
        return Tiles5;
    }

    public void setTiles5(ArrayList<OnlineTile> tiles5) {
        Tiles5 = tiles5;
    }

    public ArrayList<OnlineTile> getTiles8() {
        return Tiles8;
    }

    public void setTiles8(ArrayList<OnlineTile> tiles8) {
        Tiles8 = tiles8;
    }

    public ArrayList<OnlineTile> getTiles9() {
        return Tiles9;
    }

    public void setTiles9(ArrayList<OnlineTile> tiles9) {
        Tiles9 = tiles9;
    }

    public ArrayList<OnlineTile> getTiles4() {
        return Tiles4;
    }

    public void setTiles4(ArrayList<OnlineTile> tiles4) {
        Tiles4 = tiles4;
    }

    public GameBoardTest1(ArrayList<String> words, int timer, String player1, String player2, HashMap<String, Integer> scores, Turn turn, ArrayList<OnlineTile> tiles1, ArrayList<OnlineTile> tiles2, ArrayList<OnlineTile> tiles3, ArrayList<OnlineTile> tiles4, ArrayList<OnlineTile> tiles5, ArrayList<OnlineTile> tiles6, ArrayList<OnlineTile> tiles7, ArrayList<OnlineTile> tiles8, ArrayList<OnlineTile> tiles9, String selected1, String selected2, String word1, String word2) {
        this.words = words;
        this.timer = timer;
        this.player1 = player1;
        this.player2 = player2;
        this.scores = scores;
        this.turn = turn;
        Tiles1 = tiles1;
        Tiles2 = tiles2;
        Tiles3 = tiles3;
        Tiles4 = tiles4;
        Tiles5 = tiles5;
        Tiles6 = tiles6;
        Tiles7 = tiles7;
        Tiles8 = tiles8;
        Tiles9 = tiles9;
        this.selected1 = selected1;
        this.selected2 = selected2;
        this.word1 = word1;
        this.word2 = word2;
     setValueListeners();
    }
    private void setValueListeners(){

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
    public GameBoardTest1(ArrayList words,String player1,String player2,String mode){
        this.words=words;
        this.player1=player1;
        this.player2=player2;
        scores.put(player1,0);
        scores.put(player2,0);
        TileInitize();
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

    }
}
