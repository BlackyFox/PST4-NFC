package fr.esiea.nfc.pst4.loyalties;

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
import android.widget.Toast;

import bdd.MyBDD;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.AddCardFragment;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.HomeFragment;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.NavigationDrawerFragment;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.SeeCardsFragment;
import fr.esiea.nfc.pst4.loyalties.MainActivityFolder.SeeCompanyFragment;
import objects.People;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
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
        Toast.makeText(getApplicationContext(), "Welcome back "+people.getUsername()+" !", Toast.LENGTH_LONG).show();
        company = "";

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle().toString();
        arrTitle = getResources().getStringArray(R.array.titres);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

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
            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFrag)
                    .commit();
            //Log.d("ARR", menuArr[position]);
            //setTitle(menuArr[position]);
        }else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(currentFragment instanceof HomeFragment)) {
            Fragment objFrag = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, objFrag)
                    .commit();
        } else {
            this.finish();
        }
    }

    public String getUsername() { return this.people.getUsername(); }

    public void setCompany(String name) { this.company = name; }

    public String getCompany() { return this.company; }

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            MenuInflater inflat = getMenuInflater();
            inflat.inflate(R.menu.main, menu);
            /*getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            */
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            intent.putExtra("id", Integer.toString(people.getId()));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
