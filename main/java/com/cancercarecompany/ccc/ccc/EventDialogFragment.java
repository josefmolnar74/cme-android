package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Josef on 2016-10-30.
 */
public class EventDialogFragment extends DialogFragment {

    public static final String SUB_CATEGORY = "sub_category";

    private String subCategory;
    private LinearLayout swipeLayout;
    private int currentPage = 1;
    private int pages = 0;
    private ImageView eventInfoImage;
    private TextView eventInfoText;
    private ImageView page1;
    private ImageView page2;
    private ImageView page3;
    private int resource1;
    private int resource2;
    private int resource3;

    private ImageButton soundButton;
    private boolean mediaPlayActive = false;
    private MediaPlayer mp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_events_dialog, container, false);

        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);
        subCategory = getArguments().getString(SUB_CATEGORY);
        final Context context = MyApplication.getContext();
        swipeLayout = (LinearLayout) rootView.findViewById(R.id.lay_events_info_swipe);
        page1 = (ImageView) rootView.findViewById(R.id.img_page1);
        page2 = (ImageView) rootView.findViewById(R.id.img_page2);
        page3 = (ImageView) rootView.findViewById(R.id.img_page3);
        eventInfoImage = (ImageView) rootView.findViewById(R.id.img_subcategory_detail);
        eventInfoText = (TextView) rootView.findViewById(R.id.txt_subcategory_main);
        soundButton = (ImageButton) rootView.findViewById(R.id.btn_event_sound);

        // Prepare mediaplayer
        int soundResourceId = context.getResources().getIdentifier("event_"+subCategory+currentPage, "raw", getActivity().getPackageName());
        if (soundResourceId != 0) {
            mp = MediaPlayer.create(context, soundResourceId);
        }

            // find number of pages
        resource1 = getResources().getIdentifier("event_"+subCategory+"_txt1", "string", getActivity().getPackageName());
        resource2 = getResources().getIdentifier("event_"+subCategory+"_txt2", "string", getActivity().getPackageName());
        resource3 = getResources().getIdentifier("event_"+subCategory+"_txt3", "string", getActivity().getPackageName());
        page1.setVisibility(View.INVISIBLE);
        page2.setVisibility(View.INVISIBLE);
        page3.setVisibility(View.INVISIBLE);

        if(resource1 != 0){
            pages ++;
        }

        if(resource2 != 0){
            page1.setVisibility(View.VISIBLE);
            page2.setVisibility(View.VISIBLE);
            pages ++;
        }

        if(resource3 != 0){
            page3.setVisibility(View.VISIBLE);
            pages ++;
        }

        eventInfoImage.setImageResource(getActivity().getResources().getIdentifier("event_"+subCategory+currentPage, "drawable", getActivity().getPackageName()));
        eventInfoText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+subCategory+"_txt"+currentPage, "string", getActivity().getPackageName())));

        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeRight() {

                if(currentPage > 1) {
                    currentPage--;
                }
                else{
                    currentPage = pages;
                }

                int resourceId = context.getResources().getIdentifier("event_"+subCategory+currentPage, "drawable", getActivity().getPackageName());
                Glide.with(context).load(resourceId).fitCenter().into(eventInfoImage);
                switch (currentPage) {

                    case 1:
                        page1.setBackgroundResource(R.drawable.bluecircle);
                        page2.setBackgroundResource(R.drawable.blackcircle);
                        page3.setBackgroundResource(R.drawable.blackcircle);
                        break;

                    case 2:
                        page1.setBackgroundResource(R.drawable.blackcircle);
                        page2.setBackgroundResource(R.drawable.bluecircle);
                        page3.setBackgroundResource(R.drawable.blackcircle);
                        break;

                    case 3:
                        page1.setBackgroundResource(R.drawable.blackcircle);
                        page2.setBackgroundResource(R.drawable.blackcircle);
                        page3.setBackgroundResource(R.drawable.bluecircle);
                        break;
                }
                eventInfoImage.setImageResource(getActivity().getResources().getIdentifier("event_"+subCategory+currentPage, "drawable", getActivity().getPackageName()));
                eventInfoText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+subCategory+"_txt"+currentPage, "string", getActivity().getPackageName())));

            }

            public void onSwipeLeft() {

                if(currentPage < pages) {

                    currentPage++;

                }
                else{
                    currentPage = 1;
                }

                System.out.println(currentPage);

                int resourceId = context.getResources().getIdentifier("event_"+subCategory+currentPage, "drawable", getActivity().getPackageName());
                Glide.with(context).load(resourceId).fitCenter().into(eventInfoImage);

                switch (currentPage) {

                    case 1:
                        page1.setBackgroundResource(R.drawable.bluecircle);
                        page2.setBackgroundResource(R.drawable.blackcircle);
                        page3.setBackgroundResource(R.drawable.blackcircle);
                        break;

                    case 2:
                        page1.setBackgroundResource(R.drawable.blackcircle);
                        page2.setBackgroundResource(R.drawable.bluecircle);
                        page3.setBackgroundResource(R.drawable.blackcircle);
                        break;

                    case 3:
                        page1.setBackgroundResource(R.drawable.blackcircle);
                        page2.setBackgroundResource(R.drawable.blackcircle);
                        page3.setBackgroundResource(R.drawable.bluecircle);
                        break;
                }
                eventInfoImage.setImageResource(getActivity().getResources().getIdentifier("event_"+subCategory+currentPage, "drawable", getActivity().getPackageName()));
                eventInfoText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+subCategory+"_txt"+currentPage, "string", getActivity().getPackageName())));

            }
        });

        soundButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int soundResourceId = context.getResources().getIdentifier("event_"+subCategory+currentPage, "raw", getActivity().getPackageName());
                if ((mp != null) && (!mp.isPlaying())) {
                    mp.start();
                }
            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((mp != null) && (mp.isPlaying())){
                    mp.stop();
                }
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