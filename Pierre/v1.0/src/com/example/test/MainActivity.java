package com.example.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class MainActivity extends Activity implements View.OnClickListener {
	private Button b1 = null;
	private Button b2 = null;
	private Button b3 = null;
	private EditText prenom = null;
	private EditText nom = null;
	private RadioGroup sexe = null;
	private CheckBox dieu = null;
	private TextView result = null;
	private ImageView img = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.follow);
        b2 = (Button) findViewById(R.id.raz);
        b3 = (Button) findViewById(R.id.nfc);
        prenom = (EditText) findViewById(R.id.prenom);
    	nom = (EditText) findViewById(R.id.nom);
    	sexe = (RadioGroup) findViewById(R.id.group);
    	dieu = (CheckBox) findViewById(R.id.god);
    	result = (TextView) findViewById(R.id.result);
    	img = (ImageView) findViewById(R.id.img);
    	
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        dieu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    	Intent intent = null;
    	
        switch(v.getId()) {
	        case R.id.follow:
	        {
	        	String sentence = null;
		        img.setImageResource(0);
	        	
	        	if(prenom.getText().toString() == "" || nom.getText().toString() == "" || (sexe.getCheckedRadioButtonId() != R.id.radiomeuf && sexe.getCheckedRadioButtonId() != R.id.radiomec))
	        		sentence = "Bouge ton cul et complète les champs demandés BÂTARD !";
	        	else
	        	{
		        	String p = prenom.getText().toString();
		        	String n = nom.getText().toString();
		        	
		        	
		        	if(dieu.isChecked())
		        	{
		            	sentence = "Oh divin";
		            	if(sexe.getCheckedRadioButtonId() == R.id.radiomeuf)
		            		sentence = sentence.concat("ité");
		            	sentence = sentence.concat(" ");
		        	}
		        	else
		        	{ sentence = "Coucou "; }
		        	
		        	sentence = sentence.concat(p + " " + n + ", quel courage d'avoir cliqué !");
	        	}
	        	
		        result.setText(sentence);
	        	
	        	break;
	        }
	        case R.id.raz:
	        {
	        	result.setText("");
	        	img.setImageResource(R.drawable.coucou);
	        	break;
	        }
	        case R.id.nfc:
	        {
	        	intent = new Intent(this, NFCActivity.class);
	        	startActivity(intent);
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
