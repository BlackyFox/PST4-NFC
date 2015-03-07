package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant d'utiliser une carte NFC.                                                  */
/**************************************************************************************************/

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class EmulateActivity extends Activity {

    Intent i;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                int resultCode = bundle.getInt(EmulationService.RESULT);
                if(resultCode == 0){
                    Toast.makeText(getApplicationContext(), "Emulation is a succes!", Toast.LENGTH_LONG).show();
                    //TODO Faire un pdialog
                    finish();

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Emulation failed! Error code = "+resultCode, Toast.LENGTH_LONG).show();
                    //TODO faire un pdialog avec try again
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emulate);

        String card = null;

        if(this.getIntent().getExtras() != null)
            card = this.getIntent().getStringExtra("CARD_NUM");
        i = new Intent(getApplicationContext(), EmulationService.class);
        i.putExtra("CARD_NUM", card);
        this.startService(i);
        Log.d("EMULATIONACTI", "Service started");
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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(EmulationService.SERVICE_OVER));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
