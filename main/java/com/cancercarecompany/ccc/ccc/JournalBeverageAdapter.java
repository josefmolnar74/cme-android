package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-22.
 */
public class JournalBeverageAdapter extends ArrayAdapter<String> {

    ArrayList<String> beverageList;

    public JournalBeverageAdapter(Context context, ArrayList<String> beverageList){
        super(context, R.layout.journal_beverage_gridview, beverageList);
        this.beverageList = beverageList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customGridview = inflater.inflate(R.layout.journal_beverage_gridview, parent, false);
        ImageView glassImageView = (ImageView) customGridview.findViewById(R.id.img_journal_listview_event);

        if (beverageList.get(position) == "empty"){
            // set empty glass image
            glassImageView.setImageResource(R.drawable.journal_glass_empty);
        } else if (beverageList.get(position) == "full"){
            // set full glass image
            glassImageView.setImageResource(R.drawable.journal_glass_full);
        }
        return customGridview;
    }
}