package com.example.pierre.applicompanies;

import android.app.Activity;
import android.app.ListActivity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pierre.applicompanies.library_http.AsyncHttpClient;
import com.example.pierre.applicompanies.library_http.AsyncHttpResponseHandler;
import com.example.pierre.applicompanies.library_http.RequestParams;
import com.example.pierre.applicompanies.objectsPackage.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Created by Pierre on 06/03/2015.
 */
public class RemoveOfferActivity extends ListActivity {

    OfferCustomAdapter adapter;
    private List<OfferRowItem> offerRowItems;
    Company company;
    String[][] offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_offers);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        String name = getIntent().getStringExtra("name");
        String logo = getIntent().getStringExtra("logo");
        String card = getIntent().getStringExtra("card");
        String up_date = getIntent().getStringExtra("up_date");
        company = new Company(id, name, logo, card);
        company.setUp_date(up_date);

        getAllOffers();
    }

    // Traduit une chaîne de caractère au format JSON en format utf8_unicode
    public static String decodeJSONString(String s) {
        String decodeS = null;
        for(int i = 0 ; i < s.length() ; i++) {
            if(s.charAt(i) == '\\' && s.charAt(i+1) == 'u') {
                String key = s.substring(i, i+6);
                Properties p = new Properties();
                try {
                    p.load(new StringReader("key="+key));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(decodeS != null) {
                    decodeS = decodeS.concat(p.getProperty("key"));
                } else {
                    decodeS = p.getProperty("key");
                }
                i += 5;
            } else {
                if(decodeS != null) {
                    decodeS = decodeS.concat(Character.toString(s.charAt(i)));
                } else {
                    decodeS = Character.toString(s.charAt(i));
                }
            }
        }

        return decodeS;
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
            map.put(tmp[0], decodeJSONString(tmp[1]));
        }

        return map;
    }

    public void getAllOffers() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("company_id", Integer.toString(company.getId()));
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("getOffersJSON", gson.toJson(wordList));

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/getoffers.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                offers = null;
                System.out.println("onsuccess");

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                try {
                    JSONArray arr = new JSONArray(response);
                    System.out.println("response : " + response);
                    HashMap<String, String> map = translateResponse(response);

                    if(map.get("has_offers").equals("yes")) {
                        offers = new String[2][Integer.parseInt(map.get("nb_of_offers"))];
                        for(int i = 0 ; i < Integer.parseInt(map.get("nb_of_offers")) ; i++) {
                            offers[0][i] = map.get("offer"+i+"_id");
                            offers[1][i] = map.get("offer"+i+"_redu_name");
                        }
                    } else {
                        offers[1][0] = map.get("No offers.");
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
                if (offers != null) {
                    addItemsOnList();
                } else {
                    Toast.makeText(getApplicationContext(), "No offers.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addItemsOnList() {
        offerRowItems = new ArrayList<>();

        for (int i = 0; i < offers[0].length; i++) {
            OfferRowItem items = new OfferRowItem(offers[1][i], i);
            offerRowItems.add(items);
        }

        adapter = new OfferCustomAdapter(this, offerRowItems);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView parent, View view, int position, long id) {
        if (!offers[1][position].equals("No offers.")) {
            removeOffer(offers[0][position]);
            this.finish();
        }
    }

    public void removeOffer(String id_offer) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        System.out.println("ici -> " + id_offer);

        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();
        map.put("id_offer", id_offer);
        wordList.add(map);

        Gson gson = new GsonBuilder().create();
        params.put("removeOfferJSON", gson.toJson(wordList));

        // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
        client.post("http://www.pierre-ecarlat.com/newSql/removeoffer.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                offers = null;
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

                    if (map.get("works").equals("yes")) {
                        Toast.makeText(getApplicationContext(), "Works !", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Doesn't works", Toast.LENGTH_LONG).show();
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
