package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards;

import java.util.HashMap;


/**
 * Created by Dharak on 3/8/2017.
 */

public class GameBoard {
    public HashMap<String,Integer> scores=new HashMap<>();//this will scores of two player<player,score enared>
    public HashMap<String,String>words=new HashMap<>();//this will store words made by two player<Player,wordmade>
    public String turns;//this will store whose turn it is
    public OnlineTile mSmallTiles[][] = new OnlineTile[9][9];
    public void setScores(HashMap<String, Integer> scores) {
        this.scores = scores;
    }
    public void updateParticularTile(int large,int small,OnlineTile.Status status){
        mSmallTiles[large][small].setStatus(status);

    }
    public GameBoard(){

    }
    public GameBoard(String turns){
        this.turns=turns;
    }
    public void setCharacter(int large,int small,Character c){
        mSmallTiles[large][small].setStr(Character.toString(c));
    }

    public void updateMyScore(String player,int score){
        scores.put(player,score);
    }

    public void setWords(HashMap<String, String> words) {
        this.words = words;
    }


    public void setTurns(String turns) {
        this.turns = turns;
    }

    public void setmSmallTiles(OnlineTile[][] mSmallTiles) {
        this.mSmallTiles = mSmallTiles;
    }

    public HashMap<String, String> getWords() {
        return words;
    }

    public String getTurns() {
        return turns;
    }

    public OnlineTile[][] getmSmallTiles() {
        return mSmallTiles;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }
    public boolean isMyTurn(String player){
        return  player.equalsIgnoreCase(turns);
    }

    //public void setOnlineTile
}
