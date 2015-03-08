package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant l'ajout d'une carte par scan NFC.                                          */
/**************************************************************************************************/

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

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

import databasePackage.MyBDD;
import library_http.AsyncHttpClient;
import library_http.AsyncHttpResponseHandler;
import library_http.RequestParams;
import objectsPackage.Client;
import objectsPackage.Company;


public class ScanActivity extends Activity {

    private String msgReceived = null;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private final String TAG = this.getClass().getSimpleName();
    public static final String MIME_TEXT_PLAIN = "text/plain";
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        try {
            resoudreIntent(getIntent());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    // Clôt l'activité en affichant la conclusion
    public void endActivity(final Boolean ok1, final Boolean ok2, final Client client, final Company company) {
        String conclusion;
        if(ok1) {
            MyBDD bdd = new MyBDD(this);
            bdd.open();
            bdd.removeAllOpportunities();
            bdd.updateOpportunities();
            bdd.close(); 
            conclusion = "Success !\n" + "\tYou just joined : " + company.getName();
            if(ok2) {
                conclusion += " and " + company.getName() + " is now in the database.";
            } else {
                conclusion += " and " + company.getName() + " was also in the database.";
            }
        } else {
            conclusion = "Failed...";
        }

        new AlertDialog.Builder(this).setMessage(conclusion).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(ok1)
                    ScanActivity.this.finish();
            }
        }).show();
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

    // Vérifie si le client existe bien (si le numéro de client correspond bien aux données) et insert le client
    public void getClient() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("serial_number", msgReceived);
        wordList.add(map);

        Gson gson = new GsonBuilder().create();

        params.put("getClientJSON", gson.toJson(wordList));

        System.out.println("-> params JSON envoyés : " + params);

        client.post("http://www.pierre-ecarlat.com/newSql/getclient.php", params, new AsyncHttpResponseHandler() {
            Boolean ok1 = false;
            Boolean ok2 = false;
            Client tmpClient;
            Company tmpCompany;

            @Override
            public void onStart() {
                progress = new ProgressDialog(ScanActivity.this);
                progress.setMessage("Checking for client...");
                progress.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = null;
                System.out.println("-> le post est dans onsuccess");

                try {
                    response = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println("Get client (php response) : " + response);
                try {
                    JSONArray arr = new JSONArray(response);
                    HashMap<String, String> map = translateResponse(response);

                    if (map.get("dataOk").equals("yes")) {
                        tmpClient = new Client(Integer.parseInt(map.get("id")), Integer.parseInt(map.get("id_peop")), Integer.parseInt(map.get("id_comp")), map.get("num_client"), Integer.parseInt(map.get("nb_loyalties")), Integer.parseInt(map.get("last_used")));
                        tmpClient.setUp_date(map.get("up_date"));

                        MyBDD bdd = new MyBDD(ScanActivity.this);
                        bdd.open();
                        if(bdd.doesClientAlreadyExists(tmpClient.getId())) {
                            Toast.makeText(getApplicationContext(), "Client already in database !", Toast.LENGTH_LONG).show();
                        } else {
                            bdd.insertClient(tmpClient);
                            ok1 = true;
                            // TODO : faire avec le download d'images + stockage dans resources
                            Toast.makeText(getApplicationContext(), "Insertion client ok", Toast.LENGTH_LONG).show();
                            tmpCompany = new Company(Integer.parseInt(map.get("company_id")), map.get("company_name"), map.get("company_logo"), map.get("company_card"));
                            tmpCompany.setUp_date(map.get("company_up_date"));
                            if(!bdd.doesCompanyAlreadyExists(tmpCompany.getName())) {
                                ImageLoadTask ilt_logo = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getLogo().toLowerCase() + ".png", tmpCompany.getLogo().toLowerCase() + ".png");
                                ImageLoadTask ilt_card = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/" + tmpCompany.getCard().toLowerCase() + ".png", tmpCompany.getCard().toLowerCase() + ".png");
                                ilt_logo.execute();
                                ilt_card.execute();

                                bdd.insertCompany(tmpCompany);
                                ok2 = true;
                            }
                        }
                        bdd.close();
                        progress.dismiss();
                    }
                    else if(map.get("dataOk").equals("no")) {
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
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
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

                endActivity(ok1, ok2, tmpClient, tmpCompany);
            }
        });
    }

    public void createImage(Bitmap image, String compPath) throws FileNotFoundException {
        Context context = getApplicationContext();
        String path = context.getFilesDir().getAbsolutePath();
        OutputStream stream = new FileOutputStream(path + "/" + compPath);
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
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

    private void resoudreIntent(Intent intent) throws UnsupportedEncodingException {

        String action = intent.getAction();
        //String message = null;
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){
            String type = intent.getType();
            if(MIME_TEXT_PLAIN.equals(type)){
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String Id = bytesToHexString(tag.getId());
                Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefMessage[] messages;
                if(rawMsgs != null){
                    messages = new NdefMessage[rawMsgs.length];
                    for(int i = 0; i < rawMsgs.length; i++){
                        messages[i] = (NdefMessage) rawMsgs[i];
                        // To get a NdefRecord and its different properties from a NdefMesssage
                        NdefRecord record = messages[i].getRecords()[i];
                        //byte[] type = record.getType();
                        String message = getTextData(record.getPayload());
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        this.msgReceived = message;
                        Log.d("TAG", this.msgReceived);
                        getClient();
                    }
                }else{
                    Log.d(TAG, "rawMsgs = null");
                }
            }else{
                Log.i(TAG, "Wrong mime type : "+type);
            }
        }else if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchTech = Ndef.class.getName();
            for(String tech : techList){
                if(searchTech.equals(tech)){
                    NFCReadAsyncTask n = new NFCReadAsyncTask();
                    n.execute(tag);
                    this.msgReceived = n.getMsg();
                    break;
                }
            }
        }
    }

    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder(/*"0x"*/);
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            //System.out.println(buffer);
            stringBuilder.append(buffer);
        }

        return stringBuilder.toString();
    }

    private String getTextData(byte[] payload) {
        if(payload == null)
            return null;
        try {
            String encoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
            int langageCodeLength = payload[0] & 0077;
            return new String(payload, langageCodeLength + 1, payload.length - langageCodeLength - 1, encoding);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        try {
            resoudreIntent(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
