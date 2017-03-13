package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2.DictionaryAcknowledge;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.GameBoard;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user.User;

/**
 * Created by Dharak on 3/8/2017.
 */

public class Communication extends Activity {
    private static final String SERVER_KEY = "key=AAAApVpoVJU:APA91bGjYQDhFe24ljWuSD6tNIIZ9Y_D3UqphyOZNz8Gt8fKelpNKHMS1NAvI98is4KcBt8rvW5kQmaz-KNcbMRHwkh9F-Aj9ZFH_IWdqhT2-91mJJt49Y8ELjLNX9L7HHkCW5lspbvS";

    // This is the client registration token
    //private static final String CLIENT_REGISTRATION_TOKEN = "flmtUkY07yM:APA91bGQw8i5VdjWiDV3PLwCggUbTaAmAe0ngW4UNunh6JM9oIHqCKcnccgqutzdh0yZiuexNcm1JkwbDswo7hdNcL7F9Kzf6rMLasU6tYMCYaLB5RYVdSB40X3YA6H0ia4DB_dFnhFw";
    String token;
    DatabaseReference reference;
    Dialog userDialog;
    String requesting;
    String requestedmode;
    int waitPeriod=20;
    final HashMap<String,User> allPlayers=new HashMap<>();
    final HashMap<String,User> activePlayers=new HashMap<>();
    private AlertDialog dialog;
    User user;
    Runnable runnable;
    Handler handler=new Handler();
    Handler handler2=new Handler();
    Runnable runnable2;
    Handler handler3=new Handler();
    Runnable runnable3;
    private AlertDialog startDialog;
    Boolean startNewGame=false;
    int gameBoardwait=15;
    Boolean internetConnectivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Communication");

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            requesting = startingIntent.getStringExtra("Requesting:"); // Retrieve the id
            requestedmode=startingIntent.getStringExtra("mode");
            if(requestedmode!=null)
                if(requesting!=null)
            {Toast.makeText(Communication.this, requesting+requestedmode, Toast.LENGTH_SHORT).show();
                startNewGame=true;
            }
        }
        setContentView(R.layout.communication_main);

        variousOption();
        token = FirebaseInstanceId.getInstance().getToken();
        String refString;
        refString = "Users/" + token;
        reference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        //listOfActivePlayer();
        //reference
    listOfPlayers();
        internetAvailable();

    //    inituser();
    }

    @Override
    protected void onPause() {
        super.onPause();
        user.setActive("notActive");

        if(internetConnectivity)reference.setValue(user);
        handler.removeCallbacks(runnable);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    public User findingaPlayer(String keyRequesting){
        return allPlayers.get(keyRequesting);

    }
    public void internetAvailable(){
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    internetConnectivity=true;
                    DialogBox2("Connected to Internet working properly");
                } else {
                    internetConnectivity=false;
                    DialogBox2("Disconnected to Internet ......... ");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                internetConnectivity=false;
                DialogBox2("Listener was cancelled");
            }
        });


    }
    public void DialogBox(String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Message);
        builder.setCancelable(false);
        //builder.setTitle("Start Game with ");
        builder.setCancelable(false);
        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if(startDialog.isShowing()){
                    startDialog.dismiss();
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            pushNotification3(requesting,"yes");
                        }
                    };

                    Toast.makeText(Communication.this,"send suffessfullly",Toast.LENGTH_SHORT).show();
                    waitforGameBoard();
                }
                // Log.d(s1,s2);

            }
        });
        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int whichB){
                startDialog.dismiss();
                new Thread(){
                    @Override
                    public void run(){
                        super.run();
                        pushNotification3(requesting,"no");
                    }
                }.start();
                DictionaryLoader.GameRequest.clear();
                listenForGameRequest();
                setVariables();
            }});

           startDialog=builder.show();
    }
    public void DialogBox2(String Message){
        if(startDialog!=null)
        if(startDialog.isShowing())startDialog.dismiss();
        startDialog=null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Message);
        //builder.setTitle("Start Game with ");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startDialog.dismiss();
            }
        });
        startDialog=builder.show();
    }
    public void waitforGameBoard(){
        //DialogBox("Waiting for game board");
        gameBoardwait=waitPeriod;
        runnable2=new Runnable() {
            @Override
            public void run() {
                gameBoardwait--;
                handler2.postDelayed(this,1000);
                DatabaseReference ref =FirebaseDatabase.getInstance().getReference("GameBoard").child(requesting);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    GameBoard gameBoard=dataSnapshot.getValue(GameBoard.class);
                        if(gameBoard!=null){
                            if(startDialog.isShowing()) startDialog.dismiss();
                            Toast.makeText(Communication.this,"Yipee game connected",Toast.LENGTH_LONG).show();
                            setVariables();
                            handler2.removeCallbacks(runnable2);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    DialogBox2("Internet not available");
                    }
                });
                if(gameBoardwait<0){handler2.removeCallbacks(this);
                if(startDialog.isShowing())startDialog.dismiss();
                Toast.makeText(Communication.this,"Cannot find Game Board in "+ String.valueOf(waitPeriod)+" seconds!!!!!",Toast.LENGTH_LONG).show();
                }
            }
        };
          handler2.postDelayed(runnable2,1000);
    }
    private void listOfPlayers(){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //allPlayers.clear();
                activePlayers.clear();
                int i=0;
                for(DataSnapshot child:dataSnapshot.getChildren()){
                    //Log.d("child",child.toString());
                    Log.d("times=",String.valueOf(i++));
                    Log.d("child=",child.toString());
                    //String key=child.getKey();
                    User user=child.getValue(User.class);
                   // Log.d("Name=",user.getUsername());
                    if(user!=null){
                      allPlayers.put(user.getKey(),user);
                        if (user.getActive().equalsIgnoreCase("Active")){
                            activePlayers.put(user.getKey(),user);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            DialogBox2("Internet Not Available");
            }
        });
        Log.d("allPlayers=",allPlayers.toString());
        //Log.d()
       // return allPlayers;
    }
    @Override
    protected void onResume() {
        super.onResume();
     //  user.setActive("Active");
      //  reference.setValue(user);
        reference = FirebaseDatabase.getInstance().getReference("Users").child(token);
        //reference
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                if(user!=null){
                    Log.d(user.toString(),"fdas");
                    user.setActive("Active");
                    reference.setValue(user);
                    //inituser();
                    //listOfActivePlayer();
                }else{
                    inituser();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            DialogBox2("Internet Not Available");
            }

            //Log.d("dr",dr.toString());

        });

        if(startNewGame){
         startNewGame();
        }
        else{
            listenForGameRequest();
        }
    }
    private void gameRequest(){
    for(String key:DictionaryLoader.GameRequest.keySet()){
        requesting=key;
        startNewGame=true;
        requestedmode=DictionaryLoader.GameRequest.get(key);
        handler.removeCallbacks(runnable);
        startNewGame();
    }
    }
    public void listenForGameRequest(){
        runnable=new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(this,1000);
                gameRequest();
            }
        };
        handler.postDelayed(runnable,1000);

    }
    private void setVariables(){
        requestedmode=null;
        requesting=null;
        startNewGame=false;
      //  listenForGameRequest();
    }
    public void startNewGame() {
        // User us=findingaPlayer(requesting);
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users").child(requesting);
        //reference
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user2 = dataSnapshot.getValue(User.class);
                if (user2 != null) {
                    DialogBox("Want to Play with "+user2.username+"?");


                } else {
                    DictionaryLoader.GameRequest.clear();
                    listenForGameRequest();
                    setVariables();

                    listenForGameRequest();
                    Toast.makeText(Communication.this, "Failed to find a player", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            DialogBox2("Internet not Available");
            }

        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void inituser(){

        // Log and toast
        String msg = getString(R.string.msg_token_fmt, token);
        //Log.d(TAG, msg);
        Toast.makeText(Communication.this, msg, Toast.LENGTH_SHORT).show();
        LayoutInflater linf = LayoutInflater.from(Communication.this);

        final View inflator = linf.inflate(R.layout.userregisteration, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(Communication.this);
        alert.setView(inflator);

        final EditText email = (EditText) inflator.findViewById(R.id.emailiduser);
        final EditText username = (EditText) inflator.findViewById(R.id.usernameregister);
        alert.setCancelable(false);
        email.setVisibility(View.INVISIBLE);
        final Button setbutton=(Button)inflator.findViewById(R.id.setuser);
        setbutton.setVisibility(View.INVISIBLE);
        setbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User mydetails = new User(username.getText().toString(), null, email.getText().toString(),null,token,"Active");
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Users");
                dr.child(token).setValue(mydetails);
                user=mydetails;
                if(userDialog.isShowing())
                userDialog.dismiss();

            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>3){email.setVisibility(View.VISIBLE);
                username.setBackgroundColor(Color.GREEN);
                if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())setbutton.setVisibility(View.VISIBLE);}
                        else{
                    username.setBackgroundColor(Color.RED);
                    email.setVisibility(View.INVISIBLE);
                    setbutton.setVisibility(View.INVISIBLE);
                };
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Inside the matcher","yippe");
            if(Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                Log.d("Inside the matcher","it matching trying to set ");
                email.setBackgroundColor(Color.GREEN);
               setbutton.setVisibility(View.VISIBLE);
            }
                else{
                email.setBackgroundColor(Color.RED);
                setbutton.setVisibility(View.INVISIBLE);
            }
            }
        });




        userDialog=alert.show();}
    private  void seachActiveplayer(){

    }
    private void findPlayers(String mode){
        Log.d("Inside find Players","yipee");
        Log.d("internet Connectivity:",String.valueOf(internetConnectivity));
        if(!internetConnectivity) {
            DialogBox2("Internet Not Available");
            return;
        }
        Log.d("Internet Not available","Yipee");
        LayoutInflater linf = LayoutInflater.from(Communication.this);
        final View inflator = linf.inflate(R.layout.listtype, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(Communication.this);
        alert.setView(inflator);
        final ArrayList<String> keyValue =new ArrayList();
        final ArrayList<String>status=new ArrayList<>();
        ListView listView=(ListView)inflator.findViewById(R.id.samplelist);
        //alert.setCancelable(false);
        alert.setTitle("Select Player with whom you want to Play");
        String []listofPlayer=new String[allPlayers.size()];
        int i=0;
        final String GameMode=mode;
        Log.d("sizeOfplayers=",allPlayers.toString());
        for(String key:allPlayers.keySet()){
            Log.d("Inside list of players","Yipee");
            User us=allPlayers.get(key);
            keyValue.add(us.getKey());
            status.add(us.getActive());
            listofPlayer[i++]="UserName="+us.getUsername()+",EmailID="+us.emailID+",Status="+us.getActive();

        }
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listofPlayer );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("Item Selected","yipee");
                //alert.create().dismiss();
                if(dialog.isShowing())
                dialog.dismiss();
                sendInternetMessage(keyValue.get(position),status.get(position),GameMode);
                //alert.setView(null);
            }
        });
        dialog=alert.show();
    }
    public void sendInternetMessage(final String keyValue, final String status, final String mode){
        Thread thread=new Thread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(status.equalsIgnoreCase("notActive"))
                pushNotification(keyValue,mode);
                else{
                    pushNotification2(keyValue,mode);
                }
            }
        }).start();
        waitingforreply(keyValue);
    }
    private void waitingforreply(final String keyValue){
        gameBoardwait=waitPeriod;
        runnable2=new Runnable() {
            @Override
            public void run() {
                gameBoardwait--;
                if(DictionaryLoader.GameReply.get(keyValue)!=null) {
                    if (DictionaryLoader.GameReply.get(keyValue).equalsIgnoreCase("yes")) {
                        GameBoard board = new GameBoard("myturn");

                        Toast.makeText(Communication.this, "YIPEE opposide party said yess", Toast.LENGTH_LONG).show();
                        handler2.removeCallbacks(runnable2);
                        DictionaryLoader.GameReply.clear();
                        DictionaryLoader.GameRequest.clear();
                        setVariables();

                    }else{
                        Toast.makeText(Communication.this, "opposide party told no", Toast.LENGTH_LONG).show();
                        handler2.removeCallbacks(runnable2);
                        setVariables();
                        DictionaryLoader.GameReply.clear();
                        DictionaryLoader.GameRequest.clear();
                    }
                    if(gameBoardwait<1){
                        Toast.makeText(Communication.this, "Connection not made", Toast.LENGTH_LONG).show();
                        handler2.removeCallbacks(runnable2);
                        DictionaryLoader.GameReply.clear();
                        DictionaryLoader.GameRequest.clear();
                        setVariables();
                    }
                }

                handler2.postDelayed(this,1000);


            }
        };
        handler2.postDelayed(runnable2,1000);
    }
    private void pushNotification(String key,String mode) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData=new JSONObject();
        try {
            jNotification.put("title", "Game Play invite from "+user.getUsername());
            jNotification.put("body", "Lets Start Playing ");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "COMMUNICATION");

            // If sending to a single client
            jPayload.put("to",key );
            jData.put("Requesting:",token);
            jData.put("mode",mode);
            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */
            jPayload.put("data",jData);
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
                    Log.e("Hello", "run: " + resp);
                    Toast.makeText(Communication.this,resp,Toast.LENGTH_LONG);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
    private void pushNotification3(String key,String agree) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData=new JSONObject();
        try {
            // If sending to a single client
            jPayload.put("to",requesting );
            jData.put("Requesting:",token);
            jData.put("agree",agree);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("data",jData);
            jPayload.put("priority", "high");
            //jPayload.put("notification", jNotification);

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
            Log.d("Sending notifcation3=",requesting);
            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("notifcationf3", "run: " + resp);
                    Toast.makeText(Communication.this,resp,Toast.LENGTH_LONG);
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
    private void pushNotification2(String key,String mode) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jData=new JSONObject();
        try {
            // If sending to a single client
            jPayload.put("to",key );
            jData.put("Requesting:",token);
            jData.put("mode",mode);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("data",jData);
            jPayload.put("priority", "high");
            //jPayload.put("notification", jNotification);

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
                    Log.e("Hello", "run: " + resp);
                    Toast.makeText(Communication.this,resp,Toast.LENGTH_LONG);
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
    private void variousOption(){
        Button token=(Button)findViewById(R.id.TokenButton);
        Button dataBase=(Button)findViewById(R.id.DataBaseButton);
        Button synchronous=(Button)findViewById(R.id.SynchronousButton);
        Button aysnchronous=(Button)findViewById(R.id.AsysnchronousButton);
        Button playerProfile=(Button)findViewById(R.id.playerProfile);
        playerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetConnectivity)DialogBox2("Internet Not available");
                inituser();
            }
        });
        synchronous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPlayers("synchronous");
            }
        });
        aysnchronous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            findPlayers("asynchronous");
            }
        });
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

        Button acknowledgments=(Button)findViewById(R.id.ass7acknowlegments);
        acknowledgments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent (Communication.this,assignment7acknowlegements.class);
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

