package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité de lancement, permet de s'identifier ou identifie seule si des identifiants corrects  */
/* sont stockés en mémoire.                                                                       */
/**************************************************************************************************/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import databasePackage.*;
import library_http.*;
import objectsPackage.*;


public class LogInActivity extends Activity {
    People people;

    public EditText editText_username = null;
    public EditText editText_password = null;
    public TextView textView_wrongText = null;

    File credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editText_username = (EditText) findViewById(R.id.home_editText_username);
        editText_password = (EditText) findViewById(R.id.home_editText_password);
        textView_wrongText = (TextView) findViewById(R.id.home_textView_wrongText);

        // On récupère les identifiants dans le fichier (s'ils existent)
        credentials = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");
        File f = getFileStreamPath("userCredentials.berzerk");
        if(f.length() != 0) {
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

            String[] temp = creds.split("amoslexiii");
            editText_username.setText(temp[0]);
            editText_password.setText(temp[1]);

            // TODO : éventuellement, connecter automatiquement si le fichier est valide
        }
    }

    // Ecriture dans le fichier, sous le format 'username'amoslexiii'password'
    private void writeCreds(String username, String password) {
        File f = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/userCredentials.berzerk");
        if(f.length() > 0)
            f.delete();
        try {
            FileOutputStream os = new FileOutputStream(f);
            os.write(username.getBytes());
            os.write("amoslexiii".getBytes());
            os.write(password.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Change d'activity, et écris les identifiants dans le fichier
    public void launchNewIntent() {
        Toast.makeText(getApplicationContext(), "You're connected as " + people.getFirst_name() + " " + people.getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", Integer.toString(people.getId()));
        startActivity(intent);
        writeCreds(people.getUsername(), people.getPassword());
        this.finish();
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

    // Fonction réagissant à l'appui sur l'un des boutons (log, sign in)
    public void toDo(View v) {
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();

        Intent intent;

        switch(v.getId()) {
            case R.id.home_button_logIn: // Bouton LOG
            {
                textView_wrongText.setText("");

                MyBDD bdd = new MyBDD(this);
                bdd.open();

                if(username.equals("")) { textView_wrongText.setText("No username."); break; }
                if(password.equals("")) { textView_wrongText.setText("No password."); break; }


                if(bdd.doesPeopleAlreadyExists(username) && bdd.getPeopleWithUsername(username).getPassword().equals(password)) {
                    people = bdd.getPeopleWithUsername(username);
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

                    // On envoie username / password pour vérifier si lesidentifiants sont bons selon la bdd en ligne
                    client.post("http://www.pierre-ecarlat.com/newSql/checklogyourself.php", params, new AsyncHttpResponseHandler() {
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
                                    people = new People(Integer.parseInt(map.get("id")), map.get("username"), map.get("password"), map.get("name"), map.get("first_name"), map.get("sexe"), map.get("date_of_birth"), map.get("mail"), map.get("city"));
                                    people.setUp_date(map.get("up_date"));
                                    MyBDD bdd = new MyBDD(LogInActivity.this);
                                    bdd.open();
                                    bdd.insertPeople(people);
                                    bdd.close();
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
            case R.id.home_button_signIn: // SIGN IN
            {
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
            }
            default: {}
        }
    }
}
