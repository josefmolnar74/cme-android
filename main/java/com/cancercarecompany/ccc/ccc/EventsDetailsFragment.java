package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class EventsDetailsFragment extends Fragment {

    private Event listItem;
    private ConnectionHandler connectHandler;
    private int position;
    private boolean admin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_events_details, container, false);
        final ImageButton buttonEdit = (ImageButton) view.findViewById(R.id.btn_edit);
        final TextView txtSave = (TextView) view.findViewById(R.id.txt_save);
        txtSave.setVisibility(View.INVISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);
        int familyAvatarId = 0;

        for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
        }

            // check admin
        for (int i=0; i < connectHandler.patient.care_team.size(); i++){
            if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                (connectHandler.patient.care_team.get(i).admin == 1)){
                    admin = true;
            }
        }

        if (admin){
            buttonEdit.setVisibility(View.VISIBLE);
        }


        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.INVISIBLE);
                txtSave.setVisibility(View.VISIBLE);
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;

    }

    public void setItem(Event selectedListItem){
        listItem = selectedListItem;
    }
}