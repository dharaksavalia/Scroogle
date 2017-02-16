package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment5;

/**
 * Created by Dharak on 1/20/2017.
 */


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;


public class WordMainActivity extends Activity {
    MediaPlayer mMediaPlayer;
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wordativity_main);
        setTitle("Word Game");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer = MediaPlayer.create(this, R.raw.bensound_happiness);
        mMediaPlayer.setVolume(0.5f, 0.5f);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
    }
}
