package com.cancercarecompany.ccc.ccc;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class JournalDetailsFragment extends Fragment {

    private SideeffectExpandListItem listItem;
    private ConnectionHandler connectHandler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_journal_details, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setHasOptionsMenu(true);
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle(listItem.sideeffect.type);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lay_journal_detail);
            layout.setVisibility(View.GONE);
        }

        LinearLayout seekbarLayout = (LinearLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbars);

        switch(listItem.sideeffect.type){

            case JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_HYGIENE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_BREATHING:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_URINATION:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_FEVER:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_NAUSEA:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_NOSE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_PAIN:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_SEX:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_ITCH:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_ABUSE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING:
                break;

        }
        return view;

    }

    public void setItem(SideeffectExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}