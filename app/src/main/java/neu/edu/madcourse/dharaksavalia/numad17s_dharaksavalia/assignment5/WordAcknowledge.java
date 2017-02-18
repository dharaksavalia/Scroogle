package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

public class WordAcknowledge extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_acknowledge);
        TextView textView=(TextView)findViewById(R.id.WordAcknowlegementsText);
        setTitle("Acknowledgements");
        String str="Idea"+'\n'+"1. Hello Android Book ";
        str+="\n2. Class discussion  ";
        str+="\n\nCode Help"+'\n'+"1. https://developer.android.com/reference/android/"+"\n2. Hello Android book "+"\n3. http://stackoverflow.com/questions/29145060/how-can-i-open-and-auto-dismiss-an-alert-dialog-when-opening-a-new-section";

        str+="\n\n";
        str+="\n Lastly special thanks to my friend Amit Raul for discussion regarding project and its strategies.";
        textView.setText(str);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}

