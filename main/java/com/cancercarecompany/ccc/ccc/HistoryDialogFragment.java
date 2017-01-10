package com.cancercarecompany.ccc.ccc;

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

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Josef on 2016-12-20.
 */
public class HistoryDialogFragment extends DialogFragment implements OnChartValueSelectedListener {

    public static final String SELECTED_JOURNAL_ITEMS = "checked_journal_items";

    private ArrayList<JournalSelectionData> journalSelectionDataList;
    private TextView notesText;
    private LineChart chart;
    private CombinedChart mChart; // Not completed yet, wait after Beta is out
    ArrayList<DataSet> mDataSetList;
    private String[] xValues;
    private float[] maxValue;
    private float[] minValue;
    private ConnectionHandler connectHandler;
    private UserLoginTask mAuthTask = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_history_dialog, container, false);
        TextView headerText = (TextView) rootView.findViewById(R.id.txt_home_info_header);
        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        notesText = (TextView) rootView.findViewById(R.id.text_sideeffect_notes);
        connectHandler = ConnectionHandler.getInstance();

        journalSelectionDataList = new ArrayList<>();
        String checkedJournalItems = getArguments().getString(SELECTED_JOURNAL_ITEMS);
        String[] journalItem = checkedJournalItems.split(",");


        // Extract which data that have been selected for display
        for (int i=0; i < journalItem.length; i++){
            JournalSelectionData mJournalSelectionData = new JournalSelectionData();
            mJournalSelectionData.type = journalItem[i];
            mJournalSelectionData.chartData = new ArrayList<>();
            journalSelectionDataList.add(mJournalSelectionData);
        }

        chart = (LineChart) rootView.findViewById(R.id.linechart_history);

        mChart = (CombinedChart) rootView.findViewById(R.id.combinedchart_history);
//         mChart.getDescription().setEnabled(false);
//        mChart.setBackgroundColor(Color.WHITE);
//        mChart.setDrawGridBackground(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setHighlightFullBarEnabled(false);

        // Start Async task to build up data
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);

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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

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
                        if (connectHandler.healthData.healthdata_data.get(i).value.length() > 0) {
                            data.value = Float.parseFloat(connectHandler.healthData.healthdata_data.get(i).value);
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
            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
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


            // Get days between dates
            int days = 0;
            if ((firstDate != null) && (lastDate != null)){
                long diff = lastDate.getTime() - firstDate.getTime();
                days = (int) (long) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            }

            xValues = new String[days+1];

            minValue = new float[journalSelectionDataList.size()];
            maxValue = new float[journalSelectionDataList.size()];

            for (int i=0; i < journalSelectionDataList.size(); i++){
                maxValue[i] = 0;
                minValue[i] = Float.MAX_VALUE;
            }

            Date mDate = firstDate;
            Calendar cal = Calendar.getInstance();
            for (int i=0; i < days+1; i++){
                String dateString = format.format(mDate);

                xValues[i] = dateString;

                // Increment one day
                cal.setTime(mDate);
                cal.add(Calendar.DATE, 1);
                mDate = cal.getTime();
            }

            ArrayList<ArrayList<Entry>> entriesList = new ArrayList<ArrayList<Entry>>();
            mDataSetList = new ArrayList<DataSet>();
            for (int i=0; i < journalSelectionDataList.size(); i++) {
                DataSet mDataSet = new DataSet();
                ArrayList<Entry> entries = new ArrayList<>();
                int dataIndex = 0;
                // dataIndex is less or equal xValues length
                for (int j = 0; j < xValues.length; j++) {
                    if (xValues[j].matches(journalSelectionDataList.get(i).chartData.get(dataIndex).date)) {
                        float value = journalSelectionDataList.get(i).chartData.get(dataIndex).value;
                        if ((value > maxValue[i])){
                            maxValue[i] = value;
                        }
                        if ((value < minValue[i])){
                            minValue[i] = value;
                        }
                        entries.add(new Entry(j, value));
                        dataIndex++;
                        if (dataIndex == journalSelectionDataList.get(i).chartData.size()){
                            // no more data values
                            break;
                        }
                    } else {
                        // no registered value for this date
//                    entries.add(new Entry(j, 0));
                    }
                }
                mDataSet.entries = entries;
                mDataSetList.add(mDataSet);
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                //drawChart();
                drawChart2();
                //drawCombinedChart();
            } else {
                // error something went wrong
            }
        }
        @Override
        protected void onCancelled() {

        }
    }

    private void drawChart(){

        int i = 0;
        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xValues));
