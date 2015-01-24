package fr.esiea.nfc.pst4.loyalties;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.util.TimeZone;

import objects.*;
import bdd.*;

public class SignInActivity extends ActionBarActivity {

    public EditText editText_username = null;
    public EditText editText_password = null;
    public EditText editText_confirmPassword = null;
    public EditText editText_name = null;
    public EditText editText_first_name = null;
    public RadioGroup radioGroup_groupSex = null;
    public RadioButton radioButton_man = null;
    public RadioButton radioButton_woman = null;
    public EditText editText_birthDay = null;
    public EditText editText_birthMonth = null;
    public EditText editText_birthYear = null;
    public RadioGroup radioGroup_groupRole = null;
    public RadioButton radioButton_admin = null;
    public RadioButton radioButton_user = null;
    public TextView textView_wrongText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editText_username = (EditText) findViewById(R.id.signIn_editText_username);
        editText_password = (EditText) findViewById(R.id.signIn_editText_password);
        editText_confirmPassword = (EditText) findViewById(R.id.signIn_editText_confirmPassword);
        editText_name = (EditText) findViewById(R.id.signIn_editText_name);
        editText_first_name = (EditText) findViewById(R.id.signIn_editText_firstName);
        radioGroup_groupSex = (RadioGroup) findViewById(R.id.signIn_radioGroup_groupSex);
        radioButton_man = (RadioButton) findViewById(R.id.signIn_radioButton_man);
        radioButton_woman = (RadioButton) findViewById(R.id.signIn_radioButton_woman);
        editText_birthDay = (EditText) findViewById(R.id.signIn_editText_birthDay);
        editText_birthMonth = (EditText) findViewById(R.id.signIn_editText_birthMonth);
        editText_birthYear = (EditText) findViewById(R.id.signIn_editText_birthYear);
        radioGroup_groupRole = (RadioGroup) findViewById(R.id.signIn_radioGroup_groupRole);
        radioButton_admin = (RadioButton) findViewById(R.id.signIn_radioButton_admin);
        radioButton_user = (RadioButton) findViewById(R.id.signIn_radioButton_user);
        textView_wrongText = (TextView) findViewById(R.id.signIn_textView_wrongText);

        radioGroup_groupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.signIn_radioButton_man) {
                    radioButton_woman.setChecked(false);
                } else if (checkedId == R.id.signIn_radioButton_woman) {
                    radioButton_man.setChecked(false);
                }
            }
        });

        radioGroup_groupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.signIn_radioButton_user) {
                    radioButton_admin.setChecked(false);
                } else if (checkedId == R.id.signIn_radioButton_admin) {
                    radioButton_user.setChecked(false);
                }
            }
        });
    }

    public void toDo(View v) {
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();
        String confirmPassword = editText_confirmPassword.getText().toString();
        String name = editText_name.getText().toString();
        String first_name = editText_first_name.getText().toString();
        Boolean man = radioButton_man.isChecked();
        Boolean woman = radioButton_woman.isChecked();
        String sexe;
        String day = editText_birthDay.getText().toString();
        String month = editText_birthMonth.getText().toString();
        String year = editText_birthYear.getText().toString();
        Boolean admin = radioButton_admin.isChecked();
        Boolean user = radioButton_user.isChecked();
        String role;
        Calendar cal;

        textView_wrongText.setText("");


        switch(v.getId()) {
            case R.id.signIn_button_clear:
            {
                editText_username.setText("");
                editText_password.setText("");
                editText_confirmPassword.setText("");
                editText_name.setText("");
                editText_first_name.setText("");
                radioButton_man.setChecked(false);
                radioButton_woman.setChecked(false);
                editText_birthDay.setText("");
                editText_birthMonth.setText("");
                editText_birthYear.setText("");
                radioButton_admin.setChecked(false);
                radioButton_user.setChecked(false);

                break;
            }
            case R.id.signIn_button_signIn:
            {
                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(username == null || username.equals("")) { textView_wrongText.setText("Please enter an username."); break; }
                if(bdd.doesPeopleAlreadyExists(username)) { textView_wrongText.setText("This username is already used."); break; }

                if(password == null || password.equals("")) { textView_wrongText.setText("Please enter a password."); break; }
                if(confirmPassword == null || confirmPassword.equals("")) { textView_wrongText.setText("Please confirm the password."); break; }
                if(!password.equals(confirmPassword)) { textView_wrongText.setText("Both password and confirmation are not identical."); break; }

                if(name == null || name.equals("")) { textView_wrongText.setText("Please enter your name."); break; }
                name = name.toUpperCase();

                if(first_name == null || first_name.equals("")) { textView_wrongText.setText("Please enter your first-name."); break; }
                first_name = Character.toUpperCase(first_name.charAt(0)) + first_name.substring(1).toLowerCase();

                if(!(man ^ woman)) { textView_wrongText.setText("Man or Woman ? That is the question."); break; }
                if(man) { sexe = "M"; } else { sexe = "F"; }

                if(day == null || day.equals("") || month == null || month.equals("") || year == null || year.equals("")) { textView_wrongText.setText("Please enter your birth date."); break; }
                if(Integer.parseInt(year) < 0 || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12 || Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31
                        || (Integer.parseInt(day) == 31 && (Integer.parseInt(month) == 2 || Integer.parseInt(month) == 4 || Integer.parseInt(month) == 6 || Integer.parseInt(month) == 9 || Integer.parseInt(month) == 11))
                        || (Integer.parseInt(day) == 30 && Integer.parseInt(month) == 2)
                        || (Integer.parseInt(day) == 29 && !isBissextil(Integer.parseInt(year))))
                { textView_wrongText.setText("Please enter a valid date."); break; }
                cal = Calendar.getInstance(TimeZone.getDefault());
                if((Integer.parseInt(year) > cal.get(Calendar.YEAR))
                        || (Integer.parseInt(year) == cal.get(Calendar.YEAR) && Integer.parseInt(month) > cal.get(Calendar.MONTH)+1)
                        || (Integer.parseInt(year) == cal.get(Calendar.YEAR) && Integer.parseInt(month) == cal.get(Calendar.MONTH)+1 && Integer.parseInt(day) > cal.get(Calendar.DATE)))
                { textView_wrongText.setText("Please enter a past date."); break; }

                if(!(user ^ admin)) { textView_wrongText.setText("Chose your role."); break; }
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
                                SignInActivity.this.finish();
                            }
                        })
                        .show();

                break;
            }
            default: {}
        }
    }

    public Boolean isBissextil(int year) {
        return ((year%4 == 0 && year%100 != 0) || year%400 == 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
