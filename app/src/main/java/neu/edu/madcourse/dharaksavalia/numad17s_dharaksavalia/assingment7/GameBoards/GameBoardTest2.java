package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dharak on 3/17/2017.
 */

public class GameBoardTest2 {
    private ArrayList<String> words;
    private String player1;
    private String player2;
    private HashMap<String,Integer> scores=new HashMap<>();
    //DatabaseReference ValueScore1;
    //ValueEventListener listenerScore1;
    //DatabaseReference ValueScore2;
    //ValueEventListener listenerScore2;
    //this will scores of two player<player,score enared>
    private Turn turn;
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
    ArrayList<BigTile> bigTiles;
    GameBoardTest2(){

    }
    public GameBoardTest2(ArrayList words,String player1,String player2,String mode){
        this.words=words;
        this.player1=player1;
        this.player2=player2;
        scores.put(player1,0);
        scores.put(player2,0);
        //TileInitize();
        if(mode.equalsIgnoreCase("Asynchronous"))
            turn=Turn.player1;
        else{
            turn=Turn.no_one;
        }
        selected1="";
        selected2="";
        word1="";
        word2="";
        timer=90;

    }

    public static enum Turn{
        player1,player2,no_one
    }
}
