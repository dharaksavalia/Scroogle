package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.aboutFunction;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1.assignment1MainActivity;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2.DictionaryLoader;


public class MainActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variousMenu();
        //DictionaryLoader dl=new DictionaryLoader();
        //dl.run();
        this.setTitle("Dharak Savalia");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void variousMenu() {
        final Button tickTackToeButton = (Button) findViewById(R.id.utimateTicToeButton);
        Button aboutButton = (Button) findViewById(R.id.aboutButton);
        Button generateErrorButton = (Button) findViewById(R.id.generateErrorButton);
        Button quit = (Button) findViewById(R.id.quitButton);
        Button dictionary=(Button)findViewById(R.id.dictionary);
        tickTackToeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            Intent intenet1=new Intent(MainActivity.this,assignment1MainActivity.class);
                startActivity(intenet1);
            }
        });
        dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (MainActivity.this,DictionaryLoader.class);
                startActivity(intent);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,aboutFunction.class);
                startActivity(intent);


            }
        });
        generateErrorButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            int d=0/0;
            }
        });
        quit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.exit(0);
            }

        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
