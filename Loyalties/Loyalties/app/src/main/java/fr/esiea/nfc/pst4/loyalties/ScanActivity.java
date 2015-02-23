package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité permettant l'ajout d'une carte par scan NFC.                                          */
/**************************************************************************************************/

import android.app.Activity;
import android.os.Bundle;


public class ScanActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }
}
