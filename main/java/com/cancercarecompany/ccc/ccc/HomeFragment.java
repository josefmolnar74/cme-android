package com.cancercarecompany.ccc.ccc;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ConnectionHandler connectHandler;
    private EditText questionText;

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

        final ImageButton startJourneyButton = (ImageButton) view.findViewById(R.id.button_start_journey);
        final TextView journeyHeaderText = (TextView) view.findViewById(R.id.txt_header_journey);
        final Button sendQuestionButton = (Button) view.findViewById(R.id.button_send_question);
        questionText = (EditText) view.findViewById(R.id.edit_question);

        journeyHeaderText.setText(journeyHeaderText.getText().toString().replace("*", connectHandler.patient.patient_name));

        startJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Intent myIntent = new Intent(MyApplication.getContext(), JourneyActivity.class);
                    getActivity().startActivity(myIntent);
                    getActivity().finish();
                }
            }
        });

        sendQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add date and time later
                Question newQuestion = new Question(0,
                                                    connectHandler.patient.patient_ID,
                                                    connectHandler.person.person_ID,
                                                    questionText.getText().toString(),
                                                    "",
                                                    "",
                                                    "",
                                                    "");
                // needs implementation in backend
//                connectHandler.createQuestion(newQuestion);
                while (connectHandler.socketBusy){}
                questionText.setText("");
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
