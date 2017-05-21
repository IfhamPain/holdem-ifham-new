package com.example.chavin.myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by user on 07-May-17.
 */

public class Alarm_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("we are in the receiver","yay");

        //create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtoneplayingService.class);

        //start the ringtone service
        context.startService(service_intent);
    }


}
