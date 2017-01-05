package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Josef on 2016-10-30.
 */
public class SideeffectDialogFragment extends DialogFragment implements OnChartValueSelectedListener {

    public static final String SIDEEFFECT_TYPE = "sideeffect_type";

    public static final String INFO_TYPE = "info_type";
    public static final String INFO_TITLE = "info_title";
    public static final String INFO_TEXT = "info_text";

    private ArrayList<MyChartData> chartData;
    private TextView notesText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_sideeffect_dialog, container, false);
        TextView headerText = (TextView) rootView.findViewById(R.id.txt_home_info_header);
        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        notesText = (TextView) rootView.findViewById(R.id.text_sideeffect_notes);
        ConnectionHandler connectHandler = ConnectionHandler.getInstance();

        chartData = new ArrayList<>();
        String sideeffect_type = getArguments().getString(SIDEEFFECT_TYPE);
        headerText.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ sideeffect_type, "string", getActivity().getPackageName())));

        for (int i=0; i < connectHandler.sideeffects.sideeffect_data.size(); i++){
            if (connectHandler.sideeffects.sideeffect_data.get(i).type.matches(sideeffect_type)){
                MyChartData data = new MyChartData();
                data.date = connectHandler.sideeffects.sideeffect_data.get(i).date.substring(2,10);
                data.value = connectHandler.sideeffects.sideeffect_data.get(i).severity;
                data.notes = connectHandler.sideeffects.sideeffect_data.get(i).notes;
                chartData.add(data);
            }
        }

        // sort chart data by date
        Collections.sort(chartData, new Comparator<MyChartData>() {
            @Override
            public int compare(MyChartData o1, MyChartData o2) {
                return o1.date.compareTo(o2.date);
            }
        });

        BarChart chart = (BarChart) rootView.findViewById(R.id.chart_sideeffect);

        String[] xValues = new String[chartData.size()];

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xValues));
        xAxis.setLabelRotationAngle(-90);
//        xAxis.setTextColor(Color.RED);

        YAxis left = chart.getAxisLeft();
        left.setValueFormatter(new MyYAxisValueFormatter());

        List<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i=0; i < chartData.size(); i++){
            // Enter Data from sideeffect
            if (chartData.get(i).value != null){
                entries.add(new BarEntry(i, chartData.get(i).value));
            }
            else{
                entries.add(new BarEntry(i, 0));
            }
            xValues[i] =  chartData.get(i).date;
        }

        //int max = findMaxYValue(yourdata); // figure out the max value in your dataset
        chart.getAxisLeft().setLabelCount(6); // replace 6 with max
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(10);
        chart.getAxisRight().setLabelCount(6); // replace 6 with max
        chart.getAxisRight().setAxisMinimum(0);
        chart.getAxisRight().setAxisMaximum(10);
        chart.getXAxis().setLabelCount(chartData.size());
        chart.setExtraTopOffset(5f); //offset to assure that xAxis values fit

        BarDataSet dataSet = new BarDataSet(entries, "Josef"); // add entries to dataset
        BarData barData = new BarData(dataSet);
        dataSet.setColor(Color.parseColor("#7fc9cb")); // cme_light color
        dataSet.setDrawValues(false);
        chart.setData(barData);
        chart.getLegend().setEnabled(false);

        chart.invalidate(); // refresh

        chart.setOnChartValueSelectedListener(this);
        chart.setDescription(null);

        dismissButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        //TODO:
    }

    /**
     * Called when a value has been selected inside the chart.
     *
     * @param e The selected Entry.
     * @param h The corresponding highlight object that contains information
     * about the highlighted position
     */
    public void onValueSelected(Entry e, Highlight h){
        int i = (int) Math.floor(e.getX());
        notesText.setText(chartData.get(i).notes);
    };

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    public void onNothingSelected(){
        System.out.println("onNothingSelected");
    };

    public class MyChartData {
        String date;
        Integer value;
        String notes;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {

            } else {
                // error something went wrong
            }
        }
        @Override
        protected void onCancelled() {

        }
    }


}