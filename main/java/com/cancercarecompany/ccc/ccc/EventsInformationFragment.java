package com.cancercarecompany.ccc.ccc;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsInformationFragment extends Fragment {


    public EventsInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_information, container, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(true);
        }

        return view;
    }
}