package com.cancercarecompany.ccc.ccc;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class JourneyFragment extends Fragment {


    public JourneyFragment() {
        // Required empty public constructor
    }


    public static JourneyFragment newInstance() {
        JourneyFragment fragment = new JourneyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journey, container, false);
    }

}
