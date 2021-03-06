package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 2016-09-14.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JournalExpandListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<JournalExpandListItem>> _listDataChild;

    public JournalExpandListAdapter(Context context, List<String> listDataHeader,
                                    HashMap<String, List<JournalExpandListItem>> listChildData) {
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

        final JournalExpandListItem listItem = (JournalExpandListItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.journal_expand_list_item, null);
        }

        TextView txtListName = (TextView) convertView.findViewById(R.id.txt_journal_list_name);
        RadioButton journalItemExists = (RadioButton) convertView.findViewById(R.id.rb_sideeffect_exists);
        journalItemExists.setVisibility(View.VISIBLE);
        txtListName.setText(listItem.header);
        RelativeLayout distressLayout = (RelativeLayout) convertView.findViewById(R.id.lay_journal_expand_list_item_distress);
        distressLayout.setVisibility(View.GONE);
        SeekBar distressSeekBar = (SeekBar) convertView.findViewById(R.id.sb_journal_sideeffects_distress_seekbar1);

        if (listItem.healthData != null) {
            if (listItem.healthData.healthdata_ID != -1) {
                journalItemExists.setChecked(true);
            } else {
                journalItemExists.setChecked(false);
            }
        }

        if (listItem.sideeffect != null){
            if (!listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_DISTRESS)){
                if (listItem.sideeffect.sideeffect_ID != -1){
                    journalItemExists.setChecked(true);
                } else{
                    journalItemExists.setChecked(false);
                }
            }

            //temp solution
            if (listItem.header == ""){
                journalItemExists.setVisibility(View.GONE);
            }

            // Manage distress seekbar direct in expandList, can be removed

            if (listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_DISTRESS)){
                ConnectionHandler connectHandler = ConnectionHandler.getInstance();
                journalItemExists.setVisibility(View.GONE);
                distressLayout.setVisibility(View.VISIBLE);
                txtListName.setText(MyApplication.getContext().getString(R.string.journal_sideeffect_distress_question));
                txtListName.setText(txtListName.getText().toString().replace("*", connectHandler.patient.patient_name));
                String[] parts = listItem.sideeffect.value.split(",");
                if ((parts.length > 0) && (!parts[0].isEmpty())){
                    Integer value1 = Integer.parseInt(parts[0].substring(parts[0].indexOf(":") + 1));
                    distressSeekBar.setProgress(value1);
                }
            }
        }

        distressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                System.out.println("onStopTrackingTouch");
                ConnectionHandler connectHandler = ConnectionHandler.getInstance();
                listItem.sideeffect.value = "" + seekBar.getProgress();
                listItem.sideeffect.time = new SimpleDateFormat("kk:mm:ss").format(new Date());

                if (listItem.sideeffect.sideeffect_ID >= 0){
                    // sideeffect already exist, just update
                    connectHandler.updateSideeffect(listItem.sideeffect);
                } else {
                    // new sideeffect
                    connectHandler.createSideeffect(listItem.sideeffect);
                }

                while (connectHandler.pendingMessage) {}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                System.out.println("onStartTrackingTouch");
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                System.out.println("onProgressChanged");
            }
        });

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
            convertView = infalInflater.inflate(R.layout.journal_expand_list_group, null);
        }

        TextView listHeader = (TextView) convertView
                .findViewById(R.id.journal_list_header);
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