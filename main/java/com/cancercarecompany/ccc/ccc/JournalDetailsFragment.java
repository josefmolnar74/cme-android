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

public class JournalDetailsFragment extends Fragment {

    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_journal_details, container, false);


        // Insert Detail code here


        return view;

    }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}