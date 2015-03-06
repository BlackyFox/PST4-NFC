package com.example.pierre.applicompanies;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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


public class SeeReductionsActivity extends ListActivity {

    CustomAdapter adapter;
    private List<RowItem> rowItems;
    Company company;
    Reduction[] reductions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_reductions);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String logo = getIntent().getStringExtra("logo");
        String card = getIntent().getStringExtra("card");
        String up_date = getIntent().getStringExtra("up_date");
        company = new Company(id, name, logo, card);
        company.setUp_date(up_date);

        getAllReductions();
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

    public void getAllReductions() {
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
                reductions = null;
                System.out.println("onsuccess");

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);
                    System.out.println("response : " + response);

                    if(map.get("has_reductions").equals("yes")) {
                        reductions = new Reduction[Integer.parseInt(map.get("nb_of_reductions"))];
                        Reduction tmpRed;
                        for(int i = 0 ; i < Integer.parseInt(map.get("nb_of_reductions")) ; i++) {
                            tmpRed = new Reduction(Integer.parseInt(map.get("reduction"+i+"_id")), map.get("reduction"+i+"_name"), map.get("reduction"+i+"_description"), map.get("reduction"+i+"_sexe"), map.get("reduction"+i+"_age_relation"), Integer.parseInt(map.get("reduction"+i+"_age_value")), map.get("reduction"+i+"_nb_points_relation"), Integer.parseInt(map.get("reduction"+i+"_nb_points_value")), map.get("reduction"+i+"_city"));
                            tmpRed.setUp_date(map.get("reduction"+i+"_up_date"));
                            reductions[i] = tmpRed;
                        }
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
                if (reductions != null) {
                    addItemsOnList();
                } else {
                    Toast.makeText(getApplicationContext(), "No reductions.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addItemsOnList() {
        rowItems = new ArrayList<>();

        for (int i = 0; i < reductions.length; i++) {
            RowItem items = new RowItem(reductions[i].getName(), reductions[i].getDescription(), reductions[i].getSexe(), reductions[i].getAge_relation(), reductions[i].getAge_value(), reductions[i].getNb_points_relation(), reductions[i].getNb_points_value(), reductions[i].getCity());
            rowItems.add(items);
        }

        adapter = new CustomAdapter(this, rowItems);
        setListAdapter(adapter);
    }
}