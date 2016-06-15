package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;


public class ManageCareTeamActivity extends AppCompatActivity {


    EditText first_name;
    String last_name;
    Integer GCTp;

    ArrayList<Events> eventList;
    ArrayList<Patient> patientList = new ArrayList<>();
    ArrayList<CareTeamMember> memberlist = new ArrayList<>();

    ListView customListView;
    ListView memberListView;
    CareTeamFamilyAdapter listAdapter;
    CareTeamHealthCareAdapter memberAdapter;
    Lcl_work_area lcl;
    String lclString;
    RelativeLayout relativeLayout;
    String lbl_friends;
    String lbl_delete;
    Button add_CTmember;
    ImageButton journeyButton;
    ImageButton journalButton;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_care_team);

        customListView = (ListView) findViewById(R.id.careTeamListView);

        final Button btn_add_Memb = (Button) findViewById(R.id.btn_add_CTmember);
        final Button btn_add_CT   = (Button) findViewById(R.id.btn_add_CT);
        final Button btn_del_CT   = (Button) findViewById(R.id.btn_del_CT);
        journeyButton = (ImageButton) findViewById(R.id.journeyButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        lclString = (String) preferences.getString("Person", "");

        System.out.println(lclString);
        Gson gson = new Gson();

        lcl = gson.fromJson(lclString, Lcl_work_area.class);

        for (int i = 0; i < lcl.patient.size(); i++) {
            patientList.add(lcl.patient.get(i));
        }

//        listAdapter = new CareTeamFamilyAdapter(this, patientList);
        customListView.setAdapter(listAdapter);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int CTp, long id) {
                GCTp = CTp;
                showCareTeamMembers(CTp);
            }
        });

        journeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journeyActivity();

            }
        });

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalActivity();
            }
        });


        btn_add_Memb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_CTmember(GCTp);
            }
        });

        btn_add_CT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_CT();
            }
        });

        btn_del_CT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GCTp == null) {
                    GCTp = 0;
                }
                del_CT(GCTp);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void del_CT(final int GCTp) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.del_ct_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 700, 700);

        final TextView tw_delete = (TextView) popupView.findViewById(R.id.lbl_delete);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final Button btn_yes = (Button) popupView.findViewById(R.id.btn_yes);
        final Button btn_cancel = (Button) popupView.findViewById(R.id.btn_cancel);

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.del_ct_pop);

        popupWindow.showAsDropDown(relativeLayout, 560, -200);
// Sätt ihop rubriken???
        Resources res = getResources();
        lbl_delete = String.format(res.getString(R.string.delete_name), (patientList.get(GCTp).patient_name));
        tw_delete.setText(lbl_delete.toString());
