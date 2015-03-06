package com.example.pierre.applicompanies;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pierre.applicompanies.library_http.AsyncHttpClient;
import com.example.pierre.applicompanies.library_http.AsyncHttpResponseHandler;
import com.example.pierre.applicompanies.library_http.RequestParams;
import com.example.pierre.applicompanies.objectsPackage.Company;
import com.example.pierre.applicompanies.objectsPackage.Reduction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddReductionActivity extends Activity {
    private EditText et_name;
    private EditText et_description;
    private RadioGroup sexe_groupe;
    private RadioButton sexe_man;
    private RadioButton sexe_woman;
    private RadioButton sexe_both;
    private Spinner age_relation;
    private EditText et_age_value;
    private Spinner nb_points_relation;
    private EditText et_nb_points_value;
    private EditText et_city;
    Company company;
    Reduction reduction;

    int positionAge, positionNbPoints;
    String name, description, sexe, choosedAgeRelation, age_value_str, choosedNbPointsRelation, nb_points_value_str, city;
    Boolean man, woman, both;
    int age_value, nb_points_value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reduction);

        et_name = (EditText) findViewById(R.id.name);
        et_description = (EditText) findViewById(R.id.description);
        sexe_groupe = (RadioGroup) findViewById(R.id.sexe);
        sexe_man = (RadioButton) findViewById(R.id.man);
        sexe_woman = (RadioButton) findViewById(R.id.woman);
        sexe_both = (RadioButton) findViewById(R.id.both);
        et_age_value = (EditText) findViewById(R.id.age_value);
        et_nb_points_value = (EditText) findViewById(R.id.nb_points_value);
        et_city = (EditText) findViewById(R.id.city);

        sexe_groupe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.man) {
                    sexe_woman.setChecked(false);
                    sexe_both.setChecked(false);
                } else if (checkedId == R.id.woman) {
                    sexe_man.setChecked(false);
                    sexe_both.setChecked(false);
                } else if (checkedId == R.id.both) {
                    sexe_man.setChecked(false);
                    sexe_woman.setChecked(false);
                }
            }
        });

        addItemsOnSpinners();

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String logo = getIntent().getStringExtra("logo");
        String card = getIntent().getStringExtra("card");
        String up_date = getIntent().getStringExtra("up_date");
        company = new Company(id, name, logo, card);
        company.setUp_date(up_date);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinners() {
        age_relation = (Spinner) findViewById(R.id.age_relation);
        nb_points_relation = (Spinner) findViewById(R.id.nb_points_relation);
        List<String> list = new ArrayList<>();
        list.add(">");
        list.add("<");
        list.add("=");
        list.add("A");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_relation.setAdapter(dataAdapter);
        nb_points_relation.setAdapter(dataAdapter);
    }

    // Met les données aux formats voulus
    public void updateData() {
        if(man) { sexe = "M"; } else if(woman) { sexe = "W"; } else { sexe = "A"; }
        age_value = Integer.parseInt(age_value_str);
        nb_points_value = Integer.parseInt(nb_points_value_str);
    }

    // Vérifie que l'on a bien entré des données partout
    public Boolean checkData() {
        if(name.equals("")) { Toast.makeText(getApplicationContext(), "Please enter a reduction name.", Toast.LENGTH_LONG).show(); return false; }
        if(description.equals("")) { Toast.makeText(getApplicationContext(), "Please enter the reduction's description.", Toast.LENGTH_LONG).show(); return false; }
        if(!(man ^ woman ^ both)) { Toast.makeText(getApplicationContext(), "Please enter your sexe.", Toast.LENGTH_LONG).show(); return false; }
        if(age_value_str.equals("") && choosedAgeRelation.equals("A")) { Toast.makeText(getApplicationContext(), "Please enter an age relation.", Toast.LENGTH_LONG).show(); return false; }
        if(nb_points_value_str.equals("") && choosedNbPointsRelation.equals("A")) { Toast.makeText(getApplicationContext(), "Please enter a fidelity relation.", Toast.LENGTH_LONG).show(); return false; }
        if(city.equals("")) { Toast.makeText(getApplicationContext(), "Please enter your city.", Toast.LENGTH_LONG).show(); return false; }

        return true;
    }

    public String getRelation(int position) {
        if(position == 0) return "<";
        else if(position == 1) return ">";
        else if(position == 2) return "=";
        else return "A";
    }

    // Récupère les données entrées
    public void setValues() {
        name = et_name.getText().toString();
        description = et_description.getText().toString();
        man = sexe_man.isChecked();
        woman = sexe_woman.isChecked();
        both = sexe_both.isChecked();
        choosedAgeRelation = getRelation(age_relation.getSelectedItemPosition());
        age_value_str = et_age_value.getText().toString();
        choosedNbPointsRelation = getRelation(nb_points_relation.getSelectedItemPosition());
        nb_points_value_str = et_nb_points_value.getText().toString();
        city = et_city.getText().toString();
    }

    // Traite l'appui sur les boutons
    public void toDo(View v) {
        switch(v.getId()) {
            case R.id.btnSubmit:
            {
                setValues();
                if (!checkData()) { break; }
                updateData();

                reduction = new Reduction(-1, name, description, sexe, choosedAgeRelation, age_value, choosedNbPointsRelation, nb_points_value, city);

                addNewReductionOnline();

                break;
            }
            default: {}
        }
    }

    // Traduit la réponse du .php en ligne en tableau de string compréhensible
    public HashMap<String, String> translateResponse(String response) {
        String[] firstSep = response.split("\",\"");
        HashMap<String, String> map = new HashMap<>();
        String[] tmp;

        for(int i = 0 ; i < firstSep.length ; i++) {
            tmp = firstSep[i].split("\":\"");
            if(i == 0) tmp[0] = tmp[0].substring(3);
            if(i == firstSep.length-1) tmp[1] = tmp[1].substring(0, tmp[1].length()-3);
            map.put(tmp[0], tmp[1]);
        }

        return map;
    }

    public void addNewReductionOnline() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", reduction.getName());
        map.put("description", reduction.getDescription());
        map.put("sexe", reduction.getSexe());
        map.put("age_relation", reduction.getAge_relation());
        map.put("age_value", Integer.toString(reduction.getAge_value()));
        map.put("nb_points_relation", reduction.getNb_points_relation());
        map.put("nb_points_value", Integer.toString(reduction.getNb_points_value()));
        map.put("city", reduction.getCity());
        map.put("up_date", reduction.getUp_date());
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("addReductionJSON", gson.toJson(wordList));

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/addreduction.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                System.out.println("Check log online (php response) : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);

                    if(map.get("alreadyExists").equals("no")) {
                        if (map.get("works").equals("yes")) {
                            Toast.makeText(getApplicationContext(), "It works !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to recover data.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Reduction already exists !", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
