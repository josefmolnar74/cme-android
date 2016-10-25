package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 2016-09-14.
 */

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CareTeamExpandListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<CareTeamExpandListItem>> _listDataChild;

    public CareTeamExpandListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<CareTeamExpandListItem>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final CareTeamExpandListItem listItem = (CareTeamExpandListItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.careteam_explist_item, null);
        }

        TextView txtListName = (TextView) convertView
                .findViewById(R.id.txt_event_explist_category);
        txtListName.setText(listItem.name);

        TextView txtListRelationship = (TextView) convertView
                .findViewById(R.id.txt_event_explist_time);
        txtListRelationship.setText(listItem.relationship);


        ImageView avatar = (ImageView) convertView.findViewById(R.id.img_careteam_family_avatar);
        if (listItem.type == "healthcare") {
            switch (listItem.avatar) {
                case 1:
                    avatar.setImageResource(R.drawable.avatar_healthcare_doctor_female);
                    break;
                case 2:
                    avatar.setImageResource(R.drawable.avatar_healthcare_nurse);
                    break;
                case 3:
                    avatar.setImageResource(R.drawable.avatar_healthcare_anestetist);
                    break;
                case 4:
                    avatar.setImageResource(R.drawable.avatar_healthcare_doctor_male);
                    break;
                case 5:
                    avatar.setImageResource(R.drawable.avatar_healthcare_surgeon);
                    break;
            }
        }
        else if ((listItem.type == "family") || (listItem.type == "invite")) {
            switch (listItem.avatar) {
                case 1:
                    avatar.setImageResource(R.drawable.family_avatar_1);
                    break;
                case 2:
                    avatar.setImageResource(R.drawable.family_avatar_2);
                    break;
                case 3:
                    avatar.setImageResource(R.drawable.family_avatar_3);
                    break;
                case 4:
                    avatar.setImageResource(R.drawable.family_avatar_4);
                    break;
                case 5:
                    avatar.setImageResource(R.drawable.family_avatar_5);
                    break;
                case 6:
                    avatar.setImageResource(R.drawable.family_avatar_6);
                    break;
                case 7:
                    avatar.setImageResource(R.drawable.family_avatar_7);
                    break;
                case 8:
                    avatar.setImageResource(R.drawable.family_avatar_8);
                    break;
                case 9:
                    avatar.setImageResource(R.drawable.family_avatar_9);
                    break;
                case 10:
                    avatar.setImageResource(R.drawable.family_avatar_10);
                    break;
                case 11:
                    avatar.setImageResource(R.drawable.family_avatar_11);
                    break;
                case 12:
                    avatar.setImageResource(R.drawable.family_avatar_12);
                    break;
                case 13:
                    avatar.setImageResource(R.drawable.family_avatar_13);
                    break;
                case 14:
                    avatar.setImageResource(R.drawable.family_avatar_14);
                    break;
                case 15:
                    avatar.setImageResource(R.drawable.family_avatar_15);
                    break;
                case 16:
                    avatar.setImageResource(R.drawable.family_avatar_16);
                    break;
                case 17:
                    avatar.setImageResource(R.drawable.family_avatar_17);
                    break;
                case 18:
                    avatar.setImageResource(R.drawable.family_avatar_18);
                    break;
            }
        }
        else{
            avatar.setImageResource(R.drawable.addcontact);
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.careteam_explist_group, null);
        }

        TextView listHeader = (TextView) convertView
                .findViewById(R.id.careteam_list_header);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}