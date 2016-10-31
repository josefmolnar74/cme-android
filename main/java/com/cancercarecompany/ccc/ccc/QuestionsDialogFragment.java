package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Josef on 2016-10-30.
 */
public class QuestionsDialogFragment extends DialogFragment {

    ExpandableListAdapter questionsExpandListAdapter;
    ExpandableListView expListView;

    ArrayList<ArrayList<Question>> listOfList;

    List<String> listDataHeader;
    HashMap<String, List<Question>> listDataChild;
    ConnectionHandler connectHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questions_dialog, container, false);
        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        TextView headerText = (TextView) rootView.findViewById(R.id.txt_home_questions_header);
        headerText.setText(getString(R.string.home_all_questions));

        connectHandler = ConnectionHandler.getInstance();

        expListView = (ExpandableListView) rootView.findViewById(R.id.explist_questions);
        listOfList = new ArrayList<ArrayList<Question>>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Question>>();

        if (connectHandler.questions == null){
            connectHandler.getQuestionsForPatient(connectHandler.patient.patient_ID);
            while (connectHandler.socketBusy){}
        }

        for (int i = 0; i < connectHandler.questions.question_data.size(); i++){
            ArrayList<Question> questionExpList = new ArrayList<Question>();
            listDataHeader.add(connectHandler.questions.question_data.get(i).question);
            questionExpList.add(connectHandler.questions.question_data.get(i));
            listOfList.add(questionExpList);
        }

        for (int i = 0; i < listOfList.size(); i++){
            listDataChild.put(listDataHeader.get(i), listOfList.get(i)); // Header, Child data
        }

        questionsExpandListAdapter = new QuestionsExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expListView.setAdapter(questionsExpandListAdapter);

        dismissButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}