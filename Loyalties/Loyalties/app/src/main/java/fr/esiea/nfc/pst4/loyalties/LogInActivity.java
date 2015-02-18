package fr.esiea.nfc.pst4.loyalties;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import bdd.*;
import library_http.*;
import objects.*;


public class LogInActivity extends ActionBarActivity {
    People people;

    public EditText editText_username = null;
    public EditText editText_password = null;
    public TextView textView_wrongText = null;

    File credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        String test = "[{\"log\":\"yes\",\"username\":\"pvgd\",\"password\":\"x\",\"name\":\"DBV\",\"first_name\":\"Fvv\",\"sexe\":\"M\",\"date_of_birth\":\"0006-06-04\",\"mail\":\"dghh@ggfg.fr\",\"city\":\"fgbv\",\"up_date\":\"Tue Feb 17 23:18:49 CET 2015\"}]";
        String[] firstSep = test.split("\",\"");
        HashMap<String, String> map = new HashMap<String, String>();
        String[] tmp;

        for(int i = 0 ; i < firstSep.length ; i++) {
            tmp = firstSep[i].split("\":\"");
            if(i == 0) tmp[0] = tmp[0].substring(3);
            if(i == firstSep.length-1) tmp[1] = tmp[1].substring(0, tmp[1].length()-3);
            map.put(tmp[0], tmp[1]);
        }

        System.out.println("OHOHOHOH " + map.get("name") + " AT DATE " + map.get("up_date") + " AND LOG IN : " + map.get("log"));









        credentials = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");

        editText_username = (EditText) findViewById(R.id.home_editText_username);
        editText_password = (EditText) findViewById(R.id.home_editText_password);
        textView_wrongText = (TextView) findViewById(R.id.home_textView_wrongText);


        //recupérer les identifiants dans le fichier et si possible écrire les données dans les editext
        //si on se déconnecte, on efface le fichier
        File f = getFileStreamPath("userCredentials.berzerk");
        if(f.length() != 0){
            int l = (int) credentials.length();
            byte[] data = new byte[l];

            try {
                FileInputStream in = new FileInputStream(credentials);
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String creds = new String(data);
//            StringTokenizer tok = new StringTokenizer(creds, "amoslexiii");
//
//            String username = tok.nextToken();
//            String pass = tok.nextToken();

            String[] temp = creds.split("amoslexiii");
            Log.d("TXT", creds);

            editText_username.setText(temp[0]);
            editText_password.setText(temp[1]);
        }
    }

    private void writeCreds(String username, String password){
        File f = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");
        if(f.length() > 0)
            f.delete();
        try{
            FileOutputStream os = new FileOutputStream(f);
            os.write(username.getBytes());
            os.write("amoslexiii".getBytes());
            os.write(password.getBytes());
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void launchNewIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", people.getUsername());
        startActivity(intent);
        writeCreds(people.getUsername(), people.getPassword());
        this.finish();
    }

    public void insertPeople(People people) {
        MyBDD bdd = new MyBDD(this);
        bdd.open();
        bdd.insertPeople(people);
        bdd.close();
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

    public void toDo(View v) {
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();

        Intent intent;

        switch(v.getId()) {
            case R.id.home_button_logIn:
            {
                textView_wrongText.setText("");

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(username.equals("")) { textView_wrongText.setText("No username."); break; }
                if(password.equals("")) { textView_wrongText.setText("No password."); break; }


                if(bdd.doesPeopleAlreadyExists(username) && bdd.getPeopleWithUsername(username).getPassword().equals(password)) {
                    people = bdd.getPeopleWithUsername(username);
                    Toast.makeText(getApplicationContext(), "Allowed locale connection at " + people.getUp_date(), Toast.LENGTH_LONG).show();
                    bdd.close();
                    launchNewIntent();
                } else {
                    bdd.close();
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();

                    ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("username", username);
                    map.put("password", password);
                    wordList.add(map);

                    Gson gson = new GsonBuilder().create();
                    params.put("logJSON", gson.toJson(wordList));

                    System.out.println(params);

                    client.post("http://www.pierre-ecarlat.com/newSql/checklogyourself.php", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
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

                                if (map.get("log").equals("yes")) {
                                    Toast.makeText(getApplicationContext(), "Allowed online connection at " + map.get("up_date"), Toast.LENGTH_LONG).show();
                                    people = new People(Integer.parseInt(map.get("id")), map.get("username"), map.get("password"), map.get("name"), map.get("first_name"), map.get("sexe"), map.get("date_of_birth"), map.get("mail"), map.get("city"));
                                    people.setUp_date(map.get("up_date"));
                                    insertPeople(people);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Online connection refused.", Toast.LENGTH_LONG).show();
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
                            if (people != null) {
                                Toast.makeText(getApplicationContext(), "Log as " + people.getName() + " " + people.getFirst_name() + ".", Toast.LENGTH_LONG).show();
                                launchNewIntent();
                            } else {
                                Toast.makeText(getApplicationContext(), "Log failed.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                bdd.close();

                break;
            }
            case R.id.home_button_signIn:
            {
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
            }
            default: {}
        }
    }


/** MENU PARTS ************************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
