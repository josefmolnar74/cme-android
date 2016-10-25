package com.cancercarecompany.ccc.ccc;


import android.content.Context;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsInformationFragment extends Fragment {

    private Event listItem;
    LinearLayout swipeLayout;
    int currentPage = 1;
    int pages = 0;
    ImageView eventInfoImage;
    TextView eventInfoText;
    ImageView page1;
    ImageView page2;
    ImageView page3;
    String eventText1;
    String eventText2;
    String eventText3;
    int resource1;
    int resource2;
    int resource3;

    ImageButton soundButton;

    public EventsInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_events_information, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setHasOptionsMenu(true);
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(getActivity().getResources().getIdentifier("event_"+listItem.sub_category, "string", getActivity().getPackageName())));
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lay_event_info_header);
            layout.setVisibility(View.GONE);
        }
        final Context context = MyApplication.getContext();
        swipeLayout = (LinearLayout) view.findViewById(R.id.lay_events_info_swipe);
        page1 = (ImageView) view.findViewById(R.id.img_page1);
        page2 = (ImageView) view.findViewById(R.id.img_page2);
        page3 = (ImageView) view.findViewById(R.id.img_page3);
        eventInfoImage = (ImageView) view.findViewById(R.id.img_subcategory_detail);
        eventInfoText = (TextView) view.findViewById(R.id.txt_subcategory_main);
        soundButton = (ImageButton) view.findViewById(R.id.btn_event_sound);

        // find number of pages
        resource1 = getResources().getIdentifier("event_"+listItem.sub_category+"_txt1", "string", getActivity().getPackageName());
        resource2 = getResources().getIdentifier("event_"+listItem.sub_category+"_txt2", "string", getActivity().getPackageName());
        resource3 = getResources().getIdentifier("event_"+listItem.sub_category+"_txt3", "string", getActivity().getPackageName());
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

        eventInfoImage.setImageResource(getActivity().getResources().getIdentifier("event_"+listItem.sub_category+currentPage, "drawable", getActivity().getPackageName()));
        eventInfoText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+listItem.sub_category+"_txt"+currentPage, "string", getActivity().getPackageName())));

        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(context) {
            public void onSwipeRight() {

                if(currentPage > 1) {
                    currentPage--;
                }
                else{
                    currentPage = pages;
                }

                int resourceId = context.getResources().getIdentifier("event_"+listItem.sub_category+currentPage, "drawable", getActivity().getPackageName());
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
                eventInfoImage.setImageResource(getActivity().getResources().getIdentifier("event_"+listItem.sub_category+currentPage, "drawable", getActivity().getPackageName()));
                eventInfoText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+listItem.sub_category+"_txt"+currentPage, "string", getActivity().getPackageName())));

            }

            public void onSwipeLeft() {

                if(currentPage < pages) {

                    currentPage++;

                }
                else{
                    currentPage = 1;
                }

                System.out.println(currentPage);

                int resourceId = context.getResources().getIdentifier("event_"+listItem.sub_category+currentPage, "drawable", getActivity().getPackageName());
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
                eventInfoImage.setImageResource(getActivity().getResources().getIdentifier("event_"+listItem.sub_category+currentPage, "drawable", getActivity().getPackageName()));
                eventInfoText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+listItem.sub_category+"_txt"+currentPage, "string", getActivity().getPackageName())));

            }
        });

        soundButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int soundResourceId = context.getResources().getIdentifier("event_"+listItem.sub_category+currentPage, "raw", getActivity().getPackageName());
                if (soundResourceId != 0) {
                    MediaPlayer mp = MediaPlayer.create(context, soundResourceId);
                    mp.start();
                }
            }
        });

        return view;
    }

    public void setItem(Event selectedListItem){
        listItem = selectedListItem;
    }

}