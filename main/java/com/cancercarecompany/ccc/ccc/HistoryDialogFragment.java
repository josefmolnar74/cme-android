package com.cancercarecompany.ccc.ccc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 * Created by Josef on 2016-12-20.
 */
public class HistoryDialogFragment extends DialogFragment implements OnChartValueSelectedListener {

    public static final String SELECTED_JOURNAL_ITEMS = "checked_journal_items";

    private ArrayList<JournalSelectionData> journalSelectionDataList;
    private TextView notesText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_healthdata_dialog, container, false);
        TextView headerText = (TextView) rootView.findViewById(R.id.txt_home_info_header);
        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        notesText = (TextView) rootView.findViewById(R.id.text_sideeffect_notes);
        ConnectionHandler connectHandler = ConnectionHandler.getInstance();

        journalSelectionDataList = new ArrayList<>();
        String checkedJournalItems = getArguments().getString(SELECTED_JOURNAL_ITEMS);
        String[] journalItem = checkedJournalItems.split(",");


        // Prepare
        for (int i=0; i < journalItem.length; i++){
            JournalSelectionData mJournalSelectionData = new JournalSelectionData();
            mJournalSelectionData.type = journalItem[i];
            mJournalSelectionData.chartData = new ArrayList<>();
            journalSelectionDataList.add(mJournalSelectionData);
        }

        for (int i=0; i < connectHandler.sideeffects.sideeffect_data.size(); i++){
            for (int j=0; j < journalSelectionDataList.size(); j++ ){
                if (connectHandler.sideeffects.sideeffect_data.get(i).type.matches(journalSelectionDataList.get(j).type)) {
                    MyChartData data = new MyChartData();
                    data.date = connectHandler.sideeffects.sideeffect_data.get(i).date.substring(2, 10);
                    data.value = (float) connectHandler.sideeffects.sideeffect_data.get(i).severity;
                    data.notes = connectHandler.sideeffects.sideeffect_data.get(i).notes;
                    journalSelectionDataList.get(j).chartData.add(data);
                }
            }
        }

        for (int i=0; i < connectHandler.healthData.healthdata_data.size(); i++){
            for (int j=0; j < journalSelectionDataList.size(); j++ ){
                if (connectHandler.healthData.healthdata_data.get(i).type.matches(journalSelectionDataList.get(j).type)) {
                    MyChartData data = new MyChartData();
                    data.date = connectHandler.healthData.healthdata_data.get(i).date.substring(2, 10);
                    if (connectHandler.healthData.healthdata_data.get(i).value.length() == 1) {
                        data.value = Float.parseFloat(connectHandler.healthData.healthdata_data.get(i).value.substring(0, 1));
                    } else if (connectHandler.healthData.healthdata_data.get(i).value.length() >= 2) {
                        data.value = Float.parseFloat(connectHandler.healthData.healthdata_data.get(i).value.substring(0, 2));
                    }
                    journalSelectionDataList.get(j).chartData.add(data);
                }
            }
        }

        for (int i=0; i < journalSelectionDataList.size(); i++){
            // sort chart data by date
            Collections.sort(journalSelectionDataList.get(i).chartData, new Comparator<MyChartData>() {
                @Override
                public int compare(MyChartData o1, MyChartData o2) {
                    return o1.date.compareTo(o2.date);
                }
            });

        }

        // Find first day and last day
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = null;
        Date lastDate = null;
        for (int i=0; i < journalSelectionDataList.size(); i++){
            Date selectionFirstDate = null;
            Date selectionLastDate = null;
            try {
                // Set firstDate and lastData to first and last in the first chartData set, then compare with the other sets
                selectionFirstDate = format.parse(journalSelectionDataList.get(i).chartData.get(0).date);
                selectionLastDate =  format.parse(journalSelectionDataList.get(i).chartData.get(journalSelectionDataList.get(i).chartData.size()-1).date);
            } catch (ParseException e) {
                System.out.println("Failure when parsing the targetDateString");
            }
            if (i==0){ 
                firstDate = selectionFirstDate;
                lastDate = selectionLastDate;
            } else {
                if (selectionFirstDate.before(firstDate)){
                    firstDate = selectionFirstDate;
                }
                if (selectionLastDate.after(lastDate)){
                    lastDate = selectionLastDate;
                }
            }
        }


        LineChart chart = (LineChart) rootView.findViewById(R.id.chart_healthdata);

//        String[] xValues = new String[chartData.size()];

        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
  //      chart.getXAxis().setValueFormatter(new MyXAxisValueFormatter(xValues));
        chart.setExtraTopOffset(5f); //offset to assure that xAxis values fit

        ArrayList<ArrayList<Entry>> entriesList = new ArrayList<ArrayList<Entry>>();

        ArrayList<DataSet> mDataSetList = new ArrayList<DataSet>();
        for (int i=0; i < journalSelectionDataList.size(); i++) {
            DataSet mDataSet = new DataSet();
            ArrayList<Entry> entries = new ArrayList<Entry>();
            ArrayList<MyChartData> chartData = journalSelectionDataList.get(i).chartData;
            for (int j = 0; j < chartData.size(); j++) {
                // Enter Data from Healthdata
                if (chartData.get(j).value != null) {
                    entries.add(new Entry(j, chartData.get(j).value));
                } else {
                    entries.add(new Entry(j, 0));
                }
//            xValues[i] =  chartData.get(i).date;
            }
            mDataSet.entries = entries;
            mDataSetList.add(mDataSet);
        }

        //int max = findMaxYValue(yourdata); // figure out the max value in your dataset
        chart.getAxisLeft().setLabelCount(6); // replace 6 with max
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(10);
        chart.getAxisRight().setLabelCount(6); // replace 6 with max
        chart.getAxisRight().setAxisMinimum(0);
        chart.getAxisRight().setAxisMaximum(10);

/*        if (chartData.size()>1){
            chart.getXAxis().setLabelCount(chartData.size()-1);
        } else{
            chart.getXAxis().setLabelCount(3);
        }
*/
        LineDataSet dataSet = new LineDataSet(mDataSetList.get(0).entries, "Josef"); // add entries to dataset
        LineData lineData = new LineData(dataSet);
        dataSet.setColor(Color.parseColor("#7fc9cb")); // cme_light color
        dataSet.setDrawValues(false);
        chart.setData(lineData);
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
    };

    /**
     * Called when nothing has been selected or an "un-select" has been made.
     */
    public void onNothingSelected(){
        System.out.println("onNothingSelected");
    };

    private class MyChartData {
        String date;
        Float value;
        String notes;
    }

    private class JournalSelectionData {
        String type;
        ArrayList<MyChartData> chartData;
    }

    private class DataSet {
        ArrayList<Entry> entries;
    }
}