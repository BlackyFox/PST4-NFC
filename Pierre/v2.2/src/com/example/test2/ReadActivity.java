package com.example.test2;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
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
        	/** Carte étudiante :	Type TAG ***************************/
        	/** Passeport :			Type TAG ***************************/
        	/** Oyster Card :		Type TAG ***************************/
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
            	
            	/*****************************************************/
            	/** A tester, (= null) pour la CB + Carte étudiante **/
            	/*****************************************************/
            	
            } else {
            	Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            	t2.setText("Type :\n\t\tTag : " + NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
            					+ "\n\t\tTech : " + NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
            					+ "\n\t\tNDEF : " + NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
            				+ "\n\n"
            				+ "Caractéristiques"
            					+ "\n\t\tContents : " + tag.describeContents()
            					+ "\n\t\tID : " + tag.getId().toString()
            					+ "\n\t\tID (HEX) : " + getHex(tag.getId())
            					+ "\n\t\tID (DEC) : " + getDec(tag.getId())
            					+ "\n\t\tID (REV) : " + getReversed(tag.getId())
            					+ "\n\t\tTechList : " + getTech(tag)
            					+ "\n\t\tInfos : " + tag.toString());
            }
        } else {
          	t1.setText("Mettez un tag NFC contre le dos de votre appareil.");
        }
    }

    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private long getDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private long getReversed(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private String getTech(Parcelable p) {
    	StringBuilder sb = new StringBuilder();
        Tag tag = (Tag) p;

        String prefix = "android.nfc.tech.";
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
            	sb.append('\n');sb.append('\t');sb.append('\t');sb.append('\t');
                MifareClassic mifareTag = MifareClassic.get(tag);
                String type = "Unknown";
                switch (mifareTag.getType()) {
                case MifareClassic.TYPE_CLASSIC:
                    type = "Classic";
                    break;
                case MifareClassic.TYPE_PLUS:
                    type = "Plus";
                    break;
                case MifareClassic.TYPE_PRO:
                    type = "Pro";
                    break;
                }
                sb.append("Mifare Classic Type : ");
                sb.append(type);
            	sb.append('\n');sb.append('\t');sb.append('\t');sb.append('\t');

                sb.append("Mifare Classic Size : ");
                sb.append(mifareTag.getSize() + " bytes");
            	sb.append('\n');sb.append('\t');sb.append('\t');sb.append('\t');

                sb.append("Mifare Classic Sectors : ");
                sb.append(mifareTag.getSectorCount());
            	sb.append('\n');sb.append('\t');sb.append('\t');sb.append('\t');

                sb.append("Mifare Classic Blocks : ");
                sb.append(mifareTag.getBlockCount());
            }

            if (tech.equals(MifareUltralight.class.getName())) {
            	sb.append('\n');sb.append('\t');sb.append('\t');sb.append('\t');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                case MifareUltralight.TYPE_ULTRALIGHT:
                    type = "Ultralight";
                    break;
                case MifareUltralight.TYPE_ULTRALIGHT_C:
                    type = "Ultralight C";
                    break;
                }
                sb.append("Mifare Ultralight Type : ");
                sb.append(type);
            }
        }
            
        return sb.toString();
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