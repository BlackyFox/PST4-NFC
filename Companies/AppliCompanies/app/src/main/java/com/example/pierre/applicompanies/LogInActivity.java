package com.example.pierre.applicompanies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;


public class LogInActivity extends Activity {
    public EditText editText_company_name = null;
    public EditText editText_password = null;
    public TextView textView_wrongText = null;

    Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_company_name = (EditText) findViewById(R.id.home_editText_company_name);
        editText_password = (EditText) findViewById(R.id.home_editText_password);
        textView_wrongText = (TextView) findViewById(R.id.home_textView_wrongText);
    }

    // Change d'activity, et écris les identifiants dans le fichier
    public void launchNewIntent() {
        Toast.makeText(getApplicationContext(), "You're connected as " + company.getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", Integer.toString(company.getId()));
        intent.putExtra("name", company.getName());
        intent.putExtra("logo", company.getLogo());
        intent.putExtra("card", company.getCard());
        intent.putExtra("up_date", company.getUp_date());
        startActivity(intent);
        this.finish();
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

    // Fonction réagissant à l'appui sur l'un des boutons (log, sign in)
    public void toDo(View v) {
        String company_name = editText_company_name.getText().toString();
        String password = editText_password.getText().toString();

        switch(v.getId()) {
            case R.id.home_button_logIn: // Bouton LOG
            {
                textView_wrongText.setText("");
                company = null;

                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();

                ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
                HashMap<String, String> map = new HashMap<>();
                map.put("company_name", company_name);
                map.put("password", password);
                wordList.add(map);

                Gson gson = new GsonBuilder().create();
                params.put("companyLogJSON", gson.toJson(wordList));

                // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
                client.post("http://www.pierre-ecarlat.com/newSql/checkcompanylog.php", params, new AsyncHttpResponseHandler() {
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

                            if (map.get("log").equals("yes")) {
                                company = new Company(Integer.parseInt(map.get("id")), map.get("name"), map.get("logo"), map.get("card"));
                                company.setUp_date(map.get("up_date"));
                            } else {
                                Toast.makeText(getApplicationContext(), "Connection refused.", Toast.LENGTH_LONG).show();
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
                        if (company != null) {
                            launchNewIntent();
                        } else {
                            Toast.makeText(getApplicationContext(), "Log failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

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
