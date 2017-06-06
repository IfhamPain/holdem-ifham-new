package com.example.chavin.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    String hour_string = "hh";
    String minute_string = "mm";
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
    int clickcount = 0;
    ListView listView;
    List<String> alarmList = new ArrayList<>();
    HashMap<String, Object> map1 = new HashMap<>();
    HashMap<Object, String> map2 = new HashMap<>();
    List<String> keyList = new ArrayList<>();
    UserModel um = new UserModel();
    String key = "";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        this.context = this;

        final Calendar calendar = Calendar.getInstance();
        //initializing the alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //inititalizing the time picker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        //initialising the status
        status = (TextView) findViewById(R.id.status);

        //create an instance of a calander


        //initialize start button
        Button alarm_on = (Button) findViewById(R.id.buttonNewAlarm);

        final Button btnDelete = (Button) findViewById(R.id.buttonDelete);

        alarm1 = (TextView) findViewById(R.id.alarm1);
        alarm2 = (TextView) findViewById(R.id.alarm2);

        final Intent my_intent = new Intent(this.context, Alarm_receiver.class);

        //Retrieving the alarm list into a hashmap
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Alarm");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //Storing the values retrieved into map1
                    map1.put(childSnapshot.getKey(), childSnapshot.getValue());


                }

                Log.d("map1", map1.toString());

                List keys = new ArrayList(map1.keySet());
                Collections.sort(keys);
                Collection<Object> td = map1.values();
                Map<String, Object> map2 = new TreeMap<String, Object>(map1);
                Set set = map2.entrySet();
                //Ordering the array list in FIFO
                Iterator iterator2 = set.iterator();
                while(iterator2.hasNext()) {
                    Map.Entry me2 = (Map.Entry)iterator2.next();
                    System.out.print(me2.getKey() + ": ");
                    //Storing the alarm values in alarmList array
                    alarmList.add(me2.getValue().toString().replaceAll("[{}]", "" ));
                    //Storing the key values in keyList array
                    keyList.add(me2.getKey().toString());
                }

                Log.d("td", td.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AlarmActivity.this,
                android.R.layout.simple_list_item_1, alarmList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                //get the int values of hour and minute
                hour = (alarm_timepicker.getHour());
                minute = (alarm_timepicker.getMinute());

                //convert the int values to string
                if (hour < 10 && hour >= 0) {
                    hour_string = "0" + String.valueOf(hour);
                } else {
                    hour_string = String.valueOf(hour);
                }
                if (minute < 10 && minute >= 0) {
                    minute_string = "0" + String.valueOf(minute);
                } else {
                    minute_string = String.valueOf(minute);
                }


                //Collection<Object> td = map1.values();
                //Log.d("td", td.toString());

                TextView tv = (TextView) view;
                key = keyList.get(i);
                String newAlarm = tv.getText().toString();
                um.setAlarm(hour_string + ":" + minute_string);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                DatabaseReference ref = databaseReference.child("Alarm").child(key);
                ref.setValue(um);
                //alarm1.setText(key);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                        DatabaseReference ref = databaseReference.child("Alarm").child(key);
                        ref.removeValue();


                    }});



                UserModel um = new UserModel();

                String tempTime = (hour_string + ":" + minute_string);
                um.setAlarm(tempTime);

                tv.setText("alarm=" + hour_string + ":" + minute_string);
            }


        });
        listView.setAdapter(adapter);
        //create an onclick listener

        //initialize end button
        Button alarm_off = (Button) findViewById(R.id.buttonStopAlarm);
        //create an onclick listener
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //method that changes the status textbox
                set_alarm_text("Alarm off");

                //cancel the alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into my_intent
                //tells the clock that you pressed the "alarm off" button
                my_intent.putExtra("extra", "alarm off");

                //stop the ringtone
                sendBroadcast(my_intent);
            }
        });

        //Onclick listener for alarm_on button
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calendar.add(Calendar.DAY_OF_YEAR, 1);
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());
                calendar.set(Calendar.SECOND, 0);


                //get the int values of hour and minute
                hour = (alarm_timepicker.getHour());
                minute = (alarm_timepicker.getMinute());

                //convert the int values to string
                if (hour < 10 && hour >= 0) {
                    hour_string = "0" + String.valueOf(hour);
                } else {
                    hour_string = String.valueOf(hour);
                }
                if (minute < 10 && minute >= 0) {
                    minute_string = "0" + String.valueOf(minute);
                } else {
                    minute_string = String.valueOf(minute);
                }


                //method that changes the status Textbox
                //set_alarm_text("Alarm set to:" + hour_string + ":" + minute_string);
                set_alarm_text("Alarm is set");


                my_intent.putExtra("extra", "alarm on");
                //create a pending intent to delay the intent until the specified calender time
                pending_intent = PendingIntent.getBroadcast(AlarmActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);


                //set alarm variable
                um.setAlarm(hour_string + ":" + minute_string);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                DatabaseReference ref = databaseReference.child("Alarm").push();
                key1 = ref.getKey();
                ref.setValue(um);

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
