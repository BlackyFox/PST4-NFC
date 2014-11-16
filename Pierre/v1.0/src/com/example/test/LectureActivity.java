package com.example.test;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LectureActivity extends Activity {
	private TextView t1 = null;
	private TextView t2 = null;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        t1 = (TextView) findViewById(R.id.lecture_ou_non);
        t2 = (TextView) findViewById(R.id.comp);
		t1.setText("NONE");
		
        try {
			resoudreIntent(getIntent());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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
    
    private void resoudreIntent(Intent intent) throws UnsupportedEncodingException {
    	
   		String action = intent.getAction();
   		String message = null;
   		
   		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
   				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
   				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
   			
   			t1.setText("OK");
   			Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
   			byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
   			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
   			NdefMessage[] messages;
   			if (rawMsgs != null) {
   			    messages = new NdefMessage[rawMsgs.length];
   			    for (int i = 0; i < rawMsgs.length; i++) {
   			        messages[i] = (NdefMessage) rawMsgs[i];
   		   			NdefRecord record = messages[i].getRecords()[i];
   		   			byte[] id1 = record.getId();
   		   			short tnf = record.getTnf();
   		   			byte[] type = record.getType();
   		   			message = getTextData(record.getPayload());
   			    }
   			}
   			
   			t2.setText("COUCOU" + message);
   		}
   		else
   			t1.setText("NONE");
    }
    
    String getTextData(byte[] payload) throws UnsupportedEncodingException {
        String texteCode = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
        int langageCodeTaille = payload[0] & 0077;
        return new String(payload, langageCodeTaille + 1, payload.length - langageCodeTaille - 1, texteCode);
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    	try {
			resoudreIntent(intent);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

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
    	
    	Intent intent = null;
    	
    	switch(item.getItemId()) {
	    	case R.id.accueil:
	    	{
	    		intent = new Intent(this, MainActivity.class);
	        	startActivity(intent);
	        	break;
	    	}
	    	case R.id.pagenfc:
	    	{
	    		intent = new Intent(this, NFCActivity.class);
	        	startActivity(intent);
	        	break;
	    	}
	    	case R.id.action_settings:
	    	{
	    		intent = new Intent(this, SettingsActivity.class);
	        	startActivity(intent);
	        	break;
	    	}
    	}
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}