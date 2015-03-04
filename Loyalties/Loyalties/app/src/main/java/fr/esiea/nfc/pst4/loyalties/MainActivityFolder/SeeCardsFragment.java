package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Fragment de la page Cards. Affiche la liste des entreprises rejointes pour l'utilisateur       */
/**************************************************************************************************/


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import databasePackage.MyBDD;
import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;
import objectsPackage.People;

public class SeeCardsFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View rootview;
    CustomAdapter adapter;
    private List<RowItem> rowItems;
    String companies[];

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_see_cards, container, false);

        ((MainActivity)getActivity()).setActionBarTitle("See cards");
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        companies = null;
        MyBDD bdd = new MyBDD(getActivity());
        bdd.open();
        People people = bdd.getPeopleWithUsername(((MainActivity)getActivity()).getUsername());

        if(bdd.getLastCompanies(people.getId()) == null) {
            companies = new String[1];
            companies[0] = "No companies";
        } else {
            companies = bdd.getCompaniesJoinedByPeople(people.getId());
        }

        bdd.close();

        sortCompaniesByName();
        rowItems = new ArrayList<>();

        for (int i = 0; i < companies.length; i++) {
            RowItem items = new RowItem(companies[i], "/" + companies[i].toLowerCase() + "_logo.png");
            rowItems.add(items);
        }

        adapter = new CustomAdapter(getActivity(), rowItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    // Trie alphabétiquement le tableau de String (affiche entreprises dans l'ordre alphabétique)
    public void sortCompaniesByName() {
        Boolean flag = false;
        String tmp;
        while(!flag) {
            flag = true;
            for(int i = 0 ; i < companies.length-1 ; i++) {
                if(companies[i].compareTo(companies[i+1]) > 0) {
                    tmp = companies[i+1];
                    companies[i+1] = companies[i];
                    companies[i] = tmp;
                    flag = false;
                }
            }
        }
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