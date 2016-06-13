package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class CareTeamFamilyAdapter extends ArrayAdapter<CareTeamMember> {

    ArrayList<CareTeamMember> familyList;

    public CareTeamFamilyAdapter(Context context, ArrayList<CareTeamMember> familyList){
        super(context, R.layout.careteamlistview, familyList);
        this.familyList = familyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.careteamlistview, parent, false);
        TextView nameTextView = (TextView) customlistview.findViewById(R.id.careteamname);
        nameTextView.setText(familyList.get(position).first_name);
        return customlistview;
    }
}
