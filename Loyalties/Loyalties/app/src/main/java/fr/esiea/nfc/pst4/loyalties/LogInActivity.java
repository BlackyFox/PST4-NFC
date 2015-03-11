package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité de lancement, permet de s'identifier ou identifie seule si des identifiants corrects  */
/* sont stockés en mémoire.                                                                       */
/**************************************************************************************************/

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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

import databasePackage.*;
import library_http.*;
import objectsPackage.*;


public class LogInActivity extends Activity {
    People people;

    public EditText editText_username = null;
    public EditText editText_password = null;
    public TextView textView_wrongText = null;
    public Button submit = null;

    File credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in2);

        editText_username = (EditText) findViewById(R.id.home_editText_username);
        editText_password = (EditText) findViewById(R.id.home_editText_password);
        textView_wrongText = (TextView) findViewById(R.id.home_textView_wrongText);
        submit = (Button) findViewById(R.id.home_button_logIn);

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

            editText_password.setOnEditorActionListener(new EditText.OnEditorActionListener(){
                @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        submit.performClick();
                        return true;
                    }
                    return false;
                }
            });

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
        intent.putExtra("name", people.getFirst_name());
        startActivity(intent);
        writeCreds(people.getUsername(), people.getPassword());
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

                                    if(map.get("has_clients").equals("yes")) {
                                        System.out.println("has clients ok : number " + map.get("has_clients_number"));
                                        for(int i = 0 ; i < Integer.parseInt(map.get("has_clients_number")) ; i++) {
                                            Client tmpClient = new Client(Integer.parseInt(map.get("client"+i+"_id")), Integer.parseInt(map.get("client"+i+"_new_id_peop")), Integer.parseInt(map.get("client"+i+"_new_id_comp")), map.get("client" + i + "_new_num_client"), Integer.parseInt(map.get("client"+i+"_new_nb_loyalties")), Integer.parseInt(map.get("client"+i+"_new_last_used")));
                                            tmpClient.setUp_date(map.get("client"+i+"_new_up_date"));
                                            if(bdd.doesClientAlreadyExists(Integer.parseInt(map.get("client"+i+"_id")))) {
                                                System.out.println("client i : " + i + ", existe déjà");
                                                if(!bdd.getClientWithId(Integer.parseInt(map.get("client"+i+"_id"))).getUp_date().equals(map.get("client"+i+"_new_up_date"))) {
                                                    System.out.println("client i : " + i + ", et on cherche à l'updater");
                                                    System.out.println("Client : " + tmpClient.getId() + ", " + tmpClient.getId_peop() + ", " + tmpClient.getId_comp() + ", " + tmpClient.getNum_client() + ", " + tmpClient.getNb_loyalties() + ", " + tmpClient.getLast_used() + ", " + tmpClient.getUp_date());
                                                    bdd.updateClient(Integer.parseInt(map.get("client" + i + "_id")), tmpClient);
                                                }
                                            } else {
                                                System.out.println("client i : " + i + ", n'existe pas, mais on va l'insérer profond bientôt");
                                                System.out.println("Client : " + tmpClient.getId() + ", " + tmpClient.getId_peop() + ", " + tmpClient.getId_comp() + ", " + tmpClient.getNum_client() + ", " + tmpClient.getNb_loyalties() + ", " + tmpClient.getLast_used() + ", " + tmpClient.getUp_date());
                                                bdd.insertClient(tmpClient);
                                            }

                                            Company tmpCompany = new Company(Integer.parseInt(map.get("client"+i+"_company_id")), map.get("client"+i+"_company_name"), map.get("client" + i + "_company_logo"), map.get("client" + i + "_company_card"));
                                            tmpCompany.setUp_date(map.get("client"+i+"_company_up_date"));
                                            if(bdd.doesCompanyAlreadyExists(map.get("client" + i + "_company_name"))) {
                                                System.out.println("la company de client i : " + i + " existe !");
                                                if(!bdd.getCompanyWithId(Integer.parseInt(map.get("client"+i+"_company_id"))).getUp_date().equals(map.get("client"+i+"_company_up_date"))) {
                                                    System.out.println("la company doit être updatée");
                                                    System.out.println("Company : " + tmpCompany.getId() + ", " + tmpCompany.getName() + ", " + tmpCompany.getLogo() + ", " + tmpCompany.getCard());
                                                    bdd.updateCompany(Integer.parseInt(map.get("client" + i + "_company_id")), tmpCompany);

                                                    Context context = getApplicationContext();
                                                    String path = context.getFilesDir().getAbsolutePath();
                                                    File file1 = new File(path + "/" + bdd.getCompanyWithId(tmpCompany.getId()).getLogo() + ".png");
                                                    File file2 = new File(path + "/" + bdd.getCompanyWithId(tmpCompany.getId()).getCard() + ".png");
                                                    file1.delete();
                                                    file2.delete();
                                                    System.out.println("suppression logo card");

                                                    ImageLoadTask ilt_logo = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getLogo().toLowerCase() + ".png", tmpCompany.getLogo().toLowerCase() + ".png");
                                                    ImageLoadTask ilt_card = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getCard().toLowerCase() + ".png", tmpCompany.getCard().toLowerCase() + ".png");
                                                    ilt_logo.execute();
                                                    ilt_card.execute();
                                                    System.out.println("ajout logo card");
                                                }
                                            } else {
                                                System.out.println("la company de client i : " + i + " n'existe pas ! On l'insert");
                                                System.out.println("Company : " + tmpCompany.getId() + ", " + tmpCompany.getName() + ", " + tmpCompany.getLogo() + ", " + tmpCompany.getCard());
                                                bdd.insertCompany(tmpCompany);
                                                ImageLoadTask ilt_logo = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getLogo().toLowerCase() + ".png", tmpCompany.getLogo().toLowerCase() + ".png");
                                                ImageLoadTask ilt_card = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getCard().toLowerCase() + ".png", tmpCompany.getCard().toLowerCase() + ".png");
                                                ilt_logo.execute();
                                                ilt_card.execute();
                                                System.out.println("ajout logo card");
                                            }

                                            if(map.get("client"+i+"_has_offers").equals("yes")) {
                                                System.out.println("les offers de client i : " + i + " existent, il y en a :" + map.get("client"+i+"_has_offers_number"));
                                                for (int j = 0 ; j < Integer.parseInt(map.get("client"+i+"_has_offers_number")) ; j++) {
                                                    System.out.println("offer j : " + j);
                                                    System.out.println("Offer : " + Integer.parseInt(map.get("client"+i+"_offer"+j+"_id")));
                                                    System.out.println("id comp : " + Integer.parseInt(map.get("client"+i+"_offer"+j+"_id_comp")));
                                                    System.out.println("id redu : " + Integer.parseInt(map.get("client"+i+"_offer"+j+"_id_redu")));
                                                    System.out.println("up date : " + map.get("client" + i + "_offer" + j + "_up_date"));
                                                    Offer tmpOffer = new Offer(Integer.parseInt(map.get("client"+i+"_offer"+j+"_id")), Integer.parseInt(map.get("client"+i+"_offer"+j+"_id_comp")), Integer.parseInt(map.get("client"+i+"_offer"+j+"_id_redu")));
                                                    tmpOffer.setUp_date(map.get("client" + i + "_offer" + j + "_up_date"));
                                                    if (bdd.doesOfferAlreadyExists(Integer.parseInt(map.get("client"+i+"_offer"+j+"_id")))) {
                                                        System.out.println("offer existe déjà : " + j);
                                                        if(!bdd.getOfferWithId(Integer.parseInt(map.get("client"+i+"_offer"+j+"_id"))).getUp_date().equals(map.get("client" + i + "_offer" + j + "_up_date"))) {
                                                            System.out.println("et on doit l'updater");
                                                            System.out.println("Offer : " + tmpOffer.getId() + ", " + tmpOffer.getId_comp() + ", " + tmpOffer.getId_redu() + ", " + tmpOffer.getUp_date());
                                                            bdd.updateOffer(Integer.parseInt(map.get("client" + i + "_offer" + j + "_id")), tmpOffer);
                                                        }
                                                    } else {
                                                        System.out.println("et on doit l'insérer");
                                                        System.out.println("Offer : " + tmpOffer.getId() + ", " + tmpOffer.getId_comp() + ", " + tmpOffer.getId_redu() + ", " + tmpOffer.getUp_date());
                                                        bdd.insertOffer(tmpOffer);
                                                    }

                                                    Reduction tmpReduction = new Reduction(Integer.parseInt(map.get("client"+i+"_offer"+j+"_reduction_id")), map.get("client"+i+"_offer"+j+"_reduction_name"), map.get("client"+i+"_offer"+j+"_reduction_description"), map.get("client"+i+"_offer"+j+"_reduction_sexe"), map.get("client"+i+"_offer"+j+"_reduction_age_relation"), Integer.parseInt(map.get("client"+i+"_offer"+j+"_reduction_age_value")), map.get("client"+i+"_offer"+j+"_reduction_nb_points_relation"), Integer.parseInt(map.get("client"+i+"_offer"+j+"_reduction_nb_points_value")), map.get("client"+i+"_offer"+j+"_reduction_city"));
                                                    tmpReduction.setUp_date(map.get("client"+i+"_offer"+j+"_reduction_up_date"));
                                                    if(bdd.doesReductionAlreadyExists(map.get("client"+i+"_offer"+j+"_reduction_name"))) {
                                                        System.out.println("réduction de l'offre j : " + j);
                                                        if(!bdd.getReductionWithId(Integer.parseInt(map.get("client"+i+"_offer"+j+"_reduction_id"))).getUp_date().equals(map.get("client"+i+"_offer"+j+"_reduction_up_date"))) {
                                                            System.out.println("et on doit l'updater");
                                                            System.out.println("Reduction : " + tmpReduction.getId() + ", " + tmpReduction.getName() + ", " + tmpReduction.getDescription() + "...");
                                                            bdd.updateReduction(Integer.parseInt(map.get("client" + i + "_offer" + j + "_reduction_id")), tmpReduction);
                                                        }
                                                    } else {
                                                        System.out.println("et on doit le créer");
                                                        System.out.println("Reduction : " + tmpReduction.getId() + ", " + tmpReduction.getName() + ", " + tmpReduction.getDescription() + "...");
                                                        bdd.insertReduction(tmpReduction);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    System.out.println("remove toutes les opportunities :");
                                    bdd.removeAllOpportunities();
                                    System.out.println("then, update");
                                    bdd.updateOpportunities();

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

    public void createImage(Bitmap image, String compPath) throws FileNotFoundException {
        Context context = getApplicationContext();
        int width =  image.getWidth();
        int height = image.getHeight();
        Bitmap dest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] pixels = new int[width * height];
        image.getPixels(pixels, 0, width, 0, 0, width, height);
        dest.setPixels(pixels, 0, width, 0, 0, width, height);
        String path = context.getFilesDir().getAbsolutePath();
        OutputStream stream = new FileOutputStream(path + "/" + compPath);
        dest.compress(Bitmap.CompressFormat.PNG, 80, stream);
    }

    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private String compPath;

        public ImageLoadTask(String url, String compPath) {
            this.url = url;
            this.compPath = compPath;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //after downloading
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            try {
                createImage(result, compPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
