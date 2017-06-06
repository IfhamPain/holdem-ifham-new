package com.example.chavin.myapplication;

/**
 * Created by Chavin on 4/4/2017.
 */

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DateDialogBrush extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txt_NewBrushDate;

    public DateDialogBrush(View view) {
        txt_NewBrushDate = (EditText) view;

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



        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        c.set(year, month, day); // Now use today date.
        c.add(Calendar.DATE, 30); // Adding 30 days
        String output = sdf.format(c.getTime());
        String outputFirebase = ("You have to replace your toothbrush on: " + output);
        UserModel um = new UserModel();
        um.setDate(outputFirebase);
        txt_NewBrushDate.setText("You have to replace your toothbrush on: " + output);


    }


}
