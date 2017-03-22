package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment8;

import android.app.Activity;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 3/21/2017.
 */

public class Leaderboard extends Activity {
    String UserName;
    Integer Score;
    Scores scores;
    DatabaseReference reference;
    ArrayList<Scores> allScores = new ArrayList<>();
    ValueEventListener referenceValue;
    String token;
    TableLayout tableLayout;
    TableRow tableRow;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference=FirebaseDatabase.getInstance().getReference("Leaderboard");
        setContentView(R.layout.leardboard);
        token = FirebaseInstanceId.getInstance().getToken();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Scores dataScore = child.getValue(Scores.class);
                    if (dataScore != null) {
                        allScores.add(dataScore);
                    }

                }
                //Log.d("all scores=", allScores.toString());
                //Log.d("Now storinting=","Hello");
                Collections.sort(allScores);
                constructHeader();
                constructBody();
                // Log.d("all Scores=",allScores.toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        //tableLayout.setStretchAllColumns(true);
    }

    public void constructHeader() {
        tableRow = new TableRow(this);
        TextView Username = new TextView(this);
        TextView Country = new TextView(this);
        TextView Time = new TextView(this);
        TextView Scores = new TextView(this);
        Username.setText("USERNAME");
        Username.setTextColor(Color.RED);
        Country.setText("COUNTRIES");
        Country.setTextColor(Color.RED);
        Time.setText("TIME");
        Time.setTextColor(Color.RED);
        Scores.setText("SCORES");
        Scores.setTextColor(Color.RED);
        tableRow.addView(Username);
        tableRow.addView(Country);
        tableRow.addView(Time);
        tableRow.addView(Scores);
        tableLayout.addView(tableRow);
        //Username.setTextSize(s);
    }

    public void constructBody() {
        for (Scores scores : allScores) {
            tableRow = new TableRow(this);
            TextView Username = new TextView(this);
            TextView Country = new TextView(this);
            TextView Time = new TextView(this);
            TextView Scores = new TextView(this);
            Username.setText(scores.getUserName());
            Username.setTextColor(Color.BLUE);
            Country.setText(scores.getCountry());
            Country.setTextColor(Color.BLUE);
            Time.setText(scores.getTime());
            Time.setTextColor(Color.BLUE);
            Scores.setText(scores.getScore().toString());
            Scores.setTextColor(Color.BLUE);
            tableRow.addView(Username);
            tableRow.addView(Country);
            tableRow.addView(Time);
            tableRow.addView(Scores);
            tableLayout.addView(tableRow);
        }
    }
}