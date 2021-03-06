package com.example.chavin.myapplication;

/**
 * Created by Chavin on 5/4/2017.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("ValidFragment")
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtdate;


    public DateDialog(View view) {
        txtdate = (EditText) view;


    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // Use the current date as the default date in the dialog
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        //show to the selected date in the text box
        String date = day + "-" + (month + 1) + "-" + year;

        txtdate.setText(date);


        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.set(year, month, day); // Now use today date.
        c.add(Calendar.DATE, 30); // Adding 30 days
        String output = sdf.format(c.getTime());
        String outputFirebase = ("You have to replace your toothbrush on: " + output);
        UserModel um = new UserModel();
        um.setDate(outputFirebase);
        txt_NewBrushDate.setText("You have to replace your toothbrush on: " + output);*/


    }


}
