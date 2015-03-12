package com.example.pierre.applicompanies;

import android.app.Activity;
import android.content.res.Configuration;

/**
 * Created by Pierre on 12/03/2015.
 */
public class RemoveClientActivity extends Activity {


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