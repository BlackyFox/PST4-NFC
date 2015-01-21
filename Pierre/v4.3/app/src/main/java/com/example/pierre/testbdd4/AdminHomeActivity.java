package com.example.pierre.testbdd4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pierre.testbdd4.BDDFolder.MyBDD;
import com.example.pierre.testbdd4.Objects.*;

import java.util.Calendar;
import java.util.TimeZone;


public class AdminHomeActivity extends Activity {

    public Button button_menu = null;

    public LinearLayout linearLayout_people = null;
    public LinearLayout linearLayout_company = null;
    public LinearLayout linearLayout_reduction = null;
    public LinearLayout linearLayout_client = null;
    public LinearLayout linearLayout_offer = null;
    public LinearLayout linearLayout_show = null;
    public LinearLayout linearLayout_research = null;
    public LinearLayout linearLayout_reset = null;

    public EditText addPeople_editText_username = null;
    public EditText addPeople_editText_password = null;
    public EditText addPeople_editText_confirmPassword = null;
    public EditText addPeople_editText_name = null;
    public EditText addPeople_editText_first_name = null;
    public RadioGroup addPeople_radioGroup_groupSex = null;
    public RadioButton addPeople_radioButton_man = null;
    public RadioButton addPeople_radioButton_woman = null;
    public EditText addPeople_editText_birthDay = null;
    public EditText addPeople_editText_birthMonth = null;
    public EditText addPeople_editText_birthYear = null;
    public RadioGroup addPeople_radioGroup_groupRole = null;
    public RadioButton addPeople_radioButton_admin = null;
    public RadioButton addPeople_radioButton_user = null;
    public TextView addPeople_textView_errorText = null;

    public EditText addCompany_editText_name = null;
    public TextView addCompany_textView_errorText = null;

    public EditText addReduction_editText_name = null;
    public EditText addReduction_editText_text = null;
    public RadioGroup addReduction_radioGroup_groupSex = null;
    public RadioButton addReduction_radioButton_man = null;
    public RadioButton addReduction_radioButton_woman = null;
    public RadioButton addReduction_radioButton_all = null;
    public RadioGroup addReduction_radioGroup_groupAge = null;
    public RadioButton addReduction_radioButton_age_sup = null;
    public RadioButton addReduction_radioButton_age_inf = null;
    public RadioButton addReduction_radioButton_age_equ = null;
    public RadioButton addReduction_radioButton_age_all = null;
    public EditText addReduction_editText_age_value = null;
    public RadioGroup addReduction_radioGroup_groupNbPoints = null;
    public RadioButton addReduction_radioButton_nbPoints_sup = null;
    public RadioButton addReduction_radioButton_nbPoints_inf = null;
    public RadioButton addReduction_radioButton_nbPoints_equ = null;
    public RadioButton addReduction_radioButton_nbPoints_all = null;
    public EditText addReduction_editText_nbPoints_value = null;
    public TextView addReduction_textView_errorText = null;

    public EditText addClient_editText_idPeop = null;
    public EditText addClient_editText_idComp = null;
    public TextView addClient_textView_errorText = null;

    public EditText addOffer_editText_idComp = null;
    public EditText addOffer_editText_idRedu = null;
    public TextView addOffer_textView_errorText = null;

    public RadioGroup show_radioGroup_groupShow = null;
    public RadioButton show_radioButton_people = null;
    public RadioButton show_radioButton_company = null;
    public RadioButton show_radioButton_reduction = null;
    public RadioButton show_radioButton_client = null;
    public RadioButton show_radioButton_offer = null;
    public RadioButton show_radioButton_opportunity = null;
    public TextView show_textView_result = null;

    public RadioGroup research_radioGroup = null;
    public RadioButton research_radioButton_people = null;
    public RadioButton research_radioButton_company = null;
    public LinearLayout research_linearLayout_people = null;
    public LinearLayout research_linearLayout_company = null;
    public EditText research_editText_people = null;
    public EditText research_editText_company = null;
    public TextView research_textView_people_result = null;
    public TextView research_textView_company_result = null;


    public MyBDD bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        bdd = new MyBDD(this);
        bdd.open();


        button_menu = (Button) findViewById(R.id.admin_home_button_menu);


        linearLayout_people = (LinearLayout) findViewById(R.id.admin_home_linearLayout_people);
        linearLayout_company = (LinearLayout) findViewById(R.id.admin_home_linearLayout_company);
        linearLayout_reduction = (LinearLayout) findViewById(R.id.admin_home_linearLayout_reduction);
        linearLayout_client = (LinearLayout) findViewById(R.id.admin_home_linearLayout_client);
        linearLayout_offer = (LinearLayout) findViewById(R.id.admin_home_linearLayout_offer);
        linearLayout_show = (LinearLayout) findViewById(R.id.admin_home_linearLayout_show);
        linearLayout_research = (LinearLayout) findViewById(R.id.admin_home_research_linearLayout);
        linearLayout_reset = (LinearLayout) findViewById(R.id.admin_home_linearLayout_reset);


