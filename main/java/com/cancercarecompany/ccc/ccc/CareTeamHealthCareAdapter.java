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
public class CareTeamHealthCareAdapter extends ArrayAdapter<CareTeamMember> {

    private ArrayList<CareTeamMember> healthCareGrid;

    public CareTeamHealthCareAdapter(Context context, ArrayList<CareTeamMember> healthCareCareTeam){
        super(context, R.layout.careteamgridview , healthCareCareTeam);
       this.healthCareGrid = healthCareCareTeam;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customGridView = inflater.inflate(R.layout.careteamgridview, parent, false);
        TextView firstNameTextView = (TextView) customGridView.findViewById(R.id.careTeamGridFirstName);
        TextView emailTextView = (TextView) customGridView.findViewById(R.id.careTeamGridEmail);
        firstNameTextView.setText(healthCareGrid.get(position).first_name.toString());
        emailTextView.setText(healthCareGrid.get(position).email.toString());
        return customGridView;
    }
}
