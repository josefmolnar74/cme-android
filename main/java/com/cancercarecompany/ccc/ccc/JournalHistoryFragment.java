package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JournalHistoryFragment extends Fragment {

    private TextView journalHeaderText;
    private ExpandableListAdapter expandListAdapter;
    private ExpandableListView expandListView;

    private List<JournalHistoryItem> healthDataExpandList;
    private List<JournalHistoryItem> emotionalExpandList;
    private List<JournalHistoryItem> physicalExpandList;
    private List<JournalHistoryItem> otherExpandList;

    private List<String> listDataHeader;
    private HashMap<String, List<JournalHistoryItem>> listDataChild;
    private ConnectionHandler connectHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_journal_history, container, false);

        connectHandler = ConnectionHandler.getInstance();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setHasOptionsMenu(true);
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle("History");
//            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lay_journal_detail);
//            layout.setVisibility(View.GONE);
        }

        ((MainActivity) getActivity()).setTitle(connectHandler.patient.patient_name.concat(getString(R.string.patient_journey)));

        final Button showHistoryButton = (Button) view.findViewById(R.id.btn_journal_show_history);
        expandListView = (ExpandableListView) view.findViewById(R.id.explist_journal_history);
        healthDataExpandList = new ArrayList<JournalHistoryItem>();
        emotionalExpandList = new ArrayList<JournalHistoryItem>();
        physicalExpandList = new ArrayList<JournalHistoryItem>();
        otherExpandList = new ArrayList<JournalHistoryItem>();

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<JournalHistoryItem>>();

        listDataHeader.add(getResources().getString(R.string.journal_health_data));
        listDataHeader.add(getResources().getString(R.string.journal_sideeffect_physical));
        listDataHeader.add(getResources().getString(R.string.journal_sideeffect_emotional));
        listDataHeader.add(getResources().getString(R.string.journal_sideeffect_other));
