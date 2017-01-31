package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2;

/**
 * Created by Dharak on 1/30/2017.
 */

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;




public class Encoder {
    HashMap<Character,Integer> encoder=new HashMap<Character,Integer>();
    File IntFile;
    DataOutputStream dataOutInt;
    File LongFile;
    DataOutputStream dataOutLong;
    File LongFile2;
    DataOutputStream dataOutLong2;
    File wordFile;
    BufferedWriter dataOutWord;
    public Encoder(){
        String aphabet="abcdefghijklmnopqrstuvwxyz"+'\r';
        for(int i=0;i<aphabet.length();i++){
            encoder.put(aphabet.charAt(i),i+1);
        }
        //System.out.println(encoder);
        try {
            IntFile=new File("intFile");
            LongFile=new File("longFile");
            LongFile2=new File("longFile2");
            wordFile=new File("wordFile.txt");
            IntFile.createNewFile();
            LongFile.createNewFile();
            LongFile2.createNewFile();
            wordFile.createNewFile();
            dataOutInt=new DataOutputStream(new FileOutputStream(IntFile,true));
            dataOutLong=new DataOutputStream(new FileOutputStream(LongFile,true));
            dataOutLong2=new DataOutputStream(new FileOutputStream(LongFile2,true));
            dataOutWord=new BufferedWriter(new FileWriter(wordFile,true));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("file already exit");			}
    }

    public void encodeWord(String word)
    {
	/*
	 * Encodes words THree to six letter words can encode into integer and save according integer
	 * and above six to 12 can be save as an Double data type
	 * and 13 we are not doing anything since it contains only 10 % words only.
	 * This function basically words in length
	 */
        //System.out.println(word.length());
        int lengthOfWord=word.length();
        if(3<=lengthOfWord && lengthOfWord<7){
            int intResult=encodeThreeToSixWord(word);
            save(intResult);
        }else if(7<=lengthOfWord&&lengthOfWord<13){
            //System.out.println("in case 1");
            long longResult=encodeSevenToTwelve(word);
            save(longResult);		}
        else if(13<=lengthOfWord&&lengthOfWord<24){
            long longResult1=encodeSevenToTwelve(word.substring(0, 11));
            long longResult2=encodeSevenToTwelve(word.substring(12));
            save(longResult1,longResult2);
            //System.out.println(longResult1+longResult2);
        }
        else if(24<=lengthOfWord){
            //System.out.println(word);
            save(word);
        }

    }
    private int encodeThreeToSixWord(String Word){
		/*
		 *
		 */
        int result=0;
        for (int i=0;i<Word.length();i++){
            if (i==0)result=encoder.get(Word.charAt(0));
            else {
                //result1=result1*32;S
                //System.out.println(result1);
                result=result*32+encoder.get(Word.charAt(i));
                //System.out.println(result);
            }
            //converting char to 5 bit multiplying with 32 to shift forward
        }

        return result;
    }
    public void save(int result){
        try {
            dataOutInt.writeInt(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void save(long result){
        try {
            dataOutLong.writeLong(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void save(long result1,long result2){
        try {
            dataOutLong2.writeLong(result1);
            dataOutLong2.writeLong(result2);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void save(String result){
        try {
            //	System.out.println("inside write="+result);
            dataOutWord.write(result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private long encodeSevenToTwelve(String Word){
        long result=0;
        //long result1=1;
        for (int i=0;i<Word.length();i++){
            if (i==0)result=encoder.get(Word.charAt(0));
            else {
                //result1=result1*32;
                //System.out.println(result1);
                result=result*32+encoder.get(Word.charAt(i));
                //System.out.println(result);
            }
            //converting char to 5 bit multiplying with 32 to shift forward
        }
        return result;
    }
    private void encodeTwelveAbove(String Word){
        // for future development if i get any good techniques for words above 12 in length
    }
    protected void finalize(){
        try {
            dataOutInt.close();


            dataOutLong.close();
            dataOutWord.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}