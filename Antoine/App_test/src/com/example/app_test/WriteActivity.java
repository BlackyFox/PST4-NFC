package com.example.app_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WriteActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
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
