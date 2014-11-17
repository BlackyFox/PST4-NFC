package com.example.app_test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class IMCActivity extends Activity{

	// La cha�ne de caract�res par d�faut
	  private final String defaut = "Vous devez cliquer sur le bouton � Calculer l'IMC � pour obtenir un r�sultat.";
	  // La cha�ne de caract�res de la megafonction
	  private final String megaString = "Vous faites un poids parfait ! Wahou ! Trop fort ! On dirait Brad Pitt (si vous �tes un homme)/Angelina Jolie (si vous �tes une femme)/Willy (si vous �tes un orque) !"; 
		
	  private final String amos = "Vous avez un corps de BERZERK !!\nAmos va venir vous d�couper puis vous d�vorer !!\n:D";
	  Button envoyer = null;
	  Button raz = null;
		
	  EditText poids = null;
	  EditText taille = null;
		
	  RadioGroup group = null;
		
	  TextView result = null;
		
	  CheckBox mega = null;
	  CheckBox xiii = null;
		
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_imc);
	    	
	    // On r�cup�re toutes les vues dont on a besoin
	    envoyer = (Button)findViewById(R.id.calcul);
	    	
	    raz = (Button)findViewById(R.id.raz);
	    	
	    taille = (EditText)findViewById(R.id.taille);
	    poids = (EditText)findViewById(R.id.poids);
	    	
	    mega = (CheckBox)findViewById(R.id.mega);
	    xiii = (CheckBox)findViewById(R.id.xiii);
	    	
	    group = (RadioGroup)findViewById(R.id.group);

	    result = (TextView)findViewById(R.id.result);
	    
	    
	    // On attribue un listener adapt� aux vues qui en ont besoin
	    envoyer.setOnClickListener(envoyerListener);
	    raz.setOnClickListener(razListener);
	    taille.addTextChangedListener(textWatcher);
	    poids.addTextChangedListener(textWatcher);

	    // Solution avec des onKey
	    //taille.setOnKeyListener(modificationListener);
	    //poids.setOnKeyListener(modificationListener);
	    mega.setOnClickListener(checkedListener);
	    xiii.setOnClickListener(checkedListener);
	  }

	  /*
	  // Se lance � chaque fois qu'on appuie sur une touche en �tant sur un EditText
	  private OnKeyListener modificationListener = new OnKeyListener() {
	    @Override
	    public boolean onKey(View v, int keyCode, KeyEvent event) {
	      // On remet le texte � sa valeur par d�faut pour ne pas avoir de r�sultat incoh�rent
	      result.setText(defaut);
	      return false;
	    }
	  };*/

	  private TextWatcher textWatcher = new TextWatcher() {

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	      result.setText(defaut);
	    }
			
	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count,
	      int after) {
	  
	    }
	  
	    @Override
	    public void afterTextChanged(Editable s) {
	  
	    }
	  };
		
	  // Uniquement pour le bouton "envoyer"
	  private OnClickListener envoyerListener = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	        String t = taille.getText().toString();
	        // On r�cup�re le poids
	        String p = poids.getText().toString();
	        if(t != null && p != null){
	      if(!mega.isChecked() && !xiii.isChecked()) {
	        float tValue = Float.valueOf(t);
				
	        // Puis on v�rifie que la taille est coh�rente
	        if(tValue == 0)
	          Toast.makeText(IMCActivity.this, "H�ho, tu es un Minipouce ou quoi ?", Toast.LENGTH_SHORT).show();
	        else {
	          float pValue = Float.valueOf(p);
	          // Si l'utilisateur a indiqu� que la taille �tait en centim�tres
	          // On v�rifie que la Checkbox s�lectionn�e est la deuxi�me � l'aide de son identifiant
	          if(group.getCheckedRadioButtonId() == R.id.radio2)
	            tValue = tValue / 100;

	          tValue = (float)Math.pow(tValue, 2);
	          float imc = pValue / tValue;
	          result.setText("Votre IMC est " + String.valueOf(imc));
	        }
	      } else{
	    	  if(xiii.isChecked()){
	    		  result.setText(amos);
	    	  }else
	    		  result.setText(megaString);
	      }
	    }else{
	    	Toast.makeText(IMCActivity.this, "Hum hum... remplissez les cases !", Toast.LENGTH_SHORT).show();
	    }
	    }
	  };
		
	  // Listener du bouton de remise � z�ro
	  private OnClickListener razListener = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	      poids.getText().clear();
	      taille.getText().clear();
	      result.setText(defaut);
	    }
	  };
		
	  // Listener du bouton de la megafonction.
	  private OnClickListener checkedListener = new OnClickListener() {
	    @Override
	    public void onClick(View v) {
	      // On remet le texte par d�faut si c'�tait le texte de la megafonction qui �tait �crit
	      if(!((CheckBox)v).isChecked() && result.getText().equals(megaString))
	        result.setText(defaut);
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