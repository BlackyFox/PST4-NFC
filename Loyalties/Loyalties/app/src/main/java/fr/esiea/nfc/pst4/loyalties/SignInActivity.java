package fr.esiea.nfc.pst4.loyalties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import library_http.*;
import objects.*;
import bdd.*;

public class SignInActivity extends ActionBarActivity {
    public EditText editText_username = null;
    public EditText editText_password = null;
    public EditText editText_confirm_password = null;
    public EditText editText_name = null;
    public EditText editText_first_name = null;
    public RadioGroup radioGroup_groupSex = null;
    public RadioButton radioButton_man = null;
    public RadioButton radioButton_woman = null;
    public EditText editText_birthDay = null;
    public EditText editText_birthMonth = null;
    public EditText editText_birthYear = null;
    public EditText editText_mail = null;
    public EditText editText_city = null;
    public TextView textView_wrongText = null;

    String username, password, confirm_password, name, first_name, sexe, day, month, year , date_of_birth, mail, city;
    Boolean man, woman;
    Calendar cal;

    People people;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editText_username = (EditText) findViewById(R.id.signIn_editText_username);
        editText_password = (EditText) findViewById(R.id.signIn_editText_password);
        editText_confirm_password = (EditText) findViewById(R.id.signIn_editText_confirmPassword);
        editText_name = (EditText) findViewById(R.id.signIn_editText_name);
        editText_first_name = (EditText) findViewById(R.id.signIn_editText_firstName);
        radioGroup_groupSex = (RadioGroup) findViewById(R.id.signIn_radioGroup_groupSex);
        radioButton_man = (RadioButton) findViewById(R.id.signIn_radioButton_man);
        radioButton_woman = (RadioButton) findViewById(R.id.signIn_radioButton_woman);
        editText_birthDay = (EditText) findViewById(R.id.signIn_editText_birthDay);
        editText_birthMonth = (EditText) findViewById(R.id.signIn_editText_birthMonth);
        editText_birthYear = (EditText) findViewById(R.id.signIn_editText_birthYear);
        editText_mail = (EditText) findViewById(R.id.signIn_editText_mail);
        editText_city = (EditText) findViewById(R.id.signIn_editText_city);
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
    }

    public void endActivity(final Boolean ok) {
        String conclusion;
        if(ok) {
            conclusion = "Success !\n"
                    + "\tUsername : " + people.getUsername() + "\n"
                    + "\tPassword : " + people.getPassword() + "\n"
                    + "\tName : " + people.getName() + "\n"
                    + "\tFirst-name : " + people.getFirst_name() + "\n"
                    + "\tSexe : " + people.getSexe() + "\n"
                    + "\tDate of birth : " + people.getDate_of_birth() + "\n"
                    + "\tRole : " + people.getMail() + "\n"
                    + "\tCity : " + people.getCity();
        } else {
            conclusion = "Failed :(\n"
                    + "Please retry later or contact your mom.";
        }

        new AlertDialog.Builder(this)
                .setMessage(conclusion)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(ok)
                            SignInActivity.this.finish();
                    }
                })
                .show();
    }

    public HashMap<String, String> translateResponse(String response) {
        String[] firstSep = response.split("\",\"");
        HashMap<String, String> map = new HashMap<String, String>();
        String[] tmp;

        for(int i = 0 ; i < firstSep.length ; i++) {
            tmp = firstSep[i].split("\":\"");
            if(i == 0) tmp[0] = tmp[0].substring(3);
            if(i == firstSep.length-1) tmp[1] = tmp[1].substring(0, tmp[1].length()-3);
            map.put(tmp[0], tmp[1]);
        }

        return map;
    }

    public void addNewPeopleOnline(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("peopleJSON", people.composePeopleJSONfromSQLite());

        System.out.println(params);

        client.post("http://www.pierre-ecarlat.com/newSql/insertpeople.php", params, new AsyncHttpResponseHandler() {
            Boolean ok = false;

            @Override
            public void onStart() {
                progress = new ProgressDialog(SignInActivity.this);
                progress.setMessage("Add people online and on phone...");
                progress.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("RESPONSE : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println(arr.length());
                    HashMap<String, String> map = translateResponse(response);

                    if (map.get("alreadyExists").equals("yes")) {
                        Toast.makeText(getApplicationContext(), "User already exists !", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                    else if(map.get("works").equals("no")) {
                        Toast.makeText(getApplicationContext(), "Insertion doesn't works !", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    } else {
                        ok = true;
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progress.dismiss();
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFinish() {
                if (progress.isShowing()) {
                    progress.dismiss();
                }

                endActivity(ok);
            }
        });
    }

    public void updateData() {
        name = name.toUpperCase();
        first_name = Character.toUpperCase(first_name.charAt(0)) + first_name.substring(1).toLowerCase();
        if(man) { sexe = "M"; } else { sexe = "W"; }
        String d, m, y;
        if(day.length() == 1) { d = "0" + day; }
        else { d = day; }
        if(month.length() == 1) { m = "0" + month; }
        else { m = month; }
        if(year.length() == 1) { y = "000" + year; }
        else if(year.length() == 2) { y = "00" + year; }
        else if(year.length() == 3) { y = "0" + year; }
        else { y = year; }
        date_of_birth = y + "-" + m + "-" + d;
    }

    public Boolean checkDataFormat() {
        if(!password.equals(confirm_password)) { Toast.makeText(getApplicationContext(), "Please enter valid passwords.", Toast.LENGTH_LONG).show(); return false; }
        if(Integer.parseInt(year) < 0 || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12 || Integer.parseInt(day) < 1 || Integer.parseInt(day) > 31
                || (Integer.parseInt(day) == 31 && (Integer.parseInt(month) == 2 || Integer.parseInt(month) == 4 || Integer.parseInt(month) == 6 || Integer.parseInt(month) == 9 || Integer.parseInt(month) == 11))
                || (Integer.parseInt(day) == 30 && Integer.parseInt(month) == 2)
                || (Integer.parseInt(day) == 29 && !isBissextil(Integer.parseInt(year))))
        { Toast.makeText(getApplicationContext(), "Please enter a valid birthdate.", Toast.LENGTH_LONG).show(); return false; }
        cal = Calendar.getInstance(TimeZone.getDefault());
        if((Integer.parseInt(year) > cal.get(Calendar.YEAR))
                || (Integer.parseInt(year) == cal.get(Calendar.YEAR) && Integer.parseInt(month) > cal.get(Calendar.MONTH)+1)
                || (Integer.parseInt(year) == cal.get(Calendar.YEAR) && Integer.parseInt(month) == cal.get(Calendar.MONTH)+1 && Integer.parseInt(day) > cal.get(Calendar.DATE)))
        { Toast.makeText(getApplicationContext(), "Please enter a past birthdate.", Toast.LENGTH_LONG).show(); return false; }

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = mail;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (!matcher.matches()) {
            Toast.makeText(getApplicationContext(), "Please a valid mail.", Toast.LENGTH_LONG).show(); return false;
        }

        // Check ville ? (checker si la ville existe)

        return true;
    }

    public Boolean checkData() {
        if(username.equals("")) { Toast.makeText(getApplicationContext(), "Please enter an username.", Toast.LENGTH_LONG).show(); return false; }
        if(password.equals("")) { Toast.makeText(getApplicationContext(), "Please enter a password.", Toast.LENGTH_LONG).show(); return false; }
        if(confirm_password.equals("")) { Toast.makeText(getApplicationContext(), "Please confirm your password.", Toast.LENGTH_LONG).show(); return false; }
        if(name.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show(); return false; }
        if(first_name.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your first-name.", Toast.LENGTH_LONG).show(); return false; }
        if(!(man ^ woman)) { Toast.makeText(getApplicationContext(), "Please enter your sexe.", Toast.LENGTH_LONG).show(); return false; }
        if(day.equals("") || month.equals("") || year.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your birthdate.", Toast.LENGTH_LONG).show(); return false; }
        if(mail.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your mail.", Toast.LENGTH_LONG).show(); return false; }
        if(city.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your city.", Toast.LENGTH_LONG).show(); return false; }

        return true;
    }

    public void setValues() {
        username = editText_username.getText().toString();
        password = editText_password.getText().toString();
        confirm_password = editText_confirm_password.getText().toString();
        name = editText_name.getText().toString();
        first_name = editText_first_name.getText().toString();
        man = radioButton_man.isChecked();
        woman = radioButton_woman.isChecked();
        day = editText_birthDay.getText().toString();
        month = editText_birthMonth.getText().toString();
        year = editText_birthYear.getText().toString();
        mail = editText_mail.getText().toString();
        city = editText_city.getText().toString();
    }

    public void toDo(View v) {
        textView_wrongText.setText("");


        switch(v.getId()) {
            case R.id.signIn_button_clear:
            {
                editText_username.setText("");
                editText_password.setText("");
                editText_confirm_password.setText("");
                editText_name.setText("");
                editText_first_name.setText("");
                radioButton_man.setChecked(false);
                radioButton_woman.setChecked(false);
                editText_birthDay.setText("");
                editText_birthMonth.setText("");
                editText_birthYear.setText("");
                editText_mail.setText("");
                editText_city.setText("");

                break;
            }
            case R.id.signIn_button_signIn:
            {
                setValues();
                if(!checkData()) { break; }
                if(!checkDataFormat()) { break; }
                updateData();

                people = new People(username, password, name, first_name, sexe, date_of_birth, mail, city);

                addNewPeopleOnline();

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
