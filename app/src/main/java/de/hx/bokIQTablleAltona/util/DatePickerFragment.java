package de.hx.bokIQTablleAltona.util;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    TextView datumView;

    public DatePickerFragment(TextView datumView) {
        this.datumView = datumView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        datumView.setText(DateFormat.format("dd.MM.yyyy ", c.getTime()));
    }
}