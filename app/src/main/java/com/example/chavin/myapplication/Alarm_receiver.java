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

        //fetch extra strings from the intent
        String get_your_string = intent.getExtras().getString("extra");

        Log.e("what is the key", get_your_string);

        //create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtoneplayingService.class);

        //pass the extra string from main activity to the the ringtoneplayingservice
        service_intent.putExtra("extra", get_your_string);

        //start the ringtone service
        context.startService(service_intent);
    }


}