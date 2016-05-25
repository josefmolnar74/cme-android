package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class ManageCareTeamMemberAdapter extends ArrayAdapter<Care_team> {

    private ArrayList<Care_team> patientList;

    public ManageCareTeamMemberAdapter(Context context, ArrayList<Care_team> care_teams){
        super(context, R.layout.careteamlistview , care_teams);
       this.patientList = care_teams;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.careteamlistview, parent, false);


        TextView nameTextView = (TextView) customlistview.findViewById(R.id.careteamname);


       nameTextView.setText(patientList.get(position).first_name.toString());



        return customlistview;
    }
}
