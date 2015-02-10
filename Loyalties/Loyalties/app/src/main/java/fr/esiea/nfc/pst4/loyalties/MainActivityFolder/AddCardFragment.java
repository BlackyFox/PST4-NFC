package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;

/**
 * Created by Antoine on 17/01/2015.
 */
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