package com.comp9323.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.comp9323.data.beans.Event;
import com.comp9323.main.R;

import java.util.ArrayList;

/**
 * Created by timta on 17/09/2017.
 */

public class EventArrayAdapter extends ArrayAdapter<Event> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public EventArrayAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO:: CLEANUP CODE
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.event_item_name);
        TextView tvHome = (TextView) convertView.findViewById(R.id.event_item_start);
        TextView tvLoc = (TextView) convertView.findViewById(R.id.event_item_location);
        // Populate the data into the template view using the data object
        tvName.setText(event.getName());
        tvHome.setText("Date: ".concat(event.getStartDate().concat(" -- ").concat(event.getEndDate()).concat(" Time: ").concat(event.getStartTime()).concat(" -- ").concat(event.getEndTime())));
        tvLoc.setText(event.getPlaceName());
        // Return the completed view to render on screen
        return convertView;
    }

}
