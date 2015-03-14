package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Fragment de la page Home. Redirige vers la liste des cartes, l'ajout d'une nouvelle carte, les */
/* dernières cartes utilisées.                                                                    */
/**************************************************************************************************/

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import databasePackage.MyBDD;
import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;
import objectsPackage.People;

public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View rootview;
    CustomAdapter adapter;
    String companies[];
    private String username = "Welcome back ";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_home, container, false);

        username += ((MainActivity)getActivity()).getName()+"!";
        TextView tv = (TextView) rootview.findViewById(R.id.home_title);
        tv.setText(username);
        ((MainActivity)getActivity()).setActionBarTitle("Home");

        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        companies = new String[3];
        MyBDD bdd = new MyBDD(getActivity());
        bdd.open();
        People people = bdd.getPeopleWithUsername(((MainActivity)getActivity()).getUsername());

        if(bdd.getLastCompanies(people.getId()) == null) { // TODO : à modifier pour récupérer companies dans l'ordre
            companies = new String[1];
            companies[0] = "No companies";
        } else {
            companies = bdd.getLastCompanies(people.getId());
        }

        bdd.close();


        List<RowItem> rowItems = new ArrayList<>();

        for (int i = 0; i < companies.length; i++) {
            System.out.println(i + " -> " + companies[i]);
            RowItem items;
            if(companies[i].equals("No companies"))
                items = new RowItem(companies[i], "");
            else
                items = new RowItem(companies[i], "/" + companies[i].toLowerCase() + "_logo.png");
            rowItems.add(items);
        }

        adapter = new CustomAdapter(getActivity(), rowItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(!companies[position].equals("No companies")) {
            ((MainActivity) getActivity()).setCompany(companies[position]);
            Fragment fr = new SeeCompanyFragment();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.container, fr);
            fragmentTransaction.commit();
        }
    }
}
