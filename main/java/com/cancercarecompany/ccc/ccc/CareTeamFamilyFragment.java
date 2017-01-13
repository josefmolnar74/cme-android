package com.cancercarecompany.ccc.ccc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

public class CareTeamFamilyFragment extends Fragment {

    public static final int AVATARSDIALOG_FRAGMENT = 1; // class variable

    OnCareTeamFamilyCompletedListener mListener;
    ViewGroup mContainer;
    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position;
    private boolean admin = false;
    private boolean myUser = false;
    private int familyAvatarId;

    TextView txtName;
    TextView txtEmail;
    TextView txtRelation;
    EditText editName;
    EditText editEmail;
    EditText editRelation;
    ImageButton familyAvatar;
    ImageButton deleteButton;
    ImageButton saveButton;
    TextView alertText1;
    TextView alertText2;
    CheckBox chkAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContainer = container;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View view = inflater.inflate(R.layout.fragment_care_team_family, container, false);

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

        txtName = (TextView) view.findViewById(R.id.txt_careteam_name);
        txtEmail = (TextView) view.findViewById(R.id.txt_careteam_family_email);
        txtRelation = (TextView) view.findViewById(R.id.txt_careteam_family_relation);
        editName = (EditText) view.findViewById(R.id.etxt_careteam_name);
        editEmail = (EditText) view.findViewById(R.id.etx_careteamt_email);
        editRelation = (EditText) view.findViewById(R.id.etxt_careteam_relation);
        familyAvatar = (ImageButton) view.findViewById(R.id.img_careteam_family_avatar);
        deleteButton = (ImageButton) view.findViewById(R.id.btn_delete);
        saveButton = (ImageButton) view.findViewById(R.id.btn_save);
        alertText1 = (TextView) view.findViewById(R.id.txt_careteam_invite_alert);
        alertText2 = (TextView) view.findViewById(R.id.txt_careteam_edit_alert);
        chkAdmin = (CheckBox) view.findViewById(R.id.chkbx_careteam);
        alertText1.setVisibility(View.INVISIBLE);
        alertText2.setVisibility(View.INVISIBLE);
        familyAvatarId = 0;

        editName.setFocusable(false);
        editName.setFocusableInTouchMode(false);
        editName.setEnabled(false);
        editEmail.setFocusable(false);
        editEmail.setFocusableInTouchMode(false);
        editEmail.setEnabled(false);
        editRelation.setFocusable(false);
        editRelation.setFocusableInTouchMode(false);
        editRelation.setEnabled(false);

        familyAvatar.setFocusable(false);
        familyAvatar.setFocusableInTouchMode(false);
        familyAvatar.setEnabled(false);

        TextView textInvited = (TextView) view.findViewById(R.id.text_careteam_invited);

        if (listItem.type.matches("invite")){
            textInvited.setVisibility(View.VISIBLE);
        } else{
            textInvited.setVisibility(View.INVISIBLE);
        }

        switch(listItem.type) {

            case "family":
                saveButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
                    if (connectHandler.patient.care_team.get(i).person_ID == listItem.id) {
                        position = i;
                        familyAvatarId = connectHandler.patient.care_team.get(position).avatar;
                        editName.setText(connectHandler.patient.care_team.get(position).name);
                        editEmail.setText(connectHandler.patient.care_team.get(position).email);
                        editRelation.setText(connectHandler.patient.care_team.get(position).relationship);
                        if (connectHandler.patient.care_team.get(position).admin == 1){
                            chkAdmin.setChecked(true);
                        }
                        break;
                    }
                }
                if (connectHandler.person.person_ID == connectHandler.patient.care_team.get(position).person_ID){
                    myUser = true;
                }
                break;
            case "invite":
                saveButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.VISIBLE);
                for (int i = 0; i < connectHandler.invites.invite_data.size(); i++) {
                    if (connectHandler.invites.invite_data.get(i).invite_ID == listItem.id) {
                        position = i;
                        familyAvatarId = connectHandler.invites.invite_data.get(position).invited_avatar;
                        editName.setText(connectHandler.invites.invite_data.get(position).invited_name);
                        editEmail.setText(connectHandler.invites.invite_data.get(position).invited_email);
                        editRelation.setText(connectHandler.invites.invite_data.get(position).invited_relationship);
                        if (connectHandler.invites.invite_data.get(position).invited_admin == 1){
                            chkAdmin.setChecked(true);
                        }
                        break;
                    }
                }
                break;
            case "healthcare":
                break;
            case "new":
                saveButton.setVisibility(View.VISIBLE);
                editName.setFocusable(true);
                editName.setFocusableInTouchMode(true);
                editName.setEnabled(true);
                editName.requestFocus();
                editEmail.setFocusable(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setEnabled(true);
                editRelation.setFocusable(true);
                editRelation.setFocusableInTouchMode(true);
                editRelation.setEnabled(true);
                break;
        }

            // check admin
        for (int i=0; i < connectHandler.patient.care_team.size(); i++){
            if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                (connectHandler.patient.care_team.get(i).admin == 1)){
                    admin = true;
            }
        }
