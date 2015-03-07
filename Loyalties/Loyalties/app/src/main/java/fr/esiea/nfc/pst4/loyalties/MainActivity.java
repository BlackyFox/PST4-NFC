package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité contenant l'architecture principale de l'application. On y arrive après s'être        */
/* identifié.                                                                                     */
/**************************************************************************************************/

// TODO global : gérer téléchargement / affichage des images

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import databasePackage.MyBDD;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.*;
import objectsPackage.People;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    Context context;
    private Bitmap image1, image2, image3, image4, image5, image6, image7, image8;

    private String mTitle;
    private String[] arrTitle;
    private People people;
    private String company;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        MyBDD bdd = new MyBDD(this);
        bdd.open();
        people = bdd.getPeopleWithId(id);
        bdd.close();
        company = "";

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle().toString();
        arrTitle = getResources().getStringArray(R.array.titres);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


        context = getApplicationContext();
        ImageLoadTask ilt1 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/fnac_logo.png", "/fnac_logo.png");
        ImageLoadTask ilt2 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/fnac_card.png", "/fnac_card.png");
        ImageLoadTask ilt3 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/decathlon_logo.png", "/decathlon_logo.png");
        ImageLoadTask ilt4 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/decathlon_card.png", "/decathlon_card.png");
        ImageLoadTask ilt5 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/auchan_logo.png", "/auchan_logo.png");
        ImageLoadTask ilt6 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/auchan_card.png", "/auchan_card.png");
        ImageLoadTask ilt7 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/darty_logo.png", "/darty_logo.png");
        ImageLoadTask ilt8 = new ImageLoadTask("http://www.pierre-ecarlat.com/newSql/img/darty_card.png", "/darty_card.png");
        ilt1.execute();
        ilt2.execute();
        ilt3.execute();
        ilt4.execute();
        ilt5.execute();
        ilt6.execute();
        ilt7.execute();
        ilt8.execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Intent i = getIntent();
                finish();
                startActivity(i);
                /*Toast.makeText(getApplicationContext(), "Reaload, comme dans Matrix t'as-vu ? TMTC",
                        Toast.LENGTH_LONG).show();*/
            }
            if(resultCode == RESULT_CANCELED){
                //Nothing to do here
            }
        }
    }

    // Fonction pour intégrer une image dans le téléphone
    public void createImage(Bitmap image, String compPath) throws FileNotFoundException {
        String path = context.getFilesDir().getAbsolutePath();
        OutputStream stream = new FileOutputStream(path + "" + compPath);
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
    }

    // Fonction permettant de passer d'un fragment à l'autre
    public void switchFrag(View view) {
        Fragment fr = null;
        Intent intent;

        switch(view.getId()) {
            case R.id.home_cards:
                fr = new SeeCardsFragment();
                break;
            case R.id.home_add_card:
                fr = new AddCardFragment();
                break;
            case R.id.add_scan:
                if(NfcAdapter.getDefaultAdapter(getApplicationContext()).isEnabled()) {
                    intent = new Intent(this, ScanActivity.class);
                    startActivity(intent);
                    fr = new AddCardFragment();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "NFC is not activated !\nGo activate it in the app settings",
                            Toast.LENGTH_LONG).show();
                    fr = new AddCardFragment();
                }
                break;
            case R.id.add_manually:
                intent = new Intent(this, AddCardActivity.class);
                intent.putExtra("id", Integer.toString(people.getId()));
                startActivity(intent);
                fr = new AddCardFragment();
                break;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fr);
        fragmentTransaction.commit();
    }

    // Création du nouveau fragment
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment objFrag = null;

        switch (position){
            case 0:
                objFrag = new HomeFragment();
                //mTitle = arrTitle[position];
                restoreActionBar();
                break;
            case 1:
                objFrag = new SeeCardsFragment();
                //mTitle = arrTitle[position];
                restoreActionBar();
                break;
            case 2:
                objFrag = new AddCardFragment();
                //mTitle = arrTitle[position];
                restoreActionBar();
                break;
        }
        // update the main content by replacing fragments
        if(objFrag != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, objFrag).commit();
        }else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    // Gestion du bouton "back", retour aux fragments voulus (ou fermeture de l'appli)
    @Override
    public void onBackPressed() {
        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment instanceof SeeCardsFragment || currentFragment instanceof AddCardFragment) {
            Fragment objFrag = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, objFrag).commit();
        } else if (currentFragment instanceof SeeCompanyFragment) {
            Fragment objFrag = new SeeCardsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, objFrag).commit();
        } else {
            this.finish();
        }
    }


/** GESTION ACTIONBAR *****************************************************************************/
    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = arrTitle[number];
                break;
            case 1:
                mTitle = arrTitle[number];
                break;
            case 2:
                mTitle = arrTitle[number];
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


/** GESTION MENU -> SETTINGS **********************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            MenuInflater inflat = getMenuInflater();
            inflat.inflate(R.menu.main, menu);

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("id", Integer.toString(people.getId()));
            startActivityForResult(intent, 1);
        }

        return super.onOptionsItemSelected(item);
    }


/** GESTION DES FRAGMENTS *************************************************************************/
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


/** GETTER SETTER *********************************************************************************/
    public String getUsername() { return this.people.getUsername(); }

    public void setCompany(String name) { this.company = name; }

    public String getCompany() { return this.company; }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //setContentView(R.layout.activity_main_activity2);

        } else {
            //setContentView(R.layout.activity_main_activity2);
        }
    }


/** CLASSE TELECHARGEANT L'IMAGE ******************************************************************/
    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private String compPath;

        public ImageLoadTask(String url, String compPath) {
            this.url = url;
            this.compPath = compPath;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return (myBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        //after downloading
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            try {
                createImage(result, compPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
