package com.example.app_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class NFCActivity extends Activity {
	Button read = null;
	Button write = null;
	
	NfcAdapter nfc = null;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_nfc);
	    
	    nfc = NfcAdapter.getDefaultAdapter(this);
	    
	    read = (Button)findViewById(R.id.b_read);
	    write = (Button)findViewById(R.id.b_write);
	    
	    read.setOnClickListener(readl);
	    write.setOnClickListener(writel);	    
	}
	private OnClickListener readl = new OnClickListener(){
		  public void onClick(View v){
			  if(nfc.isEnabled()){
				  startActivity(new Intent(NFCActivity.this, NFCReadActivity.class)); //Ou ReadActivity.class
			  }else{
				  Context context = getApplicationContext();
				  CharSequence text = "NFC d�sactiv� ! Activez le, puis recommencez.";
				  int duration = Toast.LENGTH_SHORT;

				  Toast toast = Toast.makeText(context, text, duration);
				  toast.show();
			  }
		  }
	  };
	  private OnClickListener writel = new OnClickListener(){
		  public void onClick(View v){
			  if(nfc.isEnabled()){
				  startActivity(new Intent(NFCActivity.this, WriteActivity.class));
			  }else{
				  Context context = getApplicationContext();
				  CharSequence text = "NFC d�sactiv� ! Activez le, puis recommencez.";
				  int duration = Toast.LENGTH_SHORT;

				  Toast toast = Toast.makeText(context, text, duration);
				  toast.show();
			  }
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
