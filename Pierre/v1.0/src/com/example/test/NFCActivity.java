package com.example.test;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NFCActivity extends Activity implements View.OnClickListener {
	private Button b1 = null;
	private Button b2 = null;
	private Button b3 = null;
	private TextView t1 = null;
	private NfcAdapter mNfcAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        boolean nfcEnabled = mNfcAdapter.isEnabled();
        
        b1 = (Button) findViewById(R.id.lecture);
	    b2 = (Button) findViewById(R.id.ecriture);
	    b3 = (Button) findViewById(R.id.refresh);
	    t1 = (TextView) findViewById(R.id.activNfc);
    	
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        
        if(nfcEnabled)
        {
        	t1.setText("Voulez-vous lire un tag ou envoyer des données ? :D");
        	b1.setVisibility(View.VISIBLE);
        	b2.setVisibility(View.VISIBLE);
        	b3.setVisibility(View.INVISIBLE);
        }
        else
        {
        	t1.setText("Activez le NFC ! ;)");
        	b1.setVisibility(View.INVISIBLE);
        	b2.setVisibility(View.INVISIBLE);
        	b3.setVisibility(View.VISIBLE);
        }
        
    }

    @Override
    public void onClick(View v) {
    	Intent intent = null;
    	
        switch(v.getId()) {
	        case R.id.lecture:
	        {
	        	intent = new Intent(this, LectureActivity.class);
	        	startActivity(intent);
	        	break;
	        }
	        case R.id.ecriture:
	        {
	        	intent = new Intent(this, EcritureActivity.class);
	        	startActivity(intent);
	        	break;
	        }
	        case R.id.refresh:
	        {
	        	finish();
	        	startActivity(getIntent());
	        	break;
	        }
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
