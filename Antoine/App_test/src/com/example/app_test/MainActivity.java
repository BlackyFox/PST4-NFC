package com.example.app_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	private Button NFC = null;
	private Button IMC = null;
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    	
	    NFC = (Button)findViewById(R.id.b_nfc);
	    IMC = (Button)findViewById(R.id.b_imc);
	    NFC.setOnClickListener(BNFC);
	    IMC.setOnClickListener(BIMC);
	  }
	  private OnClickListener BNFC = new OnClickListener(){
		  public void onClick(View v){
			  startActivity(new Intent(MainActivity.this, NFCActivity.class));
		  }
	  };
	  private OnClickListener BIMC = new OnClickListener(){
		  public void onClick(View v){
			  startActivity(new Intent(MainActivity.this, IMCActivity.class));
		  }
	  };
	  
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