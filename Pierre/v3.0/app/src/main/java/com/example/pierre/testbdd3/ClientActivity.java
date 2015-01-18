package com.example.pierre.testbdd3;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ClientActivity extends Activity implements View.OnClickListener {

    private EditText tmp_name = null;
    private EditText tmp_first_name = null;
    public RadioGroup tmp_group = null;
    private RadioButton tmp_male = null;
    private RadioButton tmp_female = null;
    private EditText tmp_day = null;
    private EditText tmp_month = null;
    private EditText tmp_year = null;
    private EditText tmp_company = null;
    public Button tmp_button = null;
    public Button tmp_button2 = null;
    private TextView tmp_result = null;
    private TextView tmp_result2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        tmp_name = (EditText) findViewById(R.id.bdd_client_nom);
        tmp_first_name = (EditText) findViewById(R.id.bdd_client_prenom);
        tmp_group = (RadioGroup) findViewById(R.id.myRadioGroup);
        tmp_male = (RadioButton) findViewById(R.id.bdd_client_male);
        tmp_female = (RadioButton) findViewById(R.id.bdd_client_female);
        tmp_day = (EditText) findViewById(R.id.bdd_client_day);
        tmp_month = (EditText) findViewById(R.id.bdd_client_month);
        tmp_year = (EditText) findViewById(R.id.bdd_client_year);
        tmp_company = (EditText) findViewById(R.id.bdd_client_company);
        tmp_button = (Button) findViewById(R.id.button);
        tmp_button2 = (Button) findViewById(R.id.button_clean);
        tmp_result = (TextView) findViewById(R.id.bdd_client_result);
        tmp_result2 = (TextView) findViewById(R.id.result2);

        tmp_button.setOnClickListener(this);
        tmp_button2.setOnClickListener(this);

        tmp_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.bdd_client_male) {
                    tmp_female.setChecked(false);
                } else if (checkedId == R.id.bdd_client_female) {
                    tmp_male.setChecked(false);
                }
            }
        });
    }

    public String checkName(String name) {
        if(name == null || name.equals(""))
            return null;
        for(int i = 0 ; i < name.length() ; i++)
            if((name.charAt(i) < 65 || name.charAt(i) > 90) && (name.charAt(i) < 97 || name.charAt(i) > 122) && name.charAt(i) != 45 && name.charAt(i) != 32)
                return null;
        return name;
    }

    public String checkFormatName(String name) {
        return name.toUpperCase();
    }

    public String checkFormatFirstName(String name) {
        name = name.toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    public String checkFormatDay(String day, String month) {
        int tmp_day = Integer.parseInt(day);
        int tmp_month = Integer.parseInt(month);

        if(tmp_day < 1)
            return "1";

        if((tmp_month == 1 ||tmp_month == 3 ||tmp_month == 5 ||tmp_month == 7 ||tmp_month == 8 ||tmp_month == 10 ||tmp_month == 12) && tmp_day > 31)
            return "31";
        if((tmp_month == 4 ||tmp_month == 6 ||tmp_month == 9 ||tmp_month == 11) && tmp_day > 30)
            return "30";
        if(tmp_month == 2 && tmp_day > 29)
            return "29";

        return day;
    }

    public String checkFormatMonth(String month) {
        int tmp_month = Integer.parseInt(month);

        if(tmp_month < 1)
            return "1";
        else if (tmp_month > 12)
            return "12";

        return month;
    }

    public String checkFormatYear(String year) {
        int tmp_year = Integer.parseInt(year);

        if(tmp_year < 1900)
            return "1900";
        else if (tmp_year > 2015)
            return "2015";

        return year;
    }

    @Override
    public void onClick(View v) {
        String name = tmp_name.getText().toString();
        String first_name = tmp_first_name.getText().toString();
        Boolean male = tmp_male.isChecked();
        Boolean female = tmp_female.isChecked();
        String day_of_birth = tmp_day.getText().toString();
        String month_of_birth = tmp_month.getText().toString();
        String year_of_birth = tmp_year.getText().toString();
        String company = tmp_company.getText().toString();

        switch(v.getId()) {
            case R.id.button:
            {
                tmp_result.setText("");
                tmp_result2.setText("");
                name = checkName(name);
                first_name = checkName(first_name);
                if(name == null) {
                    tmp_result.setText("My name is Bond, James Bond.");
                    break;
                }
                if(first_name == null) {
                    tmp_result.setText("Doctor who ? No first-name, just \"the Doctor\".");
                    break;
                }
                if(!(male ^ female)) {
                    tmp_result.setText("Man or Woman ? That is the question.");
                    break;
                }
                if(day_of_birth == null || day_of_birth.equals("") || month_of_birth == null || month_of_birth.equals("") || year_of_birth == null || year_of_birth.equals("")) {
                    tmp_result.setText("A verry merry unbirthday to you !");
                    break;
                }
                if(company == null || company.equals("")) {
                    tmp_result.setText("If the company can run without me, then even, I can run without the company.");
                    break;
                }

                name = checkFormatName(name);
                first_name = checkFormatFirstName(first_name);

                String sexe;
                if(male)
                    sexe = "M";
                else
                    sexe = "F";

                day_of_birth = checkFormatDay(day_of_birth, month_of_birth);
                month_of_birth = checkFormatMonth(month_of_birth);
                year_of_birth = checkFormatYear(year_of_birth);

                MyBDD tmpBDD = new MyBDD(this);
                tmpBDD.open();

                People peop = new People(name, first_name, sexe, day_of_birth + "-" + month_of_birth + "-" + year_of_birth);
                Company comp = new Company(company);
                Link link = null;

                int newCompId = tmpBDD.getIdIfCompanyAlreadyExists(comp);
                if(newCompId == -1) {
                    tmp_result.setText("You can't join this company, it doesn't exists...");
                    break;
                }
                int newPeopId = tmpBDD.getIdIfPeopleAlreadyExists(peop);
                if(newPeopId == -1) {
                    tmpBDD.insertPeople(peop);
                    link = new Link(tmpBDD.getIdIfPeopleAlreadyExists(peop), newCompId, -1);
                    tmp_result.setText("Link : " + tmpBDD.getIdIfPeopleAlreadyExists(peop) + " - " + newCompId);
                }
                else {
                    link = new Link(newPeopId, newCompId, -1);
                    tmp_result2.setText("Link : " + newPeopId + " - " + newCompId);
                }

                if(tmpBDD.getIdIfLinkAlreadyExists(link) == -1) {
                    tmpBDD.insertLink(link);
                }
                else {
                    tmp_result.setText("You're already registered in this company.");
                    break;
                }

                tmp_result2.setText("GOOD !");
                tmpBDD.close();

                break;
            }
            case R.id.button_clean:
            {
                tmp_name.setText("");
                tmp_first_name.setText("");
                tmp_male.setChecked(false);
                tmp_female.setChecked(false);
                tmp_day.setText("");
                tmp_month.setText("");
                tmp_year.setText("");
                tmp_company.setText("");
                tmp_result.setText("");

                break;
            }
            default: {}
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
