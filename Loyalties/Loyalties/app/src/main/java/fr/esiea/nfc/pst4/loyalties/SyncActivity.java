package fr.esiea.nfc.pst4.loyalties;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.esiea.nfc.pst4.loyalties.R;

public class SyncActivity extends ActionBarActivity {

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        username = getIntent().getStringExtra("username");


        Toast.makeText(getApplicationContext(), "Begin sync " + username, Toast.LENGTH_SHORT).show();
        sync();
        Toast.makeText(getApplicationContext(), "Sync done", Toast.LENGTH_LONG).show();
        finish();
    }

    private void sync() {
        //TODO
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sync, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //setContentView(R.layout.activity_main_activity2);

        } else {
            //setContentView(R.layout.activity_main_activity2);
        }
    }
}
