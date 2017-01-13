package com.cancercarecompany.ccc.ccc;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CareTeamPatientFragment extends Fragment {

    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position;
    private boolean admin = false;
    private boolean myUser = false;

    TextView txtName;
    TextView txtDiagnose;
    EditText editName;
    EditText editDiagnose;
    ImageButton familyAvatar;
    ImageButton buttonDelete;
    ImageButton buttonSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View view = inflater.inflate(R.layout.fragment_care_team_patient, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.careteam_contact));
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.careteam_family_header);
            layout.setVisibility(View.GONE);
            setHasOptionsMenu(true);
        }

        connectHandler = ConnectionHandler.getInstance();

        connectHandler.getPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

        txtName = (TextView) view.findViewById(R.id.txt_careteam_name);
        editName = (EditText) view.findViewById(R.id.etxt_careteam_name);
        txtDiagnose = (TextView) view.findViewById(R.id.txt_careteam_diagnose);
        editDiagnose = (EditText) view.findViewById(R.id.etx_careteam_diagnose);
        familyAvatar = (ImageButton) view.findViewById(R.id.img_careteam_family_avatar);
        buttonSave = (ImageButton) view.findViewById(R.id.btn_save);
        int familyAvatarId = 0;

        //familyAvatarId = connectHandler.patient.care_team.get(position).avatar;
        editName.setText(connectHandler.patient.patient_name);
        editDiagnose.setText(connectHandler.patient.diagnose);

            // check admin
        for (int i=0; i < connectHandler.patient.care_team.size(); i++){
            if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                (connectHandler.patient.care_team.get(i).admin == 1)){
                    admin = true;
            }
        }


        if (!admin){
            buttonDelete.setVisibility(View.INVISIBLE);
            buttonSave.setVisibility(View.INVISIBLE);
            editName.setFocusable(false);
            editName.setFocusableInTouchMode(false);
            editName.setEnabled(false);
            editDiagnose.setFocusable(false);
            editDiagnose.setFocusableInTouchMode(false);
            editDiagnose.setEnabled(false);
        }

        switch (familyAvatarId) {
            case 0:
                familyAvatar.setImageResource(R.drawable.addcontact);
                break;
            case 1:
                familyAvatar.setImageResource(R.drawable.family_avatar_1);
                break;
            case 2:
                familyAvatar.setImageResource(R.drawable.family_avatar_2);
                break;
            case 3:
                familyAvatar.setImageResource(R.drawable.family_avatar_3);
                break;
            case 4:
                familyAvatar.setImageResource(R.drawable.family_avatar_4);
                break;
            case 5:
                familyAvatar.setImageResource(R.drawable.family_avatar_5);
                break;
            case 6:
                familyAvatar.setImageResource(R.drawable.family_avatar_6);
                break;
            case 7:
                familyAvatar.setImageResource(R.drawable.family_avatar_7);
                break;
            case 8:
                familyAvatar.setImageResource(R.drawable.family_avatar_8);
                break;
            case 9:
                familyAvatar.setImageResource(R.drawable.family_avatar_9);
                break;
            case 10:
                familyAvatar.setImageResource(R.drawable.family_avatar_10);
                break;
            case 11:
                familyAvatar.setImageResource(R.drawable.family_avatar_11);
                break;
            case 12:
                familyAvatar.setImageResource(R.drawable.family_avatar_12);
                break;
            case 13:
                familyAvatar.setImageResource(R.drawable.family_avatar_13);
                break;
            case 14:
                familyAvatar.setImageResource(R.drawable.family_avatar_14);
                break;
            case 15:
                familyAvatar.setImageResource(R.drawable.family_avatar_15);
                break;
            case 16:
                familyAvatar.setImageResource(R.drawable.family_avatar_16);
                break;
            case 17:
                familyAvatar.setImageResource(R.drawable.family_avatar_17);
                break;
            case 18:
                familyAvatar.setImageResource(R.drawable.family_avatar_18);
                break;
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // saveUser();
            }
        });

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_details_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            if (myUser){
                connectHandler.deleteUser(listItem.id);
            }
            return true;
        }

        if (id == R.id.action_save) {
            savePatient();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void savePatient() {
        // code will follow
   }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}