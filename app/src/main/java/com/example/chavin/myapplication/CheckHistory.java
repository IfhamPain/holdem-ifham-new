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



public class CheckHistory extends AppCompatActivity {
    private TextView msg;
    String test;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_history);
        msg = (TextView) findViewById(R.id.hello);
            ref = FirebaseDatabase.getInstance().getReference().child("Tomato");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                     String text = dataSnapshot.getValue(String.class);

                    msg.setText(text);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

    }
}
