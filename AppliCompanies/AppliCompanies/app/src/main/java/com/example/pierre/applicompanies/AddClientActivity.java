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

public class AddClientActivity extends Activity {
    private Spinner spinner_people;
    private Button btnSubmit;
    Company company;
    String choosedUsername;
    String[][] list_people;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String logo = getIntent().getStringExtra("logo");
        String card = getIntent().getStringExtra("card");
        String up_date = getIntent().getStringExtra("up_date");
        company = new Company(id, name, logo, card);
        company.setUp_date(up_date);

        getPeopleList();
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

    public String[][] getPeopleList() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("company_name", company.getName());
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("getPeopleJSON", gson.toJson(wordList));

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/getpeople.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                list_people = null;

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                System.out.println("Check log online (php response) : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);
                    String[][] tmp_l = new String[4][Integer.parseInt(map.get("nb_of_people"))];

                    if (map.get("works").equals("yes")) {
                        for(int i = 0 ; i < Integer.parseInt(map.get("nb_of_people")) ; i++) {
                            tmp_l[0][i] = map.get("people"+i+"_username");
                            tmp_l[1][i] = map.get("people"+i+"_name");
                            tmp_l[2][i] = map.get("people"+i+"_first_name");
                            tmp_l[3][i] = map.get("people"+i+"_id");
                        }
                        list_people = tmp_l;
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to recover data.", Toast.LENGTH_LONG).show();
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
                if (list_people != null) {
                    addItemsOnSpinnerPeople();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to recover data.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return null;
    }

    // add items into spinner dynamically
    public void addItemsOnSpinnerPeople() {

        spinner_people = (Spinner) findViewById(R.id.spinner_people);
        List<String> list = new ArrayList<>();
        for(int i = 0 ; i < list_people[0].length ; i++) {
            list.add(list_people[1][i] + " " + list_people[2][i] + " - " + list_people[0][i]);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_people.setAdapter(dataAdapter);
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner_people = (Spinner) findViewById(R.id.spinner_people);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choosedUsername = list_people[0][spinner_people.getSelectedItemPosition()];
                System.out.println("va lancer l'addOnline");
                addClientOnLine(Integer.parseInt(list_people[3][spinner_people.getSelectedItemPosition()]), list_people[1][spinner_people.getSelectedItemPosition()], list_people[2][spinner_people.getSelectedItemPosition()]);
            }
        });
    }

    public void addClientOnLine(int id, String name, String first_name) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        String name_str = Integer.toString(Character.codePointAt(name, 0));
        String first_name_str = Integer.toString(Character.codePointAt(first_name, 0));

        String num_client = name_str + "" + first_name_str;

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("people_id", Integer.toString(id));
        map.put("company_id", Integer.toString(company.getId()));
        map.put("num_client", num_client);
        map.put("up_date", Calendar.getInstance(TimeZone.getDefault()).getTime().toString());
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("addClientJSON", gson.toJson(wordList));
        System.out.println("va lancer le post de addclient");

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/addclient.php", params, new AsyncHttpResponseHandler() {
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
                            Toast.makeText(getApplicationContext(), "New client inserted !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to recover data.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Client already exists.", Toast.LENGTH_LONG).show();
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
                addItemsOnSpinnerPeople();
            }
        });
    }
}