//        xAxis.setLabelCount(xValues.length-1);
        chart.setExtraTopOffset(5f); //offset to assure that xAxis values fit

        if (maxValue[i] < 10){
            maxValue[i] = 10;
        }

        if (minValue[i] < 10){
            minValue[i] = 0;
        }

//        chart.getAxisLeft().setLabelCount(6); // replace 6 with max
        chart.getAxisLeft().setAxisMinimum(minValue[i]);
        chart.getAxisLeft().setAxisMaximum(maxValue[i]);
//        chart.getAxisRight().setLabelCount(6); // replace 6 with max
        chart.getAxisRight().setAxisMinimum(minValue[i]);
        chart.getAxisRight().setAxisMaximum(maxValue[i]);

        // Print one dataSet
        LineDataSet dataSet = new LineDataSet(mDataSetList.get(i).entries, "Josef"); // add entries to dataset
        LineData lineData = new LineData(dataSet);
        dataSet.setColor(Color.parseColor("#7fc9cb")); // cme_light color
        dataSet.setDrawValues(false);

        chart.setData(lineData);
        chart.getLegend().setEnabled(false);
        chart.invalidate(); // refresh
//            chart.setOnChartValueSelectedListener(MyApplication.getContext());
        chart.setDescription(null);

    }

    private void drawChart2(){

        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xValues));
//        xAxis.setLabelCount(xValues.length-1);
        chart.setExtraTopOffset(5f); //offset to assure that xAxis values fit

        List<ILineDataSet> dataSets= new ArrayList<>();
        List<LineData> mLineDataList= new ArrayList<>();

        for (int i=0; i<journalSelectionDataList.size(); i++){
            if (maxValue[i] < 10){
                maxValue[i] = 10;
            }
            if (minValue[i] < 10){
                minValue[i] = 0;
            }

            LineDataSet mLineDataSet = new LineDataSet(mDataSetList.get(i).entries, "DataSet".concat(String.valueOf(i)));
            mLineDataSet.setColor(Color.parseColor("#7fc9cb")); // cme_light color
            mLineDataSet.setDrawValues(false);

            if (i==0){
                mLineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            } else{
                mLineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            }

            dataSets.add(mLineDataSet);
            LineData mLineData = new LineData(dataSets.get(i));
            mLineDataList.add(mLineData);
//        dataSet.setColor(Color.parseColor("#7fc9cb")); // cme_light color
//        dataSet.setDrawValues(false);
            if (i==0) {
                chart.getAxisLeft().setAxisMinimum(minValue[i]);
                chart.getAxisLeft().setAxisMaximum(maxValue[i]);
            }
            chart.getAxisRight().setAxisMinimum(minValue[i]);
            chart.getAxisRight().setAxisMaximum(maxValue[i]);
        }
        chart.setData(new LineData(dataSets));
        chart.getLegend().setEnabled(false);
        chart.invalidate(); // refresh
//            chart.setOnChartValueSelectedListener(MyApplication.getContext());
        chart.setDescription(null);

    }

    private void drawCombinedChart(){

        XAxis xAxis = mChart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(xValues));
//        xAxis.setLabelCount(xValues.length-1);
        mChart.setExtraTopOffset(5f); //offset to assure that xAxis values fit

        CombinedData mCombinedData = new CombinedData();
        ArrayList<LineDataSet> mLineDataSetList= new ArrayList<>();
        ArrayList<LineData> mLineDataList= new ArrayList<>();

        for (int i=0; i<journalSelectionDataList.size(); i++){
            if (maxValue[i] < 10){
                maxValue[i] = 10;
            }
            if (minValue[i] < 10){
                minValue[i] = 0;
            }

            LineDataSet mLineDataSet = new LineDataSet(mDataSetList.get(i).entries, "History");
            mLineDataSet.setColor(Color.parseColor("#7fc9cb")); // cme_light color
            mLineDataSet.setDrawValues(false);
            mLineDataSetList.add(mLineDataSet);
            LineData mLineData = new LineData(mLineDataSetList.get(i));
            mLineDataList.add(mLineData);

            if (i==0){
                mChart.getAxisLeft().setAxisMinimum(minValue[i]);
                mChart.getAxisLeft().setAxisMaximum(maxValue[i]);
            }else{
                mChart.getAxisRight().setAxisMinimum(minValue[i]);
                mChart.getAxisRight().setAxisMaximum(maxValue[i]);
            }

            mCombinedData.setData(mLineDataList.get(i));
        }

        mChart.setData(mCombinedData);
        mChart.getLegend().setEnabled(false);
        mChart.invalidate(); // refresh
        mChart.setDescription(null);

    }

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