package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1;

/**
 * Created by Dharak on 1/20/2017.
 */


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;


public class assignment1MainActivity extends Activity {
    MediaPlayer mMediaPlayer;
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_main);
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
