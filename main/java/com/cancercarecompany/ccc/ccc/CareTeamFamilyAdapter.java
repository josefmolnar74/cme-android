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
 * Created by robinlarsson on 27/04/16.
 */
public class CareTeamFamilyAdapter extends ArrayAdapter<CareTeamMember> {

    ArrayList<CareTeamMember> familyList;

    public CareTeamFamilyAdapter(Context context, ArrayList<CareTeamMember> familyList){
        super(context, R.layout.careteam_family_gridview, familyList);
        this.familyList = familyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.careteam_family_gridview, parent, false);
        TextView nameTextView = (TextView) customlistview.findViewById(R.id.txt_careteam_grid_firstname);
        nameTextView.setText(familyList.get(position).first_name);
        TextView relationshipTextView = (TextView) customlistview.findViewById(R.id.txt_careteam_grid_relation);
        relationshipTextView.setText(familyList.get(position).relationship);
        ImageView familyMemberImage = (ImageView) customlistview.findViewById(R.id.careTeamFamilyImage);
        // if invited whom has not accepted yet
        if (familyList.get(position).person_ID == 0){
            familyMemberImage.setImageResource(R.drawable.careteam_family_invited);
        }
        return customlistview;
    }
}
