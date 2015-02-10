package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.esiea.nfc.pst4.loyalties.AESencrp;
import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;

public class HomeFragment extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_home, container, false);

        String password = "mypassword";
        String passwordEnc = null;
        try {
            passwordEnc = AESencrp.encrypt(password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String passwordDec = null;
        try {
            passwordDec = AESencrp.decrypt(passwordEnc);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("CRYPT","Plain Text : " + password);
        Log.d("CRYPT","Encrypted Text : " + passwordEnc);
        Log.d("CRYPT","Decrypted Text : " + passwordDec);

        ((MainActivity)getActivity()).setActionBarTitle("Home");
        return rootview;
    }
}
