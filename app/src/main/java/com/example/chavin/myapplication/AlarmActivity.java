package com.example.chavin.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    //to make the alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    Context context;
    PendingIntent pending_intent;
    TextView status;
    TextView alarm1;
    TextView alarm2;
    String key1;
    String key2;
    protected int hour;
    protected int minute;
    //defining firebase auth object
    private FirebaseAuth firebaseAuth;
    int clickcount=0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        this.context = this;

        //initializing the alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //inititalizing the time picker
        alarm_timepicker = (TimePicker)findViewById(R.id.timePicker);

        //initialising the status
        status = (TextView)findViewById(R.id.status);

        //create an instance of a calander
        final Calendar calendar = Calendar.getInstance();

        //initialize start button
        Button alarm_on = (Button)findViewById(R.id.buttonNewAlarm);

        alarm1 = (TextView) findViewById(R.id.alarm1);
        alarm2 = (TextView) findViewById(R.id.alarm2);

        final Intent my_intent = new Intent(this.context, Alarm_receiver.class);


        //create an onclick listner
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickcount++;

                //get the int values of hour and minute
                hour = (alarm_timepicker.getCurrentHour());
                minute = (alarm_timepicker.getCurrentMinute());
                String hour_string = "hh";
                String minute_string = "mm";
                //convert the int values to string
                if (hour < 10 && hour >= 0) {
                    hour_string = "0" + String.valueOf(hour);
                }
                else {
                    hour_string = String.valueOf(hour);
                }
                if(minute<10 && minute >= 0){
                    minute_string = "0" + String.valueOf(minute);
                }
                else {
                    minute_string = String.valueOf(minute);
                }
                //setting hours and minutes
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getCurrentMinute());

                UserModel um = new UserModel();




                //method that changes the status Textbox
                //set_alarm_text("Alarm set to:" + hour_string + ":" + minute_string);
                set_alarm_text("Alarm is set");
                //create a pending intent to delay the intent until the specified calender time
                pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);


                //set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);

                if(clickcount ==1) {

                    //set alarm variable
                    um.setAlarm1(hour_string + ":" + minute_string);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    DatabaseReference ref =  databaseReference.child("Alarm").push();
                    key1 = ref.getKey();
                    ref.setValue(um);

                    alarm1.setText(um.getAlarm1());
                }
                if(clickcount ==2) {
                    //get the int values of hour and minute


                    um.setAlarm2(hour_string + ":" + minute_string);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    DatabaseReference ref = databaseReference.child("Alarm").push();
                    key2 = ref.getKey();
                    ref.setValue(um);

                    alarm2.setText(um.getAlarm2());
                }
                if(clickcount ==3) {
                    //Update Existing Alarm time on Firebase Database
                    um.setAlarm1(hour_string + ":" + minute_string);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    databaseReference.child("Alarm").child(key1).setValue(um);
                    alarm1.setText(um.getAlarm1());
                }
                if(clickcount ==4) {
                    //Update Existing Alarm time on Firebase Database
                    um.setAlarm2(hour_string + ":" + minute_string);
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                    databaseReference.child("Alarm").child(key2).setValue(um);
                    alarm2.setText(um.getAlarm2());
                }
            }
        });

        //initialize end button
        Button alarm_off = (Button)findViewById(R.id.buttonStopAlarm);
        //create an onclick listner
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//method that changes the status textbox
                set_alarm_text("Alarm off");

                //cancel the alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into my_intent
                //tells the clock that you pressed the "alarm off" button
                my_intent.putExtra("extra","alarm off");

                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });

    }

    private void set_alarm_text(String output) {
        status.setText(output);
    }



    @Override
    public void onClick(View view) {

    }

}
