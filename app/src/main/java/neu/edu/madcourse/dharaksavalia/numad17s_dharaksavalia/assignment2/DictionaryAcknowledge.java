package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2;

import android.app.Activity;
import android.os.Bundle;
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
        String str="Idea"+'\n'+"1. From professor Stephen discussing that ASCII creates redundancy"+'\n'+ "2. http://stackoverflow.com/questions/2276641/way-to-store-a-large-dictionary-with-low-memory-footprint-fast-lookups-on-and\n";
        str+="\nCode Help"+'\n'+"1. https://developer.android.com/reference/android/text/"+'\n'+"2. http://stackoverflow.com/questions/10523801/java-save-binary-code-to-a-file"+'\n';
        str+="\nStrategy "+'\n'+"1. Converting ASCII format to five bits per word (Binary Format) and that storing it raw format. \n2.This not only saves loading time plus space on hard drive ";
        str+="\n3. Than a preprocessed File containing binary format is loaded into application starting phase";
        textView.setText(str);
    }
}