/*
        if (myUser || admin){
            deleteButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            if (myUser){
                editName.setFocusable(true);
                editName.setFocusableInTouchMode(true);
                editName.setEnabled(true);
                editName.requestFocus();
                editEmail.setFocusable(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setEnabled(true);
                editRelation.setFocusable(true);
                editRelation.setFocusableInTouchMode(true);
                editRelation.setEnabled(true);
            }
        }else{
            deleteButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
            editName.setFocusable(false);
            editName.setFocusableInTouchMode(false);
            editName.setEnabled(false);
            editEmail.setFocusable(false);
            editEmail.setFocusableInTouchMode(false);
            editEmail.setEnabled(false);
            editRelation.setFocusable(false);
            editRelation.setFocusableInTouchMode(false);
            editRelation.setEnabled(false);
        }
*/
        setAvatarResource(familyAvatarId);

        if (listItem.type == "new"){
            deleteButton.setVisibility(View.INVISIBLE);
            editName.requestFocus();
            txtName.setVisibility(View.INVISIBLE);
            txtEmail.setVisibility(View.INVISIBLE);
            txtRelation.setVisibility(View.INVISIBLE);
        }else if (!admin){
            editName.setFocusable(false);
            editEmail.setFocusable(false);
            editRelation.setFocusable(false);
            chkAdmin.setFocusable(false);
            chkAdmin.setEnabled(false);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCareTeamMember();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        familyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItem.type.matches("patient")){
//                    startDialog();
                }
            }
        });

        return view;

    }

    void startDialog() {
        // Open avatar dialog fragment and use bundle to send family avatar type
        AvatarsDialogFragment avatarFragment = new AvatarsDialogFragment();
        Bundle args = new Bundle();
        args.putString(AvatarsDialogFragment.AVATAR_TYPE, AvatarsDialogFragment.AVATAR_FAMILY);
        avatarFragment.setArguments(args);
        avatarFragment.setTargetFragment(this, AVATARSDIALOG_FRAGMENT);
        avatarFragment.show(getFragmentManager().beginTransaction(), "Josef");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AVATARSDIALOG_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    familyAvatarId = bundle.getInt("avatar");
                    setAvatarResource(familyAvatarId);
                } else if (resultCode == Activity.RESULT_CANCELED) {}
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_details, menu);
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
            removeCareTeamMember();
            return true;
        }

        if (id == R.id.action_save) {
            saveUser();
//            getFragmentManager().popBackStack();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveUser() {
        String nameString = editName.getText().toString();
        String emailString = editEmail.getText().toString();

        //FirstName and email must be specified, the others will get emptystring if not specified
        boolean wrongInput = false;

        if ((nameString.isEmpty()) || (emailString.isEmpty())){
            wrongInput = true;
            alertText1.setText(getString(R.string.careteam_alert1));
            alertText1.setVisibility(View.VISIBLE);
        }

        if (!wrongInput){
            alertText1.setVisibility(View.INVISIBLE);
            // invite new care team member
            int admin;
            if(chkAdmin.isChecked()) {
                admin = 1;
            }
            else {
                admin = 0;
            }

            if (listItem.type == "new"){

                Invite newInvite = new Invite(  0,
                        connectHandler.person.name,
                        connectHandler.patient.patient_ID,
                        connectHandler.patient.patient_name,
                        nameString,
                        emailString,
                        editRelation.getText().toString(),
                        0,
                        1, // force admin to 1, replace with admin later
                        0,
                        0);

                connectHandler.inviteCareTeamMember(newInvite);
                while (connectHandler.socketBusy){}
            }
            else if (myUser){
                // update my information
                Person updatedPerson = new Person(
                        listItem.id,
                        nameString,
                        emailString,
                        null,
                        familyAvatarId,
                        null
                );
                connectHandler.updateUser(updatedPerson);
            }
            closeFragment();
//            getActivity().onBackPressed();
        }
    }

    public void setAvatarResource(Integer familyAvatarId){
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
    }

    void removeCareTeamMember(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String alertText = String.format(getString(R.string.remove_question));

        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                switch(listItem.type){
                    case "family":
                        connectHandler.deleteUser(listItem.id);
                        break;

                    case "invite":
                        connectHandler.deleteCareTeamInvite(listItem.id);
                        break;
                }
                while(connectHandler.socketBusy){}
                closeFragment();
//                getFragmentManager().popBackStack();
            }
        });

        alertDialogBuilder.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }


    private void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
        if (mContainer.getId() == R.id.careteam_placeholder2){
            mListener.onCareTeamFamilyComplete();
        }
    }

    public interface OnCareTeamFamilyCompletedListener {
        // To call when save or delete has been completed to update exp list
        void onCareTeamFamilyComplete();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCareTeamFamilyCompletedListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

}