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
        super(context, R.layout.careteam_member_gridview, familyList);
        this.familyList = familyList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customlistview = inflater.inflate(R.layout.careteam_member_gridview, parent, false);
        TextView nameTextView = (TextView) customlistview.findViewById(R.id.txt_careteam_grid_firstname);
        nameTextView.setText(familyList.get(position).first_name);
        TextView relationshipTextView = (TextView) customlistview.findViewById(R.id.txt_careteam_grid_relation);
        relationshipTextView.setText(familyList.get(position).relationship);
        ImageView familyMemberImage = (ImageView) customlistview.findViewById(R.id.careTeamFamilyImage);
        // if invited whom has not accepted yet
        switch(familyList.get(position).avatar){
            case 1:
                familyMemberImage.setImageResource(R.drawable.family_avatar_1);
                break;
            case 2:
                familyMemberImage.setImageResource(R.drawable.family_avatar_2);
                break;
            case 3:
                familyMemberImage.setImageResource(R.drawable.family_avatar_3);
                break;
            case 4:
                familyMemberImage.setImageResource(R.drawable.family_avatar_4);
                break;
            case 5:
                familyMemberImage.setImageResource(R.drawable.family_avatar_5);
                break;
            case 6:
                familyMemberImage.setImageResource(R.drawable.family_avatar_6);
                break;
            case 7:
                familyMemberImage.setImageResource(R.drawable.family_avatar_7);
                break;
            case 8:
                familyMemberImage.setImageResource(R.drawable.family_avatar_8);
                break;
            case 9:
                familyMemberImage.setImageResource(R.drawable.family_avatar_9);
                break;
            case 10:
                familyMemberImage.setImageResource(R.drawable.family_avatar_10);
                break;
            case 11:
                familyMemberImage.setImageResource(R.drawable.family_avatar_11);
                break;
            case 12:
                familyMemberImage.setImageResource(R.drawable.family_avatar_12);
                break;
            case 13:
                familyMemberImage.setImageResource(R.drawable.family_avatar_13);
                break;
            case 14:
                familyMemberImage.setImageResource(R.drawable.family_avatar_14);
                break;
            case 15:
                familyMemberImage.setImageResource(R.drawable.family_avatar_15);
                break;
            case 16:
                familyMemberImage.setImageResource(R.drawable.family_avatar_16);
                break;
            case 17:
                familyMemberImage.setImageResource(R.drawable.family_avatar_17);
                break;
            case 18:
                familyMemberImage.setImageResource(R.drawable.family_avatar_18);
                break;
        }
        return customlistview;
    }
}
