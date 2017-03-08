package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user;

/**
 * Created by Dharak on 3/8/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public String score;


    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String score){
        this.username = username;
        this.score = score;
    }

}