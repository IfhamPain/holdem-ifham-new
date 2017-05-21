package com.example.chavin.myapplication;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class CheckHistory extends AppCompatActivity {
    private TextView msg;
    Long timeStamp;
    UserModel um = new UserModel();
    List<Long> checkHistoryList = new ArrayList<>();
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_history);
            msg = (TextView) findViewById(R.id.hello);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

                    if(timeStampSec > 0 && timeStampSec < 120){
                        msg.setText("You have not brushed your teeth properly");
                    }
                    else if(timeStampSec > 120 && timeStampSec < 300){
                        msg.setText("You have brushed your teeth properly. Yippiee!");
                    }
                    else{
                        msg.setText("Please place your brush properly in the holder");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
       /* ref.addValueEventListener(new ValueEventListener() {
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
