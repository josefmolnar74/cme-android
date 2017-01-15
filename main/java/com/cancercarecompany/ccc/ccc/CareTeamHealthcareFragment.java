package com.cancercarecompany.ccc.ccc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CareTeamHealthcareFragment extends Fragment{

    public static final int AVATARSDIALOG_FRAGMENT = 1; // class variable

    OnCareTeamHealthcareCompletedListener mListener;
    ViewGroup mContainer;

    private UserLoginTask mAuthTask = null;
    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position = 0;
    private boolean admin = false;
    private int healthcareAvatarId;

    private TextView txtTitle;
    private TextView txtDepartment;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtPhone1;
    private TextView txtPhone2;
    private TextView txtPhone3;
    private EditText editTitle;
    private EditText editDepartment;
    private EditText editName;
    private EditText editEmail;
    private EditText editPhone1;
    private EditText editPhone2;
    private EditText editPhone3;
    private ImageButton healthcareAvatar;
    private ImageButton buttonDelete;
    private ImageButton buttonSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContainer = container;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_care_team_healthcare, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.careteam_contact));
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.careteam_healthcare_header);
            layout.setVisibility(View.GONE);
            setHasOptionsMenu(true);
        }

        connectHandler = ConnectionHandler.getInstance();

        txtTitle = (TextView) view.findViewById(R.id.txt_careteam_healthcare_title);
        txtDepartment = (TextView) view.findViewById(R.id.txt_careteam_healthcare_department);
        txtName = (TextView) view.findViewById(R.id.txt_careteam_healthcare_name);
        txtEmail = (TextView) view.findViewById(R.id.txt_careteam_healthcare_email);
        txtPhone1 = (TextView) view.findViewById(R.id.txt_careteam_healthcare_phone1);
        txtPhone2 = (TextView) view.findViewById(R.id.txt_careteam_healthcare_phone2);
        txtPhone3 = (TextView) view.findViewById(R.id.txt_careteam_healthcare_phone3);
        editTitle = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_title);
        editDepartment = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_department);
        editName = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_name);
        editEmail = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_email);
        editPhone1 = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_phone1);
        editPhone2 = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_phone2);
        editPhone3 = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_phone3);
        healthcareAvatar = (ImageButton) view.findViewById(R.id.img_careteam_healthcare_avatar);
        buttonDelete = (ImageButton) view.findViewById(R.id.btn_delete);
        buttonSave = (ImageButton) view.findViewById(R.id.btn_save);

        // check admin
        for (int i=0; i < connectHandler.patient.care_team.size(); i++){
            if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                    (connectHandler.patient.care_team.get(i).admin == 1)){
                admin = true;
                buttonDelete.setVisibility(View.VISIBLE);
            }
        }

        if (!admin){
            buttonDelete.setVisibility(View.INVISIBLE);
            buttonSave.setVisibility(View.INVISIBLE);
            editTitle.setFocusable(false);
            editTitle.setFocusableInTouchMode(false);
            editTitle.setEnabled(false);
            editTitle.requestFocus();
            editDepartment.setFocusable(false);
            editDepartment.setFocusableInTouchMode(false);
            editDepartment.setEnabled(false);
            editName.setFocusable(false);
            editName.setFocusableInTouchMode(false);
            editName.setEnabled(false);
            editEmail.setFocusable(false);
            editEmail.setFocusableInTouchMode(false);
            editEmail.setEnabled(false);
            editPhone1.setFocusable(false);
            editPhone1.setFocusableInTouchMode(false);
            editPhone1.setEnabled(false);
            editPhone2.setFocusable(false);
            editPhone2.setFocusableInTouchMode(false);
            editPhone2.setEnabled(false);
            editPhone3.setFocusable(false);
            editPhone3.setFocusableInTouchMode(false);
            editPhone3.setEnabled(false);
        }

        if (listItem.type == "healthcare"){
            for (int i = 0; i < connectHandler.healthcare.healthcare_data.size(); i++) {
                if (connectHandler.healthcare.healthcare_data.get(i).healthcare_ID == listItem.id) {
                    position = i;
                    healthcareAvatarId = connectHandler.healthcare.healthcare_data.get(i).avatar;
                    editTitle.setText(connectHandler.healthcare.healthcare_data.get(i).title);
                    editDepartment.setText(connectHandler.healthcare.healthcare_data.get(i).department);
                    editName.setText(connectHandler.healthcare.healthcare_data.get(i).name);
                    editEmail.setText(connectHandler.healthcare.healthcare_data.get(i).email);
                    editPhone1.setText(connectHandler.healthcare.healthcare_data.get(i).phone_number1);
                    editPhone2.setText(connectHandler.healthcare.healthcare_data.get(i).phone_number2);
                    editPhone3.setText(connectHandler.healthcare.healthcare_data.get(i).phone_number3);
                    if(editTitle.getText().toString().isEmpty()){
                        editTitle.setVisibility(View.INVISIBLE);
                    }
                    if(editDepartment.getText().toString().isEmpty()){
                        editDepartment.setVisibility(View.INVISIBLE);
                    }
                    if(editName.getText().toString().isEmpty()){
                        editName.setVisibility(View.INVISIBLE);
                    }
                    if(editEmail.getText().toString().isEmpty()){
                        editEmail.setVisibility(View.INVISIBLE);
                    }
                    if(editPhone1.getText().toString().isEmpty()){
                        editPhone1.setVisibility(View.INVISIBLE);
                    }
                    if(editPhone2.getText().toString().isEmpty()){
                        editPhone2.setVisibility(View.INVISIBLE);
                    }
                    if(editPhone3.getText().toString().isEmpty()){
                        editPhone3.setVisibility(View.INVISIBLE);
                    }
                    break;
                }
            }
        }

        setAvatarResource(healthcareAvatarId);

        if (listItem.type == "new") {
            buttonDelete.setVisibility(View.INVISIBLE);
            editTitle.requestFocus();
            txtTitle.setVisibility(View.INVISIBLE);

        }
        if (!admin){
            editTitle.setFocusable(false);
            editDepartment.setFocusable(false);
            editName.setFocusable(false);
            editEmail.setFocusable(false);
            editPhone1.setFocusable(false);
            editPhone2.setFocusable(false);
            editPhone3.setFocusable(false);
        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHealthcare();
            }

        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHealthcare();
            }
        });

        healthcareAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();
            }
        });
        return view;

    }

    void setAvatarResource(int healthcareAvatarId){
        switch (healthcareAvatarId) {
            case 0:
                healthcareAvatar.setImageResource(R.drawable.addcontact);
                break;
            case 1:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_female);
                break;
            case 2:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_nurse);
                break;
            case 3:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_anestetist);
                break;
            case 4:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_male);
                break;
            case 5:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_surgeon);
                break;
        }
    }

    void startDialog() {
        // Open avatar dialog fragment and use bundle to send family avatar type
        AvatarsDialogFragment avatarFragment = new AvatarsDialogFragment();
        Bundle args = new Bundle();
        args.putString(AvatarsDialogFragment.AVATAR_TYPE, AvatarsDialogFragment.AVATAR_HEALTHCARE);
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
                    healthcareAvatarId = bundle.getInt("avatar");
                    setAvatarResource(healthcareAvatarId);
                } else if (resultCode == Activity.RESULT_CANCELED) {}
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {        // Inflate the menu; this adds items to the action bar if it is present.
        switch(listItem.type){

            case "new":
                inflater.inflate(R.menu.menu_details_save, menu);
                break;
            default:
                inflater.inflate(R.menu.menu_details, menu);
                break;
        }
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
            if (admin){
                deleteHealthcare();
            }
            return true;
        }

        if (id == R.id.action_save) {
            if (admin){
                saveHealthcare();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void deleteHealthcare(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String alertText = String.format(getString(R.string.remove_question));

        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                connectHandler.deleteHealthcare(listItem.id);
                mAuthTask = new UserLoginTask();
                mAuthTask.execute((Void) null);
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

    void saveHealthcare(){
        if (listItem.type == "new"){
            HealthCare newHealthCare = new HealthCare(
                    0,
                    connectHandler.patient.patient_ID,
                    editTitle.getText().toString(),
                    editName.getText().toString(),
                    editDepartment.getText().toString(),
                    editPhone1.getText().toString(),
                    editPhone2.getText().toString(),
                    editPhone3.getText().toString(),
                    editEmail.getText().toString(),
                    0); // must fix avatar

            connectHandler.createHealthcare(newHealthCare);
        }else{
            //update existing healthcare member
            HealthCare updateHealthCare = new HealthCare(
                    connectHandler.healthcare.healthcare_data.get(position).healthcare_ID,
                    connectHandler.patient.patient_ID,
                    editTitle.getText().toString(),
                    editName.getText().toString(),
                    editDepartment.getText().toString(),
                    editPhone1.getText().toString(),
                    editPhone2.getText().toString(),
                    editPhone3.getText().toString(),
                    editEmail.getText().toString(),
                    healthcareAvatarId);

            connectHandler.updateHealthcare(updateHealthCare);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        }
    }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }

    private void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
        if (mContainer.getId() == R.id.careteam_placeholder2){
            mListener.onCareTeamHealthcareComplete();
        }
    }

    public interface OnCareTeamHealthcareCompletedListener {
        // To call when save or delete has been completed to update exp list
        void onCareTeamHealthcareComplete();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCareTeamHealthcareCompletedListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            while(connectHandler.pendingMessage){}

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                closeFragment();
            } else {
                //
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}