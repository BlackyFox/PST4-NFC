package fr.esiea.nfc.pst4.loyalties;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import bdd.MyBDD;
import library_http.AsyncHttpClient;
import library_http.AsyncHttpResponseHandler;
import library_http.RequestParams;
import objects.Client;
import objects.People;

public class SettingsActivity extends PreferenceActivity {
    private static final boolean ALWAYS_SIMPLE_PREFS = false;
    ProgressDialog progress;
    People people;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        MyBDD bdd = new MyBDD(this);
        bdd.open();
        people = bdd.getPeopleWithId(id);
        System.out.println("Settings with : " + people.getUsername() + ", signed in " + people.getUp_date());
        bdd.close();

        setupSimplePreferencesScreen();

        Preference logOut = findPreference("LogOut");
        logOut.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                deleteCreds();
                deleteUser();
                finish();
                return true;
            }
        });

        Preference sync = findPreference("Sync");
        sync.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                synchronize();
                return true;
            }
        });
    }

    public String composeSyncJSON() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("current_people", "yes"); // If Yes, on regarde si user est à jour, on envoie clé (username) et up_date
            map.put("current_people_id", Integer.toString(people.getId()));
        System.out.println("ICIIIIIIIIIIIIIIIIIIIIIIIIIIIIII : " + people.getId());
            map.put("current_people_up_date", people.getUp_date());

        MyBDD bdd = new MyBDD(this);
        bdd.open();
        Client[] clients = bdd.getAllClients(people.getUsername());
        if(clients == null) {
            map.put("has_clients", "no");
        } else {
            map.put("has_clients", "yes");
            map.put("has_clients_number", Integer.toString(clients.length));
            for(int i = 0 ; i < clients.length ; i++) {
                map.put("has_clients_number" + i + "_id_peop", Integer.toString(clients[i].getId_peop()));
                map.put("has_clients_number" + i + "_id_comp", Integer.toString(clients[i].getId_comp()));
                map.put("has_clients_number" + i + "_up_date", clients[i].getUp_date());
            }
        }
        bdd.close();

        wordList.add(map);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
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

    private void synchronize() {
        System.out.println("TRIED TO UPLOAD " + people.getUsername() + ", OLD DATE : " + people.getUp_date());

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("synchronizeJSON", composeSyncJSON());
        System.out.println(params);

        client.post("http://www.pierre-ecarlat.com/newSql/synchronize.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progress = new ProgressDialog(SettingsActivity.this);
                progress.setMessage("Synchronization, please wait...");
                progress.show();
            }

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
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

                    if(!map.get("works").equals("no") && map.get("has_to_update_people").equals("yes")) {
                        MyBDD bdd = new MyBDD(SettingsActivity.this);
                        bdd.open();
                        People tmpPeople = new People(people.getId(), map.get("people_new_username"), map.get("people_new_password"), map.get("people_new_name"), map.get("people_new_first_name"), map.get("people_new_sexe"), map.get("people_new_date_of_birth"), map.get("people_new_mail"), map.get("people_new_city"));
                        tmpPeople.setUp_date(map.get("people_new_up_date"));
                        bdd.updatePeople(people.getId(), tmpPeople);
                        bdd.close();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
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
                finish();
            }
        });



        // Envoi people : si people(username) comme people(username) online, user:ok
        // sinon, user:no, username:blabla, password:blablabla, ...

        // Get Id people(username) online
        // Get all clients where id =
        // clients:ko
        // sinon clients:ok, idcomp:id, ..

        /*
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("synchronizeJSON", people.composePeopleJSONfromSQLite());

        System.out.println(params);

        client.post("http://www.pierre-ecarlat.com/newSql/insertpeople.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                progress = new ProgressDialog(SignInActivity.this);
                progress.setMessage("Add people online and on phone...");
                progress.show();
            }

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {
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
                    if (getExistsStatus(response)) {
                        Toast.makeText(getApplicationContext(), "User already exists !", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                    else if(!getWorksStatus(response)) {
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
            public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] responseBody, Throwable error) {
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

                endActivity();
            }
        });*/

    }

    private void deleteUser() {
        //TODO
    }

    private void deleteCreds() {
        File f = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");
        if(f.length() > 0)
            f.delete();
        Toast.makeText(getApplicationContext(), "Data deleted", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
        this.finish();
    }

    private void setupSimplePreferencesScreen() {
        if (!isSimplePreferences(this)) {
            return;
        }

        addPreferencesFromResource(R.xml.pref_app);
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this) && !isSimplePreferences(this);
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private static boolean isSimplePreferences(Context context) {
        return ALWAYS_SIMPLE_PREFS
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB
                || !isXLargeTablet(context);
    }
}
