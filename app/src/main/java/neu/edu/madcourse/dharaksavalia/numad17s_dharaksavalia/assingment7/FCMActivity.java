package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7;

/**
 * Created by Dharak on 3/7/2017.
 */


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.os.Handler;
        import android.os.Looper;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.iid.FirebaseInstanceId;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.Scanner;
        import java.util.concurrent.RunnableFuture;

        import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
        import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user.User;


public class FCMActivity extends Activity {

    private static final String TAG = FCMActivity.class.getSimpleName();
    EditText user;
    EditText emailid;
    // Please add the server key from your firebase console in the follwoing format "key=<serverKey>"
    private static final String SERVER_KEY = "key=AAAApVpoVJU:APA91bGjYQDhFe24ljWuSD6tNIIZ9Y_D3UqphyOZNz8Gt8fKelpNKHMS1NAvI98is4KcBt8rvW5kQmaz-KNcbMRHwkh9F-Aj9ZFH_IWdqhT2-91mJJt49Y8ELjLNX9L7HHkCW5lspbvS";

    // This is the client registration token
    private static final String CLIENT_REGISTRATION_TOKEN = "flmtUkY07yM:APA91bGQw8i5VdjWiDV3PLwCggUbTaAmAe0ngW4UNunh6JM9oIHqCKcnccgqutzdh0yZiuexNcm1JkwbDswo7hdNcL7F9Kzf6rMLasU6tYMCYaLB5RYVdSB40X3YA6H0ia4DB_dFnhFw";
    Handler handler=new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);


        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                final String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg);
                Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();
                LayoutInflater linf = LayoutInflater.from(FCMActivity.this);
                final View inflator = linf.inflate(R.layout.userregisteration, null);
                AlertDialog.Builder alert = new AlertDialog.Builder(FCMActivity.this);
                alert.setView(inflator);

                final EditText email = (EditText) inflator.findViewById(R.id.emailiduser);
                final EditText username = (EditText) inflator.findViewById(R.id.usernameregister);

                alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                      // Log.d(s1,s2);
                        User mydetails=new User(username.getText().toString(),null,email.getText().toString(),null,null,null);
                        DatabaseReference dr=FirebaseDatabase.getInstance().getReference("Users");
                        dr.child(token).setValue(mydetails);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert.show();}
            });

    }

    public void pushNotification(View type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                pushNotification();
            }
        }).start();
    }

    private void pushNotification() {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("title", "Google I/O 2016");
            jNotification.put("body", "Firebase Cloud Messaging (App)");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");

            // If sending to a single client
            jPayload.put("to", CLIENT_REGISTRATION_TOKEN);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + resp);
                    Toast.makeText(FCMActivity.this,resp,Toast.LENGTH_LONG);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }
}