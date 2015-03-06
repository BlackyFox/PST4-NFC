package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant d'utiliser une carte NFC.                                                  */
/**************************************************************************************************/

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;


public class EmulateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulate);

        String card = null;

        if(this.getIntent().getExtras() != null)
            card = this.getIntent().getStringExtra("CARD_NUM");
        Intent i = new Intent(getApplicationContext(), EmulationService.class);
        i.putExtra("CARD_NUM", card);
        startService(i);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //setContentView(R.layout.activity_main_activity2);

        } else {
            //setContentView(R.layout.activity_main_activity2);
        }
    }
}
