package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 3/8/2017.
 */

public class Communication extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication_main);

        variousOption();
}
    private void variousOption(){
        Button token=(Button)findViewById(R.id.TokenButton);
        Button dataBase=(Button)findViewById(R.id.DataBaseButton);
        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Communication.this,FCMActivity.class);
                startActivity(intent);

            }
        });
        dataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Communication.this,RealtimeDatabaseActivity.class);
                startActivity(intent);
            }
        });

    }
}

