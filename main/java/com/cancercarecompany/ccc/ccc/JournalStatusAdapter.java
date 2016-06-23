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
 * Created by 23047371 on 2016-06-22.
 */
public class JournalStatusAdapter extends ArrayAdapter<Status> {

    ArrayList<Status> statusList;

    public JournalStatusAdapter(Context context, ArrayList<Status> statusList){
        super(context, R.layout.journal_status_gridview, statusList);
        this.statusList = statusList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customGridview = inflater.inflate(R.layout.journal_status_gridview, parent, false);
        TextView nameTextView = (TextView) customGridview.findViewById(R.id.btn_journal_status_gridview);
        nameTextView.setText(statusList.get(position).time);
        return customGridview;
    }
}