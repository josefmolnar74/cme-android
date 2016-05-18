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
public class ManageCareTeamAdapter extends ArrayAdapter<Patients> {

    private ArrayList<Patients> patientList;

    public ManageCareTeamAdapter(Context context, ArrayList<Patients> patientList){
        super(context, R.layout.careteamlistview, patientList);
        this.patientList = patientList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.careteamlistview, parent, false);


        TextView nameTextView = (TextView) customlistview.findViewById(R.id.careteamname);


        nameTextView.setText(patientList.get(position).patient_name);
        return customlistview;
    }
}
