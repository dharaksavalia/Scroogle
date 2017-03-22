package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment8;

import java.util.Comparator;

/**
 * Created by Dharak on 3/21/2017.
 */

public class Scores implements Comparable<Scores>  {
    private String UserName;
    private String Country;


    private String Time;
    private Integer Score;
    @Override
    public int compareTo(Scores scores) {
        return scores.getScore()-Score;
    }
    public Scores(){

    }
    public Scores(String userName, String country, String time, Integer score) {
        UserName = userName;
        Country = country;
        Time = time;
        Score = score;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "UserName='" + UserName + '\'' +
                ", Country='" + Country + '\'' +
                ", Time='" + Time + '\'' +
                ", Score=" + Score +
                '}';
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }
}
