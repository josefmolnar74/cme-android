package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-07-20.
 */
public class JournalEventAdapter extends ArrayAdapter<Event> {

    ArrayList<Event> eventList;

    public JournalEventAdapter(Context context, ArrayList<Event> eventList){
        super(context, R.layout.journal_events_listview, eventList);
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customListView = inflater.inflate(R.layout.journal_events_listview, parent, false);
        Context context = MyApplication.getContext();
        ImageView eventImage = (ImageView) customListView.findViewById(R.id.img_home_events_event1);
        TextView subCategory = (TextView) customListView.findViewById(R.id.txt_home_events_sub_category1);
        TextView date = (TextView) customListView.findViewById(R.id.txt_home_events_date1);
        TextView time = (TextView) customListView.findViewById(R.id.txt_home_events_time);

        subCategory.setText(eventList.get(position).sub_category);
        date.setText(eventList.get(position).date.split("T")[0]);
        time.setText(eventList.get(position).time);

        subCategory.setText(context.getResources().getString(context.getResources().getIdentifier("event_"+eventList.get(position).sub_category, "string", context.getPackageName())));
        eventImage.setImageResource(context.getResources().getIdentifier("event_"+eventList.get(position).sub_category+"_bubble", "drawable", context.getPackageName()));

        return customListView;
    }
}