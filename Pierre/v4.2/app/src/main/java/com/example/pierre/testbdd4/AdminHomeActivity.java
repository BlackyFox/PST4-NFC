package com.example.pierre.testbdd4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
    public LinearLayout linearLayout_link = null;
    public LinearLayout linearLayout_reset = null;
    public LinearLayout linearLayout_research = null;
    public LinearLayout linearLayout_show = null;

    public EditText editText_addPeople_username = null;
    public EditText editText_addPeople_password = null;
    public EditText editText_addPeople_confirmPassword = null;
    public EditText editText_addPeople_name = null;
    public EditText editText_addPeople_first_name = null;
    public RadioGroup radioGroup_addPeople_groupSex = null;
    public RadioButton radioButton_addPeople_man = null;
    public RadioButton radioButton_addPeople_woman = null;
    public EditText editText_addPeople_birthDay = null;
    public EditText editText_addPeople_birthMonth = null;
    public EditText editText_addPeople_birthYear = null;
    public RadioGroup radioGroup_addPeople_groupRole = null;
    public RadioButton radioButton_addPeople_admin = null;
    public RadioButton radioButton_addPeople_user = null;
    public TextView textView_addPeople_wrongText = null;

    public EditText editText_addCompany_name = null;
    public TextView textView_addCompany_wrongText = null;

    public EditText editText_addReduction_type = null;
    public TextView textView_addReduction_wrongText = null;

    public EditText editText_addLink_idComp = null;
    public EditText editText_addLink_idPeop = null;
    public EditText editText_addLink_idRedu = null;
    public TextView textView_addLink_wrongText = null;

    public RadioGroup radioGroup_show_groupShow = null;
    public RadioButton radioButton_show_people = null;
    public RadioButton radioButton_show_company = null;
    public RadioButton radioButton_show_reduction = null;
    public RadioButton radioButton_show_link = null;
    public TextView textView_show_result = null;

    public RadioGroup radioGroup_research = null;
    public RadioButton radioButton_research_people = null;
    public RadioButton radioButton_research_company = null;
    public LinearLayout linearLayout_research_people = null;
    public LinearLayout linearLayout_research_company = null;
    public EditText editText_research_people = null;
    public EditText editText_research_company = null;
    public TextView textView_research_people_result = null;
    public TextView textView_research_company_result = null;


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
        linearLayout_link = (LinearLayout) findViewById(R.id.admin_home_linearLayout_link);
        linearLayout_show = (LinearLayout) findViewById(R.id.admin_home_linearLayout_show);
        linearLayout_research = (LinearLayout) findViewById(R.id.admin_home_linearLayout_research);
        linearLayout_reset = (LinearLayout) findViewById(R.id.admin_home_linearLayout_reset);


        editText_addPeople_username = (EditText) findViewById(R.id.admin_home_addPeople_editText_username);
        editText_addPeople_password = (EditText) findViewById(R.id.admin_home_addPeople_editText_password);
        editText_addPeople_confirmPassword = (EditText) findViewById(R.id.admin_home_addPeople_editText_confirmPassword);
        editText_addPeople_name = (EditText) findViewById(R.id.admin_home_addPeople_editText_name);
        editText_addPeople_first_name = (EditText) findViewById(R.id.admin_home_addPeople_editText_firstName);
        radioGroup_addPeople_groupSex = (RadioGroup) findViewById(R.id.admin_home_addPeople_radioGroup_groupSex);
        radioButton_addPeople_man = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_man);
        radioButton_addPeople_woman = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_woman);
        editText_addPeople_birthDay = (EditText) findViewById(R.id.admin_home_addPeople_editText_birthDay);
        editText_addPeople_birthMonth = (EditText) findViewById(R.id.admin_home_addPeople_editText_birthMonth);
        editText_addPeople_birthYear = (EditText) findViewById(R.id.admin_home_addPeople_editText_birthYear);
        radioGroup_addPeople_groupRole = (RadioGroup) findViewById(R.id.admin_home_addPeople_radioGroup_groupRole);
        radioButton_addPeople_admin = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_admin);
        radioButton_addPeople_user = (RadioButton) findViewById(R.id.admin_home_addPeople_radioButton_user);
        textView_addPeople_wrongText = (TextView) findViewById(R.id.admin_home_addPeople_textView_wrongText);

        radioGroup_addPeople_groupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.signIn_radioButton_man) {
                    radioButton_addPeople_woman.setChecked(false);
                } else if (checkedId == R.id.signIn_radioButton_woman) {
                    radioButton_addPeople_man.setChecked(false);
                }
            }
        });

        radioGroup_addPeople_groupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.signIn_radioButton_user) {
                    radioButton_addPeople_admin.setChecked(false);
                } else if (checkedId == R.id.signIn_radioButton_admin) {
                    radioButton_addPeople_user.setChecked(false);
                }
            }
        });


        editText_addCompany_name = (EditText) findViewById(R.id.admin_home_addCompany_editText_name);
        textView_addCompany_wrongText = (TextView) findViewById(R.id.admin_home_addCompany_textView_wrongText);


        editText_addReduction_type = (EditText) findViewById(R.id.admin_home_addReduction_editText_type);
        textView_addReduction_wrongText = (TextView) findViewById(R.id.admin_home_addReduction_textView_wrongText);


        editText_addLink_idComp = (EditText) findViewById(R.id.admin_home_addLink_editText_idComp);
        editText_addLink_idPeop = (EditText) findViewById(R.id.admin_home_addLink_editText_idPeop);
        editText_addLink_idRedu = (EditText) findViewById(R.id.admin_home_addLink_editText_idRedu);
        textView_addLink_wrongText = (TextView) findViewById(R.id.admin_home_addLink_textView_wrongText);


        radioGroup_show_groupShow = (RadioGroup) findViewById(R.id.admin_home_radioGroup_show_groupShow);
        radioButton_show_people = (RadioButton) findViewById(R.id.admin_home_radioButton_show_people);
        radioButton_show_company = (RadioButton) findViewById(R.id.admin_home_radioButton_show_company);
        radioButton_show_reduction = (RadioButton) findViewById(R.id.admin_home_radioButton_show_reduction);
        radioButton_show_link = (RadioButton) findViewById(R.id.admin_home_radioButton_show_link);
        textView_show_result = (TextView) findViewById(R.id.admin_home_textView_show_result);

        radioGroup_show_groupShow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.admin_home_radioButton_show_people) {
                    radioButton_show_company.setChecked(false);
                    radioButton_show_reduction.setChecked(false);
                    radioButton_show_link.setChecked(false);
                    String people = bdd.showPeople();
                    textView_show_result.setText(people);
                } else if (checkedId == R.id.admin_home_radioButton_show_company) {
                    radioButton_show_people.setChecked(false);
                    radioButton_show_reduction.setChecked(false);
                    radioButton_show_link.setChecked(false);
                    String company = bdd.showCompany();
                    textView_show_result.setText(company);
                } else if (checkedId == R.id.admin_home_radioButton_show_reduction) {
                    radioButton_show_people.setChecked(false);
                    radioButton_show_company.setChecked(false);
                    radioButton_show_link.setChecked(false);
                    String reduction = bdd.showReduction();
                    textView_show_result.setText(reduction);
                } else if (checkedId == R.id.admin_home_radioButton_show_link) {
                    radioButton_show_people.setChecked(false);
                    radioButton_show_company.setChecked(false);
                    radioButton_show_reduction.setChecked(false);
                    String link = bdd.showLink();
                    textView_show_result.setText(link);
                }
            }
        });


        radioGroup_research = (RadioGroup) findViewById(R.id.admin_home_radioGroup_groupResearch);
        radioButton_research_people = (RadioButton) findViewById(R.id.admin_home_radioButton_research_people);
        radioButton_research_company = (RadioButton) findViewById(R.id.admin_home_radioButton_research_company);
        linearLayout_research_people = (LinearLayout) findViewById(R.id.admin_home_linearLayout_research_people);
        linearLayout_research_company = (LinearLayout) findViewById(R.id.admin_home_linearLayout_research_company);
        editText_research_people = (EditText) findViewById(R.id.admin_home_editText_research_people);
        editText_research_company = (EditText) findViewById(R.id.admin_home_editText_research_name);
        textView_research_people_result = (TextView) findViewById(R.id.admin_home_textView_research_people_result);
        textView_research_company_result = (TextView) findViewById(R.id.admin_home_textView_research_company_result);

        radioGroup_research.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.admin_home_radioButton_research_people) {
                    radioButton_research_company.setChecked(false);
                    linearLayout_research_people.setVisibility(View.VISIBLE);
                    linearLayout_research_company.setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.admin_home_radioButton_research_company) {
                    radioButton_research_people.setChecked(false);
                    linearLayout_research_people.setVisibility(View.INVISIBLE);
                    linearLayout_research_company.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void toDo(View v) {
        String username = editText_addPeople_username.getText().toString();
        String password = editText_addPeople_password.getText().toString();
        String confirmPassword = editText_addPeople_confirmPassword.getText().toString();
        String name = editText_addPeople_name.getText().toString();
        String first_name = editText_addPeople_first_name.getText().toString();
        Boolean man = radioButton_addPeople_man.isChecked();
        Boolean woman = radioButton_addPeople_woman.isChecked();
        String sexe;
        String day = editText_addPeople_birthDay.getText().toString();
        String month = editText_addPeople_birthMonth.getText().toString();
        String year = editText_addPeople_birthYear.getText().toString();
        Boolean admin = radioButton_addPeople_admin.isChecked();
        Boolean user = radioButton_addPeople_user.isChecked();
        String role;
        Calendar cal;

        String company_name = editText_addCompany_name.getText().toString();

        String reduction_type = editText_addReduction_type.getText().toString();

        String idPeop = editText_addLink_idPeop.getText().toString();
        String idComp = editText_addLink_idComp.getText().toString();
        String idRedu = editText_addLink_idRedu.getText().toString();

        String lookForPeople = editText_research_people.getText().toString();
        String lookForCompany = editText_research_company.getText().toString();


        switch(v.getId()) {
            case R.id.admin_home_addPeople_button_clear:
            {
                editText_addPeople_username.setText("");
                editText_addPeople_password.setText("");
                editText_addPeople_confirmPassword.setText("");
                editText_addPeople_name.setText("");
                editText_addPeople_first_name.setText("");
                radioButton_addPeople_man.setChecked(false);
                radioButton_addPeople_woman.setChecked(false);
                editText_addPeople_birthDay.setText("");
                editText_addPeople_birthMonth.setText("");
                editText_addPeople_birthYear.setText("");
                radioButton_addPeople_admin.setChecked(false);
                radioButton_addPeople_user.setChecked(false);

                break;
            }
            case R.id.admin_home_addPeople_button_signIn:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(username == null || username.equals("")) { textView_addPeople_wrongText.setText("Please enter an username."); break; }
                if(bdd.doesPeopleAlreadyExists(username)) { textView_addPeople_wrongText.setText("This username is already used."); break; }

                if(password == null || password.equals("")) { textView_addPeople_wrongText.setText("Please enter a password."); break; }
                if(confirmPassword == null || confirmPassword.equals("")) { textView_addPeople_wrongText.setText("Please confirm the password."); break; }
                if(!password.equals(confirmPassword)) { textView_addPeople_wrongText.setText("Both password and confirmation are not identical."); break; }

                if(name == null || name.equals("")) { textView_addPeople_wrongText.setText("Please enter your name."); break; }
                name = name.toUpperCase();

                if(first_name == null || first_name.equals("")) { textView_addPeople_wrongText.setText("Please enter your first-name."); break; }
                first_name = Character.toUpperCase(first_name.charAt(0)) + first_name.substring(1).toLowerCase();

                if(!(man ^ woman)) { textView_addPeople_wrongText.setText("Man or Woman ? That is the question."); break; }
                if(man) { sexe = "M"; } else { sexe = "F"; }

                if(day == null || day.equals("") || month == null || month.equals("") || year == null || year.equals("")) { textView_addPeople_wrongText.setText("Please enter your birth date."); break; }
                if(Integer.parseInt(year) < 0 || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12 || Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31
                        || (Integer.parseInt(day) == 31 && (Integer.parseInt(month) == 2 || Integer.parseInt(month) == 4 || Integer.parseInt(month) == 6 || Integer.parseInt(month) == 9 || Integer.parseInt(month) == 11))
                        || (Integer.parseInt(day) == 30 && Integer.parseInt(month) == 2)
                        || (Integer.parseInt(day) == 29 && !isBissextil(Integer.parseInt(year))))
                { textView_addPeople_wrongText.setText("Please enter a valid date."); break; }
                cal = Calendar.getInstance(TimeZone.getDefault());
                if((Integer.parseInt(year) > cal.get(Calendar.YEAR))
                        || (Integer.parseInt(year) == cal.get(Calendar.YEAR) && Integer.parseInt(month) > cal.get(Calendar.MONTH)+1)
                        || (Integer.parseInt(year) == cal.get(Calendar.YEAR) && Integer.parseInt(month) == cal.get(Calendar.MONTH)+1 && Integer.parseInt(day) > cal.get(Calendar.DATE)))
                { textView_addPeople_wrongText.setText("Please enter a past date."); break; }

                if(!(user ^ admin)) { textView_addPeople_wrongText.setText("Chose your role."); break; }
                if(user) { role = "U"; } else { role = "A"; }


                People peop = new People(username, password, name, first_name, sexe, day + "-" + month + "-" + year, role);
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
                editText_addCompany_name.setText("");

                break;
            }
            case R.id.admin_home_addCompany_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(company_name == null || company_name.equals("")) { textView_addCompany_wrongText.setText("Please enter the name of your company."); break; }
                if(bdd.doesCompanyAlreadyExists(company_name)) { textView_addCompany_wrongText.setText("This company already exists."); break; }

                Company comp = new Company(company_name);
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
                editText_addReduction_type.setText("");

                break;
            }
            case R.id.admin_home_addReduction_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(reduction_type == null || reduction_type.equals("")) { textView_addReduction_wrongText.setText("Please enter the type of your reduction."); break; }
                if(bdd.doesReductionAlreadyExists(reduction_type)) { textView_addReduction_wrongText.setText("This kind of reduction already exists."); break; }

                Reduction red = new Reduction(reduction_type);
                bdd.insertReduction(red);

                String conclusion = "Success !\n"
                        + "\tType : " + red.getType();

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
            case R.id.admin_home_addLink_button_clear:
            {
                editText_addLink_idPeop.setText("");
                editText_addLink_idComp.setText("");
                editText_addLink_idRedu.setText("");

                break;
            }
            case R.id.admin_home_addLink_button_insert:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(idPeop == null || idPeop.equals("")) { textView_addLink_wrongText.setText("Please enter the ID of the people."); break; }
                if(bdd.getPeopleWithId(Integer.parseInt(idPeop)) == null) { textView_addLink_wrongText.setText("This people doesn't exists."); break; }

                if(idComp == null || idComp.equals("")) { textView_addLink_wrongText.setText("Please enter the ID of the company."); break; }
                if(bdd.getCompanyWithId(Integer.parseInt(idComp)) == null) { textView_addLink_wrongText.setText("This company doesn't exists."); break; }

                if(idRedu == null || idRedu.equals("") || bdd.getReductionWithId(Integer.parseInt(idRedu)) == null) { idRedu = "-1"; }

                Link link = new Link(Integer.parseInt(idComp), Integer.parseInt(idPeop), 0, Integer.parseInt(idRedu));
                bdd.insertLink(link);

                String conclusion = "Success !\n"
                        + "\tIDCompany : " + link.getIdComp() + "\n"
                        + "\tIDPeople : " + link.getIdPeop() + "\n"
                        + "\tIDReduction : " + link.getIdRedu();

                bdd.close();

                new AlertDialog.Builder(this)
                        .setMessage(conclusion)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                linearLayout_link.setVisibility(View.INVISIBLE);
                            }
                        })
                        .show();

                break;
            }
            case R.id.admin_home_button_research_people:
            {
                String result;

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(lookForPeople == null || lookForPeople.equals("")) { textView_research_people_result.setText("Please enter an username."); break; }
                if(!bdd.doesPeopleAlreadyExists(lookForPeople)) { textView_research_people_result.setText("This username doesn't exists."); break; }

                People people = bdd.getPeopleWithUsername(lookForPeople);
                int id_people = bdd.getPeopleIdWithUsername(lookForPeople);
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
                        result += companiesJoined[i] + "\n";

                        reductionsAllowed = bdd.getReductionsAllowedFromPeopleAndCompany(id_people, companiesJoined[i]);
                        if(reductionsAllowed == null) {
                            result += "\t-> None\n";
                        }
                        else {
                            for(int j = 0 ; j < reductionsAllowed.length ; j++)
                                result += "\t-> " + reductionsAllowed[j] + "\n";
                        }
                    }
                }

                textView_research_people_result.setText(result);

                bdd.close();

                break;
            }
            case R.id.admin_home_button_research_company:
            {
                String result;

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(lookForCompany == null || lookForCompany.equals("")) { textView_research_company_result.setText("Please enter the name of the company."); break; }
                if(!bdd.doesCompanyAlreadyExists(lookForCompany)) { textView_research_company_result.setText("This company doesn't exists."); break; }

                Company company = bdd.getCompanyWithName(lookForCompany);
                int id_company = bdd.getCompanyIdWithName(lookForCompany);
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

                textView_research_company_result.setText(result);

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
                            linearLayout_link.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD COMPANY")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.VISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_link.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD REDUCTION")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.VISIBLE);
                            linearLayout_link.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("ADD LINK")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_link.setVisibility(View.VISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("SHOW DATABASE")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_link.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.VISIBLE);
                            linearLayout_research.setVisibility(View.INVISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("RESEARCH")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_link.setVisibility(View.INVISIBLE);
                            linearLayout_show.setVisibility(View.INVISIBLE);
                            linearLayout_research.setVisibility(View.VISIBLE);
                            linearLayout_reset.setVisibility(View.INVISIBLE);
                        } else if (item.getTitle().equals("RESET DATABASE")) {
                            linearLayout_people.setVisibility(View.INVISIBLE);
                            linearLayout_company.setVisibility(View.INVISIBLE);
                            linearLayout_reduction.setVisibility(View.INVISIBLE);
                            linearLayout_link.setVisibility(View.INVISIBLE);
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
