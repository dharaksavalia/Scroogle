package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user;

/**
 * Created by Dharak on 3/8/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username=null;
    public String score=null;
    public String time=null;
    public String emailID=null;
    public String key=null;
    public String active=null;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)


    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", score='" + score + '\'' +
                ", time='" + time + '\'' +
                ", emailID='" + emailID + '\'' +
                ", key='" + key + '\'' +
                ", active='" + active + '\'' +
                '}';
    }

    public User(String username, String score, String emailID,String time,String key,String active){
        this.username = username;
        this.score = score;
        this.emailID=emailID;
        this.time=time;
        this.key=key;
        this.active=active;
    }

}