        addPeople_editText_username = (EditText) findViewById(R.id.admin_home_addPeople_editText_username);
        addPeople_editText_password = (EditText) findViewById(R.id.admin_home_addPeople_editText_password);
        addPeople_editText_confirmPassword = (EditText) findViewById(R.id.admin_home_addPeople_editText_confirmPassword);
        addPeople_editText_name = (EditText) findViewById(R.id.admin_home_addPeople_editText_name);
        addPeople_editText_first_name = (EditText) findViewById(R.id.admin_home_addPeople_editText_first_name);
        addPeople_radioGroup_groupSex = (RadioGroup) findViewById(R.id.admin_home_addPeople_radioGroup_groupSex);
        addPeople_radioButton_man = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_man);
        addPeople_radioButton_woman = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_woman);
        addPeople_editText_birthDay = (EditText) findViewById(R.id.admin_home_addPeople_editText_birthDay);
        addPeople_editText_birthMonth = (EditText) findViewById(R.id.admin_home_addPeople_editText_birthMonth);
        addPeople_editText_birthYear = (EditText) findViewById(R.id.admin_home_addPeople_editText_birthYear);
        addPeople_radioGroup_groupRole = (RadioGroup) findViewById(R.id.admin_home_addPeople_radioGroup_groupRole);
        addPeople_radioButton_admin = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_admin);
        addPeople_radioButton_user = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_user);
        addPeople_textView_errorText = (TextView) findViewById(R.id.admin_home_addPeople_textView_errorText);

        addPeople_radioGroup_groupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_home_addPeople_radioButton_man) {
                    addPeople_radioButton_woman.setChecked(false);
                } else if (checkedId == R.id.admin_home_addPeople_radioButton_woman) {
                    addPeople_radioButton_man.setChecked(false);
                }
            }
        });

        addPeople_radioGroup_groupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_home_addPeople_radioButton_user) {
                    addPeople_radioButton_admin.setChecked(false);
                } else if (checkedId == R.id.admin_home_addPeople_radioButton_admin) {
                    addPeople_radioButton_user.setChecked(false);
                }
            }
        });


        addCompany_editText_name = (EditText) findViewById(R.id.admin_home_addCompany_editText_name);
        addCompany_textView_errorText = (TextView) findViewById(R.id.admin_home_addCompany_textView_errorText);


        addReduction_editText_name = (EditText) findViewById(R.id.admin_home_addReduction_editText_name);
        addReduction_editText_text = (EditText) findViewById(R.id.admin_home_addReduction_editText_text);
        addReduction_radioGroup_groupSex = (RadioGroup) findViewById(R.id.admin_home_addReduction_radioGroup_groupSex);
        addReduction_radioButton_man = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_man);
        addReduction_radioButton_woman  = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_woman);
        addReduction_radioButton_all  = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_all);
        addReduction_radioGroup_groupAge = (RadioGroup) findViewById(R.id.admin_home_addReduction_radioGroup_groupAge);
        addReduction_radioButton_age_sup = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_age_sup);
        addReduction_radioButton_age_inf = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_age_inf);
        addReduction_radioButton_age_equ = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_age_equ);
        addReduction_radioButton_age_all = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_age_all);
        addReduction_editText_age_value = (EditText) findViewById(R.id.admin_home_addReduction_editText_age_value);
        addReduction_radioGroup_groupNbPoints = (RadioGroup) findViewById(R.id.admin_home_addReduction_radioGroup_groupNbPoints);
        addReduction_radioButton_nbPoints_sup  = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_nbPoints_sup);
        addReduction_radioButton_nbPoints_inf = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_nbPoints_inf);
        addReduction_radioButton_nbPoints_equ = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_nbPoints_equ);
        addReduction_radioButton_nbPoints_all = (RadioButton) findViewById(R.id.admin_home_addReduction_radioButton_nbPoints_all);
        addReduction_editText_nbPoints_value = (EditText) findViewById(R.id.admin_home_addReduction_editText_nbPoints_value);
        addReduction_textView_errorText = (TextView) findViewById(R.id.admin_home_addReduction_textView_errorText);

        addReduction_radioGroup_groupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_home_addReduction_radioButton_man) {
                    addReduction_radioButton_woman.setChecked(false);
                    addReduction_radioButton_all.setChecked(false);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_woman) {
                    addReduction_radioButton_man.setChecked(false);
                    addReduction_radioButton_all.setChecked(false);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_all) {
                    addReduction_radioButton_man.setChecked(false);
                    addReduction_radioButton_woman.setChecked(false);
                }
            }
        });

        addReduction_radioGroup_groupAge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_home_addReduction_radioButton_age_sup) {
                    addReduction_radioButton_age_inf.setChecked(false);
                    addReduction_radioButton_age_equ.setChecked(false);
                    addReduction_radioButton_age_all.setChecked(false);
                    addReduction_editText_age_value.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_age_inf) {
                    addReduction_radioButton_age_sup.setChecked(false);
                    addReduction_radioButton_age_equ.setChecked(false);
                    addReduction_radioButton_age_all.setChecked(false);
                    addReduction_editText_age_value.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_age_equ) {
                    addReduction_radioButton_age_sup.setChecked(false);
                    addReduction_radioButton_age_inf.setChecked(false);
                    addReduction_radioButton_age_all.setChecked(false);
                    addReduction_editText_age_value.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_age_all) {
                    addReduction_radioButton_age_sup.setChecked(false);
                    addReduction_radioButton_age_inf.setChecked(false);
                    addReduction_radioButton_age_equ.setChecked(false);
                    addReduction_editText_age_value.setVisibility(View.INVISIBLE);
                }
            }
        });

        addReduction_radioGroup_groupNbPoints.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_home_addReduction_radioButton_nbPoints_sup) {
                    addReduction_radioButton_nbPoints_inf.setChecked(false);
                    addReduction_radioButton_nbPoints_equ.setChecked(false);
                    addReduction_radioButton_nbPoints_all.setChecked(false);
                    addReduction_editText_nbPoints_value.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_nbPoints_inf) {
                    addReduction_radioButton_nbPoints_sup.setChecked(false);
                    addReduction_radioButton_nbPoints_equ.setChecked(false);
                    addReduction_radioButton_nbPoints_all.setChecked(false);
                    addReduction_editText_nbPoints_value.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_nbPoints_equ) {
                    addReduction_radioButton_nbPoints_sup.setChecked(false);
                    addReduction_radioButton_nbPoints_inf.setChecked(false);
                    addReduction_radioButton_nbPoints_all.setChecked(false);
                    addReduction_editText_nbPoints_value.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.admin_home_addReduction_radioButton_nbPoints_all) {
                    addReduction_radioButton_nbPoints_sup.setChecked(false);
                    addReduction_radioButton_nbPoints_inf.setChecked(false);
                    addReduction_radioButton_nbPoints_equ.setChecked(false);
                    addReduction_editText_nbPoints_value.setVisibility(View.INVISIBLE);
                }
            }
        });


        addClient_editText_idPeop = (EditText) findViewById(R.id.admin_home_addClient_editText_idPeop);
        addClient_editText_idComp = (EditText) findViewById(R.id.admin_home_addClient_editText_idComp);
        addClient_textView_errorText = (TextView) findViewById(R.id.admin_home_addClient_textView_errorText);


        addOffer_editText_idComp = (EditText) findViewById(R.id.admin_home_addOffer_editText_idComp);
        addOffer_editText_idRedu = (EditText) findViewById(R.id.admin_home_addOffer_editText_idRedu);
        addOffer_textView_errorText = (TextView) findViewById(R.id.admin_home_addOffer_textView_errorText);


        show_radioGroup_groupShow = (RadioGroup) findViewById(R.id.admin_home_show_radioGroup_groupShow);
        show_radioButton_people = (RadioButton) findViewById(R.id.admin_home_show_radioButton_people);
        show_radioButton_company = (RadioButton) findViewById(R.id.admin_home_show_radioButton_company);
        show_radioButton_reduction = (RadioButton) findViewById(R.id.admin_home_show_radioButton_reduction);
        show_radioButton_client = (RadioButton) findViewById(R.id.admin_home_show_radioButton_client);
        show_radioButton_offer = (RadioButton) findViewById(R.id.admin_home_show_radioButton_offer);
        show_radioButton_opportunity = (RadioButton) findViewById(R.id.admin_home_show_radioButton_opportunity);
        show_textView_result = (TextView) findViewById(R.id.admin_home_show_textView_result);

        show_radioGroup_groupShow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.admin_home_show_radioButton_people) {
                    show_radioButton_company.setChecked(false);
                    show_radioButton_reduction.setChecked(false);
                    show_radioButton_client.setChecked(false);
                    show_radioButton_offer.setChecked(false);
                    show_radioButton_opportunity.setChecked(false);
                    String people = bdd.showPeople();
                    show_textView_result.setText(people);
                } else if (checkedId == R.id.admin_home_show_radioButton_company) {
                    show_radioButton_people.setChecked(false);
                    show_radioButton_reduction.setChecked(false);
                    show_radioButton_client.setChecked(false);
                    show_radioButton_offer.setChecked(false);
                    show_radioButton_opportunity.setChecked(false);
                    String company = bdd.showCompany();
                    show_textView_result.setText(company);
                } else if (checkedId == R.id.admin_home_show_radioButton_reduction) {
                    show_radioButton_people.setChecked(false);
                    show_radioButton_company.setChecked(false);
                    show_radioButton_client.setChecked(false);
                    show_radioButton_offer.setChecked(false);
                    show_radioButton_opportunity.setChecked(false);
                    String reduction = bdd.showReduction();
                    show_textView_result.setText(reduction);
                } else if (checkedId == R.id.admin_home_show_radioButton_client) {
                    show_radioButton_people.setChecked(false);
                    show_radioButton_company.setChecked(false);
                    show_radioButton_reduction.setChecked(false);
                    show_radioButton_offer.setChecked(false);
                    show_radioButton_opportunity.setChecked(false);
                    String client = bdd.showClient();
                    show_textView_result.setText(client);
                } else if (checkedId == R.id.admin_home_show_radioButton_offer) {
                    show_radioButton_people.setChecked(false);
                    show_radioButton_company.setChecked(false);
                    show_radioButton_reduction.setChecked(false);
                    show_radioButton_client.setChecked(false);
                    show_radioButton_opportunity.setChecked(false);
                    String offer = bdd.showOffer();
                    show_textView_result.setText(offer);
                } else if (checkedId == R.id.admin_home_show_radioButton_opportunity) {
                    show_radioButton_people.setChecked(false);
                    show_radioButton_company.setChecked(false);
                    show_radioButton_reduction.setChecked(false);
                    show_radioButton_client.setChecked(false);
                    show_radioButton_offer.setChecked(false);
                    String opportunity = bdd.showOpportunity();
                    show_textView_result.setText(opportunity);
                }
            }
        });


        research_radioGroup = (RadioGroup) findViewById(R.id.admin_home_research_radioGroup_groupResearch);
        research_radioButton_people = (RadioButton) findViewById(R.id.admin_home_research_radioButton_people);
        research_radioButton_company = (RadioButton) findViewById(R.id.admin_home_research_radioButton_company);
        research_linearLayout_people = (LinearLayout) findViewById(R.id.admin_home_research_linearLayout_people);
        research_linearLayout_company = (LinearLayout) findViewById(R.id.admin_home_research_linearLayout_company);
        research_editText_people = (EditText) findViewById(R.id.admin_home_research_editText_people);
        research_editText_company = (EditText) findViewById(R.id.admin_home_research_editText_name);
        research_textView_people_result = (TextView) findViewById(R.id.admin_home_research_textView_people_result);
        research_textView_company_result = (TextView) findViewById(R.id.admin_home_research_textView_company_result);

        research_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_home_research_radioButton_people) {
                    research_radioButton_company.setChecked(false);
                    research_linearLayout_people.setVisibility(View.VISIBLE);
                    research_linearLayout_company.setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.admin_home_research_radioButton_company) {
                    research_radioButton_people.setChecked(false);
                    research_linearLayout_people.setVisibility(View.INVISIBLE);
                    research_linearLayout_company.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void toDo(View v) {
        String addPeople_username = addPeople_editText_username.getText().toString();
        String addPeople_password = addPeople_editText_password.getText().toString();
        String addPeople_confirmPassword = addPeople_editText_confirmPassword.getText().toString();
        String addPeople_name = addPeople_editText_name.getText().toString();
        String addPeople_first_name = addPeople_editText_first_name.getText().toString();
        Boolean addPeople_man = addPeople_radioButton_man.isChecked();
        Boolean addPeople_woman = addPeople_radioButton_woman.isChecked();
        String addPeople_sexe;
        String addPeople_day = addPeople_editText_birthDay.getText().toString();
        String addPeople_month = addPeople_editText_birthMonth.getText().toString();
        String addPeople_year = addPeople_editText_birthYear.getText().toString();
        Boolean addPeople_admin = addPeople_radioButton_admin.isChecked();
        Boolean addPeople_user = addPeople_radioButton_user.isChecked();
        String addPeople_role;
        Calendar addPeople_cal;

        String addCompany_name = addCompany_editText_name.getText().toString();

        String addReduction_name = addReduction_editText_name.getText().toString();
        String addReduction_text = addReduction_editText_text.getText().toString();
        Boolean addReduction_man = addReduction_radioButton_man.isChecked();
        Boolean addReduction_woman = addReduction_radioButton_woman.isChecked();
        Boolean addReduction_all = addReduction_radioButton_all.isChecked();
        String addReduction_sexe;
        Boolean addReduction_age_sup = addReduction_radioButton_age_sup.isChecked();
        Boolean addReduction_age_inf = addReduction_radioButton_age_inf.isChecked();
        Boolean addReduction_age_equ = addReduction_radioButton_age_equ.isChecked();
        Boolean addReduction_age_all = addReduction_radioButton_age_all.isChecked();
        String addReduction_age_relation;
        String addReduction_age_value = addReduction_editText_age_value.getText().toString();
        Boolean addReduction_nbPoints_sup = addReduction_radioButton_nbPoints_sup.isChecked();
        Boolean addReduction_nbPoints_inf = addReduction_radioButton_nbPoints_inf.isChecked();
        Boolean addReduction_nbPoints_equ = addReduction_radioButton_nbPoints_equ.isChecked();
        Boolean addReduction_nbPoints_all = addReduction_radioButton_nbPoints_all.isChecked();
        String addReduction_nbPoints_relation;
        String addReduction_nbPoints_value = addReduction_editText_nbPoints_value.getText().toString();

        String addClient_idPeop = addClient_editText_idPeop.getText().toString();
        String addClient_idComp = addClient_editText_idComp.getText().toString();

        String addOffer_idComp = addOffer_editText_idComp.getText().toString();
        String addOffer_idRedu = addOffer_editText_idRedu.getText().toString();

        String research_people = research_editText_people.getText().toString();
        String research_company = research_editText_company.getText().toString();


        switch(v.getId()) {
            case R.id.admin_home_addPeople_button_clear:
            {
                addPeople_editText_username.setText("");
                addPeople_editText_password.setText("");
                addPeople_editText_confirmPassword.setText("");
                addPeople_editText_name.setText("");
                addPeople_editText_first_name.setText("");
                addPeople_radioButton_man.setChecked(false);
                addPeople_radioButton_woman.setChecked(false);
                addPeople_editText_birthDay.setText("");
                addPeople_editText_birthMonth.setText("");
                addPeople_editText_birthYear.setText("");
                addPeople_radioButton_admin.setChecked(false);
                addPeople_radioButton_user.setChecked(false);

                break;
            }
            case R.id.admin_home_addPeople_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(addPeople_username == null || addPeople_username.equals("")) { addPeople_textView_errorText.setText("Please enter an username."); break; }
                if(bdd.doesPeopleAlreadyExists(addPeople_username)) { addPeople_textView_errorText.setText("This username is already used."); break; }

                if(addPeople_password == null || addPeople_password.equals("")) { addPeople_textView_errorText.setText("Please enter a password."); break; }
                if(addPeople_confirmPassword == null || addPeople_confirmPassword.equals("")) { addPeople_textView_errorText.setText("Please confirm the password."); break; }
                if(!addPeople_password.equals(addPeople_confirmPassword)) { addPeople_textView_errorText.setText("Both password and confirmation are not identical."); break; }

                if(addPeople_name == null || addPeople_name.equals("")) { addPeople_textView_errorText.setText("Please enter your name."); break; }
                addPeople_name = addPeople_name.toUpperCase();

                if(addPeople_first_name == null || addPeople_first_name.equals("")) { addPeople_textView_errorText.setText("Please enter your first-name."); break; }
                addPeople_first_name = Character.toUpperCase(addPeople_first_name.charAt(0)) + addPeople_first_name.substring(1).toLowerCase();

                if(!(addPeople_man ^ addPeople_woman)) { addPeople_textView_errorText.setText("Man or Woman ? That is the question."); break; }
                if(addPeople_man) { addPeople_sexe = "M"; } else { addPeople_sexe = "W"; }

                if(addPeople_day == null || addPeople_day.equals("") || addPeople_month == null || addPeople_month.equals("") || addPeople_year == null || addPeople_year.equals("")) { addPeople_textView_errorText.setText("Please enter your birth date."); break; }
                if(Integer.parseInt(addPeople_year) < 0 || Integer.parseInt(addPeople_month) < 1 || Integer.parseInt(addPeople_month) > 12 || Integer.parseInt(addPeople_day) < 1 || Integer.parseInt(addPeople_day) > 31
                        || (Integer.parseInt(addPeople_day) == 31 && (Integer.parseInt(addPeople_month) == 2 || Integer.parseInt(addPeople_month) == 4 || Integer.parseInt(addPeople_month) == 6 || Integer.parseInt(addPeople_month) == 9 || Integer.parseInt(addPeople_month) == 11))
                        || (Integer.parseInt(addPeople_day) == 30 && Integer.parseInt(addPeople_month) == 2)
                        || (Integer.parseInt(addPeople_day) == 29 && !isBissextil(Integer.parseInt(addPeople_year))))
                { addPeople_textView_errorText.setText("Please enter a valid date."); break; }
                addPeople_cal = Calendar.getInstance(TimeZone.getDefault());
                if((Integer.parseInt(addPeople_year) > addPeople_cal.get(Calendar.YEAR))
                        || (Integer.parseInt(addPeople_year) == addPeople_cal.get(Calendar.YEAR) && Integer.parseInt(addPeople_month) > addPeople_cal.get(Calendar.MONTH)+1)
                        || (Integer.parseInt(addPeople_year) == addPeople_cal.get(Calendar.YEAR) && Integer.parseInt(addPeople_month) == addPeople_cal.get(Calendar.MONTH)+1 && Integer.parseInt(addPeople_day) > addPeople_cal.get(Calendar.DATE)))
                { addPeople_textView_errorText.setText("Please enter a past date."); break; }

                if(!(addPeople_user ^ addPeople_admin)) { addPeople_textView_errorText.setText("Chose your role."); break; }
                if(addPeople_user) { addPeople_role = "U"; } else { addPeople_role = "A"; }


                People peop = new People(addPeople_username, addPeople_password, addPeople_name, addPeople_first_name, addPeople_sexe, addPeople_day + "-" + addPeople_month + "-" + addPeople_year, addPeople_role);
                bdd.insertPeople(peop);

                String conclusion = "Success !\n"
                        + "\tUsername : " + peop.getUsername() + "\n"
                        + "\tPassword : " + peop.getPassword() + "\n"
                        + "\tName : " + peop.getName() + "\n"
                        + "\tFirst-name : " + peop.getFirstName() + "\n"
                        + "\tSexe : " + peop.getSexe() + "\n"
                        + "\tDate of birth : " + peop.getDateOfBirth() + "\n"
                        + "\tRole : " + peop.getRole();


                bdd.close();

                new AlertDialog.Builder(this)
                        .setMessage(conclusion)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                linearLayout_people.setVisibility(View.INVISIBLE);
                            }
                        })
                        .show();

                break;
            }
            case R.id.admin_home_addCompany_button_clear:
            {
                addCompany_editText_name.setText("");

                break;
            }
            case R.id.admin_home_addCompany_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(addCompany_name == null || addCompany_name.equals("")) { addCompany_textView_errorText.setText("Please enter the name of your company."); break; }
                if(bdd.doesCompanyAlreadyExists(addCompany_name)) { addCompany_textView_errorText.setText("This company already exists."); break; }

                Company comp = new Company(addCompany_name);
                bdd.insertCompany(comp);

                String conclusion = "Success !\n"
                        + "\tName : " + comp.getName();

                bdd.close();

                new AlertDialog.Builder(this)
                        .setMessage(conclusion)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                linearLayout_company.setVisibility(View.INVISIBLE);
                            }
                        })
                        .show();

                break;
            }
            case R.id.admin_home_addReduction_button_clear:
            {
                addReduction_editText_name.setText("");
                addReduction_editText_text.setText("");
                addReduction_radioButton_man.setChecked(false);
                addReduction_radioButton_woman.setChecked(false);
                addReduction_radioButton_all.setChecked(false);
                addReduction_radioButton_age_sup.setChecked(false);
                addReduction_radioButton_age_inf.setChecked(false);
                addReduction_radioButton_age_equ.setChecked(false);
                addReduction_radioButton_age_all.setChecked(false);
                addReduction_editText_age_value.setText("");
                addReduction_editText_age_value.setVisibility(View.VISIBLE);
                addReduction_radioButton_nbPoints_sup.setChecked(false);
                addReduction_radioButton_nbPoints_inf.setChecked(false);
                addReduction_radioButton_nbPoints_equ.setChecked(false);
                addReduction_radioButton_nbPoints_all.setChecked(false);
                addReduction_editText_nbPoints_value.setText("");
                addReduction_editText_nbPoints_value.setVisibility(View.VISIBLE);

                break;
            }
            case R.id.admin_home_addReduction_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(addReduction_name == null || addReduction_name.equals("")) { addReduction_textView_errorText.setText("Please enter the name of your reduction."); break; }
                if(bdd.doesReductionAlreadyExists(addReduction_name)) { addReduction_textView_errorText.setText("This reduction name is already used."); break; }

                if(addReduction_text == null || addReduction_name.equals("")) { addReduction_textView_errorText.setText("Please enter the description of your reduction."); break; }

                if(!(addReduction_man ^ addReduction_woman ^ addReduction_all)) { addReduction_textView_errorText.setText("Please enter a sexe condition."); break; }
                if(addReduction_man) { addReduction_sexe = "M"; }
                else if(addReduction_woman) { addReduction_sexe = "W"; }
                else { addReduction_sexe = "A"; }

                if(!(addReduction_age_sup  ^ addReduction_age_inf ^ addReduction_age_equ ^ addReduction_age_all)) { addReduction_textView_errorText.setText("Please enter an age relation condition."); break; }
                if(addReduction_age_all) { addReduction_age_relation = "A"; addReduction_age_value = "0"; }
                else {
                    if(addReduction_age_value == null || addReduction_age_value.equals("")) { addReduction_textView_errorText.setText("Please enter an age value to your condition."); break; }
                    if(addReduction_age_sup) { addReduction_age_relation = ">"; }
                    else if(addReduction_age_inf) { addReduction_age_relation = "<"; }
                    else { addReduction_age_relation = "="; }
                }

                if(!(addReduction_nbPoints_sup ^ addReduction_nbPoints_inf ^ addReduction_nbPoints_equ ^ addReduction_nbPoints_all)) { addReduction_textView_errorText.setText("Please enter a nbPoints relation condition."); break; }
                if(addReduction_nbPoints_all) { addReduction_nbPoints_relation = "A"; addReduction_nbPoints_value = "0"; }
                else {
                    if(addReduction_nbPoints_value == null || addReduction_nbPoints_value.equals("")) { addReduction_textView_errorText.setText("Please enter a nbPoints value to your condition."); break; }
                    if(addReduction_nbPoints_sup) { addReduction_nbPoints_relation = ">"; }
                    else if(addReduction_nbPoints_inf) { addReduction_nbPoints_relation = "<"; }
                    else { addReduction_nbPoints_relation = "="; }
                }

                Reduction reduction = new Reduction(addReduction_name, addReduction_text, addReduction_sexe, addReduction_age_relation, Integer.parseInt(addReduction_age_value), addReduction_nbPoints_relation, Integer.parseInt(addReduction_nbPoints_value));
                bdd.insertReduction(reduction);

                String conclusion = "Success !\n"
                        + "\tName : " + reduction.getName() + "\n"
                        + "\tText : " + reduction.getText() + "\n"
                        + "\tSexe : " + reduction.getSexe() + "\n"
                        + "\tAge : " + reduction.getAge_relation() + " " + reduction.getAge_value() + "\n"
                        + "\tNbPoints : " + reduction.getNb_points_relation() + reduction.getNb_points_value();

                bdd.close();

                new AlertDialog.Builder(this)
                        .setMessage(conclusion)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                linearLayout_reduction.setVisibility(View.INVISIBLE);
                            }
                        })
                        .show();

                break;
            }
            case R.id.admin_home_addClient_button_clear:
            {
                addClient_editText_idPeop.setText("");
                addClient_editText_idComp.setText("");

                break;
            }
            case R.id.admin_home_addClient_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(addClient_idPeop == null || addClient_idPeop.equals("")) { addClient_textView_errorText.setText("Please enter the ID of the people."); break; }
                if(bdd.getPeopleWithId(Integer.parseInt(addClient_idPeop)) == null) { addClient_textView_errorText.setText("This people doesn't exists."); break; }

                if(addClient_idComp == null || addClient_idComp.equals("")) { addClient_textView_errorText.setText("Please enter the ID of the company."); break; }
                if(bdd.getCompanyWithId(Integer.parseInt(addClient_idComp)) == null) { addClient_textView_errorText.setText("This company doesn't exists."); break; }

                Client client = new Client(Integer.parseInt(addClient_idPeop), Integer.parseInt(addClient_idComp), 0);
                bdd.insertClient(client);

                String conclusion = "Success !\n"
                        + "\tIDPeople : " + client.getIdPeop() + "\n"
                        + "\tIDCompany : " + client.getIdComp() + "\n"
                        + "\tPoints : " + client.getPoints();

                bdd.close();

                new AlertDialog.Builder(this)
                        .setMessage(conclusion)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                linearLayout_client.setVisibility(View.INVISIBLE);
                            }
                        })
                        .show();

                break;
            }
            case R.id.admin_home_addOffer_button_clear:
            {
                addOffer_editText_idComp.setText("");
                addOffer_editText_idRedu.setText("");

                break;
            }
            case R.id.admin_home_addOffer_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(addOffer_idComp == null || addOffer_idComp.equals("")) { addOffer_textView_errorText.setText("Please enter the ID of the company."); break; }
                if(bdd.getCompanyWithId(Integer.parseInt(addOffer_idComp)) == null) { addOffer_textView_errorText.setText("This company doesn't exists."); break; }

                if(addOffer_idRedu == null || addOffer_idRedu.equals("")) { addOffer_textView_errorText.setText("Please enter the ID of the reduction."); break; }
                if(bdd.getReductionWithId(Integer.parseInt(addOffer_idRedu)) == null) { addOffer_textView_errorText.setText("This reduction doesn't exists."); break; }

                Offer offer = new Offer(Integer.parseInt(addOffer_idComp), Integer.parseInt(addOffer_idRedu));
                bdd.insertOffer(offer);

                String conclusion = "Success !\n"
                        + "\tIDCompany : " + offer.getIdComp() + "\n"
                        + "\tIDReduction : " + offer.getIdRedu();

                bdd.close();

                new AlertDialog.Builder(this)
                        .setMessage(conclusion)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                linearLayout_offer.setVisibility(View.INVISIBLE);
                            }
                        })
                        .show();

                break;
            }
            case R.id.admin_home_research_button_people:
            {
                String result;

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(research_people == null || research_people.equals("")) { research_textView_people_result.setText("Please enter an username."); break; }
                if(!bdd.doesPeopleAlreadyExists(research_people)) { research_textView_people_result.setText("This username doesn't exists."); break; }

                People people = bdd.getPeopleWithUsername(research_people);
                int id_people = bdd.getPeopleIdWithUsername(research_people);
                result = people.getName() + " " + people.getFirstName() + " (" + people.getSexe() + "), born on " + people.getDateOfBirth() + "\n";
                result += "Registered in :\n";

                String companiesJoined[] = null;
                String reductionsAllowed[] = null;

                companiesJoined = bdd.getCompaniesJoinedByPeople(id_people);
                if(companiesJoined == null) {
                    result += "None";
                }
                else {
                    for (int i = 0 ; i < companiesJoined.length ; i++) {
                        result += "\t->" + companiesJoined[i] + "\n";
                    }
                }

                result += "Reductions :\n";
                reductionsAllowed = bdd.getAllowedReductions(id_people);
                if(reductionsAllowed == null) {
                    result += "\t-> None\n";
                }
                else {
                    for(int i = 0 ; i < reductionsAllowed.length ; i++)
                        result += "\t-> " + reductionsAllowed[i] + "\n";
                }

                research_textView_people_result.setText(result);

                bdd.close();

                break;
            }
            case R.id.admin_home_research_button_company:
            {
                String result;

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(research_company == null || research_company.equals("")) { research_textView_company_result.setText("Please enter the name of the company."); break; }
                if(!bdd.doesCompanyAlreadyExists(research_company)) { research_textView_company_result.setText("This company doesn't exists."); break; }

                Company company = bdd.getCompanyWithName(research_company);
                int id_company = bdd.getCompanyIdWithName(research_company);
                result = company.getName() + "\n";
                result += "Offered followed reductions :\n";

                String reductionsProvided[] = null;
                String peopleJoined[] = null;

                reductionsProvided = bdd.getAllReductionsProvidedByCompany(id_company);
                if(reductionsProvided == null) {
                    result += "None\n";
                }
                else {
                    for(int i = 0 ; i < reductionsProvided.length ; i++)
                        result += reductionsProvided[i] + "\n";
                }

                result += "\nPeople in :\n";

                peopleJoined = bdd.getAllPeopleInCompany(id_company);
                if(peopleJoined == null) {
                    result += "None\n";
                }
                else {
                    for(int i = 0 ; i < peopleJoined.length ; i++)
                        result += peopleJoined[i] + "\n";
                }

                research_textView_company_result.setText(result);

                bdd.close();

                break;
            }
            case R.id.admin_home_button_reset_refresh:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();
                bdd.updateOpportunities();
                bdd.close();

                break;
            }
            case R.id.admin_home_button_reset_reset:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();
                bdd.reset();
                bdd.close();

                break;
            }
            case R.id.admin_home_button_menu:
            {
                PopupMenu popup = new PopupMenu(this, button_menu);
                popup.getMenuInflater().inflate(R.menu.menu_admin_home, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("ADD PEOPLE")) {
                            linearLayout_people.setVisibility(View.VISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD COMPANY")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.VISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD REDUCTION")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.VISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD CLIENT")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.VISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD OFFER")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.VISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("SHOW DATABASE")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.VISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("RESEARCH")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.VISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("RESET DATABASE")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_client.setVisibility(View.INVISIBLE);
                            linearLayout_offer.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                });

                popup.show();

                break;
            }
            default: {}
        }
    }

    public Boolean isBissextil(int year) {
        return ((year%4 == 0 && year%100 != 0) || year%400 == 0);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bdd.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
