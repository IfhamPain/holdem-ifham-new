package com.example.chavin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewBrush extends AppCompatActivity {
    private TextView daysRemaining;
    private Button buttonAddNewBrush;
    private DatabaseReference databaseReference;
    UserModel um = new UserModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_newbrush);
        daysRemaining = (TextView) findViewById(R.id.days_remaining);
        buttonAddNewBrush = (Button) findViewById(R.id.add_new_brush);
        buttonAddNewBrush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                um.setDate(date);
                databaseReference.child(user.getUid()).child("User Detail").child("Date").setValue(um);
              
            }
        });



        Calendar c=new GregorianCalendar();
        c.add(Calendar.DATE, 30);
        Date d=c.getTime();
        String f = "Replace your tooth brush on "+ c.getTime().toString();
        try {
            daysRemaining.setText(f);
        }
        catch(Exception e){

        }




    }

}
