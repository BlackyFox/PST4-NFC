package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Gestion des settings (MainActivity)                                                            */
/**************************************************************************************************/

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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import databasePackage.MyBDD;
import library_http.AsyncHttpClient;
import library_http.AsyncHttpResponseHandler;
import library_http.RequestParams;
import objectsPackage.Client;
import objectsPackage.Company;
import objectsPackage.Offer;
import objectsPackage.People;
import objectsPackage.Reduction;


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

    // Composition du JSON qu'on enverra au .php pour la synchronisation
    public String composeSyncJSON() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("current_people", "yes");
        map.put("current_people_id", Integer.toString(people.getId()));
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
                map.put("client_number" + i + "_id", Integer.toString(clients[i].getId()));
                map.put("client_number" + i + "_up_date", clients[i].getUp_date());
            }
        }

        Company[] companies = null;
        if(clients == null) {
            map.put("has_companies", "no");
        } else {
            companies = new Company[clients.length];
            map.put("has_companies", "yes");
            map.put("has_companies_number", Integer.toString(companies.length));
            for(int i = 0 ; i < companies.length ; i++) {
                companies[i] = bdd.getCompanyWithId(clients[i].getId_comp());
                map.put("company_number" + i + "_id", Integer.toString(companies[i].getId()));
                map.put("company_number" + i + "_up_date", companies[i].getUp_date());
            }
            bdd.removeAllOffers();
        }

        bdd.close();

        wordList.add(map);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(wordList);
    }

    // Traduit la réponse reçue par le .php
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

    // Fonction gérant la synchronisation
    private void synchronize() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("synchronizeJSON", composeSyncJSON());

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

                System.out.println("Check log online (php response) : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);

                    if(map.get("update_people_works").equals("yes") && map.get("has_to_update_people").equals("yes")) {
                        MyBDD bdd = new MyBDD(SettingsActivity.this);
                        bdd.open();
                        People tmpPeople = new People(people.getId(), map.get("people_new_username"), map.get("people_new_password"), map.get("people_new_name"), map.get("people_new_first_name"), map.get("people_new_sexe"), map.get("people_new_date_of_birth"), map.get("people_new_mail"), map.get("people_new_city"));
                        tmpPeople.setUp_date(map.get("people_new_up_date"));
                        bdd.updatePeople(people.getId(), tmpPeople);
                        bdd.close();
                    }

                    if(map.get("need_to_update_clients").equals("yes")) {
                        MyBDD bdd = new MyBDD(SettingsActivity.this);
                        bdd.open();
                        for(int i = 0 ; i < bdd.getAllClients(people.getUsername()).length ; i++) {
                            if(map.get("update_client" + i + "_works").equals("yes") && map.get("has_to_update_client" + i).equals("yes")) {
                                Client tmpClient = new Client(Integer.parseInt(map.get("client"+i+"_id")), Integer.parseInt(map.get("client"+i+"_new_id_peop")), Integer.parseInt(map.get("client"+i+"_new_id_comp")), map.get("client" + i + "_new_num_client"), Integer.parseInt(map.get("client"+i+"_new_nb_loyalties")), Integer.parseInt(map.get("client"+i+"_new_last_used")));
                                tmpClient.setUp_date(map.get("client"+i+"_new_up_date"));
                                bdd.updateClient(Integer.parseInt(map.get("client"+i+"_id")), tmpClient);
                            }
                        }
                        bdd.close();
                    }

                    if(map.get("need_to_update_companies").equals("yes")) {
                        MyBDD bdd = new MyBDD(SettingsActivity.this);
                        bdd.open();
                        for(int i = 0 ; i < bdd.getAllClients(people.getUsername()).length ; i++) {
                            if(map.get("update_company" + i + "_works").equals("yes") && map.get("has_to_update_company" + i).equals("yes")) {
                                Company tmpCompany = new Company(Integer.parseInt(map.get("company"+i+"_id")), map.get("company"+i+"_new_name"), map.get("company"+i+"_new_logo"), map.get("company" + i + "_new_card"));
                                tmpCompany.setUp_date(map.get("company"+i+"_new_up_date"));
                                bdd.updateCompany(Integer.parseInt(map.get("company"+i+"_id")), tmpCompany);
                            }
                            if(map.get("update_company"+i+"_offers_works").equals("yes")) {
                                for(int j = 0 ; j < Integer.parseInt(map.get("company"+i+"_nb_offers")) ; j++) {
                                    Offer tmpOffer = new Offer(Integer.parseInt(map.get("company"+i+"_offer"+j+"_id")), Integer.parseInt(map.get("company"+i+"_offer"+j+"_id_comp")), Integer.parseInt(map.get("company"+i+"_offer"+j+"_id_redu")));
                                    tmpOffer.setUp_date(map.get("company"+i+"_offer"+j+"_up_date"));
                                    bdd.insertOffer(tmpOffer);
                                }
                            }
                        }
                        bdd.close();
                    }

                    MyBDD bdd = new MyBDD(SettingsActivity.this);
                    bdd.open();
                    bdd.removeAllReductions();
                    for(int i = 0 ; i < Integer.parseInt(map.get("nb_reductions")) ; i++) {
                        if(map.get("reduction"+i+"_works").equals("yes")) {
                            Reduction reduction = new Reduction(Integer.parseInt(map.get("reduction"+i+"_id")), map.get("reduction"+i+"_name"), map.get("reduction"+i+"_description"), map.get("reduction"+i+"_sexe"), map.get("reduction"+i+"_age_relation"), Integer.parseInt(map.get("reduction"+i+"_age_value")), map.get("reduction"+i+"_nb_points_relation"), Integer.parseInt(map.get("reduction"+i+"_nb_points_value")), map.get("reduction"+i+"_city"));
                            reduction.setUp_date(map.get("reduction" + i + "_up_date"));
                            if(!bdd.doesReductionAlreadyExists(reduction.getName()))
                                bdd.insertReduction(reduction);
                        }
                    }
                    bdd.removeAllOpportunities();
                    bdd.updateOpportunities();

                    bdd.close();

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
    }

    // Fonction gérant la suppression d'un utilisateur (warn, pas uniquement removePeople)
    private void deleteUser() {
        //TODO
    }

    // Fonction effaçant le contenu du fichier avec les cookies
    private void deleteCreds() {
        File f = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");
        if(f.length() > 0)
            f.delete();
        Toast.makeText(getApplicationContext(), "Data deleted", Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
        this.finish();
    }


/** PARAMETRES GLOBAUX ****************************************************************************/
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
