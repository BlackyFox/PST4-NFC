package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant de s'enregistrer.                                                          */
/**************************************************************************************************/

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import objectsPackage.*;


public class SignInActivity extends Activity {
    public EditText editText_username = null;
    public EditText editText_password = null;
    public EditText editText_confirm_password = null;
    public EditText editText_name = null;
    public EditText editText_first_name = null;
    public RadioGroup radioGroup_groupSex = null;
    public RadioButton radioButton_man = null;
    public RadioButton radioButton_woman = null;
    public EditText editText_mail = null;
    public EditText editText_city = null;
    public TextView textView_wrongText = null;
    public Button date = null;
    public TextView showDate = null;

    String username, password, confirm_password, name, first_name, sexe,  date_of_birth, mail, city;
    String day = null, month = null, year = null;
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
        editText_mail = (EditText) findViewById(R.id.signIn_editText_mail);
        editText_city = (EditText) findViewById(R.id.signIn_editText_city);
        textView_wrongText = (TextView) findViewById(R.id.signIn_textView_wrongText);
        date = (Button) findViewById(R.id.setDateButton);
        showDate = (TextView) findViewById(R.id.showDate);

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
        date.setOnClickListener(datePick);
    }

    private View.OnClickListener datePick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setDate(v);
        }
    };

    public void setDate(View v){
        showDialog(999);
    }
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 999){
            int yy, mm, dd;
            Calendar c = Calendar.getInstance();
            yy = c.get(Calendar.YEAR);
            mm = c.get(Calendar.MONTH);
            dd = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(this, myDateListener, yy, mm, dd);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        int d = day;
        int m = month;
        int y = year;
        String day_s, month_s;
        if(d < 10)
            day_s = "0" + Integer.toString(d);
        else
            day_s = Integer.toString(d);
        this.day = day_s;
        if(m < 9) {
            m += 1;
            month_s = "0" + Integer.toString(m);
        }
        else {
            m+=1;
            month_s = Integer.toString(m);
        }
        this.month = month_s;
        this.year = Integer.toString(y);
        showDate.setText(new StringBuilder().append(day_s).append("/").append(month_s).append("/").append(year));
    }

    // Clot l'activité et affiche le résultat dans le progressbar
    public void endActivity(final Boolean ok) {
        String conclusion;
        if(ok) {
            conclusion = "Success !";
        } else {
            conclusion = "Failed ...";
        }

        new AlertDialog.Builder(this).setMessage(conclusion).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(ok) { SignInActivity.this.finish(); }
                    }
                }).show();
    }

    // Traduit la réponse du .php en ligne
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

    // Ajout du nouvel utilisateur dans la bdd en ligne
    public void addNewPeopleOnline(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("peopleJSON", people.composePeopleJSONfromSQLite());

        client.post("http://www.pierre-ecarlat.com/newSql/insertpeople.php", params, new AsyncHttpResponseHandler() {
            Boolean ok = false;

            @Override
            public void onStart() {
                progress = new ProgressDialog(SignInActivity.this);
                progress.setMessage("Add people online...");
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
                System.out.println("Check sign in online (php response) : " + response);
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

    // Met les données aux formats voulus
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

    // Vérifie que les données sont correctes
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

        // TODO : checker si la ville existe

        return true;
    }

    // Vérifie que l'on a bien entré des données partout
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

    // Récupère les données entrées
    public int setValues() {
        if(day == null && month == null && year == null) {
            return 1;
        }else{
            username = editText_username.getText().toString();
            password = editText_password.getText().toString();
            confirm_password = editText_confirm_password.getText().toString();
            name = editText_name.getText().toString();
            first_name = editText_first_name.getText().toString();
            man = radioButton_man.isChecked();
            woman = radioButton_woman.isChecked();
            mail = editText_mail.getText().toString();
            city = editText_city.getText().toString();
            return 0;
        }
    }

    // Traite l'appui sur les boutons
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
                day = null;
                month = null;
                year = null;
                showDate.setText("");
                editText_mail.setText("");
                editText_city.setText("");

                break;
            }
            case R.id.signIn_button_signIn:
            {
                if(setValues() == 0) {
                    if (!checkData()) {
                        break;
                    }
                    if (!checkDataFormat()) {
                        break;
                    }
                    updateData();

                    people = new People(-1, username, password, name, first_name, sexe, date_of_birth, mail, city);

                    addNewPeopleOnline();
                }else{
                    Toast.makeText(getApplicationContext(), "Come on, give us your birth date, it's for your own good!", Toast.LENGTH_LONG).show();
                }

                break;
            }
            default: {}
        }
    }

    // Vérifie si l'année est bissextile (deviendra inutile avec le DatePicker)
    public Boolean isBissextil(int year) {
        return ((year%4 == 0 && year%100 != 0) || year%400 == 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //setContentView(R.layout.activity_main_activity2);

        } else {
            //setContentView(R.layout.activity_main_activity2);
        }
    }
}
