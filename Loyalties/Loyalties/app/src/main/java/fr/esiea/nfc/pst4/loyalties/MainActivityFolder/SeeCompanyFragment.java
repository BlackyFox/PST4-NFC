package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;

/**
 * Created by Pierre on 10/02/2015.
 */
public class SeeCompanyFragment extends Fragment {

    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_see_company, container, false);

        ((MainActivity)getActivity()).setActionBarTitle("See company");
        return rootview;
    }
}