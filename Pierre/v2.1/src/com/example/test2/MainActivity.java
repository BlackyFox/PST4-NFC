package com.example.test2;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener {
	private TextView t1 = null;
	private LinearLayout ll1 = null;
	private Button b1 = null;
	private Button b2 = null;
	private Button b3 = null;
	
	private NfcAdapter mNfcAdapter = null;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        boolean nfcEnabled = mNfcAdapter.isEnabled();

        t1 = (TextView) findViewById(R.id.is_nfc_setted);
        ll1 = (LinearLayout) findViewById(R.id.read_or_write);
        b1 = (Button) findViewById(R.id.refresh);
        b2 = (Button) findViewById(R.id.read_button);
        b3 = (Button) findViewById(R.id.write_button);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        
        if(nfcEnabled)
        {
        	t1.setText("Vous pouvez, au choix, lire un tag ou en écrire un.");
        	ll1.setVisibility(View.VISIBLE);
        	b1.setVisibility(View.INVISIBLE);
        }
        else
        {
        	t1.setText("Assurez-vous d'avoir bien activé la fonction \"NFC\" de votre téléphone, puis actualisez la page !");
        	ll1.setVisibility(View.INVISIBLE);
        	b1.setVisibility(View.VISIBLE);
        }
    }


	@Override
	public void onClick(View v) {
    	Intent intent = null;
    	
        switch(v.getId()) {
	        case R.id.read_button:
	        {
	        	intent = new Intent(this, ReadActivity.class);
	        	startActivity(intent);
	        	break;
	        }
	        case R.id.write_button:
	        {
	        	intent = new Intent(this, WriteActivity.class);
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
