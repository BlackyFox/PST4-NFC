package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bdd.MyBDD;
import fr.esiea.nfc.pst4.loyalties.AESencrp;
import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;
import objects.People;

public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View rootview;
    CustomAdapter adapter;
    private List<RowItem> rowItems;
    String companies[];

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
        Log.d("CRYPT", "Encrypted Text : " + passwordEnc);
        Log.d("CRYPT","Decrypted Text : " + passwordDec);

        ((MainActivity)getActivity()).setActionBarTitle("Home");
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        companies = new String[3];
        MyBDD bdd = new MyBDD(((MainActivity)getActivity()));
        bdd.open();
        People people = bdd.getPeopleWithUsername(((MainActivity)getActivity()).getUsername());
        System.out.println(people.getName());
        if(bdd.getLastCompanies(people.getId()) == null) {
            companies = new String[1];
            companies[0] = "No companies";
            System.out.println("I am here -> ");
        } else {
            companies = bdd.getLastCompanies(people.getId());
        }

        bdd.close();


        rowItems = new ArrayList<RowItem>();

        for (int i = 0; i < companies.length; i++) {
            RowItem items = new RowItem(companies[i], 0);

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
            FragmentManager fm = ((MainActivity) getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.container, fr);
            fragmentTransaction.commit();
        }
    }
}
