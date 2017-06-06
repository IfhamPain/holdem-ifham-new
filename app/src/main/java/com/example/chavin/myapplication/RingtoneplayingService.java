package com.example.chavin.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 10-May-17.
 */

public class RingtoneplayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    public int onStartCommand(Intent intent, int flags, int startId){

        Log.i("Local service", "Received start id" + startId + ":" + intent);

        //fetch the extra string values
        String state = intent.getExtras().getString("extra");

        //converts the extra strings from the intent
        //to startIDs , values 0 or 1
        assert state != null;
        switch (state){
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        //if else statements

        //if there's no music playing and the user pressed "alarm on"
        //music should start playing
        if(!this.isRunning && startId == 1){

            //create as instance of the media player
            media_song = MediaPlayer.create(this, R.raw.dove);
            //start the ring tone
            media_song.start();

            this.isRunning = true;
            this.startId = 0;

            //notification
            //set up the notification service
            NotificationManager notify_manager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            //set up an intent that goes to the main activity
            Intent intent_main_activity = new Intent(this.getApplicationContext(),MainActivity.class);
            //set up an pending intent
            PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this,0,
                    intent_main_activity, 0);

            //making the notification parameters

            Notification notification_popup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off")
                    .setContentText("Click me")
                    .setSmallIcon(R.drawable.your_icon)
                    .setContentIntent(pending_intent_main_activity)
                    .setAutoCancel(true)
                    .build();


            //setup notification start comand
            notify_manager.notify(0,notification_popup);


        }

        //if there is music playing and the user pressed "alarm off"
        //music should stop playing
        else if (this.isRunning && startId == 0){
            //stop the ting tone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;



        }

        //these are if the user presses random buttons
        //just to bug proof the app
        //if there is no music playing and the user pressed "alarm off"
        //do nothing
        else if(!this.isRunning && startId== 0){

            this.isRunning = false;
            this.startId = 0;
        }

        //if there is music playing and the user pressed "alarm on"
        //do nothing
        else if (!this.isRunning && startId == 1){

            this.isRunning = true;
            this.startId = 1;
        }

        //cant think of anything else, just to catch the odd event
        else{

        }





        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){
        //tell the user that we stopped
        Log.e("on destroyed called", "ta da");

        super.onDestroy();
        this.isRunning = false;
    }
}