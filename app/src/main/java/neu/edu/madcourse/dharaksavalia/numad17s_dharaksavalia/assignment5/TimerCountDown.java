package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;

/**
 * Created by Dharak on 2/17/2017.
 */

public class TimerCountDown extends CountDownTimer {
    private WeakReference<GameActivity>gameactivity;
    private String Time;
    private boolean mainActivity=true,started=false;
    TextView txtOutput;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */

    public TimerCountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        if(started==false){
        start();
    }}

    @Override
    public void onTick(long millisUntilFinished) {
        Time=String.format ("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
        updateTextView();
    }
    public void setMainActivity(boolean dt){
        mainActivity=dt;
    }
    @Override
    public void onFinish() {
    if(mainActivity)gameactivity.get().TimeFinished();
    }
    private void updateTextView() {
        GameActivity activity = gameactivity.get();
        if (activity != null && !activity.isFinishing()&&mainActivity) {
            TextView textView = (TextView) activity.findViewById(R.id.wordTimer);
            textView.setText(Time);
        }
    }

    public void start(GameActivity gameActivity) {
        gameactivity=new WeakReference<GameActivity>(gameActivity);

        start();
    }
}
