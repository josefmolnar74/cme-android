package com.cancercarecompany.ccc.ccc;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ConnectionHandler connectHandler;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        connectHandler = ConnectionHandler.getInstance();

        final ImageButton startJourneyButton= (ImageButton) view.findViewById(R.id.button_start_journey);

        startJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MyApplication.getContext(), JourneyActivity.class);
                getActivity().startActivity(myIntent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
//        ((MainActivity) getActivity()).setTitle("Hem");
    }
}
