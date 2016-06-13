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

    private ArrayList<CareTeamMember> healthCareList;

    public CareTeamHealthCareAdapter(Context context, ArrayList<CareTeamMember> care_team){
        super(context, R.layout.careteamlistview , care_team);
       this.healthCareList = care_team;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.careteamlistview, parent, false);
        TextView nameTextView = (TextView) customlistview.findViewById(R.id.careteamname);
        nameTextView.setText(healthCareList.get(position).first_name.toString());
        return customlistview;
    }
}
