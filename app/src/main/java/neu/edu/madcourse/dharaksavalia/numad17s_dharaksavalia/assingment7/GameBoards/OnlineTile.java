package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5.Tile;

/**
 * Created by Dharak on 3/8/2017.
 */

public class OnlineTile {
    public static enum Status{
        selected,notselected,empty,correct,oppositePlayer
    }
    public OnlineTile(){

    }
    public OnlineTile(Status status,String str){
        this.str=str;
        this.status=status;
    }
    private String str;

    private Status status;
    public void setStr(String str){
        this.str=str.toUpperCase();
    }
    public String getStr(){
        return this.str.toLowerCase();
    }
    public void setStatus(OnlineTile.Status status){this.status=status;}

    public OnlineTile.Status getStatus(){return status;}
}
