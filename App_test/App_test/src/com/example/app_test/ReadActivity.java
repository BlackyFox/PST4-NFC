package com.example.app_test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ReadActivity extends Activity {
	
	private TextView t1 = null;
	private TextView t2 = null;
	private TextView t3 = null;
	private NfcAdapter nfc;
	private PendingIntent mPendingIntent;
	
	public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.read_activity);
	    
        t1 = (TextView) findViewById(R.id.r_read);
        t1.setText("None");
        t2 = (TextView) findViewById(R.id.nfc_message);
        t3 = (TextView) findViewById(R.id.id_nfc);
        
        handleIntent(getIntent());
        nfc = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}

	
	private void handleIntent(Intent intent) {
	    String action = intent.getAction();
	    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	         
	        String type = intent.getType();
	        if (MIME_TEXT_PLAIN.equals(type)) {
	 
	            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	            new NdefReaderTask().execute(tag);
	             
	        } else {
	            Log.d(TAG, "Wrong mime type: " + type);
	        }
	    } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
	        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	        String[] techList = tag.getTechList();
	        String searchedTech = Ndef.class.getName();
	         
	        for (String tech : techList) {
	            if (searchedTech.equals(tech)) {
	                new NdefReaderTask().execute(tag);
	                break;
	            }
	        }
	    }
	}
	
	private class NdefReaderTask extends AsyncTask<Tag, Void, String> {
	 
	    @Override
	    protected String doInBackground(Tag... params) {
	        Tag tag = params[0];
	         
	        Ndef ndef = Ndef.get(tag);
	        if (ndef == null) {
	            return null;
	        }
	 
	        NdefMessage ndefMessage = ndef.getCachedNdefMessage();
	 
	        NdefRecord[] records = ndefMessage.getRecords();
	        for (NdefRecord ndefRecord : records) {
	            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
	                try {
	                    return readText(ndefRecord);
	                } catch (UnsupportedEncodingException e) {
	                	e.printStackTrace();
	                    Log.e(TAG, "Unsupported Encoding", e);
	                }
	            }
	        }
	 
	        return null;
	    }
	     
	    private String readText(NdefRecord record) throws UnsupportedEncodingException {
	 
	        byte[] payload = record.getPayload();
	 
	        // Get the Text Encoding
	        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
	 
	        // Get the Language Code
	        int languageCodeLength = payload[0] & 0063;
	         
	        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
	        // e.g. "en"
	         
	        // Get the Text
	        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
	    }
	     
	    @Override
	    protected void onPostExecute(String result) {
	        if (result != null) {
	            t2.setText("Read content: " + result);
	        }
	    }
	}
	
	@Override
    protected void onResume() {
    	super.onResume();
    	nfc.enableForegroundDispatch(this, mPendingIntent, null, null);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	nfc.disableForegroundDispatch(this);
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    	handleIntent(getIntent());
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
   	
    	Intent intent = null;
    	
    	switch(item.getItemId()) {
	    	case R.id.imc_page:
	    	{
	    		intent = new Intent(this, IMCActivity.class);
	        	startActivity(intent);
	        	break;
	    	}
	    	case R.id.nfc_page:
	    	{
	    		intent = new Intent(this, MainActivity.class);
	        	startActivity(intent);
	        	break;
	    	}
	    	case R.id.action_settings:
	    	{
	    		intent = new Intent(this, MainActivity.class);
	        	startActivity(intent);
	        	break;
	    	}
	    	case R.id.home_page:
	    	{
	    		startActivity(new Intent(this, MainActivity.class));
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
