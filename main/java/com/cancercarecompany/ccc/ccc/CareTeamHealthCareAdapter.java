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
public class CareTeamHealthCareAdapter extends ArrayAdapter<HealthCare> {

    private ArrayList<HealthCare> healthcareList;

    public CareTeamHealthCareAdapter(Context context, ArrayList<HealthCare> healthcareList){
        super(context, R.layout.careteam_healthcare_gridview, healthcareList);
       this.healthcareList = healthcareList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customGridView = inflater.inflate(R.layout.careteam_healthcare_gridview, parent, false);
        TextView title = (TextView) customGridView.findViewById(R.id.careTeam_gridview_title);
        TextView phoneNumber = (TextView) customGridView.findViewById(R.id.careTeam_gridview_phonenumber);
        title.setText(healthcareList.get(position).title.toString());
        phoneNumber.setText(healthcareList.get(position).name.toString());
        return customGridView;
    }
}
