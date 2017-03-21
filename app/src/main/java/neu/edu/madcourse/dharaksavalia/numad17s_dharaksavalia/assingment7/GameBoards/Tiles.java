package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards;

import java.util.ArrayList;

/**
 * Created by Dharak on 3/20/2017.
 */

public class Tiles {

    private ArrayList<OnlineTile> Tiles1=new ArrayList<>();
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
    private ArrayList<String> words;

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public ArrayList<OnlineTile> getTiles1() {
        return Tiles1;
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
    public Tiles(){

    }

    public Tiles(ArrayList<String> words) {
        this.words = words;
        TileInitize();
    }


}
