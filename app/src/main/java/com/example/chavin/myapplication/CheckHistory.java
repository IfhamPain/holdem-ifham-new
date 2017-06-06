package com.example.chavin.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class CheckHistory extends AppCompatActivity {
    private TextView msg;
    Long timeStamp;
    UserModel um = new UserModel();
    List<Long> checkHistoryList = new ArrayList<>();
    DatabaseReference ref;
    List<String> alarmList = new ArrayList<>();
    List<String> keyList = new ArrayList<>();
    List<String> timeStampList = new ArrayList<>();
    List<String> timeStampkeyList = new ArrayList<>();
    HashMap<String, Object> map1 = new HashMap<>();
    HashMap<Object, String> map2 = new HashMap<>();
    HashMap<String, Object> map3 = new HashMap<>();
    HashMap<Object, String> map4 = new HashMap<>();
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_history);
            msg = (TextView) findViewById(R.id.hello);

        //Retrieving the alarm list into a hashmap
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Alarm");

        ref3.addValueEventListener(new ValueEventListener() {
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

        /* DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("timestamp");

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    //Storing the values retrieved into map1
                    map3.put(childSnapshot.getKey(), childSnapshot.getValue());


                }

                Log.d("map3", map3.toString());

                List keys = new ArrayList(map3.keySet());
                Collections.sort(keys);
                Collection<Object> td = map3.values();
                Map<String, Object> map4 = new TreeMap<String, Object>(map3);
                Set set = map3.entrySet();
                //Ordering the array list in FIFO
                Iterator iterator2 = set.iterator();
                while(iterator2.hasNext()) {
                    Map.Entry me2 = (Map.Entry)iterator2.next();
                    System.out.print(me2.getKey() + ": ");
                    //Storing the alarm values in alarmList array
                    timeStampList.add(me2.getValue().toString());
                    //Storing the key values in keyList array
                    timeStampkeyList.add(me2.getKey().toString());
                }

                Log.d("td", td.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("timestamp");

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child :  children){
                        Long checkHistory = child.getValue(Long.class);
                        checkHistoryList.add(checkHistory);
                    }
                    //Getting arraylist size
                    int n = checkHistoryList.size();

                    //Calculating the difference between the 2 most reason timestamps
                    timeStamp =  checkHistoryList.get(n-1) - checkHistoryList.get(n-2);

                    //Converting the timeStamp to seconds
                    Long timeStampSec = timeStamp/1000;

                    //Saving the value for future use (if needed)
                    um.setTimeS(timeStampSec);


                    long timeInMillis = checkHistoryList.get(n-2);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timeInMillis);
                    String timeStampStr = calendar.getTime().toString();
                    //System.out.println("Date1:"+calendar.getTime().toString());
                    time = alarmList.get(alarmList.size() - 1).replaceAll("alarm=", "");

                    String[] timeStampStrArray = timeStampStr.split(" ");

                    String[] str = timeStampStrArray[3].split(":");
                    String strTimeStamp = str[0] + ":" + str[1];


                    //msg.setText("You have brushed your teeth properly. Yippiee!");
                    if(time.equalsIgnoreCase(strTimeStamp)) {
                        if (timeStampSec >= 0 && timeStampSec < 120) {
                            msg.setText("You have not brushed your teeth properly");
                        } else if (timeStampSec >= 120 && timeStampSec <= 300) {
                            msg.setText("You have brushed your teeth properly. Yippiee!");
                        } else {
                            msg.setText("Please place your brush properly in the holder");
                        }
                    }
                    else{
                        msg.setText("You have missed brushing teeth at : " + alarmList.get(alarmList.size()-1));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkHistoryList.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Long checkHistory = postSnapshot.getValue(Long.class);
                    checkHistoryList.add(checkHistory);

                    timeStamp =  checkHistoryList.get(0);
                    Date date=new Date(timeStamp);
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String text = sfd.format(date);
                    msg.setText(text);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

    }
}
