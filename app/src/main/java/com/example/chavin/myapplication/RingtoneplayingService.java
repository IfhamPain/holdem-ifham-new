package com.example.chavin.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 10-May-17.
 */

public class RingtoneplayingService extends Service {

    MediaPlayer media_song;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){

        Log.i("Local service", "Received start id" + startId + ":" + intent);

        //create as instance of the media player
        media_song = MediaPlayer.create(this, R.raw.dove);
        media_song.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        //tell the user that we stopped
        Toast.makeText(this, "on Destroy called", Toast.LENGTH_SHORT).show();
    }
}
