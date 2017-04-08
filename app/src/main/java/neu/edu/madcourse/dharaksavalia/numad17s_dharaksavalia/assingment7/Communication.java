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
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment2.DictionaryAcknowledge;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment8.GameActivity;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.GameBoard;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.GameBoards.GameBoardTest1;
import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assingment7.user.User;

import static java.lang.Thread.sleep;
import static neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.DictionaryLoader.words9long;

/**
 * Created by Dharak on 3/8/2017.
 */

public class Communication extends Activity {
    private static final String SERVER_KEY = "key=AAAApVpoVJU:APA91bGjYQDhFe24ljWuSD6tNIIZ9Y_D3UqphyOZNz8Gt8fKelpNKHMS1NAvI98is4KcBt8rvW5kQmaz-KNcbMRHwkh9F-Aj9ZFH_IWdqhT2-91mJJt49Y8ELjLNX9L7HHkCW5lspbvS";

    // This is the client registration token
    //private static final String CLIENT_REGISTRATION_TOKEN = "flmtUkY07yM:APA91bGQw8i5VdjWiDV3PLwCggUbTaAmAe0ngW4UNunh6JM9oIHqCKcnccgqutzdh0yZiuexNcm1JkwbDswo7hdNcL7F9Kzf6rMLasU6tYMCYaLB5RYVdSB40X3YA6H0ia4DB_dFnhFw";
    String token;
    private boolean variable=false;
    DatabaseReference connectedRef;
    ValueEventListener connectedRefValue;
    DatabaseReference reference;
    ValueEventListener referenceValue;
    DatabaseReference playerRef;
    ValueEventListener playerRefValue;
    AlertDialog.Builder builder1=null;
    Dialog userDialog;
    String requesting;
    String requestedmode;
    String key;
    int waitPeriod=20;
    final HashMap<String,User> allPlayers=new HashMap<>();
    final HashMap<String,User> activePlayers=new HashMap<>();
    private AlertDialog dialog;
    private AlertDialog Connecting;
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
    Boolean internetConnectivity=false;
    Boolean flagRandom=false;
    Dialog dilogInt;
    static private String [] pattern={"036784512", "036478512", "401367852", "425103678", "748521036", "037852146", "036785214", "214587630", "254103678",
            "043678521", "630124785", "031467852"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Communication");

        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            requesting = startingIntent.getStringExtra("Requesting:"); // Retrieve the id
            key=startingIntent.getStringExtra("mode");
            if(key!=null)
                if(requesting!=null)
            {Toast.makeText(Communication.this, requesting+requestedmode, Toast.LENGTH_SHORT).show();
                startNewGame=true;
            }
        }
        setContentView(R.layout.communication_main);
        Log.d("In on Create","before various option");
        variousOption();


    }
    public ArrayList<String> randomWord(){
        while(flagRandom)
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        flagRandom=true;
        Random random=new Random();
        DictionaryLoader dr=DictionaryLoader.getInstance();
        dr.run9();
        int a= DictionaryLoader.words9long.size();

        ArrayList<Integer> integers=new ArrayList<>(9);
        for (int i=0;i<9;i++) {

            while (true) {
                int j;
                j=random.nextInt(a-1);

                if (integers.contains(j) == false) {
                    integers.add(j);
                    break;
                }
            }
        }
//        Log.d("integers=",integers.toString());
        ArrayList<String>word=new ArrayList<>();
        for (int i=0;i<9;i++){
            while(DictionaryLoader.words9long.get(integers.get(i))==null)integers.set(i,random.nextInt(a));
            word.add(DictionaryLoader.words9long.get(integers.get(i)));
        }
        ArrayList <String>patternWord=new ArrayList(9);
        for(int i=0;i<9;i++){
            String patternw=word.get(i);
            int n=random.nextInt(pattern.length);
            char [] patternchar=arrange(pattern[n],patternw);
            patternw="";
            for(char c:patternchar){
                Log.d("Char c=",Character.toString(c));

                patternw+=Character.toString(c);}
            patternWord.add(patternw);
        }
        for(String pattern:patternWord){
            Log.d(pattern,"YIpee");
        }
        Log.d("Communication.random()=",patternWord.toString());
        flagRandom=false;

        return patternWord;

    }
    private char[] arrange(String pattern, String word){
//        ArrayList<String> result=new ArrayList<>();
        char[] result= new char[9];
        Log.d(pattern,word);
        for (int i=0;i<9;i++){
            // String str="";
            //String str1=""+pattern.charAt(i);
            //Log.d("str1",str1);
            //str=""+word.charAt(i);
            result[Character.getNumericValue(pattern.charAt(i))]=word.charAt(i);
          //  Log.d(String.valueOf(word.charAt(i)),"yoho");
        }

        return result;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(user!=null)
        user.setActive("notActive");
        //if(reference!=null)reference.r
        if(startDialog!=null)if(startDialog.isShowing())startDialog.dismiss();
        if(userDialog!=null)if(userDialog.isShowing())userDialog.dismiss();
        if(internetConnectivity)reference.setValue(user);
        handler.removeCallbacks(runnable);
        if(reference!=null) {

            reference.removeEventListener(referenceValue);
            reference=null;
        }
        handler2.removeCallbacks(runnable2);
        if(connectedRef!=null) {
            connectedRef.removeEventListener(connectedRefValue);
            connectedRef=null;
        }
        if(playerRef!=null) {
            playerRef.removeEventListener(playerRefValue);
            playerRef=null;
        }
        if(allPlayers.size()!=0)
        allPlayers.clear();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    public User findingaPlayer(String keyRequesting){
        return allPlayers.get(keyRequesting);

    }
    public void internetAvailable(){
        Log.d("internet connectivity","inide interenet avaialable");
        token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("Inside intee")
        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
//        token = FirebaseInstanceId.getInstance().getToken();
        connectedRefValue= new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d("Connected to internet","Yipee");
                    internetConnectivity=true;
                    token = FirebaseInstanceId.getInstance().getToken();

                    DialogBox2("Connected to Internet working properly");

                    if(allPlayers.size()==0)
                    listOfPlayers();
                    if(reference==null){
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(token);
                    //reference
                    referenceValue=new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshot) {
                            new Thread() {
                                public void run(){
                                user=dataSnapshot.getValue(User.class);
                            if(user!=null)

                                {
                                    Log.d(user.toString(), "fdas");
                                    user.setActive("Active");
                                    reference.setValue(user);
                                    //inituser();
                                    //listOfActivePlayer();
                                }else

                                {
                                    inituser();
                                }}

                            }.start();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            DialogBox2("Internet Not Available");
                        }

                        //Log.d("dr",dr.toString());

                    };
                        reference.addValueEventListener(referenceValue);
                    }
                } else {
                    internetConnectivity=false;
                        DialogBox2("Disconnected to Internet ......... ");
                        if (dialog != null) if (dialog.isShowing()) dialog.dismiss();
                    }
                }


            @Override
            public void onCancelled(DatabaseError error) {
                internetConnectivity=false;
                DialogBox2("Listener was cancelled");
            }
        };
        connectedRef.addValueEventListener(connectedRefValue);
        Log.d("internet available ","end");
    }
    public void DialogBox(final String Message) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Message);
        builder.setCancelable(false);
        //builder.setTitle("Start Game with ");
        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("inside accept", "Yipee");
                        if (startDialog != null) if (startDialog.isShowing())
                            startDialog.dismiss();
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                pushNotification3(requesting, "yes");
                            }
                        }.start();
                       // Toast.makeText(Communication.this, "send suffessfullly", Toast.LENGTH_SHORT).show();
                        startThinking();
                        waitforGameBoard();
                    }

                    // Log.d(s1,s2);


                }
        );
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichB) {
                // startDialog.dismiss();
                final String opposideToken = requesting;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        pushNotification3(opposideToken, "no");
                    }
                }.start();
                DictionaryLoader.GameRequest.clear();
                listenForGameRequest();
                setVariables();
            }
        });
        new Thread() {
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startDialog = builder.show();
                }
            });

        }
    }.start();
    }
    public void DialogBox2(String Message){
        if(startDialog!=null)
            if(startDialog.isShowing())startDialog.dismiss();
        startDialog=null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(Message);
        //builder.setTitle("Start Game with ");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startDialog.dismiss();
            }
        });
        new Thread() {
            public  void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startDialog = builder.show();

                    }
                });
            }
        }.start();
    }

    public void waitforGameBoard(){
        //DialogBox("Waiting for game board");
        gameBoardwait=waitPeriod;
        final DatabaseReference ref =FirebaseDatabase.getInstance().getReference("GameBoard").child(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GameBoardTest1 gameBoard=dataSnapshot.getValue(GameBoardTest1.class);
                if(gameBoard!=null){
                    if(Connecting!=null)if(Connecting.isShowing())Connecting.dismiss();
                    if(startDialog!=null)if(startDialog.isShowing()) startDialog.dismiss();

                   // Toast.makeText(Communication.this,"Yipee game connected",Toast.LENGTH_LONG).show();
                    setVariables();
                    //Log.d("Game Board found","Yipee");
                    handler2.removeCallbacks(runnable2);
                    ref.removeEventListener(this);
                    stopThinking();
                    Intent intent=new Intent(Communication.this, GameActivity.class);
                    intent.putExtra("Token",key);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DialogBox2("Internet not available");
            }
        });
        runnable2=new Runnable() {
            @Override
            public void run() {
                gameBoardwait--;
                handler2.postDelayed(this,1000);
                Log.d("Times=",String.valueOf(gameBoardwait));
                if(Connecting!=null)if(Connecting.isShowing())Connecting.dismiss();
                if(gameBoardwait<0){handler2.removeCallbacks(this);
                if(startDialog.isShowing())startDialog.dismiss();
                    startThinking();
                    if(Connecting!=null)if(Connecting.isShowing())Connecting.dismiss();
                    stopThinking();
                    Toast.makeText(Communication.this,"Cannot find Game Board in "+ String.valueOf(waitPeriod)+" seconds!!!!!",Toast.LENGTH_LONG).show();
                }
            }
        };

          handler2.postDelayed(runnable2,1000);
    }
    private void listOfPlayers(){
        playerRef=FirebaseDatabase.getInstance().getReference("Users");

        playerRefValue=new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //allPlayers.clear();
                new Thread() {
                    public void run() {
                        activePlayers.clear();
                        int i = 0;
                        for (
                                DataSnapshot child : dataSnapshot.getChildren())

                        {
                            //Log.d("child",child.toString());
                            Log.d("times=", String.valueOf(i++));
                            Log.d("child=", child.toString());
                            //String key=child.getKey();
                            User user = child.getValue(User.class);
                            // Log.d("Name=",user.getUsername());
                            if (user != null) {
                                allPlayers.put(user.getKey(), user);
                                if (user.getActive().equalsIgnoreCase("Active")) {
                                    activePlayers.put(user.getKey(), user);
                                }
                            }
                        }
                    }
                    }.

                    start();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            DialogBox2("Internet Not Available");
            }
        };
        playerRef.addValueEventListener(playerRefValue);
        //Log.d("allPlayers=",allPlayers.toString());
        //Log.d()
       // return allPlayers;
    }
    @Override
    protected void onResume() {
        super.onResume();

                Log.d("inisde internet avaible","Yipee");
                internetAvailable();
                //  user.setActive("Active");
                //  reference.setValue(user);


                if (startNewGame)

                {
                    startNewGame();
                } else

                {
                    listenForGameRequest();
                }
                Log.d("endof resume","yipee");
            }




    private void gameRequest(){
    for(String key:DictionaryLoader.GameRequest.keySet()){
        requesting=key;
        startNewGame=true;
        this.key=DictionaryLoader.GameRequest.get(key);
        handler.removeCallbacks(runnable);
        startNewGame();
        DictionaryLoader.GameRequest.remove(key);
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
                    requesting=user2.getKey();



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


    private void inituser(){
        //if()
        // Log and toast
        String msg = getString(R.string.msg_token_fmt, token);
        //Log.d(TAG, msg);
        //Toast.makeText(Communication.this, msg, Toast.LENGTH_SHORT).show();
        LayoutInflater linf = LayoutInflater.from(Communication.this);
        if(internetConnectivity==null)return;
        if(!internetConnectivity){
            DialogBox2("Internet not available");
            return;
        }
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



        new Thread() {
            public void run() {
                Communication.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userDialog = alert.show();
                    }
                });

            }
        }.start();}
    private  void seachActiveplayer(){

    }
    private void findPlayers(String mode){

        Log.d("Inside find Players","yipee");
        Log.d("internet Connectivity:",String.valueOf(internetConnectivity));
        if(!internetConnectivity) {
            DialogBox2("Internet Not Available");
            return;
        }
        if(allPlayers.isEmpty()){
            return;
        }
        //
        // Log.d("Internet Not available","Yipee");
        LayoutInflater linf = LayoutInflater.from(Communication.this);
        final View inflator = linf.inflate(R.layout.listtype, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(Communication.this);
        alert.setView(inflator);
        final ArrayList<String> keyValue =new ArrayList();
        final ArrayList<String>status=new ArrayList<>();
        ListView listView=(ListView)inflator.findViewById(R.id.samplelist);
        //alert.setCancelable(false);
        alert.setTitle("Select Player with whom you want to Play");
        String []listofPlayer=new String[allPlayers.size()-1];
        int i=0;
        final String GameMode=mode;
       // Log.d("");
        //Log.d("sizeOfpla
        //
        // yers=",allPlayers.toString());
        for(String key:allPlayers.keySet()){
            //Log.d("Inside list of players",user.toString());
            User us=allPlayers.get(key);
            if(key.equals(token.toString()))continue;
            keyValue.add(us.getKey());
            status.add(us.getActive());
            try {
                listofPlayer[i++] = us.getUsername() + "\n" + us.emailID + "\n" + us.getActive();
            }
            catch(ArrayIndexOutOfBoundsException ext){

            }
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
        key=FirebaseDatabase.getInstance().getReference("GameBoard").push().getKey();
//        FirebaseDatabase.getInstance().getReference("GameBoard").setValue(key);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(status.equalsIgnoreCase("notActive"))
                pushNotification(keyValue,key);
                else{
                    pushNotification2(keyValue,key);
                }
            }
        }).start();
        AlertDialog.Builder builder1=new AlertDialog.Builder(getBaseContext());
        builder1.setMessage("Trying to Start the game");
        builder1.setCancelable(false);
       // Connecting=builder1.show();
        waitingforreply(keyValue,mode);
        startThinking();
    }
    public void startThinking() {
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.VISIBLE);
    }

    public void stopThinking() {
        View thinkView = findViewById(R.id.thinking);
        thinkView.setVisibility(View.GONE);
    }
    private void waitingforreply(final String keyValue,final String mode){
        gameBoardwait=waitPeriod;
        runnable2=new Runnable() {
            @Override
            public void run() {
                gameBoardwait--;
                Log.d("Times=",String.valueOf(gameBoardwait));
                Boolean stop=false;
                if(DictionaryLoader.GameReply.get(keyValue)!=null) {
                    if (DictionaryLoader.GameReply.get(keyValue).equalsIgnoreCase("yes")) {
                        //GameBoard board = new GameBoard("myturn");
                         DatabaseReference refgame=FirebaseDatabase.getInstance().getReference("GameBoard").child(key);

                        Toast.makeText(Communication.this, "YIPEE opposide party said yess", Toast.LENGTH_LONG).show();
                       startThinking();
                        if(runnable2!=null)
                        handler2.removeCallbacks(runnable2);
                        //Log.d("StringsWord=",randomWord().toString());
                        stop=true;
                        stopThinking();
                        DictionaryLoader.GameReply.clear();
                        DictionaryLoader.GameRequest.clear();
                        setVariables();
                        if (Connecting != null) if (Connecting.isShowing()) Connecting.dismiss();

                        refgame.setValue(new

                                GameBoardTest1(randomWord(), token, keyValue, mode,key

                        ));
                                Intent intent=new Intent(Communication.this, GameActivity.class);
                                intent.putExtra("Token",key);
                                startActivity(intent);



                    }else{
                        Toast.makeText(Communication.this, "opposide party told no", Toast.LENGTH_LONG).show();
                        handler2.removeCallbacks(runnable2);
                        setVariables();
                        stop=true;
                        if(Connecting!=null)if(Connecting.isShowing())Connecting.dismiss();
                        DictionaryLoader.GameReply.clear();
                        DictionaryLoader.GameRequest.clear();
                        listenForGameRequest();
                        stopThinking();

                    }


                }
                if(gameBoardwait<1){
                    Log.d("Killing ","handler");
                    Toast.makeText(Communication.this, "Connection not made", Toast.LENGTH_LONG).show();
                    handler2.removeCallbacks(runnable2);
                    handler2.removeCallbacks(runnable2);
                    DictionaryLoader.GameReply.clear();
                    stopThinking();
                    stop=true;
                    if(Connecting!=null)if(Connecting.isShowing())Connecting.dismiss();
                    DictionaryLoader.GameRequest.clear();
                    setVariables();
                    listenForGameRequest();
                }
                if(!stop) {
                    handler2.postDelayed(this, 1000);
                }
                if(stop)handler2.removeCallbacks(this);

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
            jPayload.put("to",key );
            jData.put("Reply:",token);
            jData.put("agree",agree);
           // Log.d("Inside the ","");

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
            //Log.d("Sending notifcation3=",requesting);
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
        Log.d("On Create ","Start of various option");
        Button token=(Button)findViewById(R.id.TokenButton);
        Button dataBase=(Button)findViewById(R.id.DataBaseButton);
        Button synchronous=(Button)findViewById(R.id.SynchronousButton);
        Button aysnchronous=(Button)findViewById(R.id.AsysnchronousButton);
        Button playerProfile=(Button)findViewById(R.id.playerProfile);
        playerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!internetConnectivity){DialogBox2("Internet Not available");

                return;
                }
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
        dataBase.setVisibility(View.INVISIBLE);
        token.setVisibility(View.INVISIBLE);
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

