package fr.esiea.nfc.pst4.loyalties.MainActivityFolder;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Fragment de la page See Company. Affiche la liste des infos concernant une entreprise.         */
/**************************************************************************************************/

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import databasePackage.MyBDD;
import fr.esiea.nfc.pst4.loyalties.EmulateActivity;
import fr.esiea.nfc.pst4.loyalties.EmulationService;
import fr.esiea.nfc.pst4.loyalties.MainActivity;
import fr.esiea.nfc.pst4.loyalties.R;
import objectsPackage.*;


public class SeeCompanyFragment extends Fragment {

    Context context;

    View rootview;
    String username;
    String companyName;
    String num;
    Client client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();

        rootview = inflater.inflate(R.layout.fragment_see_company, container, false);
        TextView title = (TextView) rootview.findViewById(R.id.see_company_title);
        ImageView iv_card = (ImageView) rootview.findViewById(R.id.see_company_card);
        TextView card_number = (TextView) rootview.findViewById(R.id.see_company_number);
        Button emulate = (Button) rootview.findViewById(R.id.see_company_emulate);
        TextView points = (TextView) rootview.findViewById(R.id.see_company_points);
        String nbPoints;
        String op = null;
        TextView opp = (TextView) rootview.findViewById(R.id.see_company_opportunities);
        username = ((MainActivity)getActivity()).getUsername();
        companyName = ((MainActivity)getActivity()).getCompany();

        MyBDD bdd = new MyBDD(getActivity());
        bdd.open();
        People people = bdd.getPeopleWithUsername(username);
        Company company = bdd.getCompanyWithName(companyName);
        client = bdd.getClientWithKey(people.getId(), company.getId());

        num = client.getNum_client();

        nbPoints =  client.getNb_loyalties() + " points";


        Opportunity[] opportunities = bdd.getAllOpportunities(client.getId());
        Reduction tmpReduction;
        if(opportunities == null) {
            op = "No opportunities.";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < opportunities.length; i++) {
                tmpReduction = bdd.getReductionWithId(opportunities[i].getId_redu());
                sb.append(tmpReduction.getName());
                sb.append(" : ");
                sb.append(tmpReduction.getDescription());
                sb.append("\n");
            }
            op = sb.toString();
        }

        bdd.close();

        title.setText(companyName);
        card_number.setText(num);
        card_number.setGravity(Gravity.CENTER_HORIZONTAL);
        emulate.setOnClickListener(emulation);
        opp.setText(op);
        points.setText(nbPoints);

        String path = context.getFilesDir().getAbsolutePath();
        Bitmap card = BitmapFactory.decodeFile(path + "/" + company.getCard().toLowerCase() + ".png");
        Bitmap newCard = resizeImage(card, 210, 300);
        iv_card.setImageBitmap(newCard);

        ((MainActivity)getActivity()).setActionBarTitle("See company");
        return rootview;
    }

    View.OnClickListener emulation = new View.OnClickListener() {
        public void onClick(View v) {
            if(NfcAdapter.getDefaultAdapter(getActivity().getApplicationContext()).isEnabled()) {
                Toast.makeText(getActivity().getApplicationContext(), "Emulation", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity().getApplicationContext(), EmulateActivity.class);
                i.putExtra("CARD_NUM", num);
                getActivity().startActivity(i);
            }else{
                Toast.makeText(getActivity().getApplicationContext(),
                        "NFC is not activated !\nGo activate it in the app settings",
                        Toast.LENGTH_LONG).show();
            }
        }
    };

    public static Bitmap resizeImage(Bitmap image, int maxWidth, int maxHeight) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double imageAspect = (double) imageWidth / imageHeight;
        double canvasAspect = (double) maxWidth / maxHeight;
        double scaleFactor;

        if (imageAspect < canvasAspect) {
            scaleFactor = (double) maxHeight / imageHeight;
        } else {
            scaleFactor = (double) maxWidth / imageWidth;
        }

        float scaleWidth = ((float) scaleFactor) * imageWidth;
        float scaleHeight = ((float) scaleFactor) * imageHeight;

        // recreate the new Bitmap
        return Bitmap.createScaledBitmap(image, (int) scaleWidth, (int) scaleHeight, true);
    }
}