package com.example.chavin.myapplication;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Chavin on 6/6/2017.
 */

public class Appointment extends AppCompatActivity {


    EditText txt_NewAppointmentDate;
    Button buttonMakeAppointment;
    HashMap<String, Object> map1 = new HashMap<>();
    ListView LV;
    List<String> keyList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    Button btnDelete;
    String key = "";
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_appointment);
        txt_NewAppointmentDate = (EditText) findViewById(R.id.txtNewAppointmentDate);
        buttonMakeAppointment = (Button) findViewById(R.id.buttonMakeAppointment);
        LV = (ListView) findViewById(R.id.doctorList);

        arrayAdapter = new ArrayAdapter<String>(Appointment.this, android.R.layout.simple_list_item_1, dateList);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Dentists");
        //Query query = ref.orderByKey();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    map1.put(childSnapshot.getKey(), childSnapshot.getValue());


                }

                Log.d("map1", map1.toString());
                List keys = new ArrayList(map1.keySet());
                Collections.sort(keys);
                Collection<Object> td = map1.values();
                Map<String, Object> map2 = new TreeMap<String, Object>(map1);
                Set set = map2.entrySet();
                Iterator iterator2 = set.iterator();
                while (iterator2.hasNext()) {
                    Map.Entry me2 = (Map.Entry) iterator2.next();
                    System.out.print(me2.getKey() + ": ");
                    dateList.add(me2.getValue().toString().replaceAll("[{}]", ""));
                    keyList.add(me2.getKey().toString());
                }
                //Removing special characters to make the return data neat
                //String alarmLongList = td.toString();
                //String alarmLongList = td.toString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("[{}]", " ");

                Log.d("td", td.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LV.setAdapter(arrayAdapter);

        View.OnClickListener makeAppointment = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Appointment.this, "Successfully Sent Notification to the Dentist you selected", Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_profile);
            }
        };
        buttonMakeAppointment.setOnClickListener(makeAppointment);

    }





    public void onStart() {
        super.onStart();

       EditText txtNewAppointmentDate = (EditText) findViewById(R.id.txtNewAppointmentDate);
        txt_NewAppointmentDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialogNewAppointment dialog = new DateDialogNewAppointment(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }


}
