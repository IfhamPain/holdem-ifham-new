package com.example.chavin.myapplication;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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

public class NewBrush extends AppCompatActivity {

    EditText txt_NewBrushDate;
    Button add_new_Brush;
    ListView LV;
    UserModel um = new UserModel();
    ArrayList<String> brushes;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference dref;
    ArrayList<String> list = new ArrayList<>();
    HashMap<String, Object> map1 = new HashMap<>();
    List<String> keyList = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    Button btnDelete;
    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_tooth_brush);
        txt_NewBrushDate = (EditText) findViewById(R.id.txt_NewBrushDate);
        add_new_Brush = (Button) findViewById(R.id.add_new_Brush);
        btnDelete = (Button) (findViewById(R.id.buttonDelete));
        LV = (ListView) findViewById(R.id.newBrushList);

        arrayAdapter = new ArrayAdapter<String>(NewBrush.this, android.R.layout.simple_list_item_1, dateList);

        //Onclick listener for the list items
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView tv = (TextView) view;
                key = keyList.get(i);

                View.OnClickListener deleteListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                        DatabaseReference ref = databaseReference.child("Brushes").child(key);
                        ref.removeValue();


                    }
                };
                btnDelete.setOnClickListener(deleteListener);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("Brushes");
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


        View.OnClickListener addListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateList.add(txt_NewBrushDate.getText().toString());

                arrayAdapter.notifyDataSetChanged();

                String brushDate = txt_NewBrushDate.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.child(user.getUid()).child("Brushes").push().setValue(brushDate);

            }
        };


        add_new_Brush.setOnClickListener(addListner);

        LV.setAdapter(arrayAdapter);


    }


    public void onStart() {
        super.onStart();

        EditText txtDate = (EditText) findViewById(R.id.txt_NewBrushDate);
        txt_NewBrushDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialogBrush dialog = new DateDialogBrush(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");

                }
            }

        });
    }


}




