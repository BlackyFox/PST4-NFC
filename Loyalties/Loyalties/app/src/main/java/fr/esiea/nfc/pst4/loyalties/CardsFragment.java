package fr.esiea.nfc.pst4.loyalties;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Antoine on 17/01/2015.
 */
public class CardsFragment extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_cards, container, false);

        ((MainActivity)getActivity()).setActionBarTitle("Mes cartes");
        return rootview;
    }
}