// Sätt ihop rubriken

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove_CT(GCTp);
            }
            private void remove_CT(int GCTp) {
                lcl.patient.get(GCTp).care_team.removeAll(lcl.patient.get(GCTp).care_team);
                lcl.patient.remove(GCTp);
                memberAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Avbryt");
                popupWindow.dismiss();
            }
        });
    }

    private void add_CT() {
        System.out.println("add_CT");
        popup_create_CT();
    }

    private void add_CTmember(int GCTp) {
        System.out.println(("add_CTmember"));
        popup_create_CTmember(GCTp);
    }

    public void popup_create_CTmember(final int CTp) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.create_care_team_member_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 1000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText edt_firstN   = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final EditText edt_lastN    = (EditText) popupView.findViewById(R.id.txt_secondName_careteam);
        final EditText edt_Phone    = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final EditText edt_Email    = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final TextView Relation     = (TextView) popupView.findViewById(R.id.lbl_relation_careteam);
        final Spinner  edt_Relation = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
        final String[] sp_Rel       = {"Pappa", "Mamma", "Patient", "Läkare"};
        final Spinner  edt_Admin    = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] sp_Admin     = {"Ja", "Nej"};
        final Button   btn_save     = (Button) popupView.findViewById(R.id.btn_save_careteam);
        final Button   btn_cancel   = (Button) popupView.findViewById(R.id.btn_cancel_careteam);

        Spinner spinner_rel = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
        ArrayAdapter<String> adapter_rel = new ArrayAdapter<String>(ManageCareTeamActivity.this,
                android.R.layout.simple_spinner_item, sp_Rel);
        adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rel.setAdapter(adapter_rel);
        Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(ManageCareTeamActivity.this, android.R.layout.simple_spinner_item, sp_Admin);
        adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_admin.setAdapter(adapter_admin);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btn_save.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCTMember(CTp);
            }
            private void saveCTMember(int CTp) {

                Relation.setText(edt_Relation.getSelectedItem().toString());
                //Admin.setText(edt_Admin.getSelectedItem().toString());
/*
                if(edt_Admin.getSelectedItem() == "Ja") {
                    CareTeamMember newMember = new CareTeamMember(edt_firstN.getText().toString(),edt_lastN.getText().toString(),edt_Email.getText().toString(),
                            edt_Relation.getSelectedItem().toString(), 0);
                    lcl.patient.get(CTp).care_team.add(newMember);
                } else {
                    CareTeamMember newMember = new CareTeamMember(edt_firstN.getText().toString(),edt_lastN.getText().toString(),edt_Email.getText().toString(),
                            edt_Relation.getSelectedItem().toString(), 1);
                    lcl.patient.get(CTp).care_team.add(newMember);
                }
*/
                memberAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.create_care_team_member_popup);
        //  LinearLayout layout = (LinearLayout) findViewById(R.id.careTeamScreen);
        popupWindow.showAsDropDown(relativeLayout, 500, -900);
        popupWindow.isFocusable();


    }

    public void popup_create_CT() {
        System.out.println("Create_CT");

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.create_care_team_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 1000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText edt_firstN   = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final EditText edt_lastN    = (EditText) popupView.findViewById(R.id.txt_secondName_careteam);
        final EditText edt_Phone    = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final EditText edt_Email    = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final TextView Relation     = (TextView) popupView.findViewById(R.id.lbl_relation_careteam);
        final Spinner  edt_Relation = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
        final String[] sp_Rel       = {"Pappa", "Mamma", "Patient", "Läkare"};
        final Spinner  edt_Admin    = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] sp_Admin     = {"Ja", "Nej"};
        final Button   btn_save     = (Button) popupView.findViewById(R.id.btn_save_careteam);
        final Button   btn_cancel   = (Button) popupView.findViewById(R.id.btn_cancel_careteam);

        Spinner spinner_rel = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
        ArrayAdapter<String> adapter_rel = new ArrayAdapter<String>(ManageCareTeamActivity.this,
                android.R.layout.simple_spinner_item, sp_Rel);
        adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_rel.setAdapter(adapter_rel);
        Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(ManageCareTeamActivity.this, android.R.layout.simple_spinner_item, sp_Admin);
        adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_admin.setAdapter(adapter_admin);

        spinner_rel.setSelection(2);
        spinner_rel.setEnabled(false);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btn_save.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Save");
                saveCT();
            }
            private void saveCT() {
/*
                memberlist.clear();
                CareTeamMember newCT = new CareTeamMember(edt_firstN.getText().toString(),edt_lastN.getText().toString(),edt_Email.getText().toString(),
                        edt_Relation.getSelectedItem().toString(),1);
                memberlist.add(newCT);
*/
//                Patient newPatient = new Patient();
                //                   new Patient(0, edt_firstN.getText().toString(), edt_Relation.getSelectedItem().toString(), memberlist, null);
                // Care_team newMember = new Care_team(edt_firstN.getText().toString(),edt_lastN.getText().toString(),edt_Email.getText().toString(),
                //         edt_Relation.getSelectedItem().toString(), 0);
//                lcl.patient.add(newPatient);
//                patientList.add(newPatient);
//                lcl.patient.get(CTp).care_team.add(newMember);

                memberAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.create_care_team_popup);
        popupWindow.showAsDropDown(relativeLayout, 500, -900);

    }



    private void showCareTeamMembers(final int CTp) {

        memberListView = (ListView) findViewById(R.id.careTeamMemberListView);
        memberAdapter  = new CareTeamHealthCareAdapter(this, lcl.patient.get(CTp).care_team);
        memberListView.setAdapter(memberAdapter);

        customListView = (ListView) findViewById(R.id.careTeamListView);
        listAdapter    = new CareTeamFamilyAdapter(this, null);
        customListView.setAdapter(listAdapter);

// Sätt ihop rubriken
        Resources res = getResources();
        lbl_friends = String.format(res.getString(R.string.txt_friends), (patientList.get(CTp).patient_name));
        TextView lbl_careteammembers = (TextView) findViewById(R.id.lbl_friends);
        lbl_careteammembers.setText(lbl_friends);
// Sätt ihop rubriken

        memberListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int MLp, long id) {
                popup(CTp, MLp);
            }
        });
    }
    public void popup(final int CTp, final int MLp) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.care_team_member_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 1000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final TextView Firstname   = (TextView) popupView.findViewById(R.id.lbl_firstName_careteam);
        final EditText edt_firstN  = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final TextView Lastname    = (TextView) popupView.findViewById(R.id.lbl_secondName_careteam);
        final EditText edt_lastN   = (EditText) popupView.findViewById(R.id.txt_secondName_careteam);
        final TextView Phone       = (TextView) popupView.findViewById(R.id.lbl_phone_careteam);
        final EditText edt_Phone   = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final TextView Email       = (TextView) popupView.findViewById(R.id.lbl_email_careteam);
        final EditText edt_Email   = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final TextView Relation    = (TextView) popupView.findViewById(R.id.lbl_relation_careteam);
        final Spinner edt_Relation = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
        final String[] sp_Rel      = {"Pappa", "Mamma", "Patient", "Läkare"};
        final TextView Admin       = (TextView) popupView.findViewById(R.id.lbl_admin_careteam);
        final Spinner edt_Admin    = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] sp_Admin    = {"Ja", "Nej"};

        final Button btn_edit      = (Button) popupView.findViewById(R.id.btn_edit_careteam);
        final Button btn_save      = (Button) popupView.findViewById(R.id.btn_save_careteam);
        final Button btn_cancel    = (Button) popupView.findViewById(R.id.btn_cancel_careteam);
        final Button btn_del_memb  = (Button) popupView.findViewById(R.id.btn_del_ct_memb);

        Firstname.setText(lcl.patient.get(CTp).care_team.get(MLp).first_name);
        Lastname.setText(lcl.patient.get(CTp).care_team.get(MLp).last_name);
        Email.setText(lcl.patient.get(CTp).care_team.get(MLp).email);
        Relation.setText(lcl.patient.get(CTp).care_team.get(MLp).relationship);
