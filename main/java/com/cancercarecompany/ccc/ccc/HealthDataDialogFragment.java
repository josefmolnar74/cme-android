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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Josef on 2016-12-04.
 */
public class HealthDataDialogFragment extends DialogFragment implements OnChartValueSelectedListener {

    public static final String HEALTH_DATA_TYPE = "healthdata_type";

    public static final String INFO_TYPE = "info_type";
    public static final String INFO_TITLE = "info_title";
    public static final String INFO_TEXT = "info_text";
    private ArrayList<MyChartData> chartData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_healthdata_dialog, container, false);
        TextView headerText = (TextView) rootView.findViewById(R.id.txt_home_info_header);
        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        ConnectionHandler connectHandler = ConnectionHandler.getInstance();

        chartData = new ArrayList<>();
        String healthdata_type = getArguments().getString(HEALTH_DATA_TYPE);
        headerText.setText(getString(getActivity().getResources().getIdentifier("journal_health_data_"+ healthdata_type, "string", getActivity().getPackageName())));

        for (int i=0; i < connectHandler.healthData.healthdata_data.size(); i++){
            if (connectHandler.healthData.healthdata_data.get(i).type.matches(healthdata_type)){
                MyChartData data = new MyChartData();
                data.date = connectHandler.healthData.healthdata_data.get(i).date.substring(2,10);
                data.value = Float.parseFloat(connectHandler.healthData.healthdata_data.get(i).value);
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

        LineChart chart = (LineChart) rootView.findViewById(R.id.chart_healthdata);

        String[] xValues = new String[chartData.size()];

        XAxis xAxis = chart.getXAxis();
        xAxis.setLabelRotationAngle(-90);
        chart.getXAxis().setValueFormatter(new MyXAxisValueFormatter(xValues));
        chart.setExtraTopOffset(5f); //offset to assure that xAxis values fit

        List<Entry> entries = new ArrayList<Entry>();

        for (int i=0; i < chartData.size(); i++){
            // Enter Data from Healthdata
            if (chartData.get(i).value != null){
                entries.add(new Entry(i, chartData.get(i).value));
            }
            else{
                entries.add(new Entry(i, 0));
            }
            xValues[i] =  chartData.get(i).date;
        }

        //int max = findMaxYValue(yourdata); // figure out the max value in your dataset
/*        chart.getAxisLeft().setLabelCount(6); // replace 6 with max
        chart.getAxisLeft().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMaximum(10);
        chart.getAxisRight().setLabelCount(6); // replace 6 with max
        chart.getAxisRight().setAxisMinimum(0);
        chart.getAxisRight().setAxisMaximum(10);*/

        if (chartData.size()>1){
            chart.getXAxis().setLabelCount(chartData.size()-1);
        } else{
            chart.getXAxis().setLabelCount(3);
        }

        LineDataSet dataSet = new LineDataSet(entries, "Josef"); // add entries to dataset
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

    public class MyChartData {
        String date;
        Float value;
        String notes;
    }

}