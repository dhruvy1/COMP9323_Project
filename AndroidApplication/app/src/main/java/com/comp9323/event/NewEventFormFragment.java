package com.comp9323.event;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.comp9323.main.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewEventFormFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "NewEventFormFragment";
    private final int EVENT_STARTDATE = 0;
    private final int EVENT_ENDDATE = 1;
    private TextView startDate, endDate;
    private Calendar mDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
    private int year, month, day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_event_form, container, false);

        setToolbar(rootView);
        initDates(rootView);
        setDateListeners();

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.new_event_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // handle confirmation button click here
            return true;
        } else if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("New Event");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);
    }

    private void initDates(View v) {
        mDate = Calendar.getInstance();
        year = mDate.get(Calendar.YEAR);
        month = mDate.get(Calendar.MONTH);
        day = mDate.get(Calendar.DAY_OF_MONTH);
        startDate = v.findViewById(R.id.new_event_startdate);
        endDate = v.findViewById(R.id.new_event_enddate);
        startDate.setText(dateFormat.format(mDate.getTime()));
        endDate.setText(dateFormat.format(mDate.getTime()));
    }

    private void setDateListeners() {
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog newDatePicker = new DatePickerDialog(getActivity(), getOnDateSetListener(startDate), year, month, day);
                newDatePicker.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog newDatePicker = new DatePickerDialog(getActivity(), getOnDateSetListener(endDate), year, month, day);
                newDatePicker.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener getOnDateSetListener(final TextView dateText) {
        return (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mDate.set(year, month, day);
                dateText.setText(dateFormat.format(mDate.getTime()));
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        dateFormat.format(c.getTime());
    }
}
