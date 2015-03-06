package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant l'ajout manuel d'une carte (demande nom, prenom, sexe, age, num client.    */
/**************************************************************************************************/

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import databasePackage.MyBDD;
import library_http.*;
import objectsPackage.*;


public class AddCardActivity extends Activity implements View.OnClickListener {

    public EditText editText_num_client = null;
    public EditText editText_name = null;
    public EditText editText_first_name = null;
    public TextView textView_date = null;
    public Button button_date = null;
    public RadioGroup radioGroup_groupSex = null;
    public RadioButton radioButton_man = null;
    public RadioButton radioButton_woman = null;

    private String num_client, name, first_name, date_of_birth, sexe;
    private int y, m, d;
    private Boolean man, woman;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card2);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        MyBDD bdd = new MyBDD(this);
        bdd.open();
        People people = bdd.getPeopleWithId(id);
        bdd.close();

        editText_num_client = (EditText) findViewById(R.id.add_manually_num_client);
        editText_name = (EditText) findViewById(R.id.add_manually_name);
        editText_first_name = (EditText) findViewById(R.id.add_manually_first_name);
        textView_date = (TextView) findViewById(R.id.add_manually_textview_date);
        button_date = (Button)findViewById(R.id.add_manually_button_date);
        radioGroup_groupSex = (RadioGroup) findViewById(R.id.add_manually_groupSex);
        radioButton_man = (RadioButton) findViewById(R.id.add_manually_man);
        radioButton_woman = (RadioButton) findViewById(R.id.add_manually_woman);

        button_date.setOnClickListener(this);

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

        editText_first_name.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm =
                            (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText_first_name.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v){
        if(v == button_date){
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            textView_date.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
    }

    // Clôt l'activité en affichant la conclusion
    public void endActivity(final Boolean ok1, final Boolean ok2, final Client client, final Company company) {
        String conclusion;
        if(ok1) {
            conclusion = "Success !\n" + "\tYou just joined : " + company.getName();
            if(ok2) {
                conclusion += " and " + company.getName() + " is now in the database.";
            } else {
                conclusion += " and " + company.getName() + " was also in the database.";
            }
        } else {
            conclusion = "Failed, maybe bad data ?";
        }

        new AlertDialog.Builder(this).setMessage(conclusion).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(ok1)
                            AddCardActivity.this.finish();
                    }
                }).show();
    }

    // Traduit la réponse reçue par le .php en ligne
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

    // Vérifie si le client existe bien (si le numéro de client correspond bien aux données) et insert le client
    public void checkIfClientIsOkOnline() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        System.out.println("-> début de checkifclientisokonline");

        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("num_client", num_client);
        map.put("name", name);
        map.put("first_name", first_name);
        map.put("sexe", sexe);
        map.put("date_of_birth", date_of_birth);
        wordList.add(map);

        Gson gson = new GsonBuilder().create();

        params.put("addClientManuallyJSON", gson.toJson(wordList));

        System.out.println("-> params JSON envoyés : " + params);

        client.post("http://www.pierre-ecarlat.com/newSql/insertclientmanually.php", params, new AsyncHttpResponseHandler() {
            Boolean ok1 = false;
            Boolean ok2 = false;
            Client tmpClient;
            Company tmpCompany;

            @Override
            public void onStart() {
                progress = new ProgressDialog(AddCardActivity.this);
                progress.setMessage("Checking if data are okay...");
                progress.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                System.out.println("-> le post est dans onsuccess");

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("Check if client is ok online (php response) : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);

                    if (map.get("dataOk").equals("yes")) {
                        tmpClient = new Client(Integer.parseInt(map.get("id")), Integer.parseInt(map.get("id_peop")), Integer.parseInt(map.get("id_comp")), map.get("num_client"), Integer.parseInt(map.get("nb_loyalties")), Integer.parseInt(map.get("last_used")));
                        tmpClient.setUp_date(map.get("up_date"));

                        MyBDD bdd = new MyBDD(AddCardActivity.this);
                        bdd.open();
                        if(bdd.doesClientAlreadyExists(tmpClient.getId())) {
                            Toast.makeText(getApplicationContext(), "Client already in database !", Toast.LENGTH_LONG).show();
                        } else {
                            bdd.insertClient(tmpClient);
                            ok1 = true;
                            // TODO : faire avec le download d'images + stockage dans resources
                            Toast.makeText(getApplicationContext(), "Insertion client ok", Toast.LENGTH_LONG).show();
                            tmpCompany = new Company(Integer.parseInt(map.get("company_id")), map.get("company_name"), map.get("company_logo"), map.get("company_card"));
                            tmpCompany.setUp_date(map.get("company_up_date"));
                            if(!bdd.doesCompanyAlreadyExists(tmpCompany.getName())) {
                                ImageLoadTask ilt_logo = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getLogo().toLowerCase() + ".png", tmpCompany.getLogo().toLowerCase() + ".png");
                                ImageLoadTask ilt_card = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getCard().toLowerCase() + ".png", tmpCompany.getCard().toLowerCase() + ".png");
                                ilt_logo.execute();
                                ilt_card.execute();

                                bdd.insertCompany(tmpCompany);
                                ok2 = true;
                            }
                        }
                        bdd.close();
                        progress.dismiss();
                    }
                    else if(map.get("dataOk").equals("no")) {
                        Toast.makeText(getApplicationContext(), "Insertion doesn't works !", Toast.LENGTH_LONG).show();
                        progress.dismiss();
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

                endActivity(ok1, ok2, tmpClient, tmpCompany);
            }
        });
    }

    public void createImage(Bitmap image, String compPath) throws FileNotFoundException {
        Context context = getApplicationContext();
        String path = context.getFilesDir().getAbsolutePath();
        OutputStream stream = new FileOutputStream(path + "/" + compPath);
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
    }

    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private String compPath;

        public ImageLoadTask(String url, String compPath) {
            this.url = url;
            this.compPath = compPath;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //after downloading
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            try {
                createImage(result, compPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Met les données dans le format souhaité
    public void updateData() {
        name = name.toUpperCase();
        first_name = Character.toUpperCase(first_name.charAt(0)) + first_name.substring(1).toLowerCase();
        String day, month, year;
        if(d < 10) day = "0" + d;
        else day = Integer.toString(d);
        if(m < 10) month = "0" + m;
        else month = Integer.toString(m);
        if(y < 10) year = "000" + y;
        else if(y < 100) year = "00" + y;
        else if(y < 1000) year = "0" + y;
        else year = Integer.toString(y);
        date_of_birth = year + "-" + month + "-" + day;
        if(man) { sexe = "M"; } else { sexe = "W"; }
    }

    // Vérifie le format des données
    public Boolean checkDataFormat() {
        String[] parts = date_of_birth.split("/");
        d = Integer.parseInt(parts[0]);
        m = Integer.parseInt(parts[1]);
        y = Integer.parseInt(parts[2]);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        if((y > cal.get(Calendar.YEAR))
                || (y == cal.get(Calendar.YEAR) && m > cal.get(Calendar.MONTH)+1)
                || (y == cal.get(Calendar.YEAR) && m == cal.get(Calendar.MONTH)+1 && d > cal.get(Calendar.DATE)))
        { Toast.makeText(getApplicationContext(), "Please enter a past birthdate.", Toast.LENGTH_LONG).show(); return false; }

        return true;
    }

    // Vérifie si l'on a entré des données
    public Boolean checkData() {
        if(num_client.equals("")) { Toast.makeText(getApplicationContext(), "Please enter a serial number.", Toast.LENGTH_LONG).show(); return false; }
        if(name.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show(); return false; }
        if(first_name.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your first-name.", Toast.LENGTH_LONG).show(); return false; }
        if(date_of_birth.equals("DD/MM/YYYY")) {Toast.makeText(getApplicationContext(), "Please enter your birth date.", Toast.LENGTH_LONG).show(); return false; }
        if(!(man ^ woman)) { Toast.makeText(getApplicationContext(), "Please enter your sexe.", Toast.LENGTH_LONG).show(); return false; }

        return true;
    }

    // Récupère les données entrées
    public void setValues() {
        num_client = editText_num_client.getText().toString();
        name = editText_name.getText().toString();
        first_name = editText_first_name.getText().toString();
        date_of_birth = textView_date.getText().toString();
        man = radioButton_man.isChecked();
        woman = radioButton_woman.isChecked();
    }

    // Gère les appuis sur les coutons (Clear, Add)
    public void toDo(View v) {
        switch(v.getId()) {
            case R.id.add_manually_button_clear:
            {
                editText_num_client.setText("");
                editText_name.setText("");
                editText_first_name.setText("");
                textView_date.setText("DD/MM/YYYY");
                radioButton_man.setChecked(false);
                radioButton_woman.setChecked(false);

                break;
            }
            case R.id.add_manually_button_add:
            {
                setValues();
                if(!checkData()) { break; }
                if(!checkDataFormat()) { break; }
                updateData();


                checkIfClientIsOkOnline();

                break;
            }
            default: {}
        }
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
