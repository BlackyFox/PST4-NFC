package fr.esiea.nfc.pst4.loyalties;

/**************************************************************************************************/
/* PS4 ESIEA - PUISSANT / ECARLAT / COSSOU - Sécurité NFC ; Porte-feuille de carte de fidélité    */
/* Activité contenant l'architecture principale de l'application. On y arrive après s'être        */
/* identifié.                                                                                     */
/**************************************************************************************************/

// TODO global : gérer téléchargement / affichage des images

import android.app.Activity;
import android.content.Intent;
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

import databasePackage.MyBDD;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.*;
import objectsPackage.People;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private String mTitle;
    private String[] arrTitle;
    private People people;
    private String company;

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

        mNavigationDrawerFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle().toString();
        arrTitle = getResources().getStringArray(R.array.titres);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
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
                intent = new Intent(this, ScanActivity.class);
                startActivity(intent);
                fr = new AddCardFragment();
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
        if (!(currentFragment instanceof HomeFragment)) {
            Fragment objFrag = new HomeFragment();
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
            startActivity(intent);
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
}
