package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards;

import java.util.ArrayList;

/**
 * Created by Dharak on 3/17/2017.
 */

public class BigTile {
    ArrayList<OnlineTile> Tiles=new ArrayList<>();

    public ArrayList<OnlineTile> getTile() {
        return Tiles;
    }

    public void setTiles(ArrayList<OnlineTile> tile) {
        Tiles = tile;
    }

    public BigTile(){

    }
    public BigTile( String str){
        for(int i=0;i<str.length();i++){
            str.charAt(i);
        }

    }
    String str;

    public OnlineTile.Status status= OnlineTile.Status.notselected;
    public void setStr(String str){
        this.str=str.toUpperCase();
    }
    public String getStr(){
        return this.str.toLowerCase();
    }
    public void setStatus(OnlineTile.Status status){this.status=status;}

    public OnlineTile.Status getStatus(){return status;}
}

