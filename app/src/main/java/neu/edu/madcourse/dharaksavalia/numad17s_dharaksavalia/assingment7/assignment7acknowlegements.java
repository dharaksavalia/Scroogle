package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 3/12/2017.
 */

public class assignment7acknowlegements extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

                setContentView(R.layout.assignment7_acknowledge);
                TextView textView=(TextView)findViewById(R.id.assignment7Acknowlegements);
                setTitle("Acknowledgements");
                String str="Idea"+'\n'+"1. From class discussion on Firebase";
                str+="\n2. https://firebase.google.com/docs/ ";
                str+="\n\nCode Help"+'\n'+"1. https://developer.android.com/reference/android/";
                str+="\n"+'\n'+"2. Code rrom Anirudh Kapila ";
                str+="\n"+'\n'+"3. https://firebase.google.com/docs/";
                str+="\n"+'\n'+"3. https://firebase.google.com/docs/";
                textView.setText(str);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
        }

