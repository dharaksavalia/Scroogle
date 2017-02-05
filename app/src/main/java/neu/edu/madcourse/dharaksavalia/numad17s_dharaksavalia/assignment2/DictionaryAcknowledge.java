package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 2/3/2017.
 */

public class DictionaryAcknowledge extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dictionary_acknowledge);
        TextView textView=(TextView)findViewById(R.id.dictionaryAcknowlegements);
        setTitle("Acknowledgements");
        String str="Idea"+'\n'+"1. From professor Stephen discussing that ASCII creates redundancy";
        str+="\n2. http://stackoverflow.com/questions/4115548/how-to-used-the-alphabet-binary-symbols. ";
        str+="\n\nCode Help"+'\n'+"1. https://developer.android.com/reference/android/";
        str+="\n\nStrategy "+'\n'+"1. Converting ASCII format to five bits per word (Binary Format) and that storing it raw format. \n2.This not only saves loading time plus space on hard drive ";
        str+="\n 3. This preprocessed file is stored in raw folder and loaded during application in on create method.";
        str+="\n4. Words are loaded into HashMap and matched with input.";
        str+="\n5. Lastly special thanks to my friend Raj Kukadia for Testing and UI Design refinement suggestion";
        textView.setText(str);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}