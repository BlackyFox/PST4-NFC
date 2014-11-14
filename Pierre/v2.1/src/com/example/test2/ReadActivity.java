package com.example.test2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ReadActivity extends Activity {
	private TextView t1 = null;
	private TextView t2 = null;
	private LinearLayout ll1 = null;
	private int compt = 0;

	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage = null; //Données NFC, contient plusieurs NDEFRecords
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        t1 = (TextView) findViewById(R.id.estate);
        t2 = (TextView) findViewById(R.id.read_results);
        ll1 = (LinearLayout) findViewById(R.id.read_results2);
        
        resolveIntent(getIntent());

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }
    
    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
        	
        	/***********************************************************/
        	/** Carte bleue :		Type TAG ***************************/ 
        	/** Carte étudiante :	************************************/
        	/** Passeport :			************************************/
        	/** Oyster Card :		************************************/
        	/***********************************************************/
        	
        	compt++;
        	t1.setText("OK ; TAG N°" + compt);
        	
        	

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            
            if(rawMsgs != null) {
            	msgs = new NdefMessage[rawMsgs.length];
            	for (int i = 0; i < rawMsgs.length; i++) {
                	msgs[i] = (NdefMessage) rawMsgs[i];
            	}
            	
            	TextView tmp = null;
            	for(int i = 0 ; i < rawMsgs.length ; i++) {
            		tmp.setText(i+1 + " : " + msgs[i].toString());
            		ll1.addView(tmp);
            	}
            	
            	/***********************************/
            	/** A tester, (= null) pour la CB **/
            	/***********************************/
            	
            } else {
            	Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            	t2.setText("Type :\n\t\tTag : " + NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
            					+ "\n\t\tTech : " + NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
            					+ "\n\t\tNDEF : " + NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
            				+ "\n\n"
            				+ "Caractéristiques"
            					+ "\n\t\tContents : " + tag.describeContents()
            					+ "\n\t\tID : " + tag.getId()
            					+ "\n\t\tTechList : " + tag.getTechList()
            					+ "\n\t\tInfos : " + tag.toString());
            }
        } else {
          	t1.setText("Mettez un tag NFC contre le dos de votre appareil.");
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

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }


/*****************************   M E N U   *****************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}