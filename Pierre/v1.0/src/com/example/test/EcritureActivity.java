package com.example.test;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EcritureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecriture);
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