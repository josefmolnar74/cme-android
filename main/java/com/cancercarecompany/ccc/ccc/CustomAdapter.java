package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by robinlarsson on 04/04/16.
 */
public class CustomAdapter extends ArrayAdapter<Care_team> {

    private ArrayList<Care_team> careTeamList;

    public CustomAdapter(Context context, ArrayList<Care_team> careTeamList){
        super(context, R.layout.customlistview, careTeamList);
        this.careTeamList = careTeamList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.customlistview, parent, false);


        TextView nameTextView = (TextView) customlistview.findViewById(R.id.nameTextView);
        TextView relationTextView = (TextView) customlistview.findViewById(R.id.relationTextView);
        LinearLayout customCell = (LinearLayout) customlistview.findViewById(R.id.customCell);

        nameTextView.setText(careTeamList.get(position).first_name);
        relationTextView.setText(careTeamList.get(position).relationship);


        switch (careTeamList.get(position).relationship){

            case "mamma":
                int color = Color.parseColor("#9cc6a6d9");
                customCell.setBackgroundColor(color);
                break;

            case "patient":
                int color2 = Color.parseColor("#8998dc8a");
                customCell.setBackgroundColor(color2);

                break;

            case "pappa":
                int color3 = Color.parseColor("#aea7d2e9");
                customCell.setBackgroundColor(color3);

        }


        return customlistview;
    }
}