/*        if ((lcl.patient.get(CTp).care_team.get(MLp).admin) == 1) {
            Admin.setText("Ja");
        } else {
            Admin.setText("Nej");
        }
 */       btn_edit.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_del_memb.setVisibility(View.VISIBLE);
        btn_save.setVisibility(View.INVISIBLE);

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.care_team_member_popup);
        popupWindow.showAsDropDown(relativeLayout, 500, -900);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                SaveContact(CTp, MLp);
            }

            private void SaveContact(int CTp, int MLp) {
                Firstname.setText(edt_firstN.getText().toString());
                Lastname.setText(edt_lastN.getText().toString());
                Email.setText(edt_Email.getText().toString());
                Relation.setText(edt_Relation.getSelectedItem().toString());
                Admin.setText(edt_Admin.getSelectedItem().toString());

                lcl.patient.get(CTp).care_team.get(MLp).first_name = Firstname.getText().toString();
                lcl.patient.get(CTp).care_team.get(MLp).last_name = Lastname.getText().toString();
                lcl.patient.get(CTp).care_team.get(MLp).email = Email.getText().toString();
                lcl.patient.get(CTp).care_team.get(MLp).relationship = Relation.getText().toString();
                if (Admin.getText() == "Ja") {
//                    lcl.patient.get(CTp).care_team.get(MLp).admin = 1;
                } else {
//                    lcl.patient.get(CTp).care_team.get(MLp).admin = 0;
                }

                memberAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
            }

            private void Done(int CTp, int MLp) {
                edt_firstN.setVisibility(View.INVISIBLE);
                Firstname.setVisibility(View.VISIBLE);

                edt_lastN.setVisibility(View.INVISIBLE);
                Lastname.setVisibility(View.VISIBLE);

                edt_Phone.setVisibility(View.INVISIBLE);
                Phone.setVisibility(View.VISIBLE);

                edt_Email.setVisibility(View.INVISIBLE);
                Email.setVisibility(View.VISIBLE);

                Spinner spinner_rel = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
                ArrayAdapter<String> adapter_rel = new ArrayAdapter<String>(ManageCareTeamActivity.this,
                        android.R.layout.simple_spinner_item, sp_Rel);
                adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_rel.setAdapter(adapter_rel);

                Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
                ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(ManageCareTeamActivity.this, android.R.layout.simple_spinner_item, sp_Admin);
                adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_admin.setAdapter(adapter_admin);

                edt_Relation.setVisibility(View.INVISIBLE);
                Relation.setVisibility(View.VISIBLE);

                edt_Admin.setVisibility(View.INVISIBLE);
                Admin.setVisibility(View.VISIBLE);

                System.out.println("CTp");
                System.out.println(patientList.get(CTp).patient_name);
                System.out.println(memberlist.get(CTp).first_name);
                System.out.println("MLp");
                System.out.println(patientList.get(MLp).patient_name);
                System.out.println(memberlist.get(MLp).first_name);
                System.out.println("Slut-popup");

            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.INVISIBLE);
                btn_del_memb.setVisibility(View.INVISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                prepareForEdit(CTp, MLp);
            }

            private void prepareForEdit(int CTp, int MLp) {

                edt_firstN.setText(Firstname.getText());
                edt_firstN.setVisibility(View.VISIBLE);
                Firstname.setVisibility(View.INVISIBLE);

                edt_lastN.setText(Lastname.getText());
                edt_lastN.setVisibility(View.VISIBLE);
                Lastname.setVisibility(View.INVISIBLE);

                edt_Phone.setText(Phone.getText());
                edt_Phone.setVisibility(View.VISIBLE);
                Phone.setVisibility(View.INVISIBLE);

                edt_Email.setText(Email.getText());
                edt_Email.setVisibility(View.VISIBLE);
                Email.setVisibility(View.INVISIBLE);

                Spinner spinner_rel = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
                ArrayAdapter<String> adapter_rel = new ArrayAdapter<String>(ManageCareTeamActivity.this,
                        android.R.layout.simple_spinner_item, sp_Rel);
                adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_rel.setAdapter(adapter_rel);

                Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
                ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(ManageCareTeamActivity.this, android.R.layout.simple_spinner_item, sp_Admin);
                adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_admin.setAdapter(adapter_admin);

                edt_Relation.setVisibility(View.VISIBLE);
                Relation.setVisibility(View.INVISIBLE);
                edt_Admin.setVisibility(View.VISIBLE);
                Admin.setVisibility(View.INVISIBLE);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_edit.getVisibility() == View.VISIBLE) {
                    popupWindow.dismiss();
                }
                btn_edit.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_del_memb.setVisibility(View.VISIBLE);
                CancelEdit(CTp, MLp);
            }

            private void CancelEdit(int CTp, int MLp) {
                edt_firstN.setVisibility(View.INVISIBLE);
                Firstname.setVisibility(View.VISIBLE);

                edt_lastN.setVisibility(View.INVISIBLE);
                Lastname.setVisibility(View.VISIBLE);

                edt_Phone.setVisibility(View.INVISIBLE);
                Phone.setVisibility(View.VISIBLE);

                edt_Email.setVisibility(View.INVISIBLE);
                Email.setVisibility(View.VISIBLE);

                Spinner spinner_rel = (Spinner) popupView.findViewById(R.id.spinner_relation_careteam);
                ArrayAdapter<String> adapter_rel = new ArrayAdapter<String>(ManageCareTeamActivity.this,
                        android.R.layout.simple_spinner_item, sp_Rel);
                adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_rel.setAdapter(adapter_rel);

                Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
                ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(ManageCareTeamActivity.this, android.R.layout.simple_spinner_item, sp_Admin);
                adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_admin.setAdapter(adapter_admin);

                edt_Relation.setVisibility(View.INVISIBLE);
                Relation.setVisibility(View.VISIBLE);

                edt_Admin.setVisibility(View.INVISIBLE);
                Admin.setVisibility(View.VISIBLE);
            }
        });

        btn_del_memb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_edit.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.INVISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_del_memb.setVisibility(View.VISIBLE);
                del_CTmemb(CTp, MLp);
            }
            private void del_CTmemb(int CTp, int MLp) {

                lcl.patient.get(CTp).care_team.remove(MLp);

                memberAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

    }

    private void journeyActivity(){
        Intent myIntent = new Intent(this, JourneyActivity.class);
        startActivity(myIntent);

    }

    private void journalActivity(){
        Intent myIntent = new Intent(this, journal.class);
        startActivity(myIntent);

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ManageCareTeam Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cancercarecompany.ccc.ccc/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ManageCareTeam Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cancercarecompany.ccc.ccc/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}