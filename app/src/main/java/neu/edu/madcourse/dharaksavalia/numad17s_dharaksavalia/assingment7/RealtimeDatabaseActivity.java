package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7;

import android.app.Activity;

/**
 * Created by Dharak on 3/8/2017.
 */

    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.RadioButton;
    import android.widget.TextView;

    import com.google.firebase.database.ChildEventListener;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.MutableData;
    import com.google.firebase.database.Transaction;
    import com.google.firebase.database.ValueEventListener;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user.User;

    public class RealtimeDatabaseActivity extends Activity {

        private static final String TAG = RealtimeDatabaseActivity.class.getSimpleName();

        private DatabaseReference mDatabase;
        private DatabaseReference mdata;
        private TextView userName;
        private TextView score;
        private TextView userName2;
        private TextView score2;
        private RadioButton player1;
        private Button add5;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_realtime_database);

            userName = (TextView) findViewById(R.id.username);
            score = (TextView) findViewById(R.id.score);
            userName2 = (TextView) findViewById(R.id.username2);
            score2 = (TextView) findViewById(R.id.score2);

            player1 = (RadioButton)findViewById(R.id.player1);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mdata=FirebaseDatabase.getInstance().getReference("Message");
            mdata.setValue("YIPEE");
            User f1=new User();
            f1.username="kal";
            f1.score="100";
            mdata=FirebaseDatabase.getInstance().getReference("Message2");
            String key=mdata.push().getKey();
            mdata.child(key).setValue(f1);
            mdata.push().setValue(f1);

            add5 = (Button)findViewById(R.id.add5);
            add5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RealtimeDatabaseActivity.this.onAddScore(mDatabase, player1.isChecked() ? "user1" : "user2");
                }
            });

            mDatabase.child("users").addChildEventListener(
                    new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            User user = dataSnapshot.getValue(User.class);

                            if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                                score.setText(user.score);
                                userName.setText(user.username);
                            } else {
                                score2.setText(String.valueOf(user.score));
                                userName2.setText(user.username);
                            }
                            Log.e(TAG, "onChildAdded: dataSnapshot = " + dataSnapshot.getValue());
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            User user = dataSnapshot.getValue(User.class);

                            if (dataSnapshot.getKey().equalsIgnoreCase("user1")) {
                                score.setText(user.score);
                                userName.setText(user.username);
                            } else {
                                score2.setText(String.valueOf(user.score));
                                userName2.setText(user.username);
                            }
                            Log.v(TAG, "onChildChanged: "+dataSnapshot.getValue().toString());
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "onCancelled:" + databaseError);
                        }
                    }
            );
        }

        /**
         * Called on score add
         * @param postRef
         * @param user
         */
        private void onAddScore(DatabaseReference postRef, String user) {
            postRef
                    .child("users")
                    .child(user)
                    .runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            User u = mutableData.getValue(User.class);
                            if (u == null) {
                                return Transaction.success(mutableData);
                            }

                            u.score = String.valueOf(Integer.valueOf(u.score) + 5);

                            mutableData.setValue(u);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b,
                                               DataSnapshot dataSnapshot) {
                            // Transaction completed
                            Log.d(TAG, "postTransaction:onComplete:" + databaseError);
                        }
                    });
        }
    }

