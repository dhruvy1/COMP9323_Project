package com.comp9323.food.fooddeal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import android.widget.EditText;
import android.widget.TimePicker;

import com.comp9323.data.DateTimeConverter;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.main.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FoodDealNewFormFragment extends DialogFragment {

    private Listener listener;

    private EditText titleEt;
    private EditText locationEt;
    private EditText messageEt;

    private TextInputEditText startDate;
    private TextInputEditText endDate;
    private TextInputEditText startTime;
    private TextInputEditText endTime;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d yyyy");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");

    private Calendar mDate;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_food_deal_form, container, false);

        setHasOptionsMenu(true);

        titleEt = view.findViewById(R.id.new_food_deal_title_et);
        locationEt = view.findViewById(R.id.new_food_deal_location_et);
        messageEt = view.findViewById(R.id.new_food_deal_message_et);

        startDate = view.findViewById(R.id.new_fd_startdate);
        endDate = view.findViewById(R.id.new_fd_enddate);
        startTime = view.findViewById(R.id.new_fd_starttime);
        endTime = view.findViewById(R.id.new_fd_endtime);

        initToolBar(view);
        initDates();
        setDateListeners();
        setTimeListeners();

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.new_food_deal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                listener.onSaveClicked(makeFoodDeal());
                dismiss();
                return true;
            case android.R.id.home:
                dismiss();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Initialises the toolbar to allow us to customise it for the fragment
     *
     * @param view the parent layout
     */
    private void initToolBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("New Food Deal"); // set the name of the toolbar
        toolbar.getMenu().clear();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // set the action bar to our custom toolbar

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // show the return own button
            actionBar.setHomeButtonEnabled(true); // allow it to be clicked
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp); // change the icon to our icon
        }
    }

    /**
     * Initialise the start date, time and end date, time on the screen
     */
    private void initDates() {
        mDate = Calendar.getInstance();
        year = mDate.get(Calendar.YEAR);
        month = mDate.get(Calendar.MONTH);
        day = mDate.get(Calendar.DAY_OF_MONTH);
        hour = mDate.get(Calendar.HOUR_OF_DAY);
        minute = mDate.get(Calendar.MINUTE);

        startDate.setText(dateFormat.format(mDate.getTime()));
        endDate.setText(dateFormat.format(mDate.getTime()));
        startTime.setText(timeFormat.format(mDate.getTime()));
        endTime.setText(timeFormat.format(mDate.getTime()));
    }

    /**
     * Show the date picker when the date is clicked
     */
    private void setDateListeners() {
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create date picker
                DatePickerDialog newDatePicker = new DatePickerDialog(getActivity(),
                        getOnDateSetListener(startDate), year, month, day);
                newDatePicker.show(); // show date picker
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create date picker
                DatePickerDialog newDatePicker = new DatePickerDialog(getActivity(),
                        getOnDateSetListener(endDate), year, month, day);
                newDatePicker.show(); // show date picker
            }
        });
    }

    /**
     * Show the time picker when the time is clicked
     */
    private void setTimeListeners() {
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create the time picker
                TimePickerDialog newTimePicker = new TimePickerDialog(getActivity(),
                        getOnTimeSetListener(startTime), hour, minute, false);
                newTimePicker.show(); // show the time picker
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create the time picker
                TimePickerDialog newTimePicker = new TimePickerDialog(getActivity(),
                        getOnTimeSetListener(endTime), hour, minute, false);
                newTimePicker.show(); // show the time picker
            }
        });
    }

    /**
     * Format the date picker to the current date
     *
     * @param dateText the view to show the selected date
     * @return the new date listener
     */
    private DatePickerDialog.OnDateSetListener getOnDateSetListener(final TextInputEditText dateText) {
        return (new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mDate.set(year, month, day);
                dateText.setText(dateFormat.format(mDate.getTime()));
            }
        });
    }

    /**
     * Format the time picker to show the current time
     *
     * @param dateText the view to show the selected time
     * @return the new time listener
     */
    private TimePickerDialog.OnTimeSetListener getOnTimeSetListener(final TextInputEditText dateText) {
        return (new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                mDate.set(Calendar.HOUR_OF_DAY, hour);
                mDate.set(Calendar.MINUTE, minute);
                dateText.setText(timeFormat.format(mDate.getTime()));
            }
        });
    }

    /**
     * Creates a new food deal object
     *
     * @return an instance of the newly created food deal
     */
    private FoodDeal makeFoodDeal() {
        FoodDeal fd = new FoodDeal();
        fd.setTitle(titleEt.getText().toString());
        fd.setLocation(locationEt.getText().toString());
        fd.setMessage(messageEt.getText().toString());

        String fdStartDate = startDate.getText().toString();
        String fdEndDate = endDate.getText().toString();
        String fdStartTime = startTime.getText().toString();
        String fdEndTime = endTime.getText().toString();

        fdStartDate = DateTimeConverter.convertA2SDate(fdStartDate);
        fdEndDate = DateTimeConverter.convertA2SDate(fdEndDate);
        fdStartTime = DateTimeConverter.convertA2STime(fdStartTime);
        fdEndTime = DateTimeConverter.convertA2STime(fdEndTime);

        fd.setStartDate(fdStartDate);
        fd.setEndDate(fdEndDate);
        fd.setStartTime(fdStartTime);
        fd.setEndTime(fdEndTime);

        return fd;
    }

    public interface Listener {
        /**
         * When the save button is clicked, the the listener handles the new food deal
         *
         * @param foodDeal the newly saved food deal
         */
        void onSaveClicked(FoodDeal foodDeal);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
