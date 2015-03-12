package com.example.pierre.applicompanies;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant l'ajout d'une carte par scan NFC.                                          */
/**************************************************************************************************/

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;


public class ScanPhysicalCardActivity extends Activity {

    private String msgReceived = null;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private final String TAG = this.getClass().getSimpleName();
    public static final String MIME_TEXT_PLAIN = "text/plain";
    ProgressDialog progress;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_physical);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        this.msg = "Scan the tag.";

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
    public void endActivity() {

        new AlertDialog.Builder(this).setMessage(this.msg).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ScanPhysicalCardActivity.this.finish();
            }
        }).show();
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
                        this.msg = "Carte ID : "+msgReceived;
                        Log.d("TAG", this.msgReceived);
                        endActivity();
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
                    this.msg = "Carte ID : "+msgReceived;
                    Log.d("TAG", this.msgReceived);
                    endActivity();
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
