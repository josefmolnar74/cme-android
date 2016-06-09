package com.cancercarecompany.ccc.ccc;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

import com.google.gson.Gson;

import java.util.ArrayList;

public class CareTeamActivity extends AppCompatActivity {

    Boolean addMode = false;
    String lclString;
    Lcl_work_area lcl;
    int position;

    PopupWindow pw;
    ArrayList<Care_team> careTeamList = new ArrayList<>();
    ListView customListView;
    CustomAdapter listAdapter;
    Spinner dropdown;
    Spinner dropdown2;
    Button editButton;
    Button saveButton;
    Button cancelButton;
    Button addButton;
    TextView contactName;
    TextView contactLastName;
    TextView contactPhone;
    TextView contactEmail;
    TextView contactRelation;
    TextView contactAdmin;
    EditText nameEdit;
    EditText lastNameEdit;
    EditText phoneEdit;
    EditText emailEdit;
    TextView information;
    TextView contact1;
    TextView other1;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    RelativeLayout contactInformation;
    TextView loggedInText;
    ImageButton journalButton;

//    EditText firstName_popup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact);


        ImageButton infoButton = (ImageButton) findViewById(R.id.logoButton);
        editButton             = (Button) findViewById(R.id.btn_edit_careteam);
        saveButton             = (Button) findViewById(R.id.btn_save_careteam);
        cancelButton           = (Button) findViewById(R.id.btn_cancel_careteam);
        addButton              = (Button) findViewById(R.id.btn_add_CTmember);
        contactName            = (TextView) findViewById(R.id.lbl_firstName_careteam);
        contactLastName        = (TextView) findViewById(R.id.lbl_secondName_careteam);
        contactPhone           = (TextView) findViewById(R.id.lbl_phone_careteam);
        contactEmail           = (TextView) findViewById(R.id.lbl_email_careteam);
        contactRelation        = (TextView) findViewById(R.id.lbl_relation_careteam);
        contactAdmin           = (TextView) findViewById(R.id.lbl_admin_careteam);
        nameEdit               = (EditText) findViewById(R.id.txt_firstName_careteam);
        lastNameEdit           = (EditText) findViewById(R.id.txt_lastName_careteam);
        phoneEdit              = (EditText) findViewById(R.id.txt_phone_careteam);
        emailEdit              = (EditText) findViewById(R.id.txt_email_careteam);
        information            = (TextView) findViewById(R.id.lbl_contactInfo_careteam);
        addButton              = (Button) findViewById(R.id.btn_add_careteam);
        contactInformation     = (RelativeLayout) findViewById(R.id.contactInformation);
        contact1               = (TextView) findViewById(R.id.lbl_contact_careteam);
        other1                 = (TextView) findViewById(R.id.lbl_other_careteam);
        text1                  = (TextView) findViewById(R.id.lbl_phone_headline_careteam);
        text2                  = (TextView) findViewById(R.id.lbl_email_headline_careteam);
        text3                  = (TextView) findViewById(R.id.lbl_relation_headline_careteam);
        text4                  = (TextView) findViewById(R.id.lbl_admin_headline_careteam);
        loggedInText           = (TextView) findViewById(R.id.loggedIn);

