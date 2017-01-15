package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 2016-09-14.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class EventsExpandListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Event>> _listDataChild;

    public EventsExpandListAdapter(Context context, List<String> listDataHeader,
                                   HashMap<String, List<Event>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Event listItem = (Event) getChild(groupPosition, childPosition);
        Context context = MyApplication.getContext();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.events_explist_item, null);
        }

        TextView eventCategory = (TextView) convertView.findViewById(R.id.txt_careteam_explist_name);
        TextView eventDate = (TextView) convertView.findViewById(R.id.txt_event_explist_date);
        TextView eventTime = (TextView) convertView.findViewById(R.id.txt_careteam_explist_relationship);
        ImageView eventImage = (ImageView) convertView.findViewById(R.id.img_event_explist_avatar);

        if (listItem.sub_category.matches("create_new")){
            eventCategory.setText(context.getString(context.getResources().getIdentifier("create_new_event", "string", context.getPackageName())));
            eventImage.setImageResource(context.getResources().getIdentifier("add_event", "drawable", context.getPackageName()));
            eventTime.setText("");
            eventDate.setText("");
        }else if (!listItem.sub_category.isEmpty()){
            eventCategory.setText(context.getString(context.getResources().getIdentifier("event_"+listItem.sub_category, "string", context.getPackageName())));
            if (!eventDate.getText().toString().matches(MyApplication.getContext().getString(R.string.event_pick_date))){
                if (listItem.date.length() >= 10){
                    eventDate.setText(listItem.date.substring(0,10));
                }
            }
            if (!eventTime.getText().toString().matches(MyApplication.getContext().getString(R.string.event_pick_time))){
                if (listItem.time.length() >= 5){
                    eventTime.setText(listItem.time.substring(0,5));
                }
            }
            eventImage.setImageResource(context.getResources().getIdentifier("event_"+listItem.sub_category+"_bubble", "drawable", context.getPackageName()));
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.events_explist_group, null);
        }

        TextView listHeader = (TextView) convertView
                .findViewById(R.id.events_list_header);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}