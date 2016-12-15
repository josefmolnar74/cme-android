package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

/**
 * Created by Josef on 2016-12-15.
 */
public class AvatarsDialogFragment extends DialogFragment {

    public static final String AVATAR_TYPE = "avatar_type";
    public static final String AVATAR_FAMILY = "family";
    public static final String AVATAR_HEALTHCARE = "healthcare";
    public static final String AVATAR_ID = "avatar_id";

    public Integer[] mFamilyAvatars = {
            R.drawable.family_avatar_1, R.drawable.family_avatar_2,
            R.drawable.family_avatar_3, R.drawable.family_avatar_4,
            R.drawable.family_avatar_5, R.drawable.family_avatar_6,
            R.drawable.family_avatar_7, R.drawable.family_avatar_8,
            R.drawable.family_avatar_9, R.drawable.family_avatar_10,
            R.drawable.family_avatar_11, R.drawable.family_avatar_12,
            R.drawable.family_avatar_13, R.drawable.family_avatar_14,
            R.drawable.family_avatar_13, R.drawable.family_avatar_15,
            R.drawable.family_avatar_16, R.drawable.family_avatar_17,
            R.drawable.family_avatar_18
    };

    public Integer[] mHealthcareAvatars = {
            R.drawable.avatar_healthcare_doctor_female, R.drawable.avatar_healthcare_nurse,
            R.drawable.avatar_healthcare_anestetist, R.drawable.avatar_healthcare_doctor_male,
            R.drawable.avatar_healthcare_surgeon
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.fragment_avatars_dialog, container, false);
        ImageButton dismissButton = (ImageButton) rootView.findViewById(R.id.btn_dialog_dismiss);

        GridView gridview = (GridView) rootView.findViewById(R.id.grid_avatars);
        if (getArguments().getString(AVATAR_TYPE).matches(AVATAR_FAMILY)){
            gridview.setAdapter(new AvatarsDialogAdapter(getContext(), mFamilyAvatars));
        } else{
            gridview.setAdapter(new AvatarsDialogAdapter(getContext(), mHealthcareAvatars));
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){

                Bundle args = new Bundle();
                args.putInt(AvatarsDialogFragment.AVATAR_ID, position);
            }
        });

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