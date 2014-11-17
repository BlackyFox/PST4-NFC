package com.example.app_test;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
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
import android.view.MenuItem;
import android.widget.TextView;

public class ReadActivity extends Activity {
	
	public static final String TAG = "App_test";
    public static final String MIME_TEXT_PLAIN = "text/plain";
	
    private TextView t0 = null;
	private TextView t1 = null;
	private TextView t2 = null;
	private TextView t3 = null;
	private TextView t4 = null;
	private TextView t5 = null;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_activity);
        Log.d(TAG, "Starting app");
        t0 = (TextView) findViewById(R.id.r_nfc_type);
        t1 = (TextView) findViewById(R.id.r_read);
        t2 = (TextView) findViewById(R.id.nfc_message);
        t3 = (TextView) findViewById(R.id.id_nfc_hex);
        t4 = (TextView) findViewById(R.id.id_nfc_dec);
        t5 = (TextView) findViewById(R.id.id_nfc_bytes);
		t1.setText("NONE");
		
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
    
    private void resoudreIntent(Intent intent) throws UnsupportedEncodingException {
    	
   		String action = intent.getAction();
   		//String message = null;
   		if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
   			t1.setText("OK NDEF");
   			String type = intent.getType();
   			t0.setText(type);
   			if(MIME_TEXT_PLAIN.equals(type)){
   				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
   				Log.d(TAG, "TAG ID = "+getTextData(tag.getId()));
   	   			t3.setText(bytesToHexString(tag.getId()));
   				new NFCReadActivity().execute(tag);
   			}else{
   				Log.i(TAG, "Wrong mime type : "+type);
   			}
   		}else if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){
   			t1.setText("OK TAG");
   			t0.setText("TAG");
   			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
   			Log.d(TAG, "TAG ID = "+getTextData(tag.getId()));
   			String Id = bytesToHexString(tag.getId());
   			t3.setText(Id);
   			Log.d(TAG, Id);
   			t4.setText((new BigInteger(Id, 16)).toString());
   			t5.setText(tag.getId().toString());
   			//Log.d(TAG, Integer.toString(Integer.parseInt(Id, 16)));
   			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
   			NdefMessage[] messages;
   			if(rawMsgs != null){
   				messages = new NdefMessage[rawMsgs.length];
   				Log.d(TAG, "l = "+rawMsgs.length);
   			    for(int i = 0; i < rawMsgs.length; i++){
   			    	messages[i] = (NdefMessage) rawMsgs[i];     
   			        // To get a NdefRecord and its different properties from a NdefMesssage
   			        NdefRecord record = messages[i].getRecords()[i];
   			        byte[] id = record.getId();
   			        short tnf = record.getTnf();
   			        byte[] type = record.getType();
   			        String message = getTextData(record.getPayload());
   			        Log.d(TAG, "message = "+message);
   			        t2.setText(message);
   			        t3.setText(id.toString());
   			    }
   			}else{
   				t2.setText("Hum... Message vide ?");
   				//t3.setText("Si c'est vide, t'as pas d'ID !");
   				Log.d(TAG, "rawMsgs = null");
   			}
   		}else if(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)){
   			t1.setText("OK TECH");
   			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
   			Log.d(TAG, "TAG ID = "+getTextData(tag.getId()));
   			t3.setText(bytesToHexString(tag.getId()));
   			String[] techList = tag.getTechList();
   			String searchTech = Ndef.class.getName();
   			for(String tech : techList){
   				if(searchTech.equals(tech)){
   					new NFCReadActivity().execute(tag);
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
   		
   		/*if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
   				|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
   				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
   			
   			t1.setText("OK");
   			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
   			NdefMessage[] messages = null;
   			if (rawMsgs != null) {

   			}
   			message = "Card read";
   			Log.i(TAG, "New Intent/3:" + message);
   			t2.setText(message);
   		}
   		else
   			t1.setText("NONE");
    }
    
   String getTextData(byte[] payload) throws UnsupportedEncodingException {
        String texteCode = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
        int langageCodeTaille = payload[0] & 0077;
        return new String(payload, langageCodeTaille + 1, payload.length - langageCodeTaille - 1, texteCode);
    }*/
    
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


    private class NFCReadActivity extends AsyncTask<Tag, Void, String> {
    	@Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];
             
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag. 
                return null;
            }
     
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();
     
            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }
     
            return null;
        }
         
        private String readText(NdefRecord record) throws UnsupportedEncodingException {
            /*
             * See NFC forum specification for "Text Record Type Definition" at 3.2.1 
             * 
             * http://www.nfc-forum.org/specs/
             * 
             * bit_7 defines encoding
             * bit_6 reserved for future use, must be 0
             * bit_5..0 length of IANA language code
             */
     
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
    
}