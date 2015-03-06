package com.example.pierre.applicompanies;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pierre.applicompanies.library_http.AsyncHttpClient;
import com.example.pierre.applicompanies.library_http.AsyncHttpResponseHandler;
import com.example.pierre.applicompanies.library_http.RequestParams;
import com.example.pierre.applicompanies.objectsPackage.Client;
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

public class AddOfferActivity extends Activity {
    private Spinner spinner_reductions;
    private Button btnSubmit;
    Company company;
    String choosedReductionName;
    String[][] list_reductions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        System.out.println("addoffer");

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String logo = getIntent().getStringExtra("logo");
        String card = getIntent().getStringExtra("card");
        String up_date = getIntent().getStringExtra("up_date");
        company = new Company(id, name, logo, card);
        company.setUp_date(up_date);

        getReductionList();
        addListenerOnButton();
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

    public void getReductionList() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("company_id", Integer.toString(company.getId()));
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("getReductionsJSON", gson.toJson(wordList));

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/getreductions.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                list_reductions = null;

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                System.out.println("get all reductions online (php response) : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);
                    String[][] tmp_l = new String[2][Integer.parseInt(map.get("nb_of_reductions"))];

                    if (map.get("has_reductions").equals("yes")) {
                        for(int i = 0 ; i < Integer.parseInt(map.get("nb_of_reductions")) ; i++) {
                            tmp_l[0][i] = map.get("reduction"+i+"_id");
                            tmp_l[1][i] = map.get("reduction"+i+"_name");
                        }
                        list_reductions = tmp_l;
                    } else {
                        tmp_l[0][0] = "0";
                        tmp_l[1][0] = "No reductions";
                        list_reductions = tmp_l;
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

            @Override
            public void onFinish() {
                if (list_reductions != null) {
                    addItemsOnSpinnerReductions();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to recover data.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinnerReductions() {

        spinner_reductions = (Spinner) findViewById(R.id.spinner_reductions);
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < list_reductions[0].length ; i++) {
            list.add(list_reductions[0][i] + " " + list_reductions[1][i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reductions.setAdapter(dataAdapter);
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner_reductions = (Spinner) findViewById(R.id.spinner_reductions);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choosedReductionName = list_reductions[1][spinner_reductions.getSelectedItemPosition()];
                System.out.println("va lancer l'addOnline");
                if(!list_reductions[1][spinner_reductions.getSelectedItemPosition()].equals("No reductions"))
                    addOfferOnLine(Integer.parseInt(list_reductions[0][spinner_reductions.getSelectedItemPosition()]));
            }
        });
    }

    public void addOfferOnLine(int id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("company_id", Integer.toString(company.getId()));
        map.put("reduction_id", Integer.toString(id));
        map.put("up_date", Calendar.getInstance(TimeZone.getDefault()).getTime().toString());
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("addOfferJSON", gson.toJson(wordList));
        System.out.println("va lancer le post de addclient");

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/addoffer.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                System.out.println("onsuccess");

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
                            Toast.makeText(getApplicationContext(), "New offer inserted !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to recover data.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Offer already exists.", Toast.LENGTH_LONG).show();
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

            @Override
            public void onFinish() {
                addItemsOnSpinnerReductions();
            }
        });
    }
}
