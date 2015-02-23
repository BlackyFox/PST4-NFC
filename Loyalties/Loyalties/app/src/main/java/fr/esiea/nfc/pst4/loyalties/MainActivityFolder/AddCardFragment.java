package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Fragment pour l'ajout d'une carte, contient deux boutons (ajout manuel et scan).               */
/**************************************************************************************************/

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;


public class AddCardFragment extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_add_card, container, false);

        ((MainActivity)getActivity()).setActionBarTitle("Add card");
        return rootview;
    }
}