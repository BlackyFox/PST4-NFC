package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bdd.MyBDD;
import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;
import objects.Client;
import objects.Company;
import objects.Opportunity;
import objects.People;
import objects.Reduction;

/**
 * Created by Pierre on 10/02/2015.
 */
public class SeeCompanyFragment extends Fragment {

    View rootview;
    private TextView result = null;
    String username;
    String companyName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_see_company, container, false);
        result = (TextView) rootview.findViewById(R.id.see_company_result);
        username = ((MainActivity)getActivity()).getUsername();
        companyName = ((MainActivity)getActivity()).getCompany();

        MyBDD bdd = new MyBDD((MainActivity)getActivity());
        bdd.open();
        People people = bdd.getPeopleWithUsername(username);
        Company company = bdd.getCompanyWithName(companyName);
        Client client = bdd.getClientWithKey(people.getId(), company.getId());

        String text = "Company : " + company.getName() + "\n";
        text += "Num client = " + client.getNum_client() + " with " + client.getNb_loyalties() + " loyalties points.\n";
        text += "Opportunities available : \n";

        Opportunity[] opportunities = bdd.getAllOpportunities(client.getId());
        Reduction tmpReduction;
        if(opportunities == null) {
            text += "\tNo opportunities.\n";
        } else {
            for (int i = 0; i < opportunities.length; i++) {
                tmpReduction = bdd.getReductionWithId(opportunities[i].getId_redu());
                text += "\t" + tmpReduction.getName() + " : " + tmpReduction.getDescription() + "\n";
            }
        }

        bdd.close();

        result.setText(text);
        ((MainActivity)getActivity()).setActionBarTitle("See company");
        return rootview;
    }
}