        Typeface face1 = Typeface.createFromAsset(getAssets(), "OpenSans-Semibold.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "Arimo-Regular.ttf");
        Typeface face3 = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        information.setTypeface(face1);
        contactName.setTypeface(face3);
        contactLastName.setTypeface(face3);
        contact1.setTypeface(face1);
        other1.setTypeface(face1);
        contactPhone.setTypeface(face3);
        contactEmail.setTypeface(face3);
        contactAdmin.setTypeface(face3);
        contactRelation.setTypeface(face3);
        text1.setTypeface(face3);
        text2.setTypeface(face3);
        text3.setTypeface(face3);
        text4.setTypeface(face3);
        customListView = (ListView) findViewById(R.id.listView);

        lclString = (String) getIntent().getSerializableExtra("Person");
        position = (int) getIntent().getIntExtra("Position", 0);
        Gson gson = new Gson();

        lcl = gson.fromJson(lclString, Lcl_work_area.class);



        for (int i = 0; i < lcl.patient.get(position).care_team.size(); i++) {
            careTeamList.add(lcl.patient.get(position).care_team.get(i));
//  tst
//            System.out.println(lcl.patients.get(i).relationship.toString());
            System.out.println("Oncreate");
            System.out.println(i);

// tst
        }

        listAdapter = new CustomAdapter(this, careTeamList);
        customListView.setAdapter(listAdapter);

//        for (int i = 0; i < lcl.patients.get(position).care_team.size(); i++) {
//            if (lcl.patients.get(position).relationship == "patient") {
//                information.setText(careTeamList.get(position).first_name.toString());
//            }
//        }

        checkDetail(0);    // Always show contactinformation for first member in CareTeam(Patient)

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                  @Override
                                                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                      checkDetail(position);
                                                  }
                                              });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  saveContact();
            }
        });

        dropdown = (Spinner) findViewById(R.id.spinner_relation_careteam);
        String[] items = new String[]{"Pappa", "Patient", "Läkare", "Mamma"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown2 = (Spinner) findViewById(R.id.spinner_admin_careteam);
        String[] items2 = new String[]{"Yes", "No"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

    }

    private void checkDetail(int position) {
        System.out.println(position);

        System.out.println(careTeamList.get(position).relationship.toString());
        addMode = false;
        contactName.setText(careTeamList.get(position).first_name.toString());
        contactLastName.setText(careTeamList.get(position).last_name.toString());
        contactEmail.setText(careTeamList.get(position).email.toString());
        contactRelation.setText(careTeamList.get(position).relationship.toString());
//        firstName_popup.setText(careTeamList.get(position).first_name);
        System.out.println("nN");
    }

    private void editContact() {
        addMode = false;
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        lastNameEdit.setVisibility(View.VISIBLE);
        contactPhone.setVisibility(View.INVISIBLE);
        phoneEdit.setVisibility(View.VISIBLE);
        contactEmail.setVisibility(View.INVISIBLE);
        emailEdit.setVisibility(View.VISIBLE);
        contactName.setVisibility(View.INVISIBLE);
        contactLastName.setVisibility(View.INVISIBLE);
        nameEdit.setVisibility(View.VISIBLE);
        dropdown.setVisibility(View.VISIBLE);
        dropdown2.setVisibility(View.VISIBLE);
        contactRelation.setVisibility(View.INVISIBLE);
        contactAdmin.setVisibility(View.INVISIBLE);
    }

/*
    public void saveContact(){
        information.setText("Contact Information");
        contactInformation.setBackgroundColor(getResources().getColor(R.color.white));

        if (addMode == false) {
            cancelButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);


            contactsArrayList.get(position).name = nameEdit.getText().toString();
            contactsArrayList.get(position).phone = phoneEdit.getText().toString();
            contactsArrayList.get(position).email = emailEdit.getText().toString();
            contactsArrayList.get(position).relation = dropdown.getSelectedItem().toString();

            if (dropdown2.getSelectedItem().toString() == "Yes") {
                contactsArrayList.get(position).admin = true;
            } else {
                contactsArrayList.get(position).admin = false;
            }

            contactName.setText(contactsArrayList.get(position).name.toString());
            contactPhone.setText(contactsArrayList.get(position).phone.toString());
            contactRelation.setText(contactsArrayList.get(position).relation.toString());
            contactEmail.setText(contactsArrayList.get(position).email.toString());
            contactAdmin.setText(contactsArrayList.get(position).admin.toString());


            contactPhone.setVisibility(View.VISIBLE);
            phoneEdit.setVisibility(View.INVISIBLE);
            contactEmail.setVisibility(View.VISIBLE);
            emailEdit.setVisibility(View.INVISIBLE);
            contactName.setVisibility(View.VISIBLE);
            nameEdit.setVisibility(View.INVISIBLE);
            dropdown.setVisibility(View.INVISIBLE);
            dropdown2.setVisibility(View.INVISIBLE);
            contactRelation.setVisibility(View.VISIBLE);
            contactAdmin.setVisibility(View.VISIBLE);

            customListView.setAdapter(listAdapter);
        }else{
            Boolean admin;
            if (dropdown2.getSelectedItem() == "Yes"){
                admin = true;
            } else{
                admin = false;
            }
            Contacts newContact = new Contacts(nameEdit.getText().toString(), phoneEdit.getText().toString(), emailEdit.getText().toString(), dropdown.getSelectedItem().toString(), admin);
            contactsArrayList.add(newContact);
            customListView.setAdapter(listAdapter);
            checkDetail(0);
        }
    }
    */



}

    /*
        checkDetail(0);

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiatePopupWindow();
            }
        });



        });



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

    }

/


    private void addContact(){
        contactInformation.setBackgroundColor(getResources().getColor(R.color.addcontact));
        addMode = true;
        nameEdit.setText("");
        phoneEdit.setText("");
        emailEdit.setText("");

        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);

        contactName.setVisibility(View.INVISIBLE);
        contactPhone.setVisibility(View.INVISIBLE);
        contactAdmin.setVisibility(View.INVISIBLE);
        contactRelation.setVisibility(View.INVISIBLE);
        contactEmail.setVisibility(View.INVISIBLE);

        nameEdit.setVisibility(View.VISIBLE);
        phoneEdit.setVisibility(View.VISIBLE);
        emailEdit.setVisibility(View.VISIBLE);
        dropdown.setVisibility(View.VISIBLE);
        dropdown2.setVisibility(View.VISIBLE);
        information.setText("New Contact");

    }


        private void cancel(){
            information.setText("Contact Information");
            contactInformation.setBackgroundColor(getResources().getColor(R.color.white));

            String colorSelecter = contactsArrayList.get(position).relation.toString();
            switch (colorSelecter){
                case "Family":
                    contactInformation.setBackgroundColor(getResources().getColor(R.color.Family));
                    break;
                case "Health Care":
                    contactInformation.setBackgroundColor(getResources().getColor(R.color.healthCare));
                    break;
                case "Friend":
                    contactInformation.setBackgroundColor(getResources().getColor(R.color.Friend));
            }



                addMode = false;
                    dropdown.setVisibility(View.INVISIBLE);
                    dropdown2.setVisibility(View.INVISIBLE);
                    phoneEdit.setVisibility(View.INVISIBLE);
                    emailEdit.setVisibility(View.INVISIBLE);
                    nameEdit.setVisibility(View.INVISIBLE);

                    contactName.setVisibility(View.VISIBLE);
                    contactPhone.setVisibility(View.VISIBLE);
                    contactEmail.setVisibility(View.VISIBLE);
                    contactRelation.setVisibility(View.VISIBLE);
                    contactAdmin.setVisibility(View.VISIBLE);

                cancelButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);

            phoneEdit.setText("");
            emailEdit.setText("");
            nameEdit.setText("");
    }

    private void checkInputInformation(){





    }




        contactName.setText(contactsArrayList.get(position).name.toString());
        contactPhone.setText(contactsArrayList.get(position).phone.toString());
        contactRelation.setText(contactsArrayList.get(position).relation.toString());
        contactEmail.setText(contactsArrayList.get(position).email.toString());

        if (contactsArrayList.get(position).admin == true){
            contactAdmin.setText("Yes");
        }  else {
            contactAdmin.setText("No");
        }

    }

    private void initiatePopupWindow() {
        try {
            LayoutInflater inflater = (LayoutInflater) CareTeamActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.infopopup,
                    (ViewGroup) findViewById(R.id.popup_info));
            pw = new PopupWindow(layout, 600, 360, true);
            pw.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 450);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
*/