//        listDataHeader.add(getResources().getString(R.string.sideeffect_distress));

        prepareExpList();

        showHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check which items that should be included in graph
                ArrayList<String> checkedJournalItems = new ArrayList<String>();
                for (int i=0; i < healthDataExpandList.size(); i++){
                    if (healthDataExpandList.get(i).isSelected){
                        checkedJournalItems.add(healthDataExpandList.get(i).type);
                    }
                }
                for (int i=0; i < physicalExpandList.size(); i++){
                    if (physicalExpandList.get(i).isSelected){
                        checkedJournalItems.add(physicalExpandList.get(i).type);
                    }
                }
                for (int i=0; i < otherExpandList.size(); i++){
                    if (otherExpandList.get(i).isSelected){
                        checkedJournalItems.add(otherExpandList.get(i).type);
                    }
                }
                for (int i=0; i < emotionalExpandList.size(); i++){
                    if (emotionalExpandList.get(i).isSelected){
                        checkedJournalItems.add(emotionalExpandList.get(i).type);
                    }
                }

                String listString = "";
                for (String s : checkedJournalItems)
                {
                    listString += s + ",";
                }
                FragmentManager fm = getFragmentManager();
                HistoryDialogFragment dialogFragment = new HistoryDialogFragment();
                Bundle args = new Bundle();
                args.putString(HistoryDialogFragment.SELECTED_JOURNAL_ITEMS, listString);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Josef");

            }
        });

        return view;
    }

    private void prepareExpList(){

        if (!healthDataExpandList.isEmpty()){
            healthDataExpandList.clear();
        }

        if (!physicalExpandList.isEmpty()){
            physicalExpandList.clear();
        }

        if (!otherExpandList.isEmpty()){
            otherExpandList.clear();
        }

        if (!emotionalExpandList.isEmpty()){
            emotionalExpandList.clear();
        }

        if (!listDataChild.isEmpty()){
            listDataChild.clear();
        }

        for (int i=0; i < connectHandler.healthData.healthdata_data.size(); i++){
            String type = connectHandler.healthData.healthdata_data.get(i).type;
            boolean exists;
            switch(type){

                // Build list with for only journal items that have data

                // HealthData
                case JournalFragment.HEALTH_DATA_WEIGHT:
                case JournalFragment.HEALTH_DATA_TEMPERATURE:
                case JournalFragment.HEALTH_DATA_BLOODPRESSURE:
                case JournalFragment.HEALTH_DATA_PULSE:

                    // check is health data type already have been listed
                    exists = false;
                    for (int j=0; j < healthDataExpandList.size(); j++){
                        if (type.matches(healthDataExpandList.get(j).type)){
                            exists = true;
                            break;
                        }
                    }
                    if (!exists){
                        // Update expand list with journal item
                        JournalHistoryItem item = new JournalHistoryItem();
                        item.header = getString(getActivity().getResources().getIdentifier("journal_health_data_"+ type, "string", getActivity().getPackageName()));
                        item.type = type;
                        item.isSelected = false;
                        healthDataExpandList.add(item);
                    }
                    break;
            }
        }

        for (int i=0; i < connectHandler.sideeffects.sideeffect_data.size(); i++){
            String type = connectHandler.sideeffects.sideeffect_data.get(i).type;
            boolean exists = false;
            switch(type){

                // Build list with for only journal items that have data

                // Physical Sideeffects
                case JournalFragment.SIDEEFFECT_PHYSICAL_BREATHING:
                case JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION:
                case JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA:
                case JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE:
                case JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY:
                case JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY:
                case JournalFragment.SIDEEFFECT_PHYSICAL_VOMIT:
                case JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP:
                case JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING:

                    // check is health data type already have been listed
                    exists = false;
                    for (int j=0; j < physicalExpandList.size(); j++){
                        if (type.matches(physicalExpandList.get(j).type)){
                            exists = true;
                            break;
                        }
                    }
                    if (!exists){
                        // Update expand list with journal item
                        JournalHistoryItem item = new JournalHistoryItem();
                        item.header = getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ type, "string", getActivity().getPackageName()));
                        item.type = type;
                        item.isSelected = false;
                        physicalExpandList.add(item);
                    }
                    break;

                // Other Sideeffects
                case JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE:
                case JournalFragment.SIDEEFFECT_PHYSICAL_URINATION:
                case JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE:
                case JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED:
                case JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION:
                case JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH:
                case JournalFragment.SIDEEFFECT_PHYSICAL_DIZZINESS:
                case JournalFragment.SIDEEFFECT_PHYSICAL_NOSE:
                case JournalFragment.SIDEEFFECT_PHYSICAL_PAIN:
                case JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL:

                    // check is health data type already have been listed
                    exists = false;
                    for (int j=0; j < otherExpandList.size(); j++){
                        if (type.matches(otherExpandList.get(j).type)){
                            exists = true;
                            break;
                        }
                    }
                    if (!exists){
                        // Update expand list with journal item
                        JournalHistoryItem item = new JournalHistoryItem();
                        item.header = getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ type, "string", getActivity().getPackageName()));
                        item.type = type;
                        item.isSelected = false;
                        otherExpandList.add(item);
                    }
                    break;

                // Emotional sideeffects
                case JournalFragment.SIDEEFFECT_EMOTIONAL_DEPRESSION:
                case JournalFragment.SIDEEFFECT_EMOTIONAL_FEAR:
                case JournalFragment.SIDEEFFECT_EMOTIONAL_NERVOUS:
                case JournalFragment.SIDEEFFECT_EMOTIONAL_DEJECTION:
                case JournalFragment.SIDEEFFECT_EMOTIONAL_WORRY:
                case JournalFragment.SIDEEFFECT_EMOTIONAL_ACTIVITIES:

                    // check is health data type already have been listed
                    exists = false;
                    for (int j=0; j < emotionalExpandList.size(); j++){
                        if (type.matches(emotionalExpandList.get(j).type)){
                            exists = true;
                            break;
                        }
                    }
                    if (!exists){
                        // Update expand list with journal item
                        JournalHistoryItem item = new JournalHistoryItem();
                        item.header = getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ type, "string", getActivity().getPackageName()));
                        item.type = type;
                        item.isSelected = false;
                        emotionalExpandList.add(item);
                    }
                    break;

            }
        }

        listDataChild.put(listDataHeader.get(0), healthDataExpandList);
        listDataChild.put(listDataHeader.get(1), physicalExpandList);
        listDataChild.put(listDataHeader.get(2), emotionalExpandList);
        listDataChild.put(listDataHeader.get(3), otherExpandList);

        expandListAdapter = new JournalHistoryExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expandListView.setAdapter(expandListAdapter);
        expandListView.collapseGroup(0);
        expandListView.collapseGroup(1);
        expandListView.collapseGroup(2);
        expandListView.collapseGroup(3);
        expandListView.expandGroup(0);
        expandListView.expandGroup(1);
        expandListView.expandGroup(2);
        expandListView.expandGroup(3);
    }

    public class JournalHistoryExpandListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<JournalHistoryItem>> _listDataChild;

        public JournalHistoryExpandListAdapter(Context context, List<String> listDataHeader,
                                        HashMap<String, List<JournalHistoryItem>> listChildData) {
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

            final JournalHistoryItem listItem = (JournalHistoryItem) getChild(groupPosition, childPosition);
            final int groupPos = groupPosition;
            final int childPos = childPosition;

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.journal_history_expand_list_item, null);
            }

            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox_journal_history);
            checkBox.setText(listItem.header);

            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch (groupPos) {
                        case 0:
                            if (checkBox.isChecked()){
                                healthDataExpandList.get(childPos).isSelected = true;
                            }else{
                                healthDataExpandList.get(childPos).isSelected = false;
                            }
                            break;
                        case 1:
                            if (checkBox.isChecked()){
                                physicalExpandList.get(childPos).isSelected = true;
                            }else{
                                physicalExpandList.get(childPos).isSelected = false;
                            }
                            break;
                        case 2:
                            if (checkBox.isChecked()){
                                otherExpandList.get(childPos).isSelected = true;
                            }else{
                                otherExpandList.get(childPos).isSelected = false;
                            }
                            break;
                        case 3:
                            if (checkBox.isChecked()){
                                emotionalExpandList.get(childPos).isSelected = true;
                            }else{
                                emotionalExpandList.get(childPos).isSelected = false;
                            }
                            break;
                    }
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
                convertView = infalInflater.inflate(R.layout.journal_expand_list_group, null); // use same as for journal expand list
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

    private class JournalHistoryItem {

        String header;
        String type;
        Boolean isSelected;

    }
}