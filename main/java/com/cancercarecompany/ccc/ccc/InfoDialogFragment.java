package com.cancercarecompany.ccc.ccc;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Josef on 2016-10-30.
 */
public class InfoDialogFragment extends DialogFragment {

    public static final String INFO_TYPE = "info_type";
    public static final String INFO_TITLE = "info_title";
    public static final String INFO_TEXT = "info_text";
    public static final String INFO_SOURCE = "info_source";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_info_dialog, container, false);

        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        TextView dialogText = (TextView) rootView.findViewById(R.id.text_dialog_info);
        TextView dialogTitle = (TextView) rootView.findViewById(R.id.text_dialog_title);
        TextView dialogSource = (TextView) rootView.findViewById(R.id.text_dialog_source);
        TextView dialogHeader = (TextView) rootView.findViewById(R.id.txt_home_info_header);

        dialogHeader.setText(getArguments().getString(INFO_TYPE));
        dialogTitle.setText(getArguments().getString(INFO_TITLE));
        dialogText.setText(getArguments().getString(INFO_TEXT));
        dialogSource.setText(getArguments().getString(INFO_SOURCE